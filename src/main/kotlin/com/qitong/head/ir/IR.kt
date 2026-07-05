package com.qitong.head.ir

/**
 * IR 中间表示 —— v0.8.2
 *
 * 三地址码风格：每条指令最多三个操作数。
 * AST → IR → Pass 优化管线 → 输出。换语言不换 IR。
 *
 * 与 Pass 协作：IR 没做到的，Pass 补；Pass 输出更干净的 IR，下游受益。
 */

// ─── 指令 ───
sealed class IRInst

/** 标签（跳转目标） */
data class IRLabel(val name: String) : IRInst()

/** 二元运算: dest = left op right */
data class IRBinary(val dest: String, val op: String, val left: String, val right: String) : IRInst()

/** 一元运算: dest = op operand */
data class IRUnary(val dest: String, val op: String, val operand: String) : IRInst()

/** 函数调用: dest = func(args) */
data class IRCall(val dest: String?, val func: String, val args: List<String>) : IRInst()

/** 加载: dest = from */
data class IRLoad(val dest: String, val from: String) : IRInst()

/** 存储: to = from */
data class IRStore(val to: String, val from: String) : IRInst()

/** 字面量: dest = value */
data class IRLit(val dest: String, val value: String, val type: String = "Any") : IRInst()

/** 条件跳转: if cond goto trueLabel else falseLabel */
data class IRCondJump(val cond: String, val trueLabel: String, val falseLabel: String) : IRInst()

/** 无条件跳转 */
data class IRJump(val label: String) : IRInst()

/** 返回 */
data class IRReturn(val value: String?) : IRInst()

/** 分配局部变量 */
data class IRAlloc(val dest: String, val type: String) : IRInst()

/** 字段访问: dest = obj.field */
data class IRFieldAccess(val dest: String, val obj: String, val field: String) : IRInst()

/** 注释（不计入代码，供调试） */
data class IRComment(val text: String) : IRInst()

// ─── 函数 ───
data class IRFunction(
    val name: String,
    val params: List<Pair<String, String>>,  // name to type
    val returnType: String,
    val instructions: MutableList<IRInst> = mutableListOf()
) {
    fun add(inst: IRInst) { instructions.add(inst) }
    fun instructionCount() = instructions.count { it !is IRComment && it !is IRLabel }
}

// ─── 模块 ───
data class IRModule(
    val name: String = "kotlin-head",
    val functions: MutableList<IRFunction> = mutableListOf(),
    val metadata: MutableMap<String, String> = mutableMapOf()
) {
    fun addFunction(func: IRFunction) { functions.add(func) }
    fun totalInstructions(): Int {
        var sum = 0
        for (f in functions) sum += f.instructionCount()
        return sum
    }
    fun functionCount() = functions.size
}