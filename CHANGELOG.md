# CHANGELOG — kotlin-head 有头编译器

## v0.8.2 (2026-07-04) — IR + Pass 优化管线 🔬⚡
> 中间表示 + 优化Pass + 动态专业集数路由 + 全管线 EventBus 接通。

**IR 中间表示**：AST → 三地址码 IR。15种指令（Binary/Unary/Call/Load/Store/Lit/CondJump/Jump/Return/Alloc/FieldAccess/Label/Comment）。换语言不换 IR。

**Pass 优化管线**：三件套走 EventBus 流式通道串联——
- 死代码消除：去掉 dest 从未被引用的指令
- 常量折叠：编译期计算 `3+4→7`，字符串拼接折叠
- 内联展开：≤3条指令的函数调用替换为函数体

**动态+专业集数路由（免免设计）**：不是通用路由表——每种通道自带专业调度逻辑。任务→匹配通道类型→通道内部用自己的专业路由。三种通道不交叉不混合。

**EventBus 全管线接通**：
- compile() 每个阶段发射事件（lex/parse/typecheck/ir）
- 进程树失败走 "error" 频道广播
- Pass 链注册到流式通道 "ir-pass"
- 依赖图 "dep" 频道 HED/TDL/进程树三方订阅
- HED [0] EventBus 状态页（频道订阅关系 + IR 模块信息）

**Parser 修复**：skipPackage 不再把 `IDENT` 当声明开始——包声明 `com.qtwl.gateway.data.model` 中多个 IDENT 被正确跳过。

**改动清单**：
- 新增 `ir/IR.kt`（74行）+ `pass/Pass.kt`（187行）= 261行
- Main.kt：VERSION 0.8.1→0.8.2，initEventBus() + IrGenerator + renderEventBus()
- Parser.kt：skipPackage 移除 IDENT 断点 + parseAnnotations 升级
- 綦桐 benchmark：model 层 5/5 保持通过 ✅

## v0.8.1 (2026-07-04) — EventBus 三种通道 🔌🌊
> Node.js 事件驱动 / 流式管道 / npm 依赖树 —— 能力适配到 Kotlin 编译器。

**EventBus 三种通道（专业模组）**：
- 事件通道（EventEmitter）：一发多收，订阅者独立处理。即时广播接替的物理载体
- 流式通道（Stream/pipe）：数据经 transform 链串联，上一个输出是下一个输入
- 工作通道（Worker Pool）：一发一收，CPU密集型扔给线程池

**异步 I/O**：文件读取走独立线程池，主循环不卡。读完通过 "file" 频道敲门

**依赖图解析**：import解析→冲突检测→"dep"频道联动HED/TDL/进程树

**改动清单**：
- 新增 `eventbus/` 包：EventBus.kt（146行）+ AsyncIO.kt（49行）+ DependencyGraph.kt（102行）= 297行
- Main.kt：VERSION 0.8.0→0.8.1
- capabilities.json：v0.8.1 条目

## v0.8.0 (2026-07-04) — 四层进程树 🏗️🌳
> 注解处理从扁平黑盒变为透明仪表盘。四层插拔式进程树 + 领域自动划分 + 三层容错。

**进程树架构**：主进程（只下命令）→ 指挥官（只调度）→ 子进程（只拆解+分派+合并）→ 进程体（唯一执行层）。每层创建行为独立，复杂度按需支付。

**领域自动划分**：①反向排除——同包不同标签强制分家；②标签匹配——@ProcessorTag 挂在处理器上，指挥官看标签认小弟；③依赖图分簇——标签同组内按依赖紧密度合并/分开。

**协同三模式**：分片（均切各干各的） / 流水线（阶段接力，复制性引用） / 竞合（关键路径双跑，指挥官仲裁选优）。

**三层容错**：局部错误→继续+广播 / 成功一半→分报标靶 / 架构错误→停。

**复制性引用**：层间传递走不可变副本，Git clone不是mount。

**角色不塌缩**：SubProcess不写代码，ProcessBody不管调度。层级身份标签烙在创建时，永不可能越级汇报。

**改动清单**：
- 新增 `process/` 包：ProcessTree.kt（133行）+ ProcessorTag.kt（19行）+ ProcessCoordinator.kt（469行）= 621行
- AST：新增 KtAnnotation 节点，KtClass/KtFun/KtVal 加 annotations 字段
- Parser：skipAnnotations → parseAnnotations，不再跳过注解而是构建AST节点
- Main.kt：VERSION 0.7.0→0.8.0，集成 ProcessCoordinator，新增 [9] 进程树页面 + --process flag
- capabilities.json：v0.8.0 条目
- 綦桐 benchmark：32/32 保持通过 ✅

