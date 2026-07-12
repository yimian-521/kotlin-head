package com.qitong.head.headstd

/**
 * HMap — 头标库映射（v1.0.2 三分特征索引）
 *
 * 特征索引：key前2字符分组，组内自适应扫描。
 * 组内≤3键→线性扫描（开销低于二分），>3键→二分查找（防同前缀退化）。
 * fuzzyMatch 独立为 getFuzzy()——get()只做精确匹配。
 */

class HMap<K, V> {
    @Volatile private var _keys: Array<Any?> = arrayOfNulls(8)
    @Volatile private var _vals: Array<Any?> = arrayOfNulls(8)
    private var _size = 0

    /** 特征索引：key前2字符 → 组内键位置列表（保持有序） */
    private var featIdx: MutableMap<String, MutableList<Int>>? = null
    private var featIdxSize = 0  // 索引版本号，put/remove后失效

    var size: Int
        get() = _size
        private set(value) { _size = value }

    fun put(key: K, value: V) {
        val i = indexOf(key)
        if (i >= 0) { _vals[i] = value; return }
        if (_size == _keys.size) grow()
        // 插入排序保持组内有序
        val prefix = keyPrefix(key)
        val group = ensureFeatIdx().getOrPut(prefix) { mutableListOf() }
        var insertPos = group.size
        for (gi in group.indices) {
            val ki = group[gi]
            if (compareKeys(_keys[ki] as K, key) > 0) { insertPos = gi; break }
        }
        group.add(insertPos, _size)
        _keys[_size] = key
        _vals[_size] = value
        _size++
        featIdxSize++
    }

    operator fun set(key: K, value: V) = put(key, value)

    /** 精确查找——特征索引加速 */
    operator fun get(key: K): V? {
        val group = featIdx?.get(keyPrefix(key))
        if (group == null) {
            val fallback = indexOfFallback(key)
            if (fallback >= 0) {
                @Suppress("UNCHECKED_CAST")
                return _vals[fallback] as V
            }
            return null
        }
        val s = group.size
        if (s == 0) return null
        // 自适应：≤3线性，>3二分
        if (s <= 3) {
            for (gi in group) {
                if (checkKey(gi, key)) {
                    @Suppress("UNCHECKED_CAST")
                    return _vals[gi] as V
                }
            }
        } else {
            var lo = 0; var hi = s - 1
            while (lo <= hi) {
                val mid = (lo + hi) / 2
                val gi = group[mid]
                val cmp = compareKeys(_keys[gi] as K, key)
                when {
                    cmp == 0 -> {
                        @Suppress("UNCHECKED_CAST")
                        return _vals[gi] as V
                    }
                    cmp < 0 -> lo = mid + 1
                    else -> hi = mid - 1
                }
            }
        }
        return null
    }

    /** 模糊匹配——独立方法，get()不自动触发 */
    fun getFuzzy(target: String): Pair<String, V?>? {
        if (target.isEmpty()) return null
        var bestKey: String? = null
        var bestScore = 0
        for (i in 0 until _size) {
            val k = _keys[i]?.toString() ?: continue
            if (k == target) continue
            val score = matchScore(k, target)
            if (score > bestScore) { bestScore = score; bestKey = k }
        }
        if (bestKey != null && bestScore > 0) {
            @Suppress("UNCHECKED_CAST")
            return bestKey to get(bestKey as K)
        }
        return null
    }

    fun remove(key: K): V? {
        val i = indexOf(key)
        if (i < 0) return null
        @Suppress("UNCHECKED_CAST")
        val old = _vals[i] as V
        _size--
        if (i < _size) {
            _keys[i] = _keys[_size]
            _vals[i] = _vals[_size]
        }
        _keys[_size] = null; _vals[_size] = null
        featIdx?.get(keyPrefix(key))?.let { group ->
            group.removeAt(group.indexOf(i))
            featIdxSize--  // 版本号降级，下次ensureFeatIdx时检查
        }
        return old
    }

    fun containsKey(key: K): Boolean = get(key) != null

    fun getExact(key: K): V? = get(key)

    @Suppress("UNCHECKED_CAST")
    fun forEach(fn: (K, V) -> Unit) {
        for (i in 0 until _size) {
            fn(_keys[i] as K, _vals[i] as V)
        }
    }

    fun keys(): HList<K> {
        val list = HList<K>()
        for (i in 0 until _size) {
            @Suppress("UNCHECKED_CAST")
            list.add(_keys[i] as K)
        }
        return list
    }

    fun values(): HList<V> {
        val list = HList<V>()
        for (i in 0 until _size) {
            @Suppress("UNCHECKED_CAST")
            list.add(_vals[i] as V)
        }
        return list
    }

    fun isEmpty() = _size == 0

    fun getOrPut(key: K, default: () -> V): V {
        val existing = get(key)
        if (existing != null) return existing
        val value = default()
        put(key, value)
        return value
    }

    fun clear() {
        for (i in 0 until _size) { _keys[i] = null; _vals[i] = null }
        _size = 0
        featIdx = null
    }

    // ─── 内部方法 ───

    private fun keyPrefix(key: K): String {
        val s = key.toString()
        return if (s.length >= 2) s.substring(0, 2) else s
    }

    private fun compareKeys(a: K, b: K): Int =
        (a as Comparable<K>).compareTo(b)

    private fun checkKey(i: Int, key: K): Boolean {
        val k = _keys[i] ?: return false
        return k == key
    }

    private fun indexOf(key: K): Int {
        val group = featIdx?.get(keyPrefix(key))
        if (group != null) {
            if (group.size <= 3) {
                for (gi in group) if (checkKey(gi, key)) return gi
            } else {
                var lo = 0; var hi = group.size - 1
                while (lo <= hi) {
                    val mid = (lo + hi) / 2
                    val gi = group[mid]
                    val cmp = compareKeys(_keys[gi] as K, key)
                    when { cmp == 0 -> return gi; cmp < 0 -> lo = mid + 1; else -> hi = mid - 1 }
                }
            }
            return -1
        }
        return indexOfFallback(key)
    }

    private fun indexOfFallback(key: K): Int {
        for (i in 0 until _size) {
            if (checkKey(i, key)) return i
        }
        return -1
    }

    private fun ensureFeatIdx(): MutableMap<String, MutableList<Int>> {
        featIdx?.let { if (featIdxSize == _size) return it }
        val idx = mutableMapOf<String, MutableList<Int>>()
        for (i in 0 until _size) {
            val prefix = keyPrefix(_keys[i] as K)
            idx.getOrPut(prefix) { mutableListOf() }.add(i)
        }
        featIdx = idx
        featIdxSize = _size
        return idx
    }

    private fun matchScore(a: String, b: String): Int {
        if (a.isEmpty() || b.isEmpty()) return 0
        var score = 0
        var i = 0
        while (i < a.length && i < b.length && a[i] == b[i]) { i++; score += 2 }
        var ai = a.length - 1; var bi = b.length - 1
        while (ai >= 0 && bi >= 0 && a[ai] == b[bi]) { ai--; bi--; score += 1 }
        val aLower = a.toLowerCase(); val bLower = b.toLowerCase()
        if (bLower in aLower || aLower in bLower) score += 5
        return score
    }

    private fun grow() {
        _keys = _keys.copyOf(_keys.size * 2)
        _vals = _vals.copyOf(_vals.size * 2)
    }

    companion object {
        fun <K, V> from(vararg pairs: Pair<K, V>): HMap<K, V> {
            val map = HMap<K, V>()
            for ((k, v) in pairs) map.put(k, v)
            return map
        }
    }
}