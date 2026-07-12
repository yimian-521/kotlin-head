package com.qitong.head.process

import com.qitong.head.headstd.HList

object ApkPackCoordinator {
    data class PackReport(
        val success: Boolean = false,
        val artifacts: List<String> = emptyList(),
        val watchReports: HList<Any> = com.qitong.head.headstd.HList()
    )
    
    data class ToolStatus(
        private val all: Boolean = true,
        private val miss: List<String> = emptyList()
    ) {
        fun allAvailable() = all
        fun missing() = miss
        fun joinToString() = miss.joinToString()
    }

    fun checkTools(): ToolStatus {
        val required = listOf("aapt2", "kotlinc", "d8", "zipalign", "apksigner")
        val missing = required.filter { !toolExists(it) }
        return ToolStatus(missing.isEmpty(), missing)
    }

    fun pack(dir: String, previousArtifacts: List<String> = emptyList()): PackReport {
        val status = checkTools()
        if (!status.allAvailable()) return PackReport(false, emptyList(), com.qitong.head.headstd.HList())
        val artifacts = mutableListOf<String>()
        val base = dir.trimEnd('/')
        try {
            // 按构建步骤收集产物
            listOf("$base/build/outputs/apk", "$base/build/libs", "$base/build/intermediates").forEach { d ->
                java.io.File(d).walkTopDown().filter { it.isFile }.forEach { artifacts.add(it.absolutePath) }
            }
        } catch (_: Exception) {}
        return PackReport(artifacts.isNotEmpty(), artifacts, com.qitong.head.headstd.HList())
    }

    private fun toolExists(name: String): Boolean {
        return try {
            val p = Runtime.getRuntime().exec(arrayOf("which", name))
            p.waitFor() == 0
        } catch (_: Exception) { false }
    }
}
