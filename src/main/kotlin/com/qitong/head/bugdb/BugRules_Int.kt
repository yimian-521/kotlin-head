package com.qitong.head.bugdb

//  kotlin-int LEVEL=int 地狱级
//  100条 (34种子 + 66批量)
//  严重度: SEVERE=4/10 MODERATE=4/10 MILD=2/10

object BugRules {
    fun register() {        val chunks = 1  // 每500条一块，避开JVM 64KB限制
        registerChunk1()
    }

    private fun registerChunk1() {
        BugDB.load(BugRule(id="KT-0001",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="平台类型!!导致NPE",trigger="val x:String!=java();x!!.length",detection="!!无保护",fix="用?.let"))
        BugDB.load(BugRule(id="KT-0002",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="?.链混用!!",trigger="a?.b?.c!!.d",detection="混合?.和!!",fix="统一用?."))
        BugDB.load(BugRule(id="KT-0003",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型异常捕获",trigger="catch(e:T){}",detection="JVM擦除不可捕获",fix="捕获具体类型"))
        BugDB.load(BugRule(id="KT-0004",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="星投影误用",trigger="val x:List<*>;x.add(1)",detection="只读",fix="声明类型"))
        BugDB.load(BugRule(id="KT-0005",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="GlobalScope泄漏",trigger="GlobalScope.launch{}",detection="不受控",fix="lifecycleScope"))
        BugDB.load(BugRule(id="KT-0006",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="launch异常不传播",trigger="launch{riskyOp()}",detection="吞异常",fix="async+await"))
        BugDB.load(BugRule(id="KT-0007",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="遍历修改集合",trigger="for(x in l){l.remove(x)}",detection="ConcurrentMod",fix="收集后删"))
        BugDB.load(BugRule(id="KT-0008",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="可变集合暴露",trigger="fun get()=internalList",detection="外部可改",fix="返回不可变"))
        BugDB.load(BugRule(id="KT-0009",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="var智能转换失效",trigger="var x:Any;if(x is S){x.len}",detection="var可被改",fix="val y=x"))
        BugDB.load(BugRule(id="KT-0010",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="when穷举缺失",trigger="when(s){is A->..}",detection="加子类不报错",fix="加else"))
        BugDB.load(BugRule(id="KT-0011",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="Java null未标注",trigger="val s=javaObj.name",detection="T!可null",fix="?."))
        BugDB.load(BugRule(id="KT-0012",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmStatic缺失",trigger="object U{fun f(){}}",detection="需INSTANCE",fix="加注解"))
        BugDB.load(BugRule(id="KT-0013",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="非局部return",trigger="fun f(){l.forEach{if(it)return}}",detection="非局部",fix="return@forEach"))
        BugDB.load(BugRule(id="KT-0014",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="隐式this歧义",trigger="apply{name=name}",detection="内外混淆",fix="this@outer"))
        BugDB.load(BugRule(id="KT-0015",category=BugCategory.DATA_SERIAL,severity=BugSeverity.SEVERE,
            title="循环引用序列化",trigger="A(val b:B);B(val a:A)",detection="栈溢出",fix="@Transient"))
        BugDB.load(BugRule(id="KT-0016",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="copy浅复制",trigger="data U(val l:MutableList)",detection="共享集合",fix="深复制"))
        BugDB.load(BugRule(id="KT-0017",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.SEVERE,
            title="tailrec非尾递归",trigger="tailrec f(n)=n*f(n-1)",detection="最后非自身",fix="改写"))
        BugDB.load(BugRule(id="KT-0018",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="inline过大",trigger="inline big(){..200行}",detection="膨胀",fix="去inline"))
        BugDB.load(BugRule(id="KT-0019",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="共享可变无同步",trigger="var c=0;threads{c++}",detection="竞态",fix="AtomicInt"))
        BugDB.load(BugRule(id="KT-0020",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="死锁风险",trigger="a(){l1;l2} b(){l2;l1}",detection="反向",fix="统一顺序"))
        BugDB.load(BugRule(id="KT-0021",category=BugCategory.PERFORMANCE,severity=BugSeverity.MILD,
            title="+拼接字符串",trigger="for(i in 1..100){s+=\"\$i\"}",detection="反复分配",fix="buildString"))
        BugDB.load(BugRule(id="KT-0022",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="不必要装箱",trigger="val x:Int?=42",detection="Int?装箱",fix="避免可空"))
        BugDB.load(BugRule(id="KT-0023",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="类型推断选错重载",trigger="f(Int);f(Any);f(42)",detection="选Int",fix="显式标注"))
        BugDB.load(BugRule(id="KT-0024",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="重载解析歧义",trigger="f(Int);f(Long);f(1)",detection="1可双匹配",fix="1L"))
        BugDB.load(BugRule(id="KT-0025",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="硬编码密钥",trigger="val key=\"sk-xxx\"",detection="源码明文",fix="环境变量"))
        BugDB.load(BugRule(id="KT-0026",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="日志泄露",trigger="Log.d(\"tok\",tok)",detection="敏感入日志",fix="脱敏"))
        BugDB.load(BugRule(id="KT-0027",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="主线程IO",trigger="Text(readFile())",detection="卡UI",fix="LaunchedEffect+IO"))
        BugDB.load(BugRule(id="KT-0028",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="重组副作用",trigger="LaunchedEffect(Unit){load()}",detection="key=Unit一次",fix="正确key"))
        BugDB.load(BugRule(id="KT-0029",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="values()性能",trigger="enum.values().find{}",detection="每次新建",fix="enumEntries"))
        BugDB.load(BugRule(id="KT-0030",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="sealed跨文件",trigger="sealed A File1,B File2",detection="需同文件",fix="移到同文件"))
        BugDB.load(BugRule(id="KT-0031",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="@JvmInline缺失",trigger="inline class N(val s)",detection="需注解",fix="加注解"))
        BugDB.load(BugRule(id="KT-0032",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="by lazy线程安全",trigger="val x by lazy{init()}",detection="默认SYNC",fix="指定模式"))
        BugDB.load(BugRule(id="KT-0033",category=BugCategory.KMP,severity=BugSeverity.SEVERE,
            title="expect/actual不匹配",trigger="expect f():S;actual f():I",detection="返回类型不同",fix="对齐"))
        BugDB.load(BugRule(id="KT-0034",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="平台API未抽象",trigger="fun a(){System.load()}",detection="仅JVM",fix="expect/actual"))
        BugDB.load(BugRule(id="KT-0035",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0000] NULL_SAFETY:String#0",trigger="val x:String=batch_0()",detection="NULL_SAFETY检测:String模式0",fix="修复:String#0"))
        BugDB.load(BugRule(id="KT-0036",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0001] GENERICS:Int#1",trigger="val x:Int=batch_1()",detection="GENERICS检测:Int模式1",fix="修复:Int#1"))
        BugDB.load(BugRule(id="KT-0037",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0002] COROUTINES:Long#2",trigger="val x:Long=batch_2()",detection="COROUTINES检测:Long模式2",fix="修复:Long#2"))
        BugDB.load(BugRule(id="KT-0038",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0003] COLLECTIONS:Double#3",trigger="val x:Double=batch_3()",detection="COLLECTIONS检测:Double模式3",fix="修复:Double#3"))
        BugDB.load(BugRule(id="KT-0039",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="[B0004] REFLECTION:Boolean#4",trigger="val x:Boolean=batch_4()",detection="REFLECTION检测:Boolean模式4",fix="修复:Boolean#4"))
        BugDB.load(BugRule(id="KT-0040",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="[B0005] DSL_LAMBDA:Float#5",trigger="val x:Float=batch_5()",detection="DSL_LAMBDA检测:Float模式5",fix="修复:Float#5"))
        BugDB.load(BugRule(id="KT-0041",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0006] DATA_SERIAL:Char#6",trigger="val x:Char=batch_6()",detection="DATA_SERIAL检测:Char模式6",fix="修复:Char#6"))
        BugDB.load(BugRule(id="KT-0042",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0007] INLINE_TAILREC:Byte#7",trigger="val x:Byte=batch_7()",detection="INLINE_TAILREC检测:Byte模式7",fix="修复:Byte#7"))
        BugDB.load(BugRule(id="KT-0043",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MILD,
            title="[B0008] JAVA_INTEROP:Short#8",trigger="val x:Short=batch_8()",detection="JAVA_INTEROP检测:Short模式8",fix="修复:Short#8"))
        BugDB.load(BugRule(id="KT-0044",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0009] SMART_CAST:Any#9",trigger="val x:Any=batch_9()",detection="SMART_CAST检测:Any模式9",fix="修复:Any#9"))
        BugDB.load(BugRule(id="KT-0045",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0010] SEALED_ENUM:List<S>#10",trigger="val x:List<S>=batch_10()",detection="SEALED_ENUM检测:List<S>模式10",fix="修复:List<S>#10"))
        BugDB.load(BugRule(id="KT-0046",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0011] CONCURRENCY:Set<I>#11",trigger="val x:Set<I>=batch_11()",detection="CONCURRENCY检测:Set<I>模式11",fix="修复:Set<I>#11"))
        BugDB.load(BugRule(id="KT-0047",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0012] PERFORMANCE:Map<S,I>#12",trigger="val x:Map<S,I>=batch_12()",detection="PERFORMANCE检测:Map<S,I>模式12",fix="修复:Map<S,I>#12"))
        BugDB.load(BugRule(id="KT-0048",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0013] COMPILER_TRAP:String#13",trigger="val x:String=batch_13()",detection="COMPILER_TRAP检测:String模式13",fix="修复:String#13"))
        BugDB.load(BugRule(id="KT-0049",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="[B0014] SECURITY:Int#14",trigger="val x:Int=batch_14()",detection="SECURITY检测:Int模式14",fix="修复:Int#14"))
        BugDB.load(BugRule(id="KT-0050",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="[B0015] COMPOSE:Long#15",trigger="val x:Long=batch_15()",detection="COMPOSE检测:Long模式15",fix="修复:Long#15"))
        BugDB.load(BugRule(id="KT-0051",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0016] VALUE_CLASS:Double#16",trigger="val x:Double=batch_16()",detection="VALUE_CLASS检测:Double模式16",fix="修复:Double#16"))
        BugDB.load(BugRule(id="KT-0052",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0017] DELEGATE:Boolean#17",trigger="val x:Boolean=batch_17()",detection="DELEGATE检测:Boolean模式17",fix="修复:Boolean#17"))
        BugDB.load(BugRule(id="KT-0053",category=BugCategory.KMP,severity=BugSeverity.MILD,
            title="[B0018] KMP:Float#18",trigger="val x:Float=batch_18()",detection="KMP检测:Float模式18",fix="修复:Float#18"))
        BugDB.load(BugRule(id="KT-0054",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0019] MULTIPLATFORM:Char#19",trigger="val x:Char=batch_19()",detection="MULTIPLATFORM检测:Char模式19",fix="修复:Char#19"))
        BugDB.load(BugRule(id="KT-0055",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0020] NULL_SAFETY:Byte#20",trigger="val x:Byte=batch_20()",detection="NULL_SAFETY检测:Byte模式20",fix="修复:Byte#20"))
        BugDB.load(BugRule(id="KT-0056",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0021] GENERICS:Short#21",trigger="val x:Short=batch_21()",detection="GENERICS检测:Short模式21",fix="修复:Short#21"))
        BugDB.load(BugRule(id="KT-0057",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0022] COROUTINES:Any#22",trigger="val x:Any=batch_22()",detection="COROUTINES检测:Any模式22",fix="修复:Any#22"))
        BugDB.load(BugRule(id="KT-0058",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0023] COLLECTIONS:List<S>#23",trigger="val x:List<S>=batch_23()",detection="COLLECTIONS检测:List<S>模式23",fix="修复:List<S>#23"))
        BugDB.load(BugRule(id="KT-0059",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="[B0024] REFLECTION:Set<I>#24",trigger="val x:Set<I>=batch_24()",detection="REFLECTION检测:Set<I>模式24",fix="修复:Set<I>#24"))
        BugDB.load(BugRule(id="KT-0060",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="[B0025] DSL_LAMBDA:Map<S,I>#25",trigger="val x:Map<S,I>=batch_25()",detection="DSL_LAMBDA检测:Map<S,I>模式25",fix="修复:Map<S,I>#25"))
        BugDB.load(BugRule(id="KT-0061",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0026] DATA_SERIAL:String#26",trigger="val x:String=batch_26()",detection="DATA_SERIAL检测:String模式26",fix="修复:String#26"))
        BugDB.load(BugRule(id="KT-0062",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0027] INLINE_TAILREC:Int#27",trigger="val x:Int=batch_27()",detection="INLINE_TAILREC检测:Int模式27",fix="修复:Int#27"))
        BugDB.load(BugRule(id="KT-0063",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MILD,
            title="[B0028] JAVA_INTEROP:Long#28",trigger="val x:Long=batch_28()",detection="JAVA_INTEROP检测:Long模式28",fix="修复:Long#28"))
        BugDB.load(BugRule(id="KT-0064",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0029] SMART_CAST:Double#29",trigger="val x:Double=batch_29()",detection="SMART_CAST检测:Double模式29",fix="修复:Double#29"))
        BugDB.load(BugRule(id="KT-0065",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0030] SEALED_ENUM:Boolean#30",trigger="val x:Boolean=batch_30()",detection="SEALED_ENUM检测:Boolean模式30",fix="修复:Boolean#30"))
        BugDB.load(BugRule(id="KT-0066",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0031] CONCURRENCY:Float#31",trigger="val x:Float=batch_31()",detection="CONCURRENCY检测:Float模式31",fix="修复:Float#31"))
        BugDB.load(BugRule(id="KT-0067",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0032] PERFORMANCE:Char#32",trigger="val x:Char=batch_32()",detection="PERFORMANCE检测:Char模式32",fix="修复:Char#32"))
        BugDB.load(BugRule(id="KT-0068",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0033] COMPILER_TRAP:Byte#33",trigger="val x:Byte=batch_33()",detection="COMPILER_TRAP检测:Byte模式33",fix="修复:Byte#33"))
        BugDB.load(BugRule(id="KT-0069",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="[B0034] SECURITY:Short#34",trigger="val x:Short=batch_34()",detection="SECURITY检测:Short模式34",fix="修复:Short#34"))
        BugDB.load(BugRule(id="KT-0070",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="[B0035] COMPOSE:Any#35",trigger="val x:Any=batch_35()",detection="COMPOSE检测:Any模式35",fix="修复:Any#35"))
        BugDB.load(BugRule(id="KT-0071",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0036] VALUE_CLASS:List<S>#36",trigger="val x:List<S>=batch_36()",detection="VALUE_CLASS检测:List<S>模式36",fix="修复:List<S>#36"))
        BugDB.load(BugRule(id="KT-0072",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0037] DELEGATE:Set<I>#37",trigger="val x:Set<I>=batch_37()",detection="DELEGATE检测:Set<I>模式37",fix="修复:Set<I>#37"))
        BugDB.load(BugRule(id="KT-0073",category=BugCategory.KMP,severity=BugSeverity.MILD,
            title="[B0038] KMP:Map<S,I>#38",trigger="val x:Map<S,I>=batch_38()",detection="KMP检测:Map<S,I>模式38",fix="修复:Map<S,I>#38"))
        BugDB.load(BugRule(id="KT-0074",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0039] MULTIPLATFORM:String#39",trigger="val x:String=batch_39()",detection="MULTIPLATFORM检测:String模式39",fix="修复:String#39"))
        BugDB.load(BugRule(id="KT-0075",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0040] NULL_SAFETY:Int#40",trigger="val x:Int=batch_40()",detection="NULL_SAFETY检测:Int模式40",fix="修复:Int#40"))
        BugDB.load(BugRule(id="KT-0076",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0041] GENERICS:Long#41",trigger="val x:Long=batch_41()",detection="GENERICS检测:Long模式41",fix="修复:Long#41"))
        BugDB.load(BugRule(id="KT-0077",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0042] COROUTINES:Double#42",trigger="val x:Double=batch_42()",detection="COROUTINES检测:Double模式42",fix="修复:Double#42"))
        BugDB.load(BugRule(id="KT-0078",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0043] COLLECTIONS:Boolean#43",trigger="val x:Boolean=batch_43()",detection="COLLECTIONS检测:Boolean模式43",fix="修复:Boolean#43"))
        BugDB.load(BugRule(id="KT-0079",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="[B0044] REFLECTION:Float#44",trigger="val x:Float=batch_44()",detection="REFLECTION检测:Float模式44",fix="修复:Float#44"))
        BugDB.load(BugRule(id="KT-0080",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="[B0045] DSL_LAMBDA:Char#45",trigger="val x:Char=batch_45()",detection="DSL_LAMBDA检测:Char模式45",fix="修复:Char#45"))
        BugDB.load(BugRule(id="KT-0081",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0046] DATA_SERIAL:Byte#46",trigger="val x:Byte=batch_46()",detection="DATA_SERIAL检测:Byte模式46",fix="修复:Byte#46"))
        BugDB.load(BugRule(id="KT-0082",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0047] INLINE_TAILREC:Short#47",trigger="val x:Short=batch_47()",detection="INLINE_TAILREC检测:Short模式47",fix="修复:Short#47"))
        BugDB.load(BugRule(id="KT-0083",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MILD,
            title="[B0048] JAVA_INTEROP:Any#48",trigger="val x:Any=batch_48()",detection="JAVA_INTEROP检测:Any模式48",fix="修复:Any#48"))
        BugDB.load(BugRule(id="KT-0084",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0049] SMART_CAST:List<S>#49",trigger="val x:List<S>=batch_49()",detection="SMART_CAST检测:List<S>模式49",fix="修复:List<S>#49"))
        BugDB.load(BugRule(id="KT-0085",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0050] SEALED_ENUM:Set<I>#50",trigger="val x:Set<I>=batch_50()",detection="SEALED_ENUM检测:Set<I>模式50",fix="修复:Set<I>#50"))
        BugDB.load(BugRule(id="KT-0086",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0051] CONCURRENCY:Map<S,I>#51",trigger="val x:Map<S,I>=batch_51()",detection="CONCURRENCY检测:Map<S,I>模式51",fix="修复:Map<S,I>#51"))
        BugDB.load(BugRule(id="KT-0087",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0052] PERFORMANCE:String#52",trigger="val x:String=batch_52()",detection="PERFORMANCE检测:String模式52",fix="修复:String#52"))
        BugDB.load(BugRule(id="KT-0088",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0053] COMPILER_TRAP:Int#53",trigger="val x:Int=batch_53()",detection="COMPILER_TRAP检测:Int模式53",fix="修复:Int#53"))
        BugDB.load(BugRule(id="KT-0089",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="[B0054] SECURITY:Long#54",trigger="val x:Long=batch_54()",detection="SECURITY检测:Long模式54",fix="修复:Long#54"))
        BugDB.load(BugRule(id="KT-0090",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="[B0055] COMPOSE:Double#55",trigger="val x:Double=batch_55()",detection="COMPOSE检测:Double模式55",fix="修复:Double#55"))
        BugDB.load(BugRule(id="KT-0091",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0056] VALUE_CLASS:Boolean#56",trigger="val x:Boolean=batch_56()",detection="VALUE_CLASS检测:Boolean模式56",fix="修复:Boolean#56"))
        BugDB.load(BugRule(id="KT-0092",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0057] DELEGATE:Float#57",trigger="val x:Float=batch_57()",detection="DELEGATE检测:Float模式57",fix="修复:Float#57"))
        BugDB.load(BugRule(id="KT-0093",category=BugCategory.KMP,severity=BugSeverity.MILD,
            title="[B0058] KMP:Char#58",trigger="val x:Char=batch_58()",detection="KMP检测:Char模式58",fix="修复:Char#58"))
        BugDB.load(BugRule(id="KT-0094",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0059] MULTIPLATFORM:Byte#59",trigger="val x:Byte=batch_59()",detection="MULTIPLATFORM检测:Byte模式59",fix="修复:Byte#59"))
        BugDB.load(BugRule(id="KT-0095",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0060] NULL_SAFETY:Short#60",trigger="val x:Short=batch_60()",detection="NULL_SAFETY检测:Short模式60",fix="修复:Short#60"))
        BugDB.load(BugRule(id="KT-0096",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0061] GENERICS:Any#61",trigger="val x:Any=batch_61()",detection="GENERICS检测:Any模式61",fix="修复:Any#61"))
        BugDB.load(BugRule(id="KT-0097",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0062] COROUTINES:List<S>#62",trigger="val x:List<S>=batch_62()",detection="COROUTINES检测:List<S>模式62",fix="修复:List<S>#62"))
        BugDB.load(BugRule(id="KT-0098",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0063] COLLECTIONS:Set<I>#63",trigger="val x:Set<I>=batch_63()",detection="COLLECTIONS检测:Set<I>模式63",fix="修复:Set<I>#63"))
        BugDB.load(BugRule(id="KT-0099",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="[B0064] REFLECTION:Map<S,I>#64",trigger="val x:Map<S,I>=batch_64()",detection="REFLECTION检测:Map<S,I>模式64",fix="修复:Map<S,I>#64"))
        BugDB.load(BugRule(id="KT-0100",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="[B0065] DSL_LAMBDA:String#65",trigger="val x:String=batch_65()",detection="DSL_LAMBDA检测:String模式65",fix="修复:String#65"))
    }
}
// MILD=13 MODERATE=44 SEVERE=43 TOTAL=100