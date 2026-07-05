package com.qitong.head.process

import com.qitong.head.runtime.HMap
/**
 * 指挥官类型 —— v0.8.5
 *
 * 四种内置指挥官，横向维度分工：
 * 同一个任务从四个维度同时处理，互不依赖。
 *
 * Bug类：诊断维度——查 bug，回报最精准的 bug 内容
 * 侦查类：探路维度——先探路，能行就不用送死
 * 军师类：拆解维度——怎么管后续行动，没它父进程连提要都拿不到
 * 执行类：执行维度——完成一切要做的事情
 *
 * 内置四种 + 可扩展：用户可实现 Commander 接口注册自定义指挥官。
 */
enum class CommanderType(val label: String, val description: String) {
    BUG("Bug类", "诊断维度——查bug，回报最精准的bug内容，发现根因而非表象"),
    SCOUT("侦查类", "探路维度——先派侦察兵探路，能行就上，不行就换方案"),
    STRATEGIST("军师类", "拆解维度——负责如何管后续行动，拆路、定序、分层"),
    EXECUTOR("执行类", "执行维度——完成一切要做的事情，拿到就干"),
    // ★ v0.11.2: 四种新指挥官类型
    MARSHAL("元帅", "大兵团调度——广度碾压，混编精英+常规，靠量取胜"),
    ELITE("尖刀", "精英渗透——深度碾压，只留精英，死不起但三五个顶三五十"),
    LIGHTNING("闪电", "速攻——给手下挂自爆倾向，一切加速，以命换命"),
    STANDARD("常规", "通用——无天赋，多派人自抢，默认指挥官，无调度开销");

    /**
     * 该类型指挥官偏好的默认协同模式
     */
    fun defaultCollaborationMode(): CollaborationMode = when (this) {
        BUG -> CollaborationMode.COLLECT
        SCOUT -> CollaborationMode.SCOUT
        STRATEGIST -> CollaborationMode.PIPELINE
        EXECUTOR -> CollaborationMode.SHARD
        MARSHAL -> CollaborationMode.SHARD      // 大兵团均切推进
        ELITE -> CollaborationMode.COMPETE      // 精英双跑仲裁
        LIGHTNING -> CollaborationMode.SHARD     // 闪电不调策略，全速冲
        STANDARD -> CollaborationMode.SHARD      // 常规就多派人
    }

    companion object {
        /** 可扩展入口：用户注册自定义指挥官类型 */
        private val customTypes = HMap<String, CommanderTypeConfig>()

        fun register(custom: CommanderTypeConfig) {
            customTypes[custom.name] = custom
        }

        fun getCustom(name: String): CommanderTypeConfig? = customTypes[name]

        fun listAll(): List<String> =
            values().map { it.name } + customTypes.keys().toList()

        fun isBuiltin(name: String): Boolean = values().any { it.name == name }
    }
}

/**
 * 自定义指挥官类型配置
 * 「内置四种但可改」——用户通过此配置注册自定义指挥官类型。
 */
data class CommanderTypeConfig(
    val name: String,
    val label: String,
    val description: String,
    val defaultMode: CollaborationMode = CollaborationMode.SHARD
)