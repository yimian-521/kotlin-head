#!/usr/bin/env python3
"""
kotlin-head 架构对比测试
裸父+子 vs 分工明确（全默认正常配置）
同一份地狱源码，两条路径
"""

import time, random

# ========== 模拟源码：一个带语法地狱的文件 ==========
HELL_FILE = """
package com.example.hell

class BrokenService<T> {
    suspend fun handle(input: T!) {  // 平台类型+协程
        val result = input?.let { process(it) }  // 链式安全调用
        result!!.save()  // 强制解包
    }
    
    fun process(data: T): Any? = when {
        data is String -> data.length
        data is Int -> data + 1
        // 漏了覆盖——when不穷举
    }
    
    override fun toString() = BrokenService::class.simpleName  // 引用反射
}
"""

# ========== 路径A：裸父+子 ==========
print("=" * 60)
print("路径A：裸父+子（无指挥官、无检测、无态势感知）")
print("=" * 60)

print("[主进程] 收到文件，直接派给列兵...")
print("[列兵] 开始解析...")
time.sleep(0.05)

# 模拟：列兵遇到不认识的东西直接崩
crashes = [
    "Lexer: T! 平台类型 → 标记为 IDENT，后续误判",
    "Parser: suspend 关键字 → 跳过（不理解协程修饰符）",
    "TypeChecker: when 不穷举 → 未检测到（无诊断）",
]
for c in crashes:
    print(f"  ⚠ {c}")
    
print("[列兵] 解析完成——但不知道漏了什么")
print("[主进程] 收到结果：1个文件、0条诊断、状态=完成")
print()
print("结果：看起来成功了。实际漏了3类问题，无任何警告。")
print("     下次遇到同样文件 → 还是从头跑，不记得。")

# ========== 路径B：分工明确（全默认正常） ==========
print()
print("=" * 60)
print("路径B：分工明确（军师+常规指挥官+标准检测+列兵）")
print("=" * 60)

# 军师扫文件
print("[军师] 扫文件特征...")
features = {
    "文件大小": "542 bytes",
    "包含 suspend": True,
    "包含 when": True,
    "包含泛型<T>": True,
    "包含平台类型 T!": True,
    "风格判断": "正常文件，无地狱标记",
}
for k, v in features.items():
    print(f"  · {k}: {v}")

print("[军师] 选优先级表：STANDARD → 派给常规指挥官")

# 常规指挥官
print("[常规指挥官] 收到任务 → 派列兵 x3 + 标准检测进程旁观")
print("[列兵#1] 解析...")
print("[列兵#2] 解析...")
print("[列兵#3] 解析...")

# 标准检测旁观
print("[标准检测] 旁路观察中...")
findings = [
    "⚠ 发现 T! 平台类型（第5行：input: T!）→ 建议标注为可空类型",
    "⚠ when 分支不穷举（第11行）→ 建议加 else 分支",
    "ℹ 链式安全调用 ?.let → 结构正常，无需处理",
]
for f in findings:
    print(f"  {f}")

# 态势简报
print()
print("[常规指挥官] 生成态势简报：")
print("  ┌─────────────────────────────────┐")
print("  │ 📋 态势简报                      │")
print("  │ 文件: BrokenService.kt (542B)    │")
print("  │ 场景: 正常·协程混合              │")
print("  │ 军队: 列兵×3 + 标准检测          │")
print("  │ 发现: 2个建议、1个信息           │")
print("  │ 严重度: 2/10（低危）             │")
print("  │ 状态: 完成，有诊断               │")
print("  └─────────────────────────────────┘")

print()
print("结果：3处结构问题全捕获。下次同样文件 → 经验缓存命中，跳过重复诊断。")

# ========== 对比总结 ==========
print()
print("=" * 60)
print("对比总结")
print("=" * 60)
print(f"{'':<20} {'裸父+子':<25} {'分工明确（全正常）':<25}")
print("-" * 70)
print(f"{'架构层数':<20} {'2（主进程+列兵）':<25} {'4（主进程+军师+指挥官+检测）':<25}")
print(f"{'文件感知':<20} {'无——不知道是什么文件':<25} {'军师扫特征→选策略':<25}")
print(f"{'诊断输出':<20} {'0条（漏报）':<25} {'3条（T!/when/链式）':<25}")
print(f"{'态势简报':<20} {'无':<25} {'场景+军队+严重度':<25}")
print(f"{'容错':<20} {'崩了就没了':<25} {'旁路观察不拦截，崩了广播接替':<25}")
print(f"{'经验积累':<20} {'无（每次重来）':<25} {'缓存命中→跳过重复诊断':<25}")
print(f"{'扩展性':<20} {'改主进程':<25} {'挂新指挥官/检测，不碰骨架':<25}")
print()
print("裸父+子能跑。分工让你知道跑得怎么样。")
print("不是多了一层——是多了一个「感知→决策→执行」的闭环。")
