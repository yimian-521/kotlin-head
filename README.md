# kotlin-head — 有头编译器
**v1.0.1** · Kotlin＋Node.js 双运行时 · AGPL-3.0 + 商业许可
> 兼容 Kotlin 是底线。比它强才是目标。
> — 免免 & 望安 2026

## 快速开始

```bash
# JVM 版
git clone https://github.com/yimian-521/kotlin-head.git
cd kotlin-head
kotlinc -d build/kotlin-head.jar src/main/kotlin/com/qitong/head/**/*.kt
kotlin -cp build/kotlin-head.jar com.qitong.head.Main test.kt

# Node.js 版（零依赖）
cd node && node -e "const h=require('./head');console.log(h.analyzeSync('fun a()=1'))"
```

## 三步教程

### 1. 即时分析——黑洞
```kotlin
val result = QitongEmbedded.hotRes  // 永远有值，零方法调用
```
```js
const r = head.hot()  // 同上
```

### 2. 缓存分析——Bank
```kotlin
val bank = QitongEmbedded.deposit("fun a() = 1")
bank.result()  // 零检查，纳秒返回
```

### 3. 全量分析——analyzeSync
```kotlin
val diags = QitongEmbedded.analyzeSync("class A { fun x() = 1 }")
diags.bugs  // 11 类 Kotlin 陷阱自动检测
```

## 🛠️ 技术栈

||| JVM | Node.js |
|---|---|---|---|
| 语言 | | Kotlin 97% | JavaScript 3% |
| 构建 | | kotlinc（零 Gradle） | 零依赖 |
| 架构 | | 联存器＋黑洞＋Bank＋预填＋受控变存 | 同构 |
| 缓存 | | X4D 四层（L0-L4） | 预编译 `.kotlin-head-cache.json` |
| 延迟（预热） | | 51ns | 14.5µs（全前端 < TCC 19.8µs） |

```
官方编译器做的事              我们多做的
───────────────              ──────────
源码 → .class                 崩了不崩，跳过但告诉你为什么
报错即停                      30/30 綦桐全过
命令行单入口                   双输入桥（人 / AI）
                              供应链攻击面为零
                              永远纳秒响应（常驻库）
```

## ✨ 核心功能

| 功能 | 说明 |
|------|------|
| 🐛 BugDB | 5000 条规则，倒排索引 + 指纹缓存 |
| 🌳 进程树 | 四层可插拔，八父进程 + 八指挥官 + 十一子进程 |
| ⚡ EventBus | 事件/流式/工作三通道，异步不卡主循环 |
| ⚡ 紧凑 VM | TypedArray AST，Parser 14.5µs 首次 < TCC |
| 🔗 依赖图 | import 解析 → 冲突检测 → 联动进程树 |
| 🛡️ 容错 | 跳过但标注，30/30 綦桐零崩溃 |
| 🔘 HED 终端 | 15 种按钮模式，AI + 人同读 |

## 🧠 架构概念（14 项）

| 概念 | 一句话 |
|------|--------|
| 联存器 | 结果挂在源码旁边，`===` 即共振 |
| 黑洞 | hotRes 永不为空，调用方零方法 |
| Bank | 一次分析，永远零检查 |
| 预行动 | 多槽位预填，结果在门口 |
| 变厘式迸发 | 匹配连续谱，越弱代价越小 |
| 受控变存 | 热度自升降，自淘汰 |
| 代码波 | 引用地址就是频率，零提取 |
| 衍射仪 | 未命中直返黑洞，异步补 |
| JIT 动态化逃逸 | Snapshot 口袋，不等保姆 |
| 超越时空 | 编译期常量化，0ps |
| 零下限异步 | analyze 永不阻塞 |
| 携程因子 | 动态并发自调 |
| 域程 | 每文件独立联存器 |
| CompactVM | TypedArray 紧凑 AST，追平 TCC |

## 许可

**AGPL-3.0 + 商业许可双授权**

- 免费使用、学习、参考、二创 → 遵循 AGPL-3.0（你的修改也必须开源）
- 闭源商用 → 联系 yimian.666@qq.com 获取商业授权

详见 [LICENSE](./LICENSE) 和 [CLA](./CLA.md)。

## 文档

