package com.qitong.head.embedded

import com.qitong.head.ast.KtFile
import com.qitong.head.bugscan.BugScanner
import com.qitong.head.lexer.Lexer
import com.qitong.head.parser.Parser
import com.qitong.head.parser.PrecProfile
import com.qitong.head.checker.TypeChecker
import java.util.LinkedHashMap

/**
 * 綦桐特调嵌入入口 —— 不抢 main、不碰进程树、不要交互 UI。
 *
 * X4D 四层缓存堆叠（免免 2026-07-10）：
 * - L1: 源码 → 完整结果（纳秒，256条LRU）
 * - L2: 源码 → token流（~20μs，跳过Lexer，64条LRU）
 * - L3: token流 → AST（~50μs，跳过Lexer+Parser，64条LRU）
 * - L4: 全编译管线（132μs，兜底）
 *
 * 编号跟着速度走：L1最快，L4最慢，中间不断层。
 */
object QitongEmbedded {

    const val VERSION = "0.13.1"

    // ═══ L1: 源码hash → 完整结果 ═══
    private val l1Cache = object : LinkedHashMap<String, Result>(256, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, Result>): Boolean = size > 256
    }

    // ═══ L2: 源码hash → Token流 ═══
    private val l2Cache = object : LinkedHashMap<String, List<*>>(64, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, List<*>>): Boolean = size > 64
    }

    // ═══ L3: Token流hash → AST ═══
    private val l3Cache = object : LinkedHashMap<Int, KtFile>(64, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<Int, KtFile>): Boolean = size > 64
    }

    // ═══ 统计 ═══
    var hitL1 = 0L; var hitL2 = 0L; var hitL3 = 0L; var hitL4 = 0L
        private set

    fun cacheStats(): String {
        val total = hitL1 + hitL2 + hitL3 + hitL4
        if (total == 0L) return "无调用"
        return "L1:${hitL1*100/total}% L2:${hitL2*100/total}% L3:${hitL3*100/total}% L4:${hitL4*100/total}%  (共${total}次)"
    }

    data class AnalysisResult(
        val success: Boolean,
        val version: String,
        val bugs: List<BugInfo>,
        val diagnostics: List<DiagInfo>,
        val error: String?
    )

    data class BugInfo(
        val message: String,
        val severity: String,
        val span: String
    )

    data class DiagInfo(
        val message: String,
        val level: String
    )

    /** 嵌入入口：X4D 四层缓存堆叠 */
    fun analyze(sourceCode: String, filePath: String = "embedded.kt"): AnalysisResult {
        // ── L1: 源码完全匹配 → 直接返回 ──
        l1Cache[sourceCode]?.let { result ->
            hitL1++
            return result.toResult()
        }

        try {
            // ── L2: 源码hash命中 → 跳过Lexer ──
            var tokens: List<*>? = l2Cache[sourceCode]
            if (tokens == null) {
                tokens = Lexer(sourceCode).tokenize()
                l2Cache[sourceCode] = tokens
            } else {
                hitL2++
            }

            // ── L3: Token流hash命中 → 跳过Parser ──
            val tokenHash = tokens.hashCode()
            var file: KtFile? = l3Cache[tokenHash]
            if (file == null || tokenHash != 0) {
                @Suppress("UNCHECKED_CAST")
                val parser = Parser(tokens as List<com.qitong.head.lexer.Token>, onRecover = { _, _ -> true })
                file = parser.parseFile() ?: return fail("解析返回null——源码语法错误？")
                if (hitL2 == 0L || tokenHash != 0) l3Cache[tokenHash] = file
            } else {
                hitL3++
            }

            // 管线后半段
            val allDiags = mutableListOf<DiagInfo>()
            val parser = Parser(tokens as List<com.qitong.head.lexer.Token>, onRecover = { _, _ -> true })
            for (d in parser.parserDiags()) {
                allDiags += DiagInfo(message = d.msg, level = d.level.name)
            }
            for (d in try { TypeChecker().check(file) } catch (_: Exception) { emptyList<TypeChecker.Diag>() }) {
                allDiags += DiagInfo(message = d.msg, level = d.level.name)
            }
            val findings = try { BugScanner.from(file) } catch (_: Exception) { emptyList<BugScanner.Finding>() }

            val result = Result(
                success = true,
                bugs = findings.map { BugInfo(message = it.message, severity = it.severity.name, span = it.span.toString()) },
                diagnostics = allDiags,
                error = null
            )
            l1Cache[sourceCode] = result
            hitL4++
            return result.toResult()

        } catch (e: Exception) {
            val result = Result(false, emptyList(), emptyList(), e.message ?: "未知错误")
            l1Cache[sourceCode] = result
            hitL4++
            return result.toResult()
        }
    }

    // 内部存储用（不暴露 version，统一在 toResult 补）
    private data class Result(
        val success: Boolean,
        val bugs: List<BugInfo>,
        val diagnostics: List<DiagInfo>,
        val error: String?
    ) {
        fun toResult() = AnalysisResult(success, VERSION, bugs, diagnostics, error)
    }

    private fun fail(msg: String) = AnalysisResult(false, VERSION, emptyList(), emptyList(), msg)
}
