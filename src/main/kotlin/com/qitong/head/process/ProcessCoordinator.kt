package com.qitong.head.process

import com.qitong.head.eventbus.AsyncIO
import com.qitong.head.eventbus.EventBus
import com.qitong.head.eventbus.EventHandler
import com.qitong.head.eventbus.Event
import com.qitong.head.headstd.HList
import com.qitong.head.headstd.HMap
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

    // ★ v0.11.5: 增派策略——保护老兵还是不管经验
    var reinforcePolicy: ReinforcePolicy = ReinforcePolicy.PROTECTIVE

    // ★ v0.11.5: 进程身份注册表
    private val identityRegistry = mutableMapOf<String, ProcessIdentity>()
    private val pidCounter = AtomicInteger(0)

    // ★ v0.11.3: 军队池——主动常备+被动应急
    private val armyPool = HList<ArmyProcess>()
    private val armyCounter = AtomicInteger(0)

    fun registerIdentity(occupation: SubProcessOccupation): ProcessIdentity {
        val pid = "proc-${pidCounter.incrementAndGet()}"
        val identity = ProcessIdentity(pid = pid, currentOccupation = occupation)
        identity.career[occupation] = ExperienceCache(occupation)
        identityRegistry[pid] = identity
        return identity
    }

    fun getIdentity(pid: String): ProcessIdentity? = identityRegistry[pid]
    fun allIdentities(): List<ProcessIdentity> = identityRegistry.values.toList()

    fun getArmies(): List<ArmyProcess> = armyPool.toList()
    fun getArmyCount(): Int = armyPool.size

    // ★ v0.11.3: 编译情景检测
    var currentScene: SceneType = SceneType.DEFAULT
        private set
    var currentProfile: SceneProfile? = null
        private set

    // ★ v0.11.3: 编译情景动态推导引擎
    data class SceneInput(
    val fileSize: Int, val fileCount: Int, val isHostile: Boolean,
    val hellType: HellType, val bugDensity: Float,
    val isBatch: Boolean, val incremental: Boolean, val qitongScore: Int,
    val style: MainProcessStyle,
    val trend: Int = 0  // v0.12.4: 正=Bug增长→需快攻，负=衰减→需收尾，零=稳定
)

object SceneEngine {
    /** v0.11.5: derive只出结构——不拼字符串，保持纳秒级 */
    fun derive(input: SceneInput): Pair<List<SubProcessOccupation>, Float> {
        val occs = mutableSetOf<SubProcessOccupation>()
        val ratio = floatArrayOf(0.35f)
        
        // R1: 地狱类型
        if (input.isHostile) {
            when (input.hellType) {
                HellType.SYNTAX -> { occs.add(SubProcessOccupation.MICRO); occs.add(SubProcessOccupation.ASSAULT) }
                HellType.TYPE -> { occs.add(SubProcessOccupation.DIGEST); occs.add(SubProcessOccupation.MICRO) }
                HellType.LINK -> { occs.add(SubProcessOccupation.BURST); occs.add(SubProcessOccupation.SOLDIER) }
                HellType.MIXED -> { occs.add(SubProcessOccupation.ASSAULT); occs.add(SubProcessOccupation.MICRO); occs.add(SubProcessOccupation.DIGEST) }
                HellType.NONE -> {}
            }
        }
        // R2: 綦桐分
        if (input.qitongScore >= 8 && input.isHostile) { ratio[0] *= 1.3f }
        // R3: 规模
        when {
            input.fileSize < 3000 -> { ratio[0] *= 0.5f; if (occs.isEmpty() && !input.isHostile) occs.add(SubProcessOccupation.GUARD) }
            input.fileSize < 15000 -> { ratio[0] = 0.4f; if (occs.isEmpty()) occs.add(SubProcessOccupation.SOLDIER) }
            input.fileSize < 50000 -> { ratio[0] = 0.3f; occs.add(SubProcessOccupation.SOLDIER); occs.add(SubProcessOccupation.DIGEST) }
            input.fileSize < 200000 -> { ratio[0] = 0.25f; occs.add(SubProcessOccupation.SOLDIER); occs.add(SubProcessOccupation.DIGEST); occs.add(SubProcessOccupation.GUARD) }
            else -> { ratio[0] = 0.2f; occs.add(SubProcessOccupation.SOLDIER); occs.add(SubProcessOccupation.DIGEST); occs.add(SubProcessOccupation.MICRO); occs.add(SubProcessOccupation.GUARD) }
        }
        // R4: bug密度
        if (input.bugDensity >= 0.3f) { occs.add(SubProcessOccupation.BURST) }
        // R5: 批量
        if (input.isBatch && input.fileSize > 3000) { occs.add(SubProcessOccupation.DIGEST) }
        // R6: 增量
        if (input.incremental) { ratio[0] *= 1.5f; occs.remove(SubProcessOccupation.ASSAULT) }
        // R7: 风格
        when (input.style) {
            MainProcessStyle.EMERGENCY -> { occs.clear(); occs.add(SubProcessOccupation.BURST); ratio[0] = 0.6f }
            MainProcessStyle.CONSERVATIVE -> { occs.clear(); occs.add(SubProcessOccupation.GUARD); ratio[0] *= 0.6f }
            MainProcessStyle.CONTRACT -> { ratio[0] = (ratio[0] * 0.8f).coerceAtLeast(0.15f) }
            else -> {}
        }
        
        if (occs.isEmpty()) occs.add(SubProcessOccupation.SOLDIER)
        return Pair(occs.toList(), ratio[0].coerceIn(0.1f, 1f))
    }

