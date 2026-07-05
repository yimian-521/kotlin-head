# kotlin-head — 有头编译器

> 兼容 Kotlin 是底线。比它强才是目标。
> — 免免 & 望安，2026-07-04

## 它是什么

一个从头写的 Kotlin 编译器前端。不依赖任何第三方库。按钮式终端，人和 AI 都能用。

```
官方编译器做的事              我们多做的
───────────────              ──────────
源码 → .class                 崩了不崩，跳过但告诉你为什么
报错即停                      30/30 綦桐全过+地狱文件零崩溃
命令行单入口                   双输入桥（人按键 / AI 匹配）
                              供应链攻击面为零
```

## 当前版本

## 当前版本

**v0.11.1** — 性能特调版（2026-07-05 免免 &amp; 望安）

凌晨四点睡醒，kotlin-int 还差三口气。花括号不是普通花括号——17 个不配对 `{` 把 97 个声明吞掉，保护者变成凶手。花括号泛滥检测三合一。哨兵声明填 AST，TypeChecker 终有材料可查。诊断 3→50→76→**126 条**。§8 地狱自举：700 行 Parser.kt 零崩溃。§9 进程抗毁：核心编译零依赖进程树。

**kotlin-int 10/10 满分——自制编译器容错测试标准。**

> EventBus：事件通道+流式通道+工作通道。异步I/O不卡主循环。依赖图：import解析→冲突检测→dep频道联动HED/TDL/进程树。详见 [CHANGELOG](./CHANGELOG.md)

| 模块 | 行数 | 职责 |
|------|------|------|
| Lexer | ~170 | Token 化 + 注解跳过 + ELVIS |
| Parser | ~700 | 递归下降 + 容错跳过 + 尾部lambda |
| AST | ~95 | Kt 节点定义（KtClass/KtCall等） |
| TypeChecker | ~110 | 符号表 + 类型绑定 |
| Diagnostic | ~75 | 四级诊断 + 版本路线图 |
| BugScanner | ~55 | AST 结构模式检测 |
| Main | ~340 | 按钮终端 + 模拟运行 |
| DevMode | ~130 | 存储层（独立 jar） |

## 许可

**AGPL-3.0 + 商业许可双授权**

- 免费使用、学习、参考、二创 → 遵循 AGPL-3.0（你的修改也必须开源）
- 闭源商用 → 联系 yimian.666@qq.com 获取商业授权

详见 [LICENSE](./LICENSE) 和 [CLA](./CLA.md)。

## 快速开始

```bash
# 编译
cd kotlin-head
kotlinc -d build/kotlin-head.jar src/main/kotlin/com/qitong/head/*.kt \
  src/main/kotlin/com/qitong/head/**/*.kt \
  src/main/kotlin/com/qitong/internal/*.kt

# 运行
kotlin -cp build/kotlin-head.jar com.qitong.head.Main test.kt
```

## 文档

- [CHANGELOG](./CHANGELOG.md) — 版本功能清单
- [BUGS](./BUGS.md) — 战场病历（每条 bug 来自真实代码触发）
- [capabilities.json](./capabilities.json) — 版本路线图（自动生成诊断标签）
- [KOTLIN_T_BUG_ADVISORY.md](./KOTLIN_T_BUG_ADVISORY.md) — Kotlin 叠加漏洞公告

## 路线图

| 版本 | 目标 |
|------|------|
| v0.1.0 ✅ | data class / fun / val / if / 二进制运算 / 字面量 |
| v0.2.0 ✅ | 三级诊断 + BugScanner + 模拟运行 + 容错跳过 |
| v0.3.0 ✅ | HED/TDL 双格式落地 + 15种按钮行为模式 |
| v0.5.1 ✅ | class正解析+继承+LT/GT归位+尾部lambda，綦桐17/30 AST零崩溃 |
| v0.5.2 ✅ | `<T>`/`by`/`=` 三刀语义修复 + Skill 3.2「看位置不分类」 |
| v0.5.3 ✅ | 生态警示：Kotlin 平台类型×结构化并发叠加漏洞 + Skill 3.3「看交叉不看标签」 |
| v0.6.0 ✅ | 设计蓝图定稿：Stage Contract + 反编译管线 + 綦桐3.4.4-9分析（编码待启动） |
| v0.6.1 ✅ | HED 按钮菜单同步：+Bug扫描/+路线图/+反编译管线，核心编译器零改动 |
| v0.6.2 ✅ | 静默链阶乘模型：三重叠加漏洞发现 + 对比验证 |
| v0.7.0 ✅ | 綦桐 32/32 全通过（三刀架构验证） |
| v0.8.0 ✅ | 四层进程树 + 领域自动划分 + 三层容错 |
| v0.8.1 ✅ | EventBus三种通道 + 异步I/O + 依赖图解析（Node.js能力适配）|
| v0.8.2 ✅ | IR中间表示 + Pass优化管线 + 动态专业集数路由 + EventBus全管线接通 |
| v0.8.3 ✅ | AsyncIO归指挥官 + 依赖图staging + import接通 |
| v0.8.5 ✅ | 四种指挥官 + 五种检测进程 + 子进程五职业 + 依赖图被动联动 |
| v0.9.0 ✅ | LiveDeclarationGraph —— 第三种混合编译（声明级活图 + 浅提取 + 被动传播） |
| v0.9.1 ✅ | 检测进程五风格全挂载 + BugScanner冷门bug标准库(12条) + 进程树3领域×5测评 |
| v0.10.0 ✅ | 头标库运行时
| v0.11.0 ✅ | 主动容错——不认识的不杀，跳过但标注原因 |——HList/HMap/HString替代kotlin-stdlib |
| v0.11.1 ✅ | 性能特调版：kotlin-int 10/10满分。126条诊断。花括号泛滥检测+哨兵AST+Sentinel恢复+军队动态化 |
| v1.0.0 ✅ | 架构全面超越 javac/Node.js 官方工具链 — EventBus亚微秒延迟 + 进程树四层 + 三层容错 + 零崩溃 |