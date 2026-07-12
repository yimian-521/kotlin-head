package com.qitong.head.simui

/**
 * v0.12.8: 原生探针格式（免免三层架构：Schema→适配层→消费者）
 * 
 * Parser 只吃 SimSchema。外部框架经适配层翻译到此格式。
 * 手搓节点——不属于官方 AST 体系。
 */

/** 节点类型 */
enum class NodeKind { BUTTON, CLICKABLE, STATE_VAR, UNKNOWN }

/** 原生探针节点 — 轻量（7字段）+ 全能（自描述） */
data class SimNode(
    val id: String,              // "Button@L10:5" / "var@L15:page"
    val kind: NodeKind,
    val label: String,
    val actionHint: String,      // onClick 目标推测
    val deps: List<String>,      // 依赖的状态变量
    val line: Int,
    val confidence: Double,      // 适配层初始置信度
    val sourceRef: Any? = null   // 仅溯源，不依赖其字段
)

/** 候选集管理 — 内置 + 外部注册，线程安全 */
object SimSchema {
    @Volatile private var builtIn = mutableSetOf(
        "Button", "IconButton", "TextButton", "OutlinedButton",
        "FloatingActionButton", "clickable", "onClick", "Modifier.clickable",
        "Text", "Image", "Row", "Column", "Box", "Card"
    )
    @Volatile private var _external = mutableSetOf<String>()

    fun register(name: String) { synchronized(this) { _external.add(name) } }
    fun unregister(name: String) { synchronized(this) { _external.remove(name) } }
    fun addBuiltIn(name: String) { synchronized(this) { builtIn.add(name) } }

    fun isCandidate(name: String): Boolean =
        name in builtIn || name in _external

    fun registered(): Set<String> = builtIn + _external
    fun clear() { synchronized(this) { _external.clear() } }
}
