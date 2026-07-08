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
        val label: String,
        val line: Int,
        val actionHint: String,     // onClick 的目标推测
        val varDependencies: List<String>,  // 依赖的状态变量
        val confidence: Double      // 0..1，探针命中=1.0，变量推测=0.6
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
        walk(file.declarations) { node ->
            when (node) {
                is KtCall -> {
                    val name = when (val t = node.target) {
                        is KtRef -> t.name
                        is KtMemberAccess -> t.member
                        else -> "?"
                    }
                    // 显式探针：Button / IconButton / clickable / onClick
                    if (isButtonCall(name)) {
                        buttons.add(UiInteraction(
                            label = extractLabel(node),
                            line = node.span.start.line,
                            actionHint = extractAction(node),
                            varDependencies = extractDeps(node),
                            confidence = 1.0
                        ))
                    } else {
                        remains.add(node)
                    }
                }
                else -> {}
            }
        }
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
        walk(file.declarations) { node ->
            if (node is KtVal && "var" in node.modifiers) {
                stateVars.add(node.name)
            }
        }

        // 找到所有对这些变量的赋值（= 操作）
        walk(file.declarations) { node ->
            when (node) {
                is KtBinary -> {
                    val leftName = when (val left = node.left) {
                        is KtRef -> left.name
                        is KtMemberAccess -> left.member
                        else -> ""
                    }
                    if (leftName in stateVars && node.op == "=") {
                        triggers.add(VarInteraction(
                            varName = leftName,
                            assignedValue = exprToString(node.right),
                            line = node.span.start.line,
                            inContext = "",
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
        walk(file.declarations) { node ->
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

    private fun isButtonCall(name: String): Boolean =
        name in setOf("Button", "IconButton", "TextButton", "OutlinedButton",
                       "FloatingActionButton", "clickable", "onClick", "HED")

    private fun extractLabel(call: KtCall): String {
        // 尝试从参数提取标签
        for (arg in call.args) {
            if (arg is KtLitStr) return arg.value
        }
        // 从命名参数找 "text" 或 "contentDescription"
        return "(未命名按钮)"
    }

    private fun extractAction(call: KtCall): String {
        // 找 lambda 参数（onClick = { ... }）
        for (arg in call.args) {
            if (arg is KtLambda) {
                val body = arg.body
                if (body is KtBlock) {
                    val calls = body.statements.filterIsInstance<KtCall>()
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
        walk(listOf(call)) { node ->
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

    /** 遍历 AST 节点 */
    private fun walk(nodes: List<KtNode>, visitor: (KtNode) -> Unit) {
        for (node in nodes) {
            visitor(node)
            when (node) {
                is KtClass -> walk(node.members, visitor)
                is KtFun -> node.body?.let { walk(listOf(it), visitor) }
                is KtBlock -> walk(node.statements, visitor)
                is KtIf -> {
                    node.thenBranch?.let { walk(listOf(it), visitor) }
                    node.elseBranch?.let { walk(listOf(it), visitor) }
                }
                is KtCall -> walk(node.args, visitor)
                is KtLambda -> walk(listOf(node.body), visitor)
                is KtBinary -> { walk(listOf(node.left, node.right), visitor) }
                else -> {}
            }
        }
    }
}