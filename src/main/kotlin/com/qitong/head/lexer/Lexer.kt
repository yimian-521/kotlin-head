package com.qitong.head.lexer

import com.qitong.head.ast.Pos
import com.qitong.head.runtime.HList
import com.qitong.head.runtime.HMap

enum class TokType {
    FUN, VAL, VAR, CLASS, DATA, IF, ELSE, RETURN,
    WHEN, FOR, WHILE, OBJECT, INTERFACE, ENUM, COMPANION, OVERRIDE,
    IMPORT, PACKAGE, AS, TRY, CATCH, FINALLY,
    INT_LIT, STR_LIT, BOOL_LIT, IDENT,
    PLUS, MINUS, STAR, SLASH, AND, OR, NOT,
    EQ, EQEQ, BANGEQ, LT, GT, LTEQ, GTEQ, ELVIS,
    LPAREN, RPAREN, LBRACE, RBRACE, LBRACK, RBRACK,
    COLON, COMMA, DOT, ARROW, SEMICOLON, AT, EOF
}

data class Token(val type: TokType, val text: String, val pos: Pos)

class Lexer(private val src: String) {
    private var i = 0
    private var line = 1
    private var col = 1

    private val keywords: HMap<String, TokType> = HMap<String, TokType>().apply {
        put("fun", TokType.FUN); put("val", TokType.VAL); put("var", TokType.VAR)
        put("class", TokType.CLASS); put("data", TokType.DATA)
        put("if", TokType.IF); put("else", TokType.ELSE); put("return", TokType.RETURN)
        put("when", TokType.WHEN); put("for", TokType.FOR); put("while", TokType.WHILE)
        put("object", TokType.OBJECT); put("interface", TokType.INTERFACE)
        put("enum", TokType.ENUM); put("companion", TokType.COMPANION)
        put("override", TokType.OVERRIDE)
        put("import", TokType.IMPORT); put("package", TokType.PACKAGE); put("as", TokType.AS)
        put("try", TokType.TRY); put("catch", TokType.CATCH); put("finally", TokType.FINALLY)
        put("true", TokType.BOOL_LIT); put("false", TokType.BOOL_LIT)
    }

    fun tokenize(): List<Token> {
        val tokens = HList<Token>()
        while (i < src.length) {
            val start = Pos(line, col)
            when (val c = peek()) {
                ' ', '\t' -> advance()
                '\n' -> { advance(); line++; col = 1 }
                '/' -> {
                    advance()
                    when (peek()) {
                        '/' -> skipLineComment()
                        '*' -> skipBlockComment()
                        else -> tokens.add(tok(TokType.SLASH, "/", start))
                    }
                }
                '@' -> { advance(); tokens.add(tok(TokType.AT, "@", start)) }
                '"' -> tokens.add(readString(start))
                in '0'..'9' -> tokens.add(readNumber(start))
                in 'a'..'z', in 'A'..'Z', '_' -> tokens.add(readIdent(start))
                '+' -> { advance(); tokens.add(tok(TokType.PLUS, "+", start)) }
                '-' -> {
                    advance()
                    tokens.add(if (peek() == '>') { advance(); tok(TokType.ARROW, "->", start) }
                    else tok(TokType.MINUS, "-", start))
                }
                '*' -> { advance(); tokens.add(tok(TokType.STAR, "*", start)) }
                '&' -> {
                    advance()
                    tokens.add(if (peek() == '&') { advance(); tok(TokType.AND, "&&", start) }
                    else tok(TokType.IDENT, "&", start))
                }
                '|' -> {
                    advance()
                    tokens.add(if (peek() == '|') { advance(); tok(TokType.OR, "||", start) }
                    else tok(TokType.IDENT, "|", start))
                }
                '(' -> { advance(); tokens.add(tok(TokType.LPAREN, "(", start)) }
                ')' -> { advance(); tokens.add(tok(TokType.RPAREN, ")", start)) }
                '{' -> { advance(); tokens.add(tok(TokType.LBRACE, "{", start)) }
                '}' -> { advance(); tokens.add(tok(TokType.RBRACE, "}", start)) }
                '[' -> { advance(); tokens.add(tok(TokType.LBRACK, "[", start)) }
                ']' -> { advance(); tokens.add(tok(TokType.RBRACK, "]", start)) }
                ':' -> { advance(); tokens.add(tok(TokType.COLON, ":", start)) }
                ',' -> { advance(); tokens.add(tok(TokType.COMMA, ",", start)) }
                '.' -> { advance(); tokens.add(tok(TokType.DOT, ".", start)) }
                ';' -> { advance(); tokens.add(tok(TokType.SEMICOLON, ";", start)) }
                '=' -> tokens.add(readEq(start))
                '!' -> tokens.add(readBang(start))
                '<' -> tokens.add(readLt(start))
                '>' -> tokens.add(readGt(start))
                '?' -> tokens.add(readQ(start))
                else -> advance()
            }
        }
        tokens.add(Token(TokType.EOF, "", Pos(line, col)))
        return tokens.toList()
    }

    private fun peek(offset: Int = 0): Char? = src.getOrNull(i + offset)
    private fun advance(): Char? { val c = src.getOrNull(i++) ?: return null; col++; return c }
    private fun tok(type: TokType, text: String, pos: Pos) = Token(type, text, pos)

    private fun skipLineComment() { while (i < src.length && src[i] != '\n') i++ }
    private fun skipBlockComment(): Boolean {
        advance()
        while (i < src.length) {
            if (src[i] == '*' && src.getOrNull(i + 1) == '/') { i += 2; col += 2; return true }
            if (src[i] == '\n') { line++; col = 1 } else col++
            i++
        }
        return false  // ★ 未闭合——不吞到末尾，标记失败
    }

    private fun readString(start: Pos): Token {
        advance()
        val sb = StringBuilder()
        while (i < src.length && src[i] != '"') {
            if (src[i] == '\\') { i++; sb.append('\\') }
            sb.append(src[i]); i++; col++
        }
        if (i < src.length) advance()
        return Token(TokType.STR_LIT, "\"$sb\"", start)
    }

    private fun readNumber(start: Pos): Token {
        val sb = StringBuilder()
        while (i < src.length && src[i] in '0'..'9') { sb.append(src[i]); advance() }
        return Token(TokType.INT_LIT, sb.toString(), start)
    }

    private fun readIdent(start: Pos): Token {
        val sb = StringBuilder()
        while (i < src.length && (src[i].isLetterOrDigit() || src[i] == '_')) { sb.append(src[i]); advance() }
        val text = sb.toString()
        val type = keywords.getExact(text) ?: TokType.IDENT
        return Token(type, text, start)
    }

    private fun readEq(start: Pos): Token {
        advance()
        return if (peek() == '=') { advance(); tok(TokType.EQEQ, "==", start) } else tok(TokType.EQ, "=", start)
    }
    private fun readBang(start: Pos): Token {
        advance()
        return if (peek() == '=') { advance(); tok(TokType.BANGEQ, "!=", start) } else tok(TokType.NOT, "!", start)
    }
    private fun readLt(start: Pos): Token {
        advance()
        return if (peek() == '=') { advance(); tok(TokType.LTEQ, "<=", start) } else tok(TokType.LT, "<", start)
    }
    private fun readGt(start: Pos): Token {
        advance()
        return if (peek() == '=') { advance(); tok(TokType.GTEQ, ">=", start) } else tok(TokType.GT, ">", start)
    }
    private fun readQ(start: Pos): Token {
        advance()
        return if (peek() == ':') { advance(); tok(TokType.ELVIS, "?:", start) } else tok(TokType.IDENT, "?", start)
    }
}