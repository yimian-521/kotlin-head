package com.qitong.head.tomb

import com.qitong.head.headstd.HMap
import com.qitong.head.headstd.HList

/**
 * 墓碑引擎 —— bug修复合约注册表。HMap索引查询O(1)。
 * fingerprint追踪独立为 queryWithTracking——count/summary不触发。
 */
object BugTombstone {
    data class Entry(
        val fileKey: String, val line: Int, val type: String,
        val fix: String, val version: String = "", val fingerprint: String = ""
    )

    private val registry = HMap<String, HList<Entry>>()

    @Synchronized
    fun register(fileKey: String, line: Int, type: String, fix: String, version: String = "", fingerprint: String = "") {
        val e = Entry(fileKey, line, type, fix, version, fingerprint)
        var list = registry.get(fileKey)
        if (list == null) { list = HList(); registry.put(fileKey, list) }
        list.add(e)
    }

    fun query(fileName: String): List<Entry> {
        val list = registry.get(java.io.File(fileName).name) ?: return emptyList()
        return list.toList()
    }

    fun queryWithTracking(fileName: String, sourceCode: String): List<Entry> {
        val list = registry.get(java.io.File(fileName).name) ?: return emptyList()
        val result = mutableListOf<Entry>()
        for (i in 0 until list.size) {
            val e = list[i]
            if (e.fingerprint.isEmpty()) { result.add(e); continue }
            val idx = sourceCode.indexOf(e.fingerprint)
            result.add(if (idx >= 0) e.copy(line = sourceCode.substring(0, idx).count { it == '\n' } + 1)
                       else e.copy(line = -e.line))
        }
        return result
    }

    fun count(fileName: String): Int = registry.get(java.io.File(fileName).name)?.size ?: 0
    fun totalTombstones(): Int { var t = 0; registry.values().forEach { t += it.size }; return t }
    fun summary(fileName: String): String = query(fileName).joinToString("; ") { "L${it.line}:${it.type}" }.ifEmpty { "" }
    fun clear() { registry.clear() }

    init {
        register("SimUiScanner.kt", 109, "NPE: when else分支null吞错", "b.condition?.let + b.body?.let", "v0.12.8", "b.condition?.let { walk(listOf(it)")
        register("SimUiScanner.kt", 116, "KtCall.target漏遍历", "walk(listOf(node.target))", "v0.12.8", "walk(listOf(node.target)")
        register("Parser.kt", 241, "传染性: ParseDiag→TypeChecker.Diag", "toTypeCheckerDiag()适配层", "v0.12.8", "toTypeCheckerDiag")
    }
}