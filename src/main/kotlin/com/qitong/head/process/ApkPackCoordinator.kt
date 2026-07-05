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

    // ── v0.11.8: 军师修复模式 ──

    /** 军师修复选项——七档渐变，改什么×改哪里 */
    enum class FixMode(val label: String) {
        OVERWRITE("全面升级（覆盖）"),
        COPY("全面升级（复制）"),
        OPTIMIZE("表面优化（覆盖）"),
        OPTIMIZE_COPY("表面优化（复制）"),
        UPGRADE("核心升级（覆盖）"),
        UPGRADE_COPY("核心升级（复制）"),
        CANCEL("拒绝——什么也不做")
    }

    /** 判断是否需要复制（COPY / OPTIMIZE_COPY / UPGRADE_COPY） */
    fun FixMode.needsCopy(): Boolean = this == FixMode.COPY || this == FixMode.OPTIMIZE_COPY || this == FixMode.UPGRADE_COPY

    /** 判断是否做优化（含全改和优化类） */
    fun FixMode.doOptimize(): Boolean = this == FixMode.OPTIMIZE || this == FixMode.OPTIMIZE_COPY || this == FixMode.OVERWRITE || this == FixMode.COPY

    /** 判断是否做升级（含全改和升级类） */
    fun FixMode.doUpgrade(): Boolean = this == FixMode.UPGRADE || this == FixMode.UPGRADE_COPY || this == FixMode.OVERWRITE || this == FixMode.COPY

    data class FixResult(
        val mode: FixMode,
        val targetPath: String,        // 实际修改的目标路径
        val originalPath: String,      // 原始路径
        val fixesApplied: List<String>, // 应用了哪些修复
        val errors: List<String> = emptyList()
    )

    /**
     * 修复预览——分两步：
     * 第一步：概览（各变更前后预计改动什么，具体改几处）
     * 第二步：详细（具体位置、内容、军师评价）
     */
    data class FixPreview(
        val mode: FixMode,
        val targetPath: String,
        val originalPath: String,
        /** 概览：每个改动项简述 */
        val overview: List<PreviewItem> = emptyList(),
        /** 详细：军师点评每处改动 */
        val details: Map<String, List<DetailItem>> = emptyMap()
    )

    data class PreviewItem(
        val category: String,      // 类别：Manifest/格式/目录/权限
        val before: String,        // 改前
        val after: String,         // 改后
        val changeCount: Int       // 改动数量
    )

    data class DetailItem(
        val file: String,          // 涉及文件
        val line: Int,             // 大约行号（-1=新增）
        val change: String,        // 具体改动内容
        val comment: String        // 军师评价
    )

    /** 生成修复后的项目路径 */
    fun fixTargetPath(projectDir: String, mode: FixMode): String = if (mode.needsCopy()) {
        val base = projectDir.trimEnd('/')
        val parent = base.substringBeforeLast("/")
        val name = base.substringAfterLast("/")
        "$parent/${name}-fixed"
    } else {
        projectDir
    }

    /**
     * 预览修复——不实际修改，只出报告。
     * 第一步：概览（改什么、改几处）
     * 第二步：详情（具体位置、军师评价）
     */
    fun previewFixes(projectDir: String, mode: FixMode): FixPreview {
        if (mode == FixMode.CANCEL) {
            return FixPreview(mode, projectDir, projectDir,
                overview = listOf(PreviewItem("无", "-", "-", 0)),
                details = mapOf("info" to listOf(DetailItem("N/A", 0, "用户拒绝，无改动", "军师：尊重选择")))
            )
        }

        val target = fixTargetPath(projectDir, mode)
        val overview = mutableListOf<PreviewItem>()
        val details = mutableMapOf<String, MutableList<DetailItem>>()

        fun addDetail(cat: String, file: String, line: Int, change: String, comment: String) {
            details.getOrPut(cat) { mutableListOf() }
                .add(DetailItem(file, line, change, comment))
        }

        val workDir = File(projectDir)  // 预览不复制，只看原项目

        // ── UPGRADE 预览 ──
        if (mode.doUpgrade()) {
            val manifestFile = findFile(workDir, "AndroidManifest.xml")
            val manifestName = manifestFile?.name ?: "AndroidManifest.xml"
            var manifestChanges = 0

            if (manifestFile != null) {
                val mc = manifestFile.readText()

                if ("usesCleartextTraffic" !in mc) {
                    manifestChanges++
                    val line = mc.lines().indexOfFirst { "<application" in it }.let { if (it >= 0) it + 1 else -1 }
                    addDetail("Manifest", manifestName, line,
                        "在 <application> 加 android:usesCleartextTraffic=\"true\"",
                        "军师：网关需代理HTTP明文流量，建议加上")
                }
                if ("FOREGROUND_SERVICE" !in mc) {
                    manifestChanges++
                    addDetail("Manifest", manifestName, -1,
                        "新增 <uses-permission android:name=\"FOREGROUND_SERVICE\"/>",
                        "军师：有前台服务必须声明权限，否则崩溃")
                }
            }

            if (manifestChanges > 0) {
                overview.add(PreviewItem("Manifest", "无补充", "补全$manifestChanges处", manifestChanges))
            }

            val outDir = File("$projectDir/output")
            if (!outDir.exists()) {
                overview.add(PreviewItem("目录", "无output/", "创建 output/", 1))
                addDetail("目录", "output/", -1, "mkdir output/", "军师：打包产物需要输出目录")
            }

            val gradlew = File("$projectDir/gradlew")
            if (gradlew.exists() && !gradlew.canExecute()) {
                overview.add(PreviewItem("权限", "gradlew不可执行", "chmod +x gradlew", 1))
                addDetail("权限", "gradlew", -1, "chmod +x gradlew", "军师：确保Gradle Wrapper可运行")
            }
        }

        // ── OPTIMIZE 预览 ──
        if (mode.doOptimize()) {
            val srcDir = File(workDir, "app/src/main/java")
            var cleanedFiles = 0
            if (srcDir.exists()) {
                srcDir.walkTopDown()
                    .filter { it.isFile && it.extension == "kt" }
                    .forEach { file ->
                        val lines = file.readLines()
                        var consecutiveBlanks = 0
                        var hasIssue = false
                        for (l in lines) {
                            if (l.isBlank()) { consecutiveBlanks++ } else { consecutiveBlanks = 0 }
                            if (consecutiveBlanks > 1) { hasIssue = true; break }
                        }
                        if (hasIssue) cleanedFiles++
                    }
            }
            if (cleanedFiles > 0) {
                overview.add(PreviewItem("格式", "有连续空行", "去连续空行", cleanedFiles))
                addDetail("格式", "$cleanedFiles个.kt文件", -1,
                    "去除连续空行和末尾空行",
                    "军师：提升可读性，不影响逻辑")
            }
        }

        // 复制预览
        if (mode.needsCopy()) {
            overview.add(0, PreviewItem("复制", projectDir, target, 1))
            addDetail("复制", projectDir, -1, "复制整个项目 → $target", "军师：原项目不动，安全")
        }

        return FixPreview(mode, target, projectDir, overview, details)
    }

    /** 格式化预览——第一步概览 */
    fun formatPreviewOverview(preview: FixPreview): String {
        val sb = StringBuilder()
        sb.appendLine("════ 军师修复预览 ════")
        sb.appendLine("模式: ${preview.mode.label}")
        sb.appendLine("原路径: ${preview.originalPath}")
        if (preview.originalPath != preview.targetPath) {
            sb.appendLine("目标: ${preview.targetPath}")
        }
        sb.appendLine()
        sb.appendLine("── 变更概览 ──")
        if (preview.overview.isEmpty()) {
            sb.appendLine("  ✓ 无需改动")
        } else {
            for ((i, item) in preview.overview.withIndex()) {
                sb.appendLine("  ${i+1}. [${item.category}] ${item.before} → ${item.after} (${item.changeCount}处)")
            }
        }
        sb.appendLine()
        sb.appendLine("  [D] 查看详细  [Q] 返回")
        sb.append("══════════════════════")
        return sb.toString()
    }

    /** 格式化预览——第二步详细 */
    fun formatPreviewDetail(preview: FixPreview): String {
        val sb = StringBuilder()
        sb.appendLine("════ 军师详细点评 ════")
        for ((cat, items) in preview.details) {
            sb.appendLine()
            sb.appendLine("── $cat ──")
            for (item in items) {
                sb.appendLine("  📄 ${item.file}")
                if (item.line > 0) sb.append("  第${item.line}行: ")
                sb.appendLine(item.change)
                sb.appendLine("     💬 ${item.comment}")
            }
        }
        sb.appendLine()
        sb.appendLine("  [Q] 返回概览")
        sb.append("══════════════════════")
        return sb.toString()
    }

    /** 一键修复——按模式铺不同深度的地基 */
    fun applyFixes(projectDir: String, mode: FixMode): FixResult {
        if (mode == FixMode.CANCEL) {
            return FixResult(mode, projectDir, projectDir, emptyList(), listOf("用户拒绝修复"))
        }

        val target = fixTargetPath(projectDir, mode)
        val fixes = mutableListOf<String>()
        val errors = mutableListOf<String>()

        // 需要复制的模式
        if (mode.needsCopy()) {
            try {
                File(projectDir).copyRecursively(File(target), overwrite = true)
                fixes.add("已复制: $projectDir → $target")
            } catch (e: Exception) {
                errors.add("复制失败: ${e.message}")
                return FixResult(mode, target, projectDir, fixes, errors)
            }
        }

        val workDir = File(target)

        // ── UPGRADE：权限/配置/结构 ──
        if (mode.doUpgrade()) {
            val manifestFile = findFile(workDir, "AndroidManifest.xml")
            if (manifestFile != null) {
                var mc = manifestFile.readText()
                var modified = false
                if ("usesCleartextTraffic" !in mc) {
                    mc = mc.replace("<application", "<application android:usesCleartextTraffic=\"true\"")
                    modified = true
                    fixes.add("Manifest: +usesCleartextTraffic")
                }
                if ("FOREGROUND_SERVICE" !in mc) {
                    val permLine = "    <uses-permission android:name=\"android.permission.FOREGROUND_SERVICE\" />\n"
                    mc = mc.replace("<application", "$permLine    <application")
                    modified = true
                    fixes.add("Manifest: +FOREGROUND_SERVICE 权限")
                }
                if (modified) manifestFile.writeText(mc)
            }

            val outDir = File("$target/output")
            if (!outDir.exists()) {
                outDir.mkdirs()
                fixes.add("创建输出目录: output/")
            }

            val gradlew = File("$target/gradlew")
            if (gradlew.exists() && !gradlew.canExecute()) {
                gradlew.setExecutable(true)
                fixes.add("gradlew → 已授权可执行")
            }
        }

        // ── OPTIMIZE：注释/格式/import（不动逻辑） ──
        if (mode.doOptimize()) {
            val srcDir = File(workDir, "app/src/main/java")
            if (srcDir.exists()) {
                var totalCleaned = 0
                srcDir.walkTopDown()
                    .filter { it.isFile && it.extension == "kt" }
                    .forEach { file ->
                        val lines = file.readLines()
                        val cleaned = mutableListOf<String>()
                        var prevBlank = false
                        for (line in lines) {
                            val trimmed = line.trim()
                            // 去连续空行
                            if (trimmed.isEmpty()) {
                                if (!prevBlank) cleaned.add("")
                                prevBlank = true
                            } else {
                                prevBlank = false
                                cleaned.add(line)  // 保留原始缩进
                            }
                        }
                        // 去末尾空行
                        while (cleaned.isNotEmpty() && cleaned.last().isBlank()) cleaned.removeAt(cleaned.lastIndex)
                        if (cleaned.size != lines.size) {
                            file.writeText(cleaned.joinToString("\n") + "\n")
                            totalCleaned++
                        }
                    }
                if (totalCleaned > 0) fixes.add("格式优化: $totalCleaned 个文件去连续空行")
            }
        }

        return FixResult(mode, target, projectDir, fixes, errors)
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