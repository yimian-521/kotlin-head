package com.qitong.head.bugdb

/**
 * Kotlin BugDB — v0.12.1 kotlin-int+ 全面 Bug 数据库
 *
 * 1000条规则：300轻度(警告) + 500中度(可能错误) + 200严重(崩溃/损坏)
 * 覆盖：空安全/泛型/协程/集合/反射/DSL/数据类/JVM互操作/多平台/智能转换/内联/密封类
 *
 * 哨兵检测进程全杀 + 轻松汇报。不是"加更严"——是"看得更全"。
 */
data class BugRule(
    val id: String,          // KT-XXXX
    val category: BugCategory,
    val severity: BugSeverity,
    val title: String,
    val trigger: String,     // 触发条件（代码模式）
    val detection: String,   // 检测方法
    val fix: String          // 修复建议
)

enum class BugCategory(val label: String) {
    NULL_SAFETY("空安全"),
    GENERICS("泛型/型变"),
    COROUTINES("协程"),
    COLLECTIONS("集合"),
    REFLECTION("反射"),
    DSL_LAMBDA("DSL/Lambda"),
    DATA_SERIAL("数据类/序列化"),
    INLINE_TAILREC("内联/尾递归"),
    JAVA_INTEROP("Java互操作"),
    MULTIPLATFORM("多平台"),
    SMART_CAST("智能转换"),
    VALUE_CLASS("值类/内联类"),
    SEALED_ENUM("密封类/枚举"),
    DELEGATE("属性委托"),
    CONCURRENCY("并发/线程"),
    COMPOSE("Compose UI"),
    KMP("KMP跨平台"),
    COMPILER_TRAP("编译器陷阱"),
    PERFORMANCE("性能"),
    SECURITY("安全")
}

enum class BugSeverity(val label: String, val weight: Int) {
    MILD("轻度·警告", 1),       // 不会崩，但不够好
    MODERATE("中度·可能错误", 3), // 可能导致错误
    SEVERE("严重·必杀", 10)      // 崩溃或数据损坏
}

/**
 * BugDB — 规则库加载器和哨兵查询接口
 */
object BugDB {
    private val rules = mutableListOf<BugRule>()
    private var triggerIndex: MutableMap<String, MutableList<BugRule>>? = null
    /** 超预索引缓存：代码指纹 → 命中规则ID列表。不算第二遍。 */
    private val scanCache = object : LinkedHashMap<String, List<BugRule>>(64, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, List<BugRule>>?): Boolean = size > 64
    }
    val all: List<BugRule> get() = rules

    fun load(r: BugRule) {
        rules.add(r)
        triggerIndex = null  // 失效索引，下次scan时重建
    }
    fun loadAll(rs: List<BugRule>) { rules.addAll(rs); triggerIndex = null }

    /** 建倒排索引：trigger词 → 命中该词的规则列表 */
    private fun ensureIndex(): Map<String, List<BugRule>> {
        triggerIndex?.let { return it }
        val idx = mutableMapOf<String, MutableList<BugRule>>()
        for (r in rules) {
            for (t in r.trigger.split("|")) {
                val key = t.trim()
                idx.getOrPut(key) { mutableListOf() }.add(r)
            }
        }
        triggerIndex = idx
        return idx
    }

    fun bySeverity(s: BugSeverity): List<BugRule> = rules.filter { it.severity == s }
    fun byCategory(c: BugCategory): List<BugRule> = rules.filter { it.category == c }
    fun byKeyword(q: String): List<BugRule> = rules.filter {
        it.title.contains(q, true) || it.trigger.contains(q, true)
    }

    fun stats(): String = buildString {
        append("BugDB 规则统计\n")
        append("━━━━━━━━━━━━━━━━━━━━\n")
        BugSeverity.values().forEach { s ->
            val c = rules.count { it.severity == s }
            append("  ${s.label}: $c 条\n")
        }
        append("  总计: ${rules.size} 条\n")
        append("━━━━━━━━━━━━━━━━━━━━\n")
        BugCategory.values().forEach { cat ->
            val c = rules.count { it.category == cat }
            if (c > 0) append("  ${cat.label}: $c 条\n")
        }
    }

    /**
     * 哨兵快速扫描——倒排索引，O(去重关键词数) 非 O(规则数)
     * 5000条规则去重后仅几十个关键词，与100条延迟同量级。
     */
    fun scan(code: String): List<BugRule> {
        val fp = code.hashCode().toString()
        scanCache[fp]?.let { return it }
        val idx = ensureIndex()
        val seen = mutableSetOf<String>()
        val hits = mutableListOf<BugRule>()
        for ((trigger, matchedRules) in idx) {
            val trimmed = trigger.trim()
            val matched = when {
                trimmed.startsWith("pattern:") ->
                    code.contains(trimmed.removePrefix("pattern:").trim())
                trimmed.startsWith("!pattern:") ->
                    !code.contains(trimmed.removePrefix("!pattern:").trim())
                else -> code.contains(trimmed, true)
            }
            if (matched) {
                for (r in matchedRules) {
                    if (seen.add(r.id)) hits.add(r)
                }
            }
        }
        return hits.apply { scanCache[fp] = this }
    }
}