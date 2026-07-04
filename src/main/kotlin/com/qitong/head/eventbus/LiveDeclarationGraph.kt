package com.qitong.head.eventbus

import com.qitong.head.ast.*

/**
 * LiveDeclarationGraph —— v0.9.0 声明级活图（混合编译的核心）
 *
 * 不是全量，不是增量，是第三种：
 * - 全量属性：每个声明都在图里，完整性来自图本身
 * - 增量属性：只动受影响的声明，经济性来自传播
 * - 图始终活在内存，编译一次后持续维护，重启只是重连
 *
 * 指挥官管图——图和 AST 同源，同一个指挥官既管调度又管图，永不过期。
 *
 * 循环依赖：浅提取（签名+返回值类型+注解，跳过 body，可随时展开）。
 */
object LiveDeclarationGraph {

    data class DeclNode(
        val declId: String,
        val kind: String,
        val name: String,
        val signature: String,
        val returnType: String?,
        val annotations: List<String>,
        val bodyRef: Any? = null,
        val depDeclIds: MutableSet<String> = mutableSetOf(),
        var isShallow: Boolean = false
    )

    private val nodes = mutableMapOf<String, DeclNode>()
    private val byFile = mutableMapOf<String, MutableSet<String>>()

    @Volatile var isLive = false
        private set

    fun buildFromAst(files: Map<String, KtFile>) {
        nodes.clear()
        byFile.clear()
        for ((fileName, ktFile) in files) {
            val declIds = mutableSetOf<String>()
            for (decl in ktFile.declarations) {
                val node = toDeclNode(fileName, decl) ?: continue
                nodes[node.declId] = node
                declIds.add(node.declId)
            }
            byFile[fileName] = declIds
        }
        resolveDeclDeps()
        isLive = true
    }

    private fun toDeclNode(fileName: String, decl: KtDecl): DeclNode? = when (decl) {
        is KtFun -> DeclNode(
            declId = "$fileName:fun:${decl.name}",
            kind = "fun", name = decl.name,
            signature = decl.params.joinToString(",") { "${it.name}:${it.type ?: "Any"}" },
            returnType = decl.returnType,
            annotations = decl.annotations.map { it.name },
            bodyRef = decl
        )
        is KtClass -> DeclNode(
            declId = "$fileName:class:${decl.name}",
            kind = "class", name = decl.name,
            signature = decl.members.joinToString(",") { memberName(it) },
            returnType = null,
            annotations = decl.annotations.map { it.name },
            bodyRef = decl
        )
        is KtVal -> DeclNode(
            declId = "$fileName:val:${decl.name}",
            kind = "val", name = decl.name,
            signature = decl.type ?: "inferred",
            returnType = decl.type,
            annotations = decl.annotations.map { it.name },
            bodyRef = decl
        )
        else -> null
    }

    private fun memberName(decl: KtDecl): String = when (decl) {
        is KtFun -> "fun:${decl.name}"
        is KtClass -> "class:${decl.name}"
        is KtVal -> "val:${decl.name}"
        is KtObject -> "object:${decl.name ?: "?"}"
        is KtInterface -> "interface:${decl.name}"
        is KtEnum -> "enum:${decl.name}"
    }

    private fun resolveDeclDeps() {
        for ((_, node) in nodes) {
            node.depDeclIds.addAll(collectDeclRefs(node))
        }
    }

    private fun collectDeclRefs(node: DeclNode): Set<String> {
        val refs = mutableSetOf<String>()
        when (val body = node.bodyRef) {
            is KtFun -> body.body?.let { collectExprRefs(it, refs) }
            is KtClass -> body.members.forEach { m ->
                if (m is KtFun) m.body?.let { collectExprRefs(it, refs) }
            }
            is KtVal -> body.value?.let { collectExprRefs(it, refs) }
        }
        return refs
    }

