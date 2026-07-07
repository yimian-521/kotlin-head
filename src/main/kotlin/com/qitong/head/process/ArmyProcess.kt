package com.qitong.head.process

import com.qitong.head.headstd.HList
import java.util.concurrent.atomic.AtomicInteger

/**
 * 军队进程 —— v0.11.3 主动+被动双模扩编
 *
 * 不是单个干活，是一组协同。高负载时被 ProcessCoordinator 自动创建/唤醒。
 * 每个军队进程内部有自己的子进程组，可并行分摊任务。
 *
 * 两层增派：
 *   被动（应急）：deployArmy — 负载超现有容量时临时扩编，用完退役
 *   主动（常备）：prepareArmy — 编译前根据源码特征预判，常驻进程树
 */
class ArmyProcess(
    val id: String,
    val capacity: Int,
    val permanent: Boolean = false,
    val occupations: List<SubProcessOccupation> = listOf(SubProcessOccupation.SOLDIER)
) {
    private val squad = HList<SubProcessImpl>()
    private var active = true
    private val subCounter = AtomicInteger(0)
    private var occIdx = 0  // 轮流取职业

    /** 向军队分派任务——创建子进程并行执行，用军队的职业组合 */
    fun deploy(
        tasks: List<AnnotationTask>,
        commander: CommanderImpl
    ): List<Pair<AnnotationTask, ProcessResult>> {
        if (tasks.isEmpty()) return emptyList()
        if (!active) return tasks.map { it to ProcessResult.Failure("army退役", true) }
        
        val chunkSize = (tasks.size / capacity.coerceAtMost(tasks.size)).coerceAtLeast(1)
        val chunks = tasks.chunked(chunkSize)
        
        val results = mutableListOf<Pair<AnnotationTask, ProcessResult>>()
        for ((idx, chunk) in chunks.withIndex()) {
            val occ = occupations[occIdx % occupations.size]
            occIdx++
            val sp = SubProcessImpl(
                id = ProcessId(commander.id, "army-$id-sub-${subCounter.incrementAndGet()}", ""),
                tag = commander.tag,
                mode = CollaborationMode.SHARD,
                parentCommander = commander,
                occupation = occ,
                tendency = ProcessTendency.NONE
            )
            squad.add(sp)
            for (task in chunk) {
                results.add(task to sp.delegate(HList.from(listOf(task.payload))).toList().first())
            }
        }
        return results
    }

    fun currentLoad(): Int = squad.size
    fun isActive(): Boolean = active && currentLoad() > 0
    fun retire() { active = false }
    
    /** v0.11.3: 重新激活（被动→主动复用） */
    fun reactivate() { active = true }
    
    fun isPermanent(): Boolean = permanent

    override fun toString(): String = "⚔️ ArmyProcess($id, cap=$capacity, [${occupations.joinToString("/") { it.label }}], squad=${squad.size}, ${if (permanent) "常备" else "临时"})"
}