package com.qitong.head.simui

import com.qitong.head.ast.*

/**
 * v0.12.8: SimUiScanner（免免三层架构第三层——消费者）
 * 
 * 身份：装配。不翻译、不提取、不调度。
 * walk AST → 调适配层翻译 → 组装 ScanResult → 决策门。
 */
object SimUiScanner {

    data class ScanResult(
        val buttons: List<UiInteraction>,
        val varTriggers: List<VarInteraction>,
        val unknowns: List<KtCall>,
        val source: String
    )

    data class UiInteraction(
        val id: String,
        val label: String,
        val line: Int,
        val actionHint: String,
        val varDependencies: List<String>,
        val confidence: Double
    )

    data class VarInteraction(
        val varName: String,
        val assignedValue: String,
        val line: Int,
        val inContext: String,
        val confidence: Double
    )

    /** 决策门 — 与 v0.12.7 route() 同构 */
    fun scan(file: KtFile, preferredStrategy: String = "auto"): ScanResult = when (preferredStrategy) {
        "probe" -> probeScan(file)
        "var"   -> variableScan(file)
        else    -> hybridScan(file)
    }

    // ══════════ 阶段1：探针扫描 ══════════

    private fun probeScan(file: KtFile): ScanResult {
        val buttons = mutableListOf<UiInteraction>()
        val remains = mutableListOf<KtCall>()
        walk(file.declarations, { node ->
            if (node is KtCall) {
                val n = AnnotationAdapter.translate(node)
                if (n != null) buttons.add(UiInteraction(
                    id = n.id, label = n.label, line = n.line,
                    actionHint = n.actionHint, varDependencies = n.deps, confidence = n.confidence
                )) else remains.add(node)
            }
        })
        return ScanResult(buttons, emptyList(), if (buttons.isEmpty()) remains else emptyList(), "探针扫描")
    }

    // ══════════ 阶段2：变量追踪 ══════════

    private fun variableScan(file: KtFile): ScanResult {
        val stateVars = mutableSetOf<String>()
        walk(file.declarations, { node ->
            if (node is KtVal && "var" in node.modifiers) stateVars.add(node.name)
        }, 0)
        val triggers = mutableListOf<VarInteraction>()
        walk(file.declarations, { node ->
            if (node is KtBinary) {
                val n = AnnotationAdapter.translateVar(node, stateVars)
                if (n != null) triggers.add(VarInteraction(
                    varName = n.label, assignedValue = n.actionHint,
                    line = n.line, inContext = "L${n.line}", confidence = n.confidence
                ))
            }
        })
        return ScanResult(emptyList(), triggers, emptyList(), "变量追踪")
    }

    // ══════════ 阶段3：混合输出 ══════════

    private fun hybridScan(file: KtFile): ScanResult {
        val probe = probeScan(file)
        if (probe.buttons.isNotEmpty()) return probe
        val vars = variableScan(file)
        if (vars.varTriggers.isNotEmpty()) return vars.copy(source = "混合扫描：探针0命中 → 变量追踪 ${vars.varTriggers.size} 个")
        val unknowns = mutableListOf<KtCall>()
        walk(file.declarations, { node -> if (node is KtCall) unknowns.add(node) }, 0)
        return ScanResult(emptyList(), emptyList(), unknowns, "混合扫描：0探针 0变量 — 请手动标注交互点")
    }

    // ══════════ AST 遍历 ══════════

    private fun walk(nodes: List<KtNode>, visitor: (KtNode) -> Unit, depth: Int = 0) {
        if (depth > 50) return
        for (node in nodes) {
            visitor(node)
            when (node) {
                is KtClass     -> walk(node.members, visitor, depth + 1)
                is KtInterface -> walk(node.members, visitor, depth + 1)
                is KtEnum      -> walk(node.members, visitor, depth + 1)
                is KtFun       -> node.body?.let { walk(listOf(it), visitor, depth + 1) }
                is KtVal       -> node.value?.let { walk(listOf(it), visitor, depth + 1) }
                is KtBlock     -> walk(node.statements, visitor, depth + 1)
                is KtIf        -> { node.thenBranch?.let { walk(listOf(it), visitor, depth + 1) }; node.elseBranch?.let { walk(listOf(it), visitor, depth + 1) } }
                is KtWhen      -> {
                    node.subject?.let { walk(listOf(it), visitor, depth + 1) }
                    node.branches.forEach { b ->
                        b.condition?.let { walk(listOf(it), visitor, depth + 1) }
                        walk(listOf(b.body), visitor, depth + 1)
                    }
                }
                is KtFor       -> { walk(listOf(node.iterable), visitor, depth + 1); node.body?.let { walk(listOf(it), visitor, depth + 1) } }
                is KtWhile     -> { node.condition?.let { walk(listOf(it), visitor, depth + 1) }; node.body?.let { walk(listOf(it), visitor, depth + 1) } }
                is KtCall      -> walk(node.args, visitor, depth + 1)
                is KtLambda    -> walk(listOf(node.body), visitor, depth + 1)
                is KtBinary    -> walk(listOf(node.left, node.right), visitor, depth + 1)
                else -> {}
            }
        }
    }
}