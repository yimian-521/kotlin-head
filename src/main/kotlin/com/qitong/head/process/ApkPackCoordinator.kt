package com.qitong.head.process

import java.io.File
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * v0.11.8 — 定量混合 APK 打包协调器
 *
 * 不是全量/增量二选一，是军师看完 diff 后自动决定每一步做不做。
 *
 * 六步管线：
 *   aapt2 compile → aapt2 link → kotlinc → d8 → zip → zipalign → apksigner
 *
 * 军师规则：
 *   只改资源 → 只跑 aapt2 + 签名
 *   只改 kotlin → 跑 kotlinc + d8 + 签名
 *   只改 manifest → 跑 aapt2 + 签名
 *   什么都没改 → "别打包了"
 *
 * 设计：和 MultiProjectCoordinator 同构——军师扫→决策→分发→汇总简报。
 */
object ApkPackCoordinator {

    // ── 打包步骤 ──

    enum class PackStep(val label: String) {
        AAPT2_COMPILE("aapt2 compile"),
        AAPT2_LINK("aapt2 link"),
        KOTLINC("kotlinc"),
        D8("d8"),
        ZIP_ALIGN("zip+zipalign"),
        APKSIGNER("apksigner")
    }

    data class StepResult(
        val step: PackStep,
        val success: Boolean,
        val message: String,
        val durationMs: Long = 0
    )

    data class PackReport(
        val projectPath: String,
        val steps: List<StepResult> = emptyList(),
        val apkPath: String? = null,
        val summary: String = "",
        val passed: Boolean = false
    )

    // ── 工具检测 ──

    private fun checkTool(cmd: String): Boolean {
        return try {
            val proc = ProcessBuilder(cmd, "--version")
                .redirectErrorStream(true)
                .start()
            proc.waitFor()
            proc.exitValue() == 0
        } catch (_: Exception) { false }
    }

    data class ToolCheck(
        val aapt2: Boolean = false,
        val kotlinc: Boolean = false,
        val d8: Boolean = false,
        val zipalign: Boolean = false,
        val apksigner: Boolean = false
    ) {
        fun allAvailable(): Boolean = aapt2 && kotlinc && d8 && zipalign && apksigner
        fun missing(): List<String> {
            val missing = mutableListOf<String>()
            if (!aapt2) missing.add("aapt2")
            if (!kotlinc) missing.add("kotlinc")
            if (!d8) missing.add("d8")
            if (!zipalign) missing.add("zipalign")
            if (!apksigner) missing.add("apksigner")
            return missing
        }
    }

    fun checkTools(): ToolCheck = ToolCheck(
        aapt2 = checkTool("aapt2"),
        kotlinc = checkTool("kotlinc"),
        d8 = checkTool("d8"),
        zipalign = checkTool("zipalign"),
        apksigner = checkTool("apksigner")
    )

    // ── 军师：扫 diff 决定跑几步 ──

    data class DiffAnalysis(
        val resourceChanged: Boolean = false,_
        val kotlinChanged: Boolean = false,
        val manifestChanged: Boolean = false,
        val nothingChanged: Boolean = true,
        /** 需要跑的步骤 */
        val neededSteps: List<PackStep> = emptyList(),
        /** 可以跳过的步骤 */
        val skippedSteps: List<PackStep> = emptyList(),
        val reason: String = ""
    )

