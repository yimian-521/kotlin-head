package com.qitong.head.process

import com.qitong.head.eventbus.AsyncIO
import com.qitong.head.eventbus.EventBus
import com.qitong.head.eventbus.EventHandler
import com.qitong.head.eventbus.Event
import com.qitong.head.runtime.HList
import com.qitong.head.runtime.HMap
import java.util.concurrent.atomic.AtomicInteger

/**
 * 进程树协调器 —— v0.8.0-a
 *
 * 连接进程树契约与 kotlin-head 编译管线。
 * 负责：扫描标签 → 创建指挥官 → 按领域分组 → 驱动注解处理全流程。
 *
 * 三层容错：局部错误继续走 / 成功一半分报 / 架构错误才停。
 */
object ProcessCoordinator {

    // 所有已注册的指挥官（按标签索引）
    private val commanders = HMap<String, CommanderImpl>()
    
    // 用于生成唯一ID
    private val cmdCounter = AtomicInteger(0)
    private val subCounter = AtomicInteger(0)
    private val bodyCounter = AtomicInteger(0)
    
    // 全局广播日志（指挥官之间）
    private val broadcastLog = HMap<String, HList<String>>()

    // ★ v0.11.3: 父进程治理风格——影响容错判断、检测进程数量
    var activeStyle: MainProcessStyle = MainProcessStyle.FEDERAL
        private set

    // ★ v0.11.3: 军队池——主动常备+被动应急
    private val armyPool = HList<ArmyProcess>()
    private val armyCounter = AtomicInteger(0)

    fun getArmies(): List<ArmyProcess> = armyPool.toList()
    fun getArmyCount(): Int = armyPool.size

    /** v0.11.3: 运行时切换治理风格 */
    fun setStyle(style: MainProcessStyle) {
        activeStyle = style
        broadcastLog.getOrPut("system") { HList<String>() }
            .add("治理风格切换: $style")
    }

    /** v0.11.3: 主动增派——编译前根据源码特征预判兵力 */
    fun prepareArmy(fileSize: Int, fileCount: Int) {
        val strategy = activeStyle
        when {
            strategy == MainProcessStyle.RENYONG && fileSize < 5000 -> return  // 仁勇小文件不扩
            fileSize < 1000 && fileCount <= 1 -> return  // 太小不用
        }
        val cap = when (strategy) {
            MainProcessStyle.EMERGENCY -> (fileCount * 5).coerceIn(5, 50)
            MainProcessStyle.FEDERAL -> (fileCount * 3).coerceIn(3, 30)
            MainProcessStyle.XIAOXIONG -> (fileCount * 4).coerceIn(4, 40)
            MainProcessStyle.CONSERVATIVE -> (fileCount * 1).coerceIn(1, 5)  // 小心扩，慢但安全
            MainProcessStyle.CONTRACT -> (fileCount * 2).coerceIn(2, 20)  // 精打细算
            else -> (fileCount * 2).coerceIn(1, 20)
        }
        val army = ArmyProcess("army-${armyCounter.incrementAndGet()}", cap, permanent = true)
        armyPool.add(army)
        broadcast("system", "⚔️ 主动增派: $army (文件${fileSize}字节×${fileCount}个)")
    }

    /** v0.11.3: 被动增派——负载超容量时临时扩编 */
    internal fun deployArmy(commander: CommanderImpl, tasks: List<AnnotationTask>):
            List<Pair<AnnotationTask, ProcessResult>>? {
        if (tasks.size <= 3) return null  // 太少不值得扩
        if (activeStyle == MainProcessStyle.CONSERVATIVE && tasks.size <= 10) return null  // 保守只在真的多时才扩
        
        // 先看常备军队有没有活的
        val perm = armyPool.filter { it.isActive() && it.isPermanent() }
        if (perm.isNotEmpty()) {
            val army = perm.last()  // 用最新的常备军
            return army.deploy(tasks, commander)
        }
        // 没有常备就临时创
        val cap = (tasks.size / 2).coerceIn(3, 20)
        val army = ArmyProcess("tmp-army-${armyCounter.incrementAndGet()}", cap, permanent = false)
        armyPool.add(army)
        broadcast("system", "⚔️ 被动增派: $army (${tasks.size}个任务)")
        val results = army.deploy(tasks, commander)
        army.retire()  // 用完退役
        return results
    }

