package com.qitong.head.process

import com.qitong.head.headstd.HList
/**
 * 检测进程 —— v0.8.5
 *
 * 与子进程平级，旁路观察，不拦截结果。
 * 子进程走数据通道，检测进程走旁路——两条线互不阻塞。
 * 指挥官收到两份报告：子进程的执行结果 + 检测进程的异常汇总。
 */

// ─── 检测风格枚举 ───

enum class WatchStyle(val label: String, val description: String) {
    TIMID("胆小型", "高频检查，每个步骤都看，浅但全，宁可多报不遗漏"),
    BRAVE("勇敢型", "低频检查，专往高危区扎，深度检测不怕危险"),
    SAMPLER("抽样型", "随机抽检，低开销，覆盖面广"),
    TREND("趋势型", "定期采样看变化曲线，单次异常不理，连续异常才报"),
    SENTINEL("哨兵型", "只在关键节点检查，深且重，平时休眠，只守大门"),
    // ★ v0.11.2: 两种新检测性格
    PEDANTIC("专业", "语法洁癖——对语法敏感至极，错一点就吼：为什么要这样！"),
    LAZY("慵懒", "平常睡觉，紧急秒醒——过滤噪音，高优先级才动，速度极快"),
    // ★ v0.11.3
    STANDARD("标准", "中规中矩基线——每步都看，异常就报，不极端。默认性格")
}

// ─── 检测进程接口 ───

interface WatchProcess {
    val id: ProcessId
    val style: WatchStyle
    val targetSubProcessId: String  // 观察哪个子进程

    /** 收到一次观察信号——进程体执行了一步 */
    fun observe(step: ProcessStep): WatchReport?

    /** 子进程结束时汇总 */
    fun finalReport(): WatchReport

    /** 风格描述 */
    fun styleDescription(): String = "${style.label}: ${style.description}"
}

// ─── 观察步骤 ───

data class ProcessStep(
    val bodyId: String,
    val action: String,       // "lex_start" / "parse_done" / "type_mismatch" 等
    val durationNs: Long,
    val abnormal: Boolean = false,
    val metadata: Map<String, String> = emptyMap()
)

// ─── 检测报告 ───

data class WatchReport(
    val anomalies: List<String>,           // 异常列表
    val suspicionLevel: Float,            // 0.0 ~ 1.0，疑点指数
    val recommendation: String,           // 建议动作
    val details: Map<String, Any> = emptyMap()
) {
    companion object {
        val CLEAN = WatchReport(emptyList(), 0f, "一切正常")
    }
}

// ═══════════════════════════════════════
// ─── 八种检测进程实现 ───
// ═══════════════════════════════════════

/**
 * 胆小型检测进程 —— 高频检查每个步骤，浅但全
 */
class TimidWatch(
    override val id: ProcessId,
    override val targetSubProcessId: String
) : WatchProcess {
    override val style = WatchStyle.TIMID
    private val seen = HList<ProcessStep>()

    override fun observe(step: ProcessStep): WatchReport {
        seen.add(step)
        if (step.abnormal) {
            return WatchReport(
                anomalies = listOf("[胆小] ${step.action} 异常: body=${step.bodyId}"),
                suspicionLevel = 0.3f,
                recommendation = "注意观察后续步骤"
            )
        }
        if (step.durationNs > 1_000_000_000) { // >1秒
            return WatchReport(
                anomalies = listOf("[胆小] ${step.action} 耗时过长: ${step.durationNs / 1_000_000}ms"),
                suspicionLevel = 0.2f,
                recommendation = "建议检查该步骤是否有性能问题"
            )
        }
        return WatchReport.CLEAN
    }

    override fun finalReport(): WatchReport {
        val abnormalCount = seen.count { it.abnormal }
        val total = seen.size
        return WatchReport(
            anomalies = seen.filter { it.abnormal }.map { "[胆小] ${it.action}" }.toList(),
            suspicionLevel = if (total > 0) abnormalCount.toFloat() / total else 0f,
            recommendation = when {
                abnormalCount == 0 -> "全程无异常"
                abnormalCount < total * 0.3 -> "少量异常，建议复查"
                else -> "异常较多，建议暂停排查"
            },
            details = mapOf("totalSteps" to total, "abnormalSteps" to abnormalCount)
        )
    }
}

/**
 * 勇敢型检测进程 —— 专往高危区扎，低频但深
 */
