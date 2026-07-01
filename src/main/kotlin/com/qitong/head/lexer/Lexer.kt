package com.qitong.head.lexer

import com.qitong.head.ast.Pos

enum class TokType {
    FUN, VAL, VAR, CLASS, DATA, IF, ELSE, RETURN,
    IMPORT, PACKAGE,
    INT_LIT, STR_LIT, BOOL_LIT,
    IDENT,
    PLUS, MINUS, STAR, SLASH,
    EQ, EQEQ, BANGEQ,
    LT, GT, LTEQ, GTEQ,
    LPAREN, RPAREN,
    LBRACE, RBRACE,
    LBRACK, RBRACK,
    COLON, COMMA, DOT, ARROW,
    AT,
    EOF
}

data class Token(
    val type: TokType,
    val text: String,
    val pos: Pos
)

class Lexer(private val src: String) {
    private var i = 0
    private var line = 1
    private var col = 1

    private val keywords = mapOf(
        "fun" to TokType.FUN, "val" to TokType.VAL, "var" to TokType.VAR,
        "class" to TokType.CLASS, "data" to TokType.DATA,
        "if" to TokType.IF, "else" to TokType.ELSE,
        "return" to TokType.RETURN,
        "import" to TokType.IMPORT, "package" to TokType.PACKAGE,
        "true" to TokType.BOOL_LIT, "false" to TokType.BOOL_LIT
    )

    fun tokenize(): List<Token> {
        val tokens = mutableListOf<Token>()
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
                        else -> tokens += tok(TokType.SLASH, "/", start)
                    }
                }
                '@' -> { advance(); tokens += tok(TokType.AT, "@", start) }
                '"' -> tokens += readString(start)
                in '0'..'9' -> tokens += readNumber(start)
                in 'a'..'z', in 'A'..'Z', '_' -> tokens += readIdent(start)
                '+' -> { advance(); tokens += tok(TokType.PLUS, "+", start) }
                '-' -> {
                    advance()
                    tokens += if (peek() == '>') {
                        advance(); tok(TokType.ARROW, "->", start)
                    } else tok(TokType.MINUS, "-", start)
                }
                '*' -> { advance(); tokens += tok(TokType.STAR, "*", start) }
                '(' -> { advance(); tokens += tok(TokType.LPAREN, "(", start) }
                ')' -> { advance(); tokens += tok(TokType.RPAREN, ")", start) }
                '{' -> { advance(); tokens += tok(TokType.LBRACE, "{", start) }
                '}' -> { advance(); tokens += tok(TokType.RBRACE, "}", start) }
                '[' -> { advance(); tokens += tok(TokType.LBRACK, "[", start) }
                ']' -> { advance(); tokens += tok(TokType.RBRACK, "]", start) }
                ':' -> { advance(); tokens += tok(TokType.COLON, ":", start) }
                ',' -> { advance(); tokens += tok(TokType.COMMA, ",", start) }
                '.' -> { advance(); tokens += tok(TokType.DOT, ".", start) }
                '=' -> tokens += readEq(start)
                '!' -> tokens += readBang(start)
                '<' -> tokens += readLt(start)
                '>' -> tokens += readGt(start)
                else -> advance()
            }
        }
        tokens += Token(TokType.EOF, "", Pos(line, col))
        return tokens
    }

    private fun peek(offset: Int = 0): Char? = src.getOrNull(i + offset)

    private fun advance(): Char? {
        val c = src.getOrNull(i++) ?: return null
        col++
        return c
    }

    private fun tok(type: TokType, text: String, pos: Pos) = Token(type, text, pos)

    private fun skipLineComment() {
        while (i < src.length && src[i] != '\n') i++
    }

    private fun skipBlockComment() {
        advance()
        while (i < src.length) {
            if (src[i] == '*' && src.getOrNull(i + 1) == '/') {
                i += 2; col += 2; return
            }
            if (src[i] == '\n') { line++; col = 1 }
            else col++
            i++
        }
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
        while (i < src.length && src[i] in '0'..'9') {
            sb.append(src[i]); advance()
        }
        return Token(TokType.INT_LIT, sb.toString(), start)
    }

    private fun readIdent(start: Pos): Token {
        val sb = StringBuilder()
        while (i < src.length && (src[i].isLetterOrDigit() || src[i] == '_')) {
            sb.append(src[i]); advance()
        }
        val text = sb.toString()
        val type = keywords[text] ?: TokType.IDENT
        return Token(type, text, start)
    }

    private fun readEq(start: Pos): Token {
        advance()
        return if (peek() == '=') {
            advance(); tok(TokType.EQEQ, "==", start)
        } else tok(TokType.EQ, "=", start)
    }

    private fun readBang(start: Pos): Token {
        advance()
        return if (peek() == '=') {
            advance(); tok(TokType.BANGEQ, "!=", start)
        } else tok(TokType.IDENT, "!", start)
    }

    private fun readLt(start: Pos): Token {
        advance()
        return if (peek() == '=') {
            advance(); tok(TokType.LTEQ, "<=", start)
        } else tok(TokType.LT, "<", start)
    }

    private fun readGt(start: Pos): Token {
        advance()
        return if (peek() == '=') {
            advance(); tok(TokType.GTEQ, ">=", start)
        } else tok(TokType.GT, ">", start)
    }
}