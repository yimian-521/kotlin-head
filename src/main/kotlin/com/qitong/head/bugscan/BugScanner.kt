package com.qitong.head.bugscan

import com.qitong.head.ast.*

class BugScanner {

    data class Finding(
        val rule: String,
        val severity: Severity,
        val message: String,
        val span: Span
    )

    enum class Severity { HIGH, MEDIUM, LOW }

    private val findings = mutableListOf<Finding>()

    fun scan(file: KtFile): List<Finding> {
        findings.clear()
        scanNode(file)
        return findings.toList()
    }

    private fun scanNode(node: Any?) {
        when (node) {
            is KtFile -> node.declarations.forEach { scanNode(it) }
            is KtClass -> {
                checkBug1_tailrecOpen(node)
                checkBug2_dataCopyAnnotations(node)
                checkBug3_sealedReflection(node)
                node.members.forEach { scanNode(it) }
            }
            is KtFun -> {
                checkBug4_crossinlineReturn(node)
                checkBug5_reifiedTrap(node)
                checkBug6_publishedApiLeak(node)
                checkBug7_byLazyThreadSafety(node)
                checkBug8_nonLocalReturn(node)
                node.body?.let { scanExpr(it) }
            }
            is KtVal -> {
                checkBug9_platformTypeBang(node)
                checkBug10_delegateTrap(node)
                node.value?.let { scanExpr(it) }
                if (node.name.contains("69")) System.err.println("[BS] saw hell_69: value=${node.value?.javaClass?.simpleName}")
            }
            is KtBlock -> node.statements.forEach { scanNode(it) }
            is KtIf -> { scanExpr(node.cond); scanExpr(node.thenBranch); node.elseBranch?.let { scanExpr(it) } }
            is KtBinary -> scanExpr(node)
            is KtCall -> { checkBug11_typeInferenceDegrade(node); scanExpr(node) }
            is KtReturn -> { node.value?.let { scanExpr(it) } }
            is KtMemberAccess -> scanNode(node.target)
            is KtLambda -> scanExpr(node.body)
            is KtWhen -> scanExpr(node)
            else -> {}
        }
    }

    private fun scanExpr(expr: KtExpr) {
        when (expr) {
            is KtBinary -> {
                checkBooleanArith(expr)
                checkStringCmp(expr)
                scanExpr(expr.left); scanExpr(expr.right)
            }
            is KtCall -> { scanExpr(expr.target); expr.args.forEach { scanExpr(it) } }
            is KtIf -> { scanExpr(expr.cond); scanExpr(expr.thenBranch); expr.elseBranch?.let { scanExpr(it) } }
            is KtWhen -> {
                if (expr.branches.isEmpty() && expr.elseBranch == null)
                    findings.add(Finding("BS-EMPTY-WHEN", Severity.MEDIUM, "空when表达式——始终为Unit", expr.whenSpan))
                expr.subject?.let { scanExpr(it) }
                expr.branches.forEach { scanExpr(it.condition); scanExpr(it.body) }
                expr.elseBranch?.let { scanExpr(it) }
            }
            is KtBlock -> expr.statements.forEach { if (it is KtExpr) scanExpr(it) else if (it is KtDecl) scanNode(it) }
            is KtReturn -> { expr.value?.let { scanExpr(it) } }
            else -> {}
        }
    }

    private fun checkBooleanArith(expr: KtBinary) {
        if (expr.op !in setOf("+", "-", "*", "/", "%")) return
        if (expr.left is KtLitBool || expr.right is KtLitBool)
            findings.add(Finding("BS-BOOL-ARITH", Severity.HIGH, "Boolean参与算术运算(${expr.op})——运行时抛异常", expr.binSpan))
    }
    private fun checkStringCmp(expr: KtBinary) {
        if (expr.op !in setOf(">", "<", ">=", "<=")) return
        if ((expr.left is KtLitStr && expr.right is KtLitInt) || (expr.left is KtLitInt && expr.right is KtLitStr))
            findings.add(Finding("BS-STR-CMP", Severity.MEDIUM, "String与Int比较(${expr.op})——非预期行为", expr.binSpan))
    }

