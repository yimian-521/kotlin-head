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

**v0.6.0** — 🏗️ 设计蓝图定稿：阶段间契约（Stage Contract）+ 反编译管线 + 綦桐 3.4.4-9 逆向分析

> 四阶段双向声明+验证，经免免全量审核修正。核心三原则：不静默、不甩锅、不断链。
> 反编译管线：APK→dex→jadx→Kt源码→kotlin-head AST→语义复原输出。
> 两条接缝：jadx非标准Kt优雅降级 + 推断链断裂标&lt;?&gt;。详见 [CHANGELOG](./CHANGELOG.md)
> 綦桐 3.4.4-9 APK 使用 apk_reverse 内置 jadx 完成逆向，确认新增 ProxyProfile + ProviderTokenSummary + AppLanguage。

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
| v0.6.0 🏗️ | 设计蓝图定稿：Stage Contract + 反编译管线 + 綦桐3.4.4-9分析（编码待启动） |
| v1.0.0 | 32/32 綦桐文件全通过 |