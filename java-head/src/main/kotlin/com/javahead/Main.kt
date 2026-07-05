package com.javahead

/**
 * java-head 独立入口。
 *
 * 用法：
 *   java -jar java-head.jar /path/to/Project.java
 *   java -jar java-head.jar --strict /path/to/file1.java /path/to/file2.java
 *   java -jar java-head.jar --check   （仅检查 javac 是否可用）
 */
fun main(args: Array<String>) {
    if (args.isEmpty() || args[0] == "--help" || args[0] == "-h") {
        printUsage()
        return
    }

    if (args[0] == "--check") {
        val ok = JavacRunner.isJavacAvailable()
        println(if (ok) "javac ✓ 可用" else "javac ✗ 不可用，请安装 JDK")
        return
    }

    val strict = args.contains("--strict")
    val paths = args.filter { !it.startsWith("--") }

    if (paths.isEmpty()) {
        println("错误：未指定 .java 文件路径")
        printUsage()
        return
    }

    println("════ java-head 诊断报告 ════")
    println("模式: ${if (strict) "严格（三观+建议）" else "基础（仅错误/警告）"}")
    println("文件数: ${paths.size}\n")

    if (paths.size == 1) {
        val report = JavacRunner.diagnose(paths[0], strict)
        printReport(report)
    } else {
        val reports = JavacRunner.diagnoseBatch(paths, strict)
        var passed = 0
        var failed = 0
        for (r in reports) {
            if (r.passed) passed++ else failed++
            printReport(r)
        }
        println("════ 汇总 ════")
        println("通过: $passed / 失败: $failed / 总计: ${reports.size}")
    }
}

private fun printReport(report: JavaFileReport) {
    val status = if (report.passed) "✓ 通过" else "✗ ${report.errors.size}个错误"
    println("── ${report.fileName} ── $status")

    for (e in report.errors) {
        println("  ✗ 第${e.line}行: ${e.message}")
    }
    for (w in report.warnings) {
        println("  ⚠ 第${w.line}行: ${w.message}")
    }
    if (report.suggestions.isNotEmpty()) {
        println("  💡 建议:")
        report.suggestions.forEach { println("     → $it") }
    }
    val s = report.stats
    if (s.totalLines > 0) {
        val ratio = if (s.totalLines > 0) "%.1f".format(s.commentLines.toFloat() / s.totalLines * 100) else "0"
        println("  📊 ${s.totalLines}行 | 注释率 $ratio% | import ${s.importCount}个" +
                if (s.unusedImports.isNotEmpty()) " (未用: ${s.unusedImports.size})" else "")
    }
    println()
}

private fun printUsage() {
    println("""
java-head —— 独立 Java 诊断器

用法:
  java -jar java-head.jar <文件路径>...  [--strict]  [--check]  [--help]

选项:
  --strict    严格模式：额外统计注释率、未使用 import、生成改进建议
  --check     仅检查 javac 是否可用
  --help      显示此帮助

示例:
  java -jar java-head.jar ./src/Main.java
  java -jar java-head.jar --strict ./src/Main.java ./src/Util.java
    """.trimIndent())
}