    /** v0.12.4: 动态模式识别——见趋势调整兵种
     *  衰减(trend<-10)→GUARD | 增长(trend>10)→BURST | 稳定(-10~10)→GUARD
     *  🏗️ 主力80%+预备20%混合编队机制待实现 */
    fun deriveTrend(input: SceneInput): Pair<List<SubProcessOccupation>, Float> {
        val (base, ratio) = derive(input)
        val adjusted = base.toMutableList()
        val trend = input.trend
        
        // 趋势调整兵种（用现有枚举：GUARD稳守 / BURST快攻 / SOLDIER通用）
        if (trend > 10) {
            if (SubProcessOccupation.BURST !in adjusted) adjusted.add(0, SubProcessOccupation.BURST)
        } else if (trend < -10) {
            if (SubProcessOccupation.GUARD !in adjusted) adjusted.add(SubProcessOccupation.GUARD)
        } else {
            if (SubProcessOccupation.GUARD !in adjusted) adjusted.add(SubProcessOccupation.GUARD)
        }
        // 预备兵
        val backup = if (SubProcessOccupation.SOLDIER !in adjusted) SubProcessOccupation.SOLDIER else SubProcessOccupation.GUARD
        if (backup !in adjusted && adjusted.size < 4) adjusted.add(backup)
        
        return Pair(adjusted, ratio)
    }

    /** v0.11.5: 态势简报——只在需要广播时调用，不参与热路径 */
    fun briefOf(input: SceneInput, occs: List<SubProcessOccupation>, ratio: Float): String {
        val r = mutableListOf<String>()
        if (input.isHostile) {
            when (input.hellType) {
                HellType.SYNTAX -> r.add("语法地狱"); HellType.TYPE -> r.add("类型地狱")
                HellType.LINK -> r.add("链接地狱"); HellType.MIXED -> r.add("混合地狱"); else -> {}
            }
        }
        if (input.qitongScore >= 8 && input.isHostile) r.add("綦桐")
        when {
            input.fileSize < 3000 -> r.add("微型")
            input.fileSize < 15000 -> r.add("轻型")
            input.fileSize < 50000 -> r.add("中型")
            input.fileSize < 200000 -> r.add("重型")
            else -> r.add("超大型")
        }
        if (input.bugDensity >= 0.3f) r.add("高密bug")
        if (input.isBatch && input.fileSize > 3000) r.add("批量")
        if (input.incremental) r.add("增量")
        when (input.style) {
            MainProcessStyle.EMERGENCY -> r.add("紧急"); MainProcessStyle.CONSERVATIVE -> r.add("保守")
            MainProcessStyle.CONTRACT -> r.add("契约"); else -> r.add("联邦")
        }
        return r.joinToString("·")
    }

    /** v0.11.6: 场景指纹——模糊化避免无限拆分 */
    fun fingerprintOf(input: SceneInput): String {
        val sizeBucket = when {
            input.fileSize < 3000 -> "S"; input.fileSize < 15000 -> "M"
            input.fileSize < 50000 -> "L"; else -> "XL"
        }
        val dense = if (input.bugDensity >= 0.3f) "D" else "d"
        return "${input.hellType.name}_${sizeBucket}_${dense}_${if(input.incremental)"I" else "i"}_${input.style.name}"
    }