    /** v0.11.3: 退役所有临时军队 */
    fun retireTemporary() {
        armyPool.filter { !it.isPermanent() }.forEach { it.retire() }
    }

    // ─── 初始化：扫描并注册指挥官 ───

    fun initialize() {
        commanders.clear()
        broadcastLog.clear()
        // v0.11.3: 退役所有军队
        for (army in armyPool.toList()) army.retire()

        // v0.8.3: 指挥官订阅 "file" 频道，接管异步文件读取
        EventBus.subscribe("file", object : EventHandler {
            override fun onEvent(event: Event) {
                if (event.type == "file_ready") {
                    val payload = event.payload as? Map<*, *> ?: return
                    val path = payload["path"] as? String ?: return
                    val content = payload["content"] as? String ?: return
                    val fileId = payload["fileId"] as? String ?: path
                    onFileReady(fileId, path, content)
                }
            }
        })

        // v0.8.5: 指挥官订阅 "dep_ready" 频道——被动响应，不主动查依赖图
        EventBus.subscribe("dep_ready", object : EventHandler {
            override fun onEvent(event: Event) {
                if (event.type == "dep_ready") {
                    val payload = event.payload as? Map<*, *> ?: return
                    val fileId = payload["fileId"] as? String ?: return
                    @Suppress("UNCHECKED_CAST")
                    val depsSatisfied = (payload["depsSatisfied"] as? List<String>) ?: emptyList()
                    commanders.values().forEach { cmd -> cmd.onDepReady(fileId, depsSatisfied) }
                }
            }
        })
    }
    
    // v0.8.3: 指挥官调度文件读取（主进程只下命令，不自己读）
    private val pendingFiles = HMap<String, (String) -> Unit>()
    
    /** 调度文件读取：指挥官决定怎么读 */
    fun scheduleFileRead(path: String, fileId: String? = null, onReady: (String) -> Unit = {}) {
        val fid = fileId ?: path
        pendingFiles.put(fid, onReady)
        AsyncIO.readFile(path, fid)
    }
    
    /** 批量调度 */
    fun scheduleFileReads(paths: List<String>) {
        paths.forEachIndexed { i, path -> scheduleFileRead(path, "batch_$i") }
    }
    
    /** 文件读完后的处理 */
    private fun onFileReady(fileId: String, path: String, content: String) {
        val callback = pendingFiles.get(fileId)
        pendingFiles.remove(fileId)
        callback?.invoke(content)
        broadcast("system", "file_ready: $path")
    }

    /** 手动注册一个处理器类（骨架期用） */
    fun registerProcessor(tag: String, processorClass: Class<*>) {
        val pkg = processorClass.`package`?.name ?: ""
        val existing = commanders.values().find { it.tag == tag }
        
        if (existing != null) {
            existing.addProcessor(processorClass)
        } else {
            val samePkgOtherTag = commanders.values().find { it.pkg == pkg && it.tag != tag }
            if (samePkgOtherTag != null) {
                broadcastLog.getOrPut("reverse-exclude") { HList<String>() }
                    .add("WARN: $processorClass 与 ${samePkgOtherTag.id} 同包但标签不同 → 强制分家")
            }
            
            val newCmd = CommanderImpl(
                id = "cmd-${cmdCounter.incrementAndGet()}",
                tag = tag,
                pkg = pkg
            )
            newCmd.addProcessor(processorClass)
            commanders.put(tag, newCmd)
        }
    }

    // ─── 主入口：驱动注解处理 ───

