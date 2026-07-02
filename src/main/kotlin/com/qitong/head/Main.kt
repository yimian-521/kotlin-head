package com.qitong.head

import com.qitong.head.ast.*
import com.qitong.head.bugscan.BugScanner
import com.qitong.head.lexer.Lexer
import com.qitong.head.parser.Parser
import com.qitong.head.checker.TypeChecker
import com.qitong.head.diagnostic.Diagnostic
import com.qitong.head.internal.JsonUtil
import java.io.File

/**
 * 有头编译器（kotlin-head）—— 按钮终端主入口。
 *
 * 双输入桥：
 *   人类 → 按键盘上的字母/数字 → 触发按钮
 *   AI   → 匹配按钮名 → 同样触发
 * 同一个界面，两端都是原生操作。
 */
object Main {

    const val VERSION = "0.4.5-dev"

    private val dev = DevMode.boot()

    // 状态机
    private var page = "main"
    private var lastFile: KtFile? = null
    private var lastSrc: String = ""
    private var lastDiags: List<TypeChecker.Diag> = emptyList()
    private var lastSrcPath: String = ""

    @JvmStatic
    fun main(args: Array<String>) {
        println("╔════════════════════════════╗")
        println("║   有头编译器 kotlin-head   ║")
        println("╚════════════════════════════╝")
        println()

        if (args.isEmpty()) {
            println("用法: kotlin-head <源码.kt> [--sim|--ast|--diag]")
            return
        }

        val path = args[0]
        val flag = args.getOrNull(1) ?: ""
        val file = File(path)
        if (!file.exists()) {
            println("✖ 文件不存在: $path")
            return
        }

        lastSrcPath = path
        lastSrc = file.readText()
        compile(lastSrc)

        // 恢复上次状态
        restoreSession()

        // 管道/flag 模式：直接跳转，不进入交互循环
        when {
            flag == "--sim" || flag == "--ast" || flag == "--diag" -> {
                page = flag.removePrefix("--")
                renderPage()
                return
            }
        }

        // 主循环
        loop()
    }

    // ─── 编译流水线 ───
    private var lastParser: Parser? = null
    private var lastFindings: List<BugScanner.Finding> = emptyList()

    private fun compile(src: String) {
        val tokens = Lexer(src).tokenize()
        val parser = Parser(tokens)
        lastParser = parser
        lastFile = try {
            parser.parseFile()
        } catch (e: Exception) {
            println("✖ 解析异常: ${e.message}")
            null
        }
        // 合并 Parser 层诊断 + TypeChecker 诊断
        val parserDiags = parser.parserDiags()
        val checkerDiags = if (lastFile != null) {
            TypeChecker().check(lastFile!!)
        } else emptyList()
        lastDiags = parserDiags + checkerDiags
        // BugScanner
        lastFindings = if (lastFile != null) {
            BugScanner.from(lastFile!!)
        } else emptyList()
    }

    // ─── 状态恢复 ───
    private fun restoreSession() {
        val data = dev.read("session") ?: return
        val json = String(data)
        val map = try {
            com.qitong.head.internal.JsonUtil.decode(json) as? Map<*, *>
        } catch (_: Exception) { null } ?: return
        page = map["page"] as? String ?: "main"
    }

    private fun saveSession() {
        val json = com.qitong.head.internal.JsonUtil.encode(
            mapOf("page" to page, "src" to lastSrcPath)
        )
        dev.store("session", json.toByteArray())
    }

    // ─── 主循环 ───
    private fun loop() {
        while (true) {
            println("\n".repeat(2))
            renderPage()

            print("\n> ")
            val input = readLine() ?: break
            if (input.isBlank()) continue

            val cmd = input.trim().toLowerCase()
            if (cmd == "q" || cmd == "quit" || cmd == "exit") {
                println("再见 👋")
                return
            }
            handle(cmd)
        }
    }

    // ─── 按钮渲染 ───
    private fun renderPage() {
        when (page) {
            "main" -> renderMain()
            "ast" -> renderAst()
            "diag" -> renderDiag()
            "sim" -> renderSim()
            "admin" -> renderAdmin()
            else -> { page = "main"; renderMain() }
        }
    }

