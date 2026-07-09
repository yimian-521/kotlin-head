package com.qitong.head.tomb

/**
 * 墓碑引擎 —— bug修复合约注册表。
 * 进程树上场前查询：此文件有多少已知bug修复合约。
 * 日常零开销，查询时零遗漏。
 *
 * 🟡 已知取舍：行号是修复时的历史快照——重构导致偏移为预期行为，墓碑不是实时追踪器
 */
object BugTombstone {
    data class Entry(
        val fileKey: String,   // 精确源文件名（如"SimUiScanner.kt"），查询时==比较
        val line: Int,          // bug所在行号（修复时的快照，重构后可能偏移）
        val type: String,       // bug类型
        val fix: String,        // 修复方案
        val version: String = "" // 修复版本
    )

    private val registry = mutableListOf<Entry>()

    /** 注册一个墓碑——修完bug后调用。线程安全。 */
    @Synchronized
    fun register(fileKey: String, line: Int, type: String, fix: String, version: String = "") {
        registry.add(Entry(fileKey, line, type, fix, version))
    }

    /** 按文件名查询墓碑列表——精确匹配源文件名。线程安全。 */
    @Synchronized
    fun query(fileName: String): List<Entry> {
        val name = java.io.File(fileName).name  // 提取纯文件名，忽略路径
        return registry.toList().filter { it.fileKey == name }
    }

    /** 查询墓碑数量 */
    fun count(fileName: String): Int = query(fileName).size

    /** 查询墓碑摘要——给SceneEngine用 */
    fun summary(fileName: String): String {
        val list = query(fileName)
        if (list.isEmpty()) return ""
        return list.joinToString("; ") { "L${it.line}:${it.type}" }
    }

    // ══════════ 默认注册表：已知bug修复合约 ══════════
    private val defaultRegistry = listOf(
        // v0.12.8 SimUiScanner修复
        Entry("SimUiScanner.kt", 109,
            "NPE: when else分支null吞错",
            "b.condition?.let + b.body?.let", "v0.12.8"),
        Entry("SimUiScanner.kt", 116,
            "KtCall.target漏遍历",
            "walk(listOf(node.target))", "v0.12.8"),
        // v0.12.8 Parser解耦
        Entry("Parser.kt", 241,
            "传染性: ParseDiag→TypeChecker.Diag",
            "toTypeCheckerDiag()适配层", "v0.12.8")
    )

    init {
        registry.addAll(defaultRegistry)
    }
}