    /**
     * 处理一批注解任务。
     * 按领域自动分派给对应指挥官。
     *
     * @param annotations 待处理的注解列表（每个带标签+负载）
     * @return 按指挥官分组的处理结果
     */
    fun processAnnotations(
        annotations: List<AnnotationTask>
    ): Map<String, CommanderReport> {
        for (task in annotations) {
            if (!commanders.containsKey(task.tag)) {
                val cmd = CommanderImpl(
                    id = "cmd-${cmdCounter.incrementAndGet()}",
                    tag = task.tag
                )
                commanders.put(task.tag, cmd)
            }
        }
        if (commanders.isEmpty()) return emptyMap()

        val results = mutableMapOf<String, CommanderReport>()

        val byTag = mutableMapOf<String, MutableList<AnnotationTask>>()
        for (task in annotations) {
            byTag.getOrPut(task.tag) { mutableListOf() }.add(task)
        }

        val tagList = byTag.entries.toList()
        for ((tag, tasks) in tagList) {
            val commander = commanders.get(tag) ?: continue
            if (commander.getWatchProcesses().isEmpty()) {
                commander.attachWatch(WatchStyle.SENTINEL, "commander")
                commander.attachWatch(WatchStyle.TIMID, "commander")
                commander.attachWatch(WatchStyle.BRAVE, "commander")
                commander.attachWatch(WatchStyle.SAMPLER, "commander")
                commander.attachWatch(WatchStyle.TREND, "commander")
                commander.attachWatch(WatchStyle.PEDANTIC, "commander")
                commander.attachWatch(WatchStyle.LAZY, "commander")
                commander.attachWatch(WatchStyle.STANDARD, "commander")
            }
            val report = runWithFaultTolerance(commander, tasks)
            results[tag] = report
        }

        return results
    }

    // ─── 三层容错判断 ───
    
    private fun runWithFaultTolerance(
        commander: CommanderImpl,
        tasks: List<AnnotationTask>
    ): CommanderReport {
        return try {
            // 判断错误级别
            val failureLevel = assessFailureLevel(tasks)

            when (failureLevel) {
                FailureLevel.LOCAL -> {
                    // 局部错误：继续走，标记+广播
                    val result = commander.dispatchTasks(tasks)
                    commander.collectAndReport(result.map { it.second })
                }
                FailureLevel.PARTIAL -> {
                    // 成功一半：成功半继续、失败半标靶
                    val (good, bad) = tasks.partition { it.isHealthy }
                    val goodResults = commander.dispatchTasks(good)
                    val badReport = bad.map { "✖ ${it.annotationName} @ ${it.location}" }
                    
                    val report = commander.collectAndReport(goodResults.map { it.second })
                    report.copy(
                        summary = "${report.summary} | 失败标靶: ${badReport.joinToString("; ")}"
                    )
                }
                FailureLevel.ARCHITECTURAL -> {
                    // 架构错误：停，汇报根因
                    CommanderReport(
                        commanderId = commander.id,
                        tag = commander.tag,
                        summary = "⛔ 架构错误，停止处理",
                        results = tasks.map {
                            ProcessResult.Failure("架构级: ${it.annotationName}", recoverable = false)
                        },
                        completedCount = 0,
                        totalCount = tasks.size
                    )
                }
            }
        } catch (e: Exception) {
            CommanderReport(
                commanderId = commander.id,
                tag = commander.tag,
                summary = "⛔ 指挥官异常: ${e.message}",
                results = emptyList(),
                completedCount = 0,
                totalCount = tasks.size
            )
        }
    }

    private fun assessFailureLevel(tasks: List<AnnotationTask>): FailureLevel {
        val unhealthyCount = tasks.count { !it.isHealthy }
        if (unhealthyCount == 0) return FailureLevel.LOCAL
        
        val ratio = unhealthyCount.toFloat() / tasks.size
        // v0.11.3: 父进程风格影响容错阈值
        val (archThreshold, partialThreshold) = when (activeStyle) {
            MainProcessStyle.EMERGENCY -> 0.5f to 0.15f   // 紧急：更宽容，少停
            MainProcessStyle.CONSERVATIVE -> 0.6f to 0.2f  // 保守：更早停
            MainProcessStyle.CONTRACT -> 0.9f to 0.4f      // 契约：能修就修
            MainProcessStyle.XIAOXIONG -> 0.7f to 0.25f    // 枭雄：中间偏激进
            MainProcessStyle.RENYONG -> 0.6f to 0.2f       // 仁勇：同保守，保护小弟
            else -> 0.8f to 0.3f                           // 联邦/独裁/正常：默认
        }
        return when {
            ratio >= archThreshold -> FailureLevel.ARCHITECTURAL
            ratio >= partialThreshold -> FailureLevel.PARTIAL
            else -> FailureLevel.LOCAL
        }
    }

    enum class FailureLevel { LOCAL, PARTIAL, ARCHITECTURAL }

    // ─── 广播系统 ───

    private var parserSentinel: SentinelWatch? = null
    fun getParserRecoveryFn(): ((String, () -> Any?) -> Any?)? {
        if (parserSentinel == null) parserSentinel = SentinelWatch(ProcessId("p","s",""), "parser")
        val s = parserSentinel!!; return { l, f -> s.tryRecover(l, f) }
    }
    
