package com.qitong.head.pass

import com.qitong.head.runtime.HList
import com.qitong.head.eventbus.StreamTransform
import com.qitong.head.ir.*

/**
 * Pass 优化管线 —— v0.8.2
 *
 * 每个 Pass 是一个 StreamTransform<IRModule, IRModule>，
 * 走 EventBus 流式通道串联：上一个 Pass 输出 = 下一个输入。
 * 可插拔——加新 Pass 不改现有代码。
 *
 * 与 IR 协作：IR 产出中间表示，Pass 在 IR 上做优化。
 * IR 没做到的（死代码、冗余常量、函数调用开销），Pass 补。
 */

// ─── Pass 基类 ───
abstract class Pass(val name: String) : StreamTransform<IRModule, IRModule> {
    abstract override fun transform(input: IRModule): IRModule

    companion object {
        /** 标准三件套 */
        fun standardChain(): HList<Pass> = HList.from(listOf(
            DeadCodeElimination(),
            ConstantFolding(),
            InlineExpansion()
        ))
    }
}

// ─── 死代码消除：去掉不会被用到的指令 ───
class DeadCodeElimination : Pass("dead-code-elim") {
    override fun transform(input: IRModule): IRModule {
        for (func in input.functions) {
            // 收集所有被引用的 dest
            val used = mutableSetOf<String>()
            for (inst in func.instructions) {
                when (inst) {
                    is IRBinary -> { used.add(inst.left); used.add(inst.right) }
                    is IRUnary -> used.add(inst.operand)
                    is IRCall -> inst.args.forEach { used.add(it) }
                    is IRLoad -> used.add(inst.from)
                    is IRStore -> { used.add(inst.to); used.add(inst.from) }
                    is IRCondJump -> used.add(inst.cond)
                    is IRReturn -> inst.value?.let { used.add(it) }
                    is IRFieldAccess -> used.add(inst.obj)
                    else -> {}
                }
            }
            // 去掉 dest 从未被引用的指令
            val toRemove = mutableListOf<IRInst>()
            var labelIdx = 0
            for (inst in func.instructions) {
                val dest = when (inst) {
                    is IRBinary -> inst.dest
                    is IRUnary -> inst.dest
                    is IRCall -> inst.dest
                    is IRLit -> inst.dest
                    is IRAlloc -> inst.dest
                    is IRFieldAccess -> inst.dest
                    is IRLoad -> inst.dest
                    else -> null
                }
                if (dest != null && !dest.startsWith("ret_") && dest !in used) {
                    toRemove.add(inst)
                }
                // 保留标签但去掉无用的
                if (inst is IRLabel && func.instructions.none { it is IRJump && it.label == inst.name || it is IRCondJump && (it.trueLabel == inst.name || it.falseLabel == inst.name) }) {
                    toRemove.add(inst)
                }
            }
            func.instructions.removeAll(toRemove)
        }
        input.metadata["pass:dead-code-elim"] = "done"
        return input
    }
}

// ─── 常量折叠：编译期计算常量表达式 ───
class ConstantFolding : Pass("const-fold") {
    private fun isIntLit(s: String) = s.toIntOrNull()
    private fun isStrLit(s: String) = s.startsWith("\"") && s.endsWith("\"")

    override fun transform(input: IRModule): IRModule {
        var folded = 0
        for (func in input.functions) {
            val litValues = mutableMapOf<String, String>()
            // 先收集所有字面量
            for (inst in func.instructions) {
                if (inst is IRLit) litValues[inst.dest] = inst.value
            }
            // 替换可用常量计算的指令
            val newInsts = mutableListOf<IRInst>()
            for (inst in func.instructions) {
                var handled = false
                if (inst is IRBinary) {
                    val l = litValues[inst.left] ?: inst.left
                    val r = litValues[inst.right] ?: inst.right
                    val li = isIntLit(l)
                    val ri = isIntLit(r)
                    if (li != null && ri != null) {
                        val result = when (inst.op) {
                            "+" -> li + ri
                            "-" -> li - ri
                            "*" -> li * ri
                            "/" -> if (ri != 0) li / ri else null
                            "%" -> if (ri != 0) li % ri else null
                            else -> null
                        }
                        if (result != null) {
                            newInsts.add(IRLit(inst.dest, result.toString(), "Int"))
                            newInsts.add(IRComment("folded: ${inst.left} ${inst.op} ${inst.right} = $result"))
                            litValues[inst.dest] = result.toString()
                            folded++
                            handled = true
                        }
                    }
                    if (!handled && inst.op == "+" && isStrLit(l) && isStrLit(r)) {
                        val result = "\"${l.trim('"')}${r.trim('"')}\""
                        newInsts.add(IRLit(inst.dest, result, "String"))
                        litValues[inst.dest] = result
                        folded++
                        handled = true
                    }
                    if (!handled) newInsts.add(inst)
                } else if (inst is IRLit) {
                    litValues[inst.dest] = inst.value
                    newInsts.add(inst)
                } else {
                    newInsts.add(inst)
                }
            }
            func.instructions.clear()
            func.instructions.addAll(newInsts)
        }
        input.metadata["pass:const-fold"] = "folded=$folded"
        return input
    }
}

// ─── 内联展开：简单函数调用替换为函数体 ───
class InlineExpansion : Pass("inline") {
    override fun transform(input: IRModule): IRModule {
        val funcMap = input.functions.associateBy { it.name }
        var inlined = 0
        for (func in input.functions) {
            val newInsts = mutableListOf<IRInst>()
            for (inst in func.instructions) {
                if (inst is IRCall && inst.func in funcMap) {
                    val target = funcMap[inst.func]!!
                    // 只内联行数少的函数（≤3条有效指令）
                    if (target.instructionCount() <= 3 && target.name != func.name) {
                        newInsts.add(IRComment("inline start: ${inst.func}"))
                        for (ti in target.instructions) {
                            if (ti is IRLabel || ti is IRComment) {
                                newInsts.add(ti)
                            } else {
                                // 参数替换
                                if (ti is IRReturn) {
                                    inst.dest?.let { d ->
                                        ti.value?.let { newInsts.add(IRLoad(d, it)) }
                                    }
                                } else if (ti is IRBinary) {
                                    newInsts.add(IRBinary(
                                        inst.dest ?: ti.dest, ti.op,
                                        target.params.getOrNull(inst.args.indexOf(ti.left))?.first ?: ti.left,
                                        target.params.getOrNull(inst.args.indexOf(ti.right))?.first ?: ti.right
                                    ))
                                } else {
                                    newInsts.add(ti)
                                }
                            }
                        }
                        newInsts.add(IRComment("inline end: ${inst.func}"))
                        inlined++
                    } else {
                        newInsts.add(inst)
                    }
                } else {
                    newInsts.add(inst)
                }
            }
            func.instructions.clear()
            func.instructions.addAll(newInsts)
        }
        input.metadata["pass:inline"] = "inlined=$inlined"
        return input
    }
}