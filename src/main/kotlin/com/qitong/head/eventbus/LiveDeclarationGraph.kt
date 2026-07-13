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
        val newRefs = collectRefs(node.declId, decl)
        // 移除旧反向边
        val oldRefs = deps.get(node.declId)
        if (oldRefs != null) {
            for (i in 0 until oldRefs.size) {
                revDeps.get(oldRefs[i])?.let { list ->
                    // 只能全量重建该被引用者的反向索引
                    rebuildRevDepsFor(oldRefs[i])
                }
            }
        }
        // 更新正向
        deps.put(node.declId, newRefs)
        // 添加新反向边
        for (i in 0 until newRefs.size) {
            val to = newRefs[i]
            var list = revDeps.get(to)
            if (list == null) { list = HList(); revDeps.put(to, list) }
            list.add(node.declId)
        }
    }

    private fun rebuildRevDepsFor(declId: String) {
        revDeps.remove(declId)
        deps.forEach { from, refs ->
            for (i in 0 until refs.size) {
                if (refs[i] == declId) {
                    var list = revDeps.get(declId)
                    if (list == null) { list = HList(); revDeps.put(declId, list) }
                    list.add(from)
                    return@rebuildRevDepsFor // 找到就停，一个声明不会被同一引用者多次引用
                }
            }
        }
    }

    fun getNode(declId: String): DeclNode? {
        // 从右向左解析：最后一个冒号前是name，倒数第二个是kind，剩余是path
        val lastColon = declId.lastIndexOf(':')
        if (lastColon < 0) return null
        val secondLast = declId.lastIndexOf(':', lastColon - 1)
        if (secondLast < 0) return null
        val path = declId.substring(0, secondLast)
        val kind = declId.substring(secondLast + 1, lastColon)
        val name = declId.substring(lastColon + 1)
        return DeclNode(declId, kind, name, "?", null, path)
    }

    fun totalDeclarations(): Int = deps.keys().toList().size  // 方程：键集合自然携带计数
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

    private fun scanExpr(expr: KtExpr, refs: HList<String>, depth: Int = 0) {
        if (depth > 100) return // 防止深层嵌套栈溢出
        when (expr) {
            is KtCall -> { scanTarget(expr.target, refs); expr.args.forEach { scanExpr(it, refs, depth + 1) } }
            is KtBinary -> { scanExpr(expr.left, refs, depth + 1); scanExpr(expr.right, refs, depth + 1) }
            is KtMemberAccess -> { scanExpr(expr.target, refs, depth + 1); refs.add("*:fun:${expr.member}") }
            is KtRef -> refs.add("*:val:${expr.name}")
            is KtIf -> { scanExpr(expr.cond, refs, depth + 1); scanExpr(expr.thenBranch, refs, depth + 1); expr.elseBranch?.let { scanExpr(it, refs, depth + 1) } }
            is KtBlock -> expr.statements.forEach { if (it is KtExpr) scanExpr(it, refs, depth + 1) }
            is KtLambda -> scanExpr(expr.body, refs, depth + 1)
            is KtReturn -> expr.value?.let { scanExpr(it, refs, depth + 1) }
            is KtWhen -> {
                expr.subject?.let { scanExpr(it, refs, depth + 1) }
                expr.branches.forEach { scanExpr(it.condition, refs, depth + 1); scanExpr(it.body, refs, depth + 1) }
                expr.elseBranch?.let { scanExpr(it, refs, depth + 1) }
            }
            is KtFor -> {
                scanExpr(expr.iterable, refs, depth + 1)
                expr.body?.let { scanExpr(it, refs, depth + 1) }
            }
            is KtWhile -> {
                scanExpr(expr.cond, refs, depth + 1)
                expr.body?.let { scanExpr(it, refs, depth + 1) }
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