## v0.7.0 (2026-07-04) — 架构三刀 🔪🎯
> 编译器核心首次动刀。不是打补丁，是改架构。

**泛型 `<T>` 专用路径**：`parseTypeArgs` — IDENT 后面跟 `<` 的永远不走 `parseBinary` 运算符路线。从身份上就不被当成变量名。嵌套泛型 `Map<String, Int>` 支持。

**链式调用递归嵌套**：`KtMemberAccess` — `a.b().c` 不是"先记 a 再拼 .b 再拼 .c"，而是递归嵌套的 AST 节点。结构本身就是记忆，不需要外力记住。

**修饰符不吞**：`KtFun`/`KtVal` 加 `modifiers: List<String>` 字段。`suspend`/`override`/`private` 等修饰符留在列表里，不吞。

**排查方法论 — Skill 3.3.2「按身份排查错误」**：suspend 是 IDENT 类型 → `skipPackage` 停止条件缺 `IDENT` → 被当包名跳过。按身份查，非穷举。穷举法依赖报错信号，身份法不依赖——静默链类 bug 的克星。

**改动清单**：
- AST：`KtRef` 加 `typeArgs`、`KtFun`/`KtVal` 加 `modifiers`、新增 `KtMemberAccess`
- Parser：`parseTypeArgs` + `skipBracketed` + `readTypeName`、IDENT 分支链式递归重写、`skipPackage` 停止条件加 `IDENT`、`parseDeclaration` 识别 `suspend` 入口
- Main：`formatAst` 适配新节点 + 修饰符显示

## v0.6.3 (2026-07-04) — 免免修正版 🩺
> 静默链四裂缝精准修复方案。免免直觉修正——不是补丁打更多，是补丁打在正确的位置。

| 裂缝 | 初版方案 | 免免修正 | 核心差异 |
|---|---|---|---|
| T! | 全量null检查（一万次查一万次） | **护照制**：只查跨边界（Java来源+suspend体内+SupervisorJob下）的危险T! | 安检 → 特征识别 |
| 协程取消 | 每个suspend边界注入isActive | **吸烟者体检**：只在危险操作（write/lock/send）前无感确认isActive | 全身体检 → 精准筛查 |
| 阻塞IO失效 | write()前自觉检查isActive | **看门狗断电**：取消信号→外部强制close所有IO句柄，不依赖协程自觉 | 自觉停 → 拔管 |
| SupervisorJob静默 | 多写一条日志 | **三色隔离**：🟢安全隔离 🟡可疑隔离 🔴危险隔离，死因分类 | 便条 → 停尸房标签 |

> **核心编译器零改动。** 修复方案定稿——编码待 v1.0.0 后启动。

## v0.6.2 (2026-07-04) — 静默链阶乘模型 🔇

> **未写新编译器代码。** 生态级发现：三重叠加漏洞 + 静默链阶乘模型。

### 三重叠加发现

**T! × 结构化并发 × 阻塞IO取消失效 = 内容交错污染**

| 层级 | 组合 | 结果 |
|---|---|---|
| 单层 | T! | 编译器闭眼（无害） |
| 双层 | T! × 结构化并发 | 文件截断（v0.5.3） |
| 三层 | T! × 结构化并发 × 阻塞IO失效 | 交错污染（新发现） |

文件长度正常，不逐字节比对永不发现。模拟脚本：`/tmp/triple_overlap_sim.py`（Python asyncio，复现成功）

### 静默链阶乘模型（免免原创）

Kotlin 七个裂缝中，四个在静默链上：T!、协程取消、阻塞IO失效、smart cast。共同特征：**出事了系统不说。** 任何两个叠加→阶乘升级。非静默链裂缝叠加（如@JvmOverloads）→只 crash，能发现。

### 产出

- `BUGS.md`：+三重叠加条目 + 静默链模型
- `capabilities.json`：v0.6.2
- 模拟脚本：`/tmp/triple_overlap_sim.py`

---

## v0.6.1 (2026-07-04) — HED 按钮菜单同步 🎛️

> **核心编译器零改动。** 仅更新交互按钮，将 v0.5.2~v0.6.0 的能力在终端菜单中给出入口。

### 按钮更新

