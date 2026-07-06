package com.qitong.head.process

import com.qitong.head.headstd.HList

/**
 * v0.12.0 — java-head 适配器（底层换 HList）
 *
 * 桥接层：把 java-head 独立输出的 JavaFileReport
 * 翻译成 kotlin-head 通道定义的 JavaDiagnosis。
 */
object JavaHeadAdapter {

    private var _channel: JavaDetectionChannel = NoJavaDetection
    val channel: JavaDetectionChannel get() = _channel

    fun tryMount(): Boolean {
        return try {
            Class.forName("com.javahead.JavacRunner")
            _channel = JavaHeadChannelImpl()
            true
        } catch (_: ClassNotFoundException) {
            _channel = NoJavaDetection
            false
        }
    }

    fun unmount() { _channel = NoJavaDetection }
    val isMounted: Boolean get() = _channel.isAvailable
}

private class JavaHeadChannelImpl : JavaDetectionChannel {

    override val isAvailable: Boolean = true

    override fun diagnose(filePath: String, strictMode: Boolean): JavaDiagnosis? {
        return try {
            com.javahead.JavacRunner.diagnose(filePath, strictMode).toDiagnosis()
        } catch (e: Exception) {
            JavaDiagnosis(
                filePath = filePath,
                fileName = filePath.substringAfterLast("/"),
                errors = HList.from(listOf("java-head 调用异常: ${e.message}")),
                passed = false
            )
        }
    }

    override fun diagnoseBatch(filePaths: HList<String>, strictMode: Boolean): HList<JavaDiagnosis> {
        return try {
            val paths = mutableListOf<String>()
            for (i in 0 until filePaths.size) paths.add(filePaths.get(i))
            val results = com.javahead.JavacRunner.diagnoseBatch(paths, strictMode)
            HList.from(results.map { it.toDiagnosis() })
        } catch (e: Exception) {
            val errors = HList<JavaDiagnosis>()
            for (i in 0 until filePaths.size) {
                val path = filePaths.get(i)
                errors.add(JavaDiagnosis(
                    filePath = path,
                    fileName = path.substringAfterLast("/"),
                    errors = HList.from(listOf("java-head 批量调用异常: ${e.message}")),
                    passed = false
                ))
            }
            errors
        }
    }

    private fun com.javahead.JavaFileReport.toDiagnosis(): JavaDiagnosis {
        return JavaDiagnosis(
            filePath = this.filePath,
            fileName = this.fileName,
            errors = HList.from(this.errors.map { it.message }),
            warnings = HList.from(this.warnings.map { it.message }),
            suggestions = HList.from(this.suggestions),
            stats = JavaFileStats(
                lineCount = this.stats.totalLines,
                commentRatio = if (this.stats.totalLines > 0)
                    this.stats.commentLines.toFloat() / this.stats.totalLines else 0f,
                complexityScore = 0f,
                complianceScore = 0f,
                importCount = this.stats.importCount,
                unusedImports = HList.from(this.stats.unusedImports)
            ),
            passed = this.passed
        )
    }
}