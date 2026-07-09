package com.qitong.head.tomb

/**
 * 墓碑引擎 —— bug修复合约注册表。
 * 进程树上场前查询：此文件有多少已知bug修复合约。
 * 日常零开销，查询时零遗漏。
 */
object BugTombstone {
    data class Entry(
        val fileKey: String,   // 文件名匹配关键字
        val line: Int,          // bug所在行号
        val type: String,       // bug类型
        val fix: String,        // 修复方案
        val version: String = "" // 修复版本
    )

    private val registry = mutableListOf<Entry>()

    /** 注册一个墓碑——修完bug后调用 */
    fun register(fileKey: String, line: Int, type: String, fix: String, version: String = "") {
        registry.add(Entry(fileKey, line, type, fix, version))
    }

    /** 按文件名查询墓碑列表 */
    fun query(fileName: String): List<Entry> {
        return registry.filter { fileName.contains(it.fileKey, ignoreCase = true) }
    }

    /** 查询墓碑数量 */
    fun count(fileName: String): Int = query(fileName).size

    /** 查询墓碑摘要——给SceneEngine用 */
    fun summary(fileName: String): String {
        val list = query(fileName)
        if (list.isEmpty()) return ""
        return list.joinToString("; ") { "L${it.line}:${it.type}" }
    }

    // ══════════ 初始化：注册已知bug修复合约 ══════════
    init {
        // v0.12.8 SimUiScanner修复
        register("SimUiScanner", 109,
            "NPE: when else分支null吞错",
            "b.condition?.let + b.body?.let", "v0.12.8")
        register("SimUiScanner", 116,
            "KtCall.target漏遍历",
            "walk(listOf(node.target))", "v0.12.8")
        // v0.12.8 Parser解耦
        register("Parser", 241,
            "传染性: ParseDiag→TypeChecker.Diag",
            "toTypeCheckerDiag()适配层", "v0.12.8")
    }
}