    /** v0.11.6: 缓存网关——老兵命中可信≥10→跳过derive */
    fun deriveCached(input: SceneInput, identity: ProcessIdentity?): Pair<List<SubProcessOccupation>, Float> {
        if (identity == null) return derive(input)
        val cache = identity.career[identity.currentOccupation] ?: return derive(input)
        val fp = fingerprintOf(input)
        val (entry, trusted, _) = cache.lookup(fp)
        if (entry != null && trusted) {
            return Pair(entry.occs, entry.ratio)  // 专家跳过7条规则链
        }
        val result = derive(input)
        cache.store(fp, result.first, result.second, "success")
        return result
    }

    /** v0.12.4: 军师基类匹配——根据环境和队友类型返回匹配度
     *  安全(低密度) → BURST/MICRO快攻 | 中等 → DIGEST缺方向受益最大
     *  危险(高密度) → 全员匹配度低，应独行扩军 */
    fun matchStrategist(bugDensity: Float, buddyType: SubProcessOccupation): Float {
        return when {
            bugDensity < 0.2f -> when (buddyType) {  // 安全
                SubProcessOccupation.BURST -> 0.90f   // 快攻
                SubProcessOccupation.MICRO -> 0.75f    // 精英级
                SubProcessOccupation.GUARD -> 0.40f
                SubProcessOccupation.DIGEST -> -0.15f
                SubProcessOccupation.SOLDIER -> -0.25f
                SubProcessOccupation.ASSAULT -> -0.20f
            }
            bugDensity < 0.5f -> when (buddyType) {  // 中等
                SubProcessOccupation.DIGEST -> 0.85f   // 缺方向受益最大
                SubProcessOccupation.MICRO -> 0.70f
                SubProcessOccupation.BURST -> 0.60f
                SubProcessOccupation.SOLDIER -> 0.45f
                SubProcessOccupation.GUARD -> 0.40f
                SubProcessOccupation.ASSAULT -> -0.05f
            }
            else -> return 0.10f  // 危险——不匹配，独行
        }
    }
    fun severityScore(occs: List<SubProcessOccupation>, ratio: Float, isHostile: Boolean): Int {
        var score = (occs.size * 2.5f).toInt()
        if (isHostile) score += 2
        if (SubProcessOccupation.BURST in occs) score += 1
        if (SubProcessOccupation.ASSAULT in occs) score += 1
        if (SubProcessOccupation.MICRO in occs) score += 1
        if (ratio < 0.2f) score -= 1
        if (ratio > 0.5f) score += 1
        return score.coerceIn(0, 10)
    }

