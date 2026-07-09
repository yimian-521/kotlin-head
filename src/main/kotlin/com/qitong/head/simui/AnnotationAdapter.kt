package com.qitong.head.simui

import com.qitong.head.ast.*

/**
 * v0.12.8: 适配层 — 外部格式翻译为原生SimSchema（免免三层架构第二层）
 * 
 * 身份：翻译。不消费、不调度、不决策。
 * Parser 只吃 SimSchema——适配层是进门前的那道翻译。
 */

object AnnotationAdapter {

    /** 从 KtCall 尝试翻译为 SimNode。失败返回 null（非探针调用） */
    fun translate(call: KtCall): SimNode? {
        val name = extractName(call) ?: return null
        if (!SimSchema.isCandidate(name)) return null

        // 核心特征：lambda 回调
        val hasLambda = call.args.any { it is KtLambda }
        if (!hasLambda) return null

        return SimNode(
            id = "${name}@L${call.span.start.line}",
            kind = NodeKind.BUTTON,
            label = extractLabel(call),
            actionHint = extractAction(call),
            deps = extractDeps(call),
            line = call.span.start.line,
            confidence = 1.0,
            sourceRef = call
        )
    }

    /** 从 KtBinary 翻译状态变量赋值 */
    fun translateVar(binary: KtBinary, knownVars: Set<String>): SimNode? {
        val leftName = when (val left = binary.left) {
            is KtRef -> left.name
            is KtMemberAccess -> left.member
            else -> return null
        }
        if (leftName !in knownVars) return null
        if (binary.op !in setOf("=", "+=", "-=", "*=", "/=", "%=")) return null

        return SimNode(
            id = "var@L${binary.span.start.line}:$leftName",
            kind = NodeKind.STATE_VAR,
            label = leftName,
            actionHint = exprToString(binary.right),
            deps = listOf(leftName),
            line = binary.span.start.line,
            confidence = 0.6,
            sourceRef = binary
        )
    }

    // ─── 内部提取函数 ───

    private fun extractName(call: KtCall): String? = when (val t = call.target) {
        is KtRef -> t.name
        is KtMemberAccess -> t.member
        else -> null
    }

    private fun extractLabel(call: KtCall): String {
        for (arg in call.args) {
            if (arg is KtLitStr && arg.value.length > 1) return arg.value
        }
        return "(未命名)"
    }

    private fun extractAction(call: KtCall): String {
        for (arg in call.args) {
            if (arg is KtLambda) {
                val body = arg.body
                val calls = mutableListOf<KtCall>()
                when (body) {
                    is KtBlock -> {
                        calls.addAll(body.statements.filterIsInstance<KtCall>())
                        // 免免：赋值也是交互——page = "detail"
                        body.statements.filterIsInstance<KtBinary>().forEach { b ->
                            val v = when (val l = b.left) { is KtRef -> l.name; else -> "?" }
                            calls.add(KtCall(KtRef("$v=${exprToString(b.right)}", null, b.span), emptyList(), b.span))
                        }
                    }
                    is KtCall -> calls.add(body)
                    else -> walkAst(listOf(body), { if (it is KtCall) calls.add(it) }, 0)
                }
                if (calls.isNotEmpty()) {
                    return calls.joinToString(" → ") {
                        when (val t = it.target) {
                            is KtRef -> t.name
                            is KtMemberAccess -> t.member
                            else -> "?"
                        }
                    }
                }
            }
        }
        return "(无lambda)"
    }

    private fun extractDeps(call: KtCall): List<String> {
        val deps = mutableListOf<String>()
        // 免免：从 call.args 开始，排除 call.target（函数名）——Button/navigateTo 不是状态依赖
        walkAst(call.args, { if (it is KtRef) deps.add(it.name) }, 0)
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

    /** 轻量 AST walk — 适配层专用，不依赖 SimUiScanner.walk */
    private fun walkAst(nodes: List<KtNode>, visitor: (KtNode) -> Unit, depth: Int = 0) {
        if (depth > 128) return
        for (node in nodes) {
            visitor(node)
            when (node) {
                is KtClass     -> walkAst(node.members, visitor, depth + 1)
                is KtInterface -> walkAst(node.members, visitor, depth + 1)
                is KtEnum      -> walkAst(node.members, visitor, depth + 1)
                is KtFun       -> node.body?.let { walkAst(listOf(it), visitor, depth + 1) }
                is KtVal       -> node.value?.let { walkAst(listOf(it), visitor, depth + 1) }
                is KtBlock     -> walkAst(node.statements, visitor, depth + 1)
                is KtIf        -> {
                    node.thenBranch?.let { walkAst(listOf(it), visitor, depth + 1) }
                    node.elseBranch?.let { walkAst(listOf(it), visitor, depth + 1) }
                }
                is KtWhen      -> {
                    node.subject?.let { walkAst(listOf(it), visitor, depth + 1) }
                    node.branches.forEach { b ->
                        walkAst(listOf(b.condition), visitor, depth + 1)
                        walkAst(listOf(b.body), visitor, depth + 1)
                    }
                }
                is KtFor       -> {
                    walkAst(listOf(node.iterable), visitor, depth + 1)
                    node.body?.let { walkAst(listOf(it), visitor, depth + 1) }
                }
                is KtWhile     -> {
                    node.condition?.let { walkAst(listOf(it), visitor, depth + 1) }
                    node.body?.let { walkAst(listOf(it), visitor, depth + 1) }
                }
                is KtCall      -> walkAst(node.args, visitor, depth + 1)
                is KtLambda    -> walkAst(listOf(node.body), visitor, depth + 1)
                is KtBinary    -> { walkAst(listOf(node.left, node.right), visitor, depth + 1) }
                else -> {}
            }
        }
    }
}