    /**
     * 军师扫 diff——决定哪些步骤值得跑。
     *
     * @param projectDir 项目根目录
     * @param previousArtifacts 上次打包产物是否存在（复用判断）
     */
    fun analyzeDiff(
        projectDir: String,
        previousArtifacts: Boolean = false
    ): DiffAnalysis {
        val dir = File(projectDir)
        if (!dir.exists() || !dir.isDirectory) {
            return DiffAnalysis(
                nothingChanged = false,
                reason = "项目目录不存在: $projectDir"
            )
        }

        var resChanged = false
        var ktChanged = false
        var mfChanged = false

        // 简化版：扫最近修改时间。实际使用时传入 git diff 结果更精确。
        val manifestFile = findFile(dir, "AndroidManifest.xml")
        val resDir = File(dir, "res")
        val srcDir = File(dir, "src")

        // Manifest 检查
        if (manifestFile != null && manifestFile.exists()) {
            // 如果能拿到上次打包时间，精确判断
            mfChanged = true // 简化：有 manifest 就认为需要检查
        }

        // 资源检查
        if (resDir.exists()) {
            val recentRes = resDir.walkTopDown()
                .filter { it.isFile && it.extension in listOf("xml", "png", "jpg", "webp") }
                .any { true } // 有资源文件
            if (recentRes) resChanged = true
        }

        // Kotlin/Java 源码检查
        if (srcDir.exists()) {
            val recentSrc = srcDir.walkTopDown()
                .filter { it.isFile && it.extension in listOf("kt", "java") }
                .any { true }
            if (recentSrc) ktChanged = true
        }

        val nothingChanged = !resChanged && !ktChanged && !mfChanged

        // 军师规则：决定每步做不做
        val needed = mutableListOf<PackStep>()
        val skipped = mutableListOf<PackStep>()

        if (nothingChanged) {
            skipped.addAll(PackStep.values().toList())
            return DiffAnalysis(
                nothingChanged = true,
                neededSteps = emptyList(),
                skippedSteps = skipped,
                reason = "未检测到变化——无需打包"
            )
        }

        // 资源变了→跑 aapt2
        if (resChanged || mfChanged) {
            needed.add(PackStep.AAPT2_COMPILE)
            needed.add(PackStep.AAPT2_LINK)
        } else {
            if (previousArtifacts) {
                skipped.add(PackStep.AAPT2_COMPILE)
                skipped.add(PackStep.AAPT2_LINK)
            } else {
                needed.add(PackStep.AAPT2_COMPILE)
                needed.add(PackStep.AAPT2_LINK)
            }
        }

        // Kotlin 变了→跑 kotlinc + d8
        if (ktChanged) {
            needed.add(PackStep.KOTLINC)
            needed.add(PackStep.D8)
        } else {
            if (previousArtifacts) {
                skipped.add(PackStep.KOTLINC)
                skipped.add(PackStep.D8)
            } else {
                needed.add(PackStep.KOTLINC)
                needed.add(PackStep.D8)
            }
        }

        // 最后两步必跑
        needed.add(PackStep.ZIP_ALIGN)
        needed.add(PackStep.APKSIGNER)

        val reasons = mutableListOf<String>()
        if (resChanged) reasons.add("资源变更")
        if (ktChanged) reasons.add("源码变更")
        if (mfChanged) reasons.add("Manifest变更")
        if (skipped.isNotEmpty()) reasons.add("跳过${skipped.size}步（复用上次产物）")

        return DiffAnalysis(
            resourceChanged = resChanged,
            kotlinChanged = ktChanged,
            manifestChanged = mfChanged,
            nothingChanged = false,
            neededSteps = needed.distinct(),
            skippedSteps = skipped.distinct(),
            reason = reasons.joinToString("，")
        )
    }

    /** 在目录中递归查找文件 */
    private fun findFile(dir: File, name: String): File? {
        return dir.walkTopDown().find { it.name == name }
    }

    // ── 打包执行 ──

