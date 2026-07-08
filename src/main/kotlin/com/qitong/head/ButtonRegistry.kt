package com.qitong.head

/**
 * v0.12.6-a: HED 按钮注册表 — 子进程路由版。
 * 
 * 核心原则：
 *   - 按钮 = 子进程认领。没有子进程认领的按钮不入列表。
 *   - 三层数据源：系统(不可变) / sim-ui(单次编译) / 自定义(持久化)
 *   - 懒汉型精确匹配 + 探测型 fallback 探索
 *   - 编号归渲染层，不做快照（单线程 readLine 阻塞零时差）
 */

object ButtonRegistry {

    // ── 按钮指令枚举 ──
    enum class Command(val label: String) {
        AST_VIEW("查看 AST 树"),
        DIAG_VIEW("查看诊断"),
        COMPILE("重新编译"),
        SIM("模拟运行"),
        ADMIN("管理员"),
        BUGS("Bug 扫描"),
        ROADMAP("能力路线图"),
        DECOMP("反编译管线"),
        PROCESS("进程树"),
        EVENTBUS("EventBus 状态"),
        MULTIPROJ("多项目测试"),
        PACK("APK打包"),
        SIMUI("源码UI模拟"),
        TEMPLATE("切换模板"),
        BUTTONS("按钮管理");
    }

    // ── 按钮定义（无 key 字段——编号归渲染层）──
    data class Button(
        val id: String,
        val label: String,
        val command: Command? = null,        // null = 自定义按钮，走 customAction
        val customAction: (() -> Unit)? = null,
        val visible: () -> Boolean = { true },
        val templates: Set<String> = setOf("通用"),
        val source: String = "系统",
        val deletable: Boolean = false
    )

    // ── 三层数据源 ──
    private val systemButtons = mutableListOf<Button>()
    private val simUiButtons = mutableListOf<Button>()
    private val customButtons = mutableListOf<Button>()

    var currentTemplate = "通用"

    // ── 模板定义 ──
    val TEMPLATES = mapOf(
        "通用" to listOf("ast","diag","compile","sim","admin","bugs","roadmap","decomp","process","multiproj","pack","simui"),
        "源码调试" to listOf("ast","diag","bugs","process","simui"),
        "綦桐生态" to listOf("simui","multiproj","pack","decomp")
    )

    // ── 初始化系统按钮 ──
    fun initDefaults() {
        if (systemButtons.isNotEmpty()) return
        systemButtons.addAll(listOf(
            Button("ast", "查看 AST 树", Command.AST_VIEW, templates = allTemplates()),
            Button("diag", "查看诊断", Command.DIAG_VIEW, templates = allTemplates()),
            Button("compile", "重新编译", Command.COMPILE, templates = allTemplates()),
            Button("sim", "模拟运行", Command.SIM, templates = allTemplates()),
            Button("admin", "管理员", Command.ADMIN, templates = allTemplates()),
            Button("bugs", "Bug 扫描", Command.BUGS, templates = allTemplates()),
            Button("roadmap", "能力路线图", Command.ROADMAP, templates = allTemplates()),
            Button("decomp", "反编译管线", Command.DECOMP, templates = allTemplates()),
            Button("process", "进程树", Command.PROCESS, templates = allTemplates()),
            Button("eventbus", "EventBus 状态", Command.EVENTBUS, templates = allTemplates(), visible = { true }),
            Button("multiproj", "多项目测试", Command.MULTIPROJ, templates = allTemplates()),
            Button("pack", "APK打包", Command.PACK, templates = allTemplates()),
            Button("simui", "源码UI模拟", Command.SIMUI, templates = allTemplates()),
        ))
    }

    private fun allTemplates() = setOf("通用", "源码调试", "綦桐生态")

    // ── 聚合层：三层合并 → 过滤模板 → 返回（不编号）──
    fun visibleButtons(): List<Button> {
        val all = systemButtons + simUiButtons + customButtons
        return all.filter { it.templates.contains(currentTemplate) && it.visible() }
    }

    // ── sim-ui 注入 ──
    fun clearSimUiInjections() { simUiButtons.clear() }
    fun injectFromSimUi(buttons: List<Button>) {
        // 校验：每个按钮必须有 command 或 customAction
        simUiButtons.addAll(buttons.filter { it.command != null || it.customAction != null })
    }

    // ── 自定义按钮 ──
    fun addCustom(label: String, action: () -> Unit, templates: Set<String> = setOf("通用")) {
        if (label.isBlank()) return
        customButtons.add(Button("cust_${System.currentTimeMillis()}", label, customAction = action, templates = templates, source = "自定义", deletable = true))
    }
    fun removeCustom(id: String) { customButtons.removeAll { it.id == id && it.deletable } }

    // ── 模板切换 ──
    fun setTemplate(name: String) { if (TEMPLATES.containsKey(name)) currentTemplate = name }

    // ── 懒汉型：精确匹配（系统按钮用）──
    fun findByCommand(cmd: Command): Button? = systemButtons.find { it.command == cmd }

    // ── 探测型：模糊搜索（sim-ui/自定义 + fallback）──
    fun searchByLabel(input: String): Button? {
        // 先精确匹配
        visibleButtons().find { it.label.equals(input, ignoreCase = true) }?.let { return it }
        // 再模糊匹配
        return visibleButtons().find { it.label.contains(input, ignoreCase = true) }
    }

    // ── 持久化（占位）──
    fun save() { /* TODO: 存到 ~/.kotlin-head/button_custom.json */ }
    fun load() { /* TODO: 从文件加载 */ }
}