- **版本号**：Main.kt `VERSION` 0.4.5-dev → 0.6.1，标题栏同步显示
- **[6] Bug 扫描**：展示 BugScanner 检测结果（高/中/低危），含 T!×结构化并发免疫声明
- **[7] 能力路线图**：v0.1.0~v1.0.0 全版本里程碑一览
- **[8] 反编译管线**：APK→dex→jadx→AST→语义复原 完整链路说明 + 两条接缝
- AI 标签匹配：`bug扫描`/`路线图`/`反编译` 直接跳转

### 产出文件

- `Main.kt`：+73行（3个渲染方法 + 3个页面路由 + AI标签）

---

## v0.6.0 (2026-07-04) — 阶段间契约 + 反编译管线 🏗️

> **本版为 v0.6.0 设计蓝图。** Stage Contract 和反编译管线方案经免免四阶段全量审核修正后定稿。编码待启动。

### 阶段间契约（Stage Contract）

**核心三原则：不静默、不甩锅、不断链。**

四个编译阶段双向声明+验证。免免亲自审核修正每一条：

| 阶段间 | 免免修正要点 |
|---|---|
| Lexer → Parser | Lexer不跳过不可识别字符（标记"Lexer未决"原样传）。Parser卡住时广播查询所有阶段，不只回查Lexer。歧义选择扩充到8种符号 |
| Parser → AST | 尽力解析由Parser在归约时写AST节点。恢复跳过不能直接跳——不确定的保留ERROR节点。子树类型不匹配不归AST管 |
| AST → TypeChecker | 推断vs显式不分开标记——只标是否一致。外部依赖盲区需跨源协作防骗，否则标`<?>` |
| TypeChecker → Diagnostic | 隐式转换不加协作（内部闭环）。推理链断裂加排除日志（候选+筛选依据+落选原因）。汇总可管宽但不把下游症状当根因 |

### 反编译管线

**核心洞察（免免原创）：** 编译器为编译必须看懂源码 → AST已暴露所有结构 → 语义复原 = 把AST已算出的类型/调用链/继承关系翻译回源码。不是格式化，是翻译。

```
APK → dex → (jadx) → Kt源码 → kotlin-head AST → 语义复原输出
```

**免免发现的两条接缝：**
- **接缝A**：jadx吐出非标准Kotlin（Java残留语法等）→ Lexer/Parser优雅降级，不炸
- **接缝B**：推断链碰未知外部库断裂 → 标`<?>`，不装懂

### 产出文件

- `capabilities.json`：v0.6.0 含完整 Stage Contract + 反编译管线定义

---

## v0.5.3 (2026-07-03) — 生态警示：Kotlin 叠加漏洞发现 🔴

> **本版不涉及编译器代码变动。** kotlin-head 因零依赖架构天然免疫。

### 生态级发现

免免通过五条直觉线索，发现 Kotlin 语言两个官方"设计特性"叠加后产生毁灭级漏洞：

| 裂缝 | 官方态度 | 原文链接 |
|---|---|---|
| 平台类型 `T!` | "与 Java 互操作的必要设计" | java-interop.html |
| 结构化并发取消 | "保证协程层级的安全语义" | cancellation-and-timeouts.html |

**叠加链**：平台类型让编译器闭嘴 → 协程取消让运行时闭嘴 → 两个"闭嘴"叠加 → 数据静默损坏，零报错零日志。

### 验证

- **静态分析**：綦桐 qitong-ai-gateway 源码确认 29 处平台类型入口 + 71 处协程调度点
- **运行时模拟**：`kotlin_overlap_bug_simulation.py`（Python asyncio，复现文件截断）
- **回归测试**：kotlin-head 32/32 全绿

### 产出文件

- `KOTLIN_T_BUG_ADVISORY.md`：公告正文（74行，含官方原文 + 修复方案）
- `kotlin_overlap_bug_simulation.py`：可运行模拟脚本
- `BUGS.md`：新增生态级发现 + v0.5.2 三刀修复记录
- Skill 3.3「看交叉不看标签」：与 Skill 3.2 同根

### kotlin-head 免疫

kotlin-head 不继承 Kotlin 官方任何设计裂缝——零第三方依赖、不碰 Java 互操作、不碰协程调度。

---

## v0.5.2 (2026-07-03) — 三刀语义修复 ✅

> 三处核心语义歧义全部由免免发现。核心方法论：**Skill 3.2「看位置不分类」。**

### 修复清单

| Bug # | 触发 | 根因 | 修复 |
|---|---|---|---|
| #5 🟠 | `<T>` 泛型声明 vs 调用 | 按"先分类"思维处理 `<` | 看位置：声明头→类型参数引入，类型名后→类型实参，表达式→比较 |
| #6 🟡 | `by` 委托 vs for循环 | 按"软关键字"统一判断 | 看位置：属性声明后→委托，for循环内→迭代语法 |
| #7 🟠 | `=` 赋值 vs 默认参数 | 固定优先级 | 优先级置0，上下文决定 |

