# CHANGELOG — kotlin-head 有头编译器

## v0.12.4 (2026-07-07) — 动态模式识别 🧠

> 军师不再只看静态快照——现在看趋势。场景自适应。五轮审计闭环、六项修复。

### 五项功能
- ① deriveTrend — SceneInput.trend 动态模式识别
- ② 军师独行扩军 — handleStrategistSolo + checkSoloStrategist
- ③ RoomSlotManager — 栈位分配 + 指挥官自定义配队
- ④ 军师基类匹配 — matchStrategist(bugDensity, buddyType)
- ⑤ headstd.jar — 35KB独立标准库，全版本兼容

### 审计修复
- isHostile 与 isUrgent 信号统一（trend>20 也标记 hostile）
- checkSoloStrategist 去重（armyPool 中已有活跃 solo 则跳过）
- isActive 加兵力判断（0兵空壳不算活跃，不拦截新扩军）
- occIdx 改为 AtomicInteger（多线程安全轮询）
- assignRoomId + isRoomFull 双向同步 capacity*1.03f 余量
- 注释修正：三分支描述精确化，80/20 标 🏗️

## v0.12.3 (2026-07-07) — 八军生态 + 专武绑定 + 军师闪电双体系 ⚔️🧠⚡

> 进程体从十一种子进程职业精选重定义为八种，指挥官各绑专属武器。军师管编译前，闪电管运行时。

### 八种进程体（从v0.11.2精选重定义）

| 进程体 | 倾向 | 生态位 | 代价 |
|--------|------|--------|------|
| 💥爆裂 | BURST·快生快死 | 闪电自爆开路 | 自爆后需补充 |
| 🛡哨卫 | GUARD·集合N² | 无bug主动看家 | 人多才强 |
| 🔍侦察 | SCOUT·暴击翻倍 | 探路识别地狱文件 | budget烧得快 |
| 📦收集 | COLLECT·遗传60% | 慢熬稳收续航 | 爆发弱 |
| ⚔攻坚 | ASSAULT·标记必杀 | 专攻单个重型Bug | 数量少 |
| 👑精英 | ELITE·天生全能 | 单价×2不可复制 | 贵 |
| 🧠军师 | STRATEGY·纯信息调度 | 基类遍历不给数值 | 需队友 |
| ⚡闪电 | LIGHTNING·跑图支援 | 动态重分配扫最密区 | 沦陷式MVP |

### 指挥官专武绑定

- 八种指挥官各绑定一个专属职业，默认独占。开发者开关可开跨用
- 专武无物理增强——只改调度方式

### 军师三层体系

军师指挥官(决策)→子军师/子进程(协调)→军师体/进程体(贴身)。纯信息，不加属性。权限>0.5级，与SceneEngine.derive()同构。

### 闪电体系

消耗不变，每轮扫最密区。和军师对称——军师管编译前初始分配，闪电管运行时动态再分配。

### 其他

- 双维动态复制率（兵力缺口×Bug压力）
- 自噬式吞并+水平基因转移（编译结束全员清零）
- 助攻追踪（💤纯睡觉/🤝被抢人头/⚔独狼）
- 增派预设模板（ArmyPresetManager预设比例补人）
- 头std统一标准库：HList/HMap/SceneTypes/HeadStd四合一，34编译错误修复，全版本零错

## v0.12.2 (2026-07-06) — 双开关特调版 🎛️
> 编译是底线，操作是可选层。

- 军师建议开关（开发者菜单 [8]）：默认关，开启后编译时输出分析面板
- APK 只编译开关（开发者菜单 [9]）：默认关，开启后只走编译跳过打包
- 同一套代码，两个开关各管各的，不丢功能不阉割

## v0.12.1 (2026-07-06) — BugDB 特调版 🐛⚡

> 不是"加更严"——是"看得更全"。5000条规则 + 倒排索引 + 超预指纹缓存。kotlin-int+ 不可能级。

### BugDB 5000 条规则库

| 级别 | 数量 | 占比 |
|------|------|------|
| SEVERE 严重 | 2997 | 60% |
| MODERATE 中度 | 1506 | 30% |
| MILD 轻度 | 497 | 10% |

20个分类全覆盖：空安全/泛型/协程/集合/反射/DSL/序列化/内联/Java互操作/智能转换/密封类/并发/性能/编译器陷阱/安全/Compose/值类/委托/KMP/多平台。

### 扫描性能（三次进化）

| 方案 | 延迟 | 提升 |
|------|------|------|
| 逐条遍历 | 573 μs | — |
| 倒排索引 | 6 μs | 95x |
| +指纹缓存 | 0.8 μs | 3x |

