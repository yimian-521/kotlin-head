package com.javahead

import java.io.File
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * javac 诊断运行器。
 *
 * 调 javac 编译 → 捕获 stderr → 按正则解析行号/列号/消息 → 翻译成 JavaFileReport。
 * javac 不存在时优雅降级，提示安装 JDK。
 */
object JavacRunner {

    private const val JAVAC_BIN = "javac"

    /** 检查 javac 是否可用 */
    fun isJavacAvailable(): Boolean {
        return try {
            val proc = ProcessBuilder(JAVAC_BIN, "-version")
                .redirectErrorStream(true)
                .start()
            proc.waitFor()
            proc.exitValue() == 0
        } catch (_: Exception) {
            false
        }
    }

    /**
     * 诊断单个 .java 文件。
     *
     * @param filePath .java 文件绝对路径
     * @param strict   是否做额外检查（注释率、import 等）
     */
    fun diagnose(filePath: String, strict: Boolean = false): JavaFileReport {
        val file = File(filePath)
        if (!file.exists()) {
            return JavaFileReport(
                filePath = filePath,
                fileName = file.name,
                errors = listOf(DiagnosisEntry(0, 0, "文件不存在: $filePath")),
                passed = false
            )
        }
        if (!file.extension.equals("java", ignoreCase = true)) {
            return JavaFileReport(
                filePath = filePath,
                fileName = file.name,
                errors = listOf(DiagnosisEntry(0, 0, "不是 .java 文件: ${file.name}")),
                passed = false
            )
        }

        if (!isJavacAvailable()) {
            return JavaFileReport(
                filePath = filePath,
                fileName = file.name,
                errors = listOf(DiagnosisEntry(0, 0, "javac 不可用，请安装 JDK")),
                passed = false
            )
        }

        // 调 javac
        val errors = mutableListOf<DiagnosisEntry>()
        val warnings = mutableListOf<DiagnosisEntry>()
        try {
            val proc = ProcessBuilder(JAVAC_BIN, "-Xlint:all", filePath)
                .redirectErrorStream(false)
                .start()

            val stderr = BufferedReader(InputStreamReader(proc.errorStream))
            for (line in stderr.lines()) {
                val entry = parseJavacLine(line)
                if (entry != null) {
                    when (entry.severity) {
                        "warning" -> warnings.add(entry)
                        else -> errors.add(entry)
                    }
                }
            }
            proc.waitFor()
        } catch (e: Exception) {
            errors.add(DiagnosisEntry(0, 0, "javac 执行异常: ${e.message}"))
        }

        // strict 模式：额外统计
        val stats = if (strict) quickStats(file) else QuickStats()
        val suggestions = if (strict) generateSuggestions(file, errors) else emptyList()

        return JavaFileReport(
            filePath = filePath,
            fileName = file.name,
            errors = errors,
            warnings = warnings,
            suggestions = suggestions,
            stats = stats,
            passed = errors.isEmpty()
        )
    }

    /**
     * 批量诊断。
     */
    fun diagnoseBatch(filePaths: List<String>, strict: Boolean = false): List<JavaFileReport> {
        return filePaths.map { diagnose(it, strict) }
    }

    // ── 内部解析 ──

    /** 解析 javac 错误行格式: File.java:行: error: 消息 */
    private fun parseJavacLine(line: String): DiagnosisEntry? {
        // 格式：path/to/File.java:42: error: some message
        val regex = Regex("""^(.+?\.java):(\d+):\s*(error|warning):\s*(.+)$""")
        val match = regex.find(line.trim()) ?: return null

        return DiagnosisEntry(
            line = match.groupValues[2].toIntOrNull() ?: 0,
            column = 0,
            message = match.groupValues[4],
            severity = match.groupValues[3]
        )
    }

    /** 快速统计：行数、注释率、import */
    private fun quickStats(file: File): QuickStats {
        val lines = file.readLines()
        var commentLines = 0
        var importCount = 0
        val imports = mutableListOf<String>()
        val usedTokens = mutableSetOf<String>()

        for (l in lines) {
            val trimmed = l.trim()
            if (trimmed.startsWith("//") || trimmed.startsWith("/*") || trimmed.startsWith("*")) {
                commentLines++
                continue
            }
            if (trimmed.startsWith("import ")) {
                importCount++
                val pkg = trimmed.removePrefix("import ").removeSuffix(";").trim()
                imports.add(pkg)
                continue
            }
            // 收集使用的类名（简单启发式）
            if (trimmed.contains(".")) {
                // 例如 SomeClass.method() → 提取 SomeClass
                val tokens = Regex("""([A-Z][a-zA-Z0-9]*)\.""")
                    .findAll(trimmed)
                    .map { it.groupValues[1] }
                tokens.forEach { usedTokens.add(it) }
            }
        }

        // 检查未使用的 import
        val unused = imports.filter { imp ->
            val shortName = imp.substringAfterLast(".")
            shortName !in usedTokens && "*" !in imp
        }

        return QuickStats(
            totalLines = lines.size,
            commentLines = commentLines,
            importCount = importCount,
            unusedImports = unused
        )
    }

    /** 按标准生成改进建议 */
    private fun generateSuggestions(file: File, errors: List<DiagnosisEntry>): List<String> {
        val suggestions = mutableListOf<String>()
        val stats = quickStats(file)

        if (stats.totalLines > 0 && stats.commentLines.toFloat() / stats.totalLines < 0.05f) {
            suggestions.add("注释率过低 (${"%.1f".format(stats.commentLines.toFloat() / stats.totalLines * 100)}%)，建议 ≥ 10%")
        }
        if (stats.unusedImports.isNotEmpty()) {
            suggestions.add("未使用的 import: ${stats.unusedImports.joinToString(", ")}")
        }

        // 从 javac 错误中提取建议
        for (e in errors) {
            when {
                e.message.contains("cannot find symbol") ->
                    suggestions.add("缺失 import 或依赖 → 检查类路径")
                e.message.contains("unreported exception") ->
                    suggestions.add("未捕获的受检异常 → 加 try/catch 或 throws")
                e.message.contains("incompatible types") ->
                    suggestions.add("类型不匹配 → 检查变量声明与赋值")
            }
        }
        return suggestions
    }
}