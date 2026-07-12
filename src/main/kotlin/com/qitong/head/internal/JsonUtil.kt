package com.qitong.head.internal

/**
 * 极简 JSON 工具 —— 零第三方依赖。
 * 只支持本编译器需要的类型：String、Number、Boolean、Map、List、null
 */
object JsonUtil {

    fun encode(obj: Any?): String = when (obj) {
        null -> "null"
        is String -> "\"${escape(obj)}\""
        is Boolean -> obj.toString()
        is Number -> obj.toString()
        is Map<*, *> -> {
            val entries = obj.entries.joinToString(",") { (k, v) ->
                "\"${escape(k.toString())}\":${encode(v)}"
            }
            "{$entries}"
        }
        is List<*> -> "[${obj.joinToString(",") { encode(it) }}]"
        else -> "\"${escape(obj.toString())}\""
    }

    fun decode(json: String): Any? {
        val reader = Reader(json.trim())
        return reader.readValue()
    }

    // ─── 内部 Reader ───
    private class Reader(private val s: String) {
        private var i = 0

        fun readValue(): Any? {
            skipWs()
            return when (val c = peek()) {
                'n' -> { expect("null"); null }
                't' -> { expect("true"); true }
                'f' -> { expect("false"); false }
                '"' -> readString()
                '{' -> readObject()
                '[' -> readArray()
                '-', in '0'..'9' -> readNumber()
                else -> throw error("unexpected '$c'")
            }
        }

        private fun readObject(): Map<String, Any?> {
            expect('{')
            val map = mutableMapOf<String, Any?>()
            if (peekWs() == '}') { advance(); return map }
            while (true) {
                skipWs()
                val key = readString()
                skipWs()
                expect(':')
                map[key] = readValue()
                skipWs()
                when (advance()) {
                    '}' -> return map
                    ',' -> {} // continue
                    else -> throw error("expected ',' or '}'")
                }
            }
        }

        private fun readArray(): List<Any?> {
            expect('[')
            val list = mutableListOf<Any?>()
            if (peekWs() == ']') { advance(); return list }
            while (true) {
                skipWs()
                list += readValue()
                skipWs()
                when (advance()) {
                    ']' -> return list
                    ',' -> {} // continue
                    else -> throw error("expected ',' or ']'")
                }
            }
        }

        private fun readString(): String {
            expect('"')
            val sb = StringBuilder()
            while (true) {
                val c = advance()
                when (c) {
                    '"' -> return sb.toString()
                    '\\' -> {
                        val ec = advance()
                        when (ec) {
                            'u' -> sb.append(readHex4().toChar())
                            else -> sb.append(escapeChar(ec))
                        }
                    }
                    else -> sb.append(c)
                }
            }
        }

        private fun readHex4(): Int {
            var v = 0
            repeat(4) {
                val c = advance()
                v = v * 16 + when (c) {
                    in '0'..'9' -> c - '0'
                    in 'a'..'f' -> c - 'a' + 10
                    in 'A'..'F' -> c - 'A' + 10
                    else -> throw error("expected hex digit, got '$c'")
                }
            }
            return v
        }

        private fun readNumber(): Number {
            val sb = StringBuilder()
            if (peek() == '-') sb.append(advance())
            while (peek() in '0'..'9') sb.append(advance())
            if (peek() == '.') {
                sb.append(advance())
                while (peek() in '0'..'9') sb.append(advance())
                return sb.toString().toDouble()
            }
            return sb.toString().toLong()
        }

        private fun peek(): Char? = s.getOrNull(i)
        private fun peekWs(): Char? {
            var j = i
            while (j < s.length && s[j].isWhitespace()) j++
            return s.getOrNull(j)
        }
        private fun advance(): Char = s.getOrElse(i++) { throw error("unexpected EOF") }
        private fun skipWs() { while (peek()?.isWhitespace() == true) advance() }
        private fun expect(ch: Char) { skipWs(); if (advance() != ch) throw error("expected '$ch'") }
        private fun expect(word: String) { for (c in word) if (advance() != c) throw error("expected '$word'") }
        private fun error(msg: String): Nothing = throw RuntimeException("JSON err at $i: $msg")
    }

    // ─── 字符串转义 ───
    private fun escape(s: String): String = s
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\t", "\\t")

    private fun escapeChar(c: Char): Char = when (c) {
        'n' -> '\n'; 'r' -> '\r'; 't' -> '\t'; '\\' -> '\\'; '"' -> '"'; else -> c
    }
}