class BraveWatch(
    override val id: ProcessId,
    override val targetSubProcessId: String
) : WatchProcess {
    override val style = WatchStyle.BRAVE
    private val highRiskActions = setOf("type_check", "codegen", "ir_lower", "link")
    private val findings = HList<String>()

    override fun observe(step: ProcessStep): WatchReport? {
        // 只盯高风险操作
        if (step.action !in highRiskActions) return null

        if (step.abnormal) {
            findings.add("[勇敢] 高危步骤 ${step.action} 异常: body=${step.bodyId}")
            return WatchReport(
                anomalies = listOf(findings.last()),
                suspicionLevel = 0.7f,
                recommendation = "高危区异常，建议立即检查"
            )
        }
        return null
    }

    override fun finalReport(): WatchReport {
        return WatchReport(
            anomalies = findings.toList(),
            suspicionLevel = if (findings.isEmpty()) 0f else 0.7f,
            recommendation = if (findings.isEmpty()) "高危区无异常"
                else "高危区发现 ${findings.size} 个异常",
            details = mapOf("highRiskFindings" to findings.size)
        )
    }
}

/**
 * 抽样型检测进程 —— 随机抽检，低开销
 */
class SamplerWatch(
    override val id: ProcessId,
    override val targetSubProcessId: String,
    private val sampleRate: Float = 0.1f // 10% 抽样率
) : WatchProcess {
    override val style = WatchStyle.SAMPLER
    private val sampled = HList<ProcessStep>()

    override fun observe(step: ProcessStep): WatchReport? {
        // 随机抽样
        if (Math.random() > sampleRate) return null
        sampled.add(step)
        if (step.abnormal) {
            return WatchReport(
                anomalies = listOf("[抽样] 抽中异常: ${step.action} body=${step.bodyId}"),
                suspicionLevel = 0.5f,
                recommendation = "抽样发现异常，建议扩大检查范围"
            )
        }
        return null
    }

    override fun finalReport(): WatchReport {
        val abnormalCount = sampled.count { it.abnormal }
        return WatchReport(
            anomalies = sampled.filter { it.abnormal }.map { "[抽样] ${it.action}" }.toList(),
            suspicionLevel = if (abnormalCount > 0) 0.5f else 0f,
            recommendation = when {
                abnormalCount == 0 -> "抽样通过"
                else -> "抽样发现 $abnormalCount 个异常，建议全量检查"
            },
            details = mapOf("sampledCount" to sampled.size, "sampleRate" to sampleRate)
        )
    }
}

/**
 * 趋势型检测进程 —— 看变化曲线，连续异常才报
 */
class TrendWatch(
    override val id: ProcessId,
    override val targetSubProcessId: String,
    private val threshold: Int = 3  // 连续N次异常才报
) : WatchProcess {
    override val style = WatchStyle.TREND
    private val history = HList<ProcessStep>()
    private var consecutiveAbnormal = 0

    override fun observe(step: ProcessStep): WatchReport? {
        history.add(step)
        if (step.abnormal) {
            consecutiveAbnormal++
        } else {
            consecutiveAbnormal = 0
        }

        if (consecutiveAbnormal >= threshold) {
            return WatchReport(
                anomalies = listOf("[趋势] 连续 $consecutiveAbnormal 次异常，触发警报"),
                suspicionLevel = 0.6f + (consecutiveAbnormal * 0.1f).coerceAtMost(0.4f),
                recommendation = "趋势恶化，建议立即介入"
            )
        }
        return null
    }

    override fun finalReport(): WatchReport {
        val total = history.size
        val abnormalTotal = history.count { it.abnormal }
        val trend = if (total >= 2) {
            // 后半段 vs 前半段的异常率对比
            val mid = total / 2
            val firstHalf = history.take(mid).count { it.abnormal }.toFloat() / mid
            val secondHalf = history.drop(mid).count { it.abnormal }.toFloat() / (total - mid)
            if (secondHalf > firstHalf) "恶化" else if (secondHalf < firstHalf) "改善" else "持平"
        } else "数据不足"

        return WatchReport(
            anomalies = if (consecutiveAbnormal >= threshold)
                listOf("[趋势] 结束时仍连续 $consecutiveAbnormal 次异常") else emptyList(),
            suspicionLevel = if (total > 0) (abnormalTotal.toFloat() / total).coerceAtMost(1f) else 0f,
            recommendation = "趋势: $trend，总计异常 $abnormalTotal/$total",
            details = mapOf("trend" to trend, "totalAbnormal" to abnormalTotal)
        )
    }
}

