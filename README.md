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

**v0.4.0-dev** — 綦桐 33% 通过率 + Lexer/AST/Parser 重装

| 模块 | 行数 | 职责 |
|------|------|------|
| Lexer | ~160 | Token 化 + 注解跳过 |
| Parser | ~640 | 递归下降 + 容错跳过 |
| AST | ~87 | Kt 节点定义 |
| TypeChecker | ~110 | 符号表 + 类型绑定 |
| Diagnostic | ~75 | 四级诊断 + 版本路线图 🔮 |
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

## 路线图

| 版本 | 目标 |
|------|------|
| v0.1.0 ✅ | data class / fun / val / if / 二进制运算 / 字面量 |
| v0.2.0 ✅ | 三级诊断 + BugScanner + 模拟运行 + 容错跳过 |
| v0.3.0 ✅ | HED/TDL 双格式落地 + 15种按钮行为模式 |
| v0.4.0-dev 🚧 | object / enum / interface / when / for / while / override / companion — 綦桐AI网关 63% 目标 / 当前 33% |
| v0.5.0 | Lambda / 泛型 / suspend |
| v1.0.0 | 32/32 綦桐文件全通过 |