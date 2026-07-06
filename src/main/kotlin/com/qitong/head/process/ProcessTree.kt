package com.qitong.head.process

import com.qitong.head.headstd.HList
import com.qitong.head.headstd.HMap

/**
 * 进程树四层类型契约 —— v0.12.0（底层换 HList/HMap）
 *
 * 角色不塌缩：每层只做一件事，类型系统锁死。
 *   MainProcess  → 只下命令
 *   Commander    → 只调度
 *   SubProcess   → 只拆解+分派+合并
 *   ProcessBody  → 只执行
 *
 * 层级身份标签：创建时烙上，永不可能越级汇报。
 * 复制性引用：层间传递走不可变副本，不共享内存。
 */

// ─── 层级身份标签 ───
data class ProcessId(
    val commanderId: String,
    val subProcessId: String,
    val bodyId: String
) {
    val reportPath: String get() = "body:$bodyId → sub:$subProcessId → cmd:$commanderId → main"
}

// ─── 不可变数据载体（复制性引用） ───
data class ProcessData(
    val content: Any,
    val sourceBodyId: String,
    val timestamp: Long = System.currentTimeMillis()
) {
    fun snapshot(): ProcessData = copy()
}

// ─── 执行结果 ───
sealed class ProcessResult {
    data class Success(val data: ProcessData, val metrics: HMap<String, Any> = HMap()) : ProcessResult()
    data class PartialSuccess(
        val data: ProcessData,
        val errors: HList<String>,
        val completedFraction: Float
    ) : ProcessResult()
    data class Failure(val error: String, val recoverable: Boolean = true) : ProcessResult()
}

// ─── 第一层：主进程 ───
interface MainProcess {
    fun command(order: String, target: String, payload: HMap<String, Any> = HMap()): String
    fun receiveCommanderReport(report: CommanderReport)
    fun broadcastToAll(message: String)
}

data class CommanderReport(
    val commanderId: String,
    val tag: String,
    val summary: String,
    val results: HList<ProcessResult>,
    val completedCount: Int,
    val totalCount: Int,
    val commanderTypeLabel: String = "常规",
    val modeLabel: String = "shard",
    val watchReports: HList<WatchReport> = HList(),
    val subProcessCount: Int = 0,
    val occupationLabels: HList<String> = HList(),
    val tendencyLabel: String = ""
)

// ─── 第二层：指挥官 ───
interface Commander {
    val id: String
    val tag: String
    fun dispatch(order: String, payload: HMap<String, Any> = HMap()): HList<SubProcess>
    fun collectAndReport(results: HList<ProcessResult>): CommanderReport
    fun onBroadcast(message: String)
    fun broadcast(message: String)
}

// ─── 协同模式选择 ───
enum class CollaborationMode {
    SHARD, PIPELINE, COMPETE, SCOUT, COLLECT
}

enum class SubProcessOccupation(val label: String, val description: String) {
    MICRO("显微", "一个bug拆成20条诊断，慢但全——细节型"),
    DIGEST("摘要", "20个bug一句话说清，快但可能漏——概括型"),
    ASSAULT("攻坚", "bug堆里如鱼得水，无bug反而没活干"),
    GUARD("哨卫", "无bug时主动维护看家，有bug反而不是主场"),
    SOLDIER("列兵", "量大管饱，通用但全领域一般般——万能兵"),
    BURST("爆裂", "极低成本自爆式，一人换数个bug。不换时素质平平")
}

enum class ProcessTendency {
    NONE, BURST
}

enum class MainProcessStyle(val label: String, val description: String) {
    FEDERAL("联邦", "管边界不管内政，指挥官各自为政——容错高。默认"),
    DICTATOR("独裁", "所有决策回传确认，指挥官只是手脚——精确但慢"),
    EMERGENCY("紧急", "战时状态——全员加速、容错阈值降低，牺牲精度换速度"),
    CONSERVATIVE("保守", "安全第一——多挂检测进程、宁可慢不炸，地狱文件场景"),
    CONTRACT("契约", "判断+尊重——修能修的、舍该舍的，每个小弟牺牲必须值得"),
    XIAOXIONG("枭雄", "激进但有底限——不择手段但尊重兄弟，带你们杀出去"),
    RENYONG("仁勇", "仁者之勇——以人为本，活着就是赢，不忍任何白白牺牲"),
    NORMAL("正常", "无偏向——既不放权也不集权，中庸基线。有人喜欢不加修饰")
}

enum class SceneType(val label: String, val description: String) {
    CLEAN("干净", "小文件无地狱标记——哨卫看家即可"),
    HEAVY("大块头", "大文件但干净——列兵+摘要批量推进"),
    HOSTILE("地狱", "地狱文件——攻坚+显微+契约高容错"),
    DENSE("高密bug", "历史bug密度高——爆裂速攻+轻量多投"),
    DEFAULT("默认", "不确定就混编——保守策略")
}

enum class HellType { NONE, SYNTAX, TYPE, LINK, MIXED }

data class SceneProfile(
    val name: String,
    val minSize: Int = 0, val maxSize: Int = Int.MAX_VALUE,
    val isHostile: Boolean? = null,
    val hellType: HellType? = null,
    val minBugDensity: Float = 0f, val maxBugDensity: Float = 1f,
    val batchMode: Boolean? = null,
    val incremental: Boolean? = null,
    val minQitongScore: Int = 0,
    val occupations: HList<SubProcessOccupation>,
    val capacityRatio: Float = 0.3f,
    val description: String = ""
)

// ─── 第三层：子进程 ───
interface SubProcess {
    val id: ProcessId
    val tag: String
    val mode: CollaborationMode
    val occupation: SubProcessOccupation
    val tendency: ProcessTendency
    fun delegate(tasks: HList<Any>): HList<ProcessResult>
    fun spawnBody(bodyId: String): ProcessBody
    fun merge(results: HList<ProcessResult>): HList<ProcessResult>
}

// ─── 第四层：进程体（唯一执行层） ───
interface ProcessBody {
    val id: ProcessId
    fun execute(task: Any): ProcessResult
    fun report(result: ProcessResult): ProcessResult
    fun fetchFromUpstream(upstreamResult: ProcessData): ProcessData {
        return upstreamResult.snapshot()
    }
}