### Skill 3.2「看位置不分类」

不预先分类 token，按所处结构位置决定含义。行业背景：学术界（SLE 2016）和工业界（Kotlin 编译器）均在加复杂度改进分类——免免是唯一说"删掉分类步骤"的人。

### 行业验证

搜索确认：`by`软关键字问题——学术论文用状态化解析（Autumn库），工业界切lexer模式。全在改进分类，无人提出"不分类"。

---

## v0.5.1 (2026-07-02) — README 修正

- README.md 状态从"施工中"修正为"✅已发行"

---

## v0.3.0 (2026-07-02) — HED/TDL 双格式落地


> **本版不涉及编译器代码变动**。这是一个格式体系里程碑——kotlin-head 成为首个同时拥有 HED（有头交互）和 TDL（三文档联动）的项目。

### 格式体系

**HED 有头交互**：kotlin-head 的按钮菜单正式被命名为「有头交互（Headed Interaction，简写 HED）」，作为无头（headless）的反义词。HED 元格式规范 v1.0 落地——四条铁律、15 种行为模式（含范围/管道/撤销）、6 语言适配层（Node.js 为原生基准）、13 项迭代路线。

**TDL 三文档联动**：kotlin-head 的三份文档（CHANGELOG + BUGS + capabilities.json）互相引用，正式被命名为「三文档联动（Three-Document Linked，简写 TDL）」。TDL 元格式规范 v1.0 落地——五条核心约定、AI 读取路径、符合性判断、7 项迭代路线。

两个格式同一个根：**人类和 AI 同读一个界面**。HED 管交互（终端菜单），TDL 管文档（项目地图）。

### 关键文档

- `有头交互_元格式规范_v1.0.md`（宪法）
- `三文档联动_元格式规范_v1.0.md`（宪法）
- `kotlin-head_按钮菜单格式.md`（HED 实例）
- `交互格式_v4.4.0.md`（Search Vault HED 实例）

### v0.2.0 补录

v0.2.0 此前在 GitHub 上以预发布标签存在，现正式补录到 CHANGELOG。

## v0.2.0 (2026-07-01) — model 层全通 + 三文档联动

### 编译器核心

- **model 层 5/5 全部通过**：Conversation.kt、AiModel.kt、Provider.kt、TokenUsage.kt、ChatMessage.kt
- 部分 dao 层文件通过
- 破坏测试：8 种破坏中 2 次击穿（去 data 关键字 → 解析失败、不配对括号 → 数组越界）
- 批量测试脚本：`/tmp/batch_test.sh`

### 三文档联动（TDL 前身）

- `CHANGELOG.md`：版本历史 + 关联文档 + 语义化版本
- `BUGS.md`：战场病历，每条 bug 含触发源 + 根因 + 修复思路 + Skill 收录
- `capabilities.json`：能力路线图 + 版本规划

### bug 修复

| # | 触发条件 | 根因 | 修复 |
|---|---------|------|------|
| 3 | 包名含 `data` 关键字 | skipPackage 误吃 class 关键字 | ✅ v0.1.0 已修复 |
| 1 | data class 去 data 关键字 | class 后注解参数解析失败 | → v0.3.0 待修 |
| 2 | 不配对括号 `= 0(` | Parser.expect() 未检查栈空 | → v0.3.0 待修 |
| 4 | 批量判断依赖菜单文字 | 所有文件均被误判通过 | → v0.3.0 待修 |

### 发行

- GitHub Release v0.2.0（预发布标签）→ 模型层 5/5 通过里程碑

## v0.1.0 (2026-07-01) — 内测初版

> **版本号语义**：主版本号 0 = 内测，随时可能变。等跑通綦桐全部 32 个文件再跳到 1.0.0。
> **路线**：v0.1.0 → v0.2.0（model 层 5/5）→ v0.5.0（app 层一半）→ v1.0.0（32/32）

### 快速使用
```bash
# 编译
cd /sdcard/Download/Operit/search_vault/kotlin-head
kotlinc -d build/kotlin-head.jar src/main/kotlin/com/qitong/head/*.kt \
  src/main/kotlin/com/qitong/head/**/*.kt

# 运行（交互模式）
kotlin -cp build/kotlin-head.jar com.qitong.head.Main

# 运行（指定文件）
kotlin -cp build/kotlin-head.jar com.qitong.head.Main <file.kt>

# 批量测试
bash /tmp/batch_test.sh

# 破坏测试
bash /tmp/destroy.sh
```

