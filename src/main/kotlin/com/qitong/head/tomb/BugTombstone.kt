package com.qitong.head.tomb

/**
 * 墓碑引擎 —— bug修复合约注册表。
 * 进程树上场前查询：此文件有多少已知bug修复合约。
 * 日常零开销，查询时零遗漏。
 * 特征码动态追踪——重构后行号自动偏移，代码删了才标⚠️。
 */
object BugTombstone {
    data class Entry(
        val fileKey: String,   // 精确源文件名（如"SimUiScanner.kt"），查询时==比较
        val line: Int,          // bug所在行号（修复时的快照，重构后通过fingerprint动态追踪）
        val type: String,       // bug类型
        val fix: String,        // 修复方案
        val version: String = "", // 修复版本
        val fingerprint: String = "" // 特征码：修复位置的代码行内容，用于动态偏移追踪
    )

    private val registry = mutableListOf<Entry>()

    /** 注册一个墓碑——修完bug后调用。线程安全。 */
    @Synchronized
    fun register(fileKey: String, line: Int, type: String, fix: String, version: String = "") {
        registry.add(Entry(fileKey, line, type, fix, version))
    }

    /** 按文件名查询墓碑列表——精确匹配源文件名。线程安全。 */
    @Synchronized
    fun query(fileName: String, sourceCode: String? = null): List<Entry> {
        val name = java.io.File(fileName).name
        return registry.toList().filter { it.fileKey == name }.map { entry ->
            if (sourceCode != null && entry.fingerprint.isNotEmpty()) {
                val idx = sourceCode.indexOf(entry.fingerprint)
                if (idx >= 0) {
                    // 特征码找到——计算新行号
                    val newLine = sourceCode.substring(0, idx).count { it == '\n' } + 1
                    entry.copy(line = newLine)
                } else {
                    // 特征码消失——标记偏移
                    entry.copy(line = -entry.line)  // 负数表示偏移
                }
            } else entry
        }
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
            "b.condition?.let + b.body?.let", "v0.12.8",
            "b.condition?.let { walk(listOf(it)"),
        Entry("SimUiScanner.kt", 116,
            "KtCall.target漏遍历",
            "walk(listOf(node.target))", "v0.12.8",
            "walk(listOf(node.target)"),
        // v0.12.8 Parser解耦
        Entry("Parser.kt", 241,
            "传染性: ParseDiag→TypeChecker.Diag",
            "toTypeCheckerDiag()适配层", "v0.12.8",
            "toTypeCheckerDiag")
    )

    init {
        registry.addAll(defaultRegistry)
    }
}