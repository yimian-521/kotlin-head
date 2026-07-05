package com.javahead

/**
 * java-head —— 独立 Java 诊断器
 *
 * 不做编译器。只做诊断——调 javac 编译 → 读输出 → 翻译成结构化报告。
 *
 * 两种运行方式：
 * 1. 独立 CLI：java -jar java-head.jar /path/to/Project.java
 * 2. 挂载进 kotlin-head：通过 JavaChannel 接口联通进程树
 *
 * 不依赖 kotlin-head 任何类。完全自包含。
 */

/**
 * 诊断报告数据模型——自包含，不依赖 kotlin-head。
 */
data class JavaFileReport(
    val filePath: String,
    val fileName: String,
    val errors: List<DiagnosisEntry> = emptyList(),
    val warnings: List<DiagnosisEntry> = emptyList(),
    val suggestions: List<String> = emptyList(),
    val stats: QuickStats = QuickStats(),
    val passed: Boolean = true
)

data class DiagnosisEntry(
    val line: Int,
    val column: Int,
    val message: String,
    val severity: String = "error"  // "error" | "warning"
)

data class QuickStats(
    val totalLines: Int = 0,
    val commentLines: Int = 0,
    val importCount: Int = 0,
    val unusedImports: List<String> = emptyList()
)
