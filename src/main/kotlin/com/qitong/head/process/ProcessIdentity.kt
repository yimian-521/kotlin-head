package com.qitong.head.process

/**
 * v0.11.5 — 进程身份系统（培养系）
 *
 * pid = 灵魂，出生即定。换职业不改pid。
 * 称号 = 铭牌，只增不减，纯娱乐标签。
 * career = 每个岗位独立的经验缓存。
 * 退役 ≠ 死亡，回大厅睡觉。
 */

// ═══════ 进程身份 ═══════

data class ProcessIdentity(
    val pid: String,                                    // 灵魂，不可变
    val titles: MutableSet<Title> = mutableSetOf(),     // 功勋，只增不减
    val career: MutableMap<SubProcessOccupation, ExperienceCache> = mutableMapOf(), // 每个岗位的记忆
    var currentOccupation: SubProcessOccupation = SubProcessOccupation.SOLDIER,
    var status: ProcessStatus = ProcessStatus.ACTIVE,
    var lastActiveTime: Long = System.currentTimeMillis(),
    var roomId: Int? = null                             // v0.12.4: 房间栈位编号，删除回收
) {
    /** 换职业：经验留档，新岗位从零开始 */
    fun switchOccupation(newOccupation: SubProcessOccupation) {
        if (newOccupation == currentOccupation) return
        // 旧岗位经验保留在career中
        if (!career.containsKey(newOccupation)) {
            career[newOccupation] = ExperienceCache(newOccupation)
        }
        currentOccupation = newOccupation
        lastActiveTime = System.currentTimeMillis()
    }

    /** 退役：保留一切，标记休眠 */
    fun retire() {
        status = ProcessStatus.RETIRED
        lastActiveTime = System.currentTimeMillis()
    }

    /** 唤醒 */
    fun wake() {
        status = ProcessStatus.ACTIVE
        lastActiveTime = System.currentTimeMillis()
    }
}

enum class ProcessStatus { ACTIVE, RETIRED }

// ═══════ 经验缓存 ═══════

class ExperienceCache(
    val occupation: SubProcessOccupation,
    val capacity: Int = 64                               // 最多记住64个场景指纹
) {
    data class Entry(
        val fingerprint: String,
        val occupation: SubProcessOccupation,              // v0.11.5-fix: 存职业，供淘汰过滤
        val occs: List<SubProcessOccupation>,
        val ratio: Float,
        val result: String,
        var hitCount: Int = 0,
        var lastHit: Long = System.currentTimeMillis()
    )

    private val entries = mutableMapOf<String, Entry>()   // fingerprint → entry

    /** 查询缓存。v0.11.6: 三档可信度——专家（≥20次+深度够）跳过校验 */
    fun lookup(fingerprint: String): Triple<Entry?, Boolean, String> {
        val entry = entries[fingerprint] ?: return Triple(null, false, "novice")
        entry.hitCount++
        entry.lastHit = System.currentTimeMillis()
        val tier = when {
            entry.hitCount >= 20 && entries.size >= 30 -> "expert"   // 深度+次数都够→可信
            entry.hitCount >= 5 -> "skilled"
            else -> "novice"
        }
        return Triple(entry, tier == "expert", tier)
    }

    /** 存入新指纹。满了就按职业倾向淘汰 */
    fun store(fingerprint: String, occs: List<SubProcessOccupation>, ratio: Float, result: String) {
        if (entries.size >= capacity) evict()
        entries[fingerprint] = Entry(fingerprint, occupation, occs, ratio, result)
    }

    /** 职业倾向淘汰：优先忘掉非本职业的记忆，其次忘最久没用的 */
    private fun evict() {
        // 1. 找非当前职业的条目，踢最久没用的
        val foreign = entries.values.filter { it.occupation != occupation }.sortedBy { it.lastHit }
        if (foreign.isNotEmpty()) {
            entries.remove(foreign.first().fingerprint)
            return
        }
        // 2. 全是本职业 → 踢最久没用的
        val sorted = entries.values.toList().sortedBy { it.lastHit }
        if (sorted.isNotEmpty()) {
            entries.remove(sorted.first().fingerprint)
        }
    }

    fun size(): Int = entries.size
    fun isFull(): Boolean = entries.size >= capacity
    fun hitRate(): Float = if (entries.isEmpty()) 0f else
        entries.values.count { it.hitCount > 0 }.toFloat() / entries.size
    /** v0.11.5: 公开条目列表（供三观推导等统计使用） */
    fun snapshot(): List<Entry> = entries.values.toList()
}