    fun broadcast(commanderId: String, message: String) {
        commanders.values().forEach { cmd ->
            if (cmd.id != commanderId) {
                cmd.onBroadcast(message)
            }
        }
        broadcastLog.getOrPut("all") { HList<String>() }
            .add("[$commanderId] → $message")
    }

    fun getBroadcastLog(): Map<String, List<String>> {
        val map = mutableMapOf<String, List<String>>()
        broadcastLog.forEach { k, v -> map[k] = v.toList() }
        return map
    }
}

// ─── 指挥官实现 ───

class CommanderImpl(
    override val id: String,
    override val tag: String,
    val pkg: String = "",
    val commanderType: CommanderType = CommanderType.STANDARD  // v0.11.2: 默认常规
) : Commander {

    private val processors = HList<Class<*>>()
    private val subProcesses = HList<SubProcessImpl>()
    private val subCounter = AtomicInteger(0)

    // v0.8.5: 挂载的检测进程列表（旁路观察，不阻塞子进程）
    private val watchProcesses = HList<WatchProcess>()

    // v0.8.5: 暂缓队列——依赖未就绪的任务暂存，等 "dep_ready" 再 dispatch
    private val pendingTasks = HList<AnnotationTask>()

    fun addProcessor(clazz: Class<*>) {
        processors.add(clazz)
    }

    fun processorCount(): Int = processors.size

    // v0.8.5: 注册检测进程（每个指挥官可挂多个，不同风格）
    fun attachWatch(watch: WatchProcess) {
        watchProcesses.add(watch)
    }

    fun attachWatch(style: WatchStyle, targetSubProcessId: String) {
        val watchId = ProcessId(id, "watch-${watchProcesses.size}", "")
        watchProcesses.add(WatchProcessFactory.create(style, watchId, targetSubProcessId))
    }

    fun getWatchProcesses(): List<WatchProcess> = watchProcesses.toList()

    // v0.8.5: 被动响应——指挥官不再主动查依赖图，等 "dep_ready" 通知
    fun onDepReady(fileId: String, depsSatisfied: List<String>) {
        // 从暂缓队列取出依赖已就绪的任务
        val ready = pendingTasks.filter { task ->
            depsSatisfied.contains(task.location)
        }
        pendingTasks.removeAll(ready)
        // dispatch 就绪的任务
        for (task in ready) {
            dispatchTasks(listOf(task))
        }
    }

    // v0.8.5: 暂缓任务——依赖未就绪先存着
    fun holdTask(task: AnnotationTask) {
        pendingTasks.add(task)
    }

    fun pendingCount(): Int = pendingTasks.size

    // v0.8.5: 通知所有检测进程观察一次步骤
    fun notifyWatches(step: ProcessStep) {
        watchProcesses.forEach { watch ->
            val report = watch.observe(step)
            if (report != null && report.anomalies.isNotEmpty()) {
                // 异常汇报——广播给该领域其他指挥官
                broadcast("WATCH_ALERT: ${watch.style.label} 报告: ${report.anomalies.joinToString()}")
            }
        }
    }

    // v0.8.5: 收集所有检测进程的最终报告
    fun collectWatchReports(): List<WatchReport> {
        return watchProcesses.map { it.finalReport() }.toList()
    }

    override fun dispatch(order: String, payload: Map<String, Any>): List<SubProcess> {
        // 每个子任务创建一个子进程
        val taskCount = (payload["taskCount"] as? Int) ?: 1
        val mode = selectMode(payload)
        // v0.11.2: 根据指挥官类型选职业+倾向
        val occupation = selectOccupation()
        val tendency = selectTendency()
        return (0 until taskCount).map { _ ->
            val sp = SubProcessImpl(
                id = ProcessId(
                    commanderId = id,
                    subProcessId = "sub-${subCounter.incrementAndGet()}",
                    bodyId = ""
                ),
                tag = tag,
                mode = mode,
                parentCommander = this,
                occupation = occupation,
                tendency = tendency
            )
            subProcesses.add(sp)
            sp
        }
    }

    /** v0.11.2: 根据指挥官类型选子进程职业 */
    private fun selectOccupation(): SubProcessOccupation = when (commanderType) {
        CommanderType.MARSHAL -> {
            // 元帅混编：显微+摘要+列兵轮流
            val idx = subCounter.get() % 3
            when (idx) { 0 -> SubProcessOccupation.MICRO; 1 -> SubProcessOccupation.DIGEST; else -> SubProcessOccupation.SOLDIER }
        }
        CommanderType.ELITE -> {
            // 尖刀：攻坚+哨卫，精英配对
            if (subCounter.get() % 2 == 0) SubProcessOccupation.ASSAULT else SubProcessOccupation.GUARD
        }
        CommanderType.LIGHTNING -> SubProcessOccupation.BURST   // 闪电→爆裂
        CommanderType.BUG -> SubProcessOccupation.MICRO         // Bug类→显微
        CommanderType.STRATEGIST -> SubProcessOccupation.DIGEST // 军师→摘要
        else -> SubProcessOccupation.SOLDIER                    // 其他→列兵
    }

    /** v0.11.2: 闪电指挥官给手下挂速攻倾向 */
    private fun selectTendency(): ProcessTendency = when (commanderType) {
        CommanderType.LIGHTNING -> ProcessTendency.BURST
        else -> ProcessTendency.NONE
    }

    /** 根据任务特征自动选协同模式 */
    private fun selectMode(payload: Map<String, Any>): CollaborationMode {
        val stages = payload["stages"] as? Int ?: 1
        val critical = payload["critical"] as? Boolean ?: false
        return when {
            critical -> CollaborationMode.COMPETE
            stages > 1 -> CollaborationMode.PIPELINE
            else -> CollaborationMode.SHARD
        }
    }

    override fun collectAndReport(results: List<ProcessResult>): CommanderReport {
        val successCount = results.count { it is ProcessResult.Success || it is ProcessResult.PartialSuccess }
        // v0.11.2: 收集所有子进程的职业标签
        val occLabels = subProcesses.map { it.occupation.label }.toList().distinct()
        val tendLabel = if (commanderType == CommanderType.LIGHTNING) "速攻" else ""
        return CommanderReport(
            commanderId = id,
            tag = tag,
            summary = buildSummary(results),
            results = results,
            completedCount = successCount,
            totalCount = results.size,
            // v0.8.5: 树状展示
            commanderTypeLabel = commanderType.label,
            modeLabel = commanderType.defaultCollaborationMode().name.toLowerCase(),
            watchReports = collectWatchReports(),
            subProcessCount = subProcesses.size,
            // v0.11.2
            occupationLabels = occLabels,
            tendencyLabel = tendLabel
        )
    }

    private fun buildSummary(results: List<ProcessResult>): String {
        val success = results.count { it is ProcessResult.Success }
        val partial = results.count { it is ProcessResult.PartialSuccess }
        val fail = results.count { it is ProcessResult.Failure && it.recoverable }
        val arch = results.count { it is ProcessResult.Failure && !it.recoverable }
        
        return buildString {
            if (success > 0) append("✓$success ")
            if (partial > 0) append("◐$partial ")
            if (fail > 0) append("✖$fail ")
            if (arch > 0) append("⛔$arch")
            if (length == 0) append("∅")
        }
    }

    override fun onBroadcast(message: String) {
        // 收到广播：通知所有子进程
        subProcesses.forEach { it.onBroadcast(message) }
    }

    override fun broadcast(message: String) {
        ProcessCoordinator.broadcast(id, message)
    }

    // ─── 内部：直接调度任务（供 ProcessCoordinator 调用） ───
    
    fun dispatchTasks(tasks: List<AnnotationTask>): List<Pair<AnnotationTask, ProcessResult>> {
        if (tasks.isEmpty()) return emptyList()

        // v0.11.3: 被动增派——任务过多时走军队并行
        if (tasks.size > 5) {
            val armyResults = ProcessCoordinator.deployArmy(this, tasks)
            if (armyResults != null) return armyResults
        }

        // v0.9.1: 通知检测进程开始
        notifyWatches(ProcessStep(bodyId = "commander", action = "dispatch_start", durationNs = 0))

        val mode = selectMode(mapOf(
            "taskCount" to tasks.size,
            "stages" to (if (tasks.any { it.stages > 1 }) 2 else 1)
        ))

        val results = when (mode) {
            CollaborationMode.SHARD -> dispatchShard(tasks)
            CollaborationMode.PIPELINE -> dispatchPipeline(tasks)
            CollaborationMode.COMPETE -> dispatchCompete(tasks)
            CollaborationMode.SCOUT -> dispatchScout(tasks)
            CollaborationMode.COLLECT -> dispatchCollect(tasks)
        }

        // v0.9.1: 通知检测进程完成
        val hasFailure = results.any { it.second is ProcessResult.Failure }
        notifyWatches(ProcessStep(
            bodyId = "commander",
            action = "dispatch_done",
            durationNs = 0,
            abnormal = hasFailure
        ))

        return results
    }

    private fun dispatchShard(tasks: List<AnnotationTask>): List<Pair<AnnotationTask, ProcessResult>> {
        // 均切：每个任务一个进程体
        return tasks.map { task ->
            val body = spawnBodyFor(task)
            task to body.execute(task.payload)
        }
    }

    private fun dispatchPipeline(tasks: List<AnnotationTask>): List<Pair<AnnotationTask, ProcessResult>> {
        // 流水线：阶段接力，复制性引用
        val results = mutableListOf<Pair<AnnotationTask, ProcessResult>>()
        var upstreamData: ProcessData? = null

        for (task in tasks) {
            val body = spawnBodyFor(task)
            // 复制性引用：从上游取副本
            if (upstreamData != null) {
                body.fetchFromUpstream(upstreamData)
            }
            val result = body.execute(task.payload)
            if (result is ProcessResult.Success) {
                upstreamData = result.data.snapshot()  // 传副本给下游
            }
            results.add(task to result)
        }
        return results
    }

    private fun dispatchCompete(tasks: List<AnnotationTask>): List<Pair<AnnotationTask, ProcessResult>> {
        // 竞合：每个关键任务双跑
        return tasks.map { task ->
            val bodyA = spawnBodyFor(task, "A")
            val bodyB = spawnBodyFor(task, "B")
            
            val resultA = bodyA.execute(task.payload)
            val resultB = bodyB.execute(task.payload)
            
            // 仲裁：能通过验证的赢
            val winner = when {
                resultA is ProcessResult.Success && resultB is ProcessResult.Success -> {
                    // 都成功 → 选数据更完整的
                    val sizeA = resultA.data.content.toString().length
                    val sizeB = resultB.data.content.toString().length
                    if (sizeA >= sizeB) resultA else resultB
                }
                resultA is ProcessResult.Success -> resultA
                resultB is ProcessResult.Success -> resultB
                resultA is ProcessResult.PartialSuccess && resultB is ProcessResult.Failure -> resultA
                resultB is ProcessResult.PartialSuccess && resultA is ProcessResult.Failure -> resultB
                else -> {
                    // 双失败 → 广播通知同领域
                    broadcast("COMPETE_DUAL_FAILURE: ${task.annotationName}")
                    ProcessResult.Failure("竞合双失败: ${task.annotationName}", recoverable = false)
                }
            }
            task to winner
        }
    }

    // v0.8.5: 侦查型——先派一个探路，通了再批量投
    private fun dispatchScout(tasks: List<AnnotationTask>): List<Pair<AnnotationTask, ProcessResult>> {
        if (tasks.isEmpty()) return emptyList()
        val results = mutableListOf<Pair<AnnotationTask, ProcessResult>>()
        
        // 派第一个探路
        val scout = tasks.first()
        val scoutBody = spawnBodyFor(scout, "scout")
        val scoutResult = scoutBody.execute(scout.payload)
        
        if (scoutResult is ProcessResult.Success) {
            // 通了！批量投剩余任务
            results.add(scout to scoutResult)
            for (task in tasks.drop(1)) {
                val body = spawnBodyFor(task)
                results.add(task to body.execute(task.payload))
            }
        } else {
            // 探路失败——返回失败结果，不浪费后续任务
            results.add(scout to scoutResult)
            broadcast("SCOUT_FAILED: ${scout.annotationName} 探路失败，${tasks.size - 1} 个任务未执行")
        }
        return results
    }

    // v0.8.5: 收集型——不拆任务，等各进程体做完后汇总合并
    private fun dispatchCollect(tasks: List<AnnotationTask>): List<Pair<AnnotationTask, ProcessResult>> {
        // 所有任务并发出，结果统一收集
        val bodies = tasks.map { task -> spawnBodyFor(task) }
        val results = bodies.zip(tasks).map { (body, task) ->
            task to body.execute(task.payload)
        }
        // 按成功/部分成功/失败排序合并
        return results.sortedBy { (_, result) ->
            when (result) {
                is ProcessResult.Success -> 0
                is ProcessResult.PartialSuccess -> 1
                is ProcessResult.Failure -> if (result.recoverable) 2 else 3
            }
        }
    }

    private val bodyIdCounter = AtomicInteger(0)
    
    private fun spawnBodyFor(task: AnnotationTask, suffix: String = ""): ProcessBodyImpl {
        return ProcessBodyImpl(
            id = ProcessId(
                commanderId = id,
                subProcessId = task.subProcessId ?: "auto",
                bodyId = "body-${bodyIdCounter.incrementAndGet()}$suffix"
            ),
            tag = tag
        )
    }
}

