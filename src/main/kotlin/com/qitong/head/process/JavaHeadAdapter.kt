package com.qitong.head.process

/**
 * v0.11.8 — java-head 适配器
 *
 * 桥接层：把 java-head 独立输出的 JavaFileReport
 * 翻译成 kotlin-head 通道定义的 JavaDiagnosis。
 *
 * 设计原则：
 * - 适配器不依赖 java-head 的类（运行时反射加载）
 * - 如果 java-head.jar 不在 classpath → 回退到 NoJavaDetection
 * - 适配器本身是 JavaDetectionChannel 的实现
 */
object JavaHeadAdapter {

    private var _channel: JavaDetectionChannel = NoJavaDetection

    /** 当前激活的通道 */
    val channel: JavaDetectionChannel get() = _channel

    /** 尝试挂载 java-head.jar */
    fun tryMount(): Boolean {
        return try {
            // 尝试加载 java-head 的桥接实现
            val clazz = Class.forName("com.javahead.JavacRunner")
            // 找到 → 说明 java-head 在 classpath
            _channel = JavaHeadChannelImpl()
            true
        } catch (_: ClassNotFoundException) {
            _channel = NoJavaDetection
            false
        }
    }

    /** 卸载，回退空壳 */
    fun unmount() {
        _channel = NoJavaDetection
    }

    val isMounted: Boolean get() = _channel.isAvailable
}

/**
 * java-head 桥接实现。
 * 把 JavaFileReport → JavaDiagnosis 做字段映射。
 *
 * 注意：此类依赖 com.javahead 包，需要 java-head.jar 在 classpath。
 */
private class JavaHeadChannelImpl : JavaDetectionChannel {

    override val isAvailable: Boolean = true

    override fun diagnose(filePath: String, strictMode: Boolean): JavaDiagnosis? {
        return try {
            val report = com.javahead.JavacRunner.diagnose(filePath, strictMode)
            report.toDiagnosis()
        } catch (e: Exception) {
            JavaDiagnosis(
                filePath = filePath,
                fileName = filePath.substringAfterLast("/"),
                errors = listOf("java-head 调用异常: ${e.message}"),
                passed = false
            )
        }
    }

    override fun diagnoseBatch(filePaths: List<String>, strictMode: Boolean): List<JavaDiagnosis> {
        return try {
            com.javahead.JavacRunner.diagnoseBatch(filePaths, strictMode)
                .map { it.toDiagnosis() }
        } catch (e: Exception) {
            filePaths.map { path ->
                JavaDiagnosis(
                    filePath = path,
                    fileName = path.substringAfterLast("/"),
                    errors = listOf("java-head 批量调用异常: ${e.message}"),
                    passed = false
                )
            }
        }
    }

    /** 字段映射：JavaFileReport → JavaDiagnosis */
    private fun com.javahead.JavaFileReport.toDiagnosis(): JavaDiagnosis {
        return JavaDiagnosis(
            filePath = this.filePath,
            fileName = this.fileName,
            errors = this.errors.map { it.message },
            warnings = this.warnings.map { it.message },
            missing = emptyList(), // java-head 当前不做缺失检查
            suggestions = this.suggestions,
            stats = JavaFileStats(
                lineCount = this.stats.totalLines,
                commentRatio = if (this.stats.totalLines > 0)
                    this.stats.commentLines.toFloat() / this.stats.totalLines else 0f,
                complexityScore = 0f, // 未实现
                complianceScore = 0f, // 未实现
                importCount = this.stats.importCount,
                unusedImports = this.stats.unusedImports
            ),
            passed = this.passed
        )
    }
}