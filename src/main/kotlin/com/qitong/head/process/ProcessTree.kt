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
    val commanderTypeLabel: String = "执行类",
    val modeLabel: String = "均切",
    val watchReports: List<WatchReport> = emptyList(),
    val subProcessCount: Int = 0
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

// ─── 第三层：子进程 ───
interface SubProcess {
    val id: ProcessId
    /** 指挥官标签 */
    val tag: String
    /** 协同模式 */
    val mode: CollaborationMode

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
