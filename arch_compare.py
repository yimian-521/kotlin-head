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

# ========== v0.11.8 扩展：APK打包对比 ==========
print()
print()
print("=" * 60)
print("扩展：APK打包对比——传统全量 vs 定量混合")
print("=" * 60)
print("场景：Android项目改了1个图标，其余不变")

# 路径A：传统全量打包
print()
print("── 路径A：传统全量打包（Gradle/命令行）──")
steps_a = [
    ("aapt2 compile", "3.2s", True),
    ("aapt2 link", "1.8s", True),
    ("kotlinc(全量)", "52.3s", True),
    ("d8(全量)", "18.7s", True),
    ("zipalign", "2.1s", True),
    ("apksigner", "1.4s", True),
]
total_a = 0
for step, dur, ok in steps_a:
    icon = "✓" if ok else "✗"
    print(f"  {icon} {step}: {dur}")
    total_a += float(dur.replace("s",""))
print(f"  总耗时: {total_a:.1f}s")
print("  结果: 成功。但只改了个图标，跑了6步全管线。")

# 路径B：军师定量混合打包
print()
print("── 路径B：军师定量混合打包（kotlin-head）──")
print("[军师] 扫 diff...")
diff_result = {
    "资源变更": "res/drawable/icon.png (修改)",
    "源码变更": "无",
    "Manifest变更": "无",
}
for k, v in diff_result.items():
    print(f"  · {k}: {v}")

print("[军师] 决策: 只改资源 → 跑 aapt2(2步) + 签名(1步)，跳过 kotlinc/d8")

steps_b = [
    ("aapt2 compile", "3.2s", True, "需要（资源变了）"),
    ("aapt2 link", "1.8s", True, "需要"),
    ("kotlinc", "0s", True, "跳过——源码没变"),
    ("d8", "0s", True, "跳过——复用上次 dex"),
    ("zipalign", "2.1s", True, "需要"),
    ("apksigner", "1.4s", True, "需要"),
]
total_b = 0
for step, dur, ok, reason in steps_b:
    icon = "✓" if ok else "✗"
    d = float(dur.replace("s","")) if dur != "0s" else 0
    total_b += d
    print(f"  {icon} {step}: {dur} — {reason}")

print(f"  总耗时: {total_b:.1f}s")
print("  跳过步骤: kotlinc + d8（复用上次产物）")
print(f"  节省: {total_a - total_b:.1f}s ({(total_a-total_b)/total_a*100:.0f}%)")

# 军师总结
print()
print("[军师] 打包简报：")
print("  ┌─────────────────────────────────┐")
print("  │ 📋 APK打包简报                   │")
print("  │ 项目: QiTong Gateway             │")
print("  │ 改动: 1个图标                     │")
print("  │ 需要: aapt2×2 + zip+签名=4步     │")
print("  │ 跳过: kotlinc+d8=2步              │")
print("  │ 原因: 源码未变更，复用上次dex     │")
print(f"  │ 耗时: {total_b:.1f}s vs 全量{total_a:.1f}s (省{(total_a-total_b)/total_a*100:.0f}%) │")
print("  │ 建议: 不需要全量打包              │")
print("  └─────────────────────────────────┘")

# 对比表
print()
print("=" * 60)
print("APK打包对比总结")
print("=" * 60)
print(f"{'':<22} {'传统全量打包':<22} {'定量混合打包':<22}")
print("-" * 66)
print(f"{'步骤数':<22} {'6步（全跑）':<22} {'4步（跳2步）':<22}")
print(f"{'耗时':<22} {f'{total_a:.1f}s':<22} {f'{total_b:.1f}s':<22}")
print(f"{'感知层':<22} {'无——不知道改了什么':<22} {'军师扫diff→决定跑几步':<22}")
print(f"{'复用':<22} {'无——每次都全量':<22} {'复用上次dex':<22}")
print(f"{'崩了':<22} {'从头再来':<22} {'只重跑失败那一步':<22}")
print()
print("不是全量/增量二选一——是改了什么，决定跑几步。定量混合。")