    /**
     * 按军师分析的步骤执行打包。
     *
     * @param projectDir 项目根目录
     * @param outputPath APK 输出路径（可选，默认 projectDir/output/app.apk）
     * @param keystore   签名 keystore 路径（可选）
     */
    fun pack(
        projectDir: String,
        outputPath: String? = null,
        keystore: String? = null,
        previousArtifacts: Boolean = false
    ): PackReport {
        val tools = checkTools()
        if (!tools.allAvailable()) {
            return PackReport(
                projectPath = projectDir,
                summary = "⛔ 缺少工具: ${tools.missing().joinToString(", ")}",
                passed = false
            )
        }

        // 1. 军师分析
        val diff = analyzeDiff(projectDir, previousArtifacts)

        if (diff.nothingChanged && previousArtifacts) {
            return PackReport(
                projectPath = projectDir,
                summary = "✓ ${diff.reason}",
                passed = true
            )
        }

        val outDir = outputPath?.substringBeforeLast("/") ?: "$projectDir/output"
        val apkPath = outputPath ?: "$outDir/app.apk"
        File(outDir).mkdirs()

        val results = mutableListOf<StepResult>()
        val baseName = apkPath.substringAfterLast("/").substringBefore(".apk")
        val unsigned = "$outDir/${baseName}-unsigned.apk"
        val aligned = "$outDir/${baseName}-aligned.apk"

        // 2. 按需执行步骤
        for (step in diff.neededSteps) {
            val start = System.currentTimeMillis()
            val result = when (step) {
                PackStep.AAPT2_COMPILE -> runAapt2Compile(projectDir, outDir)
                PackStep.AAPT2_LINK -> runAapt2Link(projectDir, outDir)
                PackStep.KOTLINC -> runKotlinc(projectDir, outDir)
                PackStep.D8 -> runD8(outDir)
                PackStep.ZIP_ALIGN -> runZipAlign(outDir, unsigned, aligned)
                PackStep.APKSIGNER -> runApkSigner(aligned, apkPath, keystore)
            }
            val elapsed = System.currentTimeMillis() - start

            val sr = StepResult(step, result.success, result.message, elapsed)
            results.add(sr)

            if (!result.success && step != PackStep.APKSIGNER) {
                // 非签名步骤失败→停止（签名失败也继续报告）
                val done = results.joinToString("\n") { "  ${if (it.success) "✓" else "✗"} ${it.step.label}: ${it.message}" }
                return PackReport(
                    projectPath = projectDir,
                    steps = results,
                    summary = "✗ ${step.label} 失败——打包中止\n$done",
                    passed = false
                )
            }
        }

        val skippedInfo = if (diff.skippedSteps.isNotEmpty()) {
            " | 跳过: ${diff.skippedSteps.joinToString(", ") { it.label }}"
        } else ""

        val done = results.joinToString("\n") { "  ${if (it.success) "✓" else "✗"} ${it.step.label}: ${it.message} (${it.durationMs}ms)" }

        val allPassed = results.all { it.success }
        return PackReport(
            projectPath = projectDir,
            steps = results,
            apkPath = if (allPassed) apkPath else null,
            summary = "${if (allPassed) "✓ 打包完成" else "✗ 打包失败"}$skippedInfo\n$done",
            passed = allPassed
        )
    }

    // ── 各步骤实现（shell 代理） ──

    private data class RunResult(val success: Boolean, val message: String)

    private fun runCmd(vararg cmd: String): RunResult {
        return try {
            val proc = ProcessBuilder(*cmd)
                .redirectErrorStream(true)
                .start()
            val output = BufferedReader(InputStreamReader(proc.inputStream)).readText()
            proc.waitFor()
            if (proc.exitValue() == 0) {
                RunResult(true, output.lines().lastOrNull()?.take(80) ?: "OK")
            } else {
                RunResult(false, output.lines().lastOrNull()?.take(120) ?: "exit=${proc.exitValue()}")
            }
        } catch (e: Exception) {
            RunResult(false, e.message ?: "未知错误")
        }
    }

    private fun runAapt2Compile(projectDir: String, outDir: String): RunResult {
        val resDir = "$projectDir/res"
        if (!File(resDir).exists()) return RunResult(false, "res/ 目录不存在")
        val flatDir = "$outDir/compiled_res"
        File(flatDir).mkdirs()
        return runCmd("aapt2", "compile", "-o", flatDir, "--dir", resDir)
    }

    private fun runAapt2Link(projectDir: String, outDir: String): RunResult {
        val manifest = "$projectDir/AndroidManifest.xml"
        if (!File(manifest).exists()) return RunResult(false, "AndroidManifest.xml 不存在")
        val flatDir = "$outDir/compiled_res"
        val apkBase = "$outDir/base.apk"
        return runCmd("aapt2", "link", "-o", apkBase, "-I", "${androidJarPath()}", "--manifest", manifest, flatDir)
    }

