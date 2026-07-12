package com.qitong.head.runtime

import com.qitong.head.headstd.HMap
import com.qitong.head.headstd.HList

/**
 * ProHList — 进化版有序追加列表（v0.12.0 头标库）
 *
 * 在 HList 基础上增加「暂存删除 + 惰性清理」能力：
 *  - add() → 直接追加，零开销（E后门）
 *  - remove() → O(1) 标记脏位，不重建
 *  - apply() → 脏了才 filter 重建（B路径）
 *
 * 和 CPU 大小核调度完全同构：
 *  轻操作（add-only）→ 纯追加，apply() 零开销
 *  重操作（remove）→ 标记+tapply 一次清理
 *
 * 与 HList 兼容：所有迭代方法（forEach/map/filter/count/none/any等）
 * 自动跳过已标记删除的元素。
 */
class ProHList<T> {
    private var elems: Array<Any?> = arrayOfNulls(4)
    private val removed = HMap<T, Boolean>()  // 特征索引O(1)查删除标记
    private var dirtyCount: Int = 0

    var size: Int = 0
        private set

    // ─── E 路径：直接追加 ───
    fun add(elem: T) {
        if (size == elems.size) grow()
        elems[size++] = elem
    }

    // ─── E 路径：O(1)标记删除 ───
    fun remove(elem: T) {
        removed.put(elem, true)
        dirtyCount++
    }

    // ─── B 路径：脏了才 filter 重建 ───
    fun apply() {
        if (dirtyCount == 0) return
        var newElems = arrayOfNulls<Any?>(elems.size)
        var newSize = 0
        for (i in 0 until size) {
            val e = elems[i]!!
            if (removed.get(e as T) == null) {
                if (newSize == newElems.size) {
                    newElems = newElems.copyOf(newElems.size * 2)
                }
                newElems[newSize++] = e
            }
        }
        elems = newElems
        size = newSize
        removed.clear()
        dirtyCount = 0
    }

    val hasDirty: Boolean get() = dirtyCount > 0
    fun effectiveSize(): Int = size - dirtyCount

    // ─── 访问（自动跳过被删的）───
    operator fun get(index: Int): T {
        var skipped = 0
        var i = 0
        while (i < size) {
            @Suppress("UNCHECKED_CAST")
            val e = elems[i] as T
            if (removed.get(e) == null) {
                if (skipped == index) return e
                skipped++
            }
            i++
        }
        throw IndexOutOfBoundsException("index $index, effective size ${effectiveSize()}")
    }

    private fun isAlive(e: T): Boolean = removed.get(e) == null

    fun isEmpty() = effectiveSize() == 0
    fun isNotEmpty() = effectiveSize() > 0

    // ─── 迭代 ───
    fun forEach(fn: (T) -> Unit) {
        for (i in 0 until size) {
            @Suppress("UNCHECKED_CAST")
            val e = elems[i] as T
            if (isAlive(e)) fn(e)
        }
    }

    fun <R> map(fn: (T) -> R): ProHList<R> {
        val result = ProHList<R>()
        forEach { result.add(fn(it)) }
        return result
    }

    fun filter(fn: (T) -> Boolean): ProHList<T> {
        val result = ProHList<T>()
        forEach { if (fn(it)) result.add(it) }
        return result
    }

    fun any(fn: (T) -> Boolean): Boolean {
        for (i in 0 until size) {
            @Suppress("UNCHECKED_CAST")
            val e = elems[i] as T
            if (isAlive(e) && fn(e)) return true
        }
        return false
    }

    fun count(fn: (T) -> Boolean): Int {
        var c = 0
        forEach { if (fn(it)) c++ }
        return c
    }

    fun none(fn: (T) -> Boolean): Boolean = !any(fn)
    fun all(fn: (T) -> Boolean): Boolean = !any { !fn(it) }

    fun find(fn: (T) -> Boolean): T? {
        for (i in 0 until size) {
            @Suppress("UNCHECKED_CAST")
            val e = elems[i] as T
            if (isAlive(e) && fn(e)) return e
        }
        return null
    }

    fun contains(elem: T): Boolean {
        for (i in 0 until size) {
            @Suppress("UNCHECKED_CAST")
            val e = elems[i] as T
            if (isAlive(e) && (e == elem || e?.equals(elem) == true)) return true
        }
        return false
    }

    fun first(): T = get(0)
    fun last(): T = get(effectiveSize() - 1)

