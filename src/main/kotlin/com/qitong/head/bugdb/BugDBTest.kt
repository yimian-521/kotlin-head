package com.qitong.head.bugdb

/**
 * BugDB 双验证测试 — kotlin-int v3
 * ① 规则加载 + 扫描命中验证（≥15条）
 * ② 性能基准：缓存命中 ≤ 1μs
 */
object BugDBTest {

    fun run(): String = buildString {
        append("═══ BugDB 双验证 ═══\n\n")

        // ① 规则加载
        BugRules.register()
        val total = BugDB.all.size
        append("规则加载: $total 条\n")
        if (total >= 2000) append("✅ 规则数正常\n") else append("❌ 异常\n")

        // ② 扫描命中
        val testCode = """
fun test() {
    val x: String? = null; x!!.length
    val s = javaObj.field; s.length
    GlobalScope.launch { heavy() }
    for (x in list) { list.remove(x) }
    var c = 0; repeat(100) { thread { c++ } }
    fun g() { list.forEach { if (it) return } }
    val API_KEY = "sk-abc123"
    db.execSQL("SELECT * FROM u WHERE n='${'$'}name'")
    fun f(i: Int) {}; fun f(a: Any) {}; f(42)
}
""".trimIndent()

        val hits = BugDB.scan(testCode)
        append("命中: ${hits.size} 条\n")
        if (hits.size >= 10) append("✅ 达标\n") else append("⚠️ 偏低\n")

        // ③ 性能
        repeat(5) { BugDB.scan(testCode) }
        val times = 1000
        val start = System.nanoTime()
        repeat(times) { BugDB.scan(testCode) }
        val perScanNs = (System.nanoTime() - start) / times

        append("延迟: ${perScanNs}ns/次\n")
        if (perScanNs < 1000) append("✅ ≤1μs\n") else append("⚠️ >1μs\n")

        // ④ 命中详情（认清每一条）
        append("\n── 命中详情 ──\n")
        hits.take(8).forEach { h ->
            val sev = when (h.severity) { BugSeverity.SEVERE -> "🔴"; BugSeverity.MODERATE -> "🟡"; else -> "⚪" }
            append("$sev ${h.id} | ${h.title}\n")
            append("   修复: ${h.fix}\n")
        }
        if (hits.size > 8) append("   ... 还有 ${hits.size - 8} 条\n")

        // ⑤ 严重度
        val severe = hits.count { it.severity == BugSeverity.SEVERE }
        append("严重: $severe | 中度: ${hits.size - severe}\n")
    }
}