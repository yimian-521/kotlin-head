package com.qitong.head

import com.qitong.head.ast.*
import com.qitong.head.bugscan.BugScanner
import com.qitong.head.bugdb.BugDB
import com.qitong.head.bugdb.BugRules
import com.qitong.head.bugdb.BugSeverity
import com.qitong.head.lexer.Lexer
import com.qitong.head.parser.Parser
import com.qitong.head.parser.PrecProfile
import com.qitong.head.checker.TypeChecker
import com.qitong.head.diagnostic.Diagnostic
import com.qitong.head.process.ProcessCoordinator
import com.qitong.head.process.CommanderReport
import com.qitong.head.process.MainProcessStyle
import com.qitong.head.process.MultiProjectCoordinator
import com.qitong.head.process.ApkPackCoordinator
import com.qitong.head.process.JavaDetectionChannel
import com.qitong.head.eventbus.*
import com.qitong.head.eventbus.DependencyGraph
import com.qitong.head.eventbus.LiveDeclarationGraph
import com.qitong.head.ir.*
import com.qitong.head.pass.*
import com.qitong.head.internal.JsonUtil
import java.io.File
import com.qitong.head.runtime.*

/**
 * 有头编译器（kotlin-head）—— 按钮终端主入口。
 *
 * 双输入桥：
 *   人类 → 按键盘上的字母/数字 → 触发按钮
 *   AI   → 匹配按钮名 → 同样触发
 * 同一个界面，两端都是原生操作。
 */
object Main {

    const val VERSION = "0.11.3"

    private val dev = DevMode.boot()

    // 状态机
    private var page = "main"
    private var lastFile: KtFile? = null
    private var lastSrc: String = ""
    private var lastDiags: List<TypeChecker.Diag> = emptyList()
    private var lastSrcPath: String = ""
    // v0.8.0: 进程树报告缓存
    private var lastProcessReports: Map<String, CommanderReport> = emptyMap()
    // v0.8.2: IR + EventBus 状态
    private var lastIR: IRModule? = null
    private var eventBusInit = false

    @JvmStatic
    fun main(args: Array<String>) {
        hPrintln("╔════════════════════════════╗")
        hPrintln("║ 有头编译器 kotlin-head v$VERSION ║")
        hPrintln("╚════════════════════════════╝")
        hPrintln()

        if (args.isEmpty()) {
            hPrintln("用法: kotlin-head <源码.kt> [--sim|--ast|--diag]")
            return
        }

        val path = args[0]
        val flag = args.getOrNull(1) ?: ""
        val file = File(path)
        if (!file.exists()) {
            hPrintln("✖ 文件不存在: $path")
            return
        }

        lastSrcPath = path
        
        // v0.8.2: EventBus 初始化 + 注册编译管线 + Pass 链
        initEventBus()
        
        // v0.12.1: BugDB 5000条规则库注册
        BugRules.register()
        
        // v0.8.3: ProcessCoordinator 接管文件读取（角色不塌缩）
        ProcessCoordinator.initialize()
        
        // v0.11.1: 军师进程——编译前决策优先级策略
        strategistDecide(path)

        // v0.11.3: 父进程治理风格——命令行或自动判断
        val styleFlag = args.find { it.startsWith("--style=") }?.removePrefix("--style=")
        if (styleFlag != null) {
            ProcessCoordinator.setStyle(try {
                MainProcessStyle.valueOf(styleFlag.uppercase())
            } catch (_: Exception) { MainProcessStyle.FEDERAL })
        }

        // v0.11.3: 主动增派——根据源码规模预配军队（地狱文件加倍）
        val isHostileFile = path.contains("hell", ignoreCase = true) || path.contains("destroy", ignoreCase = true)
        ProcessCoordinator.prepareArmy(file.length().toInt(), 1, isHostile = isHostileFile)

        // v0.8.3: AsyncIO 归指挥官——主进程只下命令
        lastSrc = file.readText()
        compile(lastSrc)

        // 恢复上次状态
        restoreSession()

        // 管道/flag 模式：直接跳转，不进入交互循环
        when {
            flag == "--sim" || flag == "--ast" || flag == "--diag" || flag == "--process" -> {
                page = flag.removePrefix("--")
                renderPage()
                return
            }
        }

        // 主循环
        loop()
    }

    // v0.11.1: 军师进程——编译前分析文件特征，决策优先级策略
    private fun strategistDecide(srcPath: String) {
        Parser.activePrecTable = when {
            srcPath.contains("hell", ignoreCase = true) -> PrecProfile.DEFENSIVE
            srcPath.contains("destroy", ignoreCase = true) -> PrecProfile.DEFENSIVE
            else -> PrecProfile.STANDARD
        }
    }

    // ─── 编译流水线 ───
    private var lastParser: Parser? = null
    private var lastFindings: List<BugScanner.Finding> = emptyList()
    private var lastBugdbHits = 0
    private var lastBugdbMild = 0
    private var lastBugdbModerate = 0
    private var lastBugdbSevere = 0

    /** v0.9.0: 检测变更声明——对比新 AST 和活图中的声明 */
    private fun detectChangedDecls(file: KtFile): Set<String> {
        val changed = mutableSetOf<String>()
        val fileName = lastSrcPath
        loop@ for (decl in file.declarations) {
            val declId = when (decl) {
                is KtFun -> "$fileName:fun:${decl.name}"
                is KtClass -> "$fileName:class:${decl.name}"
                is KtVal -> "$fileName:val:${decl.name}"
                else -> continue@loop
            }
            val existing = LiveDeclarationGraph.getNode(declId)
            if (existing == null) {
                changed.add(declId)
                LiveDeclarationGraph.registerOrUpdate(fileName, decl)
            } else {
                val newSig = when (decl) {
                    is KtFun -> decl.params.joinToString(",") { "${it.name}:${it.type ?: "Any"}" }
                    is KtClass -> decl.members.joinToString(",") { m ->
                        when (m) {
                            is KtFun -> m.name
                            is KtClass -> m.name
                            is KtVal -> m.name
                            else -> "?"
                        }
                    }
                    is KtVal -> decl.type ?: "inferred"
                    else -> ""
                }
                if (newSig != existing.signature) {
                    changed.add(declId)
                    LiveDeclarationGraph.registerOrUpdate(fileName, decl)
                }
            }
        }
        return changed
    }

