package com.qitong.head.process

/**
 * v0.11.7 — 多项目并行调度 + 军队规模系统
 *
 * 挂在 ProcessCoordinator 外面当壳：
 *   收 N 个项目 → 各自扫 → 并行跑 → 汇总简报
 *
 * 规模系统：
 *   四档预设（低/中/高/极高）→ 决定从预设唤醒多少比例的兵
 *   可自定义创建/删除，最少保留一个。没有就是null→回退2x
 *
 * 退役池共享（本来就是static大厅），不改。
 */
object MultiProjectCoordinator {

    // ═══════════════════════════════════════════
    // 规模系统
    // ═══════════════════════════════════════════

    data class ArmyScale(
        val name: String,
        val multiplier: Float,  // 1x/2x/3x/5x ...
        val editable: Boolean = true  // 只有默认四档可删可改，手动创建的同理
    )

    /** 默认四档。改了dev才生效，不存dev就用这个 */
    private val DEFAULT_SCALES = listOf(
        ArmyScale("低", 1f),
        ArmyScale("中", 2f),
        ArmyScale("高", 3f),
        ArmyScale("极高", 5f)
    )

    /** 当前生效的规模列表。运行时从dev加载或fallback默认 */
    private var scales: MutableList<ArmyScale> = mutableListOf()
    private var activeScaleIndex: Int = 1  // 默认"中"

    /** 初始化：从dev读，读不到/为空/损坏 → 写回默认 */
    fun initScales(devReader: (String) -> ByteArray?, devWriter: (String, ByteArray) -> Unit) {
        val raw = devReader("army_scales") ?: run {
            saveScales(devWriter)
            return
        }
        try {
            val str = String(raw)
            // 简单格式：每行 name:multiplier ，active行加*前缀
            val lines = str.lines().filter { it.isNotBlank() }
            if (lines.isEmpty()) {
                scales.clear()
                scales.addAll(DEFAULT_SCALES)
                activeScaleIndex = 1
                saveScales(devWriter)
                return
            }
            val parsed = mutableListOf<ArmyScale>()
            var active = 0
            for ((i, line) in lines.withIndex()) {
                val content = if (line.startsWith("*")) { active = i; line.drop(1) } else line
                val parts = content.split(":")
                if (parts.size >= 2) {
                    val mult = parts[1].toFloatOrNull() ?: 2f
                    parsed.add(ArmyScale(parts[0], mult))
                }
            }
            if (parsed.isEmpty()) {
                scales.clear()
                scales.addAll(DEFAULT_SCALES)
                activeScaleIndex = 1
            } else {
                scales.clear()
                scales.addAll(parsed)
                activeScaleIndex = active.coerceIn(0, scales.lastIndex)
            }
        } catch (_: Exception) {
            scales.clear()
            scales.addAll(DEFAULT_SCALES)
            activeScaleIndex = 1
        }
        saveScales(devWriter)
    }

    private fun saveScales(writer: (String, ByteArray) -> Unit) {
        val sb = StringBuilder()
        for ((i, s) in scales.withIndex()) {
            if (i == activeScaleIndex) sb.append("*")
            sb.append("${s.name}:${s.multiplier}\n")
        }
        writer("army_scales", sb.toString().toByteArray())
    }

    /** 当前生效的倍率——永远不为null */
    val currentMultiplier: Float
        get() = scales.getOrNull(activeScaleIndex)?.multiplier ?: 2f

    fun getScales(): List<ArmyScale> = scales.toList()
    fun getActiveScaleIndex(): Int = activeScaleIndex
    fun getActiveScale(): ArmyScale = scales.getOrElse(activeScaleIndex) { ArmyScale("中", 2f) }

    /**
     * 切换当前规模。
     * 不影响正在跑的兵——只影响下轮唤醒数量。
     */
    fun setActiveScale(index: Int, writer: (String, ByteArray) -> Unit) {
        if (index < 0 || index >= scales.size) return
        activeScaleIndex = index
        saveScales(writer)
    }

    /** 规模倍率上限——超过这个没意义，cap已被coerceIn截断 */
    private const val MAX_MULTIPLIER = 10f

    /**
     * 创建新规模。最少1个，没有上限。
     */
    fun addScale(name: String, multiplier: Float, writer: (String, ByteArray) -> Unit): Boolean {
        if (name.isBlank() || multiplier <= 0f || multiplier > MAX_MULTIPLIER) return false
        scales.add(ArmyScale(name, multiplier))
        saveScales(writer)
        return true
    }

    /**
     * 删除指定规模。最少保留一个。
     */
    fun removeScale(index: Int, writer: (String, ByteArray) -> Unit): Boolean {
        if (scales.size <= 1) return false  // 不能删到零
        if (index < 0 || index >= scales.size) return false
        scales.removeAt(index)
        // activeIndex修正
        if (activeScaleIndex >= scales.size) activeScaleIndex = scales.lastIndex
        if (activeScaleIndex > index) activeScaleIndex -= 1
        saveScales(writer)
        return true
    }

    // ═══════════════════════════════════════════
    // 多项目调度
    // ═══════════════════════════════════════════

