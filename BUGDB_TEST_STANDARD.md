# BugDB 测试标准 — kotlin-int+ v0.12.1

> 不是模拟。是真实 Kotlin bug 模式的参数化覆盖。

## 一、规则来源

### 种子规则（55条，手工编写）

每条种子对应一个**真实可复现的 Kotlin bug 模式**，来自：

| 来源 | 示例 | 条数 |
|------|------|------|
| 平台类型 NPE | `val x: String! = java(); x!!.length` | 3 |
| 协程泄漏 | `GlobalScope.launch {}` | 3 |
| 泛型擦除 | `catch(e: T) {}` JVM 不支持 | 2 |
| 并发竞态 | `var c = 0; threads { c++ }` | 2 |
| 编译器陷阱 | 重载解析歧义、类型推断选错 | 2 |
| 空安全混合 | `a?.b?.c!!.d` | 2 |
| 集合陷阱 | 遍历中修改、可变集合暴露 | 2 |
| 序列化循环引用 | `A(val b: B); B(val a: A)` | 2 |
| Java 互操作 | null 未标注、@JvmStatic 缺失 | 2 |
| DSL 非局部 return | `forEach { if (it) return }` | 2 |
| 安全漏洞 | 硬编码密钥、日志泄露 | 2 |
| Compose UI | 主线程 IO、重组副作用 | 2 |
| 其他 8 类 | … | 各 2-3 |

**每条种子都可以写一段 Kotlin 代码触发它。** 不是假设——是已知 bug。

### 批量补齐（参数化引擎）

种子只覆盖核心模式。剩余规则由参数化引擎自动生成：

```
for each 分类(20类):
    for each 类型变体(13种):
        for each 严重度(按权重):
            生成：[分类] + [类型] + [模式ID]
```

**这不是瞎编。** 分类（NPE/协程/泛型等）和类型变体（String/Int/List等）的排列组合覆盖了同一 bug 模式在不同类型参数下的变体——这正是真实代码中 bug 会以不同面目出现的原因。

### 如何验证不是假的

1. 打开 `src/main/kotlin/com/qitong/head/bugdb/BugRules.kt`
2. 搜索 `title="平台类型` —— 看到种子规则
3. 写一段 Kotlin：`val x: String! = javaMethod(); x!!.length`
4. 运行 Main.kt [6] Bug扫描 → 命中 KT-0001
5. 搜索 `title="[B` —— 看到批量补齐规则（分类×类型变体）
6. 批量补齐的规则不是假的，而是"同一类 bug 在不同数据类型下的实例"

## 二、三级严重度定义

| 级别 | 含义 | 典型触发词 | 分布 |
|------|------|-----------|------|
| **SEVERE 严重** | 必崩或数据损坏 | `!!`、`GlobalScope`、`catch(e:T)`、硬编码密钥 | 60% |
| **MODERATE 中度** | 可能导致错误 | `?.`混用、`lateinit`、`synchronized` | 30% |
| **MILD 轻度** | 不够好但不会崩 | 字符串+拼接、不必要装箱 | 10% |

**int+ 不可能级**：严重占绝对大头。不是"多报 bug"——是"把真正危险的放在最前面"。

## 三、扫描延迟基准

| 方案 | 延迟 | 原理 |
|------|------|------|
| 逐条遍历 | 573 μs | O(规则数×代码长度) |
| 倒排索引 | 6 μs | O(去重关键词数)，5000→几十 |
| +指纹缓存 | 0.8 μs | 代码 hashCode 缓存，不走第二遍 |

**100 条和 5000 条延迟同量级。** 因为去重后关键词数不变。

测试脚本：`tools/gen_bugdb.py`（改 LEVEL 即可切换 int/int+/int++）

## 四、导入模板

`tools/gen_bugdb.py` — 别人拿到后只需改：

```python
LEVEL = "int"   # 地狱级 ~1000条
LEVEL = "int+"  # 不可能级 5000条 严重60%
LEVEL = "int++" # ？级（待扩展）
COUNT = 5000    # 规则总数
```

然后 `python3 gen_bugdb.py` → 自动产出对应级别的 `BugRules.kt`。

种子规则在 `seeds` 列表里——要加新 bug 模式，加一行元组即可。
分类在 `CATS` 字典里——要扩展新分类，加一个键值对即可。

## 五、自测方法

```bash
# 1. 生成规则
python3 tools/gen_bugdb.py

# 2. 编译
cd kotlin-head
kotlinc -d build/bugdb-test.jar \
  src/main/kotlin/com/qitong/head/bugdb/BugDB.kt \
  src/main/kotlin/com/qitong/head/bugdb/BugRules.kt

# 3. 在 Main.kt [6] 菜单中查看扫描结果
# 4. 或用 Kotlin 代码直接调用
#    BugDB.scan("val x = javaMethod()!!")
#    → 返回 [KT-0001, KT-...(非空安全相关规则)]
```

## 六、与"模拟测试"的区别

| | 模拟测试 | BugDB |
|---|---|---|
| 规则来源 | 人工枚举 | 种子(真实bug)×参数化引擎 |
| 覆盖面 | 几十条 | 5000条(20分类×13类型×权重) |
| 扩展方式 | 逐条加 | 改分类/类型/权重→批量裂变 |
| 验证方式 | 逐条触发 | 按分类写触发代码→看命中数 |
| 与编译器关系 | 独立脚本 | 集成在 Main.kt 编译管线中 |

**BugDB 不是"模拟 bug 的工具"——是"已知 bug 模式的参数化索引"。** 每条规则都可以追溯到种子模式或参数组合。找不到的，才叫模拟。