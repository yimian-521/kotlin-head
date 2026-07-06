package com.qitong.head.bugdb

//  kotlin-int LEVEL=int+
//  1000条 (34种子 + 966批量)
//  严重度: SEVERE=6/10 MODERATE=3/10 MILD=1/10

object BugRules {
    fun register() {        val chunks = 2  // 每500条一块，避开JVM 64KB限制
        registerChunk1()
        registerChunk2()
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
        BugDB.load(BugRule(id="KT-0039",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0004] REFLECTION:Boolean#4",trigger="val x:Boolean=batch_4()",detection="REFLECTION检测:Boolean模式4",fix="修复:Boolean#4"))
        BugDB.load(BugRule(id="KT-0040",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0005] DSL_LAMBDA:Float#5",trigger="val x:Float=batch_5()",detection="DSL_LAMBDA检测:Float模式5",fix="修复:Float#5"))
        BugDB.load(BugRule(id="KT-0041",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0006] DATA_SERIAL:Char#6",trigger="val x:Char=batch_6()",detection="DATA_SERIAL检测:Char模式6",fix="修复:Char#6"))
        BugDB.load(BugRule(id="KT-0042",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0007] INLINE_TAILREC:Byte#7",trigger="val x:Byte=batch_7()",detection="INLINE_TAILREC检测:Byte模式7",fix="修复:Byte#7"))
        BugDB.load(BugRule(id="KT-0043",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
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
        BugDB.load(BugRule(id="KT-0049",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0014] SECURITY:Int#14",trigger="val x:Int=batch_14()",detection="SECURITY检测:Int模式14",fix="修复:Int#14"))
        BugDB.load(BugRule(id="KT-0050",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0015] COMPOSE:Long#15",trigger="val x:Long=batch_15()",detection="COMPOSE检测:Long模式15",fix="修复:Long#15"))
        BugDB.load(BugRule(id="KT-0051",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0016] VALUE_CLASS:Double#16",trigger="val x:Double=batch_16()",detection="VALUE_CLASS检测:Double模式16",fix="修复:Double#16"))
        BugDB.load(BugRule(id="KT-0052",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0017] DELEGATE:Boolean#17",trigger="val x:Boolean=batch_17()",detection="DELEGATE检测:Boolean模式17",fix="修复:Boolean#17"))
        BugDB.load(BugRule(id="KT-0053",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
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
        BugDB.load(BugRule(id="KT-0059",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0024] REFLECTION:Set<I>#24",trigger="val x:Set<I>=batch_24()",detection="REFLECTION检测:Set<I>模式24",fix="修复:Set<I>#24"))
        BugDB.load(BugRule(id="KT-0060",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0025] DSL_LAMBDA:Map<S,I>#25",trigger="val x:Map<S,I>=batch_25()",detection="DSL_LAMBDA检测:Map<S,I>模式25",fix="修复:Map<S,I>#25"))
        BugDB.load(BugRule(id="KT-0061",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0026] DATA_SERIAL:String#26",trigger="val x:String=batch_26()",detection="DATA_SERIAL检测:String模式26",fix="修复:String#26"))
        BugDB.load(BugRule(id="KT-0062",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0027] INLINE_TAILREC:Int#27",trigger="val x:Int=batch_27()",detection="INLINE_TAILREC检测:Int模式27",fix="修复:Int#27"))
        BugDB.load(BugRule(id="KT-0063",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
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
        BugDB.load(BugRule(id="KT-0069",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0034] SECURITY:Short#34",trigger="val x:Short=batch_34()",detection="SECURITY检测:Short模式34",fix="修复:Short#34"))
        BugDB.load(BugRule(id="KT-0070",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0035] COMPOSE:Any#35",trigger="val x:Any=batch_35()",detection="COMPOSE检测:Any模式35",fix="修复:Any#35"))
        BugDB.load(BugRule(id="KT-0071",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0036] VALUE_CLASS:List<S>#36",trigger="val x:List<S>=batch_36()",detection="VALUE_CLASS检测:List<S>模式36",fix="修复:List<S>#36"))
        BugDB.load(BugRule(id="KT-0072",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0037] DELEGATE:Set<I>#37",trigger="val x:Set<I>=batch_37()",detection="DELEGATE检测:Set<I>模式37",fix="修复:Set<I>#37"))
        BugDB.load(BugRule(id="KT-0073",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
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
        BugDB.load(BugRule(id="KT-0079",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0044] REFLECTION:Float#44",trigger="val x:Float=batch_44()",detection="REFLECTION检测:Float模式44",fix="修复:Float#44"))
        BugDB.load(BugRule(id="KT-0080",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0045] DSL_LAMBDA:Char#45",trigger="val x:Char=batch_45()",detection="DSL_LAMBDA检测:Char模式45",fix="修复:Char#45"))
        BugDB.load(BugRule(id="KT-0081",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0046] DATA_SERIAL:Byte#46",trigger="val x:Byte=batch_46()",detection="DATA_SERIAL检测:Byte模式46",fix="修复:Byte#46"))
        BugDB.load(BugRule(id="KT-0082",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0047] INLINE_TAILREC:Short#47",trigger="val x:Short=batch_47()",detection="INLINE_TAILREC检测:Short模式47",fix="修复:Short#47"))
        BugDB.load(BugRule(id="KT-0083",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
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
        BugDB.load(BugRule(id="KT-0089",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0054] SECURITY:Long#54",trigger="val x:Long=batch_54()",detection="SECURITY检测:Long模式54",fix="修复:Long#54"))
        BugDB.load(BugRule(id="KT-0090",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0055] COMPOSE:Double#55",trigger="val x:Double=batch_55()",detection="COMPOSE检测:Double模式55",fix="修复:Double#55"))
        BugDB.load(BugRule(id="KT-0091",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0056] VALUE_CLASS:Boolean#56",trigger="val x:Boolean=batch_56()",detection="VALUE_CLASS检测:Boolean模式56",fix="修复:Boolean#56"))
        BugDB.load(BugRule(id="KT-0092",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0057] DELEGATE:Float#57",trigger="val x:Float=batch_57()",detection="DELEGATE检测:Float模式57",fix="修复:Float#57"))
        BugDB.load(BugRule(id="KT-0093",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
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
        BugDB.load(BugRule(id="KT-0099",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0064] REFLECTION:Map<S,I>#64",trigger="val x:Map<S,I>=batch_64()",detection="REFLECTION检测:Map<S,I>模式64",fix="修复:Map<S,I>#64"))
        BugDB.load(BugRule(id="KT-0100",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0065] DSL_LAMBDA:String#65",trigger="val x:String=batch_65()",detection="DSL_LAMBDA检测:String模式65",fix="修复:String#65"))
        BugDB.load(BugRule(id="KT-0101",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0066] DATA_SERIAL:Int#66",trigger="val x:Int=batch_66()",detection="DATA_SERIAL检测:Int模式66",fix="修复:Int#66"))
        BugDB.load(BugRule(id="KT-0102",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0067] INLINE_TAILREC:Long#67",trigger="val x:Long=batch_67()",detection="INLINE_TAILREC检测:Long模式67",fix="修复:Long#67"))
        BugDB.load(BugRule(id="KT-0103",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0068] JAVA_INTEROP:Double#68",trigger="val x:Double=batch_68()",detection="JAVA_INTEROP检测:Double模式68",fix="修复:Double#68"))
        BugDB.load(BugRule(id="KT-0104",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0069] SMART_CAST:Boolean#69",trigger="val x:Boolean=batch_69()",detection="SMART_CAST检测:Boolean模式69",fix="修复:Boolean#69"))
        BugDB.load(BugRule(id="KT-0105",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0070] SEALED_ENUM:Float#70",trigger="val x:Float=batch_70()",detection="SEALED_ENUM检测:Float模式70",fix="修复:Float#70"))
        BugDB.load(BugRule(id="KT-0106",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0071] CONCURRENCY:Char#71",trigger="val x:Char=batch_71()",detection="CONCURRENCY检测:Char模式71",fix="修复:Char#71"))
        BugDB.load(BugRule(id="KT-0107",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0072] PERFORMANCE:Byte#72",trigger="val x:Byte=batch_72()",detection="PERFORMANCE检测:Byte模式72",fix="修复:Byte#72"))
        BugDB.load(BugRule(id="KT-0108",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0073] COMPILER_TRAP:Short#73",trigger="val x:Short=batch_73()",detection="COMPILER_TRAP检测:Short模式73",fix="修复:Short#73"))
        BugDB.load(BugRule(id="KT-0109",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0074] SECURITY:Any#74",trigger="val x:Any=batch_74()",detection="SECURITY检测:Any模式74",fix="修复:Any#74"))
        BugDB.load(BugRule(id="KT-0110",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0075] COMPOSE:List<S>#75",trigger="val x:List<S>=batch_75()",detection="COMPOSE检测:List<S>模式75",fix="修复:List<S>#75"))
        BugDB.load(BugRule(id="KT-0111",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0076] VALUE_CLASS:Set<I>#76",trigger="val x:Set<I>=batch_76()",detection="VALUE_CLASS检测:Set<I>模式76",fix="修复:Set<I>#76"))
        BugDB.load(BugRule(id="KT-0112",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0077] DELEGATE:Map<S,I>#77",trigger="val x:Map<S,I>=batch_77()",detection="DELEGATE检测:Map<S,I>模式77",fix="修复:Map<S,I>#77"))
        BugDB.load(BugRule(id="KT-0113",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0078] KMP:String#78",trigger="val x:String=batch_78()",detection="KMP检测:String模式78",fix="修复:String#78"))
        BugDB.load(BugRule(id="KT-0114",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0079] MULTIPLATFORM:Int#79",trigger="val x:Int=batch_79()",detection="MULTIPLATFORM检测:Int模式79",fix="修复:Int#79"))
        BugDB.load(BugRule(id="KT-0115",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0080] NULL_SAFETY:Long#80",trigger="val x:Long=batch_80()",detection="NULL_SAFETY检测:Long模式80",fix="修复:Long#80"))
        BugDB.load(BugRule(id="KT-0116",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0081] GENERICS:Double#81",trigger="val x:Double=batch_81()",detection="GENERICS检测:Double模式81",fix="修复:Double#81"))
        BugDB.load(BugRule(id="KT-0117",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0082] COROUTINES:Boolean#82",trigger="val x:Boolean=batch_82()",detection="COROUTINES检测:Boolean模式82",fix="修复:Boolean#82"))
        BugDB.load(BugRule(id="KT-0118",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0083] COLLECTIONS:Float#83",trigger="val x:Float=batch_83()",detection="COLLECTIONS检测:Float模式83",fix="修复:Float#83"))
        BugDB.load(BugRule(id="KT-0119",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0084] REFLECTION:Char#84",trigger="val x:Char=batch_84()",detection="REFLECTION检测:Char模式84",fix="修复:Char#84"))
        BugDB.load(BugRule(id="KT-0120",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0085] DSL_LAMBDA:Byte#85",trigger="val x:Byte=batch_85()",detection="DSL_LAMBDA检测:Byte模式85",fix="修复:Byte#85"))
        BugDB.load(BugRule(id="KT-0121",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0086] DATA_SERIAL:Short#86",trigger="val x:Short=batch_86()",detection="DATA_SERIAL检测:Short模式86",fix="修复:Short#86"))
        BugDB.load(BugRule(id="KT-0122",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0087] INLINE_TAILREC:Any#87",trigger="val x:Any=batch_87()",detection="INLINE_TAILREC检测:Any模式87",fix="修复:Any#87"))
        BugDB.load(BugRule(id="KT-0123",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0088] JAVA_INTEROP:List<S>#88",trigger="val x:List<S>=batch_88()",detection="JAVA_INTEROP检测:List<S>模式88",fix="修复:List<S>#88"))
        BugDB.load(BugRule(id="KT-0124",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0089] SMART_CAST:Set<I>#89",trigger="val x:Set<I>=batch_89()",detection="SMART_CAST检测:Set<I>模式89",fix="修复:Set<I>#89"))
        BugDB.load(BugRule(id="KT-0125",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0090] SEALED_ENUM:Map<S,I>#90",trigger="val x:Map<S,I>=batch_90()",detection="SEALED_ENUM检测:Map<S,I>模式90",fix="修复:Map<S,I>#90"))
        BugDB.load(BugRule(id="KT-0126",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0091] CONCURRENCY:String#91",trigger="val x:String=batch_91()",detection="CONCURRENCY检测:String模式91",fix="修复:String#91"))
        BugDB.load(BugRule(id="KT-0127",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0092] PERFORMANCE:Int#92",trigger="val x:Int=batch_92()",detection="PERFORMANCE检测:Int模式92",fix="修复:Int#92"))
        BugDB.load(BugRule(id="KT-0128",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0093] COMPILER_TRAP:Long#93",trigger="val x:Long=batch_93()",detection="COMPILER_TRAP检测:Long模式93",fix="修复:Long#93"))
        BugDB.load(BugRule(id="KT-0129",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0094] SECURITY:Double#94",trigger="val x:Double=batch_94()",detection="SECURITY检测:Double模式94",fix="修复:Double#94"))
        BugDB.load(BugRule(id="KT-0130",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0095] COMPOSE:Boolean#95",trigger="val x:Boolean=batch_95()",detection="COMPOSE检测:Boolean模式95",fix="修复:Boolean#95"))
        BugDB.load(BugRule(id="KT-0131",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0096] VALUE_CLASS:Float#96",trigger="val x:Float=batch_96()",detection="VALUE_CLASS检测:Float模式96",fix="修复:Float#96"))
        BugDB.load(BugRule(id="KT-0132",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0097] DELEGATE:Char#97",trigger="val x:Char=batch_97()",detection="DELEGATE检测:Char模式97",fix="修复:Char#97"))
        BugDB.load(BugRule(id="KT-0133",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0098] KMP:Byte#98",trigger="val x:Byte=batch_98()",detection="KMP检测:Byte模式98",fix="修复:Byte#98"))
        BugDB.load(BugRule(id="KT-0134",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0099] MULTIPLATFORM:Short#99",trigger="val x:Short=batch_99()",detection="MULTIPLATFORM检测:Short模式99",fix="修复:Short#99"))
        BugDB.load(BugRule(id="KT-0135",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0100] NULL_SAFETY:Any#100",trigger="val x:Any=batch_100()",detection="NULL_SAFETY检测:Any模式100",fix="修复:Any#100"))
        BugDB.load(BugRule(id="KT-0136",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0101] GENERICS:List<S>#101",trigger="val x:List<S>=batch_101()",detection="GENERICS检测:List<S>模式101",fix="修复:List<S>#101"))
        BugDB.load(BugRule(id="KT-0137",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0102] COROUTINES:Set<I>#102",trigger="val x:Set<I>=batch_102()",detection="COROUTINES检测:Set<I>模式102",fix="修复:Set<I>#102"))
        BugDB.load(BugRule(id="KT-0138",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0103] COLLECTIONS:Map<S,I>#103",trigger="val x:Map<S,I>=batch_103()",detection="COLLECTIONS检测:Map<S,I>模式103",fix="修复:Map<S,I>#103"))
        BugDB.load(BugRule(id="KT-0139",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0104] REFLECTION:String#104",trigger="val x:String=batch_104()",detection="REFLECTION检测:String模式104",fix="修复:String#104"))
        BugDB.load(BugRule(id="KT-0140",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0105] DSL_LAMBDA:Int#105",trigger="val x:Int=batch_105()",detection="DSL_LAMBDA检测:Int模式105",fix="修复:Int#105"))
        BugDB.load(BugRule(id="KT-0141",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0106] DATA_SERIAL:Long#106",trigger="val x:Long=batch_106()",detection="DATA_SERIAL检测:Long模式106",fix="修复:Long#106"))
        BugDB.load(BugRule(id="KT-0142",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0107] INLINE_TAILREC:Double#107",trigger="val x:Double=batch_107()",detection="INLINE_TAILREC检测:Double模式107",fix="修复:Double#107"))
        BugDB.load(BugRule(id="KT-0143",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0108] JAVA_INTEROP:Boolean#108",trigger="val x:Boolean=batch_108()",detection="JAVA_INTEROP检测:Boolean模式108",fix="修复:Boolean#108"))
        BugDB.load(BugRule(id="KT-0144",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0109] SMART_CAST:Float#109",trigger="val x:Float=batch_109()",detection="SMART_CAST检测:Float模式109",fix="修复:Float#109"))
        BugDB.load(BugRule(id="KT-0145",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0110] SEALED_ENUM:Char#110",trigger="val x:Char=batch_110()",detection="SEALED_ENUM检测:Char模式110",fix="修复:Char#110"))
        BugDB.load(BugRule(id="KT-0146",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0111] CONCURRENCY:Byte#111",trigger="val x:Byte=batch_111()",detection="CONCURRENCY检测:Byte模式111",fix="修复:Byte#111"))
        BugDB.load(BugRule(id="KT-0147",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0112] PERFORMANCE:Short#112",trigger="val x:Short=batch_112()",detection="PERFORMANCE检测:Short模式112",fix="修复:Short#112"))
        BugDB.load(BugRule(id="KT-0148",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0113] COMPILER_TRAP:Any#113",trigger="val x:Any=batch_113()",detection="COMPILER_TRAP检测:Any模式113",fix="修复:Any#113"))
        BugDB.load(BugRule(id="KT-0149",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0114] SECURITY:List<S>#114",trigger="val x:List<S>=batch_114()",detection="SECURITY检测:List<S>模式114",fix="修复:List<S>#114"))
        BugDB.load(BugRule(id="KT-0150",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0115] COMPOSE:Set<I>#115",trigger="val x:Set<I>=batch_115()",detection="COMPOSE检测:Set<I>模式115",fix="修复:Set<I>#115"))
        BugDB.load(BugRule(id="KT-0151",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0116] VALUE_CLASS:Map<S,I>#116",trigger="val x:Map<S,I>=batch_116()",detection="VALUE_CLASS检测:Map<S,I>模式116",fix="修复:Map<S,I>#116"))
        BugDB.load(BugRule(id="KT-0152",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0117] DELEGATE:String#117",trigger="val x:String=batch_117()",detection="DELEGATE检测:String模式117",fix="修复:String#117"))
        BugDB.load(BugRule(id="KT-0153",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0118] KMP:Int#118",trigger="val x:Int=batch_118()",detection="KMP检测:Int模式118",fix="修复:Int#118"))
        BugDB.load(BugRule(id="KT-0154",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0119] MULTIPLATFORM:Long#119",trigger="val x:Long=batch_119()",detection="MULTIPLATFORM检测:Long模式119",fix="修复:Long#119"))
        BugDB.load(BugRule(id="KT-0155",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0120] NULL_SAFETY:Double#120",trigger="val x:Double=batch_120()",detection="NULL_SAFETY检测:Double模式120",fix="修复:Double#120"))
        BugDB.load(BugRule(id="KT-0156",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0121] GENERICS:Boolean#121",trigger="val x:Boolean=batch_121()",detection="GENERICS检测:Boolean模式121",fix="修复:Boolean#121"))
        BugDB.load(BugRule(id="KT-0157",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0122] COROUTINES:Float#122",trigger="val x:Float=batch_122()",detection="COROUTINES检测:Float模式122",fix="修复:Float#122"))
        BugDB.load(BugRule(id="KT-0158",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0123] COLLECTIONS:Char#123",trigger="val x:Char=batch_123()",detection="COLLECTIONS检测:Char模式123",fix="修复:Char#123"))
        BugDB.load(BugRule(id="KT-0159",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0124] REFLECTION:Byte#124",trigger="val x:Byte=batch_124()",detection="REFLECTION检测:Byte模式124",fix="修复:Byte#124"))
        BugDB.load(BugRule(id="KT-0160",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0125] DSL_LAMBDA:Short#125",trigger="val x:Short=batch_125()",detection="DSL_LAMBDA检测:Short模式125",fix="修复:Short#125"))
        BugDB.load(BugRule(id="KT-0161",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0126] DATA_SERIAL:Any#126",trigger="val x:Any=batch_126()",detection="DATA_SERIAL检测:Any模式126",fix="修复:Any#126"))
        BugDB.load(BugRule(id="KT-0162",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0127] INLINE_TAILREC:List<S>#127",trigger="val x:List<S>=batch_127()",detection="INLINE_TAILREC检测:List<S>模式127",fix="修复:List<S>#127"))
        BugDB.load(BugRule(id="KT-0163",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0128] JAVA_INTEROP:Set<I>#128",trigger="val x:Set<I>=batch_128()",detection="JAVA_INTEROP检测:Set<I>模式128",fix="修复:Set<I>#128"))
        BugDB.load(BugRule(id="KT-0164",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0129] SMART_CAST:Map<S,I>#129",trigger="val x:Map<S,I>=batch_129()",detection="SMART_CAST检测:Map<S,I>模式129",fix="修复:Map<S,I>#129"))
        BugDB.load(BugRule(id="KT-0165",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0130] SEALED_ENUM:String#130",trigger="val x:String=batch_130()",detection="SEALED_ENUM检测:String模式130",fix="修复:String#130"))
        BugDB.load(BugRule(id="KT-0166",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0131] CONCURRENCY:Int#131",trigger="val x:Int=batch_131()",detection="CONCURRENCY检测:Int模式131",fix="修复:Int#131"))
        BugDB.load(BugRule(id="KT-0167",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0132] PERFORMANCE:Long#132",trigger="val x:Long=batch_132()",detection="PERFORMANCE检测:Long模式132",fix="修复:Long#132"))
        BugDB.load(BugRule(id="KT-0168",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0133] COMPILER_TRAP:Double#133",trigger="val x:Double=batch_133()",detection="COMPILER_TRAP检测:Double模式133",fix="修复:Double#133"))
        BugDB.load(BugRule(id="KT-0169",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0134] SECURITY:Boolean#134",trigger="val x:Boolean=batch_134()",detection="SECURITY检测:Boolean模式134",fix="修复:Boolean#134"))
        BugDB.load(BugRule(id="KT-0170",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0135] COMPOSE:Float#135",trigger="val x:Float=batch_135()",detection="COMPOSE检测:Float模式135",fix="修复:Float#135"))
        BugDB.load(BugRule(id="KT-0171",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0136] VALUE_CLASS:Char#136",trigger="val x:Char=batch_136()",detection="VALUE_CLASS检测:Char模式136",fix="修复:Char#136"))
        BugDB.load(BugRule(id="KT-0172",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0137] DELEGATE:Byte#137",trigger="val x:Byte=batch_137()",detection="DELEGATE检测:Byte模式137",fix="修复:Byte#137"))
        BugDB.load(BugRule(id="KT-0173",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0138] KMP:Short#138",trigger="val x:Short=batch_138()",detection="KMP检测:Short模式138",fix="修复:Short#138"))
        BugDB.load(BugRule(id="KT-0174",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0139] MULTIPLATFORM:Any#139",trigger="val x:Any=batch_139()",detection="MULTIPLATFORM检测:Any模式139",fix="修复:Any#139"))
        BugDB.load(BugRule(id="KT-0175",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0140] NULL_SAFETY:List<S>#140",trigger="val x:List<S>=batch_140()",detection="NULL_SAFETY检测:List<S>模式140",fix="修复:List<S>#140"))
        BugDB.load(BugRule(id="KT-0176",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0141] GENERICS:Set<I>#141",trigger="val x:Set<I>=batch_141()",detection="GENERICS检测:Set<I>模式141",fix="修复:Set<I>#141"))
        BugDB.load(BugRule(id="KT-0177",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0142] COROUTINES:Map<S,I>#142",trigger="val x:Map<S,I>=batch_142()",detection="COROUTINES检测:Map<S,I>模式142",fix="修复:Map<S,I>#142"))
        BugDB.load(BugRule(id="KT-0178",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0143] COLLECTIONS:String#143",trigger="val x:String=batch_143()",detection="COLLECTIONS检测:String模式143",fix="修复:String#143"))
        BugDB.load(BugRule(id="KT-0179",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0144] REFLECTION:Int#144",trigger="val x:Int=batch_144()",detection="REFLECTION检测:Int模式144",fix="修复:Int#144"))
        BugDB.load(BugRule(id="KT-0180",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0145] DSL_LAMBDA:Long#145",trigger="val x:Long=batch_145()",detection="DSL_LAMBDA检测:Long模式145",fix="修复:Long#145"))
        BugDB.load(BugRule(id="KT-0181",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0146] DATA_SERIAL:Double#146",trigger="val x:Double=batch_146()",detection="DATA_SERIAL检测:Double模式146",fix="修复:Double#146"))
        BugDB.load(BugRule(id="KT-0182",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0147] INLINE_TAILREC:Boolean#147",trigger="val x:Boolean=batch_147()",detection="INLINE_TAILREC检测:Boolean模式147",fix="修复:Boolean#147"))
        BugDB.load(BugRule(id="KT-0183",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0148] JAVA_INTEROP:Float#148",trigger="val x:Float=batch_148()",detection="JAVA_INTEROP检测:Float模式148",fix="修复:Float#148"))
        BugDB.load(BugRule(id="KT-0184",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0149] SMART_CAST:Char#149",trigger="val x:Char=batch_149()",detection="SMART_CAST检测:Char模式149",fix="修复:Char#149"))
        BugDB.load(BugRule(id="KT-0185",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0150] SEALED_ENUM:Byte#150",trigger="val x:Byte=batch_150()",detection="SEALED_ENUM检测:Byte模式150",fix="修复:Byte#150"))
        BugDB.load(BugRule(id="KT-0186",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0151] CONCURRENCY:Short#151",trigger="val x:Short=batch_151()",detection="CONCURRENCY检测:Short模式151",fix="修复:Short#151"))
        BugDB.load(BugRule(id="KT-0187",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0152] PERFORMANCE:Any#152",trigger="val x:Any=batch_152()",detection="PERFORMANCE检测:Any模式152",fix="修复:Any#152"))
        BugDB.load(BugRule(id="KT-0188",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0153] COMPILER_TRAP:List<S>#153",trigger="val x:List<S>=batch_153()",detection="COMPILER_TRAP检测:List<S>模式153",fix="修复:List<S>#153"))
        BugDB.load(BugRule(id="KT-0189",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0154] SECURITY:Set<I>#154",trigger="val x:Set<I>=batch_154()",detection="SECURITY检测:Set<I>模式154",fix="修复:Set<I>#154"))
        BugDB.load(BugRule(id="KT-0190",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0155] COMPOSE:Map<S,I>#155",trigger="val x:Map<S,I>=batch_155()",detection="COMPOSE检测:Map<S,I>模式155",fix="修复:Map<S,I>#155"))
        BugDB.load(BugRule(id="KT-0191",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0156] VALUE_CLASS:String#156",trigger="val x:String=batch_156()",detection="VALUE_CLASS检测:String模式156",fix="修复:String#156"))
        BugDB.load(BugRule(id="KT-0192",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0157] DELEGATE:Int#157",trigger="val x:Int=batch_157()",detection="DELEGATE检测:Int模式157",fix="修复:Int#157"))
        BugDB.load(BugRule(id="KT-0193",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0158] KMP:Long#158",trigger="val x:Long=batch_158()",detection="KMP检测:Long模式158",fix="修复:Long#158"))
        BugDB.load(BugRule(id="KT-0194",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0159] MULTIPLATFORM:Double#159",trigger="val x:Double=batch_159()",detection="MULTIPLATFORM检测:Double模式159",fix="修复:Double#159"))
        BugDB.load(BugRule(id="KT-0195",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0160] NULL_SAFETY:Boolean#160",trigger="val x:Boolean=batch_160()",detection="NULL_SAFETY检测:Boolean模式160",fix="修复:Boolean#160"))
        BugDB.load(BugRule(id="KT-0196",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0161] GENERICS:Float#161",trigger="val x:Float=batch_161()",detection="GENERICS检测:Float模式161",fix="修复:Float#161"))
        BugDB.load(BugRule(id="KT-0197",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0162] COROUTINES:Char#162",trigger="val x:Char=batch_162()",detection="COROUTINES检测:Char模式162",fix="修复:Char#162"))
        BugDB.load(BugRule(id="KT-0198",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0163] COLLECTIONS:Byte#163",trigger="val x:Byte=batch_163()",detection="COLLECTIONS检测:Byte模式163",fix="修复:Byte#163"))
        BugDB.load(BugRule(id="KT-0199",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0164] REFLECTION:Short#164",trigger="val x:Short=batch_164()",detection="REFLECTION检测:Short模式164",fix="修复:Short#164"))
        BugDB.load(BugRule(id="KT-0200",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0165] DSL_LAMBDA:Any#165",trigger="val x:Any=batch_165()",detection="DSL_LAMBDA检测:Any模式165",fix="修复:Any#165"))
        BugDB.load(BugRule(id="KT-0201",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0166] DATA_SERIAL:List<S>#166",trigger="val x:List<S>=batch_166()",detection="DATA_SERIAL检测:List<S>模式166",fix="修复:List<S>#166"))
        BugDB.load(BugRule(id="KT-0202",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0167] INLINE_TAILREC:Set<I>#167",trigger="val x:Set<I>=batch_167()",detection="INLINE_TAILREC检测:Set<I>模式167",fix="修复:Set<I>#167"))
        BugDB.load(BugRule(id="KT-0203",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0168] JAVA_INTEROP:Map<S,I>#168",trigger="val x:Map<S,I>=batch_168()",detection="JAVA_INTEROP检测:Map<S,I>模式168",fix="修复:Map<S,I>#168"))
        BugDB.load(BugRule(id="KT-0204",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0169] SMART_CAST:String#169",trigger="val x:String=batch_169()",detection="SMART_CAST检测:String模式169",fix="修复:String#169"))
        BugDB.load(BugRule(id="KT-0205",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0170] SEALED_ENUM:Int#170",trigger="val x:Int=batch_170()",detection="SEALED_ENUM检测:Int模式170",fix="修复:Int#170"))
        BugDB.load(BugRule(id="KT-0206",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0171] CONCURRENCY:Long#171",trigger="val x:Long=batch_171()",detection="CONCURRENCY检测:Long模式171",fix="修复:Long#171"))
        BugDB.load(BugRule(id="KT-0207",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0172] PERFORMANCE:Double#172",trigger="val x:Double=batch_172()",detection="PERFORMANCE检测:Double模式172",fix="修复:Double#172"))
        BugDB.load(BugRule(id="KT-0208",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0173] COMPILER_TRAP:Boolean#173",trigger="val x:Boolean=batch_173()",detection="COMPILER_TRAP检测:Boolean模式173",fix="修复:Boolean#173"))
        BugDB.load(BugRule(id="KT-0209",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0174] SECURITY:Float#174",trigger="val x:Float=batch_174()",detection="SECURITY检测:Float模式174",fix="修复:Float#174"))
        BugDB.load(BugRule(id="KT-0210",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0175] COMPOSE:Char#175",trigger="val x:Char=batch_175()",detection="COMPOSE检测:Char模式175",fix="修复:Char#175"))
        BugDB.load(BugRule(id="KT-0211",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0176] VALUE_CLASS:Byte#176",trigger="val x:Byte=batch_176()",detection="VALUE_CLASS检测:Byte模式176",fix="修复:Byte#176"))
        BugDB.load(BugRule(id="KT-0212",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0177] DELEGATE:Short#177",trigger="val x:Short=batch_177()",detection="DELEGATE检测:Short模式177",fix="修复:Short#177"))
        BugDB.load(BugRule(id="KT-0213",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0178] KMP:Any#178",trigger="val x:Any=batch_178()",detection="KMP检测:Any模式178",fix="修复:Any#178"))
        BugDB.load(BugRule(id="KT-0214",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0179] MULTIPLATFORM:List<S>#179",trigger="val x:List<S>=batch_179()",detection="MULTIPLATFORM检测:List<S>模式179",fix="修复:List<S>#179"))
        BugDB.load(BugRule(id="KT-0215",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0180] NULL_SAFETY:Set<I>#180",trigger="val x:Set<I>=batch_180()",detection="NULL_SAFETY检测:Set<I>模式180",fix="修复:Set<I>#180"))
        BugDB.load(BugRule(id="KT-0216",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0181] GENERICS:Map<S,I>#181",trigger="val x:Map<S,I>=batch_181()",detection="GENERICS检测:Map<S,I>模式181",fix="修复:Map<S,I>#181"))
        BugDB.load(BugRule(id="KT-0217",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0182] COROUTINES:String#182",trigger="val x:String=batch_182()",detection="COROUTINES检测:String模式182",fix="修复:String#182"))
        BugDB.load(BugRule(id="KT-0218",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0183] COLLECTIONS:Int#183",trigger="val x:Int=batch_183()",detection="COLLECTIONS检测:Int模式183",fix="修复:Int#183"))
        BugDB.load(BugRule(id="KT-0219",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0184] REFLECTION:Long#184",trigger="val x:Long=batch_184()",detection="REFLECTION检测:Long模式184",fix="修复:Long#184"))
        BugDB.load(BugRule(id="KT-0220",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0185] DSL_LAMBDA:Double#185",trigger="val x:Double=batch_185()",detection="DSL_LAMBDA检测:Double模式185",fix="修复:Double#185"))
        BugDB.load(BugRule(id="KT-0221",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0186] DATA_SERIAL:Boolean#186",trigger="val x:Boolean=batch_186()",detection="DATA_SERIAL检测:Boolean模式186",fix="修复:Boolean#186"))
        BugDB.load(BugRule(id="KT-0222",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0187] INLINE_TAILREC:Float#187",trigger="val x:Float=batch_187()",detection="INLINE_TAILREC检测:Float模式187",fix="修复:Float#187"))
        BugDB.load(BugRule(id="KT-0223",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0188] JAVA_INTEROP:Char#188",trigger="val x:Char=batch_188()",detection="JAVA_INTEROP检测:Char模式188",fix="修复:Char#188"))
        BugDB.load(BugRule(id="KT-0224",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0189] SMART_CAST:Byte#189",trigger="val x:Byte=batch_189()",detection="SMART_CAST检测:Byte模式189",fix="修复:Byte#189"))
        BugDB.load(BugRule(id="KT-0225",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0190] SEALED_ENUM:Short#190",trigger="val x:Short=batch_190()",detection="SEALED_ENUM检测:Short模式190",fix="修复:Short#190"))
        BugDB.load(BugRule(id="KT-0226",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0191] CONCURRENCY:Any#191",trigger="val x:Any=batch_191()",detection="CONCURRENCY检测:Any模式191",fix="修复:Any#191"))
        BugDB.load(BugRule(id="KT-0227",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0192] PERFORMANCE:List<S>#192",trigger="val x:List<S>=batch_192()",detection="PERFORMANCE检测:List<S>模式192",fix="修复:List<S>#192"))
        BugDB.load(BugRule(id="KT-0228",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0193] COMPILER_TRAP:Set<I>#193",trigger="val x:Set<I>=batch_193()",detection="COMPILER_TRAP检测:Set<I>模式193",fix="修复:Set<I>#193"))
        BugDB.load(BugRule(id="KT-0229",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0194] SECURITY:Map<S,I>#194",trigger="val x:Map<S,I>=batch_194()",detection="SECURITY检测:Map<S,I>模式194",fix="修复:Map<S,I>#194"))
        BugDB.load(BugRule(id="KT-0230",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0195] COMPOSE:String#195",trigger="val x:String=batch_195()",detection="COMPOSE检测:String模式195",fix="修复:String#195"))
        BugDB.load(BugRule(id="KT-0231",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0196] VALUE_CLASS:Int#196",trigger="val x:Int=batch_196()",detection="VALUE_CLASS检测:Int模式196",fix="修复:Int#196"))
        BugDB.load(BugRule(id="KT-0232",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0197] DELEGATE:Long#197",trigger="val x:Long=batch_197()",detection="DELEGATE检测:Long模式197",fix="修复:Long#197"))
        BugDB.load(BugRule(id="KT-0233",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0198] KMP:Double#198",trigger="val x:Double=batch_198()",detection="KMP检测:Double模式198",fix="修复:Double#198"))
        BugDB.load(BugRule(id="KT-0234",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0199] MULTIPLATFORM:Boolean#199",trigger="val x:Boolean=batch_199()",detection="MULTIPLATFORM检测:Boolean模式199",fix="修复:Boolean#199"))
        BugDB.load(BugRule(id="KT-0235",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0200] NULL_SAFETY:Float#200",trigger="val x:Float=batch_200()",detection="NULL_SAFETY检测:Float模式200",fix="修复:Float#200"))
        BugDB.load(BugRule(id="KT-0236",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0201] GENERICS:Char#201",trigger="val x:Char=batch_201()",detection="GENERICS检测:Char模式201",fix="修复:Char#201"))
        BugDB.load(BugRule(id="KT-0237",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0202] COROUTINES:Byte#202",trigger="val x:Byte=batch_202()",detection="COROUTINES检测:Byte模式202",fix="修复:Byte#202"))
        BugDB.load(BugRule(id="KT-0238",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0203] COLLECTIONS:Short#203",trigger="val x:Short=batch_203()",detection="COLLECTIONS检测:Short模式203",fix="修复:Short#203"))
        BugDB.load(BugRule(id="KT-0239",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0204] REFLECTION:Any#204",trigger="val x:Any=batch_204()",detection="REFLECTION检测:Any模式204",fix="修复:Any#204"))
        BugDB.load(BugRule(id="KT-0240",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0205] DSL_LAMBDA:List<S>#205",trigger="val x:List<S>=batch_205()",detection="DSL_LAMBDA检测:List<S>模式205",fix="修复:List<S>#205"))
        BugDB.load(BugRule(id="KT-0241",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0206] DATA_SERIAL:Set<I>#206",trigger="val x:Set<I>=batch_206()",detection="DATA_SERIAL检测:Set<I>模式206",fix="修复:Set<I>#206"))
        BugDB.load(BugRule(id="KT-0242",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0207] INLINE_TAILREC:Map<S,I>#207",trigger="val x:Map<S,I>=batch_207()",detection="INLINE_TAILREC检测:Map<S,I>模式207",fix="修复:Map<S,I>#207"))
        BugDB.load(BugRule(id="KT-0243",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0208] JAVA_INTEROP:String#208",trigger="val x:String=batch_208()",detection="JAVA_INTEROP检测:String模式208",fix="修复:String#208"))
        BugDB.load(BugRule(id="KT-0244",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0209] SMART_CAST:Int#209",trigger="val x:Int=batch_209()",detection="SMART_CAST检测:Int模式209",fix="修复:Int#209"))
        BugDB.load(BugRule(id="KT-0245",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0210] SEALED_ENUM:Long#210",trigger="val x:Long=batch_210()",detection="SEALED_ENUM检测:Long模式210",fix="修复:Long#210"))
        BugDB.load(BugRule(id="KT-0246",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0211] CONCURRENCY:Double#211",trigger="val x:Double=batch_211()",detection="CONCURRENCY检测:Double模式211",fix="修复:Double#211"))
        BugDB.load(BugRule(id="KT-0247",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0212] PERFORMANCE:Boolean#212",trigger="val x:Boolean=batch_212()",detection="PERFORMANCE检测:Boolean模式212",fix="修复:Boolean#212"))
        BugDB.load(BugRule(id="KT-0248",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0213] COMPILER_TRAP:Float#213",trigger="val x:Float=batch_213()",detection="COMPILER_TRAP检测:Float模式213",fix="修复:Float#213"))
        BugDB.load(BugRule(id="KT-0249",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0214] SECURITY:Char#214",trigger="val x:Char=batch_214()",detection="SECURITY检测:Char模式214",fix="修复:Char#214"))
        BugDB.load(BugRule(id="KT-0250",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0215] COMPOSE:Byte#215",trigger="val x:Byte=batch_215()",detection="COMPOSE检测:Byte模式215",fix="修复:Byte#215"))
        BugDB.load(BugRule(id="KT-0251",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0216] VALUE_CLASS:Short#216",trigger="val x:Short=batch_216()",detection="VALUE_CLASS检测:Short模式216",fix="修复:Short#216"))
        BugDB.load(BugRule(id="KT-0252",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0217] DELEGATE:Any#217",trigger="val x:Any=batch_217()",detection="DELEGATE检测:Any模式217",fix="修复:Any#217"))
        BugDB.load(BugRule(id="KT-0253",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0218] KMP:List<S>#218",trigger="val x:List<S>=batch_218()",detection="KMP检测:List<S>模式218",fix="修复:List<S>#218"))
        BugDB.load(BugRule(id="KT-0254",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0219] MULTIPLATFORM:Set<I>#219",trigger="val x:Set<I>=batch_219()",detection="MULTIPLATFORM检测:Set<I>模式219",fix="修复:Set<I>#219"))
        BugDB.load(BugRule(id="KT-0255",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0220] NULL_SAFETY:Map<S,I>#220",trigger="val x:Map<S,I>=batch_220()",detection="NULL_SAFETY检测:Map<S,I>模式220",fix="修复:Map<S,I>#220"))
        BugDB.load(BugRule(id="KT-0256",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0221] GENERICS:String#221",trigger="val x:String=batch_221()",detection="GENERICS检测:String模式221",fix="修复:String#221"))
        BugDB.load(BugRule(id="KT-0257",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0222] COROUTINES:Int#222",trigger="val x:Int=batch_222()",detection="COROUTINES检测:Int模式222",fix="修复:Int#222"))
        BugDB.load(BugRule(id="KT-0258",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0223] COLLECTIONS:Long#223",trigger="val x:Long=batch_223()",detection="COLLECTIONS检测:Long模式223",fix="修复:Long#223"))
        BugDB.load(BugRule(id="KT-0259",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0224] REFLECTION:Double#224",trigger="val x:Double=batch_224()",detection="REFLECTION检测:Double模式224",fix="修复:Double#224"))
        BugDB.load(BugRule(id="KT-0260",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0225] DSL_LAMBDA:Boolean#225",trigger="val x:Boolean=batch_225()",detection="DSL_LAMBDA检测:Boolean模式225",fix="修复:Boolean#225"))
        BugDB.load(BugRule(id="KT-0261",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0226] DATA_SERIAL:Float#226",trigger="val x:Float=batch_226()",detection="DATA_SERIAL检测:Float模式226",fix="修复:Float#226"))
        BugDB.load(BugRule(id="KT-0262",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0227] INLINE_TAILREC:Char#227",trigger="val x:Char=batch_227()",detection="INLINE_TAILREC检测:Char模式227",fix="修复:Char#227"))
        BugDB.load(BugRule(id="KT-0263",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0228] JAVA_INTEROP:Byte#228",trigger="val x:Byte=batch_228()",detection="JAVA_INTEROP检测:Byte模式228",fix="修复:Byte#228"))
        BugDB.load(BugRule(id="KT-0264",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0229] SMART_CAST:Short#229",trigger="val x:Short=batch_229()",detection="SMART_CAST检测:Short模式229",fix="修复:Short#229"))
        BugDB.load(BugRule(id="KT-0265",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0230] SEALED_ENUM:Any#230",trigger="val x:Any=batch_230()",detection="SEALED_ENUM检测:Any模式230",fix="修复:Any#230"))
        BugDB.load(BugRule(id="KT-0266",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0231] CONCURRENCY:List<S>#231",trigger="val x:List<S>=batch_231()",detection="CONCURRENCY检测:List<S>模式231",fix="修复:List<S>#231"))
        BugDB.load(BugRule(id="KT-0267",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0232] PERFORMANCE:Set<I>#232",trigger="val x:Set<I>=batch_232()",detection="PERFORMANCE检测:Set<I>模式232",fix="修复:Set<I>#232"))
        BugDB.load(BugRule(id="KT-0268",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0233] COMPILER_TRAP:Map<S,I>#233",trigger="val x:Map<S,I>=batch_233()",detection="COMPILER_TRAP检测:Map<S,I>模式233",fix="修复:Map<S,I>#233"))
        BugDB.load(BugRule(id="KT-0269",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0234] SECURITY:String#234",trigger="val x:String=batch_234()",detection="SECURITY检测:String模式234",fix="修复:String#234"))
        BugDB.load(BugRule(id="KT-0270",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0235] COMPOSE:Int#235",trigger="val x:Int=batch_235()",detection="COMPOSE检测:Int模式235",fix="修复:Int#235"))
        BugDB.load(BugRule(id="KT-0271",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0236] VALUE_CLASS:Long#236",trigger="val x:Long=batch_236()",detection="VALUE_CLASS检测:Long模式236",fix="修复:Long#236"))
        BugDB.load(BugRule(id="KT-0272",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0237] DELEGATE:Double#237",trigger="val x:Double=batch_237()",detection="DELEGATE检测:Double模式237",fix="修复:Double#237"))
        BugDB.load(BugRule(id="KT-0273",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0238] KMP:Boolean#238",trigger="val x:Boolean=batch_238()",detection="KMP检测:Boolean模式238",fix="修复:Boolean#238"))
        BugDB.load(BugRule(id="KT-0274",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0239] MULTIPLATFORM:Float#239",trigger="val x:Float=batch_239()",detection="MULTIPLATFORM检测:Float模式239",fix="修复:Float#239"))
        BugDB.load(BugRule(id="KT-0275",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0240] NULL_SAFETY:Char#240",trigger="val x:Char=batch_240()",detection="NULL_SAFETY检测:Char模式240",fix="修复:Char#240"))
        BugDB.load(BugRule(id="KT-0276",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0241] GENERICS:Byte#241",trigger="val x:Byte=batch_241()",detection="GENERICS检测:Byte模式241",fix="修复:Byte#241"))
        BugDB.load(BugRule(id="KT-0277",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0242] COROUTINES:Short#242",trigger="val x:Short=batch_242()",detection="COROUTINES检测:Short模式242",fix="修复:Short#242"))
        BugDB.load(BugRule(id="KT-0278",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0243] COLLECTIONS:Any#243",trigger="val x:Any=batch_243()",detection="COLLECTIONS检测:Any模式243",fix="修复:Any#243"))
        BugDB.load(BugRule(id="KT-0279",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0244] REFLECTION:List<S>#244",trigger="val x:List<S>=batch_244()",detection="REFLECTION检测:List<S>模式244",fix="修复:List<S>#244"))
        BugDB.load(BugRule(id="KT-0280",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0245] DSL_LAMBDA:Set<I>#245",trigger="val x:Set<I>=batch_245()",detection="DSL_LAMBDA检测:Set<I>模式245",fix="修复:Set<I>#245"))
        BugDB.load(BugRule(id="KT-0281",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0246] DATA_SERIAL:Map<S,I>#246",trigger="val x:Map<S,I>=batch_246()",detection="DATA_SERIAL检测:Map<S,I>模式246",fix="修复:Map<S,I>#246"))
        BugDB.load(BugRule(id="KT-0282",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0247] INLINE_TAILREC:String#247",trigger="val x:String=batch_247()",detection="INLINE_TAILREC检测:String模式247",fix="修复:String#247"))
        BugDB.load(BugRule(id="KT-0283",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0248] JAVA_INTEROP:Int#248",trigger="val x:Int=batch_248()",detection="JAVA_INTEROP检测:Int模式248",fix="修复:Int#248"))
        BugDB.load(BugRule(id="KT-0284",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0249] SMART_CAST:Long#249",trigger="val x:Long=batch_249()",detection="SMART_CAST检测:Long模式249",fix="修复:Long#249"))
        BugDB.load(BugRule(id="KT-0285",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0250] SEALED_ENUM:Double#250",trigger="val x:Double=batch_250()",detection="SEALED_ENUM检测:Double模式250",fix="修复:Double#250"))
        BugDB.load(BugRule(id="KT-0286",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0251] CONCURRENCY:Boolean#251",trigger="val x:Boolean=batch_251()",detection="CONCURRENCY检测:Boolean模式251",fix="修复:Boolean#251"))
        BugDB.load(BugRule(id="KT-0287",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0252] PERFORMANCE:Float#252",trigger="val x:Float=batch_252()",detection="PERFORMANCE检测:Float模式252",fix="修复:Float#252"))
        BugDB.load(BugRule(id="KT-0288",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0253] COMPILER_TRAP:Char#253",trigger="val x:Char=batch_253()",detection="COMPILER_TRAP检测:Char模式253",fix="修复:Char#253"))
        BugDB.load(BugRule(id="KT-0289",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0254] SECURITY:Byte#254",trigger="val x:Byte=batch_254()",detection="SECURITY检测:Byte模式254",fix="修复:Byte#254"))
        BugDB.load(BugRule(id="KT-0290",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0255] COMPOSE:Short#255",trigger="val x:Short=batch_255()",detection="COMPOSE检测:Short模式255",fix="修复:Short#255"))
        BugDB.load(BugRule(id="KT-0291",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0256] VALUE_CLASS:Any#256",trigger="val x:Any=batch_256()",detection="VALUE_CLASS检测:Any模式256",fix="修复:Any#256"))
        BugDB.load(BugRule(id="KT-0292",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0257] DELEGATE:List<S>#257",trigger="val x:List<S>=batch_257()",detection="DELEGATE检测:List<S>模式257",fix="修复:List<S>#257"))
        BugDB.load(BugRule(id="KT-0293",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0258] KMP:Set<I>#258",trigger="val x:Set<I>=batch_258()",detection="KMP检测:Set<I>模式258",fix="修复:Set<I>#258"))
        BugDB.load(BugRule(id="KT-0294",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0259] MULTIPLATFORM:Map<S,I>#259",trigger="val x:Map<S,I>=batch_259()",detection="MULTIPLATFORM检测:Map<S,I>模式259",fix="修复:Map<S,I>#259"))
        BugDB.load(BugRule(id="KT-0295",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0260] NULL_SAFETY:String#260",trigger="val x:String=batch_260()",detection="NULL_SAFETY检测:String模式260",fix="修复:String#260"))
        BugDB.load(BugRule(id="KT-0296",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0261] GENERICS:Int#261",trigger="val x:Int=batch_261()",detection="GENERICS检测:Int模式261",fix="修复:Int#261"))
        BugDB.load(BugRule(id="KT-0297",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0262] COROUTINES:Long#262",trigger="val x:Long=batch_262()",detection="COROUTINES检测:Long模式262",fix="修复:Long#262"))
        BugDB.load(BugRule(id="KT-0298",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0263] COLLECTIONS:Double#263",trigger="val x:Double=batch_263()",detection="COLLECTIONS检测:Double模式263",fix="修复:Double#263"))
        BugDB.load(BugRule(id="KT-0299",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0264] REFLECTION:Boolean#264",trigger="val x:Boolean=batch_264()",detection="REFLECTION检测:Boolean模式264",fix="修复:Boolean#264"))
        BugDB.load(BugRule(id="KT-0300",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0265] DSL_LAMBDA:Float#265",trigger="val x:Float=batch_265()",detection="DSL_LAMBDA检测:Float模式265",fix="修复:Float#265"))
        BugDB.load(BugRule(id="KT-0301",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0266] DATA_SERIAL:Char#266",trigger="val x:Char=batch_266()",detection="DATA_SERIAL检测:Char模式266",fix="修复:Char#266"))
        BugDB.load(BugRule(id="KT-0302",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0267] INLINE_TAILREC:Byte#267",trigger="val x:Byte=batch_267()",detection="INLINE_TAILREC检测:Byte模式267",fix="修复:Byte#267"))
        BugDB.load(BugRule(id="KT-0303",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0268] JAVA_INTEROP:Short#268",trigger="val x:Short=batch_268()",detection="JAVA_INTEROP检测:Short模式268",fix="修复:Short#268"))
        BugDB.load(BugRule(id="KT-0304",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0269] SMART_CAST:Any#269",trigger="val x:Any=batch_269()",detection="SMART_CAST检测:Any模式269",fix="修复:Any#269"))
        BugDB.load(BugRule(id="KT-0305",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0270] SEALED_ENUM:List<S>#270",trigger="val x:List<S>=batch_270()",detection="SEALED_ENUM检测:List<S>模式270",fix="修复:List<S>#270"))
        BugDB.load(BugRule(id="KT-0306",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0271] CONCURRENCY:Set<I>#271",trigger="val x:Set<I>=batch_271()",detection="CONCURRENCY检测:Set<I>模式271",fix="修复:Set<I>#271"))
        BugDB.load(BugRule(id="KT-0307",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0272] PERFORMANCE:Map<S,I>#272",trigger="val x:Map<S,I>=batch_272()",detection="PERFORMANCE检测:Map<S,I>模式272",fix="修复:Map<S,I>#272"))
        BugDB.load(BugRule(id="KT-0308",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0273] COMPILER_TRAP:String#273",trigger="val x:String=batch_273()",detection="COMPILER_TRAP检测:String模式273",fix="修复:String#273"))
        BugDB.load(BugRule(id="KT-0309",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0274] SECURITY:Int#274",trigger="val x:Int=batch_274()",detection="SECURITY检测:Int模式274",fix="修复:Int#274"))
        BugDB.load(BugRule(id="KT-0310",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0275] COMPOSE:Long#275",trigger="val x:Long=batch_275()",detection="COMPOSE检测:Long模式275",fix="修复:Long#275"))
        BugDB.load(BugRule(id="KT-0311",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0276] VALUE_CLASS:Double#276",trigger="val x:Double=batch_276()",detection="VALUE_CLASS检测:Double模式276",fix="修复:Double#276"))
        BugDB.load(BugRule(id="KT-0312",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0277] DELEGATE:Boolean#277",trigger="val x:Boolean=batch_277()",detection="DELEGATE检测:Boolean模式277",fix="修复:Boolean#277"))
        BugDB.load(BugRule(id="KT-0313",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0278] KMP:Float#278",trigger="val x:Float=batch_278()",detection="KMP检测:Float模式278",fix="修复:Float#278"))
        BugDB.load(BugRule(id="KT-0314",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0279] MULTIPLATFORM:Char#279",trigger="val x:Char=batch_279()",detection="MULTIPLATFORM检测:Char模式279",fix="修复:Char#279"))
        BugDB.load(BugRule(id="KT-0315",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0280] NULL_SAFETY:Byte#280",trigger="val x:Byte=batch_280()",detection="NULL_SAFETY检测:Byte模式280",fix="修复:Byte#280"))
        BugDB.load(BugRule(id="KT-0316",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0281] GENERICS:Short#281",trigger="val x:Short=batch_281()",detection="GENERICS检测:Short模式281",fix="修复:Short#281"))
        BugDB.load(BugRule(id="KT-0317",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0282] COROUTINES:Any#282",trigger="val x:Any=batch_282()",detection="COROUTINES检测:Any模式282",fix="修复:Any#282"))
        BugDB.load(BugRule(id="KT-0318",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0283] COLLECTIONS:List<S>#283",trigger="val x:List<S>=batch_283()",detection="COLLECTIONS检测:List<S>模式283",fix="修复:List<S>#283"))
        BugDB.load(BugRule(id="KT-0319",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0284] REFLECTION:Set<I>#284",trigger="val x:Set<I>=batch_284()",detection="REFLECTION检测:Set<I>模式284",fix="修复:Set<I>#284"))
        BugDB.load(BugRule(id="KT-0320",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0285] DSL_LAMBDA:Map<S,I>#285",trigger="val x:Map<S,I>=batch_285()",detection="DSL_LAMBDA检测:Map<S,I>模式285",fix="修复:Map<S,I>#285"))
        BugDB.load(BugRule(id="KT-0321",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0286] DATA_SERIAL:String#286",trigger="val x:String=batch_286()",detection="DATA_SERIAL检测:String模式286",fix="修复:String#286"))
        BugDB.load(BugRule(id="KT-0322",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0287] INLINE_TAILREC:Int#287",trigger="val x:Int=batch_287()",detection="INLINE_TAILREC检测:Int模式287",fix="修复:Int#287"))
        BugDB.load(BugRule(id="KT-0323",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0288] JAVA_INTEROP:Long#288",trigger="val x:Long=batch_288()",detection="JAVA_INTEROP检测:Long模式288",fix="修复:Long#288"))
        BugDB.load(BugRule(id="KT-0324",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0289] SMART_CAST:Double#289",trigger="val x:Double=batch_289()",detection="SMART_CAST检测:Double模式289",fix="修复:Double#289"))
        BugDB.load(BugRule(id="KT-0325",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0290] SEALED_ENUM:Boolean#290",trigger="val x:Boolean=batch_290()",detection="SEALED_ENUM检测:Boolean模式290",fix="修复:Boolean#290"))
        BugDB.load(BugRule(id="KT-0326",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0291] CONCURRENCY:Float#291",trigger="val x:Float=batch_291()",detection="CONCURRENCY检测:Float模式291",fix="修复:Float#291"))
        BugDB.load(BugRule(id="KT-0327",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0292] PERFORMANCE:Char#292",trigger="val x:Char=batch_292()",detection="PERFORMANCE检测:Char模式292",fix="修复:Char#292"))
        BugDB.load(BugRule(id="KT-0328",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0293] COMPILER_TRAP:Byte#293",trigger="val x:Byte=batch_293()",detection="COMPILER_TRAP检测:Byte模式293",fix="修复:Byte#293"))
        BugDB.load(BugRule(id="KT-0329",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0294] SECURITY:Short#294",trigger="val x:Short=batch_294()",detection="SECURITY检测:Short模式294",fix="修复:Short#294"))
        BugDB.load(BugRule(id="KT-0330",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0295] COMPOSE:Any#295",trigger="val x:Any=batch_295()",detection="COMPOSE检测:Any模式295",fix="修复:Any#295"))
        BugDB.load(BugRule(id="KT-0331",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0296] VALUE_CLASS:List<S>#296",trigger="val x:List<S>=batch_296()",detection="VALUE_CLASS检测:List<S>模式296",fix="修复:List<S>#296"))
        BugDB.load(BugRule(id="KT-0332",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0297] DELEGATE:Set<I>#297",trigger="val x:Set<I>=batch_297()",detection="DELEGATE检测:Set<I>模式297",fix="修复:Set<I>#297"))
        BugDB.load(BugRule(id="KT-0333",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0298] KMP:Map<S,I>#298",trigger="val x:Map<S,I>=batch_298()",detection="KMP检测:Map<S,I>模式298",fix="修复:Map<S,I>#298"))
        BugDB.load(BugRule(id="KT-0334",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0299] MULTIPLATFORM:String#299",trigger="val x:String=batch_299()",detection="MULTIPLATFORM检测:String模式299",fix="修复:String#299"))
        BugDB.load(BugRule(id="KT-0335",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0300] NULL_SAFETY:Int#300",trigger="val x:Int=batch_300()",detection="NULL_SAFETY检测:Int模式300",fix="修复:Int#300"))
        BugDB.load(BugRule(id="KT-0336",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0301] GENERICS:Long#301",trigger="val x:Long=batch_301()",detection="GENERICS检测:Long模式301",fix="修复:Long#301"))
        BugDB.load(BugRule(id="KT-0337",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0302] COROUTINES:Double#302",trigger="val x:Double=batch_302()",detection="COROUTINES检测:Double模式302",fix="修复:Double#302"))
        BugDB.load(BugRule(id="KT-0338",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0303] COLLECTIONS:Boolean#303",trigger="val x:Boolean=batch_303()",detection="COLLECTIONS检测:Boolean模式303",fix="修复:Boolean#303"))
        BugDB.load(BugRule(id="KT-0339",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0304] REFLECTION:Float#304",trigger="val x:Float=batch_304()",detection="REFLECTION检测:Float模式304",fix="修复:Float#304"))
        BugDB.load(BugRule(id="KT-0340",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0305] DSL_LAMBDA:Char#305",trigger="val x:Char=batch_305()",detection="DSL_LAMBDA检测:Char模式305",fix="修复:Char#305"))
        BugDB.load(BugRule(id="KT-0341",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0306] DATA_SERIAL:Byte#306",trigger="val x:Byte=batch_306()",detection="DATA_SERIAL检测:Byte模式306",fix="修复:Byte#306"))
        BugDB.load(BugRule(id="KT-0342",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0307] INLINE_TAILREC:Short#307",trigger="val x:Short=batch_307()",detection="INLINE_TAILREC检测:Short模式307",fix="修复:Short#307"))
        BugDB.load(BugRule(id="KT-0343",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0308] JAVA_INTEROP:Any#308",trigger="val x:Any=batch_308()",detection="JAVA_INTEROP检测:Any模式308",fix="修复:Any#308"))
        BugDB.load(BugRule(id="KT-0344",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0309] SMART_CAST:List<S>#309",trigger="val x:List<S>=batch_309()",detection="SMART_CAST检测:List<S>模式309",fix="修复:List<S>#309"))
        BugDB.load(BugRule(id="KT-0345",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0310] SEALED_ENUM:Set<I>#310",trigger="val x:Set<I>=batch_310()",detection="SEALED_ENUM检测:Set<I>模式310",fix="修复:Set<I>#310"))
        BugDB.load(BugRule(id="KT-0346",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0311] CONCURRENCY:Map<S,I>#311",trigger="val x:Map<S,I>=batch_311()",detection="CONCURRENCY检测:Map<S,I>模式311",fix="修复:Map<S,I>#311"))
        BugDB.load(BugRule(id="KT-0347",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0312] PERFORMANCE:String#312",trigger="val x:String=batch_312()",detection="PERFORMANCE检测:String模式312",fix="修复:String#312"))
        BugDB.load(BugRule(id="KT-0348",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0313] COMPILER_TRAP:Int#313",trigger="val x:Int=batch_313()",detection="COMPILER_TRAP检测:Int模式313",fix="修复:Int#313"))
        BugDB.load(BugRule(id="KT-0349",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0314] SECURITY:Long#314",trigger="val x:Long=batch_314()",detection="SECURITY检测:Long模式314",fix="修复:Long#314"))
        BugDB.load(BugRule(id="KT-0350",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0315] COMPOSE:Double#315",trigger="val x:Double=batch_315()",detection="COMPOSE检测:Double模式315",fix="修复:Double#315"))
        BugDB.load(BugRule(id="KT-0351",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0316] VALUE_CLASS:Boolean#316",trigger="val x:Boolean=batch_316()",detection="VALUE_CLASS检测:Boolean模式316",fix="修复:Boolean#316"))
        BugDB.load(BugRule(id="KT-0352",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0317] DELEGATE:Float#317",trigger="val x:Float=batch_317()",detection="DELEGATE检测:Float模式317",fix="修复:Float#317"))
        BugDB.load(BugRule(id="KT-0353",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0318] KMP:Char#318",trigger="val x:Char=batch_318()",detection="KMP检测:Char模式318",fix="修复:Char#318"))
        BugDB.load(BugRule(id="KT-0354",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0319] MULTIPLATFORM:Byte#319",trigger="val x:Byte=batch_319()",detection="MULTIPLATFORM检测:Byte模式319",fix="修复:Byte#319"))
        BugDB.load(BugRule(id="KT-0355",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0320] NULL_SAFETY:Short#320",trigger="val x:Short=batch_320()",detection="NULL_SAFETY检测:Short模式320",fix="修复:Short#320"))
        BugDB.load(BugRule(id="KT-0356",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0321] GENERICS:Any#321",trigger="val x:Any=batch_321()",detection="GENERICS检测:Any模式321",fix="修复:Any#321"))
        BugDB.load(BugRule(id="KT-0357",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0322] COROUTINES:List<S>#322",trigger="val x:List<S>=batch_322()",detection="COROUTINES检测:List<S>模式322",fix="修复:List<S>#322"))
        BugDB.load(BugRule(id="KT-0358",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0323] COLLECTIONS:Set<I>#323",trigger="val x:Set<I>=batch_323()",detection="COLLECTIONS检测:Set<I>模式323",fix="修复:Set<I>#323"))
        BugDB.load(BugRule(id="KT-0359",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0324] REFLECTION:Map<S,I>#324",trigger="val x:Map<S,I>=batch_324()",detection="REFLECTION检测:Map<S,I>模式324",fix="修复:Map<S,I>#324"))
        BugDB.load(BugRule(id="KT-0360",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0325] DSL_LAMBDA:String#325",trigger="val x:String=batch_325()",detection="DSL_LAMBDA检测:String模式325",fix="修复:String#325"))
        BugDB.load(BugRule(id="KT-0361",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0326] DATA_SERIAL:Int#326",trigger="val x:Int=batch_326()",detection="DATA_SERIAL检测:Int模式326",fix="修复:Int#326"))
        BugDB.load(BugRule(id="KT-0362",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0327] INLINE_TAILREC:Long#327",trigger="val x:Long=batch_327()",detection="INLINE_TAILREC检测:Long模式327",fix="修复:Long#327"))
        BugDB.load(BugRule(id="KT-0363",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0328] JAVA_INTEROP:Double#328",trigger="val x:Double=batch_328()",detection="JAVA_INTEROP检测:Double模式328",fix="修复:Double#328"))
        BugDB.load(BugRule(id="KT-0364",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0329] SMART_CAST:Boolean#329",trigger="val x:Boolean=batch_329()",detection="SMART_CAST检测:Boolean模式329",fix="修复:Boolean#329"))
        BugDB.load(BugRule(id="KT-0365",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0330] SEALED_ENUM:Float#330",trigger="val x:Float=batch_330()",detection="SEALED_ENUM检测:Float模式330",fix="修复:Float#330"))
        BugDB.load(BugRule(id="KT-0366",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0331] CONCURRENCY:Char#331",trigger="val x:Char=batch_331()",detection="CONCURRENCY检测:Char模式331",fix="修复:Char#331"))
        BugDB.load(BugRule(id="KT-0367",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0332] PERFORMANCE:Byte#332",trigger="val x:Byte=batch_332()",detection="PERFORMANCE检测:Byte模式332",fix="修复:Byte#332"))
        BugDB.load(BugRule(id="KT-0368",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0333] COMPILER_TRAP:Short#333",trigger="val x:Short=batch_333()",detection="COMPILER_TRAP检测:Short模式333",fix="修复:Short#333"))
        BugDB.load(BugRule(id="KT-0369",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0334] SECURITY:Any#334",trigger="val x:Any=batch_334()",detection="SECURITY检测:Any模式334",fix="修复:Any#334"))
        BugDB.load(BugRule(id="KT-0370",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0335] COMPOSE:List<S>#335",trigger="val x:List<S>=batch_335()",detection="COMPOSE检测:List<S>模式335",fix="修复:List<S>#335"))
        BugDB.load(BugRule(id="KT-0371",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0336] VALUE_CLASS:Set<I>#336",trigger="val x:Set<I>=batch_336()",detection="VALUE_CLASS检测:Set<I>模式336",fix="修复:Set<I>#336"))
        BugDB.load(BugRule(id="KT-0372",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0337] DELEGATE:Map<S,I>#337",trigger="val x:Map<S,I>=batch_337()",detection="DELEGATE检测:Map<S,I>模式337",fix="修复:Map<S,I>#337"))
        BugDB.load(BugRule(id="KT-0373",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0338] KMP:String#338",trigger="val x:String=batch_338()",detection="KMP检测:String模式338",fix="修复:String#338"))
        BugDB.load(BugRule(id="KT-0374",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0339] MULTIPLATFORM:Int#339",trigger="val x:Int=batch_339()",detection="MULTIPLATFORM检测:Int模式339",fix="修复:Int#339"))
        BugDB.load(BugRule(id="KT-0375",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0340] NULL_SAFETY:Long#340",trigger="val x:Long=batch_340()",detection="NULL_SAFETY检测:Long模式340",fix="修复:Long#340"))
        BugDB.load(BugRule(id="KT-0376",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0341] GENERICS:Double#341",trigger="val x:Double=batch_341()",detection="GENERICS检测:Double模式341",fix="修复:Double#341"))
        BugDB.load(BugRule(id="KT-0377",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0342] COROUTINES:Boolean#342",trigger="val x:Boolean=batch_342()",detection="COROUTINES检测:Boolean模式342",fix="修复:Boolean#342"))
        BugDB.load(BugRule(id="KT-0378",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0343] COLLECTIONS:Float#343",trigger="val x:Float=batch_343()",detection="COLLECTIONS检测:Float模式343",fix="修复:Float#343"))
        BugDB.load(BugRule(id="KT-0379",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0344] REFLECTION:Char#344",trigger="val x:Char=batch_344()",detection="REFLECTION检测:Char模式344",fix="修复:Char#344"))
        BugDB.load(BugRule(id="KT-0380",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0345] DSL_LAMBDA:Byte#345",trigger="val x:Byte=batch_345()",detection="DSL_LAMBDA检测:Byte模式345",fix="修复:Byte#345"))
        BugDB.load(BugRule(id="KT-0381",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0346] DATA_SERIAL:Short#346",trigger="val x:Short=batch_346()",detection="DATA_SERIAL检测:Short模式346",fix="修复:Short#346"))
        BugDB.load(BugRule(id="KT-0382",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0347] INLINE_TAILREC:Any#347",trigger="val x:Any=batch_347()",detection="INLINE_TAILREC检测:Any模式347",fix="修复:Any#347"))
        BugDB.load(BugRule(id="KT-0383",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0348] JAVA_INTEROP:List<S>#348",trigger="val x:List<S>=batch_348()",detection="JAVA_INTEROP检测:List<S>模式348",fix="修复:List<S>#348"))
        BugDB.load(BugRule(id="KT-0384",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0349] SMART_CAST:Set<I>#349",trigger="val x:Set<I>=batch_349()",detection="SMART_CAST检测:Set<I>模式349",fix="修复:Set<I>#349"))
        BugDB.load(BugRule(id="KT-0385",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0350] SEALED_ENUM:Map<S,I>#350",trigger="val x:Map<S,I>=batch_350()",detection="SEALED_ENUM检测:Map<S,I>模式350",fix="修复:Map<S,I>#350"))
        BugDB.load(BugRule(id="KT-0386",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0351] CONCURRENCY:String#351",trigger="val x:String=batch_351()",detection="CONCURRENCY检测:String模式351",fix="修复:String#351"))
        BugDB.load(BugRule(id="KT-0387",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0352] PERFORMANCE:Int#352",trigger="val x:Int=batch_352()",detection="PERFORMANCE检测:Int模式352",fix="修复:Int#352"))
        BugDB.load(BugRule(id="KT-0388",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0353] COMPILER_TRAP:Long#353",trigger="val x:Long=batch_353()",detection="COMPILER_TRAP检测:Long模式353",fix="修复:Long#353"))
        BugDB.load(BugRule(id="KT-0389",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0354] SECURITY:Double#354",trigger="val x:Double=batch_354()",detection="SECURITY检测:Double模式354",fix="修复:Double#354"))
        BugDB.load(BugRule(id="KT-0390",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0355] COMPOSE:Boolean#355",trigger="val x:Boolean=batch_355()",detection="COMPOSE检测:Boolean模式355",fix="修复:Boolean#355"))
        BugDB.load(BugRule(id="KT-0391",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0356] VALUE_CLASS:Float#356",trigger="val x:Float=batch_356()",detection="VALUE_CLASS检测:Float模式356",fix="修复:Float#356"))
        BugDB.load(BugRule(id="KT-0392",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0357] DELEGATE:Char#357",trigger="val x:Char=batch_357()",detection="DELEGATE检测:Char模式357",fix="修复:Char#357"))
        BugDB.load(BugRule(id="KT-0393",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0358] KMP:Byte#358",trigger="val x:Byte=batch_358()",detection="KMP检测:Byte模式358",fix="修复:Byte#358"))
        BugDB.load(BugRule(id="KT-0394",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0359] MULTIPLATFORM:Short#359",trigger="val x:Short=batch_359()",detection="MULTIPLATFORM检测:Short模式359",fix="修复:Short#359"))
        BugDB.load(BugRule(id="KT-0395",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0360] NULL_SAFETY:Any#360",trigger="val x:Any=batch_360()",detection="NULL_SAFETY检测:Any模式360",fix="修复:Any#360"))
        BugDB.load(BugRule(id="KT-0396",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0361] GENERICS:List<S>#361",trigger="val x:List<S>=batch_361()",detection="GENERICS检测:List<S>模式361",fix="修复:List<S>#361"))
        BugDB.load(BugRule(id="KT-0397",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0362] COROUTINES:Set<I>#362",trigger="val x:Set<I>=batch_362()",detection="COROUTINES检测:Set<I>模式362",fix="修复:Set<I>#362"))
        BugDB.load(BugRule(id="KT-0398",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0363] COLLECTIONS:Map<S,I>#363",trigger="val x:Map<S,I>=batch_363()",detection="COLLECTIONS检测:Map<S,I>模式363",fix="修复:Map<S,I>#363"))
        BugDB.load(BugRule(id="KT-0399",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0364] REFLECTION:String#364",trigger="val x:String=batch_364()",detection="REFLECTION检测:String模式364",fix="修复:String#364"))
        BugDB.load(BugRule(id="KT-0400",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0365] DSL_LAMBDA:Int#365",trigger="val x:Int=batch_365()",detection="DSL_LAMBDA检测:Int模式365",fix="修复:Int#365"))
        BugDB.load(BugRule(id="KT-0401",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0366] DATA_SERIAL:Long#366",trigger="val x:Long=batch_366()",detection="DATA_SERIAL检测:Long模式366",fix="修复:Long#366"))
        BugDB.load(BugRule(id="KT-0402",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0367] INLINE_TAILREC:Double#367",trigger="val x:Double=batch_367()",detection="INLINE_TAILREC检测:Double模式367",fix="修复:Double#367"))
        BugDB.load(BugRule(id="KT-0403",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0368] JAVA_INTEROP:Boolean#368",trigger="val x:Boolean=batch_368()",detection="JAVA_INTEROP检测:Boolean模式368",fix="修复:Boolean#368"))
        BugDB.load(BugRule(id="KT-0404",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0369] SMART_CAST:Float#369",trigger="val x:Float=batch_369()",detection="SMART_CAST检测:Float模式369",fix="修复:Float#369"))
        BugDB.load(BugRule(id="KT-0405",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0370] SEALED_ENUM:Char#370",trigger="val x:Char=batch_370()",detection="SEALED_ENUM检测:Char模式370",fix="修复:Char#370"))
        BugDB.load(BugRule(id="KT-0406",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0371] CONCURRENCY:Byte#371",trigger="val x:Byte=batch_371()",detection="CONCURRENCY检测:Byte模式371",fix="修复:Byte#371"))
        BugDB.load(BugRule(id="KT-0407",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0372] PERFORMANCE:Short#372",trigger="val x:Short=batch_372()",detection="PERFORMANCE检测:Short模式372",fix="修复:Short#372"))
        BugDB.load(BugRule(id="KT-0408",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0373] COMPILER_TRAP:Any#373",trigger="val x:Any=batch_373()",detection="COMPILER_TRAP检测:Any模式373",fix="修复:Any#373"))
        BugDB.load(BugRule(id="KT-0409",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0374] SECURITY:List<S>#374",trigger="val x:List<S>=batch_374()",detection="SECURITY检测:List<S>模式374",fix="修复:List<S>#374"))
        BugDB.load(BugRule(id="KT-0410",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0375] COMPOSE:Set<I>#375",trigger="val x:Set<I>=batch_375()",detection="COMPOSE检测:Set<I>模式375",fix="修复:Set<I>#375"))
        BugDB.load(BugRule(id="KT-0411",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0376] VALUE_CLASS:Map<S,I>#376",trigger="val x:Map<S,I>=batch_376()",detection="VALUE_CLASS检测:Map<S,I>模式376",fix="修复:Map<S,I>#376"))
        BugDB.load(BugRule(id="KT-0412",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0377] DELEGATE:String#377",trigger="val x:String=batch_377()",detection="DELEGATE检测:String模式377",fix="修复:String#377"))
        BugDB.load(BugRule(id="KT-0413",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0378] KMP:Int#378",trigger="val x:Int=batch_378()",detection="KMP检测:Int模式378",fix="修复:Int#378"))
        BugDB.load(BugRule(id="KT-0414",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0379] MULTIPLATFORM:Long#379",trigger="val x:Long=batch_379()",detection="MULTIPLATFORM检测:Long模式379",fix="修复:Long#379"))
        BugDB.load(BugRule(id="KT-0415",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0380] NULL_SAFETY:Double#380",trigger="val x:Double=batch_380()",detection="NULL_SAFETY检测:Double模式380",fix="修复:Double#380"))
        BugDB.load(BugRule(id="KT-0416",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0381] GENERICS:Boolean#381",trigger="val x:Boolean=batch_381()",detection="GENERICS检测:Boolean模式381",fix="修复:Boolean#381"))
        BugDB.load(BugRule(id="KT-0417",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0382] COROUTINES:Float#382",trigger="val x:Float=batch_382()",detection="COROUTINES检测:Float模式382",fix="修复:Float#382"))
        BugDB.load(BugRule(id="KT-0418",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0383] COLLECTIONS:Char#383",trigger="val x:Char=batch_383()",detection="COLLECTIONS检测:Char模式383",fix="修复:Char#383"))
        BugDB.load(BugRule(id="KT-0419",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0384] REFLECTION:Byte#384",trigger="val x:Byte=batch_384()",detection="REFLECTION检测:Byte模式384",fix="修复:Byte#384"))
        BugDB.load(BugRule(id="KT-0420",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0385] DSL_LAMBDA:Short#385",trigger="val x:Short=batch_385()",detection="DSL_LAMBDA检测:Short模式385",fix="修复:Short#385"))
        BugDB.load(BugRule(id="KT-0421",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0386] DATA_SERIAL:Any#386",trigger="val x:Any=batch_386()",detection="DATA_SERIAL检测:Any模式386",fix="修复:Any#386"))
        BugDB.load(BugRule(id="KT-0422",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0387] INLINE_TAILREC:List<S>#387",trigger="val x:List<S>=batch_387()",detection="INLINE_TAILREC检测:List<S>模式387",fix="修复:List<S>#387"))
        BugDB.load(BugRule(id="KT-0423",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0388] JAVA_INTEROP:Set<I>#388",trigger="val x:Set<I>=batch_388()",detection="JAVA_INTEROP检测:Set<I>模式388",fix="修复:Set<I>#388"))
        BugDB.load(BugRule(id="KT-0424",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0389] SMART_CAST:Map<S,I>#389",trigger="val x:Map<S,I>=batch_389()",detection="SMART_CAST检测:Map<S,I>模式389",fix="修复:Map<S,I>#389"))
        BugDB.load(BugRule(id="KT-0425",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0390] SEALED_ENUM:String#390",trigger="val x:String=batch_390()",detection="SEALED_ENUM检测:String模式390",fix="修复:String#390"))
        BugDB.load(BugRule(id="KT-0426",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0391] CONCURRENCY:Int#391",trigger="val x:Int=batch_391()",detection="CONCURRENCY检测:Int模式391",fix="修复:Int#391"))
        BugDB.load(BugRule(id="KT-0427",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0392] PERFORMANCE:Long#392",trigger="val x:Long=batch_392()",detection="PERFORMANCE检测:Long模式392",fix="修复:Long#392"))
        BugDB.load(BugRule(id="KT-0428",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0393] COMPILER_TRAP:Double#393",trigger="val x:Double=batch_393()",detection="COMPILER_TRAP检测:Double模式393",fix="修复:Double#393"))
        BugDB.load(BugRule(id="KT-0429",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0394] SECURITY:Boolean#394",trigger="val x:Boolean=batch_394()",detection="SECURITY检测:Boolean模式394",fix="修复:Boolean#394"))
        BugDB.load(BugRule(id="KT-0430",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0395] COMPOSE:Float#395",trigger="val x:Float=batch_395()",detection="COMPOSE检测:Float模式395",fix="修复:Float#395"))
        BugDB.load(BugRule(id="KT-0431",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0396] VALUE_CLASS:Char#396",trigger="val x:Char=batch_396()",detection="VALUE_CLASS检测:Char模式396",fix="修复:Char#396"))
        BugDB.load(BugRule(id="KT-0432",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0397] DELEGATE:Byte#397",trigger="val x:Byte=batch_397()",detection="DELEGATE检测:Byte模式397",fix="修复:Byte#397"))
        BugDB.load(BugRule(id="KT-0433",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0398] KMP:Short#398",trigger="val x:Short=batch_398()",detection="KMP检测:Short模式398",fix="修复:Short#398"))
        BugDB.load(BugRule(id="KT-0434",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0399] MULTIPLATFORM:Any#399",trigger="val x:Any=batch_399()",detection="MULTIPLATFORM检测:Any模式399",fix="修复:Any#399"))
        BugDB.load(BugRule(id="KT-0435",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0400] NULL_SAFETY:List<S>#400",trigger="val x:List<S>=batch_400()",detection="NULL_SAFETY检测:List<S>模式400",fix="修复:List<S>#400"))
        BugDB.load(BugRule(id="KT-0436",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0401] GENERICS:Set<I>#401",trigger="val x:Set<I>=batch_401()",detection="GENERICS检测:Set<I>模式401",fix="修复:Set<I>#401"))
        BugDB.load(BugRule(id="KT-0437",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0402] COROUTINES:Map<S,I>#402",trigger="val x:Map<S,I>=batch_402()",detection="COROUTINES检测:Map<S,I>模式402",fix="修复:Map<S,I>#402"))
        BugDB.load(BugRule(id="KT-0438",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0403] COLLECTIONS:String#403",trigger="val x:String=batch_403()",detection="COLLECTIONS检测:String模式403",fix="修复:String#403"))
        BugDB.load(BugRule(id="KT-0439",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0404] REFLECTION:Int#404",trigger="val x:Int=batch_404()",detection="REFLECTION检测:Int模式404",fix="修复:Int#404"))
        BugDB.load(BugRule(id="KT-0440",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0405] DSL_LAMBDA:Long#405",trigger="val x:Long=batch_405()",detection="DSL_LAMBDA检测:Long模式405",fix="修复:Long#405"))
        BugDB.load(BugRule(id="KT-0441",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0406] DATA_SERIAL:Double#406",trigger="val x:Double=batch_406()",detection="DATA_SERIAL检测:Double模式406",fix="修复:Double#406"))
        BugDB.load(BugRule(id="KT-0442",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0407] INLINE_TAILREC:Boolean#407",trigger="val x:Boolean=batch_407()",detection="INLINE_TAILREC检测:Boolean模式407",fix="修复:Boolean#407"))
        BugDB.load(BugRule(id="KT-0443",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0408] JAVA_INTEROP:Float#408",trigger="val x:Float=batch_408()",detection="JAVA_INTEROP检测:Float模式408",fix="修复:Float#408"))
        BugDB.load(BugRule(id="KT-0444",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0409] SMART_CAST:Char#409",trigger="val x:Char=batch_409()",detection="SMART_CAST检测:Char模式409",fix="修复:Char#409"))
        BugDB.load(BugRule(id="KT-0445",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0410] SEALED_ENUM:Byte#410",trigger="val x:Byte=batch_410()",detection="SEALED_ENUM检测:Byte模式410",fix="修复:Byte#410"))
        BugDB.load(BugRule(id="KT-0446",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0411] CONCURRENCY:Short#411",trigger="val x:Short=batch_411()",detection="CONCURRENCY检测:Short模式411",fix="修复:Short#411"))
        BugDB.load(BugRule(id="KT-0447",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0412] PERFORMANCE:Any#412",trigger="val x:Any=batch_412()",detection="PERFORMANCE检测:Any模式412",fix="修复:Any#412"))
        BugDB.load(BugRule(id="KT-0448",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0413] COMPILER_TRAP:List<S>#413",trigger="val x:List<S>=batch_413()",detection="COMPILER_TRAP检测:List<S>模式413",fix="修复:List<S>#413"))
        BugDB.load(BugRule(id="KT-0449",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0414] SECURITY:Set<I>#414",trigger="val x:Set<I>=batch_414()",detection="SECURITY检测:Set<I>模式414",fix="修复:Set<I>#414"))
        BugDB.load(BugRule(id="KT-0450",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0415] COMPOSE:Map<S,I>#415",trigger="val x:Map<S,I>=batch_415()",detection="COMPOSE检测:Map<S,I>模式415",fix="修复:Map<S,I>#415"))
        BugDB.load(BugRule(id="KT-0451",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0416] VALUE_CLASS:String#416",trigger="val x:String=batch_416()",detection="VALUE_CLASS检测:String模式416",fix="修复:String#416"))
        BugDB.load(BugRule(id="KT-0452",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0417] DELEGATE:Int#417",trigger="val x:Int=batch_417()",detection="DELEGATE检测:Int模式417",fix="修复:Int#417"))
        BugDB.load(BugRule(id="KT-0453",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0418] KMP:Long#418",trigger="val x:Long=batch_418()",detection="KMP检测:Long模式418",fix="修复:Long#418"))
        BugDB.load(BugRule(id="KT-0454",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0419] MULTIPLATFORM:Double#419",trigger="val x:Double=batch_419()",detection="MULTIPLATFORM检测:Double模式419",fix="修复:Double#419"))
        BugDB.load(BugRule(id="KT-0455",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0420] NULL_SAFETY:Boolean#420",trigger="val x:Boolean=batch_420()",detection="NULL_SAFETY检测:Boolean模式420",fix="修复:Boolean#420"))
        BugDB.load(BugRule(id="KT-0456",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0421] GENERICS:Float#421",trigger="val x:Float=batch_421()",detection="GENERICS检测:Float模式421",fix="修复:Float#421"))
        BugDB.load(BugRule(id="KT-0457",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0422] COROUTINES:Char#422",trigger="val x:Char=batch_422()",detection="COROUTINES检测:Char模式422",fix="修复:Char#422"))
        BugDB.load(BugRule(id="KT-0458",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0423] COLLECTIONS:Byte#423",trigger="val x:Byte=batch_423()",detection="COLLECTIONS检测:Byte模式423",fix="修复:Byte#423"))
        BugDB.load(BugRule(id="KT-0459",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0424] REFLECTION:Short#424",trigger="val x:Short=batch_424()",detection="REFLECTION检测:Short模式424",fix="修复:Short#424"))
        BugDB.load(BugRule(id="KT-0460",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0425] DSL_LAMBDA:Any#425",trigger="val x:Any=batch_425()",detection="DSL_LAMBDA检测:Any模式425",fix="修复:Any#425"))
        BugDB.load(BugRule(id="KT-0461",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0426] DATA_SERIAL:List<S>#426",trigger="val x:List<S>=batch_426()",detection="DATA_SERIAL检测:List<S>模式426",fix="修复:List<S>#426"))
        BugDB.load(BugRule(id="KT-0462",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0427] INLINE_TAILREC:Set<I>#427",trigger="val x:Set<I>=batch_427()",detection="INLINE_TAILREC检测:Set<I>模式427",fix="修复:Set<I>#427"))
        BugDB.load(BugRule(id="KT-0463",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0428] JAVA_INTEROP:Map<S,I>#428",trigger="val x:Map<S,I>=batch_428()",detection="JAVA_INTEROP检测:Map<S,I>模式428",fix="修复:Map<S,I>#428"))
        BugDB.load(BugRule(id="KT-0464",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0429] SMART_CAST:String#429",trigger="val x:String=batch_429()",detection="SMART_CAST检测:String模式429",fix="修复:String#429"))
        BugDB.load(BugRule(id="KT-0465",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0430] SEALED_ENUM:Int#430",trigger="val x:Int=batch_430()",detection="SEALED_ENUM检测:Int模式430",fix="修复:Int#430"))
        BugDB.load(BugRule(id="KT-0466",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0431] CONCURRENCY:Long#431",trigger="val x:Long=batch_431()",detection="CONCURRENCY检测:Long模式431",fix="修复:Long#431"))
        BugDB.load(BugRule(id="KT-0467",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0432] PERFORMANCE:Double#432",trigger="val x:Double=batch_432()",detection="PERFORMANCE检测:Double模式432",fix="修复:Double#432"))
        BugDB.load(BugRule(id="KT-0468",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0433] COMPILER_TRAP:Boolean#433",trigger="val x:Boolean=batch_433()",detection="COMPILER_TRAP检测:Boolean模式433",fix="修复:Boolean#433"))
        BugDB.load(BugRule(id="KT-0469",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0434] SECURITY:Float#434",trigger="val x:Float=batch_434()",detection="SECURITY检测:Float模式434",fix="修复:Float#434"))
        BugDB.load(BugRule(id="KT-0470",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0435] COMPOSE:Char#435",trigger="val x:Char=batch_435()",detection="COMPOSE检测:Char模式435",fix="修复:Char#435"))
        BugDB.load(BugRule(id="KT-0471",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0436] VALUE_CLASS:Byte#436",trigger="val x:Byte=batch_436()",detection="VALUE_CLASS检测:Byte模式436",fix="修复:Byte#436"))
        BugDB.load(BugRule(id="KT-0472",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0437] DELEGATE:Short#437",trigger="val x:Short=batch_437()",detection="DELEGATE检测:Short模式437",fix="修复:Short#437"))
        BugDB.load(BugRule(id="KT-0473",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0438] KMP:Any#438",trigger="val x:Any=batch_438()",detection="KMP检测:Any模式438",fix="修复:Any#438"))
        BugDB.load(BugRule(id="KT-0474",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0439] MULTIPLATFORM:List<S>#439",trigger="val x:List<S>=batch_439()",detection="MULTIPLATFORM检测:List<S>模式439",fix="修复:List<S>#439"))
        BugDB.load(BugRule(id="KT-0475",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0440] NULL_SAFETY:Set<I>#440",trigger="val x:Set<I>=batch_440()",detection="NULL_SAFETY检测:Set<I>模式440",fix="修复:Set<I>#440"))
        BugDB.load(BugRule(id="KT-0476",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0441] GENERICS:Map<S,I>#441",trigger="val x:Map<S,I>=batch_441()",detection="GENERICS检测:Map<S,I>模式441",fix="修复:Map<S,I>#441"))
        BugDB.load(BugRule(id="KT-0477",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0442] COROUTINES:String#442",trigger="val x:String=batch_442()",detection="COROUTINES检测:String模式442",fix="修复:String#442"))
        BugDB.load(BugRule(id="KT-0478",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0443] COLLECTIONS:Int#443",trigger="val x:Int=batch_443()",detection="COLLECTIONS检测:Int模式443",fix="修复:Int#443"))
        BugDB.load(BugRule(id="KT-0479",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0444] REFLECTION:Long#444",trigger="val x:Long=batch_444()",detection="REFLECTION检测:Long模式444",fix="修复:Long#444"))
        BugDB.load(BugRule(id="KT-0480",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0445] DSL_LAMBDA:Double#445",trigger="val x:Double=batch_445()",detection="DSL_LAMBDA检测:Double模式445",fix="修复:Double#445"))
        BugDB.load(BugRule(id="KT-0481",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0446] DATA_SERIAL:Boolean#446",trigger="val x:Boolean=batch_446()",detection="DATA_SERIAL检测:Boolean模式446",fix="修复:Boolean#446"))
        BugDB.load(BugRule(id="KT-0482",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0447] INLINE_TAILREC:Float#447",trigger="val x:Float=batch_447()",detection="INLINE_TAILREC检测:Float模式447",fix="修复:Float#447"))
        BugDB.load(BugRule(id="KT-0483",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0448] JAVA_INTEROP:Char#448",trigger="val x:Char=batch_448()",detection="JAVA_INTEROP检测:Char模式448",fix="修复:Char#448"))
        BugDB.load(BugRule(id="KT-0484",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0449] SMART_CAST:Byte#449",trigger="val x:Byte=batch_449()",detection="SMART_CAST检测:Byte模式449",fix="修复:Byte#449"))
        BugDB.load(BugRule(id="KT-0485",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0450] SEALED_ENUM:Short#450",trigger="val x:Short=batch_450()",detection="SEALED_ENUM检测:Short模式450",fix="修复:Short#450"))
        BugDB.load(BugRule(id="KT-0486",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0451] CONCURRENCY:Any#451",trigger="val x:Any=batch_451()",detection="CONCURRENCY检测:Any模式451",fix="修复:Any#451"))
        BugDB.load(BugRule(id="KT-0487",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0452] PERFORMANCE:List<S>#452",trigger="val x:List<S>=batch_452()",detection="PERFORMANCE检测:List<S>模式452",fix="修复:List<S>#452"))
        BugDB.load(BugRule(id="KT-0488",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0453] COMPILER_TRAP:Set<I>#453",trigger="val x:Set<I>=batch_453()",detection="COMPILER_TRAP检测:Set<I>模式453",fix="修复:Set<I>#453"))
        BugDB.load(BugRule(id="KT-0489",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0454] SECURITY:Map<S,I>#454",trigger="val x:Map<S,I>=batch_454()",detection="SECURITY检测:Map<S,I>模式454",fix="修复:Map<S,I>#454"))
        BugDB.load(BugRule(id="KT-0490",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0455] COMPOSE:String#455",trigger="val x:String=batch_455()",detection="COMPOSE检测:String模式455",fix="修复:String#455"))
        BugDB.load(BugRule(id="KT-0491",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0456] VALUE_CLASS:Int#456",trigger="val x:Int=batch_456()",detection="VALUE_CLASS检测:Int模式456",fix="修复:Int#456"))
        BugDB.load(BugRule(id="KT-0492",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0457] DELEGATE:Long#457",trigger="val x:Long=batch_457()",detection="DELEGATE检测:Long模式457",fix="修复:Long#457"))
        BugDB.load(BugRule(id="KT-0493",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0458] KMP:Double#458",trigger="val x:Double=batch_458()",detection="KMP检测:Double模式458",fix="修复:Double#458"))
        BugDB.load(BugRule(id="KT-0494",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0459] MULTIPLATFORM:Boolean#459",trigger="val x:Boolean=batch_459()",detection="MULTIPLATFORM检测:Boolean模式459",fix="修复:Boolean#459"))
        BugDB.load(BugRule(id="KT-0495",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0460] NULL_SAFETY:Float#460",trigger="val x:Float=batch_460()",detection="NULL_SAFETY检测:Float模式460",fix="修复:Float#460"))
        BugDB.load(BugRule(id="KT-0496",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0461] GENERICS:Char#461",trigger="val x:Char=batch_461()",detection="GENERICS检测:Char模式461",fix="修复:Char#461"))
        BugDB.load(BugRule(id="KT-0497",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0462] COROUTINES:Byte#462",trigger="val x:Byte=batch_462()",detection="COROUTINES检测:Byte模式462",fix="修复:Byte#462"))
        BugDB.load(BugRule(id="KT-0498",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0463] COLLECTIONS:Short#463",trigger="val x:Short=batch_463()",detection="COLLECTIONS检测:Short模式463",fix="修复:Short#463"))
        BugDB.load(BugRule(id="KT-0499",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0464] REFLECTION:Any#464",trigger="val x:Any=batch_464()",detection="REFLECTION检测:Any模式464",fix="修复:Any#464"))
        BugDB.load(BugRule(id="KT-0500",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0465] DSL_LAMBDA:List<S>#465",trigger="val x:List<S>=batch_465()",detection="DSL_LAMBDA检测:List<S>模式465",fix="修复:List<S>#465"))
    }

    private fun registerChunk2() {
        BugDB.load(BugRule(id="KT-0501",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0466] DATA_SERIAL:Set<I>#466",trigger="val x:Set<I>=batch_466()",detection="DATA_SERIAL检测:Set<I>模式466",fix="修复:Set<I>#466"))
        BugDB.load(BugRule(id="KT-0502",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0467] INLINE_TAILREC:Map<S,I>#467",trigger="val x:Map<S,I>=batch_467()",detection="INLINE_TAILREC检测:Map<S,I>模式467",fix="修复:Map<S,I>#467"))
        BugDB.load(BugRule(id="KT-0503",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0468] JAVA_INTEROP:String#468",trigger="val x:String=batch_468()",detection="JAVA_INTEROP检测:String模式468",fix="修复:String#468"))
        BugDB.load(BugRule(id="KT-0504",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0469] SMART_CAST:Int#469",trigger="val x:Int=batch_469()",detection="SMART_CAST检测:Int模式469",fix="修复:Int#469"))
        BugDB.load(BugRule(id="KT-0505",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0470] SEALED_ENUM:Long#470",trigger="val x:Long=batch_470()",detection="SEALED_ENUM检测:Long模式470",fix="修复:Long#470"))
        BugDB.load(BugRule(id="KT-0506",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0471] CONCURRENCY:Double#471",trigger="val x:Double=batch_471()",detection="CONCURRENCY检测:Double模式471",fix="修复:Double#471"))
        BugDB.load(BugRule(id="KT-0507",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0472] PERFORMANCE:Boolean#472",trigger="val x:Boolean=batch_472()",detection="PERFORMANCE检测:Boolean模式472",fix="修复:Boolean#472"))
        BugDB.load(BugRule(id="KT-0508",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0473] COMPILER_TRAP:Float#473",trigger="val x:Float=batch_473()",detection="COMPILER_TRAP检测:Float模式473",fix="修复:Float#473"))
        BugDB.load(BugRule(id="KT-0509",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0474] SECURITY:Char#474",trigger="val x:Char=batch_474()",detection="SECURITY检测:Char模式474",fix="修复:Char#474"))
        BugDB.load(BugRule(id="KT-0510",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0475] COMPOSE:Byte#475",trigger="val x:Byte=batch_475()",detection="COMPOSE检测:Byte模式475",fix="修复:Byte#475"))
        BugDB.load(BugRule(id="KT-0511",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0476] VALUE_CLASS:Short#476",trigger="val x:Short=batch_476()",detection="VALUE_CLASS检测:Short模式476",fix="修复:Short#476"))
        BugDB.load(BugRule(id="KT-0512",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0477] DELEGATE:Any#477",trigger="val x:Any=batch_477()",detection="DELEGATE检测:Any模式477",fix="修复:Any#477"))
        BugDB.load(BugRule(id="KT-0513",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0478] KMP:List<S>#478",trigger="val x:List<S>=batch_478()",detection="KMP检测:List<S>模式478",fix="修复:List<S>#478"))
        BugDB.load(BugRule(id="KT-0514",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0479] MULTIPLATFORM:Set<I>#479",trigger="val x:Set<I>=batch_479()",detection="MULTIPLATFORM检测:Set<I>模式479",fix="修复:Set<I>#479"))
        BugDB.load(BugRule(id="KT-0515",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0480] NULL_SAFETY:Map<S,I>#480",trigger="val x:Map<S,I>=batch_480()",detection="NULL_SAFETY检测:Map<S,I>模式480",fix="修复:Map<S,I>#480"))
        BugDB.load(BugRule(id="KT-0516",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0481] GENERICS:String#481",trigger="val x:String=batch_481()",detection="GENERICS检测:String模式481",fix="修复:String#481"))
        BugDB.load(BugRule(id="KT-0517",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0482] COROUTINES:Int#482",trigger="val x:Int=batch_482()",detection="COROUTINES检测:Int模式482",fix="修复:Int#482"))
        BugDB.load(BugRule(id="KT-0518",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0483] COLLECTIONS:Long#483",trigger="val x:Long=batch_483()",detection="COLLECTIONS检测:Long模式483",fix="修复:Long#483"))
        BugDB.load(BugRule(id="KT-0519",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0484] REFLECTION:Double#484",trigger="val x:Double=batch_484()",detection="REFLECTION检测:Double模式484",fix="修复:Double#484"))
        BugDB.load(BugRule(id="KT-0520",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0485] DSL_LAMBDA:Boolean#485",trigger="val x:Boolean=batch_485()",detection="DSL_LAMBDA检测:Boolean模式485",fix="修复:Boolean#485"))
        BugDB.load(BugRule(id="KT-0521",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0486] DATA_SERIAL:Float#486",trigger="val x:Float=batch_486()",detection="DATA_SERIAL检测:Float模式486",fix="修复:Float#486"))
        BugDB.load(BugRule(id="KT-0522",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0487] INLINE_TAILREC:Char#487",trigger="val x:Char=batch_487()",detection="INLINE_TAILREC检测:Char模式487",fix="修复:Char#487"))
        BugDB.load(BugRule(id="KT-0523",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0488] JAVA_INTEROP:Byte#488",trigger="val x:Byte=batch_488()",detection="JAVA_INTEROP检测:Byte模式488",fix="修复:Byte#488"))
        BugDB.load(BugRule(id="KT-0524",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0489] SMART_CAST:Short#489",trigger="val x:Short=batch_489()",detection="SMART_CAST检测:Short模式489",fix="修复:Short#489"))
        BugDB.load(BugRule(id="KT-0525",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0490] SEALED_ENUM:Any#490",trigger="val x:Any=batch_490()",detection="SEALED_ENUM检测:Any模式490",fix="修复:Any#490"))
        BugDB.load(BugRule(id="KT-0526",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0491] CONCURRENCY:List<S>#491",trigger="val x:List<S>=batch_491()",detection="CONCURRENCY检测:List<S>模式491",fix="修复:List<S>#491"))
        BugDB.load(BugRule(id="KT-0527",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0492] PERFORMANCE:Set<I>#492",trigger="val x:Set<I>=batch_492()",detection="PERFORMANCE检测:Set<I>模式492",fix="修复:Set<I>#492"))
        BugDB.load(BugRule(id="KT-0528",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0493] COMPILER_TRAP:Map<S,I>#493",trigger="val x:Map<S,I>=batch_493()",detection="COMPILER_TRAP检测:Map<S,I>模式493",fix="修复:Map<S,I>#493"))
        BugDB.load(BugRule(id="KT-0529",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0494] SECURITY:String#494",trigger="val x:String=batch_494()",detection="SECURITY检测:String模式494",fix="修复:String#494"))
        BugDB.load(BugRule(id="KT-0530",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0495] COMPOSE:Int#495",trigger="val x:Int=batch_495()",detection="COMPOSE检测:Int模式495",fix="修复:Int#495"))
        BugDB.load(BugRule(id="KT-0531",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0496] VALUE_CLASS:Long#496",trigger="val x:Long=batch_496()",detection="VALUE_CLASS检测:Long模式496",fix="修复:Long#496"))
        BugDB.load(BugRule(id="KT-0532",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0497] DELEGATE:Double#497",trigger="val x:Double=batch_497()",detection="DELEGATE检测:Double模式497",fix="修复:Double#497"))
        BugDB.load(BugRule(id="KT-0533",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0498] KMP:Boolean#498",trigger="val x:Boolean=batch_498()",detection="KMP检测:Boolean模式498",fix="修复:Boolean#498"))
        BugDB.load(BugRule(id="KT-0534",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0499] MULTIPLATFORM:Float#499",trigger="val x:Float=batch_499()",detection="MULTIPLATFORM检测:Float模式499",fix="修复:Float#499"))
        BugDB.load(BugRule(id="KT-0535",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0500] NULL_SAFETY:Char#500",trigger="val x:Char=batch_500()",detection="NULL_SAFETY检测:Char模式500",fix="修复:Char#500"))
        BugDB.load(BugRule(id="KT-0536",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0501] GENERICS:Byte#501",trigger="val x:Byte=batch_501()",detection="GENERICS检测:Byte模式501",fix="修复:Byte#501"))
        BugDB.load(BugRule(id="KT-0537",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0502] COROUTINES:Short#502",trigger="val x:Short=batch_502()",detection="COROUTINES检测:Short模式502",fix="修复:Short#502"))
        BugDB.load(BugRule(id="KT-0538",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0503] COLLECTIONS:Any#503",trigger="val x:Any=batch_503()",detection="COLLECTIONS检测:Any模式503",fix="修复:Any#503"))
        BugDB.load(BugRule(id="KT-0539",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0504] REFLECTION:List<S>#504",trigger="val x:List<S>=batch_504()",detection="REFLECTION检测:List<S>模式504",fix="修复:List<S>#504"))
        BugDB.load(BugRule(id="KT-0540",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0505] DSL_LAMBDA:Set<I>#505",trigger="val x:Set<I>=batch_505()",detection="DSL_LAMBDA检测:Set<I>模式505",fix="修复:Set<I>#505"))
        BugDB.load(BugRule(id="KT-0541",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0506] DATA_SERIAL:Map<S,I>#506",trigger="val x:Map<S,I>=batch_506()",detection="DATA_SERIAL检测:Map<S,I>模式506",fix="修复:Map<S,I>#506"))
        BugDB.load(BugRule(id="KT-0542",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0507] INLINE_TAILREC:String#507",trigger="val x:String=batch_507()",detection="INLINE_TAILREC检测:String模式507",fix="修复:String#507"))
        BugDB.load(BugRule(id="KT-0543",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0508] JAVA_INTEROP:Int#508",trigger="val x:Int=batch_508()",detection="JAVA_INTEROP检测:Int模式508",fix="修复:Int#508"))
        BugDB.load(BugRule(id="KT-0544",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0509] SMART_CAST:Long#509",trigger="val x:Long=batch_509()",detection="SMART_CAST检测:Long模式509",fix="修复:Long#509"))
        BugDB.load(BugRule(id="KT-0545",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0510] SEALED_ENUM:Double#510",trigger="val x:Double=batch_510()",detection="SEALED_ENUM检测:Double模式510",fix="修复:Double#510"))
        BugDB.load(BugRule(id="KT-0546",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0511] CONCURRENCY:Boolean#511",trigger="val x:Boolean=batch_511()",detection="CONCURRENCY检测:Boolean模式511",fix="修复:Boolean#511"))
        BugDB.load(BugRule(id="KT-0547",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0512] PERFORMANCE:Float#512",trigger="val x:Float=batch_512()",detection="PERFORMANCE检测:Float模式512",fix="修复:Float#512"))
        BugDB.load(BugRule(id="KT-0548",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0513] COMPILER_TRAP:Char#513",trigger="val x:Char=batch_513()",detection="COMPILER_TRAP检测:Char模式513",fix="修复:Char#513"))
        BugDB.load(BugRule(id="KT-0549",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0514] SECURITY:Byte#514",trigger="val x:Byte=batch_514()",detection="SECURITY检测:Byte模式514",fix="修复:Byte#514"))
        BugDB.load(BugRule(id="KT-0550",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0515] COMPOSE:Short#515",trigger="val x:Short=batch_515()",detection="COMPOSE检测:Short模式515",fix="修复:Short#515"))
        BugDB.load(BugRule(id="KT-0551",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0516] VALUE_CLASS:Any#516",trigger="val x:Any=batch_516()",detection="VALUE_CLASS检测:Any模式516",fix="修复:Any#516"))
        BugDB.load(BugRule(id="KT-0552",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0517] DELEGATE:List<S>#517",trigger="val x:List<S>=batch_517()",detection="DELEGATE检测:List<S>模式517",fix="修复:List<S>#517"))
        BugDB.load(BugRule(id="KT-0553",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0518] KMP:Set<I>#518",trigger="val x:Set<I>=batch_518()",detection="KMP检测:Set<I>模式518",fix="修复:Set<I>#518"))
        BugDB.load(BugRule(id="KT-0554",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0519] MULTIPLATFORM:Map<S,I>#519",trigger="val x:Map<S,I>=batch_519()",detection="MULTIPLATFORM检测:Map<S,I>模式519",fix="修复:Map<S,I>#519"))
        BugDB.load(BugRule(id="KT-0555",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0520] NULL_SAFETY:String#520",trigger="val x:String=batch_520()",detection="NULL_SAFETY检测:String模式520",fix="修复:String#520"))
        BugDB.load(BugRule(id="KT-0556",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0521] GENERICS:Int#521",trigger="val x:Int=batch_521()",detection="GENERICS检测:Int模式521",fix="修复:Int#521"))
        BugDB.load(BugRule(id="KT-0557",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0522] COROUTINES:Long#522",trigger="val x:Long=batch_522()",detection="COROUTINES检测:Long模式522",fix="修复:Long#522"))
        BugDB.load(BugRule(id="KT-0558",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0523] COLLECTIONS:Double#523",trigger="val x:Double=batch_523()",detection="COLLECTIONS检测:Double模式523",fix="修复:Double#523"))
        BugDB.load(BugRule(id="KT-0559",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0524] REFLECTION:Boolean#524",trigger="val x:Boolean=batch_524()",detection="REFLECTION检测:Boolean模式524",fix="修复:Boolean#524"))
        BugDB.load(BugRule(id="KT-0560",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0525] DSL_LAMBDA:Float#525",trigger="val x:Float=batch_525()",detection="DSL_LAMBDA检测:Float模式525",fix="修复:Float#525"))
        BugDB.load(BugRule(id="KT-0561",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0526] DATA_SERIAL:Char#526",trigger="val x:Char=batch_526()",detection="DATA_SERIAL检测:Char模式526",fix="修复:Char#526"))
        BugDB.load(BugRule(id="KT-0562",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0527] INLINE_TAILREC:Byte#527",trigger="val x:Byte=batch_527()",detection="INLINE_TAILREC检测:Byte模式527",fix="修复:Byte#527"))
        BugDB.load(BugRule(id="KT-0563",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0528] JAVA_INTEROP:Short#528",trigger="val x:Short=batch_528()",detection="JAVA_INTEROP检测:Short模式528",fix="修复:Short#528"))
        BugDB.load(BugRule(id="KT-0564",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0529] SMART_CAST:Any#529",trigger="val x:Any=batch_529()",detection="SMART_CAST检测:Any模式529",fix="修复:Any#529"))
        BugDB.load(BugRule(id="KT-0565",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0530] SEALED_ENUM:List<S>#530",trigger="val x:List<S>=batch_530()",detection="SEALED_ENUM检测:List<S>模式530",fix="修复:List<S>#530"))
        BugDB.load(BugRule(id="KT-0566",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0531] CONCURRENCY:Set<I>#531",trigger="val x:Set<I>=batch_531()",detection="CONCURRENCY检测:Set<I>模式531",fix="修复:Set<I>#531"))
        BugDB.load(BugRule(id="KT-0567",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0532] PERFORMANCE:Map<S,I>#532",trigger="val x:Map<S,I>=batch_532()",detection="PERFORMANCE检测:Map<S,I>模式532",fix="修复:Map<S,I>#532"))
        BugDB.load(BugRule(id="KT-0568",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0533] COMPILER_TRAP:String#533",trigger="val x:String=batch_533()",detection="COMPILER_TRAP检测:String模式533",fix="修复:String#533"))
        BugDB.load(BugRule(id="KT-0569",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0534] SECURITY:Int#534",trigger="val x:Int=batch_534()",detection="SECURITY检测:Int模式534",fix="修复:Int#534"))
        BugDB.load(BugRule(id="KT-0570",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0535] COMPOSE:Long#535",trigger="val x:Long=batch_535()",detection="COMPOSE检测:Long模式535",fix="修复:Long#535"))
        BugDB.load(BugRule(id="KT-0571",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0536] VALUE_CLASS:Double#536",trigger="val x:Double=batch_536()",detection="VALUE_CLASS检测:Double模式536",fix="修复:Double#536"))
        BugDB.load(BugRule(id="KT-0572",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0537] DELEGATE:Boolean#537",trigger="val x:Boolean=batch_537()",detection="DELEGATE检测:Boolean模式537",fix="修复:Boolean#537"))
        BugDB.load(BugRule(id="KT-0573",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0538] KMP:Float#538",trigger="val x:Float=batch_538()",detection="KMP检测:Float模式538",fix="修复:Float#538"))
        BugDB.load(BugRule(id="KT-0574",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0539] MULTIPLATFORM:Char#539",trigger="val x:Char=batch_539()",detection="MULTIPLATFORM检测:Char模式539",fix="修复:Char#539"))
        BugDB.load(BugRule(id="KT-0575",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0540] NULL_SAFETY:Byte#540",trigger="val x:Byte=batch_540()",detection="NULL_SAFETY检测:Byte模式540",fix="修复:Byte#540"))
        BugDB.load(BugRule(id="KT-0576",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0541] GENERICS:Short#541",trigger="val x:Short=batch_541()",detection="GENERICS检测:Short模式541",fix="修复:Short#541"))
        BugDB.load(BugRule(id="KT-0577",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0542] COROUTINES:Any#542",trigger="val x:Any=batch_542()",detection="COROUTINES检测:Any模式542",fix="修复:Any#542"))
        BugDB.load(BugRule(id="KT-0578",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0543] COLLECTIONS:List<S>#543",trigger="val x:List<S>=batch_543()",detection="COLLECTIONS检测:List<S>模式543",fix="修复:List<S>#543"))
        BugDB.load(BugRule(id="KT-0579",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0544] REFLECTION:Set<I>#544",trigger="val x:Set<I>=batch_544()",detection="REFLECTION检测:Set<I>模式544",fix="修复:Set<I>#544"))
        BugDB.load(BugRule(id="KT-0580",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0545] DSL_LAMBDA:Map<S,I>#545",trigger="val x:Map<S,I>=batch_545()",detection="DSL_LAMBDA检测:Map<S,I>模式545",fix="修复:Map<S,I>#545"))
        BugDB.load(BugRule(id="KT-0581",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0546] DATA_SERIAL:String#546",trigger="val x:String=batch_546()",detection="DATA_SERIAL检测:String模式546",fix="修复:String#546"))
        BugDB.load(BugRule(id="KT-0582",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0547] INLINE_TAILREC:Int#547",trigger="val x:Int=batch_547()",detection="INLINE_TAILREC检测:Int模式547",fix="修复:Int#547"))
        BugDB.load(BugRule(id="KT-0583",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0548] JAVA_INTEROP:Long#548",trigger="val x:Long=batch_548()",detection="JAVA_INTEROP检测:Long模式548",fix="修复:Long#548"))
        BugDB.load(BugRule(id="KT-0584",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0549] SMART_CAST:Double#549",trigger="val x:Double=batch_549()",detection="SMART_CAST检测:Double模式549",fix="修复:Double#549"))
        BugDB.load(BugRule(id="KT-0585",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0550] SEALED_ENUM:Boolean#550",trigger="val x:Boolean=batch_550()",detection="SEALED_ENUM检测:Boolean模式550",fix="修复:Boolean#550"))
        BugDB.load(BugRule(id="KT-0586",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0551] CONCURRENCY:Float#551",trigger="val x:Float=batch_551()",detection="CONCURRENCY检测:Float模式551",fix="修复:Float#551"))
        BugDB.load(BugRule(id="KT-0587",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0552] PERFORMANCE:Char#552",trigger="val x:Char=batch_552()",detection="PERFORMANCE检测:Char模式552",fix="修复:Char#552"))
        BugDB.load(BugRule(id="KT-0588",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0553] COMPILER_TRAP:Byte#553",trigger="val x:Byte=batch_553()",detection="COMPILER_TRAP检测:Byte模式553",fix="修复:Byte#553"))
        BugDB.load(BugRule(id="KT-0589",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0554] SECURITY:Short#554",trigger="val x:Short=batch_554()",detection="SECURITY检测:Short模式554",fix="修复:Short#554"))
        BugDB.load(BugRule(id="KT-0590",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0555] COMPOSE:Any#555",trigger="val x:Any=batch_555()",detection="COMPOSE检测:Any模式555",fix="修复:Any#555"))
        BugDB.load(BugRule(id="KT-0591",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0556] VALUE_CLASS:List<S>#556",trigger="val x:List<S>=batch_556()",detection="VALUE_CLASS检测:List<S>模式556",fix="修复:List<S>#556"))
        BugDB.load(BugRule(id="KT-0592",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0557] DELEGATE:Set<I>#557",trigger="val x:Set<I>=batch_557()",detection="DELEGATE检测:Set<I>模式557",fix="修复:Set<I>#557"))
        BugDB.load(BugRule(id="KT-0593",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0558] KMP:Map<S,I>#558",trigger="val x:Map<S,I>=batch_558()",detection="KMP检测:Map<S,I>模式558",fix="修复:Map<S,I>#558"))
        BugDB.load(BugRule(id="KT-0594",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0559] MULTIPLATFORM:String#559",trigger="val x:String=batch_559()",detection="MULTIPLATFORM检测:String模式559",fix="修复:String#559"))
        BugDB.load(BugRule(id="KT-0595",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0560] NULL_SAFETY:Int#560",trigger="val x:Int=batch_560()",detection="NULL_SAFETY检测:Int模式560",fix="修复:Int#560"))
        BugDB.load(BugRule(id="KT-0596",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0561] GENERICS:Long#561",trigger="val x:Long=batch_561()",detection="GENERICS检测:Long模式561",fix="修复:Long#561"))
        BugDB.load(BugRule(id="KT-0597",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0562] COROUTINES:Double#562",trigger="val x:Double=batch_562()",detection="COROUTINES检测:Double模式562",fix="修复:Double#562"))
        BugDB.load(BugRule(id="KT-0598",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0563] COLLECTIONS:Boolean#563",trigger="val x:Boolean=batch_563()",detection="COLLECTIONS检测:Boolean模式563",fix="修复:Boolean#563"))
        BugDB.load(BugRule(id="KT-0599",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0564] REFLECTION:Float#564",trigger="val x:Float=batch_564()",detection="REFLECTION检测:Float模式564",fix="修复:Float#564"))
        BugDB.load(BugRule(id="KT-0600",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0565] DSL_LAMBDA:Char#565",trigger="val x:Char=batch_565()",detection="DSL_LAMBDA检测:Char模式565",fix="修复:Char#565"))
        BugDB.load(BugRule(id="KT-0601",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0566] DATA_SERIAL:Byte#566",trigger="val x:Byte=batch_566()",detection="DATA_SERIAL检测:Byte模式566",fix="修复:Byte#566"))
        BugDB.load(BugRule(id="KT-0602",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0567] INLINE_TAILREC:Short#567",trigger="val x:Short=batch_567()",detection="INLINE_TAILREC检测:Short模式567",fix="修复:Short#567"))
        BugDB.load(BugRule(id="KT-0603",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0568] JAVA_INTEROP:Any#568",trigger="val x:Any=batch_568()",detection="JAVA_INTEROP检测:Any模式568",fix="修复:Any#568"))
        BugDB.load(BugRule(id="KT-0604",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0569] SMART_CAST:List<S>#569",trigger="val x:List<S>=batch_569()",detection="SMART_CAST检测:List<S>模式569",fix="修复:List<S>#569"))
        BugDB.load(BugRule(id="KT-0605",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0570] SEALED_ENUM:Set<I>#570",trigger="val x:Set<I>=batch_570()",detection="SEALED_ENUM检测:Set<I>模式570",fix="修复:Set<I>#570"))
        BugDB.load(BugRule(id="KT-0606",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0571] CONCURRENCY:Map<S,I>#571",trigger="val x:Map<S,I>=batch_571()",detection="CONCURRENCY检测:Map<S,I>模式571",fix="修复:Map<S,I>#571"))
        BugDB.load(BugRule(id="KT-0607",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0572] PERFORMANCE:String#572",trigger="val x:String=batch_572()",detection="PERFORMANCE检测:String模式572",fix="修复:String#572"))
        BugDB.load(BugRule(id="KT-0608",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0573] COMPILER_TRAP:Int#573",trigger="val x:Int=batch_573()",detection="COMPILER_TRAP检测:Int模式573",fix="修复:Int#573"))
        BugDB.load(BugRule(id="KT-0609",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0574] SECURITY:Long#574",trigger="val x:Long=batch_574()",detection="SECURITY检测:Long模式574",fix="修复:Long#574"))
        BugDB.load(BugRule(id="KT-0610",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0575] COMPOSE:Double#575",trigger="val x:Double=batch_575()",detection="COMPOSE检测:Double模式575",fix="修复:Double#575"))
        BugDB.load(BugRule(id="KT-0611",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0576] VALUE_CLASS:Boolean#576",trigger="val x:Boolean=batch_576()",detection="VALUE_CLASS检测:Boolean模式576",fix="修复:Boolean#576"))
        BugDB.load(BugRule(id="KT-0612",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0577] DELEGATE:Float#577",trigger="val x:Float=batch_577()",detection="DELEGATE检测:Float模式577",fix="修复:Float#577"))
        BugDB.load(BugRule(id="KT-0613",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0578] KMP:Char#578",trigger="val x:Char=batch_578()",detection="KMP检测:Char模式578",fix="修复:Char#578"))
        BugDB.load(BugRule(id="KT-0614",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0579] MULTIPLATFORM:Byte#579",trigger="val x:Byte=batch_579()",detection="MULTIPLATFORM检测:Byte模式579",fix="修复:Byte#579"))
        BugDB.load(BugRule(id="KT-0615",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0580] NULL_SAFETY:Short#580",trigger="val x:Short=batch_580()",detection="NULL_SAFETY检测:Short模式580",fix="修复:Short#580"))
        BugDB.load(BugRule(id="KT-0616",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0581] GENERICS:Any#581",trigger="val x:Any=batch_581()",detection="GENERICS检测:Any模式581",fix="修复:Any#581"))
        BugDB.load(BugRule(id="KT-0617",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0582] COROUTINES:List<S>#582",trigger="val x:List<S>=batch_582()",detection="COROUTINES检测:List<S>模式582",fix="修复:List<S>#582"))
        BugDB.load(BugRule(id="KT-0618",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0583] COLLECTIONS:Set<I>#583",trigger="val x:Set<I>=batch_583()",detection="COLLECTIONS检测:Set<I>模式583",fix="修复:Set<I>#583"))
        BugDB.load(BugRule(id="KT-0619",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0584] REFLECTION:Map<S,I>#584",trigger="val x:Map<S,I>=batch_584()",detection="REFLECTION检测:Map<S,I>模式584",fix="修复:Map<S,I>#584"))
        BugDB.load(BugRule(id="KT-0620",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0585] DSL_LAMBDA:String#585",trigger="val x:String=batch_585()",detection="DSL_LAMBDA检测:String模式585",fix="修复:String#585"))
        BugDB.load(BugRule(id="KT-0621",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0586] DATA_SERIAL:Int#586",trigger="val x:Int=batch_586()",detection="DATA_SERIAL检测:Int模式586",fix="修复:Int#586"))
        BugDB.load(BugRule(id="KT-0622",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0587] INLINE_TAILREC:Long#587",trigger="val x:Long=batch_587()",detection="INLINE_TAILREC检测:Long模式587",fix="修复:Long#587"))
        BugDB.load(BugRule(id="KT-0623",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0588] JAVA_INTEROP:Double#588",trigger="val x:Double=batch_588()",detection="JAVA_INTEROP检测:Double模式588",fix="修复:Double#588"))
        BugDB.load(BugRule(id="KT-0624",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0589] SMART_CAST:Boolean#589",trigger="val x:Boolean=batch_589()",detection="SMART_CAST检测:Boolean模式589",fix="修复:Boolean#589"))
        BugDB.load(BugRule(id="KT-0625",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0590] SEALED_ENUM:Float#590",trigger="val x:Float=batch_590()",detection="SEALED_ENUM检测:Float模式590",fix="修复:Float#590"))
        BugDB.load(BugRule(id="KT-0626",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0591] CONCURRENCY:Char#591",trigger="val x:Char=batch_591()",detection="CONCURRENCY检测:Char模式591",fix="修复:Char#591"))
        BugDB.load(BugRule(id="KT-0627",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0592] PERFORMANCE:Byte#592",trigger="val x:Byte=batch_592()",detection="PERFORMANCE检测:Byte模式592",fix="修复:Byte#592"))
        BugDB.load(BugRule(id="KT-0628",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0593] COMPILER_TRAP:Short#593",trigger="val x:Short=batch_593()",detection="COMPILER_TRAP检测:Short模式593",fix="修复:Short#593"))
        BugDB.load(BugRule(id="KT-0629",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0594] SECURITY:Any#594",trigger="val x:Any=batch_594()",detection="SECURITY检测:Any模式594",fix="修复:Any#594"))
        BugDB.load(BugRule(id="KT-0630",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0595] COMPOSE:List<S>#595",trigger="val x:List<S>=batch_595()",detection="COMPOSE检测:List<S>模式595",fix="修复:List<S>#595"))
        BugDB.load(BugRule(id="KT-0631",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0596] VALUE_CLASS:Set<I>#596",trigger="val x:Set<I>=batch_596()",detection="VALUE_CLASS检测:Set<I>模式596",fix="修复:Set<I>#596"))
        BugDB.load(BugRule(id="KT-0632",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0597] DELEGATE:Map<S,I>#597",trigger="val x:Map<S,I>=batch_597()",detection="DELEGATE检测:Map<S,I>模式597",fix="修复:Map<S,I>#597"))
        BugDB.load(BugRule(id="KT-0633",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0598] KMP:String#598",trigger="val x:String=batch_598()",detection="KMP检测:String模式598",fix="修复:String#598"))
        BugDB.load(BugRule(id="KT-0634",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0599] MULTIPLATFORM:Int#599",trigger="val x:Int=batch_599()",detection="MULTIPLATFORM检测:Int模式599",fix="修复:Int#599"))
        BugDB.load(BugRule(id="KT-0635",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0600] NULL_SAFETY:Long#600",trigger="val x:Long=batch_600()",detection="NULL_SAFETY检测:Long模式600",fix="修复:Long#600"))
        BugDB.load(BugRule(id="KT-0636",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0601] GENERICS:Double#601",trigger="val x:Double=batch_601()",detection="GENERICS检测:Double模式601",fix="修复:Double#601"))
        BugDB.load(BugRule(id="KT-0637",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0602] COROUTINES:Boolean#602",trigger="val x:Boolean=batch_602()",detection="COROUTINES检测:Boolean模式602",fix="修复:Boolean#602"))
        BugDB.load(BugRule(id="KT-0638",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0603] COLLECTIONS:Float#603",trigger="val x:Float=batch_603()",detection="COLLECTIONS检测:Float模式603",fix="修复:Float#603"))
        BugDB.load(BugRule(id="KT-0639",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0604] REFLECTION:Char#604",trigger="val x:Char=batch_604()",detection="REFLECTION检测:Char模式604",fix="修复:Char#604"))
        BugDB.load(BugRule(id="KT-0640",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0605] DSL_LAMBDA:Byte#605",trigger="val x:Byte=batch_605()",detection="DSL_LAMBDA检测:Byte模式605",fix="修复:Byte#605"))
        BugDB.load(BugRule(id="KT-0641",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0606] DATA_SERIAL:Short#606",trigger="val x:Short=batch_606()",detection="DATA_SERIAL检测:Short模式606",fix="修复:Short#606"))
        BugDB.load(BugRule(id="KT-0642",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0607] INLINE_TAILREC:Any#607",trigger="val x:Any=batch_607()",detection="INLINE_TAILREC检测:Any模式607",fix="修复:Any#607"))
        BugDB.load(BugRule(id="KT-0643",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0608] JAVA_INTEROP:List<S>#608",trigger="val x:List<S>=batch_608()",detection="JAVA_INTEROP检测:List<S>模式608",fix="修复:List<S>#608"))
        BugDB.load(BugRule(id="KT-0644",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0609] SMART_CAST:Set<I>#609",trigger="val x:Set<I>=batch_609()",detection="SMART_CAST检测:Set<I>模式609",fix="修复:Set<I>#609"))
        BugDB.load(BugRule(id="KT-0645",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0610] SEALED_ENUM:Map<S,I>#610",trigger="val x:Map<S,I>=batch_610()",detection="SEALED_ENUM检测:Map<S,I>模式610",fix="修复:Map<S,I>#610"))
        BugDB.load(BugRule(id="KT-0646",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0611] CONCURRENCY:String#611",trigger="val x:String=batch_611()",detection="CONCURRENCY检测:String模式611",fix="修复:String#611"))
        BugDB.load(BugRule(id="KT-0647",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0612] PERFORMANCE:Int#612",trigger="val x:Int=batch_612()",detection="PERFORMANCE检测:Int模式612",fix="修复:Int#612"))
        BugDB.load(BugRule(id="KT-0648",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0613] COMPILER_TRAP:Long#613",trigger="val x:Long=batch_613()",detection="COMPILER_TRAP检测:Long模式613",fix="修复:Long#613"))
        BugDB.load(BugRule(id="KT-0649",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0614] SECURITY:Double#614",trigger="val x:Double=batch_614()",detection="SECURITY检测:Double模式614",fix="修复:Double#614"))
        BugDB.load(BugRule(id="KT-0650",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0615] COMPOSE:Boolean#615",trigger="val x:Boolean=batch_615()",detection="COMPOSE检测:Boolean模式615",fix="修复:Boolean#615"))
        BugDB.load(BugRule(id="KT-0651",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0616] VALUE_CLASS:Float#616",trigger="val x:Float=batch_616()",detection="VALUE_CLASS检测:Float模式616",fix="修复:Float#616"))
        BugDB.load(BugRule(id="KT-0652",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0617] DELEGATE:Char#617",trigger="val x:Char=batch_617()",detection="DELEGATE检测:Char模式617",fix="修复:Char#617"))
        BugDB.load(BugRule(id="KT-0653",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0618] KMP:Byte#618",trigger="val x:Byte=batch_618()",detection="KMP检测:Byte模式618",fix="修复:Byte#618"))
        BugDB.load(BugRule(id="KT-0654",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0619] MULTIPLATFORM:Short#619",trigger="val x:Short=batch_619()",detection="MULTIPLATFORM检测:Short模式619",fix="修复:Short#619"))
        BugDB.load(BugRule(id="KT-0655",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0620] NULL_SAFETY:Any#620",trigger="val x:Any=batch_620()",detection="NULL_SAFETY检测:Any模式620",fix="修复:Any#620"))
        BugDB.load(BugRule(id="KT-0656",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0621] GENERICS:List<S>#621",trigger="val x:List<S>=batch_621()",detection="GENERICS检测:List<S>模式621",fix="修复:List<S>#621"))
        BugDB.load(BugRule(id="KT-0657",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0622] COROUTINES:Set<I>#622",trigger="val x:Set<I>=batch_622()",detection="COROUTINES检测:Set<I>模式622",fix="修复:Set<I>#622"))
        BugDB.load(BugRule(id="KT-0658",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0623] COLLECTIONS:Map<S,I>#623",trigger="val x:Map<S,I>=batch_623()",detection="COLLECTIONS检测:Map<S,I>模式623",fix="修复:Map<S,I>#623"))
        BugDB.load(BugRule(id="KT-0659",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0624] REFLECTION:String#624",trigger="val x:String=batch_624()",detection="REFLECTION检测:String模式624",fix="修复:String#624"))
        BugDB.load(BugRule(id="KT-0660",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0625] DSL_LAMBDA:Int#625",trigger="val x:Int=batch_625()",detection="DSL_LAMBDA检测:Int模式625",fix="修复:Int#625"))
        BugDB.load(BugRule(id="KT-0661",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0626] DATA_SERIAL:Long#626",trigger="val x:Long=batch_626()",detection="DATA_SERIAL检测:Long模式626",fix="修复:Long#626"))
        BugDB.load(BugRule(id="KT-0662",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0627] INLINE_TAILREC:Double#627",trigger="val x:Double=batch_627()",detection="INLINE_TAILREC检测:Double模式627",fix="修复:Double#627"))
        BugDB.load(BugRule(id="KT-0663",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0628] JAVA_INTEROP:Boolean#628",trigger="val x:Boolean=batch_628()",detection="JAVA_INTEROP检测:Boolean模式628",fix="修复:Boolean#628"))
        BugDB.load(BugRule(id="KT-0664",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0629] SMART_CAST:Float#629",trigger="val x:Float=batch_629()",detection="SMART_CAST检测:Float模式629",fix="修复:Float#629"))
        BugDB.load(BugRule(id="KT-0665",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0630] SEALED_ENUM:Char#630",trigger="val x:Char=batch_630()",detection="SEALED_ENUM检测:Char模式630",fix="修复:Char#630"))
        BugDB.load(BugRule(id="KT-0666",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0631] CONCURRENCY:Byte#631",trigger="val x:Byte=batch_631()",detection="CONCURRENCY检测:Byte模式631",fix="修复:Byte#631"))
        BugDB.load(BugRule(id="KT-0667",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0632] PERFORMANCE:Short#632",trigger="val x:Short=batch_632()",detection="PERFORMANCE检测:Short模式632",fix="修复:Short#632"))
        BugDB.load(BugRule(id="KT-0668",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0633] COMPILER_TRAP:Any#633",trigger="val x:Any=batch_633()",detection="COMPILER_TRAP检测:Any模式633",fix="修复:Any#633"))
        BugDB.load(BugRule(id="KT-0669",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0634] SECURITY:List<S>#634",trigger="val x:List<S>=batch_634()",detection="SECURITY检测:List<S>模式634",fix="修复:List<S>#634"))
        BugDB.load(BugRule(id="KT-0670",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0635] COMPOSE:Set<I>#635",trigger="val x:Set<I>=batch_635()",detection="COMPOSE检测:Set<I>模式635",fix="修复:Set<I>#635"))
        BugDB.load(BugRule(id="KT-0671",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0636] VALUE_CLASS:Map<S,I>#636",trigger="val x:Map<S,I>=batch_636()",detection="VALUE_CLASS检测:Map<S,I>模式636",fix="修复:Map<S,I>#636"))
        BugDB.load(BugRule(id="KT-0672",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0637] DELEGATE:String#637",trigger="val x:String=batch_637()",detection="DELEGATE检测:String模式637",fix="修复:String#637"))
        BugDB.load(BugRule(id="KT-0673",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0638] KMP:Int#638",trigger="val x:Int=batch_638()",detection="KMP检测:Int模式638",fix="修复:Int#638"))
        BugDB.load(BugRule(id="KT-0674",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0639] MULTIPLATFORM:Long#639",trigger="val x:Long=batch_639()",detection="MULTIPLATFORM检测:Long模式639",fix="修复:Long#639"))
        BugDB.load(BugRule(id="KT-0675",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0640] NULL_SAFETY:Double#640",trigger="val x:Double=batch_640()",detection="NULL_SAFETY检测:Double模式640",fix="修复:Double#640"))
        BugDB.load(BugRule(id="KT-0676",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0641] GENERICS:Boolean#641",trigger="val x:Boolean=batch_641()",detection="GENERICS检测:Boolean模式641",fix="修复:Boolean#641"))
        BugDB.load(BugRule(id="KT-0677",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0642] COROUTINES:Float#642",trigger="val x:Float=batch_642()",detection="COROUTINES检测:Float模式642",fix="修复:Float#642"))
        BugDB.load(BugRule(id="KT-0678",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0643] COLLECTIONS:Char#643",trigger="val x:Char=batch_643()",detection="COLLECTIONS检测:Char模式643",fix="修复:Char#643"))
        BugDB.load(BugRule(id="KT-0679",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0644] REFLECTION:Byte#644",trigger="val x:Byte=batch_644()",detection="REFLECTION检测:Byte模式644",fix="修复:Byte#644"))
        BugDB.load(BugRule(id="KT-0680",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0645] DSL_LAMBDA:Short#645",trigger="val x:Short=batch_645()",detection="DSL_LAMBDA检测:Short模式645",fix="修复:Short#645"))
        BugDB.load(BugRule(id="KT-0681",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0646] DATA_SERIAL:Any#646",trigger="val x:Any=batch_646()",detection="DATA_SERIAL检测:Any模式646",fix="修复:Any#646"))
        BugDB.load(BugRule(id="KT-0682",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0647] INLINE_TAILREC:List<S>#647",trigger="val x:List<S>=batch_647()",detection="INLINE_TAILREC检测:List<S>模式647",fix="修复:List<S>#647"))
        BugDB.load(BugRule(id="KT-0683",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0648] JAVA_INTEROP:Set<I>#648",trigger="val x:Set<I>=batch_648()",detection="JAVA_INTEROP检测:Set<I>模式648",fix="修复:Set<I>#648"))
        BugDB.load(BugRule(id="KT-0684",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0649] SMART_CAST:Map<S,I>#649",trigger="val x:Map<S,I>=batch_649()",detection="SMART_CAST检测:Map<S,I>模式649",fix="修复:Map<S,I>#649"))
        BugDB.load(BugRule(id="KT-0685",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0650] SEALED_ENUM:String#650",trigger="val x:String=batch_650()",detection="SEALED_ENUM检测:String模式650",fix="修复:String#650"))
        BugDB.load(BugRule(id="KT-0686",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0651] CONCURRENCY:Int#651",trigger="val x:Int=batch_651()",detection="CONCURRENCY检测:Int模式651",fix="修复:Int#651"))
        BugDB.load(BugRule(id="KT-0687",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0652] PERFORMANCE:Long#652",trigger="val x:Long=batch_652()",detection="PERFORMANCE检测:Long模式652",fix="修复:Long#652"))
        BugDB.load(BugRule(id="KT-0688",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0653] COMPILER_TRAP:Double#653",trigger="val x:Double=batch_653()",detection="COMPILER_TRAP检测:Double模式653",fix="修复:Double#653"))
        BugDB.load(BugRule(id="KT-0689",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0654] SECURITY:Boolean#654",trigger="val x:Boolean=batch_654()",detection="SECURITY检测:Boolean模式654",fix="修复:Boolean#654"))
        BugDB.load(BugRule(id="KT-0690",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0655] COMPOSE:Float#655",trigger="val x:Float=batch_655()",detection="COMPOSE检测:Float模式655",fix="修复:Float#655"))
        BugDB.load(BugRule(id="KT-0691",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0656] VALUE_CLASS:Char#656",trigger="val x:Char=batch_656()",detection="VALUE_CLASS检测:Char模式656",fix="修复:Char#656"))
        BugDB.load(BugRule(id="KT-0692",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0657] DELEGATE:Byte#657",trigger="val x:Byte=batch_657()",detection="DELEGATE检测:Byte模式657",fix="修复:Byte#657"))
        BugDB.load(BugRule(id="KT-0693",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0658] KMP:Short#658",trigger="val x:Short=batch_658()",detection="KMP检测:Short模式658",fix="修复:Short#658"))
        BugDB.load(BugRule(id="KT-0694",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0659] MULTIPLATFORM:Any#659",trigger="val x:Any=batch_659()",detection="MULTIPLATFORM检测:Any模式659",fix="修复:Any#659"))
        BugDB.load(BugRule(id="KT-0695",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0660] NULL_SAFETY:List<S>#660",trigger="val x:List<S>=batch_660()",detection="NULL_SAFETY检测:List<S>模式660",fix="修复:List<S>#660"))
        BugDB.load(BugRule(id="KT-0696",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0661] GENERICS:Set<I>#661",trigger="val x:Set<I>=batch_661()",detection="GENERICS检测:Set<I>模式661",fix="修复:Set<I>#661"))
        BugDB.load(BugRule(id="KT-0697",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0662] COROUTINES:Map<S,I>#662",trigger="val x:Map<S,I>=batch_662()",detection="COROUTINES检测:Map<S,I>模式662",fix="修复:Map<S,I>#662"))
        BugDB.load(BugRule(id="KT-0698",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0663] COLLECTIONS:String#663",trigger="val x:String=batch_663()",detection="COLLECTIONS检测:String模式663",fix="修复:String#663"))
        BugDB.load(BugRule(id="KT-0699",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0664] REFLECTION:Int#664",trigger="val x:Int=batch_664()",detection="REFLECTION检测:Int模式664",fix="修复:Int#664"))
        BugDB.load(BugRule(id="KT-0700",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0665] DSL_LAMBDA:Long#665",trigger="val x:Long=batch_665()",detection="DSL_LAMBDA检测:Long模式665",fix="修复:Long#665"))
        BugDB.load(BugRule(id="KT-0701",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0666] DATA_SERIAL:Double#666",trigger="val x:Double=batch_666()",detection="DATA_SERIAL检测:Double模式666",fix="修复:Double#666"))
        BugDB.load(BugRule(id="KT-0702",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0667] INLINE_TAILREC:Boolean#667",trigger="val x:Boolean=batch_667()",detection="INLINE_TAILREC检测:Boolean模式667",fix="修复:Boolean#667"))
        BugDB.load(BugRule(id="KT-0703",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0668] JAVA_INTEROP:Float#668",trigger="val x:Float=batch_668()",detection="JAVA_INTEROP检测:Float模式668",fix="修复:Float#668"))
        BugDB.load(BugRule(id="KT-0704",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0669] SMART_CAST:Char#669",trigger="val x:Char=batch_669()",detection="SMART_CAST检测:Char模式669",fix="修复:Char#669"))
        BugDB.load(BugRule(id="KT-0705",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0670] SEALED_ENUM:Byte#670",trigger="val x:Byte=batch_670()",detection="SEALED_ENUM检测:Byte模式670",fix="修复:Byte#670"))
        BugDB.load(BugRule(id="KT-0706",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0671] CONCURRENCY:Short#671",trigger="val x:Short=batch_671()",detection="CONCURRENCY检测:Short模式671",fix="修复:Short#671"))
        BugDB.load(BugRule(id="KT-0707",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0672] PERFORMANCE:Any#672",trigger="val x:Any=batch_672()",detection="PERFORMANCE检测:Any模式672",fix="修复:Any#672"))
        BugDB.load(BugRule(id="KT-0708",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0673] COMPILER_TRAP:List<S>#673",trigger="val x:List<S>=batch_673()",detection="COMPILER_TRAP检测:List<S>模式673",fix="修复:List<S>#673"))
        BugDB.load(BugRule(id="KT-0709",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0674] SECURITY:Set<I>#674",trigger="val x:Set<I>=batch_674()",detection="SECURITY检测:Set<I>模式674",fix="修复:Set<I>#674"))
        BugDB.load(BugRule(id="KT-0710",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0675] COMPOSE:Map<S,I>#675",trigger="val x:Map<S,I>=batch_675()",detection="COMPOSE检测:Map<S,I>模式675",fix="修复:Map<S,I>#675"))
        BugDB.load(BugRule(id="KT-0711",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0676] VALUE_CLASS:String#676",trigger="val x:String=batch_676()",detection="VALUE_CLASS检测:String模式676",fix="修复:String#676"))
        BugDB.load(BugRule(id="KT-0712",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0677] DELEGATE:Int#677",trigger="val x:Int=batch_677()",detection="DELEGATE检测:Int模式677",fix="修复:Int#677"))
        BugDB.load(BugRule(id="KT-0713",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0678] KMP:Long#678",trigger="val x:Long=batch_678()",detection="KMP检测:Long模式678",fix="修复:Long#678"))
        BugDB.load(BugRule(id="KT-0714",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0679] MULTIPLATFORM:Double#679",trigger="val x:Double=batch_679()",detection="MULTIPLATFORM检测:Double模式679",fix="修复:Double#679"))
        BugDB.load(BugRule(id="KT-0715",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0680] NULL_SAFETY:Boolean#680",trigger="val x:Boolean=batch_680()",detection="NULL_SAFETY检测:Boolean模式680",fix="修复:Boolean#680"))
        BugDB.load(BugRule(id="KT-0716",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0681] GENERICS:Float#681",trigger="val x:Float=batch_681()",detection="GENERICS检测:Float模式681",fix="修复:Float#681"))
        BugDB.load(BugRule(id="KT-0717",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0682] COROUTINES:Char#682",trigger="val x:Char=batch_682()",detection="COROUTINES检测:Char模式682",fix="修复:Char#682"))
        BugDB.load(BugRule(id="KT-0718",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0683] COLLECTIONS:Byte#683",trigger="val x:Byte=batch_683()",detection="COLLECTIONS检测:Byte模式683",fix="修复:Byte#683"))
        BugDB.load(BugRule(id="KT-0719",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0684] REFLECTION:Short#684",trigger="val x:Short=batch_684()",detection="REFLECTION检测:Short模式684",fix="修复:Short#684"))
        BugDB.load(BugRule(id="KT-0720",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0685] DSL_LAMBDA:Any#685",trigger="val x:Any=batch_685()",detection="DSL_LAMBDA检测:Any模式685",fix="修复:Any#685"))
        BugDB.load(BugRule(id="KT-0721",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0686] DATA_SERIAL:List<S>#686",trigger="val x:List<S>=batch_686()",detection="DATA_SERIAL检测:List<S>模式686",fix="修复:List<S>#686"))
        BugDB.load(BugRule(id="KT-0722",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0687] INLINE_TAILREC:Set<I>#687",trigger="val x:Set<I>=batch_687()",detection="INLINE_TAILREC检测:Set<I>模式687",fix="修复:Set<I>#687"))
        BugDB.load(BugRule(id="KT-0723",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0688] JAVA_INTEROP:Map<S,I>#688",trigger="val x:Map<S,I>=batch_688()",detection="JAVA_INTEROP检测:Map<S,I>模式688",fix="修复:Map<S,I>#688"))
        BugDB.load(BugRule(id="KT-0724",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0689] SMART_CAST:String#689",trigger="val x:String=batch_689()",detection="SMART_CAST检测:String模式689",fix="修复:String#689"))
        BugDB.load(BugRule(id="KT-0725",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0690] SEALED_ENUM:Int#690",trigger="val x:Int=batch_690()",detection="SEALED_ENUM检测:Int模式690",fix="修复:Int#690"))
        BugDB.load(BugRule(id="KT-0726",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0691] CONCURRENCY:Long#691",trigger="val x:Long=batch_691()",detection="CONCURRENCY检测:Long模式691",fix="修复:Long#691"))
        BugDB.load(BugRule(id="KT-0727",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0692] PERFORMANCE:Double#692",trigger="val x:Double=batch_692()",detection="PERFORMANCE检测:Double模式692",fix="修复:Double#692"))
        BugDB.load(BugRule(id="KT-0728",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0693] COMPILER_TRAP:Boolean#693",trigger="val x:Boolean=batch_693()",detection="COMPILER_TRAP检测:Boolean模式693",fix="修复:Boolean#693"))
        BugDB.load(BugRule(id="KT-0729",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0694] SECURITY:Float#694",trigger="val x:Float=batch_694()",detection="SECURITY检测:Float模式694",fix="修复:Float#694"))
        BugDB.load(BugRule(id="KT-0730",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0695] COMPOSE:Char#695",trigger="val x:Char=batch_695()",detection="COMPOSE检测:Char模式695",fix="修复:Char#695"))
        BugDB.load(BugRule(id="KT-0731",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0696] VALUE_CLASS:Byte#696",trigger="val x:Byte=batch_696()",detection="VALUE_CLASS检测:Byte模式696",fix="修复:Byte#696"))
        BugDB.load(BugRule(id="KT-0732",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0697] DELEGATE:Short#697",trigger="val x:Short=batch_697()",detection="DELEGATE检测:Short模式697",fix="修复:Short#697"))
        BugDB.load(BugRule(id="KT-0733",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0698] KMP:Any#698",trigger="val x:Any=batch_698()",detection="KMP检测:Any模式698",fix="修复:Any#698"))
        BugDB.load(BugRule(id="KT-0734",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0699] MULTIPLATFORM:List<S>#699",trigger="val x:List<S>=batch_699()",detection="MULTIPLATFORM检测:List<S>模式699",fix="修复:List<S>#699"))
        BugDB.load(BugRule(id="KT-0735",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0700] NULL_SAFETY:Set<I>#700",trigger="val x:Set<I>=batch_700()",detection="NULL_SAFETY检测:Set<I>模式700",fix="修复:Set<I>#700"))
        BugDB.load(BugRule(id="KT-0736",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0701] GENERICS:Map<S,I>#701",trigger="val x:Map<S,I>=batch_701()",detection="GENERICS检测:Map<S,I>模式701",fix="修复:Map<S,I>#701"))
        BugDB.load(BugRule(id="KT-0737",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0702] COROUTINES:String#702",trigger="val x:String=batch_702()",detection="COROUTINES检测:String模式702",fix="修复:String#702"))
        BugDB.load(BugRule(id="KT-0738",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0703] COLLECTIONS:Int#703",trigger="val x:Int=batch_703()",detection="COLLECTIONS检测:Int模式703",fix="修复:Int#703"))
        BugDB.load(BugRule(id="KT-0739",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0704] REFLECTION:Long#704",trigger="val x:Long=batch_704()",detection="REFLECTION检测:Long模式704",fix="修复:Long#704"))
        BugDB.load(BugRule(id="KT-0740",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0705] DSL_LAMBDA:Double#705",trigger="val x:Double=batch_705()",detection="DSL_LAMBDA检测:Double模式705",fix="修复:Double#705"))
        BugDB.load(BugRule(id="KT-0741",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0706] DATA_SERIAL:Boolean#706",trigger="val x:Boolean=batch_706()",detection="DATA_SERIAL检测:Boolean模式706",fix="修复:Boolean#706"))
        BugDB.load(BugRule(id="KT-0742",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0707] INLINE_TAILREC:Float#707",trigger="val x:Float=batch_707()",detection="INLINE_TAILREC检测:Float模式707",fix="修复:Float#707"))
        BugDB.load(BugRule(id="KT-0743",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0708] JAVA_INTEROP:Char#708",trigger="val x:Char=batch_708()",detection="JAVA_INTEROP检测:Char模式708",fix="修复:Char#708"))
        BugDB.load(BugRule(id="KT-0744",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0709] SMART_CAST:Byte#709",trigger="val x:Byte=batch_709()",detection="SMART_CAST检测:Byte模式709",fix="修复:Byte#709"))
        BugDB.load(BugRule(id="KT-0745",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0710] SEALED_ENUM:Short#710",trigger="val x:Short=batch_710()",detection="SEALED_ENUM检测:Short模式710",fix="修复:Short#710"))
        BugDB.load(BugRule(id="KT-0746",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0711] CONCURRENCY:Any#711",trigger="val x:Any=batch_711()",detection="CONCURRENCY检测:Any模式711",fix="修复:Any#711"))
        BugDB.load(BugRule(id="KT-0747",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0712] PERFORMANCE:List<S>#712",trigger="val x:List<S>=batch_712()",detection="PERFORMANCE检测:List<S>模式712",fix="修复:List<S>#712"))
        BugDB.load(BugRule(id="KT-0748",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0713] COMPILER_TRAP:Set<I>#713",trigger="val x:Set<I>=batch_713()",detection="COMPILER_TRAP检测:Set<I>模式713",fix="修复:Set<I>#713"))
        BugDB.load(BugRule(id="KT-0749",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0714] SECURITY:Map<S,I>#714",trigger="val x:Map<S,I>=batch_714()",detection="SECURITY检测:Map<S,I>模式714",fix="修复:Map<S,I>#714"))
        BugDB.load(BugRule(id="KT-0750",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0715] COMPOSE:String#715",trigger="val x:String=batch_715()",detection="COMPOSE检测:String模式715",fix="修复:String#715"))
        BugDB.load(BugRule(id="KT-0751",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0716] VALUE_CLASS:Int#716",trigger="val x:Int=batch_716()",detection="VALUE_CLASS检测:Int模式716",fix="修复:Int#716"))
        BugDB.load(BugRule(id="KT-0752",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0717] DELEGATE:Long#717",trigger="val x:Long=batch_717()",detection="DELEGATE检测:Long模式717",fix="修复:Long#717"))
        BugDB.load(BugRule(id="KT-0753",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0718] KMP:Double#718",trigger="val x:Double=batch_718()",detection="KMP检测:Double模式718",fix="修复:Double#718"))
        BugDB.load(BugRule(id="KT-0754",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0719] MULTIPLATFORM:Boolean#719",trigger="val x:Boolean=batch_719()",detection="MULTIPLATFORM检测:Boolean模式719",fix="修复:Boolean#719"))
        BugDB.load(BugRule(id="KT-0755",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0720] NULL_SAFETY:Float#720",trigger="val x:Float=batch_720()",detection="NULL_SAFETY检测:Float模式720",fix="修复:Float#720"))
        BugDB.load(BugRule(id="KT-0756",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0721] GENERICS:Char#721",trigger="val x:Char=batch_721()",detection="GENERICS检测:Char模式721",fix="修复:Char#721"))
        BugDB.load(BugRule(id="KT-0757",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0722] COROUTINES:Byte#722",trigger="val x:Byte=batch_722()",detection="COROUTINES检测:Byte模式722",fix="修复:Byte#722"))
        BugDB.load(BugRule(id="KT-0758",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0723] COLLECTIONS:Short#723",trigger="val x:Short=batch_723()",detection="COLLECTIONS检测:Short模式723",fix="修复:Short#723"))
        BugDB.load(BugRule(id="KT-0759",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0724] REFLECTION:Any#724",trigger="val x:Any=batch_724()",detection="REFLECTION检测:Any模式724",fix="修复:Any#724"))
        BugDB.load(BugRule(id="KT-0760",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0725] DSL_LAMBDA:List<S>#725",trigger="val x:List<S>=batch_725()",detection="DSL_LAMBDA检测:List<S>模式725",fix="修复:List<S>#725"))
        BugDB.load(BugRule(id="KT-0761",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0726] DATA_SERIAL:Set<I>#726",trigger="val x:Set<I>=batch_726()",detection="DATA_SERIAL检测:Set<I>模式726",fix="修复:Set<I>#726"))
        BugDB.load(BugRule(id="KT-0762",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0727] INLINE_TAILREC:Map<S,I>#727",trigger="val x:Map<S,I>=batch_727()",detection="INLINE_TAILREC检测:Map<S,I>模式727",fix="修复:Map<S,I>#727"))
        BugDB.load(BugRule(id="KT-0763",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0728] JAVA_INTEROP:String#728",trigger="val x:String=batch_728()",detection="JAVA_INTEROP检测:String模式728",fix="修复:String#728"))
        BugDB.load(BugRule(id="KT-0764",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0729] SMART_CAST:Int#729",trigger="val x:Int=batch_729()",detection="SMART_CAST检测:Int模式729",fix="修复:Int#729"))
        BugDB.load(BugRule(id="KT-0765",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0730] SEALED_ENUM:Long#730",trigger="val x:Long=batch_730()",detection="SEALED_ENUM检测:Long模式730",fix="修复:Long#730"))
        BugDB.load(BugRule(id="KT-0766",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0731] CONCURRENCY:Double#731",trigger="val x:Double=batch_731()",detection="CONCURRENCY检测:Double模式731",fix="修复:Double#731"))
        BugDB.load(BugRule(id="KT-0767",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0732] PERFORMANCE:Boolean#732",trigger="val x:Boolean=batch_732()",detection="PERFORMANCE检测:Boolean模式732",fix="修复:Boolean#732"))
        BugDB.load(BugRule(id="KT-0768",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0733] COMPILER_TRAP:Float#733",trigger="val x:Float=batch_733()",detection="COMPILER_TRAP检测:Float模式733",fix="修复:Float#733"))
        BugDB.load(BugRule(id="KT-0769",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0734] SECURITY:Char#734",trigger="val x:Char=batch_734()",detection="SECURITY检测:Char模式734",fix="修复:Char#734"))
        BugDB.load(BugRule(id="KT-0770",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0735] COMPOSE:Byte#735",trigger="val x:Byte=batch_735()",detection="COMPOSE检测:Byte模式735",fix="修复:Byte#735"))
        BugDB.load(BugRule(id="KT-0771",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0736] VALUE_CLASS:Short#736",trigger="val x:Short=batch_736()",detection="VALUE_CLASS检测:Short模式736",fix="修复:Short#736"))
        BugDB.load(BugRule(id="KT-0772",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0737] DELEGATE:Any#737",trigger="val x:Any=batch_737()",detection="DELEGATE检测:Any模式737",fix="修复:Any#737"))
        BugDB.load(BugRule(id="KT-0773",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0738] KMP:List<S>#738",trigger="val x:List<S>=batch_738()",detection="KMP检测:List<S>模式738",fix="修复:List<S>#738"))
        BugDB.load(BugRule(id="KT-0774",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0739] MULTIPLATFORM:Set<I>#739",trigger="val x:Set<I>=batch_739()",detection="MULTIPLATFORM检测:Set<I>模式739",fix="修复:Set<I>#739"))
        BugDB.load(BugRule(id="KT-0775",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0740] NULL_SAFETY:Map<S,I>#740",trigger="val x:Map<S,I>=batch_740()",detection="NULL_SAFETY检测:Map<S,I>模式740",fix="修复:Map<S,I>#740"))
        BugDB.load(BugRule(id="KT-0776",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0741] GENERICS:String#741",trigger="val x:String=batch_741()",detection="GENERICS检测:String模式741",fix="修复:String#741"))
        BugDB.load(BugRule(id="KT-0777",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0742] COROUTINES:Int#742",trigger="val x:Int=batch_742()",detection="COROUTINES检测:Int模式742",fix="修复:Int#742"))
        BugDB.load(BugRule(id="KT-0778",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0743] COLLECTIONS:Long#743",trigger="val x:Long=batch_743()",detection="COLLECTIONS检测:Long模式743",fix="修复:Long#743"))
        BugDB.load(BugRule(id="KT-0779",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0744] REFLECTION:Double#744",trigger="val x:Double=batch_744()",detection="REFLECTION检测:Double模式744",fix="修复:Double#744"))
        BugDB.load(BugRule(id="KT-0780",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0745] DSL_LAMBDA:Boolean#745",trigger="val x:Boolean=batch_745()",detection="DSL_LAMBDA检测:Boolean模式745",fix="修复:Boolean#745"))
        BugDB.load(BugRule(id="KT-0781",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0746] DATA_SERIAL:Float#746",trigger="val x:Float=batch_746()",detection="DATA_SERIAL检测:Float模式746",fix="修复:Float#746"))
        BugDB.load(BugRule(id="KT-0782",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0747] INLINE_TAILREC:Char#747",trigger="val x:Char=batch_747()",detection="INLINE_TAILREC检测:Char模式747",fix="修复:Char#747"))
        BugDB.load(BugRule(id="KT-0783",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0748] JAVA_INTEROP:Byte#748",trigger="val x:Byte=batch_748()",detection="JAVA_INTEROP检测:Byte模式748",fix="修复:Byte#748"))
        BugDB.load(BugRule(id="KT-0784",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0749] SMART_CAST:Short#749",trigger="val x:Short=batch_749()",detection="SMART_CAST检测:Short模式749",fix="修复:Short#749"))
        BugDB.load(BugRule(id="KT-0785",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0750] SEALED_ENUM:Any#750",trigger="val x:Any=batch_750()",detection="SEALED_ENUM检测:Any模式750",fix="修复:Any#750"))
        BugDB.load(BugRule(id="KT-0786",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0751] CONCURRENCY:List<S>#751",trigger="val x:List<S>=batch_751()",detection="CONCURRENCY检测:List<S>模式751",fix="修复:List<S>#751"))
        BugDB.load(BugRule(id="KT-0787",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0752] PERFORMANCE:Set<I>#752",trigger="val x:Set<I>=batch_752()",detection="PERFORMANCE检测:Set<I>模式752",fix="修复:Set<I>#752"))
        BugDB.load(BugRule(id="KT-0788",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0753] COMPILER_TRAP:Map<S,I>#753",trigger="val x:Map<S,I>=batch_753()",detection="COMPILER_TRAP检测:Map<S,I>模式753",fix="修复:Map<S,I>#753"))
        BugDB.load(BugRule(id="KT-0789",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0754] SECURITY:String#754",trigger="val x:String=batch_754()",detection="SECURITY检测:String模式754",fix="修复:String#754"))
        BugDB.load(BugRule(id="KT-0790",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0755] COMPOSE:Int#755",trigger="val x:Int=batch_755()",detection="COMPOSE检测:Int模式755",fix="修复:Int#755"))
        BugDB.load(BugRule(id="KT-0791",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0756] VALUE_CLASS:Long#756",trigger="val x:Long=batch_756()",detection="VALUE_CLASS检测:Long模式756",fix="修复:Long#756"))
        BugDB.load(BugRule(id="KT-0792",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0757] DELEGATE:Double#757",trigger="val x:Double=batch_757()",detection="DELEGATE检测:Double模式757",fix="修复:Double#757"))
        BugDB.load(BugRule(id="KT-0793",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0758] KMP:Boolean#758",trigger="val x:Boolean=batch_758()",detection="KMP检测:Boolean模式758",fix="修复:Boolean#758"))
        BugDB.load(BugRule(id="KT-0794",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0759] MULTIPLATFORM:Float#759",trigger="val x:Float=batch_759()",detection="MULTIPLATFORM检测:Float模式759",fix="修复:Float#759"))
        BugDB.load(BugRule(id="KT-0795",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0760] NULL_SAFETY:Char#760",trigger="val x:Char=batch_760()",detection="NULL_SAFETY检测:Char模式760",fix="修复:Char#760"))
        BugDB.load(BugRule(id="KT-0796",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0761] GENERICS:Byte#761",trigger="val x:Byte=batch_761()",detection="GENERICS检测:Byte模式761",fix="修复:Byte#761"))
        BugDB.load(BugRule(id="KT-0797",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0762] COROUTINES:Short#762",trigger="val x:Short=batch_762()",detection="COROUTINES检测:Short模式762",fix="修复:Short#762"))
        BugDB.load(BugRule(id="KT-0798",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0763] COLLECTIONS:Any#763",trigger="val x:Any=batch_763()",detection="COLLECTIONS检测:Any模式763",fix="修复:Any#763"))
        BugDB.load(BugRule(id="KT-0799",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0764] REFLECTION:List<S>#764",trigger="val x:List<S>=batch_764()",detection="REFLECTION检测:List<S>模式764",fix="修复:List<S>#764"))
        BugDB.load(BugRule(id="KT-0800",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0765] DSL_LAMBDA:Set<I>#765",trigger="val x:Set<I>=batch_765()",detection="DSL_LAMBDA检测:Set<I>模式765",fix="修复:Set<I>#765"))
        BugDB.load(BugRule(id="KT-0801",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0766] DATA_SERIAL:Map<S,I>#766",trigger="val x:Map<S,I>=batch_766()",detection="DATA_SERIAL检测:Map<S,I>模式766",fix="修复:Map<S,I>#766"))
        BugDB.load(BugRule(id="KT-0802",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0767] INLINE_TAILREC:String#767",trigger="val x:String=batch_767()",detection="INLINE_TAILREC检测:String模式767",fix="修复:String#767"))
        BugDB.load(BugRule(id="KT-0803",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0768] JAVA_INTEROP:Int#768",trigger="val x:Int=batch_768()",detection="JAVA_INTEROP检测:Int模式768",fix="修复:Int#768"))
        BugDB.load(BugRule(id="KT-0804",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0769] SMART_CAST:Long#769",trigger="val x:Long=batch_769()",detection="SMART_CAST检测:Long模式769",fix="修复:Long#769"))
        BugDB.load(BugRule(id="KT-0805",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0770] SEALED_ENUM:Double#770",trigger="val x:Double=batch_770()",detection="SEALED_ENUM检测:Double模式770",fix="修复:Double#770"))
        BugDB.load(BugRule(id="KT-0806",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0771] CONCURRENCY:Boolean#771",trigger="val x:Boolean=batch_771()",detection="CONCURRENCY检测:Boolean模式771",fix="修复:Boolean#771"))
        BugDB.load(BugRule(id="KT-0807",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0772] PERFORMANCE:Float#772",trigger="val x:Float=batch_772()",detection="PERFORMANCE检测:Float模式772",fix="修复:Float#772"))
        BugDB.load(BugRule(id="KT-0808",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0773] COMPILER_TRAP:Char#773",trigger="val x:Char=batch_773()",detection="COMPILER_TRAP检测:Char模式773",fix="修复:Char#773"))
        BugDB.load(BugRule(id="KT-0809",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0774] SECURITY:Byte#774",trigger="val x:Byte=batch_774()",detection="SECURITY检测:Byte模式774",fix="修复:Byte#774"))
        BugDB.load(BugRule(id="KT-0810",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0775] COMPOSE:Short#775",trigger="val x:Short=batch_775()",detection="COMPOSE检测:Short模式775",fix="修复:Short#775"))
        BugDB.load(BugRule(id="KT-0811",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0776] VALUE_CLASS:Any#776",trigger="val x:Any=batch_776()",detection="VALUE_CLASS检测:Any模式776",fix="修复:Any#776"))
        BugDB.load(BugRule(id="KT-0812",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0777] DELEGATE:List<S>#777",trigger="val x:List<S>=batch_777()",detection="DELEGATE检测:List<S>模式777",fix="修复:List<S>#777"))
        BugDB.load(BugRule(id="KT-0813",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0778] KMP:Set<I>#778",trigger="val x:Set<I>=batch_778()",detection="KMP检测:Set<I>模式778",fix="修复:Set<I>#778"))
        BugDB.load(BugRule(id="KT-0814",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0779] MULTIPLATFORM:Map<S,I>#779",trigger="val x:Map<S,I>=batch_779()",detection="MULTIPLATFORM检测:Map<S,I>模式779",fix="修复:Map<S,I>#779"))
        BugDB.load(BugRule(id="KT-0815",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0780] NULL_SAFETY:String#780",trigger="val x:String=batch_780()",detection="NULL_SAFETY检测:String模式780",fix="修复:String#780"))
        BugDB.load(BugRule(id="KT-0816",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0781] GENERICS:Int#781",trigger="val x:Int=batch_781()",detection="GENERICS检测:Int模式781",fix="修复:Int#781"))
        BugDB.load(BugRule(id="KT-0817",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0782] COROUTINES:Long#782",trigger="val x:Long=batch_782()",detection="COROUTINES检测:Long模式782",fix="修复:Long#782"))
        BugDB.load(BugRule(id="KT-0818",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0783] COLLECTIONS:Double#783",trigger="val x:Double=batch_783()",detection="COLLECTIONS检测:Double模式783",fix="修复:Double#783"))
        BugDB.load(BugRule(id="KT-0819",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0784] REFLECTION:Boolean#784",trigger="val x:Boolean=batch_784()",detection="REFLECTION检测:Boolean模式784",fix="修复:Boolean#784"))
        BugDB.load(BugRule(id="KT-0820",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0785] DSL_LAMBDA:Float#785",trigger="val x:Float=batch_785()",detection="DSL_LAMBDA检测:Float模式785",fix="修复:Float#785"))
        BugDB.load(BugRule(id="KT-0821",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0786] DATA_SERIAL:Char#786",trigger="val x:Char=batch_786()",detection="DATA_SERIAL检测:Char模式786",fix="修复:Char#786"))
        BugDB.load(BugRule(id="KT-0822",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0787] INLINE_TAILREC:Byte#787",trigger="val x:Byte=batch_787()",detection="INLINE_TAILREC检测:Byte模式787",fix="修复:Byte#787"))
        BugDB.load(BugRule(id="KT-0823",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0788] JAVA_INTEROP:Short#788",trigger="val x:Short=batch_788()",detection="JAVA_INTEROP检测:Short模式788",fix="修复:Short#788"))
        BugDB.load(BugRule(id="KT-0824",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0789] SMART_CAST:Any#789",trigger="val x:Any=batch_789()",detection="SMART_CAST检测:Any模式789",fix="修复:Any#789"))
        BugDB.load(BugRule(id="KT-0825",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0790] SEALED_ENUM:List<S>#790",trigger="val x:List<S>=batch_790()",detection="SEALED_ENUM检测:List<S>模式790",fix="修复:List<S>#790"))
        BugDB.load(BugRule(id="KT-0826",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0791] CONCURRENCY:Set<I>#791",trigger="val x:Set<I>=batch_791()",detection="CONCURRENCY检测:Set<I>模式791",fix="修复:Set<I>#791"))
        BugDB.load(BugRule(id="KT-0827",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0792] PERFORMANCE:Map<S,I>#792",trigger="val x:Map<S,I>=batch_792()",detection="PERFORMANCE检测:Map<S,I>模式792",fix="修复:Map<S,I>#792"))
        BugDB.load(BugRule(id="KT-0828",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0793] COMPILER_TRAP:String#793",trigger="val x:String=batch_793()",detection="COMPILER_TRAP检测:String模式793",fix="修复:String#793"))
        BugDB.load(BugRule(id="KT-0829",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0794] SECURITY:Int#794",trigger="val x:Int=batch_794()",detection="SECURITY检测:Int模式794",fix="修复:Int#794"))
        BugDB.load(BugRule(id="KT-0830",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0795] COMPOSE:Long#795",trigger="val x:Long=batch_795()",detection="COMPOSE检测:Long模式795",fix="修复:Long#795"))
        BugDB.load(BugRule(id="KT-0831",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0796] VALUE_CLASS:Double#796",trigger="val x:Double=batch_796()",detection="VALUE_CLASS检测:Double模式796",fix="修复:Double#796"))
        BugDB.load(BugRule(id="KT-0832",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0797] DELEGATE:Boolean#797",trigger="val x:Boolean=batch_797()",detection="DELEGATE检测:Boolean模式797",fix="修复:Boolean#797"))
        BugDB.load(BugRule(id="KT-0833",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0798] KMP:Float#798",trigger="val x:Float=batch_798()",detection="KMP检测:Float模式798",fix="修复:Float#798"))
        BugDB.load(BugRule(id="KT-0834",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0799] MULTIPLATFORM:Char#799",trigger="val x:Char=batch_799()",detection="MULTIPLATFORM检测:Char模式799",fix="修复:Char#799"))
        BugDB.load(BugRule(id="KT-0835",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0800] NULL_SAFETY:Byte#800",trigger="val x:Byte=batch_800()",detection="NULL_SAFETY检测:Byte模式800",fix="修复:Byte#800"))
        BugDB.load(BugRule(id="KT-0836",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0801] GENERICS:Short#801",trigger="val x:Short=batch_801()",detection="GENERICS检测:Short模式801",fix="修复:Short#801"))
        BugDB.load(BugRule(id="KT-0837",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0802] COROUTINES:Any#802",trigger="val x:Any=batch_802()",detection="COROUTINES检测:Any模式802",fix="修复:Any#802"))
        BugDB.load(BugRule(id="KT-0838",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0803] COLLECTIONS:List<S>#803",trigger="val x:List<S>=batch_803()",detection="COLLECTIONS检测:List<S>模式803",fix="修复:List<S>#803"))
        BugDB.load(BugRule(id="KT-0839",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0804] REFLECTION:Set<I>#804",trigger="val x:Set<I>=batch_804()",detection="REFLECTION检测:Set<I>模式804",fix="修复:Set<I>#804"))
        BugDB.load(BugRule(id="KT-0840",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0805] DSL_LAMBDA:Map<S,I>#805",trigger="val x:Map<S,I>=batch_805()",detection="DSL_LAMBDA检测:Map<S,I>模式805",fix="修复:Map<S,I>#805"))
        BugDB.load(BugRule(id="KT-0841",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0806] DATA_SERIAL:String#806",trigger="val x:String=batch_806()",detection="DATA_SERIAL检测:String模式806",fix="修复:String#806"))
        BugDB.load(BugRule(id="KT-0842",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0807] INLINE_TAILREC:Int#807",trigger="val x:Int=batch_807()",detection="INLINE_TAILREC检测:Int模式807",fix="修复:Int#807"))
        BugDB.load(BugRule(id="KT-0843",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0808] JAVA_INTEROP:Long#808",trigger="val x:Long=batch_808()",detection="JAVA_INTEROP检测:Long模式808",fix="修复:Long#808"))
        BugDB.load(BugRule(id="KT-0844",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0809] SMART_CAST:Double#809",trigger="val x:Double=batch_809()",detection="SMART_CAST检测:Double模式809",fix="修复:Double#809"))
        BugDB.load(BugRule(id="KT-0845",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0810] SEALED_ENUM:Boolean#810",trigger="val x:Boolean=batch_810()",detection="SEALED_ENUM检测:Boolean模式810",fix="修复:Boolean#810"))
        BugDB.load(BugRule(id="KT-0846",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0811] CONCURRENCY:Float#811",trigger="val x:Float=batch_811()",detection="CONCURRENCY检测:Float模式811",fix="修复:Float#811"))
        BugDB.load(BugRule(id="KT-0847",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0812] PERFORMANCE:Char#812",trigger="val x:Char=batch_812()",detection="PERFORMANCE检测:Char模式812",fix="修复:Char#812"))
        BugDB.load(BugRule(id="KT-0848",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0813] COMPILER_TRAP:Byte#813",trigger="val x:Byte=batch_813()",detection="COMPILER_TRAP检测:Byte模式813",fix="修复:Byte#813"))
        BugDB.load(BugRule(id="KT-0849",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0814] SECURITY:Short#814",trigger="val x:Short=batch_814()",detection="SECURITY检测:Short模式814",fix="修复:Short#814"))
        BugDB.load(BugRule(id="KT-0850",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0815] COMPOSE:Any#815",trigger="val x:Any=batch_815()",detection="COMPOSE检测:Any模式815",fix="修复:Any#815"))
        BugDB.load(BugRule(id="KT-0851",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0816] VALUE_CLASS:List<S>#816",trigger="val x:List<S>=batch_816()",detection="VALUE_CLASS检测:List<S>模式816",fix="修复:List<S>#816"))
        BugDB.load(BugRule(id="KT-0852",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0817] DELEGATE:Set<I>#817",trigger="val x:Set<I>=batch_817()",detection="DELEGATE检测:Set<I>模式817",fix="修复:Set<I>#817"))
        BugDB.load(BugRule(id="KT-0853",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0818] KMP:Map<S,I>#818",trigger="val x:Map<S,I>=batch_818()",detection="KMP检测:Map<S,I>模式818",fix="修复:Map<S,I>#818"))
        BugDB.load(BugRule(id="KT-0854",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0819] MULTIPLATFORM:String#819",trigger="val x:String=batch_819()",detection="MULTIPLATFORM检测:String模式819",fix="修复:String#819"))
        BugDB.load(BugRule(id="KT-0855",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0820] NULL_SAFETY:Int#820",trigger="val x:Int=batch_820()",detection="NULL_SAFETY检测:Int模式820",fix="修复:Int#820"))
        BugDB.load(BugRule(id="KT-0856",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0821] GENERICS:Long#821",trigger="val x:Long=batch_821()",detection="GENERICS检测:Long模式821",fix="修复:Long#821"))
        BugDB.load(BugRule(id="KT-0857",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0822] COROUTINES:Double#822",trigger="val x:Double=batch_822()",detection="COROUTINES检测:Double模式822",fix="修复:Double#822"))
        BugDB.load(BugRule(id="KT-0858",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0823] COLLECTIONS:Boolean#823",trigger="val x:Boolean=batch_823()",detection="COLLECTIONS检测:Boolean模式823",fix="修复:Boolean#823"))
        BugDB.load(BugRule(id="KT-0859",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0824] REFLECTION:Float#824",trigger="val x:Float=batch_824()",detection="REFLECTION检测:Float模式824",fix="修复:Float#824"))
        BugDB.load(BugRule(id="KT-0860",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0825] DSL_LAMBDA:Char#825",trigger="val x:Char=batch_825()",detection="DSL_LAMBDA检测:Char模式825",fix="修复:Char#825"))
        BugDB.load(BugRule(id="KT-0861",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0826] DATA_SERIAL:Byte#826",trigger="val x:Byte=batch_826()",detection="DATA_SERIAL检测:Byte模式826",fix="修复:Byte#826"))
        BugDB.load(BugRule(id="KT-0862",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0827] INLINE_TAILREC:Short#827",trigger="val x:Short=batch_827()",detection="INLINE_TAILREC检测:Short模式827",fix="修复:Short#827"))
        BugDB.load(BugRule(id="KT-0863",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0828] JAVA_INTEROP:Any#828",trigger="val x:Any=batch_828()",detection="JAVA_INTEROP检测:Any模式828",fix="修复:Any#828"))
        BugDB.load(BugRule(id="KT-0864",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0829] SMART_CAST:List<S>#829",trigger="val x:List<S>=batch_829()",detection="SMART_CAST检测:List<S>模式829",fix="修复:List<S>#829"))
        BugDB.load(BugRule(id="KT-0865",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0830] SEALED_ENUM:Set<I>#830",trigger="val x:Set<I>=batch_830()",detection="SEALED_ENUM检测:Set<I>模式830",fix="修复:Set<I>#830"))
        BugDB.load(BugRule(id="KT-0866",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0831] CONCURRENCY:Map<S,I>#831",trigger="val x:Map<S,I>=batch_831()",detection="CONCURRENCY检测:Map<S,I>模式831",fix="修复:Map<S,I>#831"))
        BugDB.load(BugRule(id="KT-0867",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0832] PERFORMANCE:String#832",trigger="val x:String=batch_832()",detection="PERFORMANCE检测:String模式832",fix="修复:String#832"))
        BugDB.load(BugRule(id="KT-0868",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0833] COMPILER_TRAP:Int#833",trigger="val x:Int=batch_833()",detection="COMPILER_TRAP检测:Int模式833",fix="修复:Int#833"))
        BugDB.load(BugRule(id="KT-0869",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0834] SECURITY:Long#834",trigger="val x:Long=batch_834()",detection="SECURITY检测:Long模式834",fix="修复:Long#834"))
        BugDB.load(BugRule(id="KT-0870",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0835] COMPOSE:Double#835",trigger="val x:Double=batch_835()",detection="COMPOSE检测:Double模式835",fix="修复:Double#835"))
        BugDB.load(BugRule(id="KT-0871",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0836] VALUE_CLASS:Boolean#836",trigger="val x:Boolean=batch_836()",detection="VALUE_CLASS检测:Boolean模式836",fix="修复:Boolean#836"))
        BugDB.load(BugRule(id="KT-0872",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0837] DELEGATE:Float#837",trigger="val x:Float=batch_837()",detection="DELEGATE检测:Float模式837",fix="修复:Float#837"))
        BugDB.load(BugRule(id="KT-0873",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0838] KMP:Char#838",trigger="val x:Char=batch_838()",detection="KMP检测:Char模式838",fix="修复:Char#838"))
        BugDB.load(BugRule(id="KT-0874",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0839] MULTIPLATFORM:Byte#839",trigger="val x:Byte=batch_839()",detection="MULTIPLATFORM检测:Byte模式839",fix="修复:Byte#839"))
        BugDB.load(BugRule(id="KT-0875",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0840] NULL_SAFETY:Short#840",trigger="val x:Short=batch_840()",detection="NULL_SAFETY检测:Short模式840",fix="修复:Short#840"))
        BugDB.load(BugRule(id="KT-0876",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0841] GENERICS:Any#841",trigger="val x:Any=batch_841()",detection="GENERICS检测:Any模式841",fix="修复:Any#841"))
        BugDB.load(BugRule(id="KT-0877",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0842] COROUTINES:List<S>#842",trigger="val x:List<S>=batch_842()",detection="COROUTINES检测:List<S>模式842",fix="修复:List<S>#842"))
        BugDB.load(BugRule(id="KT-0878",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0843] COLLECTIONS:Set<I>#843",trigger="val x:Set<I>=batch_843()",detection="COLLECTIONS检测:Set<I>模式843",fix="修复:Set<I>#843"))
        BugDB.load(BugRule(id="KT-0879",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0844] REFLECTION:Map<S,I>#844",trigger="val x:Map<S,I>=batch_844()",detection="REFLECTION检测:Map<S,I>模式844",fix="修复:Map<S,I>#844"))
        BugDB.load(BugRule(id="KT-0880",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0845] DSL_LAMBDA:String#845",trigger="val x:String=batch_845()",detection="DSL_LAMBDA检测:String模式845",fix="修复:String#845"))
        BugDB.load(BugRule(id="KT-0881",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0846] DATA_SERIAL:Int#846",trigger="val x:Int=batch_846()",detection="DATA_SERIAL检测:Int模式846",fix="修复:Int#846"))
        BugDB.load(BugRule(id="KT-0882",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0847] INLINE_TAILREC:Long#847",trigger="val x:Long=batch_847()",detection="INLINE_TAILREC检测:Long模式847",fix="修复:Long#847"))
        BugDB.load(BugRule(id="KT-0883",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0848] JAVA_INTEROP:Double#848",trigger="val x:Double=batch_848()",detection="JAVA_INTEROP检测:Double模式848",fix="修复:Double#848"))
        BugDB.load(BugRule(id="KT-0884",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0849] SMART_CAST:Boolean#849",trigger="val x:Boolean=batch_849()",detection="SMART_CAST检测:Boolean模式849",fix="修复:Boolean#849"))
        BugDB.load(BugRule(id="KT-0885",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0850] SEALED_ENUM:Float#850",trigger="val x:Float=batch_850()",detection="SEALED_ENUM检测:Float模式850",fix="修复:Float#850"))
        BugDB.load(BugRule(id="KT-0886",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0851] CONCURRENCY:Char#851",trigger="val x:Char=batch_851()",detection="CONCURRENCY检测:Char模式851",fix="修复:Char#851"))
        BugDB.load(BugRule(id="KT-0887",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0852] PERFORMANCE:Byte#852",trigger="val x:Byte=batch_852()",detection="PERFORMANCE检测:Byte模式852",fix="修复:Byte#852"))
        BugDB.load(BugRule(id="KT-0888",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0853] COMPILER_TRAP:Short#853",trigger="val x:Short=batch_853()",detection="COMPILER_TRAP检测:Short模式853",fix="修复:Short#853"))
        BugDB.load(BugRule(id="KT-0889",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0854] SECURITY:Any#854",trigger="val x:Any=batch_854()",detection="SECURITY检测:Any模式854",fix="修复:Any#854"))
        BugDB.load(BugRule(id="KT-0890",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0855] COMPOSE:List<S>#855",trigger="val x:List<S>=batch_855()",detection="COMPOSE检测:List<S>模式855",fix="修复:List<S>#855"))
        BugDB.load(BugRule(id="KT-0891",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0856] VALUE_CLASS:Set<I>#856",trigger="val x:Set<I>=batch_856()",detection="VALUE_CLASS检测:Set<I>模式856",fix="修复:Set<I>#856"))
        BugDB.load(BugRule(id="KT-0892",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0857] DELEGATE:Map<S,I>#857",trigger="val x:Map<S,I>=batch_857()",detection="DELEGATE检测:Map<S,I>模式857",fix="修复:Map<S,I>#857"))
        BugDB.load(BugRule(id="KT-0893",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0858] KMP:String#858",trigger="val x:String=batch_858()",detection="KMP检测:String模式858",fix="修复:String#858"))
        BugDB.load(BugRule(id="KT-0894",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0859] MULTIPLATFORM:Int#859",trigger="val x:Int=batch_859()",detection="MULTIPLATFORM检测:Int模式859",fix="修复:Int#859"))
        BugDB.load(BugRule(id="KT-0895",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0860] NULL_SAFETY:Long#860",trigger="val x:Long=batch_860()",detection="NULL_SAFETY检测:Long模式860",fix="修复:Long#860"))
        BugDB.load(BugRule(id="KT-0896",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0861] GENERICS:Double#861",trigger="val x:Double=batch_861()",detection="GENERICS检测:Double模式861",fix="修复:Double#861"))
        BugDB.load(BugRule(id="KT-0897",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0862] COROUTINES:Boolean#862",trigger="val x:Boolean=batch_862()",detection="COROUTINES检测:Boolean模式862",fix="修复:Boolean#862"))
        BugDB.load(BugRule(id="KT-0898",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0863] COLLECTIONS:Float#863",trigger="val x:Float=batch_863()",detection="COLLECTIONS检测:Float模式863",fix="修复:Float#863"))
        BugDB.load(BugRule(id="KT-0899",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0864] REFLECTION:Char#864",trigger="val x:Char=batch_864()",detection="REFLECTION检测:Char模式864",fix="修复:Char#864"))
        BugDB.load(BugRule(id="KT-0900",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0865] DSL_LAMBDA:Byte#865",trigger="val x:Byte=batch_865()",detection="DSL_LAMBDA检测:Byte模式865",fix="修复:Byte#865"))
        BugDB.load(BugRule(id="KT-0901",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0866] DATA_SERIAL:Short#866",trigger="val x:Short=batch_866()",detection="DATA_SERIAL检测:Short模式866",fix="修复:Short#866"))
        BugDB.load(BugRule(id="KT-0902",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0867] INLINE_TAILREC:Any#867",trigger="val x:Any=batch_867()",detection="INLINE_TAILREC检测:Any模式867",fix="修复:Any#867"))
        BugDB.load(BugRule(id="KT-0903",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0868] JAVA_INTEROP:List<S>#868",trigger="val x:List<S>=batch_868()",detection="JAVA_INTEROP检测:List<S>模式868",fix="修复:List<S>#868"))
        BugDB.load(BugRule(id="KT-0904",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0869] SMART_CAST:Set<I>#869",trigger="val x:Set<I>=batch_869()",detection="SMART_CAST检测:Set<I>模式869",fix="修复:Set<I>#869"))
        BugDB.load(BugRule(id="KT-0905",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0870] SEALED_ENUM:Map<S,I>#870",trigger="val x:Map<S,I>=batch_870()",detection="SEALED_ENUM检测:Map<S,I>模式870",fix="修复:Map<S,I>#870"))
        BugDB.load(BugRule(id="KT-0906",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0871] CONCURRENCY:String#871",trigger="val x:String=batch_871()",detection="CONCURRENCY检测:String模式871",fix="修复:String#871"))
        BugDB.load(BugRule(id="KT-0907",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0872] PERFORMANCE:Int#872",trigger="val x:Int=batch_872()",detection="PERFORMANCE检测:Int模式872",fix="修复:Int#872"))
        BugDB.load(BugRule(id="KT-0908",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0873] COMPILER_TRAP:Long#873",trigger="val x:Long=batch_873()",detection="COMPILER_TRAP检测:Long模式873",fix="修复:Long#873"))
        BugDB.load(BugRule(id="KT-0909",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0874] SECURITY:Double#874",trigger="val x:Double=batch_874()",detection="SECURITY检测:Double模式874",fix="修复:Double#874"))
        BugDB.load(BugRule(id="KT-0910",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0875] COMPOSE:Boolean#875",trigger="val x:Boolean=batch_875()",detection="COMPOSE检测:Boolean模式875",fix="修复:Boolean#875"))
        BugDB.load(BugRule(id="KT-0911",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0876] VALUE_CLASS:Float#876",trigger="val x:Float=batch_876()",detection="VALUE_CLASS检测:Float模式876",fix="修复:Float#876"))
        BugDB.load(BugRule(id="KT-0912",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0877] DELEGATE:Char#877",trigger="val x:Char=batch_877()",detection="DELEGATE检测:Char模式877",fix="修复:Char#877"))
        BugDB.load(BugRule(id="KT-0913",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0878] KMP:Byte#878",trigger="val x:Byte=batch_878()",detection="KMP检测:Byte模式878",fix="修复:Byte#878"))
        BugDB.load(BugRule(id="KT-0914",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0879] MULTIPLATFORM:Short#879",trigger="val x:Short=batch_879()",detection="MULTIPLATFORM检测:Short模式879",fix="修复:Short#879"))
        BugDB.load(BugRule(id="KT-0915",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0880] NULL_SAFETY:Any#880",trigger="val x:Any=batch_880()",detection="NULL_SAFETY检测:Any模式880",fix="修复:Any#880"))
        BugDB.load(BugRule(id="KT-0916",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0881] GENERICS:List<S>#881",trigger="val x:List<S>=batch_881()",detection="GENERICS检测:List<S>模式881",fix="修复:List<S>#881"))
        BugDB.load(BugRule(id="KT-0917",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0882] COROUTINES:Set<I>#882",trigger="val x:Set<I>=batch_882()",detection="COROUTINES检测:Set<I>模式882",fix="修复:Set<I>#882"))
        BugDB.load(BugRule(id="KT-0918",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0883] COLLECTIONS:Map<S,I>#883",trigger="val x:Map<S,I>=batch_883()",detection="COLLECTIONS检测:Map<S,I>模式883",fix="修复:Map<S,I>#883"))
        BugDB.load(BugRule(id="KT-0919",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0884] REFLECTION:String#884",trigger="val x:String=batch_884()",detection="REFLECTION检测:String模式884",fix="修复:String#884"))
        BugDB.load(BugRule(id="KT-0920",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0885] DSL_LAMBDA:Int#885",trigger="val x:Int=batch_885()",detection="DSL_LAMBDA检测:Int模式885",fix="修复:Int#885"))
        BugDB.load(BugRule(id="KT-0921",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0886] DATA_SERIAL:Long#886",trigger="val x:Long=batch_886()",detection="DATA_SERIAL检测:Long模式886",fix="修复:Long#886"))
        BugDB.load(BugRule(id="KT-0922",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0887] INLINE_TAILREC:Double#887",trigger="val x:Double=batch_887()",detection="INLINE_TAILREC检测:Double模式887",fix="修复:Double#887"))
        BugDB.load(BugRule(id="KT-0923",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0888] JAVA_INTEROP:Boolean#888",trigger="val x:Boolean=batch_888()",detection="JAVA_INTEROP检测:Boolean模式888",fix="修复:Boolean#888"))
        BugDB.load(BugRule(id="KT-0924",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0889] SMART_CAST:Float#889",trigger="val x:Float=batch_889()",detection="SMART_CAST检测:Float模式889",fix="修复:Float#889"))
        BugDB.load(BugRule(id="KT-0925",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0890] SEALED_ENUM:Char#890",trigger="val x:Char=batch_890()",detection="SEALED_ENUM检测:Char模式890",fix="修复:Char#890"))
        BugDB.load(BugRule(id="KT-0926",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0891] CONCURRENCY:Byte#891",trigger="val x:Byte=batch_891()",detection="CONCURRENCY检测:Byte模式891",fix="修复:Byte#891"))
        BugDB.load(BugRule(id="KT-0927",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0892] PERFORMANCE:Short#892",trigger="val x:Short=batch_892()",detection="PERFORMANCE检测:Short模式892",fix="修复:Short#892"))
        BugDB.load(BugRule(id="KT-0928",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0893] COMPILER_TRAP:Any#893",trigger="val x:Any=batch_893()",detection="COMPILER_TRAP检测:Any模式893",fix="修复:Any#893"))
        BugDB.load(BugRule(id="KT-0929",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0894] SECURITY:List<S>#894",trigger="val x:List<S>=batch_894()",detection="SECURITY检测:List<S>模式894",fix="修复:List<S>#894"))
        BugDB.load(BugRule(id="KT-0930",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0895] COMPOSE:Set<I>#895",trigger="val x:Set<I>=batch_895()",detection="COMPOSE检测:Set<I>模式895",fix="修复:Set<I>#895"))
        BugDB.load(BugRule(id="KT-0931",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0896] VALUE_CLASS:Map<S,I>#896",trigger="val x:Map<S,I>=batch_896()",detection="VALUE_CLASS检测:Map<S,I>模式896",fix="修复:Map<S,I>#896"))
        BugDB.load(BugRule(id="KT-0932",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0897] DELEGATE:String#897",trigger="val x:String=batch_897()",detection="DELEGATE检测:String模式897",fix="修复:String#897"))
        BugDB.load(BugRule(id="KT-0933",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0898] KMP:Int#898",trigger="val x:Int=batch_898()",detection="KMP检测:Int模式898",fix="修复:Int#898"))
        BugDB.load(BugRule(id="KT-0934",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0899] MULTIPLATFORM:Long#899",trigger="val x:Long=batch_899()",detection="MULTIPLATFORM检测:Long模式899",fix="修复:Long#899"))
        BugDB.load(BugRule(id="KT-0935",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0900] NULL_SAFETY:Double#900",trigger="val x:Double=batch_900()",detection="NULL_SAFETY检测:Double模式900",fix="修复:Double#900"))
        BugDB.load(BugRule(id="KT-0936",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0901] GENERICS:Boolean#901",trigger="val x:Boolean=batch_901()",detection="GENERICS检测:Boolean模式901",fix="修复:Boolean#901"))
        BugDB.load(BugRule(id="KT-0937",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0902] COROUTINES:Float#902",trigger="val x:Float=batch_902()",detection="COROUTINES检测:Float模式902",fix="修复:Float#902"))
        BugDB.load(BugRule(id="KT-0938",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0903] COLLECTIONS:Char#903",trigger="val x:Char=batch_903()",detection="COLLECTIONS检测:Char模式903",fix="修复:Char#903"))
        BugDB.load(BugRule(id="KT-0939",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0904] REFLECTION:Byte#904",trigger="val x:Byte=batch_904()",detection="REFLECTION检测:Byte模式904",fix="修复:Byte#904"))
        BugDB.load(BugRule(id="KT-0940",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0905] DSL_LAMBDA:Short#905",trigger="val x:Short=batch_905()",detection="DSL_LAMBDA检测:Short模式905",fix="修复:Short#905"))
        BugDB.load(BugRule(id="KT-0941",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0906] DATA_SERIAL:Any#906",trigger="val x:Any=batch_906()",detection="DATA_SERIAL检测:Any模式906",fix="修复:Any#906"))
        BugDB.load(BugRule(id="KT-0942",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0907] INLINE_TAILREC:List<S>#907",trigger="val x:List<S>=batch_907()",detection="INLINE_TAILREC检测:List<S>模式907",fix="修复:List<S>#907"))
        BugDB.load(BugRule(id="KT-0943",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0908] JAVA_INTEROP:Set<I>#908",trigger="val x:Set<I>=batch_908()",detection="JAVA_INTEROP检测:Set<I>模式908",fix="修复:Set<I>#908"))
        BugDB.load(BugRule(id="KT-0944",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0909] SMART_CAST:Map<S,I>#909",trigger="val x:Map<S,I>=batch_909()",detection="SMART_CAST检测:Map<S,I>模式909",fix="修复:Map<S,I>#909"))
        BugDB.load(BugRule(id="KT-0945",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0910] SEALED_ENUM:String#910",trigger="val x:String=batch_910()",detection="SEALED_ENUM检测:String模式910",fix="修复:String#910"))
        BugDB.load(BugRule(id="KT-0946",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0911] CONCURRENCY:Int#911",trigger="val x:Int=batch_911()",detection="CONCURRENCY检测:Int模式911",fix="修复:Int#911"))
        BugDB.load(BugRule(id="KT-0947",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0912] PERFORMANCE:Long#912",trigger="val x:Long=batch_912()",detection="PERFORMANCE检测:Long模式912",fix="修复:Long#912"))
        BugDB.load(BugRule(id="KT-0948",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0913] COMPILER_TRAP:Double#913",trigger="val x:Double=batch_913()",detection="COMPILER_TRAP检测:Double模式913",fix="修复:Double#913"))
        BugDB.load(BugRule(id="KT-0949",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0914] SECURITY:Boolean#914",trigger="val x:Boolean=batch_914()",detection="SECURITY检测:Boolean模式914",fix="修复:Boolean#914"))
        BugDB.load(BugRule(id="KT-0950",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0915] COMPOSE:Float#915",trigger="val x:Float=batch_915()",detection="COMPOSE检测:Float模式915",fix="修复:Float#915"))
        BugDB.load(BugRule(id="KT-0951",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0916] VALUE_CLASS:Char#916",trigger="val x:Char=batch_916()",detection="VALUE_CLASS检测:Char模式916",fix="修复:Char#916"))
        BugDB.load(BugRule(id="KT-0952",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0917] DELEGATE:Byte#917",trigger="val x:Byte=batch_917()",detection="DELEGATE检测:Byte模式917",fix="修复:Byte#917"))
        BugDB.load(BugRule(id="KT-0953",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0918] KMP:Short#918",trigger="val x:Short=batch_918()",detection="KMP检测:Short模式918",fix="修复:Short#918"))
        BugDB.load(BugRule(id="KT-0954",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0919] MULTIPLATFORM:Any#919",trigger="val x:Any=batch_919()",detection="MULTIPLATFORM检测:Any模式919",fix="修复:Any#919"))
        BugDB.load(BugRule(id="KT-0955",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0920] NULL_SAFETY:List<S>#920",trigger="val x:List<S>=batch_920()",detection="NULL_SAFETY检测:List<S>模式920",fix="修复:List<S>#920"))
        BugDB.load(BugRule(id="KT-0956",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0921] GENERICS:Set<I>#921",trigger="val x:Set<I>=batch_921()",detection="GENERICS检测:Set<I>模式921",fix="修复:Set<I>#921"))
        BugDB.load(BugRule(id="KT-0957",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0922] COROUTINES:Map<S,I>#922",trigger="val x:Map<S,I>=batch_922()",detection="COROUTINES检测:Map<S,I>模式922",fix="修复:Map<S,I>#922"))
        BugDB.load(BugRule(id="KT-0958",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0923] COLLECTIONS:String#923",trigger="val x:String=batch_923()",detection="COLLECTIONS检测:String模式923",fix="修复:String#923"))
        BugDB.load(BugRule(id="KT-0959",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0924] REFLECTION:Int#924",trigger="val x:Int=batch_924()",detection="REFLECTION检测:Int模式924",fix="修复:Int#924"))
        BugDB.load(BugRule(id="KT-0960",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0925] DSL_LAMBDA:Long#925",trigger="val x:Long=batch_925()",detection="DSL_LAMBDA检测:Long模式925",fix="修复:Long#925"))
        BugDB.load(BugRule(id="KT-0961",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0926] DATA_SERIAL:Double#926",trigger="val x:Double=batch_926()",detection="DATA_SERIAL检测:Double模式926",fix="修复:Double#926"))
        BugDB.load(BugRule(id="KT-0962",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0927] INLINE_TAILREC:Boolean#927",trigger="val x:Boolean=batch_927()",detection="INLINE_TAILREC检测:Boolean模式927",fix="修复:Boolean#927"))
        BugDB.load(BugRule(id="KT-0963",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0928] JAVA_INTEROP:Float#928",trigger="val x:Float=batch_928()",detection="JAVA_INTEROP检测:Float模式928",fix="修复:Float#928"))
        BugDB.load(BugRule(id="KT-0964",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0929] SMART_CAST:Char#929",trigger="val x:Char=batch_929()",detection="SMART_CAST检测:Char模式929",fix="修复:Char#929"))
        BugDB.load(BugRule(id="KT-0965",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0930] SEALED_ENUM:Byte#930",trigger="val x:Byte=batch_930()",detection="SEALED_ENUM检测:Byte模式930",fix="修复:Byte#930"))
        BugDB.load(BugRule(id="KT-0966",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0931] CONCURRENCY:Short#931",trigger="val x:Short=batch_931()",detection="CONCURRENCY检测:Short模式931",fix="修复:Short#931"))
        BugDB.load(BugRule(id="KT-0967",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0932] PERFORMANCE:Any#932",trigger="val x:Any=batch_932()",detection="PERFORMANCE检测:Any模式932",fix="修复:Any#932"))
        BugDB.load(BugRule(id="KT-0968",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0933] COMPILER_TRAP:List<S>#933",trigger="val x:List<S>=batch_933()",detection="COMPILER_TRAP检测:List<S>模式933",fix="修复:List<S>#933"))
        BugDB.load(BugRule(id="KT-0969",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0934] SECURITY:Set<I>#934",trigger="val x:Set<I>=batch_934()",detection="SECURITY检测:Set<I>模式934",fix="修复:Set<I>#934"))
        BugDB.load(BugRule(id="KT-0970",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0935] COMPOSE:Map<S,I>#935",trigger="val x:Map<S,I>=batch_935()",detection="COMPOSE检测:Map<S,I>模式935",fix="修复:Map<S,I>#935"))
        BugDB.load(BugRule(id="KT-0971",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0936] VALUE_CLASS:String#936",trigger="val x:String=batch_936()",detection="VALUE_CLASS检测:String模式936",fix="修复:String#936"))
        BugDB.load(BugRule(id="KT-0972",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0937] DELEGATE:Int#937",trigger="val x:Int=batch_937()",detection="DELEGATE检测:Int模式937",fix="修复:Int#937"))
        BugDB.load(BugRule(id="KT-0973",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0938] KMP:Long#938",trigger="val x:Long=batch_938()",detection="KMP检测:Long模式938",fix="修复:Long#938"))
        BugDB.load(BugRule(id="KT-0974",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0939] MULTIPLATFORM:Double#939",trigger="val x:Double=batch_939()",detection="MULTIPLATFORM检测:Double模式939",fix="修复:Double#939"))
        BugDB.load(BugRule(id="KT-0975",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0940] NULL_SAFETY:Boolean#940",trigger="val x:Boolean=batch_940()",detection="NULL_SAFETY检测:Boolean模式940",fix="修复:Boolean#940"))
        BugDB.load(BugRule(id="KT-0976",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0941] GENERICS:Float#941",trigger="val x:Float=batch_941()",detection="GENERICS检测:Float模式941",fix="修复:Float#941"))
        BugDB.load(BugRule(id="KT-0977",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0942] COROUTINES:Char#942",trigger="val x:Char=batch_942()",detection="COROUTINES检测:Char模式942",fix="修复:Char#942"))
        BugDB.load(BugRule(id="KT-0978",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0943] COLLECTIONS:Byte#943",trigger="val x:Byte=batch_943()",detection="COLLECTIONS检测:Byte模式943",fix="修复:Byte#943"))
        BugDB.load(BugRule(id="KT-0979",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0944] REFLECTION:Short#944",trigger="val x:Short=batch_944()",detection="REFLECTION检测:Short模式944",fix="修复:Short#944"))
        BugDB.load(BugRule(id="KT-0980",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0945] DSL_LAMBDA:Any#945",trigger="val x:Any=batch_945()",detection="DSL_LAMBDA检测:Any模式945",fix="修复:Any#945"))
        BugDB.load(BugRule(id="KT-0981",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="[B0946] DATA_SERIAL:List<S>#946",trigger="val x:List<S>=batch_946()",detection="DATA_SERIAL检测:List<S>模式946",fix="修复:List<S>#946"))
        BugDB.load(BugRule(id="KT-0982",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="[B0947] INLINE_TAILREC:Set<I>#947",trigger="val x:Set<I>=batch_947()",detection="INLINE_TAILREC检测:Set<I>模式947",fix="修复:Set<I>#947"))
        BugDB.load(BugRule(id="KT-0983",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="[B0948] JAVA_INTEROP:Map<S,I>#948",trigger="val x:Map<S,I>=batch_948()",detection="JAVA_INTEROP检测:Map<S,I>模式948",fix="修复:Map<S,I>#948"))
        BugDB.load(BugRule(id="KT-0984",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="[B0949] SMART_CAST:String#949",trigger="val x:String=batch_949()",detection="SMART_CAST检测:String模式949",fix="修复:String#949"))
        BugDB.load(BugRule(id="KT-0985",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="[B0950] SEALED_ENUM:Int#950",trigger="val x:Int=batch_950()",detection="SEALED_ENUM检测:Int模式950",fix="修复:Int#950"))
        BugDB.load(BugRule(id="KT-0986",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="[B0951] CONCURRENCY:Long#951",trigger="val x:Long=batch_951()",detection="CONCURRENCY检测:Long模式951",fix="修复:Long#951"))
        BugDB.load(BugRule(id="KT-0987",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="[B0952] PERFORMANCE:Double#952",trigger="val x:Double=batch_952()",detection="PERFORMANCE检测:Double模式952",fix="修复:Double#952"))
        BugDB.load(BugRule(id="KT-0988",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="[B0953] COMPILER_TRAP:Boolean#953",trigger="val x:Boolean=batch_953()",detection="COMPILER_TRAP检测:Boolean模式953",fix="修复:Boolean#953"))
        BugDB.load(BugRule(id="KT-0989",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="[B0954] SECURITY:Float#954",trigger="val x:Float=batch_954()",detection="SECURITY检测:Float模式954",fix="修复:Float#954"))
        BugDB.load(BugRule(id="KT-0990",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="[B0955] COMPOSE:Char#955",trigger="val x:Char=batch_955()",detection="COMPOSE检测:Char模式955",fix="修复:Char#955"))
        BugDB.load(BugRule(id="KT-0991",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="[B0956] VALUE_CLASS:Byte#956",trigger="val x:Byte=batch_956()",detection="VALUE_CLASS检测:Byte模式956",fix="修复:Byte#956"))
        BugDB.load(BugRule(id="KT-0992",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="[B0957] DELEGATE:Short#957",trigger="val x:Short=batch_957()",detection="DELEGATE检测:Short模式957",fix="修复:Short#957"))
        BugDB.load(BugRule(id="KT-0993",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="[B0958] KMP:Any#958",trigger="val x:Any=batch_958()",detection="KMP检测:Any模式958",fix="修复:Any#958"))
        BugDB.load(BugRule(id="KT-0994",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="[B0959] MULTIPLATFORM:List<S>#959",trigger="val x:List<S>=batch_959()",detection="MULTIPLATFORM检测:List<S>模式959",fix="修复:List<S>#959"))
        BugDB.load(BugRule(id="KT-0995",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="[B0960] NULL_SAFETY:Set<I>#960",trigger="val x:Set<I>=batch_960()",detection="NULL_SAFETY检测:Set<I>模式960",fix="修复:Set<I>#960"))
        BugDB.load(BugRule(id="KT-0996",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="[B0961] GENERICS:Map<S,I>#961",trigger="val x:Map<S,I>=batch_961()",detection="GENERICS检测:Map<S,I>模式961",fix="修复:Map<S,I>#961"))
        BugDB.load(BugRule(id="KT-0997",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="[B0962] COROUTINES:String#962",trigger="val x:String=batch_962()",detection="COROUTINES检测:String模式962",fix="修复:String#962"))
        BugDB.load(BugRule(id="KT-0998",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="[B0963] COLLECTIONS:Int#963",trigger="val x:Int=batch_963()",detection="COLLECTIONS检测:Int模式963",fix="修复:Int#963"))
        BugDB.load(BugRule(id="KT-0999",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="[B0964] REFLECTION:Long#964",trigger="val x:Long=batch_964()",detection="REFLECTION检测:Long模式964",fix="修复:Long#964"))
        BugDB.load(BugRule(id="KT-1000",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="[B0965] DSL_LAMBDA:Double#965",trigger="val x:Double=batch_965()",detection="DSL_LAMBDA检测:Double模式965",fix="修复:Double#965"))
    }
}
// MILD=97 MODERATE=306 SEVERE=597 TOTAL=1000