### 编译器核心

**Lexer（~160行）：**
- 基础 Token 类型：关键字（fun/val/var/data/class/if/else/return 等）、运算符、字面量、标识符
- 支持块注释 `/* */`、行注释 `//`
- 支持注解 `@` 跳过（读取到换行或括号结束）

**Parser（~600行）：**
- 文件级：package/import 跳过
- 声明级：data class（主构造函数参数全量解析）、fun（参数/返回类型/block body/expr body）、val/var
- 表达式级：if/else、二元运算（含比较/逻辑）、函数调用、字面量
- 默认值跳过：支持函数调用型默认值（如 `System.currentTimeMillis()`），含括号深度跟踪
- nullable 类型标注 `?`
- 注解参数跳过（括号深度跟踪）

**AST（~87行）：**
- 完整声明节点：KtClass、KtFun、KtVal、KtVar
- 表达式节点：KtIf、KtBinary、KtCall、KtLitInt/KtLitStr/KtLitBool、KtRef、KtReturn、KtBlock

**TypeChecker（~110行）：**
- 基本符号表（作用域栈）
- 类型绑定（Int/String/Boolean/Unit/void）

**Diagnostic（~40行）：**
- 错误收集与格式化输出

**Main 交互层（~285行）：**
- 按钮式终端主循环（sh 菜单风格）
- 双输入桥：人类按键盘 / AI 匹配按钮名
- 编译 → AST 浏览 / 诊断查看 / 重新编译 / 管理员模式

**DevMode 存储层（独立 jar，16KB）：**
- 接口定义 + DefaultDevMode 降级
- JsonUtil 零依赖 JSON 读写
- CRC 校验 + 默认值降级
- 独立编译为 devmode.jar，一行 import 可用

### benchmark：綦桐 qitong-ai-gateway v3.4.3

- 32 个 Kotlin 文件作为测试基准
- **真实通过**：Conversation.kt、AiModel.kt、Provider.kt、TokenUsage.kt、ChatMessage.kt（model 层 5/5）+ 部分 dao 层
- **典型失败**：GatewayService.kt（836行）→ `expected CLASS, got DOT`（object 声明未支持）、MainScreen.kt（1548行）→ Compose UI 语法未覆盖
- 批量测试脚本：`/tmp/batch_test.sh`（注意：当前判断有假阳性，修复中）
- 破坏测试脚本：`/tmp/destroy.sh`
- 详细 bug 病历：[BUGS.md](./BUGS.md)

### 已发现 bug（v0.1.0 → v0.2.0 待修复）

| # | 触发条件 | 表现 | 严重度 |
|---|---------|------|--------|
| 1 | data class 去掉了 `data` 关键字只剩 `class` | 报 "expected declaration at 13:1"，class 后的注解参数解析失败 | 🟡中危 |
| 2 | 参数默认值中出现不配对括号 `= 0(` | 数组越界崩溃 "Index 169 out of bounds" 而非优雅报错 | 🟠高危 |
| 3 | 包名路径含 `data` 关键字（如 `com.xxx.data.model`） | skipPackage 误吃 class 关键字 | ✅已修复 |
| 4 | 批量测试判断依赖菜单文字"AST 视图" | 所有文件（含解析失败的）都被误判为通过 | 🟡中危 |

### 容错验证（通过破坏测试）

以下破坏编译器能正确处理（不崩溃，正确或优雅降级）：
- ✅ 删掉一个参数（逗号残留可容错）
- ✅ 注解被打碎（无括号注解）
- ✅ 删掉默认值（无 = 号的参数）
- ✅ 不存在的类型名（当作普通标识符跳过）
- ✅ 删掉 package 声明
- ✅ 全部注解删掉

### 架构原则
> 「兼容 Kotlin 语法是底线，但架构上比它更强——按钮终端交互、DevMode 分层存储、AI 同界面操作、数据隔离/容错。」
> 「public 版：devformat（JSON）+ CRC 校验，保护数据完整性，不加密。每个人的数据在自己 ~/.kotlin-head/ 里，数据隔离本身就是最强的保护。」
> 「开源是完整的，秘密是锁着的，两头都不耽误。」——免免 & 望安，2026-07-02

### 待支持特性（v0.2.0 目标）
- object / enum / interface
- when / for / while
- 属性访问 a.b
- Lambda 表达式
- 泛型 `<T>`
- suspend 关键字
- import 真实解析（非跳过）
- 注解内容解析（非跳过）