    private fun collectExprRefs(expr: KtExpr, refs: MutableSet<String>) {
        when (expr) {
            is KtCall -> {
                collectTargetRef(expr.target, refs)
                expr.args.forEach { collectExprRefs(it, refs) }
            }
            is KtBinary -> {
                collectExprRefs(expr.left, refs)
                collectExprRefs(expr.right, refs)
            }
            is KtRef -> refs.add("*:val:${expr.name}")
            is KtMemberAccess -> {
                collectExprRefs(expr.target, refs)
                refs.add("*:fun:${expr.member}")
            }
            is KtLambda -> collectExprRefs(expr.body, refs)
            is KtIf -> {
                collectExprRefs(expr.cond, refs)
                collectExprRefs(expr.thenBranch, refs)
                expr.elseBranch?.let { collectExprRefs(it, refs) }
            }
            is KtBlock -> expr.statements.forEach {
                if (it is KtExpr) collectExprRefs(it, refs)
            }
            is KtReturn -> expr.value?.let { collectExprRefs(it, refs) }
            is KtWhen -> {
                expr.subject?.let { collectExprRefs(it, refs) }
                expr.branches.forEach {
                    collectExprRefs(it.condition, refs)
                    collectExprRefs(it.body, refs)
                }
                expr.elseBranch?.let { collectExprRefs(it, refs) }
            }
            else -> {}
        }
    }

    private fun collectTargetRef(target: KtExpr, refs: MutableSet<String>) {
        when (target) {
            is KtRef -> refs.add("*:fun:${target.name}")
            is KtMemberAccess -> refs.add("*:fun:${target.member}")
            else -> {}
        }
    }

    // ─── 浅提取 ───

    fun extractShallow(declId: String): DeclNode? {
        val node = nodes[declId] ?: return null
        if (node.isShallow) return node
        return node.copy(isShallow = true, bodyRef = null)
    }

    fun expand(declId: String): DeclNode? {
        val node = nodes[declId] ?: return null
        if (!node.isShallow) return node
        return node.copy(isShallow = false)
    }

    // ─── 传播 ───

    fun propagate(changedDeclIds: Set<String>): Set<String> {
        if (!isLive) return changedDeclIds
        val affected = mutableSetOf<String>()
        val visited = mutableSetOf<String>()
        val queue = changedDeclIds.toMutableList()

        while (queue.isNotEmpty()) {
            val current = queue.removeAt(0)
            if (current in visited) {
                extractShallow(current)
                affected.add(current)
                continue
            }
            visited.add(current)
            affected.add(current)
            if (nodes[current] == null) continue
            for ((otherId, otherNode) in nodes) {
                if (otherId in visited) continue
                if (otherNode.depDeclIds.any { matchesDecl(it, current) }) {
                    if (otherId !in queue) queue.add(otherId)
                }
            }
        }
        return affected
    }

    private fun matchesDecl(ref: String, declId: String): Boolean {
        val refParts = ref.split(":")
        val declParts = declId.split(":")
        if (refParts.size < 3 || declParts.size < 3) return false
        return (refParts[0] == "*" || refParts[0] == declParts[0]) &&
               refParts[2] == declParts[2]
    }

    // ─── 维护 ───

    fun registerOrUpdate(fileName: String, decl: KtDecl) {
        val node = toDeclNode(fileName, decl) ?: return
        nodes[node.declId] = node
        byFile.getOrPut(fileName) { mutableSetOf() }.add(node.declId)
        node.depDeclIds.clear()
        node.depDeclIds.addAll(collectDeclRefs(node))
    }

    fun invalidate(declId: String) {
        nodes.remove(declId)
        for ((_, declIds) in byFile) declIds.remove(declId)
    }

    fun invalidateFile(fileName: String) {
        val declIds = byFile[fileName] ?: return
        for (id in declIds) nodes.remove(id)
        byFile.remove(fileName)
    }

    // ─── 查询 ───

    fun getNode(declId: String): DeclNode? = nodes[declId]
    fun getByFile(fileName: String): List<DeclNode> =
        (byFile[fileName] ?: emptySet<String>()).mapNotNull { nodes[it] }
    fun totalDeclarations(): Int = nodes.size
    fun totalFiles(): Int = byFile.size
    fun isAffected(declId: String, changedSet: Set<String>): Boolean =
        propagate(changedSet).contains(declId)

    fun clear() {
        nodes.clear()
        byFile.clear()
        isLive = false
    }
}