// ═══════ 三观（从经验统计自动生长） ═══════

data class Worldview(
    val outlook: String,     // 人生观——"我是什么"
    val values: String,      // 价值观——"什么算好"
    val belief: String       // 世界观——"源码是什么"
) {
    companion object {
        /** 从经验缓存自动推导三观 */
        fun deriveFrom(cache: ExperienceCache): Worldview {
            val entries = cache.snapshot()
            if (entries.size < 10) return Worldview("新兵", "还在学习", "源码尚不可知")

            val hitRate = cache.hitRate()
            val avgRatio = entries.map { it.ratio }.average().toFloat()

            // 人生观：从响应速度和成功率判断
            val outlook = when {
                hitRate > 0.7f && avgRatio > 0.5f -> "突击手型——偏好速战，不耐持久"
                hitRate > 0.5f -> "稳扎型——不快但稳，习惯阵地战"
                avgRatio < 0.2f -> "重装型——慢但厚，专啃硬骨头"
                else -> "均衡型——什么都能做一点"
            }

            // 价值观：从最擅长的场景判断
            var bestHit = 0
            var bestFp = ""
            for (e in entries) { if (e.hitCount > bestHit) { bestHit = e.hitCount; bestFp = e.fingerprint } }
            val values = when {
                bestFp.contains("LINK") -> "链接优先——最擅长链接断裂修复"
                bestFp.contains("SYNTAX") -> "语法优先——语法结构看得最清"
                bestFp.contains("TYPE") -> "类型优先——类型系统最敏感"
                else -> "通用——没有明显偏好"
            }

            // 世界观：从整体命中率判断
            val belief = when {
                hitRate > 0.6f -> "源码是可驯服的——见过足够多，知道规律"
                hitRate > 0.3f -> "源码半可控——有些熟、有些生"
                else -> "源码未知——仍在摸爬"
            }

            return Worldview(outlook, values, belief)
        }
    }
}

// ═══════ 称号系统 ═══════

enum class Title(val label: String) {
    // 日常称号
    IRON_MAN("铁人"),           // 连续100次编译未崩溃
    NIGHT_OWL("夜猫子"),        // 深夜编译占比>30%
    SPEEDSTER("急先锋"),        // 平均响应<50ms
    SCAPEGOAT("背锅侠"),        // 误报率>10%
    SLACKER("摸鱼王"),          // 被分配但从未调用>30次
    OLD_DOG("老兵油子"),        // 存活>500次编译
    FIREFIGHTER("救火队长"),    // 被动增派>50次

    // 击杀称号——静默bug
    SILENT_100("静默·bug杀手"),
    SILENT_500("静默·灭绝者"),

    // 击杀称号——地狱bug
    HELL_50("地狱·新兵"),
    HELL_200("地狱·老兵"),
    HELL_1000("地狱·将军"),

    // 击杀称号——链接
    LINK_100("断链·修复师"),

    // 击杀称号——类型
    TYPE_500("类型·清道夫"),

    // 击杀称号——全局
    ANY_1000("万bug斩"),
    ANY_10000("虫灾终结者")
}

