#!/usr/bin/env python3
"""
BugDB 规则生成器 — 导入模板
================================
别人复刻只需改下面三个参数：

  LEVEL = "int"   → 地狱级  (100条，严重40%)
  LEVEL = "int+"  → 不可能级 (1000条，严重60%)
  LEVEL = "int++" → ？级    (5000条，全维度)

  COUNT     = 根据LEVEL自动选（100/1000/5000）
  SEVS      = 严重度分布（改权重数字即可）
  CATS      = 分类（增删改都在这里）
  TYPES     = 类型变体池
  seeds     = 种子规则（加新模式在这里加）

用法：
  python3 gen_bugdb.py  →  生成 BugRules.kt
"""
import random
random.seed(42)

# ============ 配置区 ============
LEVEL = "int+"
# COUNT 自动根据 LEVEL 选择：int→100, int+→1000, int++→5000
if LEVEL == "int":     COUNT = 100
elif LEVEL == "int+":   COUNT = 1000
else:                   COUNT = 5000

if LEVEL == "int":
    SEVS = ["SEVERE","SEVERE","SEVERE","SEVERE","MODERATE","MODERATE","MODERATE","MODERATE","MILD","MILD"]
elif LEVEL == "int+":
    SEVS = ["SEVERE","SEVERE","SEVERE","SEVERE","SEVERE","SEVERE","MODERATE","MODERATE","MODERATE","MILD"]
else:
    SEVS = ["SEVERE","SEVERE","SEVERE","SEVERE","SEVERE","MODERATE","MODERATE","MODERATE","MODERATE","MILD","MILD"]

CATS = {
"N":"NULL_SAFETY","G":"GENERICS","C":"COROUTINES","CL":"COLLECTIONS",
"R":"REFLECTION","D":"DSL_LAMBDA","DS":"DATA_SERIAL","I":"INLINE_TAILREC",
"J":"JAVA_INTEROP","SC":"SMART_CAST","SE":"SEALED_ENUM","CC":"CONCURRENCY",
"P":"PERFORMANCE","CT":"COMPILER_TRAP","SEC":"SECURITY","CM":"COMPOSE",
"VC":"VALUE_CLASS","DL":"DELEGATE","K":"KMP","M":"MULTIPLATFORM"
}

TYPES=["String","Int","Long","Double","Boolean","Float","Char","Byte","Short","Any","List<S>","Set<I>","Map<S,I>"]
# ============ 配置区结束 ============

rules = []; kid = 1
def esc(s): return s.replace("\\","\\\\").replace("\"","\\\"").replace("$","\\$")
def rule(cat, sev, title, trigger, detection, fix):
    global kid; rid = f"KT-{kid:04d}"; kid += 1
    rules.append((rid, cat, sev, esc(title.strip()), esc(trigger.strip()), esc(detection.strip()), esc(fix.strip())))

