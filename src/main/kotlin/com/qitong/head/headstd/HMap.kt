package com.qitong.head.headstd

import java.util.concurrent.atomic.AtomicInteger

/**
 * 进程并发配置——进程自己选，HMap 不替你做决定。
 * 同一套 API，不同 profile 走不同内部路径。
 */
enum class HConcurrencyProfile {
    /** 零开销单线程——跟 v0.10.0 完全一致。EventBus 事件循环默认。 */
    SINGLE_THREAD,
    /** 并发读零锁 + CAS 写——多进程共享同一个 HMap 时用。 */
    CONCURRENT_READS,
    /** 全操作 CAS——读写删全部无锁。给未来 IR 并行管线预留。 */
    FULL
}

/**
 * HMap — 头标库映射（v0.10.0 → v0.11.1 定量动态并发）
 *
 * 不像 LinkedHashMap 有双链表维护插入序。
 * O(1) 精确查找 + O(n) 模糊 fallback——精确失败时自动找最接近的 key。
 * 跟指挥官按标签匹配小弟同一个逻辑：精确对上了直接用，对不上找最像的。
 *
 * v0.11.1: 进程自选并发模型——SINGLE_THREAD 保持零开销，
 * CONCURRENT_READS 用 AtomicInteger + CAS 无锁写入，
 * FULL 预留全操作 CAS。
 */
class HMap<K, V>(
    val profile: HConcurrencyProfile = HConcurrencyProfile.SINGLE_THREAD
) {
    @Volatile private var _keys: Array<Any?> = arrayOfNulls(8)
    @Volatile private var _vals: Array<Any?> = arrayOfNulls(8)

    /** SINGLE_THREAD: plain Int。CONCURRENT_READS/FULL: AtomicInteger */
    private val _atomicSize = AtomicInteger(0)
    var size: Int
        get() = when (profile) {
            HConcurrencyProfile.SINGLE_THREAD -> _plainSize
            else -> _atomicSize.get()
        }
        private set(value) {
            when (profile) {
                HConcurrencyProfile.SINGLE_THREAD -> _plainSize = value
                else -> _atomicSize.set(value)
            }
        }
    private var _plainSize: Int = 0

    fun put(key: K, value: V) {
        when (profile) {
            HConcurrencyProfile.SINGLE_THREAD -> putSingle(key, value)
            else -> putConcurrent(key, value)
        }
    }

    private fun putSingle(key: K, value: V) {
        val i = indexOf(key)
        if (i >= 0) { _vals[i] = value; return }
        if (_plainSize == _keys.size) grow()
        _keys[_plainSize] = key
        _vals[_plainSize] = value
        _plainSize++
    }

    /** CAS 无锁写入——拿槽位→写入→完成。get 遍历时跳过 null 槽，最终一致。 */
    private fun putConcurrent(key: K, value: V) {
        // 先查是否已存在——遍历期间可能看到其他线程未完成写入（null 槽），跳过安全
        val existing = indexOf(key)
        if (existing >= 0) { _vals[existing] = value; return }
        // CAS 抢槽位
        while (true) {
            val cur = _atomicSize.get()
            if (cur >= _keys.size) grow()
            if (_atomicSize.compareAndSet(cur, cur + 1)) {
                _keys[cur] = key
                _vals[cur] = value
                return
            }
            // CAS 失败——另一个线程抢了，重试
        }
    }

    operator fun set(key: K, value: V) = put(key, value)

    /** 精确 + 模糊双层查找。并发模式遍历 null 槽安全——跳过即可 */
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
        when (profile) {
            HConcurrencyProfile.SINGLE_THREAD -> removeSingle(i)
            else -> removeConcurrent(i)
        }
        return old
    }

    private fun removeSingle(i: Int) {
        _plainSize--
        if (i < _plainSize) {
            _keys[i] = _keys[_plainSize]
            _vals[i] = _vals[_plainSize]
        }
        _keys[_plainSize] = null
        _vals[_plainSize] = null
    }

    /** 并发 remove：CAS 缩 size，swap-remove 模式 */
    private fun removeConcurrent(i: Int) {
        val last = _atomicSize.decrementAndGet()
        if (i < last) {
            _keys[i] = _keys[last]
            _vals[i] = _vals[last]
        }
        _keys[last] = null
        _vals[last] = null
    }

    fun containsKey(key: K): Boolean = indexOf(key) >= 0

    fun getExact(key: K): V? {
        val i = indexOf(key)
        @Suppress("UNCHECKED_CAST")
        return if (i >= 0) _vals[i] as V else null
    }

    @Suppress("UNCHECKED_CAST")
    fun forEach(fn: (K, V) -> Unit) {
        val s = size
        for (i in 0 until s) {
            val k = _keys[i] ?: continue  // 并发跳过未完成槽
            fn(k as K, _vals[i] as V)
        }
    }

    fun keys(): HList<K> {
        val list = HList<K>()
        val s = size
        for (i in 0 until s) {
            @Suppress("UNCHECKED_CAST")
            _keys[i]?.let { list.add(it as K) }
        }
        return list
    }

    fun values(): HList<V> {
        val list = HList<V>()
        val s = size
        for (i in 0 until s) {
            @Suppress("UNCHECKED_CAST")
            _vals[i]?.let { list.add(it as V) }
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
        val s = size
        for (i in 0 until s) { _keys[i] = null; _vals[i] = null }
        size = 0
    }

    private fun indexOf(key: K): Int {
        val s = size
        for (i in 0 until s) {
            val k = _keys[i] ?: continue  // 并发跳过未完成槽
            if (k == key || k.equals(key)) return i
        }
        return -1
    }

    private fun fuzzyMatch(target: String): Pair<String, V?>? {
        if (target.isEmpty()) return null
        var bestKey: String? = null
        var bestScore = 0
        val s = size
        for (i in 0 until s) {
            val k = _keys[i]?.toString() ?: continue
            if (k == target) continue
            val score = matchScore(k, target)
            if (score > bestScore) { bestScore = score; bestKey = k }
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
        fun <K, V> from(vararg pairs: Pair<K, V>, profile: HConcurrencyProfile = HConcurrencyProfile.SINGLE_THREAD): HMap<K, V> {
            val map = HMap<K, V>(profile)
            for ((k, v) in pairs) map.put(k, v)
            return map
        }
    }
}