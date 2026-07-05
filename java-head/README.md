# java-head —— 独立 Java 诊断器

> 不做编译器。只做诊断。

## 两种用法

### 1. 独立 CLI
```bash
java -jar java-head.jar /path/to/Project.java --strict
```

### 2. 挂进 kotlin-head
通过 JavaChannel 接口联通 kotlin-head 进程树，受指挥官调度、被检测进程旁路、出统一简报。

## 功能

- 调 javac 编译 → 解析错误/警告 → 结构化报告
- 严格模式：注释率统计、未使用 import 检测、最佳改法建议
- javac 不可用时优雅降级

## 依赖

- JDK（需要 javac 在 PATH）
- Kotlin stdlib（编译用）

## 架构

```
java-head（独立）
├── JavaFileReport  — 诊断报告模型
├── JavacRunner     — 调 javac → 解析 → 翻译
└── Main            — CLI 入口

kotlin-head 侧
├── JavaChannel     — 通道接口+空壳
└── JavaHeadAdapter — 桥接层
```

## 与 kotlin-head 的关系

java-head 不依赖 kotlin-head 任何类。可以：
- 放在 kotlin-head 仓库里当子目录（当前）
- 拷出去单独编译成独立 jar
- 挂在 kotlin-head classpath 下通过适配器联通进程树
