---
name: kotlin-head
description: 有头编译器——零依赖Kotlin编译器前端。编译Kotlin源码、AST分析、Bug扫描、进程树检测。一条命令即可运行。
disable-model-invocation: false
---

# kotlin-head 有头编译器

> **零依赖 Kotlin 编译器前端。** 拿到一个 .kt 文件，直接编译、看 AST、扫 Bug、展示进程树。jar 已预编译好，不需要安装任何依赖。

## 快速使用

```bash
# 编译一个 .kt 文件
kotlin -cp /sdcard/Download/Operit/skills/kotlin-head/kotlin-head.jar com.qitong.head.Main <file.kt>

# 只看 AST
kotlin -cp ...jar com.qitong.head.Main <file.kt> --ast

# 看诊断
kotlin -cp ...jar com.qitong.head.Main <file.kt> --diag

# 看进程树（注解处理+检测进程报告）
kotlin -cp ...jar com.qitong.head.Main <file.kt> --process
```

## 能力一览

| 能力 | 说明 |
|------|------|
| Lexer → Parser → AST | 完整 Kotlin 前端，递归下降+容错跳过 |
| TypeChecker | 符号表+类型绑定 |
| Diagnostic | 四级诊断（错误/警告/预期/信息） |
| BugScanner | 12条 Kotlin 冷门编译器 bug 规则 |
| 进程树 | 四层架构：主进程→指挥官→子进程→进程体 |
| 检测进程 | 五种风格：哨兵/胆小/勇敢/抽样/趋势 |
| LiveDeclarationGraph | 声明级活图，混合编译 |
| IR + Pass | 三地址码 IR + 死代码消除/常量折叠/内联展开 |
| EventBus | 三种通道：事件/流式/工作 |

## 测试文件

skill 目录下自带两个测试文件：
- `proc_test.kt` — 注解+进程树+检测进程完整测试
- `bug_test.kt` — BugScanner 冷门 bug 触发测试

```bash
kotlin -cp ...jar com.qitong.head.Main proc_test.kt --process
```

## 特殊语法支持

| 语法 | 状态 |
|------|------|
| data class / fun / val / var / if / return | ✅ |
| 注解 (@Entity, @Serializable, @Inject 等) | ✅ |
| when / for / while | ❌ 待支持 |
| Lambda / 泛型 <T> / suspend | ❌ 待支持 |
| object / enum / interface | ❌ 待支持 |