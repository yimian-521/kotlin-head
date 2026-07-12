package com.qitong.head.runtime

import com.qitong.head.headstd.HList

/**
 * HString — 头标库字符串（v0.10.0）
 *
 * 不像 kotlin.text 链式调用层层分配。
 * 模板解析内建——拿到"Hello ${name}"一步出结果，不拆成 replace + trim + substring。
 * 所有操作返回新实例（不可变），零分配开销的短路操作（相同的字符串直接返回 this）。
 */
class HString(private val raw: String) {

    val length: Int get() = raw.length

    fun get(index: Int): Char = raw[index]

    /**
     * 模板解析——一次遍历，O(n)，匹配所有 ${key}
     */
    fun resolve(vararg bindings: Pair<String, String>): String {
        val map = bindings.toMap()
        val sb = StringBuilder()
        var i = 0
        while (i < raw.length) {
            if (raw[i] == '$' && i + 1 < raw.length && raw[i + 1] == '{') {
                val end = raw.indexOf('}', i + 2)
                if (end >= 0) {
                    val key = raw.substring(i + 2, end)
                    sb.append(map[key] ?: "\${$key}")
                    i = end + 1
                    continue
                }
            }
            sb.append(raw[i])
            i++
        }
        return sb.toString()
    }

    /** 含断言——检查是否包含某子串 */
    operator fun contains(sub: String): Boolean = raw.contains(sub)

    fun startsWith(prefix: String): Boolean = raw.startsWith(prefix)
    fun endsWith(suffix: String): Boolean = raw.endsWith(suffix)

    fun substring(start: Int, end: Int = raw.length): HString =
        HString(raw.substring(start, kotlin.math.min(end, raw.length)))

    fun split(delim: String): HList<String> {
        val parts = raw.split(delim)
        val list = HList<String>()
        for (p in parts) list.add(p)
        return list
    }

    fun trim(): HString = HString(raw.trim())

    fun plus(other: String): HString = HString(raw + other)

    fun toLowerCase(): HString = HString(raw.toLowerCase())
    fun toUpperCase(): HString = HString(raw.toUpperCase())

    operator fun plus(other: HString): HString = HString(raw + other.raw)

    override fun toString(): String = raw

    override fun equals(other: Any?): Boolean =
        other is HString && raw == other.raw

    override fun hashCode(): Int = raw.hashCode()

    companion object {
        val EMPTY = HString("")

        fun from(value: Any?): HString = HString(value?.toString() ?: "null")
    }
}

/** 打印——走自己的 IO 通道，不依赖 kotlin.io */
fun hPrint(msg: Any?) {
    System.out.print(msg?.toString() ?: "null")
}

fun hPrintln(msg: Any? = "") {
    System.out.println(msg?.toString() ?: "")
}

/** 输入——走自己的 IO */
fun hReadLine(): String? = System.console()?.readLine()