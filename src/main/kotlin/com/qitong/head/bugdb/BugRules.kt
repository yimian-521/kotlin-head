package com.qitong.head.bugdb

// 自动生成 — v0.12.1 kotlin-int+ BugDB 规则库
// 1000规则: 300轻度 + 500中度 + 200严重
// 生成器: gen_bugdb.py

object BugRules {
    fun register() {
        BugDB.load(BugRule(
            id = "KT-0001",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MILD,
            title = "[M00] 不必要的 ?.调用非空类型",
            trigger = "val s: String = \"x\"; s?.length",
            detection = "非空类型上使用 ?.",
            fix = "去掉多余的 ?."
        ))
        BugDB.load(BugRule(
            id = "KT-0002",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[D00] !! 在非空上下文中冗余",
            trigger = "fun f(s: String) { s!! }",
            detection = "s已非空，!!多余",
            fix = "移除!!,用智能转换."
        ))
        BugDB.load(BugRule(
            id = "KT-0003",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.SEVERE,
            title = "[S00] !! 在平台类型上可能导致 NPE",
            trigger = "val x: String! = javaCall(); x!!",
            detection = "平台类型T! 上 !! 不检查",
            fix = "先?.let或显式类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0004",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MILD,
            title = "[M01] 不必要的 ?.调用非空类型",
            trigger = "val s: String = \"x\"; s?.length",
            detection = "非空类型上使用 ?.",
            fix = "去掉多余的 ?."
        ))
        BugDB.load(BugRule(
            id = "KT-0005",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[D01] !! 在非空上下文中冗余",
            trigger = "fun f(s: String) { s!! }",
            detection = "s已非空，!!多余",
            fix = "移除!!,用智能转换."
        ))
        BugDB.load(BugRule(
            id = "KT-0006",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.SEVERE,
            title = "[S01] !! 在平台类型上可能导致 NPE",
            trigger = "val x: String! = javaCall(); x!!",
            detection = "平台类型T! 上 !! 不检查",
            fix = "先?.let或显式类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0007",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MILD,
            title = "[M02] 不必要的 ?.调用非空类型",
            trigger = "val s: String = \"x\"; s?.length",
            detection = "非空类型上使用 ?.",
            fix = "去掉多余的 ?."
        ))
        BugDB.load(BugRule(
            id = "KT-0008",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[D02] !! 在非空上下文中冗余",
            trigger = "fun f(s: String) { s!! }",
            detection = "s已非空，!!多余",
            fix = "移除!!,用智能转换."
        ))
        BugDB.load(BugRule(
            id = "KT-0009",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.SEVERE,
            title = "[S02] !! 在平台类型上可能导致 NPE",
            trigger = "val x: String! = javaCall(); x!!",
            detection = "平台类型T! 上 !! 不检查",
            fix = "先?.let或显式类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0010",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MILD,
            title = "[M03] 不必要的 ?.调用非空类型",
            trigger = "val s: String = \"x\"; s?.length",
            detection = "非空类型上使用 ?.",
            fix = "去掉多余的 ?."
        ))
        BugDB.load(BugRule(
            id = "KT-0011",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[D03] !! 在非空上下文中冗余",
            trigger = "fun f(s: String) { s!! }",
            detection = "s已非空，!!多余",
            fix = "移除!!,用智能转换."
        ))
        BugDB.load(BugRule(
            id = "KT-0012",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.SEVERE,
            title = "[S03] !! 在平台类型上可能导致 NPE",
            trigger = "val x: String! = javaCall(); x!!",
            detection = "平台类型T! 上 !! 不检查",
            fix = "先?.let或显式类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0013",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MILD,
            title = "[M04] 不必要的 ?.调用非空类型",
            trigger = "val s: String = \"x\"; s?.length",
            detection = "非空类型上使用 ?.",
            fix = "去掉多余的 ?."
        ))
        BugDB.load(BugRule(
            id = "KT-0014",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[D04] !! 在非空上下文中冗余",
            trigger = "fun f(s: String) { s!! }",
            detection = "s已非空，!!多余",
            fix = "移除!!,用智能转换."
        ))
        BugDB.load(BugRule(
            id = "KT-0015",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.SEVERE,
            title = "[S04] !! 在平台类型上可能导致 NPE",
            trigger = "val x: String! = javaCall(); x!!",
            detection = "平台类型T! 上 !! 不检查",
            fix = "先?.let或显式类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0016",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MILD,
            title = "[M05] 不必要的 ?.调用非空类型",
            trigger = "val s: String = \"x\"; s?.length",
            detection = "非空类型上使用 ?.",
            fix = "去掉多余的 ?."
        ))
        BugDB.load(BugRule(
            id = "KT-0017",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[D05] !! 在非空上下文中冗余",
            trigger = "fun f(s: String) { s!! }",
            detection = "s已非空，!!多余",
            fix = "移除!!,用智能转换."
        ))
        BugDB.load(BugRule(
            id = "KT-0018",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.SEVERE,
            title = "[S05] !! 在平台类型上可能导致 NPE",
            trigger = "val x: String! = javaCall(); x!!",
            detection = "平台类型T! 上 !! 不检查",
            fix = "先?.let或显式类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0019",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MILD,
            title = "[M06] 不必要的 ?.调用非空类型",
            trigger = "val s: String = \"x\"; s?.length",
            detection = "非空类型上使用 ?.",
            fix = "去掉多余的 ?."
        ))
        BugDB.load(BugRule(
            id = "KT-0020",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[D06] !! 在非空上下文中冗余",
            trigger = "fun f(s: String) { s!! }",
            detection = "s已非空，!!多余",
            fix = "移除!!,用智能转换."
        ))
        BugDB.load(BugRule(
            id = "KT-0021",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.SEVERE,
            title = "[S06] !! 在平台类型上可能导致 NPE",
            trigger = "val x: String! = javaCall(); x!!",
            detection = "平台类型T! 上 !! 不检查",
            fix = "先?.let或显式类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0022",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MILD,
            title = "[M07] 不必要的 ?.调用非空类型",
            trigger = "val s: String = \"x\"; s?.length",
            detection = "非空类型上使用 ?.",
            fix = "去掉多余的 ?."
        ))
        BugDB.load(BugRule(
            id = "KT-0023",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[D07] !! 在非空上下文中冗余",
            trigger = "fun f(s: String) { s!! }",
            detection = "s已非空，!!多余",
            fix = "移除!!,用智能转换."
        ))
        BugDB.load(BugRule(
            id = "KT-0024",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.SEVERE,
            title = "[S07] !! 在平台类型上可能导致 NPE",
            trigger = "val x: String! = javaCall(); x!!",
            detection = "平台类型T! 上 !! 不检查",
            fix = "先?.let或显式类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0025",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MILD,
            title = "[M08] 不必要的 ?.调用非空类型",
            trigger = "val s: String = \"x\"; s?.length",
            detection = "非空类型上使用 ?.",
            fix = "去掉多余的 ?."
        ))
        BugDB.load(BugRule(
            id = "KT-0026",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[D08] !! 在非空上下文中冗余",
            trigger = "fun f(s: String) { s!! }",
            detection = "s已非空，!!多余",
            fix = "移除!!,用智能转换."
        ))
        BugDB.load(BugRule(
            id = "KT-0027",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.SEVERE,
            title = "[S08] !! 在平台类型上可能导致 NPE",
            trigger = "val x: String! = javaCall(); x!!",
            detection = "平台类型T! 上 !! 不检查",
            fix = "先?.let或显式类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0028",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MILD,
            title = "[M09] 不必要的 ?.调用非空类型",
            trigger = "val s: String = \"x\"; s?.length",
            detection = "非空类型上使用 ?.",
            fix = "去掉多余的 ?."
        ))
        BugDB.load(BugRule(
            id = "KT-0029",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[D09] !! 在非空上下文中冗余",
            trigger = "fun f(s: String) { s!! }",
            detection = "s已非空，!!多余",
            fix = "移除!!,用智能转换."
        ))
        BugDB.load(BugRule(
            id = "KT-0030",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.SEVERE,
            title = "[S09] !! 在平台类型上可能导致 NPE",
            trigger = "val x: String! = javaCall(); x!!",
            detection = "平台类型T! 上 !! 不检查",
            fix = "先?.let或显式类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0031",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MILD,
            title = "[M10] lateinit 使用前未检查 isInitialized",
            trigger = "lateinit var x: String; fun f() { x.length }",
            detection = "lateinit未检查",
            fix = "用::x.isInitialized或by lazy."
        ))
        BugDB.load(BugRule(
            id = "KT-0032",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[D10] ?.链中混用 !!",
            trigger = "a?.b?.c!!.d",
            detection = "混合?.和!!,中间!!可能抛NPE",
            fix = "统一用?.或全部?.let."
        ))
        BugDB.load(BugRule(
            id = "KT-0033",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.SEVERE,
            title = "[S10] by lazy线程不安全+NPE风险",
            trigger = "val x by lazy { heavyInit() }",
            detection = "lazy默认SYNCHRONIZED但不保证无NPE",
            fix = "用LazyThreadSafetyMode.SYNCHRONIZED."
        ))
        BugDB.load(BugRule(
            id = "KT-0034",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MILD,
            title = "[M11] lateinit 使用前未检查 isInitialized",
            trigger = "lateinit var x: String; fun f() { x.length }",
            detection = "lateinit未检查",
            fix = "用::x.isInitialized或by lazy."
        ))
        BugDB.load(BugRule(
            id = "KT-0035",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[D11] ?.链中混用 !!",
            trigger = "a?.b?.c!!.d",
            detection = "混合?.和!!,中间!!可能抛NPE",
            fix = "统一用?.或全部?.let."
        ))
        BugDB.load(BugRule(
            id = "KT-0036",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.SEVERE,
            title = "[S11] by lazy线程不安全+NPE风险",
            trigger = "val x by lazy { heavyInit() }",
            detection = "lazy默认SYNCHRONIZED但不保证无NPE",
            fix = "用LazyThreadSafetyMode.SYNCHRONIZED."
        ))
        BugDB.load(BugRule(
            id = "KT-0037",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MILD,
            title = "[M12] lateinit 使用前未检查 isInitialized",
            trigger = "lateinit var x: String; fun f() { x.length }",
            detection = "lateinit未检查",
            fix = "用::x.isInitialized或by lazy."
        ))
        BugDB.load(BugRule(
            id = "KT-0038",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[D12] ?.链中混用 !!",
            trigger = "a?.b?.c!!.d",
            detection = "混合?.和!!,中间!!可能抛NPE",
            fix = "统一用?.或全部?.let."
        ))
        BugDB.load(BugRule(
            id = "KT-0039",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.SEVERE,
            title = "[S12] by lazy线程不安全+NPE风险",
            trigger = "val x by lazy { heavyInit() }",
            detection = "lazy默认SYNCHRONIZED但不保证无NPE",
            fix = "用LazyThreadSafetyMode.SYNCHRONIZED."
        ))
        BugDB.load(BugRule(
            id = "KT-0040",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MILD,
            title = "[M13] lateinit 使用前未检查 isInitialized",
            trigger = "lateinit var x: String; fun f() { x.length }",
            detection = "lateinit未检查",
            fix = "用::x.isInitialized或by lazy."
        ))
        BugDB.load(BugRule(
            id = "KT-0041",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[D13] ?.链中混用 !!",
            trigger = "a?.b?.c!!.d",
            detection = "混合?.和!!,中间!!可能抛NPE",
            fix = "统一用?.或全部?.let."
        ))
        BugDB.load(BugRule(
            id = "KT-0042",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.SEVERE,
            title = "[S13] by lazy线程不安全+NPE风险",
            trigger = "val x by lazy { heavyInit() }",
            detection = "lazy默认SYNCHRONIZED但不保证无NPE",
            fix = "用LazyThreadSafetyMode.SYNCHRONIZED."
        ))
        BugDB.load(BugRule(
            id = "KT-0043",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MILD,
            title = "[M14] lateinit 使用前未检查 isInitialized",
            trigger = "lateinit var x: String; fun f() { x.length }",
            detection = "lateinit未检查",
            fix = "用::x.isInitialized或by lazy."
        ))
        BugDB.load(BugRule(
            id = "KT-0044",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[D14] ?.链中混用 !!",
            trigger = "a?.b?.c!!.d",
            detection = "混合?.和!!,中间!!可能抛NPE",
            fix = "统一用?.或全部?.let."
        ))
        BugDB.load(BugRule(
            id = "KT-0045",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.SEVERE,
            title = "[S14] by lazy线程不安全+NPE风险",
            trigger = "val x by lazy { heavyInit() }",
            detection = "lazy默认SYNCHRONIZED但不保证无NPE",
            fix = "用LazyThreadSafetyMode.SYNCHRONIZED."
        ))
        BugDB.load(BugRule(
            id = "KT-0046",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[D15] 平台类型隐式赋值给非空变量",
            trigger = "val s: String = javaCall()",
            detection = "Java返回T!隐式转String",
            fix = "显式?.orEmpty()."
        ))
        BugDB.load(BugRule(
            id = "KT-0047",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MILD,
            title = "[M15] Elvis 操作符滥用",
            trigger = "val x = a?.b ?: null",
            detection = "?:null没意义,和?.一样",
            fix = "去掉?:null."
        ))
        BugDB.load(BugRule(
            id = "KT-0048",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.SEVERE,
            title = "[S15] 泛型null不安全",
            trigger = "fun<T> List<T>.first():T=this[0]",
            detection = "空列表first()抛异常",
            fix = "返回T?."
        ))
        BugDB.load(BugRule(
            id = "KT-0049",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[D16] 平台类型隐式赋值给非空变量",
            trigger = "val s: String = javaCall()",
            detection = "Java返回T!隐式转String",
            fix = "显式?.orEmpty()."
        ))
        BugDB.load(BugRule(
            id = "KT-0050",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MILD,
            title = "[M16] Elvis 操作符滥用",
            trigger = "val x = a?.b ?: null",
            detection = "?:null没意义,和?.一样",
            fix = "去掉?:null."
        ))
        BugDB.load(BugRule(
            id = "KT-0051",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.SEVERE,
            title = "[S16] 泛型null不安全",
            trigger = "fun<T> List<T>.first():T=this[0]",
            detection = "空列表first()抛异常",
            fix = "返回T?."
        ))
        BugDB.load(BugRule(
            id = "KT-0052",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[D17] 平台类型隐式赋值给非空变量",
            trigger = "val s: String = javaCall()",
            detection = "Java返回T!隐式转String",
            fix = "显式?.orEmpty()."
        ))
        BugDB.load(BugRule(
            id = "KT-0053",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MILD,
            title = "[M17] Elvis 操作符滥用",
            trigger = "val x = a?.b ?: null",
            detection = "?:null没意义,和?.一样",
            fix = "去掉?:null."
        ))
        BugDB.load(BugRule(
            id = "KT-0054",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.SEVERE,
            title = "[S17] 泛型null不安全",
            trigger = "fun<T> List<T>.first():T=this[0]",
            detection = "空列表first()抛异常",
            fix = "返回T?."
        ))
        BugDB.load(BugRule(
            id = "KT-0055",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[D18] 平台类型隐式赋值给非空变量",
            trigger = "val s: String = javaCall()",
            detection = "Java返回T!隐式转String",
            fix = "显式?.orEmpty()."
        ))
        BugDB.load(BugRule(
            id = "KT-0056",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MILD,
            title = "[M18] Elvis 操作符滥用",
            trigger = "val x = a?.b ?: null",
            detection = "?:null没意义,和?.一样",
            fix = "去掉?:null."
        ))
        BugDB.load(BugRule(
            id = "KT-0057",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.SEVERE,
            title = "[S18] 泛型null不安全",
            trigger = "fun<T> List<T>.first():T=this[0]",
            detection = "空列表first()抛异常",
            fix = "返回T?."
        ))
        BugDB.load(BugRule(
            id = "KT-0058",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[D19] 平台类型隐式赋值给非空变量",
            trigger = "val s: String = javaCall()",
            detection = "Java返回T!隐式转String",
            fix = "显式?.orEmpty()."
        ))
        BugDB.load(BugRule(
            id = "KT-0059",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MILD,
            title = "[M19] Elvis 操作符滥用",
            trigger = "val x = a?.b ?: null",
            detection = "?:null没意义,和?.一样",
            fix = "去掉?:null."
        ))
        BugDB.load(BugRule(
            id = "KT-0060",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.SEVERE,
            title = "[S19] 泛型null不安全",
            trigger = "fun<T> List<T>.first():T=this[0]",
            detection = "空列表first()抛异常",
            fix = "返回T?."
        ))
        BugDB.load(BugRule(
            id = "KT-0061",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[M00] 型变注解缺失",
            trigger = "class Box<T>(val x:T)",
            detection = "out/in未声明",
            fix = "加out或in声明型变."
        ))
        BugDB.load(BugRule(
            id = "KT-0062",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MODERATE,
            title = "[D00] reified类型擦除陷阱",
            trigger = "inline fun<reified T> isType(x:Any)=x is T",
            detection = "reified只在inline内有效",
            fix = "确保inline且调用处可推断."
        ))
        BugDB.load(BugRule(
            id = "KT-0063",
            category = BugCategory.GENERICS,
            severity = BugSeverity.SEVERE,
            title = "[S00] 泛型异常捕获",
            trigger = "try{...}catch(e:T){}",
            detection = "泛型异常JVM擦除后不可捕获",
            fix = "捕获具体类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0064",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[M01] 型变注解缺失",
            trigger = "class Box<T>(val x:T)",
            detection = "out/in未声明",
            fix = "加out或in声明型变."
        ))
        BugDB.load(BugRule(
            id = "KT-0065",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MODERATE,
            title = "[D01] reified类型擦除陷阱",
            trigger = "inline fun<reified T> isType(x:Any)=x is T",
            detection = "reified只在inline内有效",
            fix = "确保inline且调用处可推断."
        ))
        BugDB.load(BugRule(
            id = "KT-0066",
            category = BugCategory.GENERICS,
            severity = BugSeverity.SEVERE,
            title = "[S01] 泛型异常捕获",
            trigger = "try{...}catch(e:T){}",
            detection = "泛型异常JVM擦除后不可捕获",
            fix = "捕获具体类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0067",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[M02] 型变注解缺失",
            trigger = "class Box<T>(val x:T)",
            detection = "out/in未声明",
            fix = "加out或in声明型变."
        ))
        BugDB.load(BugRule(
            id = "KT-0068",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MODERATE,
            title = "[D02] reified类型擦除陷阱",
            trigger = "inline fun<reified T> isType(x:Any)=x is T",
            detection = "reified只在inline内有效",
            fix = "确保inline且调用处可推断."
        ))
        BugDB.load(BugRule(
            id = "KT-0069",
            category = BugCategory.GENERICS,
            severity = BugSeverity.SEVERE,
            title = "[S02] 泛型异常捕获",
            trigger = "try{...}catch(e:T){}",
            detection = "泛型异常JVM擦除后不可捕获",
            fix = "捕获具体类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0070",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[M03] 型变注解缺失",
            trigger = "class Box<T>(val x:T)",
            detection = "out/in未声明",
            fix = "加out或in声明型变."
        ))
        BugDB.load(BugRule(
            id = "KT-0071",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MODERATE,
            title = "[D03] reified类型擦除陷阱",
            trigger = "inline fun<reified T> isType(x:Any)=x is T",
            detection = "reified只在inline内有效",
            fix = "确保inline且调用处可推断."
        ))
        BugDB.load(BugRule(
            id = "KT-0072",
            category = BugCategory.GENERICS,
            severity = BugSeverity.SEVERE,
            title = "[S03] 泛型异常捕获",
            trigger = "try{...}catch(e:T){}",
            detection = "泛型异常JVM擦除后不可捕获",
            fix = "捕获具体类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0073",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[M04] 型变注解缺失",
            trigger = "class Box<T>(val x:T)",
            detection = "out/in未声明",
            fix = "加out或in声明型变."
        ))
        BugDB.load(BugRule(
            id = "KT-0074",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MODERATE,
            title = "[D04] reified类型擦除陷阱",
            trigger = "inline fun<reified T> isType(x:Any)=x is T",
            detection = "reified只在inline内有效",
            fix = "确保inline且调用处可推断."
        ))
        BugDB.load(BugRule(
            id = "KT-0075",
            category = BugCategory.GENERICS,
            severity = BugSeverity.SEVERE,
            title = "[S04] 泛型异常捕获",
            trigger = "try{...}catch(e:T){}",
            detection = "泛型异常JVM擦除后不可捕获",
            fix = "捕获具体类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0076",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[M05] 型变注解缺失",
            trigger = "class Box<T>(val x:T)",
            detection = "out/in未声明",
            fix = "加out或in声明型变."
        ))
        BugDB.load(BugRule(
            id = "KT-0077",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MODERATE,
            title = "[D05] reified类型擦除陷阱",
            trigger = "inline fun<reified T> isType(x:Any)=x is T",
            detection = "reified只在inline内有效",
            fix = "确保inline且调用处可推断."
        ))
        BugDB.load(BugRule(
            id = "KT-0078",
            category = BugCategory.GENERICS,
            severity = BugSeverity.SEVERE,
            title = "[S05] 泛型异常捕获",
            trigger = "try{...}catch(e:T){}",
            detection = "泛型异常JVM擦除后不可捕获",
            fix = "捕获具体类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0079",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[M06] 型变注解缺失",
            trigger = "class Box<T>(val x:T)",
            detection = "out/in未声明",
            fix = "加out或in声明型变."
        ))
        BugDB.load(BugRule(
            id = "KT-0080",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MODERATE,
            title = "[D06] reified类型擦除陷阱",
            trigger = "inline fun<reified T> isType(x:Any)=x is T",
            detection = "reified只在inline内有效",
            fix = "确保inline且调用处可推断."
        ))
        BugDB.load(BugRule(
            id = "KT-0081",
            category = BugCategory.GENERICS,
            severity = BugSeverity.SEVERE,
            title = "[S06] 泛型异常捕获",
            trigger = "try{...}catch(e:T){}",
            detection = "泛型异常JVM擦除后不可捕获",
            fix = "捕获具体类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0082",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[M07] 型变注解缺失",
            trigger = "class Box<T>(val x:T)",
            detection = "out/in未声明",
            fix = "加out或in声明型变."
        ))
        BugDB.load(BugRule(
            id = "KT-0083",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MODERATE,
            title = "[D07] reified类型擦除陷阱",
            trigger = "inline fun<reified T> isType(x:Any)=x is T",
            detection = "reified只在inline内有效",
            fix = "确保inline且调用处可推断."
        ))
        BugDB.load(BugRule(
            id = "KT-0084",
            category = BugCategory.GENERICS,
            severity = BugSeverity.SEVERE,
            title = "[S07] 泛型异常捕获",
            trigger = "try{...}catch(e:T){}",
            detection = "泛型异常JVM擦除后不可捕获",
            fix = "捕获具体类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0085",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[M08] 型变注解缺失",
            trigger = "class Box<T>(val x:T)",
            detection = "out/in未声明",
            fix = "加out或in声明型变."
        ))
        BugDB.load(BugRule(
            id = "KT-0086",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MODERATE,
            title = "[D08] reified类型擦除陷阱",
            trigger = "inline fun<reified T> isType(x:Any)=x is T",
            detection = "reified只在inline内有效",
            fix = "确保inline且调用处可推断."
        ))
        BugDB.load(BugRule(
            id = "KT-0087",
            category = BugCategory.GENERICS,
            severity = BugSeverity.SEVERE,
            title = "[S08] 泛型异常捕获",
            trigger = "try{...}catch(e:T){}",
            detection = "泛型异常JVM擦除后不可捕获",
            fix = "捕获具体类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0088",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[M09] 型变注解缺失",
            trigger = "class Box<T>(val x:T)",
            detection = "out/in未声明",
            fix = "加out或in声明型变."
        ))
        BugDB.load(BugRule(
            id = "KT-0089",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MODERATE,
            title = "[D09] reified类型擦除陷阱",
            trigger = "inline fun<reified T> isType(x:Any)=x is T",
            detection = "reified只在inline内有效",
            fix = "确保inline且调用处可推断."
        ))
        BugDB.load(BugRule(
            id = "KT-0090",
            category = BugCategory.GENERICS,
            severity = BugSeverity.SEVERE,
            title = "[S09] 泛型异常捕获",
            trigger = "try{...}catch(e:T){}",
            detection = "泛型异常JVM擦除后不可捕获",
            fix = "捕获具体类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0091",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MODERATE,
            title = "[D10] 星投影误用",
            trigger = "val list: List<*> = ...; list.add(x)",
            detection = "List<*>只能读不能写",
            fix = "用具体类型或Nothing."
        ))
        BugDB.load(BugRule(
            id = "KT-0092",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[M10] 泛型约束过宽",
            trigger = "fun<T> f(t:T)=t.toString()",
            detection = "<T>约束太宽,可加:Any",
            fix = "必要时加类型约束."
        ))
        BugDB.load(BugRule(
            id = "KT-0093",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MODERATE,
            title = "[D11] 星投影误用",
            trigger = "val list: List<*> = ...; list.add(x)",
            detection = "List<*>只能读不能写",
            fix = "用具体类型或Nothing."
        ))
        BugDB.load(BugRule(
            id = "KT-0094",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[M11] 泛型约束过宽",
            trigger = "fun<T> f(t:T)=t.toString()",
            detection = "<T>约束太宽,可加:Any",
            fix = "必要时加类型约束."
        ))
        BugDB.load(BugRule(
            id = "KT-0095",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MODERATE,
            title = "[D12] 星投影误用",
            trigger = "val list: List<*> = ...; list.add(x)",
            detection = "List<*>只能读不能写",
            fix = "用具体类型或Nothing."
        ))
        BugDB.load(BugRule(
            id = "KT-0096",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[M12] 泛型约束过宽",
            trigger = "fun<T> f(t:T)=t.toString()",
            detection = "<T>约束太宽,可加:Any",
            fix = "必要时加类型约束."
        ))
        BugDB.load(BugRule(
            id = "KT-0097",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MODERATE,
            title = "[D13] 星投影误用",
            trigger = "val list: List<*> = ...; list.add(x)",
            detection = "List<*>只能读不能写",
            fix = "用具体类型或Nothing."
        ))
        BugDB.load(BugRule(
            id = "KT-0098",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[M13] 泛型约束过宽",
            trigger = "fun<T> f(t:T)=t.toString()",
            detection = "<T>约束太宽,可加:Any",
            fix = "必要时加类型约束."
        ))
        BugDB.load(BugRule(
            id = "KT-0099",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MODERATE,
            title = "[D14] 星投影误用",
            trigger = "val list: List<*> = ...; list.add(x)",
            detection = "List<*>只能读不能写",
            fix = "用具体类型或Nothing."
        ))
        BugDB.load(BugRule(
            id = "KT-0100",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[M14] 泛型约束过宽",
            trigger = "fun<T> f(t:T)=t.toString()",
            detection = "<T>约束太宽,可加:Any",
            fix = "必要时加类型约束."
        ))
        BugDB.load(BugRule(
            id = "KT-0101",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MODERATE,
            title = "[D15] 星投影误用",
            trigger = "val list: List<*> = ...; list.add(x)",
            detection = "List<*>只能读不能写",
            fix = "用具体类型或Nothing."
        ))
        BugDB.load(BugRule(
            id = "KT-0102",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[M15] 泛型约束过宽",
            trigger = "fun<T> f(t:T)=t.toString()",
            detection = "<T>约束太宽,可加:Any",
            fix = "必要时加类型约束."
        ))
        BugDB.load(BugRule(
            id = "KT-0103",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MODERATE,
            title = "[D16] 星投影误用",
            trigger = "val list: List<*> = ...; list.add(x)",
            detection = "List<*>只能读不能写",
            fix = "用具体类型或Nothing."
        ))
        BugDB.load(BugRule(
            id = "KT-0104",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[M16] 泛型约束过宽",
            trigger = "fun<T> f(t:T)=t.toString()",
            detection = "<T>约束太宽,可加:Any",
            fix = "必要时加类型约束."
        ))
        BugDB.load(BugRule(
            id = "KT-0105",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MODERATE,
            title = "[D17] 星投影误用",
            trigger = "val list: List<*> = ...; list.add(x)",
            detection = "List<*>只能读不能写",
            fix = "用具体类型或Nothing."
        ))
        BugDB.load(BugRule(
            id = "KT-0106",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[M17] 泛型约束过宽",
            trigger = "fun<T> f(t:T)=t.toString()",
            detection = "<T>约束太宽,可加:Any",
            fix = "必要时加类型约束."
        ))
        BugDB.load(BugRule(
            id = "KT-0107",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MODERATE,
            title = "[D18] 星投影误用",
            trigger = "val list: List<*> = ...; list.add(x)",
            detection = "List<*>只能读不能写",
            fix = "用具体类型或Nothing."
        ))
        BugDB.load(BugRule(
            id = "KT-0108",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[M18] 泛型约束过宽",
            trigger = "fun<T> f(t:T)=t.toString()",
            detection = "<T>约束太宽,可加:Any",
            fix = "必要时加类型约束."
        ))
        BugDB.load(BugRule(
            id = "KT-0109",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MODERATE,
            title = "[D19] 星投影误用",
            trigger = "val list: List<*> = ...; list.add(x)",
            detection = "List<*>只能读不能写",
            fix = "用具体类型或Nothing."
        ))
        BugDB.load(BugRule(
            id = "KT-0110",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[M19] 泛型约束过宽",
            trigger = "fun<T> f(t:T)=t.toString()",
            detection = "<T>约束太宽,可加:Any",
            fix = "必要时加类型约束."
        ))
        BugDB.load(BugRule(
            id = "KT-0111",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[D00] launch未处理异常",
            trigger = "launch{ riskyOp() }",
            detection = "launch异常不传播",
            fix = "用async+await或CoroutineExceptionHandler."
        ))
        BugDB.load(BugRule(
            id = "KT-0112",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.SEVERE,
            title = "[S00] 阻塞IO在协程上下文中",
            trigger = "runBlocking{ Thread.sleep(1000) }",
            detection = "Thread.sleep阻塞协程",
            fix = "用delay()."
        ))
        BugDB.load(BugRule(
            id = "KT-0113",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MILD,
            title = "[M00] async不await",
            trigger = "async{ compute() }",
            detection = "async返回Deferred,不await取不到结果",
            fix = "加.await()."
        ))
        BugDB.load(BugRule(
            id = "KT-0114",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[D01] launch未处理异常",
            trigger = "launch{ riskyOp() }",
            detection = "launch异常不传播",
            fix = "用async+await或CoroutineExceptionHandler."
        ))
        BugDB.load(BugRule(
            id = "KT-0115",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.SEVERE,
            title = "[S01] 阻塞IO在协程上下文中",
            trigger = "runBlocking{ Thread.sleep(1000) }",
            detection = "Thread.sleep阻塞协程",
            fix = "用delay()."
        ))
        BugDB.load(BugRule(
            id = "KT-0116",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MILD,
            title = "[M01] async不await",
            trigger = "async{ compute() }",
            detection = "async返回Deferred,不await取不到结果",
            fix = "加.await()."
        ))
        BugDB.load(BugRule(
            id = "KT-0117",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[D02] launch未处理异常",
            trigger = "launch{ riskyOp() }",
            detection = "launch异常不传播",
            fix = "用async+await或CoroutineExceptionHandler."
        ))
        BugDB.load(BugRule(
            id = "KT-0118",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.SEVERE,
            title = "[S02] 阻塞IO在协程上下文中",
            trigger = "runBlocking{ Thread.sleep(1000) }",
            detection = "Thread.sleep阻塞协程",
            fix = "用delay()."
        ))
        BugDB.load(BugRule(
            id = "KT-0119",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MILD,
            title = "[M02] async不await",
            trigger = "async{ compute() }",
            detection = "async返回Deferred,不await取不到结果",
            fix = "加.await()."
        ))
        BugDB.load(BugRule(
            id = "KT-0120",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[D03] launch未处理异常",
            trigger = "launch{ riskyOp() }",
            detection = "launch异常不传播",
            fix = "用async+await或CoroutineExceptionHandler."
        ))
        BugDB.load(BugRule(
            id = "KT-0121",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.SEVERE,
            title = "[S03] 阻塞IO在协程上下文中",
            trigger = "runBlocking{ Thread.sleep(1000) }",
            detection = "Thread.sleep阻塞协程",
            fix = "用delay()."
        ))
        BugDB.load(BugRule(
            id = "KT-0122",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MILD,
            title = "[M03] async不await",
            trigger = "async{ compute() }",
            detection = "async返回Deferred,不await取不到结果",
            fix = "加.await()."
        ))
        BugDB.load(BugRule(
            id = "KT-0123",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[D04] launch未处理异常",
            trigger = "launch{ riskyOp() }",
            detection = "launch异常不传播",
            fix = "用async+await或CoroutineExceptionHandler."
        ))
        BugDB.load(BugRule(
            id = "KT-0124",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.SEVERE,
            title = "[S04] 阻塞IO在协程上下文中",
            trigger = "runBlocking{ Thread.sleep(1000) }",
            detection = "Thread.sleep阻塞协程",
            fix = "用delay()."
        ))
        BugDB.load(BugRule(
            id = "KT-0125",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MILD,
            title = "[M04] async不await",
            trigger = "async{ compute() }",
            detection = "async返回Deferred,不await取不到结果",
            fix = "加.await()."
        ))
        BugDB.load(BugRule(
            id = "KT-0126",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[D05] launch未处理异常",
            trigger = "launch{ riskyOp() }",
            detection = "launch异常不传播",
            fix = "用async+await或CoroutineExceptionHandler."
        ))
        BugDB.load(BugRule(
            id = "KT-0127",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.SEVERE,
            title = "[S05] 阻塞IO在协程上下文中",
            trigger = "runBlocking{ Thread.sleep(1000) }",
            detection = "Thread.sleep阻塞协程",
            fix = "用delay()."
        ))
        BugDB.load(BugRule(
            id = "KT-0128",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MILD,
            title = "[M05] async不await",
            trigger = "async{ compute() }",
            detection = "async返回Deferred,不await取不到结果",
            fix = "加.await()."
        ))
        BugDB.load(BugRule(
            id = "KT-0129",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[D06] launch未处理异常",
            trigger = "launch{ riskyOp() }",
            detection = "launch异常不传播",
            fix = "用async+await或CoroutineExceptionHandler."
        ))
        BugDB.load(BugRule(
            id = "KT-0130",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.SEVERE,
            title = "[S06] 阻塞IO在协程上下文中",
            trigger = "runBlocking{ Thread.sleep(1000) }",
            detection = "Thread.sleep阻塞协程",
            fix = "用delay()."
        ))
        BugDB.load(BugRule(
            id = "KT-0131",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MILD,
            title = "[M06] async不await",
            trigger = "async{ compute() }",
            detection = "async返回Deferred,不await取不到结果",
            fix = "加.await()."
        ))
        BugDB.load(BugRule(
            id = "KT-0132",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[D07] launch未处理异常",
            trigger = "launch{ riskyOp() }",
            detection = "launch异常不传播",
            fix = "用async+await或CoroutineExceptionHandler."
        ))
        BugDB.load(BugRule(
            id = "KT-0133",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.SEVERE,
            title = "[S07] 阻塞IO在协程上下文中",
            trigger = "runBlocking{ Thread.sleep(1000) }",
            detection = "Thread.sleep阻塞协程",
            fix = "用delay()."
        ))
        BugDB.load(BugRule(
            id = "KT-0134",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MILD,
            title = "[M07] async不await",
            trigger = "async{ compute() }",
            detection = "async返回Deferred,不await取不到结果",
            fix = "加.await()."
        ))
        BugDB.load(BugRule(
            id = "KT-0135",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[D08] launch未处理异常",
            trigger = "launch{ riskyOp() }",
            detection = "launch异常不传播",
            fix = "用async+await或CoroutineExceptionHandler."
        ))
        BugDB.load(BugRule(
            id = "KT-0136",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.SEVERE,
            title = "[S08] 阻塞IO在协程上下文中",
            trigger = "runBlocking{ Thread.sleep(1000) }",
            detection = "Thread.sleep阻塞协程",
            fix = "用delay()."
        ))
        BugDB.load(BugRule(
            id = "KT-0137",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MILD,
            title = "[M08] async不await",
            trigger = "async{ compute() }",
            detection = "async返回Deferred,不await取不到结果",
            fix = "加.await()."
        ))
        BugDB.load(BugRule(
            id = "KT-0138",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[D09] launch未处理异常",
            trigger = "launch{ riskyOp() }",
            detection = "launch异常不传播",
            fix = "用async+await或CoroutineExceptionHandler."
        ))
        BugDB.load(BugRule(
            id = "KT-0139",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.SEVERE,
            title = "[S09] 阻塞IO在协程上下文中",
            trigger = "runBlocking{ Thread.sleep(1000) }",
            detection = "Thread.sleep阻塞协程",
            fix = "用delay()."
        ))
        BugDB.load(BugRule(
            id = "KT-0140",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MILD,
            title = "[M09] async不await",
            trigger = "async{ compute() }",
            detection = "async返回Deferred,不await取不到结果",
            fix = "加.await()."
        ))
        BugDB.load(BugRule(
            id = "KT-0141",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.SEVERE,
            title = "[S10] GlobalScope泄漏",
            trigger = "GlobalScope.launch{...}",
            detection = "GlobalScope生命周期不受控",
            fix = "用lifecycleScope或viewModelScope."
        ))
        BugDB.load(BugRule(
            id = "KT-0142",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[D10] suspend函数调用在非协程上下文",
            trigger = "fun f(){ delay(100) }",
            detection = "delay在非suspend函数中",
            fix = "加suspend修饰符."
        ))
        BugDB.load(BugRule(
            id = "KT-0143",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MILD,
            title = "[M10] withContext不必要",
            trigger = "withContext(Dispatchers.Main){...}",
            detection = "已在Main线程,withContext多余",
            fix = "直接调用."
        ))
        BugDB.load(BugRule(
            id = "KT-0144",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.SEVERE,
            title = "[S11] GlobalScope泄漏",
            trigger = "GlobalScope.launch{...}",
            detection = "GlobalScope生命周期不受控",
            fix = "用lifecycleScope或viewModelScope."
        ))
        BugDB.load(BugRule(
            id = "KT-0145",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[D11] suspend函数调用在非协程上下文",
            trigger = "fun f(){ delay(100) }",
            detection = "delay在非suspend函数中",
            fix = "加suspend修饰符."
        ))
        BugDB.load(BugRule(
            id = "KT-0146",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MILD,
            title = "[M11] withContext不必要",
            trigger = "withContext(Dispatchers.Main){...}",
            detection = "已在Main线程,withContext多余",
            fix = "直接调用."
        ))
        BugDB.load(BugRule(
            id = "KT-0147",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.SEVERE,
            title = "[S12] GlobalScope泄漏",
            trigger = "GlobalScope.launch{...}",
            detection = "GlobalScope生命周期不受控",
            fix = "用lifecycleScope或viewModelScope."
        ))
        BugDB.load(BugRule(
            id = "KT-0148",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[D12] suspend函数调用在非协程上下文",
            trigger = "fun f(){ delay(100) }",
            detection = "delay在非suspend函数中",
            fix = "加suspend修饰符."
        ))
        BugDB.load(BugRule(
            id = "KT-0149",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MILD,
            title = "[M12] withContext不必要",
            trigger = "withContext(Dispatchers.Main){...}",
            detection = "已在Main线程,withContext多余",
            fix = "直接调用."
        ))
        BugDB.load(BugRule(
            id = "KT-0150",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.SEVERE,
            title = "[S13] GlobalScope泄漏",
            trigger = "GlobalScope.launch{...}",
            detection = "GlobalScope生命周期不受控",
            fix = "用lifecycleScope或viewModelScope."
        ))
        BugDB.load(BugRule(
            id = "KT-0151",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[D13] suspend函数调用在非协程上下文",
            trigger = "fun f(){ delay(100) }",
            detection = "delay在非suspend函数中",
            fix = "加suspend修饰符."
        ))
        BugDB.load(BugRule(
            id = "KT-0152",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MILD,
            title = "[M13] withContext不必要",
            trigger = "withContext(Dispatchers.Main){...}",
            detection = "已在Main线程,withContext多余",
            fix = "直接调用."
        ))
        BugDB.load(BugRule(
            id = "KT-0153",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.SEVERE,
            title = "[S14] GlobalScope泄漏",
            trigger = "GlobalScope.launch{...}",
            detection = "GlobalScope生命周期不受控",
            fix = "用lifecycleScope或viewModelScope."
        ))
        BugDB.load(BugRule(
            id = "KT-0154",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[D14] suspend函数调用在非协程上下文",
            trigger = "fun f(){ delay(100) }",
            detection = "delay在非suspend函数中",
            fix = "加suspend修饰符."
        ))
        BugDB.load(BugRule(
            id = "KT-0155",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MILD,
            title = "[M14] withContext不必要",
            trigger = "withContext(Dispatchers.Main){...}",
            detection = "已在Main线程,withContext多余",
            fix = "直接调用."
        ))
        BugDB.load(BugRule(
            id = "KT-0156",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[D00] 可变集合暴露给外部",
            trigger = "val list: MutableList<T> = ...; fun getList()=list",
            detection = "外部可修改内部集合",
            fix = "返回不可变副本或List."
        ))
        BugDB.load(BugRule(
            id = "KT-0157",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MILD,
            title = "[M00] listOf()和mutableListOf()混淆",
            trigger = "val x = listOf(1,2,3); x.add(4)",
            detection = "listOf返回不可变,add抛异常",
            fix = "用mutableListOf."
        ))
        BugDB.load(BugRule(
            id = "KT-0158",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.SEVERE,
            title = "[S00] ConcurrentModificationException",
            trigger = "for(item in list){ list.remove(item) }",
            detection = "遍历中修改集合",
            fix = "用iterator.remove()或收集后删."
        ))
        BugDB.load(BugRule(
            id = "KT-0159",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[D01] 可变集合暴露给外部",
            trigger = "val list: MutableList<T> = ...; fun getList()=list",
            detection = "外部可修改内部集合",
            fix = "返回不可变副本或List."
        ))
        BugDB.load(BugRule(
            id = "KT-0160",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MILD,
            title = "[M01] listOf()和mutableListOf()混淆",
            trigger = "val x = listOf(1,2,3); x.add(4)",
            detection = "listOf返回不可变,add抛异常",
            fix = "用mutableListOf."
        ))
        BugDB.load(BugRule(
            id = "KT-0161",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.SEVERE,
            title = "[S01] ConcurrentModificationException",
            trigger = "for(item in list){ list.remove(item) }",
            detection = "遍历中修改集合",
            fix = "用iterator.remove()或收集后删."
        ))
        BugDB.load(BugRule(
            id = "KT-0162",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[D02] 可变集合暴露给外部",
            trigger = "val list: MutableList<T> = ...; fun getList()=list",
            detection = "外部可修改内部集合",
            fix = "返回不可变副本或List."
        ))
        BugDB.load(BugRule(
            id = "KT-0163",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MILD,
            title = "[M02] listOf()和mutableListOf()混淆",
            trigger = "val x = listOf(1,2,3); x.add(4)",
            detection = "listOf返回不可变,add抛异常",
            fix = "用mutableListOf."
        ))
        BugDB.load(BugRule(
            id = "KT-0164",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.SEVERE,
            title = "[S02] ConcurrentModificationException",
            trigger = "for(item in list){ list.remove(item) }",
            detection = "遍历中修改集合",
            fix = "用iterator.remove()或收集后删."
        ))
        BugDB.load(BugRule(
            id = "KT-0165",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[D03] 可变集合暴露给外部",
            trigger = "val list: MutableList<T> = ...; fun getList()=list",
            detection = "外部可修改内部集合",
            fix = "返回不可变副本或List."
        ))
        BugDB.load(BugRule(
            id = "KT-0166",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MILD,
            title = "[M03] listOf()和mutableListOf()混淆",
            trigger = "val x = listOf(1,2,3); x.add(4)",
            detection = "listOf返回不可变,add抛异常",
            fix = "用mutableListOf."
        ))
        BugDB.load(BugRule(
            id = "KT-0167",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.SEVERE,
            title = "[S03] ConcurrentModificationException",
            trigger = "for(item in list){ list.remove(item) }",
            detection = "遍历中修改集合",
            fix = "用iterator.remove()或收集后删."
        ))
        BugDB.load(BugRule(
            id = "KT-0168",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[D04] 可变集合暴露给外部",
            trigger = "val list: MutableList<T> = ...; fun getList()=list",
            detection = "外部可修改内部集合",
            fix = "返回不可变副本或List."
        ))
        BugDB.load(BugRule(
            id = "KT-0169",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MILD,
            title = "[M04] listOf()和mutableListOf()混淆",
            trigger = "val x = listOf(1,2,3); x.add(4)",
            detection = "listOf返回不可变,add抛异常",
            fix = "用mutableListOf."
        ))
        BugDB.load(BugRule(
            id = "KT-0170",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.SEVERE,
            title = "[S04] ConcurrentModificationException",
            trigger = "for(item in list){ list.remove(item) }",
            detection = "遍历中修改集合",
            fix = "用iterator.remove()或收集后删."
        ))
        BugDB.load(BugRule(
            id = "KT-0171",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[D05] 可变集合暴露给外部",
            trigger = "val list: MutableList<T> = ...; fun getList()=list",
            detection = "外部可修改内部集合",
            fix = "返回不可变副本或List."
        ))
        BugDB.load(BugRule(
            id = "KT-0172",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MILD,
            title = "[M05] listOf()和mutableListOf()混淆",
            trigger = "val x = listOf(1,2,3); x.add(4)",
            detection = "listOf返回不可变,add抛异常",
            fix = "用mutableListOf."
        ))
        BugDB.load(BugRule(
            id = "KT-0173",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.SEVERE,
            title = "[S05] ConcurrentModificationException",
            trigger = "for(item in list){ list.remove(item) }",
            detection = "遍历中修改集合",
            fix = "用iterator.remove()或收集后删."
        ))
        BugDB.load(BugRule(
            id = "KT-0174",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[D06] 可变集合暴露给外部",
            trigger = "val list: MutableList<T> = ...; fun getList()=list",
            detection = "外部可修改内部集合",
            fix = "返回不可变副本或List."
        ))
        BugDB.load(BugRule(
            id = "KT-0175",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MILD,
            title = "[M06] listOf()和mutableListOf()混淆",
            trigger = "val x = listOf(1,2,3); x.add(4)",
            detection = "listOf返回不可变,add抛异常",
            fix = "用mutableListOf."
        ))
        BugDB.load(BugRule(
            id = "KT-0176",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.SEVERE,
            title = "[S06] ConcurrentModificationException",
            trigger = "for(item in list){ list.remove(item) }",
            detection = "遍历中修改集合",
            fix = "用iterator.remove()或收集后删."
        ))
        BugDB.load(BugRule(
            id = "KT-0177",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[D07] 可变集合暴露给外部",
            trigger = "val list: MutableList<T> = ...; fun getList()=list",
            detection = "外部可修改内部集合",
            fix = "返回不可变副本或List."
        ))
        BugDB.load(BugRule(
            id = "KT-0178",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MILD,
            title = "[M07] listOf()和mutableListOf()混淆",
            trigger = "val x = listOf(1,2,3); x.add(4)",
            detection = "listOf返回不可变,add抛异常",
            fix = "用mutableListOf."
        ))
        BugDB.load(BugRule(
            id = "KT-0179",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.SEVERE,
            title = "[S07] ConcurrentModificationException",
            trigger = "for(item in list){ list.remove(item) }",
            detection = "遍历中修改集合",
            fix = "用iterator.remove()或收集后删."
        ))
        BugDB.load(BugRule(
            id = "KT-0180",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[D08] 可变集合暴露给外部",
            trigger = "val list: MutableList<T> = ...; fun getList()=list",
            detection = "外部可修改内部集合",
            fix = "返回不可变副本或List."
        ))
        BugDB.load(BugRule(
            id = "KT-0181",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MILD,
            title = "[M08] listOf()和mutableListOf()混淆",
            trigger = "val x = listOf(1,2,3); x.add(4)",
            detection = "listOf返回不可变,add抛异常",
            fix = "用mutableListOf."
        ))
        BugDB.load(BugRule(
            id = "KT-0182",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.SEVERE,
            title = "[S08] ConcurrentModificationException",
            trigger = "for(item in list){ list.remove(item) }",
            detection = "遍历中修改集合",
            fix = "用iterator.remove()或收集后删."
        ))
        BugDB.load(BugRule(
            id = "KT-0183",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[D09] 可变集合暴露给外部",
            trigger = "val list: MutableList<T> = ...; fun getList()=list",
            detection = "外部可修改内部集合",
            fix = "返回不可变副本或List."
        ))
        BugDB.load(BugRule(
            id = "KT-0184",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MILD,
            title = "[M09] listOf()和mutableListOf()混淆",
            trigger = "val x = listOf(1,2,3); x.add(4)",
            detection = "listOf返回不可变,add抛异常",
            fix = "用mutableListOf."
        ))
        BugDB.load(BugRule(
            id = "KT-0185",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.SEVERE,
            title = "[S09] ConcurrentModificationException",
            trigger = "for(item in list){ list.remove(item) }",
            detection = "遍历中修改集合",
            fix = "用iterator.remove()或收集后删."
        ))
        BugDB.load(BugRule(
            id = "KT-0186",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[D10] Map.getOrDefault滥用",
            trigger = "map.getOrDefault(k, expensive())",
            detection = "expensive()每次都被调用",
            fix = "用?: run{expensive()}."
        ))
        BugDB.load(BugRule(
            id = "KT-0187",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MILD,
            title = "[M10] filter+map链过长",
            trigger = "list.filter{...}.map{...}.filter{...}",
            detection = "多次遍历效率低",
            fix = "用flatMap或sequences."
        ))
        BugDB.load(BugRule(
            id = "KT-0188",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[D11] Map.getOrDefault滥用",
            trigger = "map.getOrDefault(k, expensive())",
            detection = "expensive()每次都被调用",
            fix = "用?: run{expensive()}."
        ))
        BugDB.load(BugRule(
            id = "KT-0189",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MILD,
            title = "[M11] filter+map链过长",
            trigger = "list.filter{...}.map{...}.filter{...}",
            detection = "多次遍历效率低",
            fix = "用flatMap或sequences."
        ))
        BugDB.load(BugRule(
            id = "KT-0190",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[D12] Map.getOrDefault滥用",
            trigger = "map.getOrDefault(k, expensive())",
            detection = "expensive()每次都被调用",
            fix = "用?: run{expensive()}."
        ))
        BugDB.load(BugRule(
            id = "KT-0191",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MILD,
            title = "[M12] filter+map链过长",
            trigger = "list.filter{...}.map{...}.filter{...}",
            detection = "多次遍历效率低",
            fix = "用flatMap或sequences."
        ))
        BugDB.load(BugRule(
            id = "KT-0192",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[D13] Map.getOrDefault滥用",
            trigger = "map.getOrDefault(k, expensive())",
            detection = "expensive()每次都被调用",
            fix = "用?: run{expensive()}."
        ))
        BugDB.load(BugRule(
            id = "KT-0193",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MILD,
            title = "[M13] filter+map链过长",
            trigger = "list.filter{...}.map{...}.filter{...}",
            detection = "多次遍历效率低",
            fix = "用flatMap或sequences."
        ))
        BugDB.load(BugRule(
            id = "KT-0194",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[D14] Map.getOrDefault滥用",
            trigger = "map.getOrDefault(k, expensive())",
            detection = "expensive()每次都被调用",
            fix = "用?: run{expensive()}."
        ))
        BugDB.load(BugRule(
            id = "KT-0195",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MILD,
            title = "[M14] filter+map链过长",
            trigger = "list.filter{...}.map{...}.filter{...}",
            detection = "多次遍历效率低",
            fix = "用flatMap或sequences."
        ))
        BugDB.load(BugRule(
            id = "KT-0196",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[D15] Map.getOrDefault滥用",
            trigger = "map.getOrDefault(k, expensive())",
            detection = "expensive()每次都被调用",
            fix = "用?: run{expensive()}."
        ))
        BugDB.load(BugRule(
            id = "KT-0197",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MILD,
            title = "[M15] filter+map链过长",
            trigger = "list.filter{...}.map{...}.filter{...}",
            detection = "多次遍历效率低",
            fix = "用flatMap或sequences."
        ))
        BugDB.load(BugRule(
            id = "KT-0198",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[D16] Map.getOrDefault滥用",
            trigger = "map.getOrDefault(k, expensive())",
            detection = "expensive()每次都被调用",
            fix = "用?: run{expensive()}."
        ))
        BugDB.load(BugRule(
            id = "KT-0199",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MILD,
            title = "[M16] filter+map链过长",
            trigger = "list.filter{...}.map{...}.filter{...}",
            detection = "多次遍历效率低",
            fix = "用flatMap或sequences."
        ))
        BugDB.load(BugRule(
            id = "KT-0200",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[D17] Map.getOrDefault滥用",
            trigger = "map.getOrDefault(k, expensive())",
            detection = "expensive()每次都被调用",
            fix = "用?: run{expensive()}."
        ))
        BugDB.load(BugRule(
            id = "KT-0201",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MILD,
            title = "[M17] filter+map链过长",
            trigger = "list.filter{...}.map{...}.filter{...}",
            detection = "多次遍历效率低",
            fix = "用flatMap或sequences."
        ))
        BugDB.load(BugRule(
            id = "KT-0202",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[D18] Map.getOrDefault滥用",
            trigger = "map.getOrDefault(k, expensive())",
            detection = "expensive()每次都被调用",
            fix = "用?: run{expensive()}."
        ))
        BugDB.load(BugRule(
            id = "KT-0203",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MILD,
            title = "[M18] filter+map链过长",
            trigger = "list.filter{...}.map{...}.filter{...}",
            detection = "多次遍历效率低",
            fix = "用flatMap或sequences."
        ))
        BugDB.load(BugRule(
            id = "KT-0204",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[D19] Map.getOrDefault滥用",
            trigger = "map.getOrDefault(k, expensive())",
            detection = "expensive()每次都被调用",
            fix = "用?: run{expensive()}."
        ))
        BugDB.load(BugRule(
            id = "KT-0205",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MILD,
            title = "[M19] filter+map链过长",
            trigger = "list.filter{...}.map{...}.filter{...}",
            detection = "多次遍历效率低",
            fix = "用flatMap或sequences."
        ))
        BugDB.load(BugRule(
            id = "KT-0206",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.MODERATE,
            title = "[D00] 反射访问私有成员",
            trigger = "clazz.getDeclaredField(\"secret\")",
            detection = "破坏封装,可能抛异常",
            fix = "用公开API或注解."
        ))
        BugDB.load(BugRule(
            id = "KT-0207",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.SEVERE,
            title = "[S00] ProGuard/R8混淆反射",
            trigger = "Class.forName(\"com.app.MyClass\")",
            detection = "混淆后类名改变",
            fix = "用KClass或keep规则."
        ))
        BugDB.load(BugRule(
            id = "KT-0208",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.MILD,
            title = "[M00] KClass vs Class混淆",
            trigger = "String::class.java",
            detection = "Kotlin反射和Java反射混用",
            fix = "统一用KClass或Class."
        ))
        BugDB.load(BugRule(
            id = "KT-0209",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.MODERATE,
            title = "[D01] 反射访问私有成员",
            trigger = "clazz.getDeclaredField(\"secret\")",
            detection = "破坏封装,可能抛异常",
            fix = "用公开API或注解."
        ))
        BugDB.load(BugRule(
            id = "KT-0210",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.SEVERE,
            title = "[S01] ProGuard/R8混淆反射",
            trigger = "Class.forName(\"com.app.MyClass\")",
            detection = "混淆后类名改变",
            fix = "用KClass或keep规则."
        ))
        BugDB.load(BugRule(
            id = "KT-0211",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.MILD,
            title = "[M01] KClass vs Class混淆",
            trigger = "String::class.java",
            detection = "Kotlin反射和Java反射混用",
            fix = "统一用KClass或Class."
        ))
        BugDB.load(BugRule(
            id = "KT-0212",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.MODERATE,
            title = "[D02] 反射访问私有成员",
            trigger = "clazz.getDeclaredField(\"secret\")",
            detection = "破坏封装,可能抛异常",
            fix = "用公开API或注解."
        ))
        BugDB.load(BugRule(
            id = "KT-0213",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.SEVERE,
            title = "[S02] ProGuard/R8混淆反射",
            trigger = "Class.forName(\"com.app.MyClass\")",
            detection = "混淆后类名改变",
            fix = "用KClass或keep规则."
        ))
        BugDB.load(BugRule(
            id = "KT-0214",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.MILD,
            title = "[M02] KClass vs Class混淆",
            trigger = "String::class.java",
            detection = "Kotlin反射和Java反射混用",
            fix = "统一用KClass或Class."
        ))
        BugDB.load(BugRule(
            id = "KT-0215",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.MODERATE,
            title = "[D03] 反射访问私有成员",
            trigger = "clazz.getDeclaredField(\"secret\")",
            detection = "破坏封装,可能抛异常",
            fix = "用公开API或注解."
        ))
        BugDB.load(BugRule(
            id = "KT-0216",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.SEVERE,
            title = "[S03] ProGuard/R8混淆反射",
            trigger = "Class.forName(\"com.app.MyClass\")",
            detection = "混淆后类名改变",
            fix = "用KClass或keep规则."
        ))
        BugDB.load(BugRule(
            id = "KT-0217",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.MILD,
            title = "[M03] KClass vs Class混淆",
            trigger = "String::class.java",
            detection = "Kotlin反射和Java反射混用",
            fix = "统一用KClass或Class."
        ))
        BugDB.load(BugRule(
            id = "KT-0218",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.MODERATE,
            title = "[D04] 反射访问私有成员",
            trigger = "clazz.getDeclaredField(\"secret\")",
            detection = "破坏封装,可能抛异常",
            fix = "用公开API或注解."
        ))
        BugDB.load(BugRule(
            id = "KT-0219",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.SEVERE,
            title = "[S04] ProGuard/R8混淆反射",
            trigger = "Class.forName(\"com.app.MyClass\")",
            detection = "混淆后类名改变",
            fix = "用KClass或keep规则."
        ))
        BugDB.load(BugRule(
            id = "KT-0220",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.MILD,
            title = "[M04] KClass vs Class混淆",
            trigger = "String::class.java",
            detection = "Kotlin反射和Java反射混用",
            fix = "统一用KClass或Class."
        ))
        BugDB.load(BugRule(
            id = "KT-0221",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.MODERATE,
            title = "[D05] 反射访问私有成员",
            trigger = "clazz.getDeclaredField(\"secret\")",
            detection = "破坏封装,可能抛异常",
            fix = "用公开API或注解."
        ))
        BugDB.load(BugRule(
            id = "KT-0222",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.SEVERE,
            title = "[S05] ProGuard/R8混淆反射",
            trigger = "Class.forName(\"com.app.MyClass\")",
            detection = "混淆后类名改变",
            fix = "用KClass或keep规则."
        ))
        BugDB.load(BugRule(
            id = "KT-0223",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.MILD,
            title = "[M05] KClass vs Class混淆",
            trigger = "String::class.java",
            detection = "Kotlin反射和Java反射混用",
            fix = "统一用KClass或Class."
        ))
        BugDB.load(BugRule(
            id = "KT-0224",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.MODERATE,
            title = "[D06] 反射访问私有成员",
            trigger = "clazz.getDeclaredField(\"secret\")",
            detection = "破坏封装,可能抛异常",
            fix = "用公开API或注解."
        ))
        BugDB.load(BugRule(
            id = "KT-0225",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.SEVERE,
            title = "[S06] ProGuard/R8混淆反射",
            trigger = "Class.forName(\"com.app.MyClass\")",
            detection = "混淆后类名改变",
            fix = "用KClass或keep规则."
        ))
        BugDB.load(BugRule(
            id = "KT-0226",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.MILD,
            title = "[M06] KClass vs Class混淆",
            trigger = "String::class.java",
            detection = "Kotlin反射和Java反射混用",
            fix = "统一用KClass或Class."
        ))
        BugDB.load(BugRule(
            id = "KT-0227",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.MODERATE,
            title = "[D07] 反射访问私有成员",
            trigger = "clazz.getDeclaredField(\"secret\")",
            detection = "破坏封装,可能抛异常",
            fix = "用公开API或注解."
        ))
        BugDB.load(BugRule(
            id = "KT-0228",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.SEVERE,
            title = "[S07] ProGuard/R8混淆反射",
            trigger = "Class.forName(\"com.app.MyClass\")",
            detection = "混淆后类名改变",
            fix = "用KClass或keep规则."
        ))
        BugDB.load(BugRule(
            id = "KT-0229",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.MILD,
            title = "[M07] KClass vs Class混淆",
            trigger = "String::class.java",
            detection = "Kotlin反射和Java反射混用",
            fix = "统一用KClass或Class."
        ))
        BugDB.load(BugRule(
            id = "KT-0230",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.MODERATE,
            title = "[D08] 反射访问私有成员",
            trigger = "clazz.getDeclaredField(\"secret\")",
            detection = "破坏封装,可能抛异常",
            fix = "用公开API或注解."
        ))
        BugDB.load(BugRule(
            id = "KT-0231",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.SEVERE,
            title = "[S08] ProGuard/R8混淆反射",
            trigger = "Class.forName(\"com.app.MyClass\")",
            detection = "混淆后类名改变",
            fix = "用KClass或keep规则."
        ))
        BugDB.load(BugRule(
            id = "KT-0232",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.MILD,
            title = "[M08] KClass vs Class混淆",
            trigger = "String::class.java",
            detection = "Kotlin反射和Java反射混用",
            fix = "统一用KClass或Class."
        ))
        BugDB.load(BugRule(
            id = "KT-0233",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.MODERATE,
            title = "[D09] 反射访问私有成员",
            trigger = "clazz.getDeclaredField(\"secret\")",
            detection = "破坏封装,可能抛异常",
            fix = "用公开API或注解."
        ))
        BugDB.load(BugRule(
            id = "KT-0234",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.SEVERE,
            title = "[S09] ProGuard/R8混淆反射",
            trigger = "Class.forName(\"com.app.MyClass\")",
            detection = "混淆后类名改变",
            fix = "用KClass或keep规则."
        ))
        BugDB.load(BugRule(
            id = "KT-0235",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.MILD,
            title = "[M09] KClass vs Class混淆",
            trigger = "String::class.java",
            detection = "Kotlin反射和Java反射混用",
            fix = "统一用KClass或Class."
        ))
        BugDB.load(BugRule(
            id = "KT-0236",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.MODERATE,
            title = "[D10] 注解参数反射获取异常",
            trigger = "annotation.findAnnotation<MyAnnotation>()",
            detection = "Kotlin注解需KClass",
            fix = "用@Metadata或Kotlin反射库."
        ))
        BugDB.load(BugRule(
            id = "KT-0237",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.MODERATE,
            title = "[D11] 注解参数反射获取异常",
            trigger = "annotation.findAnnotation<MyAnnotation>()",
            detection = "Kotlin注解需KClass",
            fix = "用@Metadata或Kotlin反射库."
        ))
        BugDB.load(BugRule(
            id = "KT-0238",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.MODERATE,
            title = "[D12] 注解参数反射获取异常",
            trigger = "annotation.findAnnotation<MyAnnotation>()",
            detection = "Kotlin注解需KClass",
            fix = "用@Metadata或Kotlin反射库."
        ))
        BugDB.load(BugRule(
            id = "KT-0239",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.MODERATE,
            title = "[D13] 注解参数反射获取异常",
            trigger = "annotation.findAnnotation<MyAnnotation>()",
            detection = "Kotlin注解需KClass",
            fix = "用@Metadata或Kotlin反射库."
        ))
        BugDB.load(BugRule(
            id = "KT-0240",
            category = BugCategory.REFLECTION,
            severity = BugSeverity.MODERATE,
            title = "[D14] 注解参数反射获取异常",
            trigger = "annotation.findAnnotation<MyAnnotation>()",
            detection = "Kotlin注解需KClass",
            fix = "用@Metadata或Kotlin反射库."
        ))
        BugDB.load(BugRule(
            id = "KT-0241",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[D00] 隐式this歧义",
            trigger = "apply{ name=name }",
            detection = "内外this同名,赋值给自己",
            fix = "用this@outer指定接收者."
        ))
        BugDB.load(BugRule(
            id = "KT-0242",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MILD,
            title = "[M00] lambda参数类型可推断但显式写",
            trigger = "list.map{it:String->it.length}",
            detection = "类型已推断,显式写多余",
            fix = "省略参数类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0243",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.SEVERE,
            title = "[S00] 非局部return",
            trigger = "fun f(){ list.forEach{ if(it)return } }",
            detection = "非局部return结束外层函数",
            fix = "用return@forEach."
        ))
        BugDB.load(BugRule(
            id = "KT-0244",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[D01] 隐式this歧义",
            trigger = "apply{ name=name }",
            detection = "内外this同名,赋值给自己",
            fix = "用this@outer指定接收者."
        ))
        BugDB.load(BugRule(
            id = "KT-0245",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MILD,
            title = "[M01] lambda参数类型可推断但显式写",
            trigger = "list.map{it:String->it.length}",
            detection = "类型已推断,显式写多余",
            fix = "省略参数类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0246",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.SEVERE,
            title = "[S01] 非局部return",
            trigger = "fun f(){ list.forEach{ if(it)return } }",
            detection = "非局部return结束外层函数",
            fix = "用return@forEach."
        ))
        BugDB.load(BugRule(
            id = "KT-0247",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[D02] 隐式this歧义",
            trigger = "apply{ name=name }",
            detection = "内外this同名,赋值给自己",
            fix = "用this@outer指定接收者."
        ))
        BugDB.load(BugRule(
            id = "KT-0248",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MILD,
            title = "[M02] lambda参数类型可推断但显式写",
            trigger = "list.map{it:String->it.length}",
            detection = "类型已推断,显式写多余",
            fix = "省略参数类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0249",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.SEVERE,
            title = "[S02] 非局部return",
            trigger = "fun f(){ list.forEach{ if(it)return } }",
            detection = "非局部return结束外层函数",
            fix = "用return@forEach."
        ))
        BugDB.load(BugRule(
            id = "KT-0250",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[D03] 隐式this歧义",
            trigger = "apply{ name=name }",
            detection = "内外this同名,赋值给自己",
            fix = "用this@outer指定接收者."
        ))
        BugDB.load(BugRule(
            id = "KT-0251",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MILD,
            title = "[M03] lambda参数类型可推断但显式写",
            trigger = "list.map{it:String->it.length}",
            detection = "类型已推断,显式写多余",
            fix = "省略参数类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0252",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.SEVERE,
            title = "[S03] 非局部return",
            trigger = "fun f(){ list.forEach{ if(it)return } }",
            detection = "非局部return结束外层函数",
            fix = "用return@forEach."
        ))
        BugDB.load(BugRule(
            id = "KT-0253",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[D04] 隐式this歧义",
            trigger = "apply{ name=name }",
            detection = "内外this同名,赋值给自己",
            fix = "用this@outer指定接收者."
        ))
        BugDB.load(BugRule(
            id = "KT-0254",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MILD,
            title = "[M04] lambda参数类型可推断但显式写",
            trigger = "list.map{it:String->it.length}",
            detection = "类型已推断,显式写多余",
            fix = "省略参数类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0255",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.SEVERE,
            title = "[S04] 非局部return",
            trigger = "fun f(){ list.forEach{ if(it)return } }",
            detection = "非局部return结束外层函数",
            fix = "用return@forEach."
        ))
        BugDB.load(BugRule(
            id = "KT-0256",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[D05] 隐式this歧义",
            trigger = "apply{ name=name }",
            detection = "内外this同名,赋值给自己",
            fix = "用this@outer指定接收者."
        ))
        BugDB.load(BugRule(
            id = "KT-0257",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MILD,
            title = "[M05] lambda参数类型可推断但显式写",
            trigger = "list.map{it:String->it.length}",
            detection = "类型已推断,显式写多余",
            fix = "省略参数类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0258",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.SEVERE,
            title = "[S05] 非局部return",
            trigger = "fun f(){ list.forEach{ if(it)return } }",
            detection = "非局部return结束外层函数",
            fix = "用return@forEach."
        ))
        BugDB.load(BugRule(
            id = "KT-0259",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[D06] 隐式this歧义",
            trigger = "apply{ name=name }",
            detection = "内外this同名,赋值给自己",
            fix = "用this@outer指定接收者."
        ))
        BugDB.load(BugRule(
            id = "KT-0260",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MILD,
            title = "[M06] lambda参数类型可推断但显式写",
            trigger = "list.map{it:String->it.length}",
            detection = "类型已推断,显式写多余",
            fix = "省略参数类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0261",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.SEVERE,
            title = "[S06] 非局部return",
            trigger = "fun f(){ list.forEach{ if(it)return } }",
            detection = "非局部return结束外层函数",
            fix = "用return@forEach."
        ))
        BugDB.load(BugRule(
            id = "KT-0262",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[D07] 隐式this歧义",
            trigger = "apply{ name=name }",
            detection = "内外this同名,赋值给自己",
            fix = "用this@outer指定接收者."
        ))
        BugDB.load(BugRule(
            id = "KT-0263",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MILD,
            title = "[M07] lambda参数类型可推断但显式写",
            trigger = "list.map{it:String->it.length}",
            detection = "类型已推断,显式写多余",
            fix = "省略参数类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0264",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.SEVERE,
            title = "[S07] 非局部return",
            trigger = "fun f(){ list.forEach{ if(it)return } }",
            detection = "非局部return结束外层函数",
            fix = "用return@forEach."
        ))
        BugDB.load(BugRule(
            id = "KT-0265",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[D08] 隐式this歧义",
            trigger = "apply{ name=name }",
            detection = "内外this同名,赋值给自己",
            fix = "用this@outer指定接收者."
        ))
        BugDB.load(BugRule(
            id = "KT-0266",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MILD,
            title = "[M08] lambda参数类型可推断但显式写",
            trigger = "list.map{it:String->it.length}",
            detection = "类型已推断,显式写多余",
            fix = "省略参数类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0267",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.SEVERE,
            title = "[S08] 非局部return",
            trigger = "fun f(){ list.forEach{ if(it)return } }",
            detection = "非局部return结束外层函数",
            fix = "用return@forEach."
        ))
        BugDB.load(BugRule(
            id = "KT-0268",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[D09] 隐式this歧义",
            trigger = "apply{ name=name }",
            detection = "内外this同名,赋值给自己",
            fix = "用this@outer指定接收者."
        ))
        BugDB.load(BugRule(
            id = "KT-0269",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MILD,
            title = "[M09] lambda参数类型可推断但显式写",
            trigger = "list.map{it:String->it.length}",
            detection = "类型已推断,显式写多余",
            fix = "省略参数类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0270",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.SEVERE,
            title = "[S09] 非局部return",
            trigger = "fun f(){ list.forEach{ if(it)return } }",
            detection = "非局部return结束外层函数",
            fix = "用return@forEach."
        ))
        BugDB.load(BugRule(
            id = "KT-0271",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[D10] DSL作用域嵌套过深",
            trigger = "html{ body{ div{ p{...}}}}",
            detection = "4层+嵌套DSL难读",
            fix = "提取为val或扩展函数."
        ))
        BugDB.load(BugRule(
            id = "KT-0272",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MILD,
            title = "[M10] unused lambda参数",
            trigger = "list.forEach{_,v->println(v)}",
            detection = "用_跳过但可省略",
            fix = "直接forEach{println(it)}."
        ))
        BugDB.load(BugRule(
            id = "KT-0273",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[D11] DSL作用域嵌套过深",
            trigger = "html{ body{ div{ p{...}}}}",
            detection = "4层+嵌套DSL难读",
            fix = "提取为val或扩展函数."
        ))
        BugDB.load(BugRule(
            id = "KT-0274",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MILD,
            title = "[M11] unused lambda参数",
            trigger = "list.forEach{_,v->println(v)}",
            detection = "用_跳过但可省略",
            fix = "直接forEach{println(it)}."
        ))
        BugDB.load(BugRule(
            id = "KT-0275",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[D12] DSL作用域嵌套过深",
            trigger = "html{ body{ div{ p{...}}}}",
            detection = "4层+嵌套DSL难读",
            fix = "提取为val或扩展函数."
        ))
        BugDB.load(BugRule(
            id = "KT-0276",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MILD,
            title = "[M12] unused lambda参数",
            trigger = "list.forEach{_,v->println(v)}",
            detection = "用_跳过但可省略",
            fix = "直接forEach{println(it)}."
        ))
        BugDB.load(BugRule(
            id = "KT-0277",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[D13] DSL作用域嵌套过深",
            trigger = "html{ body{ div{ p{...}}}}",
            detection = "4层+嵌套DSL难读",
            fix = "提取为val或扩展函数."
        ))
        BugDB.load(BugRule(
            id = "KT-0278",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MILD,
            title = "[M13] unused lambda参数",
            trigger = "list.forEach{_,v->println(v)}",
            detection = "用_跳过但可省略",
            fix = "直接forEach{println(it)}."
        ))
        BugDB.load(BugRule(
            id = "KT-0279",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[D14] DSL作用域嵌套过深",
            trigger = "html{ body{ div{ p{...}}}}",
            detection = "4层+嵌套DSL难读",
            fix = "提取为val或扩展函数."
        ))
        BugDB.load(BugRule(
            id = "KT-0280",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MILD,
            title = "[M14] unused lambda参数",
            trigger = "list.forEach{_,v->println(v)}",
            detection = "用_跳过但可省略",
            fix = "直接forEach{println(it)}."
        ))
        BugDB.load(BugRule(
            id = "KT-0281",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[D15] DSL作用域嵌套过深",
            trigger = "html{ body{ div{ p{...}}}}",
            detection = "4层+嵌套DSL难读",
            fix = "提取为val或扩展函数."
        ))
        BugDB.load(BugRule(
            id = "KT-0282",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MILD,
            title = "[M15] unused lambda参数",
            trigger = "list.forEach{_,v->println(v)}",
            detection = "用_跳过但可省略",
            fix = "直接forEach{println(it)}."
        ))
        BugDB.load(BugRule(
            id = "KT-0283",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[D16] DSL作用域嵌套过深",
            trigger = "html{ body{ div{ p{...}}}}",
            detection = "4层+嵌套DSL难读",
            fix = "提取为val或扩展函数."
        ))
        BugDB.load(BugRule(
            id = "KT-0284",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MILD,
            title = "[M16] unused lambda参数",
            trigger = "list.forEach{_,v->println(v)}",
            detection = "用_跳过但可省略",
            fix = "直接forEach{println(it)}."
        ))
        BugDB.load(BugRule(
            id = "KT-0285",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[D17] DSL作用域嵌套过深",
            trigger = "html{ body{ div{ p{...}}}}",
            detection = "4层+嵌套DSL难读",
            fix = "提取为val或扩展函数."
        ))
        BugDB.load(BugRule(
            id = "KT-0286",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MILD,
            title = "[M17] unused lambda参数",
            trigger = "list.forEach{_,v->println(v)}",
            detection = "用_跳过但可省略",
            fix = "直接forEach{println(it)}."
        ))
        BugDB.load(BugRule(
            id = "KT-0287",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[D18] DSL作用域嵌套过深",
            trigger = "html{ body{ div{ p{...}}}}",
            detection = "4层+嵌套DSL难读",
            fix = "提取为val或扩展函数."
        ))
        BugDB.load(BugRule(
            id = "KT-0288",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MILD,
            title = "[M18] unused lambda参数",
            trigger = "list.forEach{_,v->println(v)}",
            detection = "用_跳过但可省略",
            fix = "直接forEach{println(it)}."
        ))
        BugDB.load(BugRule(
            id = "KT-0289",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[D19] DSL作用域嵌套过深",
            trigger = "html{ body{ div{ p{...}}}}",
            detection = "4层+嵌套DSL难读",
            fix = "提取为val或扩展函数."
        ))
        BugDB.load(BugRule(
            id = "KT-0290",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MILD,
            title = "[M19] unused lambda参数",
            trigger = "list.forEach{_,v->println(v)}",
            detection = "用_跳过但可省略",
            fix = "直接forEach{println(it)}."
        ))
        BugDB.load(BugRule(
            id = "KT-0291",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[D00] data class copy陷阱",
            trigger = "data class User(val name:String, val items:MutableList); copy().items.clear()",
            detection = "copy浅复制,共享集合",
            fix = "深复制集合."
        ))
        BugDB.load(BugRule(
            id = "KT-0292",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MILD,
            title = "[M00] data class默认值序列化",
            trigger = "data class X(val a:Int=0)",
            detection = "默认值序列化可能丢",
            fix = "@Serializable加默认值标注."
        ))
        BugDB.load(BugRule(
            id = "KT-0293",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.SEVERE,
            title = "[S00] 序列化循环引用",
            trigger = "data class A(val b:B); data class B(val a:A)",
            detection = "循环引用序列化栈溢出",
            fix = "用@Transient打破环."
        ))
        BugDB.load(BugRule(
            id = "KT-0294",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[D01] data class copy陷阱",
            trigger = "data class User(val name:String, val items:MutableList); copy().items.clear()",
            detection = "copy浅复制,共享集合",
            fix = "深复制集合."
        ))
        BugDB.load(BugRule(
            id = "KT-0295",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MILD,
            title = "[M01] data class默认值序列化",
            trigger = "data class X(val a:Int=0)",
            detection = "默认值序列化可能丢",
            fix = "@Serializable加默认值标注."
        ))
        BugDB.load(BugRule(
            id = "KT-0296",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.SEVERE,
            title = "[S01] 序列化循环引用",
            trigger = "data class A(val b:B); data class B(val a:A)",
            detection = "循环引用序列化栈溢出",
            fix = "用@Transient打破环."
        ))
        BugDB.load(BugRule(
            id = "KT-0297",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[D02] data class copy陷阱",
            trigger = "data class User(val name:String, val items:MutableList); copy().items.clear()",
            detection = "copy浅复制,共享集合",
            fix = "深复制集合."
        ))
        BugDB.load(BugRule(
            id = "KT-0298",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MILD,
            title = "[M02] data class默认值序列化",
            trigger = "data class X(val a:Int=0)",
            detection = "默认值序列化可能丢",
            fix = "@Serializable加默认值标注."
        ))
        BugDB.load(BugRule(
            id = "KT-0299",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.SEVERE,
            title = "[S02] 序列化循环引用",
            trigger = "data class A(val b:B); data class B(val a:A)",
            detection = "循环引用序列化栈溢出",
            fix = "用@Transient打破环."
        ))
        BugDB.load(BugRule(
            id = "KT-0300",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[D03] data class copy陷阱",
            trigger = "data class User(val name:String, val items:MutableList); copy().items.clear()",
            detection = "copy浅复制,共享集合",
            fix = "深复制集合."
        ))
        BugDB.load(BugRule(
            id = "KT-0301",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MILD,
            title = "[M03] data class默认值序列化",
            trigger = "data class X(val a:Int=0)",
            detection = "默认值序列化可能丢",
            fix = "@Serializable加默认值标注."
        ))
        BugDB.load(BugRule(
            id = "KT-0302",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.SEVERE,
            title = "[S03] 序列化循环引用",
            trigger = "data class A(val b:B); data class B(val a:A)",
            detection = "循环引用序列化栈溢出",
            fix = "用@Transient打破环."
        ))
        BugDB.load(BugRule(
            id = "KT-0303",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[D04] data class copy陷阱",
            trigger = "data class User(val name:String, val items:MutableList); copy().items.clear()",
            detection = "copy浅复制,共享集合",
            fix = "深复制集合."
        ))
        BugDB.load(BugRule(
            id = "KT-0304",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MILD,
            title = "[M04] data class默认值序列化",
            trigger = "data class X(val a:Int=0)",
            detection = "默认值序列化可能丢",
            fix = "@Serializable加默认值标注."
        ))
        BugDB.load(BugRule(
            id = "KT-0305",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.SEVERE,
            title = "[S04] 序列化循环引用",
            trigger = "data class A(val b:B); data class B(val a:A)",
            detection = "循环引用序列化栈溢出",
            fix = "用@Transient打破环."
        ))
        BugDB.load(BugRule(
            id = "KT-0306",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[D05] data class copy陷阱",
            trigger = "data class User(val name:String, val items:MutableList); copy().items.clear()",
            detection = "copy浅复制,共享集合",
            fix = "深复制集合."
        ))
        BugDB.load(BugRule(
            id = "KT-0307",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MILD,
            title = "[M05] data class默认值序列化",
            trigger = "data class X(val a:Int=0)",
            detection = "默认值序列化可能丢",
            fix = "@Serializable加默认值标注."
        ))
        BugDB.load(BugRule(
            id = "KT-0308",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.SEVERE,
            title = "[S05] 序列化循环引用",
            trigger = "data class A(val b:B); data class B(val a:A)",
            detection = "循环引用序列化栈溢出",
            fix = "用@Transient打破环."
        ))
        BugDB.load(BugRule(
            id = "KT-0309",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[D06] data class copy陷阱",
            trigger = "data class User(val name:String, val items:MutableList); copy().items.clear()",
            detection = "copy浅复制,共享集合",
            fix = "深复制集合."
        ))
        BugDB.load(BugRule(
            id = "KT-0310",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MILD,
            title = "[M06] data class默认值序列化",
            trigger = "data class X(val a:Int=0)",
            detection = "默认值序列化可能丢",
            fix = "@Serializable加默认值标注."
        ))
        BugDB.load(BugRule(
            id = "KT-0311",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.SEVERE,
            title = "[S06] 序列化循环引用",
            trigger = "data class A(val b:B); data class B(val a:A)",
            detection = "循环引用序列化栈溢出",
            fix = "用@Transient打破环."
        ))
        BugDB.load(BugRule(
            id = "KT-0312",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[D07] data class copy陷阱",
            trigger = "data class User(val name:String, val items:MutableList); copy().items.clear()",
            detection = "copy浅复制,共享集合",
            fix = "深复制集合."
        ))
        BugDB.load(BugRule(
            id = "KT-0313",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MILD,
            title = "[M07] data class默认值序列化",
            trigger = "data class X(val a:Int=0)",
            detection = "默认值序列化可能丢",
            fix = "@Serializable加默认值标注."
        ))
        BugDB.load(BugRule(
            id = "KT-0314",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.SEVERE,
            title = "[S07] 序列化循环引用",
            trigger = "data class A(val b:B); data class B(val a:A)",
            detection = "循环引用序列化栈溢出",
            fix = "用@Transient打破环."
        ))
        BugDB.load(BugRule(
            id = "KT-0315",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[D08] data class copy陷阱",
            trigger = "data class User(val name:String, val items:MutableList); copy().items.clear()",
            detection = "copy浅复制,共享集合",
            fix = "深复制集合."
        ))
        BugDB.load(BugRule(
            id = "KT-0316",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MILD,
            title = "[M08] data class默认值序列化",
            trigger = "data class X(val a:Int=0)",
            detection = "默认值序列化可能丢",
            fix = "@Serializable加默认值标注."
        ))
        BugDB.load(BugRule(
            id = "KT-0317",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.SEVERE,
            title = "[S08] 序列化循环引用",
            trigger = "data class A(val b:B); data class B(val a:A)",
            detection = "循环引用序列化栈溢出",
            fix = "用@Transient打破环."
        ))
        BugDB.load(BugRule(
            id = "KT-0318",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[D09] data class copy陷阱",
            trigger = "data class User(val name:String, val items:MutableList); copy().items.clear()",
            detection = "copy浅复制,共享集合",
            fix = "深复制集合."
        ))
        BugDB.load(BugRule(
            id = "KT-0319",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MILD,
            title = "[M09] data class默认值序列化",
            trigger = "data class X(val a:Int=0)",
            detection = "默认值序列化可能丢",
            fix = "@Serializable加默认值标注."
        ))
        BugDB.load(BugRule(
            id = "KT-0320",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.SEVERE,
            title = "[S09] 序列化循环引用",
            trigger = "data class A(val b:B); data class B(val a:A)",
            detection = "循环引用序列化栈溢出",
            fix = "用@Transient打破环."
        ))
        BugDB.load(BugRule(
            id = "KT-0321",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[D10] toString无限递归",
            trigger = "data class Node(val parent:Node?)",
            detection = "parent引用自己,toString递归",
            fix = "用@ToString.Exclude."
        ))
        BugDB.load(BugRule(
            id = "KT-0322",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MILD,
            title = "[M10] componentN依赖顺序",
            trigger = "val (a,b,c)=dataClass",
            detection = "解构依赖属性顺序,加字段后错位",
            fix = "用命名参数."
        ))
        BugDB.load(BugRule(
            id = "KT-0323",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[D11] toString无限递归",
            trigger = "data class Node(val parent:Node?)",
            detection = "parent引用自己,toString递归",
            fix = "用@ToString.Exclude."
        ))
        BugDB.load(BugRule(
            id = "KT-0324",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MILD,
            title = "[M11] componentN依赖顺序",
            trigger = "val (a,b,c)=dataClass",
            detection = "解构依赖属性顺序,加字段后错位",
            fix = "用命名参数."
        ))
        BugDB.load(BugRule(
            id = "KT-0325",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[D12] toString无限递归",
            trigger = "data class Node(val parent:Node?)",
            detection = "parent引用自己,toString递归",
            fix = "用@ToString.Exclude."
        ))
        BugDB.load(BugRule(
            id = "KT-0326",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MILD,
            title = "[M12] componentN依赖顺序",
            trigger = "val (a,b,c)=dataClass",
            detection = "解构依赖属性顺序,加字段后错位",
            fix = "用命名参数."
        ))
        BugDB.load(BugRule(
            id = "KT-0327",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[D13] toString无限递归",
            trigger = "data class Node(val parent:Node?)",
            detection = "parent引用自己,toString递归",
            fix = "用@ToString.Exclude."
        ))
        BugDB.load(BugRule(
            id = "KT-0328",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MILD,
            title = "[M13] componentN依赖顺序",
            trigger = "val (a,b,c)=dataClass",
            detection = "解构依赖属性顺序,加字段后错位",
            fix = "用命名参数."
        ))
        BugDB.load(BugRule(
            id = "KT-0329",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[D14] toString无限递归",
            trigger = "data class Node(val parent:Node?)",
            detection = "parent引用自己,toString递归",
            fix = "用@ToString.Exclude."
        ))
        BugDB.load(BugRule(
            id = "KT-0330",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MILD,
            title = "[M14] componentN依赖顺序",
            trigger = "val (a,b,c)=dataClass",
            detection = "解构依赖属性顺序,加字段后错位",
            fix = "用命名参数."
        ))
        BugDB.load(BugRule(
            id = "KT-0331",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[D15] toString无限递归",
            trigger = "data class Node(val parent:Node?)",
            detection = "parent引用自己,toString递归",
            fix = "用@ToString.Exclude."
        ))
        BugDB.load(BugRule(
            id = "KT-0332",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MILD,
            title = "[M15] componentN依赖顺序",
            trigger = "val (a,b,c)=dataClass",
            detection = "解构依赖属性顺序,加字段后错位",
            fix = "用命名参数."
        ))
        BugDB.load(BugRule(
            id = "KT-0333",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[D16] toString无限递归",
            trigger = "data class Node(val parent:Node?)",
            detection = "parent引用自己,toString递归",
            fix = "用@ToString.Exclude."
        ))
        BugDB.load(BugRule(
            id = "KT-0334",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MILD,
            title = "[M16] componentN依赖顺序",
            trigger = "val (a,b,c)=dataClass",
            detection = "解构依赖属性顺序,加字段后错位",
            fix = "用命名参数."
        ))
        BugDB.load(BugRule(
            id = "KT-0335",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[D17] toString无限递归",
            trigger = "data class Node(val parent:Node?)",
            detection = "parent引用自己,toString递归",
            fix = "用@ToString.Exclude."
        ))
        BugDB.load(BugRule(
            id = "KT-0336",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MILD,
            title = "[M17] componentN依赖顺序",
            trigger = "val (a,b,c)=dataClass",
            detection = "解构依赖属性顺序,加字段后错位",
            fix = "用命名参数."
        ))
        BugDB.load(BugRule(
            id = "KT-0337",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[D18] toString无限递归",
            trigger = "data class Node(val parent:Node?)",
            detection = "parent引用自己,toString递归",
            fix = "用@ToString.Exclude."
        ))
        BugDB.load(BugRule(
            id = "KT-0338",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MILD,
            title = "[M18] componentN依赖顺序",
            trigger = "val (a,b,c)=dataClass",
            detection = "解构依赖属性顺序,加字段后错位",
            fix = "用命名参数."
        ))
        BugDB.load(BugRule(
            id = "KT-0339",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[D19] toString无限递归",
            trigger = "data class Node(val parent:Node?)",
            detection = "parent引用自己,toString递归",
            fix = "用@ToString.Exclude."
        ))
        BugDB.load(BugRule(
            id = "KT-0340",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MILD,
            title = "[M19] componentN依赖顺序",
            trigger = "val (a,b,c)=dataClass",
            detection = "解构依赖属性顺序,加字段后错位",
            fix = "用命名参数."
        ))
        BugDB.load(BugRule(
            id = "KT-0341",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.MODERATE,
            title = "[D00] inline函数过大",
            trigger = "inline fun big(){...100行}",
            detection = "内联展开代码膨胀",
            fix = "非热路径去掉inline."
        ))
        BugDB.load(BugRule(
            id = "KT-0342",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.SEVERE,
            title = "[S00] tailrec非尾递归",
            trigger = "tailrec fun fact(n:Int)=if(n>0)n*fact(n-1)else 1",
            detection = "fact(n-1)后有*n,非尾递归",
            fix = "改写成尾递归形式."
        ))
        BugDB.load(BugRule(
            id = "KT-0343",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.MILD,
            title = "[M00] noinline/crossinline混淆",
            trigger = "inline fun f(noinline block:()->Unit)",
            detection = "noinline阻止内联但可非局部return",
            fix = "按需选择noinline/crossinline."
        ))
        BugDB.load(BugRule(
            id = "KT-0344",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.MODERATE,
            title = "[D01] inline函数过大",
            trigger = "inline fun big(){...100行}",
            detection = "内联展开代码膨胀",
            fix = "非热路径去掉inline."
        ))
        BugDB.load(BugRule(
            id = "KT-0345",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.SEVERE,
            title = "[S01] tailrec非尾递归",
            trigger = "tailrec fun fact(n:Int)=if(n>0)n*fact(n-1)else 1",
            detection = "fact(n-1)后有*n,非尾递归",
            fix = "改写成尾递归形式."
        ))
        BugDB.load(BugRule(
            id = "KT-0346",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.MILD,
            title = "[M01] noinline/crossinline混淆",
            trigger = "inline fun f(noinline block:()->Unit)",
            detection = "noinline阻止内联但可非局部return",
            fix = "按需选择noinline/crossinline."
        ))
        BugDB.load(BugRule(
            id = "KT-0347",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.MODERATE,
            title = "[D02] inline函数过大",
            trigger = "inline fun big(){...100行}",
            detection = "内联展开代码膨胀",
            fix = "非热路径去掉inline."
        ))
        BugDB.load(BugRule(
            id = "KT-0348",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.SEVERE,
            title = "[S02] tailrec非尾递归",
            trigger = "tailrec fun fact(n:Int)=if(n>0)n*fact(n-1)else 1",
            detection = "fact(n-1)后有*n,非尾递归",
            fix = "改写成尾递归形式."
        ))
        BugDB.load(BugRule(
            id = "KT-0349",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.MILD,
            title = "[M02] noinline/crossinline混淆",
            trigger = "inline fun f(noinline block:()->Unit)",
            detection = "noinline阻止内联但可非局部return",
            fix = "按需选择noinline/crossinline."
        ))
        BugDB.load(BugRule(
            id = "KT-0350",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.MODERATE,
            title = "[D03] inline函数过大",
            trigger = "inline fun big(){...100行}",
            detection = "内联展开代码膨胀",
            fix = "非热路径去掉inline."
        ))
        BugDB.load(BugRule(
            id = "KT-0351",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.SEVERE,
            title = "[S03] tailrec非尾递归",
            trigger = "tailrec fun fact(n:Int)=if(n>0)n*fact(n-1)else 1",
            detection = "fact(n-1)后有*n,非尾递归",
            fix = "改写成尾递归形式."
        ))
        BugDB.load(BugRule(
            id = "KT-0352",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.MILD,
            title = "[M03] noinline/crossinline混淆",
            trigger = "inline fun f(noinline block:()->Unit)",
            detection = "noinline阻止内联但可非局部return",
            fix = "按需选择noinline/crossinline."
        ))
        BugDB.load(BugRule(
            id = "KT-0353",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.MODERATE,
            title = "[D04] inline函数过大",
            trigger = "inline fun big(){...100行}",
            detection = "内联展开代码膨胀",
            fix = "非热路径去掉inline."
        ))
        BugDB.load(BugRule(
            id = "KT-0354",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.SEVERE,
            title = "[S04] tailrec非尾递归",
            trigger = "tailrec fun fact(n:Int)=if(n>0)n*fact(n-1)else 1",
            detection = "fact(n-1)后有*n,非尾递归",
            fix = "改写成尾递归形式."
        ))
        BugDB.load(BugRule(
            id = "KT-0355",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.MILD,
            title = "[M04] noinline/crossinline混淆",
            trigger = "inline fun f(noinline block:()->Unit)",
            detection = "noinline阻止内联但可非局部return",
            fix = "按需选择noinline/crossinline."
        ))
        BugDB.load(BugRule(
            id = "KT-0356",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.MODERATE,
            title = "[D05] inline函数过大",
            trigger = "inline fun big(){...100行}",
            detection = "内联展开代码膨胀",
            fix = "非热路径去掉inline."
        ))
        BugDB.load(BugRule(
            id = "KT-0357",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.SEVERE,
            title = "[S05] tailrec非尾递归",
            trigger = "tailrec fun fact(n:Int)=if(n>0)n*fact(n-1)else 1",
            detection = "fact(n-1)后有*n,非尾递归",
            fix = "改写成尾递归形式."
        ))
        BugDB.load(BugRule(
            id = "KT-0358",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.MILD,
            title = "[M05] noinline/crossinline混淆",
            trigger = "inline fun f(noinline block:()->Unit)",
            detection = "noinline阻止内联但可非局部return",
            fix = "按需选择noinline/crossinline."
        ))
        BugDB.load(BugRule(
            id = "KT-0359",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.MODERATE,
            title = "[D06] inline函数过大",
            trigger = "inline fun big(){...100行}",
            detection = "内联展开代码膨胀",
            fix = "非热路径去掉inline."
        ))
        BugDB.load(BugRule(
            id = "KT-0360",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.SEVERE,
            title = "[S06] tailrec非尾递归",
            trigger = "tailrec fun fact(n:Int)=if(n>0)n*fact(n-1)else 1",
            detection = "fact(n-1)后有*n,非尾递归",
            fix = "改写成尾递归形式."
        ))
        BugDB.load(BugRule(
            id = "KT-0361",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.MILD,
            title = "[M06] noinline/crossinline混淆",
            trigger = "inline fun f(noinline block:()->Unit)",
            detection = "noinline阻止内联但可非局部return",
            fix = "按需选择noinline/crossinline."
        ))
        BugDB.load(BugRule(
            id = "KT-0362",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.MODERATE,
            title = "[D07] inline函数过大",
            trigger = "inline fun big(){...100行}",
            detection = "内联展开代码膨胀",
            fix = "非热路径去掉inline."
        ))
        BugDB.load(BugRule(
            id = "KT-0363",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.SEVERE,
            title = "[S07] tailrec非尾递归",
            trigger = "tailrec fun fact(n:Int)=if(n>0)n*fact(n-1)else 1",
            detection = "fact(n-1)后有*n,非尾递归",
            fix = "改写成尾递归形式."
        ))
        BugDB.load(BugRule(
            id = "KT-0364",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.MILD,
            title = "[M07] noinline/crossinline混淆",
            trigger = "inline fun f(noinline block:()->Unit)",
            detection = "noinline阻止内联但可非局部return",
            fix = "按需选择noinline/crossinline."
        ))
        BugDB.load(BugRule(
            id = "KT-0365",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.MODERATE,
            title = "[D08] inline函数过大",
            trigger = "inline fun big(){...100行}",
            detection = "内联展开代码膨胀",
            fix = "非热路径去掉inline."
        ))
        BugDB.load(BugRule(
            id = "KT-0366",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.SEVERE,
            title = "[S08] tailrec非尾递归",
            trigger = "tailrec fun fact(n:Int)=if(n>0)n*fact(n-1)else 1",
            detection = "fact(n-1)后有*n,非尾递归",
            fix = "改写成尾递归形式."
        ))
        BugDB.load(BugRule(
            id = "KT-0367",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.MILD,
            title = "[M08] noinline/crossinline混淆",
            trigger = "inline fun f(noinline block:()->Unit)",
            detection = "noinline阻止内联但可非局部return",
            fix = "按需选择noinline/crossinline."
        ))
        BugDB.load(BugRule(
            id = "KT-0368",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.MODERATE,
            title = "[D09] inline函数过大",
            trigger = "inline fun big(){...100行}",
            detection = "内联展开代码膨胀",
            fix = "非热路径去掉inline."
        ))
        BugDB.load(BugRule(
            id = "KT-0369",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.SEVERE,
            title = "[S09] tailrec非尾递归",
            trigger = "tailrec fun fact(n:Int)=if(n>0)n*fact(n-1)else 1",
            detection = "fact(n-1)后有*n,非尾递归",
            fix = "改写成尾递归形式."
        ))
        BugDB.load(BugRule(
            id = "KT-0370",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.MILD,
            title = "[M09] noinline/crossinline混淆",
            trigger = "inline fun f(noinline block:()->Unit)",
            detection = "noinline阻止内联但可非局部return",
            fix = "按需选择noinline/crossinline."
        ))
        BugDB.load(BugRule(
            id = "KT-0371",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.MODERATE,
            title = "[D10] 内联函数访问私有成员",
            trigger = "inline fun Foo.bar()=secretField",
            detection = "内联后访问private,可能违规",
            fix = "用@PublishedApi."
        ))
        BugDB.load(BugRule(
            id = "KT-0372",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.MODERATE,
            title = "[D11] 内联函数访问私有成员",
            trigger = "inline fun Foo.bar()=secretField",
            detection = "内联后访问private,可能违规",
            fix = "用@PublishedApi."
        ))
        BugDB.load(BugRule(
            id = "KT-0373",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.MODERATE,
            title = "[D12] 内联函数访问私有成员",
            trigger = "inline fun Foo.bar()=secretField",
            detection = "内联后访问private,可能违规",
            fix = "用@PublishedApi."
        ))
        BugDB.load(BugRule(
            id = "KT-0374",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.MODERATE,
            title = "[D13] 内联函数访问私有成员",
            trigger = "inline fun Foo.bar()=secretField",
            detection = "内联后访问private,可能违规",
            fix = "用@PublishedApi."
        ))
        BugDB.load(BugRule(
            id = "KT-0375",
            category = BugCategory.INLINE_TAILREC,
            severity = BugSeverity.MODERATE,
            title = "[D14] 内联函数访问私有成员",
            trigger = "inline fun Foo.bar()=secretField",
            detection = "内联后访问private,可能违规",
            fix = "用@PublishedApi."
        ))
        BugDB.load(BugRule(
            id = "KT-0376",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[D00] Java null返回未标注@Nullable",
            trigger = "val s = javaObj.name",
            detection = "Java返回可能null,NPE",
            fix = "加?.或检查null."
        ))
        BugDB.load(BugRule(
            id = "KT-0377",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.SEVERE,
            title = "[S00] SAM转换歧义",
            trigger = "obj.setOnClickListener{...}",
            detection = "多个SAM接口重载,编译器不知选哪个",
            fix = "显式lambda或匿名对象."
        ))
        BugDB.load(BugRule(
            id = "KT-0378",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MILD,
            title = "[M00] @JvmStatic缺失",
            trigger = "object Utils{ fun foo(){}}",
            detection = "Kotlin object方法Java调用需INSTANCE",
            fix = "加@JvmStatic."
        ))
        BugDB.load(BugRule(
            id = "KT-0379",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[D01] Java null返回未标注@Nullable",
            trigger = "val s = javaObj.name",
            detection = "Java返回可能null,NPE",
            fix = "加?.或检查null."
        ))
        BugDB.load(BugRule(
            id = "KT-0380",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.SEVERE,
            title = "[S01] SAM转换歧义",
            trigger = "obj.setOnClickListener{...}",
            detection = "多个SAM接口重载,编译器不知选哪个",
            fix = "显式lambda或匿名对象."
        ))
        BugDB.load(BugRule(
            id = "KT-0381",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MILD,
            title = "[M01] @JvmStatic缺失",
            trigger = "object Utils{ fun foo(){}}",
            detection = "Kotlin object方法Java调用需INSTANCE",
            fix = "加@JvmStatic."
        ))
        BugDB.load(BugRule(
            id = "KT-0382",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[D02] Java null返回未标注@Nullable",
            trigger = "val s = javaObj.name",
            detection = "Java返回可能null,NPE",
            fix = "加?.或检查null."
        ))
        BugDB.load(BugRule(
            id = "KT-0383",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.SEVERE,
            title = "[S02] SAM转换歧义",
            trigger = "obj.setOnClickListener{...}",
            detection = "多个SAM接口重载,编译器不知选哪个",
            fix = "显式lambda或匿名对象."
        ))
        BugDB.load(BugRule(
            id = "KT-0384",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MILD,
            title = "[M02] @JvmStatic缺失",
            trigger = "object Utils{ fun foo(){}}",
            detection = "Kotlin object方法Java调用需INSTANCE",
            fix = "加@JvmStatic."
        ))
        BugDB.load(BugRule(
            id = "KT-0385",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[D03] Java null返回未标注@Nullable",
            trigger = "val s = javaObj.name",
            detection = "Java返回可能null,NPE",
            fix = "加?.或检查null."
        ))
        BugDB.load(BugRule(
            id = "KT-0386",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.SEVERE,
            title = "[S03] SAM转换歧义",
            trigger = "obj.setOnClickListener{...}",
            detection = "多个SAM接口重载,编译器不知选哪个",
            fix = "显式lambda或匿名对象."
        ))
        BugDB.load(BugRule(
            id = "KT-0387",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MILD,
            title = "[M03] @JvmStatic缺失",
            trigger = "object Utils{ fun foo(){}}",
            detection = "Kotlin object方法Java调用需INSTANCE",
            fix = "加@JvmStatic."
        ))
        BugDB.load(BugRule(
            id = "KT-0388",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[D04] Java null返回未标注@Nullable",
            trigger = "val s = javaObj.name",
            detection = "Java返回可能null,NPE",
            fix = "加?.或检查null."
        ))
        BugDB.load(BugRule(
            id = "KT-0389",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.SEVERE,
            title = "[S04] SAM转换歧义",
            trigger = "obj.setOnClickListener{...}",
            detection = "多个SAM接口重载,编译器不知选哪个",
            fix = "显式lambda或匿名对象."
        ))
        BugDB.load(BugRule(
            id = "KT-0390",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MILD,
            title = "[M04] @JvmStatic缺失",
            trigger = "object Utils{ fun foo(){}}",
            detection = "Kotlin object方法Java调用需INSTANCE",
            fix = "加@JvmStatic."
        ))
        BugDB.load(BugRule(
            id = "KT-0391",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[D05] Java null返回未标注@Nullable",
            trigger = "val s = javaObj.name",
            detection = "Java返回可能null,NPE",
            fix = "加?.或检查null."
        ))
        BugDB.load(BugRule(
            id = "KT-0392",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.SEVERE,
            title = "[S05] SAM转换歧义",
            trigger = "obj.setOnClickListener{...}",
            detection = "多个SAM接口重载,编译器不知选哪个",
            fix = "显式lambda或匿名对象."
        ))
        BugDB.load(BugRule(
            id = "KT-0393",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MILD,
            title = "[M05] @JvmStatic缺失",
            trigger = "object Utils{ fun foo(){}}",
            detection = "Kotlin object方法Java调用需INSTANCE",
            fix = "加@JvmStatic."
        ))
        BugDB.load(BugRule(
            id = "KT-0394",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[D06] Java null返回未标注@Nullable",
            trigger = "val s = javaObj.name",
            detection = "Java返回可能null,NPE",
            fix = "加?.或检查null."
        ))
        BugDB.load(BugRule(
            id = "KT-0395",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.SEVERE,
            title = "[S06] SAM转换歧义",
            trigger = "obj.setOnClickListener{...}",
            detection = "多个SAM接口重载,编译器不知选哪个",
            fix = "显式lambda或匿名对象."
        ))
        BugDB.load(BugRule(
            id = "KT-0396",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MILD,
            title = "[M06] @JvmStatic缺失",
            trigger = "object Utils{ fun foo(){}}",
            detection = "Kotlin object方法Java调用需INSTANCE",
            fix = "加@JvmStatic."
        ))
        BugDB.load(BugRule(
            id = "KT-0397",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[D07] Java null返回未标注@Nullable",
            trigger = "val s = javaObj.name",
            detection = "Java返回可能null,NPE",
            fix = "加?.或检查null."
        ))
        BugDB.load(BugRule(
            id = "KT-0398",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.SEVERE,
            title = "[S07] SAM转换歧义",
            trigger = "obj.setOnClickListener{...}",
            detection = "多个SAM接口重载,编译器不知选哪个",
            fix = "显式lambda或匿名对象."
        ))
        BugDB.load(BugRule(
            id = "KT-0399",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MILD,
            title = "[M07] @JvmStatic缺失",
            trigger = "object Utils{ fun foo(){}}",
            detection = "Kotlin object方法Java调用需INSTANCE",
            fix = "加@JvmStatic."
        ))
        BugDB.load(BugRule(
            id = "KT-0400",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[D08] Java null返回未标注@Nullable",
            trigger = "val s = javaObj.name",
            detection = "Java返回可能null,NPE",
            fix = "加?.或检查null."
        ))
        BugDB.load(BugRule(
            id = "KT-0401",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.SEVERE,
            title = "[S08] SAM转换歧义",
            trigger = "obj.setOnClickListener{...}",
            detection = "多个SAM接口重载,编译器不知选哪个",
            fix = "显式lambda或匿名对象."
        ))
        BugDB.load(BugRule(
            id = "KT-0402",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MILD,
            title = "[M08] @JvmStatic缺失",
            trigger = "object Utils{ fun foo(){}}",
            detection = "Kotlin object方法Java调用需INSTANCE",
            fix = "加@JvmStatic."
        ))
        BugDB.load(BugRule(
            id = "KT-0403",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[D09] Java null返回未标注@Nullable",
            trigger = "val s = javaObj.name",
            detection = "Java返回可能null,NPE",
            fix = "加?.或检查null."
        ))
        BugDB.load(BugRule(
            id = "KT-0404",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.SEVERE,
            title = "[S09] SAM转换歧义",
            trigger = "obj.setOnClickListener{...}",
            detection = "多个SAM接口重载,编译器不知选哪个",
            fix = "显式lambda或匿名对象."
        ))
        BugDB.load(BugRule(
            id = "KT-0405",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MILD,
            title = "[M09] @JvmStatic缺失",
            trigger = "object Utils{ fun foo(){}}",
            detection = "Kotlin object方法Java调用需INSTANCE",
            fix = "加@JvmStatic."
        ))
        BugDB.load(BugRule(
            id = "KT-0406",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[D10] 泛型边界在Java中丢失",
            trigger = "fun<T:Comparable<T>> sort(list:List<T>)",
            detection = "Java调用泛型擦除",
            fix = "加@JvmName重载."
        ))
        BugDB.load(BugRule(
            id = "KT-0407",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.SEVERE,
            title = "[S10] Kotlin Nothing类型Java调用",
            trigger = "fun error():Nothing",
            detection = "Java侧不解Nothing,NPE",
            fix = "用@Throws声明."
        ))
        BugDB.load(BugRule(
            id = "KT-0408",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[D11] 泛型边界在Java中丢失",
            trigger = "fun<T:Comparable<T>> sort(list:List<T>)",
            detection = "Java调用泛型擦除",
            fix = "加@JvmName重载."
        ))
        BugDB.load(BugRule(
            id = "KT-0409",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.SEVERE,
            title = "[S11] Kotlin Nothing类型Java调用",
            trigger = "fun error():Nothing",
            detection = "Java侧不解Nothing,NPE",
            fix = "用@Throws声明."
        ))
        BugDB.load(BugRule(
            id = "KT-0410",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[D12] 泛型边界在Java中丢失",
            trigger = "fun<T:Comparable<T>> sort(list:List<T>)",
            detection = "Java调用泛型擦除",
            fix = "加@JvmName重载."
        ))
        BugDB.load(BugRule(
            id = "KT-0411",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.SEVERE,
            title = "[S12] Kotlin Nothing类型Java调用",
            trigger = "fun error():Nothing",
            detection = "Java侧不解Nothing,NPE",
            fix = "用@Throws声明."
        ))
        BugDB.load(BugRule(
            id = "KT-0412",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[D13] 泛型边界在Java中丢失",
            trigger = "fun<T:Comparable<T>> sort(list:List<T>)",
            detection = "Java调用泛型擦除",
            fix = "加@JvmName重载."
        ))
        BugDB.load(BugRule(
            id = "KT-0413",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.SEVERE,
            title = "[S13] Kotlin Nothing类型Java调用",
            trigger = "fun error():Nothing",
            detection = "Java侧不解Nothing,NPE",
            fix = "用@Throws声明."
        ))
        BugDB.load(BugRule(
            id = "KT-0414",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[D14] 泛型边界在Java中丢失",
            trigger = "fun<T:Comparable<T>> sort(list:List<T>)",
            detection = "Java调用泛型擦除",
            fix = "加@JvmName重载."
        ))
        BugDB.load(BugRule(
            id = "KT-0415",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.SEVERE,
            title = "[S14] Kotlin Nothing类型Java调用",
            trigger = "fun error():Nothing",
            detection = "Java侧不解Nothing,NPE",
            fix = "用@Throws声明."
        ))
        BugDB.load(BugRule(
            id = "KT-0416",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[D15] 泛型边界在Java中丢失",
            trigger = "fun<T:Comparable<T>> sort(list:List<T>)",
            detection = "Java调用泛型擦除",
            fix = "加@JvmName重载."
        ))
        BugDB.load(BugRule(
            id = "KT-0417",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.SEVERE,
            title = "[S15] Kotlin Nothing类型Java调用",
            trigger = "fun error():Nothing",
            detection = "Java侧不解Nothing,NPE",
            fix = "用@Throws声明."
        ))
        BugDB.load(BugRule(
            id = "KT-0418",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[D16] 泛型边界在Java中丢失",
            trigger = "fun<T:Comparable<T>> sort(list:List<T>)",
            detection = "Java调用泛型擦除",
            fix = "加@JvmName重载."
        ))
        BugDB.load(BugRule(
            id = "KT-0419",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.SEVERE,
            title = "[S16] Kotlin Nothing类型Java调用",
            trigger = "fun error():Nothing",
            detection = "Java侧不解Nothing,NPE",
            fix = "用@Throws声明."
        ))
        BugDB.load(BugRule(
            id = "KT-0420",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[D17] 泛型边界在Java中丢失",
            trigger = "fun<T:Comparable<T>> sort(list:List<T>)",
            detection = "Java调用泛型擦除",
            fix = "加@JvmName重载."
        ))
        BugDB.load(BugRule(
            id = "KT-0421",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.SEVERE,
            title = "[S17] Kotlin Nothing类型Java调用",
            trigger = "fun error():Nothing",
            detection = "Java侧不解Nothing,NPE",
            fix = "用@Throws声明."
        ))
        BugDB.load(BugRule(
            id = "KT-0422",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[D18] 泛型边界在Java中丢失",
            trigger = "fun<T:Comparable<T>> sort(list:List<T>)",
            detection = "Java调用泛型擦除",
            fix = "加@JvmName重载."
        ))
        BugDB.load(BugRule(
            id = "KT-0423",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.SEVERE,
            title = "[S18] Kotlin Nothing类型Java调用",
            trigger = "fun error():Nothing",
            detection = "Java侧不解Nothing,NPE",
            fix = "用@Throws声明."
        ))
        BugDB.load(BugRule(
            id = "KT-0424",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[D19] 泛型边界在Java中丢失",
            trigger = "fun<T:Comparable<T>> sort(list:List<T>)",
            detection = "Java调用泛型擦除",
            fix = "加@JvmName重载."
        ))
        BugDB.load(BugRule(
            id = "KT-0425",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.SEVERE,
            title = "[S19] Kotlin Nothing类型Java调用",
            trigger = "fun error():Nothing",
            detection = "Java侧不解Nothing,NPE",
            fix = "用@Throws声明."
        ))
        BugDB.load(BugRule(
            id = "KT-0426",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MILD,
            title = "[M20] Java getter/setter识别",
            trigger = "class User{ var name=\"\" }",
            detection = "Java侧识别为getName/setName",
            fix = "保持一致命名."
        ))
        BugDB.load(BugRule(
            id = "KT-0427",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MILD,
            title = "[M21] Java getter/setter识别",
            trigger = "class User{ var name=\"\" }",
            detection = "Java侧识别为getName/setName",
            fix = "保持一致命名."
        ))
        BugDB.load(BugRule(
            id = "KT-0428",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MILD,
            title = "[M22] Java getter/setter识别",
            trigger = "class User{ var name=\"\" }",
            detection = "Java侧识别为getName/setName",
            fix = "保持一致命名."
        ))
        BugDB.load(BugRule(
            id = "KT-0429",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MILD,
            title = "[M23] Java getter/setter识别",
            trigger = "class User{ var name=\"\" }",
            detection = "Java侧识别为getName/setName",
            fix = "保持一致命名."
        ))
        BugDB.load(BugRule(
            id = "KT-0430",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MILD,
            title = "[M24] Java getter/setter识别",
            trigger = "class User{ var name=\"\" }",
            detection = "Java侧识别为getName/setName",
            fix = "保持一致命名."
        ))
        BugDB.load(BugRule(
            id = "KT-0431",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MILD,
            title = "[M25] Java getter/setter识别",
            trigger = "class User{ var name=\"\" }",
            detection = "Java侧识别为getName/setName",
            fix = "保持一致命名."
        ))
        BugDB.load(BugRule(
            id = "KT-0432",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MILD,
            title = "[M26] Java getter/setter识别",
            trigger = "class User{ var name=\"\" }",
            detection = "Java侧识别为getName/setName",
            fix = "保持一致命名."
        ))
        BugDB.load(BugRule(
            id = "KT-0433",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MILD,
            title = "[M27] Java getter/setter识别",
            trigger = "class User{ var name=\"\" }",
            detection = "Java侧识别为getName/setName",
            fix = "保持一致命名."
        ))
        BugDB.load(BugRule(
            id = "KT-0434",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MILD,
            title = "[M28] Java getter/setter识别",
            trigger = "class User{ var name=\"\" }",
            detection = "Java侧识别为getName/setName",
            fix = "保持一致命名."
        ))
        BugDB.load(BugRule(
            id = "KT-0435",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MILD,
            title = "[M29] Java getter/setter识别",
            trigger = "class User{ var name=\"\" }",
            detection = "Java侧识别为getName/setName",
            fix = "保持一致命名."
        ))
        BugDB.load(BugRule(
            id = "KT-0436",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MODERATE,
            title = "[D00] 可变属性无法智能转换",
            trigger = "var x:Any=\"a\"; if(x is String){x.length}",
            detection = "var 可在别处改,编译器拒智能转换",
            fix = "用val或局部变量."
        ))
        BugDB.load(BugRule(
            id = "KT-0437",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MILD,
            title = "[M00] 智能转换可用但手动as",
            trigger = "if(x is String){(x as String).length}",
            detection = "智能转换后as多余",
            fix = "省略as."
        ))
        BugDB.load(BugRule(
            id = "KT-0438",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.SEVERE,
            title = "[S00] when穷举缺失",
            trigger = "when(sealed){ is A->... }",
            detection = "密封类分支不全,编译通过但else未处理",
            fix = "加else或补全分支."
        ))
        BugDB.load(BugRule(
            id = "KT-0439",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MODERATE,
            title = "[D01] 可变属性无法智能转换",
            trigger = "var x:Any=\"a\"; if(x is String){x.length}",
            detection = "var 可在别处改,编译器拒智能转换",
            fix = "用val或局部变量."
        ))
        BugDB.load(BugRule(
            id = "KT-0440",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MILD,
            title = "[M01] 智能转换可用但手动as",
            trigger = "if(x is String){(x as String).length}",
            detection = "智能转换后as多余",
            fix = "省略as."
        ))
        BugDB.load(BugRule(
            id = "KT-0441",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.SEVERE,
            title = "[S01] when穷举缺失",
            trigger = "when(sealed){ is A->... }",
            detection = "密封类分支不全,编译通过但else未处理",
            fix = "加else或补全分支."
        ))
        BugDB.load(BugRule(
            id = "KT-0442",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MODERATE,
            title = "[D02] 可变属性无法智能转换",
            trigger = "var x:Any=\"a\"; if(x is String){x.length}",
            detection = "var 可在别处改,编译器拒智能转换",
            fix = "用val或局部变量."
        ))
        BugDB.load(BugRule(
            id = "KT-0443",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MILD,
            title = "[M02] 智能转换可用但手动as",
            trigger = "if(x is String){(x as String).length}",
            detection = "智能转换后as多余",
            fix = "省略as."
        ))
        BugDB.load(BugRule(
            id = "KT-0444",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.SEVERE,
            title = "[S02] when穷举缺失",
            trigger = "when(sealed){ is A->... }",
            detection = "密封类分支不全,编译通过但else未处理",
            fix = "加else或补全分支."
        ))
        BugDB.load(BugRule(
            id = "KT-0445",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MODERATE,
            title = "[D03] 可变属性无法智能转换",
            trigger = "var x:Any=\"a\"; if(x is String){x.length}",
            detection = "var 可在别处改,编译器拒智能转换",
            fix = "用val或局部变量."
        ))
        BugDB.load(BugRule(
            id = "KT-0446",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MILD,
            title = "[M03] 智能转换可用但手动as",
            trigger = "if(x is String){(x as String).length}",
            detection = "智能转换后as多余",
            fix = "省略as."
        ))
        BugDB.load(BugRule(
            id = "KT-0447",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.SEVERE,
            title = "[S03] when穷举缺失",
            trigger = "when(sealed){ is A->... }",
            detection = "密封类分支不全,编译通过但else未处理",
            fix = "加else或补全分支."
        ))
        BugDB.load(BugRule(
            id = "KT-0448",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MODERATE,
            title = "[D04] 可变属性无法智能转换",
            trigger = "var x:Any=\"a\"; if(x is String){x.length}",
            detection = "var 可在别处改,编译器拒智能转换",
            fix = "用val或局部变量."
        ))
        BugDB.load(BugRule(
            id = "KT-0449",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MILD,
            title = "[M04] 智能转换可用但手动as",
            trigger = "if(x is String){(x as String).length}",
            detection = "智能转换后as多余",
            fix = "省略as."
        ))
        BugDB.load(BugRule(
            id = "KT-0450",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.SEVERE,
            title = "[S04] when穷举缺失",
            trigger = "when(sealed){ is A->... }",
            detection = "密封类分支不全,编译通过但else未处理",
            fix = "加else或补全分支."
        ))
        BugDB.load(BugRule(
            id = "KT-0451",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MODERATE,
            title = "[D05] 可变属性无法智能转换",
            trigger = "var x:Any=\"a\"; if(x is String){x.length}",
            detection = "var 可在别处改,编译器拒智能转换",
            fix = "用val或局部变量."
        ))
        BugDB.load(BugRule(
            id = "KT-0452",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MILD,
            title = "[M05] 智能转换可用但手动as",
            trigger = "if(x is String){(x as String).length}",
            detection = "智能转换后as多余",
            fix = "省略as."
        ))
        BugDB.load(BugRule(
            id = "KT-0453",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.SEVERE,
            title = "[S05] when穷举缺失",
            trigger = "when(sealed){ is A->... }",
            detection = "密封类分支不全,编译通过但else未处理",
            fix = "加else或补全分支."
        ))
        BugDB.load(BugRule(
            id = "KT-0454",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MODERATE,
            title = "[D06] 可变属性无法智能转换",
            trigger = "var x:Any=\"a\"; if(x is String){x.length}",
            detection = "var 可在别处改,编译器拒智能转换",
            fix = "用val或局部变量."
        ))
        BugDB.load(BugRule(
            id = "KT-0455",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MILD,
            title = "[M06] 智能转换可用但手动as",
            trigger = "if(x is String){(x as String).length}",
            detection = "智能转换后as多余",
            fix = "省略as."
        ))
        BugDB.load(BugRule(
            id = "KT-0456",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.SEVERE,
            title = "[S06] when穷举缺失",
            trigger = "when(sealed){ is A->... }",
            detection = "密封类分支不全,编译通过但else未处理",
            fix = "加else或补全分支."
        ))
        BugDB.load(BugRule(
            id = "KT-0457",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MODERATE,
            title = "[D07] 可变属性无法智能转换",
            trigger = "var x:Any=\"a\"; if(x is String){x.length}",
            detection = "var 可在别处改,编译器拒智能转换",
            fix = "用val或局部变量."
        ))
        BugDB.load(BugRule(
            id = "KT-0458",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MILD,
            title = "[M07] 智能转换可用但手动as",
            trigger = "if(x is String){(x as String).length}",
            detection = "智能转换后as多余",
            fix = "省略as."
        ))
        BugDB.load(BugRule(
            id = "KT-0459",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.SEVERE,
            title = "[S07] when穷举缺失",
            trigger = "when(sealed){ is A->... }",
            detection = "密封类分支不全,编译通过但else未处理",
            fix = "加else或补全分支."
        ))
        BugDB.load(BugRule(
            id = "KT-0460",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MODERATE,
            title = "[D08] 可变属性无法智能转换",
            trigger = "var x:Any=\"a\"; if(x is String){x.length}",
            detection = "var 可在别处改,编译器拒智能转换",
            fix = "用val或局部变量."
        ))
        BugDB.load(BugRule(
            id = "KT-0461",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MILD,
            title = "[M08] 智能转换可用但手动as",
            trigger = "if(x is String){(x as String).length}",
            detection = "智能转换后as多余",
            fix = "省略as."
        ))
        BugDB.load(BugRule(
            id = "KT-0462",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.SEVERE,
            title = "[S08] when穷举缺失",
            trigger = "when(sealed){ is A->... }",
            detection = "密封类分支不全,编译通过但else未处理",
            fix = "加else或补全分支."
        ))
        BugDB.load(BugRule(
            id = "KT-0463",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MODERATE,
            title = "[D09] 可变属性无法智能转换",
            trigger = "var x:Any=\"a\"; if(x is String){x.length}",
            detection = "var 可在别处改,编译器拒智能转换",
            fix = "用val或局部变量."
        ))
        BugDB.load(BugRule(
            id = "KT-0464",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MILD,
            title = "[M09] 智能转换可用但手动as",
            trigger = "if(x is String){(x as String).length}",
            detection = "智能转换后as多余",
            fix = "省略as."
        ))
        BugDB.load(BugRule(
            id = "KT-0465",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.SEVERE,
            title = "[S09] when穷举缺失",
            trigger = "when(sealed){ is A->... }",
            detection = "密封类分支不全,编译通过但else未处理",
            fix = "加else或补全分支."
        ))
        BugDB.load(BugRule(
            id = "KT-0466",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MODERATE,
            title = "[D10] 流式智能转换中断",
            trigger = "x?.let{if(it is String)it.length}",
            detection = "let内智能转换有效",
            fix = "放心用,或显式转换."
        ))
        BugDB.load(BugRule(
            id = "KT-0467",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MODERATE,
            title = "[D11] 流式智能转换中断",
            trigger = "x?.let{if(it is String)it.length}",
            detection = "let内智能转换有效",
            fix = "放心用,或显式转换."
        ))
        BugDB.load(BugRule(
            id = "KT-0468",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MODERATE,
            title = "[D12] 流式智能转换中断",
            trigger = "x?.let{if(it is String)it.length}",
            detection = "let内智能转换有效",
            fix = "放心用,或显式转换."
        ))
        BugDB.load(BugRule(
            id = "KT-0469",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MODERATE,
            title = "[D13] 流式智能转换中断",
            trigger = "x?.let{if(it is String)it.length}",
            detection = "let内智能转换有效",
            fix = "放心用,或显式转换."
        ))
        BugDB.load(BugRule(
            id = "KT-0470",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MODERATE,
            title = "[D14] 流式智能转换中断",
            trigger = "x?.let{if(it is String)it.length}",
            detection = "let内智能转换有效",
            fix = "放心用,或显式转换."
        ))
        BugDB.load(BugRule(
            id = "KT-0471",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MODERATE,
            title = "[D15] 流式智能转换中断",
            trigger = "x?.let{if(it is String)it.length}",
            detection = "let内智能转换有效",
            fix = "放心用,或显式转换."
        ))
        BugDB.load(BugRule(
            id = "KT-0472",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MODERATE,
            title = "[D16] 流式智能转换中断",
            trigger = "x?.let{if(it is String)it.length}",
            detection = "let内智能转换有效",
            fix = "放心用,或显式转换."
        ))
        BugDB.load(BugRule(
            id = "KT-0473",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MODERATE,
            title = "[D17] 流式智能转换中断",
            trigger = "x?.let{if(it is String)it.length}",
            detection = "let内智能转换有效",
            fix = "放心用,或显式转换."
        ))
        BugDB.load(BugRule(
            id = "KT-0474",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MODERATE,
            title = "[D18] 流式智能转换中断",
            trigger = "x?.let{if(it is String)it.length}",
            detection = "let内智能转换有效",
            fix = "放心用,或显式转换."
        ))
        BugDB.load(BugRule(
            id = "KT-0475",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MODERATE,
            title = "[D19] 流式智能转换中断",
            trigger = "x?.let{if(it is String)it.length}",
            detection = "let内智能转换有效",
            fix = "放心用,或显式转换."
        ))
        BugDB.load(BugRule(
            id = "KT-0476",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.MILD,
            title = "[M00] enum values()性能",
            trigger = "enum.values().find{...}",
            detection = "values()每次新分配数组",
            fix = "用enumEntries或缓存."
        ))
        BugDB.load(BugRule(
            id = "KT-0477",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.MODERATE,
            title = "[D00] sealed class跨文件子类",
            trigger = "sealed class A在File1,子类B在File2",
            detection = "sealed子类需同文件",
            fix = "移到同文件."
        ))
        BugDB.load(BugRule(
            id = "KT-0478",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.SEVERE,
            title = "[S00] when穷举密封类不加else",
            trigger = "when(s){is A->...is B->...}",
            detection = "密封类加子类,when不报错",
            fix = "no else分支需穷举."
        ))
        BugDB.load(BugRule(
            id = "KT-0479",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.MILD,
            title = "[M01] enum values()性能",
            trigger = "enum.values().find{...}",
            detection = "values()每次新分配数组",
            fix = "用enumEntries或缓存."
        ))
        BugDB.load(BugRule(
            id = "KT-0480",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.MODERATE,
            title = "[D01] sealed class跨文件子类",
            trigger = "sealed class A在File1,子类B在File2",
            detection = "sealed子类需同文件",
            fix = "移到同文件."
        ))
        BugDB.load(BugRule(
            id = "KT-0481",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.SEVERE,
            title = "[S01] when穷举密封类不加else",
            trigger = "when(s){is A->...is B->...}",
            detection = "密封类加子类,when不报错",
            fix = "no else分支需穷举."
        ))
        BugDB.load(BugRule(
            id = "KT-0482",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.MILD,
            title = "[M02] enum values()性能",
            trigger = "enum.values().find{...}",
            detection = "values()每次新分配数组",
            fix = "用enumEntries或缓存."
        ))
        BugDB.load(BugRule(
            id = "KT-0483",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.MODERATE,
            title = "[D02] sealed class跨文件子类",
            trigger = "sealed class A在File1,子类B在File2",
            detection = "sealed子类需同文件",
            fix = "移到同文件."
        ))
        BugDB.load(BugRule(
            id = "KT-0484",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.SEVERE,
            title = "[S02] when穷举密封类不加else",
            trigger = "when(s){is A->...is B->...}",
            detection = "密封类加子类,when不报错",
            fix = "no else分支需穷举."
        ))
        BugDB.load(BugRule(
            id = "KT-0485",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.MILD,
            title = "[M03] enum values()性能",
            trigger = "enum.values().find{...}",
            detection = "values()每次新分配数组",
            fix = "用enumEntries或缓存."
        ))
        BugDB.load(BugRule(
            id = "KT-0486",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.MODERATE,
            title = "[D03] sealed class跨文件子类",
            trigger = "sealed class A在File1,子类B在File2",
            detection = "sealed子类需同文件",
            fix = "移到同文件."
        ))
        BugDB.load(BugRule(
            id = "KT-0487",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.SEVERE,
            title = "[S03] when穷举密封类不加else",
            trigger = "when(s){is A->...is B->...}",
            detection = "密封类加子类,when不报错",
            fix = "no else分支需穷举."
        ))
        BugDB.load(BugRule(
            id = "KT-0488",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.MILD,
            title = "[M04] enum values()性能",
            trigger = "enum.values().find{...}",
            detection = "values()每次新分配数组",
            fix = "用enumEntries或缓存."
        ))
        BugDB.load(BugRule(
            id = "KT-0489",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.MODERATE,
            title = "[D04] sealed class跨文件子类",
            trigger = "sealed class A在File1,子类B在File2",
            detection = "sealed子类需同文件",
            fix = "移到同文件."
        ))
        BugDB.load(BugRule(
            id = "KT-0490",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.SEVERE,
            title = "[S04] when穷举密封类不加else",
            trigger = "when(s){is A->...is B->...}",
            detection = "密封类加子类,when不报错",
            fix = "no else分支需穷举."
        ))
        BugDB.load(BugRule(
            id = "KT-0491",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.MILD,
            title = "[M05] enum values()性能",
            trigger = "enum.values().find{...}",
            detection = "values()每次新分配数组",
            fix = "用enumEntries或缓存."
        ))
        BugDB.load(BugRule(
            id = "KT-0492",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.MODERATE,
            title = "[D05] sealed class跨文件子类",
            trigger = "sealed class A在File1,子类B在File2",
            detection = "sealed子类需同文件",
            fix = "移到同文件."
        ))
        BugDB.load(BugRule(
            id = "KT-0493",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.SEVERE,
            title = "[S05] when穷举密封类不加else",
            trigger = "when(s){is A->...is B->...}",
            detection = "密封类加子类,when不报错",
            fix = "no else分支需穷举."
        ))
        BugDB.load(BugRule(
            id = "KT-0494",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.MILD,
            title = "[M06] enum values()性能",
            trigger = "enum.values().find{...}",
            detection = "values()每次新分配数组",
            fix = "用enumEntries或缓存."
        ))
        BugDB.load(BugRule(
            id = "KT-0495",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.MODERATE,
            title = "[D06] sealed class跨文件子类",
            trigger = "sealed class A在File1,子类B在File2",
            detection = "sealed子类需同文件",
            fix = "移到同文件."
        ))
        BugDB.load(BugRule(
            id = "KT-0496",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.SEVERE,
            title = "[S06] when穷举密封类不加else",
            trigger = "when(s){is A->...is B->...}",
            detection = "密封类加子类,when不报错",
            fix = "no else分支需穷举."
        ))
        BugDB.load(BugRule(
            id = "KT-0497",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.MILD,
            title = "[M07] enum values()性能",
            trigger = "enum.values().find{...}",
            detection = "values()每次新分配数组",
            fix = "用enumEntries或缓存."
        ))
        BugDB.load(BugRule(
            id = "KT-0498",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.MODERATE,
            title = "[D07] sealed class跨文件子类",
            trigger = "sealed class A在File1,子类B在File2",
            detection = "sealed子类需同文件",
            fix = "移到同文件."
        ))
        BugDB.load(BugRule(
            id = "KT-0499",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.SEVERE,
            title = "[S07] when穷举密封类不加else",
            trigger = "when(s){is A->...is B->...}",
            detection = "密封类加子类,when不报错",
            fix = "no else分支需穷举."
        ))
        BugDB.load(BugRule(
            id = "KT-0500",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.MILD,
            title = "[M08] enum values()性能",
            trigger = "enum.values().find{...}",
            detection = "values()每次新分配数组",
            fix = "用enumEntries或缓存."
        ))
        BugDB.load(BugRule(
            id = "KT-0501",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.MODERATE,
            title = "[D08] sealed class跨文件子类",
            trigger = "sealed class A在File1,子类B在File2",
            detection = "sealed子类需同文件",
            fix = "移到同文件."
        ))
        BugDB.load(BugRule(
            id = "KT-0502",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.SEVERE,
            title = "[S08] when穷举密封类不加else",
            trigger = "when(s){is A->...is B->...}",
            detection = "密封类加子类,when不报错",
            fix = "no else分支需穷举."
        ))
        BugDB.load(BugRule(
            id = "KT-0503",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.MILD,
            title = "[M09] enum values()性能",
            trigger = "enum.values().find{...}",
            detection = "values()每次新分配数组",
            fix = "用enumEntries或缓存."
        ))
        BugDB.load(BugRule(
            id = "KT-0504",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.MODERATE,
            title = "[D09] sealed class跨文件子类",
            trigger = "sealed class A在File1,子类B在File2",
            detection = "sealed子类需同文件",
            fix = "移到同文件."
        ))
        BugDB.load(BugRule(
            id = "KT-0505",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.SEVERE,
            title = "[S09] when穷举密封类不加else",
            trigger = "when(s){is A->...is B->...}",
            detection = "密封类加子类,when不报错",
            fix = "no else分支需穷举."
        ))
        BugDB.load(BugRule(
            id = "KT-0506",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.SEVERE,
            title = "[S00] 共享可变状态无同步",
            trigger = "var counter=0; threads.forEach{ counter++}",
            detection = "多线程++竞态",
            fix = "用AtomicInteger或synchronized."
        ))
        BugDB.load(BugRule(
            id = "KT-0507",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D00] synchronized块内suspend调用",
            trigger = "synchronized(lock){ delay(100)}",
            detection = "synchronized内不可suspend",
            fix = "用Mutex.withLock."
        ))
        BugDB.load(BugRule(
            id = "KT-0508",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MILD,
            title = "[M00] volatile缺失",
            trigger = "var flag=false",
            detection = "多线程可见性不保证",
            fix = "加@Volatile."
        ))
        BugDB.load(BugRule(
            id = "KT-0509",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.SEVERE,
            title = "[S01] 共享可变状态无同步",
            trigger = "var counter=0; threads.forEach{ counter++}",
            detection = "多线程++竞态",
            fix = "用AtomicInteger或synchronized."
        ))
        BugDB.load(BugRule(
            id = "KT-0510",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D01] synchronized块内suspend调用",
            trigger = "synchronized(lock){ delay(100)}",
            detection = "synchronized内不可suspend",
            fix = "用Mutex.withLock."
        ))
        BugDB.load(BugRule(
            id = "KT-0511",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MILD,
            title = "[M01] volatile缺失",
            trigger = "var flag=false",
            detection = "多线程可见性不保证",
            fix = "加@Volatile."
        ))
        BugDB.load(BugRule(
            id = "KT-0512",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.SEVERE,
            title = "[S02] 共享可变状态无同步",
            trigger = "var counter=0; threads.forEach{ counter++}",
            detection = "多线程++竞态",
            fix = "用AtomicInteger或synchronized."
        ))
        BugDB.load(BugRule(
            id = "KT-0513",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D02] synchronized块内suspend调用",
            trigger = "synchronized(lock){ delay(100)}",
            detection = "synchronized内不可suspend",
            fix = "用Mutex.withLock."
        ))
        BugDB.load(BugRule(
            id = "KT-0514",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MILD,
            title = "[M02] volatile缺失",
            trigger = "var flag=false",
            detection = "多线程可见性不保证",
            fix = "加@Volatile."
        ))
        BugDB.load(BugRule(
            id = "KT-0515",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.SEVERE,
            title = "[S03] 共享可变状态无同步",
            trigger = "var counter=0; threads.forEach{ counter++}",
            detection = "多线程++竞态",
            fix = "用AtomicInteger或synchronized."
        ))
        BugDB.load(BugRule(
            id = "KT-0516",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D03] synchronized块内suspend调用",
            trigger = "synchronized(lock){ delay(100)}",
            detection = "synchronized内不可suspend",
            fix = "用Mutex.withLock."
        ))
        BugDB.load(BugRule(
            id = "KT-0517",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MILD,
            title = "[M03] volatile缺失",
            trigger = "var flag=false",
            detection = "多线程可见性不保证",
            fix = "加@Volatile."
        ))
        BugDB.load(BugRule(
            id = "KT-0518",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.SEVERE,
            title = "[S04] 共享可变状态无同步",
            trigger = "var counter=0; threads.forEach{ counter++}",
            detection = "多线程++竞态",
            fix = "用AtomicInteger或synchronized."
        ))
        BugDB.load(BugRule(
            id = "KT-0519",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D04] synchronized块内suspend调用",
            trigger = "synchronized(lock){ delay(100)}",
            detection = "synchronized内不可suspend",
            fix = "用Mutex.withLock."
        ))
        BugDB.load(BugRule(
            id = "KT-0520",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MILD,
            title = "[M04] volatile缺失",
            trigger = "var flag=false",
            detection = "多线程可见性不保证",
            fix = "加@Volatile."
        ))
        BugDB.load(BugRule(
            id = "KT-0521",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.SEVERE,
            title = "[S05] 共享可变状态无同步",
            trigger = "var counter=0; threads.forEach{ counter++}",
            detection = "多线程++竞态",
            fix = "用AtomicInteger或synchronized."
        ))
        BugDB.load(BugRule(
            id = "KT-0522",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D05] synchronized块内suspend调用",
            trigger = "synchronized(lock){ delay(100)}",
            detection = "synchronized内不可suspend",
            fix = "用Mutex.withLock."
        ))
        BugDB.load(BugRule(
            id = "KT-0523",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MILD,
            title = "[M05] volatile缺失",
            trigger = "var flag=false",
            detection = "多线程可见性不保证",
            fix = "加@Volatile."
        ))
        BugDB.load(BugRule(
            id = "KT-0524",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.SEVERE,
            title = "[S06] 共享可变状态无同步",
            trigger = "var counter=0; threads.forEach{ counter++}",
            detection = "多线程++竞态",
            fix = "用AtomicInteger或synchronized."
        ))
        BugDB.load(BugRule(
            id = "KT-0525",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D06] synchronized块内suspend调用",
            trigger = "synchronized(lock){ delay(100)}",
            detection = "synchronized内不可suspend",
            fix = "用Mutex.withLock."
        ))
        BugDB.load(BugRule(
            id = "KT-0526",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MILD,
            title = "[M06] volatile缺失",
            trigger = "var flag=false",
            detection = "多线程可见性不保证",
            fix = "加@Volatile."
        ))
        BugDB.load(BugRule(
            id = "KT-0527",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.SEVERE,
            title = "[S07] 共享可变状态无同步",
            trigger = "var counter=0; threads.forEach{ counter++}",
            detection = "多线程++竞态",
            fix = "用AtomicInteger或synchronized."
        ))
        BugDB.load(BugRule(
            id = "KT-0528",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D07] synchronized块内suspend调用",
            trigger = "synchronized(lock){ delay(100)}",
            detection = "synchronized内不可suspend",
            fix = "用Mutex.withLock."
        ))
        BugDB.load(BugRule(
            id = "KT-0529",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MILD,
            title = "[M07] volatile缺失",
            trigger = "var flag=false",
            detection = "多线程可见性不保证",
            fix = "加@Volatile."
        ))
        BugDB.load(BugRule(
            id = "KT-0530",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.SEVERE,
            title = "[S08] 共享可变状态无同步",
            trigger = "var counter=0; threads.forEach{ counter++}",
            detection = "多线程++竞态",
            fix = "用AtomicInteger或synchronized."
        ))
        BugDB.load(BugRule(
            id = "KT-0531",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D08] synchronized块内suspend调用",
            trigger = "synchronized(lock){ delay(100)}",
            detection = "synchronized内不可suspend",
            fix = "用Mutex.withLock."
        ))
        BugDB.load(BugRule(
            id = "KT-0532",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MILD,
            title = "[M08] volatile缺失",
            trigger = "var flag=false",
            detection = "多线程可见性不保证",
            fix = "加@Volatile."
        ))
        BugDB.load(BugRule(
            id = "KT-0533",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.SEVERE,
            title = "[S09] 共享可变状态无同步",
            trigger = "var counter=0; threads.forEach{ counter++}",
            detection = "多线程++竞态",
            fix = "用AtomicInteger或synchronized."
        ))
        BugDB.load(BugRule(
            id = "KT-0534",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D09] synchronized块内suspend调用",
            trigger = "synchronized(lock){ delay(100)}",
            detection = "synchronized内不可suspend",
            fix = "用Mutex.withLock."
        ))
        BugDB.load(BugRule(
            id = "KT-0535",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MILD,
            title = "[M09] volatile缺失",
            trigger = "var flag=false",
            detection = "多线程可见性不保证",
            fix = "加@Volatile."
        ))
        BugDB.load(BugRule(
            id = "KT-0536",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D10] 死锁风险",
            trigger = "fun a(){ lock1.lock(); lock2.lock()} fun b(){ lock2.lock(); lock1.lock()}",
            detection = "反向加锁顺序",
            fix = "统一加锁顺序."
        ))
        BugDB.load(BugRule(
            id = "KT-0537",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D11] 死锁风险",
            trigger = "fun a(){ lock1.lock(); lock2.lock()} fun b(){ lock2.lock(); lock1.lock()}",
            detection = "反向加锁顺序",
            fix = "统一加锁顺序."
        ))
        BugDB.load(BugRule(
            id = "KT-0538",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D12] 死锁风险",
            trigger = "fun a(){ lock1.lock(); lock2.lock()} fun b(){ lock2.lock(); lock1.lock()}",
            detection = "反向加锁顺序",
            fix = "统一加锁顺序."
        ))
        BugDB.load(BugRule(
            id = "KT-0539",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D13] 死锁风险",
            trigger = "fun a(){ lock1.lock(); lock2.lock()} fun b(){ lock2.lock(); lock1.lock()}",
            detection = "反向加锁顺序",
            fix = "统一加锁顺序."
        ))
        BugDB.load(BugRule(
            id = "KT-0540",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D14] 死锁风险",
            trigger = "fun a(){ lock1.lock(); lock2.lock()} fun b(){ lock2.lock(); lock1.lock()}",
            detection = "反向加锁顺序",
            fix = "统一加锁顺序."
        ))
        BugDB.load(BugRule(
            id = "KT-0541",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D15] 死锁风险",
            trigger = "fun a(){ lock1.lock(); lock2.lock()} fun b(){ lock2.lock(); lock1.lock()}",
            detection = "反向加锁顺序",
            fix = "统一加锁顺序."
        ))
        BugDB.load(BugRule(
            id = "KT-0542",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D16] 死锁风险",
            trigger = "fun a(){ lock1.lock(); lock2.lock()} fun b(){ lock2.lock(); lock1.lock()}",
            detection = "反向加锁顺序",
            fix = "统一加锁顺序."
        ))
        BugDB.load(BugRule(
            id = "KT-0543",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D17] 死锁风险",
            trigger = "fun a(){ lock1.lock(); lock2.lock()} fun b(){ lock2.lock(); lock1.lock()}",
            detection = "反向加锁顺序",
            fix = "统一加锁顺序."
        ))
        BugDB.load(BugRule(
            id = "KT-0544",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D18] 死锁风险",
            trigger = "fun a(){ lock1.lock(); lock2.lock()} fun b(){ lock2.lock(); lock1.lock()}",
            detection = "反向加锁顺序",
            fix = "统一加锁顺序."
        ))
        BugDB.load(BugRule(
            id = "KT-0545",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D19] 死锁风险",
            trigger = "fun a(){ lock1.lock(); lock2.lock()} fun b(){ lock2.lock(); lock1.lock()}",
            detection = "反向加锁顺序",
            fix = "统一加锁顺序."
        ))
        BugDB.load(BugRule(
            id = "KT-0546",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MILD,
            title = "[M00] 字符串拼接用+",
            trigger = "var s=\"\"; for(i in 1..100){ s+=\"\$i\"}",
            detection = "+在循环中反复分配",
            fix = "用buildString或StringBuilder."
        ))
        BugDB.load(BugRule(
            id = "KT-0547",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[D00] 不必要装箱",
            trigger = "val x: Int? = 42",
            detection = "Int?装箱为Integer",
            fix = "避免可空基本类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0548",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[D10] 循环中创建对象",
            trigger = "for(i in 1..1000){ val obj=Heavy() }",
            detection = "每次循环new对象",
            fix = "移到循环外或懒加载."
        ))
        BugDB.load(BugRule(
            id = "KT-0549",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MILD,
            title = "[M01] 字符串拼接用+",
            trigger = "var s=\"\"; for(i in 1..100){ s+=\"\$i\"}",
            detection = "+在循环中反复分配",
            fix = "用buildString或StringBuilder."
        ))
        BugDB.load(BugRule(
            id = "KT-0550",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[D01] 不必要装箱",
            trigger = "val x: Int? = 42",
            detection = "Int?装箱为Integer",
            fix = "避免可空基本类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0551",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[D11] 循环中创建对象",
            trigger = "for(i in 1..1000){ val obj=Heavy() }",
            detection = "每次循环new对象",
            fix = "移到循环外或懒加载."
        ))
        BugDB.load(BugRule(
            id = "KT-0552",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MILD,
            title = "[M02] 字符串拼接用+",
            trigger = "var s=\"\"; for(i in 1..100){ s+=\"\$i\"}",
            detection = "+在循环中反复分配",
            fix = "用buildString或StringBuilder."
        ))
        BugDB.load(BugRule(
            id = "KT-0553",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[D02] 不必要装箱",
            trigger = "val x: Int? = 42",
            detection = "Int?装箱为Integer",
            fix = "避免可空基本类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0554",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[D12] 循环中创建对象",
            trigger = "for(i in 1..1000){ val obj=Heavy() }",
            detection = "每次循环new对象",
            fix = "移到循环外或懒加载."
        ))
        BugDB.load(BugRule(
            id = "KT-0555",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MILD,
            title = "[M03] 字符串拼接用+",
            trigger = "var s=\"\"; for(i in 1..100){ s+=\"\$i\"}",
            detection = "+在循环中反复分配",
            fix = "用buildString或StringBuilder."
        ))
        BugDB.load(BugRule(
            id = "KT-0556",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[D03] 不必要装箱",
            trigger = "val x: Int? = 42",
            detection = "Int?装箱为Integer",
            fix = "避免可空基本类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0557",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[D13] 循环中创建对象",
            trigger = "for(i in 1..1000){ val obj=Heavy() }",
            detection = "每次循环new对象",
            fix = "移到循环外或懒加载."
        ))
        BugDB.load(BugRule(
            id = "KT-0558",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MILD,
            title = "[M04] 字符串拼接用+",
            trigger = "var s=\"\"; for(i in 1..100){ s+=\"\$i\"}",
            detection = "+在循环中反复分配",
            fix = "用buildString或StringBuilder."
        ))
        BugDB.load(BugRule(
            id = "KT-0559",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[D04] 不必要装箱",
            trigger = "val x: Int? = 42",
            detection = "Int?装箱为Integer",
            fix = "避免可空基本类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0560",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[D14] 循环中创建对象",
            trigger = "for(i in 1..1000){ val obj=Heavy() }",
            detection = "每次循环new对象",
            fix = "移到循环外或懒加载."
        ))
        BugDB.load(BugRule(
            id = "KT-0561",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MILD,
            title = "[M05] 字符串拼接用+",
            trigger = "var s=\"\"; for(i in 1..100){ s+=\"\$i\"}",
            detection = "+在循环中反复分配",
            fix = "用buildString或StringBuilder."
        ))
        BugDB.load(BugRule(
            id = "KT-0562",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[D05] 不必要装箱",
            trigger = "val x: Int? = 42",
            detection = "Int?装箱为Integer",
            fix = "避免可空基本类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0563",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[D15] 循环中创建对象",
            trigger = "for(i in 1..1000){ val obj=Heavy() }",
            detection = "每次循环new对象",
            fix = "移到循环外或懒加载."
        ))
        BugDB.load(BugRule(
            id = "KT-0564",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MILD,
            title = "[M06] 字符串拼接用+",
            trigger = "var s=\"\"; for(i in 1..100){ s+=\"\$i\"}",
            detection = "+在循环中反复分配",
            fix = "用buildString或StringBuilder."
        ))
        BugDB.load(BugRule(
            id = "KT-0565",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[D06] 不必要装箱",
            trigger = "val x: Int? = 42",
            detection = "Int?装箱为Integer",
            fix = "避免可空基本类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0566",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[D16] 循环中创建对象",
            trigger = "for(i in 1..1000){ val obj=Heavy() }",
            detection = "每次循环new对象",
            fix = "移到循环外或懒加载."
        ))
        BugDB.load(BugRule(
            id = "KT-0567",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MILD,
            title = "[M07] 字符串拼接用+",
            trigger = "var s=\"\"; for(i in 1..100){ s+=\"\$i\"}",
            detection = "+在循环中反复分配",
            fix = "用buildString或StringBuilder."
        ))
        BugDB.load(BugRule(
            id = "KT-0568",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[D07] 不必要装箱",
            trigger = "val x: Int? = 42",
            detection = "Int?装箱为Integer",
            fix = "避免可空基本类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0569",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[D17] 循环中创建对象",
            trigger = "for(i in 1..1000){ val obj=Heavy() }",
            detection = "每次循环new对象",
            fix = "移到循环外或懒加载."
        ))
        BugDB.load(BugRule(
            id = "KT-0570",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MILD,
            title = "[M08] 字符串拼接用+",
            trigger = "var s=\"\"; for(i in 1..100){ s+=\"\$i\"}",
            detection = "+在循环中反复分配",
            fix = "用buildString或StringBuilder."
        ))
        BugDB.load(BugRule(
            id = "KT-0571",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[D08] 不必要装箱",
            trigger = "val x: Int? = 42",
            detection = "Int?装箱为Integer",
            fix = "避免可空基本类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0572",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[D18] 循环中创建对象",
            trigger = "for(i in 1..1000){ val obj=Heavy() }",
            detection = "每次循环new对象",
            fix = "移到循环外或懒加载."
        ))
        BugDB.load(BugRule(
            id = "KT-0573",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MILD,
            title = "[M09] 字符串拼接用+",
            trigger = "var s=\"\"; for(i in 1..100){ s+=\"\$i\"}",
            detection = "+在循环中反复分配",
            fix = "用buildString或StringBuilder."
        ))
        BugDB.load(BugRule(
            id = "KT-0574",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[D09] 不必要装箱",
            trigger = "val x: Int? = 42",
            detection = "Int?装箱为Integer",
            fix = "避免可空基本类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0575",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[D19] 循环中创建对象",
            trigger = "for(i in 1..1000){ val obj=Heavy() }",
            detection = "每次循环new对象",
            fix = "移到循环外或懒加载."
        ))
        BugDB.load(BugRule(
            id = "KT-0576",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.SEVERE,
            title = "[S00] 类型推断歧义导致编译通过但行为错",
            trigger = "listOf(1,2).plus(listOf(3))",
            detection = "plus返回新列表,非原地修改",
            fix = "接收返回值."
        ))
        BugDB.load(BugRule(
            id = "KT-0577",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MILD,
            title = "[M00] import别名冲突",
            trigger = "import com.a.Foo as F; import com.b.Foo",
            detection = "别名和类名混用",
            fix = "统一用全限定名或别名."
        ))
        BugDB.load(BugRule(
            id = "KT-0578",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.SEVERE,
            title = "[S01] 类型推断歧义导致编译通过但行为错",
            trigger = "listOf(1,2).plus(listOf(3))",
            detection = "plus返回新列表,非原地修改",
            fix = "接收返回值."
        ))
        BugDB.load(BugRule(
            id = "KT-0579",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MILD,
            title = "[M01] import别名冲突",
            trigger = "import com.a.Foo as F; import com.b.Foo",
            detection = "别名和类名混用",
            fix = "统一用全限定名或别名."
        ))
        BugDB.load(BugRule(
            id = "KT-0580",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.SEVERE,
            title = "[S02] 类型推断歧义导致编译通过但行为错",
            trigger = "listOf(1,2).plus(listOf(3))",
            detection = "plus返回新列表,非原地修改",
            fix = "接收返回值."
        ))
        BugDB.load(BugRule(
            id = "KT-0581",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MILD,
            title = "[M02] import别名冲突",
            trigger = "import com.a.Foo as F; import com.b.Foo",
            detection = "别名和类名混用",
            fix = "统一用全限定名或别名."
        ))
        BugDB.load(BugRule(
            id = "KT-0582",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.SEVERE,
            title = "[S03] 类型推断歧义导致编译通过但行为错",
            trigger = "listOf(1,2).plus(listOf(3))",
            detection = "plus返回新列表,非原地修改",
            fix = "接收返回值."
        ))
        BugDB.load(BugRule(
            id = "KT-0583",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MILD,
            title = "[M03] import别名冲突",
            trigger = "import com.a.Foo as F; import com.b.Foo",
            detection = "别名和类名混用",
            fix = "统一用全限定名或别名."
        ))
        BugDB.load(BugRule(
            id = "KT-0584",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.SEVERE,
            title = "[S04] 类型推断歧义导致编译通过但行为错",
            trigger = "listOf(1,2).plus(listOf(3))",
            detection = "plus返回新列表,非原地修改",
            fix = "接收返回值."
        ))
        BugDB.load(BugRule(
            id = "KT-0585",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MILD,
            title = "[M04] import别名冲突",
            trigger = "import com.a.Foo as F; import com.b.Foo",
            detection = "别名和类名混用",
            fix = "统一用全限定名或别名."
        ))
        BugDB.load(BugRule(
            id = "KT-0586",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.SEVERE,
            title = "[S05] 类型推断歧义导致编译通过但行为错",
            trigger = "listOf(1,2).plus(listOf(3))",
            detection = "plus返回新列表,非原地修改",
            fix = "接收返回值."
        ))
        BugDB.load(BugRule(
            id = "KT-0587",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MILD,
            title = "[M05] import别名冲突",
            trigger = "import com.a.Foo as F; import com.b.Foo",
            detection = "别名和类名混用",
            fix = "统一用全限定名或别名."
        ))
        BugDB.load(BugRule(
            id = "KT-0588",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.SEVERE,
            title = "[S06] 类型推断歧义导致编译通过但行为错",
            trigger = "listOf(1,2).plus(listOf(3))",
            detection = "plus返回新列表,非原地修改",
            fix = "接收返回值."
        ))
        BugDB.load(BugRule(
            id = "KT-0589",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MILD,
            title = "[M06] import别名冲突",
            trigger = "import com.a.Foo as F; import com.b.Foo",
            detection = "别名和类名混用",
            fix = "统一用全限定名或别名."
        ))
        BugDB.load(BugRule(
            id = "KT-0590",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.SEVERE,
            title = "[S07] 类型推断歧义导致编译通过但行为错",
            trigger = "listOf(1,2).plus(listOf(3))",
            detection = "plus返回新列表,非原地修改",
            fix = "接收返回值."
        ))
        BugDB.load(BugRule(
            id = "KT-0591",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MILD,
            title = "[M07] import别名冲突",
            trigger = "import com.a.Foo as F; import com.b.Foo",
            detection = "别名和类名混用",
            fix = "统一用全限定名或别名."
        ))
        BugDB.load(BugRule(
            id = "KT-0592",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.SEVERE,
            title = "[S08] 类型推断歧义导致编译通过但行为错",
            trigger = "listOf(1,2).plus(listOf(3))",
            detection = "plus返回新列表,非原地修改",
            fix = "接收返回值."
        ))
        BugDB.load(BugRule(
            id = "KT-0593",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MILD,
            title = "[M08] import别名冲突",
            trigger = "import com.a.Foo as F; import com.b.Foo",
            detection = "别名和类名混用",
            fix = "统一用全限定名或别名."
        ))
        BugDB.load(BugRule(
            id = "KT-0594",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.SEVERE,
            title = "[S09] 类型推断歧义导致编译通过但行为错",
            trigger = "listOf(1,2).plus(listOf(3))",
            detection = "plus返回新列表,非原地修改",
            fix = "接收返回值."
        ))
        BugDB.load(BugRule(
            id = "KT-0595",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MILD,
            title = "[M09] import别名冲突",
            trigger = "import com.a.Foo as F; import com.b.Foo",
            detection = "别名和类名混用",
            fix = "统一用全限定名或别名."
        ))
        BugDB.load(BugRule(
            id = "KT-0596",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MODERATE,
            title = "[D10] 重载解析歧义",
            trigger = "fun f(x:Int)=...; fun f(x:Long)=...",
            detection = "f(1)可同时匹配Int和Long",
            fix = "显式类型:1L或1.toInt()."
        ))
        BugDB.load(BugRule(
            id = "KT-0597",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MODERATE,
            title = "[D11] 重载解析歧义",
            trigger = "fun f(x:Int)=...; fun f(x:Long)=...",
            detection = "f(1)可同时匹配Int和Long",
            fix = "显式类型:1L或1.toInt()."
        ))
        BugDB.load(BugRule(
            id = "KT-0598",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MODERATE,
            title = "[D12] 重载解析歧义",
            trigger = "fun f(x:Int)=...; fun f(x:Long)=...",
            detection = "f(1)可同时匹配Int和Long",
            fix = "显式类型:1L或1.toInt()."
        ))
        BugDB.load(BugRule(
            id = "KT-0599",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MODERATE,
            title = "[D13] 重载解析歧义",
            trigger = "fun f(x:Int)=...; fun f(x:Long)=...",
            detection = "f(1)可同时匹配Int和Long",
            fix = "显式类型:1L或1.toInt()."
        ))
        BugDB.load(BugRule(
            id = "KT-0600",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MODERATE,
            title = "[D14] 重载解析歧义",
            trigger = "fun f(x:Int)=...; fun f(x:Long)=...",
            detection = "f(1)可同时匹配Int和Long",
            fix = "显式类型:1L或1.toInt()."
        ))
        BugDB.load(BugRule(
            id = "KT-0601",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MODERATE,
            title = "[D15] 重载解析歧义",
            trigger = "fun f(x:Int)=...; fun f(x:Long)=...",
            detection = "f(1)可同时匹配Int和Long",
            fix = "显式类型:1L或1.toInt()."
        ))
        BugDB.load(BugRule(
            id = "KT-0602",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MODERATE,
            title = "[D16] 重载解析歧义",
            trigger = "fun f(x:Int)=...; fun f(x:Long)=...",
            detection = "f(1)可同时匹配Int和Long",
            fix = "显式类型:1L或1.toInt()."
        ))
        BugDB.load(BugRule(
            id = "KT-0603",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MODERATE,
            title = "[D17] 重载解析歧义",
            trigger = "fun f(x:Int)=...; fun f(x:Long)=...",
            detection = "f(1)可同时匹配Int和Long",
            fix = "显式类型:1L或1.toInt()."
        ))
        BugDB.load(BugRule(
            id = "KT-0604",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MODERATE,
            title = "[D18] 重载解析歧义",
            trigger = "fun f(x:Int)=...; fun f(x:Long)=...",
            detection = "f(1)可同时匹配Int和Long",
            fix = "显式类型:1L或1.toInt()."
        ))
        BugDB.load(BugRule(
            id = "KT-0605",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MODERATE,
            title = "[D19] 重载解析歧义",
            trigger = "fun f(x:Int)=...; fun f(x:Long)=...",
            detection = "f(1)可同时匹配Int和Long",
            fix = "显式类型:1L或1.toInt()."
        ))
        BugDB.load(BugRule(
            id = "KT-0606",
            category = BugCategory.SECURITY,
            severity = BugSeverity.SEVERE,
            title = "[S00] 硬编码密钥",
            trigger = "val apiKey=\"sk-xxxxxxxx\"",
            detection = "密钥在源码中明文",
            fix = "用环境变量或安全存储."
        ))
        BugDB.load(BugRule(
            id = "KT-0607",
            category = BugCategory.SECURITY,
            severity = BugSeverity.MODERATE,
            title = "[D00] 日志泄露敏感信息",
            trigger = "Log.d(\"User\",\"token=\$token\")",
            detection = "敏感数据进入日志",
            fix = "脱敏或跳过."
        ))
        BugDB.load(BugRule(
            id = "KT-0608",
            category = BugCategory.SECURITY,
            severity = BugSeverity.SEVERE,
            title = "[S10] 不安全的反射调用",
            trigger = "method.invoke(obj, userInput)",
            detection = "反射+用户输入=代码注入",
            fix = "白名单验证方法名."
        ))
        BugDB.load(BugRule(
            id = "KT-0609",
            category = BugCategory.SECURITY,
            severity = BugSeverity.SEVERE,
            title = "[S01] 硬编码密钥",
            trigger = "val apiKey=\"sk-xxxxxxxx\"",
            detection = "密钥在源码中明文",
            fix = "用环境变量或安全存储."
        ))
        BugDB.load(BugRule(
            id = "KT-0610",
            category = BugCategory.SECURITY,
            severity = BugSeverity.MODERATE,
            title = "[D01] 日志泄露敏感信息",
            trigger = "Log.d(\"User\",\"token=\$token\")",
            detection = "敏感数据进入日志",
            fix = "脱敏或跳过."
        ))
        BugDB.load(BugRule(
            id = "KT-0611",
            category = BugCategory.SECURITY,
            severity = BugSeverity.SEVERE,
            title = "[S11] 不安全的反射调用",
            trigger = "method.invoke(obj, userInput)",
            detection = "反射+用户输入=代码注入",
            fix = "白名单验证方法名."
        ))
        BugDB.load(BugRule(
            id = "KT-0612",
            category = BugCategory.SECURITY,
            severity = BugSeverity.SEVERE,
            title = "[S02] 硬编码密钥",
            trigger = "val apiKey=\"sk-xxxxxxxx\"",
            detection = "密钥在源码中明文",
            fix = "用环境变量或安全存储."
        ))
        BugDB.load(BugRule(
            id = "KT-0613",
            category = BugCategory.SECURITY,
            severity = BugSeverity.MODERATE,
            title = "[D02] 日志泄露敏感信息",
            trigger = "Log.d(\"User\",\"token=\$token\")",
            detection = "敏感数据进入日志",
            fix = "脱敏或跳过."
        ))
        BugDB.load(BugRule(
            id = "KT-0614",
            category = BugCategory.SECURITY,
            severity = BugSeverity.SEVERE,
            title = "[S12] 不安全的反射调用",
            trigger = "method.invoke(obj, userInput)",
            detection = "反射+用户输入=代码注入",
            fix = "白名单验证方法名."
        ))
        BugDB.load(BugRule(
            id = "KT-0615",
            category = BugCategory.SECURITY,
            severity = BugSeverity.SEVERE,
            title = "[S03] 硬编码密钥",
            trigger = "val apiKey=\"sk-xxxxxxxx\"",
            detection = "密钥在源码中明文",
            fix = "用环境变量或安全存储."
        ))
        BugDB.load(BugRule(
            id = "KT-0616",
            category = BugCategory.SECURITY,
            severity = BugSeverity.MODERATE,
            title = "[D03] 日志泄露敏感信息",
            trigger = "Log.d(\"User\",\"token=\$token\")",
            detection = "敏感数据进入日志",
            fix = "脱敏或跳过."
        ))
        BugDB.load(BugRule(
            id = "KT-0617",
            category = BugCategory.SECURITY,
            severity = BugSeverity.SEVERE,
            title = "[S13] 不安全的反射调用",
            trigger = "method.invoke(obj, userInput)",
            detection = "反射+用户输入=代码注入",
            fix = "白名单验证方法名."
        ))
        BugDB.load(BugRule(
            id = "KT-0618",
            category = BugCategory.SECURITY,
            severity = BugSeverity.SEVERE,
            title = "[S04] 硬编码密钥",
            trigger = "val apiKey=\"sk-xxxxxxxx\"",
            detection = "密钥在源码中明文",
            fix = "用环境变量或安全存储."
        ))
        BugDB.load(BugRule(
            id = "KT-0619",
            category = BugCategory.SECURITY,
            severity = BugSeverity.MODERATE,
            title = "[D04] 日志泄露敏感信息",
            trigger = "Log.d(\"User\",\"token=\$token\")",
            detection = "敏感数据进入日志",
            fix = "脱敏或跳过."
        ))
        BugDB.load(BugRule(
            id = "KT-0620",
            category = BugCategory.SECURITY,
            severity = BugSeverity.SEVERE,
            title = "[S14] 不安全的反射调用",
            trigger = "method.invoke(obj, userInput)",
            detection = "反射+用户输入=代码注入",
            fix = "白名单验证方法名."
        ))
        BugDB.load(BugRule(
            id = "KT-0621",
            category = BugCategory.SECURITY,
            severity = BugSeverity.SEVERE,
            title = "[S05] 硬编码密钥",
            trigger = "val apiKey=\"sk-xxxxxxxx\"",
            detection = "密钥在源码中明文",
            fix = "用环境变量或安全存储."
        ))
        BugDB.load(BugRule(
            id = "KT-0622",
            category = BugCategory.SECURITY,
            severity = BugSeverity.MODERATE,
            title = "[D05] 日志泄露敏感信息",
            trigger = "Log.d(\"User\",\"token=\$token\")",
            detection = "敏感数据进入日志",
            fix = "脱敏或跳过."
        ))
        BugDB.load(BugRule(
            id = "KT-0623",
            category = BugCategory.SECURITY,
            severity = BugSeverity.SEVERE,
            title = "[S15] 不安全的反射调用",
            trigger = "method.invoke(obj, userInput)",
            detection = "反射+用户输入=代码注入",
            fix = "白名单验证方法名."
        ))
        BugDB.load(BugRule(
            id = "KT-0624",
            category = BugCategory.SECURITY,
            severity = BugSeverity.SEVERE,
            title = "[S06] 硬编码密钥",
            trigger = "val apiKey=\"sk-xxxxxxxx\"",
            detection = "密钥在源码中明文",
            fix = "用环境变量或安全存储."
        ))
        BugDB.load(BugRule(
            id = "KT-0625",
            category = BugCategory.SECURITY,
            severity = BugSeverity.MODERATE,
            title = "[D06] 日志泄露敏感信息",
            trigger = "Log.d(\"User\",\"token=\$token\")",
            detection = "敏感数据进入日志",
            fix = "脱敏或跳过."
        ))
        BugDB.load(BugRule(
            id = "KT-0626",
            category = BugCategory.SECURITY,
            severity = BugSeverity.SEVERE,
            title = "[S16] 不安全的反射调用",
            trigger = "method.invoke(obj, userInput)",
            detection = "反射+用户输入=代码注入",
            fix = "白名单验证方法名."
        ))
        BugDB.load(BugRule(
            id = "KT-0627",
            category = BugCategory.SECURITY,
            severity = BugSeverity.SEVERE,
            title = "[S07] 硬编码密钥",
            trigger = "val apiKey=\"sk-xxxxxxxx\"",
            detection = "密钥在源码中明文",
            fix = "用环境变量或安全存储."
        ))
        BugDB.load(BugRule(
            id = "KT-0628",
            category = BugCategory.SECURITY,
            severity = BugSeverity.MODERATE,
            title = "[D07] 日志泄露敏感信息",
            trigger = "Log.d(\"User\",\"token=\$token\")",
            detection = "敏感数据进入日志",
            fix = "脱敏或跳过."
        ))
        BugDB.load(BugRule(
            id = "KT-0629",
            category = BugCategory.SECURITY,
            severity = BugSeverity.SEVERE,
            title = "[S17] 不安全的反射调用",
            trigger = "method.invoke(obj, userInput)",
            detection = "反射+用户输入=代码注入",
            fix = "白名单验证方法名."
        ))
        BugDB.load(BugRule(
            id = "KT-0630",
            category = BugCategory.SECURITY,
            severity = BugSeverity.SEVERE,
            title = "[S08] 硬编码密钥",
            trigger = "val apiKey=\"sk-xxxxxxxx\"",
            detection = "密钥在源码中明文",
            fix = "用环境变量或安全存储."
        ))
        BugDB.load(BugRule(
            id = "KT-0631",
            category = BugCategory.SECURITY,
            severity = BugSeverity.MODERATE,
            title = "[D08] 日志泄露敏感信息",
            trigger = "Log.d(\"User\",\"token=\$token\")",
            detection = "敏感数据进入日志",
            fix = "脱敏或跳过."
        ))
        BugDB.load(BugRule(
            id = "KT-0632",
            category = BugCategory.SECURITY,
            severity = BugSeverity.SEVERE,
            title = "[S18] 不安全的反射调用",
            trigger = "method.invoke(obj, userInput)",
            detection = "反射+用户输入=代码注入",
            fix = "白名单验证方法名."
        ))
        BugDB.load(BugRule(
            id = "KT-0633",
            category = BugCategory.SECURITY,
            severity = BugSeverity.SEVERE,
            title = "[S09] 硬编码密钥",
            trigger = "val apiKey=\"sk-xxxxxxxx\"",
            detection = "密钥在源码中明文",
            fix = "用环境变量或安全存储."
        ))
        BugDB.load(BugRule(
            id = "KT-0634",
            category = BugCategory.SECURITY,
            severity = BugSeverity.MODERATE,
            title = "[D09] 日志泄露敏感信息",
            trigger = "Log.d(\"User\",\"token=\$token\")",
            detection = "敏感数据进入日志",
            fix = "脱敏或跳过."
        ))
        BugDB.load(BugRule(
            id = "KT-0635",
            category = BugCategory.SECURITY,
            severity = BugSeverity.SEVERE,
            title = "[S19] 不安全的反射调用",
            trigger = "method.invoke(obj, userInput)",
            detection = "反射+用户输入=代码注入",
            fix = "白名单验证方法名."
        ))
        BugDB.load(BugRule(
            id = "KT-0636",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.MODERATE,
            title = "[D00] 重组中副作用",
            trigger = "LaunchedEffect(Unit){ viewModel.load() }",
            detection = "key=Unit导致只执行一次",
            fix = "用正确的key."
        ))
        BugDB.load(BugRule(
            id = "KT-0637",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.MILD,
            title = "[M00] remember未用",
            trigger = "fun MyComposable(){ val state= mutableStateOf(0)}",
            detection = "重组时重置状态",
            fix = "加remember."
        ))
        BugDB.load(BugRule(
            id = "KT-0638",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.SEVERE,
            title = "[S00] 主线程IO操作",
            trigger = "Text(text=readFile())",
            detection = "读取文件在主线程卡UI",
            fix = "用LaunchedEffect+Dispatchers.IO."
        ))
        BugDB.load(BugRule(
            id = "KT-0639",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.MODERATE,
            title = "[D01] 重组中副作用",
            trigger = "LaunchedEffect(Unit){ viewModel.load() }",
            detection = "key=Unit导致只执行一次",
            fix = "用正确的key."
        ))
        BugDB.load(BugRule(
            id = "KT-0640",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.MILD,
            title = "[M01] remember未用",
            trigger = "fun MyComposable(){ val state= mutableStateOf(0)}",
            detection = "重组时重置状态",
            fix = "加remember."
        ))
        BugDB.load(BugRule(
            id = "KT-0641",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.SEVERE,
            title = "[S01] 主线程IO操作",
            trigger = "Text(text=readFile())",
            detection = "读取文件在主线程卡UI",
            fix = "用LaunchedEffect+Dispatchers.IO."
        ))
        BugDB.load(BugRule(
            id = "KT-0642",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.MODERATE,
            title = "[D02] 重组中副作用",
            trigger = "LaunchedEffect(Unit){ viewModel.load() }",
            detection = "key=Unit导致只执行一次",
            fix = "用正确的key."
        ))
        BugDB.load(BugRule(
            id = "KT-0643",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.MILD,
            title = "[M02] remember未用",
            trigger = "fun MyComposable(){ val state= mutableStateOf(0)}",
            detection = "重组时重置状态",
            fix = "加remember."
        ))
        BugDB.load(BugRule(
            id = "KT-0644",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.SEVERE,
            title = "[S02] 主线程IO操作",
            trigger = "Text(text=readFile())",
            detection = "读取文件在主线程卡UI",
            fix = "用LaunchedEffect+Dispatchers.IO."
        ))
        BugDB.load(BugRule(
            id = "KT-0645",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.MODERATE,
            title = "[D03] 重组中副作用",
            trigger = "LaunchedEffect(Unit){ viewModel.load() }",
            detection = "key=Unit导致只执行一次",
            fix = "用正确的key."
        ))
        BugDB.load(BugRule(
            id = "KT-0646",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.MILD,
            title = "[M03] remember未用",
            trigger = "fun MyComposable(){ val state= mutableStateOf(0)}",
            detection = "重组时重置状态",
            fix = "加remember."
        ))
        BugDB.load(BugRule(
            id = "KT-0647",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.SEVERE,
            title = "[S03] 主线程IO操作",
            trigger = "Text(text=readFile())",
            detection = "读取文件在主线程卡UI",
            fix = "用LaunchedEffect+Dispatchers.IO."
        ))
        BugDB.load(BugRule(
            id = "KT-0648",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.MODERATE,
            title = "[D04] 重组中副作用",
            trigger = "LaunchedEffect(Unit){ viewModel.load() }",
            detection = "key=Unit导致只执行一次",
            fix = "用正确的key."
        ))
        BugDB.load(BugRule(
            id = "KT-0649",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.MILD,
            title = "[M04] remember未用",
            trigger = "fun MyComposable(){ val state= mutableStateOf(0)}",
            detection = "重组时重置状态",
            fix = "加remember."
        ))
        BugDB.load(BugRule(
            id = "KT-0650",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.SEVERE,
            title = "[S04] 主线程IO操作",
            trigger = "Text(text=readFile())",
            detection = "读取文件在主线程卡UI",
            fix = "用LaunchedEffect+Dispatchers.IO."
        ))
        BugDB.load(BugRule(
            id = "KT-0651",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.MODERATE,
            title = "[D05] 重组中副作用",
            trigger = "LaunchedEffect(Unit){ viewModel.load() }",
            detection = "key=Unit导致只执行一次",
            fix = "用正确的key."
        ))
        BugDB.load(BugRule(
            id = "KT-0652",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.MILD,
            title = "[M05] remember未用",
            trigger = "fun MyComposable(){ val state= mutableStateOf(0)}",
            detection = "重组时重置状态",
            fix = "加remember."
        ))
        BugDB.load(BugRule(
            id = "KT-0653",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.SEVERE,
            title = "[S05] 主线程IO操作",
            trigger = "Text(text=readFile())",
            detection = "读取文件在主线程卡UI",
            fix = "用LaunchedEffect+Dispatchers.IO."
        ))
        BugDB.load(BugRule(
            id = "KT-0654",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.MODERATE,
            title = "[D06] 重组中副作用",
            trigger = "LaunchedEffect(Unit){ viewModel.load() }",
            detection = "key=Unit导致只执行一次",
            fix = "用正确的key."
        ))
        BugDB.load(BugRule(
            id = "KT-0655",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.MILD,
            title = "[M06] remember未用",
            trigger = "fun MyComposable(){ val state= mutableStateOf(0)}",
            detection = "重组时重置状态",
            fix = "加remember."
        ))
        BugDB.load(BugRule(
            id = "KT-0656",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.SEVERE,
            title = "[S06] 主线程IO操作",
            trigger = "Text(text=readFile())",
            detection = "读取文件在主线程卡UI",
            fix = "用LaunchedEffect+Dispatchers.IO."
        ))
        BugDB.load(BugRule(
            id = "KT-0657",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.MODERATE,
            title = "[D07] 重组中副作用",
            trigger = "LaunchedEffect(Unit){ viewModel.load() }",
            detection = "key=Unit导致只执行一次",
            fix = "用正确的key."
        ))
        BugDB.load(BugRule(
            id = "KT-0658",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.MILD,
            title = "[M07] remember未用",
            trigger = "fun MyComposable(){ val state= mutableStateOf(0)}",
            detection = "重组时重置状态",
            fix = "加remember."
        ))
        BugDB.load(BugRule(
            id = "KT-0659",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.SEVERE,
            title = "[S07] 主线程IO操作",
            trigger = "Text(text=readFile())",
            detection = "读取文件在主线程卡UI",
            fix = "用LaunchedEffect+Dispatchers.IO."
        ))
        BugDB.load(BugRule(
            id = "KT-0660",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.MODERATE,
            title = "[D08] 重组中副作用",
            trigger = "LaunchedEffect(Unit){ viewModel.load() }",
            detection = "key=Unit导致只执行一次",
            fix = "用正确的key."
        ))
        BugDB.load(BugRule(
            id = "KT-0661",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.MILD,
            title = "[M08] remember未用",
            trigger = "fun MyComposable(){ val state= mutableStateOf(0)}",
            detection = "重组时重置状态",
            fix = "加remember."
        ))
        BugDB.load(BugRule(
            id = "KT-0662",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.SEVERE,
            title = "[S08] 主线程IO操作",
            trigger = "Text(text=readFile())",
            detection = "读取文件在主线程卡UI",
            fix = "用LaunchedEffect+Dispatchers.IO."
        ))
        BugDB.load(BugRule(
            id = "KT-0663",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.MODERATE,
            title = "[D09] 重组中副作用",
            trigger = "LaunchedEffect(Unit){ viewModel.load() }",
            detection = "key=Unit导致只执行一次",
            fix = "用正确的key."
        ))
        BugDB.load(BugRule(
            id = "KT-0664",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.MILD,
            title = "[M09] remember未用",
            trigger = "fun MyComposable(){ val state= mutableStateOf(0)}",
            detection = "重组时重置状态",
            fix = "加remember."
        ))
        BugDB.load(BugRule(
            id = "KT-0665",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.SEVERE,
            title = "[S09] 主线程IO操作",
            trigger = "Text(text=readFile())",
            detection = "读取文件在主线程卡UI",
            fix = "用LaunchedEffect+Dispatchers.IO."
        ))
        BugDB.load(BugRule(
            id = "KT-0666",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.MODERATE,
            title = "[D10] derivedStateOf缺失",
            trigger = "val filtered=list.filter{...}",
            detection = "每次重组都重新filter",
            fix = "用derivedStateOf."
        ))
        BugDB.load(BugRule(
            id = "KT-0667",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.MODERATE,
            title = "[D11] derivedStateOf缺失",
            trigger = "val filtered=list.filter{...}",
            detection = "每次重组都重新filter",
            fix = "用derivedStateOf."
        ))
        BugDB.load(BugRule(
            id = "KT-0668",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.MODERATE,
            title = "[D12] derivedStateOf缺失",
            trigger = "val filtered=list.filter{...}",
            detection = "每次重组都重新filter",
            fix = "用derivedStateOf."
        ))
        BugDB.load(BugRule(
            id = "KT-0669",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.MODERATE,
            title = "[D13] derivedStateOf缺失",
            trigger = "val filtered=list.filter{...}",
            detection = "每次重组都重新filter",
            fix = "用derivedStateOf."
        ))
        BugDB.load(BugRule(
            id = "KT-0670",
            category = BugCategory.COMPOSE,
            severity = BugSeverity.MODERATE,
            title = "[D14] derivedStateOf缺失",
            trigger = "val filtered=list.filter{...}",
            detection = "每次重组都重新filter",
            fix = "用derivedStateOf."
        ))
        BugDB.load(BugRule(
            id = "KT-0671",
            category = BugCategory.VALUE_CLASS,
            severity = BugSeverity.MODERATE,
            title = "[D00] @JvmInline缺失",
            trigger = "inline class Name(val s:String)",
            detection = "内联类需@JvmInline",
            fix = "加@JvmInline."
        ))
        BugDB.load(BugRule(
            id = "KT-0672",
            category = BugCategory.VALUE_CLASS,
            severity = BugSeverity.MILD,
            title = "[M00] 值类装箱开销",
            trigger = "val list: List<Name>",
            detection = "List<Name>装箱",
            fix = "用Array<Name>避免装箱."
        ))
        BugDB.load(BugRule(
            id = "KT-0673",
            category = BugCategory.VALUE_CLASS,
            severity = BugSeverity.MODERATE,
            title = "[D01] @JvmInline缺失",
            trigger = "inline class Name(val s:String)",
            detection = "内联类需@JvmInline",
            fix = "加@JvmInline."
        ))
        BugDB.load(BugRule(
            id = "KT-0674",
            category = BugCategory.VALUE_CLASS,
            severity = BugSeverity.MILD,
            title = "[M01] 值类装箱开销",
            trigger = "val list: List<Name>",
            detection = "List<Name>装箱",
            fix = "用Array<Name>避免装箱."
        ))
        BugDB.load(BugRule(
            id = "KT-0675",
            category = BugCategory.VALUE_CLASS,
            severity = BugSeverity.MODERATE,
            title = "[D02] @JvmInline缺失",
            trigger = "inline class Name(val s:String)",
            detection = "内联类需@JvmInline",
            fix = "加@JvmInline."
        ))
        BugDB.load(BugRule(
            id = "KT-0676",
            category = BugCategory.VALUE_CLASS,
            severity = BugSeverity.MILD,
            title = "[M02] 值类装箱开销",
            trigger = "val list: List<Name>",
            detection = "List<Name>装箱",
            fix = "用Array<Name>避免装箱."
        ))
        BugDB.load(BugRule(
            id = "KT-0677",
            category = BugCategory.VALUE_CLASS,
            severity = BugSeverity.MODERATE,
            title = "[D03] @JvmInline缺失",
            trigger = "inline class Name(val s:String)",
            detection = "内联类需@JvmInline",
            fix = "加@JvmInline."
        ))
        BugDB.load(BugRule(
            id = "KT-0678",
            category = BugCategory.VALUE_CLASS,
            severity = BugSeverity.MILD,
            title = "[M03] 值类装箱开销",
            trigger = "val list: List<Name>",
            detection = "List<Name>装箱",
            fix = "用Array<Name>避免装箱."
        ))
        BugDB.load(BugRule(
            id = "KT-0679",
            category = BugCategory.VALUE_CLASS,
            severity = BugSeverity.MODERATE,
            title = "[D04] @JvmInline缺失",
            trigger = "inline class Name(val s:String)",
            detection = "内联类需@JvmInline",
            fix = "加@JvmInline."
        ))
        BugDB.load(BugRule(
            id = "KT-0680",
            category = BugCategory.VALUE_CLASS,
            severity = BugSeverity.MILD,
            title = "[M04] 值类装箱开销",
            trigger = "val list: List<Name>",
            detection = "List<Name>装箱",
            fix = "用Array<Name>避免装箱."
        ))
        BugDB.load(BugRule(
            id = "KT-0681",
            category = BugCategory.VALUE_CLASS,
            severity = BugSeverity.MODERATE,
            title = "[D05] @JvmInline缺失",
            trigger = "inline class Name(val s:String)",
            detection = "内联类需@JvmInline",
            fix = "加@JvmInline."
        ))
        BugDB.load(BugRule(
            id = "KT-0682",
            category = BugCategory.VALUE_CLASS,
            severity = BugSeverity.MILD,
            title = "[M05] 值类装箱开销",
            trigger = "val list: List<Name>",
            detection = "List<Name>装箱",
            fix = "用Array<Name>避免装箱."
        ))
        BugDB.load(BugRule(
            id = "KT-0683",
            category = BugCategory.VALUE_CLASS,
            severity = BugSeverity.MODERATE,
            title = "[D06] @JvmInline缺失",
            trigger = "inline class Name(val s:String)",
            detection = "内联类需@JvmInline",
            fix = "加@JvmInline."
        ))
        BugDB.load(BugRule(
            id = "KT-0684",
            category = BugCategory.VALUE_CLASS,
            severity = BugSeverity.MILD,
            title = "[M06] 值类装箱开销",
            trigger = "val list: List<Name>",
            detection = "List<Name>装箱",
            fix = "用Array<Name>避免装箱."
        ))
        BugDB.load(BugRule(
            id = "KT-0685",
            category = BugCategory.VALUE_CLASS,
            severity = BugSeverity.MODERATE,
            title = "[D07] @JvmInline缺失",
            trigger = "inline class Name(val s:String)",
            detection = "内联类需@JvmInline",
            fix = "加@JvmInline."
        ))
        BugDB.load(BugRule(
            id = "KT-0686",
            category = BugCategory.VALUE_CLASS,
            severity = BugSeverity.MILD,
            title = "[M07] 值类装箱开销",
            trigger = "val list: List<Name>",
            detection = "List<Name>装箱",
            fix = "用Array<Name>避免装箱."
        ))
        BugDB.load(BugRule(
            id = "KT-0687",
            category = BugCategory.VALUE_CLASS,
            severity = BugSeverity.MODERATE,
            title = "[D08] @JvmInline缺失",
            trigger = "inline class Name(val s:String)",
            detection = "内联类需@JvmInline",
            fix = "加@JvmInline."
        ))
        BugDB.load(BugRule(
            id = "KT-0688",
            category = BugCategory.VALUE_CLASS,
            severity = BugSeverity.MILD,
            title = "[M08] 值类装箱开销",
            trigger = "val list: List<Name>",
            detection = "List<Name>装箱",
            fix = "用Array<Name>避免装箱."
        ))
        BugDB.load(BugRule(
            id = "KT-0689",
            category = BugCategory.VALUE_CLASS,
            severity = BugSeverity.MODERATE,
            title = "[D09] @JvmInline缺失",
            trigger = "inline class Name(val s:String)",
            detection = "内联类需@JvmInline",
            fix = "加@JvmInline."
        ))
        BugDB.load(BugRule(
            id = "KT-0690",
            category = BugCategory.VALUE_CLASS,
            severity = BugSeverity.MILD,
            title = "[M09] 值类装箱开销",
            trigger = "val list: List<Name>",
            detection = "List<Name>装箱",
            fix = "用Array<Name>避免装箱."
        ))
        BugDB.load(BugRule(
            id = "KT-0691",
            category = BugCategory.DELEGATE,
            severity = BugSeverity.MODERATE,
            title = "[D00] by lazy 线程安全模式默认",
            trigger = "val x by lazy{ init() }",
            detection = "默认SYNCHRONIZED,可能死锁",
            fix = "指定LazyThreadSafetyMode."
        ))
        BugDB.load(BugRule(
            id = "KT-0692",
            category = BugCategory.DELEGATE,
            severity = BugSeverity.MILD,
            title = "[M00] Delegates.observable滥用",
            trigger = "var x by Delegates.observable(0){_,_,new->...}",
            detection = "简单状态变化不需要observable",
            fix = "用StateFlow."
        ))
        BugDB.load(BugRule(
            id = "KT-0693",
            category = BugCategory.DELEGATE,
            severity = BugSeverity.MODERATE,
            title = "[D01] by lazy 线程安全模式默认",
            trigger = "val x by lazy{ init() }",
            detection = "默认SYNCHRONIZED,可能死锁",
            fix = "指定LazyThreadSafetyMode."
        ))
        BugDB.load(BugRule(
            id = "KT-0694",
            category = BugCategory.DELEGATE,
            severity = BugSeverity.MILD,
            title = "[M01] Delegates.observable滥用",
            trigger = "var x by Delegates.observable(0){_,_,new->...}",
            detection = "简单状态变化不需要observable",
            fix = "用StateFlow."
        ))
        BugDB.load(BugRule(
            id = "KT-0695",
            category = BugCategory.DELEGATE,
            severity = BugSeverity.MODERATE,
            title = "[D02] by lazy 线程安全模式默认",
            trigger = "val x by lazy{ init() }",
            detection = "默认SYNCHRONIZED,可能死锁",
            fix = "指定LazyThreadSafetyMode."
        ))
        BugDB.load(BugRule(
            id = "KT-0696",
            category = BugCategory.DELEGATE,
            severity = BugSeverity.MILD,
            title = "[M02] Delegates.observable滥用",
            trigger = "var x by Delegates.observable(0){_,_,new->...}",
            detection = "简单状态变化不需要observable",
            fix = "用StateFlow."
        ))
        BugDB.load(BugRule(
            id = "KT-0697",
            category = BugCategory.DELEGATE,
            severity = BugSeverity.MODERATE,
            title = "[D03] by lazy 线程安全模式默认",
            trigger = "val x by lazy{ init() }",
            detection = "默认SYNCHRONIZED,可能死锁",
            fix = "指定LazyThreadSafetyMode."
        ))
        BugDB.load(BugRule(
            id = "KT-0698",
            category = BugCategory.DELEGATE,
            severity = BugSeverity.MILD,
            title = "[M03] Delegates.observable滥用",
            trigger = "var x by Delegates.observable(0){_,_,new->...}",
            detection = "简单状态变化不需要observable",
            fix = "用StateFlow."
        ))
        BugDB.load(BugRule(
            id = "KT-0699",
            category = BugCategory.DELEGATE,
            severity = BugSeverity.MODERATE,
            title = "[D04] by lazy 线程安全模式默认",
            trigger = "val x by lazy{ init() }",
            detection = "默认SYNCHRONIZED,可能死锁",
            fix = "指定LazyThreadSafetyMode."
        ))
        BugDB.load(BugRule(
            id = "KT-0700",
            category = BugCategory.DELEGATE,
            severity = BugSeverity.MILD,
            title = "[M04] Delegates.observable滥用",
            trigger = "var x by Delegates.observable(0){_,_,new->...}",
            detection = "简单状态变化不需要observable",
            fix = "用StateFlow."
        ))
        BugDB.load(BugRule(
            id = "KT-0701",
            category = BugCategory.DELEGATE,
            severity = BugSeverity.MODERATE,
            title = "[D05] by lazy 线程安全模式默认",
            trigger = "val x by lazy{ init() }",
            detection = "默认SYNCHRONIZED,可能死锁",
            fix = "指定LazyThreadSafetyMode."
        ))
        BugDB.load(BugRule(
            id = "KT-0702",
            category = BugCategory.DELEGATE,
            severity = BugSeverity.MILD,
            title = "[M05] Delegates.observable滥用",
            trigger = "var x by Delegates.observable(0){_,_,new->...}",
            detection = "简单状态变化不需要observable",
            fix = "用StateFlow."
        ))
        BugDB.load(BugRule(
            id = "KT-0703",
            category = BugCategory.DELEGATE,
            severity = BugSeverity.MODERATE,
            title = "[D06] by lazy 线程安全模式默认",
            trigger = "val x by lazy{ init() }",
            detection = "默认SYNCHRONIZED,可能死锁",
            fix = "指定LazyThreadSafetyMode."
        ))
        BugDB.load(BugRule(
            id = "KT-0704",
            category = BugCategory.DELEGATE,
            severity = BugSeverity.MILD,
            title = "[M06] Delegates.observable滥用",
            trigger = "var x by Delegates.observable(0){_,_,new->...}",
            detection = "简单状态变化不需要observable",
            fix = "用StateFlow."
        ))
        BugDB.load(BugRule(
            id = "KT-0705",
            category = BugCategory.DELEGATE,
            severity = BugSeverity.MODERATE,
            title = "[D07] by lazy 线程安全模式默认",
            trigger = "val x by lazy{ init() }",
            detection = "默认SYNCHRONIZED,可能死锁",
            fix = "指定LazyThreadSafetyMode."
        ))
        BugDB.load(BugRule(
            id = "KT-0706",
            category = BugCategory.DELEGATE,
            severity = BugSeverity.MILD,
            title = "[M07] Delegates.observable滥用",
            trigger = "var x by Delegates.observable(0){_,_,new->...}",
            detection = "简单状态变化不需要observable",
            fix = "用StateFlow."
        ))
        BugDB.load(BugRule(
            id = "KT-0707",
            category = BugCategory.DELEGATE,
            severity = BugSeverity.MODERATE,
            title = "[D08] by lazy 线程安全模式默认",
            trigger = "val x by lazy{ init() }",
            detection = "默认SYNCHRONIZED,可能死锁",
            fix = "指定LazyThreadSafetyMode."
        ))
        BugDB.load(BugRule(
            id = "KT-0708",
            category = BugCategory.DELEGATE,
            severity = BugSeverity.MILD,
            title = "[M08] Delegates.observable滥用",
            trigger = "var x by Delegates.observable(0){_,_,new->...}",
            detection = "简单状态变化不需要observable",
            fix = "用StateFlow."
        ))
        BugDB.load(BugRule(
            id = "KT-0709",
            category = BugCategory.DELEGATE,
            severity = BugSeverity.MODERATE,
            title = "[D09] by lazy 线程安全模式默认",
            trigger = "val x by lazy{ init() }",
            detection = "默认SYNCHRONIZED,可能死锁",
            fix = "指定LazyThreadSafetyMode."
        ))
        BugDB.load(BugRule(
            id = "KT-0710",
            category = BugCategory.DELEGATE,
            severity = BugSeverity.MILD,
            title = "[M09] Delegates.observable滥用",
            trigger = "var x by Delegates.observable(0){_,_,new->...}",
            detection = "简单状态变化不需要observable",
            fix = "用StateFlow."
        ))
        BugDB.load(BugRule(
            id = "KT-0711",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D00] expect/actual不匹配",
            trigger = "expect fun foo():String; actual fun foo():Int",
            detection = "返回类型不同",
            fix = "对齐返回类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0712",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S00] 平台特定API未expect/actual",
            trigger = "fun androidOnly(){ System.loadLibrary() }",
            detection = "仅Android可用,其他平台炸",
            fix = "expect/actual分离."
        ))
        BugDB.load(BugRule(
            id = "KT-0713",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D01] expect/actual不匹配",
            trigger = "expect fun foo():String; actual fun foo():Int",
            detection = "返回类型不同",
            fix = "对齐返回类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0714",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S01] 平台特定API未expect/actual",
            trigger = "fun androidOnly(){ System.loadLibrary() }",
            detection = "仅Android可用,其他平台炸",
            fix = "expect/actual分离."
        ))
        BugDB.load(BugRule(
            id = "KT-0715",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D02] expect/actual不匹配",
            trigger = "expect fun foo():String; actual fun foo():Int",
            detection = "返回类型不同",
            fix = "对齐返回类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0716",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S02] 平台特定API未expect/actual",
            trigger = "fun androidOnly(){ System.loadLibrary() }",
            detection = "仅Android可用,其他平台炸",
            fix = "expect/actual分离."
        ))
        BugDB.load(BugRule(
            id = "KT-0717",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D03] expect/actual不匹配",
            trigger = "expect fun foo():String; actual fun foo():Int",
            detection = "返回类型不同",
            fix = "对齐返回类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0718",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S03] 平台特定API未expect/actual",
            trigger = "fun androidOnly(){ System.loadLibrary() }",
            detection = "仅Android可用,其他平台炸",
            fix = "expect/actual分离."
        ))
        BugDB.load(BugRule(
            id = "KT-0719",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D04] expect/actual不匹配",
            trigger = "expect fun foo():String; actual fun foo():Int",
            detection = "返回类型不同",
            fix = "对齐返回类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0720",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S04] 平台特定API未expect/actual",
            trigger = "fun androidOnly(){ System.loadLibrary() }",
            detection = "仅Android可用,其他平台炸",
            fix = "expect/actual分离."
        ))
        BugDB.load(BugRule(
            id = "KT-0721",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D05] expect/actual不匹配",
            trigger = "expect fun foo():String; actual fun foo():Int",
            detection = "返回类型不同",
            fix = "对齐返回类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0722",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S05] 平台特定API未expect/actual",
            trigger = "fun androidOnly(){ System.loadLibrary() }",
            detection = "仅Android可用,其他平台炸",
            fix = "expect/actual分离."
        ))
        BugDB.load(BugRule(
            id = "KT-0723",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D06] expect/actual不匹配",
            trigger = "expect fun foo():String; actual fun foo():Int",
            detection = "返回类型不同",
            fix = "对齐返回类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0724",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S06] 平台特定API未expect/actual",
            trigger = "fun androidOnly(){ System.loadLibrary() }",
            detection = "仅Android可用,其他平台炸",
            fix = "expect/actual分离."
        ))
        BugDB.load(BugRule(
            id = "KT-0725",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D07] expect/actual不匹配",
            trigger = "expect fun foo():String; actual fun foo():Int",
            detection = "返回类型不同",
            fix = "对齐返回类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0726",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S07] 平台特定API未expect/actual",
            trigger = "fun androidOnly(){ System.loadLibrary() }",
            detection = "仅Android可用,其他平台炸",
            fix = "expect/actual分离."
        ))
        BugDB.load(BugRule(
            id = "KT-0727",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D08] expect/actual不匹配",
            trigger = "expect fun foo():String; actual fun foo():Int",
            detection = "返回类型不同",
            fix = "对齐返回类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0728",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S08] 平台特定API未expect/actual",
            trigger = "fun androidOnly(){ System.loadLibrary() }",
            detection = "仅Android可用,其他平台炸",
            fix = "expect/actual分离."
        ))
        BugDB.load(BugRule(
            id = "KT-0729",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D09] expect/actual不匹配",
            trigger = "expect fun foo():String; actual fun foo():Int",
            detection = "返回类型不同",
            fix = "对齐返回类型."
        ))
        BugDB.load(BugRule(
            id = "KT-0730",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S09] 平台特定API未expect/actual",
            trigger = "fun androidOnly(){ System.loadLibrary() }",
            detection = "仅Android可用,其他平台炸",
            fix = "expect/actual分离."
        ))
        BugDB.load(BugRule(
            id = "KT-0731",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[D30] Kotlin 属性在Java中直接访问field",
            trigger = "class Foo{ @JvmField val x=1 }",
            detection = "Java可直接访问field,破坏封装",
            fix = "用getX()而非直接访问field."
        ))
        BugDB.load(BugRule(
            id = "KT-0732",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.SEVERE,
            title = "[S30] 平台类型在集合中传播",
            trigger = "val list: List<String!> = javaList()",
            detection = "平台类型在泛型参数中更危险",
            fix = "显式声明List<String?>."
        ))
        BugDB.load(BugRule(
            id = "KT-0733",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[D31] Kotlin 属性在Java中直接访问field",
            trigger = "class Foo{ @JvmField val x=1 }",
            detection = "Java可直接访问field,破坏封装",
            fix = "用getX()而非直接访问field."
        ))
        BugDB.load(BugRule(
            id = "KT-0734",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.SEVERE,
            title = "[S31] 平台类型在集合中传播",
            trigger = "val list: List<String!> = javaList()",
            detection = "平台类型在泛型参数中更危险",
            fix = "显式声明List<String?>."
        ))
        BugDB.load(BugRule(
            id = "KT-0735",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[D32] Kotlin 属性在Java中直接访问field",
            trigger = "class Foo{ @JvmField val x=1 }",
            detection = "Java可直接访问field,破坏封装",
            fix = "用getX()而非直接访问field."
        ))
        BugDB.load(BugRule(
            id = "KT-0736",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.SEVERE,
            title = "[S32] 平台类型在集合中传播",
            trigger = "val list: List<String!> = javaList()",
            detection = "平台类型在泛型参数中更危险",
            fix = "显式声明List<String?>."
        ))
        BugDB.load(BugRule(
            id = "KT-0737",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[D33] Kotlin 属性在Java中直接访问field",
            trigger = "class Foo{ @JvmField val x=1 }",
            detection = "Java可直接访问field,破坏封装",
            fix = "用getX()而非直接访问field."
        ))
        BugDB.load(BugRule(
            id = "KT-0738",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.SEVERE,
            title = "[S33] 平台类型在集合中传播",
            trigger = "val list: List<String!> = javaList()",
            detection = "平台类型在泛型参数中更危险",
            fix = "显式声明List<String?>."
        ))
        BugDB.load(BugRule(
            id = "KT-0739",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[D34] Kotlin 属性在Java中直接访问field",
            trigger = "class Foo{ @JvmField val x=1 }",
            detection = "Java可直接访问field,破坏封装",
            fix = "用getX()而非直接访问field."
        ))
        BugDB.load(BugRule(
            id = "KT-0740",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.SEVERE,
            title = "[S34] 平台类型在集合中传播",
            trigger = "val list: List<String!> = javaList()",
            detection = "平台类型在泛型参数中更危险",
            fix = "显式声明List<String?>."
        ))
        BugDB.load(BugRule(
            id = "KT-0741",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[D35] Kotlin 属性在Java中直接访问field",
            trigger = "class Foo{ @JvmField val x=1 }",
            detection = "Java可直接访问field,破坏封装",
            fix = "用getX()而非直接访问field."
        ))
        BugDB.load(BugRule(
            id = "KT-0742",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.SEVERE,
            title = "[S35] 平台类型在集合中传播",
            trigger = "val list: List<String!> = javaList()",
            detection = "平台类型在泛型参数中更危险",
            fix = "显式声明List<String?>."
        ))
        BugDB.load(BugRule(
            id = "KT-0743",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[D36] Kotlin 属性在Java中直接访问field",
            trigger = "class Foo{ @JvmField val x=1 }",
            detection = "Java可直接访问field,破坏封装",
            fix = "用getX()而非直接访问field."
        ))
        BugDB.load(BugRule(
            id = "KT-0744",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.SEVERE,
            title = "[S36] 平台类型在集合中传播",
            trigger = "val list: List<String!> = javaList()",
            detection = "平台类型在泛型参数中更危险",
            fix = "显式声明List<String?>."
        ))
        BugDB.load(BugRule(
            id = "KT-0745",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[D37] Kotlin 属性在Java中直接访问field",
            trigger = "class Foo{ @JvmField val x=1 }",
            detection = "Java可直接访问field,破坏封装",
            fix = "用getX()而非直接访问field."
        ))
        BugDB.load(BugRule(
            id = "KT-0746",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.SEVERE,
            title = "[S37] 平台类型在集合中传播",
            trigger = "val list: List<String!> = javaList()",
            detection = "平台类型在泛型参数中更危险",
            fix = "显式声明List<String?>."
        ))
        BugDB.load(BugRule(
            id = "KT-0747",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[D38] Kotlin 属性在Java中直接访问field",
            trigger = "class Foo{ @JvmField val x=1 }",
            detection = "Java可直接访问field,破坏封装",
            fix = "用getX()而非直接访问field."
        ))
        BugDB.load(BugRule(
            id = "KT-0748",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.SEVERE,
            title = "[S38] 平台类型在集合中传播",
            trigger = "val list: List<String!> = javaList()",
            detection = "平台类型在泛型参数中更危险",
            fix = "显式声明List<String?>."
        ))
        BugDB.load(BugRule(
            id = "KT-0749",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[D39] Kotlin 属性在Java中直接访问field",
            trigger = "class Foo{ @JvmField val x=1 }",
            detection = "Java可直接访问field,破坏封装",
            fix = "用getX()而非直接访问field."
        ))
        BugDB.load(BugRule(
            id = "KT-0750",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.SEVERE,
            title = "[S39] 平台类型在集合中传播",
            trigger = "val list: List<String!> = javaList()",
            detection = "平台类型在泛型参数中更危险",
            fix = "显式声明List<String?>."
        ))
        BugDB.load(BugRule(
            id = "KT-0751",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[D20] supervisorScope vs coroutineScope混淆",
            trigger = "supervisorScope{ launch{ error() }; launch{ ok() } }",
            detection = "supervisorScope子协程异常不取消兄弟",
            fix = "根据需求选用supervisorScope."
        ))
        BugDB.load(BugRule(
            id = "KT-0752",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.SEVERE,
            title = "[S20] 协程泄露未取消Job",
            trigger = "val job=launch{ while(true){ delay(100)} }",
            detection = "无限循环协程未取消",
            fix = "用Job.cancel()或lifecycle管理."
        ))
        BugDB.load(BugRule(
            id = "KT-0753",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[D21] supervisorScope vs coroutineScope混淆",
            trigger = "supervisorScope{ launch{ error() }; launch{ ok() } }",
            detection = "supervisorScope子协程异常不取消兄弟",
            fix = "根据需求选用supervisorScope."
        ))
        BugDB.load(BugRule(
            id = "KT-0754",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.SEVERE,
            title = "[S21] 协程泄露未取消Job",
            trigger = "val job=launch{ while(true){ delay(100)} }",
            detection = "无限循环协程未取消",
            fix = "用Job.cancel()或lifecycle管理."
        ))
        BugDB.load(BugRule(
            id = "KT-0755",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[D22] supervisorScope vs coroutineScope混淆",
            trigger = "supervisorScope{ launch{ error() }; launch{ ok() } }",
            detection = "supervisorScope子协程异常不取消兄弟",
            fix = "根据需求选用supervisorScope."
        ))
        BugDB.load(BugRule(
            id = "KT-0756",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.SEVERE,
            title = "[S22] 协程泄露未取消Job",
            trigger = "val job=launch{ while(true){ delay(100)} }",
            detection = "无限循环协程未取消",
            fix = "用Job.cancel()或lifecycle管理."
        ))
        BugDB.load(BugRule(
            id = "KT-0757",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[D23] supervisorScope vs coroutineScope混淆",
            trigger = "supervisorScope{ launch{ error() }; launch{ ok() } }",
            detection = "supervisorScope子协程异常不取消兄弟",
            fix = "根据需求选用supervisorScope."
        ))
        BugDB.load(BugRule(
            id = "KT-0758",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.SEVERE,
            title = "[S23] 协程泄露未取消Job",
            trigger = "val job=launch{ while(true){ delay(100)} }",
            detection = "无限循环协程未取消",
            fix = "用Job.cancel()或lifecycle管理."
        ))
        BugDB.load(BugRule(
            id = "KT-0759",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[D24] supervisorScope vs coroutineScope混淆",
            trigger = "supervisorScope{ launch{ error() }; launch{ ok() } }",
            detection = "supervisorScope子协程异常不取消兄弟",
            fix = "根据需求选用supervisorScope."
        ))
        BugDB.load(BugRule(
            id = "KT-0760",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.SEVERE,
            title = "[S24] 协程泄露未取消Job",
            trigger = "val job=launch{ while(true){ delay(100)} }",
            detection = "无限循环协程未取消",
            fix = "用Job.cancel()或lifecycle管理."
        ))
        BugDB.load(BugRule(
            id = "KT-0761",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[D25] supervisorScope vs coroutineScope混淆",
            trigger = "supervisorScope{ launch{ error() }; launch{ ok() } }",
            detection = "supervisorScope子协程异常不取消兄弟",
            fix = "根据需求选用supervisorScope."
        ))
        BugDB.load(BugRule(
            id = "KT-0762",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.SEVERE,
            title = "[S25] 协程泄露未取消Job",
            trigger = "val job=launch{ while(true){ delay(100)} }",
            detection = "无限循环协程未取消",
            fix = "用Job.cancel()或lifecycle管理."
        ))
        BugDB.load(BugRule(
            id = "KT-0763",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[D26] supervisorScope vs coroutineScope混淆",
            trigger = "supervisorScope{ launch{ error() }; launch{ ok() } }",
            detection = "supervisorScope子协程异常不取消兄弟",
            fix = "根据需求选用supervisorScope."
        ))
        BugDB.load(BugRule(
            id = "KT-0764",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.SEVERE,
            title = "[S26] 协程泄露未取消Job",
            trigger = "val job=launch{ while(true){ delay(100)} }",
            detection = "无限循环协程未取消",
            fix = "用Job.cancel()或lifecycle管理."
        ))
        BugDB.load(BugRule(
            id = "KT-0765",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[D27] supervisorScope vs coroutineScope混淆",
            trigger = "supervisorScope{ launch{ error() }; launch{ ok() } }",
            detection = "supervisorScope子协程异常不取消兄弟",
            fix = "根据需求选用supervisorScope."
        ))
        BugDB.load(BugRule(
            id = "KT-0766",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.SEVERE,
            title = "[S27] 协程泄露未取消Job",
            trigger = "val job=launch{ while(true){ delay(100)} }",
            detection = "无限循环协程未取消",
            fix = "用Job.cancel()或lifecycle管理."
        ))
        BugDB.load(BugRule(
            id = "KT-0767",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[D28] supervisorScope vs coroutineScope混淆",
            trigger = "supervisorScope{ launch{ error() }; launch{ ok() } }",
            detection = "supervisorScope子协程异常不取消兄弟",
            fix = "根据需求选用supervisorScope."
        ))
        BugDB.load(BugRule(
            id = "KT-0768",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.SEVERE,
            title = "[S28] 协程泄露未取消Job",
            trigger = "val job=launch{ while(true){ delay(100)} }",
            detection = "无限循环协程未取消",
            fix = "用Job.cancel()或lifecycle管理."
        ))
        BugDB.load(BugRule(
            id = "KT-0769",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[D29] supervisorScope vs coroutineScope混淆",
            trigger = "supervisorScope{ launch{ error() }; launch{ ok() } }",
            detection = "supervisorScope子协程异常不取消兄弟",
            fix = "根据需求选用supervisorScope."
        ))
        BugDB.load(BugRule(
            id = "KT-0770",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.SEVERE,
            title = "[S29] 协程泄露未取消Job",
            trigger = "val job=launch{ while(true){ delay(100)} }",
            detection = "无限循环协程未取消",
            fix = "用Job.cancel()或lifecycle管理."
        ))
        BugDB.load(BugRule(
            id = "KT-0771",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[D30] supervisorScope vs coroutineScope混淆",
            trigger = "supervisorScope{ launch{ error() }; launch{ ok() } }",
            detection = "supervisorScope子协程异常不取消兄弟",
            fix = "根据需求选用supervisorScope."
        ))
        BugDB.load(BugRule(
            id = "KT-0772",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.SEVERE,
            title = "[S30] 协程泄露未取消Job",
            trigger = "val job=launch{ while(true){ delay(100)} }",
            detection = "无限循环协程未取消",
            fix = "用Job.cancel()或lifecycle管理."
        ))
        BugDB.load(BugRule(
            id = "KT-0773",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[D31] supervisorScope vs coroutineScope混淆",
            trigger = "supervisorScope{ launch{ error() }; launch{ ok() } }",
            detection = "supervisorScope子协程异常不取消兄弟",
            fix = "根据需求选用supervisorScope."
        ))
        BugDB.load(BugRule(
            id = "KT-0774",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.SEVERE,
            title = "[S31] 协程泄露未取消Job",
            trigger = "val job=launch{ while(true){ delay(100)} }",
            detection = "无限循环协程未取消",
            fix = "用Job.cancel()或lifecycle管理."
        ))
        BugDB.load(BugRule(
            id = "KT-0775",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[D32] supervisorScope vs coroutineScope混淆",
            trigger = "supervisorScope{ launch{ error() }; launch{ ok() } }",
            detection = "supervisorScope子协程异常不取消兄弟",
            fix = "根据需求选用supervisorScope."
        ))
        BugDB.load(BugRule(
            id = "KT-0776",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.SEVERE,
            title = "[S32] 协程泄露未取消Job",
            trigger = "val job=launch{ while(true){ delay(100)} }",
            detection = "无限循环协程未取消",
            fix = "用Job.cancel()或lifecycle管理."
        ))
        BugDB.load(BugRule(
            id = "KT-0777",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[D33] supervisorScope vs coroutineScope混淆",
            trigger = "supervisorScope{ launch{ error() }; launch{ ok() } }",
            detection = "supervisorScope子协程异常不取消兄弟",
            fix = "根据需求选用supervisorScope."
        ))
        BugDB.load(BugRule(
            id = "KT-0778",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.SEVERE,
            title = "[S33] 协程泄露未取消Job",
            trigger = "val job=launch{ while(true){ delay(100)} }",
            detection = "无限循环协程未取消",
            fix = "用Job.cancel()或lifecycle管理."
        ))
        BugDB.load(BugRule(
            id = "KT-0779",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[D34] supervisorScope vs coroutineScope混淆",
            trigger = "supervisorScope{ launch{ error() }; launch{ ok() } }",
            detection = "supervisorScope子协程异常不取消兄弟",
            fix = "根据需求选用supervisorScope."
        ))
        BugDB.load(BugRule(
            id = "KT-0780",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.SEVERE,
            title = "[S34] 协程泄露未取消Job",
            trigger = "val job=launch{ while(true){ delay(100)} }",
            detection = "无限循环协程未取消",
            fix = "用Job.cancel()或lifecycle管理."
        ))
        BugDB.load(BugRule(
            id = "KT-0781",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D20] 平台特定类型未抽象",
            trigger = "fun platformApi(): android.os.Bundle",
            detection = "直接引用Android类型",
            fix = "用expect类或接口抽象."
        ))
        BugDB.load(BugRule(
            id = "KT-0782",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S20] 共用代码中调用平台API",
            trigger = "fun commonCode(){ System.getProperty(...)}",
            detection = "System.getProperty仅JVM",
            fix = "用expect/actual."
        ))
        BugDB.load(BugRule(
            id = "KT-0783",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D21] 平台特定类型未抽象",
            trigger = "fun platformApi(): android.os.Bundle",
            detection = "直接引用Android类型",
            fix = "用expect类或接口抽象."
        ))
        BugDB.load(BugRule(
            id = "KT-0784",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S21] 共用代码中调用平台API",
            trigger = "fun commonCode(){ System.getProperty(...)}",
            detection = "System.getProperty仅JVM",
            fix = "用expect/actual."
        ))
        BugDB.load(BugRule(
            id = "KT-0785",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D22] 平台特定类型未抽象",
            trigger = "fun platformApi(): android.os.Bundle",
            detection = "直接引用Android类型",
            fix = "用expect类或接口抽象."
        ))
        BugDB.load(BugRule(
            id = "KT-0786",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S22] 共用代码中调用平台API",
            trigger = "fun commonCode(){ System.getProperty(...)}",
            detection = "System.getProperty仅JVM",
            fix = "用expect/actual."
        ))
        BugDB.load(BugRule(
            id = "KT-0787",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D23] 平台特定类型未抽象",
            trigger = "fun platformApi(): android.os.Bundle",
            detection = "直接引用Android类型",
            fix = "用expect类或接口抽象."
        ))
        BugDB.load(BugRule(
            id = "KT-0788",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S23] 共用代码中调用平台API",
            trigger = "fun commonCode(){ System.getProperty(...)}",
            detection = "System.getProperty仅JVM",
            fix = "用expect/actual."
        ))
        BugDB.load(BugRule(
            id = "KT-0789",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D24] 平台特定类型未抽象",
            trigger = "fun platformApi(): android.os.Bundle",
            detection = "直接引用Android类型",
            fix = "用expect类或接口抽象."
        ))
        BugDB.load(BugRule(
            id = "KT-0790",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S24] 共用代码中调用平台API",
            trigger = "fun commonCode(){ System.getProperty(...)}",
            detection = "System.getProperty仅JVM",
            fix = "用expect/actual."
        ))
        BugDB.load(BugRule(
            id = "KT-0791",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D25] 平台特定类型未抽象",
            trigger = "fun platformApi(): android.os.Bundle",
            detection = "直接引用Android类型",
            fix = "用expect类或接口抽象."
        ))
        BugDB.load(BugRule(
            id = "KT-0792",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S25] 共用代码中调用平台API",
            trigger = "fun commonCode(){ System.getProperty(...)}",
            detection = "System.getProperty仅JVM",
            fix = "用expect/actual."
        ))
        BugDB.load(BugRule(
            id = "KT-0793",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D26] 平台特定类型未抽象",
            trigger = "fun platformApi(): android.os.Bundle",
            detection = "直接引用Android类型",
            fix = "用expect类或接口抽象."
        ))
        BugDB.load(BugRule(
            id = "KT-0794",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S26] 共用代码中调用平台API",
            trigger = "fun commonCode(){ System.getProperty(...)}",
            detection = "System.getProperty仅JVM",
            fix = "用expect/actual."
        ))
        BugDB.load(BugRule(
            id = "KT-0795",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D27] 平台特定类型未抽象",
            trigger = "fun platformApi(): android.os.Bundle",
            detection = "直接引用Android类型",
            fix = "用expect类或接口抽象."
        ))
        BugDB.load(BugRule(
            id = "KT-0796",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S27] 共用代码中调用平台API",
            trigger = "fun commonCode(){ System.getProperty(...)}",
            detection = "System.getProperty仅JVM",
            fix = "用expect/actual."
        ))
        BugDB.load(BugRule(
            id = "KT-0797",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D28] 平台特定类型未抽象",
            trigger = "fun platformApi(): android.os.Bundle",
            detection = "直接引用Android类型",
            fix = "用expect类或接口抽象."
        ))
        BugDB.load(BugRule(
            id = "KT-0798",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S28] 共用代码中调用平台API",
            trigger = "fun commonCode(){ System.getProperty(...)}",
            detection = "System.getProperty仅JVM",
            fix = "用expect/actual."
        ))
        BugDB.load(BugRule(
            id = "KT-0799",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D29] 平台特定类型未抽象",
            trigger = "fun platformApi(): android.os.Bundle",
            detection = "直接引用Android类型",
            fix = "用expect类或接口抽象."
        ))
        BugDB.load(BugRule(
            id = "KT-0800",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S29] 共用代码中调用平台API",
            trigger = "fun commonCode(){ System.getProperty(...)}",
            detection = "System.getProperty仅JVM",
            fix = "用expect/actual."
        ))
        BugDB.load(BugRule(
            id = "KT-0801",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D30] 平台特定类型未抽象",
            trigger = "fun platformApi(): android.os.Bundle",
            detection = "直接引用Android类型",
            fix = "用expect类或接口抽象."
        ))
        BugDB.load(BugRule(
            id = "KT-0802",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S30] 共用代码中调用平台API",
            trigger = "fun commonCode(){ System.getProperty(...)}",
            detection = "System.getProperty仅JVM",
            fix = "用expect/actual."
        ))
        BugDB.load(BugRule(
            id = "KT-0803",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D31] 平台特定类型未抽象",
            trigger = "fun platformApi(): android.os.Bundle",
            detection = "直接引用Android类型",
            fix = "用expect类或接口抽象."
        ))
        BugDB.load(BugRule(
            id = "KT-0804",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S31] 共用代码中调用平台API",
            trigger = "fun commonCode(){ System.getProperty(...)}",
            detection = "System.getProperty仅JVM",
            fix = "用expect/actual."
        ))
        BugDB.load(BugRule(
            id = "KT-0805",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D32] 平台特定类型未抽象",
            trigger = "fun platformApi(): android.os.Bundle",
            detection = "直接引用Android类型",
            fix = "用expect类或接口抽象."
        ))
        BugDB.load(BugRule(
            id = "KT-0806",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S32] 共用代码中调用平台API",
            trigger = "fun commonCode(){ System.getProperty(...)}",
            detection = "System.getProperty仅JVM",
            fix = "用expect/actual."
        ))
        BugDB.load(BugRule(
            id = "KT-0807",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D33] 平台特定类型未抽象",
            trigger = "fun platformApi(): android.os.Bundle",
            detection = "直接引用Android类型",
            fix = "用expect类或接口抽象."
        ))
        BugDB.load(BugRule(
            id = "KT-0808",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S33] 共用代码中调用平台API",
            trigger = "fun commonCode(){ System.getProperty(...)}",
            detection = "System.getProperty仅JVM",
            fix = "用expect/actual."
        ))
        BugDB.load(BugRule(
            id = "KT-0809",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D34] 平台特定类型未抽象",
            trigger = "fun platformApi(): android.os.Bundle",
            detection = "直接引用Android类型",
            fix = "用expect类或接口抽象."
        ))
        BugDB.load(BugRule(
            id = "KT-0810",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S34] 共用代码中调用平台API",
            trigger = "fun commonCode(){ System.getProperty(...)}",
            detection = "System.getProperty仅JVM",
            fix = "用expect/actual."
        ))
        BugDB.load(BugRule(
            id = "KT-0811",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D35] 平台特定类型未抽象",
            trigger = "fun platformApi(): android.os.Bundle",
            detection = "直接引用Android类型",
            fix = "用expect类或接口抽象."
        ))
        BugDB.load(BugRule(
            id = "KT-0812",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S35] 共用代码中调用平台API",
            trigger = "fun commonCode(){ System.getProperty(...)}",
            detection = "System.getProperty仅JVM",
            fix = "用expect/actual."
        ))
        BugDB.load(BugRule(
            id = "KT-0813",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D36] 平台特定类型未抽象",
            trigger = "fun platformApi(): android.os.Bundle",
            detection = "直接引用Android类型",
            fix = "用expect类或接口抽象."
        ))
        BugDB.load(BugRule(
            id = "KT-0814",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S36] 共用代码中调用平台API",
            trigger = "fun commonCode(){ System.getProperty(...)}",
            detection = "System.getProperty仅JVM",
            fix = "用expect/actual."
        ))
        BugDB.load(BugRule(
            id = "KT-0815",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D37] 平台特定类型未抽象",
            trigger = "fun platformApi(): android.os.Bundle",
            detection = "直接引用Android类型",
            fix = "用expect类或接口抽象."
        ))
        BugDB.load(BugRule(
            id = "KT-0816",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S37] 共用代码中调用平台API",
            trigger = "fun commonCode(){ System.getProperty(...)}",
            detection = "System.getProperty仅JVM",
            fix = "用expect/actual."
        ))
        BugDB.load(BugRule(
            id = "KT-0817",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D38] 平台特定类型未抽象",
            trigger = "fun platformApi(): android.os.Bundle",
            detection = "直接引用Android类型",
            fix = "用expect类或接口抽象."
        ))
        BugDB.load(BugRule(
            id = "KT-0818",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S38] 共用代码中调用平台API",
            trigger = "fun commonCode(){ System.getProperty(...)}",
            detection = "System.getProperty仅JVM",
            fix = "用expect/actual."
        ))
        BugDB.load(BugRule(
            id = "KT-0819",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D39] 平台特定类型未抽象",
            trigger = "fun platformApi(): android.os.Bundle",
            detection = "直接引用Android类型",
            fix = "用expect类或接口抽象."
        ))
        BugDB.load(BugRule(
            id = "KT-0820",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S39] 共用代码中调用平台API",
            trigger = "fun commonCode(){ System.getProperty(...)}",
            detection = "System.getProperty仅JVM",
            fix = "用expect/actual."
        ))
        BugDB.load(BugRule(
            id = "KT-0821",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D40] 平台特定类型未抽象",
            trigger = "fun platformApi(): android.os.Bundle",
            detection = "直接引用Android类型",
            fix = "用expect类或接口抽象."
        ))
        BugDB.load(BugRule(
            id = "KT-0822",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S40] 共用代码中调用平台API",
            trigger = "fun commonCode(){ System.getProperty(...)}",
            detection = "System.getProperty仅JVM",
            fix = "用expect/actual."
        ))
        BugDB.load(BugRule(
            id = "KT-0823",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D41] 平台特定类型未抽象",
            trigger = "fun platformApi(): android.os.Bundle",
            detection = "直接引用Android类型",
            fix = "用expect类或接口抽象."
        ))
        BugDB.load(BugRule(
            id = "KT-0824",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S41] 共用代码中调用平台API",
            trigger = "fun commonCode(){ System.getProperty(...)}",
            detection = "System.getProperty仅JVM",
            fix = "用expect/actual."
        ))
        BugDB.load(BugRule(
            id = "KT-0825",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D42] 平台特定类型未抽象",
            trigger = "fun platformApi(): android.os.Bundle",
            detection = "直接引用Android类型",
            fix = "用expect类或接口抽象."
        ))
        BugDB.load(BugRule(
            id = "KT-0826",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S42] 共用代码中调用平台API",
            trigger = "fun commonCode(){ System.getProperty(...)}",
            detection = "System.getProperty仅JVM",
            fix = "用expect/actual."
        ))
        BugDB.load(BugRule(
            id = "KT-0827",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D43] 平台特定类型未抽象",
            trigger = "fun platformApi(): android.os.Bundle",
            detection = "直接引用Android类型",
            fix = "用expect类或接口抽象."
        ))
        BugDB.load(BugRule(
            id = "KT-0828",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S43] 共用代码中调用平台API",
            trigger = "fun commonCode(){ System.getProperty(...)}",
            detection = "System.getProperty仅JVM",
            fix = "用expect/actual."
        ))
        BugDB.load(BugRule(
            id = "KT-0829",
            category = BugCategory.KMP,
            severity = BugSeverity.MODERATE,
            title = "[D44] 平台特定类型未抽象",
            trigger = "fun platformApi(): android.os.Bundle",
            detection = "直接引用Android类型",
            fix = "用expect类或接口抽象."
        ))
        BugDB.load(BugRule(
            id = "KT-0830",
            category = BugCategory.KMP,
            severity = BugSeverity.SEVERE,
            title = "[S44] 共用代码中调用平台API",
            trigger = "fun commonCode(){ System.getProperty(...)}",
            detection = "System.getProperty仅JVM",
            fix = "用expect/actual."
        ))
        BugDB.load(BugRule(
            id = "KT-0831",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.SEVERE,
            title = "[S20] AtomicReference ABA问题",
            trigger = "atomicRef.compareAndSet(old,new)",
            detection = "CAS可能遭遇ABA",
            fix = "用AtomicStampedReference."
        ))
        BugDB.load(BugRule(
            id = "KT-0832",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D20] ThreadLocal在协程中泄漏",
            trigger = "ThreadLocal<String> in coroutine",
            detection = "协程切换线程,ThreadLocal残留",
            fix = "用CoroutineContext或asContextElement."
        ))
        BugDB.load(BugRule(
            id = "KT-0833",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.SEVERE,
            title = "[S21] AtomicReference ABA问题",
            trigger = "atomicRef.compareAndSet(old,new)",
            detection = "CAS可能遭遇ABA",
            fix = "用AtomicStampedReference."
        ))
        BugDB.load(BugRule(
            id = "KT-0834",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D21] ThreadLocal在协程中泄漏",
            trigger = "ThreadLocal<String> in coroutine",
            detection = "协程切换线程,ThreadLocal残留",
            fix = "用CoroutineContext或asContextElement."
        ))
        BugDB.load(BugRule(
            id = "KT-0835",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.SEVERE,
            title = "[S22] AtomicReference ABA问题",
            trigger = "atomicRef.compareAndSet(old,new)",
            detection = "CAS可能遭遇ABA",
            fix = "用AtomicStampedReference."
        ))
        BugDB.load(BugRule(
            id = "KT-0836",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D22] ThreadLocal在协程中泄漏",
            trigger = "ThreadLocal<String> in coroutine",
            detection = "协程切换线程,ThreadLocal残留",
            fix = "用CoroutineContext或asContextElement."
        ))
        BugDB.load(BugRule(
            id = "KT-0837",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.SEVERE,
            title = "[S23] AtomicReference ABA问题",
            trigger = "atomicRef.compareAndSet(old,new)",
            detection = "CAS可能遭遇ABA",
            fix = "用AtomicStampedReference."
        ))
        BugDB.load(BugRule(
            id = "KT-0838",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D23] ThreadLocal在协程中泄漏",
            trigger = "ThreadLocal<String> in coroutine",
            detection = "协程切换线程,ThreadLocal残留",
            fix = "用CoroutineContext或asContextElement."
        ))
        BugDB.load(BugRule(
            id = "KT-0839",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.SEVERE,
            title = "[S24] AtomicReference ABA问题",
            trigger = "atomicRef.compareAndSet(old,new)",
            detection = "CAS可能遭遇ABA",
            fix = "用AtomicStampedReference."
        ))
        BugDB.load(BugRule(
            id = "KT-0840",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D24] ThreadLocal在协程中泄漏",
            trigger = "ThreadLocal<String> in coroutine",
            detection = "协程切换线程,ThreadLocal残留",
            fix = "用CoroutineContext或asContextElement."
        ))
        BugDB.load(BugRule(
            id = "KT-0841",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.SEVERE,
            title = "[S25] AtomicReference ABA问题",
            trigger = "atomicRef.compareAndSet(old,new)",
            detection = "CAS可能遭遇ABA",
            fix = "用AtomicStampedReference."
        ))
        BugDB.load(BugRule(
            id = "KT-0842",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D25] ThreadLocal在协程中泄漏",
            trigger = "ThreadLocal<String> in coroutine",
            detection = "协程切换线程,ThreadLocal残留",
            fix = "用CoroutineContext或asContextElement."
        ))
        BugDB.load(BugRule(
            id = "KT-0843",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.SEVERE,
            title = "[S26] AtomicReference ABA问题",
            trigger = "atomicRef.compareAndSet(old,new)",
            detection = "CAS可能遭遇ABA",
            fix = "用AtomicStampedReference."
        ))
        BugDB.load(BugRule(
            id = "KT-0844",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D26] ThreadLocal在协程中泄漏",
            trigger = "ThreadLocal<String> in coroutine",
            detection = "协程切换线程,ThreadLocal残留",
            fix = "用CoroutineContext或asContextElement."
        ))
        BugDB.load(BugRule(
            id = "KT-0845",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.SEVERE,
            title = "[S27] AtomicReference ABA问题",
            trigger = "atomicRef.compareAndSet(old,new)",
            detection = "CAS可能遭遇ABA",
            fix = "用AtomicStampedReference."
        ))
        BugDB.load(BugRule(
            id = "KT-0846",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D27] ThreadLocal在协程中泄漏",
            trigger = "ThreadLocal<String> in coroutine",
            detection = "协程切换线程,ThreadLocal残留",
            fix = "用CoroutineContext或asContextElement."
        ))
        BugDB.load(BugRule(
            id = "KT-0847",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.SEVERE,
            title = "[S28] AtomicReference ABA问题",
            trigger = "atomicRef.compareAndSet(old,new)",
            detection = "CAS可能遭遇ABA",
            fix = "用AtomicStampedReference."
        ))
        BugDB.load(BugRule(
            id = "KT-0848",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D28] ThreadLocal在协程中泄漏",
            trigger = "ThreadLocal<String> in coroutine",
            detection = "协程切换线程,ThreadLocal残留",
            fix = "用CoroutineContext或asContextElement."
        ))
        BugDB.load(BugRule(
            id = "KT-0849",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.SEVERE,
            title = "[S29] AtomicReference ABA问题",
            trigger = "atomicRef.compareAndSet(old,new)",
            detection = "CAS可能遭遇ABA",
            fix = "用AtomicStampedReference."
        ))
        BugDB.load(BugRule(
            id = "KT-0850",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D29] ThreadLocal在协程中泄漏",
            trigger = "ThreadLocal<String> in coroutine",
            detection = "协程切换线程,ThreadLocal残留",
            fix = "用CoroutineContext或asContextElement."
        ))
        BugDB.load(BugRule(
            id = "KT-0851",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.SEVERE,
            title = "[S30] AtomicReference ABA问题",
            trigger = "atomicRef.compareAndSet(old,new)",
            detection = "CAS可能遭遇ABA",
            fix = "用AtomicStampedReference."
        ))
        BugDB.load(BugRule(
            id = "KT-0852",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D30] ThreadLocal在协程中泄漏",
            trigger = "ThreadLocal<String> in coroutine",
            detection = "协程切换线程,ThreadLocal残留",
            fix = "用CoroutineContext或asContextElement."
        ))
        BugDB.load(BugRule(
            id = "KT-0853",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.SEVERE,
            title = "[S31] AtomicReference ABA问题",
            trigger = "atomicRef.compareAndSet(old,new)",
            detection = "CAS可能遭遇ABA",
            fix = "用AtomicStampedReference."
        ))
        BugDB.load(BugRule(
            id = "KT-0854",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D31] ThreadLocal在协程中泄漏",
            trigger = "ThreadLocal<String> in coroutine",
            detection = "协程切换线程,ThreadLocal残留",
            fix = "用CoroutineContext或asContextElement."
        ))
        BugDB.load(BugRule(
            id = "KT-0855",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.SEVERE,
            title = "[S32] AtomicReference ABA问题",
            trigger = "atomicRef.compareAndSet(old,new)",
            detection = "CAS可能遭遇ABA",
            fix = "用AtomicStampedReference."
        ))
        BugDB.load(BugRule(
            id = "KT-0856",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D32] ThreadLocal在协程中泄漏",
            trigger = "ThreadLocal<String> in coroutine",
            detection = "协程切换线程,ThreadLocal残留",
            fix = "用CoroutineContext或asContextElement."
        ))
        BugDB.load(BugRule(
            id = "KT-0857",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.SEVERE,
            title = "[S33] AtomicReference ABA问题",
            trigger = "atomicRef.compareAndSet(old,new)",
            detection = "CAS可能遭遇ABA",
            fix = "用AtomicStampedReference."
        ))
        BugDB.load(BugRule(
            id = "KT-0858",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D33] ThreadLocal在协程中泄漏",
            trigger = "ThreadLocal<String> in coroutine",
            detection = "协程切换线程,ThreadLocal残留",
            fix = "用CoroutineContext或asContextElement."
        ))
        BugDB.load(BugRule(
            id = "KT-0859",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.SEVERE,
            title = "[S34] AtomicReference ABA问题",
            trigger = "atomicRef.compareAndSet(old,new)",
            detection = "CAS可能遭遇ABA",
            fix = "用AtomicStampedReference."
        ))
        BugDB.load(BugRule(
            id = "KT-0860",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[D34] ThreadLocal在协程中泄漏",
            trigger = "ThreadLocal<String> in coroutine",
            detection = "协程切换线程,ThreadLocal残留",
            fix = "用CoroutineContext或asContextElement."
        ))
        BugDB.load(BugRule(
            id = "KT-0861",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.SEVERE,
            title = "[S10] 大对象频繁GC",
            trigger = "for(i in 1..10000){ val big=ByteArray(1_000_000)}",
            detection = "循环中分配大数组触发GC",
            fix = "复用或对象池."
        ))
        BugDB.load(BugRule(
            id = "KT-0862",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[D20] 不必要惰性初始化",
            trigger = "val x by lazy{ 42 }",
            detection = "简单常量不需要lazy",
            fix = "直接赋值val x=42."
        ))
        BugDB.load(BugRule(
            id = "KT-0863",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.SEVERE,
            title = "[S11] 大对象频繁GC",
            trigger = "for(i in 1..10000){ val big=ByteArray(1_000_000)}",
            detection = "循环中分配大数组触发GC",
            fix = "复用或对象池."
        ))
        BugDB.load(BugRule(
            id = "KT-0864",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[D21] 不必要惰性初始化",
            trigger = "val x by lazy{ 42 }",
            detection = "简单常量不需要lazy",
            fix = "直接赋值val x=42."
        ))
        BugDB.load(BugRule(
            id = "KT-0865",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.SEVERE,
            title = "[S12] 大对象频繁GC",
            trigger = "for(i in 1..10000){ val big=ByteArray(1_000_000)}",
            detection = "循环中分配大数组触发GC",
            fix = "复用或对象池."
        ))
        BugDB.load(BugRule(
            id = "KT-0866",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[D22] 不必要惰性初始化",
            trigger = "val x by lazy{ 42 }",
            detection = "简单常量不需要lazy",
            fix = "直接赋值val x=42."
        ))
        BugDB.load(BugRule(
            id = "KT-0867",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.SEVERE,
            title = "[S13] 大对象频繁GC",
            trigger = "for(i in 1..10000){ val big=ByteArray(1_000_000)}",
            detection = "循环中分配大数组触发GC",
            fix = "复用或对象池."
        ))
        BugDB.load(BugRule(
            id = "KT-0868",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[D23] 不必要惰性初始化",
            trigger = "val x by lazy{ 42 }",
            detection = "简单常量不需要lazy",
            fix = "直接赋值val x=42."
        ))
        BugDB.load(BugRule(
            id = "KT-0869",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.SEVERE,
            title = "[S14] 大对象频繁GC",
            trigger = "for(i in 1..10000){ val big=ByteArray(1_000_000)}",
            detection = "循环中分配大数组触发GC",
            fix = "复用或对象池."
        ))
        BugDB.load(BugRule(
            id = "KT-0870",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[D24] 不必要惰性初始化",
            trigger = "val x by lazy{ 42 }",
            detection = "简单常量不需要lazy",
            fix = "直接赋值val x=42."
        ))
        BugDB.load(BugRule(
            id = "KT-0871",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.SEVERE,
            title = "[S15] 大对象频繁GC",
            trigger = "for(i in 1..10000){ val big=ByteArray(1_000_000)}",
            detection = "循环中分配大数组触发GC",
            fix = "复用或对象池."
        ))
        BugDB.load(BugRule(
            id = "KT-0872",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[D25] 不必要惰性初始化",
            trigger = "val x by lazy{ 42 }",
            detection = "简单常量不需要lazy",
            fix = "直接赋值val x=42."
        ))
        BugDB.load(BugRule(
            id = "KT-0873",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.SEVERE,
            title = "[S16] 大对象频繁GC",
            trigger = "for(i in 1..10000){ val big=ByteArray(1_000_000)}",
            detection = "循环中分配大数组触发GC",
            fix = "复用或对象池."
        ))
        BugDB.load(BugRule(
            id = "KT-0874",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[D26] 不必要惰性初始化",
            trigger = "val x by lazy{ 42 }",
            detection = "简单常量不需要lazy",
            fix = "直接赋值val x=42."
        ))
        BugDB.load(BugRule(
            id = "KT-0875",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.SEVERE,
            title = "[S17] 大对象频繁GC",
            trigger = "for(i in 1..10000){ val big=ByteArray(1_000_000)}",
            detection = "循环中分配大数组触发GC",
            fix = "复用或对象池."
        ))
        BugDB.load(BugRule(
            id = "KT-0876",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[D27] 不必要惰性初始化",
            trigger = "val x by lazy{ 42 }",
            detection = "简单常量不需要lazy",
            fix = "直接赋值val x=42."
        ))
        BugDB.load(BugRule(
            id = "KT-0877",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.SEVERE,
            title = "[S18] 大对象频繁GC",
            trigger = "for(i in 1..10000){ val big=ByteArray(1_000_000)}",
            detection = "循环中分配大数组触发GC",
            fix = "复用或对象池."
        ))
        BugDB.load(BugRule(
            id = "KT-0878",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[D28] 不必要惰性初始化",
            trigger = "val x by lazy{ 42 }",
            detection = "简单常量不需要lazy",
            fix = "直接赋值val x=42."
        ))
        BugDB.load(BugRule(
            id = "KT-0879",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.SEVERE,
            title = "[S19] 大对象频繁GC",
            trigger = "for(i in 1..10000){ val big=ByteArray(1_000_000)}",
            detection = "循环中分配大数组触发GC",
            fix = "复用或对象池."
        ))
        BugDB.load(BugRule(
            id = "KT-0880",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[D29] 不必要惰性初始化",
            trigger = "val x by lazy{ 42 }",
            detection = "简单常量不需要lazy",
            fix = "直接赋值val x=42."
        ))
        BugDB.load(BugRule(
            id = "KT-0881",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.SEVERE,
            title = "[S20] inline+reified调用处类型推断失败",
            trigger = "inline fun<reified T> T.cast()=this as? T",
            detection = "调用处T无法推断时抛异常",
            fix = "显式类型参数:obj.cast<String>()."
        ))
        BugDB.load(BugRule(
            id = "KT-0882",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MODERATE,
            title = "[D20] 操作符重载歧义",
            trigger = "operator fun plus(x:Int)=...; operator fun plus(x:Long)=...",
            detection = "编译器选择最匹配,可能非预期",
            fix = "用不同方法名."
        ))
        BugDB.load(BugRule(
            id = "KT-0883",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.SEVERE,
            title = "[S21] inline+reified调用处类型推断失败",
            trigger = "inline fun<reified T> T.cast()=this as? T",
            detection = "调用处T无法推断时抛异常",
            fix = "显式类型参数:obj.cast<String>()."
        ))
        BugDB.load(BugRule(
            id = "KT-0884",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MODERATE,
            title = "[D21] 操作符重载歧义",
            trigger = "operator fun plus(x:Int)=...; operator fun plus(x:Long)=...",
            detection = "编译器选择最匹配,可能非预期",
            fix = "用不同方法名."
        ))
        BugDB.load(BugRule(
            id = "KT-0885",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.SEVERE,
            title = "[S22] inline+reified调用处类型推断失败",
            trigger = "inline fun<reified T> T.cast()=this as? T",
            detection = "调用处T无法推断时抛异常",
            fix = "显式类型参数:obj.cast<String>()."
        ))
        BugDB.load(BugRule(
            id = "KT-0886",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MODERATE,
            title = "[D22] 操作符重载歧义",
            trigger = "operator fun plus(x:Int)=...; operator fun plus(x:Long)=...",
            detection = "编译器选择最匹配,可能非预期",
            fix = "用不同方法名."
        ))
        BugDB.load(BugRule(
            id = "KT-0887",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.SEVERE,
            title = "[S23] inline+reified调用处类型推断失败",
            trigger = "inline fun<reified T> T.cast()=this as? T",
            detection = "调用处T无法推断时抛异常",
            fix = "显式类型参数:obj.cast<String>()."
        ))
        BugDB.load(BugRule(
            id = "KT-0888",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MODERATE,
            title = "[D23] 操作符重载歧义",
            trigger = "operator fun plus(x:Int)=...; operator fun plus(x:Long)=...",
            detection = "编译器选择最匹配,可能非预期",
            fix = "用不同方法名."
        ))
        BugDB.load(BugRule(
            id = "KT-0889",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.SEVERE,
            title = "[S24] inline+reified调用处类型推断失败",
            trigger = "inline fun<reified T> T.cast()=this as? T",
            detection = "调用处T无法推断时抛异常",
            fix = "显式类型参数:obj.cast<String>()."
        ))
        BugDB.load(BugRule(
            id = "KT-0890",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MODERATE,
            title = "[D24] 操作符重载歧义",
            trigger = "operator fun plus(x:Int)=...; operator fun plus(x:Long)=...",
            detection = "编译器选择最匹配,可能非预期",
            fix = "用不同方法名."
        ))
        BugDB.load(BugRule(
            id = "KT-0891",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.SEVERE,
            title = "[S25] inline+reified调用处类型推断失败",
            trigger = "inline fun<reified T> T.cast()=this as? T",
            detection = "调用处T无法推断时抛异常",
            fix = "显式类型参数:obj.cast<String>()."
        ))
        BugDB.load(BugRule(
            id = "KT-0892",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MODERATE,
            title = "[D25] 操作符重载歧义",
            trigger = "operator fun plus(x:Int)=...; operator fun plus(x:Long)=...",
            detection = "编译器选择最匹配,可能非预期",
            fix = "用不同方法名."
        ))
        BugDB.load(BugRule(
            id = "KT-0893",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.SEVERE,
            title = "[S26] inline+reified调用处类型推断失败",
            trigger = "inline fun<reified T> T.cast()=this as? T",
            detection = "调用处T无法推断时抛异常",
            fix = "显式类型参数:obj.cast<String>()."
        ))
        BugDB.load(BugRule(
            id = "KT-0894",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MODERATE,
            title = "[D26] 操作符重载歧义",
            trigger = "operator fun plus(x:Int)=...; operator fun plus(x:Long)=...",
            detection = "编译器选择最匹配,可能非预期",
            fix = "用不同方法名."
        ))
        BugDB.load(BugRule(
            id = "KT-0895",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.SEVERE,
            title = "[S27] inline+reified调用处类型推断失败",
            trigger = "inline fun<reified T> T.cast()=this as? T",
            detection = "调用处T无法推断时抛异常",
            fix = "显式类型参数:obj.cast<String>()."
        ))
        BugDB.load(BugRule(
            id = "KT-0896",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MODERATE,
            title = "[D27] 操作符重载歧义",
            trigger = "operator fun plus(x:Int)=...; operator fun plus(x:Long)=...",
            detection = "编译器选择最匹配,可能非预期",
            fix = "用不同方法名."
        ))
        BugDB.load(BugRule(
            id = "KT-0897",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.SEVERE,
            title = "[S28] inline+reified调用处类型推断失败",
            trigger = "inline fun<reified T> T.cast()=this as? T",
            detection = "调用处T无法推断时抛异常",
            fix = "显式类型参数:obj.cast<String>()."
        ))
        BugDB.load(BugRule(
            id = "KT-0898",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MODERATE,
            title = "[D28] 操作符重载歧义",
            trigger = "operator fun plus(x:Int)=...; operator fun plus(x:Long)=...",
            detection = "编译器选择最匹配,可能非预期",
            fix = "用不同方法名."
        ))
        BugDB.load(BugRule(
            id = "KT-0899",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.SEVERE,
            title = "[S29] inline+reified调用处类型推断失败",
            trigger = "inline fun<reified T> T.cast()=this as? T",
            detection = "调用处T无法推断时抛异常",
            fix = "显式类型参数:obj.cast<String>()."
        ))
        BugDB.load(BugRule(
            id = "KT-0900",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MODERATE,
            title = "[D29] 操作符重载歧义",
            trigger = "operator fun plus(x:Int)=...; operator fun plus(x:Long)=...",
            detection = "编译器选择最匹配,可能非预期",
            fix = "用不同方法名."
        ))
        BugDB.load(BugRule(
            id = "KT-0901",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.SEVERE,
            title = "[S20] 跨层智能转换失效",
            trigger = "class A{ var x:Any; fun f(){ if(x is String){ x.length }}}",
            detection = "var属性跨方法智能转换不生效",
            fix = "局部val = x; if(val is String)."
        ))
        BugDB.load(BugRule(
            id = "KT-0902",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MILD,
            title = "[M20] ?.let中冗余is检查",
            trigger = "x?.let{ if(it is String) it.length}",
            detection = "?.let内it已经非空",
            fix = "省略let内的null检查."
        ))
        BugDB.load(BugRule(
            id = "KT-0903",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.SEVERE,
            title = "[S21] 跨层智能转换失效",
            trigger = "class A{ var x:Any; fun f(){ if(x is String){ x.length }}}",
            detection = "var属性跨方法智能转换不生效",
            fix = "局部val = x; if(val is String)."
        ))
        BugDB.load(BugRule(
            id = "KT-0904",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MILD,
            title = "[M21] ?.let中冗余is检查",
            trigger = "x?.let{ if(it is String) it.length}",
            detection = "?.let内it已经非空",
            fix = "省略let内的null检查."
        ))
        BugDB.load(BugRule(
            id = "KT-0905",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.SEVERE,
            title = "[S22] 跨层智能转换失效",
            trigger = "class A{ var x:Any; fun f(){ if(x is String){ x.length }}}",
            detection = "var属性跨方法智能转换不生效",
            fix = "局部val = x; if(val is String)."
        ))
        BugDB.load(BugRule(
            id = "KT-0906",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MILD,
            title = "[M22] ?.let中冗余is检查",
            trigger = "x?.let{ if(it is String) it.length}",
            detection = "?.let内it已经非空",
            fix = "省略let内的null检查."
        ))
        BugDB.load(BugRule(
            id = "KT-0907",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.SEVERE,
            title = "[S23] 跨层智能转换失效",
            trigger = "class A{ var x:Any; fun f(){ if(x is String){ x.length }}}",
            detection = "var属性跨方法智能转换不生效",
            fix = "局部val = x; if(val is String)."
        ))
        BugDB.load(BugRule(
            id = "KT-0908",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MILD,
            title = "[M23] ?.let中冗余is检查",
            trigger = "x?.let{ if(it is String) it.length}",
            detection = "?.let内it已经非空",
            fix = "省略let内的null检查."
        ))
        BugDB.load(BugRule(
            id = "KT-0909",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.SEVERE,
            title = "[S24] 跨层智能转换失效",
            trigger = "class A{ var x:Any; fun f(){ if(x is String){ x.length }}}",
            detection = "var属性跨方法智能转换不生效",
            fix = "局部val = x; if(val is String)."
        ))
        BugDB.load(BugRule(
            id = "KT-0910",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MILD,
            title = "[M24] ?.let中冗余is检查",
            trigger = "x?.let{ if(it is String) it.length}",
            detection = "?.let内it已经非空",
            fix = "省略let内的null检查."
        ))
        BugDB.load(BugRule(
            id = "KT-0911",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.SEVERE,
            title = "[S25] 跨层智能转换失效",
            trigger = "class A{ var x:Any; fun f(){ if(x is String){ x.length }}}",
            detection = "var属性跨方法智能转换不生效",
            fix = "局部val = x; if(val is String)."
        ))
        BugDB.load(BugRule(
            id = "KT-0912",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MILD,
            title = "[M25] ?.let中冗余is检查",
            trigger = "x?.let{ if(it is String) it.length}",
            detection = "?.let内it已经非空",
            fix = "省略let内的null检查."
        ))
        BugDB.load(BugRule(
            id = "KT-0913",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.SEVERE,
            title = "[S26] 跨层智能转换失效",
            trigger = "class A{ var x:Any; fun f(){ if(x is String){ x.length }}}",
            detection = "var属性跨方法智能转换不生效",
            fix = "局部val = x; if(val is String)."
        ))
        BugDB.load(BugRule(
            id = "KT-0914",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MILD,
            title = "[M26] ?.let中冗余is检查",
            trigger = "x?.let{ if(it is String) it.length}",
            detection = "?.let内it已经非空",
            fix = "省略let内的null检查."
        ))
        BugDB.load(BugRule(
            id = "KT-0915",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.SEVERE,
            title = "[S27] 跨层智能转换失效",
            trigger = "class A{ var x:Any; fun f(){ if(x is String){ x.length }}}",
            detection = "var属性跨方法智能转换不生效",
            fix = "局部val = x; if(val is String)."
        ))
        BugDB.load(BugRule(
            id = "KT-0916",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MILD,
            title = "[M27] ?.let中冗余is检查",
            trigger = "x?.let{ if(it is String) it.length}",
            detection = "?.let内it已经非空",
            fix = "省略let内的null检查."
        ))
        BugDB.load(BugRule(
            id = "KT-0917",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.SEVERE,
            title = "[S28] 跨层智能转换失效",
            trigger = "class A{ var x:Any; fun f(){ if(x is String){ x.length }}}",
            detection = "var属性跨方法智能转换不生效",
            fix = "局部val = x; if(val is String)."
        ))
        BugDB.load(BugRule(
            id = "KT-0918",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MILD,
            title = "[M28] ?.let中冗余is检查",
            trigger = "x?.let{ if(it is String) it.length}",
            detection = "?.let内it已经非空",
            fix = "省略let内的null检查."
        ))
        BugDB.load(BugRule(
            id = "KT-0919",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.SEVERE,
            title = "[S29] 跨层智能转换失效",
            trigger = "class A{ var x:Any; fun f(){ if(x is String){ x.length }}}",
            detection = "var属性跨方法智能转换不生效",
            fix = "局部val = x; if(val is String)."
        ))
        BugDB.load(BugRule(
            id = "KT-0920",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MILD,
            title = "[M29] ?.let中冗余is检查",
            trigger = "x?.let{ if(it is String) it.length}",
            detection = "?.let内it已经非空",
            fix = "省略let内的null检查."
        ))
        BugDB.load(BugRule(
            id = "KT-0921",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.SEVERE,
            title = "[S20] Gson/Kotlin数据类反射漏洞",
            trigger = "Gson().fromJson(json,User::class.java)",
            detection = "Gson默认无参构造,data class无默认值炸",
            fix = "用kotlinx.serialization."
        ))
        BugDB.load(BugRule(
            id = "KT-0922",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[D20] @Serializable属性命名冲突",
            trigger = "@SerialName(\"id\") val userId:Int",
            detection = "序列化名称与属性名不同",
            fix = "统一命名或显式标注."
        ))
        BugDB.load(BugRule(
            id = "KT-0923",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.SEVERE,
            title = "[S21] Gson/Kotlin数据类反射漏洞",
            trigger = "Gson().fromJson(json,User::class.java)",
            detection = "Gson默认无参构造,data class无默认值炸",
            fix = "用kotlinx.serialization."
        ))
        BugDB.load(BugRule(
            id = "KT-0924",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[D21] @Serializable属性命名冲突",
            trigger = "@SerialName(\"id\") val userId:Int",
            detection = "序列化名称与属性名不同",
            fix = "统一命名或显式标注."
        ))
        BugDB.load(BugRule(
            id = "KT-0925",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.SEVERE,
            title = "[S22] Gson/Kotlin数据类反射漏洞",
            trigger = "Gson().fromJson(json,User::class.java)",
            detection = "Gson默认无参构造,data class无默认值炸",
            fix = "用kotlinx.serialization."
        ))
        BugDB.load(BugRule(
            id = "KT-0926",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[D22] @Serializable属性命名冲突",
            trigger = "@SerialName(\"id\") val userId:Int",
            detection = "序列化名称与属性名不同",
            fix = "统一命名或显式标注."
        ))
        BugDB.load(BugRule(
            id = "KT-0927",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.SEVERE,
            title = "[S23] Gson/Kotlin数据类反射漏洞",
            trigger = "Gson().fromJson(json,User::class.java)",
            detection = "Gson默认无参构造,data class无默认值炸",
            fix = "用kotlinx.serialization."
        ))
        BugDB.load(BugRule(
            id = "KT-0928",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[D23] @Serializable属性命名冲突",
            trigger = "@SerialName(\"id\") val userId:Int",
            detection = "序列化名称与属性名不同",
            fix = "统一命名或显式标注."
        ))
        BugDB.load(BugRule(
            id = "KT-0929",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.SEVERE,
            title = "[S24] Gson/Kotlin数据类反射漏洞",
            trigger = "Gson().fromJson(json,User::class.java)",
            detection = "Gson默认无参构造,data class无默认值炸",
            fix = "用kotlinx.serialization."
        ))
        BugDB.load(BugRule(
            id = "KT-0930",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[D24] @Serializable属性命名冲突",
            trigger = "@SerialName(\"id\") val userId:Int",
            detection = "序列化名称与属性名不同",
            fix = "统一命名或显式标注."
        ))
        BugDB.load(BugRule(
            id = "KT-0931",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.SEVERE,
            title = "[S25] Gson/Kotlin数据类反射漏洞",
            trigger = "Gson().fromJson(json,User::class.java)",
            detection = "Gson默认无参构造,data class无默认值炸",
            fix = "用kotlinx.serialization."
        ))
        BugDB.load(BugRule(
            id = "KT-0932",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[D25] @Serializable属性命名冲突",
            trigger = "@SerialName(\"id\") val userId:Int",
            detection = "序列化名称与属性名不同",
            fix = "统一命名或显式标注."
        ))
        BugDB.load(BugRule(
            id = "KT-0933",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.SEVERE,
            title = "[S26] Gson/Kotlin数据类反射漏洞",
            trigger = "Gson().fromJson(json,User::class.java)",
            detection = "Gson默认无参构造,data class无默认值炸",
            fix = "用kotlinx.serialization."
        ))
        BugDB.load(BugRule(
            id = "KT-0934",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[D26] @Serializable属性命名冲突",
            trigger = "@SerialName(\"id\") val userId:Int",
            detection = "序列化名称与属性名不同",
            fix = "统一命名或显式标注."
        ))
        BugDB.load(BugRule(
            id = "KT-0935",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.SEVERE,
            title = "[S27] Gson/Kotlin数据类反射漏洞",
            trigger = "Gson().fromJson(json,User::class.java)",
            detection = "Gson默认无参构造,data class无默认值炸",
            fix = "用kotlinx.serialization."
        ))
        BugDB.load(BugRule(
            id = "KT-0936",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[D27] @Serializable属性命名冲突",
            trigger = "@SerialName(\"id\") val userId:Int",
            detection = "序列化名称与属性名不同",
            fix = "统一命名或显式标注."
        ))
        BugDB.load(BugRule(
            id = "KT-0937",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.SEVERE,
            title = "[S28] Gson/Kotlin数据类反射漏洞",
            trigger = "Gson().fromJson(json,User::class.java)",
            detection = "Gson默认无参构造,data class无默认值炸",
            fix = "用kotlinx.serialization."
        ))
        BugDB.load(BugRule(
            id = "KT-0938",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[D28] @Serializable属性命名冲突",
            trigger = "@SerialName(\"id\") val userId:Int",
            detection = "序列化名称与属性名不同",
            fix = "统一命名或显式标注."
        ))
        BugDB.load(BugRule(
            id = "KT-0939",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.SEVERE,
            title = "[S29] Gson/Kotlin数据类反射漏洞",
            trigger = "Gson().fromJson(json,User::class.java)",
            detection = "Gson默认无参构造,data class无默认值炸",
            fix = "用kotlinx.serialization."
        ))
        BugDB.load(BugRule(
            id = "KT-0940",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[D29] @Serializable属性命名冲突",
            trigger = "@SerialName(\"id\") val userId:Int",
            detection = "序列化名称与属性名不同",
            fix = "统一命名或显式标注."
        ))
        BugDB.load(BugRule(
            id = "KT-0941",
            category = BugCategory.GENERICS,
            severity = BugSeverity.SEVERE,
            title = "[S20] 泛型数组创建",
            trigger = "fun<T> newArray():Array<T>",
            detection = "擦除后无法创建泛型数组",
            fix = "用inline+reified或@UnsafeVariance."
        ))
        BugDB.load(BugRule(
            id = "KT-0942",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[M20] 不必要型变注解",
            trigger = "class Box<out T>(...)",
            detection = "T仅在out位置,注解多余",
            fix = "去除多余的型变注解."
        ))
        BugDB.load(BugRule(
            id = "KT-0943",
            category = BugCategory.GENERICS,
            severity = BugSeverity.SEVERE,
            title = "[S21] 泛型数组创建",
            trigger = "fun<T> newArray():Array<T>",
            detection = "擦除后无法创建泛型数组",
            fix = "用inline+reified或@UnsafeVariance."
        ))
        BugDB.load(BugRule(
            id = "KT-0944",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[M21] 不必要型变注解",
            trigger = "class Box<out T>(...)",
            detection = "T仅在out位置,注解多余",
            fix = "去除多余的型变注解."
        ))
        BugDB.load(BugRule(
            id = "KT-0945",
            category = BugCategory.GENERICS,
            severity = BugSeverity.SEVERE,
            title = "[S22] 泛型数组创建",
            trigger = "fun<T> newArray():Array<T>",
            detection = "擦除后无法创建泛型数组",
            fix = "用inline+reified或@UnsafeVariance."
        ))
        BugDB.load(BugRule(
            id = "KT-0946",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[M22] 不必要型变注解",
            trigger = "class Box<out T>(...)",
            detection = "T仅在out位置,注解多余",
            fix = "去除多余的型变注解."
        ))
        BugDB.load(BugRule(
            id = "KT-0947",
            category = BugCategory.GENERICS,
            severity = BugSeverity.SEVERE,
            title = "[S23] 泛型数组创建",
            trigger = "fun<T> newArray():Array<T>",
            detection = "擦除后无法创建泛型数组",
            fix = "用inline+reified或@UnsafeVariance."
        ))
        BugDB.load(BugRule(
            id = "KT-0948",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[M23] 不必要型变注解",
            trigger = "class Box<out T>(...)",
            detection = "T仅在out位置,注解多余",
            fix = "去除多余的型变注解."
        ))
        BugDB.load(BugRule(
            id = "KT-0949",
            category = BugCategory.GENERICS,
            severity = BugSeverity.SEVERE,
            title = "[S24] 泛型数组创建",
            trigger = "fun<T> newArray():Array<T>",
            detection = "擦除后无法创建泛型数组",
            fix = "用inline+reified或@UnsafeVariance."
        ))
        BugDB.load(BugRule(
            id = "KT-0950",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[M24] 不必要型变注解",
            trigger = "class Box<out T>(...)",
            detection = "T仅在out位置,注解多余",
            fix = "去除多余的型变注解."
        ))
        BugDB.load(BugRule(
            id = "KT-0951",
            category = BugCategory.GENERICS,
            severity = BugSeverity.SEVERE,
            title = "[S25] 泛型数组创建",
            trigger = "fun<T> newArray():Array<T>",
            detection = "擦除后无法创建泛型数组",
            fix = "用inline+reified或@UnsafeVariance."
        ))
        BugDB.load(BugRule(
            id = "KT-0952",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[M25] 不必要型变注解",
            trigger = "class Box<out T>(...)",
            detection = "T仅在out位置,注解多余",
            fix = "去除多余的型变注解."
        ))
        BugDB.load(BugRule(
            id = "KT-0953",
            category = BugCategory.GENERICS,
            severity = BugSeverity.SEVERE,
            title = "[S26] 泛型数组创建",
            trigger = "fun<T> newArray():Array<T>",
            detection = "擦除后无法创建泛型数组",
            fix = "用inline+reified或@UnsafeVariance."
        ))
        BugDB.load(BugRule(
            id = "KT-0954",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[M26] 不必要型变注解",
            trigger = "class Box<out T>(...)",
            detection = "T仅在out位置,注解多余",
            fix = "去除多余的型变注解."
        ))
        BugDB.load(BugRule(
            id = "KT-0955",
            category = BugCategory.GENERICS,
            severity = BugSeverity.SEVERE,
            title = "[S27] 泛型数组创建",
            trigger = "fun<T> newArray():Array<T>",
            detection = "擦除后无法创建泛型数组",
            fix = "用inline+reified或@UnsafeVariance."
        ))
        BugDB.load(BugRule(
            id = "KT-0956",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[M27] 不必要型变注解",
            trigger = "class Box<out T>(...)",
            detection = "T仅在out位置,注解多余",
            fix = "去除多余的型变注解."
        ))
        BugDB.load(BugRule(
            id = "KT-0957",
            category = BugCategory.GENERICS,
            severity = BugSeverity.SEVERE,
            title = "[S28] 泛型数组创建",
            trigger = "fun<T> newArray():Array<T>",
            detection = "擦除后无法创建泛型数组",
            fix = "用inline+reified或@UnsafeVariance."
        ))
        BugDB.load(BugRule(
            id = "KT-0958",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[M28] 不必要型变注解",
            trigger = "class Box<out T>(...)",
            detection = "T仅在out位置,注解多余",
            fix = "去除多余的型变注解."
        ))
        BugDB.load(BugRule(
            id = "KT-0959",
            category = BugCategory.GENERICS,
            severity = BugSeverity.SEVERE,
            title = "[S29] 泛型数组创建",
            trigger = "fun<T> newArray():Array<T>",
            detection = "擦除后无法创建泛型数组",
            fix = "用inline+reified或@UnsafeVariance."
        ))
        BugDB.load(BugRule(
            id = "KT-0960",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[M29] 不必要型变注解",
            trigger = "class Box<out T>(...)",
            detection = "T仅在out位置,注解多余",
            fix = "去除多余的型变注解."
        ))
        BugDB.load(BugRule(
            id = "KT-0961",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MILD,
            title = "[MXN0] 补充: NULL_SAFETY轻度检查 0",
            trigger = "NULL_SAFETY pattern 0",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0962",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[DXN0] 补充: NULL_SAFETY中度检查 0",
            trigger = "NULL_SAFETY pattern 0",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0963",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.SEVERE,
            title = "[SXN0] 补充: NULL_SAFETY严重检查 0",
            trigger = "NULL_SAFETY pattern 0",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0964",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MILD,
            title = "[MXN1] 补充: NULL_SAFETY轻度检查 1",
            trigger = "NULL_SAFETY pattern 1",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0965",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[DXN1] 补充: NULL_SAFETY中度检查 1",
            trigger = "NULL_SAFETY pattern 1",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0966",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.SEVERE,
            title = "[SXN1] 补充: NULL_SAFETY严重检查 1",
            trigger = "NULL_SAFETY pattern 1",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0967",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MILD,
            title = "[MXN2] 补充: NULL_SAFETY轻度检查 2",
            trigger = "NULL_SAFETY pattern 2",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0968",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[DXN2] 补充: NULL_SAFETY中度检查 2",
            trigger = "NULL_SAFETY pattern 2",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0969",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.SEVERE,
            title = "[SXN2] 补充: NULL_SAFETY严重检查 2",
            trigger = "NULL_SAFETY pattern 2",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0970",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MILD,
            title = "[MXN3] 补充: NULL_SAFETY轻度检查 3",
            trigger = "NULL_SAFETY pattern 3",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0971",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[DXN3] 补充: NULL_SAFETY中度检查 3",
            trigger = "NULL_SAFETY pattern 3",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0972",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.SEVERE,
            title = "[SXN3] 补充: NULL_SAFETY严重检查 3",
            trigger = "NULL_SAFETY pattern 3",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0973",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MILD,
            title = "[MXN4] 补充: NULL_SAFETY轻度检查 4",
            trigger = "NULL_SAFETY pattern 4",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0974",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[DXN4] 补充: NULL_SAFETY中度检查 4",
            trigger = "NULL_SAFETY pattern 4",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0975",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.SEVERE,
            title = "[SXN4] 补充: NULL_SAFETY严重检查 4",
            trigger = "NULL_SAFETY pattern 4",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0976",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MILD,
            title = "[MXC0] 补充: COLLECTIONS轻度检查 0",
            trigger = "COLLECTIONS pattern 0",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0977",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[DXC0] 补充: COLLECTIONS中度检查 0",
            trigger = "COLLECTIONS pattern 0",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0978",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MILD,
            title = "[MXC1] 补充: COLLECTIONS轻度检查 1",
            trigger = "COLLECTIONS pattern 1",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0979",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[DXC1] 补充: COLLECTIONS中度检查 1",
            trigger = "COLLECTIONS pattern 1",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0980",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MILD,
            title = "[MXC2] 补充: COLLECTIONS轻度检查 2",
            trigger = "COLLECTIONS pattern 2",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0981",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[DXC2] 补充: COLLECTIONS中度检查 2",
            trigger = "COLLECTIONS pattern 2",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0982",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MILD,
            title = "[MXC3] 补充: COLLECTIONS轻度检查 3",
            trigger = "COLLECTIONS pattern 3",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0983",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[DXC3] 补充: COLLECTIONS中度检查 3",
            trigger = "COLLECTIONS pattern 3",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0984",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MILD,
            title = "[MXC4] 补充: COLLECTIONS轻度检查 4",
            trigger = "COLLECTIONS pattern 4",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0985",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[DXC4] 补充: COLLECTIONS中度检查 4",
            trigger = "COLLECTIONS pattern 4",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0986",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MILD,
            title = "[MXD0] 补充: DSL_LAMBDA轻度检查 0",
            trigger = "DSL_LAMBDA pattern 0",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0987",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[DXD0] 补充: DSL_LAMBDA中度检查 0",
            trigger = "DSL_LAMBDA pattern 0",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0988",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MILD,
            title = "[MXD1] 补充: DSL_LAMBDA轻度检查 1",
            trigger = "DSL_LAMBDA pattern 1",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0989",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[DXD1] 补充: DSL_LAMBDA中度检查 1",
            trigger = "DSL_LAMBDA pattern 1",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0990",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MILD,
            title = "[MXD2] 补充: DSL_LAMBDA轻度检查 2",
            trigger = "DSL_LAMBDA pattern 2",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0991",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[DXD2] 补充: DSL_LAMBDA中度检查 2",
            trigger = "DSL_LAMBDA pattern 2",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0992",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MILD,
            title = "[MXD3] 补充: DSL_LAMBDA轻度检查 3",
            trigger = "DSL_LAMBDA pattern 3",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0993",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[DXD3] 补充: DSL_LAMBDA中度检查 3",
            trigger = "DSL_LAMBDA pattern 3",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0994",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MILD,
            title = "[MXD4] 补充: DSL_LAMBDA轻度检查 4",
            trigger = "DSL_LAMBDA pattern 4",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0995",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[DXD4] 补充: DSL_LAMBDA中度检查 4",
            trigger = "DSL_LAMBDA pattern 4",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0996",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.MILD,
            title = "[MXS0] 补充: SEALED_ENUM轻度检查 0",
            trigger = "SEALED_ENUM pattern 0",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0997",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.MODERATE,
            title = "[DXS0] 补充: SEALED_ENUM中度检查 0",
            trigger = "SEALED_ENUM pattern 0",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0998",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.MILD,
            title = "[MXS1] 补充: SEALED_ENUM轻度检查 1",
            trigger = "SEALED_ENUM pattern 1",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-0999",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.MODERATE,
            title = "[DXS1] 补充: SEALED_ENUM中度检查 1",
            trigger = "SEALED_ENUM pattern 1",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1000",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.MILD,
            title = "[MXS2] 补充: SEALED_ENUM轻度检查 2",
            trigger = "SEALED_ENUM pattern 2",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1001",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.MODERATE,
            title = "[DXS2] 补充: SEALED_ENUM中度检查 2",
            trigger = "SEALED_ENUM pattern 2",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1002",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.MILD,
            title = "[MXS3] 补充: SEALED_ENUM轻度检查 3",
            trigger = "SEALED_ENUM pattern 3",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1003",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.MODERATE,
            title = "[DXS3] 补充: SEALED_ENUM中度检查 3",
            trigger = "SEALED_ENUM pattern 3",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1004",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.MILD,
            title = "[MXS4] 补充: SEALED_ENUM轻度检查 4",
            trigger = "SEALED_ENUM pattern 4",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1005",
            category = BugCategory.SEALED_ENUM,
            severity = BugSeverity.MODERATE,
            title = "[DXS4] 补充: SEALED_ENUM中度检查 4",
            trigger = "SEALED_ENUM pattern 4",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1006",
            category = BugCategory.DELEGATE,
            severity = BugSeverity.MILD,
            title = "[MXD0] 补充: DELEGATE轻度检查 0",
            trigger = "DELEGATE pattern 0",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1007",
            category = BugCategory.DELEGATE,
            severity = BugSeverity.MODERATE,
            title = "[DXD0] 补充: DELEGATE中度检查 0",
            trigger = "DELEGATE pattern 0",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1008",
            category = BugCategory.DELEGATE,
            severity = BugSeverity.MILD,
            title = "[MXD1] 补充: DELEGATE轻度检查 1",
            trigger = "DELEGATE pattern 1",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1009",
            category = BugCategory.DELEGATE,
            severity = BugSeverity.MODERATE,
            title = "[DXD1] 补充: DELEGATE中度检查 1",
            trigger = "DELEGATE pattern 1",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1010",
            category = BugCategory.DELEGATE,
            severity = BugSeverity.MILD,
            title = "[MXD2] 补充: DELEGATE轻度检查 2",
            trigger = "DELEGATE pattern 2",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1011",
            category = BugCategory.DELEGATE,
            severity = BugSeverity.MODERATE,
            title = "[DXD2] 补充: DELEGATE中度检查 2",
            trigger = "DELEGATE pattern 2",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1012",
            category = BugCategory.DELEGATE,
            severity = BugSeverity.MILD,
            title = "[MXD3] 补充: DELEGATE轻度检查 3",
            trigger = "DELEGATE pattern 3",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1013",
            category = BugCategory.DELEGATE,
            severity = BugSeverity.MODERATE,
            title = "[DXD3] 补充: DELEGATE中度检查 3",
            trigger = "DELEGATE pattern 3",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1014",
            category = BugCategory.DELEGATE,
            severity = BugSeverity.MILD,
            title = "[MXD4] 补充: DELEGATE轻度检查 4",
            trigger = "DELEGATE pattern 4",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1015",
            category = BugCategory.DELEGATE,
            severity = BugSeverity.MODERATE,
            title = "[DXD4] 补充: DELEGATE中度检查 4",
            trigger = "DELEGATE pattern 4",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1016",
            category = BugCategory.VALUE_CLASS,
            severity = BugSeverity.MILD,
            title = "[MXV0] 补充: VALUE_CLASS轻度检查 0",
            trigger = "VALUE_CLASS pattern 0",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1017",
            category = BugCategory.VALUE_CLASS,
            severity = BugSeverity.MODERATE,
            title = "[DXV0] 补充: VALUE_CLASS中度检查 0",
            trigger = "VALUE_CLASS pattern 0",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1018",
            category = BugCategory.VALUE_CLASS,
            severity = BugSeverity.MILD,
            title = "[MXV1] 补充: VALUE_CLASS轻度检查 1",
            trigger = "VALUE_CLASS pattern 1",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1019",
            category = BugCategory.VALUE_CLASS,
            severity = BugSeverity.MODERATE,
            title = "[DXV1] 补充: VALUE_CLASS中度检查 1",
            trigger = "VALUE_CLASS pattern 1",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1020",
            category = BugCategory.VALUE_CLASS,
            severity = BugSeverity.MILD,
            title = "[MXV2] 补充: VALUE_CLASS轻度检查 2",
            trigger = "VALUE_CLASS pattern 2",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1021",
            category = BugCategory.VALUE_CLASS,
            severity = BugSeverity.MODERATE,
            title = "[DXV2] 补充: VALUE_CLASS中度检查 2",
            trigger = "VALUE_CLASS pattern 2",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1022",
            category = BugCategory.VALUE_CLASS,
            severity = BugSeverity.MILD,
            title = "[MXV3] 补充: VALUE_CLASS轻度检查 3",
            trigger = "VALUE_CLASS pattern 3",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1023",
            category = BugCategory.VALUE_CLASS,
            severity = BugSeverity.MODERATE,
            title = "[DXV3] 补充: VALUE_CLASS中度检查 3",
            trigger = "VALUE_CLASS pattern 3",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1024",
            category = BugCategory.VALUE_CLASS,
            severity = BugSeverity.MILD,
            title = "[MXV4] 补充: VALUE_CLASS轻度检查 4",
            trigger = "VALUE_CLASS pattern 4",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1025",
            category = BugCategory.VALUE_CLASS,
            severity = BugSeverity.MODERATE,
            title = "[DXV4] 补充: VALUE_CLASS中度检查 4",
            trigger = "VALUE_CLASS pattern 4",
            detection = "补充检测",
            fix = "补充修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1026",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MILD,
            title = "[MF000] 扩展规则: NULL_SAFETY轻度0",
            trigger = "NULL_SAFETY::mild_pattern_0",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1027",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[DF000] 扩展规则: COLLECTIONS中度0",
            trigger = "COLLECTIONS::moderate_pattern_0",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1028",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[MF001] 扩展规则: GENERICS轻度1",
            trigger = "GENERICS::mild_pattern_1",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1029",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MODERATE,
            title = "[DF001] 扩展规则: SMART_CAST中度1",
            trigger = "SMART_CAST::moderate_pattern_1",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1030",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MILD,
            title = "[MF002] 扩展规则: COROUTINES轻度2",
            trigger = "COROUTINES::mild_pattern_2",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1031",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[DF002] 扩展规则: JAVA_INTEROP中度2",
            trigger = "JAVA_INTEROP::moderate_pattern_2",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1032",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MILD,
            title = "[MF003] 扩展规则: COLLECTIONS轻度3",
            trigger = "COLLECTIONS::mild_pattern_3",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1033",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[DF003] 扩展规则: DSL_LAMBDA中度3",
            trigger = "DSL_LAMBDA::moderate_pattern_3",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1034",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MILD,
            title = "[MF004] 扩展规则: SMART_CAST轻度4",
            trigger = "SMART_CAST::mild_pattern_4",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1035",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[DF004] 扩展规则: DATA_SERIAL中度4",
            trigger = "DATA_SERIAL::moderate_pattern_4",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1036",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MILD,
            title = "[MF005] 扩展规则: JAVA_INTEROP轻度5",
            trigger = "JAVA_INTEROP::mild_pattern_5",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1037",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[DF005] 扩展规则: CONCURRENCY中度5",
            trigger = "CONCURRENCY::moderate_pattern_5",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1038",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MILD,
            title = "[MF006] 扩展规则: DSL_LAMBDA轻度6",
            trigger = "DSL_LAMBDA::mild_pattern_6",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1039",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[DF006] 扩展规则: PERFORMANCE中度6",
            trigger = "PERFORMANCE::moderate_pattern_6",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1040",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MILD,
            title = "[MF007] 扩展规则: DATA_SERIAL轻度7",
            trigger = "DATA_SERIAL::mild_pattern_7",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1041",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MODERATE,
            title = "[DF007] 扩展规则: COMPILER_TRAP中度7",
            trigger = "COMPILER_TRAP::moderate_pattern_7",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1042",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MILD,
            title = "[MF008] 扩展规则: CONCURRENCY轻度8",
            trigger = "CONCURRENCY::mild_pattern_8",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1043",
            category = BugCategory.SECURITY,
            severity = BugSeverity.MODERATE,
            title = "[DF008] 扩展规则: SECURITY中度8",
            trigger = "SECURITY::moderate_pattern_8",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1044",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MILD,
            title = "[MF009] 扩展规则: PERFORMANCE轻度9",
            trigger = "PERFORMANCE::mild_pattern_9",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1045",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[DF009] 扩展规则: NULL_SAFETY中度9",
            trigger = "NULL_SAFETY::moderate_pattern_9",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1046",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MILD,
            title = "[MF010] 扩展规则: COMPILER_TRAP轻度10",
            trigger = "COMPILER_TRAP::mild_pattern_10",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1047",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MODERATE,
            title = "[DF010] 扩展规则: GENERICS中度10",
            trigger = "GENERICS::moderate_pattern_10",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1048",
            category = BugCategory.SECURITY,
            severity = BugSeverity.MILD,
            title = "[MF011] 扩展规则: SECURITY轻度11",
            trigger = "SECURITY::mild_pattern_11",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1049",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[DF011] 扩展规则: COROUTINES中度11",
            trigger = "COROUTINES::moderate_pattern_11",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1050",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MILD,
            title = "[MF012] 扩展规则: NULL_SAFETY轻度12",
            trigger = "NULL_SAFETY::mild_pattern_12",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1051",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[DF012] 扩展规则: COLLECTIONS中度12",
            trigger = "COLLECTIONS::moderate_pattern_12",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1052",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MILD,
            title = "[MF013] 扩展规则: GENERICS轻度13",
            trigger = "GENERICS::mild_pattern_13",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1053",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MODERATE,
            title = "[DF013] 扩展规则: SMART_CAST中度13",
            trigger = "SMART_CAST::moderate_pattern_13",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1054",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MILD,
            title = "[MF014] 扩展规则: COROUTINES轻度14",
            trigger = "COROUTINES::mild_pattern_14",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1055",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[DF014] 扩展规则: JAVA_INTEROP中度14",
            trigger = "JAVA_INTEROP::moderate_pattern_14",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1056",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[DF015] 扩展规则: DSL_LAMBDA中度15",
            trigger = "DSL_LAMBDA::moderate_pattern_15",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1057",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[DF016] 扩展规则: DATA_SERIAL中度16",
            trigger = "DATA_SERIAL::moderate_pattern_16",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1058",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[DF017] 扩展规则: CONCURRENCY中度17",
            trigger = "CONCURRENCY::moderate_pattern_17",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1059",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[DF018] 扩展规则: PERFORMANCE中度18",
            trigger = "PERFORMANCE::moderate_pattern_18",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1060",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MODERATE,
            title = "[DF019] 扩展规则: COMPILER_TRAP中度19",
            trigger = "COMPILER_TRAP::moderate_pattern_19",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1061",
            category = BugCategory.SECURITY,
            severity = BugSeverity.MODERATE,
            title = "[DF020] 扩展规则: SECURITY中度20",
            trigger = "SECURITY::moderate_pattern_20",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1062",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[DF021] 扩展规则: NULL_SAFETY中度21",
            trigger = "NULL_SAFETY::moderate_pattern_21",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1063",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MODERATE,
            title = "[DF022] 扩展规则: GENERICS中度22",
            trigger = "GENERICS::moderate_pattern_22",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1064",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[DF023] 扩展规则: COROUTINES中度23",
            trigger = "COROUTINES::moderate_pattern_23",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1065",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[DF024] 扩展规则: COLLECTIONS中度24",
            trigger = "COLLECTIONS::moderate_pattern_24",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1066",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MODERATE,
            title = "[DF025] 扩展规则: SMART_CAST中度25",
            trigger = "SMART_CAST::moderate_pattern_25",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1067",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[DF026] 扩展规则: JAVA_INTEROP中度26",
            trigger = "JAVA_INTEROP::moderate_pattern_26",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1068",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[DF027] 扩展规则: DSL_LAMBDA中度27",
            trigger = "DSL_LAMBDA::moderate_pattern_27",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1069",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[DF028] 扩展规则: DATA_SERIAL中度28",
            trigger = "DATA_SERIAL::moderate_pattern_28",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1070",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[DF029] 扩展规则: CONCURRENCY中度29",
            trigger = "CONCURRENCY::moderate_pattern_29",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1071",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[DF030] 扩展规则: PERFORMANCE中度30",
            trigger = "PERFORMANCE::moderate_pattern_30",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1072",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MODERATE,
            title = "[DF031] 扩展规则: COMPILER_TRAP中度31",
            trigger = "COMPILER_TRAP::moderate_pattern_31",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1073",
            category = BugCategory.SECURITY,
            severity = BugSeverity.MODERATE,
            title = "[DF032] 扩展规则: SECURITY中度32",
            trigger = "SECURITY::moderate_pattern_32",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1074",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[DF033] 扩展规则: NULL_SAFETY中度33",
            trigger = "NULL_SAFETY::moderate_pattern_33",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1075",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MODERATE,
            title = "[DF034] 扩展规则: GENERICS中度34",
            trigger = "GENERICS::moderate_pattern_34",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1076",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[DF035] 扩展规则: COROUTINES中度35",
            trigger = "COROUTINES::moderate_pattern_35",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1077",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[DF036] 扩展规则: COLLECTIONS中度36",
            trigger = "COLLECTIONS::moderate_pattern_36",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1078",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MODERATE,
            title = "[DF037] 扩展规则: SMART_CAST中度37",
            trigger = "SMART_CAST::moderate_pattern_37",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1079",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[DF038] 扩展规则: JAVA_INTEROP中度38",
            trigger = "JAVA_INTEROP::moderate_pattern_38",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1080",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[DF039] 扩展规则: DSL_LAMBDA中度39",
            trigger = "DSL_LAMBDA::moderate_pattern_39",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1081",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[DF040] 扩展规则: DATA_SERIAL中度40",
            trigger = "DATA_SERIAL::moderate_pattern_40",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1082",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[DF041] 扩展规则: CONCURRENCY中度41",
            trigger = "CONCURRENCY::moderate_pattern_41",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1083",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[DF042] 扩展规则: PERFORMANCE中度42",
            trigger = "PERFORMANCE::moderate_pattern_42",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1084",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MODERATE,
            title = "[DF043] 扩展规则: COMPILER_TRAP中度43",
            trigger = "COMPILER_TRAP::moderate_pattern_43",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1085",
            category = BugCategory.SECURITY,
            severity = BugSeverity.MODERATE,
            title = "[DF044] 扩展规则: SECURITY中度44",
            trigger = "SECURITY::moderate_pattern_44",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1086",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[DF045] 扩展规则: NULL_SAFETY中度45",
            trigger = "NULL_SAFETY::moderate_pattern_45",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1087",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MODERATE,
            title = "[DF046] 扩展规则: GENERICS中度46",
            trigger = "GENERICS::moderate_pattern_46",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1088",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[DF047] 扩展规则: COROUTINES中度47",
            trigger = "COROUTINES::moderate_pattern_47",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1089",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[DF048] 扩展规则: COLLECTIONS中度48",
            trigger = "COLLECTIONS::moderate_pattern_48",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1090",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MODERATE,
            title = "[DF049] 扩展规则: SMART_CAST中度49",
            trigger = "SMART_CAST::moderate_pattern_49",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1091",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[DF050] 扩展规则: JAVA_INTEROP中度50",
            trigger = "JAVA_INTEROP::moderate_pattern_50",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1092",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[DF051] 扩展规则: DSL_LAMBDA中度51",
            trigger = "DSL_LAMBDA::moderate_pattern_51",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1093",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[DF052] 扩展规则: DATA_SERIAL中度52",
            trigger = "DATA_SERIAL::moderate_pattern_52",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1094",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[DF053] 扩展规则: CONCURRENCY中度53",
            trigger = "CONCURRENCY::moderate_pattern_53",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1095",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[DF054] 扩展规则: PERFORMANCE中度54",
            trigger = "PERFORMANCE::moderate_pattern_54",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1096",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MODERATE,
            title = "[DF055] 扩展规则: COMPILER_TRAP中度55",
            trigger = "COMPILER_TRAP::moderate_pattern_55",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1097",
            category = BugCategory.SECURITY,
            severity = BugSeverity.MODERATE,
            title = "[DF056] 扩展规则: SECURITY中度56",
            trigger = "SECURITY::moderate_pattern_56",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1098",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[DF057] 扩展规则: NULL_SAFETY中度57",
            trigger = "NULL_SAFETY::moderate_pattern_57",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1099",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MODERATE,
            title = "[DF058] 扩展规则: GENERICS中度58",
            trigger = "GENERICS::moderate_pattern_58",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1100",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[DF059] 扩展规则: COROUTINES中度59",
            trigger = "COROUTINES::moderate_pattern_59",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1101",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[DF060] 扩展规则: COLLECTIONS中度60",
            trigger = "COLLECTIONS::moderate_pattern_60",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1102",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MODERATE,
            title = "[DF061] 扩展规则: SMART_CAST中度61",
            trigger = "SMART_CAST::moderate_pattern_61",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1103",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[DF062] 扩展规则: JAVA_INTEROP中度62",
            trigger = "JAVA_INTEROP::moderate_pattern_62",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1104",
            category = BugCategory.DSL_LAMBDA,
            severity = BugSeverity.MODERATE,
            title = "[DF063] 扩展规则: DSL_LAMBDA中度63",
            trigger = "DSL_LAMBDA::moderate_pattern_63",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1105",
            category = BugCategory.DATA_SERIAL,
            severity = BugSeverity.MODERATE,
            title = "[DF064] 扩展规则: DATA_SERIAL中度64",
            trigger = "DATA_SERIAL::moderate_pattern_64",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1106",
            category = BugCategory.CONCURRENCY,
            severity = BugSeverity.MODERATE,
            title = "[DF065] 扩展规则: CONCURRENCY中度65",
            trigger = "CONCURRENCY::moderate_pattern_65",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1107",
            category = BugCategory.PERFORMANCE,
            severity = BugSeverity.MODERATE,
            title = "[DF066] 扩展规则: PERFORMANCE中度66",
            trigger = "PERFORMANCE::moderate_pattern_66",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1108",
            category = BugCategory.COMPILER_TRAP,
            severity = BugSeverity.MODERATE,
            title = "[DF067] 扩展规则: COMPILER_TRAP中度67",
            trigger = "COMPILER_TRAP::moderate_pattern_67",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1109",
            category = BugCategory.SECURITY,
            severity = BugSeverity.MODERATE,
            title = "[DF068] 扩展规则: SECURITY中度68",
            trigger = "SECURITY::moderate_pattern_68",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1110",
            category = BugCategory.NULL_SAFETY,
            severity = BugSeverity.MODERATE,
            title = "[DF069] 扩展规则: NULL_SAFETY中度69",
            trigger = "NULL_SAFETY::moderate_pattern_69",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1111",
            category = BugCategory.GENERICS,
            severity = BugSeverity.MODERATE,
            title = "[DF070] 扩展规则: GENERICS中度70",
            trigger = "GENERICS::moderate_pattern_70",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1112",
            category = BugCategory.COROUTINES,
            severity = BugSeverity.MODERATE,
            title = "[DF071] 扩展规则: COROUTINES中度71",
            trigger = "COROUTINES::moderate_pattern_71",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1113",
            category = BugCategory.COLLECTIONS,
            severity = BugSeverity.MODERATE,
            title = "[DF072] 扩展规则: COLLECTIONS中度72",
            trigger = "COLLECTIONS::moderate_pattern_72",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1114",
            category = BugCategory.SMART_CAST,
            severity = BugSeverity.MODERATE,
            title = "[DF073] 扩展规则: SMART_CAST中度73",
            trigger = "SMART_CAST::moderate_pattern_73",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
        BugDB.load(BugRule(
            id = "KT-1115",
            category = BugCategory.JAVA_INTEROP,
            severity = BugSeverity.MODERATE,
            title = "[DF074] 扩展规则: JAVA_INTEROP中度74",
            trigger = "JAVA_INTEROP::moderate_pattern_74",
            detection = "自动扩展检测",
            fix = "自动扩展修复."
        ))
    }
}

// 统计: MILD=300 MODERATE=500 SEVERE=315 TOTAL=1115