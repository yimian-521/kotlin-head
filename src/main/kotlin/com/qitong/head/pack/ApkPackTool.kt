package com.qitong.head.pack

import java.io.File
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream
import java.util.zip.ZipEntry

/**
 * APK轻量打包器 — 纯Kotlin，基于kotlin-head。
 * 与QitongEmbedded同级——适配层不碰内核。
 *
 * 用法:
 *   val result = ApkPackTool.pack(projectDir)
 *   // result.apkPath / result.success / result.log
 */
object ApkPackTool {

    data class PackResult(
        val success: Boolean,
        val apkPath: String?,
        val log: List<String>
    )

    /** 打包入口 */
    fun pack(projectDir: String, templateApk: String? = null): PackResult {
        val log = mutableListOf<String>()
        val proj = File(projectDir)
        if (!proj.exists()) return PackResult(false, null, listOf("✖ 项目目录不存在: $projectDir"))

        val name = proj.name
        log += "═══ APK打包器 (kotlin-head v0.13.1) ═══"
        log += "项目: $name"

        // 1. 检测项目类型
        val (lang, srcFiles) = detectProject(proj)
        log += "语言: $lang | 源文件: ${srcFiles.size}个"
        if (srcFiles.isEmpty()) return PackResult(false, null, log + "✖ 未找到源文件")

        // 2. 编译→classes.jar
        val outDir = File(proj, "app/build/outputs/apk/debug")
        outDir.mkdirs()
        val classesJar = File(outDir, "classes.jar")
        val compileOk = when (lang) {
            "kotlin" -> compileKotlin(srcFiles, classesJar, log)
            "java"   -> compileJava(srcFiles, classesJar, log)
            "node"   -> packNode(srcFiles, outDir, log)
            else     -> { log += "✖ 不支持的语言: $lang"; return PackResult(false, null, log) }
        }
        if (!compileOk) return PackResult(false, null, log)

        // 3. 注入模板APK
        val tmpl = templateApk ?: findTemplate()
        val apk = File(outDir, "$name-debug.apk")
        val injected = if (lang == "node") {
            // Node已经单独输出
            apk.absolutePath
        } else {
            injectIntoApk(classesJar, tmpl, apk, log)
        }

        // 4. 签名
        if (injected != null && File(injected).exists()) {
            signApk(injected, log)
        }

        log += "═══ 完成 ═══"
        return PackResult(true, injected, log)
    }

    // ─── 检测 ───
    data class ProjectInfo(val lang: String, val files: List<File>)

    private fun detectProject(dir: File): Pair<String, List<File>> {
        val kt = mutableListOf<File>()
        val java = mutableListOf<File>()
        val js = mutableListOf<File>()
        var hasPkgJson = false

        fun walk(d: File) {
            for (f in d.listFiles() ?: emptyArray()) {
                if (f.isDirectory && f.name !in setOf(".git", ".gradle", "build", "node_modules")) walk(f)
                else when {
                    f.name == "package.json" -> hasPkgJson = true
                    f.extension == "kt" -> kt += f
                    f.extension == "java" -> java += f
                    f.extension == "js" || f.extension == "mjs" -> js += f
                }
            }
        }
        walk(dir)

        return when {
            kt.isNotEmpty() -> "kotlin" to kt
            java.isNotEmpty() -> "java" to java
            hasPkgJson || js.isNotEmpty() -> "node" to js
            else -> "unknown" to emptyList()
        }
    }

    // ─── Kotlin 编译 ───
    private fun compileKotlin(files: List<File>, out: File, log: MutableList<String>): Boolean {
        log += "🔨 kotlinc ${files.size}个.kt文件..."
        val cmd = mutableListOf("kotlinc")
        files.forEach { cmd += it.absolutePath }
        cmd += listOf("-include-runtime", "-d", out.absolutePath)
        return runCmd(cmd, log, out)
    }

    // ─── Java 编译 ───
    private fun compileJava(files: List<File>, out: File, log: MutableList<String>): Boolean {
        log += "🔨 javac ${files.size}个.java文件..."
        val cmd = mutableListOf("javac")
        files.forEach { cmd += it.absolutePath }
        // javac输出到临时目录再打包
        val tmp = File(out.parentFile, "classes")
        tmp.mkdirs()
        cmd += listOf("-d", tmp.absolutePath)
        val ok = runCmdRaw(cmd, log)
        if (!ok) return false
        // 打包成jar
        return runCmd(listOf("jar", "cf", out.absolutePath, "-C", tmp.absolutePath, "."), log, out)
    }

