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
    EXECUTOR("执行类", "执行维度——完成一切要做的事情，拿到就干");

    /**
     * 该类型指挥官偏好的默认协同模式
     */
    fun defaultCollaborationMode(): CollaborationMode = when (this) {
        BUG -> CollaborationMode.COLLECT      // Bug 从各处收集信息再汇总
        SCOUT -> CollaborationMode.SCOUT       // 先探路再决定
        STRATEGIST -> CollaborationMode.PIPELINE  // 分层拆解、阶段接力
        EXECUTOR -> CollaborationMode.SHARD    // 均切平推
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