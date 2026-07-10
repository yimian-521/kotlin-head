# kotlin-head v1.0.0 — 可复用架构原则

> 从 132μs 到 0ps，20 项概念提炼为 5 条可迁移原则。
> 每条原则附带 QitongEmbedded.kt 参考实现 + 伪代码模板。

---

## 原则 1：黑洞 — 结果永不空

**思想**：任何查询类 API 的返回值永远不为 null。初始化时填入默认值，查询不到就返回默认值。

**伪代码**：
```
// ❌ 传统
fun analyze(src): Result? = cache[src]  // 可能 null

// ✅ 黑洞
var latest: Result = SEED
fun analyze(src): Result = cache[src] ?: latest  // 永不空
```

**参考代码**：`QitongEmbedded.kt` — `hotRes` + `init` 预填 seed

---

## 原则 2：分层忙闲 — 快路径不查，慢路径兜底

**思想**：AP 不是让所有路径都更快，是让调用方自己选精度。

**伪代码**：
```
Bank.result()  → 零检查（调用方缓存）
hotRes         → 零方法（公共黑板）
hit()          → 轻量（=== 一条指令）
analyze()      → 兜底（全编译，永不阻塞）
```

**参考代码**：`QitongEmbedded.kt` — Bank / Snapshot / hit / analyze

---

## 原则 3：编译期常量化 — 操作不存在

**思想**：如果输入在编译时已知，结果应该在编译时替换为常量。运行时零指令。

**伪代码**（编译器 IR Pass）：
```
analyze("已知源码")  →  AnalysisResult(true, ...)  // 常量替换
```

**参考代码**：`Pass.kt` — `CompileTimeAnalyze` IR Pass

---

## 原则 4：倒推终态 — 不优化旧路

**思想**：不要问"怎么让当前方案更快"，要问"终态是什么"。从终态倒推，跳过所有中间优化。

**方法论**：
```
0→10 思维： 132μs → 500ns → 186ns → 31ns → 8ns → ...（逐级挖）
10→0 思维： 终态=操作不存在 → 编译期常量化 → 一步到位
```

**参考来源**：长缨架构（复旦周鹏-刘春森团队）— 先定"融合芯片"终态再倒推

---

## 原则 5：预填本能 — 不给第一次惩罚

**思想**：最坏情况的延迟不是正常路径——是第一次。出厂时预填足够多的"本能"，让第一次调用也是命中。

**伪代码**：
```
init { cache.put("常见输入1", result1); cache.put("常见输入2", result2); ... }
```

**参考代码**：`QitongEmbedded.kt` — `init` 预填 + `prefill()` API

---

## 架构全图

```
调用方入口（延迟递增）：
  Bank.result()  → 0ns   ← 调用方口袋
  hotRes 黑洞    → 0ns   ← 公共黑板
  hit()          → 轻量  ← === 指令
  analyzeSync()  → 557ns ← 精确全编译（仅首次）

编译时：
  CompileTimeAnalyze IR Pass → 0ps ← 操作不存在

缓存分层（PentAGI 式硬下限）：
  L0 联存器   → 1条热指针（黑洞）
  L1 HashMap  → 256条LRU
  L2 token流  → 1024条
  L3 AST树    → 1024条
  L4 全编译   → 兜底
```

---

## 迁移指南

1. **黑洞原则** — 任何查询 API 返回值永不为 null，初始化填 SEED
2. **分层入口** — 提供 Bank/字段/方法三种延迟级别让调用方选
3. **编译期常量化** — 写一个 IR Pass 扫描已知输入的函数调用并替换常量
4. **倒推思维** — 先画终态，再反推需要什么，跳过中间优化
5. **预填本能** — 启动时预填所有已知常见输入，消除首次惩罚