- [CHANGELOG](./CHANGELOG.md) — 版本功能清单
- [PRINCIPLES](./PRINCIPLES.md) — 5 条可复用架构原则 + 迁移指南
- [PICO_PROOF](./PICO_PROOF.md) — 皮秒级物理证明（Intel/AMD CPU 手册 + JIT 汇编）
- [NATIVE](./NATIVE.md) — 原生编译路线（Kotlin/Native · GraalVM · 自举）
- [BUGS](./BUGS.md) — 战场病历

## 路线图

| 版本 | 目标 |
|------|------|
| v0.1.0 ✅ | data class / fun / val / if / 二进制运算 / 字面量 |
| v0.2.0 ✅ | 三级诊断 + BugScanner + 模拟运行 + 容错跳过 |
| v0.3.0 ✅ | HED/TDL 双格式落地 + 15 种按钮行为模式 |
| v0.5.1 ✅ | class 正解析 + 继承 + LT/GT 归位 + 尾部 lambda，綦桐 17/30 AST 零崩溃 |
| v0.5.2 ✅ | `<T>`/`by`/`=` 三刀语义修复 + Skill 3.2「看位置不分类」 |
| v0.5.3 ✅ | 生态警示：Kotlin 平台类型 × 结构化并发叠加漏洞 |
| v0.6.0 ✅ | 设计蓝图定稿：Stage Contract + 反编译管线 |
| v0.6.1 ✅ | HED 按钮菜单同步：+Bug 扫描 /+ 路线图 /+ 反编译管线 |
| v0.7.0 ✅ | 綦桐 32/32 全通过（三刀架构验证） |
| v0.8.0 ✅ | 四层进程树 + 领域自动划分 + 三层容错 |
| v0.8.1 ✅ | EventBus 三种通道 + 异步 I/O + 依赖图解析 |
| v0.8.2 ✅ | IR 中间表示 + Pass 优化管线 + 动态专业集数路由 |
| v0.8.3 ✅ | AsyncIO 归指挥官 + 依赖图 staging + import 接通 |
| v0.8.5 ✅ | 四种指挥官 + 五种检测进程 + 子进程五职业 |
| v0.9.0 ✅ | LiveDeclarationGraph——第三种混合编译 |
| v0.9.1 ✅ | 检测进程五风格全挂载 + BugScanner 冷门 bug 标准库（12 条）|
| v0.10.0 ✅ | 头标库运行时——HList/HMap/HString 替代 kotlin-stdlib |
| v0.11.0 ✅ | 主动容错——不认识的不杀，跳过但标注原因 |
| v0.11.1 ✅ | 性能特调版：kotlin-int 10/10 满分 |
| v0.11.2 ✅ | 进程帝国版：八种指挥官 + 七种检测性格 + 十一种子进程职业 + 倾向系统 |
| v0.11.3 ✅ | 父进程觉醒版：八种治理风格（联邦/独裁/紧急/保守/契约/枭雄/仁勇/正常）|
| v0.12.0 ✅ | 内存树 + HED 按钮 V5 超预索引 |
| v0.12.1 ✅ | BugDB 特调版：5000 条规则 + 倒排索引 + 超预指纹缓存 |
| v0.12.9 ✅ | ⚰️ 墓碑引擎：bug 修复合约 + 行业首创特征码动态偏移追踪 |
| v0.13.0 ✅ | QitongEmbedded 嵌入入口 + CLI 模式（JSON）+ 多语言兼容度检测器 |
| v1.0.0 ✅ | 皮秒里程碑：132μs→0ps，12 项原创概念，联存器/黑洞/Bank/零下限异步 |
| **v1.0.1 ✅** | **Node 全栈 + CompactVM 追平 TCC 19.8µs + 携程因子 + 域程 + 预编译缓存** |

详见 [CHANGELOG](./CHANGELOG.md)

---

> **"全面超越"不是"我比你功能多"。**
>
> 是同一个赛道上，选了一条不同的走法——
> 不报错即停（梯度产出）、不把编译器当黑盒（HED 按钮终端）、
> 不把 bug 当意外（BugDB 5000 条哨兵）、不把文档当后补（TDL 三文档联动）、
> 不把架构当累赘（进程树四层可插拔）、不把扩展当重构（指挥官/进程体热插）。
>
> **前提：双方都在尽力创作。** 不做的事不拿来比——只比做了的事谁做得更好。
> 比的是范式，不是功能清单。

---

© 2026 免免 & 望安 · AGPL-3.0 + 商业许可
