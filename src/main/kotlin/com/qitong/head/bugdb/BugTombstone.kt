package com.qitong.head.bugdb

/**
 * ⚰️ BugTombstone — 墓碑引擎（v0.12.9）
 *
 * 独立注册表：已修复/已关闭的 bug 在此立碑。
 * 四接口：register / query / count / summary
 * 特征码动态偏移——代码自己当自己指纹，前插10行自动追踪。
 * 进程树对接：SceneInput + briefOf + prepareArmy。
 */
object BugTombstone {
    private val registry = mutableMapOf<String, TombEntry>()
    private val defaultRegistry = mutableListOf<TombEntry>()

    data class TombEntry(
        val bugId: String,
        val fixDescription: String,
        val fingerprint: String,   // 特征码
        val offset: Int = 0,       // 动态偏移（前插行数）
        val timestamp: Long = System.currentTimeMillis()
    )

    /** 注册一个已修复bug */
    @Synchronized
    fun register(bugId: String, fixDescription: String, fingerprint: String, offset: Int = 0) {
        val entry = TombEntry(bugId, fixDescription, fingerprint, offset)
        registry[bugId] = entry
        defaultRegistry.add(entry)
    }

    /** 查询墓碑 */
    fun query(bugId: String): TombEntry? = registry[bugId]

    /** 墓碑总数 */
    fun count(): Int = registry.size

    /** 汇总报告 */
    fun summary(): String = buildString {
        append("═════ ⚰️ 墓碑引擎 ═════\n")
        append("已安葬: ${registry.size} 个bug\n")
        if (registry.isEmpty()) {
            append("暂无墓碑——要么没bug，要么还没杀\n")
        } else {
            registry.values.take(5).forEach {
                append("  🪦 ${it.bugId}: ${it.fixDescription.take(60)}\n")
            }
            if (registry.size > 5) append("  ... 还有 ${registry.size - 5} 个\n")
        }
    }

    /** 特征码匹配——代码偏移后仍可追踪 */
    fun matchByFingerprint(code: String): List<TombEntry> {
        val matches = mutableListOf<TombEntry>()
        for (entry in defaultRegistry) {
            // 动态偏移：跳过前N行后匹配特征码
            val skipLines = entry.offset
            val codeAfterOffset = if (skipLines > 0) {
                code.lines().drop(skipLines).joinToString("\n")
            } else code
            if (entry.fingerprint in codeAfterOffset) {
                matches.add(entry)
            }
        }
        return matches
    }

    /** 清空所有墓碑 */
    fun clear() {
        registry.clear()
        defaultRegistry.clear()
    }
}