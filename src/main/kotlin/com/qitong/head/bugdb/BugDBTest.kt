package com.qitong.head.bugdb

/**
 * BugDB 种子规则验证集 — kotlin-int+ v0.12.1
 * 每条 val 包含对应规则的 trigger 字符串。
 * BugDB.scan() 扫描此文件源码应命中全部36条种子规则。
 */
object BugDBTest {

    /** KT-0001: 平台类型!!导致NPE → 用?.let */
    val test_KT_0001_trigger = """trigger: val x:String!=java();x!!.length"""

    /** KT-0002: ?.链混用!! → 统一用?. */
    val test_KT_0002_trigger = """trigger: a?.b?.c!!.d"""

    /** KT-0003: 泛型异常捕获 → 捕获具体类型 */
    val test_KT_0003_trigger = """trigger: catch(e:T){}"""

    /** KT-0004: 星投影误用 → 声明类型 */
    val test_KT_0004_trigger = """trigger: val x:List<*>;x.add(1)"""

    /** KT-0005: GlobalScope泄漏 → lifecycleScope */
    val test_KT_0005_trigger = """trigger: GlobalScope.launch{}"""

    /** KT-0006: launch异常不传播 → async+await */
    val test_KT_0006_trigger = """trigger: launch{riskyOp()}"""

    /** KT-0007: 遍历修改集合 → 收集后删 */
    val test_KT_0007_trigger = """trigger: for(x in l){l.remove(x)}"""

    /** KT-0008: 可变集合暴露 → 返回不可变 */
    val test_KT_0008_trigger = """trigger: fun get()=internalList"""

    /** KT-0009: var智能转换失效 → val y=x */
    val test_KT_0009_trigger = """trigger: var x:Any;if(x is S){x.len}"""

    /** KT-0010: when穷举缺失 → 加else */
    val test_KT_0010_trigger = """trigger: when(s){is A->..}"""

    /** KT-0011: Java null未标注 → ?. */
    val test_KT_0011_trigger = """trigger: val s=javaObj.name"""

    /** KT-0012: @JvmStatic缺失 → 加注解 */
    val test_KT_0012_trigger = """trigger: object U{fun f(){}}"""

    /** KT-0013: 非局部return → return@forEach */
    val test_KT_0013_trigger = """trigger: l.forEach{if(it)return}"""

    /** KT-0014: 隐式this歧义 → this@outer */
    val test_KT_0014_trigger = """trigger: apply{name=name}"""

    /** KT-0015: 循环引用序列化 → @Transient */
    val test_KT_0015_trigger = """trigger: A(val b:B);B(val a:A)"""

    /** KT-0016: copy浅复制 → 深复制 */
    val test_KT_0016_trigger = """trigger: data U(val l:MutableList)"""

    /** KT-0017: tailrec非尾递归 → 改写 */
    val test_KT_0017_trigger = """trigger: tailrec f(n)=n*f(n-1)"""

    /** KT-0018: inline过大 → 去inline */
    val test_KT_0018_trigger = """trigger: inline big(){..200行}"""

    /** KT-0019: 共享可变无同步 → AtomicInt */
    val test_KT_0019_trigger = """trigger: var c=0;threads{c++}"""

    /** KT-0020: 死锁风险 → 统一顺序 */
    val test_KT_0020_trigger = """trigger: synchronized(l1){synchronized(l2){}}"""

    /** KT-0021: +拼接字符串 → buildString */
    val test_KT_0021_trigger = """trigger: for(i in 1..100){s+="\${'$'}i"}"""

    /** KT-0022: 不必要装箱 → 避免可空 */
    val test_KT_0022_trigger = """trigger: val x:Int?=42"""

    /** KT-0023: 类型推断选错重载 → 显式标注 */
    val test_KT_0023_trigger = """trigger: f(Int);f(Any);f(42)"""

    /** KT-0024: 重载解析歧义 → 1L */
    val test_KT_0024_trigger = """trigger: f(Int);f(Long);f(1)"""

    /** KT-0025: 硬编码密钥 → 环境变量 */
    val test_KT_0025_trigger = """trigger: val key="sk-xxx""""

    /** KT-0026: 日志泄露 → 脱敏 */
    val test_KT_0026_trigger = """trigger: Log.d("tok",tok)"""

    /** KT-0027: 主线程IO → LaunchedEffect+IO */
    val test_KT_0027_trigger = """trigger: Text(readFile())"""

    /** KT-0028: 重组副作用 → 正确key */
    val test_KT_0028_trigger = """trigger: LaunchedEffect(Unit){load()}"""

    /** KT-0029: values()性能 → enumEntries */
    val test_KT_0029_trigger = """trigger: enum.values().find{}"""

    /** KT-0030: sealed跨文件 → 移到同文件 */
    val test_KT_0030_trigger = """trigger: sealed A File1,B File2"""

    /** KT-0031: @JvmInline缺失 → 加注解 */
    val test_KT_0031_trigger = """trigger: inline class N(val s)"""

    /** KT-0032: by lazy线程安全 → 指定模式 */
    val test_KT_0032_trigger = """trigger: val x by lazy{init()}"""

    /** KT-0033: expect/actual不匹配 → 对齐 */
    val test_KT_0033_trigger = """trigger: expect f():S;actual f():I"""

    /** KT-0034: 平台API未抽象 → expect/actual */
    val test_KT_0034_trigger = """trigger: fun a(){System.load()}"""

    /** KT-0035: 反射访问私有成员 → 提供公开接口 */
    val test_KT_0035_trigger = """trigger: cls.getDeclaredField("secret")"""

    /** KT-0036: 平台特定导入 → expect/actual封装 */
    val test_KT_0036_trigger = """trigger: import java.io.File"""

}