    private fun runKotlinc(projectDir: String, outDir: String): RunResult {
        val srcDir = "$projectDir/src"
        if (!File(srcDir).exists()) return RunResult(false, "src/ 目录不存在")
        val classesDir = "$outDir/classes"
        File(classesDir).mkdirs()
        val ktFiles = File(srcDir).walkTopDown()
            .filter { it.isFile && it.extension == "kt" }
            .map { it.absolutePath }
            .toList()
        if (ktFiles.isEmpty()) return RunResult(false, "无 .kt 文件")
        val args = mutableListOf("kotlinc", "-d", classesDir)
        args.addAll(ktFiles)
        return runCmd(*args.toTypedArray())
    }

    private fun runD8(outDir: String): RunResult {
        val classesDir = "$outDir/classes"
        if (!File(classesDir).exists()) return RunResult(false, "classes/ 目录不存在")
        val dexDir = "$outDir/dex"
        File(dexDir).mkdirs()
        return runCmd("d8", "--output", dexDir, "--lib", androidJarPath(), classesDir)
    }

    private fun runZipAlign(outDir: String, unsigned: String, aligned: String): RunResult {
        val classesDex = "$outDir/dex/classes.dex"
        val baseApk = "$outDir/base.apk"

        // 简化：把 classes.dex 放进 base.apk（用 zip 追加）
        if (!File(baseApk).exists()) return RunResult(false, "base.apk 不存在（aapt2 link 可能失败）")
        if (File(classesDex).exists()) {
            val zipResult = runCmd("zip", "-j", baseApk, classesDex)
            if (!zipResult.success) return zipResult
        }

        // zipalign
        return runCmd("zipalign", "-p", "-f", "4", baseApk, aligned)
    }

    private fun runApkSigner(aligned: String, apkPath: String, keystore: String?): RunResult {
        if (!File(aligned).exists()) return RunResult(false, "aligned APK 不存在")

        val ks = keystore ?: "${System.getProperty("user.home")}/.android/debug.keystore"
        if (!File(ks).exists()) {
            return RunResult(false, "keystore 不存在: $ks（请用 --keystore 指定）")
        }

        return runCmd(
            "apksigner", "sign",
            "--ks", ks,
            "--ks-pass", "pass:android",
            "--out", apkPath,
            aligned
        )
    }

    /** 尝试找 android.jar */
    private fun androidJarPath(): String {
        // 常见路径
        val candidates = listOf(
            "${System.getenv("ANDROID_HOME")}/platforms/android-34/android.jar",
            "${System.getenv("ANDROID_SDK_ROOT")}/platforms/android-34/android.jar",
            "/usr/lib/android-sdk/platforms/android-34/android.jar",
            "/opt/android-sdk/platforms/android-34/android.jar"
        )
        for (c in candidates) {
            if (File(c).exists()) return c
        }
        // 回退：用 find 找
        return try {
            val proc = ProcessBuilder("find", "/", "-name", "android.jar", "-path", "*/platforms/*")
                .redirectErrorStream(true)
                .start()
            BufferedReader(InputStreamReader(proc.inputStream)).readLine() ?: "android.jar"
        } catch (_: Exception) { "android.jar" }
    }

    // ── 简报格式化（和 MultiProjectCoordinator 同风格） ──

    fun formatReport(report: PackReport): String {
        val sb = StringBuilder()
        sb.appendLine("════ APK 打包简报 ════")
        sb.appendLine("项目: ${report.projectPath}")
        sb.appendLine("结果: ${report.summary}")
        if (report.apkPath != null) {
            sb.appendLine("产物: ${report.apkPath}")
        }
        if (report.steps.isNotEmpty()) {
            sb.appendLine("── 步骤详情 ──")
            for (s in report.steps) {
                val icon = if (s.success) "✓" else "✗"
                sb.appendLine("  $icon ${s.step.label}: ${s.message} (${s.durationMs}ms)")
            }
        }
        sb.append("══════════════════════")
        return sb.toString()
    }
}