    private fun compile(src: String) {
        // v0.8.3: 依赖图 staging —— 编译开始时建快照
        DependencyGraph.snapshot()
        
        val tokens = Lexer(src).tokenize()
        EventBus.emitTo("lex", "lex_complete", mapOf("tokens" to tokens.size))
        
        val parser = Parser(tokens, ProcessCoordinator.getParserRecoveryFn())
        lastParser = parser
        lastFile = try {
            parser.parseFile()
        } catch (e: Exception) {
            EventBus.emitTo("error", "parse_crashed", mapOf("error" to e.message))
            hPrintln("✖ 解析异常: ${e.message}")
            null
        }
        EventBus.emitTo("parse", "parse_complete", mapOf("success" to (lastFile != null)))
        
        // v0.9.0: LiveDeclarationGraph —— 第三种混合编译
        // 第一次：加量不加价，建图+全量编译同时完成
        // 之后：检测变更声明 → 传播 → 只编受影响声明
        val ktFile = lastFile
        if (ktFile != null) {
            if (!LiveDeclarationGraph.isLive) {
                // 第一次编译：从 AST 建活图，顺便全量编译
                LiveDeclarationGraph.buildFromAst(mapOf(lastSrcPath to ktFile))
                EventBus.emitTo("decl_graph", "graph_built", mapOf(
                    "declarations" to LiveDeclarationGraph.totalDeclarations(),
                    "files" to LiveDeclarationGraph.totalFiles()
                ))
            } else {
                // 重新编译：检测变更声明，传播受影响范围
                val changedIds = detectChangedDecls(ktFile)
                if (changedIds.isNotEmpty()) {
                    val affected = LiveDeclarationGraph.propagate(changedIds)
                    EventBus.emitTo("decl_graph", "change_detected", mapOf(
                        "changed" to changedIds.size,
                        "affected" to affected.size,
                        "skipped" to (LiveDeclarationGraph.totalDeclarations() - affected.size)
                    ))
                } else {
                    EventBus.emitTo("decl_graph", "no_change", mapOf(
                        "total" to LiveDeclarationGraph.totalDeclarations()
                    ))
                }
            }
        }
        
        // v0.8.3: 依赖图冲突检测 + 广播
        val conflicts = DependencyGraph.detectConflicts()
        if (conflicts.isNotEmpty()) {
            DependencyGraph.resolveConflicts()
            EventBus.emitTo("dep", "conflict_summary", mapOf("count" to conflicts.size))
        }
        
        // 合并 Parser 层诊断 + TypeChecker 诊断
        val parserDiags = parser.parserDiags()
        val checkerDiags = if (lastFile != null) {
            TypeChecker().check(lastFile!!)
        } else emptyList()
        lastDiags = parserDiags + checkerDiags
        EventBus.emitTo("typecheck", "check_complete", mapOf(
            "errors" to lastDiags.count { it.level == TypeChecker.DiagLevel.ERROR },
            "warns" to lastDiags.count { it.level != TypeChecker.DiagLevel.ERROR }
        ))
        
        // BugScanner
        lastFindings = if (lastFile != null) {
            BugScanner.from(lastFile!!)
        } else emptyList()
        
        // BugDB 5000条规则扫描
        lastBugdbHits = 0; lastBugdbMild = 0; lastBugdbModerate = 0; lastBugdbSevere = 0
        val hits = BugDB.scan(lastSrc)
        lastBugdbHits = hits.size
        for (r in hits) {
            when (r.severity) {
                BugSeverity.MILD -> lastBugdbMild++
                BugSeverity.MODERATE -> lastBugdbModerate++
                BugSeverity.SEVERE -> lastBugdbSevere++
            }
        }
        
        // v0.8.2: IR 生成 + Pass 优化管线
        lastIR = if (lastFile != null) {
            val ir = IrGenerator.from(lastFile!!)
            EventBus.emitTo("ir", "ir_generated", mapOf("functions" to ir.functionCount(), "instructions" to ir.totalInstructions()))
            val optimized = EventBus.processStream("ir-pass", ir)
            EventBus.emitTo("ir", "pass_complete", mapOf(
                "dead_code_elim" to (optimized.metadata["pass:dead-code-elim"] ?: "skipped"),
                "const_fold" to (optimized.metadata["pass:const-fold"] ?: "skipped"),
                "inline" to (optimized.metadata["pass:inline"] ?: "skipped")
            ))
            optimized
        } else null
        
        // v0.8.0: 进程树注解处理
        runProcessTree(lastFile)
        
        // v0.8.3: 依赖图合并 staging
        DependencyGraph.commit()
    }
    
    // ─── v0.8.0 进程树注解处理 ───
    private var processTreeInit = false
    
    private fun runProcessTree(file: KtFile?) {
        if (!processTreeInit) {
            ProcessCoordinator.initialize()
            processTreeInit = true
        }
        if (file == null) {
            lastProcessReports = emptyMap()
            EventBus.emitTo("error", "process_tree_no_file", mapOf("reason" to "编译失败，无法提取注解"))
            return
        }
        // 从AST提取注解，转为AnnotationTask
        val tasks = extractAnnotationTasks(file)
        if (tasks.isEmpty()) {
            lastProcessReports = emptyMap()
            return
        }
        lastProcessReports = ProcessCoordinator.processAnnotations(tasks)
        // v0.8.2: 进程树报告走 EventBus 广播
        for ((tag, report) in lastProcessReports) {
            EventBus.emitTo("process", "commander_report", mapOf(
                "tag" to tag,
                "completed" to report.completedCount,
                "total" to report.totalCount,
                "summary" to report.summary
            ))
            // 失败的任务走 error 频道
            for (r in report.results) {
                if (r is com.qitong.head.process.ProcessResult.Failure) {
                    EventBus.emitTo("error", "process_body_failed", mapOf(
                        "tag" to tag, "error" to r.error, "recoverable" to r.recoverable
                    ))
                }
            }
        }
    }
    