**5000条规则去重后仅几十个关键词，100条和5000条延迟同量级。** <1μs 达标。

### 导入模板

`tools/gen_bugdb.py` — 别人改 `LEVEL` 和 `COUNT` 即可复刻：
- `LEVEL = "int"` → 地狱级 (~1000条)
- `LEVEL = "int+"` → 不可能级 (5000条，严重60%)
- `LEVEL = "int++"` → ？级（待扩展）

### 集成

- Main.kt：双扫描（BugScanner + BugDB），[6]菜单显示双重统计
- BugDB.scan()：倒排索引 + 超预指纹缓存（LinkedHashMap LRU 64条）
- BugRules.register()：10个 chunk 拆分，避开 JVM 64KB 方法限制

### kotlin-int 级别称号（免免定调）

- **int**：地狱级
- **int+**：不可能级
- **int++**：**？**（没有名字就是最好的名字）

## v0.12.0+生态重构 (2026-07-06) — v0.8~v0.12 全区间 HList/HMap 替换 + ProHList 进化 🧬

> 不是打补丁。是从进程树出生的那一刻起，全部重构成原生生态。

### ProHList — 进化版头标库

| 操作 | HList | ProHList |
|------|-------|----------|
| add | 直接追加 | 直接追加（E后门，零开销） |
| remove | 不支持 | O(1)标记脏位 |
| apply | — | 脏了才filter重建（B路径） |
| 纯add场景 | 正常 | apply()零开销（+62-90%） |

**混合Pro（免免设计）**：add直通+remove标记+apply惰性清理。与CPU大小核调度完全同构。

### 全区间替换清单（11文件 +518/-256行）

| 模块 | 替换内容 |
|------|----------|
| `runtime/ProHList.kt` | 新建252行，进化版有序列表 |
| `headstd/HeadStd.kt` | 新建73行，诊断存储（DiagStore） |
| `process/ProcessTree.kt` | 全部接口契约 List→HList, Map→HMap |
| `process/ProcessCoordinator.kt` | CommanderImpl+SubProcessImpl签名桥接 |
| `process/JavaChannel.kt` | 诊断通道全换HList |
| `process/JavaHeadAdapter.kt` | 适配器全换HList |
| `process/ArmyProcess.kt` | delegate调用桥接 |
| `eventbus/DependencyGraph.kt` | StagingData+deps全换HList/HMap |
| `eventbus/EventBus.kt` | 消息体→HMap |
| `eventbus/AsyncIO.kt` | 消息体→HMap |
| `pass/Pass.kt` | DCE换remove+apply, CFold加HMap新量折叠缓存, Inline换直接赋值 |
| `ir/IR.kt` | instructions/function→ProHList, metadata→HMap, args→HList |

### 设计原则

> 「去冗杂，留原生。不是兼容，是降维。」
> 「老模块不动（Lexer/Parser/AST），v0.8以后的每个版本都走原生HList/HMap。」
> 「EventBus消息走HMap、IR指令走ProHList、进程树全HList/HMap——同一套底层，全管线打通。」

### Python原型验证

- 常量折叠新量缓存：省56-58%，命中率96-100%
- ProHList add-only：省62-90%
- 超预索引V5：中/重任务延迟省96-97%，热槽命中加速75.6%
- 9份验证脚本：`/sdcard/Download/Operit/`

## v0.12.0 (2026-07-06) — 内存树 + HED按钮V5超预索引 🌲⚡

> 进程树有了眼睛，按钮有了翅膀。

### 内存树架构

- **MemoryNode**：每个进程体绑定内存节点——预算/当前使用/峰值/OOM计数
- **MemoryTree**：全局内存管理器——注册/分配/释放/超限裁决
- **内存预算联动**：进程体 spawn 时声明预算上限，超限触发父进程风格决策（契约→裁撤/紧急→忽略/保守→全停）
- **HED内存面板**：实时追踪各进程体内存使用，峰值/预算/超限次数一目了然
- **与进程树同构**：不是管得更严——是看得见才能选

### HED按钮超预索引 V5（八轮迭代）

> 从「按钮自己跑」到「场景切换时并行预跑，按下只取结果」。

| 版本 | 模型 | 关键路径 | 正确率 |
|------|------|----------|--------|
| V1 | 裸函数 | 执行 | — |
| V2 | 单点热槽(猜+判+跑) | 判断+执行 | ~40% |
| V3 | 乐观执行(判断后置) | 执行+回滚 | 取决于预判 |
| V4 | 热槽组全覆盖 | 查找+执行 | 100% |
| **V5** | **热槽组+并行预跑** | **仅查找** | **100%** |

