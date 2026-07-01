package com.qitong.head.internal

import java.io.File

/** 从 capabilities.json 加载版本路线图。单例，启动时加载。 */
object Capabilities {
    private val roadmap: MutableMap<String, String> = mutableMapOf()

    init {
        load()
    }

    private fun load() {
        val file = File("/sdcard/Download/Operit/search_vault/kotlin-head/capabilities.json")
        if (!file.exists()) return
        val json = try {
            JsonUtil.decode(file.readText()) as? Map<*, *> ?: return
        } catch (_: Exception) { return }
        val rm = json["roadmap"] as? Map<*, *> ?: return
        for ((version, entry) in rm) {
            val kwMap = entry as? Map<*, *> ?: continue
            val keywords = kwMap["keywords"] as? List<*> ?: continue
            for (kw in keywords) {
                roadmap[kw.toString()] = version.toString()
            }
        }
    }

    fun expectedFor(keyword: String): String? = roadmap[keyword.toLowerCase()]

    fun allExpected(): List<Pair<String, String>> =
        roadmap.entries.map { it.key to it.value }.sortedBy { it.second }
}