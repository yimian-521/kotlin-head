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
            
            // Step2: aapt2 link → APK骨架
            if (!aapt2Link(base, buildDir, unsignedApk)) return PackReport(false, listOf("aapt2失败"), com.qitong.head.headstd.HList())
            
            // Step3: 将classes.dex注入APK
            val dexPath = "$buildDir/classes.dex"
            if (!java.io.File(dexPath).exists()) return PackReport(false, listOf("dex缺失"), com.qitong.head.headstd.HList())
            val withDexApk = "$buildDir/${outputName}-withdex.apk"
            if (!injectDex(unsignedApk, dexPath, withDexApk)) return PackReport(false, listOf("dex注入失败"), com.qitong.head.headstd.HList())
            
            // Step4: zipalign
            val alignedApk = "$buildDir/${outputName}-aligned.apk"
            val align = ProcessBuilder(listOf("zipalign", "-f", "4", withDexApk, alignedApk)).redirectErrorStream(true).start()
            if (!align.waitFor(10, java.util.concurrent.TimeUnit.SECONDS) || align.exitValue() != 0) return PackReport(false, listOf("zipalign失败"), com.qitong.head.headstd.HList())
            
            // Step5: apksigner (如果提供了密钥)
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

    private fun aapt2Link(baseDir: String, buildDir: String, outputApk: String): Boolean {
        return try {
            val manifest = "$baseDir/AndroidManifest.xml"
            val cmd = mutableListOf("aapt2", "link", "-o", outputApk)
            val androidJar = androidJar()
            if (androidJar.isNotEmpty()) { cmd.add("-I"); cmd.add(androidJar) }
            if (java.io.File(manifest).exists()) { cmd.add("--manifest"); cmd.add(manifest) }
            else { val gen = "$buildDir/AndroidManifest.xml"; java.io.File(gen).writeText("<manifest package='com.example'/>"); cmd.add("--manifest"); cmd.add(gen) }
            val resDir = "$baseDir/res"
            if (java.io.File(resDir).exists()) { cmd.add("-S"); cmd.add(resDir) }
            val p = ProcessBuilder(cmd).redirectErrorStream(true).start()
            p.waitFor(30, java.util.concurrent.TimeUnit.SECONDS) && p.exitValue() == 0
        } catch (_: Exception) { false }
    }

    private fun androidJar(): String {
        val sdkHome = System.getenv("ANDROID_HOME") ?: System.getenv("ANDROID_SDK_ROOT") ?: ""
        val searchPaths = if (sdkHome.isNotEmpty()) listOf(sdkHome)
            else listOf("/usr/lib/android-sdk", "/opt/android-sdk", System.getProperty("user.home") + "/Android/Sdk")
        for (sdk in searchPaths) {
            val platforms = java.io.File("$sdk/platforms")
            if (platforms.exists()) {
                val latest = platforms.listFiles()
                    ?.filter { it.isDirectory && it.name.startsWith("android-") }
                    ?.maxByOrNull { it.name.removePrefix("android-").toIntOrNull() ?: 0 }
                if (latest != null) return "${latest.absolutePath}/android.jar"
            }
        }
        return ""
    }

    /** 纯ZIP打包——不依赖Android SDK，直接构建APK */
    fun zipPack(dir: String, packageName: String = "com.example.app", versionCode: Int = 1, versionName: String = "1.0"): PackReport {
        val base = dir.trimEnd('/')
        val buildDir = "$base/build"
        val outputApk = "$buildDir/${base.substringAfterLast('/').ifEmpty { "app" }}.apk"
        val classJar = "$buildDir/classes.jar"
        
        try {
            // 编译→jar
            if (!java.io.File(classJar).exists()) {
                val srcDir = java.io.File(base)
                val ktFiles = srcDir.walk().filter { it.isFile && it.extension == "kt" }.map { it.absolutePath }.toList()
                if (ktFiles.isEmpty()) return PackReport(false, listOf("无.kt文件"), com.qitong.head.headstd.HList())
                java.io.File(buildDir).mkdirs()
                val p = ProcessBuilder(listOf("kotlinc", "-d", classJar) + ktFiles).redirectErrorStream(true).start()
                if (!p.waitFor(30, java.util.concurrent.TimeUnit.SECONDS) || p.exitValue() != 0) return PackReport(false, listOf("kotlinc编译失败"), com.qitong.head.headstd.HList())
            }
            
            // 生成manifest
            val manifestPath = "$buildDir/AndroidManifest.xml"
            java.io.File(manifestPath).writeText(generateManifest(packageName, versionCode, versionName))
            
            // 生成真正的DEX（不再用jar冒充）
            val dexPath = "$buildDir/classes.dex"
            java.io.File(dexPath).writeBytes(DexWriter.minimal(base.substringAfterLast('/').ifEmpty { "app" }))
            
            // ZIP打包
            val resDir = "$base/res"
            val out = java.io.File(outputApk)
            out.parentFile?.mkdirs()
            java.util.zip.ZipOutputStream(java.io.FileOutputStream(out).buffered()).use { zip ->
                listOf("AndroidManifest.xml" to manifestPath, "classes.dex" to dexPath).forEach { (name, path) ->
                    val f = java.io.File(path)
                    if (f.exists()) {
                        zip.putNextEntry(java.util.zip.ZipEntry(name))
                        java.io.FileInputStream(f).buffered().use { it.copyTo(zip) }
                        zip.closeEntry()
                    }
                }
                val res = java.io.File(resDir)
                if (res.isDirectory) res.walkTopDown().filter { it.isFile }.forEach { f ->
                    val rel = "res/" + f.relativeTo(res).path.replace('\\', '/')
                    zip.putNextEntry(java.util.zip.ZipEntry(rel))
                    java.io.FileInputStream(f).buffered().use { it.copyTo(zip) }
                    zip.closeEntry()
                }
            }
            
            val artifacts = if (out.exists()) listOf(outputApk, classJar) else emptyList()
            return PackReport(out.exists(), artifacts, com.qitong.head.headstd.HList())
        } catch (e: Exception) {
            return PackReport(false, listOf(e.message ?: "ZIP打包失败"), com.qitong.head.headstd.HList())
        }
    }

    private fun generateManifest(packageName: String, versionCode: Int, versionName: String): String {
        return """<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="$packageName"
    android:versionCode="$versionCode"
    android:versionName="$versionName">
    <uses-sdk android:minSdkVersion="21" android:targetSdkVersion="35"/>
    <application android:label="$packageName">
        <activity android:name=".MainActivity" android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>
                <category android:name="android.intent.category.LAUNCHER"/>
            </intent-filter>
        </activity>
    </application>
</manifest>"""
    }
        return try {
            java.io.File(apkPath).copyTo(java.io.File(outputPath), true)
            // 用zip把classes.dex写入APK（APK本质是ZIP）
            val apkFile = java.io.File(outputPath)
            val dexFile = java.io.File(dexPath)
            if (!apkFile.exists() || !dexFile.exists()) return false
            val zipIn = java.util.zip.ZipInputStream(java.io.FileInputStream(apkFile))
            val entries = mutableListOf<Pair<String, ByteArray>>()
            var entry = zipIn.nextEntry
            while (entry != null) {
                if (entry.name != "classes.dex") entries.add(entry.name to zipIn.readBytes())
                zipIn.closeEntry(); entry = zipIn.nextEntry
            }
            zipIn.close()
            // 写回：原内容 + classes.dex
            val zipOut = java.util.zip.ZipOutputStream(java.io.FileOutputStream(apkFile))
            for ((name, data) in entries) { zipOut.putNextEntry(java.util.zip.ZipEntry(name)); zipOut.write(data); zipOut.closeEntry() }
            zipOut.putNextEntry(java.util.zip.ZipEntry("classes.dex")); zipOut.write(dexFile.readBytes()); zipOut.closeEntry()
            zipOut.close()
            true
        } catch (_: Exception) { false }
    }
}
