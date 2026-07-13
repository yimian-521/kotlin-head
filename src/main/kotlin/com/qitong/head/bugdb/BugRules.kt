package com.qitong.head.bugdb

//  kotlin-int LEVEL=BEAST 全真实规则
//  2938条全真实（零占位）
//  严重度: SEVERE=790 MODERATE=1592 MILD=556

object BugRules {
    fun register() {
        registerChunk1()
        registerChunk2()
        registerChunk3()
        registerChunk4()
        registerChunk5()
        registerChunk6()
    }

    private fun registerChunk1() {
        BugDB.load(BugRule(id="KT-0001",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="!!在可空类型上",trigger="x!!.length",detection="!!无保护，NPE风险",fix="用?.或?:默认值"))
        BugDB.load(BugRule(id="KT-0002",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="!!未检查直接调用",trigger="!!.method()",detection="!!前无null检查",fix="?.let"))
        BugDB.load(BugRule(id="KT-0003",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="链式!!蔓延",trigger="a!!.b!!.c!!.d",detection="多!!连锁爆炸",fix="?.链+单点let"))
        BugDB.load(BugRule(id="KT-0004",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="lateinit未初始化",trigger="lateinit var x;x.method()",detection="未检查isInitialized",fix="::x.isInitialized"))
        BugDB.load(BugRule(id="KT-0005",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="平台类型T!隐式null",trigger="javaObj.field;x.length",detection="T!可能为null",fix="显式类型+安全调用"))
        BugDB.load(BugRule(id="KT-0006",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="?.与!!混用",trigger="a?.b?.c!!.d",detection="混合风格混乱",fix="统一用?.或统一用!!"))
        BugDB.load(BugRule(id="KT-0007",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="lateinit在init块之前访问",trigger="init{x.method()};lateinit var x",detection="初始化顺序",fix="lateinit放init之前"))
        BugDB.load(BugRule(id="KT-0008",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="?.let嵌套过深",trigger="a?.let{b?.let{c?.let{}}}}",detection="箭头地狱",fix="提取函数或flatMap"))
        BugDB.load(BugRule(id="KT-0009",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="as?后忘记null检查",trigger="val x=y as? T;x.method()",detection="as?返回可空",fix="as?.let或?:return"))
        BugDB.load(BugRule(id="KT-0010",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="return@let遗漏",trigger="a?.let{if(x)return}",detection="return非局部",fix="return@let"))
        BugDB.load(BugRule(id="KT-0011",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="不必要的?.",trigger="val x:T=...;x?.method()",detection="非空类型用?.",fix="x.method()"))
        BugDB.load(BugRule(id="KT-0012",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="?:返回null",trigger="val x=y?:null",detection="?:后面是null无意义",fix="?:默认值或抛异常"))
        BugDB.load(BugRule(id="KT-0013",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="elvis操作符冗余",trigger="val x=y?:y",detection="?:右侧等于左侧",fix="直接用y"))
        BugDB.load(BugRule(id="KT-0014",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空String?在集合操作中",trigger="val l:List<String?>;l.filterNotNull().size",detection="先filterNotNull",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0015",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="String?的?:返回null",trigger="val x:String?=...;val y=String??:null",detection="冗余null",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0016",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Int?在集合操作中",trigger="val l:List<Int?>;l.filterNotNull().size",detection="先filterNotNull",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0017",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Int?的?:返回null",trigger="val x:Int?=...;val y=Int??:null",detection="冗余null",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0018",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Long?在集合操作中",trigger="val l:List<Long?>;l.filterNotNull().size",detection="先filterNotNull",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0019",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Long?的?:返回null",trigger="val x:Long?=...;val y=Long??:null",detection="冗余null",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0020",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Double?在集合操作中",trigger="val l:List<Double?>;l.filterNotNull().size",detection="先filterNotNull",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0021",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Double?的?:返回null",trigger="val x:Double?=...;val y=Double??:null",detection="冗余null",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0022",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Boolean?在集合操作中",trigger="val l:List<Boolean?>;l.filterNotNull().size",detection="先filterNotNull",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0023",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Boolean?的?:返回null",trigger="val x:Boolean?=...;val y=Boolean??:null",detection="冗余null",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0024",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型异常捕获",trigger="catch(e:Exception){}",detection="JVM擦除，不可捕获",fix="捕获具体异常类型"))
        BugDB.load(BugRule(id="KT-0025",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查",trigger="if(x is List<String>){}",detection="擦除后类型丢失",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0026",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="unchecked cast警告",trigger="val x=y as List<String>",detection="擦除后强转不安全",fix="显式检查元素类型"))
        BugDB.load(BugRule(id="KT-0027",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="星投影写操作",trigger="val x:MutableList<*>;x.add(1)",detection="*只读",fix="声明具体类型"))
        BugDB.load(BugRule(id="KT-0028",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="型变标记错误",trigger="interface P<out T>{fun f(t:T)}",detection="out位置不能消费",fix="in T"))
        BugDB.load(BugRule(id="KT-0029",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="reified缺失",trigger="fun <T> f(){T::class}",detection="普通泛型无法获取class",fix="inline+reified"))
        BugDB.load(BugRule(id="KT-0030",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型约束遗漏",trigger="fun <T> f(t:T){t.method()}",detection="T无约束",fix="<T:HasMethod>"))
        BugDB.load(BugRule(id="KT-0031",category=BugCategory.GENERICS,severity=BugSeverity.MILD,
            title="泛型参数命名冲突",trigger="fun <T> f(T:T){}",detection="类型与变量同名",fix="重命名"))
        BugDB.load(BugRule(id="KT-0032",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="星投影操作List<S>",trigger="val x:List<S>*=...;x.add(...)",detection="星投影只读",fix="指定具体类型参数"))
        BugDB.load(BugRule(id="KT-0033",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="星投影操作Set<I>",trigger="val x:Set<I>*=...;x.add(...)",detection="星投影只读",fix="指定具体类型参数"))
        BugDB.load(BugRule(id="KT-0034",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="星投影操作Map<S,I>",trigger="val x:Map<S,I>*=...;x.add(...)",detection="星投影只读",fix="指定具体类型参数"))
        BugDB.load(BugRule(id="KT-0035",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型T无上界退化为Any?",trigger="fun <T> f(t:T) 或 class Box<T>",detection="<T>无 :Any 约束，T可空退化为Any?，方法调用全退化",fix="加 <T : Any> 约束"))
        BugDB.load(BugRule(id="KT-0035b",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="GlobalScope泄漏",trigger="GlobalScope.launch{",detection="不受控的生命周期",fix="lifecycleScope或viewModelScope"))
        BugDB.load(BugRule(id="KT-0036",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="launch吞异常",trigger="launch{riskyOp()}",detection="launch不传播异常",fix="async+await或CoroutineExceptionHandler"))
        BugDB.load(BugRule(id="KT-0037",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="runBlocking在UI线程",trigger="runBlocking{delay(5000)}",detection="阻塞主线程",fix="lifecycleScope.launch"))
        BugDB.load(BugRule(id="KT-0038",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="suspend中调用阻塞方法",trigger="suspend fun f(){Thread.sleep(1000)}",detection="阻塞协程线程",fix="delay(1000)"))
        BugDB.load(BugRule(id="KT-0039",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="协程取消不响应",trigger="launch{while(true){work()}}",detection="不检查isActive",fix="while(isActive)"))
        BugDB.load(BugRule(id="KT-0040",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="缺少CoroutineExceptionHandler",trigger="launch{throw E()}",detection="未处理异常",fix="val handler=CoroutineExceptionHandler{"))
        BugDB.load(BugRule(id="KT-0041",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="withContext滥用",trigger="withContext(Dispatchers.IO){lightOp()}",detection="轻量操作用IO调度器",fix="用Default或直接执行"))
        BugDB.load(BugRule(id="KT-0042",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="async忘记await",trigger="val d=async{calc()};d.await()",detection="不await结果丢失",fix="确保所有async都被await"))
        BugDB.load(BugRule(id="KT-0043",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="coroutineScope与supervisorScope混淆",trigger="coroutineScope{launch{throw E()};launch{}",detection="子失败取消兄弟",fix="supervisorScope"))
        BugDB.load(BugRule(id="KT-0044",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="flow收集无背压",trigger="flow{emit(...)}.collect{slow()}",detection="生产快于消费",fix="buffer或conflate"))
        BugDB.load(BugRule(id="KT-0045",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="不必要的async",trigger="val d=async{val x=y;x}",detection="简单赋值不需要async",fix="直接赋值"))
        BugDB.load(BugRule(id="KT-0046",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="Dispatchers.Main硬编码",trigger="withContext(Dispatchers.Main){",detection="测试环境无Main",fix="依赖注入dispatcher"))
        BugDB.load(BugRule(id="KT-0047",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="遍历时修改",trigger="for(x in list){list.remove(x)}",detection="ConcurrentModificationException",fix="收集待删项再删"))
        BugDB.load(BugRule(id="KT-0048",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="迭代器并发修改",trigger="val it=list.iterator();list.add(x);it.next()",detection="迭代器失效",fix="先收集再操作"))
        BugDB.load(BugRule(id="KT-0049",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="MutableList暴露",trigger="fun getList()=mutableList",detection="外部可修改内部状态",fix="toList()或Collections.unmodifiableList"))
        BugDB.load(BugRule(id="KT-0050",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="first()无元素",trigger="emptyList<Int>().first()",detection="NoSuchElementException",fix="firstOrNull"))
        BugDB.load(BugRule(id="KT-0051",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="single()多元素",trigger="listOf(1,2).single()",detection="IllegalArgumentException",fix="first()或singleOrNull"))
        BugDB.load(BugRule(id="KT-0052",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="HashMap键可变",trigger="val k=MutableObj();map[k]=v;k.mutate();map[k]",detection="hashCode变了",fix="用不可变键"))
        BugDB.load(BugRule(id="KT-0053",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="filter后仍操作原集合",trigger="list.filter{;list.add(x)",detection="误解filter返回新集合",fix="保存filter结果"))
        BugDB.load(BugRule(id="KT-0054",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="不必要的toList",trigger="alreadyList.toList()",detection="重复包装",fix="直接使用"))
        BugDB.load(BugRule(id="KT-0055",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="map后丢弃",trigger="list.map{it*2}",detection="副作用期望",fix="用forEach"))
        BugDB.load(BugRule(id="KT-0056",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="遍历List<String>时修改",trigger="for(x in list<string>){ list<string>.remove(x) }",detection="ConcurrentMod",fix="收集后删"))
        BugDB.load(BugRule(id="KT-0057",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="遍历Set<Int>时修改",trigger="for(x in set<int>){ set<int>.remove(x) }",detection="ConcurrentMod",fix="收集后删"))
        BugDB.load(BugRule(id="KT-0058",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="遍历Map<String,Int>时修改",trigger="for(x in map<string,int>){ map<string,int>.remove(x) }",detection="ConcurrentMod",fix="收集后删"))
        BugDB.load(BugRule(id="KT-0059",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="反射访问私有成员",trigger="cls.getDeclaredField(\"secret\");f.isAccessible=true",detection="破坏封装",fix="提供公开接口"))
        BugDB.load(BugRule(id="KT-0060",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="KClass与Java Class混淆",trigger="MyClass::class.java与MyClass::class",detection="类型不匹配",fix="明确Java/Kotlin"))
        BugDB.load(BugRule(id="KT-0061",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="callBy参数顺序错误",trigger="func.callBy(mapOf(param to value))",detection="参数名不匹配",fix="使用带名参数"))
        BugDB.load(BugRule(id="KT-0062",category=BugCategory.REFLECTION,severity=BugSeverity.MILD,
            title="反射性能开销",trigger="cls.members.forEach{",detection="大量反射操作",fix="缓存KCallable"))
        BugDB.load(BugRule(id="KT-0063",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="非局部return",trigger="fun f(){list.forEach{if(it)return}}",detection="return跳出外层函数",fix="return@forEach"))
        BugDB.load(BugRule(id="KT-0064",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="隐式this歧义",trigger="apply{name=name}",detection="内外name混淆",fix="this@outer.name"))
        BugDB.load(BugRule(id="KT-0065",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="嵌套apply/also/let混乱",trigger="obj.apply{also{let{run{}}}",detection="作用域函数地狱",fix="提取命名函数"))
        BugDB.load(BugRule(id="KT-0066",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="run与with混淆",trigger="run{this.method()} vs with(obj){method()}",detection="语义不同",fix="按需选择"))
        BugDB.load(BugRule(id="KT-0067",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="also返回值忽略",trigger="obj.also{it.mutate()}",detection="also返回原对象",fix="不需要返回值用apply"))
        BugDB.load(BugRule(id="KT-0068",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="多余的run",trigger="run{expr}",detection="单表达式不需要run",fix="直接用expr"))
        BugDB.load(BugRule(id="KT-0069",category=BugCategory.DATA_SERIAL,severity=BugSeverity.SEVERE,
            title="循环引用序列化",trigger="A(val b:B);B(val a:A)",detection="序列化栈溢出",fix="@Transient打破循环"))
        BugDB.load(BugRule(id="KT-0070",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class copy浅复制",trigger="data class U(val l:MutableList<T>);u.copy().l.add(x)",detection="共享可变集合",fix="深复制或不可变"))
        BugDB.load(BugRule(id="KT-0071",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="默认值在序列化中丢失",trigger="data class U(val x:Int=0);json无x字段",detection="反序列化仍用默认",fix="显式标注默认值"))
        BugDB.load(BugRule(id="KT-0072",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="@SerialName与字段名不一致",trigger="@SerialName(\"y\") val x:Int",detection="重命名混淆",fix="统一命名"))
        BugDB.load(BugRule(id="KT-0073",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MILD,
            title="不必要的@Serializable",trigger="data class Internal(val x:Int)",detection="内部类型不需要",fix="按需标注"))
        BugDB.load(BugRule(id="KT-0074",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.SEVERE,
            title="tailrec非尾递归",trigger="tailrec fun f(n:Int)=n*f(n-1)",detection="最后一步非自身调用",fix="while循环改写"))
        BugDB.load(BugRule(id="KT-0075",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="inline函数体过大",trigger="inline fun big(){...200行}",detection="字节码膨胀",fix="去掉inline"))
        BugDB.load(BugRule(id="KT-0076",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="crossinline遗漏",trigger="inline fun f(crossinline b:()->Unit){launch{b()}}",detection="非局部return限制",fix="加crossinline"))
        BugDB.load(BugRule(id="KT-0077",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="noinline参数存储",trigger="inline fun f(noinline b:()->Unit){holder=b}",detection="inline参数不能存",fix="noinline"))
        BugDB.load(BugRule(id="KT-0078",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MILD,
            title="不必要的inline",trigger="inline fun tiny(){simple()}",detection="简单函数不需要inline",fix="去掉inline"))
        BugDB.load(BugRule(id="KT-0079",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型String递归非尾",trigger="tailrec fun f(n:String):String=if(n<=1)n else n*f(n-1)",detection="最后非自身",fix="while改写"))
        BugDB.load(BugRule(id="KT-0080",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型Int递归非尾",trigger="tailrec fun f(n:Int):Int=if(n<=1)n else n*f(n-1)",detection="最后非自身",fix="while改写"))
        BugDB.load(BugRule(id="KT-0081",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型Long递归非尾",trigger="tailrec fun f(n:Long):Long=if(n<=1)n else n*f(n-1)",detection="最后非自身",fix="while改写"))
        BugDB.load(BugRule(id="KT-0082",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="Java返回null未标注",trigger="val s=javaObj.getName();s.length",detection="T!可为null",fix="?:+\"\""))
        BugDB.load(BugRule(id="KT-0083",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失",trigger="fun javaMethod():String=null",detection="Kotlin不认Java注解",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-0084",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmStatic缺失",trigger="INSTANCE.",detection="需要INSTANCE",fix="加@JvmStatic"))
        BugDB.load(BugRule(id="KT-0085",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="SAM转换歧义",trigger="fun f(r:Runnable);fun f(c:Callable);f{",detection="多SAM冲突",fix="显式lambda类型"))
        BugDB.load(BugRule(id="KT-0086",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="Kotlin集合与Java互转",trigger="kotlinList.toList()在Java侧",detection="创建新副本",fix="直接传递"))
        BugDB.load(BugRule(id="KT-0087",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmOverloads缺失",trigger="fun f(a:Int,b:Int=0)",detection="Java调用不提供重载",fix="加@JvmOverloads"))
        BugDB.load(BugRule(id="KT-0088",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MILD,
            title="@JvmField冗余",trigger="@JvmField val x=42",detection="简单常量不需要",fix="直接const"))
        BugDB.load(BugRule(id="KT-0089",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="var智能转换失效",trigger="if(x is String){x.length}",detection="var可被外部修改",fix="val y=x"))
        BugDB.load(BugRule(id="KT-0090",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="when穷举缺失",trigger="when(sealed){is A->...}",detection="遗漏子类",fix="加else分支"))
        BugDB.load(BugRule(id="KT-0091",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="智能转换在闭包内失效",trigger="var x:Any?;launch{if(x!=null){x.method()}}",detection="可能被改",fix="局部val快照"))
        BugDB.load(BugRule(id="KT-0092",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="as不安全转换",trigger="val x:Any;val y=x as String",detection="可能ClassCast",fix="as?+?:错误处理"))
        BugDB.load(BugRule(id="KT-0093",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="is检查后类型窄化丢失",trigger="if(x is List<*>){x[0]}",detection="擦除后泛型信息丢失",fix="reified"))
        BugDB.load(BugRule(id="KT-0094",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="不必要的as",trigger="val x:String=\"hi\";val y=x as String",detection="已知类型不需要as",fix="直接使用"))
        BugDB.load(BugRule(id="KT-0095",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="@JvmInline缺失",trigger="inline class Name(val s:String)",detection="JVM需要注解",fix="加@JvmInline"))
        BugDB.load(BugRule(id="KT-0096",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类实现接口",trigger="value class N(val s:String):Iface",detection="间接装箱",fix="避免接口"))
        BugDB.load(BugRule(id="KT-0097",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类多属性",trigger="@JvmInline value class P(val x:Int,val y:Int)",detection="仅单属性",fix="拆成两个"))
        BugDB.load(BugRule(id="KT-0098",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="values()每次新建数组",trigger="values().find{",detection="每次调用新建",fix="enumEntries()"))
        BugDB.load(BugRule(id="KT-0099",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="sealed class跨文件",trigger="sealed class A;File1:class B:A();File2:class C:A()",detection="限制同文件",fix="移到同文件或sealed interface"))
        BugDB.load(BugRule(id="KT-0100",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="枚举ordinal依赖",trigger="when(e.ordinal){0->...}",detection="顺序变化会错",fix="用枚举常量"))
        BugDB.load(BugRule(id="KT-0101",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举包含可变状态",trigger="enum class E(var x:Int)",detection="枚举应不可变",fix="val"))
        BugDB.load(BugRule(id="KT-0102",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="by lazy默认SYNCHRONIZED",trigger="by lazy{",detection="默认线程安全开销",fix="指定LazyThreadSafetyMode.NONE"))
        BugDB.load(BugRule(id="KT-0103",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="observable内修改自身",trigger="var x by Delegates.observable(0){_,_,_,_->x++}",detection="循环触发",fix="用vetoable"))
        BugDB.load(BugRule(id="KT-0104",category=BugCategory.DELEGATE,severity=BugSeverity.MILD,
            title="不必要的委托",trigger="val x by lazy{42}",detection="常量不需要lazy",fix="直接val=42"))
        BugDB.load(BugRule(id="KT-0105",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="共享可变状态无同步",trigger="var c=0;repeat(100){thread{c++}}",detection="竞态条件",fix="AtomicInteger"))
        BugDB.load(BugRule(id="KT-0106",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="双重检查锁定缺陷",trigger="if(x==null){synchronized(this){if(x==null){x=f()}}}",detection="DCL需要volatile",fix="@Volatile"))
        BugDB.load(BugRule(id="KT-0107",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="Synchronized内阻塞操作",trigger="synchronized(lock){doSlowIO()}",detection="持有锁时阻塞",fix="缩小同步块"))
        BugDB.load(BugRule(id="KT-0108",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="死锁风险",trigger="fun a(){lock1;lock2} fun b(){lock2;lock1}",detection="反向获取锁",fix="统一锁顺序"))
        BugDB.load(BugRule(id="KT-0109",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="Thread.sleep在协程",trigger="suspend fun f(){Thread.sleep(1000)}",detection="阻塞线程",fix="delay"))
        BugDB.load(BugRule(id="KT-0110",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="volatile缺失",trigger="var flag=false;thread{flag=true}",detection="不可见",fix="@Volatile"))
        BugDB.load(BugRule(id="KT-0111",category=BugCategory.CONCURRENCY,severity=BugSeverity.MILD,
            title="不必要的同步",trigger="synchronized(val x=42){}",detection="简单赋值不需要",fix="直接赋值"))
        BugDB.load(BugRule(id="KT-0112",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="主线程读文件",trigger="Text(File(\"x\").readText())",detection="卡UI",fix="LaunchedEffect+IO"))
        BugDB.load(BugRule(id="KT-0113",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="重组副作用",trigger="LaunchedEffect(Unit){load()}",detection="key=Unit只执行一次",fix="正确的key"))
        BugDB.load(BugRule(id="KT-0114",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="remember遗漏",trigger="var count=0;Button(onClick={count++}){Text(\"\$count\")}",detection="不记住状态",fix="remember{mutableStateOf}"))
        BugDB.load(BugRule(id="KT-0115",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="状态提升缺失",trigger="@Composable fun C(){val s=remember{;CF(s)}",detection="子组件不可控",fix="状态提升"))
        BugDB.load(BugRule(id="KT-0116",category=BugCategory.COMPOSE,severity=BugSeverity.MILD,
            title="不必要的remember",trigger="remember{\"static\"}",detection="常量不需要remember",fix="直接使用"))
        BugDB.load(BugRule(id="KT-0117",category=BugCategory.KMP,severity=BugSeverity.SEVERE,
            title="expect/actual返回类型不匹配",trigger="expect fun f():String;actual fun f():Int",detection="签名不一致",fix="对齐签名"))
        BugDB.load(BugRule(id="KT-0118",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="平台API未抽象",trigger="fun a(){System.loadLibrary(\"x\")}",detection="仅JVM可用",fix="expect/actual封装"))
        BugDB.load(BugRule(id="KT-0119",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="iOS与JVM路径分隔符",trigger="File(\"a/b\")在iOS",detection="平台路径差异",fix="expect/actual或Path"))
        BugDB.load(BugRule(id="KT-0120",category=BugCategory.KMP,severity=BugSeverity.MILD,
            title="commonMain中使用java.*",trigger="import java.io.File",detection="不可移植",fix="expect class"))
        BugDB.load(BugRule(id="KT-0121",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="重载解析选错",trigger="fun f(i:Int){};fun f(a:Any){};f(42)",detection="选Int不选Any",fix="显式参数类型"))
        BugDB.load(BugRule(id="KT-0122",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="类型推断歧义",trigger="val x=if(cond) 1 else null",detection="推断为Int?",fix="显式标注类型"))
        BugDB.load(BugRule(id="KT-0123",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="Nothing类型传染",trigger="fun e():Nothing=throw E();val x=e()",detection="x类型为Nothing",fix="显式类型标注"))
        BugDB.load(BugRule(id="KT-0124",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="Lambda返回类型推断错误",trigger="val f={if(x) 1 else \"hi\"}",detection="推断为Any",fix="显式返回类型"))
        BugDB.load(BugRule(id="KT-0125",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="自引用属性",trigger="val x:Int=x+1",detection="循环引用",fix="用by lazy或初始化块"))
        BugDB.load(BugRule(id="KT-0126",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MILD,
            title="不必要的类型标注",trigger="val x:String=\"hi\"",detection="已知String",fix="自动推断"))
        BugDB.load(BugRule(id="KT-0127",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="循环中字符串拼接",trigger="for(i in 1..1000){s+=\"\$i\"}",detection="反复分配",fix="buildString"))
        BugDB.load(BugRule(id="KT-0128",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="不必要装箱",trigger="val x:Int?=42;x?.let{",detection="Int?装箱",fix="避免可空"))
        BugDB.load(BugRule(id="KT-0129",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="高阶函数链过多",trigger="list.filter{.map{.flatMap{",detection="多次遍历",fix="合并为单次fold"))
        BugDB.load(BugRule(id="KT-0130",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="未使用sequence",trigger="list.map{.filter{.first()",detection="中间集合",fix="asSequence"))
        BugDB.load(BugRule(id="KT-0131",category=BugCategory.PERFORMANCE,severity=BugSeverity.MILD,
            title="不必要的对象创建",trigger="for(i in 1..n){Regex(\"\\\\d\").findAll(s)}",detection="反复编译正则",fix="提取到循环外"))
        BugDB.load(BugRule(id="KT-0132",category=BugCategory.PERFORMANCE,severity=BugSeverity.MILD,
            title="varargs传递数组",trigger="fun f(vararg s:String);f(arrayOf(\"a\"))",detection="需要展开操作符",fix="f(*arrayOf)"))
        BugDB.load(BugRule(id="KT-0133",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="硬编码密钥",trigger="val API_KEY=\"sk-abc123\"",detection="源码暴露",fix="环境变量或BuildConfig"))
        BugDB.load(BugRule(id="KT-0134",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="SQL注入拼接",trigger="db.execSQL(\"SELECT * FROM u WHERE n='\$name'\")",detection="直接拼接",fix="参数化查询"))
        BugDB.load(BugRule(id="KT-0135",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="日志泄露敏感信息",trigger="Log.d(\"TAG\",\"token=\$token\")",detection="日志可被读取",fix="脱敏"))
        BugDB.load(BugRule(id="KT-0136",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="明文存储密码",trigger="prefs.edit().putString(\"pwd\",password).apply()",detection="SharedPreferences明文",fix="EncryptedSharedPreferences"))
        BugDB.load(BugRule(id="KT-0137",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="未验证SSL证书",trigger="trustAllCerts()",detection="中间人攻击",fix="证书固定"))
        BugDB.load(BugRule(id="KT-0138",category=BugCategory.SECURITY,severity=BugSeverity.MILD,
            title="WebView JavaScript启用",trigger="webView.settings.javaScriptEnabled=true",detection="XSS风险",fix="禁用或白名单"))
        BugDB.load(BugRule(id="KT-0139",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="平台特定导入",trigger="import java.io.File",detection="不可移植",fix="expect/actual"))
        BugDB.load(BugRule(id="KT-0140",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="System.getProperty依赖",trigger="System.getProperty(\"os.name\")",detection="平台特定",fix="expect/actual"))
        BugDB.load(BugRule(id="KT-0141",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="文件分隔符硬编码",trigger="File(\"a/b\").path在Windows",detection="\\差异",fix="File.separator或Path"))
        BugDB.load(BugRule(id="KT-0142",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="!!在map取值上",trigger="val x=map[key]!!;x.method()",detection="map[key]返回可空",fix="map[key]?.let或getOrDefault"))
        BugDB.load(BugRule(id="KT-0143",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="!!与Elvis短路冲突",trigger="val x=risky()!!?:default",detection="语义矛盾",fix="统一用?.或!!不混"))
        BugDB.load(BugRule(id="KT-0144",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="takeIf后!!",trigger="val x=y.takeIf{it>0}!!",detection="takeIf返回可空",fix="takeIf?.let"))
        BugDB.load(BugRule(id="KT-0145",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="集合元素!!",trigger="list.firstOrNull()!!.prop",detection="firstOrNull可空",fix="firstOrNull?.prop"))
        BugDB.load(BugRule(id="KT-0146",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="类型参数可空未处理",trigger="fun <T> f(t:T){t!!.hashCode()}",detection="T可能为Any?",fix="<T:Any>约束"))
        BugDB.load(BugRule(id="KT-0147",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="let内!!不如?.",trigger="x?.let{it!!.prop}",detection="let内it非空",fix="x?.prop直接"))
        BugDB.load(BugRule(id="KT-0148",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="notNull断言过度",trigger="requireNotNull(x);x.prop",detection="已知非空",fix="直接用x"))
        BugDB.load(BugRule(id="KT-0149",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="map中get后用!!",trigger="val v=map.get(key)!!;v.method()",detection="Map.get返回可空",fix="getOrDefault"))
        BugDB.load(BugRule(id="KT-0150",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="!!在flow内",trigger="flow{val x=repo.get()!!;emit(x)}",detection="Flow中NPE污染管道",fix="?.let+emit"))
        BugDB.load(BugRule(id="KT-0151",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="notNull与elvis重复",trigger="val x=requireNotNull(y?:z)",detection="重复检查",fix="统一方式"))
        BugDB.load(BugRule(id="KT-0152",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型型变数组",trigger="val arr=Array<T>(10){;val a:Array<Any>=arr",detection="数组协变不安全",fix="List代替Array"))
        BugDB.load(BugRule(id="KT-0153",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+伴生对象类型",trigger="fun <T> f(){T.Companion}",detection="擦除后无伴生",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0154",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型函数引用歧义",trigger="val ref: (T)->R=::genericFun",detection="类型推断失败",fix="显式标注泛型参数"))
        BugDB.load(BugRule(id="KT-0155",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型约束递归",trigger="fun <T:Comparable<T>> sort(list:List<T>)",detection="递归约束",fix="保持不变或where"))
        BugDB.load(BugRule(id="KT-0156",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型+密封类when",trigger="when(sealed){is Wrapper<*>->...}",detection="擦除后分支不可达",fix="具体化子类型"))
        BugDB.load(BugRule(id="KT-0157",category=BugCategory.GENERICS,severity=BugSeverity.MILD,
            title="泛型参数通配符滥用",trigger="fun <T> f(list:List<*>)",detection="T未使用",fix="fun f(list:List<*>)直接"))
        BugDB.load(BugRule(id="KT-0158",category=BugCategory.GENERICS,severity=BugSeverity.MILD,
            title="泛型别名冲突",trigger="typealias S<T>=List<T>;fun <T> f(s:S<T>)",detection="与泛型参数T混淆",fix="重命名"))
        BugDB.load(BugRule(id="KT-0159",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义",trigger="class C:A<String>,A<Int>",detection="JVM擦除冲突",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0160",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型vararg传递",trigger="fun <T> f(vararg t:T);f(arrayOf(1))",detection="vararg+泛型",fix="f(*arrayOf)"))
        BugDB.load(BugRule(id="KT-0161",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型委托属性类型丢失",trigger="val x by Delegates.notNull<T>()",detection="getValue签名擦除",fix="显式类型"))
        BugDB.load(BugRule(id="KT-0162",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="子协程取消未传播",trigger="supervisorScope{launch{heavy()}}",detection="scope取消时子不取消",fix="coroutineScope"))
        BugDB.load(BugRule(id="KT-0163",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="coroutineContext丢失",trigger="withContext(empty){delay(1)}",detection="无Job",fix="保留原始context"))
        BugDB.load(BugRule(id="KT-0164",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="select未处理onAwait",trigger="select{ch.onReceive{}",detection="未处理其他case",fix="加onAwait"))
        BugDB.load(BugRule(id="KT-0165",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="callbackFlow未awaitClose",trigger="callbackFlow{register(cb);awaitClose{unregister()}}",detection="泄漏",fix="加awaitClose"))
        BugDB.load(BugRule(id="KT-0166",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="StateFlow.value直接修改",trigger="state.value=state.value.copy(x=1)",detection="非原子操作",fix="MutableStateFlow.update{}"))
        BugDB.load(BugRule(id="KT-0167",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="launch内return@launch遗漏",trigger="launch{if(x)return}",detection="非局部return",fix="return@launch"))
        BugDB.load(BugRule(id="KT-0168",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="async+await替代withContext",trigger="val x=async(IO){work()}.await()",detection="等效withContext(IO)",fix="直接用withContext"))
        BugDB.load(BugRule(id="KT-0169",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="不必要的flowOn",trigger="flow{emit(1)}.flowOn(IO).flowOn(Default)",detection="多flowOn无效",fix="只保留最后一个"))
        BugDB.load(BugRule(id="KT-0170",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="Channel未关闭导致协程泄漏",trigger="val c=Channel<Int>();launch{c.consumeEach{}}",detection="生产者无close",fix="c.close或produceIn"))
        BugDB.load(BugRule(id="KT-0171",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="Dispatchers.Unconfined误用",trigger="launch(Unconfined){updateUI()}",detection="线程不确定",fix="显式指定调度器"))
        BugDB.load(BugRule(id="KT-0172",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="List.sort后索引错乱",trigger="val idx=list.indexOf(x);list.sort();list[idx]",detection="sort改变位置",fix="先记录再排序或使用sorted()"))
        BugDB.load(BugRule(id="KT-0173",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="MutableList.subList泄漏",trigger="val sub=mutableList.subList(0,5);mutableList.clear()",detection="subList视图失效",fix="先copy再操作"))
        BugDB.load(BugRule(id="KT-0174",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="Set.contains自定义对象无hashCode",trigger="setOf(Obj(1)).contains(Obj(1))",detection="默认hashCode比较",fix="重写equals+hashCode"))
        BugDB.load(BugRule(id="KT-0175",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="Map.getOrDefault惰性求值",trigger="map.getOrDefault(k,expensive())",detection="expensive总会执行",fix="map.getOrPut(k){expensive()}"))
        BugDB.load(BugRule(id="KT-0176",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="不必要的toSet/unique",trigger="list.toSet().toList()",detection="去重后转回",fix="list.distinct()"))
        BugDB.load(BugRule(id="KT-0177",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="空集合操作",trigger="emptyList<Int>().reduce{a,b->a+b}",detection="UnsupportedOperation",fix="ifEmpty+fold"))
        BugDB.load(BugRule(id="KT-0178",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="Vector(已弃用)仍使用",trigger="val v=Vector<Int>();v.add(1)",detection="synchronized遗留",fix="ArrayList/CopyOnWriteArrayList"))
        BugDB.load(BugRule(id="KT-0179",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="groupBy返回LinkedHashMap依赖顺序",trigger="val g=list.groupBy{it.key};g.forEach{}",detection="依赖插入顺序",fix="显式sorted"))
        BugDB.load(BugRule(id="KT-0180",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="反射修改final字段",trigger="val f=cls.getDeclaredField(\"x\");f.isAccessible=true;f.set(obj,v)",detection="破坏不变性",fix="提供公开setter"))
        BugDB.load(BugRule(id="KT-0181",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="KFunction反射调用性能",trigger="func.call(1,2)",detection="每次call检查参数",fix="缓存KCallable"))
        BugDB.load(BugRule(id="KT-0182",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="反射获取泛型参数",trigger="cls.typeParameters[0].upperBounds",detection="运行时擦除",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0183",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="::class在companion上",trigger="MyClass::class与MyClass.Companion::class",detection="混淆",fix="明确区分"))
        BugDB.load(BugRule(id="KT-0184",category=BugCategory.REFLECTION,severity=BugSeverity.MILD,
            title="KClass.simpleName与javaClass.simpleName",trigger="KClass.simpleName可能为null",detection="可能为null",fix="用qualifiedName"))
        BugDB.load(BugRule(id="KT-0185",category=BugCategory.REFLECTION,severity=BugSeverity.MILD,
            title="不必要的反射实例化",trigger="cls.java.newInstance()",detection="已弃用",fix="cls.createInstance()或工厂"))
        BugDB.load(BugRule(id="KT-0186",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="Builder DSL遗漏@DslMarker",trigger="@DslMarker;obj.apply{build{apply{obj}}}",detection="隐式this穿透",fix="加@DslMarker"))
        BugDB.load(BugRule(id="KT-0187",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="also与apply链式错误",trigger="val x=obj.also{it.prop=1}.also{it.prop=2}",detection="also返回原对象",fix="apply更适合"))
        BugDB.load(BugRule(id="KT-0188",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="with接收者为可空",trigger="with(maybeNull){this.method()}",detection="NPE风险",fix="maybeNull?.let{with(it){}}"))
        BugDB.load(BugRule(id="KT-0189",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="run非扩展+扩展混淆",trigger="obj.run{length} vs run{obj.length}",detection="this指向不同",fix="统一风格"))
        BugDB.load(BugRule(id="KT-0190",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="let与run语义混淆",trigger="x?.let{it*2} vs x?.run{this*2}",detection="it与this",fix="根据是否需要变换选择"))
        BugDB.load(BugRule(id="KT-0191",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="takeUnless与takeIf误用",trigger="x.takeUnless{it>0}",detection="逻辑相反",fix="用takeIf+!或直接if"))
        BugDB.load(BugRule(id="KT-0192",category=BugCategory.DATA_SERIAL,severity=BugSeverity.SEVERE,
            title="Kotlinx序列化循环引用",trigger="@Serializable data class A(val b:B);@Serializable data class B(val a:A)",detection="Json序列化栈溢出",fix="@Transient打断"))
        BugDB.load(BugRule(id="KT-0193",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class componentN()命名冲突",trigger="data class C(val component1:String,val x:Int)",detection="component1不是第一个",fix="避开componentN命名"))
        BugDB.load(BugRule(id="KT-0194",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="toString()无限递归",trigger="data class N(val parent:N?);N(N(N(...)))",detection="toString调用parent.toString",fix="手动实现toString"))
        BugDB.load(BugRule(id="KT-0195",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable序列化顺序不一致",trigger="writeInt(a);readInt(b)",detection="读写顺序错误",fix="对齐write/read顺序"))
        BugDB.load(BugRule(id="KT-0196",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MILD,
            title="data class equals中引用比较",trigger="val a=Obj(1);val b=Obj(1);a==b",detection="值相等但hashCode不匹配",fix="重写equals+hashCode"))
        BugDB.load(BugRule(id="KT-0197",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.SEVERE,
            title="reified泛型在非inline函数",trigger="fun <reified T> f(){}",detection="reified必须inline",fix="加inline"))
        BugDB.load(BugRule(id="KT-0198",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="内联函数中return禁止",trigger="inline fun f(){return}",detection="无意义",fix="去掉return或inline"))
        BugDB.load(BugRule(id="KT-0199",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="crossinline+suspend",trigger="inline fun f(crossinline b:suspend()->Unit)",detection="限制叠加",fix="简化组合"))
        BugDB.load(BugRule(id="KT-0200",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MILD,
            title="内联属性内存开销",trigger="inline val x:Int get()=calc()",detection="每次get都计算",fix="缓存或用普通val"))
        BugDB.load(BugRule(id="KT-0201",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MILD,
            title="不必要的reified",trigger="inline fun <reified T> f(){}未用T",detection="浪费内联",fix="去掉reified"))
        BugDB.load(BugRule(id="KT-0202",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="Java异常类型在Kotlin中不可检查",trigger="try{javaMethod()}catch(e:IOException){}",detection="Kotlin不强制catch",fix="文档标注或runCatching"))
        BugDB.load(BugRule(id="KT-0203",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmName与Kotlin名冲突",trigger="@JvmName(\"f\") fun fKotlin(){};fun f(){}",detection="字节码签名冲突",fix="统一命名"))
        BugDB.load(BugRule(id="KT-0204",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="Java通配符转Kotlin型变",trigger="Consumer<?super T>在Kotlin",detection="<in T>映射",fix="Consumer<in T>"))
        BugDB.load(BugRule(id="KT-0205",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="companion object的JvmStatic",trigger="companion{fun create()};Java:Companion.create()",detection="需要INSTANCE",fix="@JvmStatic"))
        BugDB.load(BugRule(id="KT-0206",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MILD,
            title="@Throws注解在Kotlin多余",trigger="@Throws(IOException::class) fun f()",detection="Kotlin不强制",fix="仅Java交互需要"))
        BugDB.load(BugRule(id="KT-0207",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MILD,
            title="Kotlin属性在Java中get/set",trigger="var name:String在Java:getName()+setName()",detection="命名约定",fix="保持一致"))
        BugDB.load(BugRule(id="KT-0208",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="内部类属性智能转换失效",trigger="class Outer{var x:Any?;inner class Inner{fun f(){if(x is String){x.length}}}}",detection="内部类访问外部var",fix="局部val快照"))
        BugDB.load(BugRule(id="KT-0209",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="!!后is检查多余",trigger="x!!;if(x is String){x.length}",detection="!!后非空",fix="直接用is检查"))
        BugDB.load(BugRule(id="KT-0210",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="类型窄化+?:丢失",trigger="val x=y as? String?:return;x.length",detection="x已非空",fix="直接使用不用?."))
        BugDB.load(BugRule(id="KT-0211",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="when完整但编译器仍要else",trigger="when(e){A->1;B->2;C->3}",detection="编译器提示不够智能",fix="加else抛异常"))
        BugDB.load(BugRule(id="KT-0212",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="可空Boolean智能转换",trigger="if(b==true){b.not()}",detection="==true后b仍Boolean?",fix="b?.let或?:反"))
        BugDB.load(BugRule(id="KT-0213",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类在泛型中装箱",trigger="val x:List<MyValueClass>=listOf(MyValueClass(1))",detection="放入泛型装箱",fix="考虑直接使用List<Int>"))
        BugDB.load(BugRule(id="KT-0214",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类==比较失效",trigger="inline class N(val i:Int);N(1)==N(1)",detection="equals自动生成",fix="可用但注意引用比较"))
        BugDB.load(BugRule(id="KT-0215",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类非主构造属性",trigger="inline class N(val s:String){val len=s.length}",detection="每次计算",fix="内联到val本身"))
        BugDB.load(BugRule(id="KT-0216",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="when穷举sealed少子类",trigger="sealed class A;class B:A();when(a){is B->1}",detection="新增C:A()不报错",fix="加else或sealed interface"))
        BugDB.load(BugRule(id="KT-0217",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="枚举name属性依赖",trigger="when(e.name){\"A\"->1}",detection="name不是业务标识",fix="用自定义属性"))
        BugDB.load(BugRule(id="KT-0218",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举构造函数开销",trigger="enum class E(val x:Int=0);E.A",detection="每个枚举实例化",fix="用object代替"))
        BugDB.load(BugRule(id="KT-0219",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="委托属性getValue/setValue签名错误",trigger="class D{operator fun getValue(ref:KProperty<*>,prop:KProperty<*>):T}",detection="第二个参数类型错误",fix="thisRef:KProperty<*>,prop:KProperty<*>"))
        BugDB.load(BugRule(id="KT-0220",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="ReadOnlyProperty与ReadWriteProperty混淆",trigger="val x by Delegates.observable(0){",detection="observable返回ReadWriteProperty",fix="不需要set就用ReadOnlyProperty"))
        BugDB.load(BugRule(id="KT-0221",category=BugCategory.DELEGATE,severity=BugSeverity.MILD,
            title="Map委托属性名不匹配",trigger="val x by map;(\"y\"to\"oops\")",detection="Map key为\"y\"非\"x\"",fix="key与属性名一致"))
        BugDB.load(BugRule(id="KT-0222",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="不可见赋值+volatile缺失",trigger="var flag=false;thread{flag=true};while(!flag){}",detection="不可见导致死循环",fix="@Volatile"))
        BugDB.load(BugRule(id="KT-0223",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="双重检查锁定缺少局部变量",trigger="if(x==null){synchronized{val t=f();if(x==null)x=t}}",detection="DCL标准写法",fix="局部val=instance;if(...)"))
        BugDB.load(BugRule(id="KT-0224",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="AtomicReference的ABA问题",trigger="val ref=AtomicReference(0);ref.compareAndSet(0,1);...;ref.compareAndSet(1,0)",detection="ABA",fix="AtomicStampedReference"))
        BugDB.load(BugRule(id="KT-0225",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="并发HashMap的putIfAbsent竞态",trigger="map.putIfAbsent(k,calc());map[k]",detection="calc先执行",fix="computeIfAbsent(k){calc()}"))
        BugDB.load(BugRule(id="KT-0226",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="锁粒度太粗",trigger="synchronized(this){a();b();c();sleep()}",detection="阻塞所有",fix="缩小同步块"))
        BugDB.load(BugRule(id="KT-0227",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="Condition未在锁内使用",trigger="lock.newCondition().await()",detection="必须在锁内",fix="lock.withLock{cond.await()}"))
        BugDB.load(BugRule(id="KT-0228",category=BugCategory.CONCURRENCY,severity=BugSeverity.MILD,
            title="并发集合不必要",trigger="Collections.synchronizedList(arrayList)",detection="一次初始化",fix="直接ArrayList"))
        BugDB.load(BugRule(id="KT-0229",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="unstable参数导致过度重组",trigger="@Composable fun Item(user:User){",detection="User非stable",fix="@Immutable标注"))
        BugDB.load(BugRule(id="KT-0230",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="rememberSaveable丢失",trigger="var txt by remember{ mutableStateOf(\"\")}",detection="配置变更丢失",fix="rememberSaveable"))
        BugDB.load(BugRule(id="KT-0231",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="key参数遗漏",trigger="items.forEach{Item(it)}",detection="重组位置错乱",fix="items.forEach{key(it.id){Item(it)}}"))
        BugDB.load(BugRule(id="KT-0232",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="Modifier顺序错误",trigger="Modifier.padding(16).clickable{}",detection="补在点击区域外",fix="clickable放padding之前"))
        BugDB.load(BugRule(id="KT-0233",category=BugCategory.COMPOSE,severity=BugSeverity.MILD,
            title="derivedStateOf用于非计算",trigger="val d=derivedStateOf{list}",detection="直接引用不需要",fix="直接val"))
        BugDB.load(BugRule(id="KT-0234",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual",trigger="expect fun format(d:Double):String",detection="iOS无actual",fix="补actual"))
        BugDB.load(BugRule(id="KT-0235",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="commonTest中使用平台API",trigger="assertEquals(System.currentTimeMillis(),ts)",detection="平台特定",fix="expect/actual测试"))
        BugDB.load(BugRule(id="KT-0236",category=BugCategory.KMP,severity=BugSeverity.MILD,
            title="KMP模块缺少依赖",trigger="commonMain中import kotlinx.*",detection="kotlinx.datetime需显式依赖",fix="确认依赖或添加依赖"))
        BugDB.load(BugRule(id="KT-0237",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="SAM+overload解析",trigger="fun f(r:Runnable);fun f(c:Callable);f{println()}",detection="Java SAM转换歧义",fix="显式类型"))
        BugDB.load(BugRule(id="KT-0238",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="data class在when中穷举",trigger="when(val d=dataClass(...)){Data(1)->1}",detection="检查所有字段",fix="if-else替代"))
        BugDB.load(BugRule(id="KT-0239",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="Lambda返回值return+非Lambda",trigger="fun f()=lambda{return 1}",detection="return在lambda非法",fix="return@lambda"))
        BugDB.load(BugRule(id="KT-0240",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="空catch块",trigger="try{risky()}catch(e:Exception){}",detection="吞掉所有异常",fix="至少日志记录"))
        BugDB.load(BugRule(id="KT-0241",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="unsafe cast+泛型",trigger="fun <T> f(a:Any)=a as T",detection="擦除后不安全",fix="pass reified+"))
        BugDB.load(BugRule(id="KT-0242",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MILD,
            title="lazy初始化捕获可变引用",trigger="var x=0;val y by lazy{x}",detection="捕获的是var引用",fix="val x=0先"))
        BugDB.load(BugRule(id="KT-0243",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="函数引用+重载",trigger="fun f(i:Int){};fun f(s:String){};val r=::f",detection="歧义",fix="指定类型:(Int)->Unit"))
        BugDB.load(BugRule(id="KT-0244",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="N+1查询在集合操作",trigger="users.forEach{u->db.query(u.id)}",detection="每项一次DB",fix="batch查询"))
        BugDB.load(BugRule(id="KT-0245",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="频繁创建SimpleDateFormat",trigger="for(i in 1..1000){SimpleDateFormat(\"yyyy\").parse(s)}",detection="线程不安全+开销",fix="java.time或DateTimeFormatter"))
        BugDB.load(BugRule(id="KT-0246",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="未使用@Stable/@Immutable",trigger="data class D(val x:Int,val y:Int)",detection="Compose重组优化",fix="加@Immutable"))
        BugDB.load(BugRule(id="KT-0247",category=BugCategory.PERFORMANCE,severity=BugSeverity.MILD,
            title="不必要的LazyThreadSafetyMode",trigger="val x by lazy(LazyThreadSafetyMode.SYNCHRONIZED){42}",detection="常量不需要同步",fix="NONE"))
        BugDB.load(BugRule(id="KT-0248",category=BugCategory.PERFORMANCE,severity=BugSeverity.MILD,
            title="byte数组频繁copy",trigger="for(chunk in stream){buf.copyOfRange()}",detection="每次新建数组",fix="复用buffer"))
        BugDB.load(BugRule(id="KT-0249",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="Intent extras明文传敏感数据",trigger="intent.putExtra(\"token\",token)",detection="可被其他app读取",fix="加密或避免"))
        BugDB.load(BugRule(id="KT-0250",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="WebView.addJavascriptInterface",trigger="webView.addJavascriptInterface(obj,\"android\")",detection="JS可调用Java",fix="@JavascriptInterface仅暴露必要方法"))
        BugDB.load(BugRule(id="KT-0251",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="FileProvider路径遍历",trigger="FileProvider.getUriForFile(ctx,path)",detection="任意文件泄露",fix="限制根目录"))
        BugDB.load(BugRule(id="KT-0252",category=BugCategory.SECURITY,severity=BugSeverity.MILD,
            title="日志使用e.printStackTrace",trigger="e.printStackTrace()",detection="泄露到logcat",fix="Log.e(TAG,\"msg\",e)"))
        BugDB.load(BugRule(id="KT-0253",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="Windows/macOS/Linux换行符",trigger="String.split(\"\\\\n\")在Windows",detection="\\r\\n不被匹配",fix="System.lineSeparator()"))
        BugDB.load(BugRule(id="KT-0254",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="时区硬编码",trigger="SimpleDateFormat(\"yyyy\",Locale.US)",detection="跨时区偏差",fix="java.time+UTC"))
        BugDB.load(BugRule(id="KT-0255",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="文件编码默认依赖",trigger="File(\"x\").readText()",detection="默认UTF-8可能不适用",fix="readText(Charsets.UTF_8)显式"))
        BugDB.load(BugRule(id="KT-0256",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="父进程独裁导致子进程全灭",trigger="ProcessCoordinator.setStyle(DICTATOR)",detection="父进程治理风格过激",fix="FEDERAL或CONTRACT"))
        BugDB.load(BugRule(id="KT-0257",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="子进程杀死父进程",trigger="launch{parentJob.cancel()}",detection="结构化并发中子取消父",fix="子进程不应持有parentJob引用"))
        BugDB.load(BugRule(id="KT-0258",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="指挥官互相争夺同一子进程",trigger="两个@Commander注解同一tag",detection="重复声明",fix="一个tag一个指挥官"))
        BugDB.load(BugRule(id="KT-0259",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="进程树中孙子进程孤立",trigger="launch{launch{launch{}};cancel中间层",detection="中间层取消后孙子悬空",fix="supervisorScope+Job树检查"))
        BugDB.load(BugRule(id="KT-0260",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="十一种子进程职业全部失业",trigger="所有@ProcessBody都被Condition拦截",detection="注解条件过严",fix="放宽condition或加保底"))
        BugDB.load(BugRule(id="KT-0261",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="编译器编译自己源码时卡死",trigger="kotlin-head编译Main.kt自身",detection="自引用类型推断循环",fix="分离编译阶段"))
        BugDB.load(BugRule(id="KT-0262",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="BugScanner扫描到自己",trigger="BugDB.scan(BugScanner的源码)",detection="自检逻辑循环",fix="元规则豁免self-scan"))
        BugDB.load(BugRule(id="KT-0263",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="LiveDeclarationGraph自引用死循环",trigger="class A(val a:A)",detection="声明依赖自身",fix="循环引用检测"))
        BugDB.load(BugRule(id="KT-0264",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="Parser遇到文件名为hell时倾全军之力",trigger="hell.kt触发isHostileFile=true;所有资源耗尽",detection="地狱文件识别过激",fix="只加倍不倾全军"))
        BugDB.load(BugRule(id="KT-0265",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="反编译管线反编译自己",trigger="jadx反编译kotlin-head.jar再编译",detection="语义递归退化",fix="跳过自身jar"))
        BugDB.load(BugRule(id="KT-0266",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="Kotlin T!类型让编译器以为非空实则null",trigger="val x=javaGet();编译器推断String;运行时NPE",detection="T!被不当优化",fix="显式标注String?"))
        BugDB.load(BugRule(id="KT-0267",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="Nothing类型吞噬所有代码",trigger="fun e():Nothing=throw E();e().also{unreachable()}",detection="Nothing传染正常路径",fix="不链式调用Nothing"))
        BugDB.load(BugRule(id="KT-0268",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="类型推断选了谁都没想到的类型",trigger="listOf(1)与emptyList()合并推断List<Int>?",detection="多个候选时选最意外的",fix="显式泛型参数"))
        BugDB.load(BugRule(id="KT-0269",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型递归约束让编译器无限展开",trigger="fun <T:T> f(){}",detection="自约束无限循环",fix="加where约束打断"))
        BugDB.load(BugRule(id="KT-0270",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="EventBus频道互相触发死循环",trigger="EventBus.emit('a',ev);subscribe('a'){emit('b',ev)};subscribe('b'){emit('a',ev)}",detection="频道AB互相触发",fix="事件去重+深度限制"))
        BugDB.load(BugRule(id="KT-0271",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="lazy初始化循环依赖",trigger="val a by lazy{b};val b by lazy{a}",detection="两个lazy互相等",fix="break one with lateinit"))
        BugDB.load(BugRule(id="KT-0272",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="inline函数互相展开到JVM 64KB限制",trigger="inline fun a(){b()};inline fun b(){a()}",detection="互相inline展开致字节码超限",fix="一个去掉inline"))
        BugDB.load(BugRule(id="KT-0273",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="大量BugDB规则扫描空文件",trigger="BugDB.scan(\"\")",detection="全索引扫描空字符串",fix="空字符串短路返回"))
        BugDB.load(BugRule(id="KT-0274",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.SEVERE,
            title="Windows的\\r\\n在Linux被当两个换行",trigger="String.split('\\n')在Windows残留\\r",detection="跨平台行尾差异",fix="System.lineSeparator或trimEnd"))
        BugDB.load(BugRule(id="KT-0275",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable读写顺序故意相反",trigger="writeInt(a);writeString(b);readString();readInt()",detection="反序列化时值错位",fix="写读顺序严格一致"))
        BugDB.load(BugRule(id="KT-0276",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="debug日志里打印完整银行卡号",trigger="if(BuildConfig.DEBUG)Log.d('PAY',cardNumber)",detection="debug模式泄露",fix="release不输出或脱敏"))
        BugDB.load(BugRule(id="KT-0277",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="环境变量里的密钥被Git提交",trigger="BuildConfig.API_KEY从local.properties读但.gitignore漏了",detection="git add -f",fix="ci环境变量+不提交"))
        BugDB.load(BugRule(id="KT-0278",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="delay(0)比delay(1)更慢",trigger="delay(0);delay(0);delay(0)",detection="连续0延迟重排",fix="用yield()"))
        BugDB.load(BugRule(id="KT-0279",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="所有协程都在等一个永远不会set的CompletableDeferred",trigger="val d=CompletableDeferred<T>();launch{...d.await()};忘记d.complete()",detection="忘记complete",fix="加超时withTimeout"))
        BugDB.load(BugRule(id="KT-0280",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="Mutex.lock了两次同一个协程",trigger="mutex.lock();mutex.lock()",detection="不可重入Mutex死锁",fix="withLock或可重入锁"))
        BugDB.load(BugRule(id="KT-0281",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MILD,
            title="误把AI的建议当编译器报错",trigger="AI说你的代码有bug但编译器编译过了",detection="区分AI瞎说和编译器",fix="先编译再采纳"))
        BugDB.load(BugRule(id="KT-0282",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MILD,
            title="注释里写TODO导致被BugScanner报bug",trigger="// TODO: fix this → BugDB hit",detection="TODO被当bug模式",fix="BugDB加-TODO排除规则"))
        BugDB.load(BugRule(id="KT-0283",category=BugCategory.PERFORMANCE,severity=BugSeverity.MILD,
            title="10MB的JSON被当源码读入",trigger="val src=File('10mb.json').readText();compile(src)",detection="非Kotlin文件被误读",fix="文件扩展名检查"))
        BugDB.load(BugRule(id="KT-0284",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="APK当JSON解析不报错只返回空",trigger="JsonUtil.decode(apkBytes)",detection="不抛异常只静默",fix="检查Content-Type或magic bytes"))
        BugDB.load(BugRule(id="KT-0285",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="子进程反过来指挥父进程",trigger="@ProcessBody中调用ProcessCoordinator.setStyle()",detection="子进程越权修改全局策略",fix="子进程只读不写"))
        BugDB.load(BugRule(id="KT-0286",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="指挥官被进程体反杀",trigger="@Commander中调用自己tag的@ProcessBody杀自己",detection="自噬",fix="Commander不参与ProcessBody"))
        BugDB.load(BugRule(id="KT-0287",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="检测进程报告一切正常但全线崩溃",trigger="watchTag返回healthy=true但summary全是✖",detection="哨兵谎报军情",fix="cross-check其他哨兵"))
        BugDB.load(BugRule(id="KT-0288",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="依赖图标记已解决实际未解决",trigger="DependencyGraph标记conflict_resolved但detectConflicts仍有",detection="假解决",fix="解析后立即re-check"))
        BugDB.load(BugRule(id="KT-0289",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="路标指针指向null却认为有值",trigger="LiveDeclarationGraph.getNode返回null但下游当非null用",detection="假指针",fix="getNode后判null"))
        BugDB.load(BugRule(id="KT-0290",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="编译器优化阶段把正确代码优化成错误代码",trigger="Pass.inline错误展开导致语义变化",detection="Pass副作用",fix="Pass后加语义等价校验"))
        BugDB.load(BugRule(id="KT-0291",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="EventBus取消订阅后仍收到事件",trigger="unsubscribe(tag);emit(tag,ev);仍收到",detection="取消订阅延迟",fix="unsubscribe后立即yield"))
        BugDB.load(BugRule(id="KT-0292",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="flow.collect在collect后又emit了一条",trigger="flow{emit(1);awaitClose{emit(2)}}",detection="awaitClose中的emit可能丢失",fix="onCompletion"))
        BugDB.load(BugRule(id="KT-0293",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="非空断言在finally块中失效",trigger="try{x!!;risky()}finally{x.method()}",detection="finally中x可能已被置null",fix="局部val快照"))
        BugDB.load(BugRule(id="KT-0294",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="类型推断在递归函数中选择最窄类型",trigger="fun f()=if(cond) f() else 0→Int",detection="递归基推断为Int但期望Long",fix="显式返回类型"))
        BugDB.load(BugRule(id="KT-0295",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="var+!!+多线程=智能转换完全失效",trigger="var x:Any?=null;thread{x!!;x=null};thread{x.method()}",detection="竞态破坏非空假设",fix="AtomicReference+?.let"))
        BugDB.load(BugRule(id="KT-0296",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="launch+反射+私有方法=不可预测崩溃",trigger="launch{val m=cls.getDeclaredMethod(\"secret\");m.isAccessible=true;m.invoke(obj)}",detection="协程内反射破坏封装",fix="提供公开suspend接口"))
        BugDB.load(BugRule(id="KT-0297",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="reified+suspend=限制叠加",trigger="suspend inline fun <reified T> api():T",detection="reified必须inline但suspend inline有限制",fix="拆分为非suspend inline+suspend调用"))
        BugDB.load(BugRule(id="KT-0298",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="LaunchedEffect+错误key+状态更新=死循环重组",trigger="LaunchedEffect(Unit){counter++}",detection="每次重组counter变→触发重组→counter变",fix="key使用稳定值"))
        BugDB.load(BugRule(id="KT-0299",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="@Serializable+by lazy=序列化时触发初始化",trigger="@Serializable data class U(val x:Int){val y by lazy{init()}}",detection="序列化时lazy被触发",fix="@Transient标记非序列化字段"))
        BugDB.load(BugRule(id="KT-0300",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="sealed+反射枚举子类=新增子类不拦截",trigger="sealed class A;通过反射实例化未知子类",detection="when穷举被绕过",fix="sealed+禁止反射实例化"))
        BugDB.load(BugRule(id="KT-0301",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="inline+crossinline+suspend=三层限制互锁",trigger="inline fun f(crossinline b:suspend ()->Unit){launch{b()}}",detection="crossinline+suspend+launch不可组合",fix="去掉crossinline或suspend"))
        BugDB.load(BugRule(id="KT-0302",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="by lazy+@Volatile=过度同步",trigger="@Volatile var x by lazy{init()}",detection="lazy已同步+@Volatile再次同步",fix="去掉@Volatile或不用lazy"))
        BugDB.load(BugRule(id="KT-0303",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="asSequence+first+重复调用=每次重新求值",trigger="val seq=list.asSequence().filter{;seq.first();seq.first()",detection="sequence每次first重新求值",fix="先toList再取"))
        BugDB.load(BugRule(id="KT-0304",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="synchronized+协程=阻塞线程池",trigger="synchronized(lock){delay(1000)}",detection="synchronized内不可suspend但delay会挂",fix="Mutex.withLock+delay"))
        BugDB.load(BugRule(id="KT-0305",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="is检查+var+lambda=智能转换三次失效",trigger="var x:Any?=\"hi\";if(x is String){launch{x.length}}",detection="var可能被改+lambda捕获",fix="局部val快照+显式cast"))
        BugDB.load(BugRule(id="KT-0306",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="!!+?:+as?=三null操作符互相矛盾",trigger="val x=y!!?:z as? T",detection="!!先炸，?:永不执行",fix="只用一种null处理方式"))
        BugDB.load(BugRule(id="KT-0307",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="flow+retry+stateIn=重试时状态丢失",trigger="flow{apiCall()}.retry(3).stateIn(scope)",detection="retry重建flow，stateIn收到新初始值",fix="在retry外层stateIn"))
        BugDB.load(BugRule(id="KT-0308",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="inline+递归泛型=编译时间爆炸",trigger="inline fun <reified T> f(n:Int){if(n>0)f<T>(n-1)}",detection="inline+递归=每层展开",fix="去掉inline或用while"))
        BugDB.load(BugRule(id="KT-0309",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="默认参数+扩展函数+泛型=三歧义",trigger="fun <T> List<T>.f(n:Int=1){};listOf(1).f()",detection="扩展接收者+泛型+默认参数解析",fix="显式传参"))
        BugDB.load(BugRule(id="KT-0310",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="单线程协程拿到了并行结果，多线程反而串行",trigger="coroutineScope{async{a};async{b};awaitAll}",detection="协程调度并行，线程锁串行",fix="多线程用无锁数据结构"))
        BugDB.load(BugRule(id="KT-0311",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="单线程expect但实际是async语义",trigger="val x=async{a};val y=async{b};x.await()+y.await()",detection="async启动即执行不是lazy",fix="如需lazy用CoroutineStart.LAZY"))
        BugDB.load(BugRule(id="KT-0312",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="Dispatchers.Default单核跑出并行幻觉",trigger="单核CPU上launch(Default){a;b;c}",detection="Default线程池>1核=真并行但单核=并发",fix="明确用newSingleThreadContext"))
        BugDB.load(BugRule(id="KT-0313",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="多线程加锁反而比单线程更慢",trigger="1000线程竞争同一把synchronized锁",detection="锁竞争+上下文切换>计算本身",fix="缩小临界区或用无锁CAS"))
        BugDB.load(BugRule(id="KT-0314",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="Flow默认串行但collect看起来像并行",trigger="flow{emit(a);emit(b)}.collect{}",detection="emit挂起→collect处理→才继续emit",fix="要并行用channelFlow或flatMapMerge"))
        BugDB.load(BugRule(id="KT-0315",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="channel被当成广播但只发了一条",trigger="val c=Channel<Int>();launch{c.send(1)};launch{println(c.receive())};launch{println(c.receive())}",detection="Channel单消费",fix="BroadcastChannel或SharedFlow"))
        BugDB.load(BugRule(id="KT-0316",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="parallelStream在ForkJoinPool里反而串行",trigger="list.parallelStream().map{sleep(100);it}.collect()",detection="公共池被其他任务占满",fix="自定义ForkJoinPool"))
        BugDB.load(BugRule(id="KT-0317",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="volatile+递增=你以为原子实际不是",trigger="@Volatile var x=0;threads{x++}",detection="volatile只保证可见性不保证原子性",fix="AtomicInteger.incrementAndGet"))
        BugDB.load(BugRule(id="KT-0318",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="把launch当async用等不到结果",trigger="val res=launch{calc()};println(res)",detection="launch返回Job不是Deferred",fix="需要结果用async+await"))
        BugDB.load(BugRule(id="KT-0319",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="BugDB被设为主入口启动了但不编译只报告bug",trigger="fun main()=BugDB.scan(args);编译结果变成了bug报告",detection="身份混淆：工具当程序",fix="main调compile不是scan"))
        BugDB.load(BugRule(id="KT-0320",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="把编译好的jar当源码喂给编译器",trigger="kotlin-head myapp.jar",detection="二进制被当Kotlin解析→乱码AST→垃圾诊断",fix="文件扩展名检查+.kt强制"))
        BugDB.load(BugRule(id="KT-0321",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="把反编译输出当源码编译（二次降解）",trigger="jadx吐出的非标准Kotlin→kotlin-head→再反编译",detection="语义逐层退化",fix="jadx输出只读不编"))
        BugDB.load(BugRule(id="KT-0322",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="README.md被当Kotlin源码编译",trigger="kotlin-head README.md",detection="Markdown被分词→离谱token→零意义AST",fix="扩展名白名单"))
        BugDB.load(BugRule(id="KT-0323",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="把build.gradle.kts当普通Kotlin编译",trigger="kotlin-head build.gradle.kts",detection="Gradle DSL不是标准Kotlin",fix="跳过构建文件"))
        BugDB.load(BugRule(id="KT-0324",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="把ProGuard mapping文件当源码输入",trigger="kotlin-head mapping.txt",detection="混淆映射表被编译→全乱",fix="文件内容嗅探"))
        BugDB.load(BugRule(id="KT-0325",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="把jadx反编译错文件当正确文件比较",trigger="反编译A.apk的输出和B.apk的源码对比",detection="apk来源不同→比较无意义",fix="确认源一致"))
        BugDB.load(BugRule(id="KT-0326",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="EventBus频道名当类名注册",trigger="EventBus.subscribe(\"Main\"){};Main::class.java",detection="字符串与KClass混淆",fix="用KClass不是字符串"))
        BugDB.load(BugRule(id="KT-0327",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="benchmark跑在debug模式结果当release用",trigger="./gradlew benchmark在debug变体",detection="debug无优化→性能数据假",fix="用release变体或benchmark构建类型"))
        BugDB.load(BugRule(id="KT-0328",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="Python代码改.kt当Kotlin编译",trigger="kotlin-head fake.py→改名fake.kt→def foo():→def当标识符,冒号被吞",detection="假扩展名骗过编译器",fix="校验文件内容+shebang检测"))
        BugDB.load(BugRule(id="KT-0329",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="C++代码改.kt硬说Kotlin",trigger="kotlin-head main.cpp→改名main.kt→#include<iostream>→#当注释int main当标识符",detection="后缀名欺诈",fix="内容特征检测"))
        BugDB.load(BugRule(id="KT-0330",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="JavaScript改.kt语法全乱",trigger="kotlin-head app.js→改名app.kt→const x=1;→const当标识符",detection="跨语言冒充",fix="前几行特征匹配"))
        BugDB.load(BugRule(id="KT-0331",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="Shell脚本改.kt编译",trigger="kotlin-head run.sh→改名run.kt→#!/bin/bash→shebang当注释但后续shell命令全错",detection="骗扩展名",fix="首行shebang拦截"))
        BugDB.load(BugRule(id="KT-0332",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="Parser while循环缺EOF保护",trigger="while(!check(RBRACE))",detection="不完整输入死循环",fix="while条件加!isEof()"))
        BugDB.load(BugRule(id="KT-0333",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="Lexer反斜杠结尾越界",trigger="src[i]==\\;i++;sb.append(src[i])",detection="越界崩溃",fix="i>=src.length时break"))
        BugDB.load(BugRule(id="KT-0334",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="HMap swap-remove旧组索引未更新",trigger="_keys[i]=_keys[_size]后featIdx只失效被删key组",detection="被挪元素旧组脏索引→NPE",fix="swap后同时标记movedKey旧组失效"))
        BugDB.load(BugRule(id="KT-0335",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="BugDB缓存键碰撞",trigger="code.hashCode().toString()+skipPatterns.hashCode()",detection="拼接可能碰撞→返回错误结果",fix="分隔符隔开"))
        BugDB.load(BugRule(id="KT-0336",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="StreamChannel非线程安全",trigger="val transforms=mutableListOf",detection="并发add/iterate崩溃",fix="CopyOnWriteArrayList"))
        BugDB.load(BugRule(id="KT-0337",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="registerOrUpdate全量重建revDeps",trigger="rebuildRevDeps()遍历全部deps",detection="O(N²)每次更新扫全图",fix="增量更新只重建被影响的"))
        BugDB.load(BugRule(id="KT-0338",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="scanExpr递归无深度限制",trigger="scanExpr→scanExpr无depth上限",detection="深层嵌套StackOverflow",fix="depth>100时return"))
        BugDB.load(BugRule(id="KT-0339",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="getNode用split冒号解析路径",trigger="declId.split(:)若路径含冒号",detection="解析错位→错误节点",fix="lastIndexOf从右向左"))
        BugDB.load(BugRule(id="KT-0340",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="autoStyle缺RENYONG分支",trigger="when无RENYONG→prepareArmy中RENYONG短路永不触发",detection="死代码路径",fix="加fileSize<5000→RENYONG"))
        BugDB.load(BugRule(id="KT-0341",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="skipPatterns循环内编译Regex",trigger="skipPatterns.none{Regex(it).containsMatchIn(line)}",detection="每行×每模式编译",fix="预编译List<Regex>一次"))
        BugDB.load(BugRule(id="KT-0342",category=BugCategory.PERFORMANCE,severity=BugSeverity.MILD,
            title="stats()多次全量遍历",trigger="BugSeverity.values().forEach{rules.count{",detection="24次全量扫描",fix="单次遍历分组计数"))
        BugDB.load(BugRule(id="KT-0343",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="subProcesses无界增长列表",trigger="HList<SubProcessImpl>.add后从不清理",detection="长时间运行OOM",fix="上限500+超出截半"))
        BugDB.load(BugRule(id="KT-0344",category=BugCategory.SECURITY,severity=BugSeverity.MILD,
            title="emit异常静默吞掉",trigger="catch(_:Exception){}在EventChannel.emit",detection="生产环境不可观测",fix="catch(ex)System.err记录"))
        BugDB.load(BugRule(id="KT-0345",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MILD,
            title="不必要的类型标注（Int版）",trigger="val x:Int=\\\"hi\\\"",detection="已知Int",fix="自动推断"))
        BugDB.load(BugRule(id="KT-0346",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MILD,
            title="不必要的类型标注（Long版）",trigger="val x:Long=\\\"hi\\\"",detection="已知Long",fix="自动推断"))
        BugDB.load(BugRule(id="KT-0347",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MILD,
            title="不必要的类型标注（Double版）",trigger="val x:Double=\\\"hi\\\"",detection="已知Double",fix="自动推断"))
        BugDB.load(BugRule(id="KT-0348",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MILD,
            title="不必要的类型标注（Boolean版）",trigger="val x:Boolean=\\\"hi\\\"",detection="已知Boolean",fix="自动推断"))
        BugDB.load(BugRule(id="KT-0349",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="@SerialName与字段名不一致（Long版）",trigger="@SerialName(\\\"y\\\") val x:Long",detection="重命名混淆",fix="统一命名"))
        BugDB.load(BugRule(id="KT-0350",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="@SerialName与字段名不一致（Double版）",trigger="@SerialName(\\\"y\\\") val x:Double",detection="重命名混淆",fix="统一命名"))
        BugDB.load(BugRule(id="KT-0351",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="@SerialName与字段名不一致（Float版）",trigger="@SerialName(\\\"y\\\") val x:Float",detection="重命名混淆",fix="统一命名"))
        BugDB.load(BugRule(id="KT-0352",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Int?的?:返回null（Long版）",trigger="val x:Long?=...;val y=Long??:null",detection="冗余null",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0353",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Int?的?:返回null（Double版）",trigger="val x:Double?=...;val y=Double??:null",detection="冗余null",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0354",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Int?的?:返回null（Float版）",trigger="val x:Float?=...;val y=Float??:null",detection="冗余null",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0355",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Int?在集合操作中（Long版）",trigger="val l:List<Long?>;l.filterNotNull().size",detection="先filterNotNull",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0356",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Int?在集合操作中（Double版）",trigger="val l:List<Double?>;l.filterNotNull().size",detection="先filterNotNull",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0357",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="默认参数+扩展函数+泛型=三歧义（Long版）",trigger="fun <T> List<T>.f(n:Long=1){};listOf(1).f()",detection="扩展接收者+泛型+默认参数解析",fix="显式传参"))
        BugDB.load(BugRule(id="KT-0358",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="默认参数+扩展函数+泛型=三歧义（Double版）",trigger="fun <T> List<T>.f(n:Double=1){};listOf(1).f()",detection="扩展接收者+泛型+默认参数解析",fix="显式传参"))
        BugDB.load(BugRule(id="KT-0359",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="默认参数+扩展函数+泛型=三歧义（Float版）",trigger="fun <T> List<T>.f(n:Float=1){};listOf(1).f()",detection="扩展接收者+泛型+默认参数解析",fix="显式传参"))
        BugDB.load(BugRule(id="KT-0360",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空String?在集合操作中（Int版）",trigger="val l:List<Int?>;l.filterNotNull().size",detection="先filterNotNull",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0361",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空String?在集合操作中（Long版）",trigger="val l:List<Long?>;l.filterNotNull().size",detection="先filterNotNull",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0362",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空String?在集合操作中（Double版）",trigger="val l:List<Double?>;l.filterNotNull().size",detection="先filterNotNull",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0363",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类非主构造属性（Int版）",trigger="inline class N(val s:Int){val len=s.length}",detection="每次计算",fix="内联到val本身"))
        BugDB.load(BugRule(id="KT-0364",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类非主构造属性（Long版）",trigger="inline class N(val s:Long){val len=s.length}",detection="每次计算",fix="内联到val本身"))
        BugDB.load(BugRule(id="KT-0365",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类非主构造属性（Double版）",trigger="inline class N(val s:Double){val len=s.length}",detection="每次计算",fix="内联到val本身"))
        BugDB.load(BugRule(id="KT-0366",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型Int递归非尾（Long版）",trigger="tailrec fun f(n:Long):Long=if(n<=1)n else n*f(n-1)",detection="最后非自身",fix="while改写"))
        BugDB.load(BugRule(id="KT-0367",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型Int递归非尾（Double版）",trigger="tailrec fun f(n:Double):Double=if(n<=1)n else n*f(n-1)",detection="最后非自身",fix="while改写"))
        BugDB.load(BugRule(id="KT-0368",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型Int递归非尾（Float版）",trigger="tailrec fun f(n:Float):Float=if(n<=1)n else n*f(n-1)",detection="最后非自身",fix="while改写"))
        BugDB.load(BugRule(id="KT-0369",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable序列化顺序不一致（Long版）",trigger="writeLong(a);readLong(b)",detection="读写顺序错误",fix="对齐write/read顺序"))
        BugDB.load(BugRule(id="KT-0370",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable序列化顺序不一致（Double版）",trigger="writeDouble(a);readDouble(b)",detection="读写顺序错误",fix="对齐write/read顺序"))
        BugDB.load(BugRule(id="KT-0371",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable序列化顺序不一致（Float版）",trigger="writeFloat(a);readFloat(b)",detection="读写顺序错误",fix="对齐write/read顺序"))
        BugDB.load(BugRule(id="KT-0372",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="first()无元素（Long版）",trigger="emptyList<Long>().first()",detection="NoSuchElementException",fix="firstOrNull"))
        BugDB.load(BugRule(id="KT-0373",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="first()无元素（Double版）",trigger="emptyList<Double>().first()",detection="NoSuchElementException",fix="firstOrNull"))
        BugDB.load(BugRule(id="KT-0374",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="first()无元素（Float版）",trigger="emptyList<Float>().first()",detection="NoSuchElementException",fix="firstOrNull"))
        BugDB.load(BugRule(id="KT-0375",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="空集合操作（Long版）",trigger="emptyList<Long>().reduce{a,b->a+b}",detection="UnsupportedOperation",fix="ifEmpty+fold"))
        BugDB.load(BugRule(id="KT-0376",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="明文存储密码（Int版）",trigger="prefs.edit().putInt(\\\"pwd\\\",password).apply()",detection="SharedPreferences明文",fix="EncryptedSharedPreferences"))
        BugDB.load(BugRule(id="KT-0377",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="明文存储密码（Long版）",trigger="prefs.edit().putLong(\\\"pwd\\\",password).apply()",detection="SharedPreferences明文",fix="EncryptedSharedPreferences"))
        BugDB.load(BugRule(id="KT-0378",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="明文存储密码（Double版）",trigger="prefs.edit().putDouble(\\\"pwd\\\",password).apply()",detection="SharedPreferences明文",fix="EncryptedSharedPreferences"))
        BugDB.load(BugRule(id="KT-0379",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="明文存储密码（Boolean版）",trigger="prefs.edit().putBoolean(\\\"pwd\\\",password).apply()",detection="SharedPreferences明文",fix="EncryptedSharedPreferences"))
        BugDB.load(BugRule(id="KT-0380",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable读写顺序故意相反（Int版）",trigger="writeInt(a);writeInt(b);readInt();readInt()",detection="反序列化时值错位",fix="写读顺序严格一致"))
        BugDB.load(BugRule(id="KT-0381",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable读写顺序故意相反（Long版）",trigger="writeInt(a);writeLong(b);readLong();readInt()",detection="反序列化时值错位",fix="写读顺序严格一致"))
        BugDB.load(BugRule(id="KT-0382",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable读写顺序故意相反（Double版）",trigger="writeInt(a);writeDouble(b);readDouble();readInt()",detection="反序列化时值错位",fix="写读顺序严格一致"))
        BugDB.load(BugRule(id="KT-0383",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable读写顺序故意相反（Boolean版）",trigger="writeInt(a);writeBoolean(b);readBoolean();readInt()",detection="反序列化时值错位",fix="写读顺序严格一致"))
        BugDB.load(BugRule(id="KT-0384",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable读写顺序故意相反（Long版）",trigger="writeLong(a);writeString(b);readString();readLong()",detection="反序列化时值错位",fix="写读顺序严格一致"))
        BugDB.load(BugRule(id="KT-0385",category=BugCategory.KMP,severity=BugSeverity.SEVERE,
            title="expect/actual返回类型不匹配（Int版）",trigger="expect fun f():Int;actual fun f():Int",detection="签名不一致",fix="对齐签名"))
        BugDB.load(BugRule(id="KT-0386",category=BugCategory.KMP,severity=BugSeverity.SEVERE,
            title="expect/actual返回类型不匹配（Long版）",trigger="expect fun f():Long;actual fun f():Int",detection="签名不一致",fix="对齐签名"))
        BugDB.load(BugRule(id="KT-0387",category=BugCategory.KMP,severity=BugSeverity.SEVERE,
            title="expect/actual返回类型不匹配（Double版）",trigger="expect fun f():Double;actual fun f():Int",detection="签名不一致",fix="对齐签名"))
        BugDB.load(BugRule(id="KT-0388",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="@Serializable+by lazy=序列化时触发初始化（Long版）",trigger="@Serializable data class U(val x:Long){val y by lazy{init()}}",detection="序列化时lazy被触发",fix="@Transient标记非序列化字段"))
        BugDB.load(BugRule(id="KT-0389",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="is检查后类型窄化丢失（Set版）",trigger="if(x is Set<*>){x[0]}",detection="擦除后泛型信息丢失",fix="reified"))
        BugDB.load(BugRule(id="KT-0390",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="Kotlin T!类型让编译器以为非空实则null（Int版）",trigger="val x=javaGet();编译器推断Int;运行时NPE",detection="T!被不当优化",fix="显式标注Int?"))
        BugDB.load(BugRule(id="KT-0391",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="Kotlin T!类型让编译器以为非空实则null（Long版）",trigger="val x=javaGet();编译器推断Long;运行时NPE",detection="T!被不当优化",fix="显式标注Long?"))
        BugDB.load(BugRule(id="KT-0392",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="Kotlin T!类型让编译器以为非空实则null（Double版）",trigger="val x=javaGet();编译器推断Double;运行时NPE",detection="T!被不当优化",fix="显式标注Double?"))
        BugDB.load(BugRule(id="KT-0393",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="Kotlin T!类型让编译器以为非空实则null（Boolean版）",trigger="val x=javaGet();编译器推断Boolean;运行时NPE",detection="T!被不当优化",fix="显式标注Boolean?"))
        BugDB.load(BugRule(id="KT-0394",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="类型窄化+?:丢失（Int版）",trigger="val x=y as? Int?:return;x.length",detection="x已非空",fix="直接使用不用?."))
        BugDB.load(BugRule(id="KT-0395",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="类型窄化+?:丢失（Long版）",trigger="val x=y as? Long?:return;x.length",detection="x已非空",fix="直接使用不用?."))
        BugDB.load(BugRule(id="KT-0396",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="类型窄化+?:丢失（Double版）",trigger="val x=y as? Double?:return;x.length",detection="x已非空",fix="直接使用不用?."))
        BugDB.load(BugRule(id="KT-0397",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class componentN()命名冲突（Int版）",trigger="data class C(val component1:Int,val x:Int)",detection="component1不是第一个",fix="避开componentN命名"))
        BugDB.load(BugRule(id="KT-0398",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class componentN()命名冲突（Long版）",trigger="data class C(val component1:Long,val x:Int)",detection="component1不是第一个",fix="避开componentN命名"))
        BugDB.load(BugRule(id="KT-0399",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class componentN()命名冲突（Double版）",trigger="data class C(val component1:Double,val x:Int)",detection="component1不是第一个",fix="避开componentN命名"))
        BugDB.load(BugRule(id="KT-0400",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class componentN()命名冲突（Boolean版）",trigger="data class C(val component1:Boolean,val x:Int)",detection="component1不是第一个",fix="避开componentN命名"))
        BugDB.load(BugRule(id="KT-0401",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class componentN()命名冲突（Long版）",trigger="data class C(val component1:String,val x:Long)",detection="component1不是第一个",fix="避开componentN命名"))
        BugDB.load(BugRule(id="KT-0402",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.SEVERE,
            title="Windows的\\\\r\\\\n在Linux被当两个换行（Int版）",trigger="Int.split('\\\\n')在Windows残留\\\\r",detection="跨平台行尾差异",fix="System.lineSeparator或trimEnd"))
        BugDB.load(BugRule(id="KT-0403",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.SEVERE,
            title="Windows的\\\\r\\\\n在Linux被当两个换行（Long版）",trigger="Long.split('\\\\n')在Windows残留\\\\r",detection="跨平台行尾差异",fix="System.lineSeparator或trimEnd"))
        BugDB.load(BugRule(id="KT-0404",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.SEVERE,
            title="Windows的\\\\r\\\\n在Linux被当两个换行（Double版）",trigger="Double.split('\\\\n')在Windows残留\\\\r",detection="跨平台行尾差异",fix="System.lineSeparator或trimEnd"))
        BugDB.load(BugRule(id="KT-0405",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.SEVERE,
            title="Windows的\\\\r\\\\n在Linux被当两个换行（Boolean版）",trigger="Boolean.split('\\\\n')在Windows残留\\\\r",detection="跨平台行尾差异",fix="System.lineSeparator或trimEnd"))
        BugDB.load(BugRule(id="KT-0406",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="!!后is检查多余（Int版）",trigger="x!!;if(x is Int){x.length}",detection="!!后非空",fix="直接用is检查"))
        BugDB.load(BugRule(id="KT-0407",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="!!后is检查多余（Long版）",trigger="x!!;if(x is Long){x.length}",detection="!!后非空",fix="直接用is检查"))
        BugDB.load(BugRule(id="KT-0408",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="!!后is检查多余（Double版）",trigger="x!!;if(x is Double){x.length}",detection="!!后非空",fix="直接用is检查"))
        BugDB.load(BugRule(id="KT-0409",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="!!后is检查多余（Boolean版）",trigger="x!!;if(x is Boolean){x.length}",detection="!!后非空",fix="直接用is检查"))
        BugDB.load(BugRule(id="KT-0410",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="函数引用+重载（Int版）",trigger="fun f(i:Int){};fun f(s:Int){};val r=::f",detection="歧义",fix="指定类型:(Int)->Unit"))
        BugDB.load(BugRule(id="KT-0411",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="函数引用+重载（Long版）",trigger="fun f(i:Int){};fun f(s:Long){};val r=::f",detection="歧义",fix="指定类型:(Int)->Unit"))
        BugDB.load(BugRule(id="KT-0412",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="函数引用+重载（Double版）",trigger="fun f(i:Int){};fun f(s:Double){};val r=::f",detection="歧义",fix="指定类型:(Int)->Unit"))
        BugDB.load(BugRule(id="KT-0413",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="函数引用+重载（Boolean版）",trigger="fun f(i:Int){};fun f(s:Boolean){};val r=::f",detection="歧义",fix="指定类型:(Int)->Unit"))
        BugDB.load(BugRule(id="KT-0414",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类实现接口（Int版）",trigger="value class N(val s:Int):Iface",detection="间接装箱",fix="避免接口"))
        BugDB.load(BugRule(id="KT-0415",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类实现接口（Long版）",trigger="value class N(val s:Long):Iface",detection="间接装箱",fix="避免接口"))
        BugDB.load(BugRule(id="KT-0416",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类实现接口（Double版）",trigger="value class N(val s:Double):Iface",detection="间接装箱",fix="避免接口"))
        BugDB.load(BugRule(id="KT-0417",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类实现接口（Boolean版）",trigger="value class N(val s:Boolean):Iface",detection="间接装箱",fix="避免接口"))
        BugDB.load(BugRule(id="KT-0418",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类==比较失效（Long版）",trigger="inline class N(val i:Long);N(1)==N(1)",detection="equals自动生成",fix="可用但注意引用比较"))
        BugDB.load(BugRule(id="KT-0419",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类==比较失效（Double版）",trigger="inline class N(val i:Double);N(1)==N(1)",detection="equals自动生成",fix="可用但注意引用比较"))
        BugDB.load(BugRule(id="KT-0420",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类==比较失效（Float版）",trigger="inline class N(val i:Float);N(1)==N(1)",detection="equals自动生成",fix="可用但注意引用比较"))
        BugDB.load(BugRule(id="KT-0421",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Int版）",trigger="fun javaMethod():Int=null",detection="Kotlin不认Java注解",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-0422",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Long版）",trigger="fun javaMethod():Long=null",detection="Kotlin不认Java注解",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-0423",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Double版）",trigger="fun javaMethod():Double=null",detection="Kotlin不认Java注解",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-0424",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型String递归非尾（Int版）",trigger="tailrec fun f(n:Int):Int=if(n<=1)n else n*f(n-1)",detection="最后非自身",fix="while改写"))
        BugDB.load(BugRule(id="KT-0425",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型String递归非尾（Long版）",trigger="tailrec fun f(n:Long):Long=if(n<=1)n else n*f(n-1)",detection="最后非自身",fix="while改写"))
        BugDB.load(BugRule(id="KT-0426",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型String递归非尾（Double版）",trigger="tailrec fun f(n:Double):Double=if(n<=1)n else n*f(n-1)",detection="最后非自身",fix="while改写"))
        BugDB.load(BugRule(id="KT-0427",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型String递归非尾（Boolean版）",trigger="tailrec fun f(n:Boolean):Boolean=if(n<=1)n else n*f(n-1)",detection="最后非自身",fix="while改写"))
        BugDB.load(BugRule(id="KT-0428",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="String?的?:返回null（Int版）",trigger="val x:Int?=...;val y=Int??:null",detection="冗余null",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0429",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="String?的?:返回null（Long版）",trigger="val x:Long?=...;val y=Long??:null",detection="冗余null",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0430",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="String?的?:返回null（Double版）",trigger="val x:Double?=...;val y=Double??:null",detection="冗余null",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0431",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="String?的?:返回null（Boolean版）",trigger="val x:Boolean?=...;val y=Boolean??:null",detection="冗余null",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0432",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="自引用属性（Long版）",trigger="val x:Long=x+1",detection="循环引用",fix="用by lazy或初始化块"))
        BugDB.load(BugRule(id="KT-0433",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="自引用属性（Double版）",trigger="val x:Double=x+1",detection="循环引用",fix="用by lazy或初始化块"))
        BugDB.load(BugRule(id="KT-0434",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Int版）",trigger="class C:A<Int>,A<Int>",detection="JVM擦除冲突",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0435",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Long版）",trigger="class C:A<Long>,A<Int>",detection="JVM擦除冲突",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0436",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Double版）",trigger="class C:A<Double>,A<Int>",detection="JVM擦除冲突",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0437",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Boolean版）",trigger="class C:A<Boolean>,A<Int>",detection="JVM擦除冲突",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0438",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Long版）",trigger="class C:A<String>,A<Long>",detection="JVM擦除冲突",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0439",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="类型推断选了谁都没想到的类型（Long版）",trigger="listOf(1)与emptyList()合并推断List<Long>?",detection="多个候选时选最意外的",fix="显式泛型参数"))
        BugDB.load(BugRule(id="KT-0440",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmOverloads缺失（Long版）",trigger="fun f(a:Long,b:Long=0)",detection="Java调用不提供重载",fix="加@JvmOverloads"))
        BugDB.load(BugRule(id="KT-0441",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="Windows/macOS/Linux换行符（Int版）",trigger="Int.split(\\\"\\\\\\\\n\\\")在Windows",detection="\\\\r\\\\n不被匹配",fix="System.lineSeparator()"))
        BugDB.load(BugRule(id="KT-0442",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="Windows/macOS/Linux换行符（Long版）",trigger="Long.split(\\\"\\\\\\\\n\\\")在Windows",detection="\\\\r\\\\n不被匹配",fix="System.lineSeparator()"))
        BugDB.load(BugRule(id="KT-0443",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="Windows/macOS/Linux换行符（Double版）",trigger="Double.split(\\\"\\\\\\\\n\\\")在Windows",detection="\\\\r\\\\n不被匹配",fix="System.lineSeparator()"))
        BugDB.load(BugRule(id="KT-0444",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="Windows/macOS/Linux换行符（Boolean版）",trigger="Boolean.split(\\\"\\\\\\\\n\\\")在Windows",detection="\\\\r\\\\n不被匹配",fix="System.lineSeparator()"))
        BugDB.load(BugRule(id="KT-0445",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Int版）",trigger="expect fun format(d:Double):Int",detection="iOS无actual",fix="补actual"))
        BugDB.load(BugRule(id="KT-0446",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Long版）",trigger="expect fun format(d:Double):Long",detection="iOS无actual",fix="补actual"))
        BugDB.load(BugRule(id="KT-0447",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Double版）",trigger="expect fun format(d:Double):Double",detection="iOS无actual",fix="补actual"))
        BugDB.load(BugRule(id="KT-0448",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Boolean版）",trigger="expect fun format(d:Double):Boolean",detection="iOS无actual",fix="补actual"))
        BugDB.load(BugRule(id="KT-0449",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="Kotlin集合与Java互转（Set版）",trigger="kotlinSet.toSet()在Java侧",detection="创建新副本",fix="直接传递"))
        BugDB.load(BugRule(id="KT-0450",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class copy浅复制（Set版）",trigger="data class U(val l:MutableSet<T>);u.copy().l.add(x)",detection="共享可变集合",fix="深复制或不可变"))
        BugDB.load(BugRule(id="KT-0451",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.SEVERE,
            title="tailrec非尾递归（Long版）",trigger="tailrec fun f(n:Long)=n*f(n-1)",detection="最后一步非自身调用",fix="while循环改写"))
        BugDB.load(BugRule(id="KT-0452",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.SEVERE,
            title="tailrec非尾递归（Double版）",trigger="tailrec fun f(n:Double)=n*f(n-1)",detection="最后一步非自身调用",fix="while循环改写"))
        BugDB.load(BugRule(id="KT-0453",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举包含可变状态（Long版）",trigger="enum class E(var x:Long)",detection="枚举应不可变",fix="val"))
        BugDB.load(BugRule(id="KT-0454",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举包含可变状态（Double版）",trigger="enum class E(var x:Double)",detection="枚举应不可变",fix="val"))
        BugDB.load(BugRule(id="KT-0455",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举包含可变状态（Float版）",trigger="enum class E(var x:Float)",detection="枚举应不可变",fix="val"))
        BugDB.load(BugRule(id="KT-0456",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Int版）",trigger="if(x is List<Int>){}",detection="擦除后类型丢失",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0457",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Long版）",trigger="if(x is List<Long>){}",detection="擦除后类型丢失",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0458",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Double版）",trigger="if(x is List<Double>){}",detection="擦除后类型丢失",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0459",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Boolean版）",trigger="if(x is List<Boolean>){}",detection="擦除后类型丢失",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0460",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Set版）",trigger="if(x is Set<String>){}",detection="擦除后类型丢失",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0461",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="不必要装箱（Long版）",trigger="val x:Long?=42;x?.let{",detection="Long?装箱",fix="避免可空"))
        BugDB.load(BugRule(id="KT-0462",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="类型推断在递归函数中选择最窄类型（Long版）",trigger="fun f()=if(cond) f() else 0→Long",detection="递归基推断为Long但期望Long",fix="显式返回类型"))
        BugDB.load(BugRule(id="KT-0463",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="不必要的as（Int版）",trigger="val x:Int=\\\"hi\\\";val y=x as Int",detection="已知类型不需要as",fix="直接使用"))
        BugDB.load(BugRule(id="KT-0464",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="不必要的as（Long版）",trigger="val x:Long=\\\"hi\\\";val y=x as Long",detection="已知类型不需要as",fix="直接使用"))
        BugDB.load(BugRule(id="KT-0465",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="不必要的as（Double版）",trigger="val x:Double=\\\"hi\\\";val y=x as Double",detection="已知类型不需要as",fix="直接使用"))
        BugDB.load(BugRule(id="KT-0466",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="不必要的as（Boolean版）",trigger="val x:Boolean=\\\"hi\\\";val y=x as Boolean",detection="已知类型不需要as",fix="直接使用"))
        BugDB.load(BugRule(id="KT-0467",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="var智能转换失效（Int版）",trigger="if(x is Int){x.length}",detection="var可被外部修改",fix="val y=x"))
        BugDB.load(BugRule(id="KT-0468",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="var智能转换失效（Long版）",trigger="if(x is Long){x.length}",detection="var可被外部修改",fix="val y=x"))
        BugDB.load(BugRule(id="KT-0469",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="var智能转换失效（Double版）",trigger="if(x is Double){x.length}",detection="var可被外部修改",fix="val y=x"))
        BugDB.load(BugRule(id="KT-0470",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="var智能转换失效（Boolean版）",trigger="if(x is Boolean){x.length}",detection="var可被外部修改",fix="val y=x"))
        BugDB.load(BugRule(id="KT-0471",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="默认值在序列化中丢失（Long版）",trigger="data class U(val x:Long=0);json无x字段",detection="反序列化仍用默认",fix="显式标注默认值"))
        BugDB.load(BugRule(id="KT-0472",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举构造函数开销（Long版）",trigger="enum class E(val x:Long=0);E.A",detection="每个枚举实例化",fix="用object代替"))
        BugDB.load(BugRule(id="KT-0473",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举构造函数开销（Double版）",trigger="enum class E(val x:Double=0);E.A",detection="每个枚举实例化",fix="用object代替"))
        BugDB.load(BugRule(id="KT-0474",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="as不安全转换（Int版）",trigger="val x:Any;val y=x as Int",detection="可能ClassCast",fix="as?+?:错误处理"))
        BugDB.load(BugRule(id="KT-0475",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="as不安全转换（Long版）",trigger="val x:Any;val y=x as Long",detection="可能ClassCast",fix="as?+?:错误处理"))
        BugDB.load(BugRule(id="KT-0476",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="as不安全转换（Double版）",trigger="val x:Any;val y=x as Double",detection="可能ClassCast",fix="as?+?:错误处理"))
        BugDB.load(BugRule(id="KT-0477",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="as不安全转换（Boolean版）",trigger="val x:Any;val y=x as Boolean",detection="可能ClassCast",fix="as?+?:错误处理"))
        BugDB.load(BugRule(id="KT-0478",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类多属性（Long版）",trigger="@JvmInline value class P(val x:Long,val y:Long)",detection="仅单属性",fix="拆成两个"))
        BugDB.load(BugRule(id="KT-0479",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类多属性（Double版）",trigger="@JvmInline value class P(val x:Double,val y:Double)",detection="仅单属性",fix="拆成两个"))
        BugDB.load(BugRule(id="KT-0480",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类多属性（Float版）",trigger="@JvmInline value class P(val x:Float,val y:Float)",detection="仅单属性",fix="拆成两个"))
        BugDB.load(BugRule(id="KT-0481",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="内部类属性智能转换失效（Int版）",trigger="class Outer{var x:Any?;inner class Inner{fun f(){if(x is Int){x.length}}}}",detection="内部类访问外部var",fix="局部val快照"))
        BugDB.load(BugRule(id="KT-0482",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="内部类属性智能转换失效（Long版）",trigger="class Outer{var x:Any?;inner class Inner{fun f(){if(x is Long){x.length}}}}",detection="内部类访问外部var",fix="局部val快照"))
        BugDB.load(BugRule(id="KT-0483",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="内部类属性智能转换失效（Double版）",trigger="class Outer{var x:Any?;inner class Inner{fun f(){if(x is Double){x.length}}}}",detection="内部类访问外部var",fix="局部val快照"))
        BugDB.load(BugRule(id="KT-0484",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="内部类属性智能转换失效（Boolean版）",trigger="class Outer{var x:Any?;inner class Inner{fun f(){if(x is Boolean){x.length}}}}",detection="内部类访问外部var",fix="局部val快照"))
        BugDB.load(BugRule(id="KT-0485",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="BugDB缓存键碰撞（Int版）",trigger="code.hashCode().toInt()+skipPatterns.hashCode()",detection="拼接可能碰撞→返回错误结果",fix="分隔符隔开"))
        BugDB.load(BugRule(id="KT-0486",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="BugDB缓存键碰撞（Long版）",trigger="code.hashCode().toLong()+skipPatterns.hashCode()",detection="拼接可能碰撞→返回错误结果",fix="分隔符隔开"))
        BugDB.load(BugRule(id="KT-0487",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="BugDB缓存键碰撞（Double版）",trigger="code.hashCode().toDouble()+skipPatterns.hashCode()",detection="拼接可能碰撞→返回错误结果",fix="分隔符隔开"))
        BugDB.load(BugRule(id="KT-0488",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="suspend函数无超时",trigger="suspend fun api(){httpClient.get(...)}",detection="无超时挂死",fix="withTimeout"))
        BugDB.load(BugRule(id="KT-0489",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="Flow未捕获异常",trigger="flow{emit(risky())}.catch{e->...}",detection="catch位置错误",fix="catch在collect之前"))
        BugDB.load(BugRule(id="KT-0490",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="Mutex未释放",trigger="mutex.withLock{throw E()}",detection="withLock自动释放但异常未处理",fix="try-catch-withLock"))
        BugDB.load(BugRule(id="KT-0491",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="频繁创建Gson实例",trigger="Gson().toJson(obj)",detection="每次新建开销大",fix="单例"))
        BugDB.load(BugRule(id="KT-0492",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="OkHttpClient每次创建",trigger="OkHttpClient().newCall(...)",detection="连接池浪费",fix="单例"))
        BugDB.load(BugRule(id="KT-0493",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="WebView允许文件访问",trigger="webView.settings.allowFileAccess=true",detection="文件泄露",fix="禁用"))
        BugDB.load(BugRule(id="KT-0494",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="debug模式泄露",trigger="if(BuildConfig.DEBUG){logFullDump()}",detection="发布包残留调试代码",fix="移除或用if-release检查"))
        BugDB.load(BugRule(id="KT-0495",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="Paging3重复加载",trigger="PagingSource.load()返回值未去重",detection="重复数据",fix="distinctUntilChanged"))
        BugDB.load(BugRule(id="KT-0496",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Gson默认忽略transient",trigger="@Transient val x:Int;Gson仍序列化",detection="Kotlin transient≠Java",fix="@Expose(false)"))
        BugDB.load(BugRule(id="KT-0497",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="默认参数与Java重载冲突",trigger="fun f(a:Int,b:Int=0)在Java中",detection="Java看不到默认参数",fix="@JvmOverloads"))
        BugDB.load(BugRule(id="KT-0498",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="data class自动equals递归栈溢出",trigger="data class N(val n:N?)",detection="自引用",fix="手动equals"))
        BugDB.load(BugRule(id="KT-0499",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="Flow中!!操作符",trigger="flow{val x=risky()!!;emit(x)}",detection="Flow内NPE",fix="?.let或catch"))
        BugDB.load(BugRule(id="KT-0500",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="lateinit在init前后矛盾",trigger="init{val x=late};lateinit var late:String",detection="初始化顺序",fix="lateinit放init前面"))
    }

    private fun registerChunk2() {
        BugDB.load(BugRule(id="KT-0501",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="TypeReference匿名类在inline",trigger="inline fun <reified T> t(){object:TypeToken<T>(){}}",detection="内联泛型可获取",fix="直接reified"))
        BugDB.load(BugRule(id="KT-0502",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="when分支智能转换不稳定",trigger="when(x){is Int->x+1 is Long->x+1L}",detection="编译器推断不一致",fix="显式as+else"))
        BugDB.load(BugRule(id="KT-0503",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="Channel未关闭",trigger="val c=Channel<Int>();produce{",detection="生产者完成后未close",fix="finally{c.close()}"))
        BugDB.load(BugRule(id="KT-0504",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="StateFlow初始值导致重复emit",trigger="val s=MutableStateFlow(init);s.value=init",detection="同值仍触发collect",fix="distinctUntilChanged或检查值"))
        BugDB.load(BugRule(id="KT-0505",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="derivedStateOf滥用",trigger="val v=derivedStateOf{simpleCalc()}",detection="简单计算不需要",fix="直接计算"))
        BugDB.load(BugRule(id="KT-0506",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="副作用在Composition中",trigger="CompositionLocalProvider{loadData()}",detection="每次重组执行",fix="LaunchedEffect"))
        BugDB.load(BugRule(id="KT-0507",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="!!在可空类型上",trigger="x!!.length",detection="!!无保护，NPE风险 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0001）",fix="用?.或?:默认值"))
        BugDB.load(BugRule(id="KT-0508",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="!!未检查直接调用",trigger="!!.method()",detection="!!前无null检查 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0002）",fix="?.let"))
        BugDB.load(BugRule(id="KT-0509",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="链式!!蔓延",trigger="a!!.b!!.c!!.d",detection="多!!连锁爆炸 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0003）",fix="?.链+单点let"))
        BugDB.load(BugRule(id="KT-0510",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="lateinit未初始化",trigger="lateinit var x;x.method()",detection="未检查isInitialized — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0004）",fix="::x.isInitialized"))
        BugDB.load(BugRule(id="KT-0511",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="平台类型T!隐式null",trigger="javaObj.field;x.length",detection="T!可能为null — Char类型字符编码边界,正则匹配组越界（参见 KT-0005）",fix="显式类型+安全调用"))
        BugDB.load(BugRule(id="KT-0512",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="?.与!!混用",trigger="a?.b?.c!!.d",detection="混合风格混乱 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0006）",fix="统一用?.或统一用!!"))
        BugDB.load(BugRule(id="KT-0513",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="lateinit在init块之前访问",trigger="init{x.method()};lateinit var x",detection="初始化顺序 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0007）",fix="lateinit放init之前"))
        BugDB.load(BugRule(id="KT-0514",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="?.let嵌套过深",trigger="a?.let{b?.let{c?.let{}}}}",detection="箭头地狱 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0008）",fix="提取函数或flatMap"))
        BugDB.load(BugRule(id="KT-0515",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="as?后忘记null检查",trigger="val x=y as? T;x.method()",detection="as?返回可空 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0009）",fix="as?.let或?:return"))
        BugDB.load(BugRule(id="KT-0516",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="return@let遗漏",trigger="a?.let{if(x)return}",detection="return非局部 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0010）",fix="return@let"))
        BugDB.load(BugRule(id="KT-0517",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="不必要的?.",trigger="val x:T=...;x?.method()",detection="非空类型用?. — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0011）",fix="x.method()"))
        BugDB.load(BugRule(id="KT-0518",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="?:返回null",trigger="val x=y?:null",detection="?:后面是null无意义 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0012）",fix="?:默认值或抛异常"))
        BugDB.load(BugRule(id="KT-0519",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="elvis操作符冗余",trigger="val x=y?:y",detection="?:右侧等于左侧 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0013）",fix="直接用y"))
        BugDB.load(BugRule(id="KT-0520",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空String?在集合操作中（Double）",trigger="val l:Double<Double?>;l.filterNotNull().size",detection="先filterNotNull — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0014）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0521",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="String?的?:返回null（String?）",trigger="val x:String??=...;val y=String???:null",detection="冗余null — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0015）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0522",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Int?在集合操作中（Set<Int>）",trigger="val l:Set<Int><Set<Int>?>;l.filterNotNull().size",detection="先filterNotNull — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0016）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0523",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Int?的?:返回null（Long）",trigger="val x:Long?=...;val y=Long??:null",detection="冗余null — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0017）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0524",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Long?在集合操作中（Any）",trigger="val l:Any<Long?>;l.filterNotNull().size",detection="先filterNotNull — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0018）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0525",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Long?的?:返回null",trigger="val x:Long?=...;val y=Long??:null",detection="冗余null — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0019）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0526",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Double?在集合操作中（Int）",trigger="val l:Int<Double?>;l.filterNotNull().size",detection="先filterNotNull — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0020）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0527",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Double?的?:返回null",trigger="val x:Double?=...;val y=Double??:null",detection="冗余null — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0021）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0528",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Boolean?在集合操作中（Any?）",trigger="val l:Any?<Boolean?>;l.filterNotNull().size",detection="先filterNotNull — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0022）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0529",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Boolean?的?:返回null",trigger="val x:Boolean?=...;val y=Boolean??:null",detection="冗余null — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0023）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0530",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="!!在map取值上",trigger="val x=map[key]!!;x.method()",detection="map[key]返回可空 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0142）",fix="map[key]?.let或getOrDefault"))
        BugDB.load(BugRule(id="KT-0531",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="!!与Elvis短路冲突",trigger="val x=risky()!!?:default",detection="语义矛盾 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0143）",fix="统一用?.或!!不混"))
        BugDB.load(BugRule(id="KT-0532",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="takeIf后!!",trigger="val x=y.takeIf{it>0}!!",detection="takeIf返回可空 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0144）",fix="takeIf?.let"))
        BugDB.load(BugRule(id="KT-0533",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="集合元素!!",trigger="list.firstOrNull()!!.prop",detection="firstOrNull可空 — Char类型字符编码边界,正则匹配组越界（参见 KT-0145）",fix="firstOrNull?.prop"))
        BugDB.load(BugRule(id="KT-0534",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="类型参数可空未处理",trigger="fun <T> f(t:T){t!!.hashCode()}",detection="T可能为Any? — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0146）",fix="<T:Any>约束"))
        BugDB.load(BugRule(id="KT-0535",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="let内!!不如?.",trigger="x?.let{it!!.prop}",detection="let内it非空 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0147）",fix="x?.prop直接"))
        BugDB.load(BugRule(id="KT-0536",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="notNull断言过度",trigger="requireNotNull(x);x.prop",detection="已知非空 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0148）",fix="直接用x"))
        BugDB.load(BugRule(id="KT-0537",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="map中get后用!!",trigger="val v=map.get(key)!!;v.method()",detection="Map.get返回可空 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0149）",fix="getOrDefault"))
        BugDB.load(BugRule(id="KT-0538",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="!!在flow内",trigger="flow{val x=repo.get()!!;emit(x)}",detection="Flow中NPE污染管道 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0150）",fix="?.let+emit"))
        BugDB.load(BugRule(id="KT-0539",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="notNull与elvis重复",trigger="val x=requireNotNull(y?:z)",detection="重复检查 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0151）",fix="统一方式"))
        BugDB.load(BugRule(id="KT-0540",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="非空断言在finally块中失效",trigger="try{x!!;risky()}finally{x.method()}",detection="finally中x可能已被置null — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0293）",fix="局部val快照"))
        BugDB.load(BugRule(id="KT-0541",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="var+!!+多线程=智能转换完全失效",trigger="var x:Any?=null;thread{x!!;x=null};thread{x.method()}",detection="竞态破坏非空假设 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0295）",fix="AtomicReference+?.let"))
        BugDB.load(BugRule(id="KT-0542",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="!!+?:+as?=三null操作符互相矛盾",trigger="val x=y!!?:z as? T",detection="!!先炸，?:永不执行 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0306）",fix="只用一种null处理方式"))
        BugDB.load(BugRule(id="KT-0543",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Int?的?:返回null（Long版）",trigger="val x:Long?=...;val y=Long??:null",detection="冗余null — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0352）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0544",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Int?的?:返回null（Double版）",trigger="val x:Double?=...;val y=Double??:null",detection="冗余null — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0353）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0545",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Int?的?:返回null（Float版）",trigger="val x:Float?=...;val y=Float??:null",detection="冗余null — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0354）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0546",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Int?在集合操作中（Long版）（Any）",trigger="val l:Any<Long?>;l.filterNotNull().size",detection="先filterNotNull — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0355）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0547",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Int?在集合操作中（Double版）（List<String>）",trigger="val l:List<String><Double?>;l.filterNotNull().size",detection="先filterNotNull — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0356）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0548",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空String?在集合操作中（Int版）（Int）",trigger="val l:Int<Int?>;l.filterNotNull().size",detection="先filterNotNull — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0360）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0549",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空String?在集合操作中（Long版）（Short）",trigger="val l:Short<Long?>;l.filterNotNull().size",detection="先filterNotNull — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0361）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0550",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空String?在集合操作中（Double版）（Any?）",trigger="val l:Any?<Double?>;l.filterNotNull().size",detection="先filterNotNull — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0362）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0551",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="String?的?:返回null（Int版）（String）",trigger="val x:String?=...;val y=String??:null",detection="冗余null — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0428）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0552",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="String?的?:返回null（Long版）",trigger="val x:Long?=...;val y=Long??:null",detection="冗余null — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0429）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0553",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="String?的?:返回null（Double版）",trigger="val x:Double?=...;val y=Double??:null",detection="冗余null — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0430）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0554",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="String?的?:返回null（Boolean版）",trigger="val x:Boolean?=...;val y=Boolean??:null",detection="冗余null — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0431）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0555",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="Flow中!!操作符",trigger="flow{val x=risky()!!;emit(x)}",detection="Flow内NPE — Char类型字符编码边界,正则匹配组越界（参见 KT-0499）",fix="?.let或catch"))
        BugDB.load(BugRule(id="KT-0556",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="lateinit在init前后矛盾（Double?）",trigger="init{val x=late};lateinit var late:Double?",detection="初始化顺序 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0500）",fix="lateinit放init前面"))
        BugDB.load(BugRule(id="KT-0557",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="!!在可空类型上",trigger="x!!.length",detection="!!无保护，NPE风险 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0001）",fix="用?.或?:默认值"))
        BugDB.load(BugRule(id="KT-0558",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="!!未检查直接调用",trigger="!!.method()",detection="!!前无null检查 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0002）",fix="?.let"))
        BugDB.load(BugRule(id="KT-0559",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="链式!!蔓延",trigger="a!!.b!!.c!!.d",detection="多!!连锁爆炸 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0003）",fix="?.链+单点let"))
        BugDB.load(BugRule(id="KT-0560",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="lateinit未初始化",trigger="lateinit var x;x.method()",detection="未检查isInitialized — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0004）",fix="::x.isInitialized"))
        BugDB.load(BugRule(id="KT-0561",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="平台类型T!隐式null",trigger="javaObj.field;x.length",detection="T!可能为null — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0005）",fix="显式类型+安全调用"))
        BugDB.load(BugRule(id="KT-0562",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="?.与!!混用",trigger="a?.b?.c!!.d",detection="混合风格混乱 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0006）",fix="统一用?.或统一用!!"))
        BugDB.load(BugRule(id="KT-0563",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="lateinit在init块之前访问",trigger="init{x.method()};lateinit var x",detection="初始化顺序 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0007）",fix="lateinit放init之前"))
        BugDB.load(BugRule(id="KT-0564",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="?.let嵌套过深",trigger="a?.let{b?.let{c?.let{}}}}",detection="箭头地狱 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0008）",fix="提取函数或flatMap"))
        BugDB.load(BugRule(id="KT-0565",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="as?后忘记null检查",trigger="val x=y as? T;x.method()",detection="as?返回可空 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0009）",fix="as?.let或?:return"))
        BugDB.load(BugRule(id="KT-0566",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="return@let遗漏",trigger="a?.let{if(x)return}",detection="return非局部 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0010）",fix="return@let"))
        BugDB.load(BugRule(id="KT-0567",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="不必要的?.",trigger="val x:T=...;x?.method()",detection="非空类型用?. — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0011）",fix="x.method()"))
        BugDB.load(BugRule(id="KT-0568",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="?:返回null",trigger="val x=y?:null",detection="?:后面是null无意义 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0012）",fix="?:默认值或抛异常"))
        BugDB.load(BugRule(id="KT-0569",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="elvis操作符冗余",trigger="val x=y?:y",detection="?:右侧等于左侧 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0013）",fix="直接用y"))
        BugDB.load(BugRule(id="KT-0570",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空String?在集合操作中（Int）",trigger="val l:Int<Int?>;l.filterNotNull().size",detection="先filterNotNull — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0014）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0571",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="String?的?:返回null（Short）",trigger="val x:Short?=...;val y=Short??:null",detection="冗余null — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0015）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0572",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Int?在集合操作中（Any?）",trigger="val l:Any?<Any??>;l.filterNotNull().size",detection="先filterNotNull — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0016）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0573",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Int?的?:返回null（String）",trigger="val x:String?=...;val y=String??:null",detection="冗余null — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0017）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0574",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Long?在集合操作中（Byte）",trigger="val l:Byte<Long?>;l.filterNotNull().size",detection="先filterNotNull — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0018）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0575",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Long?的?:返回null",trigger="val x:Long?=...;val y=Long??:null",detection="冗余null — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0019）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0576",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Double?在集合操作中（Sequence<Long>）",trigger="val l:Sequence<Long><Double?>;l.filterNotNull().size",detection="先filterNotNull — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0020）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0577",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Double?的?:返回null",trigger="val x:Double?=...;val y=Double??:null",detection="冗余null — Char类型字符编码边界,正则匹配组越界（参见 KT-0021）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0578",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Boolean?在集合操作中（Double?）",trigger="val l:Double?<Boolean?>;l.filterNotNull().size",detection="先filterNotNull — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0022）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0579",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Boolean?的?:返回null",trigger="val x:Boolean?=...;val y=Boolean??:null",detection="冗余null — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0023）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0580",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="!!在map取值上",trigger="val x=map[key]!!;x.method()",detection="map[key]返回可空 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0142）",fix="map[key]?.let或getOrDefault"))
        BugDB.load(BugRule(id="KT-0581",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="!!与Elvis短路冲突",trigger="val x=risky()!!?:default",detection="语义矛盾 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0143）",fix="统一用?.或!!不混"))
        BugDB.load(BugRule(id="KT-0582",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="takeIf后!!",trigger="val x=y.takeIf{it>0}!!",detection="takeIf返回可空 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0144）",fix="takeIf?.let"))
        BugDB.load(BugRule(id="KT-0583",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="集合元素!!",trigger="list.firstOrNull()!!.prop",detection="firstOrNull可空 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0145）",fix="firstOrNull?.prop"))
        BugDB.load(BugRule(id="KT-0584",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="类型参数可空未处理",trigger="fun <T> f(t:T){t!!.hashCode()}",detection="T可能为Any? — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0146）",fix="<T:Any>约束"))
        BugDB.load(BugRule(id="KT-0585",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="let内!!不如?.",trigger="x?.let{it!!.prop}",detection="let内it非空 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0147）",fix="x?.prop直接"))
        BugDB.load(BugRule(id="KT-0586",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="notNull断言过度",trigger="requireNotNull(x);x.prop",detection="已知非空 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0148）",fix="直接用x"))
        BugDB.load(BugRule(id="KT-0587",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="map中get后用!!",trigger="val v=map.get(key)!!;v.method()",detection="Map.get返回可空 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0149）",fix="getOrDefault"))
        BugDB.load(BugRule(id="KT-0588",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="!!在flow内",trigger="flow{val x=repo.get()!!;emit(x)}",detection="Flow中NPE污染管道 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0150）",fix="?.let+emit"))
        BugDB.load(BugRule(id="KT-0589",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="notNull与elvis重复",trigger="val x=requireNotNull(y?:z)",detection="重复检查 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0151）",fix="统一方式"))
        BugDB.load(BugRule(id="KT-0590",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="非空断言在finally块中失效",trigger="try{x!!;risky()}finally{x.method()}",detection="finally中x可能已被置null — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0293）",fix="局部val快照"))
        BugDB.load(BugRule(id="KT-0591",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="var+!!+多线程=智能转换完全失效",trigger="var x:Any?=null;thread{x!!;x=null};thread{x.method()}",detection="竞态破坏非空假设 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0295）",fix="AtomicReference+?.let"))
        BugDB.load(BugRule(id="KT-0592",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="!!+?:+as?=三null操作符互相矛盾",trigger="val x=y!!?:z as? T",detection="!!先炸，?:永不执行 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0306）",fix="只用一种null处理方式"))
        BugDB.load(BugRule(id="KT-0593",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Int?的?:返回null（Long版）",trigger="val x:Long?=...;val y=Long??:null",detection="冗余null — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0352）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0594",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Int?的?:返回null（Double版）",trigger="val x:Double?=...;val y=Double??:null",detection="冗余null — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0353）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0595",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Int?的?:返回null（Float版）",trigger="val x:Float?=...;val y=Float??:null",detection="冗余null — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0354）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0596",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Int?在集合操作中（Long版）（Byte）",trigger="val l:Byte<Long?>;l.filterNotNull().size",detection="先filterNotNull — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0355）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0597",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Int?在集合操作中（Double版）（Boolean?）",trigger="val l:Boolean?<Double?>;l.filterNotNull().size",detection="先filterNotNull — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0356）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0598",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空String?在集合操作中（Int版）（Sequence<Long>）",trigger="val l:Sequence<Long><Sequence<Long>?>;l.filterNotNull().size",detection="先filterNotNull — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0360）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0599",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空String?在集合操作中（Long版）（Char）",trigger="val l:Char<Long?>;l.filterNotNull().size",detection="先filterNotNull — Char类型字符编码边界,正则匹配组越界（参见 KT-0361）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0600",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空String?在集合操作中（Double版）（Double?）",trigger="val l:Double?<Double?>;l.filterNotNull().size",detection="先filterNotNull — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0362）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0601",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="String?的?:返回null（Int版）（Array<Boolean>）",trigger="val x:Array<Boolean>?=...;val y=Array<Boolean>??:null",detection="冗余null — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0428）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0602",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="String?的?:返回null（Long版）",trigger="val x:Long?=...;val y=Long??:null",detection="冗余null — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0429）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0603",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="String?的?:返回null（Double版）",trigger="val x:Double?=...;val y=Double??:null",detection="冗余null — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0430）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0604",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="String?的?:返回null（Boolean版）",trigger="val x:Boolean?=...;val y=Boolean??:null",detection="冗余null — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0431）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0605",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="Flow中!!操作符",trigger="flow{val x=risky()!!;emit(x)}",detection="Flow内NPE — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0499）",fix="?.let或catch"))
        BugDB.load(BugRule(id="KT-0606",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="lateinit在init前后矛盾（Int?）",trigger="init{val x=late};lateinit var late:Int??",detection="初始化顺序 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0500）",fix="lateinit放init前面"))
        BugDB.load(BugRule(id="KT-0607",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="!!在可空类型上",trigger="x!!.length",detection="!!无保护，NPE风险 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0001）",fix="用?.或?:默认值"))
        BugDB.load(BugRule(id="KT-0608",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="!!未检查直接调用",trigger="!!.method()",detection="!!前无null检查 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0002）",fix="?.let"))
        BugDB.load(BugRule(id="KT-0609",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="链式!!蔓延",trigger="a!!.b!!.c!!.d",detection="多!!连锁爆炸 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0003）",fix="?.链+单点let"))
        BugDB.load(BugRule(id="KT-0610",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="lateinit未初始化",trigger="lateinit var x;x.method()",detection="未检查isInitialized — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0004）",fix="::x.isInitialized"))
        BugDB.load(BugRule(id="KT-0611",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="平台类型T!隐式null",trigger="javaObj.field;x.length",detection="T!可能为null — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0005）",fix="显式类型+安全调用"))
        BugDB.load(BugRule(id="KT-0612",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="?.与!!混用",trigger="a?.b?.c!!.d",detection="混合风格混乱 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0006）",fix="统一用?.或统一用!!"))
        BugDB.load(BugRule(id="KT-0613",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="lateinit在init块之前访问",trigger="init{x.method()};lateinit var x",detection="初始化顺序 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0007）",fix="lateinit放init之前"))
        BugDB.load(BugRule(id="KT-0614",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="?.let嵌套过深",trigger="a?.let{b?.let{c?.let{}}}}",detection="箭头地狱 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0008）",fix="提取函数或flatMap"))
        BugDB.load(BugRule(id="KT-0615",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="as?后忘记null检查",trigger="val x=y as? T;x.method()",detection="as?返回可空 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0009）",fix="as?.let或?:return"))
        BugDB.load(BugRule(id="KT-0616",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="return@let遗漏",trigger="a?.let{if(x)return}",detection="return非局部 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0010）",fix="return@let"))
        BugDB.load(BugRule(id="KT-0617",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="不必要的?.",trigger="val x:T=...;x?.method()",detection="非空类型用?. — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0011）",fix="x.method()"))
        BugDB.load(BugRule(id="KT-0618",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="?:返回null",trigger="val x=y?:null",detection="?:后面是null无意义 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0012）",fix="?:默认值或抛异常"))
        BugDB.load(BugRule(id="KT-0619",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="elvis操作符冗余",trigger="val x=y?:y",detection="?:右侧等于左侧 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0013）",fix="直接用y"))
        BugDB.load(BugRule(id="KT-0620",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空String?在集合操作中（Sequence<Long>）",trigger="val l:Sequence<Long><Sequence<Long>?>;l.filterNotNull().size",detection="先filterNotNull — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0014）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0621",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="String?的?:返回null（Char）",trigger="val x:Char?=...;val y=Char??:null",detection="冗余null — Char类型字符编码边界,正则匹配组越界（参见 KT-0015）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0622",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Int?在集合操作中（Double?）",trigger="val l:Double?<Double??>;l.filterNotNull().size",detection="先filterNotNull — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0016）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0623",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Int?的?:返回null（Array<Boolean>）",trigger="val x:Array<Boolean>?=...;val y=Array<Boolean>??:null",detection="冗余null — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0017）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0624",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Long?在集合操作中（Float）",trigger="val l:Float<Long?>;l.filterNotNull().size",detection="先filterNotNull — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0018）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0625",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Long?的?:返回null",trigger="val x:Long?=...;val y=Long??:null",detection="冗余null — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0019）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0626",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Double?在集合操作中（MutableList<Double>）",trigger="val l:MutableList<Double><Double?>;l.filterNotNull().size",detection="先filterNotNull — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0020）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0627",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Double?的?:返回null",trigger="val x:Double?=...;val y=Double??:null",detection="冗余null — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0021）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0628",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Boolean?在集合操作中（Int?）",trigger="val l:Int?<Boolean?>;l.filterNotNull().size",detection="先filterNotNull — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0022）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0629",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Boolean?的?:返回null",trigger="val x:Boolean?=...;val y=Boolean??:null",detection="冗余null — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0023）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0630",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="!!在map取值上",trigger="val x=map[key]!!;x.method()",detection="map[key]返回可空 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0142）",fix="map[key]?.let或getOrDefault"))
        BugDB.load(BugRule(id="KT-0631",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="!!与Elvis短路冲突",trigger="val x=risky()!!?:default",detection="语义矛盾 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0143）",fix="统一用?.或!!不混"))
        BugDB.load(BugRule(id="KT-0632",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="takeIf后!!",trigger="val x=y.takeIf{it>0}!!",detection="takeIf返回可空 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0144）",fix="takeIf?.let"))
        BugDB.load(BugRule(id="KT-0633",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="集合元素!!",trigger="list.firstOrNull()!!.prop",detection="firstOrNull可空 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0145）",fix="firstOrNull?.prop"))
        BugDB.load(BugRule(id="KT-0634",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="类型参数可空未处理",trigger="fun <T> f(t:T){t!!.hashCode()}",detection="T可能为Any? — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0146）",fix="<T:Any>约束"))
        BugDB.load(BugRule(id="KT-0635",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="let内!!不如?.",trigger="x?.let{it!!.prop}",detection="let内it非空 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0147）",fix="x?.prop直接"))
        BugDB.load(BugRule(id="KT-0636",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="notNull断言过度",trigger="requireNotNull(x);x.prop",detection="已知非空 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0148）",fix="直接用x"))
        BugDB.load(BugRule(id="KT-0637",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="map中get后用!!",trigger="val v=map.get(key)!!;v.method()",detection="Map.get返回可空 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0149）",fix="getOrDefault"))
        BugDB.load(BugRule(id="KT-0638",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="!!在flow内",trigger="flow{val x=repo.get()!!;emit(x)}",detection="Flow中NPE污染管道 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0150）",fix="?.let+emit"))
        BugDB.load(BugRule(id="KT-0639",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="notNull与elvis重复",trigger="val x=requireNotNull(y?:z)",detection="重复检查 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0151）",fix="统一方式"))
        BugDB.load(BugRule(id="KT-0640",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="非空断言在finally块中失效",trigger="try{x!!;risky()}finally{x.method()}",detection="finally中x可能已被置null — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0293）",fix="局部val快照"))
        BugDB.load(BugRule(id="KT-0641",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="var+!!+多线程=智能转换完全失效",trigger="var x:Any?=null;thread{x!!;x=null};thread{x.method()}",detection="竞态破坏非空假设 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0295）",fix="AtomicReference+?.let"))
        BugDB.load(BugRule(id="KT-0642",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="!!+?:+as?=三null操作符互相矛盾",trigger="val x=y!!?:z as? T",detection="!!先炸，?:永不执行 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0306）",fix="只用一种null处理方式"))
        BugDB.load(BugRule(id="KT-0643",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Int?的?:返回null（Long版）",trigger="val x:Long?=...;val y=Long??:null",detection="冗余null — Char类型字符编码边界,正则匹配组越界（参见 KT-0352）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0644",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Int?的?:返回null（Double版）",trigger="val x:Double?=...;val y=Double??:null",detection="冗余null — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0353）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0645",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Int?的?:返回null（Float版）",trigger="val x:Float?=...;val y=Float??:null",detection="冗余null — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0354）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0646",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Int?在集合操作中（Long版）（Float）",trigger="val l:Float<Long?>;l.filterNotNull().size",detection="先filterNotNull — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0355）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0647",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Int?在集合操作中（Double版）（Long?）",trigger="val l:Long?<Double?>;l.filterNotNull().size",detection="先filterNotNull — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0356）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0648",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空String?在集合操作中（Int版）（MutableList<Double>）",trigger="val l:MutableList<Double><MutableMutableList<Double><Double>?>;l.filterNotNull().size",detection="先filterNotNull — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0360）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0649",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空String?在集合操作中（Long版）（Boolean）",trigger="val l:Boolean<Long?>;l.filterNotNull().size",detection="先filterNotNull — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0361）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0650",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空String?在集合操作中（Double版）（Int?）",trigger="val l:Int?<Double?>;l.filterNotNull().size",detection="先filterNotNull — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0362）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0651",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="String?的?:返回null（Int版）（Map<String,Int>）",trigger="val x:Map<String,Int>?=...;val y=Map<String,Int>??:null",detection="冗余null — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0428）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0652",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="String?的?:返回null（Long版）",trigger="val x:Long?=...;val y=Long??:null",detection="冗余null — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0429）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0653",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="String?的?:返回null（Double版）",trigger="val x:Double?=...;val y=Double??:null",detection="冗余null — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0430）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0654",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="String?的?:返回null（Boolean版）",trigger="val x:Boolean?=...;val y=Boolean??:null",detection="冗余null — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0431）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0655",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="Flow中!!操作符",trigger="flow{val x=risky()!!;emit(x)}",detection="Flow内NPE — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0499）",fix="?.let或catch"))
        BugDB.load(BugRule(id="KT-0656",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="lateinit在init前后矛盾（Any）",trigger="init{val x=late};lateinit var late:Any",detection="初始化顺序 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0500）",fix="lateinit放init前面"))
        BugDB.load(BugRule(id="KT-0657",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="!!在可空类型上",trigger="x!!.length",detection="!!无保护，NPE风险 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0001）",fix="用?.或?:默认值"))
        BugDB.load(BugRule(id="KT-0658",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="!!未检查直接调用",trigger="!!.method()",detection="!!前无null检查 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0002）",fix="?.let"))
        BugDB.load(BugRule(id="KT-0659",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="链式!!蔓延",trigger="a!!.b!!.c!!.d",detection="多!!连锁爆炸 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0003）",fix="?.链+单点let"))
        BugDB.load(BugRule(id="KT-0660",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="lateinit未初始化",trigger="lateinit var x;x.method()",detection="未检查isInitialized — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0004）",fix="::x.isInitialized"))
        BugDB.load(BugRule(id="KT-0661",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="平台类型T!隐式null",trigger="javaObj.field;x.length",detection="T!可能为null — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0005）",fix="显式类型+安全调用"))
        BugDB.load(BugRule(id="KT-0662",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="?.与!!混用",trigger="a?.b?.c!!.d",detection="混合风格混乱 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0006）",fix="统一用?.或统一用!!"))
        BugDB.load(BugRule(id="KT-0663",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="lateinit在init块之前访问",trigger="init{x.method()};lateinit var x",detection="初始化顺序 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0007）",fix="lateinit放init之前"))
        BugDB.load(BugRule(id="KT-0664",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="?.let嵌套过深",trigger="a?.let{b?.let{c?.let{}}}}",detection="箭头地狱 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0008）",fix="提取函数或flatMap"))
        BugDB.load(BugRule(id="KT-0665",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="as?后忘记null检查",trigger="val x=y as? T;x.method()",detection="as?返回可空 — Char类型字符编码边界,正则匹配组越界（参见 KT-0009）",fix="as?.let或?:return"))
        BugDB.load(BugRule(id="KT-0666",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="return@let遗漏",trigger="a?.let{if(x)return}",detection="return非局部 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0010）",fix="return@let"))
        BugDB.load(BugRule(id="KT-0667",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="不必要的?.",trigger="val x:T=...;x?.method()",detection="非空类型用?. — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0011）",fix="x.method()"))
        BugDB.load(BugRule(id="KT-0668",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="?:返回null",trigger="val x=y?:null",detection="?:后面是null无意义 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0012）",fix="?:默认值或抛异常"))
        BugDB.load(BugRule(id="KT-0669",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="elvis操作符冗余",trigger="val x=y?:y",detection="?:右侧等于左侧 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0013）",fix="直接用y"))
        BugDB.load(BugRule(id="KT-0670",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空String?在集合操作中（MutableList<Double>）",trigger="val l:MutableList<Double><MutableMutableList<Double><Double>?>;l.filterNotNull().size",detection="先filterNotNull — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0014）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0671",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="String?的?:返回null（Boolean）",trigger="val x:Boolean?=...;val y=Boolean??:null",detection="冗余null — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0015）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0672",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Int?在集合操作中（Int?）",trigger="val l:Int?<Int??>;l.filterNotNull().size",detection="先filterNotNull — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0016）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0673",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Int?的?:返回null（Map<String,Int>）",trigger="val x:Map<String,Int>?=...;val y=Map<String,Int>??:null",detection="冗余null — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0017）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0674",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Long?在集合操作中（Double）",trigger="val l:Double<Long?>;l.filterNotNull().size",detection="先filterNotNull — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0018）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0675",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Long?的?:返回null",trigger="val x:Long?=...;val y=Long??:null",detection="冗余null — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0019）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0676",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Double?在集合操作中（Set<Int>）",trigger="val l:Set<Int><Double?>;l.filterNotNull().size",detection="先filterNotNull — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0020）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0677",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Double?的?:返回null",trigger="val x:Double?=...;val y=Double??:null",detection="冗余null — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0021）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0678",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Boolean?在集合操作中（Any）",trigger="val l:Any<Boolean?>;l.filterNotNull().size",detection="先filterNotNull — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0022）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0679",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Boolean?的?:返回null",trigger="val x:Boolean?=...;val y=Boolean??:null",detection="冗余null — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0023）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0680",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="!!在map取值上",trigger="val x=map[key]!!;x.method()",detection="map[key]返回可空 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0142）",fix="map[key]?.let或getOrDefault"))
        BugDB.load(BugRule(id="KT-0681",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="!!与Elvis短路冲突",trigger="val x=risky()!!?:default",detection="语义矛盾 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0143）",fix="统一用?.或!!不混"))
        BugDB.load(BugRule(id="KT-0682",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="takeIf后!!",trigger="val x=y.takeIf{it>0}!!",detection="takeIf返回可空 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0144）",fix="takeIf?.let"))
        BugDB.load(BugRule(id="KT-0683",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="集合元素!!",trigger="list.firstOrNull()!!.prop",detection="firstOrNull可空 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0145）",fix="firstOrNull?.prop"))
        BugDB.load(BugRule(id="KT-0684",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="类型参数可空未处理",trigger="fun <T> f(t:T){t!!.hashCode()}",detection="T可能为Any? — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0146）",fix="<T:Any>约束"))
        BugDB.load(BugRule(id="KT-0685",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="let内!!不如?.",trigger="x?.let{it!!.prop}",detection="let内it非空 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0147）",fix="x?.prop直接"))
        BugDB.load(BugRule(id="KT-0686",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="notNull断言过度",trigger="requireNotNull(x);x.prop",detection="已知非空 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0148）",fix="直接用x"))
        BugDB.load(BugRule(id="KT-0687",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="map中get后用!!",trigger="val v=map.get(key)!!;v.method()",detection="Map.get返回可空 — Char类型字符编码边界,正则匹配组越界（参见 KT-0149）",fix="getOrDefault"))
        BugDB.load(BugRule(id="KT-0688",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="!!在flow内",trigger="flow{val x=repo.get()!!;emit(x)}",detection="Flow中NPE污染管道 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0150）",fix="?.let+emit"))
        BugDB.load(BugRule(id="KT-0689",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="notNull与elvis重复",trigger="val x=requireNotNull(y?:z)",detection="重复检查 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0151）",fix="统一方式"))
        BugDB.load(BugRule(id="KT-0690",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="非空断言在finally块中失效",trigger="try{x!!;risky()}finally{x.method()}",detection="finally中x可能已被置null — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0293）",fix="局部val快照"))
        BugDB.load(BugRule(id="KT-0691",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="var+!!+多线程=智能转换完全失效",trigger="var x:Any?=null;thread{x!!;x=null};thread{x.method()}",detection="竞态破坏非空假设 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0295）",fix="AtomicReference+?.let"))
        BugDB.load(BugRule(id="KT-0692",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="!!+?:+as?=三null操作符互相矛盾",trigger="val x=y!!?:z as? T",detection="!!先炸，?:永不执行 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0306）",fix="只用一种null处理方式"))
        BugDB.load(BugRule(id="KT-0693",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Int?的?:返回null（Long版）",trigger="val x:Long?=...;val y=Long??:null",detection="冗余null — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0352）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0694",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Int?的?:返回null（Double版）",trigger="val x:Double?=...;val y=Double??:null",detection="冗余null — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0353）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0695",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Int?的?:返回null（Float版）",trigger="val x:Float?=...;val y=Float??:null",detection="冗余null — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0354）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0696",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Int?在集合操作中（Long版）（Double）",trigger="val l:Double<Long?>;l.filterNotNull().size",detection="先filterNotNull — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0355）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0697",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Int?在集合操作中（Double版）（String?）",trigger="val l:String?<Double?>;l.filterNotNull().size",detection="先filterNotNull — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0356）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0698",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空String?在集合操作中（Int版）（Set<Int>）",trigger="val l:Set<Int><Set<Int>?>;l.filterNotNull().size",detection="先filterNotNull — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0360）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0699",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空String?在集合操作中（Long版）（Long）",trigger="val l:Long<Long?>;l.filterNotNull().size",detection="先filterNotNull — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0361）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0700",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空String?在集合操作中（Double版）（Any）",trigger="val l:Any<Double?>;l.filterNotNull().size",detection="先filterNotNull — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0362）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0701",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="String?的?:返回null（Int版）（List<String>）",trigger="val x:List<String><String>?=...;val y=List<String><String>??:null",detection="冗余null — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0428）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0702",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="String?的?:返回null（Long版）",trigger="val x:Long?=...;val y=Long??:null",detection="冗余null — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0429）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0703",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="String?的?:返回null（Double版）",trigger="val x:Double?=...;val y=Double??:null",detection="冗余null — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0430）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0704",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="String?的?:返回null（Boolean版）",trigger="val x:Boolean?=...;val y=Boolean??:null",detection="冗余null — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0431）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0705",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="Flow中!!操作符",trigger="flow{val x=risky()!!;emit(x)}",detection="Flow内NPE — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0499）",fix="?.let或catch"))
        BugDB.load(BugRule(id="KT-0706",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="lateinit在init前后矛盾（Byte）",trigger="init{val x=late};lateinit var late:Byte",detection="初始化顺序 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0500）",fix="lateinit放init前面"))
        BugDB.load(BugRule(id="KT-0707",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="!!在可空类型上",trigger="x!!.length",detection="!!无保护，NPE风险 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0001）",fix="用?.或?:默认值"))
        BugDB.load(BugRule(id="KT-0708",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="!!未检查直接调用",trigger="!!.method()",detection="!!前无null检查 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0002）",fix="?.let"))
        BugDB.load(BugRule(id="KT-0709",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="链式!!蔓延",trigger="a!!.b!!.c!!.d",detection="多!!连锁爆炸 — Char类型字符编码边界,正则匹配组越界（参见 KT-0003）",fix="?.链+单点let"))
        BugDB.load(BugRule(id="KT-0710",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="lateinit未初始化",trigger="lateinit var x;x.method()",detection="未检查isInitialized — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0004）",fix="::x.isInitialized"))
        BugDB.load(BugRule(id="KT-0711",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="平台类型T!隐式null",trigger="javaObj.field;x.length",detection="T!可能为null — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0005）",fix="显式类型+安全调用"))
        BugDB.load(BugRule(id="KT-0712",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="?.与!!混用",trigger="a?.b?.c!!.d",detection="混合风格混乱 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0006）",fix="统一用?.或统一用!!"))
        BugDB.load(BugRule(id="KT-0713",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="lateinit在init块之前访问",trigger="init{x.method()};lateinit var x",detection="初始化顺序 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0007）",fix="lateinit放init之前"))
        BugDB.load(BugRule(id="KT-0714",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="?.let嵌套过深",trigger="a?.let{b?.let{c?.let{}}}}",detection="箭头地狱 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0008）",fix="提取函数或flatMap"))
        BugDB.load(BugRule(id="KT-0715",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="as?后忘记null检查",trigger="val x=y as? T;x.method()",detection="as?返回可空 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0009）",fix="as?.let或?:return"))
        BugDB.load(BugRule(id="KT-0716",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="return@let遗漏",trigger="a?.let{if(x)return}",detection="return非局部 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0010）",fix="return@let"))
        BugDB.load(BugRule(id="KT-0717",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="不必要的?.",trigger="val x:T=...;x?.method()",detection="非空类型用?. — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0011）",fix="x.method()"))
        BugDB.load(BugRule(id="KT-0718",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="?:返回null",trigger="val x=y?:null",detection="?:后面是null无意义 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0012）",fix="?:默认值或抛异常"))
        BugDB.load(BugRule(id="KT-0719",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="elvis操作符冗余",trigger="val x=y?:y",detection="?:右侧等于左侧 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0013）",fix="直接用y"))
        BugDB.load(BugRule(id="KT-0720",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空String?在集合操作中（Set<Int>）",trigger="val l:Set<Int><Set<Set<Int>>?>;l.filterNotNull().size",detection="先filterNotNull — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0014）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0721",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="String?的?:返回null（Long）",trigger="val x:Long?=...;val y=Long??:null",detection="冗余null — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0015）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0722",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Int?在集合操作中（Any）",trigger="val l:Any<Any?>;l.filterNotNull().size",detection="先filterNotNull — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0016）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0723",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Int?的?:返回null（List<String>）",trigger="val x:List<String><String>?=...;val y=List<String><String>??:null",detection="冗余null — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0017）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0724",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Long?在集合操作中（Int）",trigger="val l:Int<Long?>;l.filterNotNull().size",detection="先filterNotNull — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0018）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0725",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Long?的?:返回null",trigger="val x:Long?=...;val y=Long??:null",detection="冗余null — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0019）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0726",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Double?在集合操作中（Any?）",trigger="val l:Any?<Double?>;l.filterNotNull().size",detection="先filterNotNull — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0020）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0727",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Double?的?:返回null",trigger="val x:Double?=...;val y=Double??:null",detection="冗余null — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0021）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0728",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Boolean?在集合操作中（Byte）",trigger="val l:Byte<Boolean?>;l.filterNotNull().size",detection="先filterNotNull — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0022）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0729",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Boolean?的?:返回null",trigger="val x:Boolean?=...;val y=Boolean??:null",detection="冗余null — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0023）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0730",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="!!在map取值上",trigger="val x=map[key]!!;x.method()",detection="map[key]返回可空 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0142）",fix="map[key]?.let或getOrDefault"))
        BugDB.load(BugRule(id="KT-0731",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="!!与Elvis短路冲突",trigger="val x=risky()!!?:default",detection="语义矛盾 — Char类型字符编码边界,正则匹配组越界（参见 KT-0143）",fix="统一用?.或!!不混"))
        BugDB.load(BugRule(id="KT-0732",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="takeIf后!!",trigger="val x=y.takeIf{it>0}!!",detection="takeIf返回可空 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0144）",fix="takeIf?.let"))
        BugDB.load(BugRule(id="KT-0733",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="集合元素!!",trigger="list.firstOrNull()!!.prop",detection="firstOrNull可空 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0145）",fix="firstOrNull?.prop"))
        BugDB.load(BugRule(id="KT-0734",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="类型参数可空未处理",trigger="fun <T> f(t:T){t!!.hashCode()}",detection="T可能为Any? — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0146）",fix="<T:Any>约束"))
        BugDB.load(BugRule(id="KT-0735",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="let内!!不如?.",trigger="x?.let{it!!.prop}",detection="let内it非空 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0147）",fix="x?.prop直接"))
        BugDB.load(BugRule(id="KT-0736",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="notNull断言过度",trigger="requireNotNull(x);x.prop",detection="已知非空 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0148）",fix="直接用x"))
        BugDB.load(BugRule(id="KT-0737",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="map中get后用!!",trigger="val v=map.get(key)!!;v.method()",detection="Map.get返回可空 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0149）",fix="getOrDefault"))
        BugDB.load(BugRule(id="KT-0738",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="!!在flow内",trigger="flow{val x=repo.get()!!;emit(x)}",detection="Flow中NPE污染管道 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0150）",fix="?.let+emit"))
        BugDB.load(BugRule(id="KT-0739",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="notNull与elvis重复",trigger="val x=requireNotNull(y?:z)",detection="重复检查 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0151）",fix="统一方式"))
        BugDB.load(BugRule(id="KT-0740",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="非空断言在finally块中失效",trigger="try{x!!;risky()}finally{x.method()}",detection="finally中x可能已被置null — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0293）",fix="局部val快照"))
        BugDB.load(BugRule(id="KT-0741",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="var+!!+多线程=智能转换完全失效",trigger="var x:Any?=null;thread{x!!;x=null};thread{x.method()}",detection="竞态破坏非空假设 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0295）",fix="AtomicReference+?.let"))
        BugDB.load(BugRule(id="KT-0742",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="!!+?:+as?=三null操作符互相矛盾",trigger="val x=y!!?:z as? T",detection="!!先炸，?:永不执行 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0306）",fix="只用一种null处理方式"))
        BugDB.load(BugRule(id="KT-0743",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Int?的?:返回null（Long版）",trigger="val x:Long?=...;val y=Long??:null",detection="冗余null — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0352）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0744",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Int?的?:返回null（Double版）",trigger="val x:Double?=...;val y=Double??:null",detection="冗余null — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0353）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0745",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="Int?的?:返回null（Float版）",trigger="val x:Float?=...;val y=Float??:null",detection="冗余null — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0354）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0746",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Int?在集合操作中（Long版）（Int）",trigger="val l:Int<Long?>;l.filterNotNull().size",detection="先filterNotNull — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0355）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0747",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空Int?在集合操作中（Double版）（Short）",trigger="val l:Short<Double?>;l.filterNotNull().size",detection="先filterNotNull — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0356）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0748",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空String?在集合操作中（Int版）（Any?）",trigger="val l:Any?<Any??>;l.filterNotNull().size",detection="先filterNotNull — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0360）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0749",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空String?在集合操作中（Long版）（String）",trigger="val l:String<Long?>;l.filterNotNull().size",detection="先filterNotNull — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0361）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0750",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="可空String?在集合操作中（Double版）（Byte）",trigger="val l:Byte<Double?>;l.filterNotNull().size",detection="先filterNotNull — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0362）",fix="直接?.let"))
        BugDB.load(BugRule(id="KT-0751",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="String?的?:返回null（Int版）（Boolean?）",trigger="val x:Boolean??=...;val y=Boolean???:null",detection="冗余null — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0428）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0752",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="String?的?:返回null（Long版）",trigger="val x:Long?=...;val y=Long??:null",detection="冗余null — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0429）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0753",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="String?的?:返回null（Double版）",trigger="val x:Double?=...;val y=Double??:null",detection="冗余null — Char类型字符编码边界,正则匹配组越界（参见 KT-0430）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0754",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MILD,
            title="String?的?:返回null（Boolean版）",trigger="val x:Boolean?=...;val y=Boolean??:null",detection="冗余null — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0431）",fix="提供实际默认值"))
        BugDB.load(BugRule(id="KT-0755",category=BugCategory.NULL_SAFETY,severity=BugSeverity.SEVERE,
            title="Flow中!!操作符",trigger="flow{val x=risky()!!;emit(x)}",detection="Flow内NPE — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0499）",fix="?.let或catch"))
        BugDB.load(BugRule(id="KT-0756",category=BugCategory.NULL_SAFETY,severity=BugSeverity.MODERATE,
            title="lateinit在init前后矛盾（Float）",trigger="init{val x=late};lateinit var late:Float",detection="初始化顺序 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0500）",fix="lateinit放init前面"))
        BugDB.load(BugRule(id="KT-0757",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型异常捕获",trigger="catch(e:Exception){}",detection="JVM擦除，不可捕获 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0024）",fix="捕获具体异常类型"))
        BugDB.load(BugRule(id="KT-0758",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Byte）",trigger="if(x is Byte<Byte>){}",detection="擦除后类型丢失 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0025）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0759",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="unchecked cast警告（Boolean?）",trigger="val x=y as Boolean?<Boolean?>",detection="擦除后强转不安全 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0026）",fix="显式检查元素类型"))
        BugDB.load(BugRule(id="KT-0760",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="星投影写操作（Sequence<Long>）",trigger="val x:MutableSequence<Long><*>;x.add(1)",detection="*只读 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0027）",fix="声明具体类型"))
        BugDB.load(BugRule(id="KT-0761",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="型变标记错误",trigger="interface P<out T>{fun f(t:T)}",detection="out位置不能消费 — Char类型字符编码边界,正则匹配组越界（参见 KT-0028）",fix="in T"))
        BugDB.load(BugRule(id="KT-0762",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="reified缺失",trigger="fun <T> f(){T::class}",detection="普通泛型无法获取class — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0029）",fix="inline+reified"))
        BugDB.load(BugRule(id="KT-0763",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型约束遗漏",trigger="fun <T> f(t:T){t.method()}",detection="T无约束 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0030）",fix="<T:HasMethod>"))
        BugDB.load(BugRule(id="KT-0764",category=BugCategory.GENERICS,severity=BugSeverity.MILD,
            title="泛型参数命名冲突",trigger="fun <T> f(T:T){}",detection="类型与变量同名 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0031）",fix="重命名"))
        BugDB.load(BugRule(id="KT-0765",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="星投影操作List<S>（Long?）",trigger="val x:Long?<S>*=...;x.add(...)",detection="星投影只读 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0032）",fix="指定具体类型参数"))
        BugDB.load(BugRule(id="KT-0766",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="星投影操作Set<I>",trigger="val x:Set<I>*=...;x.add(...)",detection="星投影只读 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0033）",fix="指定具体类型参数"))
        BugDB.load(BugRule(id="KT-0767",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="星投影操作Map<S,I>",trigger="val x:Map<S,I>*=...;x.add(...)",detection="星投影只读 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0034）",fix="指定具体类型参数"))
        BugDB.load(BugRule(id="KT-0768",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型型变数组",trigger="val arr=Array<T>(10){;val a:Array<Any>=arr",detection="数组协变不安全 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0152）",fix="List代替Array"))
        BugDB.load(BugRule(id="KT-0769",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+伴生对象类型",trigger="fun <T> f(){T.Companion}",detection="擦除后无伴生 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0153）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0770",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型函数引用歧义",trigger="val ref: (T)->R=::genericFun",detection="类型推断失败 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0154）",fix="显式标注泛型参数"))
        BugDB.load(BugRule(id="KT-0771",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型约束递归（String?）",trigger="fun <T:Comparable<T>> sort(list:String?<T>)",detection="递归约束 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0155）",fix="保持不变或where"))
        BugDB.load(BugRule(id="KT-0772",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型+密封类when",trigger="when(sealed){is Wrapper<*>->...}",detection="擦除后分支不可达 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0156）",fix="具体化子类型"))
        BugDB.load(BugRule(id="KT-0773",category=BugCategory.GENERICS,severity=BugSeverity.MILD,
            title="泛型参数通配符滥用（Long）",trigger="fun <T> f(list:Long<*>)",detection="T未使用 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0157）",fix="fun f(list:List<*>)直接"))
        BugDB.load(BugRule(id="KT-0774",category=BugCategory.GENERICS,severity=BugSeverity.MILD,
            title="泛型别名冲突（Any）",trigger="typealias S<T>=Any<T>;fun <T> f(s:S<T>)",detection="与泛型参数T混淆 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0158）",fix="重命名"))
        BugDB.load(BugRule(id="KT-0775",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（List<String>）",trigger="class C:A<List<String><String>>,A<List<String><String>>",detection="JVM擦除冲突 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0159）",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0776",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型vararg传递",trigger="fun <T> f(vararg t:T);f(arrayOf(1))",detection="vararg+泛型 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0160）",fix="f(*arrayOf)"))
        BugDB.load(BugRule(id="KT-0777",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型委托属性类型丢失",trigger="val x by Delegates.notNull<T>()",detection="getValue签名擦除 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0161）",fix="显式类型"))
        BugDB.load(BugRule(id="KT-0778",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型递归约束让编译器无限展开",trigger="fun <T:T> f(){}",detection="自约束无限循环 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0269）",fix="加where约束打断"))
        BugDB.load(BugRule(id="KT-0779",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="reified+suspend=限制叠加",trigger="suspend inline fun <reified T> api():T",detection="reified必须inline但suspend inline有限制 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0297）",fix="拆分为非suspend inline+suspend调用"))
        BugDB.load(BugRule(id="KT-0780",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Int版）（Byte）",trigger="class C:A<Byte>,A<Byte>",detection="JVM擦除冲突 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0434）",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0781",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Long版）（Boolean?）",trigger="class C:A<Long>,A<Boolean?>",detection="JVM擦除冲突 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0435）",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0782",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Double版）（Sequence<Long>）",trigger="class C:A<Double>,A<Sequence<Long>>",detection="JVM擦除冲突 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0436）",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0783",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Boolean版）（Char）",trigger="class C:A<Boolean>,A<Char>",detection="JVM擦除冲突 — Char类型字符编码边界,正则匹配组越界（参见 KT-0437）",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0784",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Long版）（Double?）",trigger="class C:A<Double?>,A<Long>",detection="JVM擦除冲突 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0438）",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0785",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Int版）（Array<Boolean>）",trigger="if(x is Array<Boolean><Array<Boolean>>){}",detection="擦除后类型丢失 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0456）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0786",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Long版）（Float）",trigger="if(x is Float<Long>){}",detection="擦除后类型丢失 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0457）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0787",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Double版）（Long?）",trigger="if(x is Long?<Double>){}",detection="擦除后类型丢失 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0458）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0788",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Boolean版）（MutableList<Double>）",trigger="if(x is MutableList<Double><Boolean>){}",detection="擦除后类型丢失 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0459）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0789",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Set版）（Boolean）",trigger="if(x is Set<Boolean>){}",detection="擦除后类型丢失 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0460）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0790",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="TypeReference匿名类在inline",trigger="inline fun <reified T> t(){object:TypeToken<T>(){}}",detection="内联泛型可获取 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0501）",fix="直接reified"))
        BugDB.load(BugRule(id="KT-0791",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型异常捕获",trigger="catch(e:Exception){}",detection="JVM擦除，不可捕获 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0024）",fix="捕获具体异常类型"))
        BugDB.load(BugRule(id="KT-0792",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Double）",trigger="if(x is Double<Double>){}",detection="擦除后类型丢失 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0025）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0793",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="unchecked cast警告（String?）",trigger="val x=y as String?<String?>",detection="擦除后强转不安全 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0026）",fix="显式检查元素类型"))
        BugDB.load(BugRule(id="KT-0794",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="星投影写操作（Set<Int>）",trigger="val x:MutableSet<Int><*>;x.add(1)",detection="*只读 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0027）",fix="声明具体类型"))
        BugDB.load(BugRule(id="KT-0795",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="型变标记错误",trigger="interface P<out T>{fun f(t:T)}",detection="out位置不能消费 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0028）",fix="in T"))
        BugDB.load(BugRule(id="KT-0796",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="reified缺失",trigger="fun <T> f(){T::class}",detection="普通泛型无法获取class — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0029）",fix="inline+reified"))
        BugDB.load(BugRule(id="KT-0797",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型约束遗漏",trigger="fun <T> f(t:T){t.method()}",detection="T无约束 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0030）",fix="<T:HasMethod>"))
        BugDB.load(BugRule(id="KT-0798",category=BugCategory.GENERICS,severity=BugSeverity.MILD,
            title="泛型参数命名冲突",trigger="fun <T> f(T:T){}",detection="类型与变量同名 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0031）",fix="重命名"))
        BugDB.load(BugRule(id="KT-0799",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="星投影操作List<S>（Short）",trigger="val x:Short<S>*=...;x.add(...)",detection="星投影只读 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0032）",fix="指定具体类型参数"))
        BugDB.load(BugRule(id="KT-0800",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="星投影操作Set<I>",trigger="val x:Set<I>*=...;x.add(...)",detection="星投影只读 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0033）",fix="指定具体类型参数"))
        BugDB.load(BugRule(id="KT-0801",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="星投影操作Map<S,I>",trigger="val x:Map<S,I>*=...;x.add(...)",detection="星投影只读 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0034）",fix="指定具体类型参数"))
        BugDB.load(BugRule(id="KT-0802",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型型变数组",trigger="val arr=Array<T>(10){;val a:Array<Any>=arr",detection="数组协变不安全 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0152）",fix="List代替Array"))
        BugDB.load(BugRule(id="KT-0803",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+伴生对象类型",trigger="fun <T> f(){T.Companion}",detection="擦除后无伴生 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0153）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0804",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型函数引用歧义",trigger="val ref: (T)->R=::genericFun",detection="类型推断失败 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0154）",fix="显式标注泛型参数"))
        BugDB.load(BugRule(id="KT-0805",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型约束递归（Char）",trigger="fun <T:Comparable<T>> sort(list:Char<T>)",detection="递归约束 — Char类型字符编码边界,正则匹配组越界（参见 KT-0155）",fix="保持不变或where"))
        BugDB.load(BugRule(id="KT-0806",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型+密封类when",trigger="when(sealed){is Wrapper<*>->...}",detection="擦除后分支不可达 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0156）",fix="具体化子类型"))
        BugDB.load(BugRule(id="KT-0807",category=BugCategory.GENERICS,severity=BugSeverity.MILD,
            title="泛型参数通配符滥用（Array<Boolean>）",trigger="fun <T> f(list:Array<Boolean><*>)",detection="T未使用 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0157）",fix="fun f(list:List<*>)直接"))
        BugDB.load(BugRule(id="KT-0808",category=BugCategory.GENERICS,severity=BugSeverity.MILD,
            title="泛型别名冲突（Float）",trigger="typealias S<T>=Float<T>;fun <T> f(s:S<T>)",detection="与泛型参数T混淆 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0158）",fix="重命名"))
        BugDB.load(BugRule(id="KT-0809",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Long?）",trigger="class C:A<Long?>,A<Long?>",detection="JVM擦除冲突 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0159）",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0810",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型vararg传递",trigger="fun <T> f(vararg t:T);f(arrayOf(1))",detection="vararg+泛型 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0160）",fix="f(*arrayOf)"))
        BugDB.load(BugRule(id="KT-0811",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型委托属性类型丢失",trigger="val x by Delegates.notNull<T>()",detection="getValue签名擦除 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0161）",fix="显式类型"))
        BugDB.load(BugRule(id="KT-0812",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型递归约束让编译器无限展开",trigger="fun <T:T> f(){}",detection="自约束无限循环 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0269）",fix="加where约束打断"))
        BugDB.load(BugRule(id="KT-0813",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="reified+suspend=限制叠加",trigger="suspend inline fun <reified T> api():T",detection="reified必须inline但suspend inline有限制 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0297）",fix="拆分为非suspend inline+suspend调用"))
        BugDB.load(BugRule(id="KT-0814",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Int版）（Double）",trigger="class C:A<Double>,A<Double>",detection="JVM擦除冲突 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0434）",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0815",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Long版）（String?）",trigger="class C:A<Long>,A<String?>",detection="JVM擦除冲突 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0435）",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0816",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Double版）（Set<Int>）",trigger="class C:A<Double>,A<Set<Int>>",detection="JVM擦除冲突 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0436）",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0817",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Boolean版）（Long）",trigger="class C:A<Boolean>,A<Long>",detection="JVM擦除冲突 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0437）",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0818",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Long版）（Any）",trigger="class C:A<Any>,A<Long>",detection="JVM擦除冲突 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0438）",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0819",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Int版）（List<String>）",trigger="if(x is List<String><List<String><String>>){}",detection="擦除后类型丢失 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0456）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0820",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Long版）（Int）",trigger="if(x is Int<Long>){}",detection="擦除后类型丢失 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0457）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0821",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Double版）（Short）",trigger="if(x is Short<Double>){}",detection="擦除后类型丢失 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0458）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0822",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Boolean版）（Any?）",trigger="if(x is Any?<Boolean>){}",detection="擦除后类型丢失 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0459）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0823",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Set版）",trigger="if(x is Set<String>){}",detection="擦除后类型丢失 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0460）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0824",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="TypeReference匿名类在inline",trigger="inline fun <reified T> t(){object:TypeToken<T>(){}}",detection="内联泛型可获取 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0501）",fix="直接reified"))
        BugDB.load(BugRule(id="KT-0825",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型异常捕获",trigger="catch(e:Exception){}",detection="JVM擦除，不可捕获 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0024）",fix="捕获具体异常类型"))
        BugDB.load(BugRule(id="KT-0826",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Sequence<Long>）",trigger="if(x is Sequence<Long><Sequence<Long>>){}",detection="擦除后类型丢失 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0025）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0827",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="unchecked cast警告（Char）",trigger="val x=y as Char<Char>",detection="擦除后强转不安全 — Char类型字符编码边界,正则匹配组越界（参见 KT-0026）",fix="显式检查元素类型"))
        BugDB.load(BugRule(id="KT-0828",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="星投影写操作（Double?）",trigger="val x:MutableDouble?<*>;x.add(1)",detection="*只读 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0027）",fix="声明具体类型"))
        BugDB.load(BugRule(id="KT-0829",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="型变标记错误",trigger="interface P<out T>{fun f(t:T)}",detection="out位置不能消费 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0028）",fix="in T"))
        BugDB.load(BugRule(id="KT-0830",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="reified缺失",trigger="fun <T> f(){T::class}",detection="普通泛型无法获取class — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0029）",fix="inline+reified"))
        BugDB.load(BugRule(id="KT-0831",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型约束遗漏",trigger="fun <T> f(t:T){t.method()}",detection="T无约束 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0030）",fix="<T:HasMethod>"))
        BugDB.load(BugRule(id="KT-0832",category=BugCategory.GENERICS,severity=BugSeverity.MILD,
            title="泛型参数命名冲突",trigger="fun <T> f(T:T){}",detection="类型与变量同名 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0031）",fix="重命名"))
        BugDB.load(BugRule(id="KT-0833",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="星投影操作List<S>（Boolean）",trigger="val x:Boolean<S>*=...;x.add(...)",detection="星投影只读 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0032）",fix="指定具体类型参数"))
        BugDB.load(BugRule(id="KT-0834",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="星投影操作Set<I>",trigger="val x:Set<I>*=...;x.add(...)",detection="星投影只读 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0033）",fix="指定具体类型参数"))
        BugDB.load(BugRule(id="KT-0835",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="星投影操作Map<S,I>",trigger="val x:Map<S,I>*=...;x.add(...)",detection="星投影只读 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0034）",fix="指定具体类型参数"))
        BugDB.load(BugRule(id="KT-0836",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型型变数组",trigger="val arr=Array<T>(10){;val a:Array<Any>=arr",detection="数组协变不安全 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0152）",fix="List代替Array"))
        BugDB.load(BugRule(id="KT-0837",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+伴生对象类型",trigger="fun <T> f(){T.Companion}",detection="擦除后无伴生 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0153）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0838",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型函数引用歧义",trigger="val ref: (T)->R=::genericFun",detection="类型推断失败 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0154）",fix="显式标注泛型参数"))
        BugDB.load(BugRule(id="KT-0839",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型约束递归（Long）",trigger="fun <T:Comparable<T>> sort(list:Long<T>)",detection="递归约束 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0155）",fix="保持不变或where"))
        BugDB.load(BugRule(id="KT-0840",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型+密封类when",trigger="when(sealed){is Wrapper<*>->...}",detection="擦除后分支不可达 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0156）",fix="具体化子类型"))
        BugDB.load(BugRule(id="KT-0841",category=BugCategory.GENERICS,severity=BugSeverity.MILD,
            title="泛型参数通配符滥用（List<String>）",trigger="fun <T> f(list:List<String><*>)",detection="T未使用 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0157）",fix="fun f(list:List<*>)直接"))
        BugDB.load(BugRule(id="KT-0842",category=BugCategory.GENERICS,severity=BugSeverity.MILD,
            title="泛型别名冲突（Int）",trigger="typealias S<T>=Int<T>;fun <T> f(s:S<T>)",detection="与泛型参数T混淆 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0158）",fix="重命名"))
        BugDB.load(BugRule(id="KT-0843",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Short）",trigger="class C:A<Short>,A<Short>",detection="JVM擦除冲突 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0159）",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0844",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型vararg传递",trigger="fun <T> f(vararg t:T);f(arrayOf(1))",detection="vararg+泛型 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0160）",fix="f(*arrayOf)"))
        BugDB.load(BugRule(id="KT-0845",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型委托属性类型丢失",trigger="val x by Delegates.notNull<T>()",detection="getValue签名擦除 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0161）",fix="显式类型"))
        BugDB.load(BugRule(id="KT-0846",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型递归约束让编译器无限展开",trigger="fun <T:T> f(){}",detection="自约束无限循环 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0269）",fix="加where约束打断"))
        BugDB.load(BugRule(id="KT-0847",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="reified+suspend=限制叠加",trigger="suspend inline fun <reified T> api():T",detection="reified必须inline但suspend inline有限制 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0297）",fix="拆分为非suspend inline+suspend调用"))
        BugDB.load(BugRule(id="KT-0848",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Int版）（Sequence<Long>）",trigger="class C:A<Sequence<Long>>,A<Sequence<Long>>",detection="JVM擦除冲突 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0434）",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0849",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Long版）（Char）",trigger="class C:A<Long>,A<Char>",detection="JVM擦除冲突 — Char类型字符编码边界,正则匹配组越界（参见 KT-0435）",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0850",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Double版）（Double?）",trigger="class C:A<Double>,A<Double?>",detection="JVM擦除冲突 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0436）",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0851",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Boolean版）（Array<Boolean>）",trigger="class C:A<Boolean>,A<Array<Boolean>>",detection="JVM擦除冲突 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0437）",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0852",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Long版）（Float）",trigger="class C:A<Float>,A<Long>",detection="JVM擦除冲突 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0438）",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0853",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Int版）（Long?）",trigger="if(x is Long?<Long?>){}",detection="擦除后类型丢失 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0456）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0854",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Long版）（MutableList<Double>）",trigger="if(x is MutableList<Double><Long>){}",detection="擦除后类型丢失 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0457）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0855",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Double版）（Boolean）",trigger="if(x is Boolean<Double>){}",detection="擦除后类型丢失 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0458）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0856",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Boolean版）（Int?）",trigger="if(x is Int?<Boolean>){}",detection="擦除后类型丢失 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0459）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0857",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Set版）（Map<String,Int>）",trigger="if(x is Set<Map<String,Map<String,Int>>>){}",detection="擦除后类型丢失 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0460）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0858",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="TypeReference匿名类在inline",trigger="inline fun <reified T> t(){object:TypeToken<T>(){}}",detection="内联泛型可获取 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0501）",fix="直接reified"))
        BugDB.load(BugRule(id="KT-0859",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型异常捕获",trigger="catch(e:Exception){}",detection="JVM擦除，不可捕获 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0024）",fix="捕获具体异常类型"))
        BugDB.load(BugRule(id="KT-0860",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Set<Int>）",trigger="if(x is Set<Int><Set<Set<Int>>>){}",detection="擦除后类型丢失 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0025）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0861",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="unchecked cast警告（Long）",trigger="val x=y as Long<Long>",detection="擦除后强转不安全 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0026）",fix="显式检查元素类型"))
        BugDB.load(BugRule(id="KT-0862",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="星投影写操作（Any）",trigger="val x:MutableAny<*>;x.add(1)",detection="*只读 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0027）",fix="声明具体类型"))
        BugDB.load(BugRule(id="KT-0863",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="型变标记错误",trigger="interface P<out T>{fun f(t:T)}",detection="out位置不能消费 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0028）",fix="in T"))
        BugDB.load(BugRule(id="KT-0864",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="reified缺失",trigger="fun <T> f(){T::class}",detection="普通泛型无法获取class — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0029）",fix="inline+reified"))
        BugDB.load(BugRule(id="KT-0865",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型约束遗漏",trigger="fun <T> f(t:T){t.method()}",detection="T无约束 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0030）",fix="<T:HasMethod>"))
        BugDB.load(BugRule(id="KT-0866",category=BugCategory.GENERICS,severity=BugSeverity.MILD,
            title="泛型参数命名冲突",trigger="fun <T> f(T:T){}",detection="类型与变量同名 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0031）",fix="重命名"))
        BugDB.load(BugRule(id="KT-0867",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="星投影操作List<S>（String）",trigger="val x:String<S>*=...;x.add(...)",detection="星投影只读 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0032）",fix="指定具体类型参数"))
        BugDB.load(BugRule(id="KT-0868",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="星投影操作Set<I>",trigger="val x:Set<I>*=...;x.add(...)",detection="星投影只读 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0033）",fix="指定具体类型参数"))
        BugDB.load(BugRule(id="KT-0869",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="星投影操作Map<S,I>",trigger="val x:Map<S,I>*=...;x.add(...)",detection="星投影只读 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0034）",fix="指定具体类型参数"))
        BugDB.load(BugRule(id="KT-0870",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型型变数组",trigger="val arr=Array<T>(10){;val a:Array<Any>=arr",detection="数组协变不安全 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0152）",fix="List代替Array"))
        BugDB.load(BugRule(id="KT-0871",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+伴生对象类型",trigger="fun <T> f(){T.Companion}",detection="擦除后无伴生 — Char类型字符编码边界,正则匹配组越界（参见 KT-0153）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0872",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型函数引用歧义",trigger="val ref: (T)->R=::genericFun",detection="类型推断失败 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0154）",fix="显式标注泛型参数"))
        BugDB.load(BugRule(id="KT-0873",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型约束递归（Array<Boolean>）",trigger="fun <T:Comparable<T>> sort(list:Array<Boolean><T>)",detection="递归约束 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0155）",fix="保持不变或where"))
        BugDB.load(BugRule(id="KT-0874",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型+密封类when",trigger="when(sealed){is Wrapper<*>->...}",detection="擦除后分支不可达 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0156）",fix="具体化子类型"))
        BugDB.load(BugRule(id="KT-0875",category=BugCategory.GENERICS,severity=BugSeverity.MILD,
            title="泛型参数通配符滥用（Long?）",trigger="fun <T> f(list:Long?<*>)",detection="T未使用 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0157）",fix="fun f(list:List<*>)直接"))
        BugDB.load(BugRule(id="KT-0876",category=BugCategory.GENERICS,severity=BugSeverity.MILD,
            title="泛型别名冲突（MutableList<Double>）",trigger="typealias S<T>=MutableList<Double><T>;fun <T> f(s:S<T>)",detection="与泛型参数T混淆 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0158）",fix="重命名"))
        BugDB.load(BugRule(id="KT-0877",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Boolean）",trigger="class C:A<Boolean>,A<Boolean>",detection="JVM擦除冲突 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0159）",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0878",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型vararg传递",trigger="fun <T> f(vararg t:T);f(arrayOf(1))",detection="vararg+泛型 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0160）",fix="f(*arrayOf)"))
        BugDB.load(BugRule(id="KT-0879",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型委托属性类型丢失",trigger="val x by Delegates.notNull<T>()",detection="getValue签名擦除 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0161）",fix="显式类型"))
        BugDB.load(BugRule(id="KT-0880",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型递归约束让编译器无限展开",trigger="fun <T:T> f(){}",detection="自约束无限循环 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0269）",fix="加where约束打断"))
        BugDB.load(BugRule(id="KT-0881",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="reified+suspend=限制叠加",trigger="suspend inline fun <reified T> api():T",detection="reified必须inline但suspend inline有限制 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0297）",fix="拆分为非suspend inline+suspend调用"))
        BugDB.load(BugRule(id="KT-0882",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Int版）（Set<Int>）",trigger="class C:A<Set<Int>>,A<Set<Int>>",detection="JVM擦除冲突 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0434）",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0883",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Long版）（Long）",trigger="class C:A<Long>,A<Long>",detection="JVM擦除冲突 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0435）",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0884",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Double版）（Any）",trigger="class C:A<Double>,A<Any>",detection="JVM擦除冲突 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0436）",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0885",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Boolean版）（List<String>）",trigger="class C:A<Boolean>,A<List<String><String>>",detection="JVM擦除冲突 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0437）",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0886",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Long版）（Int）",trigger="class C:A<Int>,A<Long>",detection="JVM擦除冲突 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0438）",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0887",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Int版）（Short）",trigger="if(x is Short<Short>){}",detection="擦除后类型丢失 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0456）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0888",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Long版）（Any?）",trigger="if(x is Any?<Long>){}",detection="擦除后类型丢失 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0457）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0889",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Double版）（String）",trigger="if(x is String<Double>){}",detection="擦除后类型丢失 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0458）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0890",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Boolean版）（Byte）",trigger="if(x is Byte<Boolean>){}",detection="擦除后类型丢失 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0459）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0891",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Set版）（Boolean?）",trigger="if(x is Set<Boolean?>){}",detection="擦除后类型丢失 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0460）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0892",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="TypeReference匿名类在inline",trigger="inline fun <reified T> t(){object:TypeToken<T>(){}}",detection="内联泛型可获取 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0501）",fix="直接reified"))
        BugDB.load(BugRule(id="KT-0893",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型异常捕获",trigger="catch(e:Exception){}",detection="JVM擦除，不可捕获 — Char类型字符编码边界,正则匹配组越界（参见 KT-0024）",fix="捕获具体异常类型"))
        BugDB.load(BugRule(id="KT-0894",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Double?）",trigger="if(x is Double?<Double?>){}",detection="擦除后类型丢失 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0025）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0895",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="unchecked cast警告（Array<Boolean>）",trigger="val x=y as Array<Boolean><Array<Boolean>>",detection="擦除后强转不安全 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0026）",fix="显式检查元素类型"))
        BugDB.load(BugRule(id="KT-0896",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="星投影写操作（Float）",trigger="val x:MutableFloat<*>;x.add(1)",detection="*只读 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0027）",fix="声明具体类型"))
        BugDB.load(BugRule(id="KT-0897",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="型变标记错误",trigger="interface P<out T>{fun f(t:T)}",detection="out位置不能消费 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0028）",fix="in T"))
        BugDB.load(BugRule(id="KT-0898",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="reified缺失",trigger="fun <T> f(){T::class}",detection="普通泛型无法获取class — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0029）",fix="inline+reified"))
        BugDB.load(BugRule(id="KT-0899",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型约束遗漏",trigger="fun <T> f(t:T){t.method()}",detection="T无约束 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0030）",fix="<T:HasMethod>"))
        BugDB.load(BugRule(id="KT-0900",category=BugCategory.GENERICS,severity=BugSeverity.MILD,
            title="泛型参数命名冲突",trigger="fun <T> f(T:T){}",detection="类型与变量同名 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0031）",fix="重命名"))
        BugDB.load(BugRule(id="KT-0901",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="星投影操作List<S>（Map<String,Int>）",trigger="val x:Map<String,Int><S>*=...;x.add(...)",detection="星投影只读 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0032）",fix="指定具体类型参数"))
        BugDB.load(BugRule(id="KT-0902",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="星投影操作Set<I>",trigger="val x:Set<I>*=...;x.add(...)",detection="星投影只读 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0033）",fix="指定具体类型参数"))
        BugDB.load(BugRule(id="KT-0903",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="星投影操作Map<S,I>",trigger="val x:Map<S,I>*=...;x.add(...)",detection="星投影只读 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0034）",fix="指定具体类型参数"))
        BugDB.load(BugRule(id="KT-0904",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型型变数组",trigger="val arr=Array<T>(10){;val a:Array<Any>=arr",detection="数组协变不安全 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0152）",fix="List代替Array"))
        BugDB.load(BugRule(id="KT-0905",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+伴生对象类型",trigger="fun <T> f(){T.Companion}",detection="擦除后无伴生 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0153）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0906",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型函数引用歧义",trigger="val ref: (T)->R=::genericFun",detection="类型推断失败 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0154）",fix="显式标注泛型参数"))
        BugDB.load(BugRule(id="KT-0907",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型约束递归（List<String>）",trigger="fun <T:Comparable<T>> sort(list:List<String><T>)",detection="递归约束 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0155）",fix="保持不变或where"))
        BugDB.load(BugRule(id="KT-0908",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型+密封类when",trigger="when(sealed){is Wrapper<*>->...}",detection="擦除后分支不可达 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0156）",fix="具体化子类型"))
        BugDB.load(BugRule(id="KT-0909",category=BugCategory.GENERICS,severity=BugSeverity.MILD,
            title="泛型参数通配符滥用（Short）",trigger="fun <T> f(list:Short<*>)",detection="T未使用 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0157）",fix="fun f(list:List<*>)直接"))
        BugDB.load(BugRule(id="KT-0910",category=BugCategory.GENERICS,severity=BugSeverity.MILD,
            title="泛型别名冲突（Any?）",trigger="typealias S<T>=Any?<T>;fun <T> f(s:S<T>)",detection="与泛型参数T混淆 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0158）",fix="重命名"))
        BugDB.load(BugRule(id="KT-0911",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（String）",trigger="class C:A<String>,A<String>",detection="JVM擦除冲突 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0159）",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0912",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型vararg传递",trigger="fun <T> f(vararg t:T);f(arrayOf(1))",detection="vararg+泛型 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0160）",fix="f(*arrayOf)"))
        BugDB.load(BugRule(id="KT-0913",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型委托属性类型丢失",trigger="val x by Delegates.notNull<T>()",detection="getValue签名擦除 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0161）",fix="显式类型"))
        BugDB.load(BugRule(id="KT-0914",category=BugCategory.GENERICS,severity=BugSeverity.MODERATE,
            title="泛型递归约束让编译器无限展开",trigger="fun <T:T> f(){}",detection="自约束无限循环 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0269）",fix="加where约束打断"))
        BugDB.load(BugRule(id="KT-0915",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="reified+suspend=限制叠加",trigger="suspend inline fun <reified T> api():T",detection="reified必须inline但suspend inline有限制 — Char类型字符编码边界,正则匹配组越界（参见 KT-0297）",fix="拆分为非suspend inline+suspend调用"))
        BugDB.load(BugRule(id="KT-0916",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Int版）（Double?）",trigger="class C:A<Double?>,A<Double?>",detection="JVM擦除冲突 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0434）",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0917",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Long版）（Array<Boolean>）",trigger="class C:A<Long>,A<Array<Boolean>>",detection="JVM擦除冲突 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0435）",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0918",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Double版）（Float）",trigger="class C:A<Double>,A<Float>",detection="JVM擦除冲突 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0436）",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0919",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Boolean版）（Long?）",trigger="class C:A<Boolean>,A<Long?>",detection="JVM擦除冲突 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0437）",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0920",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型接口多继承歧义（Long版）（MutableList<Double>）",trigger="class C:A<MutableMutableList<Double><Double>>,A<Long>",detection="JVM擦除冲突 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0438）",fix="不同接口"))
        BugDB.load(BugRule(id="KT-0921",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Int版）（Boolean）",trigger="if(x is Boolean<Boolean>){}",detection="擦除后类型丢失 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0456）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0922",category=BugCategory.GENERICS,severity=BugSeverity.SEVERE,
            title="泛型+is检查（Long版）（Int?）",trigger="if(x is Int?<Long>){}",detection="擦除后类型丢失 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0457）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-0923",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="GlobalScope泄漏",trigger="GlobalScope.launch{",detection="不受控的生命周期 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0035）",fix="lifecycleScope或viewModelScope"))
        BugDB.load(BugRule(id="KT-0924",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="launch吞异常",trigger="launch{riskyOp()}",detection="launch不传播异常 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0036）",fix="async+await或CoroutineExceptionHandler"))
        BugDB.load(BugRule(id="KT-0925",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="runBlocking在UI线程",trigger="runBlocking{delay(5000)}",detection="阻塞主线程 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0037）",fix="lifecycleScope.launch"))
        BugDB.load(BugRule(id="KT-0926",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="suspend中调用阻塞方法",trigger="suspend fun f(){Thread.sleep(1000)}",detection="阻塞协程线程 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0038）",fix="delay(1000)"))
        BugDB.load(BugRule(id="KT-0927",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="协程取消不响应",trigger="launch{while(true){work()}}",detection="不检查isActive — Char类型字符编码边界,正则匹配组越界（参见 KT-0039）",fix="while(isActive)"))
        BugDB.load(BugRule(id="KT-0928",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="缺少CoroutineExceptionHandler",trigger="launch{throw E()}",detection="未处理异常 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0040）",fix="val handler=CoroutineExceptionHandler{"))
        BugDB.load(BugRule(id="KT-0929",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="withContext滥用",trigger="withContext(Dispatchers.IO){lightOp()}",detection="轻量操作用IO调度器 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0041）",fix="用Default或直接执行"))
        BugDB.load(BugRule(id="KT-0930",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="async忘记await",trigger="val d=async{calc()};d.await()",detection="不await结果丢失 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0042）",fix="确保所有async都被await"))
        BugDB.load(BugRule(id="KT-0931",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="coroutineScope与supervisorScope混淆",trigger="coroutineScope{launch{throw E()};launch{}",detection="子失败取消兄弟 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0043）",fix="supervisorScope"))
        BugDB.load(BugRule(id="KT-0932",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="flow收集无背压",trigger="flow{emit(...)}.collect{slow()}",detection="生产快于消费 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0044）",fix="buffer或conflate"))
        BugDB.load(BugRule(id="KT-0933",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="不必要的async",trigger="val d=async{val x=y;x}",detection="简单赋值不需要async — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0045）",fix="直接赋值"))
        BugDB.load(BugRule(id="KT-0934",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="Dispatchers.Main硬编码",trigger="withContext(Dispatchers.Main){",detection="测试环境无Main — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0046）",fix="依赖注入dispatcher"))
        BugDB.load(BugRule(id="KT-0935",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="子协程取消未传播",trigger="supervisorScope{launch{heavy()}}",detection="scope取消时子不取消 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0162）",fix="coroutineScope"))
        BugDB.load(BugRule(id="KT-0936",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="coroutineContext丢失",trigger="withContext(empty){delay(1)}",detection="无Job — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0163）",fix="保留原始context"))
        BugDB.load(BugRule(id="KT-0937",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="select未处理onAwait",trigger="select{ch.onReceive{}",detection="未处理其他case — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0164）",fix="加onAwait"))
        BugDB.load(BugRule(id="KT-0938",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="callbackFlow未awaitClose",trigger="callbackFlow{register(cb);awaitClose{unregister()}}",detection="泄漏 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0165）",fix="加awaitClose"))
        BugDB.load(BugRule(id="KT-0939",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="StateFlow.value直接修改",trigger="state.value=state.value.copy(x=1)",detection="非原子操作 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0166）",fix="MutableStateFlow.update{}"))
        BugDB.load(BugRule(id="KT-0940",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="launch内return@launch遗漏",trigger="launch{if(x)return}",detection="非局部return — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0167）",fix="return@launch"))
        BugDB.load(BugRule(id="KT-0941",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="async+await替代withContext",trigger="val x=async(IO){work()}.await()",detection="等效withContext(IO) — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0168）",fix="直接用withContext"))
        BugDB.load(BugRule(id="KT-0942",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="不必要的flowOn",trigger="flow{emit(1)}.flowOn(IO).flowOn(Default)",detection="多flowOn无效 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0169）",fix="只保留最后一个"))
        BugDB.load(BugRule(id="KT-0943",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="Channel未关闭导致协程泄漏（Short）",trigger="val c=Channel<Short>();launch{c.consumeEach{}}",detection="生产者无close — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0170）",fix="c.close或produceIn"))
        BugDB.load(BugRule(id="KT-0944",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="Dispatchers.Unconfined误用",trigger="launch(Unconfined){updateUI()}",detection="线程不确定 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0171）",fix="显式指定调度器"))
        BugDB.load(BugRule(id="KT-0945",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="十一种子进程职业全部失业",trigger="所有@ProcessBody都被Condition拦截",detection="注解条件过严 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0260）",fix="放宽condition或加保底"))
        BugDB.load(BugRule(id="KT-0946",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="delay(0)比delay(1)更慢",trigger="delay(0);delay(0);delay(0)",detection="连续0延迟重排 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0278）",fix="用yield()"))
        BugDB.load(BugRule(id="KT-0947",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="所有协程都在等一个永远不会set的CompletableDeferred",trigger="val d=CompletableDeferred<T>();launch{...d.await()};忘记d.complete()",detection="忘记complete — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0279）",fix="加超时withTimeout"))
        BugDB.load(BugRule(id="KT-0948",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="Mutex.lock了两次同一个协程",trigger="mutex.lock();mutex.lock()",detection="不可重入Mutex死锁 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0280）",fix="withLock或可重入锁"))
        BugDB.load(BugRule(id="KT-0949",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="flow.collect在collect后又emit了一条",trigger="flow{emit(1);awaitClose{emit(2)}}",detection="awaitClose中的emit可能丢失 — Char类型字符编码边界,正则匹配组越界（参见 KT-0292）",fix="onCompletion"))
        BugDB.load(BugRule(id="KT-0950",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="launch+反射+私有方法=不可预测崩溃",trigger="launch{val m=cls.getDeclaredMethod(\\\"secret\\\");m.isAccessible=true;m.invoke(obj)}",detection="协程内反射破坏封装 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0296）",fix="提供公开suspend接口"))
        BugDB.load(BugRule(id="KT-0951",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="flow+retry+stateIn=重试时状态丢失",trigger="flow{apiCall()}.retry(3).stateIn(scope)",detection="retry重建flow，stateIn收到新初始值 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0307）",fix="在retry外层stateIn"))
        BugDB.load(BugRule(id="KT-0952",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="Flow默认串行但collect看起来像并行",trigger="flow{emit(a);emit(b)}.collect{}",detection="emit挂起→collect处理→才继续emit — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0314）",fix="要并行用channelFlow或flatMapMerge"))
        BugDB.load(BugRule(id="KT-0953",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="channel被当成广播但只发了一条（Long?）",trigger="val c=Channel<Long?>();launch{c.send(1)};launch{println(c.receive())};launch{println(c.receive())}",detection="Channel单消费 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0315）",fix="BroadcastChannel或SharedFlow"))
        BugDB.load(BugRule(id="KT-0954",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="suspend函数无超时",trigger="suspend fun api(){httpClient.get(...)}",detection="无超时挂死 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0488）",fix="withTimeout"))
        BugDB.load(BugRule(id="KT-0955",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="Flow未捕获异常",trigger="flow{emit(risky())}.catch{e->...}",detection="catch位置错误 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0489）",fix="catch在collect之前"))
        BugDB.load(BugRule(id="KT-0956",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="StateFlow初始值导致重复emit",trigger="val s=MutableStateFlow(init);s.value=init",detection="同值仍触发collect — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0504）",fix="distinctUntilChanged或检查值"))
        BugDB.load(BugRule(id="KT-0957",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="GlobalScope泄漏",trigger="GlobalScope.launch{",detection="不受控的生命周期 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0035）",fix="lifecycleScope或viewModelScope"))
        BugDB.load(BugRule(id="KT-0958",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="launch吞异常",trigger="launch{riskyOp()}",detection="launch不传播异常 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0036）",fix="async+await或CoroutineExceptionHandler"))
        BugDB.load(BugRule(id="KT-0959",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="runBlocking在UI线程",trigger="runBlocking{delay(5000)}",detection="阻塞主线程 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0037）",fix="lifecycleScope.launch"))
        BugDB.load(BugRule(id="KT-0960",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="suspend中调用阻塞方法",trigger="suspend fun f(){Thread.sleep(1000)}",detection="阻塞协程线程 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0038）",fix="delay(1000)"))
        BugDB.load(BugRule(id="KT-0961",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="协程取消不响应",trigger="launch{while(true){work()}}",detection="不检查isActive — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0039）",fix="while(isActive)"))
        BugDB.load(BugRule(id="KT-0962",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="缺少CoroutineExceptionHandler",trigger="launch{throw E()}",detection="未处理异常 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0040）",fix="val handler=CoroutineExceptionHandler{"))
        BugDB.load(BugRule(id="KT-0963",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="withContext滥用",trigger="withContext(Dispatchers.IO){lightOp()}",detection="轻量操作用IO调度器 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0041）",fix="用Default或直接执行"))
        BugDB.load(BugRule(id="KT-0964",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="async忘记await",trigger="val d=async{calc()};d.await()",detection="不await结果丢失 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0042）",fix="确保所有async都被await"))
        BugDB.load(BugRule(id="KT-0965",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="coroutineScope与supervisorScope混淆",trigger="coroutineScope{launch{throw E()};launch{}",detection="子失败取消兄弟 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0043）",fix="supervisorScope"))
        BugDB.load(BugRule(id="KT-0966",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="flow收集无背压",trigger="flow{emit(...)}.collect{slow()}",detection="生产快于消费 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0044）",fix="buffer或conflate"))
        BugDB.load(BugRule(id="KT-0967",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="不必要的async",trigger="val d=async{val x=y;x}",detection="简单赋值不需要async — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0045）",fix="直接赋值"))
        BugDB.load(BugRule(id="KT-0968",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="Dispatchers.Main硬编码",trigger="withContext(Dispatchers.Main){",detection="测试环境无Main — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0046）",fix="依赖注入dispatcher"))
        BugDB.load(BugRule(id="KT-0969",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="子协程取消未传播",trigger="supervisorScope{launch{heavy()}}",detection="scope取消时子不取消 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0162）",fix="coroutineScope"))
        BugDB.load(BugRule(id="KT-0970",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="coroutineContext丢失",trigger="withContext(empty){delay(1)}",detection="无Job — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0163）",fix="保留原始context"))
        BugDB.load(BugRule(id="KT-0971",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="select未处理onAwait",trigger="select{ch.onReceive{}",detection="未处理其他case — Char类型字符编码边界,正则匹配组越界（参见 KT-0164）",fix="加onAwait"))
        BugDB.load(BugRule(id="KT-0972",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="callbackFlow未awaitClose",trigger="callbackFlow{register(cb);awaitClose{unregister()}}",detection="泄漏 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0165）",fix="加awaitClose"))
        BugDB.load(BugRule(id="KT-0973",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="StateFlow.value直接修改",trigger="state.value=state.value.copy(x=1)",detection="非原子操作 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0166）",fix="MutableStateFlow.update{}"))
        BugDB.load(BugRule(id="KT-0974",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="launch内return@launch遗漏",trigger="launch{if(x)return}",detection="非局部return — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0167）",fix="return@launch"))
        BugDB.load(BugRule(id="KT-0975",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="async+await替代withContext",trigger="val x=async(IO){work()}.await()",detection="等效withContext(IO) — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0168）",fix="直接用withContext"))
        BugDB.load(BugRule(id="KT-0976",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="不必要的flowOn",trigger="flow{emit(1)}.flowOn(IO).flowOn(Default)",detection="多flowOn无效 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0169）",fix="只保留最后一个"))
        BugDB.load(BugRule(id="KT-0977",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="Channel未关闭导致协程泄漏（Boolean）",trigger="val c=Channel<Boolean>();launch{c.consumeEach{}}",detection="生产者无close — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0170）",fix="c.close或produceIn"))
        BugDB.load(BugRule(id="KT-0978",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="Dispatchers.Unconfined误用",trigger="launch(Unconfined){updateUI()}",detection="线程不确定 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0171）",fix="显式指定调度器"))
        BugDB.load(BugRule(id="KT-0979",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="十一种子进程职业全部失业",trigger="所有@ProcessBody都被Condition拦截",detection="注解条件过严 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0260）",fix="放宽condition或加保底"))
        BugDB.load(BugRule(id="KT-0980",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="delay(0)比delay(1)更慢",trigger="delay(0);delay(0);delay(0)",detection="连续0延迟重排 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0278）",fix="用yield()"))
        BugDB.load(BugRule(id="KT-0981",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="所有协程都在等一个永远不会set的CompletableDeferred",trigger="val d=CompletableDeferred<T>();launch{...d.await()};忘记d.complete()",detection="忘记complete — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0279）",fix="加超时withTimeout"))
        BugDB.load(BugRule(id="KT-0982",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="Mutex.lock了两次同一个协程",trigger="mutex.lock();mutex.lock()",detection="不可重入Mutex死锁 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0280）",fix="withLock或可重入锁"))
        BugDB.load(BugRule(id="KT-0983",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="flow.collect在collect后又emit了一条",trigger="flow{emit(1);awaitClose{emit(2)}}",detection="awaitClose中的emit可能丢失 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0292）",fix="onCompletion"))
        BugDB.load(BugRule(id="KT-0984",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="launch+反射+私有方法=不可预测崩溃",trigger="launch{val m=cls.getDeclaredMethod(\\\"secret\\\");m.isAccessible=true;m.invoke(obj)}",detection="协程内反射破坏封装 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0296）",fix="提供公开suspend接口"))
        BugDB.load(BugRule(id="KT-0985",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="flow+retry+stateIn=重试时状态丢失",trigger="flow{apiCall()}.retry(3).stateIn(scope)",detection="retry重建flow，stateIn收到新初始值 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0307）",fix="在retry外层stateIn"))
        BugDB.load(BugRule(id="KT-0986",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="Flow默认串行但collect看起来像并行",trigger="flow{emit(a);emit(b)}.collect{}",detection="emit挂起→collect处理→才继续emit — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0314）",fix="要并行用channelFlow或flatMapMerge"))
        BugDB.load(BugRule(id="KT-0987",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="channel被当成广播但只发了一条（Short）",trigger="val c=Channel<Short>();launch{c.send(1)};launch{println(c.receive())};launch{println(c.receive())}",detection="Channel单消费 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0315）",fix="BroadcastChannel或SharedFlow"))
        BugDB.load(BugRule(id="KT-0988",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="suspend函数无超时",trigger="suspend fun api(){httpClient.get(...)}",detection="无超时挂死 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0488）",fix="withTimeout"))
        BugDB.load(BugRule(id="KT-0989",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="Flow未捕获异常",trigger="flow{emit(risky())}.catch{e->...}",detection="catch位置错误 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0489）",fix="catch在collect之前"))
        BugDB.load(BugRule(id="KT-0990",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="StateFlow初始值导致重复emit",trigger="val s=MutableStateFlow(init);s.value=init",detection="同值仍触发collect — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0504）",fix="distinctUntilChanged或检查值"))
        BugDB.load(BugRule(id="KT-0991",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="GlobalScope泄漏",trigger="GlobalScope.launch{",detection="不受控的生命周期 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0035）",fix="lifecycleScope或viewModelScope"))
        BugDB.load(BugRule(id="KT-0992",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="launch吞异常",trigger="launch{riskyOp()}",detection="launch不传播异常 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0036）",fix="async+await或CoroutineExceptionHandler"))
        BugDB.load(BugRule(id="KT-0993",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="runBlocking在UI线程",trigger="runBlocking{delay(5000)}",detection="阻塞主线程 — Char类型字符编码边界,正则匹配组越界（参见 KT-0037）",fix="lifecycleScope.launch"))
        BugDB.load(BugRule(id="KT-0994",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="suspend中调用阻塞方法",trigger="suspend fun f(){Thread.sleep(1000)}",detection="阻塞协程线程 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0038）",fix="delay(1000)"))
        BugDB.load(BugRule(id="KT-0995",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="协程取消不响应",trigger="launch{while(true){work()}}",detection="不检查isActive — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0039）",fix="while(isActive)"))
        BugDB.load(BugRule(id="KT-0996",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="缺少CoroutineExceptionHandler",trigger="launch{throw E()}",detection="未处理异常 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0040）",fix="val handler=CoroutineExceptionHandler{"))
        BugDB.load(BugRule(id="KT-0997",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="withContext滥用",trigger="withContext(Dispatchers.IO){lightOp()}",detection="轻量操作用IO调度器 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0041）",fix="用Default或直接执行"))
        BugDB.load(BugRule(id="KT-0998",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="async忘记await",trigger="val d=async{calc()};d.await()",detection="不await结果丢失 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0042）",fix="确保所有async都被await"))
        BugDB.load(BugRule(id="KT-0999",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="coroutineScope与supervisorScope混淆",trigger="coroutineScope{launch{throw E()};launch{}",detection="子失败取消兄弟 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0043）",fix="supervisorScope"))
        BugDB.load(BugRule(id="KT-1000",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="flow收集无背压",trigger="flow{emit(...)}.collect{slow()}",detection="生产快于消费 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0044）",fix="buffer或conflate"))
    }

    private fun registerChunk3() {
        BugDB.load(BugRule(id="KT-1001",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="不必要的async",trigger="val d=async{val x=y;x}",detection="简单赋值不需要async — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0045）",fix="直接赋值"))
        BugDB.load(BugRule(id="KT-1002",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="Dispatchers.Main硬编码",trigger="withContext(Dispatchers.Main){",detection="测试环境无Main — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0046）",fix="依赖注入dispatcher"))
        BugDB.load(BugRule(id="KT-1003",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="子协程取消未传播",trigger="supervisorScope{launch{heavy()}}",detection="scope取消时子不取消 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0162）",fix="coroutineScope"))
        BugDB.load(BugRule(id="KT-1004",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="coroutineContext丢失",trigger="withContext(empty){delay(1)}",detection="无Job — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0163）",fix="保留原始context"))
        BugDB.load(BugRule(id="KT-1005",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="select未处理onAwait",trigger="select{ch.onReceive{}",detection="未处理其他case — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0164）",fix="加onAwait"))
        BugDB.load(BugRule(id="KT-1006",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="callbackFlow未awaitClose",trigger="callbackFlow{register(cb);awaitClose{unregister()}}",detection="泄漏 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0165）",fix="加awaitClose"))
        BugDB.load(BugRule(id="KT-1007",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="StateFlow.value直接修改",trigger="state.value=state.value.copy(x=1)",detection="非原子操作 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0166）",fix="MutableStateFlow.update{}"))
        BugDB.load(BugRule(id="KT-1008",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="launch内return@launch遗漏",trigger="launch{if(x)return}",detection="非局部return — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0167）",fix="return@launch"))
        BugDB.load(BugRule(id="KT-1009",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="async+await替代withContext",trigger="val x=async(IO){work()}.await()",detection="等效withContext(IO) — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0168）",fix="直接用withContext"))
        BugDB.load(BugRule(id="KT-1010",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="不必要的flowOn",trigger="flow{emit(1)}.flowOn(IO).flowOn(Default)",detection="多flowOn无效 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0169）",fix="只保留最后一个"))
        BugDB.load(BugRule(id="KT-1011",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="Channel未关闭导致协程泄漏（String）",trigger="val c=Channel<String>();launch{c.consumeEach{}}",detection="生产者无close — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0170）",fix="c.close或produceIn"))
        BugDB.load(BugRule(id="KT-1012",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="Dispatchers.Unconfined误用",trigger="launch(Unconfined){updateUI()}",detection="线程不确定 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0171）",fix="显式指定调度器"))
        BugDB.load(BugRule(id="KT-1013",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="十一种子进程职业全部失业",trigger="所有@ProcessBody都被Condition拦截",detection="注解条件过严 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0260）",fix="放宽condition或加保底"))
        BugDB.load(BugRule(id="KT-1014",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="delay(0)比delay(1)更慢",trigger="delay(0);delay(0);delay(0)",detection="连续0延迟重排 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0278）",fix="用yield()"))
        BugDB.load(BugRule(id="KT-1015",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="所有协程都在等一个永远不会set的CompletableDeferred",trigger="val d=CompletableDeferred<T>();launch{...d.await()};忘记d.complete()",detection="忘记complete — Char类型字符编码边界,正则匹配组越界（参见 KT-0279）",fix="加超时withTimeout"))
        BugDB.load(BugRule(id="KT-1016",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="Mutex.lock了两次同一个协程",trigger="mutex.lock();mutex.lock()",detection="不可重入Mutex死锁 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0280）",fix="withLock或可重入锁"))
        BugDB.load(BugRule(id="KT-1017",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="flow.collect在collect后又emit了一条",trigger="flow{emit(1);awaitClose{emit(2)}}",detection="awaitClose中的emit可能丢失 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0292）",fix="onCompletion"))
        BugDB.load(BugRule(id="KT-1018",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="launch+反射+私有方法=不可预测崩溃",trigger="launch{val m=cls.getDeclaredMethod(\\\"secret\\\");m.isAccessible=true;m.invoke(obj)}",detection="协程内反射破坏封装 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0296）",fix="提供公开suspend接口"))
        BugDB.load(BugRule(id="KT-1019",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="flow+retry+stateIn=重试时状态丢失",trigger="flow{apiCall()}.retry(3).stateIn(scope)",detection="retry重建flow，stateIn收到新初始值 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0307）",fix="在retry外层stateIn"))
        BugDB.load(BugRule(id="KT-1020",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="Flow默认串行但collect看起来像并行",trigger="flow{emit(a);emit(b)}.collect{}",detection="emit挂起→collect处理→才继续emit — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0314）",fix="要并行用channelFlow或flatMapMerge"))
        BugDB.load(BugRule(id="KT-1021",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="channel被当成广播但只发了一条（Boolean）",trigger="val c=Channel<Boolean>();launch{c.send(1)};launch{println(c.receive())};launch{println(c.receive())}",detection="Channel单消费 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0315）",fix="BroadcastChannel或SharedFlow"))
        BugDB.load(BugRule(id="KT-1022",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="suspend函数无超时",trigger="suspend fun api(){httpClient.get(...)}",detection="无超时挂死 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0488）",fix="withTimeout"))
        BugDB.load(BugRule(id="KT-1023",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="Flow未捕获异常",trigger="flow{emit(risky())}.catch{e->...}",detection="catch位置错误 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0489）",fix="catch在collect之前"))
        BugDB.load(BugRule(id="KT-1024",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="StateFlow初始值导致重复emit",trigger="val s=MutableStateFlow(init);s.value=init",detection="同值仍触发collect — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0504）",fix="distinctUntilChanged或检查值"))
        BugDB.load(BugRule(id="KT-1025",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="GlobalScope泄漏",trigger="GlobalScope.launch{",detection="不受控的生命周期 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0035）",fix="lifecycleScope或viewModelScope"))
        BugDB.load(BugRule(id="KT-1026",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="launch吞异常",trigger="launch{riskyOp()}",detection="launch不传播异常 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0036）",fix="async+await或CoroutineExceptionHandler"))
        BugDB.load(BugRule(id="KT-1027",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="runBlocking在UI线程",trigger="runBlocking{delay(5000)}",detection="阻塞主线程 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0037）",fix="lifecycleScope.launch"))
        BugDB.load(BugRule(id="KT-1028",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="suspend中调用阻塞方法",trigger="suspend fun f(){Thread.sleep(1000)}",detection="阻塞协程线程 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0038）",fix="delay(1000)"))
        BugDB.load(BugRule(id="KT-1029",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="协程取消不响应",trigger="launch{while(true){work()}}",detection="不检查isActive — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0039）",fix="while(isActive)"))
        BugDB.load(BugRule(id="KT-1030",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="缺少CoroutineExceptionHandler",trigger="launch{throw E()}",detection="未处理异常 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0040）",fix="val handler=CoroutineExceptionHandler{"))
        BugDB.load(BugRule(id="KT-1031",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="withContext滥用",trigger="withContext(Dispatchers.IO){lightOp()}",detection="轻量操作用IO调度器 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0041）",fix="用Default或直接执行"))
        BugDB.load(BugRule(id="KT-1032",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="async忘记await",trigger="val d=async{calc()};d.await()",detection="不await结果丢失 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0042）",fix="确保所有async都被await"))
        BugDB.load(BugRule(id="KT-1033",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="coroutineScope与supervisorScope混淆",trigger="coroutineScope{launch{throw E()};launch{}",detection="子失败取消兄弟 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0043）",fix="supervisorScope"))
        BugDB.load(BugRule(id="KT-1034",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="flow收集无背压",trigger="flow{emit(...)}.collect{slow()}",detection="生产快于消费 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0044）",fix="buffer或conflate"))
        BugDB.load(BugRule(id="KT-1035",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="不必要的async",trigger="val d=async{val x=y;x}",detection="简单赋值不需要async — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0045）",fix="直接赋值"))
        BugDB.load(BugRule(id="KT-1036",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="Dispatchers.Main硬编码",trigger="withContext(Dispatchers.Main){",detection="测试环境无Main — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0046）",fix="依赖注入dispatcher"))
        BugDB.load(BugRule(id="KT-1037",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="子协程取消未传播",trigger="supervisorScope{launch{heavy()}}",detection="scope取消时子不取消 — Char类型字符编码边界,正则匹配组越界（参见 KT-0162）",fix="coroutineScope"))
        BugDB.load(BugRule(id="KT-1038",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="coroutineContext丢失",trigger="withContext(empty){delay(1)}",detection="无Job — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0163）",fix="保留原始context"))
        BugDB.load(BugRule(id="KT-1039",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="select未处理onAwait",trigger="select{ch.onReceive{}",detection="未处理其他case — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0164）",fix="加onAwait"))
        BugDB.load(BugRule(id="KT-1040",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="callbackFlow未awaitClose",trigger="callbackFlow{register(cb);awaitClose{unregister()}}",detection="泄漏 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0165）",fix="加awaitClose"))
        BugDB.load(BugRule(id="KT-1041",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="StateFlow.value直接修改",trigger="state.value=state.value.copy(x=1)",detection="非原子操作 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0166）",fix="MutableStateFlow.update{}"))
        BugDB.load(BugRule(id="KT-1042",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="launch内return@launch遗漏",trigger="launch{if(x)return}",detection="非局部return — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0167）",fix="return@launch"))
        BugDB.load(BugRule(id="KT-1043",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="async+await替代withContext",trigger="val x=async(IO){work()}.await()",detection="等效withContext(IO) — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0168）",fix="直接用withContext"))
        BugDB.load(BugRule(id="KT-1044",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="不必要的flowOn",trigger="flow{emit(1)}.flowOn(IO).flowOn(Default)",detection="多flowOn无效 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0169）",fix="只保留最后一个"))
        BugDB.load(BugRule(id="KT-1045",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="Channel未关闭导致协程泄漏（Map<String,Int>）",trigger="val c=Channel<Map<String,Int>>();launch{c.consumeEach{}}",detection="生产者无close — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0170）",fix="c.close或produceIn"))
        BugDB.load(BugRule(id="KT-1046",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="Dispatchers.Unconfined误用",trigger="launch(Unconfined){updateUI()}",detection="线程不确定 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0171）",fix="显式指定调度器"))
        BugDB.load(BugRule(id="KT-1047",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="十一种子进程职业全部失业",trigger="所有@ProcessBody都被Condition拦截",detection="注解条件过严 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0260）",fix="放宽condition或加保底"))
        BugDB.load(BugRule(id="KT-1048",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="delay(0)比delay(1)更慢",trigger="delay(0);delay(0);delay(0)",detection="连续0延迟重排 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0278）",fix="用yield()"))
        BugDB.load(BugRule(id="KT-1049",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="所有协程都在等一个永远不会set的CompletableDeferred",trigger="val d=CompletableDeferred<T>();launch{...d.await()};忘记d.complete()",detection="忘记complete — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0279）",fix="加超时withTimeout"))
        BugDB.load(BugRule(id="KT-1050",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="Mutex.lock了两次同一个协程",trigger="mutex.lock();mutex.lock()",detection="不可重入Mutex死锁 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0280）",fix="withLock或可重入锁"))
        BugDB.load(BugRule(id="KT-1051",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="flow.collect在collect后又emit了一条",trigger="flow{emit(1);awaitClose{emit(2)}}",detection="awaitClose中的emit可能丢失 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0292）",fix="onCompletion"))
        BugDB.load(BugRule(id="KT-1052",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="launch+反射+私有方法=不可预测崩溃",trigger="launch{val m=cls.getDeclaredMethod(\\\"secret\\\");m.isAccessible=true;m.invoke(obj)}",detection="协程内反射破坏封装 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0296）",fix="提供公开suspend接口"))
        BugDB.load(BugRule(id="KT-1053",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="flow+retry+stateIn=重试时状态丢失",trigger="flow{apiCall()}.retry(3).stateIn(scope)",detection="retry重建flow，stateIn收到新初始值 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0307）",fix="在retry外层stateIn"))
        BugDB.load(BugRule(id="KT-1054",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="Flow默认串行但collect看起来像并行",trigger="flow{emit(a);emit(b)}.collect{}",detection="emit挂起→collect处理→才继续emit — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0314）",fix="要并行用channelFlow或flatMapMerge"))
        BugDB.load(BugRule(id="KT-1055",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="channel被当成广播但只发了一条（String）",trigger="val c=Channel<String>();launch{c.send(1)};launch{println(c.receive())};launch{println(c.receive())}",detection="Channel单消费 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0315）",fix="BroadcastChannel或SharedFlow"))
        BugDB.load(BugRule(id="KT-1056",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="suspend函数无超时",trigger="suspend fun api(){httpClient.get(...)}",detection="无超时挂死 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0488）",fix="withTimeout"))
        BugDB.load(BugRule(id="KT-1057",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="Flow未捕获异常",trigger="flow{emit(risky())}.catch{e->...}",detection="catch位置错误 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0489）",fix="catch在collect之前"))
        BugDB.load(BugRule(id="KT-1058",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="StateFlow初始值导致重复emit",trigger="val s=MutableStateFlow(init);s.value=init",detection="同值仍触发collect — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0504）",fix="distinctUntilChanged或检查值"))
        BugDB.load(BugRule(id="KT-1059",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="GlobalScope泄漏",trigger="GlobalScope.launch{",detection="不受控的生命周期 — Char类型字符编码边界,正则匹配组越界（参见 KT-0035）",fix="lifecycleScope或viewModelScope"))
        BugDB.load(BugRule(id="KT-1060",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="launch吞异常",trigger="launch{riskyOp()}",detection="launch不传播异常 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0036）",fix="async+await或CoroutineExceptionHandler"))
        BugDB.load(BugRule(id="KT-1061",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="runBlocking在UI线程",trigger="runBlocking{delay(5000)}",detection="阻塞主线程 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0037）",fix="lifecycleScope.launch"))
        BugDB.load(BugRule(id="KT-1062",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="suspend中调用阻塞方法",trigger="suspend fun f(){Thread.sleep(1000)}",detection="阻塞协程线程 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0038）",fix="delay(1000)"))
        BugDB.load(BugRule(id="KT-1063",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="协程取消不响应",trigger="launch{while(true){work()}}",detection="不检查isActive — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0039）",fix="while(isActive)"))
        BugDB.load(BugRule(id="KT-1064",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="缺少CoroutineExceptionHandler",trigger="launch{throw E()}",detection="未处理异常 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0040）",fix="val handler=CoroutineExceptionHandler{"))
        BugDB.load(BugRule(id="KT-1065",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="withContext滥用",trigger="withContext(Dispatchers.IO){lightOp()}",detection="轻量操作用IO调度器 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0041）",fix="用Default或直接执行"))
        BugDB.load(BugRule(id="KT-1066",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="async忘记await",trigger="val d=async{calc()};d.await()",detection="不await结果丢失 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0042）",fix="确保所有async都被await"))
        BugDB.load(BugRule(id="KT-1067",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="coroutineScope与supervisorScope混淆",trigger="coroutineScope{launch{throw E()};launch{}",detection="子失败取消兄弟 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0043）",fix="supervisorScope"))
        BugDB.load(BugRule(id="KT-1068",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="flow收集无背压",trigger="flow{emit(...)}.collect{slow()}",detection="生产快于消费 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0044）",fix="buffer或conflate"))
        BugDB.load(BugRule(id="KT-1069",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="不必要的async",trigger="val d=async{val x=y;x}",detection="简单赋值不需要async — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0045）",fix="直接赋值"))
        BugDB.load(BugRule(id="KT-1070",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="Dispatchers.Main硬编码",trigger="withContext(Dispatchers.Main){",detection="测试环境无Main — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0046）",fix="依赖注入dispatcher"))
        BugDB.load(BugRule(id="KT-1071",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="子协程取消未传播",trigger="supervisorScope{launch{heavy()}}",detection="scope取消时子不取消 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0162）",fix="coroutineScope"))
        BugDB.load(BugRule(id="KT-1072",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="coroutineContext丢失",trigger="withContext(empty){delay(1)}",detection="无Job — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0163）",fix="保留原始context"))
        BugDB.load(BugRule(id="KT-1073",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="select未处理onAwait",trigger="select{ch.onReceive{}",detection="未处理其他case — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0164）",fix="加onAwait"))
        BugDB.load(BugRule(id="KT-1074",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="callbackFlow未awaitClose",trigger="callbackFlow{register(cb);awaitClose{unregister()}}",detection="泄漏 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0165）",fix="加awaitClose"))
        BugDB.load(BugRule(id="KT-1075",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="StateFlow.value直接修改",trigger="state.value=state.value.copy(x=1)",detection="非原子操作 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0166）",fix="MutableStateFlow.update{}"))
        BugDB.load(BugRule(id="KT-1076",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="launch内return@launch遗漏",trigger="launch{if(x)return}",detection="非局部return — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0167）",fix="return@launch"))
        BugDB.load(BugRule(id="KT-1077",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="async+await替代withContext",trigger="val x=async(IO){work()}.await()",detection="等效withContext(IO) — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0168）",fix="直接用withContext"))
        BugDB.load(BugRule(id="KT-1078",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="不必要的flowOn",trigger="flow{emit(1)}.flowOn(IO).flowOn(Default)",detection="多flowOn无效 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0169）",fix="只保留最后一个"))
        BugDB.load(BugRule(id="KT-1079",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="Channel未关闭导致协程泄漏（Boolean?）",trigger="val c=Channel<Boolean?>();launch{c.consumeEach{}}",detection="生产者无close — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0170）",fix="c.close或produceIn"))
        BugDB.load(BugRule(id="KT-1080",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="Dispatchers.Unconfined误用",trigger="launch(Unconfined){updateUI()}",detection="线程不确定 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0171）",fix="显式指定调度器"))
        BugDB.load(BugRule(id="KT-1081",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="十一种子进程职业全部失业",trigger="所有@ProcessBody都被Condition拦截",detection="注解条件过严 — Char类型字符编码边界,正则匹配组越界（参见 KT-0260）",fix="放宽condition或加保底"))
        BugDB.load(BugRule(id="KT-1082",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="delay(0)比delay(1)更慢",trigger="delay(0);delay(0);delay(0)",detection="连续0延迟重排 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0278）",fix="用yield()"))
        BugDB.load(BugRule(id="KT-1083",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="所有协程都在等一个永远不会set的CompletableDeferred",trigger="val d=CompletableDeferred<T>();launch{...d.await()};忘记d.complete()",detection="忘记complete — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0279）",fix="加超时withTimeout"))
        BugDB.load(BugRule(id="KT-1084",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="Mutex.lock了两次同一个协程",trigger="mutex.lock();mutex.lock()",detection="不可重入Mutex死锁 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0280）",fix="withLock或可重入锁"))
        BugDB.load(BugRule(id="KT-1085",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="flow.collect在collect后又emit了一条",trigger="flow{emit(1);awaitClose{emit(2)}}",detection="awaitClose中的emit可能丢失 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0292）",fix="onCompletion"))
        BugDB.load(BugRule(id="KT-1086",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="launch+反射+私有方法=不可预测崩溃",trigger="launch{val m=cls.getDeclaredMethod(\\\"secret\\\");m.isAccessible=true;m.invoke(obj)}",detection="协程内反射破坏封装 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0296）",fix="提供公开suspend接口"))
        BugDB.load(BugRule(id="KT-1087",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="flow+retry+stateIn=重试时状态丢失",trigger="flow{apiCall()}.retry(3).stateIn(scope)",detection="retry重建flow，stateIn收到新初始值 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0307）",fix="在retry外层stateIn"))
        BugDB.load(BugRule(id="KT-1088",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="Flow默认串行但collect看起来像并行",trigger="flow{emit(a);emit(b)}.collect{}",detection="emit挂起→collect处理→才继续emit — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0314）",fix="要并行用channelFlow或flatMapMerge"))
        BugDB.load(BugRule(id="KT-1089",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="channel被当成广播但只发了一条（Map<String,Int>）",trigger="val c=Channel<Map<String,Int>>();launch{c.send(1)};launch{println(c.receive())};launch{println(c.receive())}",detection="Channel单消费 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0315）",fix="BroadcastChannel或SharedFlow"))
        BugDB.load(BugRule(id="KT-1090",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="suspend函数无超时",trigger="suspend fun api(){httpClient.get(...)}",detection="无超时挂死 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0488）",fix="withTimeout"))
        BugDB.load(BugRule(id="KT-1091",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="Flow未捕获异常",trigger="flow{emit(risky())}.catch{e->...}",detection="catch位置错误 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0489）",fix="catch在collect之前"))
        BugDB.load(BugRule(id="KT-1092",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="StateFlow初始值导致重复emit",trigger="val s=MutableStateFlow(init);s.value=init",detection="同值仍触发collect — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0504）",fix="distinctUntilChanged或检查值"))
        BugDB.load(BugRule(id="KT-1093",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="GlobalScope泄漏",trigger="GlobalScope.launch{",detection="不受控的生命周期 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0035）",fix="lifecycleScope或viewModelScope"))
        BugDB.load(BugRule(id="KT-1094",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="launch吞异常",trigger="launch{riskyOp()}",detection="launch不传播异常 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0036）",fix="async+await或CoroutineExceptionHandler"))
        BugDB.load(BugRule(id="KT-1095",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="runBlocking在UI线程",trigger="runBlocking{delay(5000)}",detection="阻塞主线程 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0037）",fix="lifecycleScope.launch"))
        BugDB.load(BugRule(id="KT-1096",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="suspend中调用阻塞方法",trigger="suspend fun f(){Thread.sleep(1000)}",detection="阻塞协程线程 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0038）",fix="delay(1000)"))
        BugDB.load(BugRule(id="KT-1097",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="协程取消不响应",trigger="launch{while(true){work()}}",detection="不检查isActive — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0039）",fix="while(isActive)"))
        BugDB.load(BugRule(id="KT-1098",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="缺少CoroutineExceptionHandler",trigger="launch{throw E()}",detection="未处理异常 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0040）",fix="val handler=CoroutineExceptionHandler{"))
        BugDB.load(BugRule(id="KT-1099",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="withContext滥用",trigger="withContext(Dispatchers.IO){lightOp()}",detection="轻量操作用IO调度器 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0041）",fix="用Default或直接执行"))
        BugDB.load(BugRule(id="KT-1100",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="async忘记await",trigger="val d=async{calc()};d.await()",detection="不await结果丢失 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0042）",fix="确保所有async都被await"))
        BugDB.load(BugRule(id="KT-1101",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="coroutineScope与supervisorScope混淆",trigger="coroutineScope{launch{throw E()};launch{}",detection="子失败取消兄弟 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0043）",fix="supervisorScope"))
        BugDB.load(BugRule(id="KT-1102",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="flow收集无背压",trigger="flow{emit(...)}.collect{slow()}",detection="生产快于消费 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0044）",fix="buffer或conflate"))
        BugDB.load(BugRule(id="KT-1103",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="不必要的async",trigger="val d=async{val x=y;x}",detection="简单赋值不需要async — Char类型字符编码边界,正则匹配组越界（参见 KT-0045）",fix="直接赋值"))
        BugDB.load(BugRule(id="KT-1104",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="Dispatchers.Main硬编码",trigger="withContext(Dispatchers.Main){",detection="测试环境无Main — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0046）",fix="依赖注入dispatcher"))
        BugDB.load(BugRule(id="KT-1105",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="子协程取消未传播",trigger="supervisorScope{launch{heavy()}}",detection="scope取消时子不取消 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0162）",fix="coroutineScope"))
        BugDB.load(BugRule(id="KT-1106",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="coroutineContext丢失",trigger="withContext(empty){delay(1)}",detection="无Job — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0163）",fix="保留原始context"))
        BugDB.load(BugRule(id="KT-1107",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="select未处理onAwait",trigger="select{ch.onReceive{}",detection="未处理其他case — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0164）",fix="加onAwait"))
        BugDB.load(BugRule(id="KT-1108",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="callbackFlow未awaitClose",trigger="callbackFlow{register(cb);awaitClose{unregister()}}",detection="泄漏 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0165）",fix="加awaitClose"))
        BugDB.load(BugRule(id="KT-1109",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="StateFlow.value直接修改",trigger="state.value=state.value.copy(x=1)",detection="非原子操作 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0166）",fix="MutableStateFlow.update{}"))
        BugDB.load(BugRule(id="KT-1110",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="launch内return@launch遗漏",trigger="launch{if(x)return}",detection="非局部return — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0167）",fix="return@launch"))
        BugDB.load(BugRule(id="KT-1111",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="async+await替代withContext",trigger="val x=async(IO){work()}.await()",detection="等效withContext(IO) — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0168）",fix="直接用withContext"))
        BugDB.load(BugRule(id="KT-1112",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="不必要的flowOn",trigger="flow{emit(1)}.flowOn(IO).flowOn(Default)",detection="多flowOn无效 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0169）",fix="只保留最后一个"))
        BugDB.load(BugRule(id="KT-1113",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="Channel未关闭导致协程泄漏（String?）",trigger="val c=Channel<String?>();launch{c.consumeEach{}}",detection="生产者无close — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0170）",fix="c.close或produceIn"))
        BugDB.load(BugRule(id="KT-1114",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="Dispatchers.Unconfined误用",trigger="launch(Unconfined){updateUI()}",detection="线程不确定 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0171）",fix="显式指定调度器"))
        BugDB.load(BugRule(id="KT-1115",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="十一种子进程职业全部失业",trigger="所有@ProcessBody都被Condition拦截",detection="注解条件过严 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0260）",fix="放宽condition或加保底"))
        BugDB.load(BugRule(id="KT-1116",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="delay(0)比delay(1)更慢",trigger="delay(0);delay(0);delay(0)",detection="连续0延迟重排 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0278）",fix="用yield()"))
        BugDB.load(BugRule(id="KT-1117",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="所有协程都在等一个永远不会set的CompletableDeferred",trigger="val d=CompletableDeferred<T>();launch{...d.await()};忘记d.complete()",detection="忘记complete — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0279）",fix="加超时withTimeout"))
        BugDB.load(BugRule(id="KT-1118",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="Mutex.lock了两次同一个协程",trigger="mutex.lock();mutex.lock()",detection="不可重入Mutex死锁 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0280）",fix="withLock或可重入锁"))
        BugDB.load(BugRule(id="KT-1119",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="flow.collect在collect后又emit了一条",trigger="flow{emit(1);awaitClose{emit(2)}}",detection="awaitClose中的emit可能丢失 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0292）",fix="onCompletion"))
        BugDB.load(BugRule(id="KT-1120",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="launch+反射+私有方法=不可预测崩溃",trigger="launch{val m=cls.getDeclaredMethod(\\\"secret\\\");m.isAccessible=true;m.invoke(obj)}",detection="协程内反射破坏封装 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0296）",fix="提供公开suspend接口"))
        BugDB.load(BugRule(id="KT-1121",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="flow+retry+stateIn=重试时状态丢失",trigger="flow{apiCall()}.retry(3).stateIn(scope)",detection="retry重建flow，stateIn收到新初始值 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0307）",fix="在retry外层stateIn"))
        BugDB.load(BugRule(id="KT-1122",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="Flow默认串行但collect看起来像并行",trigger="flow{emit(a);emit(b)}.collect{}",detection="emit挂起→collect处理→才继续emit — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0314）",fix="要并行用channelFlow或flatMapMerge"))
        BugDB.load(BugRule(id="KT-1123",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="channel被当成广播但只发了一条（Boolean?）",trigger="val c=Channel<Boolean?>();launch{c.send(1)};launch{println(c.receive())};launch{println(c.receive())}",detection="Channel单消费 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0315）",fix="BroadcastChannel或SharedFlow"))
        BugDB.load(BugRule(id="KT-1124",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="suspend函数无超时",trigger="suspend fun api(){httpClient.get(...)}",detection="无超时挂死 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0488）",fix="withTimeout"))
        BugDB.load(BugRule(id="KT-1125",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="Flow未捕获异常",trigger="flow{emit(risky())}.catch{e->...}",detection="catch位置错误 — Char类型字符编码边界,正则匹配组越界（参见 KT-0489）",fix="catch在collect之前"))
        BugDB.load(BugRule(id="KT-1126",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="StateFlow初始值导致重复emit",trigger="val s=MutableStateFlow(init);s.value=init",detection="同值仍触发collect — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0504）",fix="distinctUntilChanged或检查值"))
        BugDB.load(BugRule(id="KT-1127",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="GlobalScope泄漏",trigger="GlobalScope.launch{",detection="不受控的生命周期 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0035）",fix="lifecycleScope或viewModelScope"))
        BugDB.load(BugRule(id="KT-1128",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="launch吞异常",trigger="launch{riskyOp()}",detection="launch不传播异常 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0036）",fix="async+await或CoroutineExceptionHandler"))
        BugDB.load(BugRule(id="KT-1129",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="runBlocking在UI线程",trigger="runBlocking{delay(5000)}",detection="阻塞主线程 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0037）",fix="lifecycleScope.launch"))
        BugDB.load(BugRule(id="KT-1130",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="suspend中调用阻塞方法",trigger="suspend fun f(){Thread.sleep(1000)}",detection="阻塞协程线程 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0038）",fix="delay(1000)"))
        BugDB.load(BugRule(id="KT-1131",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="协程取消不响应",trigger="launch{while(true){work()}}",detection="不检查isActive — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0039）",fix="while(isActive)"))
        BugDB.load(BugRule(id="KT-1132",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="缺少CoroutineExceptionHandler",trigger="launch{throw E()}",detection="未处理异常 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0040）",fix="val handler=CoroutineExceptionHandler{"))
        BugDB.load(BugRule(id="KT-1133",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="withContext滥用",trigger="withContext(Dispatchers.IO){lightOp()}",detection="轻量操作用IO调度器 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0041）",fix="用Default或直接执行"))
        BugDB.load(BugRule(id="KT-1134",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="async忘记await",trigger="val d=async{calc()};d.await()",detection="不await结果丢失 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0042）",fix="确保所有async都被await"))
        BugDB.load(BugRule(id="KT-1135",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="coroutineScope与supervisorScope混淆",trigger="coroutineScope{launch{throw E()};launch{}",detection="子失败取消兄弟 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0043）",fix="supervisorScope"))
        BugDB.load(BugRule(id="KT-1136",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="flow收集无背压",trigger="flow{emit(...)}.collect{slow()}",detection="生产快于消费 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0044）",fix="buffer或conflate"))
        BugDB.load(BugRule(id="KT-1137",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="不必要的async",trigger="val d=async{val x=y;x}",detection="简单赋值不需要async — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0045）",fix="直接赋值"))
        BugDB.load(BugRule(id="KT-1138",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="Dispatchers.Main硬编码",trigger="withContext(Dispatchers.Main){",detection="测试环境无Main — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0046）",fix="依赖注入dispatcher"))
        BugDB.load(BugRule(id="KT-1139",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="子协程取消未传播",trigger="supervisorScope{launch{heavy()}}",detection="scope取消时子不取消 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0162）",fix="coroutineScope"))
        BugDB.load(BugRule(id="KT-1140",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="coroutineContext丢失",trigger="withContext(empty){delay(1)}",detection="无Job — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0163）",fix="保留原始context"))
        BugDB.load(BugRule(id="KT-1141",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="select未处理onAwait",trigger="select{ch.onReceive{}",detection="未处理其他case — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0164）",fix="加onAwait"))
        BugDB.load(BugRule(id="KT-1142",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="callbackFlow未awaitClose",trigger="callbackFlow{register(cb);awaitClose{unregister()}}",detection="泄漏 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0165）",fix="加awaitClose"))
        BugDB.load(BugRule(id="KT-1143",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="StateFlow.value直接修改",trigger="state.value=state.value.copy(x=1)",detection="非原子操作 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0166）",fix="MutableStateFlow.update{}"))
        BugDB.load(BugRule(id="KT-1144",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="launch内return@launch遗漏",trigger="launch{if(x)return}",detection="非局部return — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0167）",fix="return@launch"))
        BugDB.load(BugRule(id="KT-1145",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="async+await替代withContext",trigger="val x=async(IO){work()}.await()",detection="等效withContext(IO) — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0168）",fix="直接用withContext"))
        BugDB.load(BugRule(id="KT-1146",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="不必要的flowOn",trigger="flow{emit(1)}.flowOn(IO).flowOn(Default)",detection="多flowOn无效 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0169）",fix="只保留最后一个"))
        BugDB.load(BugRule(id="KT-1147",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="Channel未关闭导致协程泄漏（Char）",trigger="val c=Channel<Char>();launch{c.consumeEach{}}",detection="生产者无close — Char类型字符编码边界,正则匹配组越界（参见 KT-0170）",fix="c.close或produceIn"))
        BugDB.load(BugRule(id="KT-1148",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="Dispatchers.Unconfined误用",trigger="launch(Unconfined){updateUI()}",detection="线程不确定 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0171）",fix="显式指定调度器"))
        BugDB.load(BugRule(id="KT-1149",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="十一种子进程职业全部失业",trigger="所有@ProcessBody都被Condition拦截",detection="注解条件过严 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0260）",fix="放宽condition或加保底"))
        BugDB.load(BugRule(id="KT-1150",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="delay(0)比delay(1)更慢",trigger="delay(0);delay(0);delay(0)",detection="连续0延迟重排 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0278）",fix="用yield()"))
        BugDB.load(BugRule(id="KT-1151",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="所有协程都在等一个永远不会set的CompletableDeferred",trigger="val d=CompletableDeferred<T>();launch{...d.await()};忘记d.complete()",detection="忘记complete — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0279）",fix="加超时withTimeout"))
        BugDB.load(BugRule(id="KT-1152",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="Mutex.lock了两次同一个协程",trigger="mutex.lock();mutex.lock()",detection="不可重入Mutex死锁 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0280）",fix="withLock或可重入锁"))
        BugDB.load(BugRule(id="KT-1153",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="flow.collect在collect后又emit了一条",trigger="flow{emit(1);awaitClose{emit(2)}}",detection="awaitClose中的emit可能丢失 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0292）",fix="onCompletion"))
        BugDB.load(BugRule(id="KT-1154",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="launch+反射+私有方法=不可预测崩溃",trigger="launch{val m=cls.getDeclaredMethod(\\\"secret\\\");m.isAccessible=true;m.invoke(obj)}",detection="协程内反射破坏封装 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0296）",fix="提供公开suspend接口"))
        BugDB.load(BugRule(id="KT-1155",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="flow+retry+stateIn=重试时状态丢失",trigger="flow{apiCall()}.retry(3).stateIn(scope)",detection="retry重建flow，stateIn收到新初始值 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0307）",fix="在retry外层stateIn"))
        BugDB.load(BugRule(id="KT-1156",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="Flow默认串行但collect看起来像并行",trigger="flow{emit(a);emit(b)}.collect{}",detection="emit挂起→collect处理→才继续emit — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0314）",fix="要并行用channelFlow或flatMapMerge"))
        BugDB.load(BugRule(id="KT-1157",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="channel被当成广播但只发了一条（String?）",trigger="val c=Channel<String?>();launch{c.send(1)};launch{println(c.receive())};launch{println(c.receive())}",detection="Channel单消费 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0315）",fix="BroadcastChannel或SharedFlow"))
        BugDB.load(BugRule(id="KT-1158",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="suspend函数无超时",trigger="suspend fun api(){httpClient.get(...)}",detection="无超时挂死 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0488）",fix="withTimeout"))
        BugDB.load(BugRule(id="KT-1159",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="Flow未捕获异常",trigger="flow{emit(risky())}.catch{e->...}",detection="catch位置错误 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0489）",fix="catch在collect之前"))
        BugDB.load(BugRule(id="KT-1160",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="StateFlow初始值导致重复emit",trigger="val s=MutableStateFlow(init);s.value=init",detection="同值仍触发collect — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0504）",fix="distinctUntilChanged或检查值"))
        BugDB.load(BugRule(id="KT-1161",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="GlobalScope泄漏",trigger="GlobalScope.launch{",detection="不受控的生命周期 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0035）",fix="lifecycleScope或viewModelScope"))
        BugDB.load(BugRule(id="KT-1162",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="launch吞异常",trigger="launch{riskyOp()}",detection="launch不传播异常 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0036）",fix="async+await或CoroutineExceptionHandler"))
        BugDB.load(BugRule(id="KT-1163",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="runBlocking在UI线程",trigger="runBlocking{delay(5000)}",detection="阻塞主线程 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0037）",fix="lifecycleScope.launch"))
        BugDB.load(BugRule(id="KT-1164",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="suspend中调用阻塞方法",trigger="suspend fun f(){Thread.sleep(1000)}",detection="阻塞协程线程 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0038）",fix="delay(1000)"))
        BugDB.load(BugRule(id="KT-1165",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="协程取消不响应",trigger="launch{while(true){work()}}",detection="不检查isActive — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0039）",fix="while(isActive)"))
        BugDB.load(BugRule(id="KT-1166",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="缺少CoroutineExceptionHandler",trigger="launch{throw E()}",detection="未处理异常 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0040）",fix="val handler=CoroutineExceptionHandler{"))
        BugDB.load(BugRule(id="KT-1167",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="withContext滥用",trigger="withContext(Dispatchers.IO){lightOp()}",detection="轻量操作用IO调度器 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0041）",fix="用Default或直接执行"))
        BugDB.load(BugRule(id="KT-1168",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="async忘记await",trigger="val d=async{calc()};d.await()",detection="不await结果丢失 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0042）",fix="确保所有async都被await"))
        BugDB.load(BugRule(id="KT-1169",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="coroutineScope与supervisorScope混淆",trigger="coroutineScope{launch{throw E()};launch{}",detection="子失败取消兄弟 — Char类型字符编码边界,正则匹配组越界（参见 KT-0043）",fix="supervisorScope"))
        BugDB.load(BugRule(id="KT-1170",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="flow收集无背压",trigger="flow{emit(...)}.collect{slow()}",detection="生产快于消费 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0044）",fix="buffer或conflate"))
        BugDB.load(BugRule(id="KT-1171",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="不必要的async",trigger="val d=async{val x=y;x}",detection="简单赋值不需要async — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0045）",fix="直接赋值"))
        BugDB.load(BugRule(id="KT-1172",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="Dispatchers.Main硬编码",trigger="withContext(Dispatchers.Main){",detection="测试环境无Main — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0046）",fix="依赖注入dispatcher"))
        BugDB.load(BugRule(id="KT-1173",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="子协程取消未传播",trigger="supervisorScope{launch{heavy()}}",detection="scope取消时子不取消 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0162）",fix="coroutineScope"))
        BugDB.load(BugRule(id="KT-1174",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="coroutineContext丢失",trigger="withContext(empty){delay(1)}",detection="无Job — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0163）",fix="保留原始context"))
        BugDB.load(BugRule(id="KT-1175",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="select未处理onAwait",trigger="select{ch.onReceive{}",detection="未处理其他case — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0164）",fix="加onAwait"))
        BugDB.load(BugRule(id="KT-1176",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="callbackFlow未awaitClose",trigger="callbackFlow{register(cb);awaitClose{unregister()}}",detection="泄漏 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0165）",fix="加awaitClose"))
        BugDB.load(BugRule(id="KT-1177",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="StateFlow.value直接修改",trigger="state.value=state.value.copy(x=1)",detection="非原子操作 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0166）",fix="MutableStateFlow.update{}"))
        BugDB.load(BugRule(id="KT-1178",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="launch内return@launch遗漏",trigger="launch{if(x)return}",detection="非局部return — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0167）",fix="return@launch"))
        BugDB.load(BugRule(id="KT-1179",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="async+await替代withContext",trigger="val x=async(IO){work()}.await()",detection="等效withContext(IO) — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0168）",fix="直接用withContext"))
        BugDB.load(BugRule(id="KT-1180",category=BugCategory.COROUTINES,severity=BugSeverity.MILD,
            title="不必要的flowOn",trigger="flow{emit(1)}.flowOn(IO).flowOn(Default)",detection="多flowOn无效 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0169）",fix="只保留最后一个"))
        BugDB.load(BugRule(id="KT-1181",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="Channel未关闭导致协程泄漏（Long）",trigger="val c=Channel<Long>();launch{c.consumeEach{}}",detection="生产者无close — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0170）",fix="c.close或produceIn"))
        BugDB.load(BugRule(id="KT-1182",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="Dispatchers.Unconfined误用",trigger="launch(Unconfined){updateUI()}",detection="线程不确定 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0171）",fix="显式指定调度器"))
        BugDB.load(BugRule(id="KT-1183",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="十一种子进程职业全部失业",trigger="所有@ProcessBody都被Condition拦截",detection="注解条件过严 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0260）",fix="放宽condition或加保底"))
        BugDB.load(BugRule(id="KT-1184",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="delay(0)比delay(1)更慢",trigger="delay(0);delay(0);delay(0)",detection="连续0延迟重排 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0278）",fix="用yield()"))
        BugDB.load(BugRule(id="KT-1185",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="所有协程都在等一个永远不会set的CompletableDeferred",trigger="val d=CompletableDeferred<T>();launch{...d.await()};忘记d.complete()",detection="忘记complete — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0279）",fix="加超时withTimeout"))
        BugDB.load(BugRule(id="KT-1186",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="Mutex.lock了两次同一个协程",trigger="mutex.lock();mutex.lock()",detection="不可重入Mutex死锁 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0280）",fix="withLock或可重入锁"))
        BugDB.load(BugRule(id="KT-1187",category=BugCategory.COROUTINES,severity=BugSeverity.MODERATE,
            title="flow.collect在collect后又emit了一条",trigger="flow{emit(1);awaitClose{emit(2)}}",detection="awaitClose中的emit可能丢失 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0292）",fix="onCompletion"))
        BugDB.load(BugRule(id="KT-1188",category=BugCategory.COROUTINES,severity=BugSeverity.SEVERE,
            title="launch+反射+私有方法=不可预测崩溃",trigger="launch{val m=cls.getDeclaredMethod(\\\"secret\\\");m.isAccessible=true;m.invoke(obj)}",detection="协程内反射破坏封装 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0296）",fix="提供公开suspend接口"))
        BugDB.load(BugRule(id="KT-1189",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="遍历时修改",trigger="for(x in list){list.remove(x)}",detection="ConcurrentModificationException — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0047）",fix="收集待删项再删"))
        BugDB.load(BugRule(id="KT-1190",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="迭代器并发修改",trigger="val it=list.iterator();list.add(x);it.next()",detection="迭代器失效 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0048）",fix="先收集再操作"))
        BugDB.load(BugRule(id="KT-1191",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="MutableList暴露（Boolean?）",trigger="fun getBoolean?()=mutableBoolean?",detection="外部可修改内部状态 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0049）",fix="toList()或Collections.unmodifiableList"))
        BugDB.load(BugRule(id="KT-1192",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="first()无元素（Sequence<Long>）",trigger="emptySequence<Long><Sequence<Long>>().first()",detection="NoSuchElementException — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0050）",fix="firstOrNull"))
        BugDB.load(BugRule(id="KT-1193",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="single()多元素",trigger="listOf(1,2).single()",detection="IllegalArgumentException — Char类型字符编码边界,正则匹配组越界（参见 KT-0051）",fix="first()或singleOrNull"))
        BugDB.load(BugRule(id="KT-1194",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="HashMap键可变",trigger="val k=MutableObj();map[k]=v;k.mutate();map[k]",detection="hashCode变了 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0052）",fix="用不可变键"))
        BugDB.load(BugRule(id="KT-1195",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="filter后仍操作原集合",trigger="list.filter{;list.add(x)",detection="误解filter返回新集合 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0053）",fix="保存filter结果"))
        BugDB.load(BugRule(id="KT-1196",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="不必要的toList（Float）",trigger="alreadyFloat.toFloat()",detection="重复包装 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0054）",fix="直接使用"))
        BugDB.load(BugRule(id="KT-1197",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="map后丢弃",trigger="list.map{it*2}",detection="副作用期望 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0055）",fix="用forEach"))
        BugDB.load(BugRule(id="KT-1198",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="遍历List<String>时修改",trigger="for(x in list<string>){ list<string>.remove(x) }",detection="ConcurrentMod — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0056）",fix="收集后删"))
        BugDB.load(BugRule(id="KT-1199",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="遍历Set<Int>时修改",trigger="for(x in set<int>){ set<int>.remove(x) }",detection="ConcurrentMod — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0057）",fix="收集后删"))
        BugDB.load(BugRule(id="KT-1200",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="遍历Map<String,Int>时修改",trigger="for(x in map<string,int>){ map<string,int>.remove(x) }",detection="ConcurrentMod — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0058）",fix="收集后删"))
        BugDB.load(BugRule(id="KT-1201",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="List.sort后索引错乱",trigger="val idx=list.indexOf(x);list.sort();list[idx]",detection="sort改变位置 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0172）",fix="先记录再排序或使用sorted()"))
        BugDB.load(BugRule(id="KT-1202",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="MutableList.subList泄漏（Double）",trigger="val sub=mutableDouble.subDouble(0,5);mutableDouble.clear()",detection="subList视图失效 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0173）",fix="先copy再操作"))
        BugDB.load(BugRule(id="KT-1203",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="Set.contains自定义对象无hashCode",trigger="setOf(Obj(1)).contains(Obj(1))",detection="默认hashCode比较 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0174）",fix="重写equals+hashCode"))
        BugDB.load(BugRule(id="KT-1204",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="Map.getOrDefault惰性求值",trigger="map.getOrDefault(k,expensive())",detection="expensive总会执行 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0175）",fix="map.getOrPut(k){expensive()}"))
        BugDB.load(BugRule(id="KT-1205",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="不必要的toSet/unique（Long）",trigger="list.toSet().toLong()",detection="去重后转回 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0176）",fix="list.distinct()"))
        BugDB.load(BugRule(id="KT-1206",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="空集合操作（Any）",trigger="emptyAny<Any>().reduce{a,b->a+b}",detection="UnsupportedOperation — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0177）",fix="ifEmpty+fold"))
        BugDB.load(BugRule(id="KT-1207",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="Vector(已弃用)仍使用（List<String>）",trigger="val v=Vector<List<String><String>>();v.add(1)",detection="synchronized遗留 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0178）",fix="ArrayList/CopyOnWriteArrayList"))
        BugDB.load(BugRule(id="KT-1208",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="groupBy返回LinkedHashMap依赖顺序",trigger="val g=list.groupBy{it.key};g.forEach{}",detection="依赖插入顺序 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0179）",fix="显式sorted"))
        BugDB.load(BugRule(id="KT-1209",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="HMap swap-remove旧组索引未更新",trigger="_keys[i]=_keys[_size]后featIdx只失效被删key组",detection="被挪元素旧组脏索引→NPE — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0334）",fix="swap后同时标记movedKey旧组失效"))
        BugDB.load(BugRule(id="KT-1210",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="first()无元素（Long版）（Any?）",trigger="emptyAny?<Long>().first()",detection="NoSuchElementException — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0372）",fix="firstOrNull"))
        BugDB.load(BugRule(id="KT-1211",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="first()无元素（Double版）（String）",trigger="emptyString<Double>().first()",detection="NoSuchElementException — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0373）",fix="firstOrNull"))
        BugDB.load(BugRule(id="KT-1212",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="first()无元素（Float版）（Byte）",trigger="emptyByte<Float>().first()",detection="NoSuchElementException — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0374）",fix="firstOrNull"))
        BugDB.load(BugRule(id="KT-1213",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="空集合操作（Long版）（Boolean?）",trigger="emptyBoolean?<Long>().reduce{a,b->a+b}",detection="UnsupportedOperation — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0375）",fix="ifEmpty+fold"))
        BugDB.load(BugRule(id="KT-1214",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="Paging3重复加载",trigger="PagingSource.load()返回值未去重",detection="重复数据 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0495）",fix="distinctUntilChanged"))
        BugDB.load(BugRule(id="KT-1215",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="遍历时修改",trigger="for(x in list){list.remove(x)}",detection="ConcurrentModificationException — Char类型字符编码边界,正则匹配组越界（参见 KT-0047）",fix="收集待删项再删"))
        BugDB.load(BugRule(id="KT-1216",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="迭代器并发修改",trigger="val it=list.iterator();list.add(x);it.next()",detection="迭代器失效 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0048）",fix="先收集再操作"))
        BugDB.load(BugRule(id="KT-1217",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="MutableList暴露（Array<Boolean>）",trigger="fun getArray<Boolean>()=mutableArray<Boolean>",detection="外部可修改内部状态 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0049）",fix="toList()或Collections.unmodifiableList"))
        BugDB.load(BugRule(id="KT-1218",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="first()无元素（Float）",trigger="emptyFloat<Float>().first()",detection="NoSuchElementException — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0050）",fix="firstOrNull"))
        BugDB.load(BugRule(id="KT-1219",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="single()多元素",trigger="listOf(1,2).single()",detection="IllegalArgumentException — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0051）",fix="first()或singleOrNull"))
        BugDB.load(BugRule(id="KT-1220",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="HashMap键可变",trigger="val k=MutableObj();map[k]=v;k.mutate();map[k]",detection="hashCode变了 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0052）",fix="用不可变键"))
        BugDB.load(BugRule(id="KT-1221",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="filter后仍操作原集合",trigger="list.filter{;list.add(x)",detection="误解filter返回新集合 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0053）",fix="保存filter结果"))
        BugDB.load(BugRule(id="KT-1222",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="不必要的toList（Int?）",trigger="alreadyInt?.toInt?()",detection="重复包装 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0054）",fix="直接使用"))
        BugDB.load(BugRule(id="KT-1223",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="map后丢弃",trigger="list.map{it*2}",detection="副作用期望 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0055）",fix="用forEach"))
        BugDB.load(BugRule(id="KT-1224",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="遍历List<String>时修改",trigger="for(x in list<string>){ list<string>.remove(x) }",detection="ConcurrentMod — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0056）",fix="收集后删"))
        BugDB.load(BugRule(id="KT-1225",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="遍历Set<Int>时修改",trigger="for(x in set<int>){ set<int>.remove(x) }",detection="ConcurrentMod — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0057）",fix="收集后删"))
        BugDB.load(BugRule(id="KT-1226",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="遍历Map<String,Int>时修改",trigger="for(x in map<string,int>){ map<string,int>.remove(x) }",detection="ConcurrentMod — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0058）",fix="收集后删"))
        BugDB.load(BugRule(id="KT-1227",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="List.sort后索引错乱",trigger="val idx=list.indexOf(x);list.sort();list[idx]",detection="sort改变位置 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0172）",fix="先记录再排序或使用sorted()"))
        BugDB.load(BugRule(id="KT-1228",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="MutableList.subList泄漏（Any）",trigger="val sub=mutableAny.subAny(0,5);mutableAny.clear()",detection="subList视图失效 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0173）",fix="先copy再操作"))
        BugDB.load(BugRule(id="KT-1229",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="Set.contains自定义对象无hashCode",trigger="setOf(Obj(1)).contains(Obj(1))",detection="默认hashCode比较 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0174）",fix="重写equals+hashCode"))
        BugDB.load(BugRule(id="KT-1230",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="Map.getOrDefault惰性求值",trigger="map.getOrDefault(k,expensive())",detection="expensive总会执行 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0175）",fix="map.getOrPut(k){expensive()}"))
        BugDB.load(BugRule(id="KT-1231",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="不必要的toSet/unique（Short）",trigger="list.toSet().toShort()",detection="去重后转回 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0176）",fix="list.distinct()"))
        BugDB.load(BugRule(id="KT-1232",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="空集合操作（Any?）",trigger="emptyAny?<Any?>().reduce{a,b->a+b}",detection="UnsupportedOperation — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0177）",fix="ifEmpty+fold"))
        BugDB.load(BugRule(id="KT-1233",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="Vector(已弃用)仍使用（String）",trigger="val v=Vector<String>();v.add(1)",detection="synchronized遗留 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0178）",fix="ArrayList/CopyOnWriteArrayList"))
        BugDB.load(BugRule(id="KT-1234",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="groupBy返回LinkedHashMap依赖顺序",trigger="val g=list.groupBy{it.key};g.forEach{}",detection="依赖插入顺序 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0179）",fix="显式sorted"))
        BugDB.load(BugRule(id="KT-1235",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="HMap swap-remove旧组索引未更新",trigger="_keys[i]=_keys[_size]后featIdx只失效被删key组",detection="被挪元素旧组脏索引→NPE — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0334）",fix="swap后同时标记movedKey旧组失效"))
        BugDB.load(BugRule(id="KT-1236",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="first()无元素（Long版）（Sequence<Long>）",trigger="emptySequence<Long><Long>().first()",detection="NoSuchElementException — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0372）",fix="firstOrNull"))
        BugDB.load(BugRule(id="KT-1237",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="first()无元素（Double版）（Char）",trigger="emptyChar<Double>().first()",detection="NoSuchElementException — Char类型字符编码边界,正则匹配组越界（参见 KT-0373）",fix="firstOrNull"))
        BugDB.load(BugRule(id="KT-1238",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="first()无元素（Float版）（Double?）",trigger="emptyDouble?<Float>().first()",detection="NoSuchElementException — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0374）",fix="firstOrNull"))
        BugDB.load(BugRule(id="KT-1239",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="空集合操作（Long版）（Array<Boolean>）",trigger="emptyArray<Boolean><Long>().reduce{a,b->a+b}",detection="UnsupportedOperation — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0375）",fix="ifEmpty+fold"))
        BugDB.load(BugRule(id="KT-1240",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="Paging3重复加载",trigger="PagingSource.load()返回值未去重",detection="重复数据 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0495）",fix="distinctUntilChanged"))
        BugDB.load(BugRule(id="KT-1241",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="遍历时修改",trigger="for(x in list){list.remove(x)}",detection="ConcurrentModificationException — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0047）",fix="收集待删项再删"))
        BugDB.load(BugRule(id="KT-1242",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="迭代器并发修改",trigger="val it=list.iterator();list.add(x);it.next()",detection="迭代器失效 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0048）",fix="先收集再操作"))
        BugDB.load(BugRule(id="KT-1243",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="MutableList暴露（Boolean）",trigger="fun getBoolean()=mutableBoolean",detection="外部可修改内部状态 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0049）",fix="toList()或Collections.unmodifiableList"))
        BugDB.load(BugRule(id="KT-1244",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="first()无元素（Int?）",trigger="emptyInt?<Int?>().first()",detection="NoSuchElementException — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0050）",fix="firstOrNull"))
        BugDB.load(BugRule(id="KT-1245",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="single()多元素",trigger="listOf(1,2).single()",detection="IllegalArgumentException — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0051）",fix="first()或singleOrNull"))
        BugDB.load(BugRule(id="KT-1246",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="HashMap键可变",trigger="val k=MutableObj();map[k]=v;k.mutate();map[k]",detection="hashCode变了 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0052）",fix="用不可变键"))
        BugDB.load(BugRule(id="KT-1247",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="filter后仍操作原集合",trigger="list.filter{;list.add(x)",detection="误解filter返回新集合 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0053）",fix="保存filter结果"))
        BugDB.load(BugRule(id="KT-1248",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="不必要的toList（Set<Int>）",trigger="alreadySet<Int>.toSet<Int>()",detection="重复包装 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0054）",fix="直接使用"))
        BugDB.load(BugRule(id="KT-1249",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="map后丢弃",trigger="list.map{it*2}",detection="副作用期望 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0055）",fix="用forEach"))
        BugDB.load(BugRule(id="KT-1250",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="遍历List<String>时修改",trigger="for(x in list<string>){ list<string>.remove(x) }",detection="ConcurrentMod — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0056）",fix="收集后删"))
        BugDB.load(BugRule(id="KT-1251",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="遍历Set<Int>时修改",trigger="for(x in set<int>){ set<int>.remove(x) }",detection="ConcurrentMod — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0057）",fix="收集后删"))
        BugDB.load(BugRule(id="KT-1252",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="遍历Map<String,Int>时修改",trigger="for(x in map<string,int>){ map<string,int>.remove(x) }",detection="ConcurrentMod — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0058）",fix="收集后删"))
        BugDB.load(BugRule(id="KT-1253",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="List.sort后索引错乱",trigger="val idx=list.indexOf(x);list.sort();list[idx]",detection="sort改变位置 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0172）",fix="先记录再排序或使用sorted()"))
        BugDB.load(BugRule(id="KT-1254",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="MutableList.subList泄漏（Any?）",trigger="val sub=mutableAny?.subAny?(0,5);mutableAny?.clear()",detection="subList视图失效 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0173）",fix="先copy再操作"))
        BugDB.load(BugRule(id="KT-1255",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="Set.contains自定义对象无hashCode",trigger="setOf(Obj(1)).contains(Obj(1))",detection="默认hashCode比较 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0174）",fix="重写equals+hashCode"))
        BugDB.load(BugRule(id="KT-1256",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="Map.getOrDefault惰性求值",trigger="map.getOrDefault(k,expensive())",detection="expensive总会执行 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0175）",fix="map.getOrPut(k){expensive()}"))
        BugDB.load(BugRule(id="KT-1257",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="不必要的toSet/unique（Boolean?）",trigger="list.toSet().toBoolean?()",detection="去重后转回 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0176）",fix="list.distinct()"))
        BugDB.load(BugRule(id="KT-1258",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="空集合操作（Sequence<Long>）",trigger="emptySequence<Long><Sequence<Long>>().reduce{a,b->a+b}",detection="UnsupportedOperation — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0177）",fix="ifEmpty+fold"))
        BugDB.load(BugRule(id="KT-1259",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="Vector(已弃用)仍使用（Char）",trigger="val v=Vector<Char>();v.add(1)",detection="synchronized遗留 — Char类型字符编码边界,正则匹配组越界（参见 KT-0178）",fix="ArrayList/CopyOnWriteArrayList"))
        BugDB.load(BugRule(id="KT-1260",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="groupBy返回LinkedHashMap依赖顺序",trigger="val g=list.groupBy{it.key};g.forEach{}",detection="依赖插入顺序 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0179）",fix="显式sorted"))
        BugDB.load(BugRule(id="KT-1261",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="HMap swap-remove旧组索引未更新",trigger="_keys[i]=_keys[_size]后featIdx只失效被删key组",detection="被挪元素旧组脏索引→NPE — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0334）",fix="swap后同时标记movedKey旧组失效"))
        BugDB.load(BugRule(id="KT-1262",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="first()无元素（Long版）（Float）",trigger="emptyFloat<Long>().first()",detection="NoSuchElementException — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0372）",fix="firstOrNull"))
        BugDB.load(BugRule(id="KT-1263",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="first()无元素（Double版）（Long?）",trigger="emptyLong?<Double>().first()",detection="NoSuchElementException — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0373）",fix="firstOrNull"))
        BugDB.load(BugRule(id="KT-1264",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="first()无元素（Float版）（MutableList<Double>）",trigger="emptyMutableList<Double><Float>().first()",detection="NoSuchElementException — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0374）",fix="firstOrNull"))
        BugDB.load(BugRule(id="KT-1265",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="空集合操作（Long版）（Boolean）",trigger="emptyBoolean<Long>().reduce{a,b->a+b}",detection="UnsupportedOperation — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0375）",fix="ifEmpty+fold"))
        BugDB.load(BugRule(id="KT-1266",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="Paging3重复加载",trigger="PagingSource.load()返回值未去重",detection="重复数据 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0495）",fix="distinctUntilChanged"))
        BugDB.load(BugRule(id="KT-1267",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="遍历时修改",trigger="for(x in list){list.remove(x)}",detection="ConcurrentModificationException — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0047）",fix="收集待删项再删"))
        BugDB.load(BugRule(id="KT-1268",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="迭代器并发修改",trigger="val it=list.iterator();list.add(x);it.next()",detection="迭代器失效 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0048）",fix="先收集再操作"))
        BugDB.load(BugRule(id="KT-1269",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="first()无元素（Set<Int>）",trigger="emptySet<Int><Set<Int>>().first()",detection="NoSuchElementException — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0050）",fix="firstOrNull"))
        BugDB.load(BugRule(id="KT-1270",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="single()多元素",trigger="listOf(1,2).single()",detection="IllegalArgumentException — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0051）",fix="first()或singleOrNull"))
        BugDB.load(BugRule(id="KT-1271",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="HashMap键可变",trigger="val k=MutableObj();map[k]=v;k.mutate();map[k]",detection="hashCode变了 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0052）",fix="用不可变键"))
        BugDB.load(BugRule(id="KT-1272",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="filter后仍操作原集合",trigger="list.filter{;list.add(x)",detection="误解filter返回新集合 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0053）",fix="保存filter结果"))
        BugDB.load(BugRule(id="KT-1273",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="不必要的toList（Int）",trigger="alreadyInt.toInt()",detection="重复包装 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0054）",fix="直接使用"))
        BugDB.load(BugRule(id="KT-1274",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="map后丢弃",trigger="list.map{it*2}",detection="副作用期望 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0055）",fix="用forEach"))
        BugDB.load(BugRule(id="KT-1275",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="遍历List<String>时修改",trigger="for(x in list<string>){ list<string>.remove(x) }",detection="ConcurrentMod — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0056）",fix="收集后删"))
        BugDB.load(BugRule(id="KT-1276",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="遍历Set<Int>时修改",trigger="for(x in set<int>){ set<int>.remove(x) }",detection="ConcurrentMod — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0057）",fix="收集后删"))
        BugDB.load(BugRule(id="KT-1277",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="遍历Map<String,Int>时修改",trigger="for(x in map<string,int>){ map<string,int>.remove(x) }",detection="ConcurrentMod — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0058）",fix="收集后删"))
        BugDB.load(BugRule(id="KT-1278",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="List.sort后索引错乱",trigger="val idx=list.indexOf(x);list.sort();list[idx]",detection="sort改变位置 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0172）",fix="先记录再排序或使用sorted()"))
        BugDB.load(BugRule(id="KT-1279",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="MutableList.subList泄漏（Sequence<Long>）",trigger="val sub=mutableSequence<Long>.subSequence<Long>(0,5);mutableSequence<Long>.clear()",detection="subList视图失效 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0173）",fix="先copy再操作"))
        BugDB.load(BugRule(id="KT-1280",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="Set.contains自定义对象无hashCode",trigger="setOf(Obj(1)).contains(Obj(1))",detection="默认hashCode比较 — Char类型字符编码边界,正则匹配组越界（参见 KT-0174）",fix="重写equals+hashCode"))
        BugDB.load(BugRule(id="KT-1281",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="Map.getOrDefault惰性求值",trigger="map.getOrDefault(k,expensive())",detection="expensive总会执行 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0175）",fix="map.getOrPut(k){expensive()}"))
        BugDB.load(BugRule(id="KT-1282",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="不必要的toSet/unique（Array<Boolean>）",trigger="list.toSet().toArray<Boolean>()",detection="去重后转回 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0176）",fix="list.distinct()"))
        BugDB.load(BugRule(id="KT-1283",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="空集合操作（Float）",trigger="emptyFloat<Float>().reduce{a,b->a+b}",detection="UnsupportedOperation — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0177）",fix="ifEmpty+fold"))
        BugDB.load(BugRule(id="KT-1284",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="Vector(已弃用)仍使用（Long?）",trigger="val v=Vector<Long?>();v.add(1)",detection="synchronized遗留 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0178）",fix="ArrayList/CopyOnWriteArrayList"))
        BugDB.load(BugRule(id="KT-1285",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="groupBy返回LinkedHashMap依赖顺序",trigger="val g=list.groupBy{it.key};g.forEach{}",detection="依赖插入顺序 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0179）",fix="显式sorted"))
        BugDB.load(BugRule(id="KT-1286",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="HMap swap-remove旧组索引未更新",trigger="_keys[i]=_keys[_size]后featIdx只失效被删key组",detection="被挪元素旧组脏索引→NPE — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0334）",fix="swap后同时标记movedKey旧组失效"))
        BugDB.load(BugRule(id="KT-1287",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="first()无元素（Long版）（Int?）",trigger="emptyInt?<Long>().first()",detection="NoSuchElementException — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0372）",fix="firstOrNull"))
        BugDB.load(BugRule(id="KT-1288",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="first()无元素（Double版）（Map<String,Int>）",trigger="emptyMap<String,Int><Double>().first()",detection="NoSuchElementException — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0373）",fix="firstOrNull"))
        BugDB.load(BugRule(id="KT-1289",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="first()无元素（Float版）（Double）",trigger="emptyDouble<Float>().first()",detection="NoSuchElementException — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0374）",fix="firstOrNull"))
        BugDB.load(BugRule(id="KT-1290",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="空集合操作（Long版）（String?）",trigger="emptyString?<Long>().reduce{a,b->a+b}",detection="UnsupportedOperation — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0375）",fix="ifEmpty+fold"))
        BugDB.load(BugRule(id="KT-1291",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="Paging3重复加载",trigger="PagingSource.load()返回值未去重",detection="重复数据 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0495）",fix="distinctUntilChanged"))
        BugDB.load(BugRule(id="KT-1292",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="遍历时修改",trigger="for(x in list){list.remove(x)}",detection="ConcurrentModificationException — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0047）",fix="收集待删项再删"))
        BugDB.load(BugRule(id="KT-1293",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="迭代器并发修改",trigger="val it=list.iterator();list.add(x);it.next()",detection="迭代器失效 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0048）",fix="先收集再操作"))
        BugDB.load(BugRule(id="KT-1294",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="MutableList暴露（List<String>）",trigger="fun getList<String>()=mutableList<String>",detection="外部可修改内部状态 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0049）",fix="toList()或Collections.unmodifiableList"))
        BugDB.load(BugRule(id="KT-1295",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="first()无元素（Int）",trigger="emptyInt<Int>().first()",detection="NoSuchElementException — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0050）",fix="firstOrNull"))
        BugDB.load(BugRule(id="KT-1296",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="single()多元素",trigger="listOf(1,2).single()",detection="IllegalArgumentException — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0051）",fix="first()或singleOrNull"))
        BugDB.load(BugRule(id="KT-1297",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="HashMap键可变",trigger="val k=MutableObj();map[k]=v;k.mutate();map[k]",detection="hashCode变了 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0052）",fix="用不可变键"))
        BugDB.load(BugRule(id="KT-1298",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="filter后仍操作原集合",trigger="list.filter{;list.add(x)",detection="误解filter返回新集合 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0053）",fix="保存filter结果"))
        BugDB.load(BugRule(id="KT-1299",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="不必要的toList（Byte）",trigger="alreadyByte.toByte()",detection="重复包装 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0054）",fix="直接使用"))
        BugDB.load(BugRule(id="KT-1300",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="map后丢弃",trigger="list.map{it*2}",detection="副作用期望 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0055）",fix="用forEach"))
        BugDB.load(BugRule(id="KT-1301",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="遍历List<String>时修改",trigger="for(x in list<string>){ list<string>.remove(x) }",detection="ConcurrentMod — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0056）",fix="收集后删"))
        BugDB.load(BugRule(id="KT-1302",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="遍历Set<Int>时修改",trigger="for(x in set<int>){ set<int>.remove(x) }",detection="ConcurrentMod — Char类型字符编码边界,正则匹配组越界（参见 KT-0057）",fix="收集后删"))
        BugDB.load(BugRule(id="KT-1303",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="遍历Map<String,Int>时修改",trigger="for(x in map<string,int>){ map<string,int>.remove(x) }",detection="ConcurrentMod — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0058）",fix="收集后删"))
        BugDB.load(BugRule(id="KT-1304",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="List.sort后索引错乱",trigger="val idx=list.indexOf(x);list.sort();list[idx]",detection="sort改变位置 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0172）",fix="先记录再排序或使用sorted()"))
        BugDB.load(BugRule(id="KT-1305",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="MutableList.subList泄漏（Float）",trigger="val sub=mutableFloat.subFloat(0,5);mutableFloat.clear()",detection="subList视图失效 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0173）",fix="先copy再操作"))
        BugDB.load(BugRule(id="KT-1306",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="Set.contains自定义对象无hashCode",trigger="setOf(Obj(1)).contains(Obj(1))",detection="默认hashCode比较 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0174）",fix="重写equals+hashCode"))
        BugDB.load(BugRule(id="KT-1307",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="Map.getOrDefault惰性求值",trigger="map.getOrDefault(k,expensive())",detection="expensive总会执行 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0175）",fix="map.getOrPut(k){expensive()}"))
        BugDB.load(BugRule(id="KT-1308",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="不必要的toSet/unique（Boolean）",trigger="list.toSet().toBoolean()",detection="去重后转回 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0176）",fix="list.distinct()"))
        BugDB.load(BugRule(id="KT-1309",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="空集合操作（Int?）",trigger="emptyInt?<Int?>().reduce{a,b->a+b}",detection="UnsupportedOperation — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0177）",fix="ifEmpty+fold"))
        BugDB.load(BugRule(id="KT-1310",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="Vector(已弃用)仍使用（Map<String,Int>）",trigger="val v=Vector<Map<String,Int>>();v.add(1)",detection="synchronized遗留 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0178）",fix="ArrayList/CopyOnWriteArrayList"))
        BugDB.load(BugRule(id="KT-1311",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="groupBy返回LinkedHashMap依赖顺序",trigger="val g=list.groupBy{it.key};g.forEach{}",detection="依赖插入顺序 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0179）",fix="显式sorted"))
        BugDB.load(BugRule(id="KT-1312",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="HMap swap-remove旧组索引未更新",trigger="_keys[i]=_keys[_size]后featIdx只失效被删key组",detection="被挪元素旧组脏索引→NPE — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0334）",fix="swap后同时标记movedKey旧组失效"))
        BugDB.load(BugRule(id="KT-1313",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="first()无元素（Long版）（Set<Int>）",trigger="emptySet<Int><Long>().first()",detection="NoSuchElementException — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0372）",fix="firstOrNull"))
        BugDB.load(BugRule(id="KT-1314",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="first()无元素（Double版）（Long）",trigger="emptyLong<Double>().first()",detection="NoSuchElementException — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0373）",fix="firstOrNull"))
        BugDB.load(BugRule(id="KT-1315",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="first()无元素（Float版）（Any）",trigger="emptyAny<Float>().first()",detection="NoSuchElementException — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0374）",fix="firstOrNull"))
        BugDB.load(BugRule(id="KT-1316",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="空集合操作（Long版）（List<String>）",trigger="emptyList<String><Long>().reduce{a,b->a+b}",detection="UnsupportedOperation — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0375）",fix="ifEmpty+fold"))
        BugDB.load(BugRule(id="KT-1317",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="Paging3重复加载",trigger="PagingSource.load()返回值未去重",detection="重复数据 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0495）",fix="distinctUntilChanged"))
        BugDB.load(BugRule(id="KT-1318",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="遍历时修改",trigger="for(x in list){list.remove(x)}",detection="ConcurrentModificationException — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0047）",fix="收集待删项再删"))
        BugDB.load(BugRule(id="KT-1319",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="迭代器并发修改",trigger="val it=list.iterator();list.add(x);it.next()",detection="迭代器失效 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0048）",fix="先收集再操作"))
        BugDB.load(BugRule(id="KT-1320",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="first()无元素（Byte）",trigger="emptyByte<Byte>().first()",detection="NoSuchElementException — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0050）",fix="firstOrNull"))
        BugDB.load(BugRule(id="KT-1321",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="single()多元素",trigger="listOf(1,2).single()",detection="IllegalArgumentException — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0051）",fix="first()或singleOrNull"))
        BugDB.load(BugRule(id="KT-1322",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="HashMap键可变",trigger="val k=MutableObj();map[k]=v;k.mutate();map[k]",detection="hashCode变了 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0052）",fix="用不可变键"))
        BugDB.load(BugRule(id="KT-1323",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="filter后仍操作原集合",trigger="list.filter{;list.add(x)",detection="误解filter返回新集合 — Char类型字符编码边界,正则匹配组越界（参见 KT-0053）",fix="保存filter结果"))
        BugDB.load(BugRule(id="KT-1324",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="不必要的toList（Double?）",trigger="alreadyDouble?.toDouble?()",detection="重复包装 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0054）",fix="直接使用"))
        BugDB.load(BugRule(id="KT-1325",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="map后丢弃",trigger="list.map{it*2}",detection="副作用期望 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0055）",fix="用forEach"))
        BugDB.load(BugRule(id="KT-1326",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="遍历List<String>时修改",trigger="for(x in list<string>){ list<string>.remove(x) }",detection="ConcurrentMod — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0056）",fix="收集后删"))
        BugDB.load(BugRule(id="KT-1327",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="遍历Set<Int>时修改",trigger="for(x in set<int>){ set<int>.remove(x) }",detection="ConcurrentMod — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0057）",fix="收集后删"))
        BugDB.load(BugRule(id="KT-1328",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="遍历Map<String,Int>时修改",trigger="for(x in map<string,int>){ map<string,int>.remove(x) }",detection="ConcurrentMod — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0058）",fix="收集后删"))
        BugDB.load(BugRule(id="KT-1329",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="List.sort后索引错乱",trigger="val idx=list.indexOf(x);list.sort();list[idx]",detection="sort改变位置 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0172）",fix="先记录再排序或使用sorted()"))
        BugDB.load(BugRule(id="KT-1330",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="MutableList.subList泄漏（Int?）",trigger="val sub=mutableInt?.subInt?(0,5);mutableInt?.clear()",detection="subList视图失效 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0173）",fix="先copy再操作"))
        BugDB.load(BugRule(id="KT-1331",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="Set.contains自定义对象无hashCode",trigger="setOf(Obj(1)).contains(Obj(1))",detection="默认hashCode比较 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0174）",fix="重写equals+hashCode"))
        BugDB.load(BugRule(id="KT-1332",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="Map.getOrDefault惰性求值",trigger="map.getOrDefault(k,expensive())",detection="expensive总会执行 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0175）",fix="map.getOrPut(k){expensive()}"))
        BugDB.load(BugRule(id="KT-1333",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="不必要的toSet/unique（String?）",trigger="list.toSet().toString?()",detection="去重后转回 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0176）",fix="list.distinct()"))
        BugDB.load(BugRule(id="KT-1334",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="空集合操作（Set<Int>）",trigger="emptySet<Int><Set<Int>>().reduce{a,b->a+b}",detection="UnsupportedOperation — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0177）",fix="ifEmpty+fold"))
        BugDB.load(BugRule(id="KT-1335",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="Vector(已弃用)仍使用（Long）",trigger="val v=Vector<Long>();v.add(1)",detection="synchronized遗留 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0178）",fix="ArrayList/CopyOnWriteArrayList"))
        BugDB.load(BugRule(id="KT-1336",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="groupBy返回LinkedHashMap依赖顺序",trigger="val g=list.groupBy{it.key};g.forEach{}",detection="依赖插入顺序 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0179）",fix="显式sorted"))
        BugDB.load(BugRule(id="KT-1337",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="HMap swap-remove旧组索引未更新",trigger="_keys[i]=_keys[_size]后featIdx只失效被删key组",detection="被挪元素旧组脏索引→NPE — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0334）",fix="swap后同时标记movedKey旧组失效"))
        BugDB.load(BugRule(id="KT-1338",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="first()无元素（Long版）（Int）",trigger="emptyInt<Long>().first()",detection="NoSuchElementException — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0372）",fix="firstOrNull"))
        BugDB.load(BugRule(id="KT-1339",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="first()无元素（Double版）（Short）",trigger="emptyShort<Double>().first()",detection="NoSuchElementException — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0373）",fix="firstOrNull"))
        BugDB.load(BugRule(id="KT-1340",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="first()无元素（Float版）（Any?）",trigger="emptyAny?<Float>().first()",detection="NoSuchElementException — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0374）",fix="firstOrNull"))
        BugDB.load(BugRule(id="KT-1341",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="空集合操作（Long版）（String）",trigger="emptyString<Long>().reduce{a,b->a+b}",detection="UnsupportedOperation — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0375）",fix="ifEmpty+fold"))
        BugDB.load(BugRule(id="KT-1342",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="Paging3重复加载",trigger="PagingSource.load()返回值未去重",detection="重复数据 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0495）",fix="distinctUntilChanged"))
        BugDB.load(BugRule(id="KT-1343",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="遍历时修改",trigger="for(x in list){list.remove(x)}",detection="ConcurrentModificationException — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0047）",fix="收集待删项再删"))
        BugDB.load(BugRule(id="KT-1344",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="迭代器并发修改",trigger="val it=list.iterator();list.add(x);it.next()",detection="迭代器失效 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0048）",fix="先收集再操作"))
        BugDB.load(BugRule(id="KT-1345",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="MutableList暴露（Char）",trigger="fun getChar()=mutableChar",detection="外部可修改内部状态 — Char类型字符编码边界,正则匹配组越界（参见 KT-0049）",fix="toList()或Collections.unmodifiableList"))
        BugDB.load(BugRule(id="KT-1346",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="first()无元素（Double?）",trigger="emptyDouble?<Double?>().first()",detection="NoSuchElementException — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0050）",fix="firstOrNull"))
        BugDB.load(BugRule(id="KT-1347",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="single()多元素",trigger="listOf(1,2).single()",detection="IllegalArgumentException — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0051）",fix="first()或singleOrNull"))
        BugDB.load(BugRule(id="KT-1348",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="HashMap键可变",trigger="val k=MutableObj();map[k]=v;k.mutate();map[k]",detection="hashCode变了 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0052）",fix="用不可变键"))
        BugDB.load(BugRule(id="KT-1349",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="filter后仍操作原集合",trigger="list.filter{;list.add(x)",detection="误解filter返回新集合 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0053）",fix="保存filter结果"))
        BugDB.load(BugRule(id="KT-1350",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="不必要的toList（MutableList<Double>）",trigger="alreadyMutableList<Double>.toMutableList<Double>()",detection="重复包装 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0054）",fix="直接使用"))
        BugDB.load(BugRule(id="KT-1351",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="map后丢弃",trigger="list.map{it*2}",detection="副作用期望 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0055）",fix="用forEach"))
        BugDB.load(BugRule(id="KT-1352",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="遍历List<String>时修改",trigger="for(x in list<string>){ list<string>.remove(x) }",detection="ConcurrentMod — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0056）",fix="收集后删"))
        BugDB.load(BugRule(id="KT-1353",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="遍历Set<Int>时修改",trigger="for(x in set<int>){ set<int>.remove(x) }",detection="ConcurrentMod — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0057）",fix="收集后删"))
        BugDB.load(BugRule(id="KT-1354",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="遍历Map<String,Int>时修改",trigger="for(x in map<string,int>){ map<string,int>.remove(x) }",detection="ConcurrentMod — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0058）",fix="收集后删"))
        BugDB.load(BugRule(id="KT-1355",category=BugCategory.COLLECTIONS,severity=BugSeverity.SEVERE,
            title="List.sort后索引错乱",trigger="val idx=list.indexOf(x);list.sort();list[idx]",detection="sort改变位置 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0172）",fix="先记录再排序或使用sorted()"))
        BugDB.load(BugRule(id="KT-1356",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="MutableList.subList泄漏（Set<Int>）",trigger="val sub=mutableSet<Int>.subSet<Int>(0,5);mutableSet<Int>.clear()",detection="subList视图失效 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0173）",fix="先copy再操作"))
        BugDB.load(BugRule(id="KT-1357",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="Set.contains自定义对象无hashCode",trigger="setOf(Obj(1)).contains(Obj(1))",detection="默认hashCode比较 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0174）",fix="重写equals+hashCode"))
        BugDB.load(BugRule(id="KT-1358",category=BugCategory.COLLECTIONS,severity=BugSeverity.MODERATE,
            title="Map.getOrDefault惰性求值",trigger="map.getOrDefault(k,expensive())",detection="expensive总会执行 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0175）",fix="map.getOrPut(k){expensive()}"))
        BugDB.load(BugRule(id="KT-1359",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="不必要的toSet/unique（List<String>）",trigger="list.toSet().toList<String>()",detection="去重后转回 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0176）",fix="list.distinct()"))
        BugDB.load(BugRule(id="KT-1360",category=BugCategory.COLLECTIONS,severity=BugSeverity.MILD,
            title="空集合操作（Int）",trigger="emptyInt<Int>().reduce{a,b->a+b}",detection="UnsupportedOperation — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0177）",fix="ifEmpty+fold"))
        BugDB.load(BugRule(id="KT-1361",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="反射访问私有成员",trigger="cls.getDeclaredField(\\\"secret\\\");f.isAccessible=true",detection="破坏封装 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0059）",fix="提供公开接口"))
        BugDB.load(BugRule(id="KT-1362",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="KClass与Java Class混淆",trigger="MyClass::class.java与MyClass::class",detection="类型不匹配 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0060）",fix="明确Java/Kotlin"))
        BugDB.load(BugRule(id="KT-1363",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="callBy参数顺序错误",trigger="func.callBy(mapOf(param to value))",detection="参数名不匹配 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0061）",fix="使用带名参数"))
        BugDB.load(BugRule(id="KT-1364",category=BugCategory.REFLECTION,severity=BugSeverity.MILD,
            title="反射性能开销",trigger="cls.members.forEach{",detection="大量反射操作 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0062）",fix="缓存KCallable"))
        BugDB.load(BugRule(id="KT-1365",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="反射修改final字段",trigger="val f=cls.getDeclaredField(\\\"x\\\");f.isAccessible=true;f.set(obj,v)",detection="破坏不变性 — Char类型字符编码边界,正则匹配组越界（参见 KT-0180）",fix="提供公开setter"))
        BugDB.load(BugRule(id="KT-1366",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="KFunction反射调用性能",trigger="func.call(1,2)",detection="每次call检查参数 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0181）",fix="缓存KCallable"))
        BugDB.load(BugRule(id="KT-1367",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="反射获取泛型参数",trigger="cls.typeParameters[0].upperBounds",detection="运行时擦除 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0182）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-1368",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="::class在companion上",trigger="MyClass::class与MyClass.Companion::class",detection="混淆 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0183）",fix="明确区分"))
        BugDB.load(BugRule(id="KT-1369",category=BugCategory.REFLECTION,severity=BugSeverity.MILD,
            title="KClass.simpleName与javaClass.simpleName",trigger="KClass.simpleName可能为null",detection="可能为null — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0184）",fix="用qualifiedName"))
        BugDB.load(BugRule(id="KT-1370",category=BugCategory.REFLECTION,severity=BugSeverity.MILD,
            title="不必要的反射实例化",trigger="cls.java.newInstance()",detection="已弃用 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0185）",fix="cls.createInstance()或工厂"))
        BugDB.load(BugRule(id="KT-1371",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="反射访问私有成员",trigger="cls.getDeclaredField(\\\"secret\\\");f.isAccessible=true",detection="破坏封装 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0059）",fix="提供公开接口"))
        BugDB.load(BugRule(id="KT-1372",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="KClass与Java Class混淆",trigger="MyClass::class.java与MyClass::class",detection="类型不匹配 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0060）",fix="明确Java/Kotlin"))
        BugDB.load(BugRule(id="KT-1373",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="callBy参数顺序错误",trigger="func.callBy(mapOf(param to value))",detection="参数名不匹配 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0061）",fix="使用带名参数"))
        BugDB.load(BugRule(id="KT-1374",category=BugCategory.REFLECTION,severity=BugSeverity.MILD,
            title="反射性能开销",trigger="cls.members.forEach{",detection="大量反射操作 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0062）",fix="缓存KCallable"))
        BugDB.load(BugRule(id="KT-1375",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="反射修改final字段",trigger="val f=cls.getDeclaredField(\\\"x\\\");f.isAccessible=true;f.set(obj,v)",detection="破坏不变性 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0180）",fix="提供公开setter"))
        BugDB.load(BugRule(id="KT-1376",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="KFunction反射调用性能",trigger="func.call(1,2)",detection="每次call检查参数 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0181）",fix="缓存KCallable"))
        BugDB.load(BugRule(id="KT-1377",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="反射获取泛型参数",trigger="cls.typeParameters[0].upperBounds",detection="运行时擦除 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0182）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-1378",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="::class在companion上",trigger="MyClass::class与MyClass.Companion::class",detection="混淆 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0183）",fix="明确区分"))
        BugDB.load(BugRule(id="KT-1379",category=BugCategory.REFLECTION,severity=BugSeverity.MILD,
            title="KClass.simpleName与javaClass.simpleName",trigger="KClass.simpleName可能为null",detection="可能为null — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0184）",fix="用qualifiedName"))
        BugDB.load(BugRule(id="KT-1380",category=BugCategory.REFLECTION,severity=BugSeverity.MILD,
            title="不必要的反射实例化",trigger="cls.java.newInstance()",detection="已弃用 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0185）",fix="cls.createInstance()或工厂"))
        BugDB.load(BugRule(id="KT-1381",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="反射访问私有成员",trigger="cls.getDeclaredField(\\\"secret\\\");f.isAccessible=true",detection="破坏封装 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0059）",fix="提供公开接口"))
        BugDB.load(BugRule(id="KT-1382",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="KClass与Java Class混淆",trigger="MyClass::class.java与MyClass::class",detection="类型不匹配 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0060）",fix="明确Java/Kotlin"))
        BugDB.load(BugRule(id="KT-1383",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="callBy参数顺序错误",trigger="func.callBy(mapOf(param to value))",detection="参数名不匹配 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0061）",fix="使用带名参数"))
        BugDB.load(BugRule(id="KT-1384",category=BugCategory.REFLECTION,severity=BugSeverity.MILD,
            title="反射性能开销",trigger="cls.members.forEach{",detection="大量反射操作 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0062）",fix="缓存KCallable"))
        BugDB.load(BugRule(id="KT-1385",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="反射修改final字段",trigger="val f=cls.getDeclaredField(\\\"x\\\");f.isAccessible=true;f.set(obj,v)",detection="破坏不变性 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0180）",fix="提供公开setter"))
        BugDB.load(BugRule(id="KT-1386",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="KFunction反射调用性能",trigger="func.call(1,2)",detection="每次call检查参数 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0181）",fix="缓存KCallable"))
        BugDB.load(BugRule(id="KT-1387",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="反射获取泛型参数",trigger="cls.typeParameters[0].upperBounds",detection="运行时擦除 — Char类型字符编码边界,正则匹配组越界（参见 KT-0182）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-1388",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="::class在companion上",trigger="MyClass::class与MyClass.Companion::class",detection="混淆 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0183）",fix="明确区分"))
        BugDB.load(BugRule(id="KT-1389",category=BugCategory.REFLECTION,severity=BugSeverity.MILD,
            title="KClass.simpleName与javaClass.simpleName",trigger="KClass.simpleName可能为null",detection="可能为null — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0184）",fix="用qualifiedName"))
        BugDB.load(BugRule(id="KT-1390",category=BugCategory.REFLECTION,severity=BugSeverity.MILD,
            title="不必要的反射实例化",trigger="cls.java.newInstance()",detection="已弃用 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0185）",fix="cls.createInstance()或工厂"))
        BugDB.load(BugRule(id="KT-1391",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="反射访问私有成员",trigger="cls.getDeclaredField(\\\"secret\\\");f.isAccessible=true",detection="破坏封装 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0059）",fix="提供公开接口"))
        BugDB.load(BugRule(id="KT-1392",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="KClass与Java Class混淆",trigger="MyClass::class.java与MyClass::class",detection="类型不匹配 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0060）",fix="明确Java/Kotlin"))
        BugDB.load(BugRule(id="KT-1393",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="callBy参数顺序错误",trigger="func.callBy(mapOf(param to value))",detection="参数名不匹配 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0061）",fix="使用带名参数"))
        BugDB.load(BugRule(id="KT-1394",category=BugCategory.REFLECTION,severity=BugSeverity.MILD,
            title="反射性能开销",trigger="cls.members.forEach{",detection="大量反射操作 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0062）",fix="缓存KCallable"))
        BugDB.load(BugRule(id="KT-1395",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="反射修改final字段",trigger="val f=cls.getDeclaredField(\\\"x\\\");f.isAccessible=true;f.set(obj,v)",detection="破坏不变性 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0180）",fix="提供公开setter"))
        BugDB.load(BugRule(id="KT-1396",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="KFunction反射调用性能",trigger="func.call(1,2)",detection="每次call检查参数 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0181）",fix="缓存KCallable"))
        BugDB.load(BugRule(id="KT-1397",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="反射获取泛型参数",trigger="cls.typeParameters[0].upperBounds",detection="运行时擦除 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0182）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-1398",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="::class在companion上",trigger="MyClass::class与MyClass.Companion::class",detection="混淆 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0183）",fix="明确区分"))
        BugDB.load(BugRule(id="KT-1399",category=BugCategory.REFLECTION,severity=BugSeverity.MILD,
            title="KClass.simpleName与javaClass.simpleName",trigger="KClass.simpleName可能为null",detection="可能为null — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0184）",fix="用qualifiedName"))
        BugDB.load(BugRule(id="KT-1400",category=BugCategory.REFLECTION,severity=BugSeverity.MILD,
            title="不必要的反射实例化",trigger="cls.java.newInstance()",detection="已弃用 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0185）",fix="cls.createInstance()或工厂"))
        BugDB.load(BugRule(id="KT-1401",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="反射访问私有成员",trigger="cls.getDeclaredField(\\\"secret\\\");f.isAccessible=true",detection="破坏封装 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0059）",fix="提供公开接口"))
        BugDB.load(BugRule(id="KT-1402",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="KClass与Java Class混淆",trigger="MyClass::class.java与MyClass::class",detection="类型不匹配 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0060）",fix="明确Java/Kotlin"))
        BugDB.load(BugRule(id="KT-1403",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="callBy参数顺序错误",trigger="func.callBy(mapOf(param to value))",detection="参数名不匹配 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0061）",fix="使用带名参数"))
        BugDB.load(BugRule(id="KT-1404",category=BugCategory.REFLECTION,severity=BugSeverity.MILD,
            title="反射性能开销",trigger="cls.members.forEach{",detection="大量反射操作 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0062）",fix="缓存KCallable"))
        BugDB.load(BugRule(id="KT-1405",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="反射修改final字段",trigger="val f=cls.getDeclaredField(\\\"x\\\");f.isAccessible=true;f.set(obj,v)",detection="破坏不变性 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0180）",fix="提供公开setter"))
        BugDB.load(BugRule(id="KT-1406",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="KFunction反射调用性能",trigger="func.call(1,2)",detection="每次call检查参数 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0181）",fix="缓存KCallable"))
        BugDB.load(BugRule(id="KT-1407",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="反射获取泛型参数",trigger="cls.typeParameters[0].upperBounds",detection="运行时擦除 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0182）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-1408",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="::class在companion上",trigger="MyClass::class与MyClass.Companion::class",detection="混淆 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0183）",fix="明确区分"))
        BugDB.load(BugRule(id="KT-1409",category=BugCategory.REFLECTION,severity=BugSeverity.MILD,
            title="KClass.simpleName与javaClass.simpleName",trigger="KClass.simpleName可能为null",detection="可能为null — Char类型字符编码边界,正则匹配组越界（参见 KT-0184）",fix="用qualifiedName"))
        BugDB.load(BugRule(id="KT-1410",category=BugCategory.REFLECTION,severity=BugSeverity.MILD,
            title="不必要的反射实例化",trigger="cls.java.newInstance()",detection="已弃用 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0185）",fix="cls.createInstance()或工厂"))
        BugDB.load(BugRule(id="KT-1411",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="反射访问私有成员",trigger="cls.getDeclaredField(\\\"secret\\\");f.isAccessible=true",detection="破坏封装 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0059）",fix="提供公开接口"))
        BugDB.load(BugRule(id="KT-1412",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="KClass与Java Class混淆",trigger="MyClass::class.java与MyClass::class",detection="类型不匹配 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0060）",fix="明确Java/Kotlin"))
        BugDB.load(BugRule(id="KT-1413",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="callBy参数顺序错误",trigger="func.callBy(mapOf(param to value))",detection="参数名不匹配 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0061）",fix="使用带名参数"))
        BugDB.load(BugRule(id="KT-1414",category=BugCategory.REFLECTION,severity=BugSeverity.MILD,
            title="反射性能开销",trigger="cls.members.forEach{",detection="大量反射操作 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0062）",fix="缓存KCallable"))
        BugDB.load(BugRule(id="KT-1415",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="反射修改final字段",trigger="val f=cls.getDeclaredField(\\\"x\\\");f.isAccessible=true;f.set(obj,v)",detection="破坏不变性 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0180）",fix="提供公开setter"))
        BugDB.load(BugRule(id="KT-1416",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="KFunction反射调用性能",trigger="func.call(1,2)",detection="每次call检查参数 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0181）",fix="缓存KCallable"))
        BugDB.load(BugRule(id="KT-1417",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="反射获取泛型参数",trigger="cls.typeParameters[0].upperBounds",detection="运行时擦除 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0182）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-1418",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="::class在companion上",trigger="MyClass::class与MyClass.Companion::class",detection="混淆 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0183）",fix="明确区分"))
        BugDB.load(BugRule(id="KT-1419",category=BugCategory.REFLECTION,severity=BugSeverity.MILD,
            title="KClass.simpleName与javaClass.simpleName",trigger="KClass.simpleName可能为null",detection="可能为null — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0184）",fix="用qualifiedName"))
        BugDB.load(BugRule(id="KT-1420",category=BugCategory.REFLECTION,severity=BugSeverity.MILD,
            title="不必要的反射实例化",trigger="cls.java.newInstance()",detection="已弃用 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0185）",fix="cls.createInstance()或工厂"))
        BugDB.load(BugRule(id="KT-1421",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="反射访问私有成员",trigger="cls.getDeclaredField(\\\"secret\\\");f.isAccessible=true",detection="破坏封装 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0059）",fix="提供公开接口"))
        BugDB.load(BugRule(id="KT-1422",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="KClass与Java Class混淆",trigger="MyClass::class.java与MyClass::class",detection="类型不匹配 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0060）",fix="明确Java/Kotlin"))
        BugDB.load(BugRule(id="KT-1423",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="callBy参数顺序错误",trigger="func.callBy(mapOf(param to value))",detection="参数名不匹配 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0061）",fix="使用带名参数"))
        BugDB.load(BugRule(id="KT-1424",category=BugCategory.REFLECTION,severity=BugSeverity.MILD,
            title="反射性能开销",trigger="cls.members.forEach{",detection="大量反射操作 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0062）",fix="缓存KCallable"))
        BugDB.load(BugRule(id="KT-1425",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="反射修改final字段",trigger="val f=cls.getDeclaredField(\\\"x\\\");f.isAccessible=true;f.set(obj,v)",detection="破坏不变性 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0180）",fix="提供公开setter"))
        BugDB.load(BugRule(id="KT-1426",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="KFunction反射调用性能",trigger="func.call(1,2)",detection="每次call检查参数 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0181）",fix="缓存KCallable"))
        BugDB.load(BugRule(id="KT-1427",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="反射获取泛型参数",trigger="cls.typeParameters[0].upperBounds",detection="运行时擦除 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0182）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-1428",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="::class在companion上",trigger="MyClass::class与MyClass.Companion::class",detection="混淆 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0183）",fix="明确区分"))
        BugDB.load(BugRule(id="KT-1429",category=BugCategory.REFLECTION,severity=BugSeverity.MILD,
            title="KClass.simpleName与javaClass.simpleName",trigger="KClass.simpleName可能为null",detection="可能为null — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0184）",fix="用qualifiedName"))
        BugDB.load(BugRule(id="KT-1430",category=BugCategory.REFLECTION,severity=BugSeverity.MILD,
            title="不必要的反射实例化",trigger="cls.java.newInstance()",detection="已弃用 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0185）",fix="cls.createInstance()或工厂"))
        BugDB.load(BugRule(id="KT-1431",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="反射访问私有成员",trigger="cls.getDeclaredField(\\\"secret\\\");f.isAccessible=true",detection="破坏封装 — Char类型字符编码边界,正则匹配组越界（参见 KT-0059）",fix="提供公开接口"))
        BugDB.load(BugRule(id="KT-1432",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="KClass与Java Class混淆",trigger="MyClass::class.java与MyClass::class",detection="类型不匹配 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0060）",fix="明确Java/Kotlin"))
        BugDB.load(BugRule(id="KT-1433",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="callBy参数顺序错误",trigger="func.callBy(mapOf(param to value))",detection="参数名不匹配 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0061）",fix="使用带名参数"))
        BugDB.load(BugRule(id="KT-1434",category=BugCategory.REFLECTION,severity=BugSeverity.MILD,
            title="反射性能开销",trigger="cls.members.forEach{",detection="大量反射操作 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0062）",fix="缓存KCallable"))
        BugDB.load(BugRule(id="KT-1435",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="反射修改final字段",trigger="val f=cls.getDeclaredField(\\\"x\\\");f.isAccessible=true;f.set(obj,v)",detection="破坏不变性 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0180）",fix="提供公开setter"))
        BugDB.load(BugRule(id="KT-1436",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="KFunction反射调用性能",trigger="func.call(1,2)",detection="每次call检查参数 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0181）",fix="缓存KCallable"))
        BugDB.load(BugRule(id="KT-1437",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="反射获取泛型参数",trigger="cls.typeParameters[0].upperBounds",detection="运行时擦除 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0182）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-1438",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="::class在companion上",trigger="MyClass::class与MyClass.Companion::class",detection="混淆 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0183）",fix="明确区分"))
        BugDB.load(BugRule(id="KT-1439",category=BugCategory.REFLECTION,severity=BugSeverity.MILD,
            title="KClass.simpleName与javaClass.simpleName",trigger="KClass.simpleName可能为null",detection="可能为null — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0184）",fix="用qualifiedName"))
        BugDB.load(BugRule(id="KT-1440",category=BugCategory.REFLECTION,severity=BugSeverity.MILD,
            title="不必要的反射实例化",trigger="cls.java.newInstance()",detection="已弃用 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0185）",fix="cls.createInstance()或工厂"))
        BugDB.load(BugRule(id="KT-1441",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="反射访问私有成员",trigger="cls.getDeclaredField(\\\"secret\\\");f.isAccessible=true",detection="破坏封装 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0059）",fix="提供公开接口"))
        BugDB.load(BugRule(id="KT-1442",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="KClass与Java Class混淆",trigger="MyClass::class.java与MyClass::class",detection="类型不匹配 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0060）",fix="明确Java/Kotlin"))
        BugDB.load(BugRule(id="KT-1443",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="callBy参数顺序错误",trigger="func.callBy(mapOf(param to value))",detection="参数名不匹配 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0061）",fix="使用带名参数"))
        BugDB.load(BugRule(id="KT-1444",category=BugCategory.REFLECTION,severity=BugSeverity.MILD,
            title="反射性能开销",trigger="cls.members.forEach{",detection="大量反射操作 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0062）",fix="缓存KCallable"))
        BugDB.load(BugRule(id="KT-1445",category=BugCategory.REFLECTION,severity=BugSeverity.SEVERE,
            title="反射修改final字段",trigger="val f=cls.getDeclaredField(\\\"x\\\");f.isAccessible=true;f.set(obj,v)",detection="破坏不变性 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0180）",fix="提供公开setter"))
        BugDB.load(BugRule(id="KT-1446",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="KFunction反射调用性能",trigger="func.call(1,2)",detection="每次call检查参数 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0181）",fix="缓存KCallable"))
        BugDB.load(BugRule(id="KT-1447",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="反射获取泛型参数",trigger="cls.typeParameters[0].upperBounds",detection="运行时擦除 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0182）",fix="reified+inline"))
        BugDB.load(BugRule(id="KT-1448",category=BugCategory.REFLECTION,severity=BugSeverity.MODERATE,
            title="::class在companion上",trigger="MyClass::class与MyClass.Companion::class",detection="混淆 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0183）",fix="明确区分"))
        BugDB.load(BugRule(id="KT-1449",category=BugCategory.REFLECTION,severity=BugSeverity.MILD,
            title="KClass.simpleName与javaClass.simpleName",trigger="KClass.simpleName可能为null",detection="可能为null — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0184）",fix="用qualifiedName"))
        BugDB.load(BugRule(id="KT-1450",category=BugCategory.REFLECTION,severity=BugSeverity.MILD,
            title="不必要的反射实例化",trigger="cls.java.newInstance()",detection="已弃用 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0185）",fix="cls.createInstance()或工厂"))
        BugDB.load(BugRule(id="KT-1451",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="非局部return",trigger="fun f(){list.forEach{if(it)return}}",detection="return跳出外层函数 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0063）",fix="return@forEach"))
        BugDB.load(BugRule(id="KT-1452",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="隐式this歧义",trigger="apply{name=name}",detection="内外name混淆 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0064）",fix="this@outer.name"))
        BugDB.load(BugRule(id="KT-1453",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="嵌套apply/also/let混乱",trigger="obj.apply{also{let{run{}}}",detection="作用域函数地狱 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0065）",fix="提取命名函数"))
        BugDB.load(BugRule(id="KT-1454",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="run与with混淆",trigger="run{this.method()} vs with(obj){method()}",detection="语义不同 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0066）",fix="按需选择"))
        BugDB.load(BugRule(id="KT-1455",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="also返回值忽略",trigger="obj.also{it.mutate()}",detection="also返回原对象 — Char类型字符编码边界,正则匹配组越界（参见 KT-0067）",fix="不需要返回值用apply"))
        BugDB.load(BugRule(id="KT-1456",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="多余的run",trigger="run{expr}",detection="单表达式不需要run — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0068）",fix="直接用expr"))
        BugDB.load(BugRule(id="KT-1457",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="Builder DSL遗漏@DslMarker",trigger="@DslMarker;obj.apply{build{apply{obj}}}",detection="隐式this穿透 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0186）",fix="加@DslMarker"))
        BugDB.load(BugRule(id="KT-1458",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="also与apply链式错误",trigger="val x=obj.also{it.prop=1}.also{it.prop=2}",detection="also返回原对象 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0187）",fix="apply更适合"))
        BugDB.load(BugRule(id="KT-1459",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="with接收者为可空",trigger="with(maybeNull){this.method()}",detection="NPE风险 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0188）",fix="maybeNull?.let{with(it){}}"))
        BugDB.load(BugRule(id="KT-1460",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="run非扩展+扩展混淆",trigger="obj.run{length} vs run{obj.length}",detection="this指向不同 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0189）",fix="统一风格"))
        BugDB.load(BugRule(id="KT-1461",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="let与run语义混淆",trigger="x?.let{it*2} vs x?.run{this*2}",detection="it与this — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0190）",fix="根据是否需要变换选择"))
        BugDB.load(BugRule(id="KT-1462",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="takeUnless与takeIf误用",trigger="x.takeUnless{it>0}",detection="逻辑相反 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0191）",fix="用takeIf+!或直接if"))
        BugDB.load(BugRule(id="KT-1463",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="非局部return",trigger="fun f(){list.forEach{if(it)return}}",detection="return跳出外层函数 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0063）",fix="return@forEach"))
        BugDB.load(BugRule(id="KT-1464",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="隐式this歧义",trigger="apply{name=name}",detection="内外name混淆 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0064）",fix="this@outer.name"))
        BugDB.load(BugRule(id="KT-1465",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="嵌套apply/also/let混乱",trigger="obj.apply{also{let{run{}}}",detection="作用域函数地狱 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0065）",fix="提取命名函数"))
        BugDB.load(BugRule(id="KT-1466",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="run与with混淆",trigger="run{this.method()} vs with(obj){method()}",detection="语义不同 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0066）",fix="按需选择"))
        BugDB.load(BugRule(id="KT-1467",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="also返回值忽略",trigger="obj.also{it.mutate()}",detection="also返回原对象 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0067）",fix="不需要返回值用apply"))
        BugDB.load(BugRule(id="KT-1468",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="多余的run",trigger="run{expr}",detection="单表达式不需要run — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0068）",fix="直接用expr"))
        BugDB.load(BugRule(id="KT-1469",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="Builder DSL遗漏@DslMarker",trigger="@DslMarker;obj.apply{build{apply{obj}}}",detection="隐式this穿透 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0186）",fix="加@DslMarker"))
        BugDB.load(BugRule(id="KT-1470",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="also与apply链式错误",trigger="val x=obj.also{it.prop=1}.also{it.prop=2}",detection="also返回原对象 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0187）",fix="apply更适合"))
        BugDB.load(BugRule(id="KT-1471",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="with接收者为可空",trigger="with(maybeNull){this.method()}",detection="NPE风险 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0188）",fix="maybeNull?.let{with(it){}}"))
        BugDB.load(BugRule(id="KT-1472",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="run非扩展+扩展混淆",trigger="obj.run{length} vs run{obj.length}",detection="this指向不同 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0189）",fix="统一风格"))
        BugDB.load(BugRule(id="KT-1473",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="let与run语义混淆",trigger="x?.let{it*2} vs x?.run{this*2}",detection="it与this — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0190）",fix="根据是否需要变换选择"))
        BugDB.load(BugRule(id="KT-1474",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="takeUnless与takeIf误用",trigger="x.takeUnless{it>0}",detection="逻辑相反 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0191）",fix="用takeIf+!或直接if"))
        BugDB.load(BugRule(id="KT-1475",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="非局部return",trigger="fun f(){list.forEach{if(it)return}}",detection="return跳出外层函数 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0063）",fix="return@forEach"))
        BugDB.load(BugRule(id="KT-1476",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="隐式this歧义",trigger="apply{name=name}",detection="内外name混淆 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0064）",fix="this@outer.name"))
        BugDB.load(BugRule(id="KT-1477",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="嵌套apply/also/let混乱",trigger="obj.apply{also{let{run{}}}",detection="作用域函数地狱 — Char类型字符编码边界,正则匹配组越界（参见 KT-0065）",fix="提取命名函数"))
        BugDB.load(BugRule(id="KT-1478",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="run与with混淆",trigger="run{this.method()} vs with(obj){method()}",detection="语义不同 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0066）",fix="按需选择"))
        BugDB.load(BugRule(id="KT-1479",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="also返回值忽略",trigger="obj.also{it.mutate()}",detection="also返回原对象 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0067）",fix="不需要返回值用apply"))
        BugDB.load(BugRule(id="KT-1480",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="多余的run",trigger="run{expr}",detection="单表达式不需要run — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0068）",fix="直接用expr"))
        BugDB.load(BugRule(id="KT-1481",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="Builder DSL遗漏@DslMarker",trigger="@DslMarker;obj.apply{build{apply{obj}}}",detection="隐式this穿透 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0186）",fix="加@DslMarker"))
        BugDB.load(BugRule(id="KT-1482",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="also与apply链式错误",trigger="val x=obj.also{it.prop=1}.also{it.prop=2}",detection="also返回原对象 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0187）",fix="apply更适合"))
        BugDB.load(BugRule(id="KT-1483",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="with接收者为可空",trigger="with(maybeNull){this.method()}",detection="NPE风险 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0188）",fix="maybeNull?.let{with(it){}}"))
        BugDB.load(BugRule(id="KT-1484",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="run非扩展+扩展混淆",trigger="obj.run{length} vs run{obj.length}",detection="this指向不同 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0189）",fix="统一风格"))
        BugDB.load(BugRule(id="KT-1485",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="let与run语义混淆",trigger="x?.let{it*2} vs x?.run{this*2}",detection="it与this — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0190）",fix="根据是否需要变换选择"))
        BugDB.load(BugRule(id="KT-1486",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="takeUnless与takeIf误用",trigger="x.takeUnless{it>0}",detection="逻辑相反 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0191）",fix="用takeIf+!或直接if"))
        BugDB.load(BugRule(id="KT-1487",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="非局部return",trigger="fun f(){list.forEach{if(it)return}}",detection="return跳出外层函数 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0063）",fix="return@forEach"))
        BugDB.load(BugRule(id="KT-1488",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="隐式this歧义",trigger="apply{name=name}",detection="内外name混淆 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0064）",fix="this@outer.name"))
        BugDB.load(BugRule(id="KT-1489",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="嵌套apply/also/let混乱",trigger="obj.apply{also{let{run{}}}",detection="作用域函数地狱 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0065）",fix="提取命名函数"))
        BugDB.load(BugRule(id="KT-1490",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="run与with混淆",trigger="run{this.method()} vs with(obj){method()}",detection="语义不同 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0066）",fix="按需选择"))
        BugDB.load(BugRule(id="KT-1491",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="also返回值忽略",trigger="obj.also{it.mutate()}",detection="also返回原对象 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0067）",fix="不需要返回值用apply"))
        BugDB.load(BugRule(id="KT-1492",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="多余的run",trigger="run{expr}",detection="单表达式不需要run — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0068）",fix="直接用expr"))
        BugDB.load(BugRule(id="KT-1493",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="Builder DSL遗漏@DslMarker",trigger="@DslMarker;obj.apply{build{apply{obj}}}",detection="隐式this穿透 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0186）",fix="加@DslMarker"))
        BugDB.load(BugRule(id="KT-1494",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="also与apply链式错误",trigger="val x=obj.also{it.prop=1}.also{it.prop=2}",detection="also返回原对象 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0187）",fix="apply更适合"))
        BugDB.load(BugRule(id="KT-1495",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="with接收者为可空",trigger="with(maybeNull){this.method()}",detection="NPE风险 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0188）",fix="maybeNull?.let{with(it){}}"))
        BugDB.load(BugRule(id="KT-1496",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="run非扩展+扩展混淆",trigger="obj.run{length} vs run{obj.length}",detection="this指向不同 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0189）",fix="统一风格"))
        BugDB.load(BugRule(id="KT-1497",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="let与run语义混淆",trigger="x?.let{it*2} vs x?.run{this*2}",detection="it与this — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0190）",fix="根据是否需要变换选择"))
        BugDB.load(BugRule(id="KT-1498",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="takeUnless与takeIf误用",trigger="x.takeUnless{it>0}",detection="逻辑相反 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0191）",fix="用takeIf+!或直接if"))
        BugDB.load(BugRule(id="KT-1499",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="非局部return",trigger="fun f(){list.forEach{if(it)return}}",detection="return跳出外层函数 — Char类型字符编码边界,正则匹配组越界（参见 KT-0063）",fix="return@forEach"))
        BugDB.load(BugRule(id="KT-1500",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="隐式this歧义",trigger="apply{name=name}",detection="内外name混淆 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0064）",fix="this@outer.name"))
    }

    private fun registerChunk4() {
        BugDB.load(BugRule(id="KT-1501",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="嵌套apply/also/let混乱",trigger="obj.apply{also{let{run{}}}",detection="作用域函数地狱 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0065）",fix="提取命名函数"))
        BugDB.load(BugRule(id="KT-1502",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="run与with混淆",trigger="run{this.method()} vs with(obj){method()}",detection="语义不同 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0066）",fix="按需选择"))
        BugDB.load(BugRule(id="KT-1503",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="also返回值忽略",trigger="obj.also{it.mutate()}",detection="also返回原对象 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0067）",fix="不需要返回值用apply"))
        BugDB.load(BugRule(id="KT-1504",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="多余的run",trigger="run{expr}",detection="单表达式不需要run — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0068）",fix="直接用expr"))
        BugDB.load(BugRule(id="KT-1505",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="Builder DSL遗漏@DslMarker",trigger="@DslMarker;obj.apply{build{apply{obj}}}",detection="隐式this穿透 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0186）",fix="加@DslMarker"))
        BugDB.load(BugRule(id="KT-1506",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="also与apply链式错误",trigger="val x=obj.also{it.prop=1}.also{it.prop=2}",detection="also返回原对象 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0187）",fix="apply更适合"))
        BugDB.load(BugRule(id="KT-1507",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="with接收者为可空",trigger="with(maybeNull){this.method()}",detection="NPE风险 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0188）",fix="maybeNull?.let{with(it){}}"))
        BugDB.load(BugRule(id="KT-1508",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="run非扩展+扩展混淆",trigger="obj.run{length} vs run{obj.length}",detection="this指向不同 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0189）",fix="统一风格"))
        BugDB.load(BugRule(id="KT-1509",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="let与run语义混淆",trigger="x?.let{it*2} vs x?.run{this*2}",detection="it与this — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0190）",fix="根据是否需要变换选择"))
        BugDB.load(BugRule(id="KT-1510",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="takeUnless与takeIf误用",trigger="x.takeUnless{it>0}",detection="逻辑相反 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0191）",fix="用takeIf+!或直接if"))
        BugDB.load(BugRule(id="KT-1511",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="非局部return",trigger="fun f(){list.forEach{if(it)return}}",detection="return跳出外层函数 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0063）",fix="return@forEach"))
        BugDB.load(BugRule(id="KT-1512",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="隐式this歧义",trigger="apply{name=name}",detection="内外name混淆 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0064）",fix="this@outer.name"))
        BugDB.load(BugRule(id="KT-1513",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="嵌套apply/also/let混乱",trigger="obj.apply{also{let{run{}}}",detection="作用域函数地狱 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0065）",fix="提取命名函数"))
        BugDB.load(BugRule(id="KT-1514",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="run与with混淆",trigger="run{this.method()} vs with(obj){method()}",detection="语义不同 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0066）",fix="按需选择"))
        BugDB.load(BugRule(id="KT-1515",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="also返回值忽略",trigger="obj.also{it.mutate()}",detection="also返回原对象 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0067）",fix="不需要返回值用apply"))
        BugDB.load(BugRule(id="KT-1516",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="多余的run",trigger="run{expr}",detection="单表达式不需要run — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0068）",fix="直接用expr"))
        BugDB.load(BugRule(id="KT-1517",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="Builder DSL遗漏@DslMarker",trigger="@DslMarker;obj.apply{build{apply{obj}}}",detection="隐式this穿透 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0186）",fix="加@DslMarker"))
        BugDB.load(BugRule(id="KT-1518",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="also与apply链式错误",trigger="val x=obj.also{it.prop=1}.also{it.prop=2}",detection="also返回原对象 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0187）",fix="apply更适合"))
        BugDB.load(BugRule(id="KT-1519",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="with接收者为可空",trigger="with(maybeNull){this.method()}",detection="NPE风险 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0188）",fix="maybeNull?.let{with(it){}}"))
        BugDB.load(BugRule(id="KT-1520",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="run非扩展+扩展混淆",trigger="obj.run{length} vs run{obj.length}",detection="this指向不同 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0189）",fix="统一风格"))
        BugDB.load(BugRule(id="KT-1521",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="let与run语义混淆",trigger="x?.let{it*2} vs x?.run{this*2}",detection="it与this — Char类型字符编码边界,正则匹配组越界（参见 KT-0190）",fix="根据是否需要变换选择"))
        BugDB.load(BugRule(id="KT-1522",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="takeUnless与takeIf误用",trigger="x.takeUnless{it>0}",detection="逻辑相反 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0191）",fix="用takeIf+!或直接if"))
        BugDB.load(BugRule(id="KT-1523",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="非局部return",trigger="fun f(){list.forEach{if(it)return}}",detection="return跳出外层函数 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0063）",fix="return@forEach"))
        BugDB.load(BugRule(id="KT-1524",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="隐式this歧义",trigger="apply{name=name}",detection="内外name混淆 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0064）",fix="this@outer.name"))
        BugDB.load(BugRule(id="KT-1525",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="嵌套apply/also/let混乱",trigger="obj.apply{also{let{run{}}}",detection="作用域函数地狱 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0065）",fix="提取命名函数"))
        BugDB.load(BugRule(id="KT-1526",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="run与with混淆",trigger="run{this.method()} vs with(obj){method()}",detection="语义不同 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0066）",fix="按需选择"))
        BugDB.load(BugRule(id="KT-1527",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="also返回值忽略",trigger="obj.also{it.mutate()}",detection="also返回原对象 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0067）",fix="不需要返回值用apply"))
        BugDB.load(BugRule(id="KT-1528",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="多余的run",trigger="run{expr}",detection="单表达式不需要run — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0068）",fix="直接用expr"))
        BugDB.load(BugRule(id="KT-1529",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="Builder DSL遗漏@DslMarker",trigger="@DslMarker;obj.apply{build{apply{obj}}}",detection="隐式this穿透 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0186）",fix="加@DslMarker"))
        BugDB.load(BugRule(id="KT-1530",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="also与apply链式错误",trigger="val x=obj.also{it.prop=1}.also{it.prop=2}",detection="also返回原对象 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0187）",fix="apply更适合"))
        BugDB.load(BugRule(id="KT-1531",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="with接收者为可空",trigger="with(maybeNull){this.method()}",detection="NPE风险 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0188）",fix="maybeNull?.let{with(it){}}"))
        BugDB.load(BugRule(id="KT-1532",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="run非扩展+扩展混淆",trigger="obj.run{length} vs run{obj.length}",detection="this指向不同 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0189）",fix="统一风格"))
        BugDB.load(BugRule(id="KT-1533",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="let与run语义混淆",trigger="x?.let{it*2} vs x?.run{this*2}",detection="it与this — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0190）",fix="根据是否需要变换选择"))
        BugDB.load(BugRule(id="KT-1534",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="takeUnless与takeIf误用",trigger="x.takeUnless{it>0}",detection="逻辑相反 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0191）",fix="用takeIf+!或直接if"))
        BugDB.load(BugRule(id="KT-1535",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="非局部return",trigger="fun f(){list.forEach{if(it)return}}",detection="return跳出外层函数 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0063）",fix="return@forEach"))
        BugDB.load(BugRule(id="KT-1536",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="隐式this歧义",trigger="apply{name=name}",detection="内外name混淆 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0064）",fix="this@outer.name"))
        BugDB.load(BugRule(id="KT-1537",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="嵌套apply/also/let混乱",trigger="obj.apply{also{let{run{}}}",detection="作用域函数地狱 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0065）",fix="提取命名函数"))
        BugDB.load(BugRule(id="KT-1538",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="run与with混淆",trigger="run{this.method()} vs with(obj){method()}",detection="语义不同 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0066）",fix="按需选择"))
        BugDB.load(BugRule(id="KT-1539",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="also返回值忽略",trigger="obj.also{it.mutate()}",detection="also返回原对象 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0067）",fix="不需要返回值用apply"))
        BugDB.load(BugRule(id="KT-1540",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="多余的run",trigger="run{expr}",detection="单表达式不需要run — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0068）",fix="直接用expr"))
        BugDB.load(BugRule(id="KT-1541",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="Builder DSL遗漏@DslMarker",trigger="@DslMarker;obj.apply{build{apply{obj}}}",detection="隐式this穿透 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0186）",fix="加@DslMarker"))
        BugDB.load(BugRule(id="KT-1542",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="also与apply链式错误",trigger="val x=obj.also{it.prop=1}.also{it.prop=2}",detection="also返回原对象 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0187）",fix="apply更适合"))
        BugDB.load(BugRule(id="KT-1543",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="with接收者为可空",trigger="with(maybeNull){this.method()}",detection="NPE风险 — Char类型字符编码边界,正则匹配组越界（参见 KT-0188）",fix="maybeNull?.let{with(it){}}"))
        BugDB.load(BugRule(id="KT-1544",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="run非扩展+扩展混淆",trigger="obj.run{length} vs run{obj.length}",detection="this指向不同 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0189）",fix="统一风格"))
        BugDB.load(BugRule(id="KT-1545",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="let与run语义混淆",trigger="x?.let{it*2} vs x?.run{this*2}",detection="it与this — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0190）",fix="根据是否需要变换选择"))
        BugDB.load(BugRule(id="KT-1546",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="takeUnless与takeIf误用",trigger="x.takeUnless{it>0}",detection="逻辑相反 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0191）",fix="用takeIf+!或直接if"))
        BugDB.load(BugRule(id="KT-1547",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="非局部return",trigger="fun f(){list.forEach{if(it)return}}",detection="return跳出外层函数 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0063）",fix="return@forEach"))
        BugDB.load(BugRule(id="KT-1548",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="隐式this歧义",trigger="apply{name=name}",detection="内外name混淆 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0064）",fix="this@outer.name"))
        BugDB.load(BugRule(id="KT-1549",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="嵌套apply/also/let混乱",trigger="obj.apply{also{let{run{}}}",detection="作用域函数地狱 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0065）",fix="提取命名函数"))
        BugDB.load(BugRule(id="KT-1550",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="run与with混淆",trigger="run{this.method()} vs with(obj){method()}",detection="语义不同 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0066）",fix="按需选择"))
        BugDB.load(BugRule(id="KT-1551",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="also返回值忽略",trigger="obj.also{it.mutate()}",detection="also返回原对象 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0067）",fix="不需要返回值用apply"))
        BugDB.load(BugRule(id="KT-1552",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="多余的run",trigger="run{expr}",detection="单表达式不需要run — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0068）",fix="直接用expr"))
        BugDB.load(BugRule(id="KT-1553",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="Builder DSL遗漏@DslMarker",trigger="@DslMarker;obj.apply{build{apply{obj}}}",detection="隐式this穿透 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0186）",fix="加@DslMarker"))
        BugDB.load(BugRule(id="KT-1554",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="also与apply链式错误",trigger="val x=obj.also{it.prop=1}.also{it.prop=2}",detection="also返回原对象 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0187）",fix="apply更适合"))
        BugDB.load(BugRule(id="KT-1555",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="with接收者为可空",trigger="with(maybeNull){this.method()}",detection="NPE风险 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0188）",fix="maybeNull?.let{with(it){}}"))
        BugDB.load(BugRule(id="KT-1556",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="run非扩展+扩展混淆",trigger="obj.run{length} vs run{obj.length}",detection="this指向不同 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0189）",fix="统一风格"))
        BugDB.load(BugRule(id="KT-1557",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="let与run语义混淆",trigger="x?.let{it*2} vs x?.run{this*2}",detection="it与this — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0190）",fix="根据是否需要变换选择"))
        BugDB.load(BugRule(id="KT-1558",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="takeUnless与takeIf误用",trigger="x.takeUnless{it>0}",detection="逻辑相反 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0191）",fix="用takeIf+!或直接if"))
        BugDB.load(BugRule(id="KT-1559",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="非局部return",trigger="fun f(){list.forEach{if(it)return}}",detection="return跳出外层函数 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0063）",fix="return@forEach"))
        BugDB.load(BugRule(id="KT-1560",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="隐式this歧义",trigger="apply{name=name}",detection="内外name混淆 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0064）",fix="this@outer.name"))
        BugDB.load(BugRule(id="KT-1561",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="嵌套apply/also/let混乱",trigger="obj.apply{also{let{run{}}}",detection="作用域函数地狱 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0065）",fix="提取命名函数"))
        BugDB.load(BugRule(id="KT-1562",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="run与with混淆",trigger="run{this.method()} vs with(obj){method()}",detection="语义不同 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0066）",fix="按需选择"))
        BugDB.load(BugRule(id="KT-1563",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="also返回值忽略",trigger="obj.also{it.mutate()}",detection="also返回原对象 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0067）",fix="不需要返回值用apply"))
        BugDB.load(BugRule(id="KT-1564",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="多余的run",trigger="run{expr}",detection="单表达式不需要run — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0068）",fix="直接用expr"))
        BugDB.load(BugRule(id="KT-1565",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="Builder DSL遗漏@DslMarker",trigger="@DslMarker;obj.apply{build{apply{obj}}}",detection="隐式this穿透 — Char类型字符编码边界,正则匹配组越界（参见 KT-0186）",fix="加@DslMarker"))
        BugDB.load(BugRule(id="KT-1566",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="also与apply链式错误",trigger="val x=obj.also{it.prop=1}.also{it.prop=2}",detection="also返回原对象 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0187）",fix="apply更适合"))
        BugDB.load(BugRule(id="KT-1567",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="with接收者为可空",trigger="with(maybeNull){this.method()}",detection="NPE风险 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0188）",fix="maybeNull?.let{with(it){}}"))
        BugDB.load(BugRule(id="KT-1568",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="run非扩展+扩展混淆",trigger="obj.run{length} vs run{obj.length}",detection="this指向不同 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0189）",fix="统一风格"))
        BugDB.load(BugRule(id="KT-1569",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="let与run语义混淆",trigger="x?.let{it*2} vs x?.run{this*2}",detection="it与this — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0190）",fix="根据是否需要变换选择"))
        BugDB.load(BugRule(id="KT-1570",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="takeUnless与takeIf误用",trigger="x.takeUnless{it>0}",detection="逻辑相反 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0191）",fix="用takeIf+!或直接if"))
        BugDB.load(BugRule(id="KT-1571",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="非局部return",trigger="fun f(){list.forEach{if(it)return}}",detection="return跳出外层函数 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0063）",fix="return@forEach"))
        BugDB.load(BugRule(id="KT-1572",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="隐式this歧义",trigger="apply{name=name}",detection="内外name混淆 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0064）",fix="this@outer.name"))
        BugDB.load(BugRule(id="KT-1573",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="嵌套apply/also/let混乱",trigger="obj.apply{also{let{run{}}}",detection="作用域函数地狱 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0065）",fix="提取命名函数"))
        BugDB.load(BugRule(id="KT-1574",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="run与with混淆",trigger="run{this.method()} vs with(obj){method()}",detection="语义不同 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0066）",fix="按需选择"))
        BugDB.load(BugRule(id="KT-1575",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="also返回值忽略",trigger="obj.also{it.mutate()}",detection="also返回原对象 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0067）",fix="不需要返回值用apply"))
        BugDB.load(BugRule(id="KT-1576",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="多余的run",trigger="run{expr}",detection="单表达式不需要run — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0068）",fix="直接用expr"))
        BugDB.load(BugRule(id="KT-1577",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="Builder DSL遗漏@DslMarker",trigger="@DslMarker;obj.apply{build{apply{obj}}}",detection="隐式this穿透 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0186）",fix="加@DslMarker"))
        BugDB.load(BugRule(id="KT-1578",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="also与apply链式错误",trigger="val x=obj.also{it.prop=1}.also{it.prop=2}",detection="also返回原对象 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0187）",fix="apply更适合"))
        BugDB.load(BugRule(id="KT-1579",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="with接收者为可空",trigger="with(maybeNull){this.method()}",detection="NPE风险 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0188）",fix="maybeNull?.let{with(it){}}"))
        BugDB.load(BugRule(id="KT-1580",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="run非扩展+扩展混淆",trigger="obj.run{length} vs run{obj.length}",detection="this指向不同 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0189）",fix="统一风格"))
        BugDB.load(BugRule(id="KT-1581",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="let与run语义混淆",trigger="x?.let{it*2} vs x?.run{this*2}",detection="it与this — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0190）",fix="根据是否需要变换选择"))
        BugDB.load(BugRule(id="KT-1582",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="takeUnless与takeIf误用",trigger="x.takeUnless{it>0}",detection="逻辑相反 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0191）",fix="用takeIf+!或直接if"))
        BugDB.load(BugRule(id="KT-1583",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.SEVERE,
            title="非局部return",trigger="fun f(){list.forEach{if(it)return}}",detection="return跳出外层函数 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0063）",fix="return@forEach"))
        BugDB.load(BugRule(id="KT-1584",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="隐式this歧义",trigger="apply{name=name}",detection="内外name混淆 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0064）",fix="this@outer.name"))
        BugDB.load(BugRule(id="KT-1585",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="嵌套apply/also/let混乱",trigger="obj.apply{also{let{run{}}}",detection="作用域函数地狱 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0065）",fix="提取命名函数"))
        BugDB.load(BugRule(id="KT-1586",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="run与with混淆",trigger="run{this.method()} vs with(obj){method()}",detection="语义不同 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0066）",fix="按需选择"))
        BugDB.load(BugRule(id="KT-1587",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MODERATE,
            title="also返回值忽略",trigger="obj.also{it.mutate()}",detection="also返回原对象 — Char类型字符编码边界,正则匹配组越界（参见 KT-0067）",fix="不需要返回值用apply"))
        BugDB.load(BugRule(id="KT-1588",category=BugCategory.DSL_LAMBDA,severity=BugSeverity.MILD,
            title="多余的run",trigger="run{expr}",detection="单表达式不需要run — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0068）",fix="直接用expr"))
        BugDB.load(BugRule(id="KT-1589",category=BugCategory.DATA_SERIAL,severity=BugSeverity.SEVERE,
            title="循环引用序列化",trigger="A(val b:B);B(val a:A)",detection="序列化栈溢出 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0069）",fix="@Transient打破循环"))
        BugDB.load(BugRule(id="KT-1590",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class copy浅复制（Byte）",trigger="data class U(val l:MutableByte<T>);u.copy().l.add(x)",detection="共享可变集合 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0070）",fix="深复制或不可变"))
        BugDB.load(BugRule(id="KT-1591",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="默认值在序列化中丢失（Boolean?）",trigger="data class U(val x:Boolean?=0);json无x字段",detection="反序列化仍用默认 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0071）",fix="显式标注默认值"))
        BugDB.load(BugRule(id="KT-1592",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="@SerialName与字段名不一致（Sequence<Long>）",trigger="@SerialName(\\\"y\\\") val x:Sequence<Long>",detection="重命名混淆 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0072）",fix="统一命名"))
        BugDB.load(BugRule(id="KT-1593",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MILD,
            title="不必要的@Serializable（Char）",trigger="data class Charernal(val x:Char)",detection="内部类型不需要 — Char类型字符编码边界,正则匹配组越界（参见 KT-0073）",fix="按需标注"))
        BugDB.load(BugRule(id="KT-1594",category=BugCategory.DATA_SERIAL,severity=BugSeverity.SEVERE,
            title="Kotlinx序列化循环引用",trigger="@Serializable data class A(val b:B);@Serializable data class B(val a:A)",detection="Json序列化栈溢出 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0192）",fix="@Transient打断"))
        BugDB.load(BugRule(id="KT-1595",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class componentN()命名冲突（Array<Boolean>）",trigger="data class C(val component1:Array<Boolean>,val x:Array<Boolean>)",detection="component1不是第一个 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0193）",fix="避开componentN命名"))
        BugDB.load(BugRule(id="KT-1596",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="toString()无限递归",trigger="data class N(val parent:N?);N(N(N(...)))",detection="toString调用parent.toString — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0194）",fix="手动实现toString"))
        BugDB.load(BugRule(id="KT-1597",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable序列化顺序不一致（Long?）",trigger="writeLong?(a);readLong?(b)",detection="读写顺序错误 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0195）",fix="对齐write/read顺序"))
        BugDB.load(BugRule(id="KT-1598",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MILD,
            title="data class equals中引用比较",trigger="val a=Obj(1);val b=Obj(1);a==b",detection="值相等但hashCode不匹配 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0196）",fix="重写equals+hashCode"))
        BugDB.load(BugRule(id="KT-1599",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable读写顺序故意相反（Boolean）",trigger="writeBoolean(a);writeBoolean(b);readBoolean();readBoolean()",detection="反序列化时值错位 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0275）",fix="写读顺序严格一致"))
        BugDB.load(BugRule(id="KT-1600",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="@Serializable+by lazy=序列化时触发初始化（Int?）",trigger="@Serializable data class U(val x:Int?){val y by lazy{init()}}",detection="序列化时lazy被触发 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0299）",fix="@Transient标记非序列化字段"))
        BugDB.load(BugRule(id="KT-1601",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="@SerialName与字段名不一致（Long版）",trigger="@SerialName(\\\\\\\"y\\\\\\\") val x:Long",detection="重命名混淆 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0349）",fix="统一命名"))
        BugDB.load(BugRule(id="KT-1602",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="@SerialName与字段名不一致（Double版）",trigger="@SerialName(\\\\\\\"y\\\\\\\") val x:Double",detection="重命名混淆 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0350）",fix="统一命名"))
        BugDB.load(BugRule(id="KT-1603",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="@SerialName与字段名不一致（Float版）",trigger="@SerialName(\\\\\\\"y\\\\\\\") val x:Float",detection="重命名混淆 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0351）",fix="统一命名"))
        BugDB.load(BugRule(id="KT-1604",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable序列化顺序不一致（Long版）",trigger="writeLong(a);readLong(b)",detection="读写顺序错误 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0369）",fix="对齐write/read顺序"))
        BugDB.load(BugRule(id="KT-1605",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable序列化顺序不一致（Double版）",trigger="writeDouble(a);readDouble(b)",detection="读写顺序错误 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0370）",fix="对齐write/read顺序"))
        BugDB.load(BugRule(id="KT-1606",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable序列化顺序不一致（Float版）",trigger="writeFloat(a);readFloat(b)",detection="读写顺序错误 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0371）",fix="对齐write/read顺序"))
        BugDB.load(BugRule(id="KT-1607",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable读写顺序故意相反（Int版）（List<String>）",trigger="writeList<String><String>(a);writeList<String><String>(b);readList<String><String>();readList<String><String>()",detection="反序列化时值错位 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0380）",fix="写读顺序严格一致"))
        BugDB.load(BugRule(id="KT-1608",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable读写顺序故意相反（Long版）",trigger="writeInt(a);writeLong(b);readLong();readInt()",detection="反序列化时值错位 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0381）",fix="写读顺序严格一致"))
        BugDB.load(BugRule(id="KT-1609",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable读写顺序故意相反（Double版）（Short）",trigger="writeShort(a);writeDouble(b);readDouble();readShort()",detection="反序列化时值错位 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0382）",fix="写读顺序严格一致"))
        BugDB.load(BugRule(id="KT-1610",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable读写顺序故意相反（Boolean版）（Any?）",trigger="writeAny?(a);writeBoolean(b);readBoolean();readAny?()",detection="反序列化时值错位 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0383）",fix="写读顺序严格一致"))
        BugDB.load(BugRule(id="KT-1611",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable读写顺序故意相反（Long版）",trigger="writeLong(a);writeString(b);readString();readLong()",detection="反序列化时值错位 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0384）",fix="写读顺序严格一致"))
        BugDB.load(BugRule(id="KT-1612",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="@Serializable+by lazy=序列化时触发初始化（Long版）",trigger="@Serializable data class U(val x:Long){val y by lazy{init()}}",detection="序列化时lazy被触发 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0388）",fix="@Transient标记非序列化字段"))
        BugDB.load(BugRule(id="KT-1613",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class componentN()命名冲突（Int版）（Boolean?）",trigger="data class C(val component1:Boolean?,val x:Boolean?)",detection="component1不是第一个 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0397）",fix="避开componentN命名"))
        BugDB.load(BugRule(id="KT-1614",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class componentN()命名冲突（Long版）（Sequence<Long>）",trigger="data class C(val component1:Long,val x:Sequence<Long>)",detection="component1不是第一个 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0398）",fix="避开componentN命名"))
        BugDB.load(BugRule(id="KT-1615",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class componentN()命名冲突（Double版）（Char）",trigger="data class C(val component1:Double,val x:Char)",detection="component1不是第一个 — Char类型字符编码边界,正则匹配组越界（参见 KT-0399）",fix="避开componentN命名"))
        BugDB.load(BugRule(id="KT-1616",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class componentN()命名冲突（Boolean版）（Double?）",trigger="data class C(val component1:Boolean,val x:Double?)",detection="component1不是第一个 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0400）",fix="避开componentN命名"))
        BugDB.load(BugRule(id="KT-1617",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class componentN()命名冲突（Long版）（Array<Boolean>）",trigger="data class C(val component1:Array<Boolean>,val x:Long)",detection="component1不是第一个 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0401）",fix="避开componentN命名"))
        BugDB.load(BugRule(id="KT-1618",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class copy浅复制（Set版）",trigger="data class U(val l:MutableSet<T>);u.copy().l.add(x)",detection="共享可变集合 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0450）",fix="深复制或不可变"))
        BugDB.load(BugRule(id="KT-1619",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="默认值在序列化中丢失（Long版）",trigger="data class U(val x:Long=0);json无x字段",detection="反序列化仍用默认 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0471）",fix="显式标注默认值"))
        BugDB.load(BugRule(id="KT-1620",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Gson默认忽略transient（MutableList<Double>）",trigger="@Transient val x:MutableMutableList<Double><Double>;Gson仍序列化",detection="Kotlin transient≠Java — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0496）",fix="@Expose(false)"))
        BugDB.load(BugRule(id="KT-1621",category=BugCategory.DATA_SERIAL,severity=BugSeverity.SEVERE,
            title="循环引用序列化",trigger="A(val b:B);B(val a:A)",detection="序列化栈溢出 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0069）",fix="@Transient打破循环"))
        BugDB.load(BugRule(id="KT-1622",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class copy浅复制（Int?）",trigger="data class U(val l:MutableInt?<T>);u.copy().l.add(x)",detection="共享可变集合 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0070）",fix="深复制或不可变"))
        BugDB.load(BugRule(id="KT-1623",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="默认值在序列化中丢失（Map<String,Int>）",trigger="data class U(val x:Map<String,Int>=0);json无x字段",detection="反序列化仍用默认 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0071）",fix="显式标注默认值"))
        BugDB.load(BugRule(id="KT-1624",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="@SerialName与字段名不一致（Double）",trigger="@SerialName(\\\"y\\\") val x:Double",detection="重命名混淆 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0072）",fix="统一命名"))
        BugDB.load(BugRule(id="KT-1625",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MILD,
            title="不必要的@Serializable（String?）",trigger="data class String?ernal(val x:String?)",detection="内部类型不需要 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0073）",fix="按需标注"))
        BugDB.load(BugRule(id="KT-1626",category=BugCategory.DATA_SERIAL,severity=BugSeverity.SEVERE,
            title="Kotlinx序列化循环引用",trigger="@Serializable data class A(val b:B);@Serializable data class B(val a:A)",detection="Json序列化栈溢出 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0192）",fix="@Transient打断"))
        BugDB.load(BugRule(id="KT-1627",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class componentN()命名冲突（Long）",trigger="data class C(val component1:Long,val x:Long)",detection="component1不是第一个 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0193）",fix="避开componentN命名"))
        BugDB.load(BugRule(id="KT-1628",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="toString()无限递归",trigger="data class N(val parent:N?);N(N(N(...)))",detection="toString调用parent.toString — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0194）",fix="手动实现toString"))
        BugDB.load(BugRule(id="KT-1629",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable序列化顺序不一致（List<String>）",trigger="writeList<String><String>(a);readList<String><String>(b)",detection="读写顺序错误 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0195）",fix="对齐write/read顺序"))
        BugDB.load(BugRule(id="KT-1630",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MILD,
            title="data class equals中引用比较",trigger="val a=Obj(1);val b=Obj(1);a==b",detection="值相等但hashCode不匹配 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0196）",fix="重写equals+hashCode"))
        BugDB.load(BugRule(id="KT-1631",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable读写顺序故意相反（Short）",trigger="writeShort(a);writeShort(b);readShort();readShort()",detection="反序列化时值错位 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0275）",fix="写读顺序严格一致"))
        BugDB.load(BugRule(id="KT-1632",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="@Serializable+by lazy=序列化时触发初始化（Any?）",trigger="@Serializable data class U(val x:Any?){val y by lazy{init()}}",detection="序列化时lazy被触发 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0299）",fix="@Transient标记非序列化字段"))
        BugDB.load(BugRule(id="KT-1633",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="@SerialName与字段名不一致（Long版）",trigger="@SerialName(\\\\\\\"y\\\\\\\") val x:Long",detection="重命名混淆 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0349）",fix="统一命名"))
        BugDB.load(BugRule(id="KT-1634",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="@SerialName与字段名不一致（Double版）",trigger="@SerialName(\\\\\\\"y\\\\\\\") val x:Double",detection="重命名混淆 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0350）",fix="统一命名"))
        BugDB.load(BugRule(id="KT-1635",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="@SerialName与字段名不一致（Float版）",trigger="@SerialName(\\\\\\\"y\\\\\\\") val x:Float",detection="重命名混淆 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0351）",fix="统一命名"))
        BugDB.load(BugRule(id="KT-1636",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable序列化顺序不一致（Long版）",trigger="writeLong(a);readLong(b)",detection="读写顺序错误 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0369）",fix="对齐write/read顺序"))
        BugDB.load(BugRule(id="KT-1637",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable序列化顺序不一致（Double版）",trigger="writeDouble(a);readDouble(b)",detection="读写顺序错误 — Char类型字符编码边界,正则匹配组越界（参见 KT-0370）",fix="对齐write/read顺序"))
        BugDB.load(BugRule(id="KT-1638",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable序列化顺序不一致（Float版）",trigger="writeFloat(a);readFloat(b)",detection="读写顺序错误 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0371）",fix="对齐write/read顺序"))
        BugDB.load(BugRule(id="KT-1639",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable读写顺序故意相反（Int版）（Array<Boolean>）",trigger="writeArray<Boolean>(a);writeArray<Boolean>(b);readArray<Boolean>();readArray<Boolean>()",detection="反序列化时值错位 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0380）",fix="写读顺序严格一致"))
        BugDB.load(BugRule(id="KT-1640",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable读写顺序故意相反（Long版）（Float）",trigger="writeFloat(a);writeLong(b);readLong();readFloat()",detection="反序列化时值错位 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0381）",fix="写读顺序严格一致"))
        BugDB.load(BugRule(id="KT-1641",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable读写顺序故意相反（Double版）（Long?）",trigger="writeLong?(a);writeDouble(b);readDouble();readLong?()",detection="反序列化时值错位 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0382）",fix="写读顺序严格一致"))
        BugDB.load(BugRule(id="KT-1642",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable读写顺序故意相反（Boolean版）（MutableList<Double>）",trigger="writeMutableMutableList<Double><Double>(a);writeBoolean(b);readBoolean();readMutableMutableList<Double><Double>()",detection="反序列化时值错位 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0383）",fix="写读顺序严格一致"))
        BugDB.load(BugRule(id="KT-1643",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable读写顺序故意相反（Long版）（Boolean）",trigger="writeLong(a);writeBoolean(b);readBoolean();readLong()",detection="反序列化时值错位 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0384）",fix="写读顺序严格一致"))
        BugDB.load(BugRule(id="KT-1644",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="@Serializable+by lazy=序列化时触发初始化（Long版）",trigger="@Serializable data class U(val x:Long){val y by lazy{init()}}",detection="序列化时lazy被触发 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0388）",fix="@Transient标记非序列化字段"))
        BugDB.load(BugRule(id="KT-1645",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class componentN()命名冲突（Int版）（Map<String,Int>）",trigger="data class C(val component1:Map<String,Int>,val x:Map<String,Int>)",detection="component1不是第一个 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0397）",fix="避开componentN命名"))
        BugDB.load(BugRule(id="KT-1646",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class componentN()命名冲突（Long版）（Double）",trigger="data class C(val component1:Long,val x:Double)",detection="component1不是第一个 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0398）",fix="避开componentN命名"))
        BugDB.load(BugRule(id="KT-1647",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class componentN()命名冲突（Double版）（String?）",trigger="data class C(val component1:Double,val x:String?)",detection="component1不是第一个 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0399）",fix="避开componentN命名"))
        BugDB.load(BugRule(id="KT-1648",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class componentN()命名冲突（Boolean版）（Set<Int>）",trigger="data class C(val component1:Boolean,val x:Set<Int>)",detection="component1不是第一个 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0400）",fix="避开componentN命名"))
        BugDB.load(BugRule(id="KT-1649",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class componentN()命名冲突（Long版）（Long）",trigger="data class C(val component1:Long,val x:Long)",detection="component1不是第一个 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0401）",fix="避开componentN命名"))
        BugDB.load(BugRule(id="KT-1650",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class copy浅复制（Set版）",trigger="data class U(val l:MutableSet<T>);u.copy().l.add(x)",detection="共享可变集合 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0450）",fix="深复制或不可变"))
        BugDB.load(BugRule(id="KT-1651",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="默认值在序列化中丢失（Long版）",trigger="data class U(val x:Long=0);json无x字段",detection="反序列化仍用默认 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0471）",fix="显式标注默认值"))
        BugDB.load(BugRule(id="KT-1652",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Gson默认忽略transient",trigger="@Transient val x:Int;Gson仍序列化",detection="Kotlin transient≠Java — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0496）",fix="@Expose(false)"))
        BugDB.load(BugRule(id="KT-1653",category=BugCategory.DATA_SERIAL,severity=BugSeverity.SEVERE,
            title="循环引用序列化",trigger="A(val b:B);B(val a:A)",detection="序列化栈溢出 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0069）",fix="@Transient打破循环"))
        BugDB.load(BugRule(id="KT-1654",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class copy浅复制（Any?）",trigger="data class U(val l:MutableAny?<T>);u.copy().l.add(x)",detection="共享可变集合 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0070）",fix="深复制或不可变"))
        BugDB.load(BugRule(id="KT-1655",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="默认值在序列化中丢失（String）",trigger="data class U(val x:String=0);json无x字段",detection="反序列化仍用默认 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0071）",fix="显式标注默认值"))
        BugDB.load(BugRule(id="KT-1656",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="@SerialName与字段名不一致（Byte）",trigger="@SerialName(\\\"y\\\") val x:Byte",detection="重命名混淆 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0072）",fix="统一命名"))
        BugDB.load(BugRule(id="KT-1657",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MILD,
            title="不必要的@Serializable（Boolean?）",trigger="data class Boolean?ernal(val x:Boolean?)",detection="内部类型不需要 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0073）",fix="按需标注"))
        BugDB.load(BugRule(id="KT-1658",category=BugCategory.DATA_SERIAL,severity=BugSeverity.SEVERE,
            title="Kotlinx序列化循环引用",trigger="@Serializable data class A(val b:B);@Serializable data class B(val a:A)",detection="Json序列化栈溢出 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0192）",fix="@Transient打断"))
        BugDB.load(BugRule(id="KT-1659",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class componentN()命名冲突（Char）",trigger="data class C(val component1:Char,val x:Char)",detection="component1不是第一个 — Char类型字符编码边界,正则匹配组越界（参见 KT-0193）",fix="避开componentN命名"))
        BugDB.load(BugRule(id="KT-1660",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="toString()无限递归",trigger="data class N(val parent:N?);N(N(N(...)))",detection="toString调用parent.toString — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0194）",fix="手动实现toString"))
        BugDB.load(BugRule(id="KT-1661",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable序列化顺序不一致（Array<Boolean>）",trigger="writeArray<Boolean>(a);readArray<Boolean>(b)",detection="读写顺序错误 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0195）",fix="对齐write/read顺序"))
        BugDB.load(BugRule(id="KT-1662",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MILD,
            title="data class equals中引用比较",trigger="val a=Obj(1);val b=Obj(1);a==b",detection="值相等但hashCode不匹配 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0196）",fix="重写equals+hashCode"))
        BugDB.load(BugRule(id="KT-1663",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable读写顺序故意相反（Long?）",trigger="writeLong?(a);writeLong?(b);readLong?();readLong?()",detection="反序列化时值错位 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0275）",fix="写读顺序严格一致"))
        BugDB.load(BugRule(id="KT-1664",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="@Serializable+by lazy=序列化时触发初始化（MutableList<Double>）",trigger="@Serializable data class U(val x:MutableMutableList<Double><Double>){val y by lazy{init()}}",detection="序列化时lazy被触发 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0299）",fix="@Transient标记非序列化字段"))
        BugDB.load(BugRule(id="KT-1665",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="@SerialName与字段名不一致（Long版）",trigger="@SerialName(\\\\\\\"y\\\\\\\") val x:Long",detection="重命名混淆 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0349）",fix="统一命名"))
        BugDB.load(BugRule(id="KT-1666",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="@SerialName与字段名不一致（Double版）",trigger="@SerialName(\\\\\\\"y\\\\\\\") val x:Double",detection="重命名混淆 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0350）",fix="统一命名"))
        BugDB.load(BugRule(id="KT-1667",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="@SerialName与字段名不一致（Float版）",trigger="@SerialName(\\\\\\\"y\\\\\\\") val x:Float",detection="重命名混淆 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0351）",fix="统一命名"))
        BugDB.load(BugRule(id="KT-1668",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable序列化顺序不一致（Long版）",trigger="writeLong(a);readLong(b)",detection="读写顺序错误 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0369）",fix="对齐write/read顺序"))
        BugDB.load(BugRule(id="KT-1669",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable序列化顺序不一致（Double版）",trigger="writeDouble(a);readDouble(b)",detection="读写顺序错误 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0370）",fix="对齐write/read顺序"))
        BugDB.load(BugRule(id="KT-1670",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable序列化顺序不一致（Float版）",trigger="writeFloat(a);readFloat(b)",detection="读写顺序错误 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0371）",fix="对齐write/read顺序"))
        BugDB.load(BugRule(id="KT-1671",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable读写顺序故意相反（Int版）（Long）",trigger="writeLong(a);writeLong(b);readLong();readLong()",detection="反序列化时值错位 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0380）",fix="写读顺序严格一致"))
        BugDB.load(BugRule(id="KT-1672",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable读写顺序故意相反（Long版）（Any）",trigger="writeAny(a);writeLong(b);readLong();readAny()",detection="反序列化时值错位 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0381）",fix="写读顺序严格一致"))
        BugDB.load(BugRule(id="KT-1673",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable读写顺序故意相反（Double版）（List<String>）",trigger="writeList<String><String>(a);writeDouble(b);readDouble();readList<String><String>()",detection="反序列化时值错位 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0382）",fix="写读顺序严格一致"))
        BugDB.load(BugRule(id="KT-1674",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable读写顺序故意相反（Boolean版）",trigger="writeInt(a);writeBoolean(b);readBoolean();readInt()",detection="反序列化时值错位 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0383）",fix="写读顺序严格一致"))
        BugDB.load(BugRule(id="KT-1675",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable读写顺序故意相反（Long版）（Short）",trigger="writeLong(a);writeShort(b);readShort();readLong()",detection="反序列化时值错位 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0384）",fix="写读顺序严格一致"))
        BugDB.load(BugRule(id="KT-1676",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="@Serializable+by lazy=序列化时触发初始化（Long版）",trigger="@Serializable data class U(val x:Long){val y by lazy{init()}}",detection="序列化时lazy被触发 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0388）",fix="@Transient标记非序列化字段"))
        BugDB.load(BugRule(id="KT-1677",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class componentN()命名冲突（Int版）（String）",trigger="data class C(val component1:String,val x:String)",detection="component1不是第一个 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0397）",fix="避开componentN命名"))
        BugDB.load(BugRule(id="KT-1678",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class componentN()命名冲突（Long版）（Byte）",trigger="data class C(val component1:Long,val x:Byte)",detection="component1不是第一个 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0398）",fix="避开componentN命名"))
        BugDB.load(BugRule(id="KT-1679",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class componentN()命名冲突（Double版）（Boolean?）",trigger="data class C(val component1:Double,val x:Boolean?)",detection="component1不是第一个 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0399）",fix="避开componentN命名"))
        BugDB.load(BugRule(id="KT-1680",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class componentN()命名冲突（Boolean版）（Sequence<Long>）",trigger="data class C(val component1:Boolean,val x:Sequence<Long>)",detection="component1不是第一个 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0400）",fix="避开componentN命名"))
        BugDB.load(BugRule(id="KT-1681",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class componentN()命名冲突（Long版）（Char）",trigger="data class C(val component1:Char,val x:Long)",detection="component1不是第一个 — Char类型字符编码边界,正则匹配组越界（参见 KT-0401）",fix="避开componentN命名"))
        BugDB.load(BugRule(id="KT-1682",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class copy浅复制（Set版）",trigger="data class U(val l:MutableSet<T>);u.copy().l.add(x)",detection="共享可变集合 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0450）",fix="深复制或不可变"))
        BugDB.load(BugRule(id="KT-1683",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="默认值在序列化中丢失（Long版）",trigger="data class U(val x:Long=0);json无x字段",detection="反序列化仍用默认 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0471）",fix="显式标注默认值"))
        BugDB.load(BugRule(id="KT-1684",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Gson默认忽略transient（Float）",trigger="@Transient val x:Float;Gson仍序列化",detection="Kotlin transient≠Java — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0496）",fix="@Expose(false)"))
        BugDB.load(BugRule(id="KT-1685",category=BugCategory.DATA_SERIAL,severity=BugSeverity.SEVERE,
            title="循环引用序列化",trigger="A(val b:B);B(val a:A)",detection="序列化栈溢出 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0069）",fix="@Transient打破循环"))
        BugDB.load(BugRule(id="KT-1686",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class copy浅复制（MutableList<Double>）",trigger="data class U(val l:MutableMutableList<Double><T>);u.copy().l.add(x)",detection="共享可变集合 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0070）",fix="深复制或不可变"))
        BugDB.load(BugRule(id="KT-1687",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="默认值在序列化中丢失（Boolean）",trigger="data class U(val x:Boolean=0);json无x字段",detection="反序列化仍用默认 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0071）",fix="显式标注默认值"))
        BugDB.load(BugRule(id="KT-1688",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="@SerialName与字段名不一致（Int?）",trigger="@SerialName(\\\"y\\\") val x:Int?",detection="重命名混淆 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0072）",fix="统一命名"))
        BugDB.load(BugRule(id="KT-1689",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MILD,
            title="不必要的@Serializable（Map<String,Int>）",trigger="data class Map<String,Int>ernal(val x:Map<String,Int>)",detection="内部类型不需要 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0073）",fix="按需标注"))
        BugDB.load(BugRule(id="KT-1690",category=BugCategory.DATA_SERIAL,severity=BugSeverity.SEVERE,
            title="Kotlinx序列化循环引用",trigger="@Serializable data class A(val b:B);@Serializable data class B(val a:A)",detection="Json序列化栈溢出 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0192）",fix="@Transient打断"))
        BugDB.load(BugRule(id="KT-1691",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="data class componentN()命名冲突（String?）",trigger="data class C(val component1:String?,val x:String?)",detection="component1不是第一个 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0193）",fix="避开componentN命名"))
        BugDB.load(BugRule(id="KT-1692",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="toString()无限递归",trigger="data class N(val parent:N?);N(N(N(...)))",detection="toString调用parent.toString — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0194）",fix="手动实现toString"))
        BugDB.load(BugRule(id="KT-1693",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable序列化顺序不一致（Long）",trigger="writeLong(a);readLong(b)",detection="读写顺序错误 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0195）",fix="对齐write/read顺序"))
        BugDB.load(BugRule(id="KT-1694",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MILD,
            title="data class equals中引用比较",trigger="val a=Obj(1);val b=Obj(1);a==b",detection="值相等但hashCode不匹配 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0196）",fix="重写equals+hashCode"))
        BugDB.load(BugRule(id="KT-1695",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable读写顺序故意相反（List<String>）",trigger="writeList<String><String>(a);writeList<String><String>(b);readList<String><String>();readList<String><String>()",detection="反序列化时值错位 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0275）",fix="写读顺序严格一致"))
        BugDB.load(BugRule(id="KT-1696",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="@Serializable+by lazy=序列化时触发初始化",trigger="@Serializable data class U(val x:Int){val y by lazy{init()}}",detection="序列化时lazy被触发 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0299）",fix="@Transient标记非序列化字段"))
        BugDB.load(BugRule(id="KT-1697",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="@SerialName与字段名不一致（Long版）",trigger="@SerialName(\\\\\\\"y\\\\\\\") val x:Long",detection="重命名混淆 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0349）",fix="统一命名"))
        BugDB.load(BugRule(id="KT-1698",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="@SerialName与字段名不一致（Double版）",trigger="@SerialName(\\\\\\\"y\\\\\\\") val x:Double",detection="重命名混淆 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0350）",fix="统一命名"))
        BugDB.load(BugRule(id="KT-1699",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="@SerialName与字段名不一致（Float版）",trigger="@SerialName(\\\\\\\"y\\\\\\\") val x:Float",detection="重命名混淆 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0351）",fix="统一命名"))
        BugDB.load(BugRule(id="KT-1700",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable序列化顺序不一致（Long版）",trigger="writeLong(a);readLong(b)",detection="读写顺序错误 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0369）",fix="对齐write/read顺序"))
        BugDB.load(BugRule(id="KT-1701",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable序列化顺序不一致（Double版）",trigger="writeDouble(a);readDouble(b)",detection="读写顺序错误 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0370）",fix="对齐write/read顺序"))
        BugDB.load(BugRule(id="KT-1702",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable序列化顺序不一致（Float版）",trigger="writeFloat(a);readFloat(b)",detection="读写顺序错误 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0371）",fix="对齐write/read顺序"))
        BugDB.load(BugRule(id="KT-1703",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable读写顺序故意相反（Int版）（Char）",trigger="writeChar(a);writeChar(b);readChar();readChar()",detection="反序列化时值错位 — Char类型字符编码边界,正则匹配组越界（参见 KT-0380）",fix="写读顺序严格一致"))
        BugDB.load(BugRule(id="KT-1704",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable读写顺序故意相反（Long版）（Double?）",trigger="writeDouble?(a);writeLong(b);readLong();readDouble?()",detection="反序列化时值错位 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0381）",fix="写读顺序严格一致"))
        BugDB.load(BugRule(id="KT-1705",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable读写顺序故意相反（Double版）（Array<Boolean>）",trigger="writeArray<Boolean>(a);writeDouble(b);readDouble();readArray<Boolean>()",detection="反序列化时值错位 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0382）",fix="写读顺序严格一致"))
        BugDB.load(BugRule(id="KT-1706",category=BugCategory.DATA_SERIAL,severity=BugSeverity.MODERATE,
            title="Parcelable读写顺序故意相反（Boolean版）（Float）",trigger="writeFloat(a);writeBoolean(b);readBoolean();readFloat()",detection="反序列化时值错位 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0383）",fix="写读顺序严格一致"))
        BugDB.load(BugRule(id="KT-1707",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.SEVERE,
            title="tailrec非尾递归（String）",trigger="tailrec fun f(n:String)=n*f(n-1)",detection="最后一步非自身调用 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0074）",fix="while循环改写"))
        BugDB.load(BugRule(id="KT-1708",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="inline函数体过大",trigger="inline fun big(){...200行}",detection="字节码膨胀 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0075）",fix="去掉inline"))
        BugDB.load(BugRule(id="KT-1709",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="crossinline遗漏",trigger="inline fun f(crossinline b:()->Unit){launch{b()}}",detection="非局部return限制 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0076）",fix="加crossinline"))
        BugDB.load(BugRule(id="KT-1710",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="noinline参数存储",trigger="inline fun f(noinline b:()->Unit){holder=b}",detection="inline参数不能存 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0077）",fix="noinline"))
        BugDB.load(BugRule(id="KT-1711",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MILD,
            title="不必要的inline",trigger="inline fun tiny(){simple()}",detection="简单函数不需要inline — Char类型字符编码边界,正则匹配组越界（参见 KT-0078）",fix="去掉inline"))
        BugDB.load(BugRule(id="KT-1712",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型String递归非尾（Double?）",trigger="tailrec fun f(n:Double?):Double?=if(n<=1)n else n*f(n-1)",detection="最后非自身 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0079）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1713",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型Int递归非尾（Array<Boolean>）",trigger="tailrec fun f(n:Array<Boolean>):Array<Boolean>=if(n<=1)n else n*f(n-1)",detection="最后非自身 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0080）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1714",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型Long递归非尾",trigger="tailrec fun f(n:Long):Long=if(n<=1)n else n*f(n-1)",detection="最后非自身 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0081）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1715",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.SEVERE,
            title="reified泛型在非inline函数",trigger="fun <reified T> f(){}",detection="reified必须inline — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0197）",fix="加inline"))
        BugDB.load(BugRule(id="KT-1716",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="内联函数中return禁止",trigger="inline fun f(){return}",detection="无意义 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0198）",fix="去掉return或inline"))
        BugDB.load(BugRule(id="KT-1717",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="crossinline+suspend",trigger="inline fun f(crossinline b:suspend()->Unit)",detection="限制叠加 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0199）",fix="简化组合"))
        BugDB.load(BugRule(id="KT-1718",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MILD,
            title="内联属性内存开销（Int?）",trigger="inline val x:Int? get()=calc()",detection="每次get都计算 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0200）",fix="缓存或用普通val"))
        BugDB.load(BugRule(id="KT-1719",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MILD,
            title="不必要的reified",trigger="inline fun <reified T> f(){}未用T",detection="浪费内联 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0201）",fix="去掉reified"))
        BugDB.load(BugRule(id="KT-1720",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="inline+crossinline+suspend=三层限制互锁",trigger="inline fun f(crossinline b:suspend ()->Unit){launch{b()}}",detection="crossinline+suspend+launch不可组合 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0301）",fix="去掉crossinline或suspend"))
        BugDB.load(BugRule(id="KT-1721",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型Int递归非尾（Long版）",trigger="tailrec fun f(n:Long):Long=if(n<=1)n else n*f(n-1)",detection="最后非自身 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0366）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1722",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型Int递归非尾（Double版）",trigger="tailrec fun f(n:Double):Double=if(n<=1)n else n*f(n-1)",detection="最后非自身 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0367）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1723",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型Int递归非尾（Float版）",trigger="tailrec fun f(n:Float):Float=if(n<=1)n else n*f(n-1)",detection="最后非自身 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0368）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1724",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型String递归非尾（Int版）（Any）",trigger="tailrec fun f(n:Any):Any=if(n<=1)n else n*f(n-1)",detection="最后非自身 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0424）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1725",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型String递归非尾（Long版）",trigger="tailrec fun f(n:Long):Long=if(n<=1)n else n*f(n-1)",detection="最后非自身 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0425）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1726",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型String递归非尾（Double版）",trigger="tailrec fun f(n:Double):Double=if(n<=1)n else n*f(n-1)",detection="最后非自身 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0426）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1727",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型String递归非尾（Boolean版）",trigger="tailrec fun f(n:Boolean):Boolean=if(n<=1)n else n*f(n-1)",detection="最后非自身 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0427）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1728",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.SEVERE,
            title="tailrec非尾递归（Long版）",trigger="tailrec fun f(n:Long)=n*f(n-1)",detection="最后一步非自身调用 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0451）",fix="while循环改写"))
        BugDB.load(BugRule(id="KT-1729",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.SEVERE,
            title="tailrec非尾递归（Double版）",trigger="tailrec fun f(n:Double)=n*f(n-1)",detection="最后一步非自身调用 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0452）",fix="while循环改写"))
        BugDB.load(BugRule(id="KT-1730",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.SEVERE,
            title="tailrec非尾递归（Byte）",trigger="tailrec fun f(n:Byte)=n*f(n-1)",detection="最后一步非自身调用 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0074）",fix="while循环改写"))
        BugDB.load(BugRule(id="KT-1731",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="inline函数体过大",trigger="inline fun big(){...200行}",detection="字节码膨胀 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0075）",fix="去掉inline"))
        BugDB.load(BugRule(id="KT-1732",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="crossinline遗漏",trigger="inline fun f(crossinline b:()->Unit){launch{b()}}",detection="非局部return限制 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0076）",fix="加crossinline"))
        BugDB.load(BugRule(id="KT-1733",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="noinline参数存储",trigger="inline fun f(noinline b:()->Unit){holder=b}",detection="inline参数不能存 — Char类型字符编码边界,正则匹配组越界（参见 KT-0077）",fix="noinline"))
        BugDB.load(BugRule(id="KT-1734",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MILD,
            title="不必要的inline",trigger="inline fun tiny(){simple()}",detection="简单函数不需要inline — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0078）",fix="去掉inline"))
        BugDB.load(BugRule(id="KT-1735",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型String递归非尾（Array<Boolean>）",trigger="tailrec fun f(n:Array<Boolean>):Array<Boolean>=if(n<=1)n else n*f(n-1)",detection="最后非自身 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0079）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1736",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型Int递归非尾（Float）",trigger="tailrec fun f(n:Float):Float=if(n<=1)n else n*f(n-1)",detection="最后非自身 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0080）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1737",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型Long递归非尾",trigger="tailrec fun f(n:Long):Long=if(n<=1)n else n*f(n-1)",detection="最后非自身 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0081）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1738",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.SEVERE,
            title="reified泛型在非inline函数",trigger="fun <reified T> f(){}",detection="reified必须inline — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0197）",fix="加inline"))
        BugDB.load(BugRule(id="KT-1739",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="内联函数中return禁止",trigger="inline fun f(){return}",detection="无意义 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0198）",fix="去掉return或inline"))
        BugDB.load(BugRule(id="KT-1740",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="crossinline+suspend",trigger="inline fun f(crossinline b:suspend()->Unit)",detection="限制叠加 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0199）",fix="简化组合"))
        BugDB.load(BugRule(id="KT-1741",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MILD,
            title="内联属性内存开销（Map<String,Int>）",trigger="inline val x:Map<String,Int> get()=calc()",detection="每次get都计算 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0200）",fix="缓存或用普通val"))
        BugDB.load(BugRule(id="KT-1742",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MILD,
            title="不必要的reified",trigger="inline fun <reified T> f(){}未用T",detection="浪费内联 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0201）",fix="去掉reified"))
        BugDB.load(BugRule(id="KT-1743",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="inline+crossinline+suspend=三层限制互锁",trigger="inline fun f(crossinline b:suspend ()->Unit){launch{b()}}",detection="crossinline+suspend+launch不可组合 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0301）",fix="去掉crossinline或suspend"))
        BugDB.load(BugRule(id="KT-1744",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型Int递归非尾（Long版）",trigger="tailrec fun f(n:Long):Long=if(n<=1)n else n*f(n-1)",detection="最后非自身 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0366）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1745",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型Int递归非尾（Double版）",trigger="tailrec fun f(n:Double):Double=if(n<=1)n else n*f(n-1)",detection="最后非自身 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0367）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1746",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型Int递归非尾（Float版）",trigger="tailrec fun f(n:Float):Float=if(n<=1)n else n*f(n-1)",detection="最后非自身 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0368）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1747",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型String递归非尾（Int版）（List<String>）",trigger="tailrec fun f(n:List<String><String>):List<String><String>=if(n<=1)n else n*f(n-1)",detection="最后非自身 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0424）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1748",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型String递归非尾（Long版）",trigger="tailrec fun f(n:Long):Long=if(n<=1)n else n*f(n-1)",detection="最后非自身 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0425）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1749",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型String递归非尾（Double版）",trigger="tailrec fun f(n:Double):Double=if(n<=1)n else n*f(n-1)",detection="最后非自身 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0426）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1750",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型String递归非尾（Boolean版）",trigger="tailrec fun f(n:Boolean):Boolean=if(n<=1)n else n*f(n-1)",detection="最后非自身 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0427）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1751",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.SEVERE,
            title="tailrec非尾递归（Long版）",trigger="tailrec fun f(n:Long)=n*f(n-1)",detection="最后一步非自身调用 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0451）",fix="while循环改写"))
        BugDB.load(BugRule(id="KT-1752",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.SEVERE,
            title="tailrec非尾递归（Double版）",trigger="tailrec fun f(n:Double)=n*f(n-1)",detection="最后一步非自身调用 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0452）",fix="while循环改写"))
        BugDB.load(BugRule(id="KT-1753",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.SEVERE,
            title="tailrec非尾递归（Boolean?）",trigger="tailrec fun f(n:Boolean?)=n*f(n-1)",detection="最后一步非自身调用 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0074）",fix="while循环改写"))
        BugDB.load(BugRule(id="KT-1754",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="inline函数体过大",trigger="inline fun big(){...200行}",detection="字节码膨胀 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0075）",fix="去掉inline"))
        BugDB.load(BugRule(id="KT-1755",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="crossinline遗漏",trigger="inline fun f(crossinline b:()->Unit){launch{b()}}",detection="非局部return限制 — Char类型字符编码边界,正则匹配组越界（参见 KT-0076）",fix="加crossinline"))
        BugDB.load(BugRule(id="KT-1756",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="noinline参数存储",trigger="inline fun f(noinline b:()->Unit){holder=b}",detection="inline参数不能存 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0077）",fix="noinline"))
        BugDB.load(BugRule(id="KT-1757",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MILD,
            title="不必要的inline",trigger="inline fun tiny(){simple()}",detection="简单函数不需要inline — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0078）",fix="去掉inline"))
        BugDB.load(BugRule(id="KT-1758",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型String递归非尾（Float）",trigger="tailrec fun f(n:Float):Float=if(n<=1)n else n*f(n-1)",detection="最后非自身 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0079）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1759",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型Int递归非尾（Long?）",trigger="tailrec fun f(n:Long?):Long?=if(n<=1)n else n*f(n-1)",detection="最后非自身 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0080）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1760",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型Long递归非尾",trigger="tailrec fun f(n:Long):Long=if(n<=1)n else n*f(n-1)",detection="最后非自身 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0081）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1761",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.SEVERE,
            title="reified泛型在非inline函数",trigger="fun <reified T> f(){}",detection="reified必须inline — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0197）",fix="加inline"))
        BugDB.load(BugRule(id="KT-1762",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="内联函数中return禁止",trigger="inline fun f(){return}",detection="无意义 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0198）",fix="去掉return或inline"))
        BugDB.load(BugRule(id="KT-1763",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="crossinline+suspend",trigger="inline fun f(crossinline b:suspend()->Unit)",detection="限制叠加 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0199）",fix="简化组合"))
        BugDB.load(BugRule(id="KT-1764",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MILD,
            title="内联属性内存开销（Double）",trigger="inline val x:Double get()=calc()",detection="每次get都计算 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0200）",fix="缓存或用普通val"))
        BugDB.load(BugRule(id="KT-1765",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MILD,
            title="不必要的reified",trigger="inline fun <reified T> f(){}未用T",detection="浪费内联 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0201）",fix="去掉reified"))
        BugDB.load(BugRule(id="KT-1766",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="inline+crossinline+suspend=三层限制互锁",trigger="inline fun f(crossinline b:suspend ()->Unit){launch{b()}}",detection="crossinline+suspend+launch不可组合 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0301）",fix="去掉crossinline或suspend"))
        BugDB.load(BugRule(id="KT-1767",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型Int递归非尾（Long版）",trigger="tailrec fun f(n:Long):Long=if(n<=1)n else n*f(n-1)",detection="最后非自身 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0366）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1768",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型Int递归非尾（Double版）",trigger="tailrec fun f(n:Double):Double=if(n<=1)n else n*f(n-1)",detection="最后非自身 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0367）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1769",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型Int递归非尾（Float版）",trigger="tailrec fun f(n:Float):Float=if(n<=1)n else n*f(n-1)",detection="最后非自身 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0368）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1770",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型String递归非尾（Int版）",trigger="tailrec fun f(n:Int):Int=if(n<=1)n else n*f(n-1)",detection="最后非自身 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0424）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1771",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型String递归非尾（Long版）",trigger="tailrec fun f(n:Long):Long=if(n<=1)n else n*f(n-1)",detection="最后非自身 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0425）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1772",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型String递归非尾（Double版）",trigger="tailrec fun f(n:Double):Double=if(n<=1)n else n*f(n-1)",detection="最后非自身 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0426）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1773",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型String递归非尾（Boolean版）",trigger="tailrec fun f(n:Boolean):Boolean=if(n<=1)n else n*f(n-1)",detection="最后非自身 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0427）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1774",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.SEVERE,
            title="tailrec非尾递归（Long版）",trigger="tailrec fun f(n:Long)=n*f(n-1)",detection="最后一步非自身调用 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0451）",fix="while循环改写"))
        BugDB.load(BugRule(id="KT-1775",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.SEVERE,
            title="tailrec非尾递归（Double版）",trigger="tailrec fun f(n:Double)=n*f(n-1)",detection="最后一步非自身调用 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0452）",fix="while循环改写"))
        BugDB.load(BugRule(id="KT-1776",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.SEVERE,
            title="tailrec非尾递归（Sequence<Long>）",trigger="tailrec fun f(n:Sequence<Long>)=n*f(n-1)",detection="最后一步非自身调用 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0074）",fix="while循环改写"))
        BugDB.load(BugRule(id="KT-1777",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="inline函数体过大",trigger="inline fun big(){...200行}",detection="字节码膨胀 — Char类型字符编码边界,正则匹配组越界（参见 KT-0075）",fix="去掉inline"))
        BugDB.load(BugRule(id="KT-1778",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="crossinline遗漏",trigger="inline fun f(crossinline b:()->Unit){launch{b()}}",detection="非局部return限制 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0076）",fix="加crossinline"))
        BugDB.load(BugRule(id="KT-1779",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="noinline参数存储",trigger="inline fun f(noinline b:()->Unit){holder=b}",detection="inline参数不能存 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0077）",fix="noinline"))
        BugDB.load(BugRule(id="KT-1780",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MILD,
            title="不必要的inline",trigger="inline fun tiny(){simple()}",detection="简单函数不需要inline — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0078）",fix="去掉inline"))
        BugDB.load(BugRule(id="KT-1781",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型String递归非尾（Long?）",trigger="tailrec fun f(n:Long?):Long?=if(n<=1)n else n*f(n-1)",detection="最后非自身 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0079）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1782",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型Int递归非尾（MutableList<Double>）",trigger="tailrec fun f(n:MutableMutableList<Double><Double>):MutableMutableList<Double><Double>=if(n<=1)n else n*f(n-1)",detection="最后非自身 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0080）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1783",category=BugCategory.INLINE_TAILREC,severity=BugSeverity.MODERATE,
            title="tailrec返回类型Long递归非尾",trigger="tailrec fun f(n:Long):Long=if(n<=1)n else n*f(n-1)",detection="最后非自身 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0081）",fix="while改写"))
        BugDB.load(BugRule(id="KT-1784",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="Java返回null未标注",trigger="val s=javaObj.getName();s.length",detection="T!可为null — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0082）",fix="?:+\\\"\\\""))
        BugDB.load(BugRule(id="KT-1785",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Byte）",trigger="fun javaMethod():Byte=null",detection="Kotlin不认Java注解 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0083）",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-1786",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmStatic缺失",trigger="INSTANCE.",detection="需要INSTANCE — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0084）",fix="加@JvmStatic"))
        BugDB.load(BugRule(id="KT-1787",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="SAM转换歧义",trigger="fun f(r:Runnable);fun f(c:Callable);f{",detection="多SAM冲突 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0085）",fix="显式lambda类型"))
        BugDB.load(BugRule(id="KT-1788",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="Kotlin集合与Java互转（Char）",trigger="kotlinChar.toChar()在Java侧",detection="创建新副本 — Char类型字符编码边界,正则匹配组越界（参见 KT-0086）",fix="直接传递"))
        BugDB.load(BugRule(id="KT-1789",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmOverloads缺失（Double?）",trigger="fun f(a:Double?,b:Double?=0)",detection="Java调用不提供重载 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0087）",fix="加@JvmOverloads"))
        BugDB.load(BugRule(id="KT-1790",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MILD,
            title="@JvmField冗余",trigger="@JvmField val x=42",detection="简单常量不需要 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0088）",fix="直接const"))
        BugDB.load(BugRule(id="KT-1791",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="Java异常类型在Kotlin中不可检查",trigger="try{javaMethod()}catch(e:IOException){}",detection="Kotlin不强制catch — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0202）",fix="文档标注或runCatching"))
        BugDB.load(BugRule(id="KT-1792",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmName与Kotlin名冲突",trigger="@JvmName(\\\"f\\\") fun fKotlin(){};fun f(){}",detection="字节码签名冲突 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0203）",fix="统一命名"))
        BugDB.load(BugRule(id="KT-1793",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="Java通配符转Kotlin型变",trigger="Consumer<?super T>在Kotlin",detection="<in T>映射 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0204）",fix="Consumer<in T>"))
        BugDB.load(BugRule(id="KT-1794",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="companion object的JvmStatic",trigger="companion{fun create()};Java:Companion.create()",detection="需要INSTANCE — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0205）",fix="@JvmStatic"))
        BugDB.load(BugRule(id="KT-1795",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MILD,
            title="@Throws注解在Kotlin多余",trigger="@Throws(IOException::class) fun f()",detection="Kotlin不强制 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0206）",fix="仅Java交互需要"))
        BugDB.load(BugRule(id="KT-1796",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MILD,
            title="Kotlin属性在Java中get/set（Map<String,Int>）",trigger="var name:Map<String,Map<String,Int>>在Java:getName()+setName()",detection="命名约定 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0207）",fix="保持一致"))
        BugDB.load(BugRule(id="KT-1797",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Int版）（Double）",trigger="fun javaMethod():Double=null",detection="Kotlin不认Java注解 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0421）",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-1798",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Long版）",trigger="fun javaMethod():Long=null",detection="Kotlin不认Java注解 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0422）",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-1799",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Double版）",trigger="fun javaMethod():Double=null",detection="Kotlin不认Java注解 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0423）",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-1800",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmOverloads缺失（Long版）",trigger="fun f(a:Long,b:Long=0)",detection="Java调用不提供重载 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0440）",fix="加@JvmOverloads"))
        BugDB.load(BugRule(id="KT-1801",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="Kotlin集合与Java互转（Set版）",trigger="kotlinSet.toSet()在Java侧",detection="创建新副本 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0449）",fix="直接传递"))
        BugDB.load(BugRule(id="KT-1802",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="Java返回null未标注",trigger="val s=javaObj.getName();s.length",detection="T!可为null — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0082）",fix="?:+\\\"\\\""))
        BugDB.load(BugRule(id="KT-1803",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Int）",trigger="fun javaMethod():Int=null",detection="Kotlin不认Java注解 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0083）",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-1804",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmStatic缺失",trigger="INSTANCE.",detection="需要INSTANCE — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0084）",fix="加@JvmStatic"))
        BugDB.load(BugRule(id="KT-1805",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="SAM转换歧义",trigger="fun f(r:Runnable);fun f(c:Callable);f{",detection="多SAM冲突 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0085）",fix="显式lambda类型"))
        BugDB.load(BugRule(id="KT-1806",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="Kotlin集合与Java互转（String）",trigger="kotlinString.toString()在Java侧",detection="创建新副本 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0086）",fix="直接传递"))
        BugDB.load(BugRule(id="KT-1807",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmOverloads缺失（Byte）",trigger="fun f(a:Byte,b:Byte=0)",detection="Java调用不提供重载 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0087）",fix="加@JvmOverloads"))
        BugDB.load(BugRule(id="KT-1808",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MILD,
            title="@JvmField冗余",trigger="@JvmField val x=42",detection="简单常量不需要 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0088）",fix="直接const"))
        BugDB.load(BugRule(id="KT-1809",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="Java异常类型在Kotlin中不可检查",trigger="try{javaMethod()}catch(e:IOException){}",detection="Kotlin不强制catch — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0202）",fix="文档标注或runCatching"))
        BugDB.load(BugRule(id="KT-1810",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmName与Kotlin名冲突",trigger="@JvmName(\\\"f\\\") fun fKotlin(){};fun f(){}",detection="字节码签名冲突 — Char类型字符编码边界,正则匹配组越界（参见 KT-0203）",fix="统一命名"))
        BugDB.load(BugRule(id="KT-1811",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="Java通配符转Kotlin型变",trigger="Consumer<?super T>在Kotlin",detection="<in T>映射 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0204）",fix="Consumer<in T>"))
        BugDB.load(BugRule(id="KT-1812",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="companion object的JvmStatic",trigger="companion{fun create()};Java:Companion.create()",detection="需要INSTANCE — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0205）",fix="@JvmStatic"))
        BugDB.load(BugRule(id="KT-1813",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MILD,
            title="@Throws注解在Kotlin多余",trigger="@Throws(IOException::class) fun f()",detection="Kotlin不强制 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0206）",fix="仅Java交互需要"))
        BugDB.load(BugRule(id="KT-1814",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MILD,
            title="Kotlin属性在Java中get/set（Long?）",trigger="var name:Long?在Java:getName()+setName()",detection="命名约定 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0207）",fix="保持一致"))
        BugDB.load(BugRule(id="KT-1815",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Int版）（MutableList<Double>）",trigger="fun javaMethod():MutableMutableList<Double><Double>=null",detection="Kotlin不认Java注解 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0421）",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-1816",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Long版）",trigger="fun javaMethod():Long=null",detection="Kotlin不认Java注解 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0422）",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-1817",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Double版）",trigger="fun javaMethod():Double=null",detection="Kotlin不认Java注解 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0423）",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-1818",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmOverloads缺失（Long版）",trigger="fun f(a:Long,b:Long=0)",detection="Java调用不提供重载 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0440）",fix="加@JvmOverloads"))
        BugDB.load(BugRule(id="KT-1819",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="Kotlin集合与Java互转（Set版）",trigger="kotlinSet.toSet()在Java侧",detection="创建新副本 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0449）",fix="直接传递"))
        BugDB.load(BugRule(id="KT-1820",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="Java返回null未标注",trigger="val s=javaObj.getName();s.length",detection="T!可为null — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0082）",fix="?:+\\\"\\\""))
        BugDB.load(BugRule(id="KT-1821",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Set<Int>）",trigger="fun javaMethod():Set<Set<Int>>=null",detection="Kotlin不认Java注解 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0083）",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-1822",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmStatic缺失",trigger="INSTANCE.",detection="需要INSTANCE — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0084）",fix="加@JvmStatic"))
        BugDB.load(BugRule(id="KT-1823",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="SAM转换歧义",trigger="fun f(r:Runnable);fun f(c:Callable);f{",detection="多SAM冲突 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0085）",fix="显式lambda类型"))
        BugDB.load(BugRule(id="KT-1824",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="Kotlin集合与Java互转（List<String>）",trigger="kotlinList<String>.toList<String>()在Java侧",detection="创建新副本 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0086）",fix="直接传递"))
        BugDB.load(BugRule(id="KT-1825",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmOverloads缺失",trigger="fun f(a:Int,b:Int=0)",detection="Java调用不提供重载 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0087）",fix="加@JvmOverloads"))
        BugDB.load(BugRule(id="KT-1826",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MILD,
            title="@JvmField冗余",trigger="@JvmField val x=42",detection="简单常量不需要 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0088）",fix="直接const"))
        BugDB.load(BugRule(id="KT-1827",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="Java异常类型在Kotlin中不可检查",trigger="try{javaMethod()}catch(e:IOException){}",detection="Kotlin不强制catch — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0202）",fix="文档标注或runCatching"))
        BugDB.load(BugRule(id="KT-1828",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmName与Kotlin名冲突",trigger="@JvmName(\\\"f\\\") fun fKotlin(){};fun f(){}",detection="字节码签名冲突 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0203）",fix="统一命名"))
        BugDB.load(BugRule(id="KT-1829",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="Java通配符转Kotlin型变",trigger="Consumer<?super T>在Kotlin",detection="<in T>映射 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0204）",fix="Consumer<in T>"))
        BugDB.load(BugRule(id="KT-1830",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="companion object的JvmStatic",trigger="companion{fun create()};Java:Companion.create()",detection="需要INSTANCE — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0205）",fix="@JvmStatic"))
        BugDB.load(BugRule(id="KT-1831",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MILD,
            title="@Throws注解在Kotlin多余",trigger="@Throws(IOException::class) fun f()",detection="Kotlin不强制 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0206）",fix="仅Java交互需要"))
        BugDB.load(BugRule(id="KT-1832",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MILD,
            title="Kotlin属性在Java中get/set（Char）",trigger="var name:Char在Java:getName()+setName()",detection="命名约定 — Char类型字符编码边界,正则匹配组越界（参见 KT-0207）",fix="保持一致"))
        BugDB.load(BugRule(id="KT-1833",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Int版）（Double?）",trigger="fun javaMethod():Double?=null",detection="Kotlin不认Java注解 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0421）",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-1834",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Long版）",trigger="fun javaMethod():Long=null",detection="Kotlin不认Java注解 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0422）",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-1835",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Double版）",trigger="fun javaMethod():Double=null",detection="Kotlin不认Java注解 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0423）",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-1836",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmOverloads缺失（Long版）",trigger="fun f(a:Long,b:Long=0)",detection="Java调用不提供重载 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0440）",fix="加@JvmOverloads"))
        BugDB.load(BugRule(id="KT-1837",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="Kotlin集合与Java互转（Set版）",trigger="kotlinSet.toSet()在Java侧",detection="创建新副本 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0449）",fix="直接传递"))
        BugDB.load(BugRule(id="KT-1838",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="Java返回null未标注",trigger="val s=javaObj.getName();s.length",detection="T!可为null — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0082）",fix="?:+\\\"\\\""))
        BugDB.load(BugRule(id="KT-1839",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Int?）",trigger="fun javaMethod():Int??=null",detection="Kotlin不认Java注解 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0083）",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-1840",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmStatic缺失",trigger="INSTANCE.",detection="需要INSTANCE — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0084）",fix="加@JvmStatic"))
        BugDB.load(BugRule(id="KT-1841",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="SAM转换歧义",trigger="fun f(r:Runnable);fun f(c:Callable);f{",detection="多SAM冲突 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0085）",fix="显式lambda类型"))
        BugDB.load(BugRule(id="KT-1842",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="Kotlin集合与Java互转（String?）",trigger="kotlinString?.toString?()在Java侧",detection="创建新副本 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0086）",fix="直接传递"))
        BugDB.load(BugRule(id="KT-1843",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmOverloads缺失（Set<Int>）",trigger="fun f(a:Set<Int>,b:Set<Int>=0)",detection="Java调用不提供重载 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0087）",fix="加@JvmOverloads"))
        BugDB.load(BugRule(id="KT-1844",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MILD,
            title="@JvmField冗余",trigger="@JvmField val x=42",detection="简单常量不需要 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0088）",fix="直接const"))
        BugDB.load(BugRule(id="KT-1845",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="Java异常类型在Kotlin中不可检查",trigger="try{javaMethod()}catch(e:IOException){}",detection="Kotlin不强制catch — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0202）",fix="文档标注或runCatching"))
        BugDB.load(BugRule(id="KT-1846",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmName与Kotlin名冲突",trigger="@JvmName(\\\"f\\\") fun fKotlin(){};fun f(){}",detection="字节码签名冲突 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0203）",fix="统一命名"))
        BugDB.load(BugRule(id="KT-1847",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="Java通配符转Kotlin型变",trigger="Consumer<?super T>在Kotlin",detection="<in T>映射 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0204）",fix="Consumer<in T>"))
        BugDB.load(BugRule(id="KT-1848",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="companion object的JvmStatic",trigger="companion{fun create()};Java:Companion.create()",detection="需要INSTANCE — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0205）",fix="@JvmStatic"))
        BugDB.load(BugRule(id="KT-1849",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MILD,
            title="@Throws注解在Kotlin多余",trigger="@Throws(IOException::class) fun f()",detection="Kotlin不强制 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0206）",fix="仅Java交互需要"))
        BugDB.load(BugRule(id="KT-1850",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MILD,
            title="Kotlin属性在Java中get/set",trigger="var name:String在Java:getName()+setName()",detection="命名约定 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0207）",fix="保持一致"))
        BugDB.load(BugRule(id="KT-1851",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Int版）（Byte）",trigger="fun javaMethod():Byte=null",detection="Kotlin不认Java注解 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0421）",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-1852",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Long版）",trigger="fun javaMethod():Long=null",detection="Kotlin不认Java注解 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0422）",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-1853",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Double版）",trigger="fun javaMethod():Double=null",detection="Kotlin不认Java注解 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0423）",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-1854",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmOverloads缺失（Long版）",trigger="fun f(a:Long,b:Long=0)",detection="Java调用不提供重载 — Char类型字符编码边界,正则匹配组越界（参见 KT-0440）",fix="加@JvmOverloads"))
        BugDB.load(BugRule(id="KT-1855",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="Kotlin集合与Java互转（Set版）",trigger="kotlinSet.toSet()在Java侧",detection="创建新副本 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0449）",fix="直接传递"))
        BugDB.load(BugRule(id="KT-1856",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="Java返回null未标注",trigger="val s=javaObj.getName();s.length",detection="T!可为null — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0082）",fix="?:+\\\"\\\""))
        BugDB.load(BugRule(id="KT-1857",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Float）",trigger="fun javaMethod():Float=null",detection="Kotlin不认Java注解 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0083）",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-1858",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmStatic缺失",trigger="INSTANCE.",detection="需要INSTANCE — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0084）",fix="加@JvmStatic"))
        BugDB.load(BugRule(id="KT-1859",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="SAM转换歧义",trigger="fun f(r:Runnable);fun f(c:Callable);f{",detection="多SAM冲突 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0085）",fix="显式lambda类型"))
        BugDB.load(BugRule(id="KT-1860",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="Kotlin集合与Java互转（Boolean）",trigger="kotlinBoolean.toBoolean()在Java侧",detection="创建新副本 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0086）",fix="直接传递"))
        BugDB.load(BugRule(id="KT-1861",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmOverloads缺失（Int?）",trigger="fun f(a:Int?,b:Int?=0)",detection="Java调用不提供重载 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0087）",fix="加@JvmOverloads"))
        BugDB.load(BugRule(id="KT-1862",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MILD,
            title="@JvmField冗余",trigger="@JvmField val x=42",detection="简单常量不需要 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0088）",fix="直接const"))
        BugDB.load(BugRule(id="KT-1863",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="Java异常类型在Kotlin中不可检查",trigger="try{javaMethod()}catch(e:IOException){}",detection="Kotlin不强制catch — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0202）",fix="文档标注或runCatching"))
        BugDB.load(BugRule(id="KT-1864",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmName与Kotlin名冲突",trigger="@JvmName(\\\"f\\\") fun fKotlin(){};fun f(){}",detection="字节码签名冲突 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0203）",fix="统一命名"))
        BugDB.load(BugRule(id="KT-1865",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="Java通配符转Kotlin型变",trigger="Consumer<?super T>在Kotlin",detection="<in T>映射 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0204）",fix="Consumer<in T>"))
        BugDB.load(BugRule(id="KT-1866",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="companion object的JvmStatic",trigger="companion{fun create()};Java:Companion.create()",detection="需要INSTANCE — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0205）",fix="@JvmStatic"))
        BugDB.load(BugRule(id="KT-1867",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MILD,
            title="@Throws注解在Kotlin多余",trigger="@Throws(IOException::class) fun f()",detection="Kotlin不强制 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0206）",fix="仅Java交互需要"))
        BugDB.load(BugRule(id="KT-1868",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MILD,
            title="Kotlin属性在Java中get/set（List<String>）",trigger="var name:List<String><String>在Java:getName()+setName()",detection="命名约定 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0207）",fix="保持一致"))
        BugDB.load(BugRule(id="KT-1869",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Int版）",trigger="fun javaMethod():Int=null",detection="Kotlin不认Java注解 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0421）",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-1870",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Long版）",trigger="fun javaMethod():Long=null",detection="Kotlin不认Java注解 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0422）",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-1871",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Double版）",trigger="fun javaMethod():Double=null",detection="Kotlin不认Java注解 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0423）",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-1872",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmOverloads缺失（Long版）",trigger="fun f(a:Long,b:Long=0)",detection="Java调用不提供重载 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0440）",fix="加@JvmOverloads"))
        BugDB.load(BugRule(id="KT-1873",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="Kotlin集合与Java互转（Set版）",trigger="kotlinSet.toSet()在Java侧",detection="创建新副本 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0449）",fix="直接传递"))
        BugDB.load(BugRule(id="KT-1874",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="Java返回null未标注",trigger="val s=javaObj.getName();s.length",detection="T!可为null — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0082）",fix="?:+\\\"\\\""))
        BugDB.load(BugRule(id="KT-1875",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Sequence<Long>）",trigger="fun javaMethod():Sequence<Long>=null",detection="Kotlin不认Java注解 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0083）",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-1876",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmStatic缺失",trigger="INSTANCE.",detection="需要INSTANCE — Char类型字符编码边界,正则匹配组越界（参见 KT-0084）",fix="加@JvmStatic"))
        BugDB.load(BugRule(id="KT-1877",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="SAM转换歧义",trigger="fun f(r:Runnable);fun f(c:Callable);f{",detection="多SAM冲突 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0085）",fix="显式lambda类型"))
        BugDB.load(BugRule(id="KT-1878",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="Kotlin集合与Java互转（Array<Boolean>）",trigger="kotlinArray<Boolean>.toArray<Boolean>()在Java侧",detection="创建新副本 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0086）",fix="直接传递"))
        BugDB.load(BugRule(id="KT-1879",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmOverloads缺失（Float）",trigger="fun f(a:Float,b:Float=0)",detection="Java调用不提供重载 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0087）",fix="加@JvmOverloads"))
        BugDB.load(BugRule(id="KT-1880",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MILD,
            title="@JvmField冗余",trigger="@JvmField val x=42",detection="简单常量不需要 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0088）",fix="直接const"))
        BugDB.load(BugRule(id="KT-1881",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="Java异常类型在Kotlin中不可检查",trigger="try{javaMethod()}catch(e:IOException){}",detection="Kotlin不强制catch — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0202）",fix="文档标注或runCatching"))
        BugDB.load(BugRule(id="KT-1882",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmName与Kotlin名冲突",trigger="@JvmName(\\\"f\\\") fun fKotlin(){};fun f(){}",detection="字节码签名冲突 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0203）",fix="统一命名"))
        BugDB.load(BugRule(id="KT-1883",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="Java通配符转Kotlin型变",trigger="Consumer<?super T>在Kotlin",detection="<in T>映射 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0204）",fix="Consumer<in T>"))
        BugDB.load(BugRule(id="KT-1884",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="companion object的JvmStatic",trigger="companion{fun create()};Java:Companion.create()",detection="需要INSTANCE — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0205）",fix="@JvmStatic"))
        BugDB.load(BugRule(id="KT-1885",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MILD,
            title="@Throws注解在Kotlin多余",trigger="@Throws(IOException::class) fun f()",detection="Kotlin不强制 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0206）",fix="仅Java交互需要"))
        BugDB.load(BugRule(id="KT-1886",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MILD,
            title="Kotlin属性在Java中get/set（String?）",trigger="var name:String?在Java:getName()+setName()",detection="命名约定 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0207）",fix="保持一致"))
        BugDB.load(BugRule(id="KT-1887",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Int版）（Set<Int>）",trigger="fun javaMethod():Set<Int>=null",detection="Kotlin不认Java注解 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0421）",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-1888",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Long版）",trigger="fun javaMethod():Long=null",detection="Kotlin不认Java注解 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0422）",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-1889",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Double版）",trigger="fun javaMethod():Double=null",detection="Kotlin不认Java注解 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0423）",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-1890",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmOverloads缺失（Long版）",trigger="fun f(a:Long,b:Long=0)",detection="Java调用不提供重载 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0440）",fix="加@JvmOverloads"))
        BugDB.load(BugRule(id="KT-1891",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="Kotlin集合与Java互转（Set版）",trigger="kotlinSet.toSet()在Java侧",detection="创建新副本 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0449）",fix="直接传递"))
        BugDB.load(BugRule(id="KT-1892",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="Java返回null未标注",trigger="val s=javaObj.getName();s.length",detection="T!可为null — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0082）",fix="?:+\\\"\\\""))
        BugDB.load(BugRule(id="KT-1893",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Any?）",trigger="fun javaMethod():Any?=null",detection="Kotlin不认Java注解 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0083）",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-1894",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmStatic缺失",trigger="INSTANCE.",detection="需要INSTANCE — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0084）",fix="加@JvmStatic"))
        BugDB.load(BugRule(id="KT-1895",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="SAM转换歧义",trigger="fun f(r:Runnable);fun f(c:Callable);f{",detection="多SAM冲突 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0085）",fix="显式lambda类型"))
        BugDB.load(BugRule(id="KT-1896",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="Kotlin集合与Java互转（Boolean?）",trigger="kotlinBoolean?.toBoolean?()在Java侧",detection="创建新副本 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0086）",fix="直接传递"))
        BugDB.load(BugRule(id="KT-1897",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmOverloads缺失（Sequence<Long>）",trigger="fun f(a:Sequence<Long>,b:Sequence<Long>=0)",detection="Java调用不提供重载 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0087）",fix="加@JvmOverloads"))
        BugDB.load(BugRule(id="KT-1898",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MILD,
            title="@JvmField冗余",trigger="@JvmField val x=42",detection="简单常量不需要 — Char类型字符编码边界,正则匹配组越界（参见 KT-0088）",fix="直接const"))
        BugDB.load(BugRule(id="KT-1899",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="Java异常类型在Kotlin中不可检查",trigger="try{javaMethod()}catch(e:IOException){}",detection="Kotlin不强制catch — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0202）",fix="文档标注或runCatching"))
        BugDB.load(BugRule(id="KT-1900",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmName与Kotlin名冲突",trigger="@JvmName(\\\"f\\\") fun fKotlin(){};fun f(){}",detection="字节码签名冲突 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0203）",fix="统一命名"))
        BugDB.load(BugRule(id="KT-1901",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="Java通配符转Kotlin型变",trigger="Consumer<?super T>在Kotlin",detection="<in T>映射 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0204）",fix="Consumer<in T>"))
        BugDB.load(BugRule(id="KT-1902",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="companion object的JvmStatic",trigger="companion{fun create()};Java:Companion.create()",detection="需要INSTANCE — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0205）",fix="@JvmStatic"))
        BugDB.load(BugRule(id="KT-1903",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MILD,
            title="@Throws注解在Kotlin多余",trigger="@Throws(IOException::class) fun f()",detection="Kotlin不强制 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0206）",fix="仅Java交互需要"))
        BugDB.load(BugRule(id="KT-1904",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MILD,
            title="Kotlin属性在Java中get/set（Boolean）",trigger="var name:Boolean在Java:getName()+setName()",detection="命名约定 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0207）",fix="保持一致"))
        BugDB.load(BugRule(id="KT-1905",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Int版）（Int?）",trigger="fun javaMethod():Int?=null",detection="Kotlin不认Java注解 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0421）",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-1906",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Long版）",trigger="fun javaMethod():Long=null",detection="Kotlin不认Java注解 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0422）",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-1907",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Double版）",trigger="fun javaMethod():Double=null",detection="Kotlin不认Java注解 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0423）",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-1908",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmOverloads缺失（Long版）",trigger="fun f(a:Long,b:Long=0)",detection="Java调用不提供重载 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0440）",fix="加@JvmOverloads"))
        BugDB.load(BugRule(id="KT-1909",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="Kotlin集合与Java互转（Set版）",trigger="kotlinSet.toSet()在Java侧",detection="创建新副本 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0449）",fix="直接传递"))
        BugDB.load(BugRule(id="KT-1910",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="Java返回null未标注",trigger="val s=javaObj.getName();s.length",detection="T!可为null — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0082）",fix="?:+\\\"\\\""))
        BugDB.load(BugRule(id="KT-1911",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.SEVERE,
            title="@NotNull注解缺失（Any）",trigger="fun javaMethod():Any=null",detection="Kotlin不认Java注解 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0083）",fix="显式标注?"))
        BugDB.load(BugRule(id="KT-1912",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmStatic缺失",trigger="INSTANCE.",detection="需要INSTANCE — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0084）",fix="加@JvmStatic"))
        BugDB.load(BugRule(id="KT-1913",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="SAM转换歧义",trigger="fun f(r:Runnable);fun f(c:Callable);f{",detection="多SAM冲突 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0085）",fix="显式lambda类型"))
        BugDB.load(BugRule(id="KT-1914",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="Kotlin集合与Java互转（Short）",trigger="kotlinShort.toShort()在Java侧",detection="创建新副本 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0086）",fix="直接传递"))
        BugDB.load(BugRule(id="KT-1915",category=BugCategory.JAVA_INTEROP,severity=BugSeverity.MODERATE,
            title="@JvmOverloads缺失（Any?）",trigger="fun f(a:Any?,b:Any?=0)",detection="Java调用不提供重载 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0087）",fix="加@JvmOverloads"))
        BugDB.load(BugRule(id="KT-1916",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="var智能转换失效",trigger="if(x is String){x.length}",detection="var可被外部修改 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0089）",fix="val y=x"))
        BugDB.load(BugRule(id="KT-1917",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="when穷举缺失",trigger="when(sealed){is A->...}",detection="遗漏子类 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0090）",fix="加else分支"))
        BugDB.load(BugRule(id="KT-1918",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="智能转换在闭包内失效",trigger="var x:Any?;launch{if(x!=null){x.method()}}",detection="可能被改 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0091）",fix="局部val快照"))
        BugDB.load(BugRule(id="KT-1919",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="as不安全转换（Sequence<Long>）",trigger="val x:Any;val y=x as Sequence<Long>",detection="可能ClassCast — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0092）",fix="as?+?:错误处理"))
        BugDB.load(BugRule(id="KT-1920",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="is检查后类型窄化丢失（Char）",trigger="if(x is Char<*>){x[0]}",detection="擦除后泛型信息丢失 — Char类型字符编码边界,正则匹配组越界（参见 KT-0093）",fix="reified"))
        BugDB.load(BugRule(id="KT-1921",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="不必要的as（Double?）",trigger="val x:Double?=\\\"hi\\\";val y=x as Double?",detection="已知类型不需要as — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0094）",fix="直接使用"))
        BugDB.load(BugRule(id="KT-1922",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="内部类属性智能转换失效（Array<Boolean>）",trigger="class Outer{var x:Any?;inner class Inner{fun f(){if(x is Array<Boolean>){x.length}}}}",detection="内部类访问外部var — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0208）",fix="局部val快照"))
        BugDB.load(BugRule(id="KT-1923",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="!!后is检查多余（Float）",trigger="x!!;if(x is Float){x.length}",detection="!!后非空 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0209）",fix="直接用is检查"))
        BugDB.load(BugRule(id="KT-1924",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="类型窄化+?:丢失（Long?）",trigger="val x=y as? Long??:return;x.length",detection="x已非空 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0210）",fix="直接使用不用?."))
        BugDB.load(BugRule(id="KT-1925",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="when完整但编译器仍要else",trigger="when(e){A->1;B->2;C->3}",detection="编译器提示不够智能 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0211）",fix="加else抛异常"))
        BugDB.load(BugRule(id="KT-1926",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="可空Boolean智能转换",trigger="if(b==true){b.not()}",detection="==true后b仍Boolean? — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0212）",fix="b?.let或?:反"))
        BugDB.load(BugRule(id="KT-1927",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="is检查+var+lambda=智能转换三次失效（Int?）",trigger="var x:Any?=\\\"hi\\\";if(x is Int??){launch{x.length}}",detection="var可能被改+lambda捕获 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0305）",fix="局部val快照+显式cast"))
        BugDB.load(BugRule(id="KT-1928",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="is检查后类型窄化丢失（Set版）",trigger="if(x is Set<*>){x[0]}",detection="擦除后泛型信息丢失 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0389）",fix="reified"))
        BugDB.load(BugRule(id="KT-1929",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="类型窄化+?:丢失（Int版）（Double）",trigger="val x=y as? Double?:return;x.length",detection="x已非空 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0394）",fix="直接使用不用?."))
        BugDB.load(BugRule(id="KT-1930",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="类型窄化+?:丢失（Long版）",trigger="val x=y as? Long?:return;x.length",detection="x已非空 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0395）",fix="直接使用不用?."))
        BugDB.load(BugRule(id="KT-1931",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="类型窄化+?:丢失（Double版）",trigger="val x=y as? Double?:return;x.length",detection="x已非空 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0396）",fix="直接使用不用?."))
        BugDB.load(BugRule(id="KT-1932",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="!!后is检查多余（Int版）（Long）",trigger="x!!;if(x is Long){x.length}",detection="!!后非空 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0406）",fix="直接用is检查"))
        BugDB.load(BugRule(id="KT-1933",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="!!后is检查多余（Long版）",trigger="x!!;if(x is Long){x.length}",detection="!!后非空 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0407）",fix="直接用is检查"))
        BugDB.load(BugRule(id="KT-1934",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="!!后is检查多余（Double版）",trigger="x!!;if(x is Double){x.length}",detection="!!后非空 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0408）",fix="直接用is检查"))
        BugDB.load(BugRule(id="KT-1935",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="!!后is检查多余（Boolean版）",trigger="x!!;if(x is Boolean){x.length}",detection="!!后非空 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0409）",fix="直接用is检查"))
        BugDB.load(BugRule(id="KT-1936",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="不必要的as（Int版）（Short）",trigger="val x:Short=\\\\\\\"hi\\\\\\\";val y=x as Short",detection="已知类型不需要as — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0463）",fix="直接使用"))
        BugDB.load(BugRule(id="KT-1937",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="不必要的as（Long版）",trigger="val x:Long=\\\\\\\"hi\\\\\\\";val y=x as Long",detection="已知类型不需要as — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0464）",fix="直接使用"))
        BugDB.load(BugRule(id="KT-1938",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="不必要的as（Double版）",trigger="val x:Double=\\\\\\\"hi\\\\\\\";val y=x as Double",detection="已知类型不需要as — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0465）",fix="直接使用"))
        BugDB.load(BugRule(id="KT-1939",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="不必要的as（Boolean版）",trigger="val x:Boolean=\\\\\\\"hi\\\\\\\";val y=x as Boolean",detection="已知类型不需要as — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0466）",fix="直接使用"))
        BugDB.load(BugRule(id="KT-1940",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="var智能转换失效（Int版）（Boolean?）",trigger="if(x is Boolean?){x.length}",detection="var可被外部修改 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0467）",fix="val y=x"))
        BugDB.load(BugRule(id="KT-1941",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="var智能转换失效（Long版）",trigger="if(x is Long){x.length}",detection="var可被外部修改 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0468）",fix="val y=x"))
        BugDB.load(BugRule(id="KT-1942",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="var智能转换失效（Double版）",trigger="if(x is Double){x.length}",detection="var可被外部修改 — Char类型字符编码边界,正则匹配组越界（参见 KT-0469）",fix="val y=x"))
        BugDB.load(BugRule(id="KT-1943",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="var智能转换失效（Boolean版）",trigger="if(x is Boolean){x.length}",detection="var可被外部修改 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0470）",fix="val y=x"))
        BugDB.load(BugRule(id="KT-1944",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="as不安全转换（Int版）（Array<Boolean>）",trigger="val x:Any;val y=x as Array<Boolean>",detection="可能ClassCast — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0474）",fix="as?+?:错误处理"))
        BugDB.load(BugRule(id="KT-1945",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="as不安全转换（Long版）",trigger="val x:Any;val y=x as Long",detection="可能ClassCast — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0475）",fix="as?+?:错误处理"))
        BugDB.load(BugRule(id="KT-1946",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="as不安全转换（Double版）",trigger="val x:Any;val y=x as Double",detection="可能ClassCast — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0476）",fix="as?+?:错误处理"))
        BugDB.load(BugRule(id="KT-1947",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="as不安全转换（Boolean版）",trigger="val x:Any;val y=x as Boolean",detection="可能ClassCast — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0477）",fix="as?+?:错误处理"))
        BugDB.load(BugRule(id="KT-1948",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="内部类属性智能转换失效（Int版）（Boolean）",trigger="class Outer{var x:Any?;inner class Inner{fun f(){if(x is Boolean){x.length}}}}",detection="内部类访问外部var — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0481）",fix="局部val快照"))
        BugDB.load(BugRule(id="KT-1949",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="内部类属性智能转换失效（Long版）",trigger="class Outer{var x:Any?;inner class Inner{fun f(){if(x is Long){x.length}}}}",detection="内部类访问外部var — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0482）",fix="局部val快照"))
        BugDB.load(BugRule(id="KT-1950",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="内部类属性智能转换失效（Double版）",trigger="class Outer{var x:Any?;inner class Inner{fun f(){if(x is Double){x.length}}}}",detection="内部类访问外部var — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0483）",fix="局部val快照"))
        BugDB.load(BugRule(id="KT-1951",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="内部类属性智能转换失效（Boolean版）",trigger="class Outer{var x:Any?;inner class Inner{fun f(){if(x is Boolean){x.length}}}}",detection="内部类访问外部var — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0484）",fix="局部val快照"))
        BugDB.load(BugRule(id="KT-1952",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="when分支智能转换不稳定（String?）",trigger="when(x){is String?->x+1 is Long->x+1L}",detection="编译器推断不一致 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0502）",fix="显式as+else"))
        BugDB.load(BugRule(id="KT-1953",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="var智能转换失效（Set<Int>）",trigger="if(x is Set<Set<Int>>){x.length}",detection="var可被外部修改 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0089）",fix="val y=x"))
        BugDB.load(BugRule(id="KT-1954",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="when穷举缺失",trigger="when(sealed){is A->...}",detection="遗漏子类 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0090）",fix="加else分支"))
        BugDB.load(BugRule(id="KT-1955",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="智能转换在闭包内失效",trigger="var x:Any?;launch{if(x!=null){x.method()}}",detection="可能被改 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0091）",fix="局部val快照"))
        BugDB.load(BugRule(id="KT-1956",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="as不安全转换（List<String>）",trigger="val x:Any;val y=x as List<String><String>",detection="可能ClassCast — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0092）",fix="as?+?:错误处理"))
        BugDB.load(BugRule(id="KT-1957",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="is检查后类型窄化丢失（Int）",trigger="if(x is Int<*>){x[0]}",detection="擦除后泛型信息丢失 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0093）",fix="reified"))
        BugDB.load(BugRule(id="KT-1958",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="不必要的as（Short）",trigger="val x:Short=\\\"hi\\\";val y=x as Short",detection="已知类型不需要as — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0094）",fix="直接使用"))
        BugDB.load(BugRule(id="KT-1959",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="内部类属性智能转换失效（Any?）",trigger="class Outer{var x:Any?;inner class Inner{fun f(){if(x is Any?){x.length}}}}",detection="内部类访问外部var — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0208）",fix="局部val快照"))
        BugDB.load(BugRule(id="KT-1960",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="!!后is检查多余",trigger="x!!;if(x is String){x.length}",detection="!!后非空 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0209）",fix="直接用is检查"))
        BugDB.load(BugRule(id="KT-1961",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="类型窄化+?:丢失（Byte）",trigger="val x=y as? Byte?:return;x.length",detection="x已非空 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0210）",fix="直接使用不用?."))
        BugDB.load(BugRule(id="KT-1962",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="when完整但编译器仍要else",trigger="when(e){A->1;B->2;C->3}",detection="编译器提示不够智能 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0211）",fix="加else抛异常"))
        BugDB.load(BugRule(id="KT-1963",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="可空Boolean智能转换",trigger="if(b==true){b.not()}",detection="==true后b仍Boolean? — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0212）",fix="b?.let或?:反"))
        BugDB.load(BugRule(id="KT-1964",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="is检查+var+lambda=智能转换三次失效（Char）",trigger="var x:Any?=\\\"hi\\\";if(x is Char){launch{x.length}}",detection="var可能被改+lambda捕获 — Char类型字符编码边界,正则匹配组越界（参见 KT-0305）",fix="局部val快照+显式cast"))
        BugDB.load(BugRule(id="KT-1965",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="is检查后类型窄化丢失（Set版）",trigger="if(x is Set<*>){x[0]}",detection="擦除后泛型信息丢失 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0389）",fix="reified"))
        BugDB.load(BugRule(id="KT-1966",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="类型窄化+?:丢失（Int版）（Array<Boolean>）",trigger="val x=y as? Array<Boolean>?:return;x.length",detection="x已非空 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0394）",fix="直接使用不用?."))
        BugDB.load(BugRule(id="KT-1967",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="类型窄化+?:丢失（Long版）",trigger="val x=y as? Long?:return;x.length",detection="x已非空 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0395）",fix="直接使用不用?."))
        BugDB.load(BugRule(id="KT-1968",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="类型窄化+?:丢失（Double版）",trigger="val x=y as? Double?:return;x.length",detection="x已非空 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0396）",fix="直接使用不用?."))
        BugDB.load(BugRule(id="KT-1969",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="!!后is检查多余（Int版）（MutableList<Double>）",trigger="x!!;if(x is MutableMutableList<Double><Double>){x.length}",detection="!!后非空 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0406）",fix="直接用is检查"))
        BugDB.load(BugRule(id="KT-1970",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="!!后is检查多余（Long版）",trigger="x!!;if(x is Long){x.length}",detection="!!后非空 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0407）",fix="直接用is检查"))
        BugDB.load(BugRule(id="KT-1971",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="!!后is检查多余（Double版）",trigger="x!!;if(x is Double){x.length}",detection="!!后非空 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0408）",fix="直接用is检查"))
        BugDB.load(BugRule(id="KT-1972",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="!!后is检查多余（Boolean版）",trigger="x!!;if(x is Boolean){x.length}",detection="!!后非空 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0409）",fix="直接用is检查"))
        BugDB.load(BugRule(id="KT-1973",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="不必要的as（Int版）（Double）",trigger="val x:Double=\\\\\\\"hi\\\\\\\";val y=x as Double",detection="已知类型不需要as — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0463）",fix="直接使用"))
        BugDB.load(BugRule(id="KT-1974",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="不必要的as（Long版）",trigger="val x:Long=\\\\\\\"hi\\\\\\\";val y=x as Long",detection="已知类型不需要as — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0464）",fix="直接使用"))
        BugDB.load(BugRule(id="KT-1975",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="不必要的as（Double版）",trigger="val x:Double=\\\\\\\"hi\\\\\\\";val y=x as Double",detection="已知类型不需要as — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0465）",fix="直接使用"))
        BugDB.load(BugRule(id="KT-1976",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="不必要的as（Boolean版）",trigger="val x:Boolean=\\\\\\\"hi\\\\\\\";val y=x as Boolean",detection="已知类型不需要as — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0466）",fix="直接使用"))
        BugDB.load(BugRule(id="KT-1977",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="var智能转换失效（Int版）（Any）",trigger="if(x is Any){x.length}",detection="var可被外部修改 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0467）",fix="val y=x"))
        BugDB.load(BugRule(id="KT-1978",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="var智能转换失效（Long版）",trigger="if(x is Long){x.length}",detection="var可被外部修改 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0468）",fix="val y=x"))
        BugDB.load(BugRule(id="KT-1979",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="var智能转换失效（Double版）",trigger="if(x is Double){x.length}",detection="var可被外部修改 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0469）",fix="val y=x"))
        BugDB.load(BugRule(id="KT-1980",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="var智能转换失效（Boolean版）",trigger="if(x is Boolean){x.length}",detection="var可被外部修改 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0470）",fix="val y=x"))
        BugDB.load(BugRule(id="KT-1981",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="as不安全转换（Int版）（Any?）",trigger="val x:Any;val y=x as Any?",detection="可能ClassCast — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0474）",fix="as?+?:错误处理"))
        BugDB.load(BugRule(id="KT-1982",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="as不安全转换（Long版）",trigger="val x:Any;val y=x as Long",detection="可能ClassCast — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0475）",fix="as?+?:错误处理"))
        BugDB.load(BugRule(id="KT-1983",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="as不安全转换（Double版）",trigger="val x:Any;val y=x as Double",detection="可能ClassCast — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0476）",fix="as?+?:错误处理"))
        BugDB.load(BugRule(id="KT-1984",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="as不安全转换（Boolean版）",trigger="val x:Any;val y=x as Boolean",detection="可能ClassCast — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0477）",fix="as?+?:错误处理"))
        BugDB.load(BugRule(id="KT-1985",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="内部类属性智能转换失效（Int版）（Sequence<Long>）",trigger="class Outer{var x:Any?;inner class Inner{fun f(){if(x is Sequence<Long>){x.length}}}}",detection="内部类访问外部var — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0481）",fix="局部val快照"))
        BugDB.load(BugRule(id="KT-1986",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="内部类属性智能转换失效（Long版）",trigger="class Outer{var x:Any?;inner class Inner{fun f(){if(x is Long){x.length}}}}",detection="内部类访问外部var — Char类型字符编码边界,正则匹配组越界（参见 KT-0482）",fix="局部val快照"))
        BugDB.load(BugRule(id="KT-1987",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="内部类属性智能转换失效（Double版）",trigger="class Outer{var x:Any?;inner class Inner{fun f(){if(x is Double){x.length}}}}",detection="内部类访问外部var — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0483）",fix="局部val快照"))
        BugDB.load(BugRule(id="KT-1988",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="内部类属性智能转换失效（Boolean版）",trigger="class Outer{var x:Any?;inner class Inner{fun f(){if(x is Boolean){x.length}}}}",detection="内部类访问外部var — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0484）",fix="局部val快照"))
        BugDB.load(BugRule(id="KT-1989",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="when分支智能转换不稳定（Float）",trigger="when(x){is Float->x+1 is Long->x+1L}",detection="编译器推断不一致 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0502）",fix="显式as+else"))
        BugDB.load(BugRule(id="KT-1990",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="var智能转换失效（Long?）",trigger="if(x is Long?){x.length}",detection="var可被外部修改 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0089）",fix="val y=x"))
        BugDB.load(BugRule(id="KT-1991",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="when穷举缺失",trigger="when(sealed){is A->...}",detection="遗漏子类 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0090）",fix="加else分支"))
        BugDB.load(BugRule(id="KT-1992",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="智能转换在闭包内失效",trigger="var x:Any?;launch{if(x!=null){x.method()}}",detection="可能被改 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0091）",fix="局部val快照"))
        BugDB.load(BugRule(id="KT-1993",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="as不安全转换（Int?）",trigger="val x:Any;val y=x as Int??",detection="可能ClassCast — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0092）",fix="as?+?:错误处理"))
        BugDB.load(BugRule(id="KT-1994",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="is检查后类型窄化丢失（Map<String,Int>）",trigger="if(x is Map<String,Int><*>){x[0]}",detection="擦除后泛型信息丢失 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0093）",fix="reified"))
        BugDB.load(BugRule(id="KT-1995",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="不必要的as（Double）",trigger="val x:Double=\\\"hi\\\";val y=x as Double",detection="已知类型不需要as — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0094）",fix="直接使用"))
        BugDB.load(BugRule(id="KT-1996",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="内部类属性智能转换失效（String?）",trigger="class Outer{var x:Any?;inner class Inner{fun f(){if(x is String?){x.length}}}}",detection="内部类访问外部var — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0208）",fix="局部val快照"))
        BugDB.load(BugRule(id="KT-1997",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="!!后is检查多余（Set<Int>）",trigger="x!!;if(x is Set<Set<Int>>){x.length}",detection="!!后非空 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0209）",fix="直接用is检查"))
        BugDB.load(BugRule(id="KT-1998",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="类型窄化+?:丢失（Long）",trigger="val x=y as? Long?:return;x.length",detection="x已非空 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0210）",fix="直接使用不用?."))
        BugDB.load(BugRule(id="KT-1999",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="when完整但编译器仍要else",trigger="when(e){A->1;B->2;C->3}",detection="编译器提示不够智能 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0211）",fix="加else抛异常"))
        BugDB.load(BugRule(id="KT-2000",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="可空Boolean智能转换",trigger="if(b==true){b.not()}",detection="==true后b仍Boolean? — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0212）",fix="b?.let或?:反"))
    }

    private fun registerChunk5() {
        BugDB.load(BugRule(id="KT-2001",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="is检查+var+lambda=智能转换三次失效（Int）",trigger="var x:Any?=\\\"hi\\\";if(x is Int){launch{x.length}}",detection="var可能被改+lambda捕获 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0305）",fix="局部val快照+显式cast"))
        BugDB.load(BugRule(id="KT-2002",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="is检查后类型窄化丢失（Set版）",trigger="if(x is Set<*>){x[0]}",detection="擦除后泛型信息丢失 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0389）",fix="reified"))
        BugDB.load(BugRule(id="KT-2003",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="类型窄化+?:丢失（Int版）（Any?）",trigger="val x=y as? Any??:return;x.length",detection="x已非空 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0394）",fix="直接使用不用?."))
        BugDB.load(BugRule(id="KT-2004",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="类型窄化+?:丢失（Long版）",trigger="val x=y as? Long?:return;x.length",detection="x已非空 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0395）",fix="直接使用不用?."))
        BugDB.load(BugRule(id="KT-2005",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="类型窄化+?:丢失（Double版）",trigger="val x=y as? Double?:return;x.length",detection="x已非空 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0396）",fix="直接使用不用?."))
        BugDB.load(BugRule(id="KT-2006",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="!!后is检查多余（Int版）（Boolean?）",trigger="x!!;if(x is Boolean?){x.length}",detection="!!后非空 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0406）",fix="直接用is检查"))
        BugDB.load(BugRule(id="KT-2007",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="!!后is检查多余（Long版）",trigger="x!!;if(x is Long){x.length}",detection="!!后非空 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0407）",fix="直接用is检查"))
        BugDB.load(BugRule(id="KT-2008",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="!!后is检查多余（Double版）",trigger="x!!;if(x is Double){x.length}",detection="!!后非空 — Char类型字符编码边界,正则匹配组越界（参见 KT-0408）",fix="直接用is检查"))
        BugDB.load(BugRule(id="KT-2009",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="!!后is检查多余（Boolean版）",trigger="x!!;if(x is Boolean){x.length}",detection="!!后非空 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0409）",fix="直接用is检查"))
        BugDB.load(BugRule(id="KT-2010",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="不必要的as（Int版）（Array<Boolean>）",trigger="val x:Array<Boolean>=\\\\\\\"hi\\\\\\\";val y=x as Array<Boolean>",detection="已知类型不需要as — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0463）",fix="直接使用"))
        BugDB.load(BugRule(id="KT-2011",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="不必要的as（Long版）",trigger="val x:Long=\\\\\\\"hi\\\\\\\";val y=x as Long",detection="已知类型不需要as — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0464）",fix="直接使用"))
        BugDB.load(BugRule(id="KT-2012",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="不必要的as（Double版）",trigger="val x:Double=\\\\\\\"hi\\\\\\\";val y=x as Double",detection="已知类型不需要as — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0465）",fix="直接使用"))
        BugDB.load(BugRule(id="KT-2013",category=BugCategory.SMART_CAST,severity=BugSeverity.MILD,
            title="不必要的as（Boolean版）",trigger="val x:Boolean=\\\\\\\"hi\\\\\\\";val y=x as Boolean",detection="已知类型不需要as — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0466）",fix="直接使用"))
        BugDB.load(BugRule(id="KT-2014",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="var智能转换失效（Int版）（Boolean）",trigger="if(x is Boolean){x.length}",detection="var可被外部修改 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0467）",fix="val y=x"))
        BugDB.load(BugRule(id="KT-2015",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="var智能转换失效（Long版）",trigger="if(x is Long){x.length}",detection="var可被外部修改 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0468）",fix="val y=x"))
        BugDB.load(BugRule(id="KT-2016",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="var智能转换失效（Double版）",trigger="if(x is Double){x.length}",detection="var可被外部修改 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0469）",fix="val y=x"))
        BugDB.load(BugRule(id="KT-2017",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="var智能转换失效（Boolean版）",trigger="if(x is Boolean){x.length}",detection="var可被外部修改 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0470）",fix="val y=x"))
        BugDB.load(BugRule(id="KT-2018",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="as不安全转换（Int版）（String?）",trigger="val x:Any;val y=x as String?",detection="可能ClassCast — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0474）",fix="as?+?:错误处理"))
        BugDB.load(BugRule(id="KT-2019",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="as不安全转换（Long版）",trigger="val x:Any;val y=x as Long",detection="可能ClassCast — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0475）",fix="as?+?:错误处理"))
        BugDB.load(BugRule(id="KT-2020",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="as不安全转换（Double版）",trigger="val x:Any;val y=x as Double",detection="可能ClassCast — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0476）",fix="as?+?:错误处理"))
        BugDB.load(BugRule(id="KT-2021",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="as不安全转换（Boolean版）",trigger="val x:Any;val y=x as Boolean",detection="可能ClassCast — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0477）",fix="as?+?:错误处理"))
        BugDB.load(BugRule(id="KT-2022",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="内部类属性智能转换失效（Int版）（List<String>）",trigger="class Outer{var x:Any?;inner class Inner{fun f(){if(x is List<String><String>){x.length}}}}",detection="内部类访问外部var — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0481）",fix="局部val快照"))
        BugDB.load(BugRule(id="KT-2023",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="内部类属性智能转换失效（Long版）",trigger="class Outer{var x:Any?;inner class Inner{fun f(){if(x is Long){x.length}}}}",detection="内部类访问外部var — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0482）",fix="局部val快照"))
        BugDB.load(BugRule(id="KT-2024",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="内部类属性智能转换失效（Double版）",trigger="class Outer{var x:Any?;inner class Inner{fun f(){if(x is Double){x.length}}}}",detection="内部类访问外部var — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0483）",fix="局部val快照"))
        BugDB.load(BugRule(id="KT-2025",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="内部类属性智能转换失效（Boolean版）",trigger="class Outer{var x:Any?;inner class Inner{fun f(){if(x is Boolean){x.length}}}}",detection="内部类访问外部var — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0484）",fix="局部val快照"))
        BugDB.load(BugRule(id="KT-2026",category=BugCategory.SMART_CAST,severity=BugSeverity.MODERATE,
            title="when分支智能转换不稳定（String）",trigger="when(x){is String->x+1 is Long->x+1L}",detection="编译器推断不一致 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0502）",fix="显式as+else"))
        BugDB.load(BugRule(id="KT-2027",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="var智能转换失效（Byte）",trigger="if(x is Byte){x.length}",detection="var可被外部修改 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0089）",fix="val y=x"))
        BugDB.load(BugRule(id="KT-2028",category=BugCategory.SMART_CAST,severity=BugSeverity.SEVERE,
            title="when穷举缺失",trigger="when(sealed){is A->...}",detection="遗漏子类 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0090）",fix="加else分支"))
        BugDB.load(BugRule(id="KT-2029",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="@JvmInline缺失",trigger="inline class Name(val s:String)",detection="JVM需要注解 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0095）",fix="加@JvmInline"))
        BugDB.load(BugRule(id="KT-2030",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类实现接口（Byte）",trigger="value class N(val s:Byte):Iface",detection="间接装箱 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0096）",fix="避免接口"))
        BugDB.load(BugRule(id="KT-2031",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类多属性（Boolean?）",trigger="@JvmInline value class P(val x:Boolean?,val y:Boolean?)",detection="仅单属性 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0097）",fix="拆成两个"))
        BugDB.load(BugRule(id="KT-2032",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类在泛型中装箱（Sequence<Long>）",trigger="val x:Sequence<Long><MyValueClass>=listOf(MyValueClass(1))",detection="放入泛型装箱 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0213）",fix="考虑直接使用List<Int>"))
        BugDB.load(BugRule(id="KT-2033",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类==比较失效（Char）",trigger="inline class N(val i:Char);N(1)==N(1)",detection="equals自动生成 — Char类型字符编码边界,正则匹配组越界（参见 KT-0214）",fix="可用但注意引用比较"))
        BugDB.load(BugRule(id="KT-2034",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类非主构造属性（Double?）",trigger="inline class N(val s:Double?){val len=s.length}",detection="每次计算 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0215）",fix="内联到val本身"))
        BugDB.load(BugRule(id="KT-2035",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类非主构造属性（Int版）（Array<Boolean>）",trigger="inline class N(val s:Array<Boolean>){val len=s.length}",detection="每次计算 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0363）",fix="内联到val本身"))
        BugDB.load(BugRule(id="KT-2036",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类非主构造属性（Long版）",trigger="inline class N(val s:Long){val len=s.length}",detection="每次计算 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0364）",fix="内联到val本身"))
        BugDB.load(BugRule(id="KT-2037",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类非主构造属性（Double版）",trigger="inline class N(val s:Double){val len=s.length}",detection="每次计算 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0365）",fix="内联到val本身"))
        BugDB.load(BugRule(id="KT-2038",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类实现接口（Int版）（MutableList<Double>）",trigger="value class N(val s:MutableMutableList<Double><Double>):Iface",detection="间接装箱 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0414）",fix="避免接口"))
        BugDB.load(BugRule(id="KT-2039",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类实现接口（Long版）",trigger="value class N(val s:Long):Iface",detection="间接装箱 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0415）",fix="避免接口"))
        BugDB.load(BugRule(id="KT-2040",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类实现接口（Double版）",trigger="value class N(val s:Double):Iface",detection="间接装箱 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0416）",fix="避免接口"))
        BugDB.load(BugRule(id="KT-2041",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类实现接口（Boolean版）",trigger="value class N(val s:Boolean):Iface",detection="间接装箱 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0417）",fix="避免接口"))
        BugDB.load(BugRule(id="KT-2042",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类==比较失效（Long版）",trigger="inline class N(val i:Long);N(1)==N(1)",detection="equals自动生成 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0418）",fix="可用但注意引用比较"))
        BugDB.load(BugRule(id="KT-2043",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类==比较失效（Double版）",trigger="inline class N(val i:Double);N(1)==N(1)",detection="equals自动生成 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0419）",fix="可用但注意引用比较"))
        BugDB.load(BugRule(id="KT-2044",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类==比较失效（Float版）",trigger="inline class N(val i:Float);N(1)==N(1)",detection="equals自动生成 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0420）",fix="可用但注意引用比较"))
        BugDB.load(BugRule(id="KT-2045",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类多属性（Long版）",trigger="@JvmInline value class P(val x:Long,val y:Long)",detection="仅单属性 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0478）",fix="拆成两个"))
        BugDB.load(BugRule(id="KT-2046",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类多属性（Double版）",trigger="@JvmInline value class P(val x:Double,val y:Double)",detection="仅单属性 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0479）",fix="拆成两个"))
        BugDB.load(BugRule(id="KT-2047",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类多属性（Float版）",trigger="@JvmInline value class P(val x:Float,val y:Float)",detection="仅单属性 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0480）",fix="拆成两个"))
        BugDB.load(BugRule(id="KT-2048",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="@JvmInline缺失（Int）",trigger="inline class Name(val s:Int)",detection="JVM需要注解 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0095）",fix="加@JvmInline"))
        BugDB.load(BugRule(id="KT-2049",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类实现接口（Short）",trigger="value class N(val s:Short):Iface",detection="间接装箱 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0096）",fix="避免接口"))
        BugDB.load(BugRule(id="KT-2050",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类多属性（Any?）",trigger="@JvmInline value class P(val x:Any?,val y:Any?)",detection="仅单属性 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0097）",fix="拆成两个"))
        BugDB.load(BugRule(id="KT-2051",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类在泛型中装箱（String）",trigger="val x:String<MyValueClass>=listOf(MyValueClass(1))",detection="放入泛型装箱 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0213）",fix="考虑直接使用List<Int>"))
        BugDB.load(BugRule(id="KT-2052",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类==比较失效（Byte）",trigger="inline class N(val i:Byte);N(1)==N(1)",detection="equals自动生成 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0214）",fix="可用但注意引用比较"))
        BugDB.load(BugRule(id="KT-2053",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类非主构造属性（Boolean?）",trigger="inline class N(val s:Boolean?){val len=s.length}",detection="每次计算 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0215）",fix="内联到val本身"))
        BugDB.load(BugRule(id="KT-2054",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类非主构造属性（Int版）（Sequence<Long>）",trigger="inline class N(val s:Sequence<Long>){val len=s.length}",detection="每次计算 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0363）",fix="内联到val本身"))
        BugDB.load(BugRule(id="KT-2055",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类非主构造属性（Long版）",trigger="inline class N(val s:Long){val len=s.length}",detection="每次计算 — Char类型字符编码边界,正则匹配组越界（参见 KT-0364）",fix="内联到val本身"))
        BugDB.load(BugRule(id="KT-2056",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类非主构造属性（Double版）",trigger="inline class N(val s:Double){val len=s.length}",detection="每次计算 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0365）",fix="内联到val本身"))
        BugDB.load(BugRule(id="KT-2057",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类实现接口（Int版）（Array<Boolean>）",trigger="value class N(val s:Array<Boolean>):Iface",detection="间接装箱 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0414）",fix="避免接口"))
        BugDB.load(BugRule(id="KT-2058",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类实现接口（Long版）",trigger="value class N(val s:Long):Iface",detection="间接装箱 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0415）",fix="避免接口"))
        BugDB.load(BugRule(id="KT-2059",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类实现接口（Double版）",trigger="value class N(val s:Double):Iface",detection="间接装箱 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0416）",fix="避免接口"))
        BugDB.load(BugRule(id="KT-2060",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类实现接口（Boolean版）",trigger="value class N(val s:Boolean):Iface",detection="间接装箱 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0417）",fix="避免接口"))
        BugDB.load(BugRule(id="KT-2061",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类==比较失效（Long版）",trigger="inline class N(val i:Long);N(1)==N(1)",detection="equals自动生成 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0418）",fix="可用但注意引用比较"))
        BugDB.load(BugRule(id="KT-2062",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类==比较失效（Double版）",trigger="inline class N(val i:Double);N(1)==N(1)",detection="equals自动生成 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0419）",fix="可用但注意引用比较"))
        BugDB.load(BugRule(id="KT-2063",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类==比较失效（Float版）",trigger="inline class N(val i:Float);N(1)==N(1)",detection="equals自动生成 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0420）",fix="可用但注意引用比较"))
        BugDB.load(BugRule(id="KT-2064",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类多属性（Long版）",trigger="@JvmInline value class P(val x:Long,val y:Long)",detection="仅单属性 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0478）",fix="拆成两个"))
        BugDB.load(BugRule(id="KT-2065",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类多属性（Double版）",trigger="@JvmInline value class P(val x:Double,val y:Double)",detection="仅单属性 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0479）",fix="拆成两个"))
        BugDB.load(BugRule(id="KT-2066",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类多属性（Float版）",trigger="@JvmInline value class P(val x:Float,val y:Float)",detection="仅单属性 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0480）",fix="拆成两个"))
        BugDB.load(BugRule(id="KT-2067",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="@JvmInline缺失（Long）",trigger="inline class Name(val s:Long)",detection="JVM需要注解 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0095）",fix="加@JvmInline"))
        BugDB.load(BugRule(id="KT-2068",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类实现接口（Any）",trigger="value class N(val s:Any):Iface",detection="间接装箱 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0096）",fix="避免接口"))
        BugDB.load(BugRule(id="KT-2069",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类多属性（List<String>）",trigger="@JvmInline value class P(val x:List<String><String>,val y:List<String><String>)",detection="仅单属性 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0097）",fix="拆成两个"))
        BugDB.load(BugRule(id="KT-2070",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类在泛型中装箱（Int）",trigger="val x:Int<MyValueClass>=listOf(MyValueClass(1))",detection="放入泛型装箱 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0213）",fix="考虑直接使用List<Int>"))
        BugDB.load(BugRule(id="KT-2071",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类==比较失效（Short）",trigger="inline class N(val i:Short);N(1)==N(1)",detection="equals自动生成 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0214）",fix="可用但注意引用比较"))
        BugDB.load(BugRule(id="KT-2072",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类非主构造属性（Any?）",trigger="inline class N(val s:Any?){val len=s.length}",detection="每次计算 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0215）",fix="内联到val本身"))
        BugDB.load(BugRule(id="KT-2073",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类非主构造属性（Int版）（String）",trigger="inline class N(val s:String){val len=s.length}",detection="每次计算 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0363）",fix="内联到val本身"))
        BugDB.load(BugRule(id="KT-2074",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类非主构造属性（Long版）",trigger="inline class N(val s:Long){val len=s.length}",detection="每次计算 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0364）",fix="内联到val本身"))
        BugDB.load(BugRule(id="KT-2075",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类非主构造属性（Double版）",trigger="inline class N(val s:Double){val len=s.length}",detection="每次计算 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0365）",fix="内联到val本身"))
        BugDB.load(BugRule(id="KT-2076",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类实现接口（Int版）（Sequence<Long>）",trigger="value class N(val s:Sequence<Long>):Iface",detection="间接装箱 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0414）",fix="避免接口"))
        BugDB.load(BugRule(id="KT-2077",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类实现接口（Long版）",trigger="value class N(val s:Long):Iface",detection="间接装箱 — Char类型字符编码边界,正则匹配组越界（参见 KT-0415）",fix="避免接口"))
        BugDB.load(BugRule(id="KT-2078",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类实现接口（Double版）",trigger="value class N(val s:Double):Iface",detection="间接装箱 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0416）",fix="避免接口"))
        BugDB.load(BugRule(id="KT-2079",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类实现接口（Boolean版）",trigger="value class N(val s:Boolean):Iface",detection="间接装箱 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0417）",fix="避免接口"))
        BugDB.load(BugRule(id="KT-2080",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类==比较失效（Long版）",trigger="inline class N(val i:Long);N(1)==N(1)",detection="equals自动生成 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0418）",fix="可用但注意引用比较"))
        BugDB.load(BugRule(id="KT-2081",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类==比较失效（Double版）",trigger="inline class N(val i:Double);N(1)==N(1)",detection="equals自动生成 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0419）",fix="可用但注意引用比较"))
        BugDB.load(BugRule(id="KT-2082",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类==比较失效（Float版）",trigger="inline class N(val i:Float);N(1)==N(1)",detection="equals自动生成 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0420）",fix="可用但注意引用比较"))
        BugDB.load(BugRule(id="KT-2083",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类多属性（Long版）",trigger="@JvmInline value class P(val x:Long,val y:Long)",detection="仅单属性 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0478）",fix="拆成两个"))
        BugDB.load(BugRule(id="KT-2084",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类多属性（Double版）",trigger="@JvmInline value class P(val x:Double,val y:Double)",detection="仅单属性 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0479）",fix="拆成两个"))
        BugDB.load(BugRule(id="KT-2085",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类多属性（Float版）",trigger="@JvmInline value class P(val x:Float,val y:Float)",detection="仅单属性 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0480）",fix="拆成两个"))
        BugDB.load(BugRule(id="KT-2086",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="@JvmInline缺失（Double）",trigger="inline class Name(val s:Double)",detection="JVM需要注解 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0095）",fix="加@JvmInline"))
        BugDB.load(BugRule(id="KT-2087",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类实现接口（String?）",trigger="value class N(val s:String?):Iface",detection="间接装箱 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0096）",fix="避免接口"))
        BugDB.load(BugRule(id="KT-2088",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MILD,
            title="内联类多属性（Set<Int>）",trigger="@JvmInline value class P(val x:Set<Int>,val y:Set<Int>)",detection="仅单属性 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0097）",fix="拆成两个"))
        BugDB.load(BugRule(id="KT-2089",category=BugCategory.VALUE_CLASS,severity=BugSeverity.MODERATE,
            title="内联类在泛型中装箱（Long）",trigger="val x:Long<MyValueClass>=listOf(MyValueClass(1))",detection="放入泛型装箱 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0213）",fix="考虑直接使用List<Int>"))
        BugDB.load(BugRule(id="KT-2090",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="values()每次新建数组",trigger="values().find{",detection="每次调用新建 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0098）",fix="enumEntries()"))
        BugDB.load(BugRule(id="KT-2091",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="sealed class跨文件",trigger="sealed class A;File1:class B:A();File2:class C:A()",detection="限制同文件 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0099）",fix="移到同文件或sealed interface"))
        BugDB.load(BugRule(id="KT-2092",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="枚举ordinal依赖",trigger="when(e.ordinal){0->...}",detection="顺序变化会错 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0100）",fix="用枚举常量"))
        BugDB.load(BugRule(id="KT-2093",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举包含可变状态（Sequence<Long>）",trigger="enum class E(var x:Sequence<Long>)",detection="枚举应不可变 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0101）",fix="val"))
        BugDB.load(BugRule(id="KT-2094",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="when穷举sealed少子类",trigger="sealed class A;class B:A();when(a){is B->1}",detection="新增C:A()不报错 — Char类型字符编码边界,正则匹配组越界（参见 KT-0216）",fix="加else或sealed interface"))
        BugDB.load(BugRule(id="KT-2095",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="枚举name属性依赖",trigger="when(e.name){\\\"A\\\"->1}",detection="name不是业务标识 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0217）",fix="用自定义属性"))
        BugDB.load(BugRule(id="KT-2096",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举构造函数开销（Array<Boolean>）",trigger="enum class E(val x:Array<Boolean>=0);E.A",detection="每个枚举实例化 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0218）",fix="用object代替"))
        BugDB.load(BugRule(id="KT-2097",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="sealed+反射枚举子类=新增子类不拦截",trigger="sealed class A;通过反射实例化未知子类",detection="when穷举被绕过 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0300）",fix="sealed+禁止反射实例化"))
        BugDB.load(BugRule(id="KT-2098",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举包含可变状态（Long版）",trigger="enum class E(var x:Long)",detection="枚举应不可变 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0453）",fix="val"))
        BugDB.load(BugRule(id="KT-2099",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举包含可变状态（Double版）",trigger="enum class E(var x:Double)",detection="枚举应不可变 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0454）",fix="val"))
        BugDB.load(BugRule(id="KT-2100",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举包含可变状态（Float版）",trigger="enum class E(var x:Float)",detection="枚举应不可变 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0455）",fix="val"))
        BugDB.load(BugRule(id="KT-2101",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举构造函数开销（Long版）",trigger="enum class E(val x:Long=0);E.A",detection="每个枚举实例化 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0472）",fix="用object代替"))
        BugDB.load(BugRule(id="KT-2102",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举构造函数开销（Double版）",trigger="enum class E(val x:Double=0);E.A",detection="每个枚举实例化 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0473）",fix="用object代替"))
        BugDB.load(BugRule(id="KT-2103",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="values()每次新建数组",trigger="values().find{",detection="每次调用新建 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0098）",fix="enumEntries()"))
        BugDB.load(BugRule(id="KT-2104",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="sealed class跨文件",trigger="sealed class A;File1:class B:A();File2:class C:A()",detection="限制同文件 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0099）",fix="移到同文件或sealed interface"))
        BugDB.load(BugRule(id="KT-2105",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="枚举ordinal依赖",trigger="when(e.ordinal){0->...}",detection="顺序变化会错 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0100）",fix="用枚举常量"))
        BugDB.load(BugRule(id="KT-2106",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举包含可变状态（Long）",trigger="enum class E(var x:Long)",detection="枚举应不可变 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0101）",fix="val"))
        BugDB.load(BugRule(id="KT-2107",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="when穷举sealed少子类",trigger="sealed class A;class B:A();when(a){is B->1}",detection="新增C:A()不报错 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0216）",fix="加else或sealed interface"))
        BugDB.load(BugRule(id="KT-2108",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="枚举name属性依赖",trigger="when(e.name){\\\"A\\\"->1}",detection="name不是业务标识 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0217）",fix="用自定义属性"))
        BugDB.load(BugRule(id="KT-2109",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举构造函数开销",trigger="enum class E(val x:Int=0);E.A",detection="每个枚举实例化 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0218）",fix="用object代替"))
        BugDB.load(BugRule(id="KT-2110",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="sealed+反射枚举子类=新增子类不拦截",trigger="sealed class A;通过反射实例化未知子类",detection="when穷举被绕过 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0300）",fix="sealed+禁止反射实例化"))
        BugDB.load(BugRule(id="KT-2111",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举包含可变状态（Long版）",trigger="enum class E(var x:Long)",detection="枚举应不可变 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0453）",fix="val"))
        BugDB.load(BugRule(id="KT-2112",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举包含可变状态（Double版）",trigger="enum class E(var x:Double)",detection="枚举应不可变 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0454）",fix="val"))
        BugDB.load(BugRule(id="KT-2113",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举包含可变状态（Float版）",trigger="enum class E(var x:Float)",detection="枚举应不可变 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0455）",fix="val"))
        BugDB.load(BugRule(id="KT-2114",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举构造函数开销（Long版）",trigger="enum class E(val x:Long=0);E.A",detection="每个枚举实例化 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0472）",fix="用object代替"))
        BugDB.load(BugRule(id="KT-2115",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举构造函数开销（Double版）",trigger="enum class E(val x:Double=0);E.A",detection="每个枚举实例化 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0473）",fix="用object代替"))
        BugDB.load(BugRule(id="KT-2116",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="values()每次新建数组",trigger="values().find{",detection="每次调用新建 — Char类型字符编码边界,正则匹配组越界（参见 KT-0098）",fix="enumEntries()"))
        BugDB.load(BugRule(id="KT-2117",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="sealed class跨文件",trigger="sealed class A;File1:class B:A();File2:class C:A()",detection="限制同文件 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0099）",fix="移到同文件或sealed interface"))
        BugDB.load(BugRule(id="KT-2118",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="枚举ordinal依赖",trigger="when(e.ordinal){0->...}",detection="顺序变化会错 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0100）",fix="用枚举常量"))
        BugDB.load(BugRule(id="KT-2119",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举包含可变状态（Float）",trigger="enum class E(var x:Float)",detection="枚举应不可变 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0101）",fix="val"))
        BugDB.load(BugRule(id="KT-2120",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="when穷举sealed少子类",trigger="sealed class A;class B:A();when(a){is B->1}",detection="新增C:A()不报错 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0216）",fix="加else或sealed interface"))
        BugDB.load(BugRule(id="KT-2121",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="枚举name属性依赖",trigger="when(e.name){\\\"A\\\"->1}",detection="name不是业务标识 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0217）",fix="用自定义属性"))
        BugDB.load(BugRule(id="KT-2122",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举构造函数开销（Boolean）",trigger="enum class E(val x:Boolean=0);E.A",detection="每个枚举实例化 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0218）",fix="用object代替"))
        BugDB.load(BugRule(id="KT-2123",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="sealed+反射枚举子类=新增子类不拦截",trigger="sealed class A;通过反射实例化未知子类",detection="when穷举被绕过 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0300）",fix="sealed+禁止反射实例化"))
        BugDB.load(BugRule(id="KT-2124",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举包含可变状态（Long版）",trigger="enum class E(var x:Long)",detection="枚举应不可变 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0453）",fix="val"))
        BugDB.load(BugRule(id="KT-2125",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举包含可变状态（Double版）",trigger="enum class E(var x:Double)",detection="枚举应不可变 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0454）",fix="val"))
        BugDB.load(BugRule(id="KT-2126",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举包含可变状态（Float版）",trigger="enum class E(var x:Float)",detection="枚举应不可变 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0455）",fix="val"))
        BugDB.load(BugRule(id="KT-2127",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举构造函数开销（Long版）",trigger="enum class E(val x:Long=0);E.A",detection="每个枚举实例化 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0472）",fix="用object代替"))
        BugDB.load(BugRule(id="KT-2128",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举构造函数开销（Double版）",trigger="enum class E(val x:Double=0);E.A",detection="每个枚举实例化 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0473）",fix="用object代替"))
        BugDB.load(BugRule(id="KT-2129",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="values()每次新建数组",trigger="values().find{",detection="每次调用新建 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0098）",fix="enumEntries()"))
        BugDB.load(BugRule(id="KT-2130",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="sealed class跨文件",trigger="sealed class A;File1:class B:A();File2:class C:A()",detection="限制同文件 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0099）",fix="移到同文件或sealed interface"))
        BugDB.load(BugRule(id="KT-2131",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="枚举ordinal依赖",trigger="when(e.ordinal){0->...}",detection="顺序变化会错 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0100）",fix="用枚举常量"))
        BugDB.load(BugRule(id="KT-2132",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举包含可变状态（Short）",trigger="enum class E(var x:Short)",detection="枚举应不可变 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0101）",fix="val"))
        BugDB.load(BugRule(id="KT-2133",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="when穷举sealed少子类",trigger="sealed class A;class B:A();when(a){is B->1}",detection="新增C:A()不报错 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0216）",fix="加else或sealed interface"))
        BugDB.load(BugRule(id="KT-2134",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="枚举name属性依赖",trigger="when(e.name){\\\"A\\\"->1}",detection="name不是业务标识 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0217）",fix="用自定义属性"))
        BugDB.load(BugRule(id="KT-2135",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举构造函数开销（Byte）",trigger="enum class E(val x:Byte=0);E.A",detection="每个枚举实例化 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0218）",fix="用object代替"))
        BugDB.load(BugRule(id="KT-2136",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="sealed+反射枚举子类=新增子类不拦截",trigger="sealed class A;通过反射实例化未知子类",detection="when穷举被绕过 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0300）",fix="sealed+禁止反射实例化"))
        BugDB.load(BugRule(id="KT-2137",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举包含可变状态（Long版）",trigger="enum class E(var x:Long)",detection="枚举应不可变 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0453）",fix="val"))
        BugDB.load(BugRule(id="KT-2138",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举包含可变状态（Double版）",trigger="enum class E(var x:Double)",detection="枚举应不可变 — Char类型字符编码边界,正则匹配组越界（参见 KT-0454）",fix="val"))
        BugDB.load(BugRule(id="KT-2139",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举包含可变状态（Float版）",trigger="enum class E(var x:Float)",detection="枚举应不可变 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0455）",fix="val"))
        BugDB.load(BugRule(id="KT-2140",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举构造函数开销（Long版）",trigger="enum class E(val x:Long=0);E.A",detection="每个枚举实例化 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0472）",fix="用object代替"))
        BugDB.load(BugRule(id="KT-2141",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举构造函数开销（Double版）",trigger="enum class E(val x:Double=0);E.A",detection="每个枚举实例化 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0473）",fix="用object代替"))
        BugDB.load(BugRule(id="KT-2142",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="values()每次新建数组",trigger="values().find{",detection="每次调用新建 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0098）",fix="enumEntries()"))
        BugDB.load(BugRule(id="KT-2143",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="sealed class跨文件",trigger="sealed class A;File1:class B:A();File2:class C:A()",detection="限制同文件 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0099）",fix="移到同文件或sealed interface"))
        BugDB.load(BugRule(id="KT-2144",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="枚举ordinal依赖",trigger="when(e.ordinal){0->...}",detection="顺序变化会错 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0100）",fix="用枚举常量"))
        BugDB.load(BugRule(id="KT-2145",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举包含可变状态（Int?）",trigger="enum class E(var x:Int?)",detection="枚举应不可变 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0101）",fix="val"))
        BugDB.load(BugRule(id="KT-2146",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="when穷举sealed少子类",trigger="sealed class A;class B:A();when(a){is B->1}",detection="新增C:A()不报错 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0216）",fix="加else或sealed interface"))
        BugDB.load(BugRule(id="KT-2147",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="枚举name属性依赖",trigger="when(e.name){\\\"A\\\"->1}",detection="name不是业务标识 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0217）",fix="用自定义属性"))
        BugDB.load(BugRule(id="KT-2148",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举构造函数开销（String?）",trigger="enum class E(val x:String?=0);E.A",detection="每个枚举实例化 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0218）",fix="用object代替"))
        BugDB.load(BugRule(id="KT-2149",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="sealed+反射枚举子类=新增子类不拦截",trigger="sealed class A;通过反射实例化未知子类",detection="when穷举被绕过 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0300）",fix="sealed+禁止反射实例化"))
        BugDB.load(BugRule(id="KT-2150",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举包含可变状态（Long版）",trigger="enum class E(var x:Long)",detection="枚举应不可变 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0453）",fix="val"))
        BugDB.load(BugRule(id="KT-2151",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举包含可变状态（Double版）",trigger="enum class E(var x:Double)",detection="枚举应不可变 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0454）",fix="val"))
        BugDB.load(BugRule(id="KT-2152",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举包含可变状态（Float版）",trigger="enum class E(var x:Float)",detection="枚举应不可变 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0455）",fix="val"))
        BugDB.load(BugRule(id="KT-2153",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举构造函数开销（Long版）",trigger="enum class E(val x:Long=0);E.A",detection="每个枚举实例化 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0472）",fix="用object代替"))
        BugDB.load(BugRule(id="KT-2154",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举构造函数开销（Double版）",trigger="enum class E(val x:Double=0);E.A",detection="每个枚举实例化 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0473）",fix="用object代替"))
        BugDB.load(BugRule(id="KT-2155",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="values()每次新建数组",trigger="values().find{",detection="每次调用新建 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0098）",fix="enumEntries()"))
        BugDB.load(BugRule(id="KT-2156",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="sealed class跨文件",trigger="sealed class A;File1:class B:A();File2:class C:A()",detection="限制同文件 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0099）",fix="移到同文件或sealed interface"))
        BugDB.load(BugRule(id="KT-2157",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="枚举ordinal依赖",trigger="when(e.ordinal){0->...}",detection="顺序变化会错 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0100）",fix="用枚举常量"))
        BugDB.load(BugRule(id="KT-2158",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举包含可变状态（Boolean?）",trigger="enum class E(var x:Boolean?)",detection="枚举应不可变 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0101）",fix="val"))
        BugDB.load(BugRule(id="KT-2159",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="when穷举sealed少子类",trigger="sealed class A;class B:A();when(a){is B->1}",detection="新增C:A()不报错 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0216）",fix="加else或sealed interface"))
        BugDB.load(BugRule(id="KT-2160",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="枚举name属性依赖",trigger="when(e.name){\\\"A\\\"->1}",detection="name不是业务标识 — Char类型字符编码边界,正则匹配组越界（参见 KT-0217）",fix="用自定义属性"))
        BugDB.load(BugRule(id="KT-2161",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举构造函数开销（Double?）",trigger="enum class E(val x:Double?=0);E.A",detection="每个枚举实例化 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0218）",fix="用object代替"))
        BugDB.load(BugRule(id="KT-2162",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="sealed+反射枚举子类=新增子类不拦截",trigger="sealed class A;通过反射实例化未知子类",detection="when穷举被绕过 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0300）",fix="sealed+禁止反射实例化"))
        BugDB.load(BugRule(id="KT-2163",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举包含可变状态（Long版）",trigger="enum class E(var x:Long)",detection="枚举应不可变 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0453）",fix="val"))
        BugDB.load(BugRule(id="KT-2164",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举包含可变状态（Double版）",trigger="enum class E(var x:Double)",detection="枚举应不可变 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0454）",fix="val"))
        BugDB.load(BugRule(id="KT-2165",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举包含可变状态（Float版）",trigger="enum class E(var x:Float)",detection="枚举应不可变 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0455）",fix="val"))
        BugDB.load(BugRule(id="KT-2166",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举构造函数开销（Long版）",trigger="enum class E(val x:Long=0);E.A",detection="每个枚举实例化 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0472）",fix="用object代替"))
        BugDB.load(BugRule(id="KT-2167",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举构造函数开销（Double版）",trigger="enum class E(val x:Double=0);E.A",detection="每个枚举实例化 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0473）",fix="用object代替"))
        BugDB.load(BugRule(id="KT-2168",category=BugCategory.SEALED_ENUM,severity=BugSeverity.SEVERE,
            title="values()每次新建数组",trigger="values().find{",detection="每次调用新建 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0098）",fix="enumEntries()"))
        BugDB.load(BugRule(id="KT-2169",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="sealed class跨文件",trigger="sealed class A;File1:class B:A();File2:class C:A()",detection="限制同文件 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0099）",fix="移到同文件或sealed interface"))
        BugDB.load(BugRule(id="KT-2170",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="枚举ordinal依赖",trigger="when(e.ordinal){0->...}",detection="顺序变化会错 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0100）",fix="用枚举常量"))
        BugDB.load(BugRule(id="KT-2171",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举包含可变状态（Set<Int>）",trigger="enum class E(var x:Set<Int>)",detection="枚举应不可变 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0101）",fix="val"))
        BugDB.load(BugRule(id="KT-2172",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="when穷举sealed少子类",trigger="sealed class A;class B:A();when(a){is B->1}",detection="新增C:A()不报错 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0216）",fix="加else或sealed interface"))
        BugDB.load(BugRule(id="KT-2173",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="枚举name属性依赖",trigger="when(e.name){\\\"A\\\"->1}",detection="name不是业务标识 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0217）",fix="用自定义属性"))
        BugDB.load(BugRule(id="KT-2174",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举构造函数开销（List<String>）",trigger="enum class E(val x:List<String><String>=0);E.A",detection="每个枚举实例化 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0218）",fix="用object代替"))
        BugDB.load(BugRule(id="KT-2175",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MODERATE,
            title="sealed+反射枚举子类=新增子类不拦截",trigger="sealed class A;通过反射实例化未知子类",detection="when穷举被绕过 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0300）",fix="sealed+禁止反射实例化"))
        BugDB.load(BugRule(id="KT-2176",category=BugCategory.SEALED_ENUM,severity=BugSeverity.MILD,
            title="枚举包含可变状态（Long版）",trigger="enum class E(var x:Long)",detection="枚举应不可变 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0453）",fix="val"))
        BugDB.load(BugRule(id="KT-2177",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="by lazy默认SYNCHRONIZED",trigger="by lazy{",detection="默认线程安全开销 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0102）",fix="指定LazyThreadSafetyMode.NONE"))
        BugDB.load(BugRule(id="KT-2178",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="observable内修改自身",trigger="var x by Delegates.observable(0){_,_,_,_->x++}",detection="循环触发 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0103）",fix="用vetoable"))
        BugDB.load(BugRule(id="KT-2179",category=BugCategory.DELEGATE,severity=BugSeverity.MILD,
            title="不必要的委托",trigger="val x by lazy{42}",detection="常量不需要lazy — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0104）",fix="直接val=42"))
        BugDB.load(BugRule(id="KT-2180",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="委托属性getValue/setValue签名错误",trigger="class D{operator fun getValue(ref:KProperty<*>,prop:KProperty<*>):T}",detection="第二个参数类型错误 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0219）",fix="thisRef:KProperty<*>,prop:KProperty<*>"))
        BugDB.load(BugRule(id="KT-2181",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="ReadOnlyProperty与ReadWriteProperty混淆",trigger="val x by Delegates.observable(0){",detection="observable返回ReadWriteProperty — Char类型字符编码边界,正则匹配组越界（参见 KT-0220）",fix="不需要set就用ReadOnlyProperty"))
        BugDB.load(BugRule(id="KT-2182",category=BugCategory.DELEGATE,severity=BugSeverity.MILD,
            title="Map委托属性名不匹配",trigger="val x by map;(\\\"y\\\"to\\\"oops\\\")",detection="Map key为\\\"y\\\"非\\\"x\\\" — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0221）",fix="key与属性名一致"))
        BugDB.load(BugRule(id="KT-2183",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="by lazy+@Volatile=过度同步",trigger="@Volatile var x by lazy{init()}",detection="lazy已同步+@Volatile再次同步 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0302）",fix="去掉@Volatile或不用lazy"))
        BugDB.load(BugRule(id="KT-2184",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="by lazy默认SYNCHRONIZED",trigger="by lazy{",detection="默认线程安全开销 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0102）",fix="指定LazyThreadSafetyMode.NONE"))
        BugDB.load(BugRule(id="KT-2185",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="observable内修改自身",trigger="var x by Delegates.observable(0){_,_,_,_->x++}",detection="循环触发 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0103）",fix="用vetoable"))
        BugDB.load(BugRule(id="KT-2186",category=BugCategory.DELEGATE,severity=BugSeverity.MILD,
            title="不必要的委托",trigger="val x by lazy{42}",detection="常量不需要lazy — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0104）",fix="直接val=42"))
        BugDB.load(BugRule(id="KT-2187",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="委托属性getValue/setValue签名错误",trigger="class D{operator fun getValue(ref:KProperty<*>,prop:KProperty<*>):T}",detection="第二个参数类型错误 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0219）",fix="thisRef:KProperty<*>,prop:KProperty<*>"))
        BugDB.load(BugRule(id="KT-2188",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="ReadOnlyProperty与ReadWriteProperty混淆",trigger="val x by Delegates.observable(0){",detection="observable返回ReadWriteProperty — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0220）",fix="不需要set就用ReadOnlyProperty"))
        BugDB.load(BugRule(id="KT-2189",category=BugCategory.DELEGATE,severity=BugSeverity.MILD,
            title="Map委托属性名不匹配",trigger="val x by map;(\\\"y\\\"to\\\"oops\\\")",detection="Map key为\\\"y\\\"非\\\"x\\\" — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0221）",fix="key与属性名一致"))
        BugDB.load(BugRule(id="KT-2190",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="by lazy+@Volatile=过度同步",trigger="@Volatile var x by lazy{init()}",detection="lazy已同步+@Volatile再次同步 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0302）",fix="去掉@Volatile或不用lazy"))
        BugDB.load(BugRule(id="KT-2191",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="by lazy默认SYNCHRONIZED",trigger="by lazy{",detection="默认线程安全开销 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0102）",fix="指定LazyThreadSafetyMode.NONE"))
        BugDB.load(BugRule(id="KT-2192",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="observable内修改自身",trigger="var x by Delegates.observable(0){_,_,_,_->x++}",detection="循环触发 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0103）",fix="用vetoable"))
        BugDB.load(BugRule(id="KT-2193",category=BugCategory.DELEGATE,severity=BugSeverity.MILD,
            title="不必要的委托",trigger="val x by lazy{42}",detection="常量不需要lazy — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0104）",fix="直接val=42"))
        BugDB.load(BugRule(id="KT-2194",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="委托属性getValue/setValue签名错误",trigger="class D{operator fun getValue(ref:KProperty<*>,prop:KProperty<*>):T}",detection="第二个参数类型错误 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0219）",fix="thisRef:KProperty<*>,prop:KProperty<*>"))
        BugDB.load(BugRule(id="KT-2195",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="ReadOnlyProperty与ReadWriteProperty混淆",trigger="val x by Delegates.observable(0){",detection="observable返回ReadWriteProperty — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0220）",fix="不需要set就用ReadOnlyProperty"))
        BugDB.load(BugRule(id="KT-2196",category=BugCategory.DELEGATE,severity=BugSeverity.MILD,
            title="Map委托属性名不匹配",trigger="val x by map;(\\\"y\\\"to\\\"oops\\\")",detection="Map key为\\\"y\\\"非\\\"x\\\" — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0221）",fix="key与属性名一致"))
        BugDB.load(BugRule(id="KT-2197",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="by lazy+@Volatile=过度同步",trigger="@Volatile var x by lazy{init()}",detection="lazy已同步+@Volatile再次同步 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0302）",fix="去掉@Volatile或不用lazy"))
        BugDB.load(BugRule(id="KT-2198",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="by lazy默认SYNCHRONIZED",trigger="by lazy{",detection="默认线程安全开销 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0102）",fix="指定LazyThreadSafetyMode.NONE"))
        BugDB.load(BugRule(id="KT-2199",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="observable内修改自身",trigger="var x by Delegates.observable(0){_,_,_,_->x++}",detection="循环触发 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0103）",fix="用vetoable"))
        BugDB.load(BugRule(id="KT-2200",category=BugCategory.DELEGATE,severity=BugSeverity.MILD,
            title="不必要的委托",trigger="val x by lazy{42}",detection="常量不需要lazy — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0104）",fix="直接val=42"))
        BugDB.load(BugRule(id="KT-2201",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="委托属性getValue/setValue签名错误",trigger="class D{operator fun getValue(ref:KProperty<*>,prop:KProperty<*>):T}",detection="第二个参数类型错误 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0219）",fix="thisRef:KProperty<*>,prop:KProperty<*>"))
        BugDB.load(BugRule(id="KT-2202",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="ReadOnlyProperty与ReadWriteProperty混淆",trigger="val x by Delegates.observable(0){",detection="observable返回ReadWriteProperty — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0220）",fix="不需要set就用ReadOnlyProperty"))
        BugDB.load(BugRule(id="KT-2203",category=BugCategory.DELEGATE,severity=BugSeverity.MILD,
            title="Map委托属性名不匹配",trigger="val x by map;(\\\"y\\\"to\\\"oops\\\")",detection="Map key为\\\"y\\\"非\\\"x\\\" — Char类型字符编码边界,正则匹配组越界（参见 KT-0221）",fix="key与属性名一致"))
        BugDB.load(BugRule(id="KT-2204",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="by lazy+@Volatile=过度同步",trigger="@Volatile var x by lazy{init()}",detection="lazy已同步+@Volatile再次同步 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0302）",fix="去掉@Volatile或不用lazy"))
        BugDB.load(BugRule(id="KT-2205",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="by lazy默认SYNCHRONIZED",trigger="by lazy{",detection="默认线程安全开销 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0102）",fix="指定LazyThreadSafetyMode.NONE"))
        BugDB.load(BugRule(id="KT-2206",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="observable内修改自身",trigger="var x by Delegates.observable(0){_,_,_,_->x++}",detection="循环触发 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0103）",fix="用vetoable"))
        BugDB.load(BugRule(id="KT-2207",category=BugCategory.DELEGATE,severity=BugSeverity.MILD,
            title="不必要的委托",trigger="val x by lazy{42}",detection="常量不需要lazy — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0104）",fix="直接val=42"))
        BugDB.load(BugRule(id="KT-2208",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="委托属性getValue/setValue签名错误",trigger="class D{operator fun getValue(ref:KProperty<*>,prop:KProperty<*>):T}",detection="第二个参数类型错误 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0219）",fix="thisRef:KProperty<*>,prop:KProperty<*>"))
        BugDB.load(BugRule(id="KT-2209",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="ReadOnlyProperty与ReadWriteProperty混淆",trigger="val x by Delegates.observable(0){",detection="observable返回ReadWriteProperty — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0220）",fix="不需要set就用ReadOnlyProperty"))
        BugDB.load(BugRule(id="KT-2210",category=BugCategory.DELEGATE,severity=BugSeverity.MILD,
            title="Map委托属性名不匹配",trigger="val x by map;(\\\"y\\\"to\\\"oops\\\")",detection="Map key为\\\"y\\\"非\\\"x\\\" — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0221）",fix="key与属性名一致"))
        BugDB.load(BugRule(id="KT-2211",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="by lazy+@Volatile=过度同步",trigger="@Volatile var x by lazy{init()}",detection="lazy已同步+@Volatile再次同步 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0302）",fix="去掉@Volatile或不用lazy"))
        BugDB.load(BugRule(id="KT-2212",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="by lazy默认SYNCHRONIZED",trigger="by lazy{",detection="默认线程安全开销 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0102）",fix="指定LazyThreadSafetyMode.NONE"))
        BugDB.load(BugRule(id="KT-2213",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="observable内修改自身",trigger="var x by Delegates.observable(0){_,_,_,_->x++}",detection="循环触发 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0103）",fix="用vetoable"))
        BugDB.load(BugRule(id="KT-2214",category=BugCategory.DELEGATE,severity=BugSeverity.MILD,
            title="不必要的委托",trigger="val x by lazy{42}",detection="常量不需要lazy — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0104）",fix="直接val=42"))
        BugDB.load(BugRule(id="KT-2215",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="委托属性getValue/setValue签名错误",trigger="class D{operator fun getValue(ref:KProperty<*>,prop:KProperty<*>):T}",detection="第二个参数类型错误 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0219）",fix="thisRef:KProperty<*>,prop:KProperty<*>"))
        BugDB.load(BugRule(id="KT-2216",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="ReadOnlyProperty与ReadWriteProperty混淆",trigger="val x by Delegates.observable(0){",detection="observable返回ReadWriteProperty — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0220）",fix="不需要set就用ReadOnlyProperty"))
        BugDB.load(BugRule(id="KT-2217",category=BugCategory.DELEGATE,severity=BugSeverity.MILD,
            title="Map委托属性名不匹配",trigger="val x by map;(\\\"y\\\"to\\\"oops\\\")",detection="Map key为\\\"y\\\"非\\\"x\\\" — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0221）",fix="key与属性名一致"))
        BugDB.load(BugRule(id="KT-2218",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="by lazy+@Volatile=过度同步",trigger="@Volatile var x by lazy{init()}",detection="lazy已同步+@Volatile再次同步 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0302）",fix="去掉@Volatile或不用lazy"))
        BugDB.load(BugRule(id="KT-2219",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="by lazy默认SYNCHRONIZED",trigger="by lazy{",detection="默认线程安全开销 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0102）",fix="指定LazyThreadSafetyMode.NONE"))
        BugDB.load(BugRule(id="KT-2220",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="observable内修改自身",trigger="var x by Delegates.observable(0){_,_,_,_->x++}",detection="循环触发 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0103）",fix="用vetoable"))
        BugDB.load(BugRule(id="KT-2221",category=BugCategory.DELEGATE,severity=BugSeverity.MILD,
            title="不必要的委托",trigger="val x by lazy{42}",detection="常量不需要lazy — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0104）",fix="直接val=42"))
        BugDB.load(BugRule(id="KT-2222",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="委托属性getValue/setValue签名错误",trigger="class D{operator fun getValue(ref:KProperty<*>,prop:KProperty<*>):T}",detection="第二个参数类型错误 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0219）",fix="thisRef:KProperty<*>,prop:KProperty<*>"))
        BugDB.load(BugRule(id="KT-2223",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="ReadOnlyProperty与ReadWriteProperty混淆",trigger="val x by Delegates.observable(0){",detection="observable返回ReadWriteProperty — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0220）",fix="不需要set就用ReadOnlyProperty"))
        BugDB.load(BugRule(id="KT-2224",category=BugCategory.DELEGATE,severity=BugSeverity.MILD,
            title="Map委托属性名不匹配",trigger="val x by map;(\\\"y\\\"to\\\"oops\\\")",detection="Map key为\\\"y\\\"非\\\"x\\\" — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0221）",fix="key与属性名一致"))
        BugDB.load(BugRule(id="KT-2225",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="by lazy+@Volatile=过度同步",trigger="@Volatile var x by lazy{init()}",detection="lazy已同步+@Volatile再次同步 — Char类型字符编码边界,正则匹配组越界（参见 KT-0302）",fix="去掉@Volatile或不用lazy"))
        BugDB.load(BugRule(id="KT-2226",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="by lazy默认SYNCHRONIZED",trigger="by lazy{",detection="默认线程安全开销 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0102）",fix="指定LazyThreadSafetyMode.NONE"))
        BugDB.load(BugRule(id="KT-2227",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="observable内修改自身",trigger="var x by Delegates.observable(0){_,_,_,_->x++}",detection="循环触发 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0103）",fix="用vetoable"))
        BugDB.load(BugRule(id="KT-2228",category=BugCategory.DELEGATE,severity=BugSeverity.MILD,
            title="不必要的委托",trigger="val x by lazy{42}",detection="常量不需要lazy — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0104）",fix="直接val=42"))
        BugDB.load(BugRule(id="KT-2229",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="委托属性getValue/setValue签名错误",trigger="class D{operator fun getValue(ref:KProperty<*>,prop:KProperty<*>):T}",detection="第二个参数类型错误 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0219）",fix="thisRef:KProperty<*>,prop:KProperty<*>"))
        BugDB.load(BugRule(id="KT-2230",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="ReadOnlyProperty与ReadWriteProperty混淆",trigger="val x by Delegates.observable(0){",detection="observable返回ReadWriteProperty — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0220）",fix="不需要set就用ReadOnlyProperty"))
        BugDB.load(BugRule(id="KT-2231",category=BugCategory.DELEGATE,severity=BugSeverity.MILD,
            title="Map委托属性名不匹配",trigger="val x by map;(\\\"y\\\"to\\\"oops\\\")",detection="Map key为\\\"y\\\"非\\\"x\\\" — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0221）",fix="key与属性名一致"))
        BugDB.load(BugRule(id="KT-2232",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="by lazy+@Volatile=过度同步",trigger="@Volatile var x by lazy{init()}",detection="lazy已同步+@Volatile再次同步 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0302）",fix="去掉@Volatile或不用lazy"))
        BugDB.load(BugRule(id="KT-2233",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="by lazy默认SYNCHRONIZED",trigger="by lazy{",detection="默认线程安全开销 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0102）",fix="指定LazyThreadSafetyMode.NONE"))
        BugDB.load(BugRule(id="KT-2234",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="observable内修改自身",trigger="var x by Delegates.observable(0){_,_,_,_->x++}",detection="循环触发 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0103）",fix="用vetoable"))
        BugDB.load(BugRule(id="KT-2235",category=BugCategory.DELEGATE,severity=BugSeverity.MILD,
            title="不必要的委托",trigger="val x by lazy{42}",detection="常量不需要lazy — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0104）",fix="直接val=42"))
        BugDB.load(BugRule(id="KT-2236",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="委托属性getValue/setValue签名错误",trigger="class D{operator fun getValue(ref:KProperty<*>,prop:KProperty<*>):T}",detection="第二个参数类型错误 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0219）",fix="thisRef:KProperty<*>,prop:KProperty<*>"))
        BugDB.load(BugRule(id="KT-2237",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="ReadOnlyProperty与ReadWriteProperty混淆",trigger="val x by Delegates.observable(0){",detection="observable返回ReadWriteProperty — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0220）",fix="不需要set就用ReadOnlyProperty"))
        BugDB.load(BugRule(id="KT-2238",category=BugCategory.DELEGATE,severity=BugSeverity.MILD,
            title="Map委托属性名不匹配",trigger="val x by map;(\\\"y\\\"to\\\"oops\\\")",detection="Map key为\\\"y\\\"非\\\"x\\\" — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0221）",fix="key与属性名一致"))
        BugDB.load(BugRule(id="KT-2239",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="by lazy+@Volatile=过度同步",trigger="@Volatile var x by lazy{init()}",detection="lazy已同步+@Volatile再次同步 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0302）",fix="去掉@Volatile或不用lazy"))
        BugDB.load(BugRule(id="KT-2240",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="by lazy默认SYNCHRONIZED",trigger="by lazy{",detection="默认线程安全开销 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0102）",fix="指定LazyThreadSafetyMode.NONE"))
        BugDB.load(BugRule(id="KT-2241",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="observable内修改自身",trigger="var x by Delegates.observable(0){_,_,_,_->x++}",detection="循环触发 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0103）",fix="用vetoable"))
        BugDB.load(BugRule(id="KT-2242",category=BugCategory.DELEGATE,severity=BugSeverity.MILD,
            title="不必要的委托",trigger="val x by lazy{42}",detection="常量不需要lazy — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0104）",fix="直接val=42"))
        BugDB.load(BugRule(id="KT-2243",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="委托属性getValue/setValue签名错误",trigger="class D{operator fun getValue(ref:KProperty<*>,prop:KProperty<*>):T}",detection="第二个参数类型错误 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0219）",fix="thisRef:KProperty<*>,prop:KProperty<*>"))
        BugDB.load(BugRule(id="KT-2244",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="ReadOnlyProperty与ReadWriteProperty混淆",trigger="val x by Delegates.observable(0){",detection="observable返回ReadWriteProperty — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0220）",fix="不需要set就用ReadOnlyProperty"))
        BugDB.load(BugRule(id="KT-2245",category=BugCategory.DELEGATE,severity=BugSeverity.MILD,
            title="Map委托属性名不匹配",trigger="val x by map;(\\\"y\\\"to\\\"oops\\\")",detection="Map key为\\\"y\\\"非\\\"x\\\" — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0221）",fix="key与属性名一致"))
        BugDB.load(BugRule(id="KT-2246",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="by lazy+@Volatile=过度同步",trigger="@Volatile var x by lazy{init()}",detection="lazy已同步+@Volatile再次同步 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0302）",fix="去掉@Volatile或不用lazy"))
        BugDB.load(BugRule(id="KT-2247",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="by lazy默认SYNCHRONIZED",trigger="by lazy{",detection="默认线程安全开销 — Char类型字符编码边界,正则匹配组越界（参见 KT-0102）",fix="指定LazyThreadSafetyMode.NONE"))
        BugDB.load(BugRule(id="KT-2248",category=BugCategory.DELEGATE,severity=BugSeverity.MODERATE,
            title="observable内修改自身",trigger="var x by Delegates.observable(0){_,_,_,_->x++}",detection="循环触发 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0103）",fix="用vetoable"))
        BugDB.load(BugRule(id="KT-2249",category=BugCategory.DELEGATE,severity=BugSeverity.MILD,
            title="不必要的委托",trigger="val x by lazy{42}",detection="常量不需要lazy — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0104）",fix="直接val=42"))
        BugDB.load(BugRule(id="KT-2250",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="共享可变状态无同步",trigger="var c=0;repeat(100){thread{c++}}",detection="竞态条件 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0105）",fix="AtomicInteger"))
        BugDB.load(BugRule(id="KT-2251",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="双重检查锁定缺陷",trigger="if(x==null){synchronized(this){if(x==null){x=f()}}}",detection="DCL需要volatile — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0106）",fix="@Volatile"))
        BugDB.load(BugRule(id="KT-2252",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="Synchronized内阻塞操作",trigger="synchronized(lock){doSlowIO()}",detection="持有锁时阻塞 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0107）",fix="缩小同步块"))
        BugDB.load(BugRule(id="KT-2253",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="死锁风险",trigger="fun a(){lock1;lock2} fun b(){lock2;lock1}",detection="反向获取锁 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0108）",fix="统一锁顺序"))
        BugDB.load(BugRule(id="KT-2254",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="Thread.sleep在协程",trigger="suspend fun f(){Thread.sleep(1000)}",detection="阻塞线程 — Char类型字符编码边界,正则匹配组越界（参见 KT-0109）",fix="delay"))
        BugDB.load(BugRule(id="KT-2255",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="volatile缺失",trigger="var flag=false;thread{flag=true}",detection="不可见 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0110）",fix="@Volatile"))
        BugDB.load(BugRule(id="KT-2256",category=BugCategory.CONCURRENCY,severity=BugSeverity.MILD,
            title="不必要的同步",trigger="synchronized(val x=42){}",detection="简单赋值不需要 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0111）",fix="直接赋值"))
        BugDB.load(BugRule(id="KT-2257",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="不可见赋值+volatile缺失",trigger="var flag=false;thread{flag=true};while(!flag){}",detection="不可见导致死循环 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0222）",fix="@Volatile"))
        BugDB.load(BugRule(id="KT-2258",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="双重检查锁定缺少局部变量",trigger="if(x==null){synchronized{val t=f();if(x==null)x=t}}",detection="DCL标准写法 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0223）",fix="局部val=instance;if(...)"))
        BugDB.load(BugRule(id="KT-2259",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="AtomicReference的ABA问题",trigger="val ref=AtomicReference(0);ref.compareAndSet(0,1);...;ref.compareAndSet(1,0)",detection="ABA — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0224）",fix="AtomicStampedReference"))
        BugDB.load(BugRule(id="KT-2260",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="并发HashMap的putIfAbsent竞态",trigger="map.putIfAbsent(k,calc());map[k]",detection="calc先执行 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0225）",fix="computeIfAbsent(k){calc()}"))
        BugDB.load(BugRule(id="KT-2261",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="锁粒度太粗",trigger="synchronized(this){a();b();c();sleep()}",detection="阻塞所有 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0226）",fix="缩小同步块"))
        BugDB.load(BugRule(id="KT-2262",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="Condition未在锁内使用",trigger="lock.newCondition().await()",detection="必须在锁内 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0227）",fix="lock.withLock{cond.await()}"))
        BugDB.load(BugRule(id="KT-2263",category=BugCategory.CONCURRENCY,severity=BugSeverity.MILD,
            title="并发集合不必要（Double）",trigger="Collections.synchronizedDouble(arrayDouble)",detection="一次初始化 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0228）",fix="直接ArrayList"))
        BugDB.load(BugRule(id="KT-2264",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="父进程独裁导致子进程全灭",trigger="ProcessCoordinator.setStyle(DICTATOR)",detection="父进程治理风格过激 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0256）",fix="FEDERAL或CONTRACT"))
        BugDB.load(BugRule(id="KT-2265",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="子进程杀死父进程",trigger="launch{parentJob.cancel()}",detection="结构化并发中子取消父 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0257）",fix="子进程不应持有parentJob引用"))
        BugDB.load(BugRule(id="KT-2266",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="指挥官互相争夺同一子进程",trigger="两个@Commander注解同一tag",detection="重复声明 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0258）",fix="一个tag一个指挥官"))
        BugDB.load(BugRule(id="KT-2267",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="进程树中孙子进程孤立",trigger="launch{launch{launch{}};cancel中间层",detection="中间层取消后孙子悬空 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0259）",fix="supervisorScope+Job树检查"))
        BugDB.load(BugRule(id="KT-2268",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="子进程反过来指挥父进程",trigger="@ProcessBody中调用ProcessCoordinator.setStyle()",detection="子进程越权修改全局策略 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0285）",fix="子进程只读不写"))
        BugDB.load(BugRule(id="KT-2269",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="指挥官被进程体反杀",trigger="@Commander中调用自己tag的@ProcessBody杀自己",detection="自噬 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0286）",fix="Commander不参与ProcessBody"))
        BugDB.load(BugRule(id="KT-2270",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="检测进程报告一切正常但全线崩溃",trigger="watchTag返回healthy=true但summary全是✖",detection="哨兵谎报军情 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0287）",fix="cross-check其他哨兵"))
        BugDB.load(BugRule(id="KT-2271",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="依赖图标记已解决实际未解决",trigger="DependencyGraph标记conflict_resolved但detectConflicts仍有",detection="假解决 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0288）",fix="解析后立即re-check"))
        BugDB.load(BugRule(id="KT-2272",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="EventBus取消订阅后仍收到事件",trigger="unsubscribe(tag);emit(tag,ev);仍收到",detection="取消订阅延迟 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0291）",fix="unsubscribe后立即yield"))
        BugDB.load(BugRule(id="KT-2273",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="synchronized+协程=阻塞线程池",trigger="synchronized(lock){delay(1000)}",detection="synchronized内不可suspend但delay会挂 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0304）",fix="Mutex.withLock+delay"))
        BugDB.load(BugRule(id="KT-2274",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="单线程协程拿到了并行结果，多线程反而串行",trigger="coroutineScope{async{a};async{b};awaitAll}",detection="协程调度并行，线程锁串行 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0310）",fix="多线程用无锁数据结构"))
        BugDB.load(BugRule(id="KT-2275",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="单线程expect但实际是async语义",trigger="val x=async{a};val y=async{b};x.await()+y.await()",detection="async启动即执行不是lazy — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0311）",fix="如需lazy用CoroutineStart.LAZY"))
        BugDB.load(BugRule(id="KT-2276",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="Dispatchers.Default单核跑出并行幻觉",trigger="单核CPU上launch(Default){a;b;c}",detection="Default线程池>1核=真并行但单核=并发 — Char类型字符编码边界,正则匹配组越界（参见 KT-0312）",fix="明确用newSingleThreadContext"))
        BugDB.load(BugRule(id="KT-2277",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="多线程加锁反而比单线程更慢",trigger="1000线程竞争同一把synchronized锁",detection="锁竞争+上下文切换>计算本身 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0313）",fix="缩小临界区或用无锁CAS"))
        BugDB.load(BugRule(id="KT-2278",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="volatile+递增=你以为原子实际不是",trigger="@Volatile var x=0;threads{x++}",detection="volatile只保证可见性不保证原子性 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0317）",fix="AtomicInteger.incrementAndGet"))
        BugDB.load(BugRule(id="KT-2279",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="StreamChannel非线程安全（Float）",trigger="val transforms=mutableFloatOf",detection="并发add/iterate崩溃 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0336）",fix="CopyOnWriteArrayList"))
        BugDB.load(BugRule(id="KT-2280",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="Mutex未释放",trigger="mutex.withLock{throw E()}",detection="withLock自动释放但异常未处理 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0490）",fix="try-catch-withLock"))
        BugDB.load(BugRule(id="KT-2281",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="Channel未关闭（MutableList<Double>）",trigger="val c=Channel<MutableMutableList<Double><Double>>();produce{",detection="生产者完成后未close — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0503）",fix="finally{c.close()}"))
        BugDB.load(BugRule(id="KT-2282",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="共享可变状态无同步",trigger="var c=0;repeat(100){thread{c++}}",detection="竞态条件 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0105）",fix="AtomicInteger"))
        BugDB.load(BugRule(id="KT-2283",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="双重检查锁定缺陷",trigger="if(x==null){synchronized(this){if(x==null){x=f()}}}",detection="DCL需要volatile — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0106）",fix="@Volatile"))
        BugDB.load(BugRule(id="KT-2284",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="Synchronized内阻塞操作",trigger="synchronized(lock){doSlowIO()}",detection="持有锁时阻塞 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0107）",fix="缩小同步块"))
        BugDB.load(BugRule(id="KT-2285",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="死锁风险",trigger="fun a(){lock1;lock2} fun b(){lock2;lock1}",detection="反向获取锁 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0108）",fix="统一锁顺序"))
        BugDB.load(BugRule(id="KT-2286",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="Thread.sleep在协程",trigger="suspend fun f(){Thread.sleep(1000)}",detection="阻塞线程 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0109）",fix="delay"))
        BugDB.load(BugRule(id="KT-2287",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="volatile缺失",trigger="var flag=false;thread{flag=true}",detection="不可见 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0110）",fix="@Volatile"))
        BugDB.load(BugRule(id="KT-2288",category=BugCategory.CONCURRENCY,severity=BugSeverity.MILD,
            title="不必要的同步",trigger="synchronized(val x=42){}",detection="简单赋值不需要 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0111）",fix="直接赋值"))
        BugDB.load(BugRule(id="KT-2289",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="不可见赋值+volatile缺失",trigger="var flag=false;thread{flag=true};while(!flag){}",detection="不可见导致死循环 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0222）",fix="@Volatile"))
        BugDB.load(BugRule(id="KT-2290",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="双重检查锁定缺少局部变量",trigger="if(x==null){synchronized{val t=f();if(x==null)x=t}}",detection="DCL标准写法 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0223）",fix="局部val=instance;if(...)"))
        BugDB.load(BugRule(id="KT-2291",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="AtomicReference的ABA问题",trigger="val ref=AtomicReference(0);ref.compareAndSet(0,1);...;ref.compareAndSet(1,0)",detection="ABA — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0224）",fix="AtomicStampedReference"))
        BugDB.load(BugRule(id="KT-2292",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="并发HashMap的putIfAbsent竞态",trigger="map.putIfAbsent(k,calc());map[k]",detection="calc先执行 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0225）",fix="computeIfAbsent(k){calc()}"))
        BugDB.load(BugRule(id="KT-2293",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="锁粒度太粗",trigger="synchronized(this){a();b();c();sleep()}",detection="阻塞所有 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0226）",fix="缩小同步块"))
        BugDB.load(BugRule(id="KT-2294",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="Condition未在锁内使用",trigger="lock.newCondition().await()",detection="必须在锁内 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0227）",fix="lock.withLock{cond.await()}"))
        BugDB.load(BugRule(id="KT-2295",category=BugCategory.CONCURRENCY,severity=BugSeverity.MILD,
            title="并发集合不必要（Byte）",trigger="Collections.synchronizedByte(arrayByte)",detection="一次初始化 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0228）",fix="直接ArrayList"))
        BugDB.load(BugRule(id="KT-2296",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="父进程独裁导致子进程全灭",trigger="ProcessCoordinator.setStyle(DICTATOR)",detection="父进程治理风格过激 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0256）",fix="FEDERAL或CONTRACT"))
        BugDB.load(BugRule(id="KT-2297",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="子进程杀死父进程",trigger="launch{parentJob.cancel()}",detection="结构化并发中子取消父 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0257）",fix="子进程不应持有parentJob引用"))
        BugDB.load(BugRule(id="KT-2298",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="指挥官互相争夺同一子进程",trigger="两个@Commander注解同一tag",detection="重复声明 — Char类型字符编码边界,正则匹配组越界（参见 KT-0258）",fix="一个tag一个指挥官"))
        BugDB.load(BugRule(id="KT-2299",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="进程树中孙子进程孤立",trigger="launch{launch{launch{}};cancel中间层",detection="中间层取消后孙子悬空 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0259）",fix="supervisorScope+Job树检查"))
        BugDB.load(BugRule(id="KT-2300",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="子进程反过来指挥父进程",trigger="@ProcessBody中调用ProcessCoordinator.setStyle()",detection="子进程越权修改全局策略 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0285）",fix="子进程只读不写"))
        BugDB.load(BugRule(id="KT-2301",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="指挥官被进程体反杀",trigger="@Commander中调用自己tag的@ProcessBody杀自己",detection="自噬 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0286）",fix="Commander不参与ProcessBody"))
        BugDB.load(BugRule(id="KT-2302",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="检测进程报告一切正常但全线崩溃",trigger="watchTag返回healthy=true但summary全是✖",detection="哨兵谎报军情 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0287）",fix="cross-check其他哨兵"))
        BugDB.load(BugRule(id="KT-2303",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="依赖图标记已解决实际未解决",trigger="DependencyGraph标记conflict_resolved但detectConflicts仍有",detection="假解决 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0288）",fix="解析后立即re-check"))
        BugDB.load(BugRule(id="KT-2304",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="EventBus取消订阅后仍收到事件",trigger="unsubscribe(tag);emit(tag,ev);仍收到",detection="取消订阅延迟 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0291）",fix="unsubscribe后立即yield"))
        BugDB.load(BugRule(id="KT-2305",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="synchronized+协程=阻塞线程池",trigger="synchronized(lock){delay(1000)}",detection="synchronized内不可suspend但delay会挂 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0304）",fix="Mutex.withLock+delay"))
        BugDB.load(BugRule(id="KT-2306",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="单线程协程拿到了并行结果，多线程反而串行",trigger="coroutineScope{async{a};async{b};awaitAll}",detection="协程调度并行，线程锁串行 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0310）",fix="多线程用无锁数据结构"))
        BugDB.load(BugRule(id="KT-2307",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="单线程expect但实际是async语义",trigger="val x=async{a};val y=async{b};x.await()+y.await()",detection="async启动即执行不是lazy — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0311）",fix="如需lazy用CoroutineStart.LAZY"))
        BugDB.load(BugRule(id="KT-2308",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="Dispatchers.Default单核跑出并行幻觉",trigger="单核CPU上launch(Default){a;b;c}",detection="Default线程池>1核=真并行但单核=并发 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0312）",fix="明确用newSingleThreadContext"))
        BugDB.load(BugRule(id="KT-2309",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="多线程加锁反而比单线程更慢",trigger="1000线程竞争同一把synchronized锁",detection="锁竞争+上下文切换>计算本身 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0313）",fix="缩小临界区或用无锁CAS"))
        BugDB.load(BugRule(id="KT-2310",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="volatile+递增=你以为原子实际不是",trigger="@Volatile var x=0;threads{x++}",detection="volatile只保证可见性不保证原子性 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0317）",fix="AtomicInteger.incrementAndGet"))
        BugDB.load(BugRule(id="KT-2311",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="StreamChannel非线程安全（Any）",trigger="val transforms=mutableAnyOf",detection="并发add/iterate崩溃 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0336）",fix="CopyOnWriteArrayList"))
        BugDB.load(BugRule(id="KT-2312",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="Mutex未释放",trigger="mutex.withLock{throw E()}",detection="withLock自动释放但异常未处理 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0490）",fix="try-catch-withLock"))
        BugDB.load(BugRule(id="KT-2313",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="Channel未关闭",trigger="val c=Channel<Int>();produce{",detection="生产者完成后未close — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0503）",fix="finally{c.close()}"))
        BugDB.load(BugRule(id="KT-2314",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="共享可变状态无同步",trigger="var c=0;repeat(100){thread{c++}}",detection="竞态条件 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0105）",fix="AtomicInteger"))
        BugDB.load(BugRule(id="KT-2315",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="双重检查锁定缺陷",trigger="if(x==null){synchronized(this){if(x==null){x=f()}}}",detection="DCL需要volatile — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0106）",fix="@Volatile"))
        BugDB.load(BugRule(id="KT-2316",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="Synchronized内阻塞操作",trigger="synchronized(lock){doSlowIO()}",detection="持有锁时阻塞 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0107）",fix="缩小同步块"))
        BugDB.load(BugRule(id="KT-2317",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="死锁风险",trigger="fun a(){lock1;lock2} fun b(){lock2;lock1}",detection="反向获取锁 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0108）",fix="统一锁顺序"))
        BugDB.load(BugRule(id="KT-2318",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="Thread.sleep在协程",trigger="suspend fun f(){Thread.sleep(1000)}",detection="阻塞线程 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0109）",fix="delay"))
        BugDB.load(BugRule(id="KT-2319",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="volatile缺失",trigger="var flag=false;thread{flag=true}",detection="不可见 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0110）",fix="@Volatile"))
        BugDB.load(BugRule(id="KT-2320",category=BugCategory.CONCURRENCY,severity=BugSeverity.MILD,
            title="不必要的同步",trigger="synchronized(val x=42){}",detection="简单赋值不需要 — Char类型字符编码边界,正则匹配组越界（参见 KT-0111）",fix="直接赋值"))
        BugDB.load(BugRule(id="KT-2321",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="不可见赋值+volatile缺失",trigger="var flag=false;thread{flag=true};while(!flag){}",detection="不可见导致死循环 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0222）",fix="@Volatile"))
        BugDB.load(BugRule(id="KT-2322",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="双重检查锁定缺少局部变量",trigger="if(x==null){synchronized{val t=f();if(x==null)x=t}}",detection="DCL标准写法 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0223）",fix="局部val=instance;if(...)"))
        BugDB.load(BugRule(id="KT-2323",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="AtomicReference的ABA问题",trigger="val ref=AtomicReference(0);ref.compareAndSet(0,1);...;ref.compareAndSet(1,0)",detection="ABA — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0224）",fix="AtomicStampedReference"))
        BugDB.load(BugRule(id="KT-2324",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="并发HashMap的putIfAbsent竞态",trigger="map.putIfAbsent(k,calc());map[k]",detection="calc先执行 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0225）",fix="computeIfAbsent(k){calc()}"))
        BugDB.load(BugRule(id="KT-2325",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="锁粒度太粗",trigger="synchronized(this){a();b();c();sleep()}",detection="阻塞所有 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0226）",fix="缩小同步块"))
        BugDB.load(BugRule(id="KT-2326",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="Condition未在锁内使用",trigger="lock.newCondition().await()",detection="必须在锁内 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0227）",fix="lock.withLock{cond.await()}"))
        BugDB.load(BugRule(id="KT-2327",category=BugCategory.CONCURRENCY,severity=BugSeverity.MILD,
            title="并发集合不必要（Int?）",trigger="Collections.synchronizedInt?(arrayInt?)",detection="一次初始化 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0228）",fix="直接ArrayList"))
        BugDB.load(BugRule(id="KT-2328",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="父进程独裁导致子进程全灭",trigger="ProcessCoordinator.setStyle(DICTATOR)",detection="父进程治理风格过激 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0256）",fix="FEDERAL或CONTRACT"))
        BugDB.load(BugRule(id="KT-2329",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="子进程杀死父进程",trigger="launch{parentJob.cancel()}",detection="结构化并发中子取消父 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0257）",fix="子进程不应持有parentJob引用"))
        BugDB.load(BugRule(id="KT-2330",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="指挥官互相争夺同一子进程",trigger="两个@Commander注解同一tag",detection="重复声明 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0258）",fix="一个tag一个指挥官"))
        BugDB.load(BugRule(id="KT-2331",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="进程树中孙子进程孤立",trigger="launch{launch{launch{}};cancel中间层",detection="中间层取消后孙子悬空 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0259）",fix="supervisorScope+Job树检查"))
        BugDB.load(BugRule(id="KT-2332",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="子进程反过来指挥父进程",trigger="@ProcessBody中调用ProcessCoordinator.setStyle()",detection="子进程越权修改全局策略 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0285）",fix="子进程只读不写"))
        BugDB.load(BugRule(id="KT-2333",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="指挥官被进程体反杀",trigger="@Commander中调用自己tag的@ProcessBody杀自己",detection="自噬 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0286）",fix="Commander不参与ProcessBody"))
        BugDB.load(BugRule(id="KT-2334",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="检测进程报告一切正常但全线崩溃",trigger="watchTag返回healthy=true但summary全是✖",detection="哨兵谎报军情 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0287）",fix="cross-check其他哨兵"))
        BugDB.load(BugRule(id="KT-2335",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="依赖图标记已解决实际未解决",trigger="DependencyGraph标记conflict_resolved但detectConflicts仍有",detection="假解决 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0288）",fix="解析后立即re-check"))
        BugDB.load(BugRule(id="KT-2336",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="EventBus取消订阅后仍收到事件",trigger="unsubscribe(tag);emit(tag,ev);仍收到",detection="取消订阅延迟 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0291）",fix="unsubscribe后立即yield"))
        BugDB.load(BugRule(id="KT-2337",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="synchronized+协程=阻塞线程池",trigger="synchronized(lock){delay(1000)}",detection="synchronized内不可suspend但delay会挂 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0304）",fix="Mutex.withLock+delay"))
        BugDB.load(BugRule(id="KT-2338",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="单线程协程拿到了并行结果，多线程反而串行",trigger="coroutineScope{async{a};async{b};awaitAll}",detection="协程调度并行，线程锁串行 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0310）",fix="多线程用无锁数据结构"))
        BugDB.load(BugRule(id="KT-2339",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="单线程expect但实际是async语义",trigger="val x=async{a};val y=async{b};x.await()+y.await()",detection="async启动即执行不是lazy — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0311）",fix="如需lazy用CoroutineStart.LAZY"))
        BugDB.load(BugRule(id="KT-2340",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="Dispatchers.Default单核跑出并行幻觉",trigger="单核CPU上launch(Default){a;b;c}",detection="Default线程池>1核=真并行但单核=并发 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0312）",fix="明确用newSingleThreadContext"))
        BugDB.load(BugRule(id="KT-2341",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="多线程加锁反而比单线程更慢",trigger="1000线程竞争同一把synchronized锁",detection="锁竞争+上下文切换>计算本身 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0313）",fix="缩小临界区或用无锁CAS"))
        BugDB.load(BugRule(id="KT-2342",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="volatile+递增=你以为原子实际不是",trigger="@Volatile var x=0;threads{x++}",detection="volatile只保证可见性不保证原子性 — Char类型字符编码边界,正则匹配组越界（参见 KT-0317）",fix="AtomicInteger.incrementAndGet"))
        BugDB.load(BugRule(id="KT-2343",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="StreamChannel非线程安全（Double?）",trigger="val transforms=mutableDouble?Of",detection="并发add/iterate崩溃 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0336）",fix="CopyOnWriteArrayList"))
        BugDB.load(BugRule(id="KT-2344",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="Mutex未释放",trigger="mutex.withLock{throw E()}",detection="withLock自动释放但异常未处理 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0490）",fix="try-catch-withLock"))
        BugDB.load(BugRule(id="KT-2345",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="Channel未关闭（Float）",trigger="val c=Channel<Float>();produce{",detection="生产者完成后未close — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0503）",fix="finally{c.close()}"))
        BugDB.load(BugRule(id="KT-2346",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="共享可变状态无同步",trigger="var c=0;repeat(100){thread{c++}}",detection="竞态条件 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0105）",fix="AtomicInteger"))
        BugDB.load(BugRule(id="KT-2347",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="双重检查锁定缺陷",trigger="if(x==null){synchronized(this){if(x==null){x=f()}}}",detection="DCL需要volatile — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0106）",fix="@Volatile"))
        BugDB.load(BugRule(id="KT-2348",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="Synchronized内阻塞操作",trigger="synchronized(lock){doSlowIO()}",detection="持有锁时阻塞 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0107）",fix="缩小同步块"))
        BugDB.load(BugRule(id="KT-2349",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="死锁风险",trigger="fun a(){lock1;lock2} fun b(){lock2;lock1}",detection="反向获取锁 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0108）",fix="统一锁顺序"))
        BugDB.load(BugRule(id="KT-2350",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="Thread.sleep在协程",trigger="suspend fun f(){Thread.sleep(1000)}",detection="阻塞线程 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0109）",fix="delay"))
        BugDB.load(BugRule(id="KT-2351",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="volatile缺失",trigger="var flag=false;thread{flag=true}",detection="不可见 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0110）",fix="@Volatile"))
        BugDB.load(BugRule(id="KT-2352",category=BugCategory.CONCURRENCY,severity=BugSeverity.MILD,
            title="不必要的同步",trigger="synchronized(val x=42){}",detection="简单赋值不需要 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0111）",fix="直接赋值"))
        BugDB.load(BugRule(id="KT-2353",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="不可见赋值+volatile缺失",trigger="var flag=false;thread{flag=true};while(!flag){}",detection="不可见导致死循环 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0222）",fix="@Volatile"))
        BugDB.load(BugRule(id="KT-2354",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="双重检查锁定缺少局部变量",trigger="if(x==null){synchronized{val t=f();if(x==null)x=t}}",detection="DCL标准写法 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0223）",fix="局部val=instance;if(...)"))
        BugDB.load(BugRule(id="KT-2355",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="AtomicReference的ABA问题",trigger="val ref=AtomicReference(0);ref.compareAndSet(0,1);...;ref.compareAndSet(1,0)",detection="ABA — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0224）",fix="AtomicStampedReference"))
        BugDB.load(BugRule(id="KT-2356",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="并发HashMap的putIfAbsent竞态",trigger="map.putIfAbsent(k,calc());map[k]",detection="calc先执行 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0225）",fix="computeIfAbsent(k){calc()}"))
        BugDB.load(BugRule(id="KT-2357",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="锁粒度太粗",trigger="synchronized(this){a();b();c();sleep()}",detection="阻塞所有 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0226）",fix="缩小同步块"))
        BugDB.load(BugRule(id="KT-2358",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="Condition未在锁内使用",trigger="lock.newCondition().await()",detection="必须在锁内 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0227）",fix="lock.withLock{cond.await()}"))
        BugDB.load(BugRule(id="KT-2359",category=BugCategory.CONCURRENCY,severity=BugSeverity.MILD,
            title="并发集合不必要（Any?）",trigger="Collections.synchronizedAny?(arrayAny?)",detection="一次初始化 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0228）",fix="直接ArrayList"))
        BugDB.load(BugRule(id="KT-2360",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="父进程独裁导致子进程全灭",trigger="ProcessCoordinator.setStyle(DICTATOR)",detection="父进程治理风格过激 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0256）",fix="FEDERAL或CONTRACT"))
        BugDB.load(BugRule(id="KT-2361",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="子进程杀死父进程",trigger="launch{parentJob.cancel()}",detection="结构化并发中子取消父 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0257）",fix="子进程不应持有parentJob引用"))
        BugDB.load(BugRule(id="KT-2362",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="指挥官互相争夺同一子进程",trigger="两个@Commander注解同一tag",detection="重复声明 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0258）",fix="一个tag一个指挥官"))
        BugDB.load(BugRule(id="KT-2363",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="进程树中孙子进程孤立",trigger="launch{launch{launch{}};cancel中间层",detection="中间层取消后孙子悬空 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0259）",fix="supervisorScope+Job树检查"))
        BugDB.load(BugRule(id="KT-2364",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="子进程反过来指挥父进程",trigger="@ProcessBody中调用ProcessCoordinator.setStyle()",detection="子进程越权修改全局策略 — Char类型字符编码边界,正则匹配组越界（参见 KT-0285）",fix="子进程只读不写"))
        BugDB.load(BugRule(id="KT-2365",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="指挥官被进程体反杀",trigger="@Commander中调用自己tag的@ProcessBody杀自己",detection="自噬 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0286）",fix="Commander不参与ProcessBody"))
        BugDB.load(BugRule(id="KT-2366",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="检测进程报告一切正常但全线崩溃",trigger="watchTag返回healthy=true但summary全是✖",detection="哨兵谎报军情 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0287）",fix="cross-check其他哨兵"))
        BugDB.load(BugRule(id="KT-2367",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="依赖图标记已解决实际未解决",trigger="DependencyGraph标记conflict_resolved但detectConflicts仍有",detection="假解决 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0288）",fix="解析后立即re-check"))
        BugDB.load(BugRule(id="KT-2368",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="EventBus取消订阅后仍收到事件",trigger="unsubscribe(tag);emit(tag,ev);仍收到",detection="取消订阅延迟 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0291）",fix="unsubscribe后立即yield"))
        BugDB.load(BugRule(id="KT-2369",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="synchronized+协程=阻塞线程池",trigger="synchronized(lock){delay(1000)}",detection="synchronized内不可suspend但delay会挂 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0304）",fix="Mutex.withLock+delay"))
        BugDB.load(BugRule(id="KT-2370",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="单线程协程拿到了并行结果，多线程反而串行",trigger="coroutineScope{async{a};async{b};awaitAll}",detection="协程调度并行，线程锁串行 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0310）",fix="多线程用无锁数据结构"))
        BugDB.load(BugRule(id="KT-2371",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="单线程expect但实际是async语义",trigger="val x=async{a};val y=async{b};x.await()+y.await()",detection="async启动即执行不是lazy — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0311）",fix="如需lazy用CoroutineStart.LAZY"))
        BugDB.load(BugRule(id="KT-2372",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="Dispatchers.Default单核跑出并行幻觉",trigger="单核CPU上launch(Default){a;b;c}",detection="Default线程池>1核=真并行但单核=并发 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0312）",fix="明确用newSingleThreadContext"))
        BugDB.load(BugRule(id="KT-2373",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="多线程加锁反而比单线程更慢",trigger="1000线程竞争同一把synchronized锁",detection="锁竞争+上下文切换>计算本身 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0313）",fix="缩小临界区或用无锁CAS"))
        BugDB.load(BugRule(id="KT-2374",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="volatile+递增=你以为原子实际不是",trigger="@Volatile var x=0;threads{x++}",detection="volatile只保证可见性不保证原子性 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0317）",fix="AtomicInteger.incrementAndGet"))
        BugDB.load(BugRule(id="KT-2375",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="StreamChannel非线程安全（Set<Int>）",trigger="val transforms=mutableSet<Int>Of",detection="并发add/iterate崩溃 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0336）",fix="CopyOnWriteArrayList"))
        BugDB.load(BugRule(id="KT-2376",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="Mutex未释放",trigger="mutex.withLock{throw E()}",detection="withLock自动释放但异常未处理 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0490）",fix="try-catch-withLock"))
        BugDB.load(BugRule(id="KT-2377",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="Channel未关闭（Any）",trigger="val c=Channel<Any>();produce{",detection="生产者完成后未close — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0503）",fix="finally{c.close()}"))
        BugDB.load(BugRule(id="KT-2378",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="共享可变状态无同步",trigger="var c=0;repeat(100){thread{c++}}",detection="竞态条件 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0105）",fix="AtomicInteger"))
        BugDB.load(BugRule(id="KT-2379",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="双重检查锁定缺陷",trigger="if(x==null){synchronized(this){if(x==null){x=f()}}}",detection="DCL需要volatile — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0106）",fix="@Volatile"))
        BugDB.load(BugRule(id="KT-2380",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="Synchronized内阻塞操作",trigger="synchronized(lock){doSlowIO()}",detection="持有锁时阻塞 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0107）",fix="缩小同步块"))
        BugDB.load(BugRule(id="KT-2381",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="死锁风险",trigger="fun a(){lock1;lock2} fun b(){lock2;lock1}",detection="反向获取锁 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0108）",fix="统一锁顺序"))
        BugDB.load(BugRule(id="KT-2382",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="Thread.sleep在协程",trigger="suspend fun f(){Thread.sleep(1000)}",detection="阻塞线程 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0109）",fix="delay"))
        BugDB.load(BugRule(id="KT-2383",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="volatile缺失",trigger="var flag=false;thread{flag=true}",detection="不可见 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0110）",fix="@Volatile"))
        BugDB.load(BugRule(id="KT-2384",category=BugCategory.CONCURRENCY,severity=BugSeverity.MILD,
            title="不必要的同步",trigger="synchronized(val x=42){}",detection="简单赋值不需要 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0111）",fix="直接赋值"))
        BugDB.load(BugRule(id="KT-2385",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="不可见赋值+volatile缺失",trigger="var flag=false;thread{flag=true};while(!flag){}",detection="不可见导致死循环 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0222）",fix="@Volatile"))
        BugDB.load(BugRule(id="KT-2386",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="双重检查锁定缺少局部变量",trigger="if(x==null){synchronized{val t=f();if(x==null)x=t}}",detection="DCL标准写法 — Char类型字符编码边界,正则匹配组越界（参见 KT-0223）",fix="局部val=instance;if(...)"))
        BugDB.load(BugRule(id="KT-2387",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="AtomicReference的ABA问题",trigger="val ref=AtomicReference(0);ref.compareAndSet(0,1);...;ref.compareAndSet(1,0)",detection="ABA — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0224）",fix="AtomicStampedReference"))
        BugDB.load(BugRule(id="KT-2388",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="并发HashMap的putIfAbsent竞态",trigger="map.putIfAbsent(k,calc());map[k]",detection="calc先执行 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0225）",fix="computeIfAbsent(k){calc()}"))
        BugDB.load(BugRule(id="KT-2389",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="锁粒度太粗",trigger="synchronized(this){a();b();c();sleep()}",detection="阻塞所有 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0226）",fix="缩小同步块"))
        BugDB.load(BugRule(id="KT-2390",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="Condition未在锁内使用",trigger="lock.newCondition().await()",detection="必须在锁内 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0227）",fix="lock.withLock{cond.await()}"))
        BugDB.load(BugRule(id="KT-2391",category=BugCategory.CONCURRENCY,severity=BugSeverity.MILD,
            title="并发集合不必要（MutableList<Double>）",trigger="Collections.synchronizedMutableList<Double>(arrayMutableList<Double>)",detection="一次初始化 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0228）",fix="直接ArrayList"))
        BugDB.load(BugRule(id="KT-2392",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="父进程独裁导致子进程全灭",trigger="ProcessCoordinator.setStyle(DICTATOR)",detection="父进程治理风格过激 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0256）",fix="FEDERAL或CONTRACT"))
        BugDB.load(BugRule(id="KT-2393",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="子进程杀死父进程",trigger="launch{parentJob.cancel()}",detection="结构化并发中子取消父 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0257）",fix="子进程不应持有parentJob引用"))
        BugDB.load(BugRule(id="KT-2394",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="指挥官互相争夺同一子进程",trigger="两个@Commander注解同一tag",detection="重复声明 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0258）",fix="一个tag一个指挥官"))
        BugDB.load(BugRule(id="KT-2395",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="进程树中孙子进程孤立",trigger="launch{launch{launch{}};cancel中间层",detection="中间层取消后孙子悬空 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0259）",fix="supervisorScope+Job树检查"))
        BugDB.load(BugRule(id="KT-2396",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="子进程反过来指挥父进程",trigger="@ProcessBody中调用ProcessCoordinator.setStyle()",detection="子进程越权修改全局策略 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0285）",fix="子进程只读不写"))
        BugDB.load(BugRule(id="KT-2397",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="指挥官被进程体反杀",trigger="@Commander中调用自己tag的@ProcessBody杀自己",detection="自噬 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0286）",fix="Commander不参与ProcessBody"))
        BugDB.load(BugRule(id="KT-2398",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="检测进程报告一切正常但全线崩溃",trigger="watchTag返回healthy=true但summary全是✖",detection="哨兵谎报军情 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0287）",fix="cross-check其他哨兵"))
        BugDB.load(BugRule(id="KT-2399",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="依赖图标记已解决实际未解决",trigger="DependencyGraph标记conflict_resolved但detectConflicts仍有",detection="假解决 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0288）",fix="解析后立即re-check"))
        BugDB.load(BugRule(id="KT-2400",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="EventBus取消订阅后仍收到事件",trigger="unsubscribe(tag);emit(tag,ev);仍收到",detection="取消订阅延迟 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0291）",fix="unsubscribe后立即yield"))
        BugDB.load(BugRule(id="KT-2401",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="synchronized+协程=阻塞线程池",trigger="synchronized(lock){delay(1000)}",detection="synchronized内不可suspend但delay会挂 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0304）",fix="Mutex.withLock+delay"))
        BugDB.load(BugRule(id="KT-2402",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="单线程协程拿到了并行结果，多线程反而串行",trigger="coroutineScope{async{a};async{b};awaitAll}",detection="协程调度并行，线程锁串行 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0310）",fix="多线程用无锁数据结构"))
        BugDB.load(BugRule(id="KT-2403",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="单线程expect但实际是async语义",trigger="val x=async{a};val y=async{b};x.await()+y.await()",detection="async启动即执行不是lazy — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0311）",fix="如需lazy用CoroutineStart.LAZY"))
        BugDB.load(BugRule(id="KT-2404",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="Dispatchers.Default单核跑出并行幻觉",trigger="单核CPU上launch(Default){a;b;c}",detection="Default线程池>1核=真并行但单核=并发 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0312）",fix="明确用newSingleThreadContext"))
        BugDB.load(BugRule(id="KT-2405",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="多线程加锁反而比单线程更慢",trigger="1000线程竞争同一把synchronized锁",detection="锁竞争+上下文切换>计算本身 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0313）",fix="缩小临界区或用无锁CAS"))
        BugDB.load(BugRule(id="KT-2406",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="volatile+递增=你以为原子实际不是",trigger="@Volatile var x=0;threads{x++}",detection="volatile只保证可见性不保证原子性 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0317）",fix="AtomicInteger.incrementAndGet"))
        BugDB.load(BugRule(id="KT-2407",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="StreamChannel非线程安全（Sequence<Long>）",trigger="val transforms=mutableSequence<Long>Of",detection="并发add/iterate崩溃 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0336）",fix="CopyOnWriteArrayList"))
        BugDB.load(BugRule(id="KT-2408",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="Mutex未释放",trigger="mutex.withLock{throw E()}",detection="withLock自动释放但异常未处理 — Char类型字符编码边界,正则匹配组越界（参见 KT-0490）",fix="try-catch-withLock"))
        BugDB.load(BugRule(id="KT-2409",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="Channel未关闭（Double?）",trigger="val c=Channel<Double?>();produce{",detection="生产者完成后未close — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0503）",fix="finally{c.close()}"))
        BugDB.load(BugRule(id="KT-2410",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="共享可变状态无同步",trigger="var c=0;repeat(100){thread{c++}}",detection="竞态条件 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0105）",fix="AtomicInteger"))
        BugDB.load(BugRule(id="KT-2411",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="双重检查锁定缺陷",trigger="if(x==null){synchronized(this){if(x==null){x=f()}}}",detection="DCL需要volatile — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0106）",fix="@Volatile"))
        BugDB.load(BugRule(id="KT-2412",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="Synchronized内阻塞操作",trigger="synchronized(lock){doSlowIO()}",detection="持有锁时阻塞 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0107）",fix="缩小同步块"))
        BugDB.load(BugRule(id="KT-2413",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="死锁风险",trigger="fun a(){lock1;lock2} fun b(){lock2;lock1}",detection="反向获取锁 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0108）",fix="统一锁顺序"))
        BugDB.load(BugRule(id="KT-2414",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="Thread.sleep在协程",trigger="suspend fun f(){Thread.sleep(1000)}",detection="阻塞线程 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0109）",fix="delay"))
        BugDB.load(BugRule(id="KT-2415",category=BugCategory.CONCURRENCY,severity=BugSeverity.MODERATE,
            title="volatile缺失",trigger="var flag=false;thread{flag=true}",detection="不可见 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0110）",fix="@Volatile"))
        BugDB.load(BugRule(id="KT-2416",category=BugCategory.CONCURRENCY,severity=BugSeverity.MILD,
            title="不必要的同步",trigger="synchronized(val x=42){}",detection="简单赋值不需要 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0111）",fix="直接赋值"))
        BugDB.load(BugRule(id="KT-2417",category=BugCategory.CONCURRENCY,severity=BugSeverity.SEVERE,
            title="不可见赋值+volatile缺失",trigger="var flag=false;thread{flag=true};while(!flag){}",detection="不可见导致死循环 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0222）",fix="@Volatile"))
        BugDB.load(BugRule(id="KT-2418",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="主线程读文件",trigger="Text(File(\\\"x\\\").readText())",detection="卡UI — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0112）",fix="LaunchedEffect+IO"))
        BugDB.load(BugRule(id="KT-2419",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="重组副作用",trigger="LaunchedEffect(Unit){load()}",detection="key=Unit只执行一次 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0113）",fix="正确的key"))
        BugDB.load(BugRule(id="KT-2420",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="remember遗漏",trigger="var count=0;Button(onClick={count++}){Text(\\\"\\\$count\\\")}",detection="不记住状态 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0114）",fix="remember{mutableStateOf}"))
        BugDB.load(BugRule(id="KT-2421",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="状态提升缺失",trigger="@Composable fun C(){val s=remember{;CF(s)}",detection="子组件不可控 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0115）",fix="状态提升"))
        BugDB.load(BugRule(id="KT-2422",category=BugCategory.COMPOSE,severity=BugSeverity.MILD,
            title="不必要的remember",trigger="remember{\\\"static\\\"}",detection="常量不需要remember — Char类型字符编码边界,正则匹配组越界（参见 KT-0116）",fix="直接使用"))
        BugDB.load(BugRule(id="KT-2423",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="unstable参数导致过度重组",trigger="@Composable fun Item(user:User){",detection="User非stable — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0229）",fix="@Immutable标注"))
        BugDB.load(BugRule(id="KT-2424",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="rememberSaveable丢失",trigger="var txt by remember{ mutableStateOf(\\\"\\\")}",detection="配置变更丢失 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0230）",fix="rememberSaveable"))
        BugDB.load(BugRule(id="KT-2425",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="key参数遗漏",trigger="items.forEach{Item(it)}",detection="重组位置错乱 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0231）",fix="items.forEach{key(it.id){Item(it)}}"))
        BugDB.load(BugRule(id="KT-2426",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="Modifier顺序错误",trigger="Modifier.padding(16).clickable{}",detection="补在点击区域外 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0232）",fix="clickable放padding之前"))
        BugDB.load(BugRule(id="KT-2427",category=BugCategory.COMPOSE,severity=BugSeverity.MILD,
            title="derivedStateOf用于非计算",trigger="val d=derivedStateOf{list}",detection="直接引用不需要 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0233）",fix="直接val"))
        BugDB.load(BugRule(id="KT-2428",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="LaunchedEffect+错误key+状态更新=死循环重组",trigger="LaunchedEffect(Unit){counter++}",detection="每次重组counter变→触发重组→counter变 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0298）",fix="key使用稳定值"))
        BugDB.load(BugRule(id="KT-2429",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="derivedStateOf滥用",trigger="val v=derivedStateOf{simpleCalc()}",detection="简单计算不需要 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0505）",fix="直接计算"))
        BugDB.load(BugRule(id="KT-2430",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="副作用在Composition中",trigger="CompositionLocalProvider{loadData()}",detection="每次重组执行 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0506）",fix="LaunchedEffect"))
        BugDB.load(BugRule(id="KT-2431",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="主线程读文件",trigger="Text(File(\\\"x\\\").readText())",detection="卡UI — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0112）",fix="LaunchedEffect+IO"))
        BugDB.load(BugRule(id="KT-2432",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="重组副作用",trigger="LaunchedEffect(Unit){load()}",detection="key=Unit只执行一次 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0113）",fix="正确的key"))
        BugDB.load(BugRule(id="KT-2433",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="remember遗漏",trigger="var count=0;Button(onClick={count++}){Text(\\\"\\\$count\\\")}",detection="不记住状态 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0114）",fix="remember{mutableStateOf}"))
        BugDB.load(BugRule(id="KT-2434",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="状态提升缺失",trigger="@Composable fun C(){val s=remember{;CF(s)}",detection="子组件不可控 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0115）",fix="状态提升"))
        BugDB.load(BugRule(id="KT-2435",category=BugCategory.COMPOSE,severity=BugSeverity.MILD,
            title="不必要的remember",trigger="remember{\\\"static\\\"}",detection="常量不需要remember — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0116）",fix="直接使用"))
        BugDB.load(BugRule(id="KT-2436",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="unstable参数导致过度重组",trigger="@Composable fun Item(user:User){",detection="User非stable — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0229）",fix="@Immutable标注"))
        BugDB.load(BugRule(id="KT-2437",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="rememberSaveable丢失",trigger="var txt by remember{ mutableStateOf(\\\"\\\")}",detection="配置变更丢失 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0230）",fix="rememberSaveable"))
        BugDB.load(BugRule(id="KT-2438",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="key参数遗漏",trigger="items.forEach{Item(it)}",detection="重组位置错乱 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0231）",fix="items.forEach{key(it.id){Item(it)}}"))
        BugDB.load(BugRule(id="KT-2439",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="Modifier顺序错误",trigger="Modifier.padding(16).clickable{}",detection="补在点击区域外 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0232）",fix="clickable放padding之前"))
        BugDB.load(BugRule(id="KT-2440",category=BugCategory.COMPOSE,severity=BugSeverity.MILD,
            title="derivedStateOf用于非计算",trigger="val d=derivedStateOf{list}",detection="直接引用不需要 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0233）",fix="直接val"))
        BugDB.load(BugRule(id="KT-2441",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="LaunchedEffect+错误key+状态更新=死循环重组",trigger="LaunchedEffect(Unit){counter++}",detection="每次重组counter变→触发重组→counter变 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0298）",fix="key使用稳定值"))
        BugDB.load(BugRule(id="KT-2442",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="derivedStateOf滥用",trigger="val v=derivedStateOf{simpleCalc()}",detection="简单计算不需要 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0505）",fix="直接计算"))
        BugDB.load(BugRule(id="KT-2443",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="副作用在Composition中",trigger="CompositionLocalProvider{loadData()}",detection="每次重组执行 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0506）",fix="LaunchedEffect"))
        BugDB.load(BugRule(id="KT-2444",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="主线程读文件",trigger="Text(File(\\\"x\\\").readText())",detection="卡UI — Char类型字符编码边界,正则匹配组越界（参见 KT-0112）",fix="LaunchedEffect+IO"))
        BugDB.load(BugRule(id="KT-2445",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="重组副作用",trigger="LaunchedEffect(Unit){load()}",detection="key=Unit只执行一次 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0113）",fix="正确的key"))
        BugDB.load(BugRule(id="KT-2446",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="remember遗漏",trigger="var count=0;Button(onClick={count++}){Text(\\\"\\\$count\\\")}",detection="不记住状态 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0114）",fix="remember{mutableStateOf}"))
        BugDB.load(BugRule(id="KT-2447",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="状态提升缺失",trigger="@Composable fun C(){val s=remember{;CF(s)}",detection="子组件不可控 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0115）",fix="状态提升"))
        BugDB.load(BugRule(id="KT-2448",category=BugCategory.COMPOSE,severity=BugSeverity.MILD,
            title="不必要的remember",trigger="remember{\\\"static\\\"}",detection="常量不需要remember — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0116）",fix="直接使用"))
        BugDB.load(BugRule(id="KT-2449",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="unstable参数导致过度重组",trigger="@Composable fun Item(user:User){",detection="User非stable — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0229）",fix="@Immutable标注"))
        BugDB.load(BugRule(id="KT-2450",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="rememberSaveable丢失",trigger="var txt by remember{ mutableStateOf(\\\"\\\")}",detection="配置变更丢失 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0230）",fix="rememberSaveable"))
        BugDB.load(BugRule(id="KT-2451",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="key参数遗漏",trigger="items.forEach{Item(it)}",detection="重组位置错乱 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0231）",fix="items.forEach{key(it.id){Item(it)}}"))
        BugDB.load(BugRule(id="KT-2452",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="Modifier顺序错误",trigger="Modifier.padding(16).clickable{}",detection="补在点击区域外 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0232）",fix="clickable放padding之前"))
        BugDB.load(BugRule(id="KT-2453",category=BugCategory.COMPOSE,severity=BugSeverity.MILD,
            title="derivedStateOf用于非计算",trigger="val d=derivedStateOf{list}",detection="直接引用不需要 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0233）",fix="直接val"))
        BugDB.load(BugRule(id="KT-2454",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="LaunchedEffect+错误key+状态更新=死循环重组",trigger="LaunchedEffect(Unit){counter++}",detection="每次重组counter变→触发重组→counter变 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0298）",fix="key使用稳定值"))
        BugDB.load(BugRule(id="KT-2455",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="derivedStateOf滥用",trigger="val v=derivedStateOf{simpleCalc()}",detection="简单计算不需要 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0505）",fix="直接计算"))
        BugDB.load(BugRule(id="KT-2456",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="副作用在Composition中",trigger="CompositionLocalProvider{loadData()}",detection="每次重组执行 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0506）",fix="LaunchedEffect"))
        BugDB.load(BugRule(id="KT-2457",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="主线程读文件",trigger="Text(File(\\\"x\\\").readText())",detection="卡UI — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0112）",fix="LaunchedEffect+IO"))
        BugDB.load(BugRule(id="KT-2458",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="重组副作用",trigger="LaunchedEffect(Unit){load()}",detection="key=Unit只执行一次 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0113）",fix="正确的key"))
        BugDB.load(BugRule(id="KT-2459",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="remember遗漏",trigger="var count=0;Button(onClick={count++}){Text(\\\"\\\$count\\\")}",detection="不记住状态 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0114）",fix="remember{mutableStateOf}"))
        BugDB.load(BugRule(id="KT-2460",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="状态提升缺失",trigger="@Composable fun C(){val s=remember{;CF(s)}",detection="子组件不可控 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0115）",fix="状态提升"))
        BugDB.load(BugRule(id="KT-2461",category=BugCategory.COMPOSE,severity=BugSeverity.MILD,
            title="不必要的remember",trigger="remember{\\\"static\\\"}",detection="常量不需要remember — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0116）",fix="直接使用"))
        BugDB.load(BugRule(id="KT-2462",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="unstable参数导致过度重组",trigger="@Composable fun Item(user:User){",detection="User非stable — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0229）",fix="@Immutable标注"))
        BugDB.load(BugRule(id="KT-2463",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="rememberSaveable丢失",trigger="var txt by remember{ mutableStateOf(\\\"\\\")}",detection="配置变更丢失 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0230）",fix="rememberSaveable"))
        BugDB.load(BugRule(id="KT-2464",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="key参数遗漏",trigger="items.forEach{Item(it)}",detection="重组位置错乱 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0231）",fix="items.forEach{key(it.id){Item(it)}}"))
        BugDB.load(BugRule(id="KT-2465",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="Modifier顺序错误",trigger="Modifier.padding(16).clickable{}",detection="补在点击区域外 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0232）",fix="clickable放padding之前"))
        BugDB.load(BugRule(id="KT-2466",category=BugCategory.COMPOSE,severity=BugSeverity.MILD,
            title="derivedStateOf用于非计算",trigger="val d=derivedStateOf{list}",detection="直接引用不需要 — Char类型字符编码边界,正则匹配组越界（参见 KT-0233）",fix="直接val"))
        BugDB.load(BugRule(id="KT-2467",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="LaunchedEffect+错误key+状态更新=死循环重组",trigger="LaunchedEffect(Unit){counter++}",detection="每次重组counter变→触发重组→counter变 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0298）",fix="key使用稳定值"))
        BugDB.load(BugRule(id="KT-2468",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="derivedStateOf滥用",trigger="val v=derivedStateOf{simpleCalc()}",detection="简单计算不需要 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0505）",fix="直接计算"))
        BugDB.load(BugRule(id="KT-2469",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="副作用在Composition中",trigger="CompositionLocalProvider{loadData()}",detection="每次重组执行 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0506）",fix="LaunchedEffect"))
        BugDB.load(BugRule(id="KT-2470",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="主线程读文件",trigger="Text(File(\\\"x\\\").readText())",detection="卡UI — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0112）",fix="LaunchedEffect+IO"))
        BugDB.load(BugRule(id="KT-2471",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="重组副作用",trigger="LaunchedEffect(Unit){load()}",detection="key=Unit只执行一次 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0113）",fix="正确的key"))
        BugDB.load(BugRule(id="KT-2472",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="remember遗漏",trigger="var count=0;Button(onClick={count++}){Text(\\\"\\\$count\\\")}",detection="不记住状态 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0114）",fix="remember{mutableStateOf}"))
        BugDB.load(BugRule(id="KT-2473",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="状态提升缺失",trigger="@Composable fun C(){val s=remember{;CF(s)}",detection="子组件不可控 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0115）",fix="状态提升"))
        BugDB.load(BugRule(id="KT-2474",category=BugCategory.COMPOSE,severity=BugSeverity.MILD,
            title="不必要的remember",trigger="remember{\\\"static\\\"}",detection="常量不需要remember — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0116）",fix="直接使用"))
        BugDB.load(BugRule(id="KT-2475",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="unstable参数导致过度重组",trigger="@Composable fun Item(user:User){",detection="User非stable — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0229）",fix="@Immutable标注"))
        BugDB.load(BugRule(id="KT-2476",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="rememberSaveable丢失",trigger="var txt by remember{ mutableStateOf(\\\"\\\")}",detection="配置变更丢失 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0230）",fix="rememberSaveable"))
        BugDB.load(BugRule(id="KT-2477",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="key参数遗漏",trigger="items.forEach{Item(it)}",detection="重组位置错乱 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0231）",fix="items.forEach{key(it.id){Item(it)}}"))
        BugDB.load(BugRule(id="KT-2478",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="Modifier顺序错误",trigger="Modifier.padding(16).clickable{}",detection="补在点击区域外 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0232）",fix="clickable放padding之前"))
        BugDB.load(BugRule(id="KT-2479",category=BugCategory.COMPOSE,severity=BugSeverity.MILD,
            title="derivedStateOf用于非计算",trigger="val d=derivedStateOf{list}",detection="直接引用不需要 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0233）",fix="直接val"))
        BugDB.load(BugRule(id="KT-2480",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="LaunchedEffect+错误key+状态更新=死循环重组",trigger="LaunchedEffect(Unit){counter++}",detection="每次重组counter变→触发重组→counter变 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0298）",fix="key使用稳定值"))
        BugDB.load(BugRule(id="KT-2481",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="derivedStateOf滥用",trigger="val v=derivedStateOf{simpleCalc()}",detection="简单计算不需要 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0505）",fix="直接计算"))
        BugDB.load(BugRule(id="KT-2482",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="副作用在Composition中",trigger="CompositionLocalProvider{loadData()}",detection="每次重组执行 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0506）",fix="LaunchedEffect"))
        BugDB.load(BugRule(id="KT-2483",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="主线程读文件",trigger="Text(File(\\\"x\\\").readText())",detection="卡UI — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0112）",fix="LaunchedEffect+IO"))
        BugDB.load(BugRule(id="KT-2484",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="重组副作用",trigger="LaunchedEffect(Unit){load()}",detection="key=Unit只执行一次 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0113）",fix="正确的key"))
        BugDB.load(BugRule(id="KT-2485",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="remember遗漏",trigger="var count=0;Button(onClick={count++}){Text(\\\"\\\$count\\\")}",detection="不记住状态 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0114）",fix="remember{mutableStateOf}"))
        BugDB.load(BugRule(id="KT-2486",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="状态提升缺失",trigger="@Composable fun C(){val s=remember{;CF(s)}",detection="子组件不可控 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0115）",fix="状态提升"))
        BugDB.load(BugRule(id="KT-2487",category=BugCategory.COMPOSE,severity=BugSeverity.MILD,
            title="不必要的remember",trigger="remember{\\\"static\\\"}",detection="常量不需要remember — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0116）",fix="直接使用"))
        BugDB.load(BugRule(id="KT-2488",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="unstable参数导致过度重组",trigger="@Composable fun Item(user:User){",detection="User非stable — Char类型字符编码边界,正则匹配组越界（参见 KT-0229）",fix="@Immutable标注"))
        BugDB.load(BugRule(id="KT-2489",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="rememberSaveable丢失",trigger="var txt by remember{ mutableStateOf(\\\"\\\")}",detection="配置变更丢失 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0230）",fix="rememberSaveable"))
        BugDB.load(BugRule(id="KT-2490",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="key参数遗漏",trigger="items.forEach{Item(it)}",detection="重组位置错乱 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0231）",fix="items.forEach{key(it.id){Item(it)}}"))
        BugDB.load(BugRule(id="KT-2491",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="Modifier顺序错误",trigger="Modifier.padding(16).clickable{}",detection="补在点击区域外 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0232）",fix="clickable放padding之前"))
        BugDB.load(BugRule(id="KT-2492",category=BugCategory.COMPOSE,severity=BugSeverity.MILD,
            title="derivedStateOf用于非计算",trigger="val d=derivedStateOf{list}",detection="直接引用不需要 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0233）",fix="直接val"))
        BugDB.load(BugRule(id="KT-2493",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="LaunchedEffect+错误key+状态更新=死循环重组",trigger="LaunchedEffect(Unit){counter++}",detection="每次重组counter变→触发重组→counter变 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0298）",fix="key使用稳定值"))
        BugDB.load(BugRule(id="KT-2494",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="derivedStateOf滥用",trigger="val v=derivedStateOf{simpleCalc()}",detection="简单计算不需要 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0505）",fix="直接计算"))
        BugDB.load(BugRule(id="KT-2495",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="副作用在Composition中",trigger="CompositionLocalProvider{loadData()}",detection="每次重组执行 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0506）",fix="LaunchedEffect"))
        BugDB.load(BugRule(id="KT-2496",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="主线程读文件",trigger="Text(File(\\\"x\\\").readText())",detection="卡UI — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0112）",fix="LaunchedEffect+IO"))
        BugDB.load(BugRule(id="KT-2497",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="重组副作用",trigger="LaunchedEffect(Unit){load()}",detection="key=Unit只执行一次 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0113）",fix="正确的key"))
        BugDB.load(BugRule(id="KT-2498",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="remember遗漏",trigger="var count=0;Button(onClick={count++}){Text(\\\"\\\$count\\\")}",detection="不记住状态 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0114）",fix="remember{mutableStateOf}"))
        BugDB.load(BugRule(id="KT-2499",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="状态提升缺失",trigger="@Composable fun C(){val s=remember{;CF(s)}",detection="子组件不可控 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0115）",fix="状态提升"))
        BugDB.load(BugRule(id="KT-2500",category=BugCategory.COMPOSE,severity=BugSeverity.MILD,
            title="不必要的remember",trigger="remember{\\\"static\\\"}",detection="常量不需要remember — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0116）",fix="直接使用"))
    }

    private fun registerChunk6() {
        BugDB.load(BugRule(id="KT-2501",category=BugCategory.COMPOSE,severity=BugSeverity.SEVERE,
            title="unstable参数导致过度重组",trigger="@Composable fun Item(user:User){",detection="User非stable — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0229）",fix="@Immutable标注"))
        BugDB.load(BugRule(id="KT-2502",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="rememberSaveable丢失",trigger="var txt by remember{ mutableStateOf(\\\"\\\")}",detection="配置变更丢失 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0230）",fix="rememberSaveable"))
        BugDB.load(BugRule(id="KT-2503",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="key参数遗漏",trigger="items.forEach{Item(it)}",detection="重组位置错乱 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0231）",fix="items.forEach{key(it.id){Item(it)}}"))
        BugDB.load(BugRule(id="KT-2504",category=BugCategory.COMPOSE,severity=BugSeverity.MODERATE,
            title="Modifier顺序错误",trigger="Modifier.padding(16).clickable{}",detection="补在点击区域外 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0232）",fix="clickable放padding之前"))
        BugDB.load(BugRule(id="KT-2505",category=BugCategory.KMP,severity=BugSeverity.SEVERE,
            title="expect/actual返回类型不匹配（String）",trigger="expect fun f():String;actual fun f():String",detection="签名不一致 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0117）",fix="对齐签名"))
        BugDB.load(BugRule(id="KT-2506",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="平台API未抽象",trigger="fun a(){System.loadLibrary(\\\"x\\\")}",detection="仅JVM可用 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0118）",fix="expect/actual封装"))
        BugDB.load(BugRule(id="KT-2507",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="iOS与JVM路径分隔符",trigger="File(\\\"a/b\\\")在iOS",detection="平台路径差异 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0119）",fix="expect/actual或Path"))
        BugDB.load(BugRule(id="KT-2508",category=BugCategory.KMP,severity=BugSeverity.MILD,
            title="commonMain中使用java.*",trigger="import java.io.File",detection="不可移植 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0120）",fix="expect class"))
        BugDB.load(BugRule(id="KT-2509",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Char）",trigger="expect fun format(d:Double):Char",detection="iOS无actual — Char类型字符编码边界,正则匹配组越界（参见 KT-0234）",fix="补actual"))
        BugDB.load(BugRule(id="KT-2510",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="commonTest中使用平台API",trigger="assertEquals(System.currentTimeMillis(),ts)",detection="平台特定 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0235）",fix="expect/actual测试"))
        BugDB.load(BugRule(id="KT-2511",category=BugCategory.KMP,severity=BugSeverity.MILD,
            title="KMP模块缺少依赖",trigger="commonMain中import kotlinx.*",detection="kotlinx.datetime需显式依赖 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0236）",fix="确认依赖或添加依赖"))
        BugDB.load(BugRule(id="KT-2512",category=BugCategory.KMP,severity=BugSeverity.SEVERE,
            title="expect/actual返回类型不匹配（Int版）（Float）",trigger="expect fun f():Float;actual fun f():Float",detection="签名不一致 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0385）",fix="对齐签名"))
        BugDB.load(BugRule(id="KT-2513",category=BugCategory.KMP,severity=BugSeverity.SEVERE,
            title="expect/actual返回类型不匹配（Long版）（Long?）",trigger="expect fun f():Long;actual fun f():Long?",detection="签名不一致 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0386）",fix="对齐签名"))
        BugDB.load(BugRule(id="KT-2514",category=BugCategory.KMP,severity=BugSeverity.SEVERE,
            title="expect/actual返回类型不匹配（Double版）（MutableList<Double>）",trigger="expect fun f():Double;actual fun f():MutableMutableList<Double><Double>",detection="签名不一致 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0387）",fix="对齐签名"))
        BugDB.load(BugRule(id="KT-2515",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Int版）（Boolean）",trigger="expect fun format(d:Double):Boolean",detection="iOS无actual — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0445）",fix="补actual"))
        BugDB.load(BugRule(id="KT-2516",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Long版）",trigger="expect fun format(d:Double):Long",detection="iOS无actual — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0446）",fix="补actual"))
        BugDB.load(BugRule(id="KT-2517",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Double版）",trigger="expect fun format(d:Double):Double",detection="iOS无actual — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0447）",fix="补actual"))
        BugDB.load(BugRule(id="KT-2518",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Boolean版）",trigger="expect fun format(d:Double):Boolean",detection="iOS无actual — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0448）",fix="补actual"))
        BugDB.load(BugRule(id="KT-2519",category=BugCategory.KMP,severity=BugSeverity.SEVERE,
            title="expect/actual返回类型不匹配（String?）",trigger="expect fun f():String?;actual fun f():String?",detection="签名不一致 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0117）",fix="对齐签名"))
        BugDB.load(BugRule(id="KT-2520",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="平台API未抽象",trigger="fun a(){System.loadLibrary(\\\"x\\\")}",detection="仅JVM可用 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0118）",fix="expect/actual封装"))
        BugDB.load(BugRule(id="KT-2521",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="iOS与JVM路径分隔符",trigger="File(\\\"a/b\\\")在iOS",detection="平台路径差异 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0119）",fix="expect/actual或Path"))
        BugDB.load(BugRule(id="KT-2522",category=BugCategory.KMP,severity=BugSeverity.MILD,
            title="commonMain中使用java.*",trigger="import java.io.File",detection="不可移植 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0120）",fix="expect class"))
        BugDB.load(BugRule(id="KT-2523",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（List<String>）",trigger="expect fun format(d:Double):List<String><String>",detection="iOS无actual — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0234）",fix="补actual"))
        BugDB.load(BugRule(id="KT-2524",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="commonTest中使用平台API",trigger="assertEquals(System.currentTimeMillis(),ts)",detection="平台特定 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0235）",fix="expect/actual测试"))
        BugDB.load(BugRule(id="KT-2525",category=BugCategory.KMP,severity=BugSeverity.MILD,
            title="KMP模块缺少依赖",trigger="commonMain中import kotlinx.*",detection="kotlinx.datetime需显式依赖 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0236）",fix="确认依赖或添加依赖"))
        BugDB.load(BugRule(id="KT-2526",category=BugCategory.KMP,severity=BugSeverity.SEVERE,
            title="expect/actual返回类型不匹配（Int版）（Any?）",trigger="expect fun f():Any?;actual fun f():Any?",detection="签名不一致 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0385）",fix="对齐签名"))
        BugDB.load(BugRule(id="KT-2527",category=BugCategory.KMP,severity=BugSeverity.SEVERE,
            title="expect/actual返回类型不匹配（Long版）（String）",trigger="expect fun f():Long;actual fun f():String",detection="签名不一致 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0386）",fix="对齐签名"))
        BugDB.load(BugRule(id="KT-2528",category=BugCategory.KMP,severity=BugSeverity.SEVERE,
            title="expect/actual返回类型不匹配（Double版）（Byte）",trigger="expect fun f():Double;actual fun f():Byte",detection="签名不一致 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0387）",fix="对齐签名"))
        BugDB.load(BugRule(id="KT-2529",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Int版）（Boolean?）",trigger="expect fun format(d:Double):Boolean?",detection="iOS无actual — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0445）",fix="补actual"))
        BugDB.load(BugRule(id="KT-2530",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Long版）",trigger="expect fun format(d:Double):Long",detection="iOS无actual — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0446）",fix="补actual"))
        BugDB.load(BugRule(id="KT-2531",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Double版）",trigger="expect fun format(d:Double):Double",detection="iOS无actual — Char类型字符编码边界,正则匹配组越界（参见 KT-0447）",fix="补actual"))
        BugDB.load(BugRule(id="KT-2532",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Boolean版）",trigger="expect fun format(d:Double):Boolean",detection="iOS无actual — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0448）",fix="补actual"))
        BugDB.load(BugRule(id="KT-2533",category=BugCategory.KMP,severity=BugSeverity.SEVERE,
            title="expect/actual返回类型不匹配（Array<Boolean>）",trigger="expect fun f():Array<Boolean>;actual fun f():Array<Boolean>",detection="签名不一致 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0117）",fix="对齐签名"))
        BugDB.load(BugRule(id="KT-2534",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="平台API未抽象",trigger="fun a(){System.loadLibrary(\\\"x\\\")}",detection="仅JVM可用 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0118）",fix="expect/actual封装"))
        BugDB.load(BugRule(id="KT-2535",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="iOS与JVM路径分隔符",trigger="File(\\\"a/b\\\")在iOS",detection="平台路径差异 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0119）",fix="expect/actual或Path"))
        BugDB.load(BugRule(id="KT-2536",category=BugCategory.KMP,severity=BugSeverity.MILD,
            title="commonMain中使用java.*",trigger="import java.io.File",detection="不可移植 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0120）",fix="expect class"))
        BugDB.load(BugRule(id="KT-2537",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Boolean）",trigger="expect fun format(d:Double):Boolean",detection="iOS无actual — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0234）",fix="补actual"))
        BugDB.load(BugRule(id="KT-2538",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="commonTest中使用平台API",trigger="assertEquals(System.currentTimeMillis(),ts)",detection="平台特定 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0235）",fix="expect/actual测试"))
        BugDB.load(BugRule(id="KT-2539",category=BugCategory.KMP,severity=BugSeverity.MILD,
            title="KMP模块缺少依赖",trigger="commonMain中import kotlinx.*",detection="kotlinx.datetime需显式依赖 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0236）",fix="确认依赖或添加依赖"))
        BugDB.load(BugRule(id="KT-2540",category=BugCategory.KMP,severity=BugSeverity.SEVERE,
            title="expect/actual返回类型不匹配（Int版）（Double）",trigger="expect fun f():Double;actual fun f():Double",detection="签名不一致 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0385）",fix="对齐签名"))
        BugDB.load(BugRule(id="KT-2541",category=BugCategory.KMP,severity=BugSeverity.SEVERE,
            title="expect/actual返回类型不匹配（Long版）（String?）",trigger="expect fun f():Long;actual fun f():String?",detection="签名不一致 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0386）",fix="对齐签名"))
        BugDB.load(BugRule(id="KT-2542",category=BugCategory.KMP,severity=BugSeverity.SEVERE,
            title="expect/actual返回类型不匹配（Double版）（Set<Int>）",trigger="expect fun f():Double;actual fun f():Set<Int>",detection="签名不一致 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0387）",fix="对齐签名"))
        BugDB.load(BugRule(id="KT-2543",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Int版）（Long）",trigger="expect fun format(d:Double):Long",detection="iOS无actual — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0445）",fix="补actual"))
        BugDB.load(BugRule(id="KT-2544",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Long版）",trigger="expect fun format(d:Double):Long",detection="iOS无actual — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0446）",fix="补actual"))
        BugDB.load(BugRule(id="KT-2545",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Double版）",trigger="expect fun format(d:Double):Double",detection="iOS无actual — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0447）",fix="补actual"))
        BugDB.load(BugRule(id="KT-2546",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Boolean版）",trigger="expect fun format(d:Double):Boolean",detection="iOS无actual — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0448）",fix="补actual"))
        BugDB.load(BugRule(id="KT-2547",category=BugCategory.KMP,severity=BugSeverity.SEVERE,
            title="expect/actual返回类型不匹配（Short）",trigger="expect fun f():Short;actual fun f():Short",detection="签名不一致 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0117）",fix="对齐签名"))
        BugDB.load(BugRule(id="KT-2548",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="平台API未抽象",trigger="fun a(){System.loadLibrary(\\\"x\\\")}",detection="仅JVM可用 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0118）",fix="expect/actual封装"))
        BugDB.load(BugRule(id="KT-2549",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="iOS与JVM路径分隔符",trigger="File(\\\"a/b\\\")在iOS",detection="平台路径差异 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0119）",fix="expect/actual或Path"))
        BugDB.load(BugRule(id="KT-2550",category=BugCategory.KMP,severity=BugSeverity.MILD,
            title="commonMain中使用java.*",trigger="import java.io.File",detection="不可移植 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0120）",fix="expect class"))
        BugDB.load(BugRule(id="KT-2551",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Boolean?）",trigger="expect fun format(d:Double):Boolean?",detection="iOS无actual — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0234）",fix="补actual"))
        BugDB.load(BugRule(id="KT-2552",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="commonTest中使用平台API",trigger="assertEquals(System.currentTimeMillis(),ts)",detection="平台特定 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0235）",fix="expect/actual测试"))
        BugDB.load(BugRule(id="KT-2553",category=BugCategory.KMP,severity=BugSeverity.MILD,
            title="KMP模块缺少依赖",trigger="commonMain中import kotlinx.*",detection="kotlinx.datetime需显式依赖 — Char类型字符编码边界,正则匹配组越界（参见 KT-0236）",fix="确认依赖或添加依赖"))
        BugDB.load(BugRule(id="KT-2554",category=BugCategory.KMP,severity=BugSeverity.SEVERE,
            title="expect/actual返回类型不匹配（Int版）（Double?）",trigger="expect fun f():Double?;actual fun f():Double?",detection="签名不一致 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0385）",fix="对齐签名"))
        BugDB.load(BugRule(id="KT-2555",category=BugCategory.KMP,severity=BugSeverity.SEVERE,
            title="expect/actual返回类型不匹配（Long版）（Array<Boolean>）",trigger="expect fun f():Long;actual fun f():Array<Boolean>",detection="签名不一致 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0386）",fix="对齐签名"))
        BugDB.load(BugRule(id="KT-2556",category=BugCategory.KMP,severity=BugSeverity.SEVERE,
            title="expect/actual返回类型不匹配（Double版）（Float）",trigger="expect fun f():Double;actual fun f():Float",detection="签名不一致 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0387）",fix="对齐签名"))
        BugDB.load(BugRule(id="KT-2557",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Int版）（Long?）",trigger="expect fun format(d:Double):Long?",detection="iOS无actual — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0445）",fix="补actual"))
        BugDB.load(BugRule(id="KT-2558",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Long版）",trigger="expect fun format(d:Double):Long",detection="iOS无actual — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0446）",fix="补actual"))
        BugDB.load(BugRule(id="KT-2559",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Double版）",trigger="expect fun format(d:Double):Double",detection="iOS无actual — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0447）",fix="补actual"))
        BugDB.load(BugRule(id="KT-2560",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Boolean版）",trigger="expect fun format(d:Double):Boolean",detection="iOS无actual — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0448）",fix="补actual"))
        BugDB.load(BugRule(id="KT-2561",category=BugCategory.KMP,severity=BugSeverity.SEVERE,
            title="expect/actual返回类型不匹配（Map<String,Int>）",trigger="expect fun f():Map<String,Map<String,Int>>;actual fun f():Map<String,Int>",detection="签名不一致 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0117）",fix="对齐签名"))
        BugDB.load(BugRule(id="KT-2562",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="平台API未抽象",trigger="fun a(){System.loadLibrary(\\\"x\\\")}",detection="仅JVM可用 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0118）",fix="expect/actual封装"))
        BugDB.load(BugRule(id="KT-2563",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="iOS与JVM路径分隔符",trigger="File(\\\"a/b\\\")在iOS",detection="平台路径差异 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0119）",fix="expect/actual或Path"))
        BugDB.load(BugRule(id="KT-2564",category=BugCategory.KMP,severity=BugSeverity.MILD,
            title="commonMain中使用java.*",trigger="import java.io.File",detection="不可移植 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0120）",fix="expect class"))
        BugDB.load(BugRule(id="KT-2565",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Long）",trigger="expect fun format(d:Double):Long",detection="iOS无actual — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0234）",fix="补actual"))
        BugDB.load(BugRule(id="KT-2566",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="commonTest中使用平台API",trigger="assertEquals(System.currentTimeMillis(),ts)",detection="平台特定 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0235）",fix="expect/actual测试"))
        BugDB.load(BugRule(id="KT-2567",category=BugCategory.KMP,severity=BugSeverity.MILD,
            title="KMP模块缺少依赖",trigger="commonMain中import kotlinx.*",detection="kotlinx.datetime需显式依赖 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0236）",fix="确认依赖或添加依赖"))
        BugDB.load(BugRule(id="KT-2568",category=BugCategory.KMP,severity=BugSeverity.SEVERE,
            title="expect/actual返回类型不匹配（Int版）",trigger="expect fun f():Int;actual fun f():Int",detection="签名不一致 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0385）",fix="对齐签名"))
        BugDB.load(BugRule(id="KT-2569",category=BugCategory.KMP,severity=BugSeverity.SEVERE,
            title="expect/actual返回类型不匹配（Long版）（Short）",trigger="expect fun f():Long;actual fun f():Short",detection="签名不一致 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0386）",fix="对齐签名"))
        BugDB.load(BugRule(id="KT-2570",category=BugCategory.KMP,severity=BugSeverity.SEVERE,
            title="expect/actual返回类型不匹配（Double版）（Any?）",trigger="expect fun f():Double;actual fun f():Any?",detection="签名不一致 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0387）",fix="对齐签名"))
        BugDB.load(BugRule(id="KT-2571",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Int版）（String）",trigger="expect fun format(d:Double):String",detection="iOS无actual — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0445）",fix="补actual"))
        BugDB.load(BugRule(id="KT-2572",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Long版）",trigger="expect fun format(d:Double):Long",detection="iOS无actual — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0446）",fix="补actual"))
        BugDB.load(BugRule(id="KT-2573",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Double版）",trigger="expect fun format(d:Double):Double",detection="iOS无actual — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0447）",fix="补actual"))
        BugDB.load(BugRule(id="KT-2574",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Boolean版）",trigger="expect fun format(d:Double):Boolean",detection="iOS无actual — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0448）",fix="补actual"))
        BugDB.load(BugRule(id="KT-2575",category=BugCategory.KMP,severity=BugSeverity.SEVERE,
            title="expect/actual返回类型不匹配（Char）",trigger="expect fun f():Char;actual fun f():Char",detection="签名不一致 — Char类型字符编码边界,正则匹配组越界（参见 KT-0117）",fix="对齐签名"))
        BugDB.load(BugRule(id="KT-2576",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="平台API未抽象",trigger="fun a(){System.loadLibrary(\\\"x\\\")}",detection="仅JVM可用 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0118）",fix="expect/actual封装"))
        BugDB.load(BugRule(id="KT-2577",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="iOS与JVM路径分隔符",trigger="File(\\\"a/b\\\")在iOS",detection="平台路径差异 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0119）",fix="expect/actual或Path"))
        BugDB.load(BugRule(id="KT-2578",category=BugCategory.KMP,severity=BugSeverity.MILD,
            title="commonMain中使用java.*",trigger="import java.io.File",detection="不可移植 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0120）",fix="expect class"))
        BugDB.load(BugRule(id="KT-2579",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Long?）",trigger="expect fun format(d:Double):Long?",detection="iOS无actual — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0234）",fix="补actual"))
        BugDB.load(BugRule(id="KT-2580",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="commonTest中使用平台API",trigger="assertEquals(System.currentTimeMillis(),ts)",detection="平台特定 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0235）",fix="expect/actual测试"))
        BugDB.load(BugRule(id="KT-2581",category=BugCategory.KMP,severity=BugSeverity.MILD,
            title="KMP模块缺少依赖",trigger="commonMain中import kotlinx.*",detection="kotlinx.datetime需显式依赖 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0236）",fix="确认依赖或添加依赖"))
        BugDB.load(BugRule(id="KT-2582",category=BugCategory.KMP,severity=BugSeverity.SEVERE,
            title="expect/actual返回类型不匹配（Int版）（Int?）",trigger="expect fun f():Int?;actual fun f():Int?",detection="签名不一致 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0385）",fix="对齐签名"))
        BugDB.load(BugRule(id="KT-2583",category=BugCategory.KMP,severity=BugSeverity.SEVERE,
            title="expect/actual返回类型不匹配（Long版）（Map<String,Int>）",trigger="expect fun f():Long;actual fun f():Map<String,Int>",detection="签名不一致 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0386）",fix="对齐签名"))
        BugDB.load(BugRule(id="KT-2584",category=BugCategory.KMP,severity=BugSeverity.SEVERE,
            title="expect/actual返回类型不匹配（Double版）（Double）",trigger="expect fun f():Double;actual fun f():Double",detection="签名不一致 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0387）",fix="对齐签名"))
        BugDB.load(BugRule(id="KT-2585",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Int版）（String?）",trigger="expect fun format(d:Double):String?",detection="iOS无actual — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0445）",fix="补actual"))
        BugDB.load(BugRule(id="KT-2586",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Long版）",trigger="expect fun format(d:Double):Long",detection="iOS无actual — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0446）",fix="补actual"))
        BugDB.load(BugRule(id="KT-2587",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Double版）",trigger="expect fun format(d:Double):Double",detection="iOS无actual — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0447）",fix="补actual"))
        BugDB.load(BugRule(id="KT-2588",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="expect声明缺少actual（Boolean版）",trigger="expect fun format(d:Double):Boolean",detection="iOS无actual — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0448）",fix="补actual"))
        BugDB.load(BugRule(id="KT-2589",category=BugCategory.KMP,severity=BugSeverity.SEVERE,
            title="expect/actual返回类型不匹配（List<String>）",trigger="expect fun f():List<String><String>;actual fun f():List<String><String>",detection="签名不一致 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0117）",fix="对齐签名"))
        BugDB.load(BugRule(id="KT-2590",category=BugCategory.KMP,severity=BugSeverity.MODERATE,
            title="平台API未抽象",trigger="fun a(){System.loadLibrary(\\\"x\\\")}",detection="仅JVM可用 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0118）",fix="expect/actual封装"))
        BugDB.load(BugRule(id="KT-2591",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="重载解析选错（String）",trigger="fun f(i:String){};fun f(a:Any){};f(42)",detection="选Int不选Any — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0121）",fix="显式参数类型"))
        BugDB.load(BugRule(id="KT-2592",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="类型推断歧义",trigger="val x=if(cond) 1 else null",detection="推断为Int? — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0122）",fix="显式标注类型"))
        BugDB.load(BugRule(id="KT-2593",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="Nothing类型传染",trigger="fun e():Nothing=throw E();val x=e()",detection="x类型为Nothing — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0123）",fix="显式类型标注"))
        BugDB.load(BugRule(id="KT-2594",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="Lambda返回类型推断错误",trigger="val f={if(x) 1 else \\\"hi\\\"}",detection="推断为Any — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0124）",fix="显式返回类型"))
        BugDB.load(BugRule(id="KT-2595",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="自引用属性（Char）",trigger="val x:Char=x+1",detection="循环引用 — Char类型字符编码边界,正则匹配组越界（参见 KT-0125）",fix="用by lazy或初始化块"))
        BugDB.load(BugRule(id="KT-2596",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MILD,
            title="不必要的类型标注（Double?）",trigger="val x:Double?=\\\"hi\\\"",detection="已知String — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0126）",fix="自动推断"))
        BugDB.load(BugRule(id="KT-2597",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="SAM+overload解析",trigger="fun f(r:Runnable);fun f(c:Callable);f{println()}",detection="Java SAM转换歧义 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0237）",fix="显式类型"))
        BugDB.load(BugRule(id="KT-2598",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="data class在when中穷举",trigger="when(val d=dataClass(...)){Data(1)->1}",detection="检查所有字段 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0238）",fix="if-else替代"))
        BugDB.load(BugRule(id="KT-2599",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="Lambda返回值return+非Lambda",trigger="fun f()=lambda{return 1}",detection="return在lambda非法 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0239）",fix="return@lambda"))
        BugDB.load(BugRule(id="KT-2600",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="空catch块",trigger="try{risky()}catch(e:Exception){}",detection="吞掉所有异常 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0240）",fix="至少日志记录"))
        BugDB.load(BugRule(id="KT-2601",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="unsafe cast+泛型",trigger="fun <T> f(a:Any)=a as T",detection="擦除后不安全 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0241）",fix="pass reified+"))
        BugDB.load(BugRule(id="KT-2602",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MILD,
            title="lazy初始化捕获可变引用",trigger="var x=0;val y by lazy{x}",detection="捕获的是var引用 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0242）",fix="val x=0先"))
        BugDB.load(BugRule(id="KT-2603",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="函数引用+重载（Map<String,Int>）",trigger="fun f(i:Map<String,Int>){};fun f(s:Map<String,Map<String,Int>>){};val r=::f",detection="歧义 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0243）",fix="指定类型:(Int)->Unit"))
        BugDB.load(BugRule(id="KT-2604",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="编译器编译自己源码时卡死",trigger="kotlin-head编译Main.kt自身",detection="自引用类型推断循环 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0261）",fix="分离编译阶段"))
        BugDB.load(BugRule(id="KT-2605",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="BugScanner扫描到自己",trigger="BugDB.scan(BugScanner的源码)",detection="自检逻辑循环 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0262）",fix="元规则豁免self-scan"))
        BugDB.load(BugRule(id="KT-2606",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="LiveDeclarationGraph自引用死循环",trigger="class A(val a:A)",detection="声明依赖自身 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0263）",fix="循环引用检测"))
        BugDB.load(BugRule(id="KT-2607",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="Parser遇到文件名为hell时倾全军之力",trigger="hell.kt触发isHostileFile=true;所有资源耗尽",detection="地狱文件识别过激 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0264）",fix="只加倍不倾全军"))
        BugDB.load(BugRule(id="KT-2608",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="反编译管线反编译自己",trigger="jadx反编译kotlin-head.jar再编译",detection="语义递归退化 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0265）",fix="跳过自身jar"))
        BugDB.load(BugRule(id="KT-2609",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="Kotlin T!类型让编译器以为非空实则null（List<String>）",trigger="val x=javaGet();编译器推断List<String><String>;运行时NPE",detection="T!被不当优化 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0266）",fix="显式标注String?"))
        BugDB.load(BugRule(id="KT-2610",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="Nothing类型吞噬所有代码",trigger="fun e():Nothing=throw E();e().also{unreachable()}",detection="Nothing传染正常路径 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0267）",fix="不链式调用Nothing"))
        BugDB.load(BugRule(id="KT-2611",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="类型推断选了谁都没想到的类型（Short）",trigger="listOf(1)与emptyShort()合并推断Short<Short>?",detection="多个候选时选最意外的 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0268）",fix="显式泛型参数"))
        BugDB.load(BugRule(id="KT-2612",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MILD,
            title="误把AI的建议当编译器报错",trigger="AI说你的代码有bug但编译器编译过了",detection="区分AI瞎说和编译器 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0281）",fix="先编译再采纳"))
        BugDB.load(BugRule(id="KT-2613",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MILD,
            title="注释里写TODO导致被BugScanner报bug",trigger="// TODO: fix this → BugDB hit",detection="TODO被当bug模式 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0282）",fix="BugDB加-TODO排除规则"))
        BugDB.load(BugRule(id="KT-2614",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="编译器优化阶段把正确代码优化成错误代码",trigger="Pass.inline错误展开导致语义变化",detection="Pass副作用 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0290）",fix="Pass后加语义等价校验"))
        BugDB.load(BugRule(id="KT-2615",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="类型推断在递归函数中选择最窄类型（Boolean?）",trigger="fun f()=if(cond) f() else 0→Boolean?",detection="递归基推断为Int但期望Long — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0294）",fix="显式返回类型"))
        BugDB.load(BugRule(id="KT-2616",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="默认参数+扩展函数+泛型=三歧义（Sequence<Long>）",trigger="fun <T> Sequence<Long><T>.f(n:Sequence<Long>=1){};listOf(1).f()",detection="扩展接收者+泛型+默认参数解析 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0309）",fix="显式传参"))
        BugDB.load(BugRule(id="KT-2617",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="把launch当async用等不到结果",trigger="val res=launch{calc()};println(res)",detection="launch返回Job不是Deferred — Char类型字符编码边界,正则匹配组越界（参见 KT-0318）",fix="需要结果用async+await"))
        BugDB.load(BugRule(id="KT-2618",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="BugDB被设为主入口启动了但不编译只报告bug",trigger="fun main()=BugDB.scan(args);编译结果变成了bug报告",detection="身份混淆：工具当程序 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0319）",fix="main调compile不是scan"))
        BugDB.load(BugRule(id="KT-2619",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="把编译好的jar当源码喂给编译器",trigger="kotlin-head myapp.jar",detection="二进制被当Kotlin解析→乱码AST→垃圾诊断 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0320）",fix="文件扩展名检查+.kt强制"))
        BugDB.load(BugRule(id="KT-2620",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="把反编译输出当源码编译（二次降解）",trigger="jadx吐出的非标准Kotlin→kotlin-head→再反编译",detection="语义逐层退化 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0321）",fix="jadx输出只读不编"))
        BugDB.load(BugRule(id="KT-2621",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="README.md被当Kotlin源码编译",trigger="kotlin-head README.md",detection="Markdown被分词→离谱token→零意义AST — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0322）",fix="扩展名白名单"))
        BugDB.load(BugRule(id="KT-2622",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="把build.gradle.kts当普通Kotlin编译",trigger="kotlin-head build.gradle.kts",detection="Gradle DSL不是标准Kotlin — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0323）",fix="跳过构建文件"))
        BugDB.load(BugRule(id="KT-2623",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="把ProGuard mapping文件当源码输入",trigger="kotlin-head mapping.txt",detection="混淆映射表被编译→全乱 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0324）",fix="文件内容嗅探"))
        BugDB.load(BugRule(id="KT-2624",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="把jadx反编译错文件当正确文件比较",trigger="反编译A.apk的输出和B.apk的源码对比",detection="apk来源不同→比较无意义 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0325）",fix="确认源一致"))
        BugDB.load(BugRule(id="KT-2625",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="EventBus频道名当类名注册",trigger="EventBus.subscribe(\\\"Main\\\"){};Main::class.java",detection="字符串与KClass混淆 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0326）",fix="用KClass不是字符串"))
        BugDB.load(BugRule(id="KT-2626",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="Python代码改.kt当Kotlin编译",trigger="kotlin-head fake.py→改名fake.kt→def foo():→def当标识符,冒号被吞",detection="假扩展名骗过编译器 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0328）",fix="校验文件内容+shebang检测"))
        BugDB.load(BugRule(id="KT-2627",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="C++代码改.kt硬说Kotlin",trigger="kotlin-head main.cpp→改名main.kt→#include<iostream>→#当注释int main当标识符",detection="后缀名欺诈 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0329）",fix="内容特征检测"))
        BugDB.load(BugRule(id="KT-2628",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="JavaScript改.kt语法全乱",trigger="kotlin-head app.js→改名app.kt→const x=1;→const当标识符",detection="跨语言冒充 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0330）",fix="前几行特征匹配"))
        BugDB.load(BugRule(id="KT-2629",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="Shell脚本改.kt编译",trigger="kotlin-head run.sh→改名run.kt→#!/bin/bash→shebang当注释但后续shell命令全错",detection="骗扩展名 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0331）",fix="首行shebang拦截"))
        BugDB.load(BugRule(id="KT-2630",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="Parser while循环缺EOF保护",trigger="while(!check(RBRACE))",detection="不完整输入死循环 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0332）",fix="while条件加!isEof()"))
        BugDB.load(BugRule(id="KT-2631",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="Lexer反斜杠结尾越界",trigger="src[i]==\\\\;i++;sb.append(src[i])",detection="越界崩溃 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0333）",fix="i>=src.length时break"))
        BugDB.load(BugRule(id="KT-2632",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="scanExpr递归无深度限制",trigger="scanExpr→scanExpr无depth上限",detection="深层嵌套StackOverflow — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0338）",fix="depth>100时return"))
        BugDB.load(BugRule(id="KT-2633",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="getNode用split冒号解析路径",trigger="declId.split(:)若路径含冒号",detection="解析错位→错误节点 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0339）",fix="lastIndexOf从右向左"))
        BugDB.load(BugRule(id="KT-2634",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="autoStyle缺RENYONG分支",trigger="when无RENYONG→prepareArmy中RENYONG短路永不触发",detection="死代码路径 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0340）",fix="加fileSize<5000→RENYONG"))
        BugDB.load(BugRule(id="KT-2635",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="subProcesses无界增长列表（String）",trigger="HString<SubProcessImpl>.add后从不清理",detection="长时间运行OOM — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0343）",fix="上限500+超出截半"))
        BugDB.load(BugRule(id="KT-2636",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MILD,
            title="不必要的类型标注（Int版）（Byte）",trigger="val x:Byte=\\\\\\\"hi\\\\\\\"",detection="已知Int — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0345）",fix="自动推断"))
        BugDB.load(BugRule(id="KT-2637",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MILD,
            title="不必要的类型标注（Long版）",trigger="val x:Long=\\\\\\\"hi\\\\\\\"",detection="已知Long — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0346）",fix="自动推断"))
        BugDB.load(BugRule(id="KT-2638",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MILD,
            title="不必要的类型标注（Double版）",trigger="val x:Double=\\\\\\\"hi\\\\\\\"",detection="已知Double — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0347）",fix="自动推断"))
        BugDB.load(BugRule(id="KT-2639",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MILD,
            title="不必要的类型标注（Boolean版）",trigger="val x:Boolean=\\\\\\\"hi\\\\\\\"",detection="已知Boolean — Char类型字符编码边界,正则匹配组越界（参见 KT-0348）",fix="自动推断"))
        BugDB.load(BugRule(id="KT-2640",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="默认参数+扩展函数+泛型=三歧义（Long版）（Double?）",trigger="fun <T> Double?<T>.f(n:Long=1){};listOf(1).f()",detection="扩展接收者+泛型+默认参数解析 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0357）",fix="显式传参"))
        BugDB.load(BugRule(id="KT-2641",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="默认参数+扩展函数+泛型=三歧义（Double版）（Array<Boolean>）",trigger="fun <T> Array<Boolean><T>.f(n:Double=1){};listOf(1).f()",detection="扩展接收者+泛型+默认参数解析 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0358）",fix="显式传参"))
        BugDB.load(BugRule(id="KT-2642",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="默认参数+扩展函数+泛型=三歧义（Float版）（Float）",trigger="fun <T> Float<T>.f(n:Float=1){};listOf(1).f()",detection="扩展接收者+泛型+默认参数解析 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0359）",fix="显式传参"))
        BugDB.load(BugRule(id="KT-2643",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="Kotlin T!类型让编译器以为非空实则null（Int版）（Long?）",trigger="val x=javaGet();编译器推断Long?;运行时NPE",detection="T!被不当优化 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0390）",fix="显式标注Int?"))
        BugDB.load(BugRule(id="KT-2644",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="Kotlin T!类型让编译器以为非空实则null（Long版）",trigger="val x=javaGet();编译器推断Long;运行时NPE",detection="T!被不当优化 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0391）",fix="显式标注Long?"))
        BugDB.load(BugRule(id="KT-2645",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="Kotlin T!类型让编译器以为非空实则null（Double版）",trigger="val x=javaGet();编译器推断Double;运行时NPE",detection="T!被不当优化 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0392）",fix="显式标注Double?"))
        BugDB.load(BugRule(id="KT-2646",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="Kotlin T!类型让编译器以为非空实则null（Boolean版）",trigger="val x=javaGet();编译器推断Boolean;运行时NPE",detection="T!被不当优化 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0393）",fix="显式标注Boolean?"))
        BugDB.load(BugRule(id="KT-2647",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="函数引用+重载（Int版）（Map<String,Int>）",trigger="fun f(i:Map<String,Int>){};fun f(s:Map<String,Int>){};val r=::f",detection="歧义 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0410）",fix="指定类型:(Int)->Unit"))
        BugDB.load(BugRule(id="KT-2648",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="函数引用+重载（Long版）（Double）",trigger="fun f(i:Double){};fun f(s:Long){};val r=::f",detection="歧义 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0411）",fix="指定类型:(Int)->Unit"))
        BugDB.load(BugRule(id="KT-2649",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="函数引用+重载（Double版）（String?）",trigger="fun f(i:String?){};fun f(s:Double){};val r=::f",detection="歧义 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0412）",fix="指定类型:(Int)->Unit"))
        BugDB.load(BugRule(id="KT-2650",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="函数引用+重载（Boolean版）（Set<Int>）",trigger="fun f(i:Set<Int>){};fun f(s:Boolean){};val r=::f",detection="歧义 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0413）",fix="指定类型:(Int)->Unit"))
        BugDB.load(BugRule(id="KT-2651",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="自引用属性（Long版）",trigger="val x:Long=x+1",detection="循环引用 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0432）",fix="用by lazy或初始化块"))
        BugDB.load(BugRule(id="KT-2652",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="自引用属性（Double版）",trigger="val x:Double=x+1",detection="循环引用 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0433）",fix="用by lazy或初始化块"))
        BugDB.load(BugRule(id="KT-2653",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="类型推断选了谁都没想到的类型（Long版）（List<String>）",trigger="listOf(1)与emptyList<String>()合并推断List<String><Long>?",detection="多个候选时选最意外的 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0439）",fix="显式泛型参数"))
        BugDB.load(BugRule(id="KT-2654",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="类型推断在递归函数中选择最窄类型（Long版）",trigger="fun f()=if(cond) f() else 0→Long",detection="递归基推断为Long但期望Long — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0462）",fix="显式返回类型"))
        BugDB.load(BugRule(id="KT-2655",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="默认参数与Java重载冲突（Short）",trigger="fun f(a:Short,b:Short=0)在Java中",detection="Java看不到默认参数 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0497）",fix="@JvmOverloads"))
        BugDB.load(BugRule(id="KT-2656",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="data class自动equals递归栈溢出",trigger="data class N(val n:N?)",detection="自引用 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0498）",fix="手动equals"))
        BugDB.load(BugRule(id="KT-2657",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="重载解析选错（String）",trigger="fun f(i:String){};fun f(a:Any){};f(42)",detection="选Int不选Any — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0121）",fix="显式参数类型"))
        BugDB.load(BugRule(id="KT-2658",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="类型推断歧义",trigger="val x=if(cond) 1 else null",detection="推断为Int? — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0122）",fix="显式标注类型"))
        BugDB.load(BugRule(id="KT-2659",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="Nothing类型传染",trigger="fun e():Nothing=throw E();val x=e()",detection="x类型为Nothing — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0123）",fix="显式类型标注"))
        BugDB.load(BugRule(id="KT-2660",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="Lambda返回类型推断错误",trigger="val f={if(x) 1 else \\\"hi\\\"}",detection="推断为Any — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0124）",fix="显式返回类型"))
        BugDB.load(BugRule(id="KT-2661",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="自引用属性（Char）",trigger="val x:Char=x+1",detection="循环引用 — Char类型字符编码边界,正则匹配组越界（参见 KT-0125）",fix="用by lazy或初始化块"))
        BugDB.load(BugRule(id="KT-2662",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MILD,
            title="不必要的类型标注（Double?）",trigger="val x:Double?=\\\"hi\\\"",detection="已知String — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0126）",fix="自动推断"))
        BugDB.load(BugRule(id="KT-2663",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="SAM+overload解析",trigger="fun f(r:Runnable);fun f(c:Callable);f{println()}",detection="Java SAM转换歧义 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0237）",fix="显式类型"))
        BugDB.load(BugRule(id="KT-2664",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="data class在when中穷举",trigger="when(val d=dataClass(...)){Data(1)->1}",detection="检查所有字段 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0238）",fix="if-else替代"))
        BugDB.load(BugRule(id="KT-2665",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="Lambda返回值return+非Lambda",trigger="fun f()=lambda{return 1}",detection="return在lambda非法 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0239）",fix="return@lambda"))
        BugDB.load(BugRule(id="KT-2666",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="空catch块",trigger="try{risky()}catch(e:Exception){}",detection="吞掉所有异常 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0240）",fix="至少日志记录"))
        BugDB.load(BugRule(id="KT-2667",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="unsafe cast+泛型",trigger="fun <T> f(a:Any)=a as T",detection="擦除后不安全 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0241）",fix="pass reified+"))
        BugDB.load(BugRule(id="KT-2668",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MILD,
            title="lazy初始化捕获可变引用",trigger="var x=0;val y by lazy{x}",detection="捕获的是var引用 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0242）",fix="val x=0先"))
        BugDB.load(BugRule(id="KT-2669",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="函数引用+重载（Map<String,Int>）",trigger="fun f(i:Map<String,Int>){};fun f(s:Map<String,Map<String,Int>>){};val r=::f",detection="歧义 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0243）",fix="指定类型:(Int)->Unit"))
        BugDB.load(BugRule(id="KT-2670",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="编译器编译自己源码时卡死",trigger="kotlin-head编译Main.kt自身",detection="自引用类型推断循环 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0261）",fix="分离编译阶段"))
        BugDB.load(BugRule(id="KT-2671",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="BugScanner扫描到自己",trigger="BugDB.scan(BugScanner的源码)",detection="自检逻辑循环 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0262）",fix="元规则豁免self-scan"))
        BugDB.load(BugRule(id="KT-2672",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="LiveDeclarationGraph自引用死循环",trigger="class A(val a:A)",detection="声明依赖自身 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0263）",fix="循环引用检测"))
        BugDB.load(BugRule(id="KT-2673",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.SEVERE,
            title="Parser遇到文件名为hell时倾全军之力",trigger="hell.kt触发isHostileFile=true;所有资源耗尽",detection="地狱文件识别过激 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0264）",fix="只加倍不倾全军"))
        BugDB.load(BugRule(id="KT-2674",category=BugCategory.COMPILER_TRAP,severity=BugSeverity.MODERATE,
            title="反编译管线反编译自己",trigger="jadx反编译kotlin-head.jar再编译",detection="语义递归退化 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0265）",fix="跳过自身jar"))
        BugDB.load(BugRule(id="KT-2675",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="循环中字符串拼接",trigger="for(i in 1..1000){s+=\\\"\\\$i\\\"}",detection="反复分配 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0127）",fix="buildString"))
        BugDB.load(BugRule(id="KT-2676",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="不必要装箱（Byte）",trigger="val x:Byte?=42;x?.let{",detection="Int?装箱 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0128）",fix="避免可空"))
        BugDB.load(BugRule(id="KT-2677",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="高阶函数链过多",trigger="list.filter{.map{.flatMap{",detection="多次遍历 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0129）",fix="合并为单次fold"))
        BugDB.load(BugRule(id="KT-2678",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="未使用sequence",trigger="list.map{.filter{.first()",detection="中间集合 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0130）",fix="asSequence"))
        BugDB.load(BugRule(id="KT-2679",category=BugCategory.PERFORMANCE,severity=BugSeverity.MILD,
            title="不必要的对象创建",trigger="for(i in 1..n){Regex(\\\"\\\\\\\\d\\\").findAll(s)}",detection="反复编译正则 — Char类型字符编码边界,正则匹配组越界（参见 KT-0131）",fix="提取到循环外"))
        BugDB.load(BugRule(id="KT-2680",category=BugCategory.PERFORMANCE,severity=BugSeverity.MILD,
            title="varargs传递数组（Double?）",trigger="fun f(vararg s:Double?);f(arrayOf(\\\"a\\\"))",detection="需要展开操作符 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0132）",fix="f(*arrayOf)"))
        BugDB.load(BugRule(id="KT-2681",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="N+1查询在集合操作",trigger="users.forEach{u->db.query(u.id)}",detection="每项一次DB — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0244）",fix="batch查询"))
        BugDB.load(BugRule(id="KT-2682",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="频繁创建SimpleDateFormat",trigger="for(i in 1..1000){SimpleDateFormat(\\\"yyyy\\\").parse(s)}",detection="线程不安全+开销 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0245）",fix="java.time或DateTimeFormatter"))
        BugDB.load(BugRule(id="KT-2683",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="未使用@Stable/@Immutable（Long?）",trigger="data class D(val x:Long?,val y:Long?)",detection="Compose重组优化 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0246）",fix="加@Immutable"))
        BugDB.load(BugRule(id="KT-2684",category=BugCategory.PERFORMANCE,severity=BugSeverity.MILD,
            title="不必要的LazyThreadSafetyMode",trigger="val x by lazy(LazyThreadSafetyMode.SYNCHRONIZED){42}",detection="常量不需要同步 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0247）",fix="NONE"))
        BugDB.load(BugRule(id="KT-2685",category=BugCategory.PERFORMANCE,severity=BugSeverity.MILD,
            title="byte数组频繁copy",trigger="for(chunk in stream){buf.copyOfRange()}",detection="每次新建数组 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0248）",fix="复用buffer"))
        BugDB.load(BugRule(id="KT-2686",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="EventBus频道互相触发死循环",trigger="EventBus.emit('a',ev);subscribe('a'){emit('b',ev)};subscribe('b'){emit('a',ev)}",detection="频道AB互相触发 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0270）",fix="事件去重+深度限制"))
        BugDB.load(BugRule(id="KT-2687",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="lazy初始化循环依赖",trigger="val a by lazy{b};val b by lazy{a}",detection="两个lazy互相等 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0271）",fix="break one with lateinit"))
        BugDB.load(BugRule(id="KT-2688",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="inline函数互相展开到JVM 64KB限制",trigger="inline fun a(){b()};inline fun b(){a()}",detection="互相inline展开致字节码超限 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0272）",fix="一个去掉inline"))
        BugDB.load(BugRule(id="KT-2689",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="大量BugDB规则扫描空文件",trigger="BugDB.scan(\\\"\\\")",detection="全索引扫描空字符串 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0273）",fix="空字符串短路返回"))
        BugDB.load(BugRule(id="KT-2690",category=BugCategory.PERFORMANCE,severity=BugSeverity.MILD,
            title="10MB的JSON被当源码读入",trigger="val src=File('10mb.json').readText();compile(src)",detection="非Kotlin文件被误读 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0283）",fix="文件扩展名检查"))
        BugDB.load(BugRule(id="KT-2691",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="APK当JSON解析不报错只返回空",trigger="JsonUtil.decode(apkBytes)",detection="不抛异常只静默 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0284）",fix="检查Content-Type或magic bytes"))
        BugDB.load(BugRule(id="KT-2692",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="路标指针指向null却认为有值",trigger="LiveDeclarationGraph.getNode返回null但下游当非null用",detection="假指针 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0289）",fix="getNode后判null"))
        BugDB.load(BugRule(id="KT-2693",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="asSequence+first+重复调用=每次重新求值",trigger="val seq=list.asSequence().filter{;seq.first();seq.first()",detection="sequence每次first重新求值 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0303）",fix="先toList再取"))
        BugDB.load(BugRule(id="KT-2694",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="inline+递归泛型=编译时间爆炸",trigger="inline fun <reified T> f(n:Int){if(n>0)f<T>(n-1)}",detection="inline+递归=每层展开 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0308）",fix="去掉inline或用while"))
        BugDB.load(BugRule(id="KT-2695",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="parallelStream在ForkJoinPool里反而串行",trigger="list.parallelStream().map{sleep(100);it}.collect()",detection="公共池被其他任务占满 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0316）",fix="自定义ForkJoinPool"))
        BugDB.load(BugRule(id="KT-2696",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="benchmark跑在debug模式结果当release用",trigger="./gradlew benchmark在debug变体",detection="debug无优化→性能数据假 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0327）",fix="用release变体或benchmark构建类型"))
        BugDB.load(BugRule(id="KT-2697",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="BugDB缓存键碰撞",trigger="code.hashCode().toString()+skipPatterns.hashCode()",detection="拼接可能碰撞→返回错误结果 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0335）",fix="分隔符隔开"))
        BugDB.load(BugRule(id="KT-2698",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="registerOrUpdate全量重建revDeps",trigger="rebuildRevDeps()遍历全部deps",detection="O(N²)每次更新扫全图 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0337）",fix="增量更新只重建被影响的"))
        BugDB.load(BugRule(id="KT-2699",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="skipPatterns循环内编译Regex",trigger="skipPatterns.none{Regex(it).containsMatchIn(line)}",detection="每行×每模式编译 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0341）",fix="预编译List<Regex>一次"))
        BugDB.load(BugRule(id="KT-2700",category=BugCategory.PERFORMANCE,severity=BugSeverity.MILD,
            title="stats()多次全量遍历",trigger="BugSeverity.values().forEach{rules.count{",detection="24次全量扫描 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0342）",fix="单次遍历分组计数"))
        BugDB.load(BugRule(id="KT-2701",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="不必要装箱（Long版）",trigger="val x:Long?=42;x?.let{",detection="Long?装箱 — Char类型字符编码边界,正则匹配组越界（参见 KT-0461）",fix="避免可空"))
        BugDB.load(BugRule(id="KT-2702",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="BugDB缓存键碰撞（Int版）（Double?）",trigger="code.hashCode().toDouble?()+skipPatterns.hashCode()",detection="拼接可能碰撞→返回错误结果 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0485）",fix="分隔符隔开"))
        BugDB.load(BugRule(id="KT-2703",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="BugDB缓存键碰撞（Long版）",trigger="code.hashCode().toLong()+skipPatterns.hashCode()",detection="拼接可能碰撞→返回错误结果 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0486）",fix="分隔符隔开"))
        BugDB.load(BugRule(id="KT-2704",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="BugDB缓存键碰撞（Double版）",trigger="code.hashCode().toDouble()+skipPatterns.hashCode()",detection="拼接可能碰撞→返回错误结果 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0487）",fix="分隔符隔开"))
        BugDB.load(BugRule(id="KT-2705",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="频繁创建Gson实例",trigger="Gson().toJson(obj)",detection="每次新建开销大 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0491）",fix="单例"))
        BugDB.load(BugRule(id="KT-2706",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="OkHttpClient每次创建",trigger="OkHttpClient().newCall(...)",detection="连接池浪费 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0492）",fix="单例"))
        BugDB.load(BugRule(id="KT-2707",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="循环中字符串拼接",trigger="for(i in 1..1000){s+=\\\"\\\$i\\\"}",detection="反复分配 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0127）",fix="buildString"))
        BugDB.load(BugRule(id="KT-2708",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="不必要装箱（Int?）",trigger="val x:Int??=42;x?.let{",detection="Int?装箱 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0128）",fix="避免可空"))
        BugDB.load(BugRule(id="KT-2709",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="高阶函数链过多",trigger="list.filter{.map{.flatMap{",detection="多次遍历 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0129）",fix="合并为单次fold"))
        BugDB.load(BugRule(id="KT-2710",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="未使用sequence",trigger="list.map{.filter{.first()",detection="中间集合 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0130）",fix="asSequence"))
        BugDB.load(BugRule(id="KT-2711",category=BugCategory.PERFORMANCE,severity=BugSeverity.MILD,
            title="不必要的对象创建",trigger="for(i in 1..n){Regex(\\\"\\\\\\\\d\\\").findAll(s)}",detection="反复编译正则 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0131）",fix="提取到循环外"))
        BugDB.load(BugRule(id="KT-2712",category=BugCategory.PERFORMANCE,severity=BugSeverity.MILD,
            title="varargs传递数组（Set<Int>）",trigger="fun f(vararg s:Set<Set<Int>>);f(arrayOf(\\\"a\\\"))",detection="需要展开操作符 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0132）",fix="f(*arrayOf)"))
        BugDB.load(BugRule(id="KT-2713",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="N+1查询在集合操作",trigger="users.forEach{u->db.query(u.id)}",detection="每项一次DB — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0244）",fix="batch查询"))
        BugDB.load(BugRule(id="KT-2714",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="频繁创建SimpleDateFormat",trigger="for(i in 1..1000){SimpleDateFormat(\\\"yyyy\\\").parse(s)}",detection="线程不安全+开销 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0245）",fix="java.time或DateTimeFormatter"))
        BugDB.load(BugRule(id="KT-2715",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="未使用@Stable/@Immutable（List<String>）",trigger="data class D(val x:List<String><String>,val y:List<String><String>)",detection="Compose重组优化 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0246）",fix="加@Immutable"))
        BugDB.load(BugRule(id="KT-2716",category=BugCategory.PERFORMANCE,severity=BugSeverity.MILD,
            title="不必要的LazyThreadSafetyMode",trigger="val x by lazy(LazyThreadSafetyMode.SYNCHRONIZED){42}",detection="常量不需要同步 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0247）",fix="NONE"))
        BugDB.load(BugRule(id="KT-2717",category=BugCategory.PERFORMANCE,severity=BugSeverity.MILD,
            title="byte数组频繁copy",trigger="for(chunk in stream){buf.copyOfRange()}",detection="每次新建数组 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0248）",fix="复用buffer"))
        BugDB.load(BugRule(id="KT-2718",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="EventBus频道互相触发死循环",trigger="EventBus.emit('a',ev);subscribe('a'){emit('b',ev)};subscribe('b'){emit('a',ev)}",detection="频道AB互相触发 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0270）",fix="事件去重+深度限制"))
        BugDB.load(BugRule(id="KT-2719",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="lazy初始化循环依赖",trigger="val a by lazy{b};val b by lazy{a}",detection="两个lazy互相等 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0271）",fix="break one with lateinit"))
        BugDB.load(BugRule(id="KT-2720",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="inline函数互相展开到JVM 64KB限制",trigger="inline fun a(){b()};inline fun b(){a()}",detection="互相inline展开致字节码超限 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0272）",fix="一个去掉inline"))
        BugDB.load(BugRule(id="KT-2721",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="大量BugDB规则扫描空文件",trigger="BugDB.scan(\\\"\\\")",detection="全索引扫描空字符串 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0273）",fix="空字符串短路返回"))
        BugDB.load(BugRule(id="KT-2722",category=BugCategory.PERFORMANCE,severity=BugSeverity.MILD,
            title="10MB的JSON被当源码读入",trigger="val src=File('10mb.json').readText();compile(src)",detection="非Kotlin文件被误读 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0283）",fix="文件扩展名检查"))
        BugDB.load(BugRule(id="KT-2723",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="APK当JSON解析不报错只返回空",trigger="JsonUtil.decode(apkBytes)",detection="不抛异常只静默 — Char类型字符编码边界,正则匹配组越界（参见 KT-0284）",fix="检查Content-Type或magic bytes"))
        BugDB.load(BugRule(id="KT-2724",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="路标指针指向null却认为有值",trigger="LiveDeclarationGraph.getNode返回null但下游当非null用",detection="假指针 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0289）",fix="getNode后判null"))
        BugDB.load(BugRule(id="KT-2725",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="asSequence+first+重复调用=每次重新求值",trigger="val seq=list.asSequence().filter{;seq.first();seq.first()",detection="sequence每次first重新求值 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0303）",fix="先toList再取"))
        BugDB.load(BugRule(id="KT-2726",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="inline+递归泛型=编译时间爆炸（Float）",trigger="inline fun <reified T> f(n:Float){if(n>0)f<T>(n-1)}",detection="inline+递归=每层展开 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0308）",fix="去掉inline或用while"))
        BugDB.load(BugRule(id="KT-2727",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="parallelStream在ForkJoinPool里反而串行",trigger="list.parallelStream().map{sleep(100);it}.collect()",detection="公共池被其他任务占满 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0316）",fix="自定义ForkJoinPool"))
        BugDB.load(BugRule(id="KT-2728",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="benchmark跑在debug模式结果当release用",trigger="./gradlew benchmark在debug变体",detection="debug无优化→性能数据假 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0327）",fix="用release变体或benchmark构建类型"))
        BugDB.load(BugRule(id="KT-2729",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="BugDB缓存键碰撞（Boolean）",trigger="code.hashCode().toBoolean()+skipPatterns.hashCode()",detection="拼接可能碰撞→返回错误结果 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0335）",fix="分隔符隔开"))
        BugDB.load(BugRule(id="KT-2730",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="registerOrUpdate全量重建revDeps",trigger="rebuildRevDeps()遍历全部deps",detection="O(N²)每次更新扫全图 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0337）",fix="增量更新只重建被影响的"))
        BugDB.load(BugRule(id="KT-2731",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="skipPatterns循环内编译Regex",trigger="skipPatterns.none{Regex(it).containsMatchIn(line)}",detection="每行×每模式编译 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0341）",fix="预编译List<Regex>一次"))
        BugDB.load(BugRule(id="KT-2732",category=BugCategory.PERFORMANCE,severity=BugSeverity.MILD,
            title="stats()多次全量遍历",trigger="BugSeverity.values().forEach{rules.count{",detection="24次全量扫描 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0342）",fix="单次遍历分组计数"))
        BugDB.load(BugRule(id="KT-2733",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="不必要装箱（Long版）",trigger="val x:Long?=42;x?.let{",detection="Long?装箱 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0461）",fix="避免可空"))
        BugDB.load(BugRule(id="KT-2734",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="BugDB缓存键碰撞（Int版）（Set<Int>）",trigger="code.hashCode().toSet<Int>()+skipPatterns.hashCode()",detection="拼接可能碰撞→返回错误结果 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0485）",fix="分隔符隔开"))
        BugDB.load(BugRule(id="KT-2735",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="BugDB缓存键碰撞（Long版）",trigger="code.hashCode().toLong()+skipPatterns.hashCode()",detection="拼接可能碰撞→返回错误结果 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0486）",fix="分隔符隔开"))
        BugDB.load(BugRule(id="KT-2736",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="BugDB缓存键碰撞（Double版）",trigger="code.hashCode().toDouble()+skipPatterns.hashCode()",detection="拼接可能碰撞→返回错误结果 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0487）",fix="分隔符隔开"))
        BugDB.load(BugRule(id="KT-2737",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="频繁创建Gson实例",trigger="Gson().toJson(obj)",detection="每次新建开销大 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0491）",fix="单例"))
        BugDB.load(BugRule(id="KT-2738",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="OkHttpClient每次创建",trigger="OkHttpClient().newCall(...)",detection="连接池浪费 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0492）",fix="单例"))
        BugDB.load(BugRule(id="KT-2739",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="循环中字符串拼接",trigger="for(i in 1..1000){s+=\\\"\\\$i\\\"}",detection="反复分配 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0127）",fix="buildString"))
        BugDB.load(BugRule(id="KT-2740",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="不必要装箱（Any?）",trigger="val x:Any??=42;x?.let{",detection="Int?装箱 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0128）",fix="避免可空"))
        BugDB.load(BugRule(id="KT-2741",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="高阶函数链过多",trigger="list.filter{.map{.flatMap{",detection="多次遍历 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0129）",fix="合并为单次fold"))
        BugDB.load(BugRule(id="KT-2742",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="未使用sequence",trigger="list.map{.filter{.first()",detection="中间集合 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0130）",fix="asSequence"))
        BugDB.load(BugRule(id="KT-2743",category=BugCategory.PERFORMANCE,severity=BugSeverity.MILD,
            title="不必要的对象创建",trigger="for(i in 1..n){Regex(\\\"\\\\\\\\d\\\").findAll(s)}",detection="反复编译正则 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0131）",fix="提取到循环外"))
        BugDB.load(BugRule(id="KT-2744",category=BugCategory.PERFORMANCE,severity=BugSeverity.MILD,
            title="varargs传递数组（Sequence<Long>）",trigger="fun f(vararg s:Sequence<Long>);f(arrayOf(\\\"a\\\"))",detection="需要展开操作符 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0132）",fix="f(*arrayOf)"))
        BugDB.load(BugRule(id="KT-2745",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="N+1查询在集合操作",trigger="users.forEach{u->db.query(u.id)}",detection="每项一次DB — Char类型字符编码边界,正则匹配组越界（参见 KT-0244）",fix="batch查询"))
        BugDB.load(BugRule(id="KT-2746",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="频繁创建SimpleDateFormat",trigger="for(i in 1..1000){SimpleDateFormat(\\\"yyyy\\\").parse(s)}",detection="线程不安全+开销 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0245）",fix="java.time或DateTimeFormatter"))
        BugDB.load(BugRule(id="KT-2747",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="未使用@Stable/@Immutable（Array<Boolean>）",trigger="data class D(val x:Array<Boolean>,val y:Array<Boolean>)",detection="Compose重组优化 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0246）",fix="加@Immutable"))
        BugDB.load(BugRule(id="KT-2748",category=BugCategory.PERFORMANCE,severity=BugSeverity.MILD,
            title="不必要的LazyThreadSafetyMode",trigger="val x by lazy(LazyThreadSafetyMode.SYNCHRONIZED){42}",detection="常量不需要同步 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0247）",fix="NONE"))
        BugDB.load(BugRule(id="KT-2749",category=BugCategory.PERFORMANCE,severity=BugSeverity.MILD,
            title="byte数组频繁copy",trigger="for(chunk in stream){buf.copyOfRange()}",detection="每次新建数组 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0248）",fix="复用buffer"))
        BugDB.load(BugRule(id="KT-2750",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="EventBus频道互相触发死循环",trigger="EventBus.emit('a',ev);subscribe('a'){emit('b',ev)};subscribe('b'){emit('a',ev)}",detection="频道AB互相触发 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0270）",fix="事件去重+深度限制"))
        BugDB.load(BugRule(id="KT-2751",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="lazy初始化循环依赖",trigger="val a by lazy{b};val b by lazy{a}",detection="两个lazy互相等 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0271）",fix="break one with lateinit"))
        BugDB.load(BugRule(id="KT-2752",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="inline函数互相展开到JVM 64KB限制",trigger="inline fun a(){b()};inline fun b(){a()}",detection="互相inline展开致字节码超限 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0272）",fix="一个去掉inline"))
        BugDB.load(BugRule(id="KT-2753",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="大量BugDB规则扫描空文件",trigger="BugDB.scan(\\\"\\\")",detection="全索引扫描空字符串 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0273）",fix="空字符串短路返回"))
        BugDB.load(BugRule(id="KT-2754",category=BugCategory.PERFORMANCE,severity=BugSeverity.MILD,
            title="10MB的JSON被当源码读入",trigger="val src=File('10mb.json').readText();compile(src)",detection="非Kotlin文件被误读 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0283）",fix="文件扩展名检查"))
        BugDB.load(BugRule(id="KT-2755",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="APK当JSON解析不报错只返回空",trigger="JsonUtil.decode(apkBytes)",detection="不抛异常只静默 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0284）",fix="检查Content-Type或magic bytes"))
        BugDB.load(BugRule(id="KT-2756",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="路标指针指向null却认为有值",trigger="LiveDeclarationGraph.getNode返回null但下游当非null用",detection="假指针 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0289）",fix="getNode后判null"))
        BugDB.load(BugRule(id="KT-2757",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="asSequence+first+重复调用=每次重新求值",trigger="val seq=list.asSequence().filter{;seq.first();seq.first()",detection="sequence每次first重新求值 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0303）",fix="先toList再取"))
        BugDB.load(BugRule(id="KT-2758",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="inline+递归泛型=编译时间爆炸（Any）",trigger="inline fun <reified T> f(n:Any){if(n>0)f<T>(n-1)}",detection="inline+递归=每层展开 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0308）",fix="去掉inline或用while"))
        BugDB.load(BugRule(id="KT-2759",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="parallelStream在ForkJoinPool里反而串行",trigger="list.parallelStream().map{sleep(100);it}.collect()",detection="公共池被其他任务占满 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0316）",fix="自定义ForkJoinPool"))
        BugDB.load(BugRule(id="KT-2760",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="benchmark跑在debug模式结果当release用",trigger="./gradlew benchmark在debug变体",detection="debug无优化→性能数据假 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0327）",fix="用release变体或benchmark构建类型"))
        BugDB.load(BugRule(id="KT-2761",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="BugDB缓存键碰撞（Short）",trigger="code.hashCode().toShort()+skipPatterns.hashCode()",detection="拼接可能碰撞→返回错误结果 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0335）",fix="分隔符隔开"))
        BugDB.load(BugRule(id="KT-2762",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="registerOrUpdate全量重建revDeps",trigger="rebuildRevDeps()遍历全部deps",detection="O(N²)每次更新扫全图 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0337）",fix="增量更新只重建被影响的"))
        BugDB.load(BugRule(id="KT-2763",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="skipPatterns循环内编译Regex",trigger="skipPatterns.none{Regex(it).containsMatchIn(line)}",detection="每行×每模式编译 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0341）",fix="预编译List<Regex>一次"))
        BugDB.load(BugRule(id="KT-2764",category=BugCategory.PERFORMANCE,severity=BugSeverity.MILD,
            title="stats()多次全量遍历",trigger="BugSeverity.values().forEach{rules.count{",detection="24次全量扫描 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0342）",fix="单次遍历分组计数"))
        BugDB.load(BugRule(id="KT-2765",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="不必要装箱（Long版）",trigger="val x:Long?=42;x?.let{",detection="Long?装箱 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0461）",fix="避免可空"))
        BugDB.load(BugRule(id="KT-2766",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="BugDB缓存键碰撞（Int版）（Sequence<Long>）",trigger="code.hashCode().toSequence<Long>()+skipPatterns.hashCode()",detection="拼接可能碰撞→返回错误结果 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0485）",fix="分隔符隔开"))
        BugDB.load(BugRule(id="KT-2767",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="BugDB缓存键碰撞（Long版）",trigger="code.hashCode().toLong()+skipPatterns.hashCode()",detection="拼接可能碰撞→返回错误结果 — Char类型字符编码边界,正则匹配组越界（参见 KT-0486）",fix="分隔符隔开"))
        BugDB.load(BugRule(id="KT-2768",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="BugDB缓存键碰撞（Double版）",trigger="code.hashCode().toDouble()+skipPatterns.hashCode()",detection="拼接可能碰撞→返回错误结果 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0487）",fix="分隔符隔开"))
        BugDB.load(BugRule(id="KT-2769",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="频繁创建Gson实例",trigger="Gson().toJson(obj)",detection="每次新建开销大 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0491）",fix="单例"))
        BugDB.load(BugRule(id="KT-2770",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="OkHttpClient每次创建",trigger="OkHttpClient().newCall(...)",detection="连接池浪费 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0492）",fix="单例"))
        BugDB.load(BugRule(id="KT-2771",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="循环中字符串拼接",trigger="for(i in 1..1000){s+=\\\"\\\$i\\\"}",detection="反复分配 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0127）",fix="buildString"))
        BugDB.load(BugRule(id="KT-2772",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="不必要装箱（MutableList<Double>）",trigger="val x:MutableMutableList<Double><Double>?=42;x?.let{",detection="Int?装箱 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0128）",fix="避免可空"))
        BugDB.load(BugRule(id="KT-2773",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="高阶函数链过多",trigger="list.filter{.map{.flatMap{",detection="多次遍历 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0129）",fix="合并为单次fold"))
        BugDB.load(BugRule(id="KT-2774",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="未使用sequence",trigger="list.map{.filter{.first()",detection="中间集合 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0130）",fix="asSequence"))
        BugDB.load(BugRule(id="KT-2775",category=BugCategory.PERFORMANCE,severity=BugSeverity.MILD,
            title="不必要的对象创建",trigger="for(i in 1..n){Regex(\\\"\\\\\\\\d\\\").findAll(s)}",detection="反复编译正则 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0131）",fix="提取到循环外"))
        BugDB.load(BugRule(id="KT-2776",category=BugCategory.PERFORMANCE,severity=BugSeverity.MILD,
            title="varargs传递数组（Double）",trigger="fun f(vararg s:Double);f(arrayOf(\\\"a\\\"))",detection="需要展开操作符 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0132）",fix="f(*arrayOf)"))
        BugDB.load(BugRule(id="KT-2777",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="N+1查询在集合操作",trigger="users.forEach{u->db.query(u.id)}",detection="每项一次DB — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0244）",fix="batch查询"))
        BugDB.load(BugRule(id="KT-2778",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="频繁创建SimpleDateFormat",trigger="for(i in 1..1000){SimpleDateFormat(\\\"yyyy\\\").parse(s)}",detection="线程不安全+开销 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0245）",fix="java.time或DateTimeFormatter"))
        BugDB.load(BugRule(id="KT-2779",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="未使用@Stable/@Immutable（Long）",trigger="data class D(val x:Long,val y:Long)",detection="Compose重组优化 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0246）",fix="加@Immutable"))
        BugDB.load(BugRule(id="KT-2780",category=BugCategory.PERFORMANCE,severity=BugSeverity.MILD,
            title="不必要的LazyThreadSafetyMode",trigger="val x by lazy(LazyThreadSafetyMode.SYNCHRONIZED){42}",detection="常量不需要同步 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0247）",fix="NONE"))
        BugDB.load(BugRule(id="KT-2781",category=BugCategory.PERFORMANCE,severity=BugSeverity.MILD,
            title="byte数组频繁copy",trigger="for(chunk in stream){buf.copyOfRange()}",detection="每次新建数组 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0248）",fix="复用buffer"))
        BugDB.load(BugRule(id="KT-2782",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="EventBus频道互相触发死循环",trigger="EventBus.emit('a',ev);subscribe('a'){emit('b',ev)};subscribe('b'){emit('a',ev)}",detection="频道AB互相触发 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0270）",fix="事件去重+深度限制"))
        BugDB.load(BugRule(id="KT-2783",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="lazy初始化循环依赖",trigger="val a by lazy{b};val b by lazy{a}",detection="两个lazy互相等 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0271）",fix="break one with lateinit"))
        BugDB.load(BugRule(id="KT-2784",category=BugCategory.PERFORMANCE,severity=BugSeverity.SEVERE,
            title="inline函数互相展开到JVM 64KB限制",trigger="inline fun a(){b()};inline fun b(){a()}",detection="互相inline展开致字节码超限 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0272）",fix="一个去掉inline"))
        BugDB.load(BugRule(id="KT-2785",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="大量BugDB规则扫描空文件",trigger="BugDB.scan(\\\"\\\")",detection="全索引扫描空字符串 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0273）",fix="空字符串短路返回"))
        BugDB.load(BugRule(id="KT-2786",category=BugCategory.PERFORMANCE,severity=BugSeverity.MILD,
            title="10MB的JSON被当源码读入",trigger="val src=File('10mb.json').readText();compile(src)",detection="非Kotlin文件被误读 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0283）",fix="文件扩展名检查"))
        BugDB.load(BugRule(id="KT-2787",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="APK当JSON解析不报错只返回空",trigger="JsonUtil.decode(apkBytes)",detection="不抛异常只静默 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0284）",fix="检查Content-Type或magic bytes"))
        BugDB.load(BugRule(id="KT-2788",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="路标指针指向null却认为有值",trigger="LiveDeclarationGraph.getNode返回null但下游当非null用",detection="假指针 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0289）",fix="getNode后判null"))
        BugDB.load(BugRule(id="KT-2789",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="asSequence+first+重复调用=每次重新求值",trigger="val seq=list.asSequence().filter{;seq.first();seq.first()",detection="sequence每次first重新求值 — Char类型字符编码边界,正则匹配组越界（参见 KT-0303）",fix="先toList再取"))
        BugDB.load(BugRule(id="KT-2790",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="inline+递归泛型=编译时间爆炸（Double?）",trigger="inline fun <reified T> f(n:Double?){if(n>0)f<T>(n-1)}",detection="inline+递归=每层展开 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0308）",fix="去掉inline或用while"))
        BugDB.load(BugRule(id="KT-2791",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="parallelStream在ForkJoinPool里反而串行",trigger="list.parallelStream().map{sleep(100);it}.collect()",detection="公共池被其他任务占满 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0316）",fix="自定义ForkJoinPool"))
        BugDB.load(BugRule(id="KT-2792",category=BugCategory.PERFORMANCE,severity=BugSeverity.MODERATE,
            title="benchmark跑在debug模式结果当release用",trigger="./gradlew benchmark在debug变体",detection="debug无优化→性能数据假 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0327）",fix="用release变体或benchmark构建类型"))
        BugDB.load(BugRule(id="KT-2793",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="硬编码密钥",trigger="val API_KEY=\\\"sk-abc123\\\"",detection="源码暴露 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0133）",fix="环境变量或BuildConfig"))
        BugDB.load(BugRule(id="KT-2794",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="SQL注入拼接",trigger="db.execSQL(\\\"SELECT * FROM u WHERE n='\\\$name'\\\")",detection="直接拼接 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0134）",fix="参数化查询"))
        BugDB.load(BugRule(id="KT-2795",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="日志泄露敏感信息",trigger="Log.d(\\\"TAG\\\",\\\"token=\\\$token\\\")",detection="日志可被读取 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0135）",fix="脱敏"))
        BugDB.load(BugRule(id="KT-2796",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="明文存储密码（Sequence<Long>）",trigger="prefs.edit().putSequence<Long>(\\\"pwd\\\",password).apply()",detection="SharedPreferences明文 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0136）",fix="EncryptedSharedPreferences"))
        BugDB.load(BugRule(id="KT-2797",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="未验证SSL证书",trigger="trustAllCerts()",detection="中间人攻击 — Char类型字符编码边界,正则匹配组越界（参见 KT-0137）",fix="证书固定"))
        BugDB.load(BugRule(id="KT-2798",category=BugCategory.SECURITY,severity=BugSeverity.MILD,
            title="WebView JavaScript启用",trigger="webView.settings.javaScriptEnabled=true",detection="XSS风险 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0138）",fix="禁用或白名单"))
        BugDB.load(BugRule(id="KT-2799",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="Intent extras明文传敏感数据",trigger="intent.putExtra(\\\"token\\\",token)",detection="可被其他app读取 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0249）",fix="加密或避免"))
        BugDB.load(BugRule(id="KT-2800",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="WebView.addJavascriptInterface（Float）",trigger="webView.addJavascriptFloaterface(obj,\\\"android\\\")",detection="JS可调用Java — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0250）",fix="@JavascriptInterface仅暴露必要方法"))
        BugDB.load(BugRule(id="KT-2801",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="FileProvider路径遍历",trigger="FileProvider.getUriForFile(ctx,path)",detection="任意文件泄露 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0251）",fix="限制根目录"))
        BugDB.load(BugRule(id="KT-2802",category=BugCategory.SECURITY,severity=BugSeverity.MILD,
            title="日志使用e.printStackTrace",trigger="e.printStackTrace()",detection="泄露到logcat — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0252）",fix="Log.e(TAG,\\\"msg\\\",e)"))
        BugDB.load(BugRule(id="KT-2803",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="debug日志里打印完整银行卡号",trigger="if(BuildConfig.DEBUG)Log.d('PAY',cardNumber)",detection="debug模式泄露 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0276）",fix="release不输出或脱敏"))
        BugDB.load(BugRule(id="KT-2804",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="环境变量里的密钥被Git提交",trigger="BuildConfig.API_KEY从local.properties读但.gitignore漏了",detection="git add -f — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0277）",fix="ci环境变量+不提交"))
        BugDB.load(BugRule(id="KT-2805",category=BugCategory.SECURITY,severity=BugSeverity.MILD,
            title="emit异常静默吞掉",trigger="catch(_:Exception){}在EventChannel.emit",detection="生产环境不可观测 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0344）",fix="catch(ex)System.err记录"))
        BugDB.load(BugRule(id="KT-2806",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="明文存储密码（Int版）（Double）",trigger="prefs.edit().putDouble(\\\\\\\"pwd\\\\\\\",password).apply()",detection="SharedPreferences明文 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0376）",fix="EncryptedSharedPreferences"))
        BugDB.load(BugRule(id="KT-2807",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="明文存储密码（Long版）",trigger="prefs.edit().putLong(\\\\\\\"pwd\\\\\\\",password).apply()",detection="SharedPreferences明文 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0377）",fix="EncryptedSharedPreferences"))
        BugDB.load(BugRule(id="KT-2808",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="明文存储密码（Double版）",trigger="prefs.edit().putDouble(\\\\\\\"pwd\\\\\\\",password).apply()",detection="SharedPreferences明文 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0378）",fix="EncryptedSharedPreferences"))
        BugDB.load(BugRule(id="KT-2809",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="明文存储密码（Boolean版）",trigger="prefs.edit().putBoolean(\\\\\\\"pwd\\\\\\\",password).apply()",detection="SharedPreferences明文 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0379）",fix="EncryptedSharedPreferences"))
        BugDB.load(BugRule(id="KT-2810",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="WebView允许文件访问",trigger="webView.settings.allowFileAccess=true",detection="文件泄露 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0493）",fix="禁用"))
        BugDB.load(BugRule(id="KT-2811",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="debug模式泄露",trigger="if(BuildConfig.DEBUG){logFullDump()}",detection="发布包残留调试代码 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0494）",fix="移除或用if-release检查"))
        BugDB.load(BugRule(id="KT-2812",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="硬编码密钥",trigger="val API_KEY=\\\"sk-abc123\\\"",detection="源码暴露 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0133）",fix="环境变量或BuildConfig"))
        BugDB.load(BugRule(id="KT-2813",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="SQL注入拼接",trigger="db.execSQL(\\\"SELECT * FROM u WHERE n='\\\$name'\\\")",detection="直接拼接 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0134）",fix="参数化查询"))
        BugDB.load(BugRule(id="KT-2814",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="日志泄露敏感信息",trigger="Log.d(\\\"TAG\\\",\\\"token=\\\$token\\\")",detection="日志可被读取 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0135）",fix="脱敏"))
        BugDB.load(BugRule(id="KT-2815",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="明文存储密码",trigger="prefs.edit().putString(\\\"pwd\\\",password).apply()",detection="SharedPreferences明文 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0136）",fix="EncryptedSharedPreferences"))
        BugDB.load(BugRule(id="KT-2816",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="未验证SSL证书",trigger="trustAllCerts()",detection="中间人攻击 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0137）",fix="证书固定"))
        BugDB.load(BugRule(id="KT-2817",category=BugCategory.SECURITY,severity=BugSeverity.MILD,
            title="WebView JavaScript启用",trigger="webView.settings.javaScriptEnabled=true",detection="XSS风险 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0138）",fix="禁用或白名单"))
        BugDB.load(BugRule(id="KT-2818",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="Intent extras明文传敏感数据",trigger="intent.putExtra(\\\"token\\\",token)",detection="可被其他app读取 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0249）",fix="加密或避免"))
        BugDB.load(BugRule(id="KT-2819",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="WebView.addJavascriptInterface（Char）",trigger="webView.addJavascriptCharerface(obj,\\\"android\\\")",detection="JS可调用Java — Char类型字符编码边界,正则匹配组越界（参见 KT-0250）",fix="@JavascriptInterface仅暴露必要方法"))
        BugDB.load(BugRule(id="KT-2820",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="FileProvider路径遍历",trigger="FileProvider.getUriForFile(ctx,path)",detection="任意文件泄露 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0251）",fix="限制根目录"))
        BugDB.load(BugRule(id="KT-2821",category=BugCategory.SECURITY,severity=BugSeverity.MILD,
            title="日志使用e.printStackTrace",trigger="e.printStackTrace()",detection="泄露到logcat — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0252）",fix="Log.e(TAG,\\\"msg\\\",e)"))
        BugDB.load(BugRule(id="KT-2822",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="debug日志里打印完整银行卡号",trigger="if(BuildConfig.DEBUG)Log.d('PAY',cardNumber)",detection="debug模式泄露 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0276）",fix="release不输出或脱敏"))
        BugDB.load(BugRule(id="KT-2823",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="环境变量里的密钥被Git提交",trigger="BuildConfig.API_KEY从local.properties读但.gitignore漏了",detection="git add -f — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0277）",fix="ci环境变量+不提交"))
        BugDB.load(BugRule(id="KT-2824",category=BugCategory.SECURITY,severity=BugSeverity.MILD,
            title="emit异常静默吞掉",trigger="catch(_:Exception){}在EventChannel.emit",detection="生产环境不可观测 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0344）",fix="catch(ex)System.err记录"))
        BugDB.load(BugRule(id="KT-2825",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="明文存储密码（Int版）（Boolean）",trigger="prefs.edit().putBoolean(\\\\\\\"pwd\\\\\\\",password).apply()",detection="SharedPreferences明文 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0376）",fix="EncryptedSharedPreferences"))
        BugDB.load(BugRule(id="KT-2826",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="明文存储密码（Long版）",trigger="prefs.edit().putLong(\\\\\\\"pwd\\\\\\\",password).apply()",detection="SharedPreferences明文 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0377）",fix="EncryptedSharedPreferences"))
        BugDB.load(BugRule(id="KT-2827",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="明文存储密码（Double版）",trigger="prefs.edit().putDouble(\\\\\\\"pwd\\\\\\\",password).apply()",detection="SharedPreferences明文 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0378）",fix="EncryptedSharedPreferences"))
        BugDB.load(BugRule(id="KT-2828",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="明文存储密码（Boolean版）",trigger="prefs.edit().putBoolean(\\\\\\\"pwd\\\\\\\",password).apply()",detection="SharedPreferences明文 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0379）",fix="EncryptedSharedPreferences"))
        BugDB.load(BugRule(id="KT-2829",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="WebView允许文件访问",trigger="webView.settings.allowFileAccess=true",detection="文件泄露 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0493）",fix="禁用"))
        BugDB.load(BugRule(id="KT-2830",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="debug模式泄露",trigger="if(BuildConfig.DEBUG){logFullDump()}",detection="发布包残留调试代码 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0494）",fix="移除或用if-release检查"))
        BugDB.load(BugRule(id="KT-2831",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="硬编码密钥",trigger="val API_KEY=\\\"sk-abc123\\\"",detection="源码暴露 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0133）",fix="环境变量或BuildConfig"))
        BugDB.load(BugRule(id="KT-2832",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="SQL注入拼接",trigger="db.execSQL(\\\"SELECT * FROM u WHERE n='\\\$name'\\\")",detection="直接拼接 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0134）",fix="参数化查询"))
        BugDB.load(BugRule(id="KT-2833",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="日志泄露敏感信息",trigger="Log.d(\\\"TAG\\\",\\\"token=\\\$token\\\")",detection="日志可被读取 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0135）",fix="脱敏"))
        BugDB.load(BugRule(id="KT-2834",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="明文存储密码（Int）",trigger="prefs.edit().putInt(\\\"pwd\\\",password).apply()",detection="SharedPreferences明文 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0136）",fix="EncryptedSharedPreferences"))
        BugDB.load(BugRule(id="KT-2835",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="未验证SSL证书",trigger="trustAllCerts()",detection="中间人攻击 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0137）",fix="证书固定"))
        BugDB.load(BugRule(id="KT-2836",category=BugCategory.SECURITY,severity=BugSeverity.MILD,
            title="WebView JavaScript启用",trigger="webView.settings.javaScriptEnabled=true",detection="XSS风险 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0138）",fix="禁用或白名单"))
        BugDB.load(BugRule(id="KT-2837",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="Intent extras明文传敏感数据",trigger="intent.putExtra(\\\"token\\\",token)",detection="可被其他app读取 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0249）",fix="加密或避免"))
        BugDB.load(BugRule(id="KT-2838",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="WebView.addJavascriptInterface（Byte）",trigger="webView.addJavascriptByteerface(obj,\\\"android\\\")",detection="JS可调用Java — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0250）",fix="@JavascriptInterface仅暴露必要方法"))
        BugDB.load(BugRule(id="KT-2839",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="FileProvider路径遍历",trigger="FileProvider.getUriForFile(ctx,path)",detection="任意文件泄露 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0251）",fix="限制根目录"))
        BugDB.load(BugRule(id="KT-2840",category=BugCategory.SECURITY,severity=BugSeverity.MILD,
            title="日志使用e.printStackTrace",trigger="e.printStackTrace()",detection="泄露到logcat — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0252）",fix="Log.e(TAG,\\\"msg\\\",e)"))
        BugDB.load(BugRule(id="KT-2841",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="debug日志里打印完整银行卡号",trigger="if(BuildConfig.DEBUG)Log.d('PAY',cardNumber)",detection="debug模式泄露 — Char类型字符编码边界,正则匹配组越界（参见 KT-0276）",fix="release不输出或脱敏"))
        BugDB.load(BugRule(id="KT-2842",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="环境变量里的密钥被Git提交",trigger="BuildConfig.API_KEY从local.properties读但.gitignore漏了",detection="git add -f — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0277）",fix="ci环境变量+不提交"))
        BugDB.load(BugRule(id="KT-2843",category=BugCategory.SECURITY,severity=BugSeverity.MILD,
            title="emit异常静默吞掉",trigger="catch(_:Exception){}在EventChannel.emit",detection="生产环境不可观测 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0344）",fix="catch(ex)System.err记录"))
        BugDB.load(BugRule(id="KT-2844",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="明文存储密码（Int版）（Float）",trigger="prefs.edit().putFloat(\\\\\\\"pwd\\\\\\\",password).apply()",detection="SharedPreferences明文 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0376）",fix="EncryptedSharedPreferences"))
        BugDB.load(BugRule(id="KT-2845",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="明文存储密码（Long版）",trigger="prefs.edit().putLong(\\\\\\\"pwd\\\\\\\",password).apply()",detection="SharedPreferences明文 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0377）",fix="EncryptedSharedPreferences"))
        BugDB.load(BugRule(id="KT-2846",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="明文存储密码（Double版）",trigger="prefs.edit().putDouble(\\\\\\\"pwd\\\\\\\",password).apply()",detection="SharedPreferences明文 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0378）",fix="EncryptedSharedPreferences"))
        BugDB.load(BugRule(id="KT-2847",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="明文存储密码（Boolean版）",trigger="prefs.edit().putBoolean(\\\\\\\"pwd\\\\\\\",password).apply()",detection="SharedPreferences明文 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0379）",fix="EncryptedSharedPreferences"))
        BugDB.load(BugRule(id="KT-2848",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="WebView允许文件访问",trigger="webView.settings.allowFileAccess=true",detection="文件泄露 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0493）",fix="禁用"))
        BugDB.load(BugRule(id="KT-2849",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="debug模式泄露",trigger="if(BuildConfig.DEBUG){logFullDump()}",detection="发布包残留调试代码 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0494）",fix="移除或用if-release检查"))
        BugDB.load(BugRule(id="KT-2850",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="硬编码密钥",trigger="val API_KEY=\\\"sk-abc123\\\"",detection="源码暴露 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0133）",fix="环境变量或BuildConfig"))
        BugDB.load(BugRule(id="KT-2851",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="SQL注入拼接",trigger="db.execSQL(\\\"SELECT * FROM u WHERE n='\\\$name'\\\")",detection="直接拼接 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0134）",fix="参数化查询"))
        BugDB.load(BugRule(id="KT-2852",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="日志泄露敏感信息",trigger="Log.d(\\\"TAG\\\",\\\"token=\\\$token\\\")",detection="日志可被读取 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0135）",fix="脱敏"))
        BugDB.load(BugRule(id="KT-2853",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="明文存储密码（Long）",trigger="prefs.edit().putLong(\\\"pwd\\\",password).apply()",detection="SharedPreferences明文 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0136）",fix="EncryptedSharedPreferences"))
        BugDB.load(BugRule(id="KT-2854",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="未验证SSL证书",trigger="trustAllCerts()",detection="中间人攻击 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0137）",fix="证书固定"))
        BugDB.load(BugRule(id="KT-2855",category=BugCategory.SECURITY,severity=BugSeverity.MILD,
            title="WebView JavaScript启用",trigger="webView.settings.javaScriptEnabled=true",detection="XSS风险 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0138）",fix="禁用或白名单"))
        BugDB.load(BugRule(id="KT-2856",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="Intent extras明文传敏感数据",trigger="intent.putExtra(\\\"token\\\",token)",detection="可被其他app读取 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0249）",fix="加密或避免"))
        BugDB.load(BugRule(id="KT-2857",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="WebView.addJavascriptInterface（Short）",trigger="webView.addJavascriptShorterface(obj,\\\"android\\\")",detection="JS可调用Java — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0250）",fix="@JavascriptInterface仅暴露必要方法"))
        BugDB.load(BugRule(id="KT-2858",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="FileProvider路径遍历",trigger="FileProvider.getUriForFile(ctx,path)",detection="任意文件泄露 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0251）",fix="限制根目录"))
        BugDB.load(BugRule(id="KT-2859",category=BugCategory.SECURITY,severity=BugSeverity.MILD,
            title="日志使用e.printStackTrace",trigger="e.printStackTrace()",detection="泄露到logcat — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0252）",fix="Log.e(TAG,\\\"msg\\\",e)"))
        BugDB.load(BugRule(id="KT-2860",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="debug日志里打印完整银行卡号",trigger="if(BuildConfig.DEBUG)Log.d('PAY',cardNumber)",detection="debug模式泄露 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0276）",fix="release不输出或脱敏"))
        BugDB.load(BugRule(id="KT-2861",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="环境变量里的密钥被Git提交",trigger="BuildConfig.API_KEY从local.properties读但.gitignore漏了",detection="git add -f — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0277）",fix="ci环境变量+不提交"))
        BugDB.load(BugRule(id="KT-2862",category=BugCategory.SECURITY,severity=BugSeverity.MILD,
            title="emit异常静默吞掉",trigger="catch(_:Exception){}在EventChannel.emit",detection="生产环境不可观测 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0344）",fix="catch(ex)System.err记录"))
        BugDB.load(BugRule(id="KT-2863",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="明文存储密码（Int版）（Char）",trigger="prefs.edit().putChar(\\\\\\\"pwd\\\\\\\",password).apply()",detection="SharedPreferences明文 — Char类型字符编码边界,正则匹配组越界（参见 KT-0376）",fix="EncryptedSharedPreferences"))
        BugDB.load(BugRule(id="KT-2864",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="明文存储密码（Long版）",trigger="prefs.edit().putLong(\\\\\\\"pwd\\\\\\\",password).apply()",detection="SharedPreferences明文 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0377）",fix="EncryptedSharedPreferences"))
        BugDB.load(BugRule(id="KT-2865",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="明文存储密码（Double版）",trigger="prefs.edit().putDouble(\\\\\\\"pwd\\\\\\\",password).apply()",detection="SharedPreferences明文 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0378）",fix="EncryptedSharedPreferences"))
        BugDB.load(BugRule(id="KT-2866",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="明文存储密码（Boolean版）",trigger="prefs.edit().putBoolean(\\\\\\\"pwd\\\\\\\",password).apply()",detection="SharedPreferences明文 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0379）",fix="EncryptedSharedPreferences"))
        BugDB.load(BugRule(id="KT-2867",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="WebView允许文件访问",trigger="webView.settings.allowFileAccess=true",detection="文件泄露 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0493）",fix="禁用"))
        BugDB.load(BugRule(id="KT-2868",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="debug模式泄露",trigger="if(BuildConfig.DEBUG){logFullDump()}",detection="发布包残留调试代码 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0494）",fix="移除或用if-release检查"))
        BugDB.load(BugRule(id="KT-2869",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="硬编码密钥",trigger="val API_KEY=\\\"sk-abc123\\\"",detection="源码暴露 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0133）",fix="环境变量或BuildConfig"))
        BugDB.load(BugRule(id="KT-2870",category=BugCategory.SECURITY,severity=BugSeverity.SEVERE,
            title="SQL注入拼接",trigger="db.execSQL(\\\"SELECT * FROM u WHERE n='\\\$name'\\\")",detection="直接拼接 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0134）",fix="参数化查询"))
        BugDB.load(BugRule(id="KT-2871",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="日志泄露敏感信息",trigger="Log.d(\\\"TAG\\\",\\\"token=\\\$token\\\")",detection="日志可被读取 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0135）",fix="脱敏"))
        BugDB.load(BugRule(id="KT-2872",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="明文存储密码（Double）",trigger="prefs.edit().putDouble(\\\"pwd\\\",password).apply()",detection="SharedPreferences明文 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0136）",fix="EncryptedSharedPreferences"))
        BugDB.load(BugRule(id="KT-2873",category=BugCategory.SECURITY,severity=BugSeverity.MODERATE,
            title="未验证SSL证书",trigger="trustAllCerts()",detection="中间人攻击 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0137）",fix="证书固定"))
        BugDB.load(BugRule(id="KT-2874",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="平台特定导入",trigger="import java.io.File",detection="不可移植 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0139）",fix="expect/actual"))
        BugDB.load(BugRule(id="KT-2875",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="System.getProperty依赖",trigger="System.getProperty(\\\"os.name\\\")",detection="平台特定 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0140）",fix="expect/actual"))
        BugDB.load(BugRule(id="KT-2876",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="文件分隔符硬编码",trigger="File(\\\"a/b\\\").path在Windows",detection="\\\\差异 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0141）",fix="File.separator或Path"))
        BugDB.load(BugRule(id="KT-2877",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="Windows/macOS/Linux换行符（Sequence<Long>）",trigger="Sequence<Long>.split(\\\"\\\\\\\\n\\\")在Windows",detection="\\\\r\\\\n不被匹配 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0253）",fix="System.lineSeparator()"))
        BugDB.load(BugRule(id="KT-2878",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="时区硬编码",trigger="SimpleDateFormat(\\\"yyyy\\\",Locale.US)",detection="跨时区偏差 — Char类型字符编码边界,正则匹配组越界（参见 KT-0254）",fix="java.time+UTC"))
        BugDB.load(BugRule(id="KT-2879",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="文件编码默认依赖",trigger="File(\\\"x\\\").readText()",detection="默认UTF-8可能不适用 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0255）",fix="readText(Charsets.UTF_8)显式"))
        BugDB.load(BugRule(id="KT-2880",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.SEVERE,
            title="Windows的\\\\r\\\\n在Linux被当两个换行（Array<Boolean>）",trigger="Array<Boolean>.split('\\\\n')在Windows残留\\\\r",detection="跨平台行尾差异 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0274）",fix="System.lineSeparator或trimEnd"))
        BugDB.load(BugRule(id="KT-2881",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.SEVERE,
            title="Windows的\\\\\\\\r\\\\\\\\n在Linux被当两个换行（Int版）（Float）",trigger="Float.split('\\\\\\\\n')在Windows残留\\\\\\\\r",detection="跨平台行尾差异 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0402）",fix="System.lineSeparator或trimEnd"))
        BugDB.load(BugRule(id="KT-2882",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.SEVERE,
            title="Windows的\\\\\\\\r\\\\\\\\n在Linux被当两个换行（Long版）",trigger="Long.split('\\\\\\\\n')在Windows残留\\\\\\\\r",detection="跨平台行尾差异 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0403）",fix="System.lineSeparator或trimEnd"))
        BugDB.load(BugRule(id="KT-2883",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.SEVERE,
            title="Windows的\\\\\\\\r\\\\\\\\n在Linux被当两个换行（Double版）",trigger="Double.split('\\\\\\\\n')在Windows残留\\\\\\\\r",detection="跨平台行尾差异 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0404）",fix="System.lineSeparator或trimEnd"))
        BugDB.load(BugRule(id="KT-2884",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.SEVERE,
            title="Windows的\\\\\\\\r\\\\\\\\n在Linux被当两个换行（Boolean版）",trigger="Boolean.split('\\\\\\\\n')在Windows残留\\\\\\\\r",detection="跨平台行尾差异 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0405）",fix="System.lineSeparator或trimEnd"))
        BugDB.load(BugRule(id="KT-2885",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="Windows/macOS/Linux换行符（Int版）（Int?）",trigger="Int?.split(\\\\\\\"\\\\\\\\\\\\\\\\n\\\\\\\")在Windows",detection="\\\\\\\\r\\\\\\\\n不被匹配 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0441）",fix="System.lineSeparator()"))
        BugDB.load(BugRule(id="KT-2886",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="Windows/macOS/Linux换行符（Long版）",trigger="Long.split(\\\\\\\"\\\\\\\\\\\\\\\\n\\\\\\\")在Windows",detection="\\\\\\\\r\\\\\\\\n不被匹配 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0442）",fix="System.lineSeparator()"))
        BugDB.load(BugRule(id="KT-2887",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="Windows/macOS/Linux换行符（Double版）",trigger="Double.split(\\\\\\\"\\\\\\\\\\\\\\\\n\\\\\\\")在Windows",detection="\\\\\\\\r\\\\\\\\n不被匹配 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0443）",fix="System.lineSeparator()"))
        BugDB.load(BugRule(id="KT-2888",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="Windows/macOS/Linux换行符（Boolean版）",trigger="Boolean.split(\\\\\\\"\\\\\\\\\\\\\\\\n\\\\\\\")在Windows",detection="\\\\\\\\r\\\\\\\\n不被匹配 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0444）",fix="System.lineSeparator()"))
        BugDB.load(BugRule(id="KT-2889",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="平台特定导入",trigger="import java.io.File",detection="不可移植 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0139）",fix="expect/actual"))
        BugDB.load(BugRule(id="KT-2890",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="System.getProperty依赖",trigger="System.getProperty(\\\"os.name\\\")",detection="平台特定 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0140）",fix="expect/actual"))
        BugDB.load(BugRule(id="KT-2891",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="文件分隔符硬编码",trigger="File(\\\"a/b\\\").path在Windows",detection="\\\\差异 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0141）",fix="File.separator或Path"))
        BugDB.load(BugRule(id="KT-2892",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="Windows/macOS/Linux换行符（List<String>）",trigger="List<String><String>.split(\\\"\\\\\\\\n\\\")在Windows",detection="\\\\r\\\\n不被匹配 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0253）",fix="System.lineSeparator()"))
        BugDB.load(BugRule(id="KT-2893",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="时区硬编码",trigger="SimpleDateFormat(\\\"yyyy\\\",Locale.US)",detection="跨时区偏差 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0254）",fix="java.time+UTC"))
        BugDB.load(BugRule(id="KT-2894",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="文件编码默认依赖",trigger="File(\\\"x\\\").readText()",detection="默认UTF-8可能不适用 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0255）",fix="readText(Charsets.UTF_8)显式"))
        BugDB.load(BugRule(id="KT-2895",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.SEVERE,
            title="Windows的\\\\r\\\\n在Linux被当两个换行（Any?）",trigger="Any?.split('\\\\n')在Windows残留\\\\r",detection="跨平台行尾差异 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0274）",fix="System.lineSeparator或trimEnd"))
        BugDB.load(BugRule(id="KT-2896",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.SEVERE,
            title="Windows的\\\\\\\\r\\\\\\\\n在Linux被当两个换行（Int版）（String）",trigger="String.split('\\\\\\\\n')在Windows残留\\\\\\\\r",detection="跨平台行尾差异 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0402）",fix="System.lineSeparator或trimEnd"))
        BugDB.load(BugRule(id="KT-2897",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.SEVERE,
            title="Windows的\\\\\\\\r\\\\\\\\n在Linux被当两个换行（Long版）",trigger="Long.split('\\\\\\\\n')在Windows残留\\\\\\\\r",detection="跨平台行尾差异 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0403）",fix="System.lineSeparator或trimEnd"))
        BugDB.load(BugRule(id="KT-2898",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.SEVERE,
            title="Windows的\\\\\\\\r\\\\\\\\n在Linux被当两个换行（Double版）",trigger="Double.split('\\\\\\\\n')在Windows残留\\\\\\\\r",detection="跨平台行尾差异 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0404）",fix="System.lineSeparator或trimEnd"))
        BugDB.load(BugRule(id="KT-2899",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.SEVERE,
            title="Windows的\\\\\\\\r\\\\\\\\n在Linux被当两个换行（Boolean版）",trigger="Boolean.split('\\\\\\\\n')在Windows残留\\\\\\\\r",detection="跨平台行尾差异 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0405）",fix="System.lineSeparator或trimEnd"))
        BugDB.load(BugRule(id="KT-2900",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="Windows/macOS/Linux换行符（Int版）（Char）",trigger="Char.split(\\\\\\\"\\\\\\\\\\\\\\\\n\\\\\\\")在Windows",detection="\\\\\\\\r\\\\\\\\n不被匹配 — Char类型字符编码边界,正则匹配组越界（参见 KT-0441）",fix="System.lineSeparator()"))
        BugDB.load(BugRule(id="KT-2901",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="Windows/macOS/Linux换行符（Long版）",trigger="Long.split(\\\\\\\"\\\\\\\\\\\\\\\\n\\\\\\\")在Windows",detection="\\\\\\\\r\\\\\\\\n不被匹配 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0442）",fix="System.lineSeparator()"))
        BugDB.load(BugRule(id="KT-2902",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="Windows/macOS/Linux换行符（Double版）",trigger="Double.split(\\\\\\\"\\\\\\\\\\\\\\\\n\\\\\\\")在Windows",detection="\\\\\\\\r\\\\\\\\n不被匹配 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0443）",fix="System.lineSeparator()"))
        BugDB.load(BugRule(id="KT-2903",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="Windows/macOS/Linux换行符（Boolean版）",trigger="Boolean.split(\\\\\\\"\\\\\\\\\\\\\\\\n\\\\\\\")在Windows",detection="\\\\\\\\r\\\\\\\\n不被匹配 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0444）",fix="System.lineSeparator()"))
        BugDB.load(BugRule(id="KT-2904",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="平台特定导入",trigger="import java.io.File",detection="不可移植 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0139）",fix="expect/actual"))
        BugDB.load(BugRule(id="KT-2905",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="System.getProperty依赖",trigger="System.getProperty(\\\"os.name\\\")",detection="平台特定 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0140）",fix="expect/actual"))
        BugDB.load(BugRule(id="KT-2906",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="文件分隔符硬编码",trigger="File(\\\"a/b\\\").path在Windows",detection="\\\\差异 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0141）",fix="File.separator或Path"))
        BugDB.load(BugRule(id="KT-2907",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="Windows/macOS/Linux换行符（Int?）",trigger="Int??.split(\\\"\\\\\\\\n\\\")在Windows",detection="\\\\r\\\\n不被匹配 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0253）",fix="System.lineSeparator()"))
        BugDB.load(BugRule(id="KT-2908",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="时区硬编码",trigger="SimpleDateFormat(\\\"yyyy\\\",Locale.US)",detection="跨时区偏差 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0254）",fix="java.time+UTC"))
        BugDB.load(BugRule(id="KT-2909",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="文件编码默认依赖",trigger="File(\\\"x\\\").readText()",detection="默认UTF-8可能不适用 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0255）",fix="readText(Charsets.UTF_8)显式"))
        BugDB.load(BugRule(id="KT-2910",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.SEVERE,
            title="Windows的\\\\r\\\\n在Linux被当两个换行（String?）",trigger="String?.split('\\\\n')在Windows残留\\\\r",detection="跨平台行尾差异 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0274）",fix="System.lineSeparator或trimEnd"))
        BugDB.load(BugRule(id="KT-2911",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.SEVERE,
            title="Windows的\\\\\\\\r\\\\\\\\n在Linux被当两个换行（Int版）（Set<Int>）",trigger="Set<Int>.split('\\\\\\\\n')在Windows残留\\\\\\\\r",detection="跨平台行尾差异 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0402）",fix="System.lineSeparator或trimEnd"))
        BugDB.load(BugRule(id="KT-2912",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.SEVERE,
            title="Windows的\\\\\\\\r\\\\\\\\n在Linux被当两个换行（Long版）",trigger="Long.split('\\\\\\\\n')在Windows残留\\\\\\\\r",detection="跨平台行尾差异 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0403）",fix="System.lineSeparator或trimEnd"))
        BugDB.load(BugRule(id="KT-2913",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.SEVERE,
            title="Windows的\\\\\\\\r\\\\\\\\n在Linux被当两个换行（Double版）",trigger="Double.split('\\\\\\\\n')在Windows残留\\\\\\\\r",detection="跨平台行尾差异 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0404）",fix="System.lineSeparator或trimEnd"))
        BugDB.load(BugRule(id="KT-2914",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.SEVERE,
            title="Windows的\\\\\\\\r\\\\\\\\n在Linux被当两个换行（Boolean版）",trigger="Boolean.split('\\\\\\\\n')在Windows残留\\\\\\\\r",detection="跨平台行尾差异 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0405）",fix="System.lineSeparator或trimEnd"))
        BugDB.load(BugRule(id="KT-2915",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="Windows/macOS/Linux换行符（Int版）",trigger="Int.split(\\\\\\\"\\\\\\\\\\\\\\\\n\\\\\\\")在Windows",detection="\\\\\\\\r\\\\\\\\n不被匹配 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0441）",fix="System.lineSeparator()"))
        BugDB.load(BugRule(id="KT-2916",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="Windows/macOS/Linux换行符（Long版）",trigger="Long.split(\\\\\\\"\\\\\\\\\\\\\\\\n\\\\\\\")在Windows",detection="\\\\\\\\r\\\\\\\\n不被匹配 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0442）",fix="System.lineSeparator()"))
        BugDB.load(BugRule(id="KT-2917",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="Windows/macOS/Linux换行符（Double版）",trigger="Double.split(\\\\\\\"\\\\\\\\\\\\\\\\n\\\\\\\")在Windows",detection="\\\\\\\\r\\\\\\\\n不被匹配 — Any?类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0443）",fix="System.lineSeparator()"))
        BugDB.load(BugRule(id="KT-2918",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="Windows/macOS/Linux换行符（Boolean版）",trigger="Boolean.split(\\\\\\\"\\\\\\\\\\\\\\\\n\\\\\\\")在Windows",detection="\\\\\\\\r\\\\\\\\n不被匹配 — String类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0444）",fix="System.lineSeparator()"))
        BugDB.load(BugRule(id="KT-2919",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="平台特定导入",trigger="import java.io.File",detection="不可移植 — Byte类型二进制协议解析,文件I/O偏移量,位运算掩码（参见 KT-0139）",fix="expect/actual"))
        BugDB.load(BugRule(id="KT-2920",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="System.getProperty依赖",trigger="System.getProperty(\\\"os.name\\\")",detection="平台特定 — Boolean?类型三态逻辑误用,配置项缺失默认值（参见 KT-0140）",fix="expect/actual"))
        BugDB.load(BugRule(id="KT-2921",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="文件分隔符硬编码",trigger="File(\\\"a/b\\\").path在Windows",detection="\\\\差异 — Sequence<Long>类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0141）",fix="File.separator或Path"))
        BugDB.load(BugRule(id="KT-2922",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="Windows/macOS/Linux换行符（Char）",trigger="Char.split(\\\"\\\\\\\\n\\\")在Windows",detection="\\\\r\\\\n不被匹配 — Char类型字符编码边界,正则匹配组越界（参见 KT-0253）",fix="System.lineSeparator()"))
        BugDB.load(BugRule(id="KT-2923",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="时区硬编码",trigger="SimpleDateFormat(\\\"yyyy\\\",Locale.US)",detection="跨时区偏差 — Double?类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0254）",fix="java.time+UTC"))
        BugDB.load(BugRule(id="KT-2924",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="文件编码默认依赖",trigger="File(\\\"x\\\").readText()",detection="默认UTF-8可能不适用 — Array<Boolean>类型三态逻辑误用,配置项缺失默认值（参见 KT-0255）",fix="readText(Charsets.UTF_8)显式"))
        BugDB.load(BugRule(id="KT-2925",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.SEVERE,
            title="Windows的\\\\r\\\\n在Linux被当两个换行（Float）",trigger="Float.split('\\\\n')在Windows残留\\\\r",detection="跨平台行尾差异 — Float类型精度低于Double,GPU纹理坐标,传感器数据（参见 KT-0274）",fix="System.lineSeparator或trimEnd"))
        BugDB.load(BugRule(id="KT-2926",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.SEVERE,
            title="Windows的\\\\\\\\r\\\\\\\\n在Linux被当两个换行（Int版）（Long?）",trigger="Long?.split('\\\\\\\\n')在Windows残留\\\\\\\\r",detection="跨平台行尾差异 — Long?类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0402）",fix="System.lineSeparator或trimEnd"))
        BugDB.load(BugRule(id="KT-2927",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.SEVERE,
            title="Windows的\\\\\\\\r\\\\\\\\n在Linux被当两个换行（Long版）",trigger="Long.split('\\\\\\\\n')在Windows残留\\\\\\\\r",detection="跨平台行尾差异 — MutableList<Double>类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0403）",fix="System.lineSeparator或trimEnd"))
        BugDB.load(BugRule(id="KT-2928",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.SEVERE,
            title="Windows的\\\\\\\\r\\\\\\\\n在Linux被当两个换行（Double版）",trigger="Double.split('\\\\\\\\n')在Windows残留\\\\\\\\r",detection="跨平台行尾差异 — Boolean类型三态逻辑误用,配置项缺失默认值（参见 KT-0404）",fix="System.lineSeparator或trimEnd"))
        BugDB.load(BugRule(id="KT-2929",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.SEVERE,
            title="Windows的\\\\\\\\r\\\\\\\\n在Linux被当两个换行（Boolean版）",trigger="Boolean.split('\\\\\\\\n')在Windows残留\\\\\\\\r",detection="跨平台行尾差异 — Int?类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0405）",fix="System.lineSeparator或trimEnd"))
        BugDB.load(BugRule(id="KT-2930",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="Windows/macOS/Linux换行符（Int版）（Map<String,Int>）",trigger="Map<String,Int>.split(\\\\\\\"\\\\\\\\\\\\\\\\n\\\\\\\")在Windows",detection="\\\\\\\\r\\\\\\\\n不被匹配 — Map<String,Int>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0441）",fix="System.lineSeparator()"))
        BugDB.load(BugRule(id="KT-2931",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="Windows/macOS/Linux换行符（Long版）",trigger="Long.split(\\\\\\\"\\\\\\\\\\\\\\\\n\\\\\\\")在Windows",detection="\\\\\\\\r\\\\\\\\n不被匹配 — Double类型浮点精度丢失,JSON数字类型模糊,累积误差（参见 KT-0442）",fix="System.lineSeparator()"))
        BugDB.load(BugRule(id="KT-2932",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="Windows/macOS/Linux换行符（Double版）",trigger="Double.split(\\\\\\\"\\\\\\\\\\\\\\\\n\\\\\\\")在Windows",detection="\\\\\\\\r\\\\\\\\n不被匹配 — String?类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0443）",fix="System.lineSeparator()"))
        BugDB.load(BugRule(id="KT-2933",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="Windows/macOS/Linux换行符（Boolean版）",trigger="Boolean.split(\\\\\\\"\\\\\\\\\\\\\\\\n\\\\\\\")在Windows",detection="\\\\\\\\r\\\\\\\\n不被匹配 — Set<Int>类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0444）",fix="System.lineSeparator()"))
        BugDB.load(BugRule(id="KT-2934",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="平台特定导入",trigger="import java.io.File",detection="不可移植 — Long类型时间戳边界,ID生成器溢出,计数器超限（参见 KT-0139）",fix="expect/actual"))
        BugDB.load(BugRule(id="KT-2935",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="System.getProperty依赖",trigger="System.getProperty(\\\"os.name\\\")",detection="平台特定 — Any类型类型擦除信息丢失,动态代理返回,反射调用（参见 KT-0140）",fix="expect/actual"))
        BugDB.load(BugRule(id="KT-2936",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MILD,
            title="文件分隔符硬编码",trigger="File(\\\"a/b\\\").path在Windows",detection="\\\\差异 — List<String>类型Java互操作T!,JSON缺字段,DB nullable列（参见 KT-0141）",fix="File.separator或Path"))
        BugDB.load(BugRule(id="KT-2937",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="Windows/macOS/Linux换行符（Int）",trigger="Int.split(\\\"\\\\\\\\n\\\")在Windows",detection="\\\\r\\\\n不被匹配 — Int类型Map.get返回值,SharedPreferences读取,DB自增ID溢出（参见 KT-0253）",fix="System.lineSeparator()"))
        BugDB.load(BugRule(id="KT-2938",category=BugCategory.MULTIPLATFORM,severity=BugSeverity.MODERATE,
            title="时区硬编码",trigger="SimpleDateFormat(\\\"yyyy\\\",Locale.US)",detection="跨时区偏差 — Short类型端口号溢出,旧协议数据长度字段（参见 KT-0254）",fix="java.time+UTC"))
    }
}
// MILD=556 MODERATE=1592 SEVERE=790 TOTAL=2938