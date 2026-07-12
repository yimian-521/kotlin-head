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

    fun pack(dir: String, keystorePath: String = "", keystorePass: String = "", keyAlias: String = ""): PackReport {
        val status = checkTools()
        if (!status.allAvailable()) return PackReport(false, listOf("工具缺失: ${status.joinToString()}"), com.qitong.head.headstd.HList())
        val base = dir.trimEnd('/')
        val buildDir = "$base/build"
        val outputName = base.substringAfterLast('/').ifEmpty { "app" }
        val unsignedApk = "$buildDir/${outputName}-unsigned.apk"
        val signedApk = "$buildDir/$outputName.apk"
        
        try {
            // Step1: kotlinc → classes.jar → d8 → classes.dex
            if (!compileToDex(base, buildDir)) return PackReport(false, listOf("compileToDex失败"), com.qitong.head.headstd.HList())
            
            // Step2: aapt2 link
            val manifest = "$base/AndroidManifest.xml"
            val resDir = "$base/res"
            if (java.io.File(manifest).exists()) {
                val aapt2 = ProcessBuilder(listOf("aapt2", "link", "-o", unsignedApk, "-I", "${androidJar()}", "--manifest", manifest, if (java.io.File(resDir).exists()) "-S" to resDir else "" to "").filter { it.second.isNotEmpty() }.flatMap { listOf(it.first, it.second) })
                    .redirectErrorStream(true).start()
                if (!aapt2.waitFor(30, java.util.concurrent.TimeUnit.SECONDS) || aapt2.exitValue() != 0) return PackReport(false, listOf("aapt2失败"), com.qitong.head.headstd.HList())
            } else {
                // 无manifest: 用基础manifest
                val genManifest = "$buildDir/AndroidManifest.xml"
                java.io.File(genManifest).writeText("<manifest package='com.example'/>")
                val aapt2 = ProcessBuilder(listOf("aapt2", "link", "-o", unsignedApk, "--manifest", genManifest))
                    .redirectErrorStream(true).start()
                if (!aapt2.waitFor(30, java.util.concurrent.TimeUnit.SECONDS) || aapt2.exitValue() != 0) return PackReport(false, listOf("aapt2失败"), com.qitong.head.headstd.HList())
            }
            
            // Step3: zipalign
            val alignedApk = "$buildDir/${outputName}-aligned.apk"
            val align = ProcessBuilder(listOf("zipalign", "-f", "4", unsignedApk, alignedApk)).redirectErrorStream(true).start()
            if (!align.waitFor(10, java.util.concurrent.TimeUnit.SECONDS) || align.exitValue() != 0) return PackReport(false, listOf("zipalign失败"), com.qitong.head.headstd.HList())
            
            // Step4: apksigner (如果提供了密钥)
            val finalApk = if (keystorePath.isNotEmpty()) {
                val sign = ProcessBuilder(listOf("apksigner", "sign", "--ks", keystorePath, "--ks-pass", "pass:$keystorePass", "--ks-key-alias", keyAlias, signedApk)).redirectErrorStream(true).start()
                if (!sign.waitFor(10, java.util.concurrent.TimeUnit.SECONDS) || sign.exitValue() != 0) return PackReport(false, listOf("签名失败"), com.qitong.head.headstd.HList())
                signedApk
            } else alignedApk
            
            val artifacts = listOfNotNull(
                if (java.io.File(finalApk).exists()) finalApk else null,
                "$buildDir/classes.dex",
                "$buildDir/classes.jar"
            ).filter { java.io.File(it).exists() }
            return PackReport(artifacts.isNotEmpty(), artifacts, com.qitong.head.headstd.HList())
        } catch (e: Exception) {
            return PackReport(false, listOf(e.message ?: "未知错误"), com.qitong.head.headstd.HList())
        }
    }

    private fun toolExists(name: String): Boolean {
        return try {
            val p = Runtime.getRuntime().exec(arrayOf("which", name))
            p.waitFor() == 0
        } catch (_: Exception) { false }
    }

    private fun compileToDex(sourceDir: String, buildDir: String): Boolean {
        return try {
            val srcDir = java.io.File(sourceDir)
            val outDir = java.io.File(buildDir).also { it.mkdirs() }
            val classJar = java.io.File(outDir, "classes.jar")
            val dexFile = java.io.File(outDir, "classes.dex")
            val ktFiles = srcDir.walk().filter { it.isFile && it.extension == "kt" }.map { it.absolutePath }.toList()
            if (ktFiles.isEmpty()) return false

            val kotlinc = ProcessBuilder(listOf("kotlinc", "-d", classJar.absolutePath) + ktFiles)
                .redirectErrorStream(true).start()
            if (!kotlinc.waitFor(20, java.util.concurrent.TimeUnit.SECONDS) || kotlinc.exitValue() != 0) return false
            if (!classJar.exists()) return false

            val d8 = ProcessBuilder(listOf("d8", "--output", dexFile.absolutePath, classJar.absolutePath))
                .redirectErrorStream(true).start()
            if (!d8.waitFor(10, java.util.concurrent.TimeUnit.SECONDS) || d8.exitValue() != 0) return false
            dexFile.exists()
        } catch (_: Exception) { false }
    }

    private fun androidJar(): String {
        val candidates = listOf(
            System.getenv("ANDROID_HOME")?.let { "$it/platforms/android-34/android.jar" },
            "/usr/lib/android-sdk/platforms/android-34/android.jar",
            "/opt/android-sdk/platforms/android-34/android.jar"
        )
        return candidates.filterNotNull().firstOrNull { java.io.File(it).exists() } ?: ""
    }
}