    /** v0.11.4: 根据文件特征自动选最合适的父进程风格 */
    fun autoStyle(isHostile: Boolean, bugDensity: Float, fileSize: Int, incremental: Boolean): MainProcessStyle {
        return when {
            isHostile -> MainProcessStyle.CONTRACT        // 地狱→契约
            bugDensity > 0.3f -> MainProcessStyle.XIAOXIONG // 高密bug→枭雄
            fileSize > 200000 -> MainProcessStyle.FEDERAL   // 超大→元帅
            incremental -> MainProcessStyle.EMERGENCY       // 增量→紧急
            else -> MainProcessStyle.FEDERAL               // 默认联邦
        }
    }
}

    /** v0.11.3: 运行时切换治理风格 */
    fun setStyle(style: MainProcessStyle) {
        activeStyle = style
        broadcastLog.getOrPut("system") { HList<String>() }
            .add("治理风格切换: $style")
    }

    // ★ v0.11.7: 多项目模式开关
    var multiProjectMode: Boolean = false

    // ★ v0.11.8: Java 检测开关
    var javaDetectionEnabled: Boolean = false
        private set

    /** 启用 Java 检测——尝试挂载 java-head */
    fun enableJavaDetection(): Boolean {
        val ok = JavaHeadAdapter.tryMount()
        if (ok) javaDetectionEnabled = true
        return ok
    }

    /** 关闭 Java 检测 */
    fun disableJavaDetection() {
        JavaHeadAdapter.unmount()
        javaDetectionEnabled = false
    }

    /** 当前 Java 检测通道 */
    val javaChannel: JavaDetectionChannel get() = JavaHeadAdapter.channel

    /** v0.11.3: 主动增派——编译前根据源码特征预判兵力 */
    fun prepareArmy(fileSize: Int, fileCount: Int, isHostile: Boolean = false, bugDensity: Float = 0f, hellType: HellType = HellType.NONE, incremental: Boolean = false, qitongScore: Int = 0, multiProjectMode: Boolean = false) {
        val strategy = activeStyle
        if (strategy == MainProcessStyle.RENYONG && fileSize < 5000 && !isHostile) return
        if (strategy == MainProcessStyle.CONSERVATIVE && fileSize < 3000 && !isHostile) return
        if (fileSize < 500 && fileCount <= 1 && !isHostile) return
        
        val input = SceneInput(fileSize, fileCount, isHostile, hellType, bugDensity, fileCount > 1, incremental, qitongScore, strategy)
        val (occs, ratio) = SceneEngine.derive(input)
        val estTasks = (fileSize / 500).coerceIn(1, 100)
        val cap = ((estTasks * ratio).toInt()).coerceIn(1, 60)
        val army = ArmyProcess("army-${armyCounter.incrementAndGet()}", cap, permanent = true, occupations = occs)
        armyPool.add(army)
        broadcast("system", "⚔️ 主动增派 [${SceneEngine.briefOf(input, occs, ratio)}] → ${occs.map { it.name }.joinToString("+")} cap@${"%.2f".format(ratio)}")
        // v0.12.4: 军师独行扩军检测
        checkSoloStrategist(bugDensity, 0)
    }

    /** v0.11.4: 被动增派——走SceneEngine，不再当瞎子 */
    internal fun deployArmy(commander: CommanderImpl, tasks: List<AnnotationTask>):
            List<Pair<AnnotationTask, ProcessResult>>? {
        if (tasks.size <= 3) return null
        if (activeStyle == MainProcessStyle.CONSERVATIVE && tasks.size <= 10) return null
        
        var totalCap = 0; var totalLoad = 0
        for (army in armyPool.toList()) {
            if (army.isActive()) { totalCap += army.capacity; totalLoad += army.currentLoad() }
        }
        val remainingCap = totalCap - totalLoad
        if (tasks.size <= remainingCap.coerceAtLeast(1)) return null
        
        val perm = armyPool.filter { it.isActive() && it.isPermanent() }
        if (perm.isNotEmpty()) {
            val army = perm.last()
            return army.deploy(tasks, commander)
        }
        
        // v0.11.4: 被动增派走SceneEngine推导
        val input = SceneInput(tasks.size * 500, 1, false, HellType.NONE, 0f, false, false, 0, activeStyle)
        val (occs, ratio) = SceneEngine.derive(input)
        val cap = ((tasks.size * ratio).toInt()).coerceIn(3, 30)
        val army = ArmyProcess("tmp-army-${armyCounter.incrementAndGet()}", cap, permanent = false, occupations = occs)
        armyPool.add(army)
        broadcast("system", "⚔️ 被动增派 [${SceneEngine.briefOf(input, occs, ratio)}] → ${occs.map { it.name }.joinToString("+")} cap=$cap")
        val results = army.deploy(tasks, commander)
        army.retire()
        return results
    }

    /** v0.11.3: 退役所有临时军队 */
    fun retireTemporary() {
        armyPool.filter { !it.isPermanent() }.forEach { it.retire() }
    }

    // v0.12.4: 军师独行扩军——总指挥层决策，军师不上报，总指挥主动扫描
    fun handleStrategistSolo(bugDensity: Float, trend: Int) {
        val isUrgent = bugDensity > 0.5f || trend > 20
        val isHostile = bugDensity > 0.5f || trend > 20  // v0.12.4-fix: trend>20也标记hostile
        val (occs, ratio) = SceneEngine.deriveTrend(
            SceneInput(10000, 5, isHostile, HellType.NONE, bugDensity, false, false, 0, activeStyle, trend)
        )
        val cap = if (isUrgent) 12 else 8
        val army = ArmyProcess("solo-${armyCounter.incrementAndGet()}", cap, permanent = false, occupations = occs)
        armyPool.add(army)
        broadcast("system", "🧠 军师独行扩军 [urgent=$isUrgent] → ${occs.map { it.name }.joinToString("+")} cap=$cap")
    }

    private fun checkSoloStrategist(bugDensity: Float = 0f, trend: Int = 0): Boolean {
        // v0.12.4-fix: 先检查是否已有活跃solo军队，避免重复创建
        val hasSolo = armyPool.any { it.id.startsWith("solo-") && it.isActive() }
        if (hasSolo) return false
        val strategists = commanders.values().filter { it.commanderType == CommanderType.STRATEGIST }
        if (strategists.size == 1 && commanders.size == 1) {
            handleStrategistSolo(bugDensity, trend)
            return true
        }
        return false
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
                    val result = commander.dispatchTasks(tasks)
                    commander.collectAndReport(HList.from(result.toList().map { it.second }))
                }
                FailureLevel.PARTIAL -> {
                    val (good, bad) = tasks.partition { it.isHealthy }
                    val goodResults = commander.dispatchTasks(good)
                    val badReport = bad.map { "✖ ${it.annotationName} @ ${it.location}" }
                    val report = commander.collectAndReport(HList.from(goodResults.toList().map { it.second }))
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
                        results = HList.from(tasks.map {
                            ProcessResult.Failure("架构级: ${it.annotationName}", recoverable = false)
                        }),
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
                results = HList(),
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
    fun collectWatchReports(): HList<WatchReport> {
        return HList.from(watchProcesses.toList().map { it.finalReport() })
    }

    override fun dispatch(order: String, payload: HMap<String, Any>): HList<SubProcess> {
        val taskCount = (payload.get("taskCount") as? Int) ?: 1
        val mode = selectMode(payload)
        val occupation = selectOccupation()
        val tendency = selectTendency()
        val result = HList<SubProcess>()
        for (i in 0 until taskCount) { result.add(SubProcessImpl(
            id = ProcessId(commanderId = id, subProcessId = "sub-${subCounter.incrementAndGet()}", bodyId = ""),
            tag = tag, mode = mode, parentCommander = this, occupation = occupation, tendency = tendency
        ).also { subProcesses.add(it) }) }
        return result
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
    private fun selectMode(payload: HMap<String, Any>): CollaborationMode {
        val stages = (payload.get("stages") as? Int) ?: 1
        val critical = (payload.get("critical") as? Boolean) ?: false
        return when {
            critical -> CollaborationMode.COMPETE
            stages > 1 -> CollaborationMode.PIPELINE
            else -> CollaborationMode.SHARD
        }
    }

    override fun collectAndReport(results: HList<ProcessResult>): CommanderReport {
        val list = results.toList()
        val successCount = list.count { it is ProcessResult.Success || it is ProcessResult.PartialSuccess }
        val occLabels = mutableListOf<String>()
        for (sp in subProcesses.toList()) { val l = sp.occupation.label; if (l !in occLabels) occLabels.add(l) }
        return CommanderReport(
            commanderId = id, tag = tag,
            summary = buildSummary(results),
            results = results,
            completedCount = successCount, totalCount = results.size,
            commanderTypeLabel = commanderType.label,
            modeLabel = commanderType.defaultCollaborationMode().name.toLowerCase(),
            watchReports = collectWatchReports(),
            subProcessCount = subProcesses.size,
            occupationLabels = HList.from(occLabels),
            tendencyLabel = if (commanderType == CommanderType.LIGHTNING) "速攻" else ""
        )
    }

    private fun buildSummary(results: HList<ProcessResult>): String {
        val list = results.toList()
        val success = list.count { it is ProcessResult.Success }
        val partial = list.count { it is ProcessResult.PartialSuccess }
        val fail = list.count { it is ProcessResult.Failure && it.recoverable }
        val arch = list.count { it is ProcessResult.Failure && !it.recoverable }
        
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

        val modePayload = HMap<String, Any>()
        modePayload.put("taskCount", tasks.size)
        modePayload.put("stages", if (tasks.any { it.stages > 1 }) 2 else 1)
        val mode = selectMode(modePayload)

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

    override fun delegate(tasks: HList<Any>): HList<ProcessResult> {
        val list = tasks.toList()
        val results = mutableListOf<ProcessResult>()
        for (task in list) {
            val body = spawnBody("body-${bodies.size}")
            results.add(body.execute(task))
        }
        return merge(HList.from(results))
    }

    override fun spawnBody(bodyId: String): ProcessBody {
        val body = ProcessBodyImpl(
            id = id.copy(bodyId = bodyId),
            tag = tag
        )
        bodies.add(body)
        return body
    }

    override fun merge(results: HList<ProcessResult>): HList<ProcessResult> {
        val sorted = results.toList().sortedBy {
            when (it) {
                is ProcessResult.Success -> 0
                is ProcessResult.PartialSuccess -> 1
                is ProcessResult.Failure -> if (it.recoverable) 2 else 3
            }
        }
        return HList.from(sorted)
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
            val m = HMap<String, Any>(); m.put("elapsed_ms", 0); m.put("tag", tag)
            ProcessResult.Success(
                data = ProcessData(content = content, sourceBodyId = id.bodyId),
                metrics = m
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