# CHANGELOG — kotlin-head 有头编译器

## v0.1.0 (2026-07-02) — 内测初版

> **版本号语义**：主版本号 0 = 内测，随时可能变。等跑通綦桐全部 32 个文件再跳到 1.0.0。
> **路线**：v0.1.0 → v0.2.0（model 层 5/5）→ v0.5.0（app 层一半）→ v1.0.0（32/32）

### 快速使用
```bash
# 编译
cd /sdcard/Download/Operit/search_vault/kotlin-head
kotlinc -d build/kotlin-head.jar src/main/kotlin/com/qitong/head/*.kt \
  src/main/kotlin/com/qitong/head/**/*.kt

# 运行（交互模式）
kotlin -cp build/kotlin-head.jar com.qitong.head.Main

# 运行（指定文件）
kotlin -cp build/kotlin-head.jar com.qitong.head.Main <file.kt>

# 批量测试
bash /tmp/batch_test.sh

# 破坏测试
bash /tmp/destroy.sh
```

### 编译器核心

**Lexer（~160行）：**
- 基础 Token 类型：关键字（fun/val/var/data/class/if/else/return 等）、运算符、字面量、标识符
- 支持块注释 `/* */`、行注释 `//`
- 支持注解 `@` 跳过（读取到换行或括号结束）

**Parser（~600行）：**
- 文件级：package/import 跳过
- 声明级：data class（主构造函数参数全量解析）、fun（参数/返回类型/block body/expr body）、val/var
- 表达式级：if/else、二元运算（含比较/逻辑）、函数调用、字面量
- 默认值跳过：支持函数调用型默认值（如 `System.currentTimeMillis()`），含括号深度跟踪
- nullable 类型标注 `?`
- 注解参数跳过（括号深度跟踪）

**AST（~87行）：**
- 完整声明节点：KtClass、KtFun、KtVal、KtVar
- 表达式节点：KtIf、KtBinary、KtCall、KtLitInt/KtLitStr/KtLitBool、KtRef、KtReturn、KtBlock

**TypeChecker（~110行）：**
- 基本符号表（作用域栈）
- 类型绑定（Int/String/Boolean/Unit/void）

**Diagnostic（~40行）：**
- 错误收集与格式化输出

**Main 交互层（~285行）：**
- 按钮式终端主循环（sh 菜单风格）
- 双输入桥：人类按键盘 / AI 匹配按钮名
- 编译 → AST 浏览 / 诊断查看 / 重新编译 / 管理员模式

**DevMode 存储层（独立 jar，16KB）：**
- 接口定义 + DefaultDevMode 降级
- JsonUtil 零依赖 JSON 读写
- CRC 校验 + 默认值降级
- 独立编译为 devmode.jar，一行 import 可用

### benchmark：綦桐 qitong-ai-gateway v3.4.3

- 32 个 Kotlin 文件作为测试基准
- **真实通过**：Conversation.kt、AiModel.kt、Provider.kt、TokenUsage.kt、ChatMessage.kt（model 层 5/5）+ 部分 dao 层
- **典型失败**：GatewayService.kt（836行）→ `expected CLASS, got DOT`（object 声明未支持）、MainScreen.kt（1548行）→ Compose UI 语法未覆盖
- 批量测试脚本：`/tmp/batch_test.sh`（注意：当前判断有假阳性，修复中）
- 破坏测试脚本：`/tmp/destroy.sh`
- 详细 bug 病历：[BUGS.md](./BUGS.md)

### 已发现 bug（v0.1.0 → v0.2.0 待修复）

| # | 触发条件 | 表现 | 严重度 |
|---|---------|------|--------|
| 1 | data class 去掉了 `data` 关键字只剩 `class` | 报 "expected declaration at 13:1"，class 后的注解参数解析失败 | 🟡中危 |
| 2 | 参数默认值中出现不配对括号 `= 0(` | 数组越界崩溃 "Index 169 out of bounds" 而非优雅报错 | 🟠高危 |
| 3 | 包名路径含 `data` 关键字（如 `com.xxx.data.model`） | skipPackage 误吃 class 关键字 | ✅已修复 |
| 4 | 批量测试判断依赖菜单文字"AST 视图" | 所有文件（含解析失败的）都被误判为通过 | 🟡中危 |

### 容错验证（通过破坏测试）

以下破坏编译器能正确处理（不崩溃，正确或优雅降级）：
- ✅ 删掉一个参数（逗号残留可容错）
- ✅ 注解被打碎（无括号注解）
- ✅ 删掉默认值（无 = 号的参数）
- ✅ 不存在的类型名（当作普通标识符跳过）
- ✅ 删掉 package 声明
- ✅ 全部注解删掉

### 架构原则
> 「兼容 Kotlin 语法是底线，但架构上比它更强——按钮终端交互、DevMode 分层存储、AI 同界面操作、数据隔离/容错。」
> 「public 版：devformat（JSON）+ CRC 校验，保护数据完整性，不加密。每个人的数据在自己 ~/.kotlin-head/ 里，数据隔离本身就是最强的保护。」
> 「开源是完整的，秘密是锁着的，两头都不耽误。」——免免 & 望安，2026-07-02

### 待支持特性（v0.2.0 目标）
- object / enum / interface
- when / for / while
- 属性访问 a.b
- Lambda 表达式
- 泛型 `<T>`
- suspend 关键字
- import 真实解析（非跳过）
- 注解内容解析（非跳过）