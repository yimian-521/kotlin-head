package com.qitong.head.simui

import com.qitong.head.ast.*

/**
 * v0.12.8: 混合扫描器 — 探针 + 变量追踪 + 决策门。
 * 
 * 三层策略（继承 v0.12.7 混合输出决策门）：
 *   阶段1：探针精确命中 Button/onClick → 不启动变量追踪
 *   阶段2：探针未命中 → 并发：全量 KtCall + LiveDeclarationGraph 变量赋值追踪
 *   阶段3：都未命中 → 诚实声明"遇到非标准交互，请手动标注"
 * 
 * 核心理念（免免原创）：
 *   - Button 不是唯一交互标记——变量赋值才是。
 *   - 找不到探针就找 `=`, 谁改了状态变量谁就是交互源。
 */

object SimUiScanner {

    /** 扫描结果 */
    data class ScanResult(
        val buttons: List<UiInteraction>,       // 阶段1：标准按钮
        val varTriggers: List<VarInteraction>,   // 阶段2：变量驱动的交互
        val unknowns: List<KtCall>,             // 阶段3：无法识别
        val source: String                       // 扫描来源描述
    )

    /** 标准 UI 交互（Button/onClick/Composable） */
    data class UiInteraction(
        val id: String,            // 免免原则2：探针用id不用名字 — "Button@L10:5"
        val label: String,
        val line: Int,
        val actionHint: String,     // onClick 的目标推测
        val varDependencies: List<String>,  // 依赖的状态变量
        val confidence: Double      // 0..1，探针命中=1.0，变量推测=0.6
    )

    /** 原生探针节点 — 免免原则1：生下来就完整，轻量+全能 */
    enum class MarkerKind { BUTTON, CLICKABLE, VAR_ASSIGN, UNKNOWN }

    private data class SimMarker(
        val id: String,              // "Button@L10:5"
        val kind: MarkerKind,        // 探针类型
        val label: String,           // 提取的标签
        val actionHint: String,      // onClick 目标推测
        val deps: List<String>,      // 依赖变量
        val confidence: Double,      // 0..1
        val sourceNode: KtNode       // 原始AST引用（仅溯源，不依赖其字段）
    )

    /** 变量驱动的交互（赋值即交互） */
    data class VarInteraction(
        val varName: String,
        val assignedValue: String,
        val line: Int,
        val inContext: String,      // 所在函数/Composable
        val confidence: Double
    )

    /**
     * 决策门——决定用哪个扫描策略。
     * 与 v0.12.7 route() 同构。
     */
    fun scan(file: KtFile, preferredStrategy: String = "auto"): ScanResult {
        return when (preferredStrategy) {
            "probe"  -> probeScan(file)       // 只用探针
            "var"    -> variableScan(file)     // 只用变量追踪
            "auto"   -> hybridScan(file)       // 混合输出
            else     -> hybridScan(file)
        }
    }

    // ═════════════════════════════════════════
    //  阶段1：探针扫描
    // ═════════════════════════════════════════

private fun probeScan(file: KtFile): ScanResult {
        val buttons = mutableListOf<UiInteraction>()
        val remains = mutableListOf<KtCall>()
        walk(file.declarations, { node ->
            when (node) {
                is KtCall -> {
                    // 免免原则1+2：手搓原生探针节点 — 名字初筛→lambda验证→自描述标记
                    val marker = tryMarkButton(node)
                    if (marker != null) {
                        buttons.add(UiInteraction(
                            id = marker.id,
                            label = marker.label,
                            line = node.span.start.line,
                            actionHint = marker.actionHint,
                            varDependencies = marker.deps,
                            confidence = marker.confidence
                        ))
                    } else {
                        remains.add(node)
                    }
                }
                else -> {}
            }
        })
        return ScanResult(
            buttons = buttons,
            varTriggers = emptyList(),
            unknowns = if (buttons.isEmpty()) remains else emptyList(),
            source = "探针扫描"
        )
    }

    // ═════════════════════════════════════════
    //  阶段2：变量追踪
    // ═════════════════════════════════════════