// ─── 子进程实现 ───

class SubProcessImpl(
    override val id: ProcessId,
    override val tag: String,
    override val mode: CollaborationMode,
    private val parentCommander: CommanderImpl,
    // v0.11.2: 子进程职业+倾向
    override val occupation: SubProcessOccupation = SubProcessOccupation.SOLDIER,
    override val tendency: ProcessTendency = ProcessTendency.NONE
) : SubProcess {

    private val bodies = HList<ProcessBodyImpl>()

    override fun delegate(tasks: List<Any>): List<ProcessResult> {
        // 子进程不写代码——只拆解+分派+合并
        val results = mutableListOf<ProcessResult>()
        for (task in tasks) {
            val body = spawnBody("body-${bodies.size}")
            results.add(body.execute(task))
        }
        return merge(results)
    }

    override fun spawnBody(bodyId: String): ProcessBody {
        val body = ProcessBodyImpl(
            id = id.copy(bodyId = bodyId),
            tag = tag
        )
        bodies.add(body)
        return body
    }

    override fun merge(results: List<ProcessResult>): List<ProcessResult> {
        // 合并结果：保持成功在前、部分成功在中、失败在后
        return results.sortedBy {
            when (it) {
                is ProcessResult.Success -> 0
                is ProcessResult.PartialSuccess -> 1
                is ProcessResult.Failure -> if (it.recoverable) 2 else 3
            }
        }
    }

    fun onBroadcast(message: String) {
        // 广播传递给所有进程体
        bodies.forEach { it.onBroadcast(message) }
    }
}