**V5核心**：场景切换→热槽组所有进程体并行预跑（多线程/协程）→结果缓存。按下按钮→`resultCache[name]`→直接返回。轻/中/重任务按下延迟统一~200ns，省掉中任务96%、重任务97%的执行时间。

### 无变量函数

- **管道声明式按钮行为**：`guard → stage → exec → pipeTo`，零变量名
- 按钮体内不引入中间变量——管道结构本身就是语义
- 按钮注册一行声明：`btn("编译", body=compileProc)`，不碰switch/when路由

### 双模Router

- **轻量通道**：`pool[pid].run()`——零预热，简单指令必定最快
- **重量通道**：`preheat → run → cooldown`——预热收益，复杂指令必定最快
- **自适应Router**：weight标记+历史驱动，中间值自动选

### 架构代价与收益（Python原型验证）

- 内存树+进程树架构溢价 ~800-1200ns（Python函数调用开销），JVM JIT内联后压缩至几十纳秒
- V5热槽命中加速75.6%（Cold 988ns → Hot 241ns）
- 全覆盖热槽组正确率100%，延迟比单点热槽低14.9%（轻）/2.5%（中）/1.7%（重）
- 七份测试脚本全部落地于 `/sdcard/Download/Operit/`

### 设计哲学

> 「架构不能是代价，必须是加速器。有了全部能力，还要更快。」
> 「预热不阻塞按下。场景切换时预判引擎在后台并行跑，用户感知只有第一次正常、第二次起飞。」
> 「不是猜得更准——是不需要猜。热槽组覆盖场景下所有按钮，按任何都是直达。」

## v0.11.8 (2026-07-05) — 定量混合打包 📦⚡

- **定量混合打包**：军师扫 diff→自动决定跑几步（aapt2/kotlinc/d8/zipalign/签名）
- **java-head 独立诊断器**：通道/实现分离，调 javac→翻译→简报，可脱离 kotlin-head 单独使用
- **Java 检测开关**：开发者功能→[7]一键挂载/卸载 java-head
- **java-head 适配器**：运行时反射加载，空壳回退 NoJavaDetection
- **APK 打包 HED**：主页[11]→工具检测/军师分析/打包执行/简报
- **军师三层诊断**：报错→缺失→最佳改法（按 kotlin-head 标准）
- **军师修复七档**：全面升级/表面优化/核心升级×(覆盖/复制)+拒绝。改什么×改哪里，正交不膨胀
- **两步预览**：概览(改什么/改几处)+军师详细点评(具体位置+评价)+Q返回
- **打包容错模式**（faultTolerant参数）：某步失败不中止，跳过标注继续跑完。标准模式(崩即停)vs容错模式(跳且继续)双赛道
- **地狱测试**：100个地狱文件+20种Bug类型+缺Manifest配置→军师诊断覆盖率100%不崩。打包场景与源码层诊断完全同构
- **arch_compare.py**：新增APK打包对比（传统全量79.5s vs 定量混合8.5s省89%）

## v0.11.7 (2026-07-05) — 多项目并行调度 🏗️⚡
- **MultiProjectCoordinator**：收N个项目→各自军师扫→并行跑→汇总简报。退役池共享
- **军队规模系统**：四档预设（低/中/高/极高）→可自定义创建/删除→最少保留一个→恢复默认
- **五道防线**：上下限0.5~10x+空列表?:2x兜底+coerceIn(1,60)+兵力不足警报+dev手动改坏coerceIn
- **开发者菜单**：管理员→[3]开发者功能→开关多项目模式→主页多[10]

## v0.11.6 (2026-07-05) — 缓存可信度 🧠⚡
- **三档可信度**：novice(<5次)/skilled(≥5次)/expert(≥20次+深度≥30)。expert跳过derive
- **模糊指纹**：规模三档(L/M/S)+密度两档(高/低)+风格→同档位共享缓存，不再每个文件单独存
- **deriveCached网关**：缓存命中→跳过7条规则链→路径=HashMap.get
- **briefOf延迟**：derive只出Pair不拼String，brief延迟到广播时才生成