    private fun variableScan(file: KtFile): ScanResult {
        val triggers = mutableListOf<VarInteraction>()
        
        // 收集所有 var 声明
        val stateVars = mutableSetOf<String>()
        walk(file.declarations, { node ->
            if (node is KtVal && "var" in node.modifiers) {
                stateVars.add(node.name)
            }
        }

        // 找到所有对这些变量的赋值（含复合赋值：= += -= *= /= %=）
        walk(file.declarations, { node ->
            when (node) {
                is KtBinary -> {
                    val leftName = when (val left = node.left) {
                        is KtRef -> left.name
                        is KtMemberAccess -> left.member
                        else -> ""
                    }
                    // 免免：支配域不够——"="只覆盖了简单赋值，漏了 += -= 等
                    if (leftName in stateVars && node.op in setOf("=", "+=", "-=", "*=", "/=", "%=")) {
                        triggers.add(VarInteraction(
                            varName = leftName,
                            assignedValue = exprToString(node.right),
                            line = node.span.start.line,
                            inContext = findContext(node),
                            confidence = 0.6
                        ))
                    }
                }
                else -> {}
            }
        }

        return ScanResult(
            buttons = emptyList(),
            varTriggers = triggers,
            unknowns = emptyList(),
            source = "变量追踪"
        )
    }

    // ═════════════════════════════════════════
    //  阶段3：混合输出
    // ═════════════════════════════════════════

    private fun hybridScan(file: KtFile): ScanResult {
        // 先用探针扫
        val probe = probeScan(file)
        
        // 决策门：探针命中 ≥ 1 个 → 不扩并
        if (probe.buttons.isNotEmpty()) {
            return probe
        }
        
        // 探针未命中 → 并发变量追踪
        val vars = variableScan(file)
        
        // 变量追踪有结果 → 返回
        if (vars.varTriggers.isNotEmpty()) {
            return vars.copy(source = "混合扫描：探针0命中 → 变量追踪 ${vars.varTriggers.size} 个")
        }
        
        // 都未命中 → 诚实声明
        val unknowns = mutableListOf<KtCall>()
        walk(file.declarations, { node ->
            if (node is KtCall) unknowns.add(node)
        }
        return ScanResult(
            buttons = emptyList(),
            varTriggers = emptyList(),
            unknowns = unknowns,
            source = "混合扫描：0探针 0变量 — 请手动标注交互点"
        )
    }

    // ═════════════════════════════════════════
    //  工具函数
    // ═════════════════════════════════════════

    // 免免原则1+2：原生探针节点 — 名字初筛→lambda验证→自描述标记（轻量+全能）
    private fun tryMarkButton(call: KtCall): SimMarker? {
        val name = when (val t = call.target) {
            is KtRef -> t.name
            is KtMemberAccess -> t.member
            else -> return null
        }
        // 阶段1：名字初筛
        if (name !in BUTTON_CANDIDATES && name !in _externalCandidates) return null
        // 阶段2：lambda验证 — 按钮的核心特征，不是名字
        val hasLambda = call.args.any { it is KtLambda }
        if (!hasLambda) return null
        // 阶段3：构建自描述探针节点 — 一次性提取所有信息，下游不翻AST
        return SimMarker(
            id = "${name}@L${call.span.start.line}",
            kind = MarkerKind.BUTTON,
            label = extractLabel(call),
            actionHint = extractAction(call),
            deps = extractDeps(call),
            confidence = 1.0,
            sourceNode = call
        )
    }

    // 免免原则3：适配层接口 — 外部扩展候选集，不直接改 BUTTON_CANDIDATES
    private val _externalCandidates = mutableSetOf<String>()
    fun registerButton(id: String) { _externalCandidates.add(id) }

    private val BUTTON_CANDIDATES = setOf(
        "Button", "IconButton", "TextButton", "OutlinedButton",
        "FloatingActionButton", "clickable", "onClick"
    )

    private fun extractLabel(call: KtCall): String {
        // 免免：第一个KtLitStr不一定是标签——跳过单字符
        for (arg in call.args) {
            if (arg is KtLitStr && arg.value.length > 1) return arg.value
        }
        return "(未命名按钮)"
    }

    private fun extractAction(call: KtCall): String {
        // 找 lambda 参数（onClick = { ... }）
        // 免免直觉1: if 是不是真的 if — body 可能是单表达式，不一定是 KtBlock
        for (arg in call.args) {
            if (arg is KtLambda) {
                val body = arg.body
                val calls = mutableListOf<KtCall>()
                when (body) {
                    is KtBlock -> calls.addAll(body.statements.filterIsInstance<KtCall>())
                    is KtCall -> calls.add(body)
                    // 单表达式：KtIf/KtBinary/KtWhen 里可能嵌套调用，走 walk 收
                    else -> walk(listOf(body), { if (it is KtCall) calls.add(it) }
                }
                if (calls.isNotEmpty()) {
                    val targets = calls.map {
                        when (val t = it.target) {
                            is KtRef -> t.name
                            is KtMemberAccess -> t.member
                            else -> "?"
                        }
                    }
                    return targets.joinToString(" → ")
                }
            }
        }
        return "(无 lambda)"
    }

    private fun extractDeps(call: KtCall): List<String> {
        val deps = mutableListOf<String>()
        walk(listOf(call), { node ->
            if (node is KtRef) deps.add(node.name)
        }
        return deps.distinct()
    }

    private fun exprToString(expr: KtExpr): String = when (expr) {
        is KtLitStr -> "\"${expr.value}\""
        is KtLitInt -> expr.value.toString()
        is KtRef -> expr.name
        is KtCall -> when (val t = expr.target) {
            is KtRef -> "${t.name}()"
            is KtMemberAccess -> "${t.member}()"
            else -> "call()"
        }
        else -> expr.javaClass.simpleName
    }

    /** 向上找节点所在的函数/类上下文 */
    // 免免：inContext声明时为""且永远是""——声明≠事实
    private fun findContext(node: KtNode): String {
        // 简化版：从当前文件声明中找包含该行号的 KtFun/KtClass
        // 由于没有父指针，此处返回 span 信息作为位置标记
        return "L${node.span.start.line}"
    }

    /** 遍历 AST 节点 */
    // 免免直觉2: 会不会自己掉回自己 — 深度计数器防栈溢出
    // 免免直觉3: 等号是不是该多画几道 — 补全 KtWhen/KtTry/KtFor/KtWhile/KtVal
    private fun walk(nodes: List<KtNode>, visitor: (KtNode) -> Unit, depth: Int = 0) {
        if (depth > 50) return  // 不塞假节点，静默截断
        for (node in nodes) {
            visitor(node)
            when (node) {
                is KtClass     -> walk(node.members, visitor, depth + 1)
                is KtInterface -> walk(node.members, visitor, depth + 1)
                is KtEnum      -> walk(node.members, visitor, depth + 1)
                is KtFun       -> node.body?.let { walk(listOf(it), visitor, depth + 1) }
                is KtVal       -> node.value?.let { walk(listOf(it), visitor, depth + 1) }  // value 可空
                is KtBlock     -> walk(node.statements, visitor, depth + 1)
                is KtIf        -> {
                    node.thenBranch?.let { walk(listOf(it), visitor, depth + 1) }
                    node.elseBranch?.let { walk(listOf(it), visitor, depth + 1) }
                }
                is KtWhen      -> {
                    node.subject?.let { walk(listOf(it), visitor, depth + 1) }
                    node.branches.forEach { b ->
                        walk(listOf(b.condition), visitor, depth + 1)
                        walk(listOf(b.body), visitor, depth + 1)
                    }
                }
                is KtFor       -> {
                    walk(listOf(node.iterable), visitor, depth + 1)  // 免免：iterable 未递归→漏交互源
                    node.body?.let { walk(listOf(it), visitor, depth + 1) }
                }
                is KtWhile  -> {
                    node.condition?.let { walk(listOf(it), visitor, depth + 1) }
                    node.body?.let { walk(listOf(it), visitor, depth + 1) }
                }
                is KtCall   -> walk(node.args, visitor, depth + 1)
                is KtLambda -> walk(listOf(node.body), visitor, depth + 1)
                is KtBinary -> { walk(listOf(node.left, node.right), visitor, depth + 1) }
                else -> {}
            }
        }
    }
}