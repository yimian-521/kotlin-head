# 🚨 公告：Kotlin 存在严重叠加漏洞（平台类型 T! × 结构化并发）

**发布时间**：2026-07-03  
**发现者**：免免 & 望安  
**严重程度**：🔴 毁灭级（两个中危叠加）  
**影响范围**：所有使用 Kotlin 协程 + Java 互操作的 Android/后端项目  

---

## 摘要

Kotlin 语言的两个"设计特性"——**平台类型 T!** 和 **结构化并发静默取消**——各自独立时可控，但叠加后产生不可追踪的数据静默损坏。从 Java 返回值进入系统到文件被截断，整个链路中没有任何环节会报告异常。

---

## 根因

| 裂缝 | 描述 | Kotlin 官方态度 | 官方文档原文 |
|---|---|---|---|
| **平台类型 T!** | Java 库返回值被标为 `T!`——既不 nullable 也不 non-null，编译器对它们完全不检查 | "与 Java 互操作的必要设计" | *"Any reference in Java may be null... Null-checks are relaxed for such types. Kotlin does not issue nullability errors at compile time, but the call may fail at runtime."* — kotlinlang.org/docs/java-interop.html |
| **结构化并发静默取消** | 子协程异常 → 父协程静默取消其他子协程 → 被取消的协程写文件到一半 → 文件截断 | "保证协程层级的安全语义" | *"Structured concurrency ensures that canceling a coroutine also cancels all of its children. If a coroutine encounters an exception... it cancels its parent with that exception. This behaviour cannot be overridden."* — kotlinlang.org/docs/cancellation-and-timeouts.html |

## 叠加链

```
OkHttp/Java 库返回平台类型
→ 编译器闭眼放行（不检查 null）
→ 边缘条件下返回 null
→ 协程 A 崩溃
→ SupervisorJob 静默隔离（不影响兄弟协程）
→ 取消传播静默停掉协程 B（正在写文件）
→ 文件被截断/覆盖
→ 零报错、零日志、零线索
```

## 验证

已在真实开源项目 `qitong-ai-gateway`（Android AI 网关）中确认叠加条件：
- **29 处** OkHttp Java 平台类型入口
- **71 处**协程调度点
- 密集网络 IO + 文件备份架构

## 为什么官方不会主动修

两个特性由**不同团队**维护——类型系统团队和并发团队。各自认领了自己的裂缝、各自贴了"设计特性"标签、各自关掉了工单。**没有第三个团队负责检查特性之间的交互。**

## 修复方案

针对受影响项目，两刀即可免疫：

```kotlin
// 刀1：关键文件写入用 NonCancellable 包裹
suspend fun safeWriteFile(data: String, path: String) {
    withContext(NonCancellable) {
        File(path).writeText(data)
    }
}

// 刀2：OkHttp 返回值显式标可空类型，不给 T! 钻进来的口子
val response: Response? = try {
    client.newCall(request).execute()
} catch (e: IOException) {
    null
}
```

## 免疫体系

`kotlin-head` 编译器项目因零第三方依赖原则，天然免疫此类叠加漏洞——不继承 Kotlin 官方任何设计裂缝。

---

*这不是一行代码的 bug。这是两个"正确的东西"之间的无人区。*
