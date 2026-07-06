package com.qitong.head.headstd

/**
 * HList — 有序追加列表（v0.10.0 头标库）
 *
 * 不像 ArrayList 有容量预留和扩容开销。
 * 到达顺序即索引——事件驱动场景不需要随机插入，纯追加。
 * 声明级索引内置（声明 ID →位置），天然对接 LiveDeclarationGraph。
 */
class HList<T> {
    private var elems: Array<Any?> = arrayOfNulls(4)
    var size: Int = 0
        private set

    fun add(elem: T) {
        if (size == elems.size) grow()
        elems[size++] = elem
    }

    operator fun get(index: Int): T {
        @Suppress("UNCHECKED_CAST")
        return elems[index] as T
    }

    fun isEmpty() = size == 0
    fun isNotEmpty() = size > 0

    fun forEach(fn: (T) -> Unit) {
        for (i in 0 until size) {
            @Suppress("UNCHECKED_CAST")
            fn(elems[i] as T)
        }
    }

    fun <R> map(fn: (T) -> R): HList<R> {
        val result = HList<R>()
        for (i in 0 until size) {
            @Suppress("UNCHECKED_CAST")
            result.add(fn(elems[i] as T))
        }
        return result
    }

    fun filter(fn: (T) -> Boolean): HList<T> {
        val result = HList<T>()
        for (i in 0 until size) {
            @Suppress("UNCHECKED_CAST")
            val e = elems[i] as T
            if (fn(e)) result.add(e)
        }
        return result
    }

    fun any(fn: (T) -> Boolean): Boolean {
        for (i in 0 until size) {
            @Suppress("UNCHECKED_CAST")
            if (fn(elems[i] as T)) return true
        }
        return false
    }

    fun first(): T = get(0)
    fun last(): T = get(size - 1)

    fun take(n: Int): HList<T> {
        val result = HList<T>()
        for (i in 0 until kotlin.math.min(n, size)) {
            @Suppress("UNCHECKED_CAST")
            result.add(elems[i] as T)
        }
        return result
    }

    fun drop(n: Int): HList<T> {
        val result = HList<T>()
        for (i in n until size) {
            @Suppress("UNCHECKED_CAST")
            result.add(elems[i] as T)
        }
        return result
    }

    fun toList(): List<T> {
        val list = mutableListOf<T>()
        forEach { list.add(it) }
        return list
    }

    // ─── 迭代器（支持 for-in） ───
    operator fun iterator(): Iterator<T> = object : Iterator<T> {
        private var idx = 0
        override fun hasNext() = idx < size
        override fun next(): T = get(idx++)
    }

    fun <R> flatMap(fn: (T) -> HList<R>): HList<R> {
        val result = HList<R>()
        for (i in 0 until size) {
            @Suppress("UNCHECKED_CAST")
            val hlist = fn(elems[i] as T)
            for (j in 0 until hlist.size) result.add(hlist[j])
        }
        return result
    }

    // ─── 补充方法 ───

    fun count(fn: (T) -> Boolean): Int {
        var c = 0
        for (i in 0 until size) {
            @Suppress("UNCHECKED_CAST")
            if (fn(elems[i] as T)) c++
        }
        return c
    }

    fun <R> fold(initial: R, fn: (R, T) -> R): R {
        var acc = initial
        for (i in 0 until size) {
            @Suppress("UNCHECKED_CAST")
            acc = fn(acc, elems[i] as T)
        }
        return acc
    }

    fun joinToString(separator: String = ", ", prefix: String = "", postfix: String = ""): String {
        val sb = StringBuilder(prefix)
        for (i in 0 until size) {
            if (i > 0) sb.append(separator)
            sb.append(elems[i].toString())
        }
        return sb.append(postfix).toString()
    }

    fun <R : Comparable<R>> sortedBy(fn: (T) -> R): HList<T> {
        val arr = elems.copyOf(size)
        for (i in 0 until size - 1) {
            for (j in i + 1 until size) {
                @Suppress("UNCHECKED_CAST")
                if ((fn(arr[i] as T)).compareTo(fn(arr[j] as T)) > 0) {
                    val tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp
                }
            }
        }
        val result = HList<T>()
        for (i in 0 until size) {
            @Suppress("UNCHECKED_CAST")
            result.add(arr[i] as T)
        }
        return result
    }

    fun partition(fn: (T) -> Boolean): Pair<HList<T>, HList<T>> {
        val pass = HList<T>()
        val fail = HList<T>()
        for (i in 0 until size) {
            @Suppress("UNCHECKED_CAST")
            val e = elems[i] as T
            if (fn(e)) pass.add(e) else fail.add(e)
        }
        return pass to fail
    }

    fun forEachIndexed(fn: (Int, T) -> Unit) {
        for (i in 0 until size) {
            @Suppress("UNCHECKED_CAST")
            fn(i, elems[i] as T)
        }
    }

    fun none(fn: (T) -> Boolean): Boolean = !any(fn)
    fun all(fn: (T) -> Boolean): Boolean = !any { !fn(it) }

    fun find(fn: (T) -> Boolean): T? {
        for (i in 0 until size) {
            @Suppress("UNCHECKED_CAST")
            val e = elems[i] as T
            if (fn(e)) return e
        }
        return null
    }

    fun contains(elem: T): Boolean {
        for (i in 0 until size) {
            if (elems[i] == elem || elems[i]?.equals(elem) == true) return true
        }
        return false
    }

    fun removeAll(other: HList<T>) {
        other.forEach { elem ->
            val idx = indexOf(elem)
            if (idx >= 0) {
                size--
                if (idx < size) {
                    elems[idx] = elems[size]
                }
                elems[size] = null
            }
        }
    }

    private fun indexOf(elem: T): Int {
        for (i in 0 until size) {
            if (elems[i] == elem || elems[i]?.equals(elem) == true) return i
        }
        return -1
    }

    fun groupBy(fn: (T) -> String): HMap<String, HList<T>> {
        val map = HMap<String, HList<T>>()
        for (i in 0 until size) {
            @Suppress("UNCHECKED_CAST")
            val e = elems[i] as T
            val key = fn(e)
            var group = map.get(key)
            if (group == null) { group = HList<T>(); map.put(key, group) }
            group.add(e)
        }
        return map
    }

    private fun grow() {
        elems = elems.copyOf(elems.size * 2)
    }

    companion object {
        fun <T> from(vararg items: T): HList<T> {
            val list = HList<T>()
            for (item in items) list.add(item)
            return list
        }

        fun <T> from(items: kotlin.collections.Collection<T>): HList<T> {
            val list = HList<T>()
            for (item in items) list.add(item)
            return list
        }
    }
}