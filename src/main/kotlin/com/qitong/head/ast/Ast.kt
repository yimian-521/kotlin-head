package com.qitong.head.ast

/** 源码位置：行号从 1 开始，列号从 1 开始 */
data class Pos(val line: Int, val col: Int) {
    override fun toString() = "$line:$col"
}

/** 源码范围 */
data class Span(val start: Pos, val end: Pos) {
    override fun toString() = "$start-$end"
}

/** AST 节点基类。每个节点知道自己在哪。 */
sealed class KtNode(val span: Span)

// ─── 顶层 ───
/** 一个 .kt 文件 = 包声明 + 零或多个顶层声明 */
data class KtFile(val pkg: String?, val declarations: List<KtDecl>) : KtNode(
    Span(Pos(1, 1), Pos(1, 1)) // 文件级，范围由外部设置
)

// ─── 声明 ───
sealed class KtDecl(span: Span) : KtNode(span)

data class KtClass(
    val name: String,
    val modifiers: List<String>,
    val annotations: List<KtAnnotation>,  // v0.8.0
    val members: List<KtDecl>,
    val classSpan: Span
) : KtDecl(classSpan)

data class KtFun(
    val name: String,
    val params: List<KtParam>,
    val returnType: String?,
    val body: KtExpr?,
    val modifiers: List<String>,
    val annotations: List<KtAnnotation>,  // v0.8.0
    val funSpan: Span
) : KtDecl(funSpan)

data class KtVal(
    val name: String,
    val type: String?,
    val value: KtExpr?,
    val modifiers: List<String>,
    val annotations: List<KtAnnotation>,  // v0.8.0
    val valSpan: Span
) : KtDecl(valSpan)

data class KtParam(
    val name: String,
    val type: String?,
    val paramSpan: Span
)

// ─── 表达式 ───
sealed class KtExpr(span: Span) : KtNode(span)

data class KtLitInt(val value: Int, val litSpan: Span) : KtExpr(litSpan)
data class KtLitStr(val value: String, val litSpan: Span) : KtExpr(litSpan)
data class KtLitBool(val value: Boolean, val litSpan: Span) : KtExpr(litSpan)

data class KtRef(val name: String, val typeArgs: List<KtRef>? = null, val refSpan: Span) : KtExpr(refSpan)

data class KtBinary(
    val left: KtExpr,
    val op: String,       // + - * / == != && || > < ...
    val right: KtExpr,
    val binSpan: Span
) : KtExpr(binSpan)

data class KtCall(
    val target: KtExpr,       // 函数引用
    val args: List<KtExpr>,
    val callSpan: Span
) : KtExpr(callSpan)

// ★ v0.7.0: 链式成员访问 a.b().c — 递归嵌套，结构就是记忆
// ★ v0.11.1: safeAccess 标记 ?. 安全调用——TypeChecker 据此推断可空链
data class KtMemberAccess(
    val target: KtExpr,       // 左边的表达式
    val member: String,       // 右边的成员名
    val accessSpan: Span,
    val safeAccess: Boolean = false  // true 表示 ?. 而非 .
) : KtExpr(accessSpan)

data class KtIf(
    val cond: KtExpr,
    val thenBranch: KtExpr,
    val elseBranch: KtExpr?,
    val ifSpan: Span
) : KtExpr(ifSpan)

data class KtLambda(
    val params: List<KtParam>,
    val body: KtExpr,
    val lambdaSpan: Span
) : KtExpr(lambdaSpan)

data class KtReturn(
    val value: KtExpr?,
    val returnSpan: Span
) : KtExpr(returnSpan)

data class KtBlock(
    val statements: List<KtNode>,  // KtDecl | KtExpr
    val blockSpan: Span
) : KtExpr(blockSpan)

// ─── v0.4 新增 ───
data class KtWhen(
    val subject: KtExpr?,         // when(x) 中的 x，或 null（when { }）
    val branches: List<KtWhenBranch>,
    val elseBranch: KtExpr?,
    val whenSpan: Span
) : KtExpr(whenSpan)

data class KtWhenBranch(
    val condition: KtExpr,
    val body: KtExpr,
    val branchSpan: Span
)

data class KtFor(
    val variable: String,
    val iterable: KtExpr,
    val body: KtExpr,
    val forSpan: Span
) : KtExpr(forSpan)

data class KtWhile(
    val condition: KtExpr,
    val body: KtExpr,
    val whileSpan: Span
) : KtExpr(whileSpan)

data class KtObject(
    val name: String?,
    val isCompanion: Boolean,
    val members: List<KtDecl>,
    val objectSpan: Span
) : KtDecl(objectSpan)

data class KtInterface(
    val name: String,
    val members: List<KtDecl>,
    val interfaceSpan: Span
) : KtDecl(interfaceSpan)

data class KtEnum(
    val name: String,
    val constants: List<String>,
    val members: List<KtDecl>,
    val enumSpan: Span
) : KtDecl(enumSpan)

data class KtPrefixExpr(
    val op: String,
    val operand: KtExpr,
    val prefixSpan: Span
) : KtExpr(prefixSpan)

// ─── v0.8.0 注解 ───
data class KtAnnotation(
    val name: String,
    val args: List<String>,   // 注解参数（简化：字符串列表）
    val annSpan: Span
) : KtNode(annSpan)
