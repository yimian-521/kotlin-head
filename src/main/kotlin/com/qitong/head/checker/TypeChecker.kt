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
    private fun bindExpr(expr: KtExpr) {
        when (expr) {
            is KtRef -> {
                val sym = lookup(expr.name)
                if (sym == null) {
                    diagnostics += Diag(DiagLevel.ERROR, "未定义的引用: ${expr.name}", expr.span.start)
                }
            }
            is KtBinary -> {
                bindExpr(expr.left)
                bindExpr(expr.right)
            }
            is KtCall -> {
                bindExpr(expr.target)
                for (a in expr.args) bindExpr(a)
            }
            is KtIf -> {
                bindExpr(expr.cond)
                bindExpr(expr.thenBranch)
                expr.elseBranch?.let { bindExpr(it) }
            }
            is KtLambda -> {
                scopes.addLast(mutableMapOf<String, Symbol>())
                for (p in expr.params) register(p.name, SymbolKind.PARAM, expr, p.type)
                bindExpr(expr.body)
                scopes.removeLast()
            }
            // 字面量不需要绑定
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

    // ─── 符号表操作 ───
    private fun register(name: String, kind: SymbolKind, node: KtNode, type: String? = null) {
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