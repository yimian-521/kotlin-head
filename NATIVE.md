# NATIVE.md — kotlin-head 原生编译路线

> 当前: JVM 热态全编译 404µs（不含Codegen）
> 目标: 原生编译 + Codegen，全链路 < 10µs

## 方案

### 方案 A: Kotlin/Native（零代码改动）

```
kotlinc-native src/... -o kotlin-head
→ LLVM IR → x86-64 binary
→ 零JVM、零GC、零JIT
→ Lexer: 4.5µs → < 500ns
→ 全前端: 404µs → < 10µs
```

### 方案 B: GraalVM Native Image

```
native-image -cp kotlin-head.jar QitongEmbedded
→ AOT编译 → 原生二进制
→ 仍保留部分GC，但类加载归零
→ 全前端: 404µs → ~5µs
```

### 方案 C: 自举（手搓 Codegen）

```
kotlin-head IR → 字节码生成 → 可脱离 kotlinc
阶段1: IR → Kotlin源码回写（~500行）
阶段2: IR → JVM bytecode（~2000行）
阶段3: IR → x86（类似TCC）
```

## 当前状态

- 方案 A/B 缺本地编译链（kotlin-native / native-image）
- 方案 C 需补 Codegen
- 架构已就绪，所有源码可零改动直通原生编译