package com.qitong.head
import com.qitong.head.ast.*
import com.qitong.head.lexer.Lexer
import com.qitong.head.parser.Parser
import com.qitong.head.simui.SimUiScanner
import com.qitong.head.tomb.BugTombstone
import java.io.File

fun main(args: Array<String>) {
    if (args.isEmpty()) { println("用法: java -jar kotlin-head.jar <文件路径>"); return }
    val src = File(args[0]).readText()
    val fileName = File(args[0]).name
    val tokens = Lexer(src).tokenize()
    val file = Parser(tokens).parseFile()
    val result = SimUiScanner.scan(file)
    
    // ══════════ 墓碑引擎 ══════════
    val entries = BugTombstone.query(fileName)
    val tombs = entries.map { Triple(it.fileKey, it.line, "${it.type} → ${it.fix} (${it.version})") }.toMutableList()
    
    println("=== kotlin-head v0.12.8 ===")
    
    // 墓碑输出——在按钮扫描之前
    if (tombs.isNotEmpty()) {
        println()
        println("══════ ⚰️ 墓碑合约 ══════")
        println("  此文件有 ${tombs.size} 个已知bug修复合约：")
        tombs.forEach { (f, line, info) ->
            println("  ⚰️ L$line · $info")
        }
        println("  → 改动相关代码时请确认不覆盖已修复路径")
        println("══════════════════════════")
        println()
    }
    println("按钮: ${result.buttons.size} 个")
    result.buttons.forEach { btn ->
        println("  [${btn.label}] L${btn.line} -> ${btn.actionHint}")
        if (btn.varDependencies.isNotEmpty()) println("  依赖: ${btn.varDependencies}")
    }
    
    // 内置诊断——不需TypeChecker，纯AST遍历
    val diags = diagnose(file, src)
    if (diags.isNotEmpty()) {
        println()
        println("── 内置诊断 ──")
        val lines = src.lines()
        diags.forEach { d ->
            println()
            println("  $d")
            // 提取行号并显示对应源码
            val lineNo = Regex("""L(\d+)""").find(d)?.groupValues?.get(1)?.toIntOrNull()
            if (lineNo != null && lineNo <= lines.size) {
                println("    ${lines[lineNo - 1].trim()}")
            }
        }
    }
}

/** 纯AST扫描 + 源码正则：空catch、硬编码常量 */
fun diagnose(file: KtFile, src: String): List<String> {
    val issues = mutableListOf<String>()
    
    // 源码级：空catch（Parser不解析try-catch）
    val emptyCatch = Regex("""catch\s*\([^)]*\)\s*\{\s*\}""")
    var lineNum = 1
    for (line in src.lines()) {
        if (emptyCatch.containsMatchIn(line)) {
            issues.add("L$lineNum: 空catch——异常被静默吞掉")
        }
        lineNum++
    }
    
    // AST级：硬编码常量、静默路径
    fun walk(nodes: List<KtNode>, depth: Int = 0) {
        if (depth > 60) return
        for (node in nodes) {
            when (node) {
                is KtVal -> {
                    if (node.name.all { it.isUpperCase() || it == '_' } && node.value != null) {
                        val v = node.value
                        if (v is KtLitInt || v is KtLitStr) {
                            issues.add("L${node.span.start.line}: 硬编码常量 ${node.name}=${(v as? KtLitInt)?.value ?: (v as KtLitStr).value}")
                        }
                    }
                    node.value?.let { walk(listOf(it), depth + 1) }
                }
                is KtBlock -> walk(node.statements, depth + 1)
                is KtIf -> {
                    node.thenBranch?.let { walk(listOf(it), depth + 1) }
                    node.elseBranch?.let { walk(listOf(it), depth + 1) }
                }
                is KtWhen -> {
                    for (b in node.branches) walk(listOf(b.body), depth + 1)
                    node.elseBranch?.let { walk(listOf(it), depth + 1) }
                }
                is KtFor -> { walk(listOf(node.iterable), depth + 1); node.body?.let { walk(listOf(it), depth + 1) } }
                is KtWhile -> { node.condition?.let { walk(listOf(it), depth + 1) }; node.body?.let { walk(listOf(it), depth + 1) } }
                is KtClass -> walk(node.members, depth + 1)
                is KtObject -> walk(node.members, depth + 1)
                is KtFun -> node.body?.let { walk(listOf(it), depth + 1) }
                is KtCall -> walk(node.args, depth + 1)
                is KtBinary -> { walk(listOf(node.left), depth + 1); walk(listOf(node.right), depth + 1) }
                else -> {}
            }
        }
    }
    
    walk(file.declarations)
    return issues
}