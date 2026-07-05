package com.qitong.head.process

/**
 * 进程树四层类型契约 —— v0.8.0-a
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
    /** 汇报路径：自己 → 子进程 → 指挥官 → 主进程 */
    val reportPath: String get() = "body:$bodyId → sub:$subProcessId → cmd:$commanderId → main"
}

// ─── 不可变数据载体（复制性引用） ───
data class ProcessData(
    val content: Any,
    val sourceBodyId: String,
    val timestamp: Long = System.currentTimeMillis()
) {
    /** 深复制一份，断开与原持有者的所有引用 */
    fun snapshot(): ProcessData = copy()
}

// ─── 执行结果 ───
sealed class ProcessResult {
    data class Success(val data: ProcessData, val metrics: Map<String, Any> = emptyMap()) : ProcessResult()
    data class PartialSuccess(
        val data: ProcessData,
        val errors: List<String>,
        val completedFraction: Float  // 0.0~1.0，成功那半的比例
    ) : ProcessResult()
    data class Failure(val error: String, val recoverable: Boolean = true) : ProcessResult()
}

// ─── 第一层：主进程 ───
interface MainProcess {
    /** 下命令，不执行。返回命令ID供追踪。 */
    fun command(order: String, target: String, payload: Map<String, Any> = emptyMap()): String

    /** 收指挥官汇总报告，不做二次加工 */
    fun receiveCommanderReport(report: CommanderReport)

    /** 紧急广播：通知所有指挥官 */
    fun broadcastToAll(message: String)
}

data class CommanderReport(
    val commanderId: String,
    val tag: String,
    val summary: String,
    val results: List<ProcessResult>,
    val completedCount: Int,
    val totalCount: Int,
    // v0.8.5: 树状展示所需
    val commanderTypeLabel: String = "常规",
    val modeLabel: String = "shard",
    val watchReports: List<WatchReport> = emptyList(),
    val subProcessCount: Int = 0,
    // v0.11.2: 子进程职业+倾向展示
    val occupationLabels: List<String> = emptyList(),
    val tendencyLabel: String = ""
)

// ─── 第二层：指挥官 ───
interface Commander {
    val id: String
    /** 所属标签（如 "crud-generator"） */
    val tag: String

    /** 调度：接收主进程命令，决定用哪个协同模式，创建子进程 */
    fun dispatch(order: String, payload: Map<String, Any> = emptyMap()): List<SubProcess>

    /** 收子进程合并结果，汇总后上报主进程。不亲自处理数据。 */
    fun collectAndReport(results: List<ProcessResult>): CommanderReport

    /** 收广播：处理同领域其他指挥官的通知 */
    fun onBroadcast(message: String)

    /** 发布广播：通知同领域其他指挥官 */
    fun broadcast(message: String)
}

// ─── 协同模式选择 ───
enum class CollaborationMode {
    SHARD,      // 分片：均切，各干各的
    PIPELINE,   // 流水线：阶段接力，复制传递
    COMPETE,    // 竞合：双跑仲裁
    SCOUT,      // v0.8.5: 侦查型——先派一个探路，通了再批量投
    COLLECT     // v0.8.5: 收集型——不拆任务，等各进程体做完后汇总合并
}

// ★ v0.11.2: 子进程职业——不替代 CollaborationMode，描述工作本质
enum class SubProcessOccupation(val label: String, val description: String) {
    MICRO("显微", "一个bug拆成20条诊断，慢但全——细节型"),
    DIGEST("摘要", "20个bug一句话说清，快但可能漏——概括型"),
    ASSAULT("攻坚", "bug堆里如鱼得水，无bug反而没活干"),
    GUARD("哨卫", "无bug时主动维护看家，有bug反而不是主场"),
    SOLDIER("列兵", "量大管饱，通用但全领域一般般——万能兵"),
    BURST("爆裂", "极低成本自爆式，一人换数个bug。不换时素质平平")
}

// ★ v0.11.2: 进程倾向——指挥官赋予的临时buff，正交于职业
enum class ProcessTendency {
    NONE,   // 无倾向
    BURST   // 速攻倾向——速度+1，会自爆。由闪电指挥官挂载
}

// ★ v0.11.3: 父进程治理风格——最高指挥官没有职业，但有统治哲学
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

// ★ v0.11.3: 编译情景分类——决定配什么兵，不只看多少兵
enum class SceneType(val label: String, val description: String) {
    CLEAN("干净", "小文件无地狱标记——哨卫看家即可"),
    HEAVY("大块头", "大文件但干净——列兵+摘要批量推进"),
    HOSTILE("地狱", "地狱文件——攻坚+显微+契约高容错"),
    DENSE("高密bug", "历史bug密度高——爆裂速攻+轻量多投"),
    DEFAULT("默认", "不确定就混编——保守策略")
}

// ★ v0.11.3: 情景模板——数据驱动精确匹配
// 维度：规模 × 地狱标记 × 地狱类型 × bug密度 × 批量 × 增量 × 綦桐分
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
    val occupations: List<SubProcessOccupation>,
    val capacityRatio: Float = 0.3f,
    val description: String = ""
)

// ─── 第三层：子进程 ───
interface SubProcess {
    val id: ProcessId
    /** 指挥官标签 */
    val tag: String
    /** 协同模式 */
    val mode: CollaborationMode
    /** v0.11.2: 子进程职业 */
    val occupation: SubProcessOccupation
    /** v0.11.2: 进程倾向（指挥官赋予的临时buff） */
    val tendency: ProcessTendency

    /**
     * 拆解 + 分派 + 合并。
     * 不写一行代码——只把任务拆成进程体能吃的粒度，派出去，收回来合并。
     */
    fun delegate(tasks: List<Any>): List<ProcessResult>

    /** 创建进程体并烙上身份标签 */
    fun spawnBody(bodyId: String): ProcessBody

    /** 合并进程体结果 */
    fun merge(results: List<ProcessResult>): List<ProcessResult>
}

// ─── 第四层：进程体（唯一执行层） ───
interface ProcessBody {
    val id: ProcessId

    /** 执行具体任务。这是全树唯一出汗的地方。 */
    fun execute(task: Any): ProcessResult

    /** 汇报：沿烙好的路径上传，不改路径 */
    fun report(result: ProcessResult): ProcessResult

    /**
     * 复制性引用：从上游取数据时，复制副本。
     * 不拿指针，不共享内存。拿完断开。
     */
    fun fetchFromUpstream(upstreamResult: ProcessData): ProcessData {
        return upstreamResult.snapshot()
    }
}