    // ─── v0.8.2 EventBus 初始化 ───
    private fun initEventBus() {
        if (eventBusInit) return
        eventBusInit = true
        
        // 注册编译阶段日志订阅者（HED/TDL可订阅同频道）
        EventBus.subscribe("lex", object : EventHandler {
            override fun onEvent(event: Event) {
                // 供 HED 面板 / TDL 文档订阅
            }
        })
        EventBus.subscribe("parse", object : EventHandler {
            override fun onEvent(event: Event) {
                // 供进程树 / HED 面板订阅
            }
        })
        EventBus.subscribe("error", object : EventHandler {
            override fun onEvent(event: Event) {
                // 错误中心：HED面板、TDL文档、进程树指挥官三方订阅
                // 三层容错判断：局部继续 / 成功一半分报 / 架构才停
            }
        })
        
        // v0.8.2: 依赖图解析走 "dep" 频道（HED/TDL/进程树三方订阅）
        EventBus.subscribe("dep", object : EventHandler {
            override fun onEvent(event: Event) {
                when (event.type) {
                    "conflict_detected" -> {
                        val payload = event.payload as? Map<*, *> ?: return
                        // 进程树指挥官收到后标记不健康
                    }
                    "conflict_resolved" -> {
                        // HED 面板展示解决方案
                    }
                }
            }
        })
        
        // Pass 链注册到流式通道 "ir-pass"
        val passChannel = EventBus.streamChannel<IRModule>("ir-pass")
        for (pass in Pass.standardChain()) {
            passChannel.pipe(pass)
        }
    }
    
    // ─── v0.8.2 IrGenerator: AST → IR ───
    private object IrGenerator {
        private var tmpIdx = 0
        private fun tmp(prefix: String = "t") = "${prefix}${tmpIdx++}"
        
        fun from(file: KtFile): IRModule {
            tmpIdx = 0
            val module = IRModule(name = file.pkg ?: "unnamed")
            for (decl in file.declarations) {
                when (decl) {
                    is KtClass -> fromClass(decl, module)
                    is KtFun -> fromFun(decl, module)
                    else -> {}
                }
            }
            return module
        }
        
        private fun fromClass(cls: KtClass, module: IRModule) {
            val ctorFunc = IRFunction("<init>_${cls.name}", emptyList(), cls.name)
            ctorFunc.add(IRComment("class ${cls.name}"))
            ctorFunc.add(IRAlloc("this_${cls.name}", cls.name))
            for (m in cls.members) {
                when (m) {
                    is KtVal -> {
                        val v = tmp("v")
                        if (m.value != null) {
                            ctorFunc.add(IRLit(v, "...", m.type ?: "Any"))
                            ctorFunc.add(IRStore("${cls.name}.${m.name}", v))
                        }
                    }
                    is KtFun -> fromFun(m, module)
                    else -> {}
                }
            }
            module.addFunction(ctorFunc)
        }
        
        private fun fromFun(fn: KtFun, module: IRModule) {
            val func = IRFunction(
                fn.name,
                fn.params.map { it.name to (it.type ?: "Any") },
                fn.returnType ?: "Unit"
            )
            func.add(IRComment("fun ${fn.name}"))
            fn.body?.let { body ->
                if (body is KtBlock) {
                    for (s in body.statements) fromStmt(s, func)
                } else {
                    fromStmt(body, func)
                }
            }
            module.addFunction(func)
        }
        
        private fun fromStmt(node: KtNode, func: IRFunction) {
            when (node) {
                is KtReturn -> {
                    val v = node.value?.let { exprToIR(it, func) }
                    func.add(IRReturn(v))
                }
                is KtCall -> {
                    val calleeName = when (val t = node.target) {
                        is KtRef -> t.name
                        is KtMemberAccess -> t.member
                        else -> "?"
                    }
                    val dest = tmp("call")
                    func.add(IRCall(dest, calleeName, node.args.map { exprToIR(it, func) }))
                }
                is KtVal -> {
                    val v = exprToIR(node.value ?: return, func)
                    func.add(IRStore(node.name, v))
                }
                is KtIf -> {
                    val condVar = exprToIR(node.cond, func)
                    val trueLbl = "L${tmpIdx}_true"
                    val falseLbl = "L${tmpIdx}_false"
                    val endLbl = "L${tmpIdx}_end"
                    func.add(IRCondJump(condVar, trueLbl, falseLbl))
                    func.add(IRLabel(trueLbl))
                    node.thenBranch?.let { fromStmt(it, func) }
                    func.add(IRJump(endLbl))
                    func.add(IRLabel(falseLbl))
                    node.elseBranch?.let { fromStmt(it, func) }
                    func.add(IRLabel(endLbl))
                }
                is KtBlock -> {
                    for (s in node.statements) fromStmt(s, func)
                }
                is KtBinary -> {
                    val dest = tmp("bin")
                    val l = exprToIR(node.left, func)
                    val r = exprToIR(node.right, func)
                    func.add(IRBinary(dest, node.op, l, r))
                }
                else -> func.add(IRComment("stmt: ${node.javaClass.simpleName}"))
            }
        }
        
        private fun exprToIR(expr: KtExpr, func: IRFunction): String {
            return when (expr) {
                is KtLitInt -> { val d = tmp("lit"); func.add(IRLit(d, expr.value.toString(), "Int")); d }
                is KtLitStr -> { val d = tmp("str"); func.add(IRLit(d, "\"${expr.value}\"", "String")); d }
                is KtRef -> expr.name
                is KtCall -> { val d = tmp("call")
                    val cn = when (val t = expr.target) { is KtRef -> t.name; is KtMemberAccess -> t.member; else -> "?" }
                    func.add(IRCall(d, cn, expr.args.map { exprToIR(it, func) })); d }
                is KtBinary -> { val d = tmp("bin"); val l = exprToIR(expr.left, func); val r = exprToIR(expr.right, func); func.add(IRBinary(d, expr.op, l, r)); d }
                is KtMemberAccess -> { val d = tmp("mem"); func.add(IRFieldAccess(d, "obj", expr.member)); d }
                else -> "?"
            }
        }
    }
    