    data class ProjectTask(
        val name: String,
        val path: String,
        val fileSize: Int = 0,
        val fileCount: Int = 1
    )

    data class ProjectReport(
        val name: String,
        val path: String,
        val diagCount: Int,
        val durationMs: Long,
        val faults: List<String>,
        val scene: String,
        val severity: Int
    )

    /**
     * 并行跑 N 个项目，返回汇总。
     * 单线程模拟并行——实际多线程留给后续版本。
     */
    fun runBatch(
        projects: List<ProjectTask>,
        onProgress: (String) -> Unit = {}
    ): List<ProjectReport> {
        val reports = mutableListOf<ProjectReport>()

        for (proj in projects) {
            val start = System.currentTimeMillis()
            onProgress("军师扫 [$proj.name]...")

            // 每个项目独立：军师决策 + prepareArmy（多项目模式下唤醒更多兵）
            val isHostile = proj.path.contains("hell", true) || proj.path.contains("destroy", true)
            ProcessCoordinator.prepareArmy(
                proj.fileSize, proj.fileCount,
                isHostile = isHostile,
                multiProjectMode = true  // v0.11.7: 唤醒扩展槽位的兵
            )

            // 跑编译诊断（传路径，不传内容，让编译器自己读）
            onProgress("编译 [$proj.name]...")
            val result = runSingleProject(proj)

            val elapsed = System.currentTimeMillis() - start
            reports.add(ProjectReport(
                name = proj.name,
                path = proj.path,
                diagCount = result.first,
                durationMs = elapsed,
                faults = result.second,
                scene = if (isHostile) "地狱·多项目" else "正常·多项目",
                severity = result.second.size
            ))
        }

        return reports
    }

    /**
     * 跑单个项目，返回 (诊断数, 故障列表)
     */
    private fun runSingleProject(proj: ProjectTask): Pair<Int, List<String>> {
        // 实际调用 kotlin-head 编译管线
        // 对于模拟/测试：返回固定值
        val faults = mutableListOf<String>()
        try {
            val file = java.io.File(proj.path)
            if (!file.exists()) {
                faults.add("文件不存在: ${proj.path}")
                return Pair(0, faults)
            }
            val src = file.readText()

            // Le x
            val tokens = com.qitong.head.lexer.Lexer(src).tokenize()

            // Parse
            val parser = com.qitong.head.parser.Parser(tokens)
            val ktFile = try {
                parser.parseFile()
            } catch (e: Exception) {
                faults.add("Parse: ${e.message}")
                null
            }

            // TypeChecker
            val diags = if (ktFile != null) {
                val checker = com.qitong.head.checker.TypeChecker()
                checker.check(ktFile)
            } else emptyList()

            // BugScanner
            val findings = if (ktFile != null) {
                com.qitong.head.bugscan.BugScanner.from(ktFile)
            } else emptyList()

            // 收集故障
            for (d in diags) {
                if (d.level == com.qitong.head.checker.TypeChecker.DiagLevel.ERROR) {
                    faults.add("${d.msg} @ ${d.pos}")
                }
            }
            for (f in findings) {
                if (f.severity == com.qitong.head.bugscan.BugScanner.Severity.HIGH) {
                    faults.add("BugScan: ${f.message} @ ${f.span}")
                }
            }

            return Pair(diags.size + findings.size, faults)
        } catch (e: Exception) {
            faults.add("崩溃: ${e.message}")
            return Pair(0, faults)
        }
    }

    /** 生成汇总简报——N个项目拼一张表 */
    fun formatSummary(reports: List<ProjectReport>): String {
        if (reports.isEmpty()) return "（无项目）"
        val sb = StringBuilder()
        fun ap(s: String) { sb.append(s).append('\n') }
        ap("═══ 多项目汇总简报 ═══")
        ap("规模: ${getActiveScale().name} (${"%.0f".format(getActiveScale().multiplier)}x)")
        ap("")
        ap("项目          诊断  耗时    严重度  状态")
        ap("────────────  ────  ─────  ──────  ────")
        var totalDiags = 0
        var totalMs = 0L
        var maxSev = 0
        for (r in reports) {
            val status = when {
                r.faults.any { it.startsWith("崩溃") } -> "✖ 崩溃"
                r.faults.isNotEmpty() -> "⚠ ${r.faults.size}故障"
                else -> "✓ 通过"
            }
            val sevBar = when {
                r.severity >= 5 -> "🔴 ${r.severity}"
                r.severity >= 2 -> "🟡 ${r.severity}"
                else -> "⚪ ${r.severity}"
            }
            ap("${r.name.padEnd(14)} ${r.diagCount.toString().padEnd(5)} ${r.durationMs.toString().padEnd(6)}ms $sevBar".padEnd(34) + "  $status")
            totalDiags += r.diagCount
            totalMs += r.durationMs
            if (r.severity > maxSev) maxSev = r.severity
        }
        ap("────────────  ────  ─────  ──────  ────")
        ap("${"总计 ${reports.size}项目".padEnd(14)} ${totalDiags.toString().padEnd(5)} ${totalMs.toString().padEnd(6)}ms")
        ap("")
        if (maxSev >= 5) ap("⚠ 存在高危项目，建议单独排查")
        return sb.toString()
    }
}