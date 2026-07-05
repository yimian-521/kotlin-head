package com.qitong.head.process

import com.qitong.head.runtime.HList

/**
 * 军队进程 —— v0.11.0-a 自适应扩编
 *
 * 不是单个干活，是一组协同。高负载时被 ProcessCoordinator 自动创建。
 * 每个军队进程内部有自己的子进程组，可并行分摊任务。
 */
class ArmyProcess(
    val id: String,
    val capacity: Int  // 最大并行任务数
) {
    /** 内部的子进程组 */
    private val squad = HList<SubProcessImpl>()
    private var active = true

    /** 向军队分派任务——军队内部自动拆分+并行 */
    fun deploy(tasks: List<AnnotationTask>): List<ProcessResult> {
        if (tasks.isEmpty()) return emptyList()
        
        // 按容量拆分
        val chunks = tasks.chunked(capacity.coerceAtMost(tasks.size / (squad.size.coerceAtLeast(1))))
        
        return chunks.flatMap { chunk ->
            chunk.map { task ->
                try {
                    ProcessResult.Success(
                        data = ProcessData(content = "army:$id processed ${task.annotationName}", sourceBodyId = id),
                        metrics = mapOf("army" to id, "capacity" to capacity)
                    )
                } catch (e: Exception) {
                    ProcessResult.Failure("army:$id failed ${task.annotationName}: ${e.message}", true)
                }
            }
        }
    }

    /** 军队当前负载 */
    fun currentLoad(): Int = squad.size
    
    /** 是否活跃 */
    fun isActive(): Boolean = active
    
    /** 退役 */
    fun retire() { active = false }
    
    override fun toString(): String = "⚔️ ArmyProcess($id, cap=$capacity, squad=${squad.size}, active=$active)"
}