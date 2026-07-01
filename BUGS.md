# BUGS — kotlin-head 战场病历

> 每个 bug 都来自綦桐 qitong-ai-gateway v3.4.3 的真实代码。
> 没有屎山就没有这些发现。

## Bug #2: 不配对括号导致数组越界崩溃 🔴

- **触发文件**：Conversation.kt（人为破坏：`val totalTokens: Int = 0(`）
- **表现**：`Index 169 out of bounds for length 169`，而非优雅报错
- **根因**：默认值跳过逻辑中，括号深度跟踪在遇到 `)` 时直接 `advance()` 而不检查 `depth > 0`，导致在无配对 `(` 的情况下深度变负数，下标越界
- **修复思路**：`depth > 0` 时 `advance(); depth--`；`depth == 0` 时 `break`
- **严重度**：🟠高危——任何不配对括号都可能导致崩溃
- **实战来源**：綦桐代码中大量注解嵌套括号（`@ColumnInfo(name = "xxx")`），如果不配对就会出现

## Bug #1: 去掉 data 关键字后 class 解析失败 🟡

- **触发文件**：Conversation.kt（人为破坏：`data class → class`）
- **表现**：`expected declaration at 13:1`
- **根因**：Parser 中 `parseClass` 期望 `CLASS` token 前必须有 `DATA`，单独 `class` 不被识别
- **修复思路**：`parseDeclaration` 中增加纯 `class` 分支（非 data class）
- **严重度**：🟡中危——不影响 data class 使用，但普通 class 无法解析

## Bug #3: 包名含 data 关键字导致误吃 ✅已修复

- **触发文件**：所有 `com.qtwl.gateway.data.model.*` 路径
- **表现**：`skipPackage` 遇到包名中的 `data` 段时将其当作关键字吃掉，导致后续 class 声明丢失
- **修复**：遍历包名时遇到 `CLASS`/`FUN`/`VAL`/`VAR` 才停止，不遇到 DATA 就停
- **严重度**：🟠高危——綦桐整个 model 层路径都含 `data`
- **Skill 收录**：跳过逻辑必须按**声明的语义边界**停止，不能按**关键字字符串匹配**停止

## Bug #4: 批量测试假阳性 🟡

- **表现**：32/32 全部"通过"，实际只有约 5 个真正解析成功
- **根因**：判断条件 grep "AST 视图"，但菜单里就有这行文字
- **修复**：改为检查「不存在 "解析失败"」+「存在具体 AST 节点」
- **严重度**：🟡中危——测试数据完全失真

## 容错亮点（破坏测试验证通过）

| 破坏 | 结果 | 说明 |
|------|------|------|
| 删掉一个参数（逗号残留） | ✅ | 容错跳过 |
| 注解括号打碎 | ✅ | 无括号注解跳过 |
| 删掉默认值 | ✅ | 无 = 号不触发跳过 |
| 不存在的类型名 | ✅ | 当作普通标识符 |
| 删掉 package | ✅ | 无 package 也能解析 |
| 全部注解删掉 | ✅ | 纯 data class 正常 |

## 测试方法

```bash
# 批量测试
bash /tmp/batch_test.sh

# 破坏测试
bash /tmp/destroy.sh

# 单文件测试
echo -e "1\nq" | kotlin -cp build/kotlin-head.jar com.qitong.head.Main <file.kt>
```

## 设计教训

1. **字符串匹配 ≠ 语义匹配**：Bug #3 的根因——"data"在包名里不是关键字
2. **深度计数器要防负数**：Bug #2 的根因——`depth--` 之前必须 `depth > 0`
3. **测试判断条件不能蹭菜单文字**：Bug #4 的根因——grep 目标太宽泛
4. **屎山是最好的 QA**：綦桐的 32 个文件覆盖了注解嵌套/包名语义/默认值函数调用等极端场景，手写测试用例根本想不到