package com.qitong.head.eventbus

import com.qitong.head.ast.*
import com.qitong.head.headstd.HMap
import com.qitong.head.headstd.HList

/**
 * LiveDeclarationGraph — 声明级活图（本质化重构）
 *
 * 本质原则：引用即传播。
 * 每个声明自带反向索引（谁引用我），改了自动暴露受影响者。
 * 不需要教进程"怎么算传播"——数据结构自身就包含答案。
 */
object LiveDeclarationGraph {

    data class DeclNode(
        val declId: String, val kind: String, val name: String,
        val signature: String, val returnType: String?,
        val filePath: String
    )

    // 正向：declId → 它引用了哪些声明
    private val deps = HMap<String, HList<String>>()
    // 反向：declId → 被哪些声明引用（本质）
    private val revDeps = HMap<String, HList<String>>()

    @Volatile var isLive = false; private set

    fun buildFromAst(files: Map<String, KtFile>) {
        clear()
        for ((path, file) in files) {
            for (decl in file.declarations) {
                val node = toNode(path, decl) ?: continue
                deps.put(node.declId, collectRefs(node.declId, decl))
            }
        }
        // 建反向索引：对于dep A→B，记录B被A引用
        deps.forEach { from, refs ->
            for (i in 0 until refs.size) {
                val to = refs[i]
                var list = revDeps.get(to)
                if (list == null) { list = HList(); revDeps.put(to, list) }
                list.add(from)
            }
        }
        isLive = true
    }

    /** 唯一公共方法：声明变了，谁受影响？
     * @return 直接+间接受影响的声明ID列表
     */
    fun affectedBy(changedDeclIds: Set<String>): HList<String> {
        val result = HList<String>()
        val queue = HList<String>()
        val visited = mutableSetOf<String>()
        for (id in changedDeclIds) { queue.add(id); visited.add(id); result.add(id) }
        var idx = 0
        while (idx < queue.size) {
            val current = queue[idx++]
            val affected = revDeps.get(current) ?: continue
            for (i in 0 until affected.size) {
                val a = affected[i]
                if (visited.add(a)) { queue.add(a); result.add(a) }
            }
        }
        return result
    }

    fun registerOrUpdate(filePath: String, decl: KtDecl) {
        val node = toNode(filePath, decl) ?: return
        // 更新正向依赖
        deps.put(node.declId, collectRefs(node.declId, decl))
        // 全量重建反向索引（HList无remove单个元素）
        rebuildRevDeps()
    }

    private fun rebuildRevDeps() {
        revDeps.clear()
        deps.forEach { from, refs ->
            for (i in 0 until refs.size) {
                val to = refs[i]
                var list = revDeps.get(to)
                if (list == null) { list = HList(); revDeps.put(to, list) }
                list.add(from)
            }
        }
    }

    fun getNode(declId: String): DeclNode? {
        // 从deps推导kind/name（简化：从declId格式解析）
        val parts = declId.split(":")
        if (parts.size < 3) return null
        return DeclNode(declId, parts[1], parts[2], "?", null, parts[0])
    }

    fun totalDeclarations(): Int { var c = 0; deps.keys().forEach { c++ }; return c }
    fun totalFiles(): Int { val files = mutableSetOf<String>(); deps.keys().forEach { files.add(it.substringBefore(":")) }; return files.size }

    fun clear() { deps.clear(); revDeps.clear(); isLive = false }

    // ─── 内部 ───
    private fun toNode(path: String, decl: KtDecl): DeclNode? = when (decl) {
        is KtFun -> DeclNode("$path:fun:${decl.name}", "fun", decl.name,
            decl.params.joinToString(",") { "${it.name}:${it.type ?: "Any"}" },
            decl.returnType, path)
        is KtClass -> DeclNode("$path:class:${decl.name}", "class", decl.name,
            decl.members.size.toString(), null, path)
        is KtVal -> DeclNode("$path:val:${decl.name}", "val", decl.name,
            decl.type ?: "inferred", decl.type, path)
        else -> null
    }

    private fun collectRefs(declId: String, decl: KtDecl): HList<String> {
        val refs = HList<String>()
        when (decl) {
            is KtFun -> decl.body?.let { scanExpr(it, refs) }
            is KtClass -> decl.members.forEach { if (it is KtFun) it.body?.let { b -> scanExpr(b, refs) } }
            is KtVal -> decl.value?.let { scanExpr(it, refs) }
            else -> {}
        }
        return refs
    }

    private fun scanExpr(expr: KtExpr, refs: HList<String>) {
        when (expr) {
            is KtCall -> { scanTarget(expr.target, refs); expr.args.forEach { scanExpr(it, refs) } }
            is KtBinary -> { scanExpr(expr.left, refs); scanExpr(expr.right, refs) }
            is KtMemberAccess -> { scanExpr(expr.target, refs); refs.add("*:fun:${expr.member}") }
            is KtRef -> refs.add("*:val:${expr.name}")
            is KtIf -> { scanExpr(expr.cond, refs); scanExpr(expr.thenBranch, refs); expr.elseBranch?.let { scanExpr(it, refs) } }
            is KtBlock -> expr.statements.forEach { if (it is KtExpr) scanExpr(it, refs) }
            is KtLambda -> scanExpr(expr.body, refs)
            is KtReturn -> expr.value?.let { scanExpr(it, refs) }
            is KtWhen -> {
                expr.subject?.let { scanExpr(it, refs) }
                expr.branches.forEach { scanExpr(it.condition, refs); scanExpr(it.body, refs) }
                expr.elseBranch?.let { scanExpr(it, refs) }
            }
            else -> {}
        }
    }

    private fun scanTarget(target: KtExpr, refs: HList<String>) {
        when (target) {
            is KtRef -> refs.add("*:fun:${target.name}")
            is KtMemberAccess -> refs.add("*:fun:${target.member}")
            else -> {}
        }
    }
}