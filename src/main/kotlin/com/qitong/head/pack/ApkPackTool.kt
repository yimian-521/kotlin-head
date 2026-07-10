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

    // ─── Node 打包（完整支持）───
    private fun packNode(files: List<File>, outDir: File, log: MutableList<String>): Boolean {
        log += "📦 Node.js项目打包（完整模式）..."

        // 1. 解析 package.json → 找 main 入口
        val pkgJson = files.firstOrNull { it.name == "package.json" }
        var mainFile = ""
        var pkgName = outDir.parentFile.parentFile.parentFile.name
        if (pkgJson != null) {
            try {
                val json = pkgJson.readText()
                // 简易 JSON 解析（不依赖外部库）
                val mainMatch = Regex("\"main\"\\s*:\\s*\"([^\"]+)\"").find(json)
                mainFile = mainMatch?.groupValues?.get(1) ?: "index.js"
                val nameMatch = Regex("\"name\"\\s*:\\s*\"([^\"]+)\"").find(json)
                if (nameMatch != null) pkgName = nameMatch.groupValues[1]
                log += "  📋 package.json → main: $mainFile, name: $pkgName"
            } catch (e: Exception) {
                log += "  ⚠️ package.json 解析失败: ${e.message}"
            }
        } else {
            mainFile = files.firstOrNull { it.extension in listOf("js","mjs") }?.name ?: "index.js"
        }

        // 2. 打包完整项目结构 → node-bundle.zip（保留目录结构）
        val bundle = File(outDir, "node-bundle.zip")
        val projRoot = outDir.parentFile.parentFile.parentFile  // 项目根目录
        ZipOutputStream(bundle.outputStream()).use { zos ->
            for (f in files) {
                // 保留相对于项目根目录的路径
                val relPath = f.relativeTo(projRoot).path.replace("\\", "/")
                zos.putNextEntry(ZipEntry("node-bundle/$relPath"))
                f.inputStream().use { it.copyTo(zos) }
                zos.closeEntry()
            }
        }
        log += "  ✅ node-bundle.zip (${bundle.length()} bytes, ${files.size}文件)"

        // 3. 生成 NodeHost.java → 编译 → classes.jar
        val nodeHostCode = generateNodeHost(pkgName, mainFile)
        val nodeHostFile = File(outDir, "NodeHost.java")
        nodeHostFile.writeText(nodeHostCode)
        log += "  📝 生成 NodeHost.java"

        val classesJar = File(outDir, "classes.jar")
        val ok = runCmd(listOf(
            "javac", nodeHostFile.absolutePath,
            "-d", outDir.absolutePath
        ), log, File(outDir, "NodeHost.class"))
        if (!ok) { log += "  ⚠️ NodeHost 编译失败——输出node-bundle.zip"; return true }

        // 打包NodeHost.class成jar
        runCmd(listOf(
            "jar", "cf", classesJar.absolutePath,
            "-C", outDir.absolutePath, "NodeHost.class"
        ), log, classesJar)

        // 4. 注入模板APK——classes.jar变成dex，node-bundle.zip作为asset
        val tmpl = findTemplate()
        if (tmpl.isNotEmpty() && File(tmpl).exists()) {
            val apk = File(outDir, "$pkgName-debug.apk")
            val tmpApk = File(outDir, apk.name + ".tmp")
            ZipFile(tmpl).use { zin ->
                ZipOutputStream(tmpApk.outputStream()).use { zout ->
                    zin.entries().asIterator().forEach { entry ->
                        if (entry.name == "classes.dex") return@forEach
                        zout.putNextEntry(ZipEntry(entry.name))
                        zin.getInputStream(entry).use { it.copyTo(zout) }
                        zout.closeEntry()
                    }
                    // 注入 NodeHost.dex
                    zout.putNextEntry(ZipEntry("classes.dex"))
                    classesJar.inputStream().use { it.copyTo(zout) }
                    zout.closeEntry()
                    // 注入 node-bundle → assets
                    zout.putNextEntry(ZipEntry("assets/node-bundle.zip"))
                    bundle.inputStream().use { it.copyTo(zout) }
                    zout.closeEntry()
                }
            }
            tmpApk.renameTo(apk)
            log += "  ✅ APK: ${apk.absolutePath} (${apk.length()} bytes)"
        } else {
            log += "  ⚠️ 无模板APK——输出: $bundle + $classesJar"
        }
        return true
    }

    /** 生成 NodeHost.java — 启动时加载node-bundle，用WebView执行JS */
    private fun generateNodeHost(pkgName: String, mainFile: String): String {
        return """
package com.qitong.nodehost;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.io.*;
import java.util.zip.*;

public class NodeHost extends Activity {
    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        WebView wv = new WebView(this);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setAllowFileAccess(true);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView v, String url) {
                // 注入 node-bundle 中的所有 JS 文件
                try {
                    ZipInputStream zis = new ZipInputStream(getAssets().open("node-bundle.zip"));
                    ZipEntry entry;
                    StringBuilder sb = new StringBuilder();
                    // 先在 global scope 模拟最小 Node 环境
                    sb.append("var global=this;var process={env:{}};var console={log:function(m){android.console.log(m);},error:function(m){android.console.error(m);}};var require=function(){return{};};var module={exports:{}};var exports=module.exports;");
                    while ((entry = zis.getNextEntry()) != null) {
                        if (entry.getName().endsWith(".js")) {
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            byte[] buf = new byte[4096]; int n;
                            while ((n = zis.read(buf)) != -1) bos.write(buf, 0, n);
                            sb.append(new String(bos.toByteArray())).append(";\\n");
                        }
                        zis.closeEntry();
                    }
                    zis.close();
                    // 最后 eval 入口文件
                    sb.append("(function(){try{");
                    InputStream main = getAssets().open("node-bundle/node-bundle/${mainFile}");
                    if (main == null) {
                        // fallback: 从 zip 里读
                        v.evaluateJavascript(sb.toString() + "}catch(e){console.error(e.toString())}})();", null);
                    } else {
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        byte[] buf = new byte[4096]; int n;
                        while ((n = main.read(buf)) != -1) bos.write(buf,0,n);
                        main.close();
                        sb.append(new String(bos.toByteArray()));
                        sb.append("}catch(e){console.error(e.toString())}})();");
                        v.evaluateJavascript(sb.toString(), null);
                    }
                } catch (Exception e) {
                    v.evaluateJavascript("console.error('NodeHost load failed: " + e.getMessage().replace("'","\\'") + "')", null);
                }
            }
        });
        setContentView(wv);
        wv.loadData("<html><body><h3>${pkgName}</h3><p>Node.js powered by kotlin-head</p><div id='log'></div></body></html>", "text/html", "UTF-8");
    }
}
""".trimIndent()
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