    // ─── 模拟运行 ───
    private val simProfiles: List<SimProfile> by lazy {
        val f = File("/sdcard/Download/Operit/search_vault/kotlin-head/simulate.json")
        if (!f.exists()) emptyList<SimProfile>()
        else try {
            val json = JsonUtil.decode(f.readText()) as? Map<*, *> ?: return@lazy emptyList<SimProfile>()
            val arr = json["profiles"] as? List<*> ?: return@lazy emptyList<SimProfile>()
            arr.mapNotNull {
                val m = it as? Map<*, *> ?: return@mapNotNull null
                SimProfile(m["name"] as? String ?: "?", m["file"] as? String ?: "")
            }
        } catch (_: Exception) { emptyList<SimProfile>() }
    }
    data class SimProfile(val name: String, val file: String)

    private fun renderSim() {
        println("═══ 模拟运行 ═══")
        println("  选一个配置，自动跑编译+诊断")
        println()
        simProfiles.forEachIndexed { i, p ->
            println("  [${i + 1}] ${p.name}")
        }
        println("  [0] 返回主页")
    }

    private fun handleSim(key: String) {
        if (key == "0") { page = "main"; return }
        val idx = key.toIntOrNull()?.minus(1) ?: return
        val profile = simProfiles.getOrNull(idx) ?: return
        println("  模拟: ${profile.name}")
        if (profile.file.isEmpty()) {
            compile("")
        } else {
            val f = File(profile.file)
            if (!f.exists()) { println("  ✖ 文件不存在: ${profile.file}"); return }
            lastSrcPath = profile.file
            lastSrc = f.readText()
            compile(lastSrc)
        }
        println("  完成 ✓")
        println("  [1] 查看AST  [2] 查看诊断  [0] 返回")
        val input = readLine() ?: ""
        when (input.trim()) {
            "1" -> page = "ast"
            "2" -> page = "diag"
            else -> page = "main"
        }
    }

    private fun renderMain() {
        println("═══ 主页 ═══  ${lastSrcPath.takeLast(30)}")
        println()
        println("  [1] 查看 AST 树")
        println("  [2] 查看诊断 (${diagSummary()})")
        println("  [3] 重新编译")
        println("  [4] 模拟运行")
        println("  [5] 管理员")
        println("  [q] 退出")
    }

    private fun renderAst() {
        println("═══ AST 视图 ═══")
        println()
        if (lastFile == null) {
            println("  (无 AST —— 编译失败)")
        } else {
            println(formatAst(lastFile!!))
        }
        println()
        println("  [1] 返回主页")
    }

    private fun renderDiag() {
        println(Diagnostic.from(lastDiags, lastFindings).format(lastFile?.declarations?.size ?: 0))
        println("  [1] 返回主页")
    }

    private fun renderAdmin() {
        println("═══ 管理员 ═══")
        println()
        println("  ~/.kotlin-head/  ← 数据在这，随便看随便改")
        println("  CRC 校验保护完整性，改坏自动恢复默认值")
        println()
        println("  [1] 返回主页")
        println("  [2] 清除所有缓存")
    }

    // ─── 输入处理 ───
    private fun handle(input: String) {
        // AI 匹配按钮名（不用数字）
        val byLabel = when (input) {
            "查看ast树", "ast", "ast视图" -> "1"
            "查看诊断", "diagnostic", "diag", "诊断" -> "2"
            "重新编译", "compile", "编译", "rebuild" -> "3"
            "模拟运行", "sim", "模拟" -> "4"
            "管理员", "admin", "管理" -> "5"
            "返回主页", "back", "主页", "main", "返回" -> "1"
            "清除所有缓存", "清除缓存", "clearcache" -> "2"
            else -> input
        }

        when (page) {
            "main" -> handleMain(byLabel)
            "ast" -> handleAst(byLabel)
            "diag" -> handleDiag(byLabel)
            "sim" -> handleSim(byLabel)
            "admin" -> handleAdmin(byLabel)
        }
        saveSession()
    }

    private fun handleMain(key: String) {
        when (key) {
            "1" -> page = "ast"
            "2" -> page = "diag"
            "3" -> {
                println("  重新编译中...")
                lastSrc = File(lastSrcPath).readText()
                compile(lastSrc)
                println("  完成 ✓")
            }
            "4" -> page = "sim"
            "5" -> page = "admin"
            else -> println("  ? 未知按钮: $key")
        }
    }

