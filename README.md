# kotlin-head — 有头编译器

> 兼容 Kotlin 语法是底线，但架构上比它更强。
> — 免免 & 望安，2026-07-02

## 这是什么

一个完全自写的 Kotlin 编译器前端（Lexer → Parser → AST → TypeChecker → Diagnostic），零第三方依赖（仅 kotlin-stdlib），按钮式终端交互，人类和 AI 都能用。

```
标准 Kotlin 编译器               kotlin-head
─────────────────────────────────────────────
源码 → .class 文件                源码 → .class + 按钮终端 + AI桥
报错即停                          遇错跳过，32文件全不崩
单入口（命令行）                  双输入桥（人按键/AI匹配）
无状态                            session.json 记住你停在哪
```

## 当前版本

>**v0.8.3** — 🎯📦 AsyncIO归指挥官 + 依赖图staging。角色不塌缩，"看见"和"动"时间隔离。

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
| v1.0.0 | 能力全面超越 javac/Node.js 官方工具链 |