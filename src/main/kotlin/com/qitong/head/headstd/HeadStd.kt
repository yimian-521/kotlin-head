// head-std — 头标标准库
// 底层 HList/HMap，和 kotlin-head 同源
// java-head 适配器一行 import 接入
package com.qitong.head.headstd

import com.qitong.head.runtime.HList
import com.qitong.head.runtime.HMap

data class Diag(
    val group: String,
    val level: String,
    val message: String,
    val source: String = ""
)

open class DiagStore {
    private val groups = HMap<String, HList<Diag>>()
    private var frozen = HList<String>()

    open fun put(group: String, diag: Diag) {
        var list = groups.get(group)
        if (list == null) { list = HList(); groups.put(group, list) }
        list.add(diag)
        onDiagChanged(group)
    }

    open fun get(group: String): HList<Diag> = groups.get(group) ?: HList()
    fun count(group: String): Int = groups.get(group)?.size ?: 0
    fun totalCount(): Int { var t = 0; groups.values().forEach { t += it.size }; return t }

    open fun clear(group: String? = null) {
        if (group != null) {
            val list = groups.get(group)
            if (list != null) { val c = list.size; groups.remove(group); onDiagCleared(group, c) }
        } else {
            val snap = HList<Pair<String, Int>>()
            groups.keys().forEach { k ->
                val list = groups.get(k)
                if (list != null) snap.add(Pair(k, list.size))
            }
            groups.clear()
            snap.forEach { (k, c) -> onDiagCleared(k, c) }
        }
    }

    fun freeze(group: String) { if (!isFrozen(group)) frozen.add(group) }
    fun unfreeze(group: String) { frozen = frozen.filter { it != group } }
    fun isFrozen(group: String): Boolean {
        for (i in 0 until frozen.size) { if (frozen.get(i) == group) return true }
        return false
    }
    fun frozenGroups(): HList<String> = frozen

fun forEachGroup(fn: (String, HList<Diag>) -> Unit) {
        groups.forEach { g, diags -> if (!isFrozen(g)) fn(g, diags) }
    }

    protected open fun onDiagChanged(group: String) {}
    protected open fun onDiagCleared(group: String, count: Int) {}
}

open class LinkedDiagStore(
    private val onChanged: ((String) -> Unit)? = null,
    private val onCleared: ((String, Int) -> Unit)? = null
) : DiagStore() {
    override fun onDiagChanged(group: String) { onChanged?.invoke(group) }
    override fun onDiagCleared(group: String, count: Int) { onCleared?.invoke(group, count) }
}

object HeadStd {
    /** 新量折叠缓存——独立频道，下版接内存树联动 */
    val foldCache = HMap<String, String>()

    fun diagStore(): DiagStore = DiagStore()
    fun linkedStore(
        onChanged: (String) -> Unit,
        onCleared: (String, Int) -> Unit
    ): DiagStore = LinkedDiagStore(onChanged, onCleared)
}