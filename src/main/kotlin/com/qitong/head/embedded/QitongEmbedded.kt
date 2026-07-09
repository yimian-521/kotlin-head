package com.qitong.head.embedded

import com.qitong.head.ast.KtFile
import com.qitong.head.bugscan.BugScanner
import com.qitong.head.lexer.Lexer
import com.qitong.head.parser.Parser
import com.qitong.head.parser.PrecProfile
import com.qitong.head.checker.TypeChecker

/**
 * 綦桐特调嵌入入口 —— 不抢 main、不碰进程树、不要交互 UI。
 *
 * 綦桐网关直接调 [analyze]，拿结构化结果。
 * 内核（Lexer/Parser/TypeChecker/BugScanner）不变——外面只加一层适配。
 *
 * 架构四原则：
 * - 适配层不碰核心逻辑
 * - 探针用 id 不用名字
 * - 组件不塌缩：这个类只管嵌入调度
 */
object QitongEmbedded {

    const val VERSION = "0.12.9-qitong"

    /** 结果容器 —— 綦桐网关拿到直接映射到 UI */
    data class AnalysisResult(
        val success: Boolean,
        val version: String,
        val bugs: List<BugInfo>,
        val diagnostics: List<DiagInfo>,
        val error: String?
    )

    data class BugInfo(
        val message: String,
        val severity: String,    // HIGH / MEDIUM / LOW
        val span: String         // "文件:行:列"
    )

    data class DiagInfo(
        val message: String,
        val level: String        // ERROR / WARN
    )

    /** 嵌入入口: 一行调用，返回结构化结果 */
    fun analyze(sourceCode: String, filePath: String = "embedded.kt"): AnalysisResult {
        try {
            // 编译管线（纯逻辑，无全局状态依赖）
            val tokens = Lexer(sourceCode).tokenize()
            val parser = Parser(tokens) { _, _ -> true }
            val file: KtFile = parser.parseFile() ?: return AnalysisResult(
                false, VERSION, emptyList(), emptyList(), "解析返回null——源码语法错误？"
            )

            // 诊断
            val allDiags = mutableListOf<DiagInfo>()
            for (d in parser.parserDiags()) {
                allDiags += DiagInfo(message = d.msg, level = d.level.name)
            }
            for (d in try { TypeChecker().check(file) } catch (_: Exception) { emptyList() }) {
                allDiags += DiagInfo(message = d.msg, level = d.level.name)
            }

            // Bug 扫描
            val findings = try { BugScanner.from(file) } catch (_: Exception) { emptyList() }

            return AnalysisResult(
                success = true,
                version = VERSION,
                bugs = findings.map {
                    BugInfo(message = it.message, severity = it.severity.name, span = it.span.toString())
                },
                diagnostics = allDiags,
                error = null
            )
        } catch (e: Exception) {
            return AnalysisResult(
                false, VERSION, emptyList(), emptyList(),
                e.message ?: "未知错误"
            )
        }
    }
}