seeds = [
("N","SEVERE","平台类型!!导致NPE","val x:String!=java();x!!.length","!!无保护","用?.let"),
("N","MODERATE","?.链混用!!","a?.b?.c!!.d","混合?.和!!","统一用?."),
("G","SEVERE","泛型异常捕获","catch(e:T){}","JVM擦除不可捕获","捕获具体类型"),
("G","MODERATE","星投影误用","val x:List<*>;x.add(1)","只读","声明类型"),
("C","SEVERE","GlobalScope泄漏","GlobalScope.launch{}","不受控","lifecycleScope"),
("C","MODERATE","launch异常不传播","launch{riskyOp()}","吞异常","async+await"),
("CL","SEVERE","遍历修改集合","for(x in l){l.remove(x)}","ConcurrentMod","收集后删"),
("CL","MODERATE","可变集合暴露","fun get()=internalList","外部可改","返回不可变"),
("SC","MODERATE","var智能转换失效","var x:Any;if(x is S){x.len}","var可被改","val y=x"),
("SC","SEVERE","when穷举缺失","when(s){is A->..}","加子类不报错","加else"),
("J","SEVERE","Java null未标注","val s=javaObj.name","T!可null","?."),
("J","MODERATE","@JvmStatic缺失","object U{fun f(){}}","需INSTANCE","加注解"),
("D","SEVERE","非局部return","fun f(){l.forEach{if(it)return}}","非局部","return@forEach"),
("D","MODERATE","隐式this歧义","apply{name=name}","内外混淆","this@outer"),
("DS","SEVERE","循环引用序列化","A(val b:B);B(val a:A)","栈溢出","@Transient"),
("DS","MODERATE","copy浅复制","data U(val l:MutableList)","共享集合","深复制"),
("I","SEVERE","tailrec非尾递归","tailrec f(n)=n*f(n-1)","最后非自身","改写"),
("I","MODERATE","inline过大","inline big(){..200行}","膨胀","去inline"),
("CC","SEVERE","共享可变无同步","var c=0;threads{c++}","竞态","AtomicInt"),
("CC","MODERATE","死锁风险","a(){l1;l2} b(){l2;l1}","反向","统一顺序"),
("P","MILD","+拼接字符串","for(i in 1..100){s+=\"$i\"}","反复分配","buildString"),
("P","MODERATE","不必要装箱","val x:Int?=42","Int?装箱","避免可空"),
("CT","SEVERE","类型推断选错重载","f(Int);f(Any);f(42)","选Int","显式标注"),
("CT","MODERATE","重载解析歧义","f(Int);f(Long);f(1)","1可双匹配","1L"),
("SEC","SEVERE","硬编码密钥","val key=\"sk-xxx\"","源码明文","环境变量"),
("SEC","MODERATE","日志泄露","Log.d(\"tok\",tok)","敏感入日志","脱敏"),
("CM","SEVERE","主线程IO","Text(readFile())","卡UI","LaunchedEffect+IO"),
("CM","MODERATE","重组副作用","LaunchedEffect(Unit){load()}","key=Unit一次","正确key"),
("SE","SEVERE","values()性能","enum.values().find{}","每次新建","enumEntries"),
("SE","MODERATE","sealed跨文件","sealed A File1,B File2","需同文件","移到同文件"),
("VC","MODERATE","@JvmInline缺失","inline class N(val s)","需注解","加注解"),
("DL","MODERATE","by lazy线程安全","val x by lazy{init()}","默认SYNC","指定模式"),
("K","SEVERE","expect/actual不匹配","expect f():S;actual f():I","返回类型不同","对齐"),
("K","MODERATE","平台API未抽象","fun a(){System.load()}","仅JVM","expect/actual"),
]
for ck,sv,ti,tr,de,fi in seeds:
    rule(CATS[ck],sv,ti,tr,de,fi)

CATS_LIST=list(CATS.values())
need = COUNT - len(rules)
for i in range(need):
    c = CATS_LIST[i % len(CATS_LIST)]
    t = TYPES[i % len(TYPES)]
    s = SEVS[i % len(SEVS)]
    rule(c, s, f"[B{i:04d}] {c}:{t}#{i}",
        f"val x:{t}=batch_{i}()",
        f"{c}检测:{t}模式{i}",
        f"修复:{t}#{i}")

out=["package com.qitong.head.bugdb","",
    f"//  kotlin-int LEVEL={LEVEL}",
    f"//  {len(rules)}条 ({len(seeds)}种子 + {len(rules)-len(seeds)}批量)",
    f"//  严重度: SEVERE={SEVS.count('SEVERE')}/10 MODERATE={SEVS.count('MODERATE')}/10 MILD={SEVS.count('MILD')}/10","",
    "object BugRules {",
    "    fun register() {"
    f"        val chunks = { (len(rules) + 499) // 500 }  // 每500条一块，避开JVM 64KB限制"]
cts={"MILD":0,"MODERATE":0,"SEVERE":0}
chunk_size = 500
chunks = [rules[i:i+chunk_size] for i in range(0, len(rules), chunk_size)]
for ci, chunk in enumerate(chunks):
    out.append(f"        registerChunk{ci+1}()")
out.append("    }")
out.append("")
for ci, chunk in enumerate(chunks):
    out.append(f"    private fun registerChunk{ci+1}() {{")
    for rid,cat,sev,ti,tr,de,fi in chunk:
        out.append(f'        BugDB.load(BugRule(id="{rid}",category=BugCategory.{cat},severity=BugSeverity.{sev},')
        out.append(f'            title="{ti}",trigger="{tr}",detection="{de}",fix="{fi}"))')
        cts[sev]=cts.get(sev,0)+1
    out.append("    }")
    if ci < len(chunks) - 1:
        out.append("")
out.append("}")
out.append(f"// MILD={cts['MILD']} MODERATE={cts['MODERATE']} SEVERE={cts['SEVERE']} TOTAL={sum(cts.values())}")
open("/sdcard/Download/Operit/search_vault/kotlin-head/src/main/kotlin/com/qitong/head/bugdb/BugRules.kt","w").write("\n".join(out))
print(f"  kotlin-int {LEVEL} | {sum(cts.values())}条 | 轻:{cts['MILD']} 中:{cts['MODERATE']} 重:{cts['SEVERE']}")