/**
 * 哨兵型检测进程 —— 只在关键节点检查，平时休眠
 */
class SentinelWatch(
    override val id: ProcessId,
    override val targetSubProcessId: String
) : WatchProcess {
    override val style = WatchStyle.SENTINEL
    private val gateActions = setOf("compile_start", "compile_done", "dispatch_done", "ir_lower", "link")
    private val gateReports = HList<WatchReport>()
    private var recoverAttempts = 0
    private var recoverSuccesses = 0
    private var recoverBudget = 500

    fun tryRecover(label: String, fn: () -> Any?): Any? {
        if (recoverBudget <= 0) return null
        recoverBudget--
        recoverAttempts++
        return try { val r = fn(); if (r != null) recoverSuccesses++; r } catch (_: Exception) { null }
    }
    fun recoverStats() = Triple(recoverAttempts, recoverSuccesses, recoverBudget)

    override fun observe(step: ProcessStep): WatchReport? {
        // 只在守门节点醒来
        if (step.action !in gateActions) return null

        val report = WatchReport(
            anomalies = if (step.abnormal) listOf("[哨兵] 守门节点 ${step.action} 异常") else emptyList(),
            suspicionLevel = if (step.abnormal) 0.9f else 0f,
            recommendation = if (step.abnormal) "守门节点异常——这是大事" else "守门节点通行",
            details = mapOf("gate" to step.action, "durationMs" to step.durationNs / 1_000_000)
        )
        if (step.abnormal) gateReports.add(report)
        return report
    }

    override fun finalReport(): WatchReport {
        return WatchReport(
            anomalies = gateReports.flatMap { HList.from(it.anomalies) }.toList(),
            suspicionLevel = if (gateReports.isEmpty()) 0f else 0.9f,
            recommendation = if (gateReports.isEmpty()) "所有守门节点通过"
                else "${gateReports.size} 个守门节点报警",
            details = mapOf("gatesChecked" to gateActions.size, "gatesFailed" to gateReports.size)
        )
    }
}

// ★ v0.11.2: 专业型检测进程——语法洁癖，极低容忍度
class PerfectionistWatch(
    override val id: ProcessId,
    override val targetSubProcessId: String
) : WatchProcess {
    override val style = WatchStyle.PEDANTIC
    private val strictActions = setOf("parse_done", "type_check", "type_mismatch", "ir_lower")
    private val rants = HList<String>()

    override fun observe(step: ProcessStep): WatchReport? {
        // 只盯语法相关步骤
        if (step.action !in strictActions) return null
        
        if (step.abnormal) {
            val rant = "[专业型] 为什么要这样！${step.action} 异常: body=${step.bodyId}"
            rants.add(rant)
            return WatchReport(
                anomalies = listOf(rant),
                suspicionLevel = 0.85f,
                recommendation = "语法不纯，必须修正——这不是建议，是命令"
            )
        }
        // 连正常步骤也要吹毛求疵——检查 duration
        if (step.durationNs > 500_000_000) { // >500ms
            val rant = "[专业型] ${step.action} 太慢了(${step.durationNs/1_000_000}ms)——为什么要这么慢！"
            rants.add(rant)
            return WatchReport(
                anomalies = listOf(rant),
                suspicionLevel = 0.4f,
                recommendation = "性能不达标，建议优化"
            )
        }
        return null
    }

    override fun finalReport(): WatchReport {
        return WatchReport(
            anomalies = rants.toList(),
            suspicionLevel = if (rants.isEmpty()) 0f else 0.85f,
            recommendation = if (rants.isEmpty()) "语法纯度通过——难得"
                else "语法纯度不及格：${rants.size} 处瑕疵",
            details = mapOf("rants" to rants.size)
        )
    }
}

