package com.qitong.head.ir

import com.qitong.head.headstd.HList
import com.qitong.head.headstd.HMap
import com.qitong.head.runtime.ProHList

/**
 * IR 中间表示 —— v0.12.0（底层换 ProHList/HMap）
 */

// ─── 指令 ───
sealed class IRInst

data class IRLabel(val name: String) : IRInst()
data class IRBinary(val dest: String, val op: String, val left: String, val right: String) : IRInst()
data class IRUnary(val dest: String, val op: String, val operand: String) : IRInst()
data class IRCall(val dest: String?, val func: String, val args: HList<String>) : IRInst()
data class IRLoad(val dest: String, val from: String) : IRInst()
data class IRStore(val to: String, val from: String) : IRInst()
data class IRLit(val dest: String, val value: String, val type: String = "Any") : IRInst()
data class IRCondJump(val cond: String, val trueLabel: String, val falseLabel: String) : IRInst()
data class IRJump(val label: String) : IRInst()
data class IRReturn(val value: String?) : IRInst()
data class IRAlloc(val dest: String, val type: String) : IRInst()
data class IRFieldAccess(val dest: String, val obj: String, val field: String) : IRInst()
data class IRComment(val text: String) : IRInst()

// ─── 函数 ───
data class IRFunction(
    val name: String,
    val params: List<Pair<String, String>>,
    val returnType: String,
    var instructions: ProHList<IRInst> = ProHList()
) {
    fun add(inst: IRInst) { instructions.add(inst) }
    fun instructionCount(): Int = instructions.count { it !is IRComment && it !is IRLabel }
}

// ─── 模块 ───
data class IRModule(
    val name: String = "kotlin-head",
    var functions: ProHList<IRFunction> = ProHList(),
    val metadata: HMap<String, String> = HMap()
) {
    fun addFunction(func: IRFunction) { functions.add(func) }
    fun totalInstructions(): Int {
        var sum = 0
        functions.forEach { sum += it.instructionCount() }
        return sum
    }
    fun functionCount() = functions.effectiveSize()
}