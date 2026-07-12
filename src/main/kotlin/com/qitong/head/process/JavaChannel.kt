package com.qitong.head.process

import com.qitong.head.headstd.HList

/**
 * v0.12.0 — Java 检测通道接口（底层换 HList）
 *
 * 协议层：定义 kotlin-head 与 java-head 之间的诊断通道。
 * 不开 Java 检测时为空壳，不占资源。
 * 挂载 java-head.jar 后实现此接口，被 ProcessCoordinator 统一调度。
 *
 * 设计原则：
 * - 通道不关心实现细节，只收翻译后的简报
 * - java-head 可脱离 kotlin-head 单独使用
 * - 接口签名定下来就不动，两边独立迭代
 */
interface JavaDetectionChannel {
    fun diagnose(filePath: String, strictMode: Boolean = false): JavaDiagnosis?
    fun diagnoseBatch(filePaths: HList<String>, strictMode: Boolean = false): HList<JavaDiagnosis>
    val isAvailable: Boolean

    /** 批量诊断后的汇总统计 */
    fun summarize(results: HList<JavaDiagnosis>): String {
        if (results.isEmpty()) return "无Java文件"
        val passed = results.count { it.passed }
        val totalErrors = results.fold(0) { acc, d -> acc + d.errors.size }
        return "Java检测: $passed/${results.size}通过 | ${totalErrors}个错误"
    }
}

data class JavaDiagnosis(
    val filePath: String,
    val fileName: String,
    val errors: HList<String> = HList(),
    val warnings: HList<String> = HList(),
    val missing: HList<String> = HList(),
    val suggestions: HList<String> = HList(),
    val stats: JavaFileStats? = null,
    val passed: Boolean
)

data class JavaFileStats(
    val lineCount: Int = 0,
    val commentRatio: Float = 0f,
    val complexityScore: Float = 0f,
    val complianceScore: Float = 0f,
    val importCount: Int = 0,
    val unusedImports: HList<String> = HList()
)

object NoJavaDetection : JavaDetectionChannel {
    override fun diagnose(filePath: String, strictMode: Boolean): JavaDiagnosis? = null
    override fun diagnoseBatch(filePaths: HList<String>, strictMode: Boolean): HList<JavaDiagnosis> = HList()
    override val isAvailable: Boolean = false
}