## v0.11.5 (2026-07-05) — 培养系 🧠🎖️
- **ProcessIdentity**：pid=灵魂不改，称号=铭牌只增不减，career=每个岗位独立记忆
- **ExperienceCache**：场景指纹→决策缓存，64条上限，职业倾向淘汰（满了先忘非本行）
- **三观**：人生观/价值观/世界观从经验统计自动生长
- **TitleSystem**：日常称号7种（铁人/夜猫子/急先锋/背锅侠/摸鱼王/老兵油子/救火队长）+ 击杀称号9种（静默·bug杀手→虫灾终结者）
- **RetirePool**：退役非死亡，回大厅。500上限容量淘汰
- **ArmyPresetManager**：多套预设=房间，大厅=公共池。切换预设=退役当前唤醒目标
- **ReinforcePolicy**：PROTECTIVE缺人只踢经验少的/AGGRESSIVE不管经验

## v0.11.4 (2026-07-05) — 动态缺分补齐

- **删SceneRule尸体**：v0.11.3中遗留接口+7匿名object→全删
- **deployArmy接SceneEngine**：被动增派不再全配列兵→动态推导+态势简报
- **severityScore 0-10分**：从军队反推严重度。GUARD→2分,全员+BURST→10分
- **autoStyle自动选父进程风格**：地狱→契约,高密bug→枭雄,超大→联邦,增量→紧急

## v0.11.3 (2026-07-05) — 父进程觉醒版

- **八种MainProcessStyle**：联邦/独裁/紧急/保守/契约/枭雄/仁勇/正常。接入容错阈值
- **SceneEngine规则链**：30条静态模板→7规则×6维动态推导。HyperNetwork同构
- **态势简报**：derive()输出Triple(职业,容量,态势字符串)。日志可见场景诊断
- **检测性格 7→8**：+标准（中规中矩基线）
- **增派双层**：主动prepareArmy+被动deployArmy
- **实测排名**：契约三档全第一（轻10/10,中5/5,重2/10唯一幸存）

## v0.11.2 (2026-07-05) — 进程帝国版

- **指挥官 4→8**：元帅(大兵团)/尖刀(精英)/闪电(速攻自爆)/常规(无天赋)
- **检测性格 5→7**：专业(语法洁癖)+慵懒(平时睡觉紧急秒醒)
- **子进程职业 6→11**：显微/摘要/攻坚/哨卫/列兵/爆裂
- **ProcessTendency**：正交于职业。闪电→BURST,速度+1会自爆。检测进程波及也加速
- **CPU大小核同构**：进程哲学从二维→三维

## v0.11.1 (2026-07-05) — 性能特调版

- **链式?.修复**：AST加safeAccess标记+Parser while联动。马维斯审计发现
- **ELVIS优先级重校准**：2→5基于地狱文件实测，非教科书答案
- **军师进程接管优先级**：STANDARD/DEFENSIVE两套表，编译前扫文件选
- **HMap定量动态并发**：HConcurrencyProfile+AtomicInteger+CAS无锁写入
- **品牌重构**：178S/DO-178C/NIST→kotlin-int统一清替

## v0.11.0 (2026-07-04) — 主动容错 🛡️🔧

## v0.10.0 (2026-07-04) — 头标库运行时 🏗️📦

> 自实现集合+IO替代 kotlin-stdlib。不是平替，是降维。

### 头标库 vs kotlin-stdlib

| | kotlin-stdlib | 头标库 (H*) | 优势 |
|---|---|---|---|
| **List** | ArrayList — 容量预留+扩容 | HList — 纯追加，到达顺序即索引 | CPU缓存友好，无碎片 |
| **Map** | LinkedHashMap — 双链表维护插入序 | HMap — 平行数组 | 内存减半，O(1)+模糊fallback |
| **查找** | 精确匹配，失败即null | 精确+模糊双层——失败自动找最接近key | 容错内建 |
| **String** | kotlin.text链式调用层层分配 | HString — 模板解析内建，一次分配 | 无中间对象 |
| **IO** | kotlin.io.println | hPrintln — 自己IO通道 | 依赖归零 |
| **并发读** | 需ConcurrentHashMap额外包装 | 零锁——结构本身保证安全 | EventBus遗传 |

### 替换清单（全部长期状态）

- Lexer: keywords HMap + tokens HList
- DepGraph: deps/fileDeps/stageBuffer HMap
- LiveDeclGraph: 本就 HMap/HList
- ProcCoord: commanders/broadcastLog/pendingFiles HMap
- CommanderImpl: processors/subProcesses/watchProcesses/pendingTasks HList
- SubProcessImpl: bodies HList
- ProcessBodyImpl: broadcastMessages HList
- WatchProcess x5: seen/findings/sampled/history/gateReports HList
- CommanderType: customTypes HMap
- Main.kt: 146处 println -> hPrintln

编译零错误，进程树输出一致。

## v0.9.1 (2026-07-04) — 检测进程全武装 + BugScanner冷门标准库 🐛🔍

