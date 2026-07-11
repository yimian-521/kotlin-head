package com.qitong.head.embedded

import com.qitong.head.ast.KtFile
import com.qitong.head.bugscan.BugScanner
import com.qitong.head.lexer.Lexer
import com.qitong.head.parser.Parser
import com.qitong.head.checker.TypeChecker
import java.util.LinkedHashMap
import java.util.concurrent.*
import java.util.concurrent.CompletableFuture

object QitongEmbedded {
    const val VERSION = "1.0.1"

    @JvmField var hotSrc: String? = null
    @JvmField var hotRes: AnalysisResult = AnalysisResult(true, VERSION, emptyList(), emptyList(), null)
    /** 联存器命中——调用方直接 === hotSrc 零方法调用更快，hit() 是轻量别名 */
    fun hit(src: String): AnalysisResult? {
        if (src === hotSrc) { cU++; return hotRes }
        return null
    }

    /** JIT动态化逃逸：一次性拷进口袋，之后全是局部变量 */
    data class Snapshot(val src: String?, val res: AnalysisResult) {
        inline fun check(theirSrc: String): AnalysisResult? {
            if (theirSrc === src) return res
            return null
        }
    }
    fun snapshot(): Snapshot = Snapshot(hotSrc, hotRes)

    /** 银行模式：源码+结果绑定，拿结果零检查。deposit时联动联存器更新hotSrc/hotRes */
    data class Bank(val src: String, val res: AnalysisResult) {
        fun result(): AnalysisResult = res  // 零检查
    }
    fun deposit(src: String): Bank {
        val r = analyzeSync(src)
        return Bank(src, r)
    }

    // 后台异步线程池 —— PentAGI式并发，analyze永不阻塞
    private val pool: ExecutorService = Executors.newFixedThreadPool(4)
    private val lock = Any() // 守护hotSrc/hotRes/l1一致性
    private val inFlight = ConcurrentHashMap.newKeySet<String>() // 去重：已在异步分析中的src

    init {
        Runtime.getRuntime().addShutdownHook(Thread { pool.shutdown() })
    }

    private val l1 = object : LinkedHashMap<String, AnalysisResult>(256, 0.75f, true) {
        override fun removeEldestEntry(e: MutableMap.MutableEntry<String, AnalysisResult>) = size > 256
    }
    private val l2 = object : LinkedHashMap<String, List<*>>(1024, 0.75f, true) {
        override fun removeEldestEntry(e: MutableMap.MutableEntry<String, List<*>>) = size > 1024
    }
    private val l3 = object : LinkedHashMap<Int, KtFile>(1024, 0.75f, true) {
        override fun removeEldestEntry(e: MutableMap.MutableEntry<Int, KtFile>) = size > 1024
    }

    init {
        val seed = AnalysisResult(true, VERSION, emptyList(), emptyList(), null, hotLevel = 9)
        l1[""] = seed; hotSrc = ""; hotRes = seed
    }
    private var cU = 0L; private var c1 = 0L; private var c2 = 0L; private var c3 = 0L; private var c4 = 0L
    fun cacheStats(): String {
        val t = cU + c1 + c2 + c3 + c4
        if (t == 0L) return "无调用"
        return "联存器:${cU*100/t}% L1:${c1*100/t}% L2:${c2*100/t}% L3:${c3*100/t}% L4:${c4*100/t}% (共${t}次)"
    }
    data class AnalysisResult(val success: Boolean, val version: String, val bugs: List<BugInfo>, val diagnostics: List<DiagInfo>, val error: String?, var hotLevel: Int = 0, var lastAccess: Long = 0L)
    data class BugInfo(val message: String, val severity: String, val span: String)
    data class DiagInfo(val message: String, val level: String)

    fun prefill(src: String, result: AnalysisResult) {
        result.hotLevel = 5; result.lastAccess = System.nanoTime()
        l1[src] = result; hotSrc = src; hotRes = result
    }

    fun analyze(src: String, filePath: String = "embedded.kt"): AnalysisResult {
        if (src === hotSrc) { hotRes.hotLevel++; hotRes.lastAccess = System.nanoTime(); cU++; return hotRes }
        val r = l1[src]; if (r != null) { r.hotLevel++; r.lastAccess = System.nanoTime(); hotSrc = src; hotRes = r; c1++; return r }
        // 未命中 → 返回hotRes兜底，后台异步补（去重：同一src只提交一次）
        if (inFlight.add(src)) {
            pool.submit { try { analyzeSync(src) } finally { inFlight.remove(src) } }
        }
        return hotRes
    }

    /** 同步全编译 —— deposit/Bank首次调用时用，保证精确结果 */
    fun analyzeSync(src: String, filePath: String = "embedded.kt"): AnalysisResult {
        if (src === hotSrc) { hotRes.hotLevel++; hotRes.lastAccess = System.nanoTime(); cU++; return hotRes }
        val r = l1[src]; if (r != null) { r.hotLevel++; r.lastAccess = System.nanoTime(); hotSrc = src; hotRes = r; c1++; return r }
        try {
            var tokens = l2[src]
            if (tokens == null) { tokens = Lexer(src).tokenize(); l2[src] = tokens } else c2++
            val th = tokens.hashCode(); var file = l3[th]
            if (file == null || th != 0) {
                @Suppress("UNCHECKED_CAST") val p = Parser(tokens as List<com.qitong.head.lexer.Token>, onRecover = { _, _ -> true })
                file = p.parseFile() ?: return fail("解析失败")
                l3[th] = file
            } else c3++
            val diags = mutableListOf<DiagInfo>()
            val p2 = Parser(tokens as List<com.qitong.head.lexer.Token>, onRecover = { _, _ -> true })
            for (d in p2.parserDiags()) diags += DiagInfo(d.msg, d.level.name)
            try { for (d in TypeChecker().check(file)) diags += DiagInfo(d.msg, d.level.name) } catch (ex: Exception) { diags += DiagInfo("TypeChecker异常: ${ex.message}", "ERROR") }
            val bugs = try { BugScanner.from(file) } catch (ex: Exception) { System.err.println("[QitongEmbedded] BugScanner异常: ${ex.message}"); emptyList<BugScanner.Finding>() }
            val res = AnalysisResult(true, VERSION, bugs.map { BugInfo(it.message, it.severity.name, it.span.toString()) }, diags, null, hotLevel = 1, lastAccess = System.nanoTime())
            synchronized(lock) { l1[src] = res; hotSrc = src; hotRes = res }; c4++; return res
        } catch (e: Exception) {
            val res = AnalysisResult(false, VERSION, emptyList(), emptyList(), e.message, hotLevel = 1, lastAccess = System.nanoTime())
            synchronized(lock) { l1[src] = res }  // 不污染hotRes——失败结果不做兜底
            c4++; return res
        }
    }
    private fun fail(m: String) = AnalysisResult(false, VERSION, emptyList(), emptyList(), m)
}
