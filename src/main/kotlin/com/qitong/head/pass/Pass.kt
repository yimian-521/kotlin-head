package com.qitong.head.pass

import com.qitong.head.headstd.HList
import com.qitong.head.headstd.HMap
import com.qitong.head.runtime.ProHList
import com.qitong.head.headstd.HeadStd
import com.qitong.head.eventbus.StreamTransform
import com.qitong.head.ir.*

/**
 * Pass 优化管线 —— v0.12.0（ProHList + 新量折叠缓存）
 */

// ─── Pass 基类 ───
abstract class Pass(val name: String) : StreamTransform<IRModule, IRModule> {
    abstract override fun transform(input: IRModule): IRModule

    companion object {
        fun standardChain(): HList<Pass> = HList.from(listOf(
            DeadCodeElimination(), ConstantFolding(), InlineExpansion()
        ))
        fun names(): String = standardChain().joinToString("→") { it.name }
    }
}

// ─── 死代码消除：remove标记 + apply清理 ───
class DeadCodeElimination : Pass("dead-code-elim") {
    override fun transform(input: IRModule): IRModule {
        for (func in input.functions.toList()) {
            val used = mutableSetOf<String>()
            for (inst in func.instructions.toList()) {
                when (inst) {
                    is IRBinary -> { used.add(inst.left); used.add(inst.right) }
                    is IRUnary -> used.add(inst.operand)
                    is IRCall -> inst.args.toList().forEach { used.add(it) }
                    is IRLoad -> used.add(inst.from)
                    is IRStore -> { used.add(inst.to); used.add(inst.from) }
                    is IRCondJump -> used.add(inst.cond)
                    is IRReturn -> inst.value?.let { used.add(it) }
                    is IRFieldAccess -> used.add(inst.obj)
                    else -> {}
                }
            }
            val toRemove = mutableListOf<IRInst>()
            for (inst in func.instructions.toList()) {
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
                if (inst is IRLabel && func.instructions.toList().none {
                    it is IRJump && it.label == inst.name ||
                    it is IRCondJump && (it.trueLabel == inst.name || it.falseLabel == inst.name)
                }) {
                    toRemove.add(inst)
                }
            }
            for (r in toRemove) { func.instructions.remove(r) }
            func.instructions.apply()
        }
        input.metadata.put("pass:dead-code-elim", "done")
        return input
    }
}

// ─── 常量折叠：HMap新量折叠缓存 ───
class ConstantFolding : Pass("const-fold") {
    // foldCache 已迁移到 HeadStd.foldCache——独立频道，下版接内存树
    private fun isIntLit(s: String) = s.toIntOrNull()
    private fun isStrLit(s: String) = s.startsWith("\"") && s.endsWith("\"")

    override fun transform(input: IRModule): IRModule {
        var folded = 0
        for (func in input.functions.toList()) {
            val litValues = HMap<String, String>()
            for (inst in func.instructions.toList()) {
                if (inst is IRLit) litValues.put(inst.dest, inst.value)
            }
            val newInsts = ProHList<IRInst>()
            for (inst in func.instructions.toList()) {
                var handled = false
                if (inst is IRBinary) {
                    val exprKey = "${inst.left}${inst.op}${inst.right}"
                    // 新量折叠：先查缓存
                    val cached = HeadStd.foldCache.get(exprKey)
                    if (cached != null) {
                        newInsts.add(IRLit(inst.dest, cached, "Int"))
                        newInsts.add(IRComment("cache-hit: $exprKey = $cached"))
                        litValues.put(inst.dest, cached)
                        folded++
                        handled = true
                    } else {
                        val l = litValues.get(inst.left) ?: inst.left
                        val r = litValues.get(inst.right) ?: inst.right
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
                                val s = result.toString()
                                newInsts.add(IRLit(inst.dest, s, "Int"))
                                newInsts.add(IRComment("folded: $exprKey = $s"))
                                litValues.put(inst.dest, s)
                                HeadStd.foldCache.put(exprKey, s)  // 存缓存
                                folded++
                                handled = true
                            }
                        }
                        if (!handled && inst.op == "+" && isStrLit(l) && isStrLit(r)) {
                            val result = "\"${l.trim('"')}${r.trim('"')}\""
                            newInsts.add(IRLit(inst.dest, result, "String"))
                            litValues.put(inst.dest, result)
                            HeadStd.foldCache.put(exprKey, result)
                            folded++
                            handled = true
                        }
                    }
                    if (!handled) newInsts.add(inst)
                } else if (inst is IRLit) {
                    litValues.put(inst.dest, inst.value)
                    newInsts.add(inst)
                } else {
                    newInsts.add(inst)
                }
            }
            func.instructions = newInsts  // 直接赋值，ProHList add-only零开销
        }
        input.metadata.put("pass:const-fold", "folded=$folded")
        return input
    }
}

// ─── 内联展开：纯add，apply零开销 ───
class InlineExpansion : Pass("inline") {
    override fun transform(input: IRModule): IRModule {
        val funcMap = input.functions.toList().associateBy { it.name }
        var inlined = 0
        for (func in input.functions.toList()) {
            val newInsts = ProHList<IRInst>()
            for (inst in func.instructions.toList()) {
                if (inst is IRCall && inst.func in funcMap) {
                    val target = funcMap[inst.func]!!
                    if (target.instructionCount() <= 3 && target.name != func.name) {
                        newInsts.add(IRComment("inline start: ${inst.func}"))
                        val argsList = inst.args.toList()
                        for (ti in target.instructions.toList()) {
                            when {
                                ti is IRLabel || ti is IRComment -> newInsts.add(ti)
                                ti is IRReturn -> {
                                    inst.dest?.let { d ->
                                        ti.value?.let { newInsts.add(IRLoad(d, it)) }
                                    }
                                }
                                ti is IRBinary -> {
                                    newInsts.add(IRBinary(
                                        inst.dest ?: ti.dest, ti.op,
                                        target.params.getOrNull(argsList.indexOf(ti.left))?.first ?: ti.left,
                                        target.params.getOrNull(argsList.indexOf(ti.right))?.first ?: ti.right
                                    ))
                                }
                                else -> newInsts.add(ti)
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
            func.instructions = newInsts  // 直接赋值，纯add零开销
        }
        input.metadata.put("pass:inline", "inlined=$inlined")
        return input
    }
}