    private fun handleAst(key: String) {
        if (key == "1") page = "main"
    }

    private fun handleDiag(key: String) {
        if (key == "1") page = "main"
    }

    private fun handleAdmin(key: String) {
        when (key) {
            "1" -> page = "main"
            "2" -> {
                val cacheDir = File(System.getProperty("user.home"), ".kotlin-head/cache")
                cacheDir.listFiles()?.forEach { it.delete() }
                println("  缓存已清除 ✓")
            }
            else -> println("  ? 未知按钮: $key")
        }
    }

    // ─── 格式化 ───
    private fun diagSummary(): String {
        val errors = lastDiags.count { it.level == TypeChecker.DiagLevel.ERROR }
        val warns = lastDiags.count { it.level == TypeChecker.DiagLevel.WARN || it.level == TypeChecker.DiagLevel.EXPECTED }
        val bugs = lastFindings.count { it.severity == BugScanner.Severity.HIGH }
        return when {
            errors > 0 || bugs > 0 -> "✖ ${errors + bugs}"
            warns > 0 -> "⚠ $warns"
            else -> "✓"
        }
    }

    private fun formatAst(node: KtNode, indent: Int = 0): String {
        val pad = "  ".repeat(indent)
        val sb = StringBuilder()
        when (node) {
            is KtFile -> {
                if (node.pkg != null) sb.append("${pad}package ${node.pkg}\n")
                for (d in node.declarations) sb.append(formatAst(d, indent))
            }
            is KtClass -> {
                sb.append("${pad}class ${node.name}")
                if (node.modifiers.isNotEmpty()) sb.append(" (${node.modifiers.joinToString()})")
                sb.append("\n")
                for (m in node.members) sb.append(formatAst(m, indent + 1))
            }
            is KtFun -> {
                val params = node.params.joinToString(", ") { "${it.name}:${it.type ?: "?"}" }
                val ret = node.returnType ?: "?"
                sb.append("${pad}fun ${node.name}($params): $ret")
                if (node.body != null) sb.append(" { ... }")
                sb.append("\n")
            }
            is KtVal -> {
                val t = node.type ?: "?"
                sb.append("${pad}val ${node.name}: $t")
                if (node.value != null) sb.append(" = ...")
                sb.append("\n")
            }
            is KtIf -> {
                sb.append("${pad}if (...) { ... }")
                if (node.elseBranch != null) sb.append(" else { ... }")
                sb.append("\n")
            }
            is KtCall -> {
                val args = node.args.joinToString(", ") { "..." }
                sb.append("${pad}call($args)\n")
            }
            is KtBinary -> sb.append("${pad}binary(${node.op})\n")
            is KtLambda -> sb.append("${pad}lambda\n")
            is KtRef -> sb.append("${pad}ref:${node.name}\n")
            is KtLitInt -> sb.append("${pad}int:${node.value}\n")
            is KtLitStr -> sb.append("${pad}str:${node.value}\n")
            is KtReturn -> sb.append("${pad}return\n")
            is KtBlock -> {
                sb.append("${pad}{\n")
                for (s in node.statements) sb.append(formatAst(s, indent + 1))
                sb.append("${pad}}\n")
            }
            // v0.4 新增
            is KtWhen -> {
                sb.append("${pad}when")
                if (node.subject != null) sb.append(" (...)")
                sb.append(" {\n")
                for (b in node.branches) sb.append("${pad}  ... -> ...\n")
                if (node.elseBranch != null) sb.append("${pad}  else -> ...\n")
                sb.append("${pad}}\n")
            }
            is KtFor -> sb.append("${pad}for(${node.variable} in ...) { ... }\n")
            is KtWhile -> sb.append("${pad}while(...) { ... }\n")
            is KtObject -> {
                val label = if (node.isCompanion) "companion object" else "object"
                val n = node.name ?: ""
                sb.append("${pad}$label $n { ${node.members.size} members }\n")
            }
            is KtInterface -> sb.append("${pad}interface ${node.name} { ${node.members.size} members }\n")
            is KtEnum -> {
                val consts = node.constants.joinToString(", ")
                sb.append("${pad}enum class ${node.name} { $consts; ${node.members.size} members }\n")
            }
            else -> sb.append("${pad}${node.javaClass.simpleName}\n")
        }
        return sb.toString()
    }
}