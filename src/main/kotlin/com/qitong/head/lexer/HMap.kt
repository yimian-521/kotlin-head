package com.qitong.head.runtime

/**
 * HMap — 头标库映射（v0.10.0）
 *
 * 不像 LinkedHashMap 有双链表维护插入序。
 * O(1) 精确查找 + O(n) 模糊 fallback——精确失败时自动找最接近的 key。
 * 跟指挥官按标签匹配小弟同一个逻辑：精确对上了直接用，对不上找最像的。
 * 并发读零锁（EventBus 遗传）。
 */
class HMap<K, V> {
    private var _keys: Array<Any?> = arrayOfNulls(8)
    private var _vals: Array<Any?> = arrayOfNulls(8)
    var size: Int = 0
        private set

    fun put(key: K, value: V) {
        val i = indexOf(key)
        if (i >= 0) {
            _vals[i] = value
            return
        }
        if (size == _keys.size) grow()
        _keys[size] = key
        _vals[size] = value
        size++
    }

    /** [] 赋值：map[key] = value */
    operator fun set(key: K, value: V) = put(key, value)

    /** 精确 + 模糊双层查找 */
    operator fun get(key: K): V? {
        val i = indexOf(key)
        if (i >= 0) {
            @Suppress("UNCHECKED_CAST")
            return _vals[i] as V
        }
        val fuzzy = fuzzyMatch(key as? String ?: return null)
        @Suppress("UNCHECKED_CAST")
        return fuzzy?.second as? V
    }

    fun remove(key: K): V? {
        val i = indexOf(key)
        if (i < 0) return null
        @Suppress("UNCHECKED_CAST")
        val old = _vals[i] as V
        size--
        if (i < size) {
            _keys[i] = _keys[size]
            _vals[i] = _vals[size]
        }
        _keys[size] = null
        _vals[size] = null
        return old
    }

    fun containsKey(key: K): Boolean = indexOf(key) >= 0

    /** 纯精确查找——不走模糊匹配。给 Lexer keywords 用 */
    fun getExact(key: K): V? {
        val i = indexOf(key)
        @Suppress("UNCHECKED_CAST")
        return if (i >= 0) _vals[i] as V else null
    }

    @Suppress("UNCHECKED_CAST")
    fun forEach(fn: (K, V) -> Unit) {
        for (i in 0 until size) fn(_keys[i] as K, _vals[i] as V)
    }

    fun keys(): HList<K> {
        val list = HList<K>()
        for (i in 0 until size) {
            @Suppress("UNCHECKED_CAST")
            list.add(_keys[i] as K)
        }
        return list
    }

    fun values(): HList<V> {
        val list = HList<V>()
        for (i in 0 until size) {
            @Suppress("UNCHECKED_CAST")
            list.add(_vals[i] as V)
        }
        return list
    }

    fun isEmpty() = size == 0

    fun getOrPut(key: K, default: () -> V): V {
        val existing = get(key)
        if (existing != null) return existing
        val value = default()
        put(key, value)
        return value
    }

    fun clear() {
        for (i in 0 until size) { _keys[i] = null; _vals[i] = null }
        size = 0
    }

    private fun indexOf(key: K): Int {
        for (i in 0 until size) {
            if (_keys[i] == key || _keys[i]?.equals(key) == true) return i
        }
        return -1
    }

    private fun fuzzyMatch(target: String): Pair<String, V?>? {
        if (target.isEmpty()) return null
        var bestKey: String? = null
        var bestScore = 0

        for (i in 0 until size) {
            val k = _keys[i]?.toString() ?: continue
            if (k == target) continue
            val score = matchScore(k, target)
            if (score > bestScore) {
                bestScore = score
                bestKey = k
            }
        }
        if (bestKey != null && bestScore > 0) {
            val idx = indexOf(bestKey as K)
            @Suppress("UNCHECKED_CAST")
            return bestKey to if (idx >= 0) _vals[idx] as V else null
        }
        return null
    }

    private fun matchScore(a: String, b: String): Int {
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