    // #1-#11: 原有冷门规则
    private fun checkBug1_tailrecOpen(cls: KtClass) {
        cls.members.forEach { m ->
            if (m is KtFun && m.modifiers.contains("tailrec") && m.modifiers.contains("open"))
                findings.add(Finding("KT-TAILREC-OPEN", Severity.HIGH, "tailrec在open/trait函数上无效", m.funSpan))
        }
    }
    private fun checkBug2_dataCopyAnnotations(cls: KtClass) {
        if (!cls.modifiers.contains("data")) return
        if (cls.annotations.isNotEmpty() && cls.members.any { m ->
            when (m) { is KtFun -> m.annotations.isNotEmpty(); is KtVal -> m.annotations.isNotEmpty(); else -> false }
        }) findings.add(Finding("KT-DATA-COPY-ANN", Severity.MEDIUM, "data class有成员注解——copy()不复制", cls.classSpan))
    }
    private fun checkBug3_sealedReflection(cls: KtClass) {
        if (cls.modifiers.contains("sealed"))
            findings.add(Finding("KT-SEALED-REFLECT", Severity.LOW, "sealed class反射枚举跨模块不完整", cls.classSpan))
    }
    private fun checkBug4_crossinlineReturn(fn: KtFun) {
        if (!fn.modifiers.contains("inline")) return
        fn.body?.let { checkReturnInLambda(it, true) }
    }
    private fun checkBug5_reifiedTrap(fn: KtFun) {
        if (fn.modifiers.contains("inline") && fn.params.any { it.type?.contains("reified") == true })
            findings.add(Finding("KT-REIFIED-TRAP", Severity.LOW, "inline+reified跨边界假错误", fn.funSpan))
    }
    private fun checkBug6_publishedApiLeak(fn: KtFun) {
        if (fn.annotations.any { it.name == "PublishedApi" } && fn.modifiers.contains("internal"))
            findings.add(Finding("KT-PUBAPI-LEAK", Severity.MEDIUM, "@PublishedApi internal二进制兼容性破坏", fn.funSpan))
    }
    private fun checkBug7_byLazyThreadSafety(fn: KtFun) { fn.body?.let { checkLazyInBody(it) } }
    private fun checkLazyInBody(node: Any?) {
        when (node) {
            is KtBlock -> node.statements.forEach { checkLazyInBody(it) }
            is KtBinary -> { checkLazyInBody(node.left); checkLazyInBody(node.right) }
            is KtCall -> {
                if (node.target is KtRef && (node.target as KtRef).name == "lazy")
                    findings.add(Finding("KT-LAZY-THREAD", Severity.LOW, "by lazy默认SYNCHRONIZED——不必要锁开销", node.callSpan))
                node.args.forEach { checkLazyInBody(it) }
            }
            else -> {}
        }
    }
    private fun checkBug8_nonLocalReturn(fn: KtFun) {
        if (!fn.modifiers.contains("inline")) return
        fn.body?.let { checkReturnInLambda(it, false) }
    }
    private fun checkReturnInLambda(node: Any?, inLambda: Boolean) {
        when (node) {
            is KtBlock -> node.statements.forEach { checkReturnInLambda(it, inLambda) }
            is KtLambda -> checkReturnInLambda(node.body, true)
            is KtReturn -> {
                if (inLambda) findings.add(Finding("KT-NONLOCAL-RETURN", Severity.MEDIUM, "inline lambda中return非局部返回", node.returnSpan))
            }
            is KtCall -> node.args.forEach { checkReturnInLambda(it, inLambda) }
            else -> {}
        }
    }
    private fun checkBug9_platformTypeBang(valDecl: KtVal) { valDecl.value?.let { checkBangInExpr(it) } }
    private fun checkBangInExpr(expr: KtExpr) {
        when (expr) {
            is KtBinary -> {
                if (expr.op == "!!") findings.add(Finding("KT-PLATFORM-BANG", Severity.HIGH, "!!吞掉NPE根因——用?.let{}替代", expr.binSpan))
                checkBangInExpr(expr.left); checkBangInExpr(expr.right)
            }
            is KtCall -> expr.args.forEach { checkBangInExpr(it) }
            is KtBlock -> expr.statements.forEach { if (it is KtExpr) checkBangInExpr(it) }
            else -> {}
        }
    }
    private fun checkBug10_delegateTrap(valDecl: KtVal) {
        if (valDecl.modifiers.contains("by"))
            findings.add(Finding("KT-DELEGATE-TRAP", Severity.LOW, "委托属性异常被包装为KotlinNullPointerException", valDecl.valSpan))
    }
    private fun checkBug11_typeInferenceDegrade(call: KtCall) {
        val targetName = when (val t = call.target) {
            is KtRef -> t.name; is KtMemberAccess -> t.member; else -> return
        }
        if (call.args.size >= 3 && targetName.length > 10)
            findings.add(Finding("KT-TYPE-DEGRADE", Severity.LOW, "复杂泛型调用($targetName)可能退化为Any", call.callSpan))
    }

    companion object {
        fun from(file: KtFile) = BugScanner().scan(file)
    }
}