// ─── 进程体实现 ───

class ProcessBodyImpl(
    override val id: ProcessId,
    val tag: String
) : ProcessBody {

    private var cachedUpstream: ProcessData? = null
    private val broadcastMessages = HList<String>()

    override fun execute(task: Any): ProcessResult {
        return try {
            // 实际执行：调用对应注解处理器
            // TODO: 通过反射调用 @ProcessorTag 标注的类的处理方法
            val content = "processed: $task by body:${id.bodyId} (cmd:${id.commanderId})"
            ProcessResult.Success(
                data = ProcessData(content = content, sourceBodyId = id.bodyId),
                metrics = mapOf("elapsed_ms" to 0, "tag" to tag)
            )
        } catch (e: Exception) {
            ProcessResult.Failure(
                error = "进程体 ${id.bodyId} 执行异常: ${e.message}",
                recoverable = true
            )
        }
    }

    override fun report(result: ProcessResult): ProcessResult {
        // 沿烙好的路径上传：不改路径，原样返回
        return result
    }

    override fun fetchFromUpstream(upstreamResult: ProcessData): ProcessData {
        // 复制性引用：深复制，断开共享
        cachedUpstream = upstreamResult.snapshot()
        return cachedUpstream!!
    }

    fun onBroadcast(message: String) {
        broadcastMessages.add(message)
    }

    fun getBroadcastMessages(): List<String> = broadcastMessages.toList()
}

// ─── 注解任务数据结构 ───

data class AnnotationTask(
    /** 注解全限定名，如 javax.persistence.Entity */
    val annotationName: String,
    /** 处理器标签，如 "crud-generator" */
    val tag: String,
    /** 注解所在位置，如 "ConversationDao.kt:15" */
    val location: String,
    /** 任务负载（注解的参数等） */
    val payload: Any,
    /** 是否健康（局部错误判断用） */
    val isHealthy: Boolean = true,
    /** 多阶段任务时阶段数 */
    val stages: Int = 1,
    /** 关联的子进程ID */
    val subProcessId: String? = null
)