object TitleSystem {
    /** 根据统计授予日常称号 */
    fun evaluateDaily(
        consecutiveNoCrash: Int,
        nightRatio: Float,
        avgResponseMs: Long,
        falsePositiveRate: Float,
        idleAssignments: Int,
        totalCompilations: Int,
        passiveDeployCount: Int
    ): Set<Title> {
        val titles = mutableSetOf<Title>()
        if (consecutiveNoCrash >= 100) titles.add(Title.IRON_MAN)
        if (nightRatio > 0.3f) titles.add(Title.NIGHT_OWL)
        if (avgResponseMs < 50) titles.add(Title.SPEEDSTER)
        if (falsePositiveRate > 0.1f) titles.add(Title.SCAPEGOAT)
        if (idleAssignments > 30) titles.add(Title.SLACKER)
        if (totalCompilations >= 500) titles.add(Title.OLD_DOG)
        if (passiveDeployCount >= 50) titles.add(Title.FIREFIGHTER)
        return titles
    }

    /** 根据累计击杀授予称号 */
    fun evaluateKills(
        silentKills: Int,
        hellKills: Int,
        linkKills: Int,
        typeKills: Int,
        totalKills: Int
    ): Set<Title> {
        val titles = mutableSetOf<Title>()
        if (silentKills >= 100) titles.add(Title.SILENT_100)
        if (silentKills >= 500) titles.add(Title.SILENT_500)
        if (hellKills >= 50) titles.add(Title.HELL_50)
        if (hellKills >= 200) titles.add(Title.HELL_200)
        if (hellKills >= 1000) titles.add(Title.HELL_1000)
        if (linkKills >= 100) titles.add(Title.LINK_100)
        if (typeKills >= 500) titles.add(Title.TYPE_500)
        if (totalKills >= 1000) titles.add(Title.ANY_1000)
        if (totalKills >= 10000) titles.add(Title.ANY_10000)
        return titles
    }
}

// ═══════ 退役池 ═══════

object RetirePool {
    private const val MAX_POOL_SIZE = 500
    private val retired = mutableMapOf<String, ProcessIdentity>()  // pid → 身份

    fun store(identity: ProcessIdentity) {
        if (retired.size >= MAX_POOL_SIZE) {
            // 容量淘汰：挤掉最不活跃的
            val sorted = retired.values.toList().sortedBy { it.lastActiveTime }
            if (sorted.isNotEmpty()) {
                retired.remove(sorted.first().pid)
            }
        }
        identity.retire()
        retired[identity.pid] = identity
    }

    fun recall(pid: String): ProcessIdentity? {
        val identity = retired.remove(pid)
        identity?.wake()
        return identity
    }

    fun get(pid: String): ProcessIdentity? = retired[pid]
    fun size(): Int = retired.size
    fun all(): List<ProcessIdentity> = retired.values.toList()
}

// ═══════ 军队预设管理器 ═══════

object ArmyPresetManager {
    data class Preset(
        val name: String,
        val identities: MutableList<ProcessIdentity> = mutableListOf(),
        var status: PresetStatus = PresetStatus.IDLE
    )

    enum class PresetStatus { ACTIVE, IDLE }

    private val presets = mutableMapOf<String, Preset>()  // name → Preset
    var activePreset: String? = null
        private set

    fun createPreset(name: String) {
        if (!presets.containsKey(name)) {
            presets[name] = Preset(name)
        }
    }

    /** 切换预设：当前预设全退役回大厅，目标预设唤醒 */
    fun switchTo(name: String) {
        val target = presets[name] ?: return
        // 退役当前
        activePreset?.let { current ->
            presets[current]?.let { preset ->
                preset.identities.forEach { RetirePool.store(it) }
                preset.status = PresetStatus.IDLE
            }
        }
        // 唤醒目标
        target.identities.clear()
        // 从退役池召回（如果有同名pid）或新建
        target.status = PresetStatus.ACTIVE
        activePreset = name
    }

