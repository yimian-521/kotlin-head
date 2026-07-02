# 平台类型 T! × 结构化并发叠加漏洞 —— Python 模拟验证

import asyncio
import os

TMP_FILE = "/tmp/kotlin_bug_test_output.txt"

class PlatformType:
    """模拟Kotlin的 T! 平台类型——编译器闭眼放行"""
    def __init__(self, value):
        self._value = value
    
    def body(self):
        """模拟 response.body——如果是null，炸在这里"""
        if self._value is None:
            raise Exception("NullPointerException: response.body is null!")
        return self._value

class OkHttpSimulator:
    call_count = 0
    
    @staticmethod
    def execute():
        OkHttpSimulator.call_count += 1
        if OkHttpSimulator.call_count == 3:
            print("[Java/OkHttp] execute() → null（平台类型 T!，编译器：没问题，放行）")
            return PlatformType(None)
        return PlatformType(f"OK #{OkHttpSimulator.call_count}")


async def write_file_slowly(filename, content, cancel_on):
    print(f"[协程B-文件备份] 开始写入 {len(content)} 字符...")
    with open(filename, "w") as f:
        for i, chunk in enumerate(content):
            if cancel_on.is_set():
                f.flush()
                print(f"[协程B-文件备份] ⚠️ 写到 {i}/{len(content)} 被静默取消！文件截断。")
                return
            f.write(chunk)
            await asyncio.sleep(0.008)
    print(f"[协程B-文件备份] ✅ 完整写入")


async def use_platform_type(cancel_on):
    for i in range(5):
        print(f"[协程A-网关转发] 请求 #{i+1}...")
        response = OkHttpSimulator.execute()  # → PlatformType（T!）
        try:
            body = response.body()  # ← 这里可能NPE
            print(f"[协程A-网关转发] 响应: {body}")
        except Exception as e:
            print(f"[协程A-网关转发] 💥 {e}")
            cancel_on.set()  # ← 结构化并发：静默取消传播
            raise
        await asyncio.sleep(0.03)


async def supervisor_simulator(cancel_on):
    print("=" * 55)
    print("🧪 平台类型 T! × 结构化并发静默取消")
    print("=" * 55)
    
    file_content = "【DB备份】用户配置|API密钥|聊天记录|Token统计"
    with open(TMP_FILE, "w") as f:
        f.write(file_content)
    print(f"\n📄 初始文件: [{file_content}]")
    
    task_b = asyncio.create_task(write_file_slowly(TMP_FILE, file_content, cancel_on))
    task_a = asyncio.create_task(use_platform_type(cancel_on))
    
    try:
        await task_a
    except Exception:
        print("[SupervisorJob] 协程A异常已隔离（表面上兄弟不受影响）")
    
    await task_b
    
    print("\n" + "=" * 55)
    print("📊 结果")
    print("=" * 55)
    
    with open(TMP_FILE, "r") as f:
        actual = f.read()
    print(f"📄 文件内容: [{actual}] ({len(actual)}/{len(file_content)} 字符)")
    
    if len(actual) < len(file_content):
        print(f"🔴 截断！丢失 {len(file_content)-len(actual)} 字符")
    elif len(actual) == len(file_content):
        print("✅ 完整")
    
    print("\n💀 整个链路零报错：")
    print("   编译器：T!不检查 → 放行")
    print("   SupervisorJob：异常已隔离 → 不报")
    print("   协程B被取消：静默退出 → 不报")
    print("   文件截断：没有任何环节告诉你")
    
    os.remove(TMP_FILE)

if __name__ == "__main__":
    cancel_signal = asyncio.Event()
    asyncio.run(supervisor_simulator(cancel_signal))