    private fun extractAnnotationTasks(file: KtFile): List<com.qitong.head.process.AnnotationTask> {
        val tasks = mutableListOf<com.qitong.head.process.AnnotationTask>()
        fun collect(anns: List<KtAnnotation>, loc: String) {
            for (ann in anns) {
                val tag = when {
                    ann.name in listOf("Dao", "Query", "Insert", "Update", "Delete", "Entity", "PrimaryKey", "ColumnInfo") -> "crud-generator"
                    ann.name in listOf("Serializable", "JsonIgnore", "JsonProperty") -> "serializer"
                    ann.name in listOf("Inject", "Provides", "Module", "Component", "Scope") -> "di"
                    else -> "general"
                }
                tasks.add(com.qitong.head.process.AnnotationTask(
                    annotationName = ann.name,
                    tag = tag,
                    location = loc,
                    payload = ann.args.joinToString(", "),
                    isHealthy = true
                ))
            }
        }
        // 遍历顶层声明中的注解
        for (decl in file.declarations) {
            val loc = "${file.pkg ?: ""}:${decl.javaClass.simpleName}"
            when (decl) {
                is KtClass -> collect(decl.annotations, loc)
                is KtFun -> collect(decl.annotations, loc)
                is KtVal -> collect(decl.annotations, loc)
                is KtObject -> {}
                is KtInterface -> {}
                is KtEnum -> {}
            }
        }
        return tasks
    }