    fun take(n: Int): ProHList<T> {
        val result = ProHList<T>()
        var c = 0
        for (i in 0 until size) {
            @Suppress("UNCHECKED_CAST")
            val e = elems[i] as T
            if (isAlive(e)) {
                if (c++ < n) result.add(e) else return result
            }
        }
        return result
    }

    fun drop(n: Int): ProHList<T> {
        val result = ProHList<T>()
        var c = 0
        forEach { if (c++ >= n) result.add(it) }
        return result
    }

    fun <R> flatMap(fn: (T) -> ProHList<R>): ProHList<R> {
        val result = ProHList<R>()
        forEach { fn(it).forEach { r -> result.add(r) } }
        return result
    }

    fun <R> fold(initial: R, fn: (R, T) -> R): R {
        var acc = initial
        forEach { acc = fn(acc, it) }
        return acc
    }

    fun joinToString(separator: String = ", ", prefix: String = "", postfix: String = ""): String {
        val sb = StringBuilder(prefix)
        var first = true
        forEach {
            if (!first) sb.append(separator)
            sb.append(it.toString())
            first = false
        }
        return sb.append(postfix).toString()
    }

    // ─── 归并排序 O(n log n) ───
    fun <R : Comparable<R>> sortedBy(fn: (T) -> R): ProHList<T> {
        val arr = arrayOfNulls<Any?>(effectiveSize())
        var idx = 0
        forEach { arr[idx++] = it }
        mergeSort(arr, 0, idx - 1, fn)
        val result = ProHList<T>()
        for (i in 0 until idx) {
            @Suppress("UNCHECKED_CAST")
            result.add(arr[i] as T)
        }
        return result
    }

    private fun <R : Comparable<R>> mergeSort(arr: Array<Any?>, lo: Int, hi: Int, fn: (T) -> R) {
        if (lo >= hi) return
        val mid = (lo + hi) / 2
        mergeSort(arr, lo, mid, fn)
        mergeSort(arr, mid + 1, hi, fn)
        merge(arr, lo, mid, hi, fn)
    }

    private fun <R : Comparable<R>> merge(arr: Array<Any?>, lo: Int, mid: Int, hi: Int, fn: (T) -> R) {
        val tmp = arr.copyOfRange(lo, hi + 1)
        var i = 0; val mid2 = mid - lo; var j = mid2 + 1; var k = lo
        while (i <= mid2 && j < tmp.size) {
            @Suppress("UNCHECKED_CAST")
            arr[k++] = if ((fn(tmp[i] as T)).compareTo(fn(tmp[j] as T)) <= 0) tmp[i++] else tmp[j++]
        }
        while (i <= mid2) arr[k++] = tmp[i++]
    }

    fun partition(fn: (T) -> Boolean): Pair<ProHList<T>, ProHList<T>> {
        val pass = ProHList<T>()
        val fail = ProHList<T>()
        forEach { if (fn(it)) pass.add(it) else fail.add(it) }
        return pass to fail
    }

    fun forEachIndexed(fn: (Int, T) -> Unit) {
        var idx = 0
        forEach { fn(idx++, it) }
    }

    fun groupBy(fn: (T) -> String): HMap<String, ProHList<T>> {
        val map = HMap<String, ProHList<T>>()
        forEach { e ->
            val key = fn(e)
            var group = map.get(key)
            if (group == null) { group = ProHList<T>(); map.put(key, group) }
            group.add(e)
        }
        return map
    }

    fun toList(): List<T> {
        val list = mutableListOf<T>()
        forEach { list.add(it) }
        return list
    }

    fun toHList(): HList<T> {
        val result = HList<T>()
        forEach { result.add(it) }
        return result
    }

    operator fun iterator(): Iterator<T> = object : Iterator<T> {
        private var idx = 0
        override fun hasNext(): Boolean {
            while (idx < size) {
                @Suppress("UNCHECKED_CAST")
                if (isAlive(elems[idx] as T)) return true
                idx++
            }
            return false
        }
        override fun next(): T {
            @Suppress("UNCHECKED_CAST")
            return elems[idx++] as T
        }
    }

    // ─── 内部 ───
    private fun grow() {
        elems = elems.copyOf(elems.size * 2)
    }

    companion object {
        fun <T> from(items: Collection<T>): ProHList<T> {
            val list = ProHList<T>()
            for (item in items) list.add(item)
            return list
        }
    }
}