// ★ v0.11.2: 慵懒型检测进程——平常睡觉，紧急秒醒
class LazyWatch(
    override val id: ProcessId,
    override val targetSubProcessId: String,
    private val lazyThreshold: Long = 3  // 连续多少次小异常才叫"紧急"（慵懒阈值高）
) : WatchProcess {
    override val style = WatchStyle.LAZY
    private var awake = false
    private var emergencyCount = 0
    private val emergencyLog = HList<String>()

    override fun observe(step: ProcessStep): WatchReport? {
        // 平常懒得动——小问题直接忽略
        if (!awake && !step.abnormal) return null
        
        if (step.abnormal) {
            emergencyCount++
            if (emergencyCount >= lazyThreshold || step.action in setOf("compile_start", "compile_crashed", "parse_crashed", "link")) {
                // 紧急！秒醒
                awake = true
                val msg = "[慵懒] 🔥秒醒！紧急: ${step.action} 异常，连续 ${emergencyCount} 次"
                emergencyLog.add(msg)
                return WatchReport(
                    anomalies = listOf(msg),
                    suspicionLevel = 0.9f,
                    recommendation = "紧急介入——平时不动，动了就是大事"
                )
            }
        }
        
        if (awake && step.abnormal) {
            val msg = "[慵懒] 醒着处理: ${step.action} body=${step.bodyId}"
            emergencyLog.add(msg)
            return WatchReport(
                anomalies = listOf(msg),
                suspicionLevel = 0.7f,
                recommendation = "正在紧急处理中"
            )
        }
        return null
    }

    override fun finalReport(): WatchReport {
        return WatchReport(
            anomalies = emergencyLog.toList(),
            suspicionLevel = if (awake) 0.8f else 0f,
            recommendation = if (awake) "慵懒被唤醒 ${emergencyCount} 次——不是小事"
                else "全程没被吵醒，一切正常",
            details = mapOf("awake" to awake, "emergencies" to emergencyCount)
        )
    }
}

// ★ v0.11.3: 标准型检测进程——中规中矩基线
class StandardWatch(
    override val id: ProcessId,
    override val targetSubProcessId: String
) : WatchProcess {
    override val style = WatchStyle.STANDARD
    private val seen = HList<ProcessStep>()

    override fun observe(step: ProcessStep): WatchReport {
        seen.add(step)
        if (step.abnormal) {
            return WatchReport(
                anomalies = listOf("[标准] ${step.action} 异常: body=${step.bodyId}"),
                suspicionLevel = 0.5f,
                recommendation = "发现异常，按流程处理"
            )
        }
        if (step.durationNs > 2_000_000_000) { // >2秒才提醒
            return WatchReport(
                anomalies = listOf("[标准] ${step.action} 耗时偏长: ${step.durationNs / 1_000_000}ms"),
                suspicionLevel = 0.2f,
                recommendation = "注意性能趋势"
            )
        }
        return WatchReport.CLEAN
    }

    override fun finalReport(): WatchReport {
        val abnormalCount = seen.count { it.abnormal }
        val total = seen.size
        return WatchReport(
            anomalies = seen.filter { it.abnormal }.map { "[标准] ${it.action}" }.toList(),
            suspicionLevel = if (total > 0) (abnormalCount.toFloat() / total * 0.7f).coerceAtMost(1f) else 0f,
            recommendation = when {
                abnormalCount == 0 -> "流程正常"
                abnormalCount < total * 0.3 -> "少量异常，正常流程可处理"
                else -> "异常较多，建议排查"
            },
            details = mapOf("totalSteps" to total, "abnormalSteps" to abnormalCount)
        )
    }
}

// ─── 检测进程工厂 ───

object WatchProcessFactory {
    fun create(
        style: WatchStyle,
        id: ProcessId,
        targetSubProcessId: String,
        config: Map<String, Any> = emptyMap()
    ): WatchProcess = when (style) {
        WatchStyle.TIMID -> TimidWatch(id, targetSubProcessId)
        WatchStyle.BRAVE -> BraveWatch(id, targetSubProcessId)
        WatchStyle.SAMPLER -> SamplerWatch(
            id, targetSubProcessId,
            sampleRate = (config["sampleRate"] as? Float) ?: 0.1f
        )
        WatchStyle.TREND -> TrendWatch(
            id, targetSubProcessId,
            threshold = (config["threshold"] as? Int) ?: 3
        )
        WatchStyle.SENTINEL -> SentinelWatch(id, targetSubProcessId)
        WatchStyle.PEDANTIC -> PerfectionistWatch(id, targetSubProcessId)
        WatchStyle.LAZY -> LazyWatch(
            id, targetSubProcessId,
            lazyThreshold = (config["lazyThreshold"] as? Long) ?: 3
        )
        WatchStyle.STANDARD -> StandardWatch(id, targetSubProcessId)
    }
}