    // ─── Node 打包 ───
    private fun packNode(files: List<File>, outDir: File, log: MutableList<String>): Boolean {
        log += "📦 Node.js项目打包..."
        // 收集所有文件打成一个node-payload.zip，附在APK里
        val payload = File(outDir, "node-payload.zip")
        ZipOutputStream(payload.outputStream()).use { zos ->
            for (f in files) {
                zos.putNextEntry(ZipEntry(f.name))
                f.inputStream().use { it.copyTo(zos) }
                zos.closeEntry()
            }
        }
        log += "✅ Node payload: ${payload.absolutePath} (${payload.length()} bytes)"
        return true
    }

    // ─── APK 注入 ───
    private fun injectIntoApk(classesJar: File, templatePath: String, outApk: File, log: MutableList<String>): String? {
        val tmpl = File(templatePath)
        if (!tmpl.exists()) {
            log += "⚠️ 模板APK不存在: $templatePath → 输出classes.jar"
            return classesJar.absolutePath
        }
        log += "📦 注入模板APK..."

        val tmpFile = File(outApk.parentFile, outApk.name + ".tmp")
        ZipFile(tmpl).use { zin ->
            ZipOutputStream(tmpFile.outputStream()).use { zout ->
                zin.entries().asIterator().forEach { entry ->
                    if (entry.name == "classes.dex") return@forEach // 跳过旧dex
                    zout.putNextEntry(ZipEntry(entry.name))
                    zin.getInputStream(entry).use { it.copyTo(zout) }
                    zout.closeEntry()
                }
                // 注入新classes.jar
                zout.putNextEntry(ZipEntry("classes.dex"))
                classesJar.inputStream().use { it.copyTo(zout) }
                zout.closeEntry()
            }
        }
        tmpFile.renameTo(outApk)
        log += "✅ APK: ${outApk.absolutePath} (${outApk.length()} bytes)"
        return outApk.absolutePath
    }

    // ─── 签名 ───
    private fun signApk(apkPath: String, log: MutableList<String>) {
        val ks = File(System.getProperty("user.home"), ".android/debug.keystore")
        if (!ks.exists()) {
            log += "🔑 生成debug keystore..."
            runCmdRaw(listOf(
                "keytool", "-genkey", "-v", "-keystore", ks.absolutePath,
                "-storepass", "android", "-alias", "androiddebugkey",
                "-keypass", "android", "-keyalg", "RSA", "-keysize", "2048",
                "-validity", "10000", "-dname", "CN=Debug,O=Test,C=US"
            ), log)
        }
        log += "✍️ 签名..."
        val ok = runCmdRaw(listOf(
            "jarsigner", "-sigalg", "SHA1withRSA", "-digestalg", "SHA1",
            "-keystore", ks.absolutePath, "-storepass", "android",
            apkPath, "androiddebugkey"
        ), log)
        if (ok) log += "✅ 签名完成"
        else log += "⚠️ 签名失败——可手动签名"
    }

    // ─── 工具 ───
    private fun findTemplate(): String {
        val candidates = listOf(
            "/sdcard/Download/Operit/qitong-ai-gateway/qitong-ai-gateway-main/app/build/outputs/apk/debug/app-debug.apk",
            "/sdcard/Download/app-debug.apk"
        )
        return candidates.firstOrNull { File(it).exists() } ?: ""
    }

    private fun runCmd(cmd: List<String>, log: MutableList<String>, expectOut: File): Boolean {
        val ok = runCmdRaw(cmd, log)
        if (ok && expectOut.exists()) {
            log += "✅ ${expectOut.name} (${expectOut.length()} bytes)"
        }
        return ok
    }

    private fun runCmdRaw(cmd: List<String>, log: MutableList<String>): Boolean {
        try {
            val proc = ProcessBuilder(cmd)
                .redirectErrorStream(true)
                .start()
            val out = proc.inputStream.bufferedReader().readText()
            val code = proc.waitFor()
            if (code != 0 && out.isNotBlank()) {
                log += "⚠️ ${out.takeLast(300)}"
            }
            return code == 0
        } catch (e: Exception) {
            log += "✖ 执行失败: ${e.message}"
            return false
        }
    }
}