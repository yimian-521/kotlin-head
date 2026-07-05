package com.qitong.head.process

/**
 * v0.11.8 — Java 检测通道接口
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
    /**
     * 对单个 .java 文件执行诊断。
     *
     * @param filePath   .java 文件绝对路径
     * @param strictMode 是否启用严格模式（三观统计 + 最佳改法）
     * @return JavaDiagnosis 诊断结果，空壳模式下返回 null
     */
    fun diagnose(filePath: String, strictMode: Boolean = false): JavaDiagnosis?

    /**
     * 批量诊断多个 .java 文件。
     *
     * @param filePaths   .java 文件绝对路径列表
     * @param strictMode  是否启用严格模式
     * @return 诊断结果列表，仅包含有问题的文件
     */
    fun diagnoseBatch(filePaths: List<String>, strictMode: Boolean = false): List<JavaDiagnosis>

    /** 当前通道是否已挂载实现 */
    val isAvailable: Boolean
}

/**
 * Java 诊断结果。
 * 与 kotlin-head 简报格式统一——错误、警告、缺失、最佳改法。
 */
data class JavaDiagnosis(
    val filePath: String,
    val fileName: String,
    /** 编译错误（javac 原样，翻译后） */
    val errors: List<String> = emptyList(),
    /** 标准警告（按 kotlin-head 标准） */
    val warnings: List<String> = emptyList(),
    /** 缺失项（import、包名对齐等） */
    val missing: List<String> = emptyList(),
    /** 最佳改法（培养系经验驱动） */
    val suggestions: List<String> = emptyList(),
    /** 三观统计（注释率/复杂度/规范度） */
    val stats: JavaFileStats? = null,
    /** 文件是否通过诊断（无错误） */
    val passed: Boolean = errors.isEmpty()
)

data class JavaFileStats(
    val lineCount: Int = 0,
    val commentRatio: Float = 0f,        // 注释率 0~1
    val complexityScore: Float = 0f,     // 复杂度评分 0~1（低=简单）
    val complianceScore: Float = 0f,     // 规范度评分 0~1（高=规范）
    val importCount: Int = 0,
    val unusedImports: List<String> = emptyList()
)

/**
 * 空壳实现——不开 Java 检测时使用。
 * 零开销：所有方法返回 null/空列表。
 */
object NoJavaDetection : JavaDetectionChannel {
    override fun diagnose(filePath: String, strictMode: Boolean): JavaDiagnosis? = null
    override fun diagnoseBatch(filePaths: List<String>, strictMode: Boolean): List<JavaDiagnosis> = emptyList()
    override val isAvailable: Boolean = false
}