    // ─── 状态恢复 ───
    private fun restoreSession() {
        val data = dev.read("session") ?: return
        val json = String(data, kotlin.text.Charsets.UTF_8)
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
            hPrintln("\n".repeat(2))
            renderPage()

            hPrint("\n> ")
            val input = readLine() ?: break
            if (input.isBlank()) continue

            val cmd = input.trim().toLowerCase()
            if (cmd == "q" || cmd == "quit" || cmd == "exit") {
                hPrintln("再见 👋")
                return
            }
            handle(cmd)
        }
    }

    // v0.11.7: 多项目模式开关
    private var multiProjectEnabled = false
    private var lastMultiReports: List<MultiProjectCoordinator.ProjectReport> = emptyList()
    private var lastPackReport: ApkPackCoordinator.PackReport? = null

    // ─── 按钮渲染 ───
    private fun renderPage() {
        when (page) {
            "main" -> renderMain()
            "ast" -> renderAst()
            "diag" -> renderDiag()
            "sim" -> renderSim()
            "admin" -> renderAdmin()
            "bugs" -> renderBugs()
            "roadmap" -> renderRoadmap()
            "decomp" -> renderDecomp()
            "process" -> renderProcess()
            "eventbus" -> renderEventBus()
            "dev" -> renderDev()
            "multiproj" -> renderMultiProject()
            "pack" -> renderPackPage()
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
                SimProfile(m["name"] as? String ?: "?", m["file"] as? String ?: "", (m["expectedMinDiags"] as? Int) ?: 0)
            }
        } catch (_: Exception) { emptyList<SimProfile>() }
    }
    data class SimProfile(val name: String, val file: String, val expectedMinDiags: Int = 0)

    private fun renderSim() {
        hPrintln("═══ 模拟运行 ═══")
        hPrintln("  选一个配置，自动跑编译+诊断")
        hPrintln()
        simProfiles.forEachIndexed { i, p ->
            hPrintln("  [${i + 1}] ${p.name}")
        }
        hPrintln("  [0] 返回主页")
    }

    private fun handleSim(key: String) {
        if (key == "0") { page = "main"; return }
        val idx = key.toIntOrNull()?.minus(1) ?: return
        val profile = simProfiles.getOrNull(idx) ?: return
        hPrintln("  模拟: ${profile.name}")
        if (profile.file.isEmpty()) {
            compile("")
        } else {
            val f = File(profile.file)
            if (!f.exists()) { hPrintln("  ✖ 文件不存在: ${profile.file}"); return }
            lastSrcPath = profile.file
            lastSrc = f.readText()
            compile(lastSrc)
        }
        val totalDiags = lastDiags.size + lastFindings.size
        hPrintln("  完成 ✓  诊断: $totalDiags 条 (Parser+TypeChecker: ${lastDiags.size}, BugScanner: ${lastFindings.size}, BugDB: $lastBugdbHits)")
        if (profile.expectedMinDiags > 0) {
            if (totalDiags >= profile.expectedMinDiags) hPrintln("  ✅ 诊断数达标 (≥${profile.expectedMinDiags})")
            else hPrintln("  ⚠️ 诊断数未达标 (${totalDiags}/${profile.expectedMinDiags})")
        }
        hPrintln("  [1] 查看AST  [2] 查看诊断  [0] 返回")
        val input = readLine() ?: ""
        when (input.trim()) {
            "1" -> page = "ast"
            "2" -> page = "diag"
            else -> page = "main"
        }
    }
    private fun renderMain() {
        hPrintln("═══ 主页 ═══  ${lastSrcPath.takeLast(30)}")
        hPrintln()
        hPrintln("  [1] 查看 AST 树")
        hPrintln("  [2] 查看诊断 (${diagSummary()})")
        hPrintln("  [3] 重新编译")
        hPrintln("  [4] 模拟运行")
        hPrintln("  [5] 管理员")
        hPrintln("  [6] Bug 扫描 (BugDB:$lastBugdbHits, Scanner:${lastFindings.size})")
        hPrintln("  [7] 能力路线图")
        hPrintln("  [8] 反编译管线")
        hPrintln("  [9] 进程树 (${lastProcessReports.size}个领域)")
        hPrintln("  [0] EventBus 状态")
        if (multiProjectEnabled) hPrintln("  [10] 多项目测试")
        hPrintln("  [11] APK打包")
        hPrintln("  [q] 退出")
    }

    private fun renderAst() {
        hPrintln("═══ AST 视图 ═══")
        hPrintln()
        if (lastFile == null) {
            hPrintln("  (无 AST —— 编译失败)")
        } else {
            hPrintln(formatAst(lastFile!!))
        }
        hPrintln()
        hPrintln("  [1] 返回主页")
    }

    private fun renderDiag() {
        hPrintln(Diagnostic.from(lastDiags, lastFindings).format(lastFile?.declarations?.size ?: 0))
        hPrintln("  [1] 返回主页")
    }

    private fun renderAdmin() {
        hPrintln("═══ 管理员 ═══")
        hPrintln()
        hPrintln("  ~/.kotlin-head/  ← 数据在这，随便看随便改")
        hPrintln("  CRC 校验保护完整性，改坏自动恢复默认值")
        hPrintln()
        hPrintln("  [1] 返回主页")
        hPrintln("  [2] 清除所有缓存")
        hPrintln("  [3] 开发者功能")
    }

    // ─── 新页面 v0.6.1 ───
    private fun renderBugs() {
        hPrintln("═══ Bug 扫描 ═══")
        hPrintln()
        hPrintln("  ── BugDB 5000条规则库 ──")
        hPrintln("  命中: $lastBugdbHits 条 (🔴严重:$lastBugdbSevere 🟡中度:$lastBugdbModerate ⚪轻度:$lastBugdbMild)")
        hPrintln()
        if (lastFindings.isEmpty() && lastBugdbHits == 0) {
            hPrintln("  ✓ 未发现已知 Bug 模式")
        } else {
            if (lastFindings.isNotEmpty()) {
                hPrintln("  ── BugScanner AST分析 ──")
                for (f in lastFindings.take(5)) {
                    val icon = when (f.severity) {
                        BugScanner.Severity.HIGH -> "🔴"
                        BugScanner.Severity.MEDIUM -> "🟡"
                        BugScanner.Severity.LOW -> "⚪"
                    }
                    hPrintln("  $icon ${f.message} | ${f.span}")
                }
                if (lastFindings.size > 5) hPrintln("  ... 还有 ${lastFindings.size - 5} 条")
            }
        }
        hPrintln()
        hPrintln("  Kotlin 叠加漏洞（T!×结构化并发）：kotlin-head 零依赖天然免疫")
        hPrintln()
        hPrintln("  [1] 返回主页")
    }

    private fun renderRoadmap() {
        hPrintln("═══ 能力路线图 ═══")
        hPrintln()
        hPrintln("  当前: v$VERSION — Stage Contract + 反编译管线")
        hPrintln()
        hPrintln("  v0.1.0 ✅ data class / fun / val / if / 字面量")
        hPrintln("  v0.2.0 ✅ 三级诊断 + BugScanner + 容错跳过")
        hPrintln("  v0.3.0 ✅ HED/TDL 双格式落地")
        hPrintln("  v0.5.1 ✅ class正解析+继承+LT/GT归位+尾部lambda")
        hPrintln("  v0.5.2 ✅ <T>/by/= 三刀语义修复 + 看位置不分类")
        hPrintln("  v0.5.3 ✅ T!×结构化并发叠加漏洞发现")
        hPrintln("  v0.6.0 ✅ Stage Contract + 反编译管线 + 綦桐3.4.4-9分析")
        hPrintln("  v0.7.0 ✅ 綦桐 32/32 全通过（三刀架构验证）")
        hPrintln("  v0.8.0 ✅ 四层进程树 + 注解领域划分 + 三层容错")
        hPrintln("  v0.8.1 ✅ EventBus三种通道 + 异步I/O + 依赖图解析")
        hPrintln("  v0.8.2 ✅ IR中间表示 + Pass优化管线 + 动态专业集数路由")
        hPrintln("  v0.8.3 ✅ AsyncIO归指挥官 + 依赖图staging + import接通")
        hPrintln("  v0.8.5 ✅ 四种指挥官 + 五种检测进程 + 子进程五职业 + 依赖图被动联动")
        hPrintln("  v0.9.0 ✅ LiveDeclarationGraph —— 第三种混合编译（声明级活图 + 浅提取 + 被动传播）")
        hPrintln("  v0.9.1 ✅ 检测进程五风格全挂载 + BugScanner冷门bug标准库(12条)")
        hPrintln("  v0.11.1 ✅ 链式?.修复 + ELVIS优先级校准 + 军师接管 + HMap定量动态并发")
        hPrintln("  v0.11.2 ✅ 八种指挥官(元帅/尖刀/闪电/常规+4) + 七种检测性格(专业/慵懒+5) + 十一种子进程职业 + ProcessTendency倾向系统")
        hPrintln("  v0.11.3 ✅ 父进程治理风格(联邦/独裁/紧急/保守/契约/枭雄/仁勇/正常) + 检测性格+标准基线")
        hPrintln("  v1.0.0   能力全面超越 javac/Node.js 官方工具链")
        hPrintln()
        hPrintln("  [1] 返回主页")
    }

    private fun renderDecomp() {
        hPrintln("═══ 反编译管线 ═══")
        hPrintln()
        hPrintln("  APK → dex → jadx → Kt源码 → kotlin-head AST → 语义复原")
        hPrintln()
        hPrintln("  核心洞察（免免原创）：")
        hPrintln("  编译器为编译必须看懂源码 → AST已暴露所有结构")
        hPrintln("  → 语义复原 = 把AST已算出的类型/调用链翻译回源码")
        hPrintln()
        hPrintln("  接缝A: jadx吐出非标准Kotlin → 优雅降级，不炸管道")
        hPrintln("  接缝B: 推断链碰未知外部库 → 标<?>，不装懂")
        hPrintln()
        hPrintln("  已在綦桐3.4.4-9 APK验证：apk_reverse + jadx + strings")
        hPrintln()
        hPrintln("  [1] 返回主页")
    }

    // ─── v0.8.5 进程树页面（树状展示）───
    private fun renderProcess() {
        hPrintln("═══ 进程树（v0.11.3 八种父进程 · 八种指挥官 · 八种检测性格 · 十一种子进程职业） ═══")
        hPrintln()
        if (lastProcessReports.isEmpty()) {
            hPrintln("  当前源码未检测到注解，或编译未完成")
            hPrintln("  （注解处理器按标签自动分组：crud-generator / serializer / di / general）")
        } else {
            // 树状展示：主进程 → 指挥官 → 子进程 → 进程体 + 检测进程
            hPrintln("  kotlin-head v$VERSION  (主进程)")
            val cmds = lastProcessReports.entries.toList()
            cmds.forEachIndexed { ci, (tag, report) ->
                val isLast = ci == cmds.lastIndex
                val branch = if (isLast) "└──" else "├──"
                val indent = if (isLast) "    " else "│   "
                
                val tendIcon = if (report.tendencyLabel.isNotEmpty()) " ⚡" else ""
                val bugIcon = if (report.summary.contains("✖")) " " else ""
                val watchTag = if (report.watchReports.isNotEmpty()) " · " else ""
                hPrintln("  $branch 指挥官[$tag] · ${report.commanderTypeLabel} · ${report.modeLabel}$tendIcon$watchTag$bugIcon")
                hPrintln("  $indent│ 完成: ${report.completedCount}/${report.totalCount}  ${report.summary}")
                
                // 子进程层 + 职业展示
                val successes = report.results.count { it is com.qitong.head.process.ProcessResult.Success }
                val partials = report.results.count { it is com.qitong.head.process.ProcessResult.PartialSuccess }
                val failures = report.results.count { it is com.qitong.head.process.ProcessResult.Failure }
                val occStr = if (report.occupationLabels.isNotEmpty()) " [${report.occupationLabels.joinToString("/")}]" else ""
                hPrintln("  $indent├── 子进程 ×${report.subProcessCount}$occStr")
                hPrintln("  $indent│   └── 进程体 梯次: ✓$successes ◐$partials ✖$failures")
                
                // 检测进程层（旁路）
                if (report.watchReports.isNotEmpty()) {
                    report.watchReports.forEachIndexed { wi, wr ->
                        val wBranch = if (wi == report.watchReports.lastIndex) "└──" else "├──"
                        val wIndent = "$indent$wBranch"
                        val susIcon = when {
                            wr.suspicionLevel > 0.7f -> "🔴"
                            wr.suspicionLevel > 0.3f -> "🟡"
                            wr.suspicionLevel > 0f -> "⚪"
                            else -> "✓"
                        }
                        hPrintln("  $indent$wBranch 检测进程 [$susIcon] 疑点:${(wr.suspicionLevel * 100).toInt()}%")
                        if (wr.anomalies.isNotEmpty()) {
                            wr.anomalies.take(2).forEach { a ->
                                hPrintln("  $wIndent    · $a")
                            }
                            if (wr.anomalies.size > 2) hPrintln("  $wIndent    · …还有 ${wr.anomalies.size - 2} 条")
                        }
                        hPrintln("  $wIndent    建议: ${wr.recommendation}")
                    }
                } else {
                    hPrintln("  $indent└── 检测进程 (未挂载)")
                }
                hPrintln()
            }
            hPrintln("  ─── 架构特性 ───")
            hPrintln("  五层: 父进程(8种治理风格) → 指挥官(8种) → 子进程(11种职业) → 进程体")
            hPrintln("  旁路: 检测进程(8种性格) · 观察不拦截 · 与数据通道隔离")
            hPrintln("  倾向: ProcessTendency(速攻) · 闪电指挥官临时赋予 · 正交于职业")
            hPrintln("  联动: 依赖图 → \"dep_ready\" → 指挥官被动响应")
            hPrintln("  隔离: 领域间零耦合 · 反向排除近邻但标签不同者")
            hPrintln("  传递: 复制性引用 · Git clone不是mount")
            hPrintln("  协同: 分片/流水线/竞合/侦查/收集 · 指挥官按类型自动选")
        }
        hPrintln()
        hPrintln("  [1] 返回主页")
    }

    // ─── v0.8.2 EventBus 状态页 ───
    private fun renderEventBus() {
        hPrintln("═══ EventBus 状态（v0.8.2 动态+专业集数路由） ═══")
        hPrintln()
        hPrintln("  事件循环: ${if (eventBusInit) "✓ 运行中" else "✖ 未初始化"}")
        hPrintln()
        // 事件通道
        hPrintln("  ── 事件通道（EventEmitter · 一发多收）──")
        listOf("lex" to "Lex完成", "parse" to "Parse完成", "typecheck" to "类型检查", 
               "ir" to "IR生成", "error" to "错误中心", "dep" to "依赖图", 
               "file" to "文件就绪", "process" to "进程树").forEach { (ch, desc) ->
            val count = try { EventBus.eventChannel(ch).subscriberCount() } catch (_: Exception) { 0 }
            hPrintln("  ${if (count > 0) "●" else "○"} $ch ($desc) · ${count}人订阅")
        }
        hPrintln()
        // 流式通道
        hPrintln("  ── 流式通道（Stream/pipe · 链式串联）──")
        val passCount = try { EventBus.streamChannel<IRModule>("ir-pass").pipeCount() } catch (_: Exception) { 0 }
        hPrintln("  ● ir-pass · ${passCount}个Pass串联: 死代码消除 → 常量折叠 → 内联展开")
        hPrintln()
        // 工作通道
        hPrintln("  ── 工作通道（Worker Pool · 任务池分发）──")
        hPrintln("  ● worker · 4线程池（CPU密集型: 多文件TypeCheck / 注解处理）")
        hPrintln()
        // IR 状态
        if (lastIR != null) {
            hPrintln("  ── 当前 IR 模块 ──")
            hPrintln("  名称: ${lastIR!!.name}")
            hPrintln("  函数: ${lastIR!!.functionCount()}个")
            hPrintln("  指令: ${lastIR!!.totalInstructions()}条")
            lastIR!!.metadata.forEach { (k, v) -> hPrintln("  $k: $v") }
        } else {
            hPrintln("  ── 当前 IR 模块 ──")
            hPrintln("  (未编译或编译失败)")
        }
        hPrintln()
        // 动态+专业集数路由说明
        hPrintln("  ── 路由策略 ──")
        hPrintln("  任务进来 → 匹配通道类型 → 通道自带专业路由处理")
        hPrintln("  事件通道: 广播式 | 流式通道: 链式串联 | 工作通道: 任务池")
        hPrintln("  不是通用路由表——每条路自带专业调度逻辑，不交叉不混合")
        hPrintln()
        hPrintln("  [1] 返回主页")
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
            "bug扫描", "bug", "bugs", "扫描" -> "6"
            "能力路线图", "路线图", "roadmap", "版本" -> "7"
            "反编译管线", "反编译", "decomp", "decompilation" -> "8"
            else -> input
        }

        when (page) {
            "main" -> handleMain(byLabel)
            "ast" -> handleAst(byLabel)
            "diag" -> handleDiag(byLabel)
            "sim" -> handleSim(byLabel)
            "admin" -> handleAdmin(byLabel)
            "dev" -> handleDev(byLabel)
            "multiproj" -> handleMultiProject(byLabel)
            "pack" -> handlePack(byLabel)
            "bugs" -> if (byLabel == "1") page = "main"
            "roadmap" -> if (byLabel == "1") page = "main"
            "decomp" -> if (byLabel == "1") page = "main"
            "process" -> if (byLabel == "1") page = "main"
            "eventbus" -> if (byLabel == "1") page = "main"
        }
        saveSession()
    }

    private fun handleMain(key: String) {
        when (key) {
            "1" -> page = "ast"
            "2" -> page = "diag"
            "3" -> {
                hPrintln("  重新编译中...")
                lastSrc = File(lastSrcPath).readText()
                compile(lastSrc)
                hPrintln("  完成 ✓")
            }
            "4" -> page = "sim"
            "5" -> page = "admin"
            "6" -> page = "bugs"
            "7" -> page = "roadmap"
            "8" -> page = "decomp"
            "9" -> page = "process"
            "0" -> page = "eventbus"
            "10" -> if (multiProjectEnabled) page = "multiproj"
            "11" -> page = "pack"
            else -> hPrintln("  ? 未知按钮: $key")
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
                hPrintln("  缓存已清除 ✓")
            }
            "3" -> page = "dev"
            else -> hPrintln("  ? 未知按钮: $key")
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
                val mods = if (node.modifiers.isNotEmpty()) "(${node.modifiers.joinToString()}) " else ""
                val params = node.params.joinToString(", ") { "${it.name}:${it.type ?: "?"}" }
                val ret = node.returnType ?: "?"
                sb.append("${pad}${mods}fun ${node.name}($params): $ret")
                if (node.body != null) sb.append(" { ... }")
                sb.append("\n")
            }
            is KtVal -> {
                val mods = if (node.modifiers.isNotEmpty()) "(${node.modifiers.joinToString()}) " else ""
                val t = node.type ?: "?"
                sb.append("${pad}${mods}val ${node.name}: $t")
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
            is KtMemberAccess -> sb.append("${pad}${node.member}\n")
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

    // ─── v0.11.7 开发者功能 ───
    private fun renderDev() {
        hPrintln("═══ 开发者功能 ═══")
        hPrintln()
        hPrintln("  多项目测试模式: ${if (multiProjectEnabled) "✓ 已开启" else "○ 关闭"}")
        hPrintln("  Java 检测模块: ${if (ProcessCoordinator.javaDetectionEnabled) "✓ 已挂载" else "○ 关闭"}")
        hPrintln()
        hPrintln("  [1] 返回管理员")
        hPrintln("  [2] ${if (multiProjectEnabled) "关闭" else "开启"}多项目测试模式")
        hPrintln("  [7] ${if (ProcessCoordinator.javaDetectionEnabled) "关闭" else "启用"} Java 检测")
        if (multiProjectEnabled) {
            hPrintln()
            hPrintln("  ── 军队规模 ──")
            val scales = MultiProjectCoordinator.getScales()
            val act = MultiProjectCoordinator.getActiveScaleIndex()
            scales.forEachIndexed { i, s ->
                hPrintln("  ${if (i == act) "●" else "○"} ${s.name} (${s.multiplier}x)")
            }
            hPrintln()
            hPrintln("  [3] 切换规模  [4] 新建  [5] 删除  [6] 恢复默认")
        }
    }

    private fun handleDev(key: String) {
        when (key) {
            "1" -> page = "admin"
            "2" -> {
                multiProjectEnabled = !multiProjectEnabled
                ProcessCoordinator.multiProjectMode = multiProjectEnabled
                hPrintln("  多项目测试模式: ${if (multiProjectEnabled) "✓ 已开启" else "○ 关闭"}")
            }
            "3" -> {
                if (!multiProjectEnabled) return
                val scales = MultiProjectCoordinator.getScales()
                hPrintln()
                hPrintln("  ── 切换规模 ──")
                scales.forEachIndexed { i, s ->
                    hPrintln("  [${i + 1}] ${s.name} (${s.multiplier}x)${if (i == MultiProjectCoordinator.getActiveScaleIndex()) " ←当前" else ""}")
                }
                hPrintln("  输入数字：")
                val inp = readLine() ?: return
                val idx = inp.trim().toIntOrNull()?.minus(1) ?: return
                if (idx >= 0 && idx < scales.size) {
                    MultiProjectCoordinator.setActiveScale(idx, dev::store)
                    hPrintln("  ✓ 已切换到: ${scales[idx].name}")
                }
            }
            "4" -> {
                if (!multiProjectEnabled) return
                hPrintln()
                hPrintln("  ── 新建规模 ──")
                hPrintln("  倍率范围: 0.5~10")
                hPrintln("  格式: 名称:倍率 (例: 自定义:3)")
                val inp = readLine() ?: return
                val parts = inp.trim().split(":")
                if (parts.size < 2) { hPrintln("  ✖ 格式错误"); return }
                val name = parts[0].trim()
                val mult = parts[1].trim().toFloatOrNull()
                if (mult == null) { hPrintln("  ✖ 倍率无效"); return }
                if (MultiProjectCoordinator.addScale(name, mult, dev::store)) {
                    hPrintln("  ✓ 已创建: $name (${mult}x)")
                } else {
                    hPrintln("  ✖ 创建失败: 倍率需在0.5~10，名称不能为空")
                }
            }
            "5" -> {
                if (!multiProjectEnabled) return
                val scales = MultiProjectCoordinator.getScales()
                if (scales.size <= 1) { hPrintln("  ✖ 至少保留一个规模"); return }
                hPrintln()
                hPrintln("  ── 删除规模 ──")
                scales.forEachIndexed { i, s ->
                    hPrintln("  [${i + 1}] ${s.name} (${s.multiplier}x)")
                }
                hPrintln("  输入数字删除：")
                val inp = readLine() ?: return
                val idx = inp.trim().toIntOrNull()?.minus(1) ?: return
                if (MultiProjectCoordinator.removeScale(idx, dev::store)) {
                    hPrintln("  ✓ 已删除")
                } else {
                    hPrintln("  ✖ 删除失败: 至少保留一个")
                }
            }
            "6" -> {
                if (!multiProjectEnabled) return
                MultiProjectCoordinator.resetToDefaults(dev::store)
                hPrintln("  ✓ 已恢复默认四档（低/中/高/极高）")
            }
            "7" -> {
                if (ProcessCoordinator.javaDetectionEnabled) {
                    ProcessCoordinator.disableJavaDetection()
                    hPrintln("  Java 检测: ○ 已关闭")
                } else {
                    val ok = ProcessCoordinator.enableJavaDetection()
                    if (ok) {
                        hPrintln("  Java 检测: ✓ 已挂载 java-head")
                    } else {
                        hPrintln("  ✖ 未找到 java-head.jar，请将 java-head.jar 放入 classpath")
                    }
                }
            }
            else -> page = "admin"
        }
    }

    private fun renderMultiProject() {
        hPrintln("═══ 多项目测试 ═══")
        hPrintln("  军队: ●${MultiProjectCoordinator.getActiveScale().name} (${MultiProjectCoordinator.currentMultiplier}x)  — 在开发者功能切换")
        hPrintln("  绑定规则: 添加时自动绑定当前军队，切军队后新添的跟新军队")
        hPrintln()
        if (lastMultiReports.isNotEmpty()) {
            hPrint(MultiProjectCoordinator.formatSummary(lastMultiReports))
        } else {
            hPrintln("  输入项目路径（用逗号分隔多个）：")
            hPrintln("  例: /path/a.kt,/path/b.kt")
        }
        hPrintln()
        hPrintln("  [1] 返回主页")
        hPrintln("  [2] 输入路径并运行")
    }

    private fun handleMultiProject(key: String) {
        when (key) {
            "1" -> page = "main"
            "2" -> {
                hPrintln()
                hPrintln("  输入项目路径（逗号分隔）：")
                val inp = readLine() ?: return
                val paths = inp.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                if (paths.isEmpty()) { hPrintln("  ✖ 未输入路径"); return }
                val projects = paths.mapIndexed { i, p ->
                    val f = File(p)
                    val name = f.name.ifEmpty { "项目${i + 1}" }
                    val size = if (f.exists()) { val len = f.length(); if (len > Int.MAX_VALUE) Int.MAX_VALUE else len.toInt() } else 0
                    MultiProjectCoordinator.ProjectTask(name, p, size)
                }
                hPrintln("  跑 ${projects.size} 个项目...")
                lastMultiReports = MultiProjectCoordinator.runBatch(projects)
                hPrintln()
                hPrint(MultiProjectCoordinator.formatSummary(lastMultiReports))
            }
            else -> page = "main"
        }
    }

    // ─── v0.11.8: APK 打包 ───

    private fun renderPackPage() {
        hPrintln("═══ APK 打包 ═══")
        hPrintln()
        val tools = ApkPackCoordinator.checkTools()
        val allAvailable = tools.allAvailable()
        hPrintln("  工具检测: ${if (allAvailable) "✓ 全部可用" else "✗ 缺少: ${tools.missing().joinToString()}"}")
        hPrintln()
        if (lastPackReport != null) {
            hPrint(ApkPackCoordinator.formatReport(lastPackReport!!))
        } else {
            hPrintln("  输入 Android 项目路径即可打包")
            hPrintln("  例: /path/to/android-project")
        }
        hPrintln()
        hPrintln("  [1] 返回主页")
        hPrintln("  [2] 输入项目路径并打包")
        hPrintln("  [3] 检查工具链")
        hPrintln("  [4] 仅分析（不打）")
    }

    private fun handlePack(key: String) {
        when (key) {
            "1" -> page = "main"
            "2" -> {
                if (!ApkPackCoordinator.checkTools().allAvailable()) {
                    hPrintln("  ✖ 工具链不完整，无法打包")
                    hPrintln("  缺少: ${ApkPackCoordinator.checkTools().missing().joinToString()}")
                    return
                }
                hPrintln()
                hPrintln("  输入 Android 项目路径：")
                val inp = readLine() ?: return
                val dir = inp.trim()
                if (dir.isEmpty() || !File(dir).exists()) {
                    hPrintln("  ✖ 目录不存在: $dir")
                    return
                }
                hPrintln("  打包中...")
                val previous = lastPackReport?.passed ?: false
                val report = ApkPackCoordinator.pack(dir, previousArtifacts = previous)
                lastPackReport = report
                hPrintln()
                hPrint(ApkPackCoordinator.formatReport(report))
            }
            "3" -> {
                val tools = ApkPackCoordinator.checkTools()
                hPrintln("═══ 工具链检查 ═══")
                hPrintln("  aapt2:     ${if (tools.aapt2) "✓" else "✗"}")
                hPrintln("  kotlinc:   ${if (tools.kotlinc) "✓" else "✗"}")
                hPrintln("  d8:        ${if (tools.d8) "✓" else "✗"}")
                hPrintln("  zipalign:  ${if (tools.zipalign) "✓" else "✗"}")
                hPrintln("  apksigner: ${if (tools.apksigner) "✓" else "✗"}")
                hPrintln("  全部可用: ${if (tools.allAvailable()) "✓" else "✗ 缺少: ${tools.missing().joinToString()}"}")
            }
            "4" -> {
                hPrintln()
                hPrintln("  输入 Android 项目路径：")
                val inp = readLine() ?: return
                val dir = inp.trim()
                if (dir.isEmpty() || !File(dir).exists()) {
                    hPrintln("  ✖ 目录不存在: $dir")
                    return
                }
                val diff = ApkPackCoordinator.analyzeDiff(dir)
                hPrintln()
                hPrintln("═══ 军师分析 ═══")
                hPrintln("  原因: ${diff.reason}")
                hPrintln("  需要跑: ${diff.neededSteps.joinToString { it.label }}")
                hPrintln("  可跳过: ${diff.skippedSteps.joinToString { it.label }}")
            }
            else -> page = "main"
        }
    }
}