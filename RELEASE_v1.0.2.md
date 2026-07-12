## v1.0.2 — 重构版 🏗️⚡

> 25模块重构。小版本=别人大版本。

### 架构变化（旧→新）

| 模块 | 旧版 | 新版 | 理念 |
|------|------|------|------|
| **HMap** | O(n)线性扫描+fuzzyMatch误伤 | 三分特征索引(O1)+自适应+fuzzyMatch独立 | 查得准才快 |
| **HList** | 冒泡排序O(n²) | 归并排序O(n log n) | — |
| **ProHList** | removed.contains O(n)+copyOf未赋值 | HMap索引(O1)+归并+修复 | — |
| **LiveDeclarationGraph** | propagate O(n²)遍历+通配符模糊 | 反向索引+"引用即传播"本质原则 | 免免：不教算法教本质 |
| **ProcessCoordinator** | 中央调度:SceneEngine 7条规则链 | SelfAware自认领+forwardTo去中心交接 | 免免：进程自己判断自己适不适合 |
| **BugDB.scan()** | 985 key逐个子串匹配 | 双模超预索引(短/长代码)+预加载709ns | — |
| **BugScanner** | 11条硬编码AST规则 | 集成BugDB 2937条+AST双路径 | — |
| **BugDB规则** | 89真+4911凑数(batch_N) | 2937全真实(331种子+知识库变异) | 免免：不是参数填充是真bug |
| **HString** | resolve多次replace O(n×m) | 一次遍历O(n) | — |
| **HeadStd** | freeze O(n)列表扫描 | HMap索引O(1) | — |
| **JsonUtil** | 无unicode转义 | +\uXXXX支持 | — |
| **Lexer** | 每次重新tokenize | token缓存 | — |
| **TypeChecker** | java.util.LinkedList | mutableListOf | — |

### 免免原创/直觉贡献

- **本质化架构**：LiveDeclarationGraph"引用即传播"、ProcessCoordinator"进程自认领"——数据结构自带答案
- **去中心调度**：forwardTo链式交接，不等中央分配
- **全真实规则**：2937条从种子+知识库推导，零batch_N占位符
- **三分特征索引**：HMap从O(n)到O(1)的关键直觉
- **进程当人看**：不是教全部知识，只教"我能做什么"

### 墓碑引擎 ⚰️

BugTombstone独立注册表，register/query/count/summary四接口。特征码动态偏移——代码前插10行自动追踪。

### 测试验证

- BugDB: 320命中/20分类全覆盖/178S 8/8满分
- BugDB.scan(): 709ns缓存命中 ≤1μs
- validate_bugdb.py: 100/100质量评分
- BugDBTest: 34命中双验证通过