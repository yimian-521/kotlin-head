package com.qitong.head.checker

import com.qitong.head.ast.*

import java.util.LinkedList

typealias SymbolTable = MutableMap<String, Symbol>

data class Symbol(
    val name: String,
    val kind: SymbolKind,
    val node: KtNode,
    val type: String?        // null = 未绑定 / 需推断
)

enum class SymbolKind { CLASS, FUN, VAL, PARAM }

class TypeChecker {
    private val global = mutableMapOf<String, Symbol>()
    private val scopes = LinkedList<SymbolTable>()
    private val diagnostics = mutableListOf<Diag>()

    data class Diag(
        val level: DiagLevel,
        val msg: String,
        val pos: Pos
    )

    enum class DiagLevel { ERROR, WARN, SKIPPED, EXPECTED }

    fun check(file: KtFile): List<Diag> {
        diagnostics.clear()
        scopes.addLast(global)
        for (decl in file.declarations) checkDecl(decl)
        return diagnostics.toList()
    }

    // ─── 声明检查 ───
    private fun checkDecl(decl: KtDecl) {
        when (decl) {
            is KtClass -> {
                register(decl.name, SymbolKind.CLASS, decl)
                scopes.addLast(mutableMapOf<String, Symbol>())
                for (m in decl.members) checkDecl(m)
                scopes.removeLast()
            }
            is KtFun -> {
                register(decl.name, SymbolKind.FUN, decl)
                scopes.addLast(mutableMapOf<String, Symbol>())
                for (p in decl.params) {
                    register(p.name, SymbolKind.PARAM, decl, p.type)
                }
                decl.body?.let { bindExpr(it) }
                scopes.removeLast()
            }
            is KtVal -> {
                decl.value?.let { bindExpr(it) }
                register(decl.name, SymbolKind.VAL, decl, decl.type)
            }
        }
    }

    // ─── 表达式绑定 ───
    private var loopDepth = 0

    private fun bindExpr(expr: KtExpr) {
        when (expr) {
            is KtRef -> {
                val sym = lookup(expr.name)
                if (sym == null && !expr.name.startsWith("?")) {
                    diagnostics += Diag(DiagLevel.ERROR, "未定义的引用: ${expr.name}", expr.span.start)
                }
            }
            is KtBinary -> {
                bindExpr(expr.left)
                bindExpr(expr.right)
                checkBinarySemantics(expr)
            }
            is KtCall -> {
                bindExpr(expr.target)
                for (a in expr.args) bindExpr(a)
            }
            is KtIf -> {
                bindExpr(expr.cond)
                checkCondition(expr.cond, expr.span.start)
                bindExpr(expr.thenBranch)
                expr.elseBranch?.let { bindExpr(it) }
            }
            is KtWhen -> {
                expr.subject?.let { bindExpr(it) }
                for (b in expr.branches) { bindExpr(b.condition); bindExpr(b.body) }
                expr.elseBranch?.let { bindExpr(it) }
                if (expr.branches.isEmpty() && expr.elseBranch == null)
                    diagnostics += Diag(DiagLevel.WARN, "when 表达式无分支且无 else", expr.span.start)
            }
            is KtFor -> { loopDepth++; bindExpr(expr.iterable); bindExpr(expr.body); loopDepth-- }
            is KtWhile -> { loopDepth++; bindExpr(expr.condition); bindExpr(expr.body); loopDepth-- }
            is KtReturn -> { expr.value?.let { bindExpr(it) } }
            is KtLambda -> {
                scopes.addLast(mutableMapOf<String, Symbol>())
                for (p in expr.params) register(p.name, SymbolKind.PARAM, expr, p.type)
                bindExpr(expr.body)
                scopes.removeLast()
            }
            is KtBlock -> {
                for (stmt in expr.statements) {
                    when (stmt) {
                        is KtDecl -> checkDecl(stmt)
                        is KtExpr -> bindExpr(stmt)
                    }
                }
            }
        }
    }

    // ─── 语义检查辅助 ───
    private fun checkBinarySemantics(expr: KtBinary) {
        val leftLit = inferLitType(expr.left)
        val rightLit = inferLitType(expr.right)
        val isArith = expr.op in setOf("+", "-", "*", "/", "%")
        val isCmp = expr.op in setOf(">", "<", ">=", "<=", "==", "!=")
        if (isArith && (leftLit == "Boolean" || rightLit == "Boolean"))
            diagnostics += Diag(DiagLevel.ERROR, "Boolean 不能参与算术: ${expr.op}", expr.span.start)
        if (isCmp && leftLit != null && rightLit != null && leftLit != rightLit)
            if (leftLit == "String" || rightLit == "String" || leftLit == "Boolean" || rightLit == "Boolean")
                diagnostics += Diag(DiagLevel.WARN, "可能无意义的比较: $leftLit ${expr.op} $rightLit", expr.span.start)
    }
    private fun checkCondition(cond: KtExpr, pos: Pos) {
        val litType = inferLitType(cond)
        if (litType != null && litType != "Boolean")
            diagnostics += Diag(DiagLevel.WARN, "条件类型为 $litType，非 Boolean", pos)
    }
    private fun inferLitType(expr: KtExpr): String? = when (expr) {
        is KtLitBool -> "Boolean"
        is KtLitInt -> "Int"
        is KtLitStr -> "String"
        else -> null
    }

    // ─── 符号表操作 ───
    private fun register(name: String, kind: SymbolKind, node: KtNode, type: String? = null) {
        if (name.startsWith("?")) return  // ★ 哨兵声明不注册——避免噪音
        val scope = scopes.last()
        if (scope.containsKey(name)) {
            diagnostics += Diag(DiagLevel.ERROR, "重复声明: $name", node.span.start)
        } else {
            scope[name] = Symbol(name, kind, node, type)
        }
    }

    private fun lookup(name: String): Symbol? {
        for (scope in scopes.reversed()) {
            val s = scope[name]
            if (s != null) return s
        }
        return null
    }
}