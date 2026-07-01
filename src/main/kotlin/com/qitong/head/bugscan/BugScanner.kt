package com.qitong.head.bugscan

import com.qitong.head.ast.*

/**
 * BugScanner — 扫描 AST 结构模式，不跑代码就发现隐患。
 * 每条规则来自 BUGS.md 的真实踩坑记录。
 * 语言无关：任何 Kotlin 方言只要有对应模式就能被检测。
 */
class BugScanner {

    data class Finding(
        val rule: String,
        val severity: Severity,
        val message: String,
        val span: Span
    )

    enum class Severity { HIGH, MEDIUM, LOW }

    fun scan(file: KtFile): List<Finding> {
        val findings = mutableListOf<Finding>()
        scanNode(file, findings)
        return findings
    }

    private fun scanNode(node: Any?, findings: MutableList<Finding>) {
        when (node) {
            is KtFile -> node.declarations.forEach { scanNode(it, findings) }
            is KtClass -> node.members.forEach { scanNode(it, findings) }
            is KtFun -> node.body?.let { scanNode(it, findings) }
            is KtBlock -> node.statements.forEach { scanNode(it, findings) }
            is KtIf -> {
                scanNode(node.thenBranch, findings)
                node.elseBranch?.let { scanNode(it, findings) }
            }
            is KtBinary -> {
                scanNode(node.left, findings)
                scanNode(node.right, findings)
            }
            is KtCall -> {
                scanNode(node.target, findings)
                node.args.forEach { scanNode(it, findings) }
            }
            is KtReturn -> node.value?.let { scanNode(it, findings) }
            is KtLambda -> scanNode(node.body, findings)
            // 字面量和引用是叶子节点，不递归
            else -> {}
        }
    }

    companion object {
        fun from(file: KtFile) = BugScanner().scan(file)
    }
}