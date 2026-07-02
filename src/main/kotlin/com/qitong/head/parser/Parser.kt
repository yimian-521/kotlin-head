package com.qitong.head.parser

import com.qitong.head.ast.*
import com.qitong.head.checker.TypeChecker
import com.qitong.head.internal.Capabilities
import com.qitong.head.lexer.Token
import com.qitong.head.lexer.TokType
import com.qitong.head.lexer.TokType.*

class Parser(private val tokens: List<Token>) {
    private var pos = 0

    // ─── 顶层 ───
    fun parseFile(): KtFile {
        parseDiags.clear()
        skipPackage()
        skipImports()
        val decls = mutableListOf<KtDecl>()
        while (!isEof()) {
            skipAnnotations()
            if (isEof()) break
            parseDeclaration()?.let { decls += it }
        }
        return KtFile(null, decls)
    }

    private fun skipPackage() {
        if (matchType(PACKAGE)) advance()
        // 读到 import 或声明开始（data class / fun / val / @ 等）
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
            while (!isEof() && peek().type != IMPORT && !isDeclarationStart()) advance()
        }
    }

    private fun skipAnnotations() {
        while (matchType(AT)) {
            advance() // @
            advance() // 注解名
            if (checkType(LPAREN)) {
                advance()
                var depth = 1
                while (depth > 0 && !isEof()) {
                    when (peek().type) {
                        LPAREN -> { advance(); depth++ }
                        RPAREN -> { advance(); depth-- }
                        else -> advance()
                    }
                }
            }
        }
    }

    private fun isDeclarationStart(): Boolean {
        val t = peek().type
        return t == FUN || t == VAL || t == VAR || t == CLASS || t == AT || t == EOF
            || t == OBJECT || t == INTERFACE || t == ENUM || t == COMPANION
            || (t == DATA && tokens.getOrNull(pos + 1)?.type == CLASS)
    }

    // ─── 声明 ───
    private val parseDiags = mutableListOf<TypeChecker.Diag>()
    
    /** Parser 层的诊断（WARNING/SKIPPED/EXPECTED），最终合并到 Diagnostic */
    fun parserDiags(): List<TypeChecker.Diag> = parseDiags.toList()

    private fun warnSkip(reason: String, expected: String? = null) {
        val pos = peek().pos
        val level = if (expected != null) TypeChecker.DiagLevel.EXPECTED else TypeChecker.DiagLevel.WARN
        val msg = if (expected != null) "跳过: $reason —— 🔮 $expected" else "跳过: $reason"
        parseDiags += TypeChecker.Diag(level, msg, pos)
    }

    private fun parseDeclaration(): KtDecl? {
        // ★ v0.4.3: 容忍修饰符
        while (peek().type == IDENT && peek().text in setOf("private", "internal", "public", "protected")) advance()
        // data class ...
        if (match("data")) {
            val dataKw = advance()
            expect(CLASS); advance()
            val name = advance().text
            val start = dataKw.pos
            val members = if (check(LPAREN)) parsePrimaryCtorMembers() else emptyList<KtDecl>()
            val end = lastPos()
            return KtClass(name, listOf("data", "public"), members, Span(start, end))
        }
        // fun ...
        if (matchType(FUN)) return parseFun()
        // val / var ...
        if (matchType(VAL) || matchType(VAR)) return parseVal()
        // class (non-data) ...
        if (matchType(CLASS)) {
            warnSkip("class ${peekNext()?.text ?: "?"}", "v0.2.0")
            skipToNextDecl()
            return null
        }
        // object / companion object ...
        if (matchType(OBJECT) || matchType(COMPANION)) return parseObject()
        // interface ...
        if (matchType(INTERFACE)) return parseInterface()
        // enum class ...
        if (matchType(ENUM)) return parseEnum()
        // 不认识的关键字 → 跳过整段
        val t = peek()
        warnSkip("${t.type} ${t.text}", Capabilities.expectedFor(t.text))
        skipToNextDecl()
        return null
    }

    private fun peekNext(): Token? = tokens.getOrNull(pos + 1)

    /** 跳到下一个声明开始，不抛异常 */
    private fun skipToNextDecl() {
        while (!isEof()) {
            val t = peek().type
            if (t == DATA || t == CLASS || t == FUN || t == VAL || t == VAR || t == AT || t == EOF) break
            advance()
        }
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
            if (checkType(COLON)) { advance(); type = advance().text }
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
            members += KtVal(name, type, null, Span(lastPos(), lastPos()))
        }
        expect(RPAREN); advance()
        return members
    }

    private fun parseFun(): KtDecl {
        // ★ v0.4.1: 容忍 suspend/override/open 等修饰符
        while (match("suspend") || match("override") || match("open") || match("abstract") || match("private") || match("internal") || match("public")) {
            advance()
        }
        val funKw = advance() // fun
        val name = advance().text
        expect(LPAREN); advance()
        val params = mutableListOf<KtParam>()
        while (!check(RPAREN)) {
            if (checkType(COMMA)) { advance(); continue }
            val pName = advance().text
            var pType: String? = null
            if (checkType(COLON)) { advance(); pType = advance().text }
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
        if (checkType(COLON)) { advance(); returnType = advance().text }
        val body = if (checkType(EQ)) parseExprBody() else if (checkType(LBRACE)) parseBlockBody() else null
        return KtFun(name, params, returnType, body, Span(funKw.pos, lastPos()))
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
                else -> stmts += parseExpression()
            }
        }
        val end = advance().pos  // }
        return KtBlock(stmts, Span(start, end))
    }

    private fun parseVal(): KtDecl {
        // ★ v0.4.1: 容忍 override/private 等修饰符
        while (match("override") || match("private") || match("internal") || match("public") || match("open") || match("abstract") || match("lateinit") || match("const")) {
            advance()
        }
        val kw = advance() // val / var
        val name = advance().text
        var type: String? = null
        if (checkType(COLON)) { advance(); type = advance().text }
        var value: KtExpr? = null
        if (checkType(EQ)) { advance(); value = parseExpression() }
        return KtVal(name, type, value, Span(kw.pos, lastPos()))
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
                skipAnnotations()
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
                skipAnnotations()
                if (check(RBRACE)) break
                if (matchType(FUN)) {
                    skipAnnotations() // ★ 跳过 @Query 等注解
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
                skipAnnotations()
                if (check(RBRACE)) break
                parseDeclaration()?.let { members += it }
            }
            advance()
        }
        return KtEnum(name, constants, members, Span(start, lastPos()))
    }

    // ─── 表达式（递归下降，按优先级） ───
    private fun parseExpression(): KtExpr = parseWhen()
    
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
        IDENT -> {
                val start = t.pos
                val sb = StringBuilder(t.text)
                advance()
                // ★ v0.4.2: 链式成员访问 a.b.c()
                while (checkType(DOT)) {
                    advance() // DOT
                    if (checkType(IDENT)) {
                        sb.append('.').append(advance().text)
                    } else break
                }
                val fullName = sb.toString()
                if (checkType(LPAREN)) parseCall(KtRef(fullName, Span(start, lastPos())))
                else KtRef(fullName, Span(start, start))
            }
            LPAREN -> parseParenOrLambda()
            LBRACE -> parseLambda()
            else -> throw error("unexpected token: ${t.type} (${t.text})")
        }
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
        expect(ARROW); advance()
        val body = parseExpression()
        expect(RBRACE); advance()
        val params = if (captured != null) {
            listOf(KtParam("it", null, Span(captured.span.start, captured.span.end)))
                } else emptyList<KtParam>()
        return KtLambda(params, body, Span(start, lastPos()))
    }

    // ─── 辅助 ───
    private fun peek() = tokens[pos]
    private fun advance() = tokens[pos++]
    private fun isEof() = peek().type == EOF
    private fun check(type: TokType) = peek().type == type
    private fun checkType(type: TokType) = peek().type == type
    private fun match(text: String) = peek().text == text
    private fun matchType(type: TokType) = peek().type == type
    private fun expect(type: TokType) { if (!check(type)) throw error("expected $type, got ${peek().type}") }
    private fun lastPos(): Pos = if (pos > 0) tokens[pos - 1].pos else Pos(1, 1)
    private fun error(msg: String): Nothing =
        throw ParseException("$msg at ${peek().pos}", peek().pos)

    // 运算符优先级
    private fun curPrec(): Int? = when (peek().type) {
        OR -> 1
        AND -> 2
        EQEQ, BANGEQ -> 3
        LT, GT, LTEQ, GTEQ -> 4
        AS -> 5
        PLUS, MINUS -> 6
        STAR, SLASH -> 7
        else -> null
    }
}

class ParseException(msg: String, val pos: Pos) : RuntimeException(msg)