    /** 增派——优先从大厅拉散兵，不踢预设里的老兵 */
    fun reinforce(needed: Int, policy: ReinforcePolicy, occupation: SubProcessOccupation): List<ProcessIdentity> {
        val recruited = mutableListOf<ProcessIdentity>()

        // 1. 从退役池拉人（大厅散兵）
        val hallRecruits = RetirePool.all()
            .filter { it.currentOccupation == occupation || policy == ReinforcePolicy.AGGRESSIVE }
            .take(needed)
            .toList()  // v0.11.5-fix: 立即物化，避免懒读取

        for (r in hallRecruits) {
            RetirePool.recall(r.pid)
            recruited.add(r)
        }

        val remaining = needed - recruited.size
        if (remaining <= 0) return recruited

        // 2. 大厅空了 → 策略判断：从active预设踢人，踢完招回recruited
        val preset = activePreset?.let { presets[it] } ?: return recruited
        val kicked = when (policy) {
            ReinforcePolicy.AGGRESSIVE -> {
                preset.identities.take(remaining).toList().also {
                    preset.identities.removeAll(it)       // 从预设移除
                    it.forEach { i -> RetirePool.store(i) } // 进退役池
                }
            }
            ReinforcePolicy.PROTECTIVE -> {
                preset.identities
                    .sortedBy { it.career[it.currentOccupation]?.size() ?: 0 }
                    .take(remaining)
                    .toList()
                    .also {
                        preset.identities.removeAll(it)
                        it.forEach { i -> RetirePool.store(i) }
                    }
            }
        }
        // 立刻从退役池召回加入招募列表
        for (k in kicked) {
            RetirePool.recall(k.pid)?.let { recruited.add(it) }
        }
        return recruited
    }
}

// ═══════ 增派策略 ═══════

enum class ReinforcePolicy(val label: String) {
    PROTECTIVE("缺人只踢经验少的，老兵不动"),
    AGGRESSIVE("缺人就踢，不管经验")
}

// ═══════ v0.12.4: 房间栈位 + 指挥官自定义配队 ═══════

object RoomSlotManager {
    /** 每个预设房间的容量上限 */
    private val roomCapacities = mutableMapOf<String, Int>()
    /** 每个预设房间的已分配编号集合 */
    private val roomSlots = mutableMapOf<String, MutableSet<Int>>()
    /** 房间内编号→职业映射 */
    private val roomSquads = mutableMapOf<String, MutableMap<Int, SubProcessOccupation>>()

    /** 初始化房间，指定容量上限 */
    fun initRoom(presetName: String, capacity: Int) {
        roomCapacities[presetName] = capacity
        roomSlots.getOrPut(presetName) { mutableSetOf() }
        roomSquads.getOrPut(presetName) { mutableMapOf() }
    }

    /** 分配最小可用编号（栈位机制） */
    fun assignRoomId(presetName: String): Int? {
        val capacity = roomCapacities[presetName] ?: return null
        val slots = roomSlots.getOrPut(presetName) { mutableSetOf() }
        for (id in 1..capacity) {
            if (id !in slots) {
                slots.add(id)
                return id
            }
        }
        return null  // 房间满
    }

    /** 释放编号（删除时回收） */
    fun releaseRoomId(presetName: String, roomId: Int) {
        roomSlots[presetName]?.remove(roomId)
        roomSquads[presetName]?.remove(roomId)
    }

    /** 指挥官自定义配队：指定房间编号→职业 */
    fun configureSquad(presetName: String, mapping: Map<Int, SubProcessOccupation>) {
        val squads = roomSquads.getOrPut(presetName) { mutableMapOf() }
        squads.putAll(mapping)
    }

    /** 查询某编号的职业 */
    fun getOccupation(presetName: String, roomId: Int): SubProcessOccupation? {
        return roomSquads[presetName]?.get(roomId)
    }

    fun roomSize(presetName: String): Int = roomSlots[presetName]?.size ?: 0
    fun roomCapacity(presetName: String): Int = roomCapacities[presetName] ?: 0
    fun isRoomFull(presetName: String): Boolean = roomSize(presetName) >= (roomCapacity(presetName) * 1.03f).toInt()  // v0.12.4: 3%余量，防满载误判
}