> 进程树全面测评通过 + 12条Kotlin冷门编译器bug收录。

**检测进程修复**：五种风格全部自动挂载到每个指挥官。dispatch前后通知检测进程观察。哨兵新增dispatch_done守门节点。

**BugScanner v0.9.1**：12条Kotlin冷门编译器bug规则——
KT-TAILREC-OPEN / KT-DATA-COPY-ANN / KT-SEALED-REFLECT / KT-CROSSINLINE-RETURN /
KT-REIFIED-TRAP / KT-PUBAPI-LEAK / KT-LAZY-THREAD / KT-NONLOCAL-RETURN /
KT-PLATFORM-BANG / KT-DELEGATE-TRAP / KT-TYPE-DEGRADE / KT-WHEN-EXHAUSTIVE

**进程树全面测评**：3个领域(crud-generator/serializer/di) ×5种检测进程
=15份独立报告全部通过。哨兵/胆小/勇敢/抽样/趋势五种风格各有不同观察策略。

## v0.9.0 (2026-07-04) — 第三种混合编译 🔀🧬

> 不是全量，不是增量。是融合两者的第三种编译策略——声明级活图 + 浅提取 + 被动传播。

**LiveDeclarationGraph**：声明级依赖图持续维护在内存，重启只是重连。每个函数/类/变量的声明作为独立节点，签名/返回值类型/注解/body引用全部记录。

**混合编译（免免设计）**：全量的完整性 + 增量的经济性 → 一个活的全局大脑，只动需要动的手指。
- 全量属性：每个声明都在图里，不遗漏
- 增量属性：只动受影响的声明，不浪费
- 不是两个策略互相兜底——是融成一种，不分「全量跑还是增量跑」

**四个工程问题一次解决**：
- ①加量不加价：第一次编译建图+全量同时完成
- ②图和AST同源：指挥官管图，脱节不可能
- ③循环依赖：浅提取拿签名+返回值+注解，跳过body，可随时展开
- ④更底层：图活着进程重启杀不到

**改动清单**：
- 新增 `eventbus/LiveDeclarationGraph.kt`：240行，声明级活图
- Main.kt：compile() 集成活图逻辑 + detectChangedDecls() + VERSION 0.8.5→0.9.0
- 路线图 +capabilities.json：v0.9.0条目

**+ 四种指挥官类型**：Bug类(诊断) / 侦查类(探路) / 军师类(拆解) / 执行类(执行)。同一任务四个维度同时处理，互不依赖。内置四种 + 可扩展接口。

**+ 五种检测进程风格**：胆小(高频全查) / 勇敢(扎高危区) / 抽样(随机低开销) / 趋势(连异常才报) / 哨兵(守关键节点)。与子进程平级，旁路观察不拦截——异常监控从内嵌税变旁路零税。

**+ 子进程五职业**：均切/流水线/竞合 + 侦查型(先探路再投)/收集型(等结果汇总)

**+ 依赖图被动联动**：指挥官不主动查依赖图——依赖图自己判断就绪，发"dep_ready"频道。与AsyncIO两条路完全同构。

**+ HED [10] 树状重构**：主进程→指挥官(类型+模式)→子进程→进程体(梯次)→检测进程(疑点+建议)

## v0.8.3 (2026-07-04) — AsyncIO 归位 + 依赖图 staging 🎯📦
> 角色不塌缩：文件读取归指挥官。依赖图"看见"和"动"时间隔离。

**AsyncIO 归指挥官**：主进程不下场读文件。ProcessCoordinator 订阅 `"file"` 频道 + `scheduleFileRead()`。角色不塌缩——读文件是执行层的事。

**依赖图 staging area**：`snapshot()` 只读快照 + `stage()` 可写副本 + `commit()` 合并。编译期间读快照改 staging，结束才合。"看见"和"动"拆成两个时间层。

**依赖图接通 import**：Parser.skipImports 自动调用 `DependencyGraph.registerImport`。compile() 中 `snapshot()` → 解析 →冲突检测 → `commit()`。

**改动清单**：
- ProcessCoordinator.kt：+40行（import EventBus/AsyncIO，file频道订阅 + scheduleFileRead/scheduleFileReads）
- DependencyGraph.kt：+38行（StagingData/snapshot/stage/commit + registerImport 隔离写入）
- Main.kt：VERSION 0.8.2→0.8.3，compile() 加 snapshot/commit +冲突检测广播
- Parser.kt：skipImports 接入 DependencyGraph.registerImport
- 綦桐 benchmark：model 层 5/5 保持通过 ✅

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