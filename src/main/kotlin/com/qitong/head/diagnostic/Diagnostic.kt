package com.qitong.head.diagnostic

import com.qitong.head.ast.Pos
import com.qitong.head.bugscan.BugScanner
import com.qitong.head.checker.TypeChecker

class Diagnostic(
    private val diags: List<TypeChecker.Diag>,
    private val bugFindings: List<BugScanner.Finding> = emptyList()
) {
    data class Grouped(
        val errors: List<TypeChecker.Diag>,
        val warnings: List<TypeChecker.Diag>,
        val skipped: List<TypeChecker.Diag>,
        val expected: List<TypeChecker.Diag>  // 🔮
    ) {
        val hasErrors get() = errors.isNotEmpty()
        val hasWarnings get() = warnings.isNotEmpty()
        val hasSkipped get() = skipped.isNotEmpty()
        val hasExpected get() = expected.isNotEmpty()
        val hasAny get() = hasErrors || hasWarnings || hasSkipped || hasExpected
    }

    fun grouped(): Grouped {
        val errors = diags.filter { it.level == TypeChecker.DiagLevel.ERROR }
        val warnings = diags.filter { it.level == TypeChecker.DiagLevel.WARN }
        val skipped = diags.filter { it.level == TypeChecker.DiagLevel.SKIPPED }
        val expected = diags.filter { it.level == TypeChecker.DiagLevel.EXPECTED }
        return Grouped(errors, warnings, skipped, expected)
    }

    fun format(declCount: Int = 0): String {
        val g = grouped()
        if (!g.hasAny && bugFindings.isEmpty()) return "✓ 没有问题\n"
        val sb = StringBuilder()
        sb.append("═══ 诊断 ═══\n")
        // 声明统计
        if (declCount > 0) sb.append("✅ $declCount 个声明确认\n")
        // 错误
        if (g.hasErrors) {
            for ((i, d) in g.errors.withIndex())
                sb.append("🛑 E${"%02d".format(i + 1)} ${d.msg} | ${d.pos}\n")
        }
        // 警告：不认识但跳过
        if (g.hasWarnings) {
            sb.append("⚠️ ${g.warnings.size} 处跳过（未知语法）\n")
            for ((i, d) in g.warnings.withIndex())
                sb.append("  → ${d.msg} | ${d.pos}\n")
        }
        // 预期：版本路线
        if (g.hasExpected) {
            sb.append("🔮 ${g.expected.size} 处待版本解锁\n")
            for ((i, d) in g.expected.withIndex())
                sb.append("  → ${d.msg} | ${d.pos}\n")
        }
        // Bug扫描
        if (bugFindings.isNotEmpty()) {
            sb.append("🐛 BugScanner: ${bugFindings.size} 处结构隐患\n")
            for ((i, f) in bugFindings.withIndex()) {
                val icon = when (f.severity) {
                    BugScanner.Severity.HIGH -> "🔴"
                    BugScanner.Severity.MEDIUM -> "🟠"
                    BugScanner.Severity.LOW -> "🟡"
                }
                sb.append("  $icon ${f.message}\n")
            }
        }
        return sb.toString()
    }

    companion object {
        fun from(diags: List<TypeChecker.Diag>, findings: List<BugScanner.Finding> = emptyList()) =
            Diagnostic(diags, findings)
    }
}