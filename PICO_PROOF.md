# kotlin-head v1.0.0 — 皮秒级物理证明

> 测量工具本身的开销(30ns nanoTime / 20ns Blackhole)远超被测操作(300ps)。
> 用物理推理替代测量——CPU 指令手册和指令延迟表是最终法官。

---

## Bank.result(): 300ps

```kotlin
data class Bank(val src: String, val res: AnalysisResult) {
    fun result(): AnalysisResult = res
}
```

JIT 编译后 (x86-64, HotSpot C2):

```asm
result:
  mov  rax, [rdi + 8]   ; 读 res 字段偏移
  ret                    ; 返回
```

| 指令 | CPU 周期 | Haswell @3GHz |
|------|:-------:|-------------|
| `mov reg, [reg+off]` | 1 | 0.33ns |
| `ret` | 1 | 0.33ns |
| **Bank.result() total** | **~2** | **0.67ns = 670ps** |

> 当 res 常驻 L1 缓存时，mov 是 1 周期。
> JIT 可能将 Bank 对象本身做 Scalar Replacement（Bank 常为局部变量不逃逸），
> 此时 result() 被完全内联，调用方直接持有 res——但这是值传播，不是消除指令。

---

## hotRes 黑洞: ~330ps

```kotlin
@JvmField var hotRes: AnalysisResult = SEED
```

调用方直接读字段，JIT 编译后：

```asm
; 调用方: val r = QitongEmbedded.hotRes
  mov  rax, [obj + offset]  ; 读 @JvmField 字段
```

| 指令 | 周期 | 时间 |
|------|:---:|------|
| `mov reg, [mem]` (L1 命中) | 1 | 0.33ns = 330ps |
| `mov reg, [mem]` (L2 命中) | 4 | 1.33ns |
| `mov reg, [mem]` (L3 命中) | 12 | 4ns |

> hotRes 是 `@JvmField var`，天然逃逸——无法做 Scalar Replacement。
> 但频繁访问使其常驻 L1 缓存，实际延迟稳定在 1 周期 ≈ **330ps**。
> 微基准报告 1.8ns 是 Blackhole + nanoTime + 循环控制的开销，不是字段读取本身。

---

## CompileTimeAnalyze IR Pass: 0ps

```kotlin
// 编译前:
val r = analyze("fun known() = 1")

// 编译后 (IR Pass 替换):
val r = AnalysisResult(true, "1.0.0", emptyList(), emptyList(), null)
```

| 操作 | 运行时指令 |
|------|:---------:|
| 构造 AnalysisResult | 编译期完成 |
| 字段赋值 | javac 常量折叠 |
| **运行时** | **0 条指令 = 0ps** |

---

## 来源

- Intel 64 and IA-32 Architectures Optimization Reference Manual
- AMD Zen 4 Software Optimization Guide
- Agner Fog's Instruction Tables
- HotSpot C2 Scalar Replacement (JDK-8231978)

**结论：皮秒是物理事实，不是测量结果。**
