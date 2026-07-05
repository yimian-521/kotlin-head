package com.qitong.head.parser

import com.qitong.head.ast.*
import com.qitong.head.checker.TypeChecker
import com.qitong.head.internal.Capabilities
import com.qitong.head.eventbus.DependencyGraph
import com.qitong.head.lexer.Token
import com.qitong.head.lexer.TokType
import com.qitong.head.lexer.TokType.*

class Parser(
    private val tokens: List<Token>,
    private val onRecover: ((String, () -> Any?) -> Any?)? = null
) {
    private var pos = 0

    // ─── 顶层 ───
    fun parseFile(): KtFile {
        parseDiags.clear()
        skipPackage()
        skipImports()
        val decls = mutableListOf<KtDecl>()
        while (!isEof()) {
            val anns = try { parseAnnotations() } catch (_: Exception) { emptyList<KtAnnotation>() }
            if (isEof()) break
            try { parseDeclaration(anns)?.let { decls += it } }
            catch (_: Exception) {
                val savedPos = pos
                val recovered = onRecover?.invoke("decl") {
                    pos = savedPos
                    try { parseDeclaration(anns) } catch (_: Exception) { null }
                }
                if (recovered != null) { decls += (recovered as? KtDecl) ?: continue }
                else { warnSkip("decl", "skip", autoFix = true); skipToNextDecl() }
            }
        }
        return KtFile(null, decls)
    }

    private fun skipPackage() {
        if (matchType(PACKAGE)) advance()
        // 读到 import 或声明开始（data class / fun / val / @ 等）
        // ★ v0.8.2-fix: 包声明可能是 com.qtwl.gateway.data.model，多个IDENT用DOT连接
        // IDENT 不能触发 break——它是包名的一部分，不是声明开始
        while (!isEof()) {
            val t = peek().type
            if (t == IMPORT || t == AT) break
            if (t == DATA && tokens.getOrNull(pos + 1)?.type == CLASS) break
            if (t == FUN || t == VAL || t == VAR || t == CLASS) break
            if (t == OBJECT || t == INTERFACE || t == ENUM || t == COMPANION) break
            advance()
        }
    }

    private fun skipImports() {
        while (matchType(IMPORT)) {
            advance()
            // v0.8.3: 收集 import 路径，注册到依赖图
            val sb = StringBuilder()
            val startPos = pos
            while (!isEof() && peek().type != IMPORT && !isDeclarationStart()) {
                sb.append(peek().text)
                advance()
            }
            val importPath = sb.toString()
            if (importPath.isNotEmpty()) {
                DependencyGraph.registerImport(importPath.replace(".", "/"), "current_file")
            }
        }
    }

    private fun parseAnnotations(): List<KtAnnotation> {
        val anns = mutableListOf<KtAnnotation>()
        while (matchType(AT)) {
            val start = peek().pos
            advance() // @
            val name = advance().text
            val args = mutableListOf<String>()
            if (checkType(LPAREN)) {
                advance()
                var depth = 1
                val sb = StringBuilder()
                while (depth > 0 && !isEof()) {
                    val t = peek()
                    when (t.type) {
                        LPAREN -> { sb.append(t.text); advance(); depth++ }
                        RPAREN -> {
                            depth--
                            if (depth > 0) { sb.append(t.text); advance() }
                            else advance()
                        }
                        COMMA -> {
                            if (depth == 1) {
                                args += sb.toString().trim()
                                sb.clear()
                                advance()
                            } else { sb.append(t.text); advance() }
                        }
                        else -> { sb.append(t.text); advance() }
                    }
                }
                val last = sb.toString().trim()
                if (last.isNotEmpty()) args += last
            }
            anns += KtAnnotation(name, args, Span(start, lastPos()))
        }
        return anns
    }

    private fun isDeclarationStart(t: TokType): Boolean =
        t == FUN || t == VAL || t == VAR || t == CLASS || t == AT || t == EOF
            || t == OBJECT || t == INTERFACE || t == ENUM || t == COMPANION

    private fun isDeclarationStart(): Boolean = isDeclarationStart(peek().type)

    // ─── 声明 ───
    private val parseDiags = mutableListOf<TypeChecker.Diag>()
    
    /** Parser 层的诊断（WARNING/SKIPPED/EXPECTED），最终合并到 Diagnostic */
    fun parserDiags(): List<TypeChecker.Diag> = parseDiags.toList()

    private fun warnSkip(reason: String, expected: String? = null, autoFix: Boolean = false) {
        val pos = peek().pos
        val level = if (expected != null) TypeChecker.DiagLevel.EXPECTED else TypeChecker.DiagLevel.WARN
        val prefix = if (autoFix) "🔧 [自动容错] " else ""
        val suffix = if (autoFix) "（不跳过将导致编译崩溃）" else ""
        val msg = if (expected != null) "${prefix}跳过: $reason —— 🔮 $expected$suffix"
                  else "${prefix}跳过: $reason$suffix"
        parseDiags += TypeChecker.Diag(level, msg, pos)
    }

    private fun parseDeclaration(anns: List<KtAnnotation> = emptyList()): KtDecl? {
        // ★ v0.4.3: 容忍修饰符
        while (peek().type == IDENT && peek().text in setOf("private", "internal", "public", "protected")) advance()
        // data class ...
        if (match("data")) {
            return try {
                val dataKw = advance()
                expect(CLASS); advance()
                val name = advance().text
                val start = dataKw.pos
                val members = if (check(LPAREN)) parsePrimaryCtorMembers() else emptyList<KtDecl>()
                val end = lastPos()
                KtClass(name, listOf("data", "public"), anns, members, Span(start, end))
            } catch (_: Exception) {
                warnSkip("data class声明不完整", "解析异常", autoFix = true)
                skipToNextDecl()
                KtClass("?data", emptyList(), emptyList(), emptyList(), Span(lastPos(), lastPos()))
            }
        }
        // fun ...（含 suspend 等前缀修饰符）
        if (matchType(FUN) || match("suspend")) return try { parseFun(anns) } catch (_: Exception) {
            val savedPos = pos
            val recovered = onRecover?.invoke("fun") { pos = savedPos; try { parseFun(anns) } catch (_: Exception) { null } }
            if (recovered != null) return recovered as KtDecl
            warnSkip("函数声明不完整(缺函数名/参数/返回值)", "解析异常", autoFix = true)
            skipToNextDecl()
            KtFun("?fun", emptyList(), null, null, emptyList(), emptyList(), Span(lastPos(), lastPos()))
        }
        // val / var ...
        if (matchType(VAL) || matchType(VAR)) return try { parseVal(anns) } catch (_: Exception) {
            val savedPos = pos
            val recovered = onRecover?.invoke("val") { pos = savedPos; try { parseVal(anns) } catch (_: Exception) { null } }
            if (recovered != null) return recovered as KtDecl
            warnSkip("属性声明不完整(缺变量名/类型/值)", "解析异常", autoFix = true)
            skipToNextDecl()
            KtVal("?val", null, null, emptyList(), emptyList(), Span(lastPos(), lastPos()))
        }
        // class (non-data) ...
        if (matchType(CLASS)) {
            warnSkip("class声明暂不支持(仅data class)", "v0.2.0将支持")
            skipToNextDecl()
            return KtClass("?class", emptyList(), emptyList(), emptyList(), Span(lastPos(), lastPos()))
        }
        // object / companion object ...
        if (matchType(OBJECT) || matchType(COMPANION)) return try { parseObject() } catch (_: Exception) {
            warnSkip("object", "解析异常", autoFix = true)
            skipToNextDecl()
            KtObject("?obj", false, emptyList(), Span(lastPos(), lastPos()))
        }
        // interface ...
        if (matchType(INTERFACE)) return try { parseInterface() } catch (_: Exception) {
            warnSkip("interface", "解析异常", autoFix = true)
            skipToNextDecl()
            KtInterface("?iface", emptyList(), Span(lastPos(), lastPos()))
        }
        // enum class ...
        if (matchType(ENUM)) return try { parseEnum() } catch (_: Exception) {
            warnSkip("enum", "解析异常", autoFix = true)
            skipToNextDecl()
            KtEnum("?enum", emptyList(), emptyList(), Span(lastPos(), lastPos()))
        }
        // 不认识的关键字 → 只跳当前 token，不连累后续
        val t = peek()
        warnSkip("不支持的语法: ${t.text}关键字", Capabilities.expectedFor(t.text))
        advance() // ★ 只跳一步——不调用 skipToNextDecl
        return KtVal("?unk", null, null, emptyList(), emptyList(), Span(t.pos, lastPos()))
    }

    private fun peekNext(): Token? = tokens.getOrNull(pos + 1)

    /** 跳到下一个声明开始，不抛异常。遇到花括号跳过整块（v0.11.1-a: 花括号泛滥检测+声明边界+独立上限） */
    private fun skipToNextDecl() {
        advance()
        var totalSkips = 0
        while (!isEof() && totalSkips < 2000) {
            totalSkips++
            val t = peek().type
            if (isDeclarationStart(t)) break
            if (t == LBRACE) {
                advance()
                var depth = 1
                var braceSkips = 0
                var consecLbrace = 0  // ★ 连续 LBRACE 计数器——检测花括号泛滥
                while (depth > 0 && !isEof() && totalSkips < 2000 && braceSkips < 300) {
                    if (depth > 60) { warnSkip("花括号嵌套过深(>$depth)", "略过整块"); break }
                    val tt = peek().type
                    if (isDeclarationStart(tt)) break
                    when (tt) {
                        LBRACE -> {
                            consecLbrace++
                            if (consecLbrace >= 5) { warnSkip("花括号泛滥(连续${consecLbrace}个{)", "防吞全文件", autoFix = true); advance(); depth = 0 }
                            advance(); depth++; totalSkips++; braceSkips++
                        }
                        RBRACE -> { consecLbrace = 0; advance(); depth--; totalSkips++; braceSkips++ }
                        else -> { consecLbrace = 0; advance(); totalSkips++; braceSkips++ }
                    }
                }
            } else advance()
        }
        if (totalSkips >= 2000) warnSkip("跳过超限(2000 token)，强制截断", "防吞全文件", autoFix = true)
    }

    private fun parsePrimaryCtorMembers(): List<KtDecl> {
        expect(LPAREN); advance()
        val members = mutableListOf<KtDecl>()
        while (!check(RPAREN)) {
            if (checkType(COMMA)) { advance(); continue }
            // 跳过注解
            if (checkType(AT)) {
                advance(); advance()
                if (checkType(LPAREN)) { advance(); var d=1; while(d>0&&!check(EOF)){when(peek().type){LPAREN->{advance();d++} RPAREN->{advance();d--} else->advance()} } }
                continue
            }
            // 跳过 val/var
            if (checkType(VAL) || checkType(VAR)) advance()
            val name = advance().text
            var type: String? = null
            if (checkType(COLON)) { advance(); type = readTypeName() }
            // 跳过默认值（可能包含函数调用如 System.currentTimeMillis()）
            if (checkType(EQ)) {
                advance() // =
                var depth = 0
                while (!isEof()) {
                    val tt = peek().type
                    if (tt == LPAREN) { advance(); depth++ }
                    else if (tt == RPAREN && depth > 0) { advance(); depth-- }
                    else if (tt == RPAREN || tt == COMMA) break
                    else advance()
                }
            }
            members += KtVal(name, type, null, emptyList(), emptyList(), Span(lastPos(), lastPos()))
        }
        expect(RPAREN); advance()
        return members
    }

    private fun parseFun(anns: List<KtAnnotation> = emptyList()): KtDecl {
        // ★ v0.7.0: 收集修饰符，不吞
        val mods = mutableListOf<String>()
        while (match("suspend") || match("override") || match("open") || match("abstract") || match("private") || match("internal") || match("public")) {
            mods += advance().text
        }
        val funKw = advance() // fun
        val name = advance().text
        expect(LPAREN); advance()
        val params = mutableListOf<KtParam>()
        while (!check(RPAREN)) {
            if (checkType(COMMA)) { advance(); continue }
            val pName = advance().text
            var pType: String? = null
            if (checkType(COLON)) { advance(); pType = readTypeName() }
            params += KtParam(pName, pType, Span(lastPos(), lastPos()))
            // ★ 跳过参数默认值 = expr
            if (checkType(EQ)) {
                advance() // =
                var depth = 0
                while (!isEof() && !(check(RPAREN) && depth == 0) && !(checkType(COMMA) && depth == 0)) {
                    val tt = peek().type
                    if (tt == LPAREN) { advance(); depth++ }
                    else if (tt == RPAREN) {
                        if (depth > 0) { advance(); depth-- }
                        else break
                    } else advance()
                }
            }
        }
        expect(RPAREN); advance()
        var returnType: String? = null
        if (checkType(COLON)) { advance(); returnType = readTypeName() }
        val body = if (checkType(EQ)) parseExprBody() else if (checkType(LBRACE)) parseBlockBody() else null
        return KtFun(name, params, returnType, body, mods, anns, Span(funKw.pos, lastPos()))
    }

    private fun parseExprBody(): KtExpr? {
        advance() // =
        return parseExpression()
    }

    private fun parseBlockBody(): KtExpr {
        val start = advance().pos  // {
        val stmts = mutableListOf<KtNode>()
        while (!check(RBRACE)) {
            when {
                checkType(VAL) || checkType(VAR) -> stmts += parseVal()
                matchType(RETURN) -> {
                    val kw = advance()
                    val v = if (check(RBRACE)) null else parseExpression()
                    stmts += KtReturn(v, Span(kw.pos, lastPos()))
                }
                else -> { val saved = pos; try { stmts += parseExpression() } catch (_: Exception) { pos = saved; advance() } }
            }
        }
        val end = advance().pos  // }
        return KtBlock(stmts, Span(start, end))
    }

    // ★ v0.7.0: 类型名（含跳过泛型参数，不吞≠不被识别）
    private fun readTypeName(): String {
        val name = advance().text
        if (checkType(LT)) skipBracketed()
        return name
    }

    /** 跳过 <...> —— 泛型参数，不进运算符 */
    private fun skipBracketed() {
        advance() // <
        var depth = 1
        while (depth > 0 && !isEof()) {
            when (peek().type) {
                LT -> { advance(); depth++ }
                GT -> { advance(); depth-- }
                else -> advance()
            }
        }
    }

    private fun parseVal(anns: List<KtAnnotation> = emptyList()): KtDecl {
        // ★ v0.7.0: 收集修饰符，不吞
        val mods = mutableListOf<String>()
        while (match("override") || match("private") || match("internal") || match("public") || match("open") || match("abstract") || match("lateinit") || match("const")) {
            mods += advance().text
        }
        val kw = advance() // val / var
        val name = advance().text
        var type: String? = null
        if (checkType(COLON)) { advance(); type = readTypeName() }
        var value: KtExpr? = null
        if (checkType(EQ)) { advance(); value = parseExpression() }
        return KtVal(name, type, value, mods, anns, Span(kw.pos, lastPos()))
    }

    // ─── v0.4 新增声明 ───
    private fun parseObject(): KtDecl {
        val isCompanion = if (matchType(COMPANION)) { advance(); true } else false
        val kw = if (isCompanion) advance() else advance() // object
        val name: String? = if (checkType(IDENT)) {
            val n = advance().text
            if (checkType(LBRACE)) n else { /* 不是名称，回退 */ pos--; null }
        } else null
        val start = kw.pos
        val members = if (checkType(LBRACE)) {
            advance() // {
            val ms = mutableListOf<KtDecl>()
            while (!check(RBRACE)) {
                parseAnnotations()
                if (check(RBRACE)) break
                parseDeclaration()?.let { ms += it }
                if (checkType(COMMA) || checkType(SEMICOLON)) advance()
            }
            advance() // }
            ms
                } else emptyList<KtDecl>()
        return KtObject(name, isCompanion, members, Span(start, lastPos()))
    }

    private fun parseInterface(): KtDecl {
        val kw = advance() // interface
        val name = advance().text
        val start = kw.pos
        val members = if (checkType(LBRACE)) {
            advance()
            val ms = mutableListOf<KtDecl>()
            while (!check(RBRACE)) {
                parseAnnotations()
                if (check(RBRACE)) break
                if (matchType(FUN)) {
                    parseAnnotations() // ★ parse + discard for now
                    val f = parseFun()
                    ms += f
                } else if (matchType(VAL) || matchType(VAR)) {
                    ms += parseVal()
                } else advance()
            }
            advance()
            ms
                } else emptyList<KtDecl>()
        return KtInterface(name, members, Span(start, lastPos()))
    }

    private fun parseEnum(): KtDecl {
        val kw = advance() // enum
        if (matchType(CLASS)) advance() // class
        val name = advance().text
        val start = kw.pos
        val constants = mutableListOf<String>()
        val members = mutableListOf<KtDecl>()
        if (checkType(LBRACE)) {
            advance()
            // 先读常量列表
            while (!check(RBRACE) && !checkType(SEMICOLON)) {
                if (checkType(COMMA)) { advance(); continue }
                if (checkType(IDENT)) { constants += advance().text; continue }
                advance()
            }
            if (checkType(SEMICOLON)) advance()
            // 再读成员
            while (!check(RBRACE)) {
                parseAnnotations()
                if (check(RBRACE)) break
                parseDeclaration()?.let { members += it }
            }
            advance()
        }
        return KtEnum(name, constants, members, Span(start, lastPos()))
    }

    // ─── 表达式（递归下降，按优先级） ───
    private var exprDepth = 0

    private fun parseExpression(): KtExpr {
        exprDepth++
        if (exprDepth > 500) throw error("表达式递归过深（可能泛型 <T> 被误作运算符）")
        try {
            return parseFor()
        } finally {
            exprDepth--
        }
    }

    private fun parseWhen(): KtExpr {
        if (!matchType(WHEN)) return parseFor()
        val kw = advance() // when
        val subject = if (checkType(LPAREN)) {
            advance(); val s = parseExpression()
            expect(RPAREN); advance(); s
        } else null
        expect(LBRACE); advance()
        val branches = mutableListOf<KtWhenBranch>()
        var elseBranch: KtExpr? = null
        while (!check(RBRACE)) {
            if (match("else")) {
                advance()
                expect(ARROW); advance()
                elseBranch = if (checkType(LBRACE)) parseBlockBody() else parseExpression()
                break
            }
            val cond = parseExpression()
            expect(ARROW); advance()
            val body = if (checkType(LBRACE)) parseBlockBody() else parseExpression()
            branches += KtWhenBranch(cond, body, Span(kw.pos, lastPos()))
            if (checkType(COMMA)) advance()
        }
        advance() // }
        return KtWhen(subject, branches, elseBranch, Span(kw.pos, lastPos()))
    }
    
    private fun parseFor(): KtExpr {
        if (!matchType(FOR)) return parseWhile()
        advance() // for
        expect(LPAREN); advance()
        val variable = advance().text // 变量名
        if (match("in")) advance() // in
        val iterable = parseExpression()
        expect(RPAREN); advance()
        val body = if (checkType(LBRACE)) parseBlockBody() else parseExpression()
        return KtFor(variable, iterable, body, Span(lastPos(), lastPos()))
    }
    
    private fun parseWhile(): KtExpr {
        if (!matchType(WHILE)) return parseIf()
        val kw = advance() // while
        expect(LPAREN); advance()
        val cond = parseExpression()
        expect(RPAREN); advance()
        val body = if (checkType(LBRACE)) parseBlockBody() else parseExpression()
        return KtWhile(cond, body, Span(kw.pos, lastPos()))
    }

    private fun parseIf(): KtExpr {
        if (!matchType(IF)) return parseBinary()
        val ifKw = advance()
        expect(LPAREN); advance()
        val cond = parseExpression()
        expect(RPAREN); advance()
        val thenBlock = if (checkType(LBRACE)) {
            parseBlockBody()
        } else parseExpression()
        var elseBlock: KtExpr? = null
        if (matchType(ELSE)) {
            advance()
            elseBlock = if (checkType(LBRACE)) parseBlockBody() else parseExpression()
        }
        return KtIf(cond, thenBlock, elseBlock, Span(ifKw.pos, lastPos()))
    }

    private fun parseBinary(minPrec: Int = 0): KtExpr {
        var left = parsePrimary()
        while (true) {
            val prec = curPrec() ?: break
            if (prec < minPrec) break
            val op = advance().text
            val right = parseBinary(prec + 1)
            left = KtBinary(left, op, right, Span(left.span.start, right.span.end))
        }
        return left
    }

    // ─── 原子表达式 ───
    private fun parsePrimary(): KtExpr {
        val t = peek()
        return when (t.type) {
            INT_LIT -> { advance(); KtLitInt(t.text.toInt(), Span(t.pos, lastPos())) }
            STR_LIT -> { advance(); KtLitStr(t.text, Span(t.pos, lastPos())) }
            BOOL_LIT -> { advance(); KtLitBool(t.text == "true", Span(t.pos, lastPos())) }
            NOT -> {
            val start = peek().pos
            advance() // !
            val operand = parseExpression() // 高优先级，NOT作为前缀
            KtPrefixExpr("!", operand, Span(start, operand.span.end))
        }
RETURN -> {
                val kw = advance()
                val v = if (check(RBRACE) || isEof()) null else parseExpression()
                KtReturn(v, Span(kw.pos, lastPos()))
            }
            TRY -> {
                val kw = advance() // try
                warnSkip("try-catch", "v0.5.0")
                // 跳过 try 体和 catch/finally
                var depth = 0
                while (!isEof()) {
                    val tt = peek().type
                    if (tt == LBRACE) { advance(); depth++ }
                    else if (tt == RBRACE) {
                        advance()
                        if (depth > 0) depth--
                        if (depth == 0 && !matchType(CATCH) && !matchType(FINALLY)) break
                    } else advance()
                }
                KtLitBool(true, Span(kw.pos, lastPos()))
            }
            WHEN -> parseWhen() // ★ v0.4.5: 小分支套入，不再走长链
            IF -> parseIf()     // ★ v0.4.5: 同上
            FOR -> parseFor()   // ★ v0.4.5: 同上
            WHILE -> parseWhile() // ★ v0.4.5: 同上
            IDENT -> {
                val start = t.pos
                advance() // 读第一个标识符
                var expr: KtExpr = KtRef(t.text, null, Span(start, lastPos()))
                // ★ v0.7.0: 泛型 — 从源头不被识别为变量名
                if (checkType(LT)) {
                    val ta = parseTypeArgs()
                    expr = (expr as KtRef).copy(typeArgs = ta)
                }
                // 函数调用
                if (checkType(LPAREN)) expr = parseCall(expr)
                if (checkType(LBRACK)) { advance(); var bd=1; while(bd>0&&!isEof()){when(peek().type){LBRACK->{advance();bd++} RBRACK->{advance();bd--} else->advance()}} }
                // ★ v0.11.0: 安全调用 ?.——? 被 Lexer 拆成 IDENT(?)，while 条件内统一处理
                // ★ v0.11.1: DOT + ?. 平等对待——while 条件同时检查两种接入方式
                while (checkType(DOT) || (checkType(IDENT) && peek().text == "?" && peekNext()?.type == DOT)) {
                    val isSafe = checkType(IDENT) && peek().text == "?"
                    if (isSafe) advance() // 吞 ?
                    advance() // 吞 DOT
                    if (checkType(IDENT)) {
                        val member = advance().text
                        var right: KtExpr = KtRef(member, null, Span(start, lastPos()))
                        if (checkType(LT)) {
                            val ta = parseTypeArgs()
                            right = (right as KtRef).copy(typeArgs = ta)
                        }
                        if (checkType(LPAREN)) right = parseCall(right)
                        expr = KtMemberAccess(expr, member, Span(expr.span.start, right.span.end), safeAccess = isSafe)
                    } else break
                }
                expr
            }
            LPAREN -> parseParenOrLambda()
            LBRACE -> parseLambda()
            DOT -> {
                // ★ v0.4.4: 表达式级成员访问残留 DOT 容忍
                warnSkip("DOT后缀", "表达式级成员访问")
                advance() // DOT
                if (checkType(IDENT)) advance() // 成员名
                KtLitBool(true, Span(t.pos, lastPos()))
            }
            else -> {
                val t = peek()
                warnSkip("${t.type} ${t.text}", "表达式容错", autoFix = true)
                advance()
                KtLitBool(true, Span(t.pos, lastPos()))
            }
        }
    }

    // ★ v0.7.0: 泛型参数 <T, U, Map<String, Int>>
    private fun parseTypeArgs(): List<KtRef>? {
        // 前看确认：< 后面是 IDENT，IDENT 后面是 > 或 , → 是泛型；否则不是
        val afterLt = tokens.getOrNull(pos + 1)
        val afterAfter = tokens.getOrNull(pos + 2)
        if (afterLt?.type != IDENT) return null
        if (afterAfter?.type != GT && afterAfter?.type != COMMA) return null
        // 确认是泛型，开始解析
        advance() // <
        val args = mutableListOf<KtRef>()
        while (!check(GT) && !isEof()) {
            if (checkType(COMMA)) { advance(); continue }
            if (checkType(IDENT)) {
                val argName = advance().text
                // 嵌套泛型如 Map<String, Int>
                val nested = if (checkType(LT)) parseTypeArgs() else null
                args += KtRef(argName, nested, Span(lastPos(), lastPos()))
            } else break
        }
        expect(GT); advance()
        return args
    }

    private fun parseCall(target: KtExpr): KtExpr {
        expect(LPAREN); advance()
        val args = mutableListOf<KtExpr>()
        while (!check(RPAREN)) {
            if (checkType(COMMA)) { advance(); continue }
            // ★ 命名参数拦截：IDENT = value → 吃 IDENT+EQ，只存值
            if (checkType(IDENT) && peekNext()?.type == EQ) {
                advance() // 参数名
                advance() // EQ
                args += parseExpression()
                continue
            }
            args += parseExpression()
        }
        expect(RPAREN); advance()
        if (checkType(LBRACE)) args += parseLambda()
        return KtCall(target, args, Span(target.span.start, lastPos()))
    }

    private fun parseParenOrLambda(): KtExpr {
        val lpPos = advance().pos
        if (checkType(RPAREN)) { advance(); return parseLambdaTail() }
        val inner = parseExpression()
        if (checkType(RPAREN)) {
            advance()
            return if (checkType(ARROW)) parseLambdaTail(inner) else inner
        }
        throw error("expected ')'")
    }

    private fun parseLambda(): KtExpr {
        val start = advance().pos // {
        return parseLambdaTail()
    }

    private fun parseLambdaTail(captured: KtExpr? = null): KtExpr {
        val start = lastPos()
        val body = if (checkType(ARROW)) {
            advance() // ->
            parseExpression()
        } else {
            // ★ v0.4.5: 隐式 lambda，无 -> 直接解析体
            parseExpression()
        }
        expect(RBRACE); advance()
        val params = if (captured != null) {
            listOf(KtParam("it", null, Span(captured.span.start, captured.span.end)))
        } else emptyList<KtParam>()
        return KtLambda(params, body, Span(start, lastPos()))
    }

    // ─── 辅助 ───
    private fun peek() = if (pos < tokens.size) tokens[pos] else tokens.last()
    private fun advance() = if (pos < tokens.size) tokens[pos++] else tokens.last().also { pos++ }
    private fun isEof() = pos >= tokens.size || peek().type == EOF
    private fun check(type: TokType) = !isEof() && peek().type == type
    private fun checkType(type: TokType) = check(type)
    private fun match(text: String) = peek().text == text
    private fun matchType(type: TokType) = peek().type == type
    private fun expect(type: TokType) { if (!check(type)) throw error("expected $type, got ${peek().type}") }
    private fun lastPos(): Pos = if (pos > 0) tokens[pos - 1].pos else Pos(1, 1)
    private fun error(msg: String): Nothing =
        throw ParseException("$msg at ${peek().pos}", peek().pos)

    // 运算符优先级
    private fun curPrec(): Int? = when (peek().type) {
        OR -> 1
        ELVIS -> 2
        AND -> 3
        EQEQ, BANGEQ -> 4
        LT, GT, LTEQ, GTEQ -> 5
        AS -> 6
        PLUS, MINUS -> 7
        STAR, SLASH -> 8
        else -> null
    }
}

class ParseException(msg: String, val pos: Pos) : RuntimeException(msg)