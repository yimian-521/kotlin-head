# BugDB 对照手册 —— 描述 → 修复
> 总规则: 2937 条 | 生成: 2026-07-12
> 🔗 = 组合/叠加bug，必须按组合路径修，不能拆开单独修

## COLLECTIONS（197条）

| ID | 严重度 | Bug描述 | 触发模式 | 修复建议 |
|---|------|------|------|------|
| KT-0047 | 🔴 | 遍历时修改 | *另修: 用removeAll{条件}, 转CopyOnWriteArrayList, 用iterator.remove()* |`for(x in list){list.remove(x)}` | 收集待删项再删 |
| KT-0048 | 🔴 | 迭代器并发修改 | `val it=list.iterator();list.add(x);it.next()` | 先收集再操作 |
| KT-0049 | 🔴 | MutableList暴露 | `fun getList()=mutableList` | toList()或Collections.unmodifiableList |
| KT-0050 | 🟡 | first()无元素 | `emptyList<Int>().first()` | firstOrNull |
| KT-0051 | 🟡 | single()多元素 | `listOf(1,2).single()` | first()或singleOrNull |
| KT-0052 | 🟡 | HashMap键可变 | `val k=MutableObj();map[k]=v;k.mutate();map[k]` | 用不可变键 |
| KT-0053 | 🟡 | filter后仍操作原集合 | `list.filter{;list.add(x)` | 保存filter结果 |
| KT-0054 | ⚪ | 不必要的toList | `alreadyList.toList()` | 直接使用 |
| KT-0055 | ⚪ | map后丢弃 | `list.map{it*2}` | 用forEach |
| KT-0056 | 🟡 | 遍历List<String>时修改 | `for(x in {ct.lower()}){{ {ct.lower()}.remove(x) }}` | 收集后删 |
| KT-0057 | 🟡 | 遍历Set<Int>时修改 | `for(x in {ct.lower()}){{ {ct.lower()}.remove(x) }}` | 收集后删 |
| KT-0058 | 🟡 | 遍历Map<String,Int>时修改 | `for(x in {ct.lower()}){{ {ct.lower()}.remove(x) }}` | 收集后删 |
| KT-0172 | 🔴 | List.sort后索引错乱 | `val idx=list.indexOf(x);list.sort();list[idx]` | 先记录再排序或使用sorted() |
| KT-0173 | 🟡 | MutableList.subList泄漏 | `val sub=mutableList.subList(0,5);mutableList.clear()` | 先copy再操作 |
| KT-0174 | 🟡 | Set.contains自定义对象无hashCode | `setOf(Obj(1)).contains(Obj(1))` | 重写equals+hashCode |
| KT-0175 | 🟡 | Map.getOrDefault惰性求值 | `map.getOrDefault(k,expensive())` | map.getOrPut(k){expensive()} |
| KT-0176 | ⚪ | 不必要的toSet/unique | `list.toSet().toList()` | list.distinct() |
| KT-0177 | ⚪ | 空集合操作 | `emptyList<Int>().reduce{a,b->a+b}` | ifEmpty+fold |
| KT-0178 | 🔴 | Vector(已弃用)仍使用 | `val v=Vector<Int>();v.add(1)` | ArrayList/CopyOnWriteArrayList |
| KT-0179 | 🟡 | groupBy返回LinkedHashMap依赖顺序 | `val g=list.groupBy{it.key};g.forEach{}` | 显式sorted |
| KT-0345 | 🟡 | MutableList.subList泄漏（Set版） | `val sub=mutableSet.subSet(0,5);mutableSet.clear()` | 先copy再操作 |
| KT-0346 | 🟡 | first()无元素（Long版） | `emptyList<Long>().first()` | firstOrNull |
| KT-0368 | ⚪ | 不必要的toSet/unique（Set版） | `list.toSet().toSet()` | list.distinct() |
| KT-0414 | 🔴 | Vector(已弃用)仍使用（Long版） | `val v=Vector<Long>();v.add(1)` | ArrayList/CopyOnWriteArrayList |
| KT-0415 | 🔴 | Vector(已弃用)仍使用（Double版） | `val v=Vector<Double>();v.add(1)` | ArrayList/CopyOnWriteArrayList |
| KT-0416 | 🔴 | Vector(已弃用)仍使用（Float版） | `val v=Vector<Float>();v.add(1)` | ArrayList/CopyOnWriteArrayList |
| KT-0464 | 🔴 | MutableList暴露（Set版） | `fun getSet()=mutableSet` | toSet()或Collections.unmodifiableSet |
| KT-0480 | 🟡 | Paging3重复加载 | `PagingSource.load()返回值未去重` | distinctUntilChanged |
| KT-1179 | 🔴 | COLLECTIONS深度变异String#0 | `val x:String=...;for(x in list){list.remove(x)}` | 收集待删项再删 |
| KT-1180 | 🔴 | COLLECTIONS深度变异Byte#1 | `val x:Byte=...;val it=list.iterator();list.add(x);it.next()` | 先收集再操作 |
| KT-1181 | 🔴 | COLLECTIONS深度变异Boolean?#2 | `fun getBoolean?()=mutableBoolean?` | toList()或Collections.unmodifiableList |
| KT-1182 | 🟡 | COLLECTIONS深度变异Sequence<Long>#3 | `emptySequence<Long><Sequence<Long>>().first()` | firstOrNull |
| KT-1183 | 🟡 | COLLECTIONS深度变异Char#4 | `val x:Char=...;listOf(1,2).single()` | first()或singleOrNull |
| KT-1184 | 🟡 | COLLECTIONS深度变异Double?#5 | `val x:Double?=...;val k=MutableObj();map[k]=v;k.mutate();map` | 用不可变键 |
| KT-1185 | 🟡 | COLLECTIONS深度变异Array<Boolean>#6 | `val x:Array<Boolean>=...;list.filter{;list.add(x)` | 保存filter结果 |
| KT-1186 | ⚪ | COLLECTIONS深度变异Float#7 | `alreadyFloat.toFloat()` | 直接使用 |
| KT-1187 | ⚪ | COLLECTIONS深度变异Long?#8 | `val x:Long?=...;list.map{it*2}` | 用forEach |
| KT-1188 | 🟡 | COLLECTIONS深度变异MutableList<Double>#9 | `val x:MutableList<Double>=...;for(x in {ct.lower()}){{ {ct.l` | 收集后删 |
| KT-1189 | 🟡 | COLLECTIONS深度变异Boolean#10 | `val x:Boolean=...;for(x in {ct.lower()}){{ {ct.lower()}.remo` | 收集后删 |
| KT-1190 | 🟡 | COLLECTIONS深度变异Int?#11 | `val x:Int?=...;for(x in {ct.lower()}){{ {ct.lower()}.remove(` | 收集后删 |
| KT-1191 | 🔴 | COLLECTIONS深度变异Map<String,Int>#12 | `val x:Map<String,Int>=...;val idx=list.indexOf(x);list.sort(` | 先记录再排序或使用sorted() |
| KT-1192 | 🟡 | COLLECTIONS深度变异Double#13 | `val sub=mutableDouble.subDouble(0,5);mutableDouble.clear()` | 先copy再操作 |
| KT-1193 | 🟡 | COLLECTIONS深度变异String?#14 | `val x:String?=...;setOf(Obj(1)).contains(Obj(1))` | 重写equals+hashCode |
| KT-1194 | 🟡 | COLLECTIONS深度变异Set<Int>#15 | `val x:Set<Int>=...;map.getOrDefault(k,expensive())` | map.getOrPut(k){expensive()} |
| KT-1195 | ⚪ | COLLECTIONS深度变异Long#16 | `list.toSet().toLong()` | list.distinct() |
| KT-1196 | ⚪ | COLLECTIONS深度变异Any#17 | `emptyAny<Any>().reduce{a,b->a+b}` | ifEmpty+fold |
| KT-1197 | 🔴 | COLLECTIONS深度变异List<String>#18 | `val v=Vector<List<String><String>>();v.add(1)` | ArrayList/CopyOnWriteArrayList |
| KT-1198 | 🟡 | COLLECTIONS深度变异Int#19 | `val x:Int=...;val g=list.groupBy{it.key};g.forEach{}` | 显式sorted |
| KT-1199 | 🟡 | COLLECTIONS深度变异Short#20 | `val x:Short=...;val sub=mutableSet.subSet(0,5);mutableSet.cl` | 先copy再操作 |
| KT-1200 | 🟡 | COLLECTIONS深度变异Any?#21 | `emptyAny?<Long>().first()` | firstOrNull |
| KT-1201 | ⚪ | COLLECTIONS深度变异String#22 | `val x:String=...;list.toSet().toSet()` | list.distinct() |
| KT-1202 | 🔴 | COLLECTIONS深度变异Byte#23 | `val x:Byte=...;val v=Vector<Long>();v.add(1)` | ArrayList/CopyOnWriteArrayList |
| KT-1203 | 🔴 | COLLECTIONS深度变异Boolean?#24 | `val x:Boolean?=...;val v=Vector<Double>();v.add(1)` | ArrayList/CopyOnWriteArrayList |
| KT-1204 | 🔴 | COLLECTIONS深度变异Sequence<Long>#25 | `val x:Sequence<Long>=...;val v=Vector<Float>();v.add(1)` | ArrayList/CopyOnWriteArrayList |
| KT-1205 | 🔴 | COLLECTIONS深度变异Char#26 | `val x:Char=...;fun getSet()=mutableSet` | toSet()或Collections.unmodifiableSet |
| KT-1206 | 🟡 | COLLECTIONS深度变异Double?#27 | `val x:Double?=...;PagingSource.load()返回值未去重` | distinctUntilChanged |
| KT-1207 | 🔴 | COLLECTIONS深度变异Array<Boolean>#28 | `val x:Array<Boolean>=...;for(x in list){list.remove(x)}` | 收集待删项再删 |
| KT-1208 | 🔴 | COLLECTIONS深度变异Float#29 | `val x:Float=...;val it=list.iterator();list.add(x);it.next()` | 先收集再操作 |
| KT-1209 | 🟡 | COLLECTIONS深度变异MutableList<Double>#31 | `emptyMutableList<Double><MutableMutableList<Double><Double>>` | firstOrNull |
| KT-1210 | 🟡 | COLLECTIONS深度变异Boolean#32 | `val x:Boolean=...;listOf(1,2).single()` | first()或singleOrNull |
| KT-1211 | 🟡 | COLLECTIONS深度变异Int?#33 | `val x:Int?=...;val k=MutableObj();map[k]=v;k.mutate();map[k]` | 用不可变键 |
| KT-1212 | 🟡 | COLLECTIONS深度变异Map<String,Int>#34 | `val x:Map<String,Int>=...;list.filter{;list.add(x)` | 保存filter结果 |
| KT-1213 | ⚪ | COLLECTIONS深度变异Double#35 | `alreadyDouble.toDouble()` | 直接使用 |
| KT-1214 | ⚪ | COLLECTIONS深度变异String?#36 | `val x:String?=...;list.map{it*2}` | 用forEach |
| KT-1215 | 🟡 | COLLECTIONS深度变异Set<Int>#37 | `val x:Set<Int>=...;for(x in {ct.lower()}){{ {ct.lower()}.rem` | 收集后删 |
| KT-1216 | 🟡 | COLLECTIONS深度变异Long#38 | `val x:Long=...;for(x in {ct.lower()}){{ {ct.lower()}.remove(` | 收集后删 |
| KT-1217 | 🟡 | COLLECTIONS深度变异Any#39 | `val x:Any=...;for(x in {ct.lower()}){{ {ct.lower()}.remove(x` | 收集后删 |
| KT-1218 | 🔴 | COLLECTIONS深度变异List<String>#40 | `val x:List<String>=...;val idx=list.indexOf(x);list.sort();l` | 先记录再排序或使用sorted() |
| KT-1219 | 🟡 | COLLECTIONS深度变异Int#41 | `val sub=mutableInt.subInt(0,5);mutableInt.clear()` | 先copy再操作 |
| KT-1220 | 🟡 | COLLECTIONS深度变异Short#42 | `val x:Short=...;setOf(Obj(1)).contains(Obj(1))` | 重写equals+hashCode |
| KT-1221 | 🟡 | COLLECTIONS深度变异Any?#43 | `val x:Any?=...;map.getOrDefault(k,expensive())` | map.getOrPut(k){expensive()} |
| KT-1222 | ⚪ | COLLECTIONS深度变异String#44 | `list.toSet().toString()` | list.distinct() |
| KT-1223 | ⚪ | COLLECTIONS深度变异Byte#45 | `emptyByte<Byte>().reduce{a,b->a+b}` | ifEmpty+fold |
| KT-1224 | 🔴 | COLLECTIONS深度变异Boolean?#46 | `val v=Vector<Boolean?>();v.add(1)` | ArrayList/CopyOnWriteArrayList |
| KT-1225 | 🟡 | COLLECTIONS深度变异Sequence<Long>#47 | `val x:Sequence<Long>=...;val g=list.groupBy{it.key};g.forEac` | 显式sorted |
| KT-1226 | 🟡 | COLLECTIONS深度变异Char#48 | `val x:Char=...;val sub=mutableSet.subSet(0,5);mutableSet.cle` | 先copy再操作 |
| KT-1227 | 🟡 | COLLECTIONS深度变异Double?#49 | `emptyDouble?<Long>().first()` | firstOrNull |
| KT-1228 | ⚪ | COLLECTIONS深度变异Array<Boolean>#50 | `val x:Array<Boolean>=...;list.toSet().toSet()` | list.distinct() |
| KT-1229 | 🔴 | COLLECTIONS深度变异Float#51 | `val x:Float=...;val v=Vector<Long>();v.add(1)` | ArrayList/CopyOnWriteArrayList |
| KT-1230 | 🔴 | COLLECTIONS深度变异Long?#52 | `val x:Long?=...;val v=Vector<Double>();v.add(1)` | ArrayList/CopyOnWriteArrayList |
| KT-1231 | 🔴 | COLLECTIONS深度变异MutableList<Double>#53 | `val x:MutableList<Double>=...;val v=Vector<Float>();v.add(1)` | ArrayList/CopyOnWriteArrayList |
| KT-1232 | 🔴 | COLLECTIONS深度变异Boolean#54 | `val x:Boolean=...;fun getSet()=mutableSet` | toSet()或Collections.unmodifiableSet |
| KT-1233 | 🟡 | COLLECTIONS深度变异Int?#55 | `val x:Int?=...;PagingSource.load()返回值未去重` | distinctUntilChanged |
| KT-1234 | 🔴 | COLLECTIONS深度变异Map<String,Int>#56 | `val x:Map<String,Int>=...;for(x in list){list.remove(x)}` | 收集待删项再删 |
| KT-1235 | 🔴 | COLLECTIONS深度变异Double#57 | `val x:Double=...;val it=list.iterator();list.add(x);it.next(` | 先收集再操作 |
| KT-1236 | 🟡 | COLLECTIONS深度变异Set<Int>#59 | `emptySet<Int><Set<Int>>().first()` | firstOrNull |
| KT-1237 | 🟡 | COLLECTIONS深度变异Long#60 | `val x:Long=...;listOf(1,2).single()` | first()或singleOrNull |
| KT-1238 | 🟡 | COLLECTIONS深度变异Any#61 | `val x:Any=...;val k=MutableObj();map[k]=v;k.mutate();map[k]` | 用不可变键 |
| KT-1239 | 🟡 | COLLECTIONS深度变异List<String>#62 | `val x:List<String>=...;list.filter{;list.add(x)` | 保存filter结果 |
| KT-1240 | ⚪ | COLLECTIONS深度变异Int#63 | `alreadyInt.toInt()` | 直接使用 |
| KT-1241 | ⚪ | COLLECTIONS深度变异Short#64 | `val x:Short=...;list.map{it*2}` | 用forEach |
| KT-1242 | 🟡 | COLLECTIONS深度变异Any?#65 | `val x:Any?=...;for(x in {ct.lower()}){{ {ct.lower()}.remove(` | 收集后删 |
| KT-1243 | 🟡 | COLLECTIONS深度变异String#66 | `val x:String=...;for(x in {ct.lower()}){{ {ct.lower()}.remov` | 收集后删 |
| KT-1244 | 🟡 | COLLECTIONS深度变异Byte#67 | `val x:Byte=...;for(x in {ct.lower()}){{ {ct.lower()}.remove(` | 收集后删 |
| KT-1245 | 🔴 | COLLECTIONS深度变异Boolean?#68 | `val x:Boolean?=...;val idx=list.indexOf(x);list.sort();list[` | 先记录再排序或使用sorted() |
| KT-1246 | 🟡 | COLLECTIONS深度变异Sequence<Long>#69 | `val sub=mutableSequence<Long>.subSequence<Long>(0,5);mutable` | 先copy再操作 |
| KT-1247 | 🟡 | COLLECTIONS深度变异Char#70 | `val x:Char=...;setOf(Obj(1)).contains(Obj(1))` | 重写equals+hashCode |
| KT-1248 | 🟡 | COLLECTIONS深度变异Double?#71 | `val x:Double?=...;map.getOrDefault(k,expensive())` | map.getOrPut(k){expensive()} |
| KT-1249 | ⚪ | COLLECTIONS深度变异Array<Boolean>#72 | `list.toSet().toArray<Boolean>()` | list.distinct() |
| KT-1250 | ⚪ | COLLECTIONS深度变异Float#73 | `emptyFloat<Float>().reduce{a,b->a+b}` | ifEmpty+fold |
| KT-1251 | 🔴 | COLLECTIONS深度变异Long?#74 | `val v=Vector<Long?>();v.add(1)` | ArrayList/CopyOnWriteArrayList |
| KT-1252 | 🟡 | COLLECTIONS深度变异MutableList<Double>#75 | `val x:MutableList<Double>=...;val g=list.groupBy{it.key};g.f` | 显式sorted |
| KT-1253 | 🟡 | COLLECTIONS深度变异Boolean#76 | `val x:Boolean=...;val sub=mutableSet.subSet(0,5);mutableSet.` | 先copy再操作 |
| KT-1254 | 🟡 | COLLECTIONS深度变异Int?#77 | `emptyInt?<Long>().first()` | firstOrNull |
| KT-1255 | ⚪ | COLLECTIONS深度变异Map<String,Int>#78 | `val x:Map<String,Int>=...;list.toSet().toSet()` | list.distinct() |
| KT-1256 | 🔴 | COLLECTIONS深度变异Double#79 | `val x:Double=...;val v=Vector<Long>();v.add(1)` | ArrayList/CopyOnWriteArrayList |
| KT-1257 | 🔴 | COLLECTIONS深度变异String?#80 | `val x:String?=...;val v=Vector<Double>();v.add(1)` | ArrayList/CopyOnWriteArrayList |
| KT-1258 | 🔴 | COLLECTIONS深度变异Set<Int>#81 | `val x:Set<Int>=...;val v=Vector<Float>();v.add(1)` | ArrayList/CopyOnWriteArrayList |
| KT-1259 | 🔴 | COLLECTIONS深度变异Long#82 | `val x:Long=...;fun getSet()=mutableSet` | toSet()或Collections.unmodifiableSet |
| KT-1260 | 🟡 | COLLECTIONS深度变异Any#83 | `val x:Any=...;PagingSource.load()返回值未去重` | distinctUntilChanged |
| KT-1261 | 🔴 | COLLECTIONS深度变异List<String>#84 | `val x:List<String>=...;for(x in list){list.remove(x)}` | 收集待删项再删 |
| KT-1262 | 🔴 | COLLECTIONS深度变异Int#85 | `val x:Int=...;val it=list.iterator();list.add(x);it.next()` | 先收集再操作 |
| KT-1263 | 🔴 | COLLECTIONS深度变异Short#86 | `fun getShort()=mutableShort` | toList()或Collections.unmodifiableList |
| KT-1264 | 🟡 | COLLECTIONS深度变异Any?#87 | `emptyAny?<Any?>().first()` | firstOrNull |
| KT-1265 | 🟡 | COLLECTIONS深度变异String#88 | `val x:String=...;listOf(1,2).single()` | first()或singleOrNull |
| KT-1266 | 🟡 | COLLECTIONS深度变异Byte#89 | `val x:Byte=...;val k=MutableObj();map[k]=v;k.mutate();map[k]` | 用不可变键 |
| KT-1267 | 🟡 | COLLECTIONS深度变异Boolean?#90 | `val x:Boolean?=...;list.filter{;list.add(x)` | 保存filter结果 |
| KT-1268 | ⚪ | COLLECTIONS深度变异Sequence<Long>#91 | `alreadySequence<Long>.toSequence<Long>()` | 直接使用 |
| KT-1269 | ⚪ | COLLECTIONS深度变异Char#92 | `val x:Char=...;list.map{it*2}` | 用forEach |
| KT-1270 | 🟡 | COLLECTIONS深度变异Double?#93 | `val x:Double?=...;for(x in {ct.lower()}){{ {ct.lower()}.remo` | 收集后删 |
| KT-1271 | 🟡 | COLLECTIONS深度变异Array<Boolean>#94 | `val x:Array<Boolean>=...;for(x in {ct.lower()}){{ {ct.lower(` | 收集后删 |
| KT-1272 | 🟡 | COLLECTIONS深度变异Float#95 | `val x:Float=...;for(x in {ct.lower()}){{ {ct.lower()}.remove` | 收集后删 |
| KT-1273 | 🔴 | COLLECTIONS深度变异Long?#96 | `val x:Long?=...;val idx=list.indexOf(x);list.sort();list[idx` | 先记录再排序或使用sorted() |
| KT-1274 | 🟡 | COLLECTIONS深度变异MutableList<Double>#97 | `val sub=mutableMutableList<Double>.subMutableList<Double>(0,` | 先copy再操作 |
| KT-1275 | 🟡 | COLLECTIONS深度变异Boolean#98 | `val x:Boolean=...;setOf(Obj(1)).contains(Obj(1))` | 重写equals+hashCode |
| KT-1276 | 🟡 | COLLECTIONS深度变异Int?#99 | `val x:Int?=...;map.getOrDefault(k,expensive())` | map.getOrPut(k){expensive()} |
| KT-1277 | ⚪ | COLLECTIONS深度变异Map<String,Int>#100 | `list.toSet().toMap<String,Int>()` | list.distinct() |
| KT-1278 | ⚪ | COLLECTIONS深度变异Double#101 | `emptyDouble<Double>().reduce{a,b->a+b}` | ifEmpty+fold |
| KT-1279 | 🔴 | COLLECTIONS深度变异String?#102 | `val v=Vector<String?>();v.add(1)` | ArrayList/CopyOnWriteArrayList |
| KT-1280 | 🟡 | COLLECTIONS深度变异Set<Int>#103 | `val x:Set<Int>=...;val g=list.groupBy{it.key};g.forEach{}` | 显式sorted |
| KT-1281 | 🟡 | COLLECTIONS深度变异Long#104 | `val x:Long=...;val sub=mutableSet.subSet(0,5);mutableSet.cle` | 先copy再操作 |
| KT-1282 | 🟡 | COLLECTIONS深度变异Any#105 | `emptyAny<Long>().first()` | firstOrNull |
| KT-1283 | ⚪ | COLLECTIONS深度变异List<String>#106 | `val x:List<String>=...;list.toSet().toSet()` | list.distinct() |
| KT-1284 | 🔴 | COLLECTIONS深度变异Int#107 | `val x:Int=...;val v=Vector<Long>();v.add(1)` | ArrayList/CopyOnWriteArrayList |
| KT-1285 | 🔴 | COLLECTIONS深度变异Short#108 | `val x:Short=...;val v=Vector<Double>();v.add(1)` | ArrayList/CopyOnWriteArrayList |
| KT-1286 | 🔴 | COLLECTIONS深度变异Any?#109 | `val x:Any?=...;val v=Vector<Float>();v.add(1)` | ArrayList/CopyOnWriteArrayList |
| KT-1287 | 🔴 | COLLECTIONS深度变异String#110 | `val x:String=...;fun getSet()=mutableSet` | toSet()或Collections.unmodifiableSet |
| KT-1288 | 🟡 | COLLECTIONS深度变异Byte#111 | `val x:Byte=...;PagingSource.load()返回值未去重` | distinctUntilChanged |
| KT-1289 | 🔴 | COLLECTIONS深度变异Boolean?#112 | `val x:Boolean?=...;for(x in list){list.remove(x)}` | 收集待删项再删 |
| KT-1290 | 🔴 | COLLECTIONS深度变异Sequence<Long>#113 | `val x:Sequence<Long>=...;val it=list.iterator();list.add(x);` | 先收集再操作 |
| KT-1291 | 🔴 | COLLECTIONS深度变异Char#114 | `fun getChar()=mutableChar` | toList()或Collections.unmodifiableList |
| KT-1292 | 🟡 | COLLECTIONS深度变异Double?#115 | `emptyDouble?<Double?>().first()` | firstOrNull |
| KT-1293 | 🟡 | COLLECTIONS深度变异Array<Boolean>#116 | `val x:Array<Boolean>=...;listOf(1,2).single()` | first()或singleOrNull |
| KT-1294 | 🟡 | COLLECTIONS深度变异Float#117 | `val x:Float=...;val k=MutableObj();map[k]=v;k.mutate();map[k` | 用不可变键 |
| KT-1295 | 🟡 | COLLECTIONS深度变异Long?#118 | `val x:Long?=...;list.filter{;list.add(x)` | 保存filter结果 |
| KT-1296 | ⚪ | COLLECTIONS深度变异MutableList<Double>#119 | `alreadyMutableList<Double>.toMutableList<Double>()` | 直接使用 |
| KT-1297 | ⚪ | COLLECTIONS深度变异Boolean#120 | `val x:Boolean=...;list.map{it*2}` | 用forEach |
| KT-1298 | 🟡 | COLLECTIONS深度变异Int?#121 | `val x:Int?=...;for(x in {ct.lower()}){{ {ct.lower()}.remove(` | 收集后删 |
| KT-1299 | 🟡 | COLLECTIONS深度变异Map<String,Int>#122 | `val x:Map<String,Int>=...;for(x in {ct.lower()}){{ {ct.lower` | 收集后删 |
| KT-1300 | 🟡 | COLLECTIONS深度变异Double#123 | `val x:Double=...;for(x in {ct.lower()}){{ {ct.lower()}.remov` | 收集后删 |
| KT-1301 | 🔴 | COLLECTIONS深度变异String?#124 | `val x:String?=...;val idx=list.indexOf(x);list.sort();list[i` | 先记录再排序或使用sorted() |
| KT-1302 | 🟡 | COLLECTIONS深度变异Set<Int>#125 | `val sub=mutableSet<Int>.subSet<Int>(0,5);mutableSet<Int>.cle` | 先copy再操作 |
| KT-1303 | 🟡 | COLLECTIONS深度变异Long#126 | `val x:Long=...;setOf(Obj(1)).contains(Obj(1))` | 重写equals+hashCode |
| KT-1304 | 🟡 | COLLECTIONS深度变异Any#127 | `val x:Any=...;map.getOrDefault(k,expensive())` | map.getOrPut(k){expensive()} |
| KT-1305 | ⚪ | COLLECTIONS深度变异List<String>#128 | `list.toSet().toList<String>()` | list.distinct() |
| KT-1306 | ⚪ | COLLECTIONS深度变异Int#129 | `emptyInt<Int>().reduce{a,b->a+b}` | ifEmpty+fold |
| KT-1307 | 🔴 | COLLECTIONS深度变异Short#130 | `val v=Vector<Short>();v.add(1)` | ArrayList/CopyOnWriteArrayList |
| KT-1308 | 🟡 | COLLECTIONS深度变异Any?#131 | `val x:Any?=...;val g=list.groupBy{it.key};g.forEach{}` | 显式sorted |
| KT-1309 | 🟡 | COLLECTIONS深度变异String#132 | `val x:String=...;val sub=mutableSet.subSet(0,5);mutableSet.c` | 先copy再操作 |
| KT-1310 | 🟡 | COLLECTIONS深度变异Byte#133 | `emptyByte<Long>().first()` | firstOrNull |
| KT-1311 | ⚪ | COLLECTIONS深度变异Boolean?#134 | `val x:Boolean?=...;list.toSet().toSet()` | list.distinct() |
| KT-1312 | 🔴 | COLLECTIONS深度变异Sequence<Long>#135 | `val x:Sequence<Long>=...;val v=Vector<Long>();v.add(1)` | ArrayList/CopyOnWriteArrayList |
| KT-1313 | 🔴 | COLLECTIONS深度变异Char#136 | `val x:Char=...;val v=Vector<Double>();v.add(1)` | ArrayList/CopyOnWriteArrayList |
| KT-1314 | 🔴 | COLLECTIONS深度变异Double?#137 | `val x:Double?=...;val v=Vector<Float>();v.add(1)` | ArrayList/CopyOnWriteArrayList |
| KT-1315 | 🔴 | COLLECTIONS深度变异Array<Boolean>#138 | `val x:Array<Boolean>=...;fun getSet()=mutableSet` | toSet()或Collections.unmodifiableSet |
| KT-1316 | 🟡 | COLLECTIONS深度变异Float#139 | `val x:Float=...;PagingSource.load()返回值未去重` | distinctUntilChanged |
| KT-1317 | 🔴 | COLLECTIONS深度变异Long?#140 | `val x:Long?=...;for(x in list){list.remove(x)}` | 收集待删项再删 |
| KT-1318 | 🔴 | COLLECTIONS深度变异MutableList<Double>#141 | `val x:MutableList<Double>=...;val it=list.iterator();list.ad` | 先收集再操作 |
| KT-1319 | 🔴 | COLLECTIONS深度变异Boolean#142 | `fun getBoolean()=mutableBoolean` | toList()或Collections.unmodifiableList |
| KT-1320 | 🟡 | COLLECTIONS深度变异Int?#143 | `emptyInt?<Int?>().first()` | firstOrNull |
| KT-1321 | 🟡 | COLLECTIONS深度变异Map<String,Int>#144 | `val x:Map<String,Int>=...;listOf(1,2).single()` | first()或singleOrNull |
| KT-1322 | 🟡 | COLLECTIONS深度变异Double#145 | `val x:Double=...;val k=MutableObj();map[k]=v;k.mutate();map[` | 用不可变键 |
| KT-1323 | 🟡 | COLLECTIONS深度变异String?#146 | `val x:String?=...;list.filter{;list.add(x)` | 保存filter结果 |
| KT-1324 | ⚪ | COLLECTIONS深度变异Set<Int>#147 | `alreadySet<Int>.toSet<Int>()` | 直接使用 |
| KT-1325 | ⚪ | COLLECTIONS深度变异Long#148 | `val x:Long=...;list.map{it*2}` | 用forEach |
| KT-1326 | 🟡 | COLLECTIONS深度变异Any#149 | `val x:Any=...;for(x in {ct.lower()}){{ {ct.lower()}.remove(x` | 收集后删 |
| KT-1327 | 🟡 | COLLECTIONS深度变异List<String>#150 | `val x:List<String>=...;for(x in {ct.lower()}){{ {ct.lower()}` | 收集后删 |
| KT-1328 | 🟡 | COLLECTIONS深度变异Int#151 | `val x:Int=...;for(x in {ct.lower()}){{ {ct.lower()}.remove(x` | 收集后删 |
| KT-1329 | 🔴 | COLLECTIONS深度变异Short#152 | `val x:Short=...;val idx=list.indexOf(x);list.sort();list[idx` | 先记录再排序或使用sorted() |
| KT-1330 | 🟡 | COLLECTIONS深度变异Any?#153 | `val sub=mutableAny?.subAny?(0,5);mutableAny?.clear()` | 先copy再操作 |
| KT-1331 | 🟡 | COLLECTIONS深度变异String#154 | `val x:String=...;setOf(Obj(1)).contains(Obj(1))` | 重写equals+hashCode |
| KT-1332 | 🟡 | COLLECTIONS深度变异Byte#155 | `val x:Byte=...;map.getOrDefault(k,expensive())` | map.getOrPut(k){expensive()} |
| KT-1333 | ⚪ | COLLECTIONS深度变异Boolean?#156 | `list.toSet().toBoolean?()` | list.distinct() |
| KT-1334 | ⚪ | COLLECTIONS深度变异Sequence<Long>#157 | `emptySequence<Long><Sequence<Long>>().reduce{a,b->a+b}` | ifEmpty+fold |
| KT-1335 | 🔴 | COLLECTIONS深度变异Char#158 | `val v=Vector<Char>();v.add(1)` | ArrayList/CopyOnWriteArrayList |
| KT-1336 | 🟡 | COLLECTIONS深度变异Double?#159 | `val x:Double?=...;val g=list.groupBy{it.key};g.forEach{}` | 显式sorted |
| KT-1337 | 🟡 | COLLECTIONS深度变异Array<Boolean>#160 | `val x:Array<Boolean>=...;val sub=mutableSet.subSet(0,5);muta` | 先copy再操作 |
| KT-1338 | 🟡 | COLLECTIONS深度变异Float#161 | `emptyFloat<Long>().first()` | firstOrNull |
| KT-1339 | ⚪ | COLLECTIONS深度变异Long?#162 | `val x:Long?=...;list.toSet().toSet()` | list.distinct() |
| KT-1340 | 🔴 | COLLECTIONS深度变异MutableList<Double>#163 | `val x:MutableList<Double>=...;val v=Vector<Long>();v.add(1)` | ArrayList/CopyOnWriteArrayList |
| KT-1341 | 🔴 | COLLECTIONS深度变异Boolean#164 | `val x:Boolean=...;val v=Vector<Double>();v.add(1)` | ArrayList/CopyOnWriteArrayList |
| KT-1342 | 🔴 | COLLECTIONS深度变异Int?#165 | `val x:Int?=...;val v=Vector<Float>();v.add(1)` | ArrayList/CopyOnWriteArrayList |
| KT-1343 | 🔴 | COLLECTIONS深度变异Map<String,Int>#166 | `val x:Map<String,Int>=...;fun getSet()=mutableSet` | toSet()或Collections.unmodifiableSet |
| KT-1344 | 🟡 | COLLECTIONS深度变异Double#167 | `val x:Double=...;PagingSource.load()返回值未去重` | distinctUntilChanged |
| KT-1345 | 🔴 | COLLECTIONS深度变异String?#168 | `val x:String?=...;for(x in list){list.remove(x)}` | 收集待删项再删 |
| KT-1346 | 🔴 | COLLECTIONS深度变异Set<Int>#169 | `val x:Set<Int>=...;val it=list.iterator();list.add(x);it.nex` | 先收集再操作 |
| KT-1347 | 🟡 | COLLECTIONS深度变异Any#171 | `emptyAny<Any>().first()` | firstOrNull |

## COMPILER_TRAP（150条）

| ID | 严重度 | Bug描述 | 触发模式 | 修复建议 |
|---|------|------|------|------|
| KT-0121 | 🔴 | 重载解析选错 | `fun f(i:Int){};fun f(a:Any){};f(42)` | 显式参数类型 |
| KT-0122 | 🟡 | 类型推断歧义 | `val x=if(cond) 1 else null` | 显式标注类型 |
| KT-0123 | 🟡 | Nothing类型传染 | `fun e():Nothing=throw E();val x=e()` | 显式类型标注 |
| KT-0124 | 🟡 | Lambda返回类型推断错误 | `val f={if(x) 1 else "hi"}` | 显式返回类型 |
| KT-0125 | 🟡 | 自引用属性 | `val x:Int=x+1` | 用by lazy或初始化块 |
| KT-0126 | ⚪ | 不必要的类型标注 | `val x:String="hi"` | 自动推断 |
| KT-0237 | 🔴 | SAM+overload解析 | `fun f(r:Runnable);fun f(c:Callable);f{println()}` | 显式类型 |
| KT-0238 | 🟡 | data class在when中穷举 | `when(val d=dataClass(...)){Data(1)->1}` | if-else替代 |
| KT-0239 | 🟡 | Lambda返回值return+非Lambda | `fun f()=lambda{return 1}` | return@lambda |
| KT-0240 | 🟡 | 空catch块 | `try{risky()}catch(e:Exception){}` | 至少日志记录 |
| KT-0241 | 🟡 | unsafe cast+泛型 | `fun <T> f(a:Any)=a as T` | pass reified+ |
| KT-0242 | ⚪ | lazy初始化捕获可变引用 | `var x=0;val y by lazy{x}` | val x=0先 |
| KT-0243 | 🔴 | 函数引用+重载 | `fun f(i:Int){};fun f(s:String){};val r=::f` | 指定类型:(Int)->Unit |
| KT-0261 | 🔴 | 编译器编译自己源码时卡死 | `kotlin-head编译Main.kt自身` | 分离编译阶段 |
| KT-0262 | 🔴 | BugScanner扫描到自己 | `BugDB.scan(BugScanner的源码)` | 元规则豁免self-scan |
| KT-0263 | 🟡 | LiveDeclarationGraph自引用死循环 | `class A(val a:A)` | 循环引用检测 |
| KT-0264 | 🔴 | Parser遇到文件名为hell时倾全军之力 | `hell.kt触发isHostileFile=true;所有资源耗尽` | 只加倍不倾全军 |
| KT-0265 | 🟡 | 反编译管线反编译自己 | `jadx反编译kotlin-head.jar再编译` | 跳过自身jar |
| KT-0266 | 🔴 | Kotlin T!类型让编译器以为非空实则null | `val x=javaGet();编译器推断String;运行时NPE` | 显式标注String? |
| KT-0267 | 🟡 | Nothing类型吞噬所有代码 | `fun e():Nothing=throw E();e().also{unreachable()}` | 不链式调用Nothing |
| KT-0268 | 🟡 | 类型推断选了谁都没想到的类型 | `listOf(1)与emptyList()合并推断List<Int>?` | 显式泛型参数 |
| KT-0281 | ⚪ | 误把AI的建议当编译器报错 | `AI说你的代码有bug但编译器编译过了` | 先编译再采纳 |
| KT-0282 | ⚪ | 注释里写TODO导致被BugScanner报bug | `// TODO: fix this → BugDB hit` | BugDB加-TODO排除规则 |
| KT-0290 | 🔴 | 编译器优化阶段把正确代码优化成错误代码 | `Pass.inline错误展开导致语义变化` | Pass后加语义等价校验 |
| KT-0294 | 🔴 | 类型推断在递归函数中选择最窄类型 | `fun f()=if(cond) f() else 0→Int` | 显式返回类型 |
| KT-0309 | 🟡 | 默认参数+扩展函数+泛型=三歧义 | `fun <T> List<T>.f(n:Int=1){};listOf(1).f()` | 显式传参 |
| KT-0318 | 🟡 | 把launch当async用等不到结果 | `val res=launch{calc()};println(res)` | 需要结果用async+await |
| KT-0319 | 🔴 | BugDB被设为主入口启动了但不编译只报告bug | `fun main()=BugDB.scan(args);编译结果变成了bug报告` | main调compile不是scan |
| KT-0320 | 🔴 | 把编译好的jar当源码喂给编译器 | `kotlin-head myapp.jar` | 文件扩展名检查+.kt强制 |
| KT-0321 | 🔴 | 把反编译输出当源码编译（二次降解） | `jadx吐出的非标准Kotlin→kotlin-head→再反编译` | jadx输出只读不编 |
| KT-0322 | 🟡 | README.md被当Kotlin源码编译 | `kotlin-head README.md` | 扩展名白名单 |
| KT-0323 | 🟡 | 把build.gradle.kts当普通Kotlin编译 | `kotlin-head build.gradle.kts` | 跳过构建文件 |
| KT-0324 | 🔴 | 把ProGuard mapping文件当源码输入 | `kotlin-head mapping.txt` | 文件内容嗅探 |
| KT-0325 | 🟡 | 把jadx反编译错文件当正确文件比较 | `反编译A.apk的输出和B.apk的源码对比` | 确认源一致 |
| KT-0326 | 🔴 | EventBus频道名当类名注册 | `EventBus.subscribe("Main"){};Main::class.java` | 用KClass不是字符串 |
| KT-0328 | 🔴 | Python代码改.kt当Kotlin编译 | `kotlin-head fake.py→改名fake.kt→def foo():→def当标识符,冒号被吞` | 校验文件内容+shebang检测 |
| KT-0329 | 🔴 | C++代码改.kt硬说Kotlin | `kotlin-head main.cpp→改名main.kt→#include<iostream>→#当注释int ma` | 内容特征检测 |
| KT-0330 | 🟡 | JavaScript改.kt语法全乱 | `kotlin-head app.js→改名app.kt→const x=1;→const当标识符` | 前几行特征匹配 |
| KT-0331 | 🟡 | Shell脚本改.kt编译 | `kotlin-head run.sh→改名run.kt→#!/bin/bash→shebang当注释但后续shell命令` | 首行shebang拦截 |
| KT-0332 | ⚪ | 不必要的类型标注（Int版） | `val x:Int=\"hi\"` | 自动推断 |
| KT-0333 | ⚪ | 不必要的类型标注（Long版） | `val x:Long=\"hi\"` | 自动推断 |
| KT-0334 | ⚪ | 不必要的类型标注（Double版） | `val x:Double=\"hi\"` | 自动推断 |
| KT-0335 | ⚪ | 不必要的类型标注（Boolean版） | `val x:Boolean=\"hi\"` | 自动推断 |
| KT-0339 | 🟡 | 默认参数+扩展函数+泛型=三歧义（Long版） | `fun <T> List<T>.f(n:Long=1){};listOf(1).f()` | 显式传参 |
| KT-0340 | 🟡 | 默认参数+扩展函数+泛型=三歧义（Double版） | `fun <T> List<T>.f(n:Double=1){};listOf(1).f()` | 显式传参 |
| KT-0365 | 🟡 | 类型推断选了谁都没想到的类型（Long版） | `listOf(1)与emptyList()合并推断List<Long>?` | 显式泛型参数 |
| KT-0366 | 🟡 | 类型推断选了谁都没想到的类型（Double版） | `listOf(1)与emptyList()合并推断List<Double>?` | 显式泛型参数 |
| KT-0367 | 🟡 | 类型推断选了谁都没想到的类型（Float版） | `listOf(1)与emptyList()合并推断List<Float>?` | 显式泛型参数 |
| KT-0384 | 🔴 | 函数引用+重载（Int版） | `fun f(i:Int){};fun f(s:Int){};val r=::f` | 指定类型:(Int)->Unit |
| KT-0385 | 🔴 | 函数引用+重载（Long版） | `fun f(i:Int){};fun f(s:Long){};val r=::f` | 指定类型:(Int)->Unit |
| KT-0386 | 🔴 | 函数引用+重载（Double版） | `fun f(i:Int){};fun f(s:Double){};val r=::f` | 指定类型:(Int)->Unit |
| KT-0387 | 🔴 | 函数引用+重载（Boolean版） | `fun f(i:Int){};fun f(s:Boolean){};val r=::f` | 指定类型:(Int)->Unit |
| KT-0400 | 🔴 | Kotlin T!类型让编译器以为非空实则null（Int版） | `val x=javaGet();编译器推断Int;运行时NPE` | 显式标注Int? |
| KT-0401 | 🔴 | Kotlin T!类型让编译器以为非空实则null（Long版） | `val x=javaGet();编译器推断Long;运行时NPE` | 显式标注Long? |
| KT-0402 | 🔴 | Kotlin T!类型让编译器以为非空实则null（Double版） | `val x=javaGet();编译器推断Double;运行时NPE` | 显式标注Double? |
| KT-0403 | 🔴 | Kotlin T!类型让编译器以为非空实则null（Boolean版） | `val x=javaGet();编译器推断Boolean;运行时NPE` | 显式标注Boolean? |
| KT-0407 | 🟡 | 自引用属性（Long版） | `val x:Long=x+1` | 用by lazy或初始化块 |
| KT-0471 | 🔴 | 类型推断在递归函数中选择最窄类型（Long版） | `fun f()=if(cond) f() else 0→Long` | 显式返回类型 |
| KT-0472 | 🔴 | 类型推断在递归函数中选择最窄类型（Double版） | `fun f()=if(cond) f() else 0→Double` | 显式返回类型 |
| KT-0482 | 🔴 | 默认参数与Java重载冲突 | `fun f(a:Int,b:Int=0)在Java中` | @JvmOverloads |
| KT-0483 | 🟡 | data class自动equals递归栈溢出 | `data class N(val n:N?)` | 手动equals |
| KT-2583 | 🔴 | COMPILER_TRAP深度变异String#0 | `fun f(i:String){};fun f(a:Any){};f(42)` | 显式参数类型 |
| KT-2584 | 🟡 | COMPILER_TRAP深度变异Byte#1 | `val x:Byte=...;val x=if(cond) 1 else null` | 显式标注类型 |
| KT-2585 | 🟡 | COMPILER_TRAP深度变异Boolean?#2 | `val x:Boolean?=...;fun e():Nothing=throw E();val x=e()` | 显式类型标注 |
| KT-2586 | 🟡 | COMPILER_TRAP深度变异Sequence<Long>#3 | `val x:Sequence<Long>=...;val f={if(x) 1 else \"hi\"}` | 显式返回类型 |
| KT-2587 | 🟡 | COMPILER_TRAP深度变异Char#4 | `val x:Char=x+1` | 用by lazy或初始化块 |
| KT-2588 | ⚪ | COMPILER_TRAP深度变异Double?#5 | `val x:Double?=\"hi\"` | 自动推断 |
| KT-2589 | 🔴 | COMPILER_TRAP深度变异Array<Boolean>#6 | `val x:Array<Boolean>=...;fun f(r:Runnable);fun f(c:Callable)` | 显式类型 |
| KT-2590 | 🟡 | COMPILER_TRAP深度变异Float#7 | `val x:Float=...;when(val d=dataClass(...)){Data(1)->1}` | if-else替代 |
| KT-2591 | 🟡 | COMPILER_TRAP深度变异Long?#8 | `val x:Long?=...;fun f()=lambda{return 1}` | return@lambda |
| KT-2592 | 🟡 | COMPILER_TRAP深度变异MutableList<Double>#9 | `val x:MutableList<Double>=...;try{risky()}catch(e:Exception)` | 至少日志记录 |
| KT-2593 | 🟡 | COMPILER_TRAP深度变异Boolean#10 | `val x:Boolean=...;fun <T> f(a:Any)=a as T` | pass reified+ |
| KT-2594 | ⚪ | COMPILER_TRAP深度变异Int?#11 | `val x:Int?=...;var x=0;val y by lazy{x}` | val x=0先 |
| KT-2595 | 🔴 | COMPILER_TRAP深度变异Map<String,Int>#12 | `fun f(i:Map<String,Int>){};fun f(s:Map<String,Map<String,Int` | 指定类型:(Int)->Unit |
| KT-2596 | 🔴 | COMPILER_TRAP深度变异Double#13 | `val x:Double=...;kotlin-head编译Main.kt自身` | 分离编译阶段 |
| KT-2597 | 🔴 | COMPILER_TRAP深度变异String?#14 | `val x:String?=...;BugDB.scan(BugScanner的源码)` | 元规则豁免self-scan |
| KT-2598 | 🟡 | COMPILER_TRAP深度变异Set<Int>#15 | `val x:Set<Int>=...;class A(val a:A)` | 循环引用检测 |
| KT-2599 | 🔴 | COMPILER_TRAP深度变异Long#16 | `val x:Long=...;hell.kt触发isHostileFile=true;所有资源耗尽` | 只加倍不倾全军 |
| KT-2600 | 🟡 | COMPILER_TRAP深度变异Any#17 | `val x:Any=...;jadx反编译kotlin-head.jar再编译` | 跳过自身jar |
| KT-2601 | 🔴 | COMPILER_TRAP深度变异List<String>#18 | `val x=javaGet();编译器推断List<String><String>;运行时NPE` | 显式标注String? |
| KT-2602 | 🟡 | COMPILER_TRAP深度变异Int#19 | `val x:Int=...;fun e():Nothing=throw E();e().also{unreachable` | 不链式调用Nothing |
| KT-2603 | 🟡 | COMPILER_TRAP深度变异Short#20 | `listOf(1)与emptyShort()合并推断Short<Short>?` | 显式泛型参数 |
| KT-2604 | ⚪ | COMPILER_TRAP深度变异Any?#21 | `val x:Any?=...;AI说你的代码有bug但编译器编译过了` | 先编译再采纳 |
| KT-2605 | ⚪ | COMPILER_TRAP深度变异String#22 | `val x:String=...;// TODO: fix this → BugDB hit` | BugDB加-TODO排除规则 |
| KT-2606 | 🔴 | COMPILER_TRAP深度变异Byte#23 | `val x:Byte=...;Pass.inline错误展开导致语义变化` | Pass后加语义等价校验 |
| KT-2607 | 🔴 | COMPILER_TRAP深度变异Boolean?#24 | `fun f()=if(cond) f() else 0→Boolean?` | 显式返回类型 |
| KT-2608 | 🟡 | COMPILER_TRAP深度变异Sequence<Long>#25 | `fun <T> Sequence<Long><T>.f(n:Sequence<Long>=1){};listOf(1).` | 显式传参 |
| KT-2609 | 🟡 | COMPILER_TRAP深度变异Char#26 | `val x:Char=...;val res=launch{calc()};println(res)` | 需要结果用async+await |
| KT-2610 | 🔴 | COMPILER_TRAP深度变异Double?#27 | `val x:Double?=...;fun main()=BugDB.scan(args);编译结果变成了bug报告` | main调compile不是scan |
| KT-2611 | 🔴 | COMPILER_TRAP深度变异Array<Boolean>#28 | `val x:Array<Boolean>=...;kotlin-head myapp.jar` | 文件扩展名检查+.kt强制 |
| KT-2612 | 🔴 | COMPILER_TRAP深度变异Float#29 | `val x:Float=...;jadx吐出的非标准Kotlin→kotlin-head→再反编译` | jadx输出只读不编 |
| KT-2613 | 🟡 | COMPILER_TRAP深度变异Long?#30 | `val x:Long?=...;kotlin-head README.md` | 扩展名白名单 |
| KT-2614 | 🟡 | COMPILER_TRAP深度变异MutableList<Double>#31 | `val x:MutableList<Double>=...;kotlin-head build.gradle.kts` | 跳过构建文件 |
| KT-2615 | 🔴 | COMPILER_TRAP深度变异Boolean#32 | `val x:Boolean=...;kotlin-head mapping.txt` | 文件内容嗅探 |
| KT-2616 | 🟡 | COMPILER_TRAP深度变异Int?#33 | `val x:Int?=...;反编译A.apk的输出和B.apk的源码对比` | 确认源一致 |
| KT-2617 | 🔴 | COMPILER_TRAP深度变异Map<String,Int>#34 | `val x:Map<String,Int>=...;EventBus.subscribe(\"Main\"){};Mai` | 用KClass不是字符串 |
| KT-2618 | 🔴 | COMPILER_TRAP深度变异Double#35 | `val x:Double=...;kotlin-head fake.py→改名fake.kt→def foo():→de` | 校验文件内容+shebang检测 |
| KT-2619 | 🔴 | COMPILER_TRAP深度变异String?#36 | `val x:String?=...;kotlin-head main.cpp→改名main.kt→#include<io` | 内容特征检测 |
| KT-2620 | 🟡 | COMPILER_TRAP深度变异Set<Int>#37 | `val x:Set<Int>=...;kotlin-head app.js→改名app.kt→const x=1;→co` | 前几行特征匹配 |
| KT-2621 | 🟡 | COMPILER_TRAP深度变异Long#38 | `val x:Long=...;kotlin-head run.sh→改名run.kt→#!/bin/bash→sheba` | 首行shebang拦截 |
| KT-2622 | ⚪ | COMPILER_TRAP深度变异Any#39 | `val x:Any=\\\"hi\\\"` | 自动推断 |
| KT-2623 | ⚪ | COMPILER_TRAP深度变异List<String>#40 | `val x:List<String>=...;val x:Long=\\\"hi\\\"` | 自动推断 |
| KT-2624 | ⚪ | COMPILER_TRAP深度变异Int#41 | `val x:Int=...;val x:Double=\\\"hi\\\"` | 自动推断 |
| KT-2625 | ⚪ | COMPILER_TRAP深度变异Short#42 | `val x:Short=...;val x:Boolean=\\\"hi\\\"` | 自动推断 |
| KT-2626 | 🟡 | COMPILER_TRAP深度变异Any?#43 | `fun <T> Any?<T>.f(n:Long=1){};listOf(1).f()` | 显式传参 |
| KT-2627 | 🟡 | COMPILER_TRAP深度变异String#44 | `fun <T> String<T>.f(n:Double=1){};listOf(1).f()` | 显式传参 |
| KT-2628 | 🟡 | COMPILER_TRAP深度变异Byte#45 | `listOf(1)与emptyByte()合并推断Byte<Long>?` | 显式泛型参数 |
| KT-2629 | 🟡 | COMPILER_TRAP深度变异Boolean?#46 | `listOf(1)与emptyBoolean?()合并推断Boolean?<Double>?` | 显式泛型参数 |
| KT-2630 | 🟡 | COMPILER_TRAP深度变异Sequence<Long>#47 | `listOf(1)与emptySequence<Long>()合并推断Sequence<Long><Float>?` | 显式泛型参数 |
| KT-2631 | 🔴 | COMPILER_TRAP深度变异Char#48 | `fun f(i:Char){};fun f(s:Char){};val r=::f` | 指定类型:(Int)->Unit |
| KT-2632 | 🔴 | COMPILER_TRAP深度变异Double?#49 | `fun f(i:Double?){};fun f(s:Long){};val r=::f` | 指定类型:(Int)->Unit |
| KT-2633 | 🔴 | COMPILER_TRAP深度变异Array<Boolean>#50 | `fun f(i:Array<Boolean>){};fun f(s:Double){};val r=::f` | 指定类型:(Int)->Unit |
| KT-2634 | 🔴 | COMPILER_TRAP深度变异Float#51 | `fun f(i:Float){};fun f(s:Boolean){};val r=::f` | 指定类型:(Int)->Unit |
| KT-2635 | 🔴 | COMPILER_TRAP深度变异Long?#52 | `val x=javaGet();编译器推断Long?;运行时NPE` | 显式标注Int? |
| KT-2636 | 🔴 | COMPILER_TRAP深度变异MutableList<Double>#53 | `val x:MutableList<Double>=...;val x=javaGet();编译器推断Long;运行时N` | 显式标注Long? |
| KT-2637 | 🔴 | COMPILER_TRAP深度变异Boolean#54 | `val x:Boolean=...;val x=javaGet();编译器推断Double;运行时NPE` | 显式标注Double? |
| KT-2638 | 🔴 | COMPILER_TRAP深度变异Int?#55 | `val x:Int?=...;val x=javaGet();编译器推断Boolean;运行时NPE` | 显式标注Boolean? |
| KT-2639 | 🟡 | COMPILER_TRAP深度变异Map<String,Int>#56 | `val x:Map<String,Int>=...;val x:Long=x+1` | 用by lazy或初始化块 |
| KT-2640 | 🔴 | COMPILER_TRAP深度变异Double#57 | `val x:Double=...;fun f()=if(cond) f() else 0→Long` | 显式返回类型 |
| KT-2641 | 🔴 | COMPILER_TRAP深度变异String?#58 | `val x:String?=...;fun f()=if(cond) f() else 0→Double` | 显式返回类型 |
| KT-2642 | 🔴 | COMPILER_TRAP深度变异Set<Int>#59 | `fun f(a:Set<Int>,b:Set<Int>=0)在Java中` | @JvmOverloads |
| KT-2643 | 🟡 | COMPILER_TRAP深度变异Long#60 | `val x:Long=...;data class N(val n:N?)` | 手动equals |
| KT-2644 | 🔴 | COMPILER_TRAP深度变异Any#61 | `fun f(i:Any){};fun f(a:Any){};f(42)` | 显式参数类型 |
| KT-2645 | 🟡 | COMPILER_TRAP深度变异List<String>#62 | `val x:List<String>=...;val x=if(cond) 1 else null` | 显式标注类型 |
| KT-2646 | 🟡 | COMPILER_TRAP深度变异Int#63 | `val x:Int=...;fun e():Nothing=throw E();val x=e()` | 显式类型标注 |
| KT-2647 | 🟡 | COMPILER_TRAP深度变异Short#64 | `val x:Short=...;val f={if(x) 1 else \"hi\"}` | 显式返回类型 |
| KT-2648 | 🟡 | COMPILER_TRAP深度变异Any?#65 | `val x:Any?=x+1` | 用by lazy或初始化块 |
| KT-2649 | ⚪ | COMPILER_TRAP深度变异String#66 | `val x:String=...;val x:String=\"hi\"` | 自动推断 |
| KT-2650 | 🔴 | COMPILER_TRAP深度变异Byte#67 | `val x:Byte=...;fun f(r:Runnable);fun f(c:Callable);f{println` | 显式类型 |
| KT-2651 | 🟡 | COMPILER_TRAP深度变异Boolean?#68 | `val x:Boolean?=...;when(val d=dataClass(...)){Data(1)->1}` | if-else替代 |
| KT-2652 | 🟡 | COMPILER_TRAP深度变异Sequence<Long>#69 | `val x:Sequence<Long>=...;fun f()=lambda{return 1}` | return@lambda |
| KT-2653 | 🟡 | COMPILER_TRAP深度变异Char#70 | `val x:Char=...;try{risky()}catch(e:Exception){}` | 至少日志记录 |
| KT-2654 | 🟡 | COMPILER_TRAP深度变异Double?#71 | `val x:Double?=...;fun <T> f(a:Any)=a as T` | pass reified+ |
| KT-2655 | ⚪ | COMPILER_TRAP深度变异Array<Boolean>#72 | `val x:Array<Boolean>=...;var x=0;val y by lazy{x}` | val x=0先 |
| KT-2656 | 🔴 | COMPILER_TRAP深度变异Float#73 | `fun f(i:Float){};fun f(s:Float){};val r=::f` | 指定类型:(Int)->Unit |
| KT-2657 | 🔴 | COMPILER_TRAP深度变异Long?#74 | `val x:Long?=...;kotlin-head编译Main.kt自身` | 分离编译阶段 |
| KT-2658 | 🔴 | COMPILER_TRAP深度变异MutableList<Double>#75 | `val x:MutableList<Double>=...;BugDB.scan(BugScanner的源码)` | 元规则豁免self-scan |
| KT-2659 | 🟡 | COMPILER_TRAP深度变异Boolean#76 | `val x:Boolean=...;class A(val a:A)` | 循环引用检测 |
| KT-2660 | 🔴 | COMPILER_TRAP深度变异Int?#77 | `val x:Int?=...;hell.kt触发isHostileFile=true;所有资源耗尽` | 只加倍不倾全军 |
| KT-2661 | 🟡 | COMPILER_TRAP深度变异Map<String,Int>#78 | `val x:Map<String,Int>=...;jadx反编译kotlin-head.jar再编译` | 跳过自身jar |
| KT-2662 | 🔴 | COMPILER_TRAP深度变异Double#79 | `val x=javaGet();编译器推断Double;运行时NPE` | 显式标注String? |
| KT-2663 | 🟡 | COMPILER_TRAP深度变异String?#80 | `val x:String?=...;fun e():Nothing=throw E();e().also{unreach` | 不链式调用Nothing |
| KT-2664 | 🟡 | COMPILER_TRAP深度变异Set<Int>#81 | `listOf(1)与emptySet<Int>()合并推断Set<Int><Set<Int>>?` | 显式泛型参数 |
| KT-2665 | ⚪ | COMPILER_TRAP深度变异Long#82 | `val x:Long=...;AI说你的代码有bug但编译器编译过了` | 先编译再采纳 |
| KT-2666 | ⚪ | COMPILER_TRAP深度变异Any#83 | `val x:Any=...;// TODO: fix this → BugDB hit` | BugDB加-TODO排除规则 |
| KT-2667 | 🔴 | COMPILER_TRAP深度变异List<String>#84 | `val x:List<String>=...;Pass.inline错误展开导致语义变化` | Pass后加语义等价校验 |
| KT-2668 | 🔴 | COMPILER_TRAP深度变异Int#85 | `val x:Int=...;fun f()=if(cond) f() else 0→Int` | 显式返回类型 |
| KT-2669 | 🟡 | COMPILER_TRAP深度变异Short#86 | `fun <T> Short<T>.f(n:Short=1){};listOf(1).f()` | 显式传参 |
| KT-2670 | 🟡 | COMPILER_TRAP深度变异Any?#87 | `val x:Any?=...;val res=launch{calc()};println(res)` | 需要结果用async+await |
| KT-2671 | 🔴 | COMPILER_TRAP深度变异String#88 | `val x:String=...;fun main()=BugDB.scan(args);编译结果变成了bug报告` | main调compile不是scan |

## COMPOSE（100条）

| ID | 严重度 | Bug描述 | 触发模式 | 修复建议 |
|---|------|------|------|------|
| KT-0112 | 🔴 | 主线程读文件 | `Text(File("x").readText())` | LaunchedEffect+IO |
| KT-0113 | 🟡 | 重组副作用 | `LaunchedEffect(Unit){load()}` | 正确的key |
| KT-0114 | 🟡 | remember遗漏 | `var count=0;Button(onClick={count++}){Text("$count")}` | remember{mutableStateOf} |
| KT-0115 | 🟡 | 状态提升缺失 | `@Composable fun C(){val s=remember{;CF(s)}` | 状态提升 |
| KT-0116 | ⚪ | 不必要的remember | `remember{"static"}` | 直接使用 |
| KT-0229 | 🔴 | unstable参数导致过度重组 | `@Composable fun Item(user:User){` | @Immutable标注 |
| KT-0230 | 🟡 | rememberSaveable丢失 | `var txt by remember{ mutableStateOf("")}` | rememberSaveable |
| KT-0231 | 🟡 | key参数遗漏 | `items.forEach{Item(it)}` | items.forEach{key(it.id){Item(it)}} |
| KT-0232 | 🟡 | Modifier顺序错误 | `Modifier.padding(16).clickable{}` | clickable放padding之前 |
| KT-0233 | ⚪ | derivedStateOf用于非计算 | `val d=derivedStateOf{list}` | 直接val |
| KT-0298 | 🔴 | LaunchedEffect+错误key+状态更新=死循环重组 | `LaunchedEffect(Unit){counter++}` | key使用稳定值 |
| KT-0490 | 🟡 | derivedStateOf滥用 | `val v=derivedStateOf{simpleCalc()}` | 直接计算 |
| KT-0491 | 🔴 | 副作用在Composition中 | `CompositionLocalProvider{loadData()}` | LaunchedEffect |
| KT-2414 | 🔴 | COMPOSE深度变异String#0 | `val x:String=...;Text(File(\"x\").readText())` | LaunchedEffect+IO |
| KT-2415 | 🟡 | COMPOSE深度变异Byte#1 | `val x:Byte=...;LaunchedEffect(Unit){load()}` | 正确的key |
| KT-2416 | 🟡 | COMPOSE深度变异Boolean?#2 | `val x:Boolean?=...;var count=0;Button(onClick={count++}){Tex` | remember{mutableStateOf} |
| KT-2417 | 🟡 | COMPOSE深度变异Sequence<Long>#3 | `val x:Sequence<Long>=...;@Composable fun C(){val s=remember{` | 状态提升 |
| KT-2418 | ⚪ | COMPOSE深度变异Char#4 | `val x:Char=...;remember{\"static\"}` | 直接使用 |
| KT-2419 | 🔴 | COMPOSE深度变异Double?#5 | `val x:Double?=...;@Composable fun Item(user:User){` | @Immutable标注 |
| KT-2420 | 🟡 | COMPOSE深度变异Array<Boolean>#6 | `val x:Array<Boolean>=...;var txt by remember{ mutableStateOf` | rememberSaveable |
| KT-2421 | 🟡 | COMPOSE深度变异Float#7 | `val x:Float=...;items.forEach{Item(it)}` | items.forEach{key(it.id){Item(it)}} |
| KT-2422 | 🟡 | COMPOSE深度变异Long?#8 | `val x:Long?=...;Modifier.padding(16).clickable{}` | clickable放padding之前 |
| KT-2423 | ⚪ | COMPOSE深度变异MutableList<Double>#9 | `val x:MutableList<Double>=...;val d=derivedStateOf{list}` | 直接val |
| KT-2424 | 🔴 | COMPOSE深度变异Boolean#10 | `val x:Boolean=...;LaunchedEffect(Unit){counter++}` | key使用稳定值 |
| KT-2425 | 🟡 | COMPOSE深度变异Int?#11 | `val x:Int?=...;val v=derivedStateOf{simpleCalc()}` | 直接计算 |
| KT-2426 | 🔴 | COMPOSE深度变异Map<String,Int>#12 | `val x:Map<String,Int>=...;CompositionLocalProvider{loadData(` | LaunchedEffect |
| KT-2427 | 🔴 | COMPOSE深度变异Double#13 | `val x:Double=...;Text(File(\"x\").readText())` | LaunchedEffect+IO |
| KT-2428 | 🟡 | COMPOSE深度变异String?#14 | `val x:String?=...;LaunchedEffect(Unit){load()}` | 正确的key |
| KT-2429 | 🟡 | COMPOSE深度变异Set<Int>#15 | `val x:Set<Int>=...;var count=0;Button(onClick={count++}){Tex` | remember{mutableStateOf} |
| KT-2430 | 🟡 | COMPOSE深度变异Long#16 | `val x:Long=...;@Composable fun C(){val s=remember{;CF(s)}` | 状态提升 |
| KT-2431 | ⚪ | COMPOSE深度变异Any#17 | `val x:Any=...;remember{\"static\"}` | 直接使用 |
| KT-2432 | 🔴 | COMPOSE深度变异List<String>#18 | `val x:List<String>=...;@Composable fun Item(user:User){` | @Immutable标注 |
| KT-2433 | 🟡 | COMPOSE深度变异Int#19 | `val x:Int=...;var txt by remember{ mutableStateOf(\"\")}` | rememberSaveable |
| KT-2434 | 🟡 | COMPOSE深度变异Short#20 | `val x:Short=...;items.forEach{Item(it)}` | items.forEach{key(it.id){Item(it)}} |
| KT-2435 | 🟡 | COMPOSE深度变异Any?#21 | `val x:Any?=...;Modifier.padding(16).clickable{}` | clickable放padding之前 |
| KT-2436 | ⚪ | COMPOSE深度变异String#22 | `val x:String=...;val d=derivedStateOf{list}` | 直接val |
| KT-2437 | 🔴 | COMPOSE深度变异Byte#23 | `val x:Byte=...;LaunchedEffect(Unit){counter++}` | key使用稳定值 |
| KT-2438 | 🟡 | COMPOSE深度变异Boolean?#24 | `val x:Boolean?=...;val v=derivedStateOf{simpleCalc()}` | 直接计算 |
| KT-2439 | 🔴 | COMPOSE深度变异Sequence<Long>#25 | `val x:Sequence<Long>=...;CompositionLocalProvider{loadData()` | LaunchedEffect |
| KT-2440 | 🔴 | COMPOSE深度变异Char#26 | `val x:Char=...;Text(File(\"x\").readText())` | LaunchedEffect+IO |
| KT-2441 | 🟡 | COMPOSE深度变异Double?#27 | `val x:Double?=...;LaunchedEffect(Unit){load()}` | 正确的key |
| KT-2442 | 🟡 | COMPOSE深度变异Array<Boolean>#28 | `val x:Array<Boolean>=...;var count=0;Button(onClick={count++` | remember{mutableStateOf} |
| KT-2443 | 🟡 | COMPOSE深度变异Float#29 | `val x:Float=...;@Composable fun C(){val s=remember{;CF(s)}` | 状态提升 |
| KT-2444 | ⚪ | COMPOSE深度变异Long?#30 | `val x:Long?=...;remember{\"static\"}` | 直接使用 |
| KT-2445 | 🔴 | COMPOSE深度变异MutableList<Double>#31 | `val x:MutableList<Double>=...;@Composable fun Item(user:User` | @Immutable标注 |
| KT-2446 | 🟡 | COMPOSE深度变异Boolean#32 | `val x:Boolean=...;var txt by remember{ mutableStateOf(\"\")}` | rememberSaveable |
| KT-2447 | 🟡 | COMPOSE深度变异Int?#33 | `val x:Int?=...;items.forEach{Item(it)}` | items.forEach{key(it.id){Item(it)}} |
| KT-2448 | 🟡 | COMPOSE深度变异Map<String,Int>#34 | `val x:Map<String,Int>=...;Modifier.padding(16).clickable{}` | clickable放padding之前 |
| KT-2449 | ⚪ | COMPOSE深度变异Double#35 | `val x:Double=...;val d=derivedStateOf{list}` | 直接val |
| KT-2450 | 🔴 | COMPOSE深度变异String?#36 | `val x:String?=...;LaunchedEffect(Unit){counter++}` | key使用稳定值 |
| KT-2451 | 🟡 | COMPOSE深度变异Set<Int>#37 | `val x:Set<Int>=...;val v=derivedStateOf{simpleCalc()}` | 直接计算 |
| KT-2452 | 🔴 | COMPOSE深度变异Long#38 | `val x:Long=...;CompositionLocalProvider{loadData()}` | LaunchedEffect |
| KT-2453 | 🔴 | COMPOSE深度变异Any#39 | `val x:Any=...;Text(File(\"x\").readText())` | LaunchedEffect+IO |
| KT-2454 | 🟡 | COMPOSE深度变异List<String>#40 | `val x:List<String>=...;LaunchedEffect(Unit){load()}` | 正确的key |
| KT-2455 | 🟡 | COMPOSE深度变异Int#41 | `val x:Int=...;var count=0;Button(onClick={count++}){Text(\"\` | remember{mutableStateOf} |
| KT-2456 | 🟡 | COMPOSE深度变异Short#42 | `val x:Short=...;@Composable fun C(){val s=remember{;CF(s)}` | 状态提升 |
| KT-2457 | ⚪ | COMPOSE深度变异Any?#43 | `val x:Any?=...;remember{\"static\"}` | 直接使用 |
| KT-2458 | 🔴 | COMPOSE深度变异String#44 | `val x:String=...;@Composable fun Item(user:User){` | @Immutable标注 |
| KT-2459 | 🟡 | COMPOSE深度变异Byte#45 | `val x:Byte=...;var txt by remember{ mutableStateOf(\"\")}` | rememberSaveable |
| KT-2460 | 🟡 | COMPOSE深度变异Boolean?#46 | `val x:Boolean?=...;items.forEach{Item(it)}` | items.forEach{key(it.id){Item(it)}} |
| KT-2461 | 🟡 | COMPOSE深度变异Sequence<Long>#47 | `val x:Sequence<Long>=...;Modifier.padding(16).clickable{}` | clickable放padding之前 |
| KT-2462 | ⚪ | COMPOSE深度变异Char#48 | `val x:Char=...;val d=derivedStateOf{list}` | 直接val |
| KT-2463 | 🔴 | COMPOSE深度变异Double?#49 | `val x:Double?=...;LaunchedEffect(Unit){counter++}` | key使用稳定值 |
| KT-2464 | 🟡 | COMPOSE深度变异Array<Boolean>#50 | `val x:Array<Boolean>=...;val v=derivedStateOf{simpleCalc()}` | 直接计算 |
| KT-2465 | 🔴 | COMPOSE深度变异Float#51 | `val x:Float=...;CompositionLocalProvider{loadData()}` | LaunchedEffect |
| KT-2466 | 🔴 | COMPOSE深度变异Long?#52 | `val x:Long?=...;Text(File(\"x\").readText())` | LaunchedEffect+IO |
| KT-2467 | 🟡 | COMPOSE深度变异MutableList<Double>#53 | `val x:MutableList<Double>=...;LaunchedEffect(Unit){load()}` | 正确的key |
| KT-2468 | 🟡 | COMPOSE深度变异Boolean#54 | `val x:Boolean=...;var count=0;Button(onClick={count++}){Text` | remember{mutableStateOf} |
| KT-2469 | 🟡 | COMPOSE深度变异Int?#55 | `val x:Int?=...;@Composable fun C(){val s=remember{;CF(s)}` | 状态提升 |
| KT-2470 | ⚪ | COMPOSE深度变异Map<String,Int>#56 | `val x:Map<String,Int>=...;remember{\"static\"}` | 直接使用 |
| KT-2471 | 🔴 | COMPOSE深度变异Double#57 | `val x:Double=...;@Composable fun Item(user:User){` | @Immutable标注 |
| KT-2472 | 🟡 | COMPOSE深度变异String?#58 | `val x:String?=...;var txt by remember{ mutableStateOf(\"\")}` | rememberSaveable |
| KT-2473 | 🟡 | COMPOSE深度变异Set<Int>#59 | `val x:Set<Int>=...;items.forEach{Item(it)}` | items.forEach{key(it.id){Item(it)}} |
| KT-2474 | 🟡 | COMPOSE深度变异Long#60 | `val x:Long=...;Modifier.padding(16).clickable{}` | clickable放padding之前 |
| KT-2475 | ⚪ | COMPOSE深度变异Any#61 | `val x:Any=...;val d=derivedStateOf{list}` | 直接val |
| KT-2476 | 🔴 | COMPOSE深度变异List<String>#62 | `val x:List<String>=...;LaunchedEffect(Unit){counter++}` | key使用稳定值 |
| KT-2477 | 🟡 | COMPOSE深度变异Int#63 | `val x:Int=...;val v=derivedStateOf{simpleCalc()}` | 直接计算 |
| KT-2478 | 🔴 | COMPOSE深度变异Short#64 | `val x:Short=...;CompositionLocalProvider{loadData()}` | LaunchedEffect |
| KT-2479 | 🔴 | COMPOSE深度变异Any?#65 | `val x:Any?=...;Text(File(\"x\").readText())` | LaunchedEffect+IO |
| KT-2480 | 🟡 | COMPOSE深度变异String#66 | `val x:String=...;LaunchedEffect(Unit){load()}` | 正确的key |
| KT-2481 | 🟡 | COMPOSE深度变异Byte#67 | `val x:Byte=...;var count=0;Button(onClick={count++}){Text(\"` | remember{mutableStateOf} |
| KT-2482 | 🟡 | COMPOSE深度变异Boolean?#68 | `val x:Boolean?=...;@Composable fun C(){val s=remember{;CF(s)` | 状态提升 |
| KT-2483 | ⚪ | COMPOSE深度变异Sequence<Long>#69 | `val x:Sequence<Long>=...;remember{\"static\"}` | 直接使用 |
| KT-2484 | 🔴 | COMPOSE深度变异Char#70 | `val x:Char=...;@Composable fun Item(user:User){` | @Immutable标注 |
| KT-2485 | 🟡 | COMPOSE深度变异Double?#71 | `val x:Double?=...;var txt by remember{ mutableStateOf(\"\")}` | rememberSaveable |
| KT-2486 | 🟡 | COMPOSE深度变异Array<Boolean>#72 | `val x:Array<Boolean>=...;items.forEach{Item(it)}` | items.forEach{key(it.id){Item(it)}} |
| KT-2487 | 🟡 | COMPOSE深度变异Float#73 | `val x:Float=...;Modifier.padding(16).clickable{}` | clickable放padding之前 |
| KT-2488 | ⚪ | COMPOSE深度变异Long?#74 | `val x:Long?=...;val d=derivedStateOf{list}` | 直接val |
| KT-2489 | 🔴 | COMPOSE深度变异MutableList<Double>#75 | `val x:MutableList<Double>=...;LaunchedEffect(Unit){counter++` | key使用稳定值 |
| KT-2490 | 🟡 | COMPOSE深度变异Boolean#76 | `val x:Boolean=...;val v=derivedStateOf{simpleCalc()}` | 直接计算 |
| KT-2491 | 🔴 | COMPOSE深度变异Int?#77 | `val x:Int?=...;CompositionLocalProvider{loadData()}` | LaunchedEffect |
| KT-2492 | 🔴 | COMPOSE深度变异Map<String,Int>#78 | `val x:Map<String,Int>=...;Text(File(\"x\").readText())` | LaunchedEffect+IO |
| KT-2493 | 🟡 | COMPOSE深度变异Double#79 | `val x:Double=...;LaunchedEffect(Unit){load()}` | 正确的key |
| KT-2494 | 🟡 | COMPOSE深度变异String?#80 | `val x:String?=...;var count=0;Button(onClick={count++}){Text` | remember{mutableStateOf} |
| KT-2495 | 🟡 | COMPOSE深度变异Set<Int>#81 | `val x:Set<Int>=...;@Composable fun C(){val s=remember{;CF(s)` | 状态提升 |
| KT-2496 | ⚪ | COMPOSE深度变异Long#82 | `val x:Long=...;remember{\"static\"}` | 直接使用 |
| KT-2497 | 🔴 | COMPOSE深度变异Any#83 | `val x:Any=...;@Composable fun Item(user:User){` | @Immutable标注 |
| KT-2498 | 🟡 | COMPOSE深度变异List<String>#84 | `val x:List<String>=...;var txt by remember{ mutableStateOf(\` | rememberSaveable |
| KT-2499 | 🟡 | COMPOSE深度变异Int#85 | `val x:Int=...;items.forEach{Item(it)}` | items.forEach{key(it.id){Item(it)}} |
| KT-2500 | 🟡 | COMPOSE深度变异Short#86 | `val x:Short=...;Modifier.padding(16).clickable{}` | clickable放padding之前 |

## CONCURRENCY（200条）

| ID | 严重度 | Bug描述 | 触发模式 | 修复建议 |
|---|------|------|------|------|
| KT-0105 | 🔴 | 共享可变状态无同步 | *另修: synchronized块, ReentrantLock, 改用不可变数据+copy* |`var c=0;repeat(100){thread{c++}}` | AtomicInteger |
| KT-0106 | 🔴 | 双重检查锁定缺陷 | `if(x==null){synchronized(this){if(x==null){x=f()}}}` | @Volatile |
| KT-0107 | 🔴 | Synchronized内阻塞操作 | `synchronized(lock){doSlowIO()}` | 缩小同步块 |
| KT-0108 | 🟡 | 死锁风险 | `fun a(){lock1;lock2} fun b(){lock2;lock1}` | 统一锁顺序 |
| KT-0109 | 🟡 | Thread.sleep在协程 | `suspend fun f(){Thread.sleep(1000)}` | delay |
| KT-0110 | 🟡 | volatile缺失 | `var flag=false;thread{flag=true}` | @Volatile |
| KT-0111 | ⚪ | 不必要的同步 | `synchronized(val x=42){}` | 直接赋值 |
| KT-0222 | 🔴 | 不可见赋值+volatile缺失 | `var flag=false;thread{flag=true};while(!flag){}` | @Volatile |
| KT-0223 | 🔴 | 双重检查锁定缺少局部变量 | `if(x==null){synchronized{val t=f();if(x==null)x=t}}` | 局部val=instance;if(...) |
| KT-0224 | 🟡 | AtomicReference的ABA问题 | `val ref=AtomicReference(0);ref.compareAndSet(0,1);...;ref.co` | AtomicStampedReference |
| KT-0225 | 🟡 | 并发HashMap的putIfAbsent竞态 | `map.putIfAbsent(k,calc());map[k]` | computeIfAbsent(k){calc()} |
| KT-0226 | 🟡 | 锁粒度太粗 | `synchronized(this){a();b();c();sleep()}` | 缩小同步块 |
| KT-0227 | 🟡 | Condition未在锁内使用 | `lock.newCondition().await()` | lock.withLock{cond.await()} |
| KT-0228 | ⚪ | 并发集合不必要 | `Collections.synchronizedList(arrayList)` | 直接ArrayList |
| KT-0256 | 🔴 | 父进程独裁导致子进程全灭 | `ProcessCoordinator.setStyle(DICTATOR)` | FEDERAL或CONTRACT |
| KT-0257 | 🔴 | 子进程杀死父进程 | `launch{parentJob.cancel()}` | 子进程不应持有parentJob引用 |
| KT-0258 | 🟡 | 指挥官互相争夺同一子进程 | `两个@Commander注解同一tag` | 一个tag一个指挥官 |
| KT-0259 | 🔴 | 进程树中孙子进程孤立 | `launch{launch{launch{}};cancel中间层` | supervisorScope+Job树检查 |
| KT-0285 | 🔴 | 子进程反过来指挥父进程 | `@ProcessBody中调用ProcessCoordinator.setStyle()` | 子进程只读不写 |
| KT-0286 | 🟡 | 指挥官被进程体反杀 | `@Commander中调用自己tag的@ProcessBody杀自己` | Commander不参与ProcessBody |
| KT-0287 | 🔴 | 检测进程报告一切正常但全线崩溃 | `watchTag返回healthy=true但summary全是✖` | cross-check其他哨兵 |
| KT-0288 | 🟡 | 依赖图标记已解决实际未解决 | `DependencyGraph标记conflict_resolved但detectConflicts仍有` | 解析后立即re-check |
| KT-0291 | 🟡 | EventBus取消订阅后仍收到事件 | `unsubscribe(tag);emit(tag,ev);仍收到` | unsubscribe后立即yield |
| KT-0304 | 🔴 | synchronized+协程=阻塞线程池 | `synchronized(lock){delay(1000)}` | Mutex.withLock+delay |
| KT-0310 | 🔴 | 单线程协程拿到了并行结果，多线程反而串行 | `coroutineScope{async{a};async{b};awaitAll}` | 多线程用无锁数据结构 |
| KT-0311 | 🔴 | 单线程expect但实际是async语义 | `val x=async{a};val y=async{b};x.await()+y.await()` | 如需lazy用CoroutineStart.LAZY |
| KT-0312 | 🟡 | Dispatchers.Default单核跑出并行幻觉 | `单核CPU上launch(Default){a;b;c}` | 明确用newSingleThreadContext |
| KT-0313 | 🟡 | 多线程加锁反而比单线程更慢 | `1000线程竞争同一把synchronized锁` | 缩小临界区或用无锁CAS |
| KT-0317 | 🔴 | volatile+递增=你以为原子实际不是 | `@Volatile var x=0;threads{x++}` | AtomicInteger.incrementAndGet |
| KT-0475 | 🔴 | Mutex未释放 | `mutex.withLock{throw E()}` | try-catch-withLock |
| KT-0488 | 🟡 | Channel未关闭 | `val c=Channel<Int>();produce{` | finally{c.close()} |
| KT-2245 | 🔴 | CONCURRENCY深度变异String#0 | `val x:String=...;var c=0;repeat(100){thread{c++}}` | AtomicInteger |
| KT-2246 | 🔴 | CONCURRENCY深度变异Byte#1 | `val x:Byte=...;if(x==null){synchronized(this){if(x==null){x=` | @Volatile |
| KT-2247 | 🔴 | CONCURRENCY深度变异Boolean?#2 | `val x:Boolean?=...;synchronized(lock){doSlowIO()}` | 缩小同步块 |
| KT-2248 | 🟡 | CONCURRENCY深度变异Sequence<Long>#3 | `val x:Sequence<Long>=...;fun a(){lock1;lock2} fun b(){lock2;` | 统一锁顺序 |
| KT-2249 | 🟡 | CONCURRENCY深度变异Char#4 | `val x:Char=...;suspend fun f(){Thread.sleep(1000)}` | delay |
| KT-2250 | 🟡 | CONCURRENCY深度变异Double?#5 | `val x:Double?=...;var flag=false;thread{flag=true}` | @Volatile |
| KT-2251 | ⚪ | CONCURRENCY深度变异Array<Boolean>#6 | `val x:Array<Boolean>=...;synchronized(val x=42){}` | 直接赋值 |
| KT-2252 | 🔴 | CONCURRENCY深度变异Float#7 | `val x:Float=...;var flag=false;thread{flag=true};while(!flag` | @Volatile |
| KT-2253 | 🔴 | CONCURRENCY深度变异Long?#8 | `val x:Long?=...;if(x==null){synchronized{val t=f();if(x==nul` | 局部val=instance;if(...) |
| KT-2254 | 🟡 | CONCURRENCY深度变异MutableList<Double>#9 | `val x:MutableList<Double>=...;val ref=AtomicReference(0);ref` | AtomicStampedReference |
| KT-2255 | 🟡 | CONCURRENCY深度变异Boolean#10 | `val x:Boolean=...;map.putIfAbsent(k,calc());map[k]` | computeIfAbsent(k){calc()} |
| KT-2256 | 🟡 | CONCURRENCY深度变异Int?#11 | `val x:Int?=...;synchronized(this){a();b();c();sleep()}` | 缩小同步块 |
| KT-2257 | 🟡 | CONCURRENCY深度变异Map<String,Int>#12 | `val x:Map<String,Int>=...;lock.newCondition().await()` | lock.withLock{cond.await()} |
| KT-2258 | ⚪ | CONCURRENCY深度变异Double#13 | `Collections.synchronizedDouble(arrayDouble)` | 直接ArrayList |
| KT-2259 | 🔴 | CONCURRENCY深度变异String?#14 | `val x:String?=...;ProcessCoordinator.setStyle(DICTATOR)` | FEDERAL或CONTRACT |
| KT-2260 | 🔴 | CONCURRENCY深度变异Set<Int>#15 | `val x:Set<Int>=...;launch{parentJob.cancel()}` | 子进程不应持有parentJob引用 |
| KT-2261 | 🟡 | CONCURRENCY深度变异Long#16 | `val x:Long=...;两个@Commander注解同一tag` | 一个tag一个指挥官 |
| KT-2262 | 🔴 | CONCURRENCY深度变异Any#17 | `val x:Any=...;launch{launch{launch{}};cancel中间层` | supervisorScope+Job树检查 |
| KT-2263 | 🔴 | CONCURRENCY深度变异List<String>#18 | `val x:List<String>=...;@ProcessBody中调用ProcessCoordinator.set` | 子进程只读不写 |
| KT-2264 | 🟡 | CONCURRENCY深度变异Int#19 | `val x:Int=...;@Commander中调用自己tag的@ProcessBody杀自己` | Commander不参与ProcessBody |
| KT-2265 | 🔴 | CONCURRENCY深度变异Short#20 | `val x:Short=...;watchTag返回healthy=true但summary全是✖` | cross-check其他哨兵 |
| KT-2266 | 🟡 | CONCURRENCY深度变异Any?#21 | `val x:Any?=...;DependencyGraph标记conflict_resolved但detectConf` | 解析后立即re-check |
| KT-2267 | 🟡 | CONCURRENCY深度变异String#22 | `val x:String=...;unsubscribe(tag);emit(tag,ev);仍收到` | unsubscribe后立即yield |
| KT-2268 | 🔴 | CONCURRENCY深度变异Byte#23 | `val x:Byte=...;synchronized(lock){delay(1000)}` | Mutex.withLock+delay |
| KT-2269 | 🔴 | CONCURRENCY深度变异Boolean?#24 | `val x:Boolean?=...;coroutineScope{async{a};async{b};awaitAll` | 多线程用无锁数据结构 |
| KT-2270 | 🔴 | CONCURRENCY深度变异Sequence<Long>#25 | `val x:Sequence<Long>=...;val x=async{a};val y=async{b};x.awa` | 如需lazy用CoroutineStart.LAZY |
| KT-2271 | 🟡 | CONCURRENCY深度变异Char#26 | `val x:Char=...;单核CPU上launch(Default){a;b;c}` | 明确用newSingleThreadContext |
| KT-2272 | 🟡 | CONCURRENCY深度变异Double?#27 | `val x:Double?=...;1000线程竞争同一把synchronized锁` | 缩小临界区或用无锁CAS |
| KT-2273 | 🔴 | CONCURRENCY深度变异Array<Boolean>#28 | `val x:Array<Boolean>=...;@Volatile var x=0;threads{x++}` | AtomicInteger.incrementAndGet |
| KT-2274 | 🔴 | CONCURRENCY深度变异Float#29 | `val x:Float=...;mutex.withLock{throw E()}` | try-catch-withLock |
| KT-2275 | 🟡 | CONCURRENCY深度变异Long?#30 | `val c=Channel<Long?>();produce{` | finally{c.close()} |
| KT-2276 | 🔴 | CONCURRENCY深度变异MutableList<Double>#31 | `val x:MutableList<Double>=...;var c=0;repeat(100){thread{c++` | AtomicInteger |
| KT-2277 | 🔴 | CONCURRENCY深度变异Boolean#32 | `val x:Boolean=...;if(x==null){synchronized(this){if(x==null)` | @Volatile |
| KT-2278 | 🔴 | CONCURRENCY深度变异Int?#33 | `val x:Int?=...;synchronized(lock){doSlowIO()}` | 缩小同步块 |
| KT-2279 | 🟡 | CONCURRENCY深度变异Map<String,Int>#34 | `val x:Map<String,Int>=...;fun a(){lock1;lock2} fun b(){lock2` | 统一锁顺序 |
| KT-2280 | 🟡 | CONCURRENCY深度变异Double#35 | `val x:Double=...;suspend fun f(){Thread.sleep(1000)}` | delay |
| KT-2281 | 🟡 | CONCURRENCY深度变异String?#36 | `val x:String?=...;var flag=false;thread{flag=true}` | @Volatile |
| KT-2282 | ⚪ | CONCURRENCY深度变异Set<Int>#37 | `val x:Set<Int>=...;synchronized(val x=42){}` | 直接赋值 |
| KT-2283 | 🔴 | CONCURRENCY深度变异Long#38 | `val x:Long=...;var flag=false;thread{flag=true};while(!flag)` | @Volatile |
| KT-2284 | 🔴 | CONCURRENCY深度变异Any#39 | `val x:Any=...;if(x==null){synchronized{val t=f();if(x==null)` | 局部val=instance;if(...) |
| KT-2285 | 🟡 | CONCURRENCY深度变异List<String>#40 | `val x:List<String>=...;val ref=AtomicReference(0);ref.compar` | AtomicStampedReference |
| KT-2286 | 🟡 | CONCURRENCY深度变异Int#41 | `val x:Int=...;map.putIfAbsent(k,calc());map[k]` | computeIfAbsent(k){calc()} |
| KT-2287 | 🟡 | CONCURRENCY深度变异Short#42 | `val x:Short=...;synchronized(this){a();b();c();sleep()}` | 缩小同步块 |
| KT-2288 | 🟡 | CONCURRENCY深度变异Any?#43 | `val x:Any?=...;lock.newCondition().await()` | lock.withLock{cond.await()} |
| KT-2289 | ⚪ | CONCURRENCY深度变异String#44 | `Collections.synchronizedString(arrayString)` | 直接ArrayList |
| KT-2290 | 🔴 | CONCURRENCY深度变异Byte#45 | `val x:Byte=...;ProcessCoordinator.setStyle(DICTATOR)` | FEDERAL或CONTRACT |
| KT-2291 | 🔴 | CONCURRENCY深度变异Boolean?#46 | `val x:Boolean?=...;launch{parentJob.cancel()}` | 子进程不应持有parentJob引用 |
| KT-2292 | 🟡 | CONCURRENCY深度变异Sequence<Long>#47 | `val x:Sequence<Long>=...;两个@Commander注解同一tag` | 一个tag一个指挥官 |
| KT-2293 | 🔴 | CONCURRENCY深度变异Char#48 | `val x:Char=...;launch{launch{launch{}};cancel中间层` | supervisorScope+Job树检查 |
| KT-2294 | 🔴 | CONCURRENCY深度变异Double?#49 | `val x:Double?=...;@ProcessBody中调用ProcessCoordinator.setStyle` | 子进程只读不写 |
| KT-2295 | 🟡 | CONCURRENCY深度变异Array<Boolean>#50 | `val x:Array<Boolean>=...;@Commander中调用自己tag的@ProcessBody杀自己` | Commander不参与ProcessBody |
| KT-2296 | 🔴 | CONCURRENCY深度变异Float#51 | `val x:Float=...;watchTag返回healthy=true但summary全是✖` | cross-check其他哨兵 |
| KT-2297 | 🟡 | CONCURRENCY深度变异Long?#52 | `val x:Long?=...;DependencyGraph标记conflict_resolved但detectCon` | 解析后立即re-check |
| KT-2298 | 🟡 | CONCURRENCY深度变异MutableList<Double>#53 | `val x:MutableList<Double>=...;unsubscribe(tag);emit(tag,ev);` | unsubscribe后立即yield |
| KT-2299 | 🔴 | CONCURRENCY深度变异Boolean#54 | `val x:Boolean=...;synchronized(lock){delay(1000)}` | Mutex.withLock+delay |
| KT-2300 | 🔴 | CONCURRENCY深度变异Int?#55 | `val x:Int?=...;coroutineScope{async{a};async{b};awaitAll}` | 多线程用无锁数据结构 |
| KT-2301 | 🔴 | CONCURRENCY深度变异Map<String,Int>#56 | `val x:Map<String,Int>=...;val x=async{a};val y=async{b};x.aw` | 如需lazy用CoroutineStart.LAZY |
| KT-2302 | 🟡 | CONCURRENCY深度变异Double#57 | `val x:Double=...;单核CPU上launch(Default){a;b;c}` | 明确用newSingleThreadContext |
| KT-2303 | 🟡 | CONCURRENCY深度变异String?#58 | `val x:String?=...;1000线程竞争同一把synchronized锁` | 缩小临界区或用无锁CAS |
| KT-2304 | 🔴 | CONCURRENCY深度变异Set<Int>#59 | `val x:Set<Int>=...;@Volatile var x=0;threads{x++}` | AtomicInteger.incrementAndGet |
| KT-2305 | 🔴 | CONCURRENCY深度变异Long#60 | `val x:Long=...;mutex.withLock{throw E()}` | try-catch-withLock |
| KT-2306 | 🟡 | CONCURRENCY深度变异Any#61 | `val c=Channel<Any>();produce{` | finally{c.close()} |
| KT-2307 | 🔴 | CONCURRENCY深度变异List<String>#62 | `val x:List<String>=...;var c=0;repeat(100){thread{c++}}` | AtomicInteger |
| KT-2308 | 🔴 | CONCURRENCY深度变异Int#63 | `val x:Int=...;if(x==null){synchronized(this){if(x==null){x=f` | @Volatile |
| KT-2309 | 🔴 | CONCURRENCY深度变异Short#64 | `val x:Short=...;synchronized(lock){doSlowIO()}` | 缩小同步块 |
| KT-2310 | 🟡 | CONCURRENCY深度变异Any?#65 | `val x:Any?=...;fun a(){lock1;lock2} fun b(){lock2;lock1}` | 统一锁顺序 |
| KT-2311 | 🟡 | CONCURRENCY深度变异String#66 | `val x:String=...;suspend fun f(){Thread.sleep(1000)}` | delay |
| KT-2312 | 🟡 | CONCURRENCY深度变异Byte#67 | `val x:Byte=...;var flag=false;thread{flag=true}` | @Volatile |
| KT-2313 | ⚪ | CONCURRENCY深度变异Boolean?#68 | `val x:Boolean?=...;synchronized(val x=42){}` | 直接赋值 |
| KT-2314 | 🔴 | CONCURRENCY深度变异Sequence<Long>#69 | `val x:Sequence<Long>=...;var flag=false;thread{flag=true};wh` | @Volatile |
| KT-2315 | 🔴 | CONCURRENCY深度变异Char#70 | `val x:Char=...;if(x==null){synchronized{val t=f();if(x==null` | 局部val=instance;if(...) |
| KT-2316 | 🟡 | CONCURRENCY深度变异Double?#71 | `val x:Double?=...;val ref=AtomicReference(0);ref.compareAndS` | AtomicStampedReference |
| KT-2317 | 🟡 | CONCURRENCY深度变异Array<Boolean>#72 | `val x:Array<Boolean>=...;map.putIfAbsent(k,calc());map[k]` | computeIfAbsent(k){calc()} |
| KT-2318 | 🟡 | CONCURRENCY深度变异Float#73 | `val x:Float=...;synchronized(this){a();b();c();sleep()}` | 缩小同步块 |
| KT-2319 | 🟡 | CONCURRENCY深度变异Long?#74 | `val x:Long?=...;lock.newCondition().await()` | lock.withLock{cond.await()} |
| KT-2320 | ⚪ | CONCURRENCY深度变异MutableList<Double>#75 | `Collections.synchronizedMutableList<Double>(arrayMutableList` | 直接ArrayList |
| KT-2321 | 🔴 | CONCURRENCY深度变异Boolean#76 | `val x:Boolean=...;ProcessCoordinator.setStyle(DICTATOR)` | FEDERAL或CONTRACT |
| KT-2322 | 🔴 | CONCURRENCY深度变异Int?#77 | `val x:Int?=...;launch{parentJob.cancel()}` | 子进程不应持有parentJob引用 |
| KT-2323 | 🟡 | CONCURRENCY深度变异Map<String,Int>#78 | `val x:Map<String,Int>=...;两个@Commander注解同一tag` | 一个tag一个指挥官 |
| KT-2324 | 🔴 | CONCURRENCY深度变异Double#79 | `val x:Double=...;launch{launch{launch{}};cancel中间层` | supervisorScope+Job树检查 |
| KT-2325 | 🔴 | CONCURRENCY深度变异String?#80 | `val x:String?=...;@ProcessBody中调用ProcessCoordinator.setStyle` | 子进程只读不写 |
| KT-2326 | 🟡 | CONCURRENCY深度变异Set<Int>#81 | `val x:Set<Int>=...;@Commander中调用自己tag的@ProcessBody杀自己` | Commander不参与ProcessBody |
| KT-2327 | 🔴 | CONCURRENCY深度变异Long#82 | `val x:Long=...;watchTag返回healthy=true但summary全是✖` | cross-check其他哨兵 |
| KT-2328 | 🟡 | CONCURRENCY深度变异Any#83 | `val x:Any=...;DependencyGraph标记conflict_resolved但detectConfl` | 解析后立即re-check |
| KT-2329 | 🟡 | CONCURRENCY深度变异List<String>#84 | `val x:List<String>=...;unsubscribe(tag);emit(tag,ev);仍收到` | unsubscribe后立即yield |
| KT-2330 | 🔴 | CONCURRENCY深度变异Int#85 | `val x:Int=...;synchronized(lock){delay(1000)}` | Mutex.withLock+delay |
| KT-2331 | 🔴 | CONCURRENCY深度变异Short#86 | `val x:Short=...;coroutineScope{async{a};async{b};awaitAll}` | 多线程用无锁数据结构 |
| KT-2332 | 🔴 | CONCURRENCY深度变异Any?#87 | `val x:Any?=...;val x=async{a};val y=async{b};x.await()+y.awa` | 如需lazy用CoroutineStart.LAZY |
| KT-2333 | 🟡 | CONCURRENCY深度变异String#88 | `val x:String=...;单核CPU上launch(Default){a;b;c}` | 明确用newSingleThreadContext |
| KT-2334 | 🟡 | CONCURRENCY深度变异Byte#89 | `val x:Byte=...;1000线程竞争同一把synchronized锁` | 缩小临界区或用无锁CAS |
| KT-2335 | 🔴 | CONCURRENCY深度变异Boolean?#90 | `val x:Boolean?=...;@Volatile var x=0;threads{x++}` | AtomicInteger.incrementAndGet |
| KT-2336 | 🔴 | CONCURRENCY深度变异Sequence<Long>#91 | `val x:Sequence<Long>=...;mutex.withLock{throw E()}` | try-catch-withLock |
| KT-2337 | 🟡 | CONCURRENCY深度变异Char#92 | `val c=Channel<Char>();produce{` | finally{c.close()} |
| KT-2338 | 🔴 | CONCURRENCY深度变异Double?#93 | `val x:Double?=...;var c=0;repeat(100){thread{c++}}` | AtomicInteger |
| KT-2339 | 🔴 | CONCURRENCY深度变异Array<Boolean>#94 | `val x:Array<Boolean>=...;if(x==null){synchronized(this){if(x` | @Volatile |
| KT-2340 | 🔴 | CONCURRENCY深度变异Float#95 | `val x:Float=...;synchronized(lock){doSlowIO()}` | 缩小同步块 |
| KT-2341 | 🟡 | CONCURRENCY深度变异Long?#96 | `val x:Long?=...;fun a(){lock1;lock2} fun b(){lock2;lock1}` | 统一锁顺序 |
| KT-2342 | 🟡 | CONCURRENCY深度变异MutableList<Double>#97 | `val x:MutableList<Double>=...;suspend fun f(){Thread.sleep(1` | delay |
| KT-2343 | 🟡 | CONCURRENCY深度变异Boolean#98 | `val x:Boolean=...;var flag=false;thread{flag=true}` | @Volatile |
| KT-2344 | ⚪ | CONCURRENCY深度变异Int?#99 | `val x:Int?=...;synchronized(val x=42){}` | 直接赋值 |
| KT-2345 | 🔴 | CONCURRENCY深度变异Map<String,Int>#100 | `val x:Map<String,Int>=...;var flag=false;thread{flag=true};w` | @Volatile |
| KT-2346 | 🔴 | CONCURRENCY深度变异Double#101 | `val x:Double=...;if(x==null){synchronized{val t=f();if(x==nu` | 局部val=instance;if(...) |
| KT-2347 | 🟡 | CONCURRENCY深度变异String?#102 | `val x:String?=...;val ref=AtomicReference(0);ref.compareAndS` | AtomicStampedReference |
| KT-2348 | 🟡 | CONCURRENCY深度变异Set<Int>#103 | `val x:Set<Int>=...;map.putIfAbsent(k,calc());map[k]` | computeIfAbsent(k){calc()} |
| KT-2349 | 🟡 | CONCURRENCY深度变异Long#104 | `val x:Long=...;synchronized(this){a();b();c();sleep()}` | 缩小同步块 |
| KT-2350 | 🟡 | CONCURRENCY深度变异Any#105 | `val x:Any=...;lock.newCondition().await()` | lock.withLock{cond.await()} |
| KT-2351 | ⚪ | CONCURRENCY深度变异List<String>#106 | `Collections.synchronizedList<String>(arrayList<String>)` | 直接ArrayList |
| KT-2352 | 🔴 | CONCURRENCY深度变异Int#107 | `val x:Int=...;ProcessCoordinator.setStyle(DICTATOR)` | FEDERAL或CONTRACT |
| KT-2353 | 🔴 | CONCURRENCY深度变异Short#108 | `val x:Short=...;launch{parentJob.cancel()}` | 子进程不应持有parentJob引用 |
| KT-2354 | 🟡 | CONCURRENCY深度变异Any?#109 | `val x:Any?=...;两个@Commander注解同一tag` | 一个tag一个指挥官 |
| KT-2355 | 🔴 | CONCURRENCY深度变异String#110 | `val x:String=...;launch{launch{launch{}};cancel中间层` | supervisorScope+Job树检查 |
| KT-2356 | 🔴 | CONCURRENCY深度变异Byte#111 | `val x:Byte=...;@ProcessBody中调用ProcessCoordinator.setStyle()` | 子进程只读不写 |
| KT-2357 | 🟡 | CONCURRENCY深度变异Boolean?#112 | `val x:Boolean?=...;@Commander中调用自己tag的@ProcessBody杀自己` | Commander不参与ProcessBody |
| KT-2358 | 🔴 | CONCURRENCY深度变异Sequence<Long>#113 | `val x:Sequence<Long>=...;watchTag返回healthy=true但summary全是✖` | cross-check其他哨兵 |
| KT-2359 | 🟡 | CONCURRENCY深度变异Char#114 | `val x:Char=...;DependencyGraph标记conflict_resolved但detectConf` | 解析后立即re-check |
| KT-2360 | 🟡 | CONCURRENCY深度变异Double?#115 | `val x:Double?=...;unsubscribe(tag);emit(tag,ev);仍收到` | unsubscribe后立即yield |
| KT-2361 | 🔴 | CONCURRENCY深度变异Array<Boolean>#116 | `val x:Array<Boolean>=...;synchronized(lock){delay(1000)}` | Mutex.withLock+delay |
| KT-2362 | 🔴 | CONCURRENCY深度变异Float#117 | `val x:Float=...;coroutineScope{async{a};async{b};awaitAll}` | 多线程用无锁数据结构 |
| KT-2363 | 🔴 | CONCURRENCY深度变异Long?#118 | `val x:Long?=...;val x=async{a};val y=async{b};x.await()+y.aw` | 如需lazy用CoroutineStart.LAZY |
| KT-2364 | 🟡 | CONCURRENCY深度变异MutableList<Double>#119 | `val x:MutableList<Double>=...;单核CPU上launch(Default){a;b;c}` | 明确用newSingleThreadContext |
| KT-2365 | 🟡 | CONCURRENCY深度变异Boolean#120 | `val x:Boolean=...;1000线程竞争同一把synchronized锁` | 缩小临界区或用无锁CAS |
| KT-2366 | 🔴 | CONCURRENCY深度变异Int?#121 | `val x:Int?=...;@Volatile var x=0;threads{x++}` | AtomicInteger.incrementAndGet |
| KT-2367 | 🔴 | CONCURRENCY深度变异Map<String,Int>#122 | `val x:Map<String,Int>=...;mutex.withLock{throw E()}` | try-catch-withLock |
| KT-2368 | 🟡 | CONCURRENCY深度变异Double#123 | `val c=Channel<Double>();produce{` | finally{c.close()} |
| KT-2369 | 🔴 | CONCURRENCY深度变异String?#124 | `val x:String?=...;var c=0;repeat(100){thread{c++}}` | AtomicInteger |
| KT-2370 | 🔴 | CONCURRENCY深度变异Set<Int>#125 | `val x:Set<Int>=...;if(x==null){synchronized(this){if(x==null` | @Volatile |
| KT-2371 | 🔴 | CONCURRENCY深度变异Long#126 | `val x:Long=...;synchronized(lock){doSlowIO()}` | 缩小同步块 |
| KT-2372 | 🟡 | CONCURRENCY深度变异Any#127 | `val x:Any=...;fun a(){lock1;lock2} fun b(){lock2;lock1}` | 统一锁顺序 |
| KT-2373 | 🟡 | CONCURRENCY深度变异List<String>#128 | `val x:List<String>=...;suspend fun f(){Thread.sleep(1000)}` | delay |
| KT-2374 | 🟡 | CONCURRENCY深度变异Int#129 | `val x:Int=...;var flag=false;thread{flag=true}` | @Volatile |
| KT-2375 | ⚪ | CONCURRENCY深度变异Short#130 | `val x:Short=...;synchronized(val x=42){}` | 直接赋值 |
| KT-2376 | 🔴 | CONCURRENCY深度变异Any?#131 | `val x:Any?=...;var flag=false;thread{flag=true};while(!flag)` | @Volatile |
| KT-2377 | 🔴 | CONCURRENCY深度变异String#132 | `val x:String=...;if(x==null){synchronized{val t=f();if(x==nu` | 局部val=instance;if(...) |
| KT-2378 | 🟡 | CONCURRENCY深度变异Byte#133 | `val x:Byte=...;val ref=AtomicReference(0);ref.compareAndSet(` | AtomicStampedReference |
| KT-2379 | 🟡 | CONCURRENCY深度变异Boolean?#134 | `val x:Boolean?=...;map.putIfAbsent(k,calc());map[k]` | computeIfAbsent(k){calc()} |
| KT-2380 | 🟡 | CONCURRENCY深度变异Sequence<Long>#135 | `val x:Sequence<Long>=...;synchronized(this){a();b();c();slee` | 缩小同步块 |
| KT-2381 | 🟡 | CONCURRENCY深度变异Char#136 | `val x:Char=...;lock.newCondition().await()` | lock.withLock{cond.await()} |
| KT-2382 | ⚪ | CONCURRENCY深度变异Double?#137 | `Collections.synchronizedDouble?(arrayDouble?)` | 直接ArrayList |
| KT-2383 | 🔴 | CONCURRENCY深度变异Array<Boolean>#138 | `val x:Array<Boolean>=...;ProcessCoordinator.setStyle(DICTATO` | FEDERAL或CONTRACT |
| KT-2384 | 🔴 | CONCURRENCY深度变异Float#139 | `val x:Float=...;launch{parentJob.cancel()}` | 子进程不应持有parentJob引用 |
| KT-2385 | 🟡 | CONCURRENCY深度变异Long?#140 | `val x:Long?=...;两个@Commander注解同一tag` | 一个tag一个指挥官 |
| KT-2386 | 🔴 | CONCURRENCY深度变异MutableList<Double>#141 | `val x:MutableList<Double>=...;launch{launch{launch{}};cancel` | supervisorScope+Job树检查 |
| KT-2387 | 🔴 | CONCURRENCY深度变异Boolean#142 | `val x:Boolean=...;@ProcessBody中调用ProcessCoordinator.setStyle` | 子进程只读不写 |
| KT-2388 | 🟡 | CONCURRENCY深度变异Int?#143 | `val x:Int?=...;@Commander中调用自己tag的@ProcessBody杀自己` | Commander不参与ProcessBody |
| KT-2389 | 🔴 | CONCURRENCY深度变异Map<String,Int>#144 | `val x:Map<String,Int>=...;watchTag返回healthy=true但summary全是✖` | cross-check其他哨兵 |
| KT-2390 | 🟡 | CONCURRENCY深度变异Double#145 | `val x:Double=...;DependencyGraph标记conflict_resolved但detectCo` | 解析后立即re-check |
| KT-2391 | 🟡 | CONCURRENCY深度变异String?#146 | `val x:String?=...;unsubscribe(tag);emit(tag,ev);仍收到` | unsubscribe后立即yield |
| KT-2392 | 🔴 | CONCURRENCY深度变异Set<Int>#147 | `val x:Set<Int>=...;synchronized(lock){delay(1000)}` | Mutex.withLock+delay |
| KT-2393 | 🔴 | CONCURRENCY深度变异Long#148 | `val x:Long=...;coroutineScope{async{a};async{b};awaitAll}` | 多线程用无锁数据结构 |
| KT-2394 | 🔴 | CONCURRENCY深度变异Any#149 | `val x:Any=...;val x=async{a};val y=async{b};x.await()+y.awai` | 如需lazy用CoroutineStart.LAZY |
| KT-2395 | 🟡 | CONCURRENCY深度变异List<String>#150 | `val x:List<String>=...;单核CPU上launch(Default){a;b;c}` | 明确用newSingleThreadContext |
| KT-2396 | 🟡 | CONCURRENCY深度变异Int#151 | `val x:Int=...;1000线程竞争同一把synchronized锁` | 缩小临界区或用无锁CAS |
| KT-2397 | 🔴 | CONCURRENCY深度变异Short#152 | `val x:Short=...;@Volatile var x=0;threads{x++}` | AtomicInteger.incrementAndGet |
| KT-2398 | 🔴 | CONCURRENCY深度变异Any?#153 | `val x:Any?=...;mutex.withLock{throw E()}` | try-catch-withLock |
| KT-2399 | 🟡 | CONCURRENCY深度变异String#154 | `val c=Channel<String>();produce{` | finally{c.close()} |
| KT-2400 | 🔴 | CONCURRENCY深度变异Byte#155 | `val x:Byte=...;var c=0;repeat(100){thread{c++}}` | AtomicInteger |
| KT-2401 | 🔴 | CONCURRENCY深度变异Boolean?#156 | `val x:Boolean?=...;if(x==null){synchronized(this){if(x==null` | @Volatile |
| KT-2402 | 🔴 | CONCURRENCY深度变异Sequence<Long>#157 | `val x:Sequence<Long>=...;synchronized(lock){doSlowIO()}` | 缩小同步块 |
| KT-2403 | 🟡 | CONCURRENCY深度变异Char#158 | `val x:Char=...;fun a(){lock1;lock2} fun b(){lock2;lock1}` | 统一锁顺序 |
| KT-2404 | 🟡 | CONCURRENCY深度变异Double?#159 | `val x:Double?=...;suspend fun f(){Thread.sleep(1000)}` | delay |
| KT-2405 | 🟡 | CONCURRENCY深度变异Array<Boolean>#160 | `val x:Array<Boolean>=...;var flag=false;thread{flag=true}` | @Volatile |
| KT-2406 | ⚪ | CONCURRENCY深度变异Float#161 | `val x:Float=...;synchronized(val x=42){}` | 直接赋值 |
| KT-2407 | 🔴 | CONCURRENCY深度变异Long?#162 | `val x:Long?=...;var flag=false;thread{flag=true};while(!flag` | @Volatile |
| KT-2408 | 🔴 | CONCURRENCY深度变异MutableList<Double>#163 | `val x:MutableList<Double>=...;if(x==null){synchronized{val t` | 局部val=instance;if(...) |
| KT-2409 | 🟡 | CONCURRENCY深度变异Boolean#164 | `val x:Boolean=...;val ref=AtomicReference(0);ref.compareAndS` | AtomicStampedReference |
| KT-2410 | 🟡 | CONCURRENCY深度变异Int?#165 | `val x:Int?=...;map.putIfAbsent(k,calc());map[k]` | computeIfAbsent(k){calc()} |
| KT-2411 | 🟡 | CONCURRENCY深度变异Map<String,Int>#166 | `val x:Map<String,Int>=...;synchronized(this){a();b();c();sle` | 缩小同步块 |
| KT-2412 | 🟡 | CONCURRENCY深度变异Double#167 | `val x:Double=...;lock.newCondition().await()` | lock.withLock{cond.await()} |
| KT-2413 | ⚪ | CONCURRENCY深度变异String?#168 | `Collections.synchronizedString?(arrayString?)` | 直接ArrayList |

## COROUTINES（300条）

| ID | 严重度 | Bug描述 | 触发模式 | 修复建议 |
|---|------|------|------|------|
| KT-0035 | 🔴 | GlobalScope泄漏 | *另修: 手动Job管理并在onDestroy中cancel* |`GlobalScope.launch{` | lifecycleScope或viewModelScope |
| KT-0036 | 🔴 | launch吞异常 | `launch{riskyOp()}` | async+await或CoroutineExceptionHandler |
| KT-0037 | 🔴 | runBlocking在UI线程 | *另修: 用withContext(IO)包裹, 改用回调式异步* |`runBlocking{delay(5000)}` | lifecycleScope.launch |
| KT-0038 | 🔴 | suspend中调用阻塞方法 | `suspend fun f(){Thread.sleep(1000)}` | delay(1000) |
| KT-0039 | 🔴 | 协程取消不响应 | `launch{while(true){work()}}` | while(isActive) |
| KT-0040 | 🟡 | 缺少CoroutineExceptionHandler | `launch{throw E()}` | val handler=CoroutineExceptionHandler{ |
| KT-0041 | 🟡 | withContext滥用 | `withContext(Dispatchers.IO){lightOp()}` | 用Default或直接执行 |
| KT-0042 | 🟡 | async忘记await | `val d=async{calc()};d.await()` | 确保所有async都被await |
| KT-0043 | 🟡 | coroutineScope与supervisorScope混淆 | `coroutineScope{launch{throw E()};launch{}` | supervisorScope |
| KT-0044 | 🟡 | flow收集无背压 | `flow{emit(...)}.collect{slow()}` | buffer或conflate |
| KT-0045 | ⚪ | 不必要的async | `val d=async{val x=y;x}` | 直接赋值 |
| KT-0046 | ⚪ | Dispatchers.Main硬编码 | `withContext(Dispatchers.Main){` | 依赖注入dispatcher |
| KT-0162 | 🔴 | 子协程取消未传播 | `supervisorScope{launch{heavy()}}` | coroutineScope |
| KT-0163 | 🔴 | coroutineContext丢失 | `withContext(empty){delay(1)}` | 保留原始context |
| KT-0164 | 🟡 | select未处理onAwait | `select{ch.onReceive{}` | 加onAwait |
| KT-0165 | 🟡 | callbackFlow未awaitClose | `callbackFlow{register(cb);awaitClose{unregister()}}` | 加awaitClose |
| KT-0166 | 🟡 | StateFlow.value直接修改 | `state.value=state.value.copy(x=1)` | MutableStateFlow.update{} |
| KT-0167 | 🟡 | launch内return@launch遗漏 | `launch{if(x)return}` | return@launch |
| KT-0168 | ⚪ | async+await替代withContext | `val x=async(IO){work()}.await()` | 直接用withContext |
| KT-0169 | ⚪ | 不必要的flowOn | `flow{emit(1)}.flowOn(IO).flowOn(Default)` | 只保留最后一个 |
| KT-0170 | 🔴 | Channel未关闭导致协程泄漏 | `val c=Channel<Int>();launch{c.consumeEach{}}` | c.close或produceIn |
| KT-0171 | 🟡 | Dispatchers.Unconfined误用 | `launch(Unconfined){updateUI()}` | 显式指定调度器 |
| KT-0260 | 🟡 | 十一种子进程职业全部失业 | `所有@ProcessBody都被Condition拦截` | 放宽condition或加保底 |
| KT-0278 | 🔴 | delay(0)比delay(1)更慢 | `delay(0);delay(0);delay(0)` | 用yield() |
| KT-0279 | 🟡 | 所有协程都在等一个永远不会set的CompletableDeferred | `val d=CompletableDeferred<T>();launch{...d.await()};忘记d.comp` | 加超时withTimeout |
| KT-0280 | 🔴 | Mutex.lock了两次同一个协程 | `mutex.lock();mutex.lock()` | withLock或可重入锁 |
| KT-0292 | 🟡 | flow.collect在collect后又emit了一条 | `flow{emit(1);awaitClose{emit(2)}}` | onCompletion |
| KT-0296 | 🔴 | launch+反射+私有方法=不可预测崩溃 | `launch{val m=cls.getDeclaredMethod("secret");m.isAccessible=` | 提供公开suspend接口 |
| KT-0307 | 🟡 | flow+retry+stateIn=重试时状态丢失 | `flow{apiCall()}.retry(3).stateIn(scope)` | 在retry外层stateIn |
| KT-0314 | 🔴 | Flow默认串行但collect看起来像并行 | `flow{emit(a);emit(b)}.collect{}` | 要并行用channelFlow或flatMapMerge |
| KT-0315 | 🟡 | channel被当成广播但只发了一条 | `val c=Channel<Int>();launch{c.send(1)};launch{println(c.rece` | BroadcastChannel或SharedFlow |
| KT-0364 | 🟡 | channel被当成广播但只发了一条（Long版） | `val c=Channel<Long>();launch{c.send(1)};launch{println(c.rec` | BroadcastChannel或SharedFlow |
| KT-0454 | 🔴 | Channel未关闭导致协程泄漏（Long版） | `val c=Channel<Long>();launch{c.consumeEach{}}` | c.close或produceIn |
| KT-0455 | 🔴 | Channel未关闭导致协程泄漏（Double版） | `val c=Channel<Double>();launch{c.consumeEach{}}` | c.close或produceIn |
| KT-0456 | 🔴 | Channel未关闭导致协程泄漏（Float版） | `val c=Channel<Float>();launch{c.consumeEach{}}` | c.close或produceIn |
| KT-0473 | 🟡 | suspend函数无超时 | `suspend fun api(){httpClient.get(...)}` | withTimeout |
| KT-0474 | 🟡 | Flow未捕获异常 | `flow{emit(risky())}.catch{e->...}` | catch在collect之前 |
| KT-0489 | 🟡 | StateFlow初始值导致重复emit | `val s=MutableStateFlow(init);s.value=init` | distinctUntilChanged或检查值 |
| KT-0917 | 🔴 | COROUTINES深度变异String#0 | `val x:String=...;GlobalScope.launch{` | lifecycleScope或viewModelScope |
| KT-0918 | 🔴 | COROUTINES深度变异Byte#1 | `val x:Byte=...;launch{riskyOp()}` | async+await或CoroutineExceptionHandler |
| KT-0919 | 🔴 | COROUTINES深度变异Boolean?#2 | `val x:Boolean?=...;runBlocking{delay(5000)}` | lifecycleScope.launch |
| KT-0920 | 🔴 | COROUTINES深度变异Sequence<Long>#3 | `val x:Sequence<Long>=...;suspend fun f(){Thread.sleep(1000)}` | delay(1000) |
| KT-0921 | 🔴 | COROUTINES深度变异Char#4 | `val x:Char=...;launch{while(true){work()}}` | while(isActive) |
| KT-0922 | 🟡 | COROUTINES深度变异Double?#5 | `val x:Double?=...;launch{throw E()}` | val handler=CoroutineExceptionHandler{ |
| KT-0923 | 🟡 | COROUTINES深度变异Array<Boolean>#6 | `val x:Array<Boolean>=...;withContext(Dispatchers.IO){lightOp` | 用Default或直接执行 |
| KT-0924 | 🟡 | COROUTINES深度变异Float#7 | `val x:Float=...;val d=async{calc()};d.await()` | 确保所有async都被await |
| KT-0925 | 🟡 | COROUTINES深度变异Long?#8 | `val x:Long?=...;coroutineScope{launch{throw E()};launch{}` | supervisorScope |
| KT-0926 | 🟡 | COROUTINES深度变异MutableList<Double>#9 | `val x:MutableList<Double>=...;flow{emit(...)}.collect{slow()` | buffer或conflate |
| KT-0927 | ⚪ | COROUTINES深度变异Boolean#10 | `val x:Boolean=...;val d=async{val x=y;x}` | 直接赋值 |
| KT-0928 | ⚪ | COROUTINES深度变异Int?#11 | `val x:Int?=...;withContext(Dispatchers.Main){` | 依赖注入dispatcher |
| KT-0929 | 🔴 | COROUTINES深度变异Map<String,Int>#12 | `val x:Map<String,Int>=...;supervisorScope{launch{heavy()}}` | coroutineScope |
| KT-0930 | 🔴 | COROUTINES深度变异Double#13 | `val x:Double=...;withContext(empty){delay(1)}` | 保留原始context |
| KT-0931 | 🟡 | COROUTINES深度变异String?#14 | `val x:String?=...;select{ch.onReceive{}` | 加onAwait |
| KT-0932 | 🟡 | COROUTINES深度变异Set<Int>#15 | `val x:Set<Int>=...;callbackFlow{register(cb);awaitClose{unre` | 加awaitClose |
| KT-0933 | 🟡 | COROUTINES深度变异Long#16 | `val x:Long=...;state.value=state.value.copy(x=1)` | MutableStateFlow.update{} |
| KT-0934 | 🟡 | COROUTINES深度变异Any#17 | `val x:Any=...;launch{if(x)return}` | return@launch |
| KT-0935 | ⚪ | COROUTINES深度变异List<String>#18 | `val x:List<String>=...;val x=async(IO){work()}.await()` | 直接用withContext |
| KT-0936 | ⚪ | COROUTINES深度变异Int#19 | `val x:Int=...;flow{emit(1)}.flowOn(IO).flowOn(Default)` | 只保留最后一个 |
| KT-0937 | 🔴 | COROUTINES深度变异Short#20 | `val c=Channel<Short>();launch{c.consumeEach{}}` | c.close或produceIn |
| KT-0938 | 🟡 | COROUTINES深度变异Any?#21 | `val x:Any?=...;launch(Unconfined){updateUI()}` | 显式指定调度器 |
| KT-0939 | 🟡 | COROUTINES深度变异String#22 | `val x:String=...;所有@ProcessBody都被Condition拦截` | 放宽condition或加保底 |
| KT-0940 | 🔴 | COROUTINES深度变异Byte#23 | `val x:Byte=...;delay(0);delay(0);delay(0)` | 用yield() |
| KT-0941 | 🟡 | COROUTINES深度变异Boolean?#24 | `val x:Boolean?=...;val d=CompletableDeferred<T>();launch{...` | 加超时withTimeout |
| KT-0942 | 🔴 | COROUTINES深度变异Sequence<Long>#25 | `val x:Sequence<Long>=...;mutex.lock();mutex.lock()` | withLock或可重入锁 |
| KT-0943 | 🟡 | COROUTINES深度变异Char#26 | `val x:Char=...;flow{emit(1);awaitClose{emit(2)}}` | onCompletion |
| KT-0944 | 🔴 | COROUTINES深度变异Double?#27 | `val x:Double?=...;launch{val m=cls.getDeclaredMethod(\"secre` | 提供公开suspend接口 |
| KT-0945 | 🟡 | COROUTINES深度变异Array<Boolean>#28 | `val x:Array<Boolean>=...;flow{apiCall()}.retry(3).stateIn(sc` | 在retry外层stateIn |
| KT-0946 | 🔴 | COROUTINES深度变异Float#29 | `val x:Float=...;flow{emit(a);emit(b)}.collect{}` | 要并行用channelFlow或flatMapMerge |
| KT-0947 | 🟡 | COROUTINES深度变异Long?#30 | `val c=Channel<Long?>();launch{c.send(1)};launch{println(c.re` | BroadcastChannel或SharedFlow |
| KT-0948 | 🟡 | COROUTINES深度变异MutableList<Double>#31 | `val x:MutableList<Double>=...;val c=Channel<Long>();launch{c` | BroadcastChannel或SharedFlow |
| KT-0949 | 🔴 | COROUTINES深度变异Boolean#32 | `val x:Boolean=...;val c=Channel<Long>();launch{c.consumeEach` | c.close或produceIn |
| KT-0950 | 🔴 | COROUTINES深度变异Int?#33 | `val x:Int?=...;val c=Channel<Double>();launch{c.consumeEach{` | c.close或produceIn |
| KT-0951 | 🔴 | COROUTINES深度变异Map<String,Int>#34 | `val x:Map<String,Int>=...;val c=Channel<Float>();launch{c.co` | c.close或produceIn |
| KT-0952 | 🟡 | COROUTINES深度变异Double#35 | `val x:Double=...;suspend fun api(){httpClient.get(...)}` | withTimeout |
| KT-0953 | 🟡 | COROUTINES深度变异String?#36 | `val x:String?=...;flow{emit(risky())}.catch{e->...}` | catch在collect之前 |
| KT-0954 | 🟡 | COROUTINES深度变异Set<Int>#37 | `val x:Set<Int>=...;val s=MutableStateFlow(init);s.value=init` | distinctUntilChanged或检查值 |
| KT-0955 | 🔴 | COROUTINES深度变异Long#38 | `val x:Long=...;GlobalScope.launch{` | lifecycleScope或viewModelScope |
| KT-0956 | 🔴 | COROUTINES深度变异Any#39 | `val x:Any=...;launch{riskyOp()}` | async+await或CoroutineExceptionHandler |
| KT-0957 | 🔴 | COROUTINES深度变异List<String>#40 | `val x:List<String>=...;runBlocking{delay(5000)}` | lifecycleScope.launch |
| KT-0958 | 🔴 | COROUTINES深度变异Int#41 | `val x:Int=...;suspend fun f(){Thread.sleep(1000)}` | delay(1000) |
| KT-0959 | 🔴 | COROUTINES深度变异Short#42 | `val x:Short=...;launch{while(true){work()}}` | while(isActive) |
| KT-0960 | 🟡 | COROUTINES深度变异Any?#43 | `val x:Any?=...;launch{throw E()}` | val handler=CoroutineExceptionHandler{ |
| KT-0961 | 🟡 | COROUTINES深度变异String#44 | `val x:String=...;withContext(Dispatchers.IO){lightOp()}` | 用Default或直接执行 |
| KT-0962 | 🟡 | COROUTINES深度变异Byte#45 | `val x:Byte=...;val d=async{calc()};d.await()` | 确保所有async都被await |
| KT-0963 | 🟡 | COROUTINES深度变异Boolean?#46 | `val x:Boolean?=...;coroutineScope{launch{throw E()};launch{}` | supervisorScope |
| KT-0964 | 🟡 | COROUTINES深度变异Sequence<Long>#47 | `val x:Sequence<Long>=...;flow{emit(...)}.collect{slow()}` | buffer或conflate |
| KT-0965 | ⚪ | COROUTINES深度变异Char#48 | `val x:Char=...;val d=async{val x=y;x}` | 直接赋值 |
| KT-0966 | ⚪ | COROUTINES深度变异Double?#49 | `val x:Double?=...;withContext(Dispatchers.Main){` | 依赖注入dispatcher |
| KT-0967 | 🔴 | COROUTINES深度变异Array<Boolean>#50 | `val x:Array<Boolean>=...;supervisorScope{launch{heavy()}}` | coroutineScope |
| KT-0968 | 🔴 | COROUTINES深度变异Float#51 | `val x:Float=...;withContext(empty){delay(1)}` | 保留原始context |
| KT-0969 | 🟡 | COROUTINES深度变异Long?#52 | `val x:Long?=...;select{ch.onReceive{}` | 加onAwait |
| KT-0970 | 🟡 | COROUTINES深度变异MutableList<Double>#53 | `val x:MutableList<Double>=...;callbackFlow{register(cb);awai` | 加awaitClose |
| KT-0971 | 🟡 | COROUTINES深度变异Boolean#54 | `val x:Boolean=...;state.value=state.value.copy(x=1)` | MutableStateFlow.update{} |
| KT-0972 | 🟡 | COROUTINES深度变异Int?#55 | `val x:Int?=...;launch{if(x)return}` | return@launch |
| KT-0973 | ⚪ | COROUTINES深度变异Map<String,Int>#56 | `val x:Map<String,Int>=...;val x=async(IO){work()}.await()` | 直接用withContext |
| KT-0974 | ⚪ | COROUTINES深度变异Double#57 | `val x:Double=...;flow{emit(1)}.flowOn(IO).flowOn(Default)` | 只保留最后一个 |
| KT-0975 | 🔴 | COROUTINES深度变异String?#58 | `val c=Channel<String?>();launch{c.consumeEach{}}` | c.close或produceIn |
| KT-0976 | 🟡 | COROUTINES深度变异Set<Int>#59 | `val x:Set<Int>=...;launch(Unconfined){updateUI()}` | 显式指定调度器 |
| KT-0977 | 🟡 | COROUTINES深度变异Long#60 | `val x:Long=...;所有@ProcessBody都被Condition拦截` | 放宽condition或加保底 |
| KT-0978 | 🔴 | COROUTINES深度变异Any#61 | `val x:Any=...;delay(0);delay(0);delay(0)` | 用yield() |
| KT-0979 | 🟡 | COROUTINES深度变异List<String>#62 | `val x:List<String>=...;val d=CompletableDeferred<T>();launch` | 加超时withTimeout |
| KT-0980 | 🔴 | COROUTINES深度变异Int#63 | `val x:Int=...;mutex.lock();mutex.lock()` | withLock或可重入锁 |
| KT-0981 | 🟡 | COROUTINES深度变异Short#64 | `val x:Short=...;flow{emit(1);awaitClose{emit(2)}}` | onCompletion |
| KT-0982 | 🔴 | COROUTINES深度变异Any?#65 | `val x:Any?=...;launch{val m=cls.getDeclaredMethod(\"secret\"` | 提供公开suspend接口 |
| KT-0983 | 🟡 | COROUTINES深度变异String#66 | `val x:String=...;flow{apiCall()}.retry(3).stateIn(scope)` | 在retry外层stateIn |
| KT-0984 | 🔴 | COROUTINES深度变异Byte#67 | `val x:Byte=...;flow{emit(a);emit(b)}.collect{}` | 要并行用channelFlow或flatMapMerge |
| KT-0985 | 🟡 | COROUTINES深度变异Boolean?#68 | `val c=Channel<Boolean?>();launch{c.send(1)};launch{println(c` | BroadcastChannel或SharedFlow |
| KT-0986 | 🟡 | COROUTINES深度变异Sequence<Long>#69 | `val x:Sequence<Long>=...;val c=Channel<Long>();launch{c.send` | BroadcastChannel或SharedFlow |
| KT-0987 | 🔴 | COROUTINES深度变异Char#70 | `val x:Char=...;val c=Channel<Long>();launch{c.consumeEach{}}` | c.close或produceIn |
| KT-0988 | 🔴 | COROUTINES深度变异Double?#71 | `val x:Double?=...;val c=Channel<Double>();launch{c.consumeEa` | c.close或produceIn |
| KT-0989 | 🔴 | COROUTINES深度变异Array<Boolean>#72 | `val x:Array<Boolean>=...;val c=Channel<Float>();launch{c.con` | c.close或produceIn |
| KT-0990 | 🟡 | COROUTINES深度变异Float#73 | `val x:Float=...;suspend fun api(){httpClient.get(...)}` | withTimeout |
| KT-0991 | 🟡 | COROUTINES深度变异Long?#74 | `val x:Long?=...;flow{emit(risky())}.catch{e->...}` | catch在collect之前 |
| KT-0992 | 🟡 | COROUTINES深度变异MutableList<Double>#75 | `val x:MutableList<Double>=...;val s=MutableStateFlow(init);s` | distinctUntilChanged或检查值 |
| KT-0993 | 🔴 | COROUTINES深度变异Boolean#76 | `val x:Boolean=...;GlobalScope.launch{` | lifecycleScope或viewModelScope |
| KT-0994 | 🔴 | COROUTINES深度变异Int?#77 | `val x:Int?=...;launch{riskyOp()}` | async+await或CoroutineExceptionHandler |
| KT-0995 | 🔴 | COROUTINES深度变异Map<String,Int>#78 | `val x:Map<String,Int>=...;runBlocking{delay(5000)}` | lifecycleScope.launch |
| KT-0996 | 🔴 | COROUTINES深度变异Double#79 | `val x:Double=...;suspend fun f(){Thread.sleep(1000)}` | delay(1000) |
| KT-0997 | 🔴 | COROUTINES深度变异String?#80 | `val x:String?=...;launch{while(true){work()}}` | while(isActive) |
| KT-0998 | 🟡 | COROUTINES深度变异Set<Int>#81 | `val x:Set<Int>=...;launch{throw E()}` | val handler=CoroutineExceptionHandler{ |
| KT-0999 | 🟡 | COROUTINES深度变异Long#82 | `val x:Long=...;withContext(Dispatchers.IO){lightOp()}` | 用Default或直接执行 |
| KT-1000 | 🟡 | COROUTINES深度变异Any#83 | `val x:Any=...;val d=async{calc()};d.await()` | 确保所有async都被await |
| KT-1001 | 🟡 | COROUTINES深度变异List<String>#84 | `val x:List<String>=...;coroutineScope{launch{throw E()};laun` | supervisorScope |
| KT-1002 | 🟡 | COROUTINES深度变异Int#85 | `val x:Int=...;flow{emit(...)}.collect{slow()}` | buffer或conflate |
| KT-1003 | ⚪ | COROUTINES深度变异Short#86 | `val x:Short=...;val d=async{val x=y;x}` | 直接赋值 |
| KT-1004 | ⚪ | COROUTINES深度变异Any?#87 | `val x:Any?=...;withContext(Dispatchers.Main){` | 依赖注入dispatcher |
| KT-1005 | 🔴 | COROUTINES深度变异String#88 | `val x:String=...;supervisorScope{launch{heavy()}}` | coroutineScope |
| KT-1006 | 🔴 | COROUTINES深度变异Byte#89 | `val x:Byte=...;withContext(empty){delay(1)}` | 保留原始context |
| KT-1007 | 🟡 | COROUTINES深度变异Boolean?#90 | `val x:Boolean?=...;select{ch.onReceive{}` | 加onAwait |
| KT-1008 | 🟡 | COROUTINES深度变异Sequence<Long>#91 | `val x:Sequence<Long>=...;callbackFlow{register(cb);awaitClos` | 加awaitClose |
| KT-1009 | 🟡 | COROUTINES深度变异Char#92 | `val x:Char=...;state.value=state.value.copy(x=1)` | MutableStateFlow.update{} |
| KT-1010 | 🟡 | COROUTINES深度变异Double?#93 | `val x:Double?=...;launch{if(x)return}` | return@launch |
| KT-1011 | ⚪ | COROUTINES深度变异Array<Boolean>#94 | `val x:Array<Boolean>=...;val x=async(IO){work()}.await()` | 直接用withContext |
| KT-1012 | ⚪ | COROUTINES深度变异Float#95 | `val x:Float=...;flow{emit(1)}.flowOn(IO).flowOn(Default)` | 只保留最后一个 |
| KT-1013 | 🔴 | COROUTINES深度变异Long?#96 | `val c=Channel<Long?>();launch{c.consumeEach{}}` | c.close或produceIn |
| KT-1014 | 🟡 | COROUTINES深度变异MutableList<Double>#97 | `val x:MutableList<Double>=...;launch(Unconfined){updateUI()}` | 显式指定调度器 |
| KT-1015 | 🟡 | COROUTINES深度变异Boolean#98 | `val x:Boolean=...;所有@ProcessBody都被Condition拦截` | 放宽condition或加保底 |
| KT-1016 | 🔴 | COROUTINES深度变异Int?#99 | `val x:Int?=...;delay(0);delay(0);delay(0)` | 用yield() |
| KT-1017 | 🟡 | COROUTINES深度变异Map<String,Int>#100 | `val x:Map<String,Int>=...;val d=CompletableDeferred<T>();lau` | 加超时withTimeout |
| KT-1018 | 🔴 | COROUTINES深度变异Double#101 | `val x:Double=...;mutex.lock();mutex.lock()` | withLock或可重入锁 |
| KT-1019 | 🟡 | COROUTINES深度变异String?#102 | `val x:String?=...;flow{emit(1);awaitClose{emit(2)}}` | onCompletion |
| KT-1020 | 🔴 | COROUTINES深度变异Set<Int>#103 | `val x:Set<Int>=...;launch{val m=cls.getDeclaredMethod(\"secr` | 提供公开suspend接口 |
| KT-1021 | 🟡 | COROUTINES深度变异Long#104 | `val x:Long=...;flow{apiCall()}.retry(3).stateIn(scope)` | 在retry外层stateIn |
| KT-1022 | 🔴 | COROUTINES深度变异Any#105 | `val x:Any=...;flow{emit(a);emit(b)}.collect{}` | 要并行用channelFlow或flatMapMerge |
| KT-1023 | 🟡 | COROUTINES深度变异List<String>#106 | `val c=Channel<List<String><String>>();launch{c.send(1)};laun` | BroadcastChannel或SharedFlow |
| KT-1024 | 🟡 | COROUTINES深度变异Int#107 | `val x:Int=...;val c=Channel<Long>();launch{c.send(1)};launch` | BroadcastChannel或SharedFlow |
| KT-1025 | 🔴 | COROUTINES深度变异Short#108 | `val x:Short=...;val c=Channel<Long>();launch{c.consumeEach{}` | c.close或produceIn |
| KT-1026 | 🔴 | COROUTINES深度变异Any?#109 | `val x:Any?=...;val c=Channel<Double>();launch{c.consumeEach{` | c.close或produceIn |
| KT-1027 | 🔴 | COROUTINES深度变异String#110 | `val x:String=...;val c=Channel<Float>();launch{c.consumeEach` | c.close或produceIn |
| KT-1028 | 🟡 | COROUTINES深度变异Byte#111 | `val x:Byte=...;suspend fun api(){httpClient.get(...)}` | withTimeout |
| KT-1029 | 🟡 | COROUTINES深度变异Boolean?#112 | `val x:Boolean?=...;flow{emit(risky())}.catch{e->...}` | catch在collect之前 |
| KT-1030 | 🟡 | COROUTINES深度变异Sequence<Long>#113 | `val x:Sequence<Long>=...;val s=MutableStateFlow(init);s.valu` | distinctUntilChanged或检查值 |
| KT-1031 | 🔴 | COROUTINES深度变异Char#114 | `val x:Char=...;GlobalScope.launch{` | lifecycleScope或viewModelScope |
| KT-1032 | 🔴 | COROUTINES深度变异Double?#115 | `val x:Double?=...;launch{riskyOp()}` | async+await或CoroutineExceptionHandler |
| KT-1033 | 🔴 | COROUTINES深度变异Array<Boolean>#116 | `val x:Array<Boolean>=...;runBlocking{delay(5000)}` | lifecycleScope.launch |
| KT-1034 | 🔴 | COROUTINES深度变异Float#117 | `val x:Float=...;suspend fun f(){Thread.sleep(1000)}` | delay(1000) |
| KT-1035 | 🔴 | COROUTINES深度变异Long?#118 | `val x:Long?=...;launch{while(true){work()}}` | while(isActive) |
| KT-1036 | 🟡 | COROUTINES深度变异MutableList<Double>#119 | `val x:MutableList<Double>=...;launch{throw E()}` | val handler=CoroutineExceptionHandler{ |
| KT-1037 | 🟡 | COROUTINES深度变异Boolean#120 | `val x:Boolean=...;withContext(Dispatchers.IO){lightOp()}` | 用Default或直接执行 |
| KT-1038 | 🟡 | COROUTINES深度变异Int?#121 | `val x:Int?=...;val d=async{calc()};d.await()` | 确保所有async都被await |
| KT-1039 | 🟡 | COROUTINES深度变异Map<String,Int>#122 | `val x:Map<String,Int>=...;coroutineScope{launch{throw E()};l` | supervisorScope |
| KT-1040 | 🟡 | COROUTINES深度变异Double#123 | `val x:Double=...;flow{emit(...)}.collect{slow()}` | buffer或conflate |
| KT-1041 | ⚪ | COROUTINES深度变异String?#124 | `val x:String?=...;val d=async{val x=y;x}` | 直接赋值 |
| KT-1042 | ⚪ | COROUTINES深度变异Set<Int>#125 | `val x:Set<Int>=...;withContext(Dispatchers.Main){` | 依赖注入dispatcher |
| KT-1043 | 🔴 | COROUTINES深度变异Long#126 | `val x:Long=...;supervisorScope{launch{heavy()}}` | coroutineScope |
| KT-1044 | 🔴 | COROUTINES深度变异Any#127 | `val x:Any=...;withContext(empty){delay(1)}` | 保留原始context |
| KT-1045 | 🟡 | COROUTINES深度变异List<String>#128 | `val x:List<String>=...;select{ch.onReceive{}` | 加onAwait |
| KT-1046 | 🟡 | COROUTINES深度变异Int#129 | `val x:Int=...;callbackFlow{register(cb);awaitClose{unregiste` | 加awaitClose |
| KT-1047 | 🟡 | COROUTINES深度变异Short#130 | `val x:Short=...;state.value=state.value.copy(x=1)` | MutableStateFlow.update{} |
| KT-1048 | 🟡 | COROUTINES深度变异Any?#131 | `val x:Any?=...;launch{if(x)return}` | return@launch |
| KT-1049 | ⚪ | COROUTINES深度变异String#132 | `val x:String=...;val x=async(IO){work()}.await()` | 直接用withContext |
| KT-1050 | ⚪ | COROUTINES深度变异Byte#133 | `val x:Byte=...;flow{emit(1)}.flowOn(IO).flowOn(Default)` | 只保留最后一个 |
| KT-1051 | 🔴 | COROUTINES深度变异Boolean?#134 | `val c=Channel<Boolean?>();launch{c.consumeEach{}}` | c.close或produceIn |
| KT-1052 | 🟡 | COROUTINES深度变异Sequence<Long>#135 | `val x:Sequence<Long>=...;launch(Unconfined){updateUI()}` | 显式指定调度器 |
| KT-1053 | 🟡 | COROUTINES深度变异Char#136 | `val x:Char=...;所有@ProcessBody都被Condition拦截` | 放宽condition或加保底 |
| KT-1054 | 🔴 | COROUTINES深度变异Double?#137 | `val x:Double?=...;delay(0);delay(0);delay(0)` | 用yield() |
| KT-1055 | 🟡 | COROUTINES深度变异Array<Boolean>#138 | `val x:Array<Boolean>=...;val d=CompletableDeferred<T>();laun` | 加超时withTimeout |
| KT-1056 | 🔴 | COROUTINES深度变异Float#139 | `val x:Float=...;mutex.lock();mutex.lock()` | withLock或可重入锁 |
| KT-1057 | 🟡 | COROUTINES深度变异Long?#140 | `val x:Long?=...;flow{emit(1);awaitClose{emit(2)}}` | onCompletion |
| KT-1058 | 🔴 | COROUTINES深度变异MutableList<Double>#141 | `val x:MutableList<Double>=...;launch{val m=cls.getDeclaredMe` | 提供公开suspend接口 |
| KT-1059 | 🟡 | COROUTINES深度变异Boolean#142 | `val x:Boolean=...;flow{apiCall()}.retry(3).stateIn(scope)` | 在retry外层stateIn |
| KT-1060 | 🔴 | COROUTINES深度变异Int?#143 | `val x:Int?=...;flow{emit(a);emit(b)}.collect{}` | 要并行用channelFlow或flatMapMerge |
| KT-1061 | 🟡 | COROUTINES深度变异Map<String,Int>#144 | `val c=Channel<Map<String,Int>>();launch{c.send(1)};launch{pr` | BroadcastChannel或SharedFlow |
| KT-1062 | 🟡 | COROUTINES深度变异Double#145 | `val x:Double=...;val c=Channel<Long>();launch{c.send(1)};lau` | BroadcastChannel或SharedFlow |
| KT-1063 | 🔴 | COROUTINES深度变异String?#146 | `val x:String?=...;val c=Channel<Long>();launch{c.consumeEach` | c.close或produceIn |
| KT-1064 | 🔴 | COROUTINES深度变异Set<Int>#147 | `val x:Set<Int>=...;val c=Channel<Double>();launch{c.consumeE` | c.close或produceIn |
| KT-1065 | 🔴 | COROUTINES深度变异Long#148 | `val x:Long=...;val c=Channel<Float>();launch{c.consumeEach{}` | c.close或produceIn |
| KT-1066 | 🟡 | COROUTINES深度变异Any#149 | `val x:Any=...;suspend fun api(){httpClient.get(...)}` | withTimeout |
| KT-1067 | 🟡 | COROUTINES深度变异List<String>#150 | `val x:List<String>=...;flow{emit(risky())}.catch{e->...}` | catch在collect之前 |
| KT-1068 | 🟡 | COROUTINES深度变异Int#151 | `val x:Int=...;val s=MutableStateFlow(init);s.value=init` | distinctUntilChanged或检查值 |
| KT-1069 | 🔴 | COROUTINES深度变异Short#152 | `val x:Short=...;GlobalScope.launch{` | lifecycleScope或viewModelScope |
| KT-1070 | 🔴 | COROUTINES深度变异Any?#153 | `val x:Any?=...;launch{riskyOp()}` | async+await或CoroutineExceptionHandler |
| KT-1071 | 🔴 | COROUTINES深度变异String#154 | `val x:String=...;runBlocking{delay(5000)}` | lifecycleScope.launch |
| KT-1072 | 🔴 | COROUTINES深度变异Byte#155 | `val x:Byte=...;suspend fun f(){Thread.sleep(1000)}` | delay(1000) |
| KT-1073 | 🔴 | COROUTINES深度变异Boolean?#156 | `val x:Boolean?=...;launch{while(true){work()}}` | while(isActive) |
| KT-1074 | 🟡 | COROUTINES深度变异Sequence<Long>#157 | `val x:Sequence<Long>=...;launch{throw E()}` | val handler=CoroutineExceptionHandler{ |
| KT-1075 | 🟡 | COROUTINES深度变异Char#158 | `val x:Char=...;withContext(Dispatchers.IO){lightOp()}` | 用Default或直接执行 |
| KT-1076 | 🟡 | COROUTINES深度变异Double?#159 | `val x:Double?=...;val d=async{calc()};d.await()` | 确保所有async都被await |
| KT-1077 | 🟡 | COROUTINES深度变异Array<Boolean>#160 | `val x:Array<Boolean>=...;coroutineScope{launch{throw E()};la` | supervisorScope |
| KT-1078 | 🟡 | COROUTINES深度变异Float#161 | `val x:Float=...;flow{emit(...)}.collect{slow()}` | buffer或conflate |
| KT-1079 | ⚪ | COROUTINES深度变异Long?#162 | `val x:Long?=...;val d=async{val x=y;x}` | 直接赋值 |
| KT-1080 | ⚪ | COROUTINES深度变异MutableList<Double>#163 | `val x:MutableList<Double>=...;withContext(Dispatchers.Main){` | 依赖注入dispatcher |
| KT-1081 | 🔴 | COROUTINES深度变异Boolean#164 | `val x:Boolean=...;supervisorScope{launch{heavy()}}` | coroutineScope |
| KT-1082 | 🔴 | COROUTINES深度变异Int?#165 | `val x:Int?=...;withContext(empty){delay(1)}` | 保留原始context |
| KT-1083 | 🟡 | COROUTINES深度变异Map<String,Int>#166 | `val x:Map<String,Int>=...;select{ch.onReceive{}` | 加onAwait |
| KT-1084 | 🟡 | COROUTINES深度变异Double#167 | `val x:Double=...;callbackFlow{register(cb);awaitClose{unregi` | 加awaitClose |
| KT-1085 | 🟡 | COROUTINES深度变异String?#168 | `val x:String?=...;state.value=state.value.copy(x=1)` | MutableStateFlow.update{} |
| KT-1086 | 🟡 | COROUTINES深度变异Set<Int>#169 | `val x:Set<Int>=...;launch{if(x)return}` | return@launch |
| KT-1087 | ⚪ | COROUTINES深度变异Long#170 | `val x:Long=...;val x=async(IO){work()}.await()` | 直接用withContext |
| KT-1088 | ⚪ | COROUTINES深度变异Any#171 | `val x:Any=...;flow{emit(1)}.flowOn(IO).flowOn(Default)` | 只保留最后一个 |
| KT-1089 | 🔴 | COROUTINES深度变异List<String>#172 | `val c=Channel<List<String><String>>();launch{c.consumeEach{}` | c.close或produceIn |
| KT-1090 | 🟡 | COROUTINES深度变异Int#173 | `val x:Int=...;launch(Unconfined){updateUI()}` | 显式指定调度器 |
| KT-1091 | 🟡 | COROUTINES深度变异Short#174 | `val x:Short=...;所有@ProcessBody都被Condition拦截` | 放宽condition或加保底 |
| KT-1092 | 🔴 | COROUTINES深度变异Any?#175 | `val x:Any?=...;delay(0);delay(0);delay(0)` | 用yield() |
| KT-1093 | 🟡 | COROUTINES深度变异String#176 | `val x:String=...;val d=CompletableDeferred<T>();launch{...d.` | 加超时withTimeout |
| KT-1094 | 🔴 | COROUTINES深度变异Byte#177 | `val x:Byte=...;mutex.lock();mutex.lock()` | withLock或可重入锁 |
| KT-1095 | 🟡 | COROUTINES深度变异Boolean?#178 | `val x:Boolean?=...;flow{emit(1);awaitClose{emit(2)}}` | onCompletion |
| KT-1096 | 🔴 | COROUTINES深度变异Sequence<Long>#179 | `val x:Sequence<Long>=...;launch{val m=cls.getDeclaredMethod(` | 提供公开suspend接口 |
| KT-1097 | 🟡 | COROUTINES深度变异Char#180 | `val x:Char=...;flow{apiCall()}.retry(3).stateIn(scope)` | 在retry外层stateIn |
| KT-1098 | 🔴 | COROUTINES深度变异Double?#181 | `val x:Double?=...;flow{emit(a);emit(b)}.collect{}` | 要并行用channelFlow或flatMapMerge |
| KT-1099 | 🟡 | COROUTINES深度变异Array<Boolean>#182 | `val c=Channel<Array<Boolean>>();launch{c.send(1)};launch{pri` | BroadcastChannel或SharedFlow |
| KT-1100 | 🟡 | COROUTINES深度变异Float#183 | `val x:Float=...;val c=Channel<Long>();launch{c.send(1)};laun` | BroadcastChannel或SharedFlow |
| KT-1101 | 🔴 | COROUTINES深度变异Long?#184 | `val x:Long?=...;val c=Channel<Long>();launch{c.consumeEach{}` | c.close或produceIn |
| KT-1102 | 🔴 | COROUTINES深度变异MutableList<Double>#185 | `val x:MutableList<Double>=...;val c=Channel<Double>();launch` | c.close或produceIn |
| KT-1103 | 🔴 | COROUTINES深度变异Boolean#186 | `val x:Boolean=...;val c=Channel<Float>();launch{c.consumeEac` | c.close或produceIn |
| KT-1104 | 🟡 | COROUTINES深度变异Int?#187 | `val x:Int?=...;suspend fun api(){httpClient.get(...)}` | withTimeout |
| KT-1105 | 🟡 | COROUTINES深度变异Map<String,Int>#188 | `val x:Map<String,Int>=...;flow{emit(risky())}.catch{e->...}` | catch在collect之前 |
| KT-1106 | 🟡 | COROUTINES深度变异Double#189 | `val x:Double=...;val s=MutableStateFlow(init);s.value=init` | distinctUntilChanged或检查值 |
| KT-1107 | 🔴 | COROUTINES深度变异String?#190 | `val x:String?=...;GlobalScope.launch{` | lifecycleScope或viewModelScope |
| KT-1108 | 🔴 | COROUTINES深度变异Set<Int>#191 | `val x:Set<Int>=...;launch{riskyOp()}` | async+await或CoroutineExceptionHandler |
| KT-1109 | 🔴 | COROUTINES深度变异Long#192 | `val x:Long=...;runBlocking{delay(5000)}` | lifecycleScope.launch |
| KT-1110 | 🔴 | COROUTINES深度变异Any#193 | `val x:Any=...;suspend fun f(){Thread.sleep(1000)}` | delay(1000) |
| KT-1111 | 🔴 | COROUTINES深度变异List<String>#194 | `val x:List<String>=...;launch{while(true){work()}}` | while(isActive) |
| KT-1112 | 🟡 | COROUTINES深度变异Int#195 | `val x:Int=...;launch{throw E()}` | val handler=CoroutineExceptionHandler{ |
| KT-1113 | 🟡 | COROUTINES深度变异Short#196 | `val x:Short=...;withContext(Dispatchers.IO){lightOp()}` | 用Default或直接执行 |
| KT-1114 | 🟡 | COROUTINES深度变异Any?#197 | `val x:Any?=...;val d=async{calc()};d.await()` | 确保所有async都被await |
| KT-1115 | 🟡 | COROUTINES深度变异String#198 | `val x:String=...;coroutineScope{launch{throw E()};launch{}` | supervisorScope |
| KT-1116 | 🟡 | COROUTINES深度变异Byte#199 | `val x:Byte=...;flow{emit(...)}.collect{slow()}` | buffer或conflate |
| KT-1117 | ⚪ | COROUTINES深度变异Boolean?#200 | `val x:Boolean?=...;val d=async{val x=y;x}` | 直接赋值 |
| KT-1118 | ⚪ | COROUTINES深度变异Sequence<Long>#201 | `val x:Sequence<Long>=...;withContext(Dispatchers.Main){` | 依赖注入dispatcher |
| KT-1119 | 🔴 | COROUTINES深度变异Char#202 | `val x:Char=...;supervisorScope{launch{heavy()}}` | coroutineScope |
| KT-1120 | 🔴 | COROUTINES深度变异Double?#203 | `val x:Double?=...;withContext(empty){delay(1)}` | 保留原始context |
| KT-1121 | 🟡 | COROUTINES深度变异Array<Boolean>#204 | `val x:Array<Boolean>=...;select{ch.onReceive{}` | 加onAwait |
| KT-1122 | 🟡 | COROUTINES深度变异Float#205 | `val x:Float=...;callbackFlow{register(cb);awaitClose{unregis` | 加awaitClose |
| KT-1123 | 🟡 | COROUTINES深度变异Long?#206 | `val x:Long?=...;state.value=state.value.copy(x=1)` | MutableStateFlow.update{} |
| KT-1124 | 🟡 | COROUTINES深度变异MutableList<Double>#207 | `val x:MutableList<Double>=...;launch{if(x)return}` | return@launch |
| KT-1125 | ⚪ | COROUTINES深度变异Boolean#208 | `val x:Boolean=...;val x=async(IO){work()}.await()` | 直接用withContext |
| KT-1126 | ⚪ | COROUTINES深度变异Int?#209 | `val x:Int?=...;flow{emit(1)}.flowOn(IO).flowOn(Default)` | 只保留最后一个 |
| KT-1127 | 🔴 | COROUTINES深度变异Map<String,Int>#210 | `val c=Channel<Map<String,Int>>();launch{c.consumeEach{}}` | c.close或produceIn |
| KT-1128 | 🟡 | COROUTINES深度变异Double#211 | `val x:Double=...;launch(Unconfined){updateUI()}` | 显式指定调度器 |
| KT-1129 | 🟡 | COROUTINES深度变异String?#212 | `val x:String?=...;所有@ProcessBody都被Condition拦截` | 放宽condition或加保底 |
| KT-1130 | 🔴 | COROUTINES深度变异Set<Int>#213 | `val x:Set<Int>=...;delay(0);delay(0);delay(0)` | 用yield() |
| KT-1131 | 🟡 | COROUTINES深度变异Long#214 | `val x:Long=...;val d=CompletableDeferred<T>();launch{...d.aw` | 加超时withTimeout |
| KT-1132 | 🔴 | COROUTINES深度变异Any#215 | `val x:Any=...;mutex.lock();mutex.lock()` | withLock或可重入锁 |
| KT-1133 | 🟡 | COROUTINES深度变异List<String>#216 | `val x:List<String>=...;flow{emit(1);awaitClose{emit(2)}}` | onCompletion |
| KT-1134 | 🔴 | COROUTINES深度变异Int#217 | `val x:Int=...;launch{val m=cls.getDeclaredMethod(\"secret\")` | 提供公开suspend接口 |
| KT-1135 | 🟡 | COROUTINES深度变异Short#218 | `val x:Short=...;flow{apiCall()}.retry(3).stateIn(scope)` | 在retry外层stateIn |
| KT-1136 | 🔴 | COROUTINES深度变异Any?#219 | `val x:Any?=...;flow{emit(a);emit(b)}.collect{}` | 要并行用channelFlow或flatMapMerge |
| KT-1137 | 🟡 | COROUTINES深度变异String#220 | `val c=Channel<String>();launch{c.send(1)};launch{println(c.r` | BroadcastChannel或SharedFlow |
| KT-1138 | 🟡 | COROUTINES深度变异Byte#221 | `val x:Byte=...;val c=Channel<Long>();launch{c.send(1)};launc` | BroadcastChannel或SharedFlow |
| KT-1139 | 🔴 | COROUTINES深度变异Boolean?#222 | `val x:Boolean?=...;val c=Channel<Long>();launch{c.consumeEac` | c.close或produceIn |
| KT-1140 | 🔴 | COROUTINES深度变异Sequence<Long>#223 | `val x:Sequence<Long>=...;val c=Channel<Double>();launch{c.co` | c.close或produceIn |
| KT-1141 | 🔴 | COROUTINES深度变异Char#224 | `val x:Char=...;val c=Channel<Float>();launch{c.consumeEach{}` | c.close或produceIn |
| KT-1142 | 🟡 | COROUTINES深度变异Double?#225 | `val x:Double?=...;suspend fun api(){httpClient.get(...)}` | withTimeout |
| KT-1143 | 🟡 | COROUTINES深度变异Array<Boolean>#226 | `val x:Array<Boolean>=...;flow{emit(risky())}.catch{e->...}` | catch在collect之前 |
| KT-1144 | 🟡 | COROUTINES深度变异Float#227 | `val x:Float=...;val s=MutableStateFlow(init);s.value=init` | distinctUntilChanged或检查值 |
| KT-1145 | 🔴 | COROUTINES深度变异Long?#228 | `val x:Long?=...;GlobalScope.launch{` | lifecycleScope或viewModelScope |
| KT-1146 | 🔴 | COROUTINES深度变异MutableList<Double>#229 | `val x:MutableList<Double>=...;launch{riskyOp()}` | async+await或CoroutineExceptionHandler |
| KT-1147 | 🔴 | COROUTINES深度变异Boolean#230 | `val x:Boolean=...;runBlocking{delay(5000)}` | lifecycleScope.launch |
| KT-1148 | 🔴 | COROUTINES深度变异Int?#231 | `val x:Int?=...;suspend fun f(){Thread.sleep(1000)}` | delay(1000) |
| KT-1149 | 🔴 | COROUTINES深度变异Map<String,Int>#232 | `val x:Map<String,Int>=...;launch{while(true){work()}}` | while(isActive) |
| KT-1150 | 🟡 | COROUTINES深度变异Double#233 | `val x:Double=...;launch{throw E()}` | val handler=CoroutineExceptionHandler{ |
| KT-1151 | 🟡 | COROUTINES深度变异String?#234 | `val x:String?=...;withContext(Dispatchers.IO){lightOp()}` | 用Default或直接执行 |
| KT-1152 | 🟡 | COROUTINES深度变异Set<Int>#235 | `val x:Set<Int>=...;val d=async{calc()};d.await()` | 确保所有async都被await |
| KT-1153 | 🟡 | COROUTINES深度变异Long#236 | `val x:Long=...;coroutineScope{launch{throw E()};launch{}` | supervisorScope |
| KT-1154 | 🟡 | COROUTINES深度变异Any#237 | `val x:Any=...;flow{emit(...)}.collect{slow()}` | buffer或conflate |
| KT-1155 | ⚪ | COROUTINES深度变异List<String>#238 | `val x:List<String>=...;val d=async{val x=y;x}` | 直接赋值 |
| KT-1156 | ⚪ | COROUTINES深度变异Int#239 | `val x:Int=...;withContext(Dispatchers.Main){` | 依赖注入dispatcher |
| KT-1157 | 🔴 | COROUTINES深度变异Short#240 | `val x:Short=...;supervisorScope{launch{heavy()}}` | coroutineScope |
| KT-1158 | 🔴 | COROUTINES深度变异Any?#241 | `val x:Any?=...;withContext(empty){delay(1)}` | 保留原始context |
| KT-1159 | 🟡 | COROUTINES深度变异String#242 | `val x:String=...;select{ch.onReceive{}` | 加onAwait |
| KT-1160 | 🟡 | COROUTINES深度变异Byte#243 | `val x:Byte=...;callbackFlow{register(cb);awaitClose{unregist` | 加awaitClose |
| KT-1161 | 🟡 | COROUTINES深度变异Boolean?#244 | `val x:Boolean?=...;state.value=state.value.copy(x=1)` | MutableStateFlow.update{} |
| KT-1162 | 🟡 | COROUTINES深度变异Sequence<Long>#245 | `val x:Sequence<Long>=...;launch{if(x)return}` | return@launch |
| KT-1163 | ⚪ | COROUTINES深度变异Char#246 | `val x:Char=...;val x=async(IO){work()}.await()` | 直接用withContext |
| KT-1164 | ⚪ | COROUTINES深度变异Double?#247 | `val x:Double?=...;flow{emit(1)}.flowOn(IO).flowOn(Default)` | 只保留最后一个 |
| KT-1165 | 🔴 | COROUTINES深度变异Array<Boolean>#248 | `val c=Channel<Array<Boolean>>();launch{c.consumeEach{}}` | c.close或produceIn |
| KT-1166 | 🟡 | COROUTINES深度变异Float#249 | `val x:Float=...;launch(Unconfined){updateUI()}` | 显式指定调度器 |
| KT-1167 | 🟡 | COROUTINES深度变异Long?#250 | `val x:Long?=...;所有@ProcessBody都被Condition拦截` | 放宽condition或加保底 |
| KT-1168 | 🔴 | COROUTINES深度变异MutableList<Double>#251 | `val x:MutableList<Double>=...;delay(0);delay(0);delay(0)` | 用yield() |
| KT-1169 | 🟡 | COROUTINES深度变异Boolean#252 | `val x:Boolean=...;val d=CompletableDeferred<T>();launch{...d` | 加超时withTimeout |
| KT-1170 | 🔴 | COROUTINES深度变异Int?#253 | `val x:Int?=...;mutex.lock();mutex.lock()` | withLock或可重入锁 |
| KT-1171 | 🟡 | COROUTINES深度变异Map<String,Int>#254 | `val x:Map<String,Int>=...;flow{emit(1);awaitClose{emit(2)}}` | onCompletion |
| KT-1172 | 🔴 | COROUTINES深度变异Double#255 | `val x:Double=...;launch{val m=cls.getDeclaredMethod(\"secret` | 提供公开suspend接口 |
| KT-1173 | 🟡 | COROUTINES深度变异String?#256 | `val x:String?=...;flow{apiCall()}.retry(3).stateIn(scope)` | 在retry外层stateIn |
| KT-1174 | 🔴 | COROUTINES深度变异Set<Int>#257 | `val x:Set<Int>=...;flow{emit(a);emit(b)}.collect{}` | 要并行用channelFlow或flatMapMerge |
| KT-1175 | 🟡 | COROUTINES深度变异Long#258 | `val c=Channel<Long>();launch{c.send(1)};launch{println(c.rec` | BroadcastChannel或SharedFlow |
| KT-1176 | 🟡 | COROUTINES深度变异Any#259 | `val x:Any=...;val c=Channel<Long>();launch{c.send(1)};launch` | BroadcastChannel或SharedFlow |
| KT-1177 | 🔴 | COROUTINES深度变异List<String>#260 | `val x:List<String>=...;val c=Channel<Long>();launch{c.consum` | c.close或produceIn |
| KT-1178 | 🔴 | COROUTINES深度变异Int#261 | `val x:Int=...;val c=Channel<Double>();launch{c.consumeEach{}` | c.close或produceIn |

## DATA_SERIAL（150条）

| ID | 严重度 | Bug描述 | 触发模式 | 修复建议 |
|---|------|------|------|------|
| KT-0069 | 🔴 | 循环引用序列化 | `A(val b:B);B(val a:A)` | @Transient打破循环 |
| KT-0070 | 🟡 | data class copy浅复制 | `data class U(val l:MutableList<T>);u.copy().l.add(x)` | 深复制或不可变 |
| KT-0071 | 🟡 | 默认值在序列化中丢失 | `data class U(val x:Int=0);json无x字段` | 显式标注默认值 |
| KT-0072 | 🟡 | @SerialName与字段名不一致 | `@SerialName("y") val x:Int` | 统一命名 |
| KT-0073 | ⚪ | 不必要的@Serializable | `data class Internal(val x:Int)` | 按需标注 |
| KT-0192 | 🔴 | Kotlinx序列化循环引用 | `@Serializable data class A(val b:B);@Serializable data class` | @Transient打断 |
| KT-0193 | 🟡 | data class componentN()命名冲突 | `data class C(val component1:String,val x:Int)` | 避开componentN命名 |
| KT-0194 | 🟡 | toString()无限递归 | `data class N(val parent:N?);N(N(N(...)))` | 手动实现toString |
| KT-0195 | 🟡 | Parcelable序列化顺序不一致 | `writeInt(a);readInt(b)` | 对齐write/read顺序 |
| KT-0196 | ⚪ | data class equals中引用比较 | `val a=Obj(1);val b=Obj(1);a==b` | 重写equals+hashCode |
| KT-0275 | 🟡 | Parcelable读写顺序故意相反 | `writeInt(a);writeString(b);readString();readInt()` | 写读顺序严格一致 |
| KT-0299 | 🟡 | @Serializable+by lazy=序列化时触发初始化 | `@Serializable data class U(val x:Int){val y by lazy{init()}}` | @Transient标记非序列化字段 |
| KT-0336 | 🟡 | @SerialName与字段名不一致（Long版） | `@SerialName(\"y\") val x:Long` | 统一命名 |
| KT-0337 | 🟡 | @SerialName与字段名不一致（Double版） | `@SerialName(\"y\") val x:Double` | 统一命名 |
| KT-0338 | 🟡 | @SerialName与字段名不一致（Float版） | `@SerialName(\"y\") val x:Float` | 统一命名 |
| KT-0351 | 🟡 | Parcelable读写顺序故意相反（Int版） | `writeInt(a);writeInt(b);readInt();readInt()` | 写读顺序严格一致 |
| KT-0352 | 🟡 | Parcelable读写顺序故意相反（Long版） | `writeInt(a);writeLong(b);readLong();readInt()` | 写读顺序严格一致 |
| KT-0353 | 🟡 | Parcelable读写顺序故意相反（Double版） | `writeInt(a);writeDouble(b);readDouble();readInt()` | 写读顺序严格一致 |
| KT-0354 | 🟡 | Parcelable读写顺序故意相反（Boolean版） | `writeInt(a);writeBoolean(b);readBoolean();readInt()` | 写读顺序严格一致 |
| KT-0355 | 🟡 | Parcelable读写顺序故意相反（Long版） | `writeLong(a);writeString(b);readString();readLong()` | 写读顺序严格一致 |
| KT-0356 | 🟡 | Parcelable读写顺序故意相反（Double版） | `writeDouble(a);writeString(b);readString();readDouble()` | 写读顺序严格一致 |
| KT-0412 | 🟡 | 默认值在序列化中丢失（Long版） | `data class U(val x:Long=0);json无x字段` | 显式标注默认值 |
| KT-0413 | 🟡 | 默认值在序列化中丢失（Double版） | `data class U(val x:Double=0);json无x字段` | 显式标注默认值 |
| KT-0481 | 🟡 | Gson默认忽略transient | `@Transient val x:Int;Gson仍序列化` | @Expose(false) |
| KT-1576 | 🔴 | DATA_SERIAL深度变异String#0 | `val x:String=...;A(val b:B);B(val a:A)` | @Transient打破循环 |
| KT-1577 | 🟡 | DATA_SERIAL深度变异Byte#1 | `data class U(val l:MutableByte<T>);u.copy().l.add(x)` | 深复制或不可变 |
| KT-1578 | 🟡 | DATA_SERIAL深度变异Boolean?#2 | `data class U(val x:Boolean?=0);json无x字段` | 显式标注默认值 |
| KT-1579 | 🟡 | DATA_SERIAL深度变异Sequence<Long>#3 | `@SerialName(\"y\") val x:Sequence<Long>` | 统一命名 |
| KT-1580 | ⚪ | DATA_SERIAL深度变异Char#4 | `data class Charernal(val x:Char)` | 按需标注 |
| KT-1581 | 🔴 | DATA_SERIAL深度变异Double?#5 | `val x:Double?=...;@Serializable data class A(val b:B);@Seria` | @Transient打断 |
| KT-1582 | 🟡 | DATA_SERIAL深度变异Array<Boolean>#6 | `data class C(val component1:Array<Boolean>,val x:Array<Boole` | 避开componentN命名 |
| KT-1583 | 🟡 | DATA_SERIAL深度变异Float#7 | `val x:Float=...;data class N(val parent:N?);N(N(N(...)))` | 手动实现toString |
| KT-1584 | 🟡 | DATA_SERIAL深度变异Long?#8 | `writeLong?(a);readLong?(b)` | 对齐write/read顺序 |
| KT-1585 | ⚪ | DATA_SERIAL深度变异MutableList<Double>#9 | `val x:MutableList<Double>=...;val a=Obj(1);val b=Obj(1);a==b` | 重写equals+hashCode |
| KT-1586 | 🟡 | DATA_SERIAL深度变异Boolean#10 | `writeBoolean(a);writeBoolean(b);readBoolean();readBoolean()` | 写读顺序严格一致 |
| KT-1587 | 🟡 | DATA_SERIAL深度变异Int?#11 | `@Serializable data class U(val x:Int?){val y by lazy{init()}` | @Transient标记非序列化字段 |
| KT-1588 | 🟡 | DATA_SERIAL深度变异Map<String,Int>#12 | `val x:Map<String,Int>=...;@SerialName(\\\"y\\\") val x:Long` | 统一命名 |
| KT-1589 | 🟡 | DATA_SERIAL深度变异Double#13 | `val x:Double=...;@SerialName(\\\"y\\\") val x:Double` | 统一命名 |
| KT-1590 | 🟡 | DATA_SERIAL深度变异String?#14 | `val x:String?=...;@SerialName(\\\"y\\\") val x:Float` | 统一命名 |
| KT-1591 | 🟡 | DATA_SERIAL深度变异Set<Int>#15 | `writeSet<Int>(a);writeSet<Int>(b);readSet<Int>();readSet<Int` | 写读顺序严格一致 |
| KT-1592 | 🟡 | DATA_SERIAL深度变异Long#16 | `writeLong(a);writeLong(b);readLong();readLong()` | 写读顺序严格一致 |
| KT-1593 | 🟡 | DATA_SERIAL深度变异Any#17 | `writeAny(a);writeDouble(b);readDouble();readAny()` | 写读顺序严格一致 |
| KT-1594 | 🟡 | DATA_SERIAL深度变异List<String>#18 | `writeList<String><String>(a);writeBoolean(b);readBoolean();r` | 写读顺序严格一致 |
| KT-1595 | 🟡 | DATA_SERIAL深度变异Int#19 | `writeLong(a);writeInt(b);readInt();readLong()` | 写读顺序严格一致 |
| KT-1596 | 🟡 | DATA_SERIAL深度变异Short#20 | `writeDouble(a);writeShort(b);readShort();readDouble()` | 写读顺序严格一致 |
| KT-1597 | 🟡 | DATA_SERIAL深度变异Any?#21 | `val x:Any?=...;data class U(val x:Long=0);json无x字段` | 显式标注默认值 |
| KT-1598 | 🟡 | DATA_SERIAL深度变异String#22 | `val x:String=...;data class U(val x:Double=0);json无x字段` | 显式标注默认值 |
| KT-1599 | 🟡 | DATA_SERIAL深度变异Byte#23 | `@Transient val x:Byte;Gson仍序列化` | @Expose(false) |
| KT-1600 | 🔴 | DATA_SERIAL深度变异Boolean?#24 | `val x:Boolean?=...;A(val b:B);B(val a:A)` | @Transient打破循环 |
| KT-1601 | 🟡 | DATA_SERIAL深度变异Sequence<Long>#25 | `data class U(val l:MutableSequence<Long><T>);u.copy().l.add(` | 深复制或不可变 |
| KT-1602 | 🟡 | DATA_SERIAL深度变异Char#26 | `data class U(val x:Char=0);json无x字段` | 显式标注默认值 |
| KT-1603 | 🟡 | DATA_SERIAL深度变异Double?#27 | `@SerialName(\"y\") val x:Double?` | 统一命名 |
| KT-1604 | ⚪ | DATA_SERIAL深度变异Array<Boolean>#28 | `data class Array<Boolean>ernal(val x:Array<Boolean>)` | 按需标注 |
| KT-1605 | 🔴 | DATA_SERIAL深度变异Float#29 | `val x:Float=...;@Serializable data class A(val b:B);@Seriali` | @Transient打断 |
| KT-1606 | 🟡 | DATA_SERIAL深度变异Long?#30 | `data class C(val component1:Long?,val x:Long?)` | 避开componentN命名 |
| KT-1607 | 🟡 | DATA_SERIAL深度变异MutableList<Double>#31 | `val x:MutableList<Double>=...;data class N(val parent:N?);N(` | 手动实现toString |
| KT-1608 | 🟡 | DATA_SERIAL深度变异Boolean#32 | `writeBoolean(a);readBoolean(b)` | 对齐write/read顺序 |
| KT-1609 | ⚪ | DATA_SERIAL深度变异Int?#33 | `val x:Int?=...;val a=Obj(1);val b=Obj(1);a==b` | 重写equals+hashCode |
| KT-1610 | 🟡 | DATA_SERIAL深度变异Map<String,Int>#34 | `writeMap<String,Int>(a);writeMap<String,Map<String,Int>>(b);` | 写读顺序严格一致 |
| KT-1611 | 🟡 | DATA_SERIAL深度变异Double#35 | `@Serializable data class U(val x:Double){val y by lazy{init(` | @Transient标记非序列化字段 |
| KT-1612 | 🟡 | DATA_SERIAL深度变异String?#36 | `val x:String?=...;@SerialName(\\\"y\\\") val x:Long` | 统一命名 |
| KT-1613 | 🟡 | DATA_SERIAL深度变异Set<Int>#37 | `val x:Set<Int>=...;@SerialName(\\\"y\\\") val x:Double` | 统一命名 |
| KT-1614 | 🟡 | DATA_SERIAL深度变异Long#38 | `val x:Long=...;@SerialName(\\\"y\\\") val x:Float` | 统一命名 |
| KT-1615 | 🟡 | DATA_SERIAL深度变异Any#39 | `writeAny(a);writeAny(b);readAny();readAny()` | 写读顺序严格一致 |
| KT-1616 | 🟡 | DATA_SERIAL深度变异List<String>#40 | `writeList<String><String>(a);writeLong(b);readLong();readLis` | 写读顺序严格一致 |
| KT-1617 | 🟡 | DATA_SERIAL深度变异Int#41 | `val x:Int=...;writeInt(a);writeDouble(b);readDouble();readIn` | 写读顺序严格一致 |
| KT-1618 | 🟡 | DATA_SERIAL深度变异Short#42 | `writeShort(a);writeBoolean(b);readBoolean();readShort()` | 写读顺序严格一致 |
| KT-1619 | 🟡 | DATA_SERIAL深度变异Any?#43 | `writeLong(a);writeAny?(b);readAny?();readLong()` | 写读顺序严格一致 |
| KT-1620 | 🟡 | DATA_SERIAL深度变异String#44 | `val x:String=...;writeDouble(a);writeString(b);readString();` | 写读顺序严格一致 |
| KT-1621 | 🟡 | DATA_SERIAL深度变异Byte#45 | `val x:Byte=...;data class U(val x:Long=0);json无x字段` | 显式标注默认值 |
| KT-1622 | 🟡 | DATA_SERIAL深度变异Boolean?#46 | `val x:Boolean?=...;data class U(val x:Double=0);json无x字段` | 显式标注默认值 |
| KT-1623 | 🟡 | DATA_SERIAL深度变异Sequence<Long>#47 | `@Transient val x:Sequence<Long>;Gson仍序列化` | @Expose(false) |
| KT-1624 | 🔴 | DATA_SERIAL深度变异Char#48 | `val x:Char=...;A(val b:B);B(val a:A)` | @Transient打破循环 |
| KT-1625 | 🟡 | DATA_SERIAL深度变异Double?#49 | `data class U(val l:MutableDouble?<T>);u.copy().l.add(x)` | 深复制或不可变 |
| KT-1626 | 🟡 | DATA_SERIAL深度变异Array<Boolean>#50 | `data class U(val x:Array<Boolean>=0);json无x字段` | 显式标注默认值 |
| KT-1627 | 🟡 | DATA_SERIAL深度变异Float#51 | `@SerialName(\"y\") val x:Float` | 统一命名 |
| KT-1628 | ⚪ | DATA_SERIAL深度变异Long?#52 | `data class Long?ernal(val x:Long?)` | 按需标注 |
| KT-1629 | 🔴 | DATA_SERIAL深度变异MutableList<Double>#53 | `val x:MutableList<Double>=...;@Serializable data class A(val` | @Transient打断 |
| KT-1630 | 🟡 | DATA_SERIAL深度变异Boolean#54 | `data class C(val component1:Boolean,val x:Boolean)` | 避开componentN命名 |
| KT-1631 | 🟡 | DATA_SERIAL深度变异Int?#55 | `val x:Int?=...;data class N(val parent:N?);N(N(N(...)))` | 手动实现toString |
| KT-1632 | 🟡 | DATA_SERIAL深度变异Map<String,Int>#56 | `writeMap<String,Int>(a);readMap<String,Int>(b)` | 对齐write/read顺序 |
| KT-1633 | ⚪ | DATA_SERIAL深度变异Double#57 | `val x:Double=...;val a=Obj(1);val b=Obj(1);a==b` | 重写equals+hashCode |
| KT-1634 | 🟡 | DATA_SERIAL深度变异String?#58 | `writeString?(a);writeString?(b);readString?();readString?()` | 写读顺序严格一致 |
| KT-1635 | 🟡 | DATA_SERIAL深度变异Set<Int>#59 | `@Serializable data class U(val x:Set<Int>){val y by lazy{ini` | @Transient标记非序列化字段 |
| KT-1636 | 🟡 | DATA_SERIAL深度变异Long#60 | `val x:Long=...;@SerialName(\\\"y\\\") val x:Long` | 统一命名 |
| KT-1637 | 🟡 | DATA_SERIAL深度变异Any#61 | `val x:Any=...;@SerialName(\\\"y\\\") val x:Double` | 统一命名 |
| KT-1638 | 🟡 | DATA_SERIAL深度变异List<String>#62 | `val x:List<String>=...;@SerialName(\\\"y\\\") val x:Float` | 统一命名 |
| KT-1639 | 🟡 | DATA_SERIAL深度变异Int#63 | `val x:Int=...;writeInt(a);writeInt(b);readInt();readInt()` | 写读顺序严格一致 |
| KT-1640 | 🟡 | DATA_SERIAL深度变异Short#64 | `writeShort(a);writeLong(b);readLong();readShort()` | 写读顺序严格一致 |
| KT-1641 | 🟡 | DATA_SERIAL深度变异Any?#65 | `writeAny?(a);writeDouble(b);readDouble();readAny?()` | 写读顺序严格一致 |
| KT-1642 | 🟡 | DATA_SERIAL深度变异String#66 | `writeString(a);writeBoolean(b);readBoolean();readString()` | 写读顺序严格一致 |
| KT-1643 | 🟡 | DATA_SERIAL深度变异Byte#67 | `writeLong(a);writeByte(b);readByte();readLong()` | 写读顺序严格一致 |
| KT-1644 | 🟡 | DATA_SERIAL深度变异Boolean?#68 | `writeDouble(a);writeBoolean?(b);readBoolean?();readDouble()` | 写读顺序严格一致 |
| KT-1645 | 🟡 | DATA_SERIAL深度变异Sequence<Long>#69 | `val x:Sequence<Long>=...;data class U(val x:Long=0);json无x字段` | 显式标注默认值 |
| KT-1646 | 🟡 | DATA_SERIAL深度变异Char#70 | `val x:Char=...;data class U(val x:Double=0);json无x字段` | 显式标注默认值 |
| KT-1647 | 🟡 | DATA_SERIAL深度变异Double?#71 | `@Transient val x:Double?;Gson仍序列化` | @Expose(false) |
| KT-1648 | 🔴 | DATA_SERIAL深度变异Array<Boolean>#72 | `val x:Array<Boolean>=...;A(val b:B);B(val a:A)` | @Transient打破循环 |
| KT-1649 | 🟡 | DATA_SERIAL深度变异Float#73 | `data class U(val l:MutableFloat<T>);u.copy().l.add(x)` | 深复制或不可变 |
| KT-1650 | 🟡 | DATA_SERIAL深度变异Long?#74 | `data class U(val x:Long?=0);json无x字段` | 显式标注默认值 |
| KT-1651 | 🟡 | DATA_SERIAL深度变异MutableList<Double>#75 | `@SerialName(\"y\") val x:MutableMutableList<Double><Double>` | 统一命名 |
| KT-1652 | ⚪ | DATA_SERIAL深度变异Boolean#76 | `data class Booleanernal(val x:Boolean)` | 按需标注 |
| KT-1653 | 🔴 | DATA_SERIAL深度变异Int?#77 | `val x:Int?=...;@Serializable data class A(val b:B);@Serializ` | @Transient打断 |
| KT-1654 | 🟡 | DATA_SERIAL深度变异Map<String,Int>#78 | `data class C(val component1:Map<String,Map<String,Int>>,val ` | 避开componentN命名 |
| KT-1655 | 🟡 | DATA_SERIAL深度变异Double#79 | `val x:Double=...;data class N(val parent:N?);N(N(N(...)))` | 手动实现toString |
| KT-1656 | 🟡 | DATA_SERIAL深度变异String?#80 | `writeString?(a);readString?(b)` | 对齐write/read顺序 |
| KT-1657 | ⚪ | DATA_SERIAL深度变异Set<Int>#81 | `val x:Set<Int>=...;val a=Obj(1);val b=Obj(1);a==b` | 重写equals+hashCode |
| KT-1658 | 🟡 | DATA_SERIAL深度变异Long#82 | `writeLong(a);writeLong(b);readLong();readLong()` | 写读顺序严格一致 |
| KT-1659 | 🟡 | DATA_SERIAL深度变异Any#83 | `@Serializable data class U(val x:Any){val y by lazy{init()}}` | @Transient标记非序列化字段 |
| KT-1660 | 🟡 | DATA_SERIAL深度变异List<String>#84 | `val x:List<String>=...;@SerialName(\\\"y\\\") val x:Long` | 统一命名 |
| KT-1661 | 🟡 | DATA_SERIAL深度变异Int#85 | `val x:Int=...;@SerialName(\\\"y\\\") val x:Double` | 统一命名 |
| KT-1662 | 🟡 | DATA_SERIAL深度变异Short#86 | `val x:Short=...;@SerialName(\\\"y\\\") val x:Float` | 统一命名 |
| KT-1663 | 🟡 | DATA_SERIAL深度变异Any?#87 | `writeAny?(a);writeAny?(b);readAny?();readAny?()` | 写读顺序严格一致 |
| KT-1664 | 🟡 | DATA_SERIAL深度变异String#88 | `writeString(a);writeLong(b);readLong();readString()` | 写读顺序严格一致 |
| KT-1665 | 🟡 | DATA_SERIAL深度变异Byte#89 | `writeByte(a);writeDouble(b);readDouble();readByte()` | 写读顺序严格一致 |
| KT-1666 | 🟡 | DATA_SERIAL深度变异Boolean?#90 | `writeBoolean?(a);writeBoolean(b);readBoolean();readBoolean?(` | 写读顺序严格一致 |
| KT-1667 | 🟡 | DATA_SERIAL深度变异Sequence<Long>#91 | `writeLong(a);writeSequence<Long>(b);readSequence<Long>();rea` | 写读顺序严格一致 |
| KT-1668 | 🟡 | DATA_SERIAL深度变异Char#92 | `writeDouble(a);writeChar(b);readChar();readDouble()` | 写读顺序严格一致 |
| KT-1669 | 🟡 | DATA_SERIAL深度变异Double?#93 | `val x:Double?=...;data class U(val x:Long=0);json无x字段` | 显式标注默认值 |
| KT-1670 | 🟡 | DATA_SERIAL深度变异Array<Boolean>#94 | `val x:Array<Boolean>=...;data class U(val x:Double=0);json无x` | 显式标注默认值 |
| KT-1671 | 🟡 | DATA_SERIAL深度变异Float#95 | `@Transient val x:Float;Gson仍序列化` | @Expose(false) |
| KT-1672 | 🔴 | DATA_SERIAL深度变异Long?#96 | `val x:Long?=...;A(val b:B);B(val a:A)` | @Transient打破循环 |
| KT-1673 | 🟡 | DATA_SERIAL深度变异MutableList<Double>#97 | `data class U(val l:MutableMutableList<Double><T>);u.copy().l` | 深复制或不可变 |
| KT-1674 | 🟡 | DATA_SERIAL深度变异Boolean#98 | `data class U(val x:Boolean=0);json无x字段` | 显式标注默认值 |
| KT-1675 | 🟡 | DATA_SERIAL深度变异Int?#99 | `@SerialName(\"y\") val x:Int?` | 统一命名 |
| KT-1676 | ⚪ | DATA_SERIAL深度变异Map<String,Int>#100 | `data class Map<String,Int>ernal(val x:Map<String,Int>)` | 按需标注 |
| KT-1677 | 🔴 | DATA_SERIAL深度变异Double#101 | `val x:Double=...;@Serializable data class A(val b:B);@Serial` | @Transient打断 |
| KT-1678 | 🟡 | DATA_SERIAL深度变异String?#102 | `data class C(val component1:String?,val x:String?)` | 避开componentN命名 |
| KT-1679 | 🟡 | DATA_SERIAL深度变异Set<Int>#103 | `val x:Set<Int>=...;data class N(val parent:N?);N(N(N(...)))` | 手动实现toString |
| KT-1680 | 🟡 | DATA_SERIAL深度变异Long#104 | `writeLong(a);readLong(b)` | 对齐write/read顺序 |
| KT-1681 | ⚪ | DATA_SERIAL深度变异Any#105 | `val x:Any=...;val a=Obj(1);val b=Obj(1);a==b` | 重写equals+hashCode |
| KT-1682 | 🟡 | DATA_SERIAL深度变异List<String>#106 | `writeList<String><String>(a);writeList<String><String>(b);re` | 写读顺序严格一致 |
| KT-1683 | 🟡 | DATA_SERIAL深度变异Int#107 | `val x:Int=...;@Serializable data class U(val x:Int){val y by` | @Transient标记非序列化字段 |
| KT-1684 | 🟡 | DATA_SERIAL深度变异Short#108 | `val x:Short=...;@SerialName(\\\"y\\\") val x:Long` | 统一命名 |
| KT-1685 | 🟡 | DATA_SERIAL深度变异Any?#109 | `val x:Any?=...;@SerialName(\\\"y\\\") val x:Double` | 统一命名 |
| KT-1686 | 🟡 | DATA_SERIAL深度变异String#110 | `val x:String=...;@SerialName(\\\"y\\\") val x:Float` | 统一命名 |
| KT-1687 | 🟡 | DATA_SERIAL深度变异Byte#111 | `writeByte(a);writeByte(b);readByte();readByte()` | 写读顺序严格一致 |
| KT-1688 | 🟡 | DATA_SERIAL深度变异Boolean?#112 | `writeBoolean?(a);writeLong(b);readLong();readBoolean?()` | 写读顺序严格一致 |
| KT-1689 | 🟡 | DATA_SERIAL深度变异Sequence<Long>#113 | `writeSequence<Long>(a);writeDouble(b);readDouble();readSeque` | 写读顺序严格一致 |
| KT-1690 | 🟡 | DATA_SERIAL深度变异Char#114 | `writeChar(a);writeBoolean(b);readBoolean();readChar()` | 写读顺序严格一致 |
| KT-1691 | 🟡 | DATA_SERIAL深度变异Double?#115 | `writeLong(a);writeDouble?(b);readDouble?();readLong()` | 写读顺序严格一致 |
| KT-1692 | 🟡 | DATA_SERIAL深度变异Array<Boolean>#116 | `writeDouble(a);writeArray<Boolean>(b);readArray<Boolean>();r` | 写读顺序严格一致 |
| KT-1693 | 🟡 | DATA_SERIAL深度变异Float#117 | `val x:Float=...;data class U(val x:Long=0);json无x字段` | 显式标注默认值 |
| KT-1694 | 🟡 | DATA_SERIAL深度变异Long?#118 | `val x:Long?=...;data class U(val x:Double=0);json无x字段` | 显式标注默认值 |
| KT-1695 | 🟡 | DATA_SERIAL深度变异MutableList<Double>#119 | `@Transient val x:MutableMutableList<Double><Double>;Gson仍序列化` | @Expose(false) |
| KT-1696 | 🔴 | DATA_SERIAL深度变异Boolean#120 | `val x:Boolean=...;A(val b:B);B(val a:A)` | @Transient打破循环 |
| KT-1697 | 🟡 | DATA_SERIAL深度变异Int?#121 | `data class U(val l:MutableInt?<T>);u.copy().l.add(x)` | 深复制或不可变 |
| KT-1698 | 🟡 | DATA_SERIAL深度变异Map<String,Int>#122 | `data class U(val x:Map<String,Int>=0);json无x字段` | 显式标注默认值 |
| KT-1699 | 🟡 | DATA_SERIAL深度变异Double#123 | `@SerialName(\"y\") val x:Double` | 统一命名 |
| KT-1700 | ⚪ | DATA_SERIAL深度变异String?#124 | `data class String?ernal(val x:String?)` | 按需标注 |
| KT-1701 | 🔴 | DATA_SERIAL深度变异Set<Int>#125 | `val x:Set<Int>=...;@Serializable data class A(val b:B);@Seri` | @Transient打断 |

## DELEGATE（80条）

| ID | 严重度 | Bug描述 | 触发模式 | 修复建议 |
|---|------|------|------|------|
| KT-0102 | 🟡 | by lazy默认SYNCHRONIZED | `by lazy{` | 指定LazyThreadSafetyMode.NONE |
| KT-0103 | 🟡 | observable内修改自身 | `var x by Delegates.observable(0){_,_,_,_->x++}` | 用vetoable |
| KT-0104 | ⚪ | 不必要的委托 | `val x by lazy{42}` | 直接val=42 |
| KT-0219 | 🟡 | 委托属性getValue/setValue签名错误 | `class D{operator fun getValue(ref:KProperty<*>,prop:KPropert` | thisRef:KProperty<*>,prop:KProperty<*> |
| KT-0220 | 🟡 | ReadOnlyProperty与ReadWriteProperty混淆 | `val x by Delegates.observable(0){` | 不需要set就用ReadOnlyProperty |
| KT-0221 | ⚪ | Map委托属性名不匹配 | `val x by map;("y"to"oops")` | key与属性名一致 |
| KT-0302 | 🟡 | by lazy+@Volatile=过度同步 | `@Volatile var x by lazy{init()}` | 去掉@Volatile或不用lazy |
| KT-2172 | 🟡 | DELEGATE深度变异String#0 | `val x:String=...;by lazy{` | 指定LazyThreadSafetyMode.NONE |
| KT-2173 | 🟡 | DELEGATE深度变异Byte#1 | `val x:Byte=...;var x by Delegates.observable(0){_,_,_,_->x++` | 用vetoable |
| KT-2174 | ⚪ | DELEGATE深度变异Boolean?#2 | `val x:Boolean?=...;val x by lazy{42}` | 直接val=42 |
| KT-2175 | 🟡 | DELEGATE深度变异Sequence<Long>#3 | `val x:Sequence<Long>=...;class D{operator fun getValue(ref:K` | thisRef:KProperty<*>,prop:KProperty<*> |
| KT-2176 | 🟡 | DELEGATE深度变异Char#4 | `val x:Char=...;val x by Delegates.observable(0){` | 不需要set就用ReadOnlyProperty |
| KT-2177 | ⚪ | DELEGATE深度变异Double?#5 | `val x:Double?=...;val x by map;(\"y\"to\"oops\")` | key与属性名一致 |
| KT-2178 | 🟡 | DELEGATE深度变异Array<Boolean>#6 | `val x:Array<Boolean>=...;@Volatile var x by lazy{init()}` | 去掉@Volatile或不用lazy |
| KT-2179 | 🟡 | DELEGATE深度变异Float#7 | `val x:Float=...;by lazy{` | 指定LazyThreadSafetyMode.NONE |
| KT-2180 | 🟡 | DELEGATE深度变异Long?#8 | `val x:Long?=...;var x by Delegates.observable(0){_,_,_,_->x+` | 用vetoable |
| KT-2181 | ⚪ | DELEGATE深度变异MutableList<Double>#9 | `val x:MutableList<Double>=...;val x by lazy{42}` | 直接val=42 |
| KT-2182 | 🟡 | DELEGATE深度变异Boolean#10 | `val x:Boolean=...;class D{operator fun getValue(ref:KPropert` | thisRef:KProperty<*>,prop:KProperty<*> |
| KT-2183 | 🟡 | DELEGATE深度变异Int?#11 | `val x:Int?=...;val x by Delegates.observable(0){` | 不需要set就用ReadOnlyProperty |
| KT-2184 | ⚪ | DELEGATE深度变异Map<String,Int>#12 | `val x:Map<String,Int>=...;val x by map;(\"y\"to\"oops\")` | key与属性名一致 |
| KT-2185 | 🟡 | DELEGATE深度变异Double#13 | `val x:Double=...;@Volatile var x by lazy{init()}` | 去掉@Volatile或不用lazy |
| KT-2186 | 🟡 | DELEGATE深度变异String?#14 | `val x:String?=...;by lazy{` | 指定LazyThreadSafetyMode.NONE |
| KT-2187 | 🟡 | DELEGATE深度变异Set<Int>#15 | `val x:Set<Int>=...;var x by Delegates.observable(0){_,_,_,_-` | 用vetoable |
| KT-2188 | ⚪ | DELEGATE深度变异Long#16 | `val x:Long=...;val x by lazy{42}` | 直接val=42 |
| KT-2189 | 🟡 | DELEGATE深度变异Any#17 | `val x:Any=...;class D{operator fun getValue(ref:KProperty<*>` | thisRef:KProperty<*>,prop:KProperty<*> |
| KT-2190 | 🟡 | DELEGATE深度变异List<String>#18 | `val x:List<String>=...;val x by Delegates.observable(0){` | 不需要set就用ReadOnlyProperty |
| KT-2191 | ⚪ | DELEGATE深度变异Int#19 | `val x:Int=...;val x by map;(\"y\"to\"oops\")` | key与属性名一致 |
| KT-2192 | 🟡 | DELEGATE深度变异Short#20 | `val x:Short=...;@Volatile var x by lazy{init()}` | 去掉@Volatile或不用lazy |
| KT-2193 | 🟡 | DELEGATE深度变异Any?#21 | `val x:Any?=...;by lazy{` | 指定LazyThreadSafetyMode.NONE |
| KT-2194 | 🟡 | DELEGATE深度变异String#22 | `val x:String=...;var x by Delegates.observable(0){_,_,_,_->x` | 用vetoable |
| KT-2195 | ⚪ | DELEGATE深度变异Byte#23 | `val x:Byte=...;val x by lazy{42}` | 直接val=42 |
| KT-2196 | 🟡 | DELEGATE深度变异Boolean?#24 | `val x:Boolean?=...;class D{operator fun getValue(ref:KProper` | thisRef:KProperty<*>,prop:KProperty<*> |
| KT-2197 | 🟡 | DELEGATE深度变异Sequence<Long>#25 | `val x:Sequence<Long>=...;val x by Delegates.observable(0){` | 不需要set就用ReadOnlyProperty |
| KT-2198 | ⚪ | DELEGATE深度变异Char#26 | `val x:Char=...;val x by map;(\"y\"to\"oops\")` | key与属性名一致 |
| KT-2199 | 🟡 | DELEGATE深度变异Double?#27 | `val x:Double?=...;@Volatile var x by lazy{init()}` | 去掉@Volatile或不用lazy |
| KT-2200 | 🟡 | DELEGATE深度变异Array<Boolean>#28 | `val x:Array<Boolean>=...;by lazy{` | 指定LazyThreadSafetyMode.NONE |
| KT-2201 | 🟡 | DELEGATE深度变异Float#29 | `val x:Float=...;var x by Delegates.observable(0){_,_,_,_->x+` | 用vetoable |
| KT-2202 | ⚪ | DELEGATE深度变异Long?#30 | `val x:Long?=...;val x by lazy{42}` | 直接val=42 |
| KT-2203 | 🟡 | DELEGATE深度变异MutableList<Double>#31 | `val x:MutableList<Double>=...;class D{operator fun getValue(` | thisRef:KProperty<*>,prop:KProperty<*> |
| KT-2204 | 🟡 | DELEGATE深度变异Boolean#32 | `val x:Boolean=...;val x by Delegates.observable(0){` | 不需要set就用ReadOnlyProperty |
| KT-2205 | ⚪ | DELEGATE深度变异Int?#33 | `val x:Int?=...;val x by map;(\"y\"to\"oops\")` | key与属性名一致 |
| KT-2206 | 🟡 | DELEGATE深度变异Map<String,Int>#34 | `val x:Map<String,Int>=...;@Volatile var x by lazy{init()}` | 去掉@Volatile或不用lazy |
| KT-2207 | 🟡 | DELEGATE深度变异Double#35 | `val x:Double=...;by lazy{` | 指定LazyThreadSafetyMode.NONE |
| KT-2208 | 🟡 | DELEGATE深度变异String?#36 | `val x:String?=...;var x by Delegates.observable(0){_,_,_,_->` | 用vetoable |
| KT-2209 | ⚪ | DELEGATE深度变异Set<Int>#37 | `val x:Set<Int>=...;val x by lazy{42}` | 直接val=42 |
| KT-2210 | 🟡 | DELEGATE深度变异Long#38 | `val x:Long=...;class D{operator fun getValue(ref:KProperty<*` | thisRef:KProperty<*>,prop:KProperty<*> |
| KT-2211 | 🟡 | DELEGATE深度变异Any#39 | `val x:Any=...;val x by Delegates.observable(0){` | 不需要set就用ReadOnlyProperty |
| KT-2212 | ⚪ | DELEGATE深度变异List<String>#40 | `val x:List<String>=...;val x by map;(\"y\"to\"oops\")` | key与属性名一致 |
| KT-2213 | 🟡 | DELEGATE深度变异Int#41 | `val x:Int=...;@Volatile var x by lazy{init()}` | 去掉@Volatile或不用lazy |
| KT-2214 | 🟡 | DELEGATE深度变异Short#42 | `val x:Short=...;by lazy{` | 指定LazyThreadSafetyMode.NONE |
| KT-2215 | 🟡 | DELEGATE深度变异Any?#43 | `val x:Any?=...;var x by Delegates.observable(0){_,_,_,_->x++` | 用vetoable |
| KT-2216 | ⚪ | DELEGATE深度变异String#44 | `val x:String=...;val x by lazy{42}` | 直接val=42 |
| KT-2217 | 🟡 | DELEGATE深度变异Byte#45 | `val x:Byte=...;class D{operator fun getValue(ref:KProperty<*` | thisRef:KProperty<*>,prop:KProperty<*> |
| KT-2218 | 🟡 | DELEGATE深度变异Boolean?#46 | `val x:Boolean?=...;val x by Delegates.observable(0){` | 不需要set就用ReadOnlyProperty |
| KT-2219 | ⚪ | DELEGATE深度变异Sequence<Long>#47 | `val x:Sequence<Long>=...;val x by map;(\"y\"to\"oops\")` | key与属性名一致 |
| KT-2220 | 🟡 | DELEGATE深度变异Char#48 | `val x:Char=...;@Volatile var x by lazy{init()}` | 去掉@Volatile或不用lazy |
| KT-2221 | 🟡 | DELEGATE深度变异Double?#49 | `val x:Double?=...;by lazy{` | 指定LazyThreadSafetyMode.NONE |
| KT-2222 | 🟡 | DELEGATE深度变异Array<Boolean>#50 | `val x:Array<Boolean>=...;var x by Delegates.observable(0){_,` | 用vetoable |
| KT-2223 | ⚪ | DELEGATE深度变异Float#51 | `val x:Float=...;val x by lazy{42}` | 直接val=42 |
| KT-2224 | 🟡 | DELEGATE深度变异Long?#52 | `val x:Long?=...;class D{operator fun getValue(ref:KProperty<` | thisRef:KProperty<*>,prop:KProperty<*> |
| KT-2225 | 🟡 | DELEGATE深度变异MutableList<Double>#53 | `val x:MutableList<Double>=...;val x by Delegates.observable(` | 不需要set就用ReadOnlyProperty |
| KT-2226 | ⚪ | DELEGATE深度变异Boolean#54 | `val x:Boolean=...;val x by map;(\"y\"to\"oops\")` | key与属性名一致 |
| KT-2227 | 🟡 | DELEGATE深度变异Int?#55 | `val x:Int?=...;@Volatile var x by lazy{init()}` | 去掉@Volatile或不用lazy |
| KT-2228 | 🟡 | DELEGATE深度变异Map<String,Int>#56 | `val x:Map<String,Int>=...;by lazy{` | 指定LazyThreadSafetyMode.NONE |
| KT-2229 | 🟡 | DELEGATE深度变异Double#57 | `val x:Double=...;var x by Delegates.observable(0){_,_,_,_->x` | 用vetoable |
| KT-2230 | ⚪ | DELEGATE深度变异String?#58 | `val x:String?=...;val x by lazy{42}` | 直接val=42 |
| KT-2231 | 🟡 | DELEGATE深度变异Set<Int>#59 | `val x:Set<Int>=...;class D{operator fun getValue(ref:KProper` | thisRef:KProperty<*>,prop:KProperty<*> |
| KT-2232 | 🟡 | DELEGATE深度变异Long#60 | `val x:Long=...;val x by Delegates.observable(0){` | 不需要set就用ReadOnlyProperty |
| KT-2233 | ⚪ | DELEGATE深度变异Any#61 | `val x:Any=...;val x by map;(\"y\"to\"oops\")` | key与属性名一致 |
| KT-2234 | 🟡 | DELEGATE深度变异List<String>#62 | `val x:List<String>=...;@Volatile var x by lazy{init()}` | 去掉@Volatile或不用lazy |
| KT-2235 | 🟡 | DELEGATE深度变异Int#63 | `val x:Int=...;by lazy{` | 指定LazyThreadSafetyMode.NONE |
| KT-2236 | 🟡 | DELEGATE深度变异Short#64 | `val x:Short=...;var x by Delegates.observable(0){_,_,_,_->x+` | 用vetoable |
| KT-2237 | ⚪ | DELEGATE深度变异Any?#65 | `val x:Any?=...;val x by lazy{42}` | 直接val=42 |
| KT-2238 | 🟡 | DELEGATE深度变异String#66 | `val x:String=...;class D{operator fun getValue(ref:KProperty` | thisRef:KProperty<*>,prop:KProperty<*> |
| KT-2239 | 🟡 | DELEGATE深度变异Byte#67 | `val x:Byte=...;val x by Delegates.observable(0){` | 不需要set就用ReadOnlyProperty |
| KT-2240 | ⚪ | DELEGATE深度变异Boolean?#68 | `val x:Boolean?=...;val x by map;(\"y\"to\"oops\")` | key与属性名一致 |
| KT-2241 | 🟡 | DELEGATE深度变异Sequence<Long>#69 | `val x:Sequence<Long>=...;@Volatile var x by lazy{init()}` | 去掉@Volatile或不用lazy |
| KT-2242 | 🟡 | DELEGATE深度变异Char#70 | `val x:Char=...;by lazy{` | 指定LazyThreadSafetyMode.NONE |
| KT-2243 | 🟡 | DELEGATE深度变异Double?#71 | `val x:Double?=...;var x by Delegates.observable(0){_,_,_,_->` | 用vetoable |
| KT-2244 | ⚪ | DELEGATE深度变异Array<Boolean>#72 | `val x:Array<Boolean>=...;val x by lazy{42}` | 直接val=42 |

## DSL_LAMBDA（150条）

| ID | 严重度 | Bug描述 | 触发模式 | 修复建议 |
|---|------|------|------|------|
| KT-0063 | 🔴 | 非局部return | *另修: 改用for循环+break, 提取为命名函数* |`fun f(){list.forEach{if(it)return}}` | return@forEach |
| KT-0064 | 🟡 | 隐式this歧义 | `apply{name=name}` | this@outer.name |
| KT-0065 | 🟡 | 嵌套apply/also/let混乱 | `obj.apply{also{let{run{}}}` | 提取命名函数 |
| KT-0066 | 🟡 | run与with混淆 | `run{this.method()} vs with(obj){method()}` | 按需选择 |
| KT-0067 | 🟡 | also返回值忽略 | `obj.also{it.mutate()}` | 不需要返回值用apply |
| KT-0068 | ⚪ | 多余的run | `run{expr}` | 直接用expr |
| KT-0186 | 🔴 | Builder DSL遗漏@DslMarker | `@DslMarker;obj.apply{build{apply{obj}}}` | 加@DslMarker |
| KT-0187 | 🟡 | also与apply链式错误 | `val x=obj.also{it.prop=1}.also{it.prop=2}` | apply更适合 |
| KT-0188 | 🟡 | with接收者为可空 | `with(maybeNull){this.method()}` | maybeNull?.let{with(it){}} |
| KT-0189 | 🟡 | run非扩展+扩展混淆 | `obj.run{length} vs run{obj.length}` | 统一风格 |
| KT-0190 | ⚪ | let与run语义混淆 | `x?.let{it*2} vs x?.run{this*2}` | 根据是否需要变换选择 |
| KT-0191 | ⚪ | takeUnless与takeIf误用 | `x.takeUnless{it>0}` | 用takeIf+!或直接if |
| KT-1438 | 🔴 | DSL_LAMBDA深度变异String#0 | `val x:String=...;fun f(){list.forEach{if(it)return}}` | return@forEach |
| KT-1439 | 🟡 | DSL_LAMBDA深度变异Byte#1 | `val x:Byte=...;apply{name=name}` | this@outer.name |
| KT-1440 | 🟡 | DSL_LAMBDA深度变异Boolean?#2 | `val x:Boolean?=...;obj.apply{also{let{run{}}}` | 提取命名函数 |
| KT-1441 | 🟡 | DSL_LAMBDA深度变异Sequence<Long>#3 | `val x:Sequence<Long>=...;run{this.method()} vs with(obj){met` | 按需选择 |
| KT-1442 | 🟡 | DSL_LAMBDA深度变异Char#4 | `val x:Char=...;obj.also{it.mutate()}` | 不需要返回值用apply |
| KT-1443 | ⚪ | DSL_LAMBDA深度变异Double?#5 | `val x:Double?=...;run{expr}` | 直接用expr |
| KT-1444 | 🔴 | DSL_LAMBDA深度变异Array<Boolean>#6 | `val x:Array<Boolean>=...;@DslMarker;obj.apply{build{apply{ob` | 加@DslMarker |
| KT-1445 | 🟡 | DSL_LAMBDA深度变异Float#7 | `val x:Float=...;val x=obj.also{it.prop=1}.also{it.prop=2}` | apply更适合 |
| KT-1446 | 🟡 | DSL_LAMBDA深度变异Long?#8 | `val x:Long?=...;with(maybeNull){this.method()}` | maybeNull?.let{with(it){}} |
| KT-1447 | 🟡 | DSL_LAMBDA深度变异MutableList<Double>#9 | `val x:MutableList<Double>=...;obj.run{length} vs run{obj.len` | 统一风格 |
| KT-1448 | ⚪ | DSL_LAMBDA深度变异Boolean#10 | `val x:Boolean=...;x?.let{it*2} vs x?.run{this*2}` | 根据是否需要变换选择 |
| KT-1449 | ⚪ | DSL_LAMBDA深度变异Int?#11 | `val x:Int?=...;x.takeUnless{it>0}` | 用takeIf+!或直接if |
| KT-1450 | 🔴 | DSL_LAMBDA深度变异Map<String,Int>#12 | `val x:Map<String,Int>=...;fun f(){list.forEach{if(it)return}` | return@forEach |
| KT-1451 | 🟡 | DSL_LAMBDA深度变异Double#13 | `val x:Double=...;apply{name=name}` | this@outer.name |
| KT-1452 | 🟡 | DSL_LAMBDA深度变异String?#14 | `val x:String?=...;obj.apply{also{let{run{}}}` | 提取命名函数 |
| KT-1453 | 🟡 | DSL_LAMBDA深度变异Set<Int>#15 | `val x:Set<Int>=...;run{this.method()} vs with(obj){method()}` | 按需选择 |
| KT-1454 | 🟡 | DSL_LAMBDA深度变异Long#16 | `val x:Long=...;obj.also{it.mutate()}` | 不需要返回值用apply |
| KT-1455 | ⚪ | DSL_LAMBDA深度变异Any#17 | `val x:Any=...;run{expr}` | 直接用expr |
| KT-1456 | 🔴 | DSL_LAMBDA深度变异List<String>#18 | `val x:List<String>=...;@DslMarker;obj.apply{build{apply{obj}` | 加@DslMarker |
| KT-1457 | 🟡 | DSL_LAMBDA深度变异Int#19 | `val x:Int=...;val x=obj.also{it.prop=1}.also{it.prop=2}` | apply更适合 |
| KT-1458 | 🟡 | DSL_LAMBDA深度变异Short#20 | `val x:Short=...;with(maybeNull){this.method()}` | maybeNull?.let{with(it){}} |
| KT-1459 | 🟡 | DSL_LAMBDA深度变异Any?#21 | `val x:Any?=...;obj.run{length} vs run{obj.length}` | 统一风格 |
| KT-1460 | ⚪ | DSL_LAMBDA深度变异String#22 | `val x:String=...;x?.let{it*2} vs x?.run{this*2}` | 根据是否需要变换选择 |
| KT-1461 | ⚪ | DSL_LAMBDA深度变异Byte#23 | `val x:Byte=...;x.takeUnless{it>0}` | 用takeIf+!或直接if |
| KT-1462 | 🔴 | DSL_LAMBDA深度变异Boolean?#24 | `val x:Boolean?=...;fun f(){list.forEach{if(it)return}}` | return@forEach |
| KT-1463 | 🟡 | DSL_LAMBDA深度变异Sequence<Long>#25 | `val x:Sequence<Long>=...;apply{name=name}` | this@outer.name |
| KT-1464 | 🟡 | DSL_LAMBDA深度变异Char#26 | `val x:Char=...;obj.apply{also{let{run{}}}` | 提取命名函数 |
| KT-1465 | 🟡 | DSL_LAMBDA深度变异Double?#27 | `val x:Double?=...;run{this.method()} vs with(obj){method()}` | 按需选择 |
| KT-1466 | 🟡 | DSL_LAMBDA深度变异Array<Boolean>#28 | `val x:Array<Boolean>=...;obj.also{it.mutate()}` | 不需要返回值用apply |
| KT-1467 | ⚪ | DSL_LAMBDA深度变异Float#29 | `val x:Float=...;run{expr}` | 直接用expr |
| KT-1468 | 🔴 | DSL_LAMBDA深度变异Long?#30 | `val x:Long?=...;@DslMarker;obj.apply{build{apply{obj}}}` | 加@DslMarker |
| KT-1469 | 🟡 | DSL_LAMBDA深度变异MutableList<Double>#31 | `val x:MutableList<Double>=...;val x=obj.also{it.prop=1}.also` | apply更适合 |
| KT-1470 | 🟡 | DSL_LAMBDA深度变异Boolean#32 | `val x:Boolean=...;with(maybeNull){this.method()}` | maybeNull?.let{with(it){}} |
| KT-1471 | 🟡 | DSL_LAMBDA深度变异Int?#33 | `val x:Int?=...;obj.run{length} vs run{obj.length}` | 统一风格 |
| KT-1472 | ⚪ | DSL_LAMBDA深度变异Map<String,Int>#34 | `val x:Map<String,Int>=...;x?.let{it*2} vs x?.run{this*2}` | 根据是否需要变换选择 |
| KT-1473 | ⚪ | DSL_LAMBDA深度变异Double#35 | `val x:Double=...;x.takeUnless{it>0}` | 用takeIf+!或直接if |
| KT-1474 | 🔴 | DSL_LAMBDA深度变异String?#36 | `val x:String?=...;fun f(){list.forEach{if(it)return}}` | return@forEach |
| KT-1475 | 🟡 | DSL_LAMBDA深度变异Set<Int>#37 | `val x:Set<Int>=...;apply{name=name}` | this@outer.name |
| KT-1476 | 🟡 | DSL_LAMBDA深度变异Long#38 | `val x:Long=...;obj.apply{also{let{run{}}}` | 提取命名函数 |
| KT-1477 | 🟡 | DSL_LAMBDA深度变异Any#39 | `val x:Any=...;run{this.method()} vs with(obj){method()}` | 按需选择 |
| KT-1478 | 🟡 | DSL_LAMBDA深度变异List<String>#40 | `val x:List<String>=...;obj.also{it.mutate()}` | 不需要返回值用apply |
| KT-1479 | ⚪ | DSL_LAMBDA深度变异Int#41 | `val x:Int=...;run{expr}` | 直接用expr |
| KT-1480 | 🔴 | DSL_LAMBDA深度变异Short#42 | `val x:Short=...;@DslMarker;obj.apply{build{apply{obj}}}` | 加@DslMarker |
| KT-1481 | 🟡 | DSL_LAMBDA深度变异Any?#43 | `val x:Any?=...;val x=obj.also{it.prop=1}.also{it.prop=2}` | apply更适合 |
| KT-1482 | 🟡 | DSL_LAMBDA深度变异String#44 | `val x:String=...;with(maybeNull){this.method()}` | maybeNull?.let{with(it){}} |
| KT-1483 | 🟡 | DSL_LAMBDA深度变异Byte#45 | `val x:Byte=...;obj.run{length} vs run{obj.length}` | 统一风格 |
| KT-1484 | ⚪ | DSL_LAMBDA深度变异Boolean?#46 | `val x:Boolean?=...;x?.let{it*2} vs x?.run{this*2}` | 根据是否需要变换选择 |
| KT-1485 | ⚪ | DSL_LAMBDA深度变异Sequence<Long>#47 | `val x:Sequence<Long>=...;x.takeUnless{it>0}` | 用takeIf+!或直接if |
| KT-1486 | 🔴 | DSL_LAMBDA深度变异Char#48 | `val x:Char=...;fun f(){list.forEach{if(it)return}}` | return@forEach |
| KT-1487 | 🟡 | DSL_LAMBDA深度变异Double?#49 | `val x:Double?=...;apply{name=name}` | this@outer.name |
| KT-1488 | 🟡 | DSL_LAMBDA深度变异Array<Boolean>#50 | `val x:Array<Boolean>=...;obj.apply{also{let{run{}}}` | 提取命名函数 |
| KT-1489 | 🟡 | DSL_LAMBDA深度变异Float#51 | `val x:Float=...;run{this.method()} vs with(obj){method()}` | 按需选择 |
| KT-1490 | 🟡 | DSL_LAMBDA深度变异Long?#52 | `val x:Long?=...;obj.also{it.mutate()}` | 不需要返回值用apply |
| KT-1491 | ⚪ | DSL_LAMBDA深度变异MutableList<Double>#53 | `val x:MutableList<Double>=...;run{expr}` | 直接用expr |
| KT-1492 | 🔴 | DSL_LAMBDA深度变异Boolean#54 | `val x:Boolean=...;@DslMarker;obj.apply{build{apply{obj}}}` | 加@DslMarker |
| KT-1493 | 🟡 | DSL_LAMBDA深度变异Int?#55 | `val x:Int?=...;val x=obj.also{it.prop=1}.also{it.prop=2}` | apply更适合 |
| KT-1494 | 🟡 | DSL_LAMBDA深度变异Map<String,Int>#56 | `val x:Map<String,Int>=...;with(maybeNull){this.method()}` | maybeNull?.let{with(it){}} |
| KT-1495 | 🟡 | DSL_LAMBDA深度变异Double#57 | `val x:Double=...;obj.run{length} vs run{obj.length}` | 统一风格 |
| KT-1496 | ⚪ | DSL_LAMBDA深度变异String?#58 | `val x:String?=...;x?.let{it*2} vs x?.run{this*2}` | 根据是否需要变换选择 |
| KT-1497 | ⚪ | DSL_LAMBDA深度变异Set<Int>#59 | `val x:Set<Int>=...;x.takeUnless{it>0}` | 用takeIf+!或直接if |
| KT-1498 | 🔴 | DSL_LAMBDA深度变异Long#60 | `val x:Long=...;fun f(){list.forEach{if(it)return}}` | return@forEach |
| KT-1499 | 🟡 | DSL_LAMBDA深度变异Any#61 | `val x:Any=...;apply{name=name}` | this@outer.name |
| KT-1500 | 🟡 | DSL_LAMBDA深度变异List<String>#62 | `val x:List<String>=...;obj.apply{also{let{run{}}}` | 提取命名函数 |
| KT-1501 | 🟡 | DSL_LAMBDA深度变异Int#63 | `val x:Int=...;run{this.method()} vs with(obj){method()}` | 按需选择 |
| KT-1502 | 🟡 | DSL_LAMBDA深度变异Short#64 | `val x:Short=...;obj.also{it.mutate()}` | 不需要返回值用apply |
| KT-1503 | ⚪ | DSL_LAMBDA深度变异Any?#65 | `val x:Any?=...;run{expr}` | 直接用expr |
| KT-1504 | 🔴 | DSL_LAMBDA深度变异String#66 | `val x:String=...;@DslMarker;obj.apply{build{apply{obj}}}` | 加@DslMarker |
| KT-1505 | 🟡 | DSL_LAMBDA深度变异Byte#67 | `val x:Byte=...;val x=obj.also{it.prop=1}.also{it.prop=2}` | apply更适合 |
| KT-1506 | 🟡 | DSL_LAMBDA深度变异Boolean?#68 | `val x:Boolean?=...;with(maybeNull){this.method()}` | maybeNull?.let{with(it){}} |
| KT-1507 | 🟡 | DSL_LAMBDA深度变异Sequence<Long>#69 | `val x:Sequence<Long>=...;obj.run{length} vs run{obj.length}` | 统一风格 |
| KT-1508 | ⚪ | DSL_LAMBDA深度变异Char#70 | `val x:Char=...;x?.let{it*2} vs x?.run{this*2}` | 根据是否需要变换选择 |
| KT-1509 | ⚪ | DSL_LAMBDA深度变异Double?#71 | `val x:Double?=...;x.takeUnless{it>0}` | 用takeIf+!或直接if |
| KT-1510 | 🔴 | DSL_LAMBDA深度变异Array<Boolean>#72 | `val x:Array<Boolean>=...;fun f(){list.forEach{if(it)return}}` | return@forEach |
| KT-1511 | 🟡 | DSL_LAMBDA深度变异Float#73 | `val x:Float=...;apply{name=name}` | this@outer.name |
| KT-1512 | 🟡 | DSL_LAMBDA深度变异Long?#74 | `val x:Long?=...;obj.apply{also{let{run{}}}` | 提取命名函数 |
| KT-1513 | 🟡 | DSL_LAMBDA深度变异MutableList<Double>#75 | `val x:MutableList<Double>=...;run{this.method()} vs with(obj` | 按需选择 |
| KT-1514 | 🟡 | DSL_LAMBDA深度变异Boolean#76 | `val x:Boolean=...;obj.also{it.mutate()}` | 不需要返回值用apply |
| KT-1515 | ⚪ | DSL_LAMBDA深度变异Int?#77 | `val x:Int?=...;run{expr}` | 直接用expr |
| KT-1516 | 🔴 | DSL_LAMBDA深度变异Map<String,Int>#78 | `val x:Map<String,Int>=...;@DslMarker;obj.apply{build{apply{o` | 加@DslMarker |
| KT-1517 | 🟡 | DSL_LAMBDA深度变异Double#79 | `val x:Double=...;val x=obj.also{it.prop=1}.also{it.prop=2}` | apply更适合 |
| KT-1518 | 🟡 | DSL_LAMBDA深度变异String?#80 | `val x:String?=...;with(maybeNull){this.method()}` | maybeNull?.let{with(it){}} |
| KT-1519 | 🟡 | DSL_LAMBDA深度变异Set<Int>#81 | `val x:Set<Int>=...;obj.run{length} vs run{obj.length}` | 统一风格 |
| KT-1520 | ⚪ | DSL_LAMBDA深度变异Long#82 | `val x:Long=...;x?.let{it*2} vs x?.run{this*2}` | 根据是否需要变换选择 |
| KT-1521 | ⚪ | DSL_LAMBDA深度变异Any#83 | `val x:Any=...;x.takeUnless{it>0}` | 用takeIf+!或直接if |
| KT-1522 | 🔴 | DSL_LAMBDA深度变异List<String>#84 | `val x:List<String>=...;fun f(){list.forEach{if(it)return}}` | return@forEach |
| KT-1523 | 🟡 | DSL_LAMBDA深度变异Int#85 | `val x:Int=...;apply{name=name}` | this@outer.name |
| KT-1524 | 🟡 | DSL_LAMBDA深度变异Short#86 | `val x:Short=...;obj.apply{also{let{run{}}}` | 提取命名函数 |
| KT-1525 | 🟡 | DSL_LAMBDA深度变异Any?#87 | `val x:Any?=...;run{this.method()} vs with(obj){method()}` | 按需选择 |
| KT-1526 | 🟡 | DSL_LAMBDA深度变异String#88 | `val x:String=...;obj.also{it.mutate()}` | 不需要返回值用apply |
| KT-1527 | ⚪ | DSL_LAMBDA深度变异Byte#89 | `val x:Byte=...;run{expr}` | 直接用expr |
| KT-1528 | 🔴 | DSL_LAMBDA深度变异Boolean?#90 | `val x:Boolean?=...;@DslMarker;obj.apply{build{apply{obj}}}` | 加@DslMarker |
| KT-1529 | 🟡 | DSL_LAMBDA深度变异Sequence<Long>#91 | `val x:Sequence<Long>=...;val x=obj.also{it.prop=1}.also{it.p` | apply更适合 |
| KT-1530 | 🟡 | DSL_LAMBDA深度变异Char#92 | `val x:Char=...;with(maybeNull){this.method()}` | maybeNull?.let{with(it){}} |
| KT-1531 | 🟡 | DSL_LAMBDA深度变异Double?#93 | `val x:Double?=...;obj.run{length} vs run{obj.length}` | 统一风格 |
| KT-1532 | ⚪ | DSL_LAMBDA深度变异Array<Boolean>#94 | `val x:Array<Boolean>=...;x?.let{it*2} vs x?.run{this*2}` | 根据是否需要变换选择 |
| KT-1533 | ⚪ | DSL_LAMBDA深度变异Float#95 | `val x:Float=...;x.takeUnless{it>0}` | 用takeIf+!或直接if |
| KT-1534 | 🔴 | DSL_LAMBDA深度变异Long?#96 | `val x:Long?=...;fun f(){list.forEach{if(it)return}}` | return@forEach |
| KT-1535 | 🟡 | DSL_LAMBDA深度变异MutableList<Double>#97 | `val x:MutableList<Double>=...;apply{name=name}` | this@outer.name |
| KT-1536 | 🟡 | DSL_LAMBDA深度变异Boolean#98 | `val x:Boolean=...;obj.apply{also{let{run{}}}` | 提取命名函数 |
| KT-1537 | 🟡 | DSL_LAMBDA深度变异Int?#99 | `val x:Int?=...;run{this.method()} vs with(obj){method()}` | 按需选择 |
| KT-1538 | 🟡 | DSL_LAMBDA深度变异Map<String,Int>#100 | `val x:Map<String,Int>=...;obj.also{it.mutate()}` | 不需要返回值用apply |
| KT-1539 | ⚪ | DSL_LAMBDA深度变异Double#101 | `val x:Double=...;run{expr}` | 直接用expr |
| KT-1540 | 🔴 | DSL_LAMBDA深度变异String?#102 | `val x:String?=...;@DslMarker;obj.apply{build{apply{obj}}}` | 加@DslMarker |
| KT-1541 | 🟡 | DSL_LAMBDA深度变异Set<Int>#103 | `val x:Set<Int>=...;val x=obj.also{it.prop=1}.also{it.prop=2}` | apply更适合 |
| KT-1542 | 🟡 | DSL_LAMBDA深度变异Long#104 | `val x:Long=...;with(maybeNull){this.method()}` | maybeNull?.let{with(it){}} |
| KT-1543 | 🟡 | DSL_LAMBDA深度变异Any#105 | `val x:Any=...;obj.run{length} vs run{obj.length}` | 统一风格 |
| KT-1544 | ⚪ | DSL_LAMBDA深度变异List<String>#106 | `val x:List<String>=...;x?.let{it*2} vs x?.run{this*2}` | 根据是否需要变换选择 |
| KT-1545 | ⚪ | DSL_LAMBDA深度变异Int#107 | `val x:Int=...;x.takeUnless{it>0}` | 用takeIf+!或直接if |
| KT-1546 | 🔴 | DSL_LAMBDA深度变异Short#108 | `val x:Short=...;fun f(){list.forEach{if(it)return}}` | return@forEach |
| KT-1547 | 🟡 | DSL_LAMBDA深度变异Any?#109 | `val x:Any?=...;apply{name=name}` | this@outer.name |
| KT-1548 | 🟡 | DSL_LAMBDA深度变异String#110 | `val x:String=...;obj.apply{also{let{run{}}}` | 提取命名函数 |
| KT-1549 | 🟡 | DSL_LAMBDA深度变异Byte#111 | `val x:Byte=...;run{this.method()} vs with(obj){method()}` | 按需选择 |
| KT-1550 | 🟡 | DSL_LAMBDA深度变异Boolean?#112 | `val x:Boolean?=...;obj.also{it.mutate()}` | 不需要返回值用apply |
| KT-1551 | ⚪ | DSL_LAMBDA深度变异Sequence<Long>#113 | `val x:Sequence<Long>=...;run{expr}` | 直接用expr |
| KT-1552 | 🔴 | DSL_LAMBDA深度变异Char#114 | `val x:Char=...;@DslMarker;obj.apply{build{apply{obj}}}` | 加@DslMarker |
| KT-1553 | 🟡 | DSL_LAMBDA深度变异Double?#115 | `val x:Double?=...;val x=obj.also{it.prop=1}.also{it.prop=2}` | apply更适合 |
| KT-1554 | 🟡 | DSL_LAMBDA深度变异Array<Boolean>#116 | `val x:Array<Boolean>=...;with(maybeNull){this.method()}` | maybeNull?.let{with(it){}} |
| KT-1555 | 🟡 | DSL_LAMBDA深度变异Float#117 | `val x:Float=...;obj.run{length} vs run{obj.length}` | 统一风格 |
| KT-1556 | ⚪ | DSL_LAMBDA深度变异Long?#118 | `val x:Long?=...;x?.let{it*2} vs x?.run{this*2}` | 根据是否需要变换选择 |
| KT-1557 | ⚪ | DSL_LAMBDA深度变异MutableList<Double>#119 | `val x:MutableList<Double>=...;x.takeUnless{it>0}` | 用takeIf+!或直接if |
| KT-1558 | 🔴 | DSL_LAMBDA深度变异Boolean#120 | `val x:Boolean=...;fun f(){list.forEach{if(it)return}}` | return@forEach |
| KT-1559 | 🟡 | DSL_LAMBDA深度变异Int?#121 | `val x:Int?=...;apply{name=name}` | this@outer.name |
| KT-1560 | 🟡 | DSL_LAMBDA深度变异Map<String,Int>#122 | `val x:Map<String,Int>=...;obj.apply{also{let{run{}}}` | 提取命名函数 |
| KT-1561 | 🟡 | DSL_LAMBDA深度变异Double#123 | `val x:Double=...;run{this.method()} vs with(obj){method()}` | 按需选择 |
| KT-1562 | 🟡 | DSL_LAMBDA深度变异String?#124 | `val x:String?=...;obj.also{it.mutate()}` | 不需要返回值用apply |
| KT-1563 | ⚪ | DSL_LAMBDA深度变异Set<Int>#125 | `val x:Set<Int>=...;run{expr}` | 直接用expr |
| KT-1564 | 🔴 | DSL_LAMBDA深度变异Long#126 | `val x:Long=...;@DslMarker;obj.apply{build{apply{obj}}}` | 加@DslMarker |
| KT-1565 | 🟡 | DSL_LAMBDA深度变异Any#127 | `val x:Any=...;val x=obj.also{it.prop=1}.also{it.prop=2}` | apply更适合 |
| KT-1566 | 🟡 | DSL_LAMBDA深度变异List<String>#128 | `val x:List<String>=...;with(maybeNull){this.method()}` | maybeNull?.let{with(it){}} |
| KT-1567 | 🟡 | DSL_LAMBDA深度变异Int#129 | `val x:Int=...;obj.run{length} vs run{obj.length}` | 统一风格 |
| KT-1568 | ⚪ | DSL_LAMBDA深度变异Short#130 | `val x:Short=...;x?.let{it*2} vs x?.run{this*2}` | 根据是否需要变换选择 |
| KT-1569 | ⚪ | DSL_LAMBDA深度变异Any?#131 | `val x:Any?=...;x.takeUnless{it>0}` | 用takeIf+!或直接if |
| KT-1570 | 🔴 | DSL_LAMBDA深度变异String#132 | `val x:String=...;fun f(){list.forEach{if(it)return}}` | return@forEach |
| KT-1571 | 🟡 | DSL_LAMBDA深度变异Byte#133 | `val x:Byte=...;apply{name=name}` | this@outer.name |
| KT-1572 | 🟡 | DSL_LAMBDA深度变异Boolean?#134 | `val x:Boolean?=...;obj.apply{also{let{run{}}}` | 提取命名函数 |
| KT-1573 | 🟡 | DSL_LAMBDA深度变异Sequence<Long>#135 | `val x:Sequence<Long>=...;run{this.method()} vs with(obj){met` | 按需选择 |
| KT-1574 | 🟡 | DSL_LAMBDA深度变异Char#136 | `val x:Char=...;obj.also{it.mutate()}` | 不需要返回值用apply |
| KT-1575 | ⚪ | DSL_LAMBDA深度变异Double?#137 | `val x:Double?=...;run{expr}` | 直接用expr |

## GENERICS（200条）

| ID | 严重度 | Bug描述 | 触发模式 | 修复建议 |
|---|------|------|------|------|
| KT-0024 | 🔴 | 泛型异常捕获 | `catch(e:` | 捕获具体异常类型 |
| KT-0025 | 🔴 | 泛型+is检查 | `if(x is List<String>){}` | reified+inline |
| KT-0026 | 🔴 | unchecked cast警告 | `val x=y as List<String>` | 显式检查元素类型 |
| KT-0027 | 🟡 | 星投影写操作 | `val x:MutableList<*>;x.add(1)` | 声明具体类型 |
| KT-0028 | 🟡 | 型变标记错误 | `interface P<out T>{fun f(t:T)}` | in T |
| KT-0029 | 🟡 | reified缺失 | `fun <T> f(){T::class}` | inline+reified |
| KT-0030 | 🟡 | 泛型约束遗漏 | `fun <T> f(t:T){t.method()}` | <T:HasMethod> |
| KT-0031 | ⚪ | 泛型参数命名冲突 | `fun <T> f(T:T){}` | 重命名 |
| KT-0032 | 🟡 | 星投影操作List<S> | `val x:{g}*=...;x.add(...)` | 指定具体类型参数 |
| KT-0033 | 🟡 | 星投影操作Set<I> | `val x:{g}*=...;x.add(...)` | 指定具体类型参数 |
| KT-0034 | 🟡 | 星投影操作Map<S,I> | `val x:{g}*=...;x.add(...)` | 指定具体类型参数 |
| KT-0152 | 🔴 | 泛型型变数组 | `val arr=Array<T>(10){;val a:Array<Any>=arr` | List代替Array |
| KT-0153 | 🔴 | 泛型+伴生对象类型 | `fun <T> f(){T.Companion}` | reified+inline |
| KT-0154 | 🟡 | 泛型函数引用歧义 | `val ref: (T)->R=::genericFun` | 显式标注泛型参数 |
| KT-0155 | 🟡 | 泛型约束递归 | `fun <T:Comparable<T>> sort(list:List<T>)` | 保持不变或where |
| KT-0156 | 🟡 | 泛型+密封类when | `when(sealed){is Wrapper<*>->...}` | 具体化子类型 |
| KT-0157 | ⚪ | 泛型参数通配符滥用 | `fun <T> f(list:List<*>)` | fun f(list:List<*>)直接 |
| KT-0158 | ⚪ | 泛型别名冲突 | `typealias S<T>=List<T>;fun <T> f(s:S<T>)` | 重命名 |
| KT-0159 | 🔴 | 泛型接口多继承歧义 | `class C:A<String>,A<Int>` | 不同接口 |
| KT-0160 | 🟡 | 泛型vararg传递 | `fun <T> f(vararg t:T);f(arrayOf(1))` | f(*arrayOf) |
| KT-0161 | 🟡 | 泛型委托属性类型丢失 | `val x by Delegates.notNull<T>()` | 显式类型 |
| KT-0269 | 🟡 | 泛型递归约束让编译器无限展开 | `fun <T:T> f(){}` | 加where约束打断 |
| KT-0297 | 🔴 | reified+suspend=限制叠加 | `suspend inline fun <reified T> api():T` | 拆分为非suspend inline+suspend调用 |
| KT-0418 | 🔴 | 泛型接口多继承歧义（Int版） | `class C:A<Int>,A<Int>` | 不同接口 |
| KT-0419 | 🔴 | 泛型接口多继承歧义（Long版） | `class C:A<Long>,A<Int>` | 不同接口 |
| KT-0420 | 🔴 | 泛型接口多继承歧义（Double版） | `class C:A<Double>,A<Int>` | 不同接口 |
| KT-0421 | 🔴 | 泛型接口多继承歧义（Boolean版） | `class C:A<Boolean>,A<Int>` | 不同接口 |
| KT-0422 | 🔴 | 泛型接口多继承歧义（Long版） | `class C:A<String>,A<Long>` | 不同接口 |
| KT-0429 | 🔴 | 泛型+is检查（Int版） | `if(x is List<Int>){}` | reified+inline |
| KT-0430 | 🔴 | 泛型+is检查（Long版） | `if(x is List<Long>){}` | reified+inline |
| KT-0431 | 🔴 | 泛型+is检查（Double版） | `if(x is List<Double>){}` | reified+inline |
| KT-0432 | 🔴 | 泛型+is检查（Boolean版） | `if(x is List<Boolean>){}` | reified+inline |
| KT-0465 | 🔴 | unchecked cast警告（Int版） | `val x=y as List<Int>` | 显式检查元素类型 |
| KT-0466 | 🔴 | unchecked cast警告（Long版） | `val x=y as List<Long>` | 显式检查元素类型 |
| KT-0467 | 🔴 | unchecked cast警告（Double版） | `val x=y as List<Double>` | 显式检查元素类型 |
| KT-0468 | 🔴 | unchecked cast警告（Boolean版） | `val x=y as List<Boolean>` | 显式检查元素类型 |
| KT-0486 | 🟡 | TypeReference匿名类在inline | `inline fun <reified T> t(){object:TypeToken<T>(){}}` | 直接reified |
| KT-0754 | 🔴 | GENERICS深度变异String#0 | `val x:String=...;catch(e:` | 捕获具体异常类型 |
| KT-0755 | 🔴 | GENERICS深度变异Byte#1 | `if(x is Byte<Byte>){}` | reified+inline |
| KT-0756 | 🔴 | GENERICS深度变异Boolean?#2 | `val x=y as Boolean?<Boolean?>` | 显式检查元素类型 |
| KT-0757 | 🟡 | GENERICS深度变异Sequence<Long>#3 | `val x:MutableSequence<Long><*>;x.add(1)` | 声明具体类型 |
| KT-0758 | 🟡 | GENERICS深度变异Char#4 | `val x:Char=...;interface P<out T>{fun f(t:T)}` | in T |
| KT-0759 | 🟡 | GENERICS深度变异Double?#5 | `val x:Double?=...;fun <T> f(){T::class}` | inline+reified |
| KT-0760 | 🟡 | GENERICS深度变异Array<Boolean>#6 | `val x:Array<Boolean>=...;fun <T> f(t:T){t.method()}` | <T:HasMethod> |
| KT-0761 | ⚪ | GENERICS深度变异Float#7 | `val x:Float=...;fun <T> f(T:T){}` | 重命名 |
| KT-0762 | 🟡 | GENERICS深度变异Long?#8 | `val x:Long?=...;val x:{g}*=...;x.add(...)` | 指定具体类型参数 |
| KT-0763 | 🟡 | GENERICS深度变异MutableList<Double>#9 | `val x:MutableList<Double>=...;val x:{g}*=...;x.add(...)` | 指定具体类型参数 |
| KT-0764 | 🟡 | GENERICS深度变异Boolean#10 | `val x:Boolean=...;val x:{g}*=...;x.add(...)` | 指定具体类型参数 |
| KT-0765 | 🔴 | GENERICS深度变异Int?#11 | `val x:Int?=...;val arr=Array<T>(10){;val a:Array<Any>=arr` | List代替Array |
| KT-0766 | 🔴 | GENERICS深度变异Map<String,Int>#12 | `val x:Map<String,Int>=...;fun <T> f(){T.Companion}` | reified+inline |
| KT-0767 | 🟡 | GENERICS深度变异Double#13 | `val x:Double=...;val ref: (T)->R=::genericFun` | 显式标注泛型参数 |
| KT-0768 | 🟡 | GENERICS深度变异String?#14 | `fun <T:Comparable<T>> sort(list:String?<T>)` | 保持不变或where |
| KT-0769 | 🟡 | GENERICS深度变异Set<Int>#15 | `val x:Set<Int>=...;when(sealed){is Wrapper<*>->...}` | 具体化子类型 |
| KT-0770 | ⚪ | GENERICS深度变异Long#16 | `fun <T> f(list:Long<*>)` | fun f(list:List<*>)直接 |
| KT-0771 | ⚪ | GENERICS深度变异Any#17 | `typealias S<T>=Any<T>;fun <T> f(s:S<T>)` | 重命名 |
| KT-0772 | 🔴 | GENERICS深度变异List<String>#18 | `class C:A<List<String><String>>,A<List<String><String>>` | 不同接口 |
| KT-0773 | 🟡 | GENERICS深度变异Int#19 | `val x:Int=...;fun <T> f(vararg t:T);f(arrayOf(1))` | f(*arrayOf) |
| KT-0774 | 🟡 | GENERICS深度变异Short#20 | `val x:Short=...;val x by Delegates.notNull<T>()` | 显式类型 |
| KT-0775 | 🟡 | GENERICS深度变异Any?#21 | `val x:Any?=...;fun <T:T> f(){}` | 加where约束打断 |
| KT-0776 | 🔴 | GENERICS深度变异String#22 | `val x:String=...;suspend inline fun <reified T> api():T` | 拆分为非suspend inline+suspend调用 |
| KT-0777 | 🔴 | GENERICS深度变异Byte#23 | `class C:A<Byte>,A<Byte>` | 不同接口 |
| KT-0778 | 🔴 | GENERICS深度变异Boolean?#24 | `class C:A<Long>,A<Boolean?>` | 不同接口 |
| KT-0779 | 🔴 | GENERICS深度变异Sequence<Long>#25 | `class C:A<Double>,A<Sequence<Long>>` | 不同接口 |
| KT-0780 | 🔴 | GENERICS深度变异Char#26 | `class C:A<Boolean>,A<Char>` | 不同接口 |
| KT-0781 | 🔴 | GENERICS深度变异Double?#27 | `class C:A<Double?>,A<Long>` | 不同接口 |
| KT-0782 | 🔴 | GENERICS深度变异Array<Boolean>#28 | `if(x is Array<Boolean><Array<Boolean>>){}` | reified+inline |
| KT-0783 | 🔴 | GENERICS深度变异Float#29 | `if(x is Float<Long>){}` | reified+inline |
| KT-0784 | 🔴 | GENERICS深度变异Long?#30 | `if(x is Long?<Double>){}` | reified+inline |
| KT-0785 | 🔴 | GENERICS深度变异MutableList<Double>#31 | `if(x is MutableList<Double><Boolean>){}` | reified+inline |
| KT-0786 | 🔴 | GENERICS深度变异Boolean#32 | `val x=y as Boolean<Boolean>` | 显式检查元素类型 |
| KT-0787 | 🔴 | GENERICS深度变异Int?#33 | `val x=y as Int?<Long>` | 显式检查元素类型 |
| KT-0788 | 🔴 | GENERICS深度变异Map<String,Int>#34 | `val x=y as Map<String,Int><Double>` | 显式检查元素类型 |
| KT-0789 | 🔴 | GENERICS深度变异Double#35 | `val x=y as Double<Boolean>` | 显式检查元素类型 |
| KT-0790 | 🟡 | GENERICS深度变异String?#36 | `val x:String?=...;inline fun <reified T> t(){object:TypeToke` | 直接reified |
| KT-0791 | 🔴 | GENERICS深度变异Set<Int>#37 | `val x:Set<Int>=...;catch(e:` | 捕获具体异常类型 |
| KT-0792 | 🔴 | GENERICS深度变异Long#38 | `if(x is Long<Long>){}` | reified+inline |
| KT-0793 | 🔴 | GENERICS深度变异Any#39 | `val x=y as Any<Any>` | 显式检查元素类型 |
| KT-0794 | 🟡 | GENERICS深度变异List<String>#40 | `val x:MutableList<String><*>;x.add(1)` | 声明具体类型 |
| KT-0795 | 🟡 | GENERICS深度变异Int#41 | `val x:Int=...;interface P<out T>{fun f(t:T)}` | in T |
| KT-0796 | 🟡 | GENERICS深度变异Short#42 | `val x:Short=...;fun <T> f(){T::class}` | inline+reified |
| KT-0797 | 🟡 | GENERICS深度变异Any?#43 | `val x:Any?=...;fun <T> f(t:T){t.method()}` | <T:HasMethod> |
| KT-0798 | ⚪ | GENERICS深度变异String#44 | `val x:String=...;fun <T> f(T:T){}` | 重命名 |
| KT-0799 | 🟡 | GENERICS深度变异Byte#45 | `val x:Byte=...;val x:{g}*=...;x.add(...)` | 指定具体类型参数 |
| KT-0800 | 🟡 | GENERICS深度变异Boolean?#46 | `val x:Boolean?=...;val x:{g}*=...;x.add(...)` | 指定具体类型参数 |
| KT-0801 | 🟡 | GENERICS深度变异Sequence<Long>#47 | `val x:Sequence<Long>=...;val x:{g}*=...;x.add(...)` | 指定具体类型参数 |
| KT-0802 | 🔴 | GENERICS深度变异Char#48 | `val x:Char=...;val arr=Array<T>(10){;val a:Array<Any>=arr` | List代替Array |
| KT-0803 | 🔴 | GENERICS深度变异Double?#49 | `val x:Double?=...;fun <T> f(){T.Companion}` | reified+inline |
| KT-0804 | 🟡 | GENERICS深度变异Array<Boolean>#50 | `val x:Array<Boolean>=...;val ref: (T)->R=::genericFun` | 显式标注泛型参数 |
| KT-0805 | 🟡 | GENERICS深度变异Float#51 | `fun <T:Comparable<T>> sort(list:Float<T>)` | 保持不变或where |
| KT-0806 | 🟡 | GENERICS深度变异Long?#52 | `val x:Long?=...;when(sealed){is Wrapper<*>->...}` | 具体化子类型 |
| KT-0807 | ⚪ | GENERICS深度变异MutableList<Double>#53 | `fun <T> f(list:MutableList<Double><*>)` | fun f(list:List<*>)直接 |
| KT-0808 | ⚪ | GENERICS深度变异Boolean#54 | `typealias S<T>=Boolean<T>;fun <T> f(s:S<T>)` | 重命名 |
| KT-0809 | 🔴 | GENERICS深度变异Int?#55 | `class C:A<Int??>,A<Int?>` | 不同接口 |
| KT-0810 | 🟡 | GENERICS深度变异Map<String,Int>#56 | `val x:Map<String,Int>=...;fun <T> f(vararg t:T);f(arrayOf(1)` | f(*arrayOf) |
| KT-0811 | 🟡 | GENERICS深度变异Double#57 | `val x:Double=...;val x by Delegates.notNull<T>()` | 显式类型 |
| KT-0812 | 🟡 | GENERICS深度变异String?#58 | `val x:String?=...;fun <T:T> f(){}` | 加where约束打断 |
| KT-0813 | 🔴 | GENERICS深度变异Set<Int>#59 | `val x:Set<Int>=...;suspend inline fun <reified T> api():T` | 拆分为非suspend inline+suspend调用 |
| KT-0814 | 🔴 | GENERICS深度变异Long#60 | `class C:A<Long>,A<Long>` | 不同接口 |
| KT-0815 | 🔴 | GENERICS深度变异Any#61 | `class C:A<Long>,A<Any>` | 不同接口 |
| KT-0816 | 🔴 | GENERICS深度变异List<String>#62 | `class C:A<Double>,A<List<String><String>>` | 不同接口 |
| KT-0817 | 🔴 | GENERICS深度变异Int#63 | `val x:Int=...;class C:A<Boolean>,A<Int>` | 不同接口 |
| KT-0818 | 🔴 | GENERICS深度变异Short#64 | `class C:A<Short>,A<Long>` | 不同接口 |
| KT-0819 | 🔴 | GENERICS深度变异Any?#65 | `if(x is Any?<Any?>){}` | reified+inline |
| KT-0820 | 🔴 | GENERICS深度变异String#66 | `if(x is String<Long>){}` | reified+inline |
| KT-0821 | 🔴 | GENERICS深度变异Byte#67 | `if(x is Byte<Double>){}` | reified+inline |
| KT-0822 | 🔴 | GENERICS深度变异Boolean?#68 | `if(x is Boolean?<Boolean>){}` | reified+inline |
| KT-0823 | 🔴 | GENERICS深度变异Sequence<Long>#69 | `val x=y as Sequence<Long><Sequence<Long>>` | 显式检查元素类型 |
| KT-0824 | 🔴 | GENERICS深度变异Char#70 | `val x=y as Char<Long>` | 显式检查元素类型 |
| KT-0825 | 🔴 | GENERICS深度变异Double?#71 | `val x=y as Double?<Double>` | 显式检查元素类型 |
| KT-0826 | 🔴 | GENERICS深度变异Array<Boolean>#72 | `val x=y as Array<Boolean><Boolean>` | 显式检查元素类型 |
| KT-0827 | 🟡 | GENERICS深度变异Float#73 | `val x:Float=...;inline fun <reified T> t(){object:TypeToken<` | 直接reified |
| KT-0828 | 🔴 | GENERICS深度变异Long?#74 | `val x:Long?=...;catch(e:` | 捕获具体异常类型 |
| KT-0829 | 🔴 | GENERICS深度变异MutableList<Double>#75 | `if(x is MutableList<Double><MutableMutableList<Double><Doubl` | reified+inline |
| KT-0830 | 🔴 | GENERICS深度变异Boolean#76 | `val x=y as Boolean<Boolean>` | 显式检查元素类型 |
| KT-0831 | 🟡 | GENERICS深度变异Int?#77 | `val x:MutableInt?<*>;x.add(1)` | 声明具体类型 |
| KT-0832 | 🟡 | GENERICS深度变异Map<String,Int>#78 | `val x:Map<String,Int>=...;interface P<out T>{fun f(t:T)}` | in T |
| KT-0833 | 🟡 | GENERICS深度变异Double#79 | `val x:Double=...;fun <T> f(){T::class}` | inline+reified |
| KT-0834 | 🟡 | GENERICS深度变异String?#80 | `val x:String?=...;fun <T> f(t:T){t.method()}` | <T:HasMethod> |
| KT-0835 | ⚪ | GENERICS深度变异Set<Int>#81 | `val x:Set<Int>=...;fun <T> f(T:T){}` | 重命名 |
| KT-0836 | 🟡 | GENERICS深度变异Long#82 | `val x:Long=...;val x:{g}*=...;x.add(...)` | 指定具体类型参数 |
| KT-0837 | 🟡 | GENERICS深度变异Any#83 | `val x:Any=...;val x:{g}*=...;x.add(...)` | 指定具体类型参数 |
| KT-0838 | 🟡 | GENERICS深度变异List<String>#84 | `val x:List<String>=...;val x:{g}*=...;x.add(...)` | 指定具体类型参数 |
| KT-0839 | 🔴 | GENERICS深度变异Int#85 | `val x:Int=...;val arr=Array<T>(10){;val a:Array<Any>=arr` | List代替Array |
| KT-0840 | 🔴 | GENERICS深度变异Short#86 | `val x:Short=...;fun <T> f(){T.Companion}` | reified+inline |
| KT-0841 | 🟡 | GENERICS深度变异Any?#87 | `val x:Any?=...;val ref: (T)->R=::genericFun` | 显式标注泛型参数 |
| KT-0842 | 🟡 | GENERICS深度变异String#88 | `fun <T:Comparable<T>> sort(list:String<T>)` | 保持不变或where |
| KT-0843 | 🟡 | GENERICS深度变异Byte#89 | `val x:Byte=...;when(sealed){is Wrapper<*>->...}` | 具体化子类型 |
| KT-0844 | ⚪ | GENERICS深度变异Boolean?#90 | `fun <T> f(list:Boolean?<*>)` | fun f(list:List<*>)直接 |
| KT-0845 | ⚪ | GENERICS深度变异Sequence<Long>#91 | `typealias S<T>=Sequence<Long><T>;fun <T> f(s:S<T>)` | 重命名 |
| KT-0846 | 🔴 | GENERICS深度变异Char#92 | `class C:A<Char>,A<Char>` | 不同接口 |
| KT-0847 | 🟡 | GENERICS深度变异Double?#93 | `val x:Double?=...;fun <T> f(vararg t:T);f(arrayOf(1))` | f(*arrayOf) |
| KT-0848 | 🟡 | GENERICS深度变异Array<Boolean>#94 | `val x:Array<Boolean>=...;val x by Delegates.notNull<T>()` | 显式类型 |
| KT-0849 | 🟡 | GENERICS深度变异Float#95 | `val x:Float=...;fun <T:T> f(){}` | 加where约束打断 |
| KT-0850 | 🔴 | GENERICS深度变异Long?#96 | `val x:Long?=...;suspend inline fun <reified T> api():T` | 拆分为非suspend inline+suspend调用 |
| KT-0851 | 🔴 | GENERICS深度变异MutableList<Double>#97 | `class C:A<MutableMutableList<Double><Double>>,A<MutableMutab` | 不同接口 |
| KT-0852 | 🔴 | GENERICS深度变异Boolean#98 | `class C:A<Long>,A<Boolean>` | 不同接口 |
| KT-0853 | 🔴 | GENERICS深度变异Int?#99 | `class C:A<Double>,A<Int?>` | 不同接口 |
| KT-0854 | 🔴 | GENERICS深度变异Map<String,Int>#100 | `class C:A<Boolean>,A<Map<String,Int>>` | 不同接口 |
| KT-0855 | 🔴 | GENERICS深度变异Double#101 | `class C:A<Double>,A<Long>` | 不同接口 |
| KT-0856 | 🔴 | GENERICS深度变异String?#102 | `if(x is String?<String?>){}` | reified+inline |
| KT-0857 | 🔴 | GENERICS深度变异Set<Int>#103 | `if(x is Set<Int><Long>){}` | reified+inline |
| KT-0858 | 🔴 | GENERICS深度变异Long#104 | `if(x is Long<Double>){}` | reified+inline |
| KT-0859 | 🔴 | GENERICS深度变异Any#105 | `if(x is Any<Boolean>){}` | reified+inline |
| KT-0860 | 🔴 | GENERICS深度变异List<String>#106 | `val x=y as List<String><List<String><String>>` | 显式检查元素类型 |
| KT-0861 | 🔴 | GENERICS深度变异Int#107 | `val x=y as Int<Long>` | 显式检查元素类型 |
| KT-0862 | 🔴 | GENERICS深度变异Short#108 | `val x=y as Short<Double>` | 显式检查元素类型 |
| KT-0863 | 🔴 | GENERICS深度变异Any?#109 | `val x=y as Any?<Boolean>` | 显式检查元素类型 |
| KT-0864 | 🟡 | GENERICS深度变异String#110 | `val x:String=...;inline fun <reified T> t(){object:TypeToken` | 直接reified |
| KT-0865 | 🔴 | GENERICS深度变异Byte#111 | `val x:Byte=...;catch(e:` | 捕获具体异常类型 |
| KT-0866 | 🔴 | GENERICS深度变异Boolean?#112 | `if(x is Boolean?<Boolean?>){}` | reified+inline |
| KT-0867 | 🔴 | GENERICS深度变异Sequence<Long>#113 | `val x=y as Sequence<Long><Sequence<Long>>` | 显式检查元素类型 |
| KT-0868 | 🟡 | GENERICS深度变异Char#114 | `val x:MutableChar<*>;x.add(1)` | 声明具体类型 |
| KT-0869 | 🟡 | GENERICS深度变异Double?#115 | `val x:Double?=...;interface P<out T>{fun f(t:T)}` | in T |
| KT-0870 | 🟡 | GENERICS深度变异Array<Boolean>#116 | `val x:Array<Boolean>=...;fun <T> f(){T::class}` | inline+reified |
| KT-0871 | 🟡 | GENERICS深度变异Float#117 | `val x:Float=...;fun <T> f(t:T){t.method()}` | <T:HasMethod> |
| KT-0872 | ⚪ | GENERICS深度变异Long?#118 | `val x:Long?=...;fun <T> f(T:T){}` | 重命名 |
| KT-0873 | 🟡 | GENERICS深度变异MutableList<Double>#119 | `val x:MutableList<Double>=...;val x:{g}*=...;x.add(...)` | 指定具体类型参数 |
| KT-0874 | 🟡 | GENERICS深度变异Boolean#120 | `val x:Boolean=...;val x:{g}*=...;x.add(...)` | 指定具体类型参数 |
| KT-0875 | 🟡 | GENERICS深度变异Int?#121 | `val x:Int?=...;val x:{g}*=...;x.add(...)` | 指定具体类型参数 |
| KT-0876 | 🔴 | GENERICS深度变异Map<String,Int>#122 | `val x:Map<String,Int>=...;val arr=Array<T>(10){;val a:Array<` | List代替Array |
| KT-0877 | 🔴 | GENERICS深度变异Double#123 | `val x:Double=...;fun <T> f(){T.Companion}` | reified+inline |
| KT-0878 | 🟡 | GENERICS深度变异String?#124 | `val x:String?=...;val ref: (T)->R=::genericFun` | 显式标注泛型参数 |
| KT-0879 | 🟡 | GENERICS深度变异Set<Int>#125 | `fun <T:Comparable<T>> sort(list:Set<Int><T>)` | 保持不变或where |
| KT-0880 | 🟡 | GENERICS深度变异Long#126 | `val x:Long=...;when(sealed){is Wrapper<*>->...}` | 具体化子类型 |
| KT-0881 | ⚪ | GENERICS深度变异Any#127 | `fun <T> f(list:Any<*>)` | fun f(list:List<*>)直接 |
| KT-0882 | ⚪ | GENERICS深度变异List<String>#128 | `typealias S<T>=List<String><T>;fun <T> f(s:S<T>)` | 重命名 |
| KT-0883 | 🔴 | GENERICS深度变异Int#129 | `class C:A<Int>,A<Int>` | 不同接口 |
| KT-0884 | 🟡 | GENERICS深度变异Short#130 | `val x:Short=...;fun <T> f(vararg t:T);f(arrayOf(1))` | f(*arrayOf) |
| KT-0885 | 🟡 | GENERICS深度变异Any?#131 | `val x:Any?=...;val x by Delegates.notNull<T>()` | 显式类型 |
| KT-0886 | 🟡 | GENERICS深度变异String#132 | `val x:String=...;fun <T:T> f(){}` | 加where约束打断 |
| KT-0887 | 🔴 | GENERICS深度变异Byte#133 | `val x:Byte=...;suspend inline fun <reified T> api():T` | 拆分为非suspend inline+suspend调用 |
| KT-0888 | 🔴 | GENERICS深度变异Boolean?#134 | `class C:A<Boolean?>,A<Boolean?>` | 不同接口 |
| KT-0889 | 🔴 | GENERICS深度变异Sequence<Long>#135 | `class C:A<Long>,A<Sequence<Long>>` | 不同接口 |
| KT-0890 | 🔴 | GENERICS深度变异Char#136 | `class C:A<Double>,A<Char>` | 不同接口 |
| KT-0891 | 🔴 | GENERICS深度变异Double?#137 | `class C:A<Boolean>,A<Double?>` | 不同接口 |
| KT-0892 | 🔴 | GENERICS深度变异Array<Boolean>#138 | `class C:A<Array<Boolean>>,A<Long>` | 不同接口 |
| KT-0893 | 🔴 | GENERICS深度变异Float#139 | `if(x is Float<Float>){}` | reified+inline |
| KT-0894 | 🔴 | GENERICS深度变异Long?#140 | `if(x is Long?<Long>){}` | reified+inline |
| KT-0895 | 🔴 | GENERICS深度变异MutableList<Double>#141 | `if(x is MutableList<Double><Double>){}` | reified+inline |
| KT-0896 | 🔴 | GENERICS深度变异Boolean#142 | `if(x is Boolean<Boolean>){}` | reified+inline |
| KT-0897 | 🔴 | GENERICS深度变异Int?#143 | `val x=y as Int?<Int?>` | 显式检查元素类型 |
| KT-0898 | 🔴 | GENERICS深度变异Map<String,Int>#144 | `val x=y as Map<String,Int><Long>` | 显式检查元素类型 |
| KT-0899 | 🔴 | GENERICS深度变异Double#145 | `val x=y as Double<Double>` | 显式检查元素类型 |
| KT-0900 | 🔴 | GENERICS深度变异String?#146 | `val x=y as String?<Boolean>` | 显式检查元素类型 |
| KT-0901 | 🟡 | GENERICS深度变异Set<Int>#147 | `val x:Set<Int>=...;inline fun <reified T> t(){object:TypeTok` | 直接reified |
| KT-0902 | 🔴 | GENERICS深度变异Long#148 | `val x:Long=...;catch(e:` | 捕获具体异常类型 |
| KT-0903 | 🔴 | GENERICS深度变异Any#149 | `if(x is Any<Any>){}` | reified+inline |
| KT-0904 | 🔴 | GENERICS深度变异List<String>#150 | `val x=y as List<String><List<String><String>>` | 显式检查元素类型 |
| KT-0905 | 🟡 | GENERICS深度变异Int#151 | `val x:MutableInt<*>;x.add(1)` | 声明具体类型 |
| KT-0906 | 🟡 | GENERICS深度变异Short#152 | `val x:Short=...;interface P<out T>{fun f(t:T)}` | in T |
| KT-0907 | 🟡 | GENERICS深度变异Any?#153 | `val x:Any?=...;fun <T> f(){T::class}` | inline+reified |
| KT-0908 | 🟡 | GENERICS深度变异String#154 | `val x:String=...;fun <T> f(t:T){t.method()}` | <T:HasMethod> |
| KT-0909 | ⚪ | GENERICS深度变异Byte#155 | `val x:Byte=...;fun <T> f(T:T){}` | 重命名 |
| KT-0910 | 🟡 | GENERICS深度变异Boolean?#156 | `val x:Boolean?=...;val x:{g}*=...;x.add(...)` | 指定具体类型参数 |
| KT-0911 | 🟡 | GENERICS深度变异Sequence<Long>#157 | `val x:Sequence<Long>=...;val x:{g}*=...;x.add(...)` | 指定具体类型参数 |
| KT-0912 | 🟡 | GENERICS深度变异Char#158 | `val x:Char=...;val x:{g}*=...;x.add(...)` | 指定具体类型参数 |
| KT-0913 | 🔴 | GENERICS深度变异Double?#159 | `val x:Double?=...;val arr=Array<T>(10){;val a:Array<Any>=arr` | List代替Array |
| KT-0914 | 🔴 | GENERICS深度变异Array<Boolean>#160 | `val x:Array<Boolean>=...;fun <T> f(){T.Companion}` | reified+inline |
| KT-0915 | 🟡 | GENERICS深度变异Float#161 | `val x:Float=...;val ref: (T)->R=::genericFun` | 显式标注泛型参数 |
| KT-0916 | 🟡 | GENERICS深度变异Long?#162 | `fun <T:Comparable<T>> sort(list:Long?<T>)` | 保持不变或where |

## INLINE_TAILREC（100条）

| ID | 严重度 | Bug描述 | 触发模式 | 修复建议 |
|---|------|------|------|------|
| KT-0074 | 🔴 | tailrec非尾递归 | `tailrec fun f(n:Int)=n*f(n-1)` | while循环改写 |
| KT-0075 | 🟡 | inline函数体过大 | `inline fun big(){...200行}` | 去掉inline |
| KT-0076 | 🟡 | crossinline遗漏 | `inline fun f(crossinline b:()->Unit){launch{b()}}` | 加crossinline |
| KT-0077 | 🟡 | noinline参数存储 | `inline fun f(noinline b:()->Unit){holder=b}` | noinline |
| KT-0078 | ⚪ | 不必要的inline | `inline fun tiny(){simple()}` | 去掉inline |
| KT-0079 | 🟡 | tailrec返回类型String递归非尾 | `tailrec fun f(n:{t}):{t}=if(n<=1)n else n*f(n-1)` | while改写 |
| KT-0080 | 🟡 | tailrec返回类型Int递归非尾 | `tailrec fun f(n:{t}):{t}=if(n<=1)n else n*f(n-1)` | while改写 |
| KT-0081 | 🟡 | tailrec返回类型Long递归非尾 | `tailrec fun f(n:{t}):{t}=if(n<=1)n else n*f(n-1)` | while改写 |
| KT-0197 | 🔴 | reified泛型在非inline函数 | `fun <reified T> f(){}` | 加inline |
| KT-0198 | 🟡 | 内联函数中return禁止 | `inline fun f(){return}` | 去掉return或inline |
| KT-0199 | 🟡 | crossinline+suspend | `inline fun f(crossinline b:suspend()->Unit)` | 简化组合 |
| KT-0200 | ⚪ | 内联属性内存开销 | `inline val x:Int get()=calc()` | 缓存或用普通val |
| KT-0201 | ⚪ | 不必要的reified | `inline fun <reified T> f(){}未用T` | 去掉reified |
| KT-0301 | 🟡 | inline+crossinline+suspend=三层限制互锁 | `inline fun f(crossinline b:suspend ()->Unit){launch{b()}}` | 去掉crossinline或suspend |
| KT-0377 | ⚪ | 内联属性内存开销（Long版） | `inline val x:Long get()=calc()` | 缓存或用普通val |
| KT-0378 | ⚪ | 内联属性内存开销（Double版） | `inline val x:Double get()=calc()` | 缓存或用普通val |
| KT-0379 | ⚪ | 内联属性内存开销（Float版） | `inline val x:Float get()=calc()` | 缓存或用普通val |
| KT-0423 | 🔴 | tailrec非尾递归（Long版） | `tailrec fun f(n:Long)=n*f(n-1)` | while循环改写 |
| KT-0424 | 🔴 | tailrec非尾递归（Double版） | `tailrec fun f(n:Double)=n*f(n-1)` | while循环改写 |
| KT-0425 | 🔴 | tailrec非尾递归（Float版） | `tailrec fun f(n:Float)=n*f(n-1)` | while循环改写 |
| KT-1702 | 🔴 | INLINE_TAILREC深度变异String#0 | `tailrec fun f(n:String)=n*f(n-1)` | while循环改写 |
| KT-1703 | 🟡 | INLINE_TAILREC深度变异Byte#1 | `val x:Byte=...;inline fun big(){...200行}` | 去掉inline |
| KT-1704 | 🟡 | INLINE_TAILREC深度变异Boolean?#2 | `val x:Boolean?=...;inline fun f(crossinline b:()->Unit){laun` | 加crossinline |
| KT-1705 | 🟡 | INLINE_TAILREC深度变异Sequence<Long>#3 | `val x:Sequence<Long>=...;inline fun f(noinline b:()->Unit){h` | noinline |
| KT-1706 | ⚪ | INLINE_TAILREC深度变异Char#4 | `val x:Char=...;inline fun tiny(){simple()}` | 去掉inline |
| KT-1707 | 🟡 | INLINE_TAILREC深度变异Double?#5 | `val x:Double?=...;tailrec fun f(n:{t}):{t}=if(n<=1)n else n*` | while改写 |
| KT-1708 | 🟡 | INLINE_TAILREC深度变异Array<Boolean>#6 | `val x:Array<Boolean>=...;tailrec fun f(n:{t}):{t}=if(n<=1)n ` | while改写 |
| KT-1709 | 🟡 | INLINE_TAILREC深度变异Float#7 | `val x:Float=...;tailrec fun f(n:{t}):{t}=if(n<=1)n else n*f(` | while改写 |
| KT-1710 | 🔴 | INLINE_TAILREC深度变异Long?#8 | `val x:Long?=...;fun <reified T> f(){}` | 加inline |
| KT-1711 | 🟡 | INLINE_TAILREC深度变异MutableList<Double>#9 | `val x:MutableList<Double>=...;inline fun f(){return}` | 去掉return或inline |
| KT-1712 | 🟡 | INLINE_TAILREC深度变异Boolean#10 | `val x:Boolean=...;inline fun f(crossinline b:suspend()->Unit` | 简化组合 |
| KT-1713 | ⚪ | INLINE_TAILREC深度变异Int?#11 | `inline val x:Int? get()=calc()` | 缓存或用普通val |
| KT-1714 | ⚪ | INLINE_TAILREC深度变异Map<String,Int>#12 | `val x:Map<String,Int>=...;inline fun <reified T> f(){}未用T` | 去掉reified |
| KT-1715 | 🟡 | INLINE_TAILREC深度变异Double#13 | `val x:Double=...;inline fun f(crossinline b:suspend ()->Unit` | 去掉crossinline或suspend |
| KT-1716 | ⚪ | INLINE_TAILREC深度变异String?#14 | `val x:String?=...;inline val x:Long get()=calc()` | 缓存或用普通val |
| KT-1717 | ⚪ | INLINE_TAILREC深度变异Set<Int>#15 | `val x:Set<Int>=...;inline val x:Double get()=calc()` | 缓存或用普通val |
| KT-1718 | ⚪ | INLINE_TAILREC深度变异Long#16 | `val x:Long=...;inline val x:Float get()=calc()` | 缓存或用普通val |
| KT-1719 | 🔴 | INLINE_TAILREC深度变异Any#17 | `val x:Any=...;tailrec fun f(n:Long)=n*f(n-1)` | while循环改写 |
| KT-1720 | 🔴 | INLINE_TAILREC深度变异List<String>#18 | `val x:List<String>=...;tailrec fun f(n:Double)=n*f(n-1)` | while循环改写 |
| KT-1721 | 🔴 | INLINE_TAILREC深度变异Int#19 | `val x:Int=...;tailrec fun f(n:Float)=n*f(n-1)` | while循环改写 |
| KT-1722 | 🔴 | INLINE_TAILREC深度变异Short#20 | `tailrec fun f(n:Short)=n*f(n-1)` | while循环改写 |
| KT-1723 | 🟡 | INLINE_TAILREC深度变异Any?#21 | `val x:Any?=...;inline fun big(){...200行}` | 去掉inline |
| KT-1724 | 🟡 | INLINE_TAILREC深度变异String#22 | `val x:String=...;inline fun f(crossinline b:()->Unit){launch` | 加crossinline |
| KT-1725 | 🟡 | INLINE_TAILREC深度变异Byte#23 | `val x:Byte=...;inline fun f(noinline b:()->Unit){holder=b}` | noinline |
| KT-1726 | ⚪ | INLINE_TAILREC深度变异Boolean?#24 | `val x:Boolean?=...;inline fun tiny(){simple()}` | 去掉inline |
| KT-1727 | 🟡 | INLINE_TAILREC深度变异Sequence<Long>#25 | `val x:Sequence<Long>=...;tailrec fun f(n:{t}):{t}=if(n<=1)n ` | while改写 |
| KT-1728 | 🟡 | INLINE_TAILREC深度变异Char#26 | `val x:Char=...;tailrec fun f(n:{t}):{t}=if(n<=1)n else n*f(n` | while改写 |
| KT-1729 | 🟡 | INLINE_TAILREC深度变异Double?#27 | `val x:Double?=...;tailrec fun f(n:{t}):{t}=if(n<=1)n else n*` | while改写 |
| KT-1730 | 🔴 | INLINE_TAILREC深度变异Array<Boolean>#28 | `val x:Array<Boolean>=...;fun <reified T> f(){}` | 加inline |
| KT-1731 | 🟡 | INLINE_TAILREC深度变异Float#29 | `val x:Float=...;inline fun f(){return}` | 去掉return或inline |
| KT-1732 | 🟡 | INLINE_TAILREC深度变异Long?#30 | `val x:Long?=...;inline fun f(crossinline b:suspend()->Unit)` | 简化组合 |
| KT-1733 | ⚪ | INLINE_TAILREC深度变异MutableList<Double>#31 | `inline val x:MutableMutableList<Double><Double> get()=calc()` | 缓存或用普通val |
| KT-1734 | ⚪ | INLINE_TAILREC深度变异Boolean#32 | `val x:Boolean=...;inline fun <reified T> f(){}未用T` | 去掉reified |
| KT-1735 | 🟡 | INLINE_TAILREC深度变异Int?#33 | `val x:Int?=...;inline fun f(crossinline b:suspend ()->Unit){` | 去掉crossinline或suspend |
| KT-1736 | ⚪ | INLINE_TAILREC深度变异Map<String,Int>#34 | `val x:Map<String,Int>=...;inline val x:Long get()=calc()` | 缓存或用普通val |
| KT-1737 | ⚪ | INLINE_TAILREC深度变异Double#35 | `val x:Double=...;inline val x:Double get()=calc()` | 缓存或用普通val |
| KT-1738 | ⚪ | INLINE_TAILREC深度变异String?#36 | `val x:String?=...;inline val x:Float get()=calc()` | 缓存或用普通val |
| KT-1739 | 🔴 | INLINE_TAILREC深度变异Set<Int>#37 | `val x:Set<Int>=...;tailrec fun f(n:Long)=n*f(n-1)` | while循环改写 |
| KT-1740 | 🔴 | INLINE_TAILREC深度变异Long#38 | `val x:Long=...;tailrec fun f(n:Double)=n*f(n-1)` | while循环改写 |
| KT-1741 | 🔴 | INLINE_TAILREC深度变异Any#39 | `val x:Any=...;tailrec fun f(n:Float)=n*f(n-1)` | while循环改写 |
| KT-1742 | 🔴 | INLINE_TAILREC深度变异List<String>#40 | `tailrec fun f(n:List<String><String>)=n*f(n-1)` | while循环改写 |
| KT-1743 | 🟡 | INLINE_TAILREC深度变异Int#41 | `val x:Int=...;inline fun big(){...200行}` | 去掉inline |
| KT-1744 | 🟡 | INLINE_TAILREC深度变异Short#42 | `val x:Short=...;inline fun f(crossinline b:()->Unit){launch{` | 加crossinline |
| KT-1745 | 🟡 | INLINE_TAILREC深度变异Any?#43 | `val x:Any?=...;inline fun f(noinline b:()->Unit){holder=b}` | noinline |
| KT-1746 | ⚪ | INLINE_TAILREC深度变异String#44 | `val x:String=...;inline fun tiny(){simple()}` | 去掉inline |
| KT-1747 | 🟡 | INLINE_TAILREC深度变异Byte#45 | `val x:Byte=...;tailrec fun f(n:{t}):{t}=if(n<=1)n else n*f(n` | while改写 |
| KT-1748 | 🟡 | INLINE_TAILREC深度变异Boolean?#46 | `val x:Boolean?=...;tailrec fun f(n:{t}):{t}=if(n<=1)n else n` | while改写 |
| KT-1749 | 🟡 | INLINE_TAILREC深度变异Sequence<Long>#47 | `val x:Sequence<Long>=...;tailrec fun f(n:{t}):{t}=if(n<=1)n ` | while改写 |
| KT-1750 | 🔴 | INLINE_TAILREC深度变异Char#48 | `val x:Char=...;fun <reified T> f(){}` | 加inline |
| KT-1751 | 🟡 | INLINE_TAILREC深度变异Double?#49 | `val x:Double?=...;inline fun f(){return}` | 去掉return或inline |
| KT-1752 | 🟡 | INLINE_TAILREC深度变异Array<Boolean>#50 | `val x:Array<Boolean>=...;inline fun f(crossinline b:suspend(` | 简化组合 |
| KT-1753 | ⚪ | INLINE_TAILREC深度变异Float#51 | `inline val x:Float get()=calc()` | 缓存或用普通val |
| KT-1754 | ⚪ | INLINE_TAILREC深度变异Long?#52 | `val x:Long?=...;inline fun <reified T> f(){}未用T` | 去掉reified |
| KT-1755 | 🟡 | INLINE_TAILREC深度变异MutableList<Double>#53 | `val x:MutableList<Double>=...;inline fun f(crossinline b:sus` | 去掉crossinline或suspend |
| KT-1756 | ⚪ | INLINE_TAILREC深度变异Boolean#54 | `val x:Boolean=...;inline val x:Long get()=calc()` | 缓存或用普通val |
| KT-1757 | ⚪ | INLINE_TAILREC深度变异Int?#55 | `val x:Int?=...;inline val x:Double get()=calc()` | 缓存或用普通val |
| KT-1758 | ⚪ | INLINE_TAILREC深度变异Map<String,Int>#56 | `val x:Map<String,Int>=...;inline val x:Float get()=calc()` | 缓存或用普通val |
| KT-1759 | 🔴 | INLINE_TAILREC深度变异Double#57 | `val x:Double=...;tailrec fun f(n:Long)=n*f(n-1)` | while循环改写 |
| KT-1760 | 🔴 | INLINE_TAILREC深度变异String?#58 | `val x:String?=...;tailrec fun f(n:Double)=n*f(n-1)` | while循环改写 |
| KT-1761 | 🔴 | INLINE_TAILREC深度变异Set<Int>#59 | `val x:Set<Int>=...;tailrec fun f(n:Float)=n*f(n-1)` | while循环改写 |
| KT-1762 | 🔴 | INLINE_TAILREC深度变异Long#60 | `tailrec fun f(n:Long)=n*f(n-1)` | while循环改写 |
| KT-1763 | 🟡 | INLINE_TAILREC深度变异Any#61 | `val x:Any=...;inline fun big(){...200行}` | 去掉inline |
| KT-1764 | 🟡 | INLINE_TAILREC深度变异List<String>#62 | `val x:List<String>=...;inline fun f(crossinline b:()->Unit){` | 加crossinline |
| KT-1765 | 🟡 | INLINE_TAILREC深度变异Int#63 | `val x:Int=...;inline fun f(noinline b:()->Unit){holder=b}` | noinline |
| KT-1766 | ⚪ | INLINE_TAILREC深度变异Short#64 | `val x:Short=...;inline fun tiny(){simple()}` | 去掉inline |
| KT-1767 | 🟡 | INLINE_TAILREC深度变异Any?#65 | `val x:Any?=...;tailrec fun f(n:{t}):{t}=if(n<=1)n else n*f(n` | while改写 |
| KT-1768 | 🟡 | INLINE_TAILREC深度变异String#66 | `val x:String=...;tailrec fun f(n:{t}):{t}=if(n<=1)n else n*f` | while改写 |
| KT-1769 | 🟡 | INLINE_TAILREC深度变异Byte#67 | `val x:Byte=...;tailrec fun f(n:{t}):{t}=if(n<=1)n else n*f(n` | while改写 |
| KT-1770 | 🔴 | INLINE_TAILREC深度变异Boolean?#68 | `val x:Boolean?=...;fun <reified T> f(){}` | 加inline |
| KT-1771 | 🟡 | INLINE_TAILREC深度变异Sequence<Long>#69 | `val x:Sequence<Long>=...;inline fun f(){return}` | 去掉return或inline |
| KT-1772 | 🟡 | INLINE_TAILREC深度变异Char#70 | `val x:Char=...;inline fun f(crossinline b:suspend()->Unit)` | 简化组合 |
| KT-1773 | ⚪ | INLINE_TAILREC深度变异Double?#71 | `inline val x:Double? get()=calc()` | 缓存或用普通val |
| KT-1774 | ⚪ | INLINE_TAILREC深度变异Array<Boolean>#72 | `val x:Array<Boolean>=...;inline fun <reified T> f(){}未用T` | 去掉reified |
| KT-1775 | 🟡 | INLINE_TAILREC深度变异Float#73 | `val x:Float=...;inline fun f(crossinline b:suspend ()->Unit)` | 去掉crossinline或suspend |
| KT-1776 | ⚪ | INLINE_TAILREC深度变异Long?#74 | `val x:Long?=...;inline val x:Long get()=calc()` | 缓存或用普通val |
| KT-1777 | ⚪ | INLINE_TAILREC深度变异MutableList<Double>#75 | `val x:MutableList<Double>=...;inline val x:Double get()=calc` | 缓存或用普通val |
| KT-1778 | ⚪ | INLINE_TAILREC深度变异Boolean#76 | `val x:Boolean=...;inline val x:Float get()=calc()` | 缓存或用普通val |
| KT-1779 | 🔴 | INLINE_TAILREC深度变异Int?#77 | `val x:Int?=...;tailrec fun f(n:Long)=n*f(n-1)` | while循环改写 |
| KT-1780 | 🔴 | INLINE_TAILREC深度变异Map<String,Int>#78 | `val x:Map<String,Int>=...;tailrec fun f(n:Double)=n*f(n-1)` | while循环改写 |
| KT-1781 | 🔴 | INLINE_TAILREC深度变异Double#79 | `val x:Double=...;tailrec fun f(n:Float)=n*f(n-1)` | while循环改写 |

## JAVA_INTEROP（150条）

| ID | 严重度 | Bug描述 | 触发模式 | 修复建议 |
|---|------|------|------|------|
| KT-0082 | 🔴 | Java返回null未标注 | `val s=javaObj.getName();s.length` | ?:+"" |
| KT-0083 | 🔴 | @NotNull注解缺失 | `fun javaMethod():String=null` | 显式标注? |
| KT-0084 | 🟡 | @JvmStatic缺失 | `INSTANCE.` | 加@JvmStatic |
| KT-0085 | 🟡 | SAM转换歧义 | `fun f(r:Runnable);fun f(c:Callable);f{` | 显式lambda类型 |
| KT-0086 | 🟡 | Kotlin集合与Java互转 | `kotlinList.toList()在Java侧` | 直接传递 |
| KT-0087 | 🟡 | @JvmOverloads缺失 | `fun f(a:Int,b:Int=0)` | 加@JvmOverloads |
| KT-0088 | ⚪ | @JvmField冗余 | `@JvmField val x=42` | 直接const |
| KT-0202 | 🔴 | Java异常类型在Kotlin中不可检查 | `try{javaMethod()}catch(e:IOException){}` | 文档标注或runCatching |
| KT-0203 | 🟡 | @JvmName与Kotlin名冲突 | `@JvmName("f") fun fKotlin(){};fun f(){}` | 统一命名 |
| KT-0204 | 🟡 | Java通配符转Kotlin型变 | `Consumer<?super T>在Kotlin` | Consumer<in T> |
| KT-0205 | 🟡 | companion object的JvmStatic | `companion{fun create()};Java:Companion.create()` | @JvmStatic |
| KT-0206 | ⚪ | @Throws注解在Kotlin多余 | `@Throws(IOException::class) fun f()` | 仅Java交互需要 |
| KT-0207 | ⚪ | Kotlin属性在Java中get/set | `var name:String在Java:getName()+setName()` | 保持一致 |
| KT-0369 | ⚪ | Kotlin属性在Java中get/set（Int版） | `var name:Int在Java:getName()+setName()` | 保持一致 |
| KT-0370 | ⚪ | Kotlin属性在Java中get/set（Long版） | `var name:Long在Java:getName()+setName()` | 保持一致 |
| KT-0371 | ⚪ | Kotlin属性在Java中get/set（Double版） | `var name:Double在Java:getName()+setName()` | 保持一致 |
| KT-0372 | ⚪ | Kotlin属性在Java中get/set（Boolean版） | `var name:Boolean在Java:getName()+setName()` | 保持一致 |
| KT-0396 | 🔴 | @NotNull注解缺失（Int版） | `fun javaMethod():Int=null` | 显式标注? |
| KT-0397 | 🔴 | @NotNull注解缺失（Long版） | `fun javaMethod():Long=null` | 显式标注? |
| KT-0398 | 🔴 | @NotNull注解缺失（Double版） | `fun javaMethod():Double=null` | 显式标注? |
| KT-0399 | 🔴 | @NotNull注解缺失（Boolean版） | `fun javaMethod():Boolean=null` | 显式标注? |
| KT-0417 | 🟡 | Kotlin集合与Java互转（Set版） | `kotlinSet.toSet()在Java侧` | 直接传递 |
| KT-0449 | 🟡 | @JvmOverloads缺失（Long版） | `fun f(a:Long,b:Long=0)` | 加@JvmOverloads |
| KT-1782 | 🔴 | JAVA_INTEROP深度变异String#0 | `val x:String=...;val s=javaObj.getName();s.length` | ?:+\"\" |
| KT-1783 | 🔴 | JAVA_INTEROP深度变异Byte#1 | `fun javaMethod():Byte=null` | 显式标注? |
| KT-1784 | 🟡 | JAVA_INTEROP深度变异Boolean?#2 | `val x:Boolean?=...;INSTANCE.` | 加@JvmStatic |
| KT-1785 | 🟡 | JAVA_INTEROP深度变异Sequence<Long>#3 | `val x:Sequence<Long>=...;fun f(r:Runnable);fun f(c:Callable)` | 显式lambda类型 |
| KT-1786 | 🟡 | JAVA_INTEROP深度变异Char#4 | `kotlinChar.toChar()在Java侧` | 直接传递 |
| KT-1787 | 🟡 | JAVA_INTEROP深度变异Double?#5 | `fun f(a:Double?,b:Double?=0)` | 加@JvmOverloads |
| KT-1788 | ⚪ | JAVA_INTEROP深度变异Array<Boolean>#6 | `val x:Array<Boolean>=...;@JvmField val x=42` | 直接const |
| KT-1789 | 🔴 | JAVA_INTEROP深度变异Float#7 | `val x:Float=...;try{javaMethod()}catch(e:IOException){}` | 文档标注或runCatching |
| KT-1790 | 🟡 | JAVA_INTEROP深度变异Long?#8 | `val x:Long?=...;@JvmName(\"f\") fun fKotlin(){};fun f(){}` | 统一命名 |
| KT-1791 | 🟡 | JAVA_INTEROP深度变异MutableList<Double>#9 | `val x:MutableList<Double>=...;Consumer<?super T>在Kotlin` | Consumer<in T> |
| KT-1792 | 🟡 | JAVA_INTEROP深度变异Boolean#10 | `val x:Boolean=...;companion{fun create()};Java:Companion.cre` | @JvmStatic |
| KT-1793 | ⚪ | JAVA_INTEROP深度变异Int?#11 | `val x:Int?=...;@Throws(IOException::class) fun f()` | 仅Java交互需要 |
| KT-1794 | ⚪ | JAVA_INTEROP深度变异Map<String,Int>#12 | `var name:Map<String,Map<String,Int>>在Java:getName()+setName(` | 保持一致 |
| KT-1795 | ⚪ | JAVA_INTEROP深度变异Double#13 | `var name:Double在Java:getName()+setName()` | 保持一致 |
| KT-1796 | ⚪ | JAVA_INTEROP深度变异String?#14 | `val x:String?=...;var name:Long在Java:getName()+setName()` | 保持一致 |
| KT-1797 | ⚪ | JAVA_INTEROP深度变异Set<Int>#15 | `val x:Set<Int>=...;var name:Double在Java:getName()+setName()` | 保持一致 |
| KT-1798 | ⚪ | JAVA_INTEROP深度变异Long#16 | `val x:Long=...;var name:Boolean在Java:getName()+setName()` | 保持一致 |
| KT-1799 | 🔴 | JAVA_INTEROP深度变异Any#17 | `fun javaMethod():Any=null` | 显式标注? |
| KT-1800 | 🔴 | JAVA_INTEROP深度变异List<String>#18 | `val x:List<String>=...;fun javaMethod():Long=null` | 显式标注? |
| KT-1801 | 🔴 | JAVA_INTEROP深度变异Int#19 | `val x:Int=...;fun javaMethod():Double=null` | 显式标注? |
| KT-1802 | 🔴 | JAVA_INTEROP深度变异Short#20 | `val x:Short=...;fun javaMethod():Boolean=null` | 显式标注? |
| KT-1803 | 🟡 | JAVA_INTEROP深度变异Any?#21 | `val x:Any?=...;kotlinSet.toSet()在Java侧` | 直接传递 |
| KT-1804 | 🟡 | JAVA_INTEROP深度变异String#22 | `val x:String=...;fun f(a:Long,b:Long=0)` | 加@JvmOverloads |
| KT-1805 | 🔴 | JAVA_INTEROP深度变异Byte#23 | `val x:Byte=...;val s=javaObj.getName();s.length` | ?:+\"\" |
| KT-1806 | 🔴 | JAVA_INTEROP深度变异Boolean?#24 | `fun javaMethod():Boolean?=null` | 显式标注? |
| KT-1807 | 🟡 | JAVA_INTEROP深度变异Sequence<Long>#25 | `val x:Sequence<Long>=...;INSTANCE.` | 加@JvmStatic |
| KT-1808 | 🟡 | JAVA_INTEROP深度变异Char#26 | `val x:Char=...;fun f(r:Runnable);fun f(c:Callable);f{` | 显式lambda类型 |
| KT-1809 | 🟡 | JAVA_INTEROP深度变异Double?#27 | `kotlinDouble?.toDouble?()在Java侧` | 直接传递 |
| KT-1810 | 🟡 | JAVA_INTEROP深度变异Array<Boolean>#28 | `fun f(a:Array<Boolean>,b:Array<Boolean>=0)` | 加@JvmOverloads |
| KT-1811 | ⚪ | JAVA_INTEROP深度变异Float#29 | `val x:Float=...;@JvmField val x=42` | 直接const |
| KT-1812 | 🔴 | JAVA_INTEROP深度变异Long?#30 | `val x:Long?=...;try{javaMethod()}catch(e:IOException){}` | 文档标注或runCatching |
| KT-1813 | 🟡 | JAVA_INTEROP深度变异MutableList<Double>#31 | `val x:MutableList<Double>=...;@JvmName(\"f\") fun fKotlin(){` | 统一命名 |
| KT-1814 | 🟡 | JAVA_INTEROP深度变异Boolean#32 | `val x:Boolean=...;Consumer<?super T>在Kotlin` | Consumer<in T> |
| KT-1815 | 🟡 | JAVA_INTEROP深度变异Int?#33 | `val x:Int?=...;companion{fun create()};Java:Companion.create` | @JvmStatic |
| KT-1816 | ⚪ | JAVA_INTEROP深度变异Map<String,Int>#34 | `val x:Map<String,Int>=...;@Throws(IOException::class) fun f(` | 仅Java交互需要 |
| KT-1817 | ⚪ | JAVA_INTEROP深度变异Double#35 | `var name:Double在Java:getName()+setName()` | 保持一致 |
| KT-1818 | ⚪ | JAVA_INTEROP深度变异String?#36 | `var name:String?在Java:getName()+setName()` | 保持一致 |
| KT-1819 | ⚪ | JAVA_INTEROP深度变异Set<Int>#37 | `val x:Set<Int>=...;var name:Long在Java:getName()+setName()` | 保持一致 |
| KT-1820 | ⚪ | JAVA_INTEROP深度变异Long#38 | `val x:Long=...;var name:Double在Java:getName()+setName()` | 保持一致 |
| KT-1821 | ⚪ | JAVA_INTEROP深度变异Any#39 | `val x:Any=...;var name:Boolean在Java:getName()+setName()` | 保持一致 |
| KT-1822 | 🔴 | JAVA_INTEROP深度变异List<String>#40 | `fun javaMethod():List<String><String>=null` | 显式标注? |
| KT-1823 | 🔴 | JAVA_INTEROP深度变异Int#41 | `val x:Int=...;fun javaMethod():Long=null` | 显式标注? |
| KT-1824 | 🔴 | JAVA_INTEROP深度变异Short#42 | `val x:Short=...;fun javaMethod():Double=null` | 显式标注? |
| KT-1825 | 🔴 | JAVA_INTEROP深度变异Any?#43 | `val x:Any?=...;fun javaMethod():Boolean=null` | 显式标注? |
| KT-1826 | 🟡 | JAVA_INTEROP深度变异String#44 | `val x:String=...;kotlinSet.toSet()在Java侧` | 直接传递 |
| KT-1827 | 🟡 | JAVA_INTEROP深度变异Byte#45 | `val x:Byte=...;fun f(a:Long,b:Long=0)` | 加@JvmOverloads |
| KT-1828 | 🔴 | JAVA_INTEROP深度变异Boolean?#46 | `val x:Boolean?=...;val s=javaObj.getName();s.length` | ?:+\"\" |
| KT-1829 | 🔴 | JAVA_INTEROP深度变异Sequence<Long>#47 | `fun javaMethod():Sequence<Long>=null` | 显式标注? |
| KT-1830 | 🟡 | JAVA_INTEROP深度变异Char#48 | `val x:Char=...;INSTANCE.` | 加@JvmStatic |
| KT-1831 | 🟡 | JAVA_INTEROP深度变异Double?#49 | `val x:Double?=...;fun f(r:Runnable);fun f(c:Callable);f{` | 显式lambda类型 |
| KT-1832 | 🟡 | JAVA_INTEROP深度变异Array<Boolean>#50 | `kotlinArray<Boolean>.toArray<Boolean>()在Java侧` | 直接传递 |
| KT-1833 | 🟡 | JAVA_INTEROP深度变异Float#51 | `fun f(a:Float,b:Float=0)` | 加@JvmOverloads |
| KT-1834 | ⚪ | JAVA_INTEROP深度变异Long?#52 | `val x:Long?=...;@JvmField val x=42` | 直接const |
| KT-1835 | 🔴 | JAVA_INTEROP深度变异MutableList<Double>#53 | `val x:MutableList<Double>=...;try{javaMethod()}catch(e:IOExc` | 文档标注或runCatching |
| KT-1836 | 🟡 | JAVA_INTEROP深度变异Boolean#54 | `val x:Boolean=...;@JvmName(\"f\") fun fKotlin(){};fun f(){}` | 统一命名 |
| KT-1837 | 🟡 | JAVA_INTEROP深度变异Int?#55 | `val x:Int?=...;Consumer<?super T>在Kotlin` | Consumer<in T> |
| KT-1838 | 🟡 | JAVA_INTEROP深度变异Map<String,Int>#56 | `val x:Map<String,Int>=...;companion{fun create()};Java:Compa` | @JvmStatic |
| KT-1839 | ⚪ | JAVA_INTEROP深度变异Double#57 | `val x:Double=...;@Throws(IOException::class) fun f()` | 仅Java交互需要 |
| KT-1840 | ⚪ | JAVA_INTEROP深度变异String?#58 | `var name:String?在Java:getName()+setName()` | 保持一致 |
| KT-1841 | ⚪ | JAVA_INTEROP深度变异Set<Int>#59 | `var name:Set<Int>在Java:getName()+setName()` | 保持一致 |
| KT-1842 | ⚪ | JAVA_INTEROP深度变异Long#60 | `val x:Long=...;var name:Long在Java:getName()+setName()` | 保持一致 |
| KT-1843 | ⚪ | JAVA_INTEROP深度变异Any#61 | `val x:Any=...;var name:Double在Java:getName()+setName()` | 保持一致 |
| KT-1844 | ⚪ | JAVA_INTEROP深度变异List<String>#62 | `val x:List<String>=...;var name:Boolean在Java:getName()+setNa` | 保持一致 |
| KT-1845 | 🔴 | JAVA_INTEROP深度变异Int#63 | `val x:Int=...;fun javaMethod():Int=null` | 显式标注? |
| KT-1846 | 🔴 | JAVA_INTEROP深度变异Short#64 | `val x:Short=...;fun javaMethod():Long=null` | 显式标注? |
| KT-1847 | 🔴 | JAVA_INTEROP深度变异Any?#65 | `val x:Any?=...;fun javaMethod():Double=null` | 显式标注? |
| KT-1848 | 🔴 | JAVA_INTEROP深度变异String#66 | `val x:String=...;fun javaMethod():Boolean=null` | 显式标注? |
| KT-1849 | 🟡 | JAVA_INTEROP深度变异Byte#67 | `val x:Byte=...;kotlinSet.toSet()在Java侧` | 直接传递 |
| KT-1850 | 🟡 | JAVA_INTEROP深度变异Boolean?#68 | `val x:Boolean?=...;fun f(a:Long,b:Long=0)` | 加@JvmOverloads |
| KT-1851 | 🔴 | JAVA_INTEROP深度变异Sequence<Long>#69 | `val x:Sequence<Long>=...;val s=javaObj.getName();s.length` | ?:+\"\" |
| KT-1852 | 🔴 | JAVA_INTEROP深度变异Char#70 | `fun javaMethod():Char=null` | 显式标注? |
| KT-1853 | 🟡 | JAVA_INTEROP深度变异Double?#71 | `val x:Double?=...;INSTANCE.` | 加@JvmStatic |
| KT-1854 | 🟡 | JAVA_INTEROP深度变异Array<Boolean>#72 | `val x:Array<Boolean>=...;fun f(r:Runnable);fun f(c:Callable)` | 显式lambda类型 |
| KT-1855 | 🟡 | JAVA_INTEROP深度变异Float#73 | `kotlinFloat.toFloat()在Java侧` | 直接传递 |
| KT-1856 | 🟡 | JAVA_INTEROP深度变异Long?#74 | `fun f(a:Long?,b:Long?=0)` | 加@JvmOverloads |
| KT-1857 | ⚪ | JAVA_INTEROP深度变异MutableList<Double>#75 | `val x:MutableList<Double>=...;@JvmField val x=42` | 直接const |
| KT-1858 | 🔴 | JAVA_INTEROP深度变异Boolean#76 | `val x:Boolean=...;try{javaMethod()}catch(e:IOException){}` | 文档标注或runCatching |
| KT-1859 | 🟡 | JAVA_INTEROP深度变异Int?#77 | `val x:Int?=...;@JvmName(\"f\") fun fKotlin(){};fun f(){}` | 统一命名 |
| KT-1860 | 🟡 | JAVA_INTEROP深度变异Map<String,Int>#78 | `val x:Map<String,Int>=...;Consumer<?super T>在Kotlin` | Consumer<in T> |
| KT-1861 | 🟡 | JAVA_INTEROP深度变异Double#79 | `val x:Double=...;companion{fun create()};Java:Companion.crea` | @JvmStatic |
| KT-1862 | ⚪ | JAVA_INTEROP深度变异String?#80 | `val x:String?=...;@Throws(IOException::class) fun f()` | 仅Java交互需要 |
| KT-1863 | ⚪ | JAVA_INTEROP深度变异Set<Int>#81 | `var name:Set<Set<Int>>在Java:getName()+setName()` | 保持一致 |
| KT-1864 | ⚪ | JAVA_INTEROP深度变异Long#82 | `var name:Long在Java:getName()+setName()` | 保持一致 |
| KT-1865 | ⚪ | JAVA_INTEROP深度变异Any#83 | `val x:Any=...;var name:Long在Java:getName()+setName()` | 保持一致 |
| KT-1866 | ⚪ | JAVA_INTEROP深度变异List<String>#84 | `val x:List<String>=...;var name:Double在Java:getName()+setNam` | 保持一致 |
| KT-1867 | ⚪ | JAVA_INTEROP深度变异Int#85 | `val x:Int=...;var name:Boolean在Java:getName()+setName()` | 保持一致 |
| KT-1868 | 🔴 | JAVA_INTEROP深度变异Short#86 | `fun javaMethod():Short=null` | 显式标注? |
| KT-1869 | 🔴 | JAVA_INTEROP深度变异Any?#87 | `val x:Any?=...;fun javaMethod():Long=null` | 显式标注? |
| KT-1870 | 🔴 | JAVA_INTEROP深度变异String#88 | `val x:String=...;fun javaMethod():Double=null` | 显式标注? |
| KT-1871 | 🔴 | JAVA_INTEROP深度变异Byte#89 | `val x:Byte=...;fun javaMethod():Boolean=null` | 显式标注? |
| KT-1872 | 🟡 | JAVA_INTEROP深度变异Boolean?#90 | `val x:Boolean?=...;kotlinSet.toSet()在Java侧` | 直接传递 |
| KT-1873 | 🟡 | JAVA_INTEROP深度变异Sequence<Long>#91 | `val x:Sequence<Long>=...;fun f(a:Long,b:Long=0)` | 加@JvmOverloads |
| KT-1874 | 🔴 | JAVA_INTEROP深度变异Char#92 | `val x:Char=...;val s=javaObj.getName();s.length` | ?:+\"\" |
| KT-1875 | 🔴 | JAVA_INTEROP深度变异Double?#93 | `fun javaMethod():Double?=null` | 显式标注? |
| KT-1876 | 🟡 | JAVA_INTEROP深度变异Array<Boolean>#94 | `val x:Array<Boolean>=...;INSTANCE.` | 加@JvmStatic |
| KT-1877 | 🟡 | JAVA_INTEROP深度变异Float#95 | `val x:Float=...;fun f(r:Runnable);fun f(c:Callable);f{` | 显式lambda类型 |
| KT-1878 | 🟡 | JAVA_INTEROP深度变异Long?#96 | `kotlinLong?.toLong?()在Java侧` | 直接传递 |
| KT-1879 | 🟡 | JAVA_INTEROP深度变异MutableList<Double>#97 | `fun f(a:MutableMutableList<Double><Double>,b:MutableMutableL` | 加@JvmOverloads |
| KT-1880 | ⚪ | JAVA_INTEROP深度变异Boolean#98 | `val x:Boolean=...;@JvmField val x=42` | 直接const |
| KT-1881 | 🔴 | JAVA_INTEROP深度变异Int?#99 | `val x:Int?=...;try{javaMethod()}catch(e:IOException){}` | 文档标注或runCatching |
| KT-1882 | 🟡 | JAVA_INTEROP深度变异Map<String,Int>#100 | `val x:Map<String,Int>=...;@JvmName(\"f\") fun fKotlin(){};fu` | 统一命名 |
| KT-1883 | 🟡 | JAVA_INTEROP深度变异Double#101 | `val x:Double=...;Consumer<?super T>在Kotlin` | Consumer<in T> |
| KT-1884 | 🟡 | JAVA_INTEROP深度变异String?#102 | `val x:String?=...;companion{fun create()};Java:Companion.cre` | @JvmStatic |
| KT-1885 | ⚪ | JAVA_INTEROP深度变异Set<Int>#103 | `val x:Set<Int>=...;@Throws(IOException::class) fun f()` | 仅Java交互需要 |
| KT-1886 | ⚪ | JAVA_INTEROP深度变异Long#104 | `var name:Long在Java:getName()+setName()` | 保持一致 |
| KT-1887 | ⚪ | JAVA_INTEROP深度变异Any#105 | `var name:Any在Java:getName()+setName()` | 保持一致 |
| KT-1888 | ⚪ | JAVA_INTEROP深度变异List<String>#106 | `val x:List<String>=...;var name:Long在Java:getName()+setName(` | 保持一致 |
| KT-1889 | ⚪ | JAVA_INTEROP深度变异Int#107 | `val x:Int=...;var name:Double在Java:getName()+setName()` | 保持一致 |
| KT-1890 | ⚪ | JAVA_INTEROP深度变异Short#108 | `val x:Short=...;var name:Boolean在Java:getName()+setName()` | 保持一致 |
| KT-1891 | 🔴 | JAVA_INTEROP深度变异Any?#109 | `fun javaMethod():Any?=null` | 显式标注? |
| KT-1892 | 🔴 | JAVA_INTEROP深度变异String#110 | `val x:String=...;fun javaMethod():Long=null` | 显式标注? |
| KT-1893 | 🔴 | JAVA_INTEROP深度变异Byte#111 | `val x:Byte=...;fun javaMethod():Double=null` | 显式标注? |
| KT-1894 | 🔴 | JAVA_INTEROP深度变异Boolean?#112 | `val x:Boolean?=...;fun javaMethod():Boolean=null` | 显式标注? |
| KT-1895 | 🟡 | JAVA_INTEROP深度变异Sequence<Long>#113 | `val x:Sequence<Long>=...;kotlinSet.toSet()在Java侧` | 直接传递 |
| KT-1896 | 🟡 | JAVA_INTEROP深度变异Char#114 | `val x:Char=...;fun f(a:Long,b:Long=0)` | 加@JvmOverloads |
| KT-1897 | 🔴 | JAVA_INTEROP深度变异Double?#115 | `val x:Double?=...;val s=javaObj.getName();s.length` | ?:+\"\" |
| KT-1898 | 🔴 | JAVA_INTEROP深度变异Array<Boolean>#116 | `fun javaMethod():Array<Boolean>=null` | 显式标注? |
| KT-1899 | 🟡 | JAVA_INTEROP深度变异Float#117 | `val x:Float=...;INSTANCE.` | 加@JvmStatic |
| KT-1900 | 🟡 | JAVA_INTEROP深度变异Long?#118 | `val x:Long?=...;fun f(r:Runnable);fun f(c:Callable);f{` | 显式lambda类型 |
| KT-1901 | 🟡 | JAVA_INTEROP深度变异MutableList<Double>#119 | `kotlinMutableList<Double>.toMutableList<Double>()在Java侧` | 直接传递 |
| KT-1902 | 🟡 | JAVA_INTEROP深度变异Boolean#120 | `fun f(a:Boolean,b:Boolean=0)` | 加@JvmOverloads |
| KT-1903 | ⚪ | JAVA_INTEROP深度变异Int?#121 | `val x:Int?=...;@JvmField val x=42` | 直接const |
| KT-1904 | 🔴 | JAVA_INTEROP深度变异Map<String,Int>#122 | `val x:Map<String,Int>=...;try{javaMethod()}catch(e:IOExcepti` | 文档标注或runCatching |
| KT-1905 | 🟡 | JAVA_INTEROP深度变异Double#123 | `val x:Double=...;@JvmName(\"f\") fun fKotlin(){};fun f(){}` | 统一命名 |
| KT-1906 | 🟡 | JAVA_INTEROP深度变异String?#124 | `val x:String?=...;Consumer<?super T>在Kotlin` | Consumer<in T> |
| KT-1907 | 🟡 | JAVA_INTEROP深度变异Set<Int>#125 | `val x:Set<Int>=...;companion{fun create()};Java:Companion.cr` | @JvmStatic |
| KT-1908 | ⚪ | JAVA_INTEROP深度变异Long#126 | `val x:Long=...;@Throws(IOException::class) fun f()` | 仅Java交互需要 |

## KMP（100条）

| ID | 严重度 | Bug描述 | 触发模式 | 修复建议 |
|---|------|------|------|------|
| KT-0117 | 🔴 | expect/actual返回类型不匹配 | `expect fun f():String;actual fun f():Int` | 对齐签名 |
| KT-0118 | 🟡 | 平台API未抽象 | `fun a(){System.loadLibrary("x")}` | expect/actual封装 |
| KT-0119 | 🟡 | iOS与JVM路径分隔符 | `File("a/b")在iOS` | expect/actual或Path |
| KT-0120 | ⚪ | commonMain中使用java.* | `import java.io.File` | expect class |
| KT-0234 | 🟡 | expect声明缺少actual | `expect fun format(d:Double):String` | 补actual |
| KT-0235 | 🟡 | commonTest中使用平台API | `assertEquals(System.currentTimeMillis(),ts)` | expect/actual测试 |
| KT-0236 | ⚪ | KMP模块缺少依赖 | `commonMain中import kotlinx.*` | 确认依赖或添加依赖 |
| KT-0357 | 🔴 | expect/actual返回类型不匹配（Int版） | `expect fun f():Int;actual fun f():Int` | 对齐签名 |
| KT-0358 | 🔴 | expect/actual返回类型不匹配（Long版） | `expect fun f():Long;actual fun f():Int` | 对齐签名 |
| KT-0359 | 🔴 | expect/actual返回类型不匹配（Double版） | `expect fun f():Double;actual fun f():Int` | 对齐签名 |
| KT-0360 | 🔴 | expect/actual返回类型不匹配（Boolean版） | `expect fun f():Boolean;actual fun f():Int` | 对齐签名 |
| KT-0361 | 🔴 | expect/actual返回类型不匹配（Long版） | `expect fun f():String;actual fun f():Long` | 对齐签名 |
| KT-0362 | 🔴 | expect/actual返回类型不匹配（Double版） | `expect fun f():String;actual fun f():Double` | 对齐签名 |
| KT-0363 | 🔴 | expect/actual返回类型不匹配（Float版） | `expect fun f():String;actual fun f():Float` | 对齐签名 |
| KT-0373 | 🟡 | expect声明缺少actual（Int版） | `expect fun format(d:Double):Int` | 补actual |
| KT-0374 | 🟡 | expect声明缺少actual（Long版） | `expect fun format(d:Double):Long` | 补actual |
| KT-0375 | 🟡 | expect声明缺少actual（Double版） | `expect fun format(d:Double):Double` | 补actual |
| KT-0376 | 🟡 | expect声明缺少actual（Boolean版） | `expect fun format(d:Double):Boolean` | 补actual |
| KT-2501 | 🔴 | KMP深度变异String#0 | `expect fun f():String;actual fun f():String` | 对齐签名 |
| KT-2502 | 🟡 | KMP深度变异Byte#1 | `val x:Byte=...;fun a(){System.loadLibrary(\"x\")}` | expect/actual封装 |
| KT-2503 | 🟡 | KMP深度变异Boolean?#2 | `val x:Boolean?=...;File(\"a/b\")在iOS` | expect/actual或Path |
| KT-2504 | ⚪ | KMP深度变异Sequence<Long>#3 | `val x:Sequence<Long>=...;import java.io.File` | expect class |
| KT-2505 | 🟡 | KMP深度变异Char#4 | `expect fun format(d:Double):Char` | 补actual |
| KT-2506 | 🟡 | KMP深度变异Double?#5 | `val x:Double?=...;assertEquals(System.currentTimeMillis(),ts` | expect/actual测试 |
| KT-2507 | ⚪ | KMP深度变异Array<Boolean>#6 | `val x:Array<Boolean>=...;commonMain中import kotlinx.*` | 确认依赖或添加依赖 |
| KT-2508 | 🔴 | KMP深度变异Float#7 | `expect fun f():Float;actual fun f():Float` | 对齐签名 |
| KT-2509 | 🔴 | KMP深度变异Long?#8 | `expect fun f():Long;actual fun f():Long?` | 对齐签名 |
| KT-2510 | 🔴 | KMP深度变异MutableList<Double>#9 | `expect fun f():Double;actual fun f():MutableMutableList<Doub` | 对齐签名 |
| KT-2511 | 🔴 | KMP深度变异Boolean#10 | `expect fun f():Boolean;actual fun f():Boolean` | 对齐签名 |
| KT-2512 | 🔴 | KMP深度变异Int?#11 | `expect fun f():Int??;actual fun f():Long` | 对齐签名 |
| KT-2513 | 🔴 | KMP深度变异Map<String,Int>#12 | `expect fun f():Map<String,Map<String,Int>>;actual fun f():Do` | 对齐签名 |
| KT-2514 | 🔴 | KMP深度变异Double#13 | `expect fun f():Double;actual fun f():Float` | 对齐签名 |
| KT-2515 | 🟡 | KMP深度变异String?#14 | `expect fun format(d:Double):String?` | 补actual |
| KT-2516 | 🟡 | KMP深度变异Set<Int>#15 | `val x:Set<Int>=...;expect fun format(d:Double):Long` | 补actual |
| KT-2517 | 🟡 | KMP深度变异Long#16 | `val x:Long=...;expect fun format(d:Double):Double` | 补actual |
| KT-2518 | 🟡 | KMP深度变异Any#17 | `val x:Any=...;expect fun format(d:Double):Boolean` | 补actual |
| KT-2519 | 🔴 | KMP深度变异List<String>#18 | `expect fun f():List<String><String>;actual fun f():List<Stri` | 对齐签名 |
| KT-2520 | 🟡 | KMP深度变异Int#19 | `val x:Int=...;fun a(){System.loadLibrary(\"x\")}` | expect/actual封装 |
| KT-2521 | 🟡 | KMP深度变异Short#20 | `val x:Short=...;File(\"a/b\")在iOS` | expect/actual或Path |
| KT-2522 | ⚪ | KMP深度变异Any?#21 | `val x:Any?=...;import java.io.File` | expect class |
| KT-2523 | 🟡 | KMP深度变异String#22 | `val x:String=...;expect fun format(d:Double):String` | 补actual |
| KT-2524 | 🟡 | KMP深度变异Byte#23 | `val x:Byte=...;assertEquals(System.currentTimeMillis(),ts)` | expect/actual测试 |
| KT-2525 | ⚪ | KMP深度变异Boolean?#24 | `val x:Boolean?=...;commonMain中import kotlinx.*` | 确认依赖或添加依赖 |
| KT-2526 | 🔴 | KMP深度变异Sequence<Long>#25 | `expect fun f():Sequence<Long>;actual fun f():Sequence<Long>` | 对齐签名 |
| KT-2527 | 🔴 | KMP深度变异Char#26 | `expect fun f():Long;actual fun f():Char` | 对齐签名 |
| KT-2528 | 🔴 | KMP深度变异Double?#27 | `expect fun f():Double;actual fun f():Double?` | 对齐签名 |
| KT-2529 | 🔴 | KMP深度变异Array<Boolean>#28 | `expect fun f():Boolean;actual fun f():Array<Boolean>` | 对齐签名 |
| KT-2530 | 🔴 | KMP深度变异Float#29 | `expect fun f():Float;actual fun f():Long` | 对齐签名 |
| KT-2531 | 🔴 | KMP深度变异Long?#30 | `expect fun f():Long?;actual fun f():Double` | 对齐签名 |
| KT-2532 | 🔴 | KMP深度变异MutableList<Double>#31 | `expect fun f():MutableMutableList<Double><Double>;actual fun` | 对齐签名 |
| KT-2533 | 🟡 | KMP深度变异Boolean#32 | `expect fun format(d:Double):Boolean` | 补actual |
| KT-2534 | 🟡 | KMP深度变异Int?#33 | `val x:Int?=...;expect fun format(d:Double):Long` | 补actual |
| KT-2535 | 🟡 | KMP深度变异Map<String,Int>#34 | `val x:Map<String,Int>=...;expect fun format(d:Double):Double` | 补actual |
| KT-2536 | 🟡 | KMP深度变异Double#35 | `val x:Double=...;expect fun format(d:Double):Boolean` | 补actual |
| KT-2537 | 🔴 | KMP深度变异String?#36 | `expect fun f():String?;actual fun f():String?` | 对齐签名 |
| KT-2538 | 🟡 | KMP深度变异Set<Int>#37 | `val x:Set<Int>=...;fun a(){System.loadLibrary(\"x\")}` | expect/actual封装 |
| KT-2539 | 🟡 | KMP深度变异Long#38 | `val x:Long=...;File(\"a/b\")在iOS` | expect/actual或Path |
| KT-2540 | ⚪ | KMP深度变异Any#39 | `val x:Any=...;import java.io.File` | expect class |
| KT-2541 | 🟡 | KMP深度变异List<String>#40 | `expect fun format(d:Double):List<String><String>` | 补actual |
| KT-2542 | 🟡 | KMP深度变异Int#41 | `val x:Int=...;assertEquals(System.currentTimeMillis(),ts)` | expect/actual测试 |
| KT-2543 | ⚪ | KMP深度变异Short#42 | `val x:Short=...;commonMain中import kotlinx.*` | 确认依赖或添加依赖 |
| KT-2544 | 🔴 | KMP深度变异Any?#43 | `expect fun f():Any?;actual fun f():Any?` | 对齐签名 |
| KT-2545 | 🔴 | KMP深度变异String#44 | `expect fun f():Long;actual fun f():String` | 对齐签名 |
| KT-2546 | 🔴 | KMP深度变异Byte#45 | `expect fun f():Double;actual fun f():Byte` | 对齐签名 |
| KT-2547 | 🔴 | KMP深度变异Boolean?#46 | `expect fun f():Boolean;actual fun f():Boolean?` | 对齐签名 |
| KT-2548 | 🔴 | KMP深度变异Sequence<Long>#47 | `expect fun f():Sequence<Long>;actual fun f():Long` | 对齐签名 |
| KT-2549 | 🔴 | KMP深度变异Char#48 | `expect fun f():Char;actual fun f():Double` | 对齐签名 |
| KT-2550 | 🔴 | KMP深度变异Double?#49 | `expect fun f():Double?;actual fun f():Float` | 对齐签名 |
| KT-2551 | 🟡 | KMP深度变异Array<Boolean>#50 | `expect fun format(d:Double):Array<Boolean>` | 补actual |
| KT-2552 | 🟡 | KMP深度变异Float#51 | `val x:Float=...;expect fun format(d:Double):Long` | 补actual |
| KT-2553 | 🟡 | KMP深度变异Long?#52 | `val x:Long?=...;expect fun format(d:Double):Double` | 补actual |
| KT-2554 | 🟡 | KMP深度变异MutableList<Double>#53 | `val x:MutableList<Double>=...;expect fun format(d:Double):Bo` | 补actual |
| KT-2555 | 🔴 | KMP深度变异Boolean#54 | `expect fun f():Boolean;actual fun f():Boolean` | 对齐签名 |
| KT-2556 | 🟡 | KMP深度变异Int?#55 | `val x:Int?=...;fun a(){System.loadLibrary(\"x\")}` | expect/actual封装 |
| KT-2557 | 🟡 | KMP深度变异Map<String,Int>#56 | `val x:Map<String,Int>=...;File(\"a/b\")在iOS` | expect/actual或Path |
| KT-2558 | ⚪ | KMP深度变异Double#57 | `val x:Double=...;import java.io.File` | expect class |
| KT-2559 | 🟡 | KMP深度变异String?#58 | `expect fun format(d:Double):String?` | 补actual |
| KT-2560 | 🟡 | KMP深度变异Set<Int>#59 | `val x:Set<Int>=...;assertEquals(System.currentTimeMillis(),t` | expect/actual测试 |
| KT-2561 | ⚪ | KMP深度变异Long#60 | `val x:Long=...;commonMain中import kotlinx.*` | 确认依赖或添加依赖 |
| KT-2562 | 🔴 | KMP深度变异Any#61 | `expect fun f():Any;actual fun f():Any` | 对齐签名 |
| KT-2563 | 🔴 | KMP深度变异List<String>#62 | `expect fun f():Long;actual fun f():List<String><String>` | 对齐签名 |
| KT-2564 | 🔴 | KMP深度变异Int#63 | `val x:Int=...;expect fun f():Double;actual fun f():Int` | 对齐签名 |
| KT-2565 | 🔴 | KMP深度变异Short#64 | `expect fun f():Boolean;actual fun f():Short` | 对齐签名 |
| KT-2566 | 🔴 | KMP深度变异Any?#65 | `expect fun f():Any?;actual fun f():Long` | 对齐签名 |
| KT-2567 | 🔴 | KMP深度变异String#66 | `val x:String=...;expect fun f():String;actual fun f():Double` | 对齐签名 |
| KT-2568 | 🔴 | KMP深度变异Byte#67 | `expect fun f():Byte;actual fun f():Float` | 对齐签名 |
| KT-2569 | 🟡 | KMP深度变异Boolean?#68 | `expect fun format(d:Double):Boolean?` | 补actual |
| KT-2570 | 🟡 | KMP深度变异Sequence<Long>#69 | `val x:Sequence<Long>=...;expect fun format(d:Double):Long` | 补actual |
| KT-2571 | 🟡 | KMP深度变异Char#70 | `val x:Char=...;expect fun format(d:Double):Double` | 补actual |
| KT-2572 | 🟡 | KMP深度变异Double?#71 | `val x:Double?=...;expect fun format(d:Double):Boolean` | 补actual |
| KT-2573 | 🔴 | KMP深度变异Array<Boolean>#72 | `expect fun f():Array<Boolean>;actual fun f():Array<Boolean>` | 对齐签名 |
| KT-2574 | 🟡 | KMP深度变异Float#73 | `val x:Float=...;fun a(){System.loadLibrary(\"x\")}` | expect/actual封装 |
| KT-2575 | 🟡 | KMP深度变异Long?#74 | `val x:Long?=...;File(\"a/b\")在iOS` | expect/actual或Path |
| KT-2576 | ⚪ | KMP深度变异MutableList<Double>#75 | `val x:MutableList<Double>=...;import java.io.File` | expect class |
| KT-2577 | 🟡 | KMP深度变异Boolean#76 | `expect fun format(d:Double):Boolean` | 补actual |
| KT-2578 | 🟡 | KMP深度变异Int?#77 | `val x:Int?=...;assertEquals(System.currentTimeMillis(),ts)` | expect/actual测试 |
| KT-2579 | ⚪ | KMP深度变异Map<String,Int>#78 | `val x:Map<String,Int>=...;commonMain中import kotlinx.*` | 确认依赖或添加依赖 |
| KT-2580 | 🔴 | KMP深度变异Double#79 | `expect fun f():Double;actual fun f():Double` | 对齐签名 |
| KT-2581 | 🔴 | KMP深度变异String?#80 | `expect fun f():Long;actual fun f():String?` | 对齐签名 |
| KT-2582 | 🔴 | KMP深度变异Set<Int>#81 | `expect fun f():Double;actual fun f():Set<Int>` | 对齐签名 |

## MULTIPLATFORM（80条）

| ID | 严重度 | Bug描述 | 触发模式 | 修复建议 |
|---|------|------|------|------|
| KT-0139 | 🟡 | 平台特定导入 | `import java.io.File` | expect/actual |
| KT-0140 | 🟡 | System.getProperty依赖 | `System.getProperty("os.name")` | expect/actual |
| KT-0141 | ⚪ | 文件分隔符硬编码 | `File("a/b").path在Windows` | File.separator或Path |
| KT-0253 | 🟡 | Windows/macOS/Linux换行符 | `String.split("\\n")在Windows` | System.lineSeparator() |
| KT-0254 | 🟡 | 时区硬编码 | `SimpleDateFormat("yyyy",Locale.US)` | java.time+UTC |
| KT-0255 | ⚪ | 文件编码默认依赖 | `File("x").readText()` | readText(Charsets.UTF_8)显式 |
| KT-0274 | 🔴 | Windows的\r\n在Linux被当两个换行 | `String.split('\n')在Windows残留\r` | System.lineSeparator或trimEnd |
| KT-0380 | 🔴 | Windows的\\r\\n在Linux被当两个换行（Int版） | `Int.split('\\n')在Windows残留\\r` | System.lineSeparator或trimEnd |
| KT-0381 | 🔴 | Windows的\\r\\n在Linux被当两个换行（Long版） | `Long.split('\\n')在Windows残留\\r` | System.lineSeparator或trimEnd |
| KT-0382 | 🔴 | Windows的\\r\\n在Linux被当两个换行（Double版） | `Double.split('\\n')在Windows残留\\r` | System.lineSeparator或trimEnd |
| KT-0383 | 🔴 | Windows的\\r\\n在Linux被当两个换行（Boolean版） | `Boolean.split('\\n')在Windows残留\\r` | System.lineSeparator或trimEnd |
| KT-0408 | 🟡 | Windows/macOS/Linux换行符（Int版） | `Int.split(\"\\\\n\")在Windows` | System.lineSeparator() |
| KT-0409 | 🟡 | Windows/macOS/Linux换行符（Long版） | `Long.split(\"\\\\n\")在Windows` | System.lineSeparator() |
| KT-0410 | 🟡 | Windows/macOS/Linux换行符（Double版） | `Double.split(\"\\\\n\")在Windows` | System.lineSeparator() |
| KT-0411 | 🟡 | Windows/macOS/Linux换行符（Boolean版） | `Boolean.split(\"\\\\n\")在Windows` | System.lineSeparator() |
| KT-2873 | 🟡 | MULTIPLATFORM深度变异String#0 | `val x:String=...;import java.io.File` | expect/actual |
| KT-2874 | 🟡 | MULTIPLATFORM深度变异Byte#1 | `val x:Byte=...;System.getProperty(\"os.name\")` | expect/actual |
| KT-2875 | ⚪ | MULTIPLATFORM深度变异Boolean?#2 | `val x:Boolean?=...;File(\"a/b\").path在Windows` | File.separator或Path |
| KT-2876 | 🟡 | MULTIPLATFORM深度变异Sequence<Long>#3 | `Sequence<Long>.split(\"\\\\n\")在Windows` | System.lineSeparator() |
| KT-2877 | 🟡 | MULTIPLATFORM深度变异Char#4 | `val x:Char=...;SimpleDateFormat(\"yyyy\",Locale.US)` | java.time+UTC |
| KT-2878 | ⚪ | MULTIPLATFORM深度变异Double?#5 | `val x:Double?=...;File(\"x\").readText()` | readText(Charsets.UTF_8)显式 |
| KT-2879 | 🔴 | MULTIPLATFORM深度变异Array<Boolean>#6 | `Array<Boolean>.split('\\n')在Windows残留\\r` | System.lineSeparator或trimEnd |
| KT-2880 | 🔴 | MULTIPLATFORM深度变异Float#7 | `Float.split('\\\\n')在Windows残留\\\\r` | System.lineSeparator或trimEnd |
| KT-2881 | 🔴 | MULTIPLATFORM深度变异Long?#8 | `val x:Long?=...;Long.split('\\\\n')在Windows残留\\\\r` | System.lineSeparator或trimEnd |
| KT-2882 | 🔴 | MULTIPLATFORM深度变异MutableList<Double>#9 | `val x:MutableList<Double>=...;Double.split('\\\\n')在Windows残` | System.lineSeparator或trimEnd |
| KT-2883 | 🔴 | MULTIPLATFORM深度变异Boolean#10 | `val x:Boolean=...;Boolean.split('\\\\n')在Windows残留\\\\r` | System.lineSeparator或trimEnd |
| KT-2884 | 🟡 | MULTIPLATFORM深度变异Int?#11 | `Int?.split(\\\"\\\\\\\\n\\\")在Windows` | System.lineSeparator() |
| KT-2885 | 🟡 | MULTIPLATFORM深度变异Map<String,Int>#12 | `val x:Map<String,Int>=...;Long.split(\\\"\\\\\\\\n\\\")在Wind` | System.lineSeparator() |
| KT-2886 | 🟡 | MULTIPLATFORM深度变异Double#13 | `val x:Double=...;Double.split(\\\"\\\\\\\\n\\\")在Windows` | System.lineSeparator() |
| KT-2887 | 🟡 | MULTIPLATFORM深度变异String?#14 | `val x:String?=...;Boolean.split(\\\"\\\\\\\\n\\\")在Windows` | System.lineSeparator() |
| KT-2888 | 🟡 | MULTIPLATFORM深度变异Set<Int>#15 | `val x:Set<Int>=...;import java.io.File` | expect/actual |
| KT-2889 | 🟡 | MULTIPLATFORM深度变异Long#16 | `val x:Long=...;System.getProperty(\"os.name\")` | expect/actual |
| KT-2890 | ⚪ | MULTIPLATFORM深度变异Any#17 | `val x:Any=...;File(\"a/b\").path在Windows` | File.separator或Path |
| KT-2891 | 🟡 | MULTIPLATFORM深度变异List<String>#18 | `List<String><String>.split(\"\\\\n\")在Windows` | System.lineSeparator() |
| KT-2892 | 🟡 | MULTIPLATFORM深度变异Int#19 | `val x:Int=...;SimpleDateFormat(\"yyyy\",Locale.US)` | java.time+UTC |
| KT-2893 | ⚪ | MULTIPLATFORM深度变异Short#20 | `val x:Short=...;File(\"x\").readText()` | readText(Charsets.UTF_8)显式 |
| KT-2894 | 🔴 | MULTIPLATFORM深度变异Any?#21 | `Any?.split('\\n')在Windows残留\\r` | System.lineSeparator或trimEnd |
| KT-2895 | 🔴 | MULTIPLATFORM深度变异String#22 | `String.split('\\\\n')在Windows残留\\\\r` | System.lineSeparator或trimEnd |
| KT-2896 | 🔴 | MULTIPLATFORM深度变异Byte#23 | `val x:Byte=...;Long.split('\\\\n')在Windows残留\\\\r` | System.lineSeparator或trimEnd |
| KT-2897 | 🔴 | MULTIPLATFORM深度变异Boolean?#24 | `val x:Boolean?=...;Double.split('\\\\n')在Windows残留\\\\r` | System.lineSeparator或trimEnd |
| KT-2898 | 🔴 | MULTIPLATFORM深度变异Sequence<Long>#25 | `val x:Sequence<Long>=...;Boolean.split('\\\\n')在Windows残留\\\` | System.lineSeparator或trimEnd |
| KT-2899 | 🟡 | MULTIPLATFORM深度变异Char#26 | `Char.split(\\\"\\\\\\\\n\\\")在Windows` | System.lineSeparator() |
| KT-2900 | 🟡 | MULTIPLATFORM深度变异Double?#27 | `val x:Double?=...;Long.split(\\\"\\\\\\\\n\\\")在Windows` | System.lineSeparator() |
| KT-2901 | 🟡 | MULTIPLATFORM深度变异Array<Boolean>#28 | `val x:Array<Boolean>=...;Double.split(\\\"\\\\\\\\n\\\")在Win` | System.lineSeparator() |
| KT-2902 | 🟡 | MULTIPLATFORM深度变异Float#29 | `val x:Float=...;Boolean.split(\\\"\\\\\\\\n\\\")在Windows` | System.lineSeparator() |
| KT-2903 | 🟡 | MULTIPLATFORM深度变异Long?#30 | `val x:Long?=...;import java.io.File` | expect/actual |
| KT-2904 | 🟡 | MULTIPLATFORM深度变异MutableList<Double>#31 | `val x:MutableList<Double>=...;System.getProperty(\"os.name\"` | expect/actual |
| KT-2905 | ⚪ | MULTIPLATFORM深度变异Boolean#32 | `val x:Boolean=...;File(\"a/b\").path在Windows` | File.separator或Path |
| KT-2906 | 🟡 | MULTIPLATFORM深度变异Int?#33 | `Int??.split(\"\\\\n\")在Windows` | System.lineSeparator() |
| KT-2907 | 🟡 | MULTIPLATFORM深度变异Map<String,Int>#34 | `val x:Map<String,Int>=...;SimpleDateFormat(\"yyyy\",Locale.U` | java.time+UTC |
| KT-2908 | ⚪ | MULTIPLATFORM深度变异Double#35 | `val x:Double=...;File(\"x\").readText()` | readText(Charsets.UTF_8)显式 |
| KT-2909 | 🔴 | MULTIPLATFORM深度变异String?#36 | `String?.split('\\n')在Windows残留\\r` | System.lineSeparator或trimEnd |
| KT-2910 | 🔴 | MULTIPLATFORM深度变异Set<Int>#37 | `Set<Int>.split('\\\\n')在Windows残留\\\\r` | System.lineSeparator或trimEnd |
| KT-2911 | 🔴 | MULTIPLATFORM深度变异Long#38 | `val x:Long=...;Long.split('\\\\n')在Windows残留\\\\r` | System.lineSeparator或trimEnd |
| KT-2912 | 🔴 | MULTIPLATFORM深度变异Any#39 | `val x:Any=...;Double.split('\\\\n')在Windows残留\\\\r` | System.lineSeparator或trimEnd |
| KT-2913 | 🔴 | MULTIPLATFORM深度变异List<String>#40 | `val x:List<String>=...;Boolean.split('\\\\n')在Windows残留\\\\r` | System.lineSeparator或trimEnd |
| KT-2914 | 🟡 | MULTIPLATFORM深度变异Int#41 | `val x:Int=...;Int.split(\\\"\\\\\\\\n\\\")在Windows` | System.lineSeparator() |
| KT-2915 | 🟡 | MULTIPLATFORM深度变异Short#42 | `val x:Short=...;Long.split(\\\"\\\\\\\\n\\\")在Windows` | System.lineSeparator() |
| KT-2916 | 🟡 | MULTIPLATFORM深度变异Any?#43 | `val x:Any?=...;Double.split(\\\"\\\\\\\\n\\\")在Windows` | System.lineSeparator() |
| KT-2917 | 🟡 | MULTIPLATFORM深度变异String#44 | `val x:String=...;Boolean.split(\\\"\\\\\\\\n\\\")在Windows` | System.lineSeparator() |
| KT-2918 | 🟡 | MULTIPLATFORM深度变异Byte#45 | `val x:Byte=...;import java.io.File` | expect/actual |
| KT-2919 | 🟡 | MULTIPLATFORM深度变异Boolean?#46 | `val x:Boolean?=...;System.getProperty(\"os.name\")` | expect/actual |
| KT-2920 | ⚪ | MULTIPLATFORM深度变异Sequence<Long>#47 | `val x:Sequence<Long>=...;File(\"a/b\").path在Windows` | File.separator或Path |
| KT-2921 | 🟡 | MULTIPLATFORM深度变异Char#48 | `Char.split(\"\\\\n\")在Windows` | System.lineSeparator() |
| KT-2922 | 🟡 | MULTIPLATFORM深度变异Double?#49 | `val x:Double?=...;SimpleDateFormat(\"yyyy\",Locale.US)` | java.time+UTC |
| KT-2923 | ⚪ | MULTIPLATFORM深度变异Array<Boolean>#50 | `val x:Array<Boolean>=...;File(\"x\").readText()` | readText(Charsets.UTF_8)显式 |
| KT-2924 | 🔴 | MULTIPLATFORM深度变异Float#51 | `Float.split('\\n')在Windows残留\\r` | System.lineSeparator或trimEnd |
| KT-2925 | 🔴 | MULTIPLATFORM深度变异Long?#52 | `Long?.split('\\\\n')在Windows残留\\\\r` | System.lineSeparator或trimEnd |
| KT-2926 | 🔴 | MULTIPLATFORM深度变异MutableList<Double>#53 | `val x:MutableList<Double>=...;Long.split('\\\\n')在Windows残留\` | System.lineSeparator或trimEnd |
| KT-2927 | 🔴 | MULTIPLATFORM深度变异Boolean#54 | `val x:Boolean=...;Double.split('\\\\n')在Windows残留\\\\r` | System.lineSeparator或trimEnd |
| KT-2928 | 🔴 | MULTIPLATFORM深度变异Int?#55 | `val x:Int?=...;Boolean.split('\\\\n')在Windows残留\\\\r` | System.lineSeparator或trimEnd |
| KT-2929 | 🟡 | MULTIPLATFORM深度变异Map<String,Int>#56 | `Map<String,Int>.split(\\\"\\\\\\\\n\\\")在Windows` | System.lineSeparator() |
| KT-2930 | 🟡 | MULTIPLATFORM深度变异Double#57 | `val x:Double=...;Long.split(\\\"\\\\\\\\n\\\")在Windows` | System.lineSeparator() |
| KT-2931 | 🟡 | MULTIPLATFORM深度变异String?#58 | `val x:String?=...;Double.split(\\\"\\\\\\\\n\\\")在Windows` | System.lineSeparator() |
| KT-2932 | 🟡 | MULTIPLATFORM深度变异Set<Int>#59 | `val x:Set<Int>=...;Boolean.split(\\\"\\\\\\\\n\\\")在Windows` | System.lineSeparator() |
| KT-2933 | 🟡 | MULTIPLATFORM深度变异Long#60 | `val x:Long=...;import java.io.File` | expect/actual |
| KT-2934 | 🟡 | MULTIPLATFORM深度变异Any#61 | `val x:Any=...;System.getProperty(\"os.name\")` | expect/actual |
| KT-2935 | ⚪ | MULTIPLATFORM深度变异List<String>#62 | `val x:List<String>=...;File(\"a/b\").path在Windows` | File.separator或Path |
| KT-2936 | 🟡 | MULTIPLATFORM深度变异Int#63 | `Int.split(\"\\\\n\")在Windows` | System.lineSeparator() |
| KT-2937 | 🟡 | MULTIPLATFORM深度变异Short#64 | `val x:Short=...;SimpleDateFormat(\"yyyy\",Locale.US)` | java.time+UTC |

## NULL_SAFETY（300条）

| ID | 严重度 | Bug描述 | 触发模式 | 修复建议 |
|---|------|------|------|------|
| KT-0001 | 🔴 | !!在可空类型上 | *另修: 改为非空类型声明, 加requireNotNull前置检查* |`x!!.length` | 用?.或?:默认值 |
| KT-0002 | 🔴 | !!未检查直接调用 | `!!.method()` | ?.let |
| KT-0003 | 🔴 | 链式!!蔓延 | `a!!.b!!.c!!.d` | ?.链+单点let |
| KT-0004 | 🔴 | lateinit未初始化 | `lateinit var x;x.method()` | ::x.isInitialized |
| KT-0005 | 🔴 | 平台类型T!隐式null | `javaObj.field;x.length` | 显式类型+安全调用 |
| KT-0006 | 🟡 | ?.与!!混用 | `a?.b?.c!!.d` | 统一用?.或统一用!! |
| KT-0007 | 🟡 | lateinit在init块之前访问 | `init{x.method()};lateinit var x` | lateinit放init之前 |
| KT-0008 | 🟡 | ?.let嵌套过深 | `a?.let{b?.let{c?.let{}}}}` | 提取函数或flatMap |
| KT-0009 | 🟡 | as?后忘记null检查 | `val x=y as? T;x.method()` | as?.let或?:return |
| KT-0010 | 🟡 | return@let遗漏 | `a?.let{if(x)return}` | return@let |
| KT-0011 | ⚪ | 不必要的?. | `val x:T=...;x?.method()` | x.method() |
| KT-0012 | ⚪ | ?:返回null | `val x=y?:null` | ?:默认值或抛异常 |
| KT-0013 | ⚪ | elvis操作符冗余 | `val x=y?:y` | 直接用y |
| KT-0014 | 🟡 | 可空String?在集合操作中 | `val l:List<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0015 | ⚪ | String?的?:返回null | `val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0016 | 🟡 | 可空Int?在集合操作中 | `val l:List<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0017 | ⚪ | Int?的?:返回null | `val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0018 | 🟡 | 可空Long?在集合操作中 | `val l:List<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0019 | ⚪ | Long?的?:返回null | `val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0020 | 🟡 | 可空Double?在集合操作中 | `val l:List<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0021 | ⚪ | Double?的?:返回null | `val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0022 | 🟡 | 可空Boolean?在集合操作中 | `val l:List<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0023 | ⚪ | Boolean?的?:返回null | `val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0142 | 🔴 | !!在map取值上 | `val x=map[key]!!;x.method()` | map[key]?.let或getOrDefault |
| KT-0143 | 🔴 | !!与Elvis短路冲突 | `val x=risky()!!?:default` | 统一用?.或!!不混 |
| KT-0144 | 🟡 | takeIf后!! | `val x=y.takeIf{it>0}!!` | takeIf?.let |
| KT-0145 | 🟡 | 集合元素!! | `list.firstOrNull()!!.prop` | firstOrNull?.prop |
| KT-0146 | 🟡 | 类型参数可空未处理 | `fun <T> f(t:T){t!!.hashCode()}` | <T:Any>约束 |
| KT-0147 | ⚪ | let内!!不如?. | `x?.let{it!!.prop}` | x?.prop直接 |
| KT-0148 | ⚪ | notNull断言过度 | `requireNotNull(x);x.prop` | 直接用x |
| KT-0149 | 🔴 | map中get后用!! | `val v=map.get(key)!!;v.method()` | getOrDefault |
| KT-0150 | 🟡 | !!在flow内 | `flow{val x=repo.get()!!;emit(x)}` | ?.let+emit |
| KT-0151 | 🟡 | notNull与elvis重复 | `val x=requireNotNull(y?:z)` | 统一方式 |
| KT-0293 | 🟡 | 非空断言在finally块中失效 | `try{x!!;risky()}finally{x.method()}` | 局部val快照 |
| KT-0295 | 🔴 | var+!!+多线程=智能转换完全失效 | `var x:Any?=null;thread{x!!;x=null};thread{x.method()}` | AtomicReference+?.let |
| KT-0306 | 🔴 | !!+?:+as?=三null操作符互相矛盾 | `val x=y!!?:z as? T` | 只用一种null处理方式 |
| KT-0484 | 🔴 | Flow中!!操作符 | `flow{val x=risky()!!;emit(x)}` | ?.let或catch |
| KT-0485 | 🟡 | lateinit在init前后矛盾 | `init{val x=late};lateinit var late:String` | lateinit放init前面 |
| KT-0492 | 🔴 | NULL_SAFETY深度变异String#0 | `val x:String=...;x!!.length` | 用?.或?:默认值 |
| KT-0493 | 🔴 | NULL_SAFETY深度变异Byte#1 | `val x:Byte=...;!!.method()` | ?.let |
| KT-0494 | 🔴 | NULL_SAFETY深度变异Boolean?#2 | `val x:Boolean?=...;a!!.b!!.c!!.d` | ?.链+单点let |
| KT-0495 | 🔴 | NULL_SAFETY深度变异Sequence<Long>#3 | `val x:Sequence<Long>=...;lateinit var x;x.method()` | ::x.isInitialized |
| KT-0496 | 🔴 | NULL_SAFETY深度变异Char#4 | `val x:Char=...;javaObj.field;x.length` | 显式类型+安全调用 |
| KT-0497 | 🟡 | NULL_SAFETY深度变异Double?#5 | `val x:Double?=...;a?.b?.c!!.d` | 统一用?.或统一用!! |
| KT-0498 | 🟡 | NULL_SAFETY深度变异Array<Boolean>#6 | `val x:Array<Boolean>=...;init{x.method()};lateinit var x` | lateinit放init之前 |
| KT-0499 | 🟡 | NULL_SAFETY深度变异Float#7 | `val x:Float=...;a?.let{b?.let{c?.let{}}}}` | 提取函数或flatMap |
| KT-0500 | 🟡 | NULL_SAFETY深度变异Long?#8 | `val x:Long?=...;val x=y as? T;x.method()` | as?.let或?:return |
| KT-0501 | 🟡 | NULL_SAFETY深度变异MutableList<Double>#9 | `val x:MutableList<Double>=...;a?.let{if(x)return}` | return@let |
| KT-0502 | ⚪ | NULL_SAFETY深度变异Boolean#10 | `val x:Boolean=...;val x:T=...;x?.method()` | x.method() |
| KT-0503 | ⚪ | NULL_SAFETY深度变异Int?#11 | `val x:Int?=...;val x=y?:null` | ?:默认值或抛异常 |
| KT-0504 | ⚪ | NULL_SAFETY深度变异Map<String,Int>#12 | `val x:Map<String,Int>=...;val x=y?:y` | 直接用y |
| KT-0505 | 🟡 | NULL_SAFETY深度变异Double#13 | `val l:Double<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0506 | ⚪ | NULL_SAFETY深度变异String?#14 | `val x:String?=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0507 | 🟡 | NULL_SAFETY深度变异Set<Int>#15 | `val l:Set<Int><{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0508 | ⚪ | NULL_SAFETY深度变异Long#16 | `val x:Long=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0509 | 🟡 | NULL_SAFETY深度变异Any#17 | `val l:Any<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0510 | ⚪ | NULL_SAFETY深度变异List<String>#18 | `val x:List<String>=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0511 | 🟡 | NULL_SAFETY深度变异Int#19 | `val l:Int<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0512 | ⚪ | NULL_SAFETY深度变异Short#20 | `val x:Short=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0513 | 🟡 | NULL_SAFETY深度变异Any?#21 | `val l:Any?<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0514 | ⚪ | NULL_SAFETY深度变异String#22 | `val x:String=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0515 | 🔴 | NULL_SAFETY深度变异Byte#23 | `val x:Byte=...;val x=map[key]!!;x.method()` | map[key]?.let或getOrDefault |
| KT-0516 | 🔴 | NULL_SAFETY深度变异Boolean?#24 | `val x:Boolean?=...;val x=risky()!!?:default` | 统一用?.或!!不混 |
| KT-0517 | 🟡 | NULL_SAFETY深度变异Sequence<Long>#25 | `val x:Sequence<Long>=...;val x=y.takeIf{it>0}!!` | takeIf?.let |
| KT-0518 | 🟡 | NULL_SAFETY深度变异Char#26 | `val x:Char=...;list.firstOrNull()!!.prop` | firstOrNull?.prop |
| KT-0519 | 🟡 | NULL_SAFETY深度变异Double?#27 | `val x:Double?=...;fun <T> f(t:T){t!!.hashCode()}` | <T:Any>约束 |
| KT-0520 | ⚪ | NULL_SAFETY深度变异Array<Boolean>#28 | `val x:Array<Boolean>=...;x?.let{it!!.prop}` | x?.prop直接 |
| KT-0521 | ⚪ | NULL_SAFETY深度变异Float#29 | `val x:Float=...;requireNotNull(x);x.prop` | 直接用x |
| KT-0522 | 🔴 | NULL_SAFETY深度变异Long?#30 | `val x:Long?=...;val v=map.get(key)!!;v.method()` | getOrDefault |
| KT-0523 | 🟡 | NULL_SAFETY深度变异MutableList<Double>#31 | `val x:MutableList<Double>=...;flow{val x=repo.get()!!;emit(x` | ?.let+emit |
| KT-0524 | 🟡 | NULL_SAFETY深度变异Boolean#32 | `val x:Boolean=...;val x=requireNotNull(y?:z)` | 统一方式 |
| KT-0525 | 🟡 | NULL_SAFETY深度变异Int?#33 | `val x:Int?=...;try{x!!;risky()}finally{x.method()}` | 局部val快照 |
| KT-0526 | 🔴 | NULL_SAFETY深度变异Map<String,Int>#34 | `val x:Map<String,Int>=...;var x:Any?=null;thread{x!!;x=null}` | AtomicReference+?.let |
| KT-0527 | 🔴 | NULL_SAFETY深度变异Double#35 | `val x:Double=...;val x=y!!?:z as? T` | 只用一种null处理方式 |
| KT-0528 | 🔴 | NULL_SAFETY深度变异String?#36 | `val x:String?=...;flow{val x=risky()!!;emit(x)}` | ?.let或catch |
| KT-0529 | 🟡 | NULL_SAFETY深度变异Set<Int>#37 | `init{val x=late};lateinit var late:Set<Set<Int>>` | lateinit放init前面 |
| KT-0530 | 🔴 | NULL_SAFETY深度变异Long#38 | `val x:Long=...;x!!.length` | 用?.或?:默认值 |
| KT-0531 | 🔴 | NULL_SAFETY深度变异Any#39 | `val x:Any=...;!!.method()` | ?.let |
| KT-0532 | 🔴 | NULL_SAFETY深度变异List<String>#40 | `val x:List<String>=...;a!!.b!!.c!!.d` | ?.链+单点let |
| KT-0533 | 🔴 | NULL_SAFETY深度变异Int#41 | `val x:Int=...;lateinit var x;x.method()` | ::x.isInitialized |
| KT-0534 | 🔴 | NULL_SAFETY深度变异Short#42 | `val x:Short=...;javaObj.field;x.length` | 显式类型+安全调用 |
| KT-0535 | 🟡 | NULL_SAFETY深度变异Any?#43 | `val x:Any?=...;a?.b?.c!!.d` | 统一用?.或统一用!! |
| KT-0536 | 🟡 | NULL_SAFETY深度变异String#44 | `val x:String=...;init{x.method()};lateinit var x` | lateinit放init之前 |
| KT-0537 | 🟡 | NULL_SAFETY深度变异Byte#45 | `val x:Byte=...;a?.let{b?.let{c?.let{}}}}` | 提取函数或flatMap |
| KT-0538 | 🟡 | NULL_SAFETY深度变异Boolean?#46 | `val x:Boolean?=...;val x=y as? T;x.method()` | as?.let或?:return |
| KT-0539 | 🟡 | NULL_SAFETY深度变异Sequence<Long>#47 | `val x:Sequence<Long>=...;a?.let{if(x)return}` | return@let |
| KT-0540 | ⚪ | NULL_SAFETY深度变异Char#48 | `val x:Char=...;val x:T=...;x?.method()` | x.method() |
| KT-0541 | ⚪ | NULL_SAFETY深度变异Double?#49 | `val x:Double?=...;val x=y?:null` | ?:默认值或抛异常 |
| KT-0542 | ⚪ | NULL_SAFETY深度变异Array<Boolean>#50 | `val x:Array<Boolean>=...;val x=y?:y` | 直接用y |
| KT-0543 | 🟡 | NULL_SAFETY深度变异Float#51 | `val l:Float<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0544 | ⚪ | NULL_SAFETY深度变异Long?#52 | `val x:Long?=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0545 | 🟡 | NULL_SAFETY深度变异MutableList<Double>#53 | `val l:MutableList<Double><{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0546 | ⚪ | NULL_SAFETY深度变异Boolean#54 | `val x:Boolean=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0547 | 🟡 | NULL_SAFETY深度变异Int?#55 | `val l:Int?<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0548 | ⚪ | NULL_SAFETY深度变异Map<String,Int>#56 | `val x:Map<String,Int>=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0549 | 🟡 | NULL_SAFETY深度变异Double#57 | `val l:Double<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0550 | ⚪ | NULL_SAFETY深度变异String?#58 | `val x:String?=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0551 | 🟡 | NULL_SAFETY深度变异Set<Int>#59 | `val l:Set<Int><{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0552 | ⚪ | NULL_SAFETY深度变异Long#60 | `val x:Long=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0553 | 🔴 | NULL_SAFETY深度变异Any#61 | `val x:Any=...;val x=map[key]!!;x.method()` | map[key]?.let或getOrDefault |
| KT-0554 | 🔴 | NULL_SAFETY深度变异List<String>#62 | `val x:List<String>=...;val x=risky()!!?:default` | 统一用?.或!!不混 |
| KT-0555 | 🟡 | NULL_SAFETY深度变异Int#63 | `val x:Int=...;val x=y.takeIf{it>0}!!` | takeIf?.let |
| KT-0556 | 🟡 | NULL_SAFETY深度变异Short#64 | `val x:Short=...;list.firstOrNull()!!.prop` | firstOrNull?.prop |
| KT-0557 | 🟡 | NULL_SAFETY深度变异Any?#65 | `val x:Any?=...;fun <T> f(t:T){t!!.hashCode()}` | <T:Any>约束 |
| KT-0558 | ⚪ | NULL_SAFETY深度变异String#66 | `val x:String=...;x?.let{it!!.prop}` | x?.prop直接 |
| KT-0559 | ⚪ | NULL_SAFETY深度变异Byte#67 | `val x:Byte=...;requireNotNull(x);x.prop` | 直接用x |
| KT-0560 | 🔴 | NULL_SAFETY深度变异Boolean?#68 | `val x:Boolean?=...;val v=map.get(key)!!;v.method()` | getOrDefault |
| KT-0561 | 🟡 | NULL_SAFETY深度变异Sequence<Long>#69 | `val x:Sequence<Long>=...;flow{val x=repo.get()!!;emit(x)}` | ?.let+emit |
| KT-0562 | 🟡 | NULL_SAFETY深度变异Char#70 | `val x:Char=...;val x=requireNotNull(y?:z)` | 统一方式 |
| KT-0563 | 🟡 | NULL_SAFETY深度变异Double?#71 | `val x:Double?=...;try{x!!;risky()}finally{x.method()}` | 局部val快照 |
| KT-0564 | 🔴 | NULL_SAFETY深度变异Array<Boolean>#72 | `val x:Array<Boolean>=...;var x:Any?=null;thread{x!!;x=null};` | AtomicReference+?.let |
| KT-0565 | 🔴 | NULL_SAFETY深度变异Float#73 | `val x:Float=...;val x=y!!?:z as? T` | 只用一种null处理方式 |
| KT-0566 | 🔴 | NULL_SAFETY深度变异Long?#74 | `val x:Long?=...;flow{val x=risky()!!;emit(x)}` | ?.let或catch |
| KT-0567 | 🟡 | NULL_SAFETY深度变异MutableList<Double>#75 | `init{val x=late};lateinit var late:MutableMutableList<Double` | lateinit放init前面 |
| KT-0568 | 🔴 | NULL_SAFETY深度变异Boolean#76 | `val x:Boolean=...;x!!.length` | 用?.或?:默认值 |
| KT-0569 | 🔴 | NULL_SAFETY深度变异Int?#77 | `val x:Int?=...;!!.method()` | ?.let |
| KT-0570 | 🔴 | NULL_SAFETY深度变异Map<String,Int>#78 | `val x:Map<String,Int>=...;a!!.b!!.c!!.d` | ?.链+单点let |
| KT-0571 | 🔴 | NULL_SAFETY深度变异Double#79 | `val x:Double=...;lateinit var x;x.method()` | ::x.isInitialized |
| KT-0572 | 🔴 | NULL_SAFETY深度变异String?#80 | `val x:String?=...;javaObj.field;x.length` | 显式类型+安全调用 |
| KT-0573 | 🟡 | NULL_SAFETY深度变异Set<Int>#81 | `val x:Set<Int>=...;a?.b?.c!!.d` | 统一用?.或统一用!! |
| KT-0574 | 🟡 | NULL_SAFETY深度变异Long#82 | `val x:Long=...;init{x.method()};lateinit var x` | lateinit放init之前 |
| KT-0575 | 🟡 | NULL_SAFETY深度变异Any#83 | `val x:Any=...;a?.let{b?.let{c?.let{}}}}` | 提取函数或flatMap |
| KT-0576 | 🟡 | NULL_SAFETY深度变异List<String>#84 | `val x:List<String>=...;val x=y as? T;x.method()` | as?.let或?:return |
| KT-0577 | 🟡 | NULL_SAFETY深度变异Int#85 | `val x:Int=...;a?.let{if(x)return}` | return@let |
| KT-0578 | ⚪ | NULL_SAFETY深度变异Short#86 | `val x:Short=...;val x:T=...;x?.method()` | x.method() |
| KT-0579 | ⚪ | NULL_SAFETY深度变异Any?#87 | `val x:Any?=...;val x=y?:null` | ?:默认值或抛异常 |
| KT-0580 | ⚪ | NULL_SAFETY深度变异String#88 | `val x:String=...;val x=y?:y` | 直接用y |
| KT-0581 | 🟡 | NULL_SAFETY深度变异Byte#89 | `val l:Byte<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0582 | ⚪ | NULL_SAFETY深度变异Boolean?#90 | `val x:Boolean?=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0583 | 🟡 | NULL_SAFETY深度变异Sequence<Long>#91 | `val l:Sequence<Long><{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0584 | ⚪ | NULL_SAFETY深度变异Char#92 | `val x:Char=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0585 | 🟡 | NULL_SAFETY深度变异Double?#93 | `val l:Double?<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0586 | ⚪ | NULL_SAFETY深度变异Array<Boolean>#94 | `val x:Array<Boolean>=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0587 | 🟡 | NULL_SAFETY深度变异Float#95 | `val l:Float<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0588 | ⚪ | NULL_SAFETY深度变异Long?#96 | `val x:Long?=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0589 | 🟡 | NULL_SAFETY深度变异MutableList<Double>#97 | `val l:MutableList<Double><{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0590 | ⚪ | NULL_SAFETY深度变异Boolean#98 | `val x:Boolean=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0591 | 🔴 | NULL_SAFETY深度变异Int?#99 | `val x:Int?=...;val x=map[key]!!;x.method()` | map[key]?.let或getOrDefault |
| KT-0592 | 🔴 | NULL_SAFETY深度变异Map<String,Int>#100 | `val x:Map<String,Int>=...;val x=risky()!!?:default` | 统一用?.或!!不混 |
| KT-0593 | 🟡 | NULL_SAFETY深度变异Double#101 | `val x:Double=...;val x=y.takeIf{it>0}!!` | takeIf?.let |
| KT-0594 | 🟡 | NULL_SAFETY深度变异String?#102 | `val x:String?=...;list.firstOrNull()!!.prop` | firstOrNull?.prop |
| KT-0595 | 🟡 | NULL_SAFETY深度变异Set<Int>#103 | `val x:Set<Int>=...;fun <T> f(t:T){t!!.hashCode()}` | <T:Any>约束 |
| KT-0596 | ⚪ | NULL_SAFETY深度变异Long#104 | `val x:Long=...;x?.let{it!!.prop}` | x?.prop直接 |
| KT-0597 | ⚪ | NULL_SAFETY深度变异Any#105 | `val x:Any=...;requireNotNull(x);x.prop` | 直接用x |
| KT-0598 | 🔴 | NULL_SAFETY深度变异List<String>#106 | `val x:List<String>=...;val v=map.get(key)!!;v.method()` | getOrDefault |
| KT-0599 | 🟡 | NULL_SAFETY深度变异Int#107 | `val x:Int=...;flow{val x=repo.get()!!;emit(x)}` | ?.let+emit |
| KT-0600 | 🟡 | NULL_SAFETY深度变异Short#108 | `val x:Short=...;val x=requireNotNull(y?:z)` | 统一方式 |
| KT-0601 | 🟡 | NULL_SAFETY深度变异Any?#109 | `val x:Any?=...;try{x!!;risky()}finally{x.method()}` | 局部val快照 |
| KT-0602 | 🔴 | NULL_SAFETY深度变异String#110 | `val x:String=...;var x:Any?=null;thread{x!!;x=null};thread{x` | AtomicReference+?.let |
| KT-0603 | 🔴 | NULL_SAFETY深度变异Byte#111 | `val x:Byte=...;val x=y!!?:z as? T` | 只用一种null处理方式 |
| KT-0604 | 🔴 | NULL_SAFETY深度变异Boolean?#112 | `val x:Boolean?=...;flow{val x=risky()!!;emit(x)}` | ?.let或catch |
| KT-0605 | 🟡 | NULL_SAFETY深度变异Sequence<Long>#113 | `init{val x=late};lateinit var late:Sequence<Long>` | lateinit放init前面 |
| KT-0606 | 🔴 | NULL_SAFETY深度变异Char#114 | `val x:Char=...;x!!.length` | 用?.或?:默认值 |
| KT-0607 | 🔴 | NULL_SAFETY深度变异Double?#115 | `val x:Double?=...;!!.method()` | ?.let |
| KT-0608 | 🔴 | NULL_SAFETY深度变异Array<Boolean>#116 | `val x:Array<Boolean>=...;a!!.b!!.c!!.d` | ?.链+单点let |
| KT-0609 | 🔴 | NULL_SAFETY深度变异Float#117 | `val x:Float=...;lateinit var x;x.method()` | ::x.isInitialized |
| KT-0610 | 🔴 | NULL_SAFETY深度变异Long?#118 | `val x:Long?=...;javaObj.field;x.length` | 显式类型+安全调用 |
| KT-0611 | 🟡 | NULL_SAFETY深度变异MutableList<Double>#119 | `val x:MutableList<Double>=...;a?.b?.c!!.d` | 统一用?.或统一用!! |
| KT-0612 | 🟡 | NULL_SAFETY深度变异Boolean#120 | `val x:Boolean=...;init{x.method()};lateinit var x` | lateinit放init之前 |
| KT-0613 | 🟡 | NULL_SAFETY深度变异Int?#121 | `val x:Int?=...;a?.let{b?.let{c?.let{}}}}` | 提取函数或flatMap |
| KT-0614 | 🟡 | NULL_SAFETY深度变异Map<String,Int>#122 | `val x:Map<String,Int>=...;val x=y as? T;x.method()` | as?.let或?:return |
| KT-0615 | 🟡 | NULL_SAFETY深度变异Double#123 | `val x:Double=...;a?.let{if(x)return}` | return@let |
| KT-0616 | ⚪ | NULL_SAFETY深度变异String?#124 | `val x:String?=...;val x:T=...;x?.method()` | x.method() |
| KT-0617 | ⚪ | NULL_SAFETY深度变异Set<Int>#125 | `val x:Set<Int>=...;val x=y?:null` | ?:默认值或抛异常 |
| KT-0618 | ⚪ | NULL_SAFETY深度变异Long#126 | `val x:Long=...;val x=y?:y` | 直接用y |
| KT-0619 | 🟡 | NULL_SAFETY深度变异Any#127 | `val l:Any<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0620 | ⚪ | NULL_SAFETY深度变异List<String>#128 | `val x:List<String>=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0621 | 🟡 | NULL_SAFETY深度变异Int#129 | `val l:Int<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0622 | ⚪ | NULL_SAFETY深度变异Short#130 | `val x:Short=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0623 | 🟡 | NULL_SAFETY深度变异Any?#131 | `val l:Any?<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0624 | ⚪ | NULL_SAFETY深度变异String#132 | `val x:String=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0625 | 🟡 | NULL_SAFETY深度变异Byte#133 | `val l:Byte<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0626 | ⚪ | NULL_SAFETY深度变异Boolean?#134 | `val x:Boolean?=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0627 | 🟡 | NULL_SAFETY深度变异Sequence<Long>#135 | `val l:Sequence<Long><{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0628 | ⚪ | NULL_SAFETY深度变异Char#136 | `val x:Char=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0629 | 🔴 | NULL_SAFETY深度变异Double?#137 | `val x:Double?=...;val x=map[key]!!;x.method()` | map[key]?.let或getOrDefault |
| KT-0630 | 🔴 | NULL_SAFETY深度变异Array<Boolean>#138 | `val x:Array<Boolean>=...;val x=risky()!!?:default` | 统一用?.或!!不混 |
| KT-0631 | 🟡 | NULL_SAFETY深度变异Float#139 | `val x:Float=...;val x=y.takeIf{it>0}!!` | takeIf?.let |
| KT-0632 | 🟡 | NULL_SAFETY深度变异Long?#140 | `val x:Long?=...;list.firstOrNull()!!.prop` | firstOrNull?.prop |
| KT-0633 | 🟡 | NULL_SAFETY深度变异MutableList<Double>#141 | `val x:MutableList<Double>=...;fun <T> f(t:T){t!!.hashCode()}` | <T:Any>约束 |
| KT-0634 | ⚪ | NULL_SAFETY深度变异Boolean#142 | `val x:Boolean=...;x?.let{it!!.prop}` | x?.prop直接 |
| KT-0635 | ⚪ | NULL_SAFETY深度变异Int?#143 | `val x:Int?=...;requireNotNull(x);x.prop` | 直接用x |
| KT-0636 | 🔴 | NULL_SAFETY深度变异Map<String,Int>#144 | `val x:Map<String,Int>=...;val v=map.get(key)!!;v.method()` | getOrDefault |
| KT-0637 | 🟡 | NULL_SAFETY深度变异Double#145 | `val x:Double=...;flow{val x=repo.get()!!;emit(x)}` | ?.let+emit |
| KT-0638 | 🟡 | NULL_SAFETY深度变异String?#146 | `val x:String?=...;val x=requireNotNull(y?:z)` | 统一方式 |
| KT-0639 | 🟡 | NULL_SAFETY深度变异Set<Int>#147 | `val x:Set<Int>=...;try{x!!;risky()}finally{x.method()}` | 局部val快照 |
| KT-0640 | 🔴 | NULL_SAFETY深度变异Long#148 | `val x:Long=...;var x:Any?=null;thread{x!!;x=null};thread{x.m` | AtomicReference+?.let |
| KT-0641 | 🔴 | NULL_SAFETY深度变异Any#149 | `val x:Any=...;val x=y!!?:z as? T` | 只用一种null处理方式 |
| KT-0642 | 🔴 | NULL_SAFETY深度变异List<String>#150 | `val x:List<String>=...;flow{val x=risky()!!;emit(x)}` | ?.let或catch |
| KT-0643 | 🟡 | NULL_SAFETY深度变异Int#151 | `init{val x=late};lateinit var late:Int` | lateinit放init前面 |
| KT-0644 | 🔴 | NULL_SAFETY深度变异Short#152 | `val x:Short=...;x!!.length` | 用?.或?:默认值 |
| KT-0645 | 🔴 | NULL_SAFETY深度变异Any?#153 | `val x:Any?=...;!!.method()` | ?.let |
| KT-0646 | 🔴 | NULL_SAFETY深度变异String#154 | `val x:String=...;a!!.b!!.c!!.d` | ?.链+单点let |
| KT-0647 | 🔴 | NULL_SAFETY深度变异Byte#155 | `val x:Byte=...;lateinit var x;x.method()` | ::x.isInitialized |
| KT-0648 | 🔴 | NULL_SAFETY深度变异Boolean?#156 | `val x:Boolean?=...;javaObj.field;x.length` | 显式类型+安全调用 |
| KT-0649 | 🟡 | NULL_SAFETY深度变异Sequence<Long>#157 | `val x:Sequence<Long>=...;a?.b?.c!!.d` | 统一用?.或统一用!! |
| KT-0650 | 🟡 | NULL_SAFETY深度变异Char#158 | `val x:Char=...;init{x.method()};lateinit var x` | lateinit放init之前 |
| KT-0651 | 🟡 | NULL_SAFETY深度变异Double?#159 | `val x:Double?=...;a?.let{b?.let{c?.let{}}}}` | 提取函数或flatMap |
| KT-0652 | 🟡 | NULL_SAFETY深度变异Array<Boolean>#160 | `val x:Array<Boolean>=...;val x=y as? T;x.method()` | as?.let或?:return |
| KT-0653 | 🟡 | NULL_SAFETY深度变异Float#161 | `val x:Float=...;a?.let{if(x)return}` | return@let |
| KT-0654 | ⚪ | NULL_SAFETY深度变异Long?#162 | `val x:Long?=...;val x:T=...;x?.method()` | x.method() |
| KT-0655 | ⚪ | NULL_SAFETY深度变异MutableList<Double>#163 | `val x:MutableList<Double>=...;val x=y?:null` | ?:默认值或抛异常 |
| KT-0656 | ⚪ | NULL_SAFETY深度变异Boolean#164 | `val x:Boolean=...;val x=y?:y` | 直接用y |
| KT-0657 | 🟡 | NULL_SAFETY深度变异Int?#165 | `val l:Int?<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0658 | ⚪ | NULL_SAFETY深度变异Map<String,Int>#166 | `val x:Map<String,Int>=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0659 | 🟡 | NULL_SAFETY深度变异Double#167 | `val l:Double<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0660 | ⚪ | NULL_SAFETY深度变异String?#168 | `val x:String?=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0661 | 🟡 | NULL_SAFETY深度变异Set<Int>#169 | `val l:Set<Int><{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0662 | ⚪ | NULL_SAFETY深度变异Long#170 | `val x:Long=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0663 | 🟡 | NULL_SAFETY深度变异Any#171 | `val l:Any<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0664 | ⚪ | NULL_SAFETY深度变异List<String>#172 | `val x:List<String>=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0665 | 🟡 | NULL_SAFETY深度变异Int#173 | `val l:Int<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0666 | ⚪ | NULL_SAFETY深度变异Short#174 | `val x:Short=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0667 | 🔴 | NULL_SAFETY深度变异Any?#175 | `val x:Any?=...;val x=map[key]!!;x.method()` | map[key]?.let或getOrDefault |
| KT-0668 | 🔴 | NULL_SAFETY深度变异String#176 | `val x:String=...;val x=risky()!!?:default` | 统一用?.或!!不混 |
| KT-0669 | 🟡 | NULL_SAFETY深度变异Byte#177 | `val x:Byte=...;val x=y.takeIf{it>0}!!` | takeIf?.let |
| KT-0670 | 🟡 | NULL_SAFETY深度变异Boolean?#178 | `val x:Boolean?=...;list.firstOrNull()!!.prop` | firstOrNull?.prop |
| KT-0671 | 🟡 | NULL_SAFETY深度变异Sequence<Long>#179 | `val x:Sequence<Long>=...;fun <T> f(t:T){t!!.hashCode()}` | <T:Any>约束 |
| KT-0672 | ⚪ | NULL_SAFETY深度变异Char#180 | `val x:Char=...;x?.let{it!!.prop}` | x?.prop直接 |
| KT-0673 | ⚪ | NULL_SAFETY深度变异Double?#181 | `val x:Double?=...;requireNotNull(x);x.prop` | 直接用x |
| KT-0674 | 🔴 | NULL_SAFETY深度变异Array<Boolean>#182 | `val x:Array<Boolean>=...;val v=map.get(key)!!;v.method()` | getOrDefault |
| KT-0675 | 🟡 | NULL_SAFETY深度变异Float#183 | `val x:Float=...;flow{val x=repo.get()!!;emit(x)}` | ?.let+emit |
| KT-0676 | 🟡 | NULL_SAFETY深度变异Long?#184 | `val x:Long?=...;val x=requireNotNull(y?:z)` | 统一方式 |
| KT-0677 | 🟡 | NULL_SAFETY深度变异MutableList<Double>#185 | `val x:MutableList<Double>=...;try{x!!;risky()}finally{x.meth` | 局部val快照 |
| KT-0678 | 🔴 | NULL_SAFETY深度变异Boolean#186 | `val x:Boolean=...;var x:Any?=null;thread{x!!;x=null};thread{` | AtomicReference+?.let |
| KT-0679 | 🔴 | NULL_SAFETY深度变异Int?#187 | `val x:Int?=...;val x=y!!?:z as? T` | 只用一种null处理方式 |
| KT-0680 | 🔴 | NULL_SAFETY深度变异Map<String,Int>#188 | `val x:Map<String,Int>=...;flow{val x=risky()!!;emit(x)}` | ?.let或catch |
| KT-0681 | 🟡 | NULL_SAFETY深度变异Double#189 | `init{val x=late};lateinit var late:Double` | lateinit放init前面 |
| KT-0682 | 🔴 | NULL_SAFETY深度变异String?#190 | `val x:String?=...;x!!.length` | 用?.或?:默认值 |
| KT-0683 | 🔴 | NULL_SAFETY深度变异Set<Int>#191 | `val x:Set<Int>=...;!!.method()` | ?.let |
| KT-0684 | 🔴 | NULL_SAFETY深度变异Long#192 | `val x:Long=...;a!!.b!!.c!!.d` | ?.链+单点let |
| KT-0685 | 🔴 | NULL_SAFETY深度变异Any#193 | `val x:Any=...;lateinit var x;x.method()` | ::x.isInitialized |
| KT-0686 | 🔴 | NULL_SAFETY深度变异List<String>#194 | `val x:List<String>=...;javaObj.field;x.length` | 显式类型+安全调用 |
| KT-0687 | 🟡 | NULL_SAFETY深度变异Int#195 | `val x:Int=...;a?.b?.c!!.d` | 统一用?.或统一用!! |
| KT-0688 | 🟡 | NULL_SAFETY深度变异Short#196 | `val x:Short=...;init{x.method()};lateinit var x` | lateinit放init之前 |
| KT-0689 | 🟡 | NULL_SAFETY深度变异Any?#197 | `val x:Any?=...;a?.let{b?.let{c?.let{}}}}` | 提取函数或flatMap |
| KT-0690 | 🟡 | NULL_SAFETY深度变异String#198 | `val x:String=...;val x=y as? T;x.method()` | as?.let或?:return |
| KT-0691 | 🟡 | NULL_SAFETY深度变异Byte#199 | `val x:Byte=...;a?.let{if(x)return}` | return@let |
| KT-0692 | ⚪ | NULL_SAFETY深度变异Boolean?#200 | `val x:Boolean?=...;val x:T=...;x?.method()` | x.method() |
| KT-0693 | ⚪ | NULL_SAFETY深度变异Sequence<Long>#201 | `val x:Sequence<Long>=...;val x=y?:null` | ?:默认值或抛异常 |
| KT-0694 | ⚪ | NULL_SAFETY深度变异Char#202 | `val x:Char=...;val x=y?:y` | 直接用y |
| KT-0695 | 🟡 | NULL_SAFETY深度变异Double?#203 | `val l:Double?<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0696 | ⚪ | NULL_SAFETY深度变异Array<Boolean>#204 | `val x:Array<Boolean>=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0697 | 🟡 | NULL_SAFETY深度变异Float#205 | `val l:Float<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0698 | ⚪ | NULL_SAFETY深度变异Long?#206 | `val x:Long?=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0699 | 🟡 | NULL_SAFETY深度变异MutableList<Double>#207 | `val l:MutableList<Double><{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0700 | ⚪ | NULL_SAFETY深度变异Boolean#208 | `val x:Boolean=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0701 | 🟡 | NULL_SAFETY深度变异Int?#209 | `val l:Int?<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0702 | ⚪ | NULL_SAFETY深度变异Map<String,Int>#210 | `val x:Map<String,Int>=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0703 | 🟡 | NULL_SAFETY深度变异Double#211 | `val l:Double<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0704 | ⚪ | NULL_SAFETY深度变异String?#212 | `val x:String?=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0705 | 🔴 | NULL_SAFETY深度变异Set<Int>#213 | `val x:Set<Int>=...;val x=map[key]!!;x.method()` | map[key]?.let或getOrDefault |
| KT-0706 | 🔴 | NULL_SAFETY深度变异Long#214 | `val x:Long=...;val x=risky()!!?:default` | 统一用?.或!!不混 |
| KT-0707 | 🟡 | NULL_SAFETY深度变异Any#215 | `val x:Any=...;val x=y.takeIf{it>0}!!` | takeIf?.let |
| KT-0708 | 🟡 | NULL_SAFETY深度变异List<String>#216 | `val x:List<String>=...;list.firstOrNull()!!.prop` | firstOrNull?.prop |
| KT-0709 | 🟡 | NULL_SAFETY深度变异Int#217 | `val x:Int=...;fun <T> f(t:T){t!!.hashCode()}` | <T:Any>约束 |
| KT-0710 | ⚪ | NULL_SAFETY深度变异Short#218 | `val x:Short=...;x?.let{it!!.prop}` | x?.prop直接 |
| KT-0711 | ⚪ | NULL_SAFETY深度变异Any?#219 | `val x:Any?=...;requireNotNull(x);x.prop` | 直接用x |
| KT-0712 | 🔴 | NULL_SAFETY深度变异String#220 | `val x:String=...;val v=map.get(key)!!;v.method()` | getOrDefault |
| KT-0713 | 🟡 | NULL_SAFETY深度变异Byte#221 | `val x:Byte=...;flow{val x=repo.get()!!;emit(x)}` | ?.let+emit |
| KT-0714 | 🟡 | NULL_SAFETY深度变异Boolean?#222 | `val x:Boolean?=...;val x=requireNotNull(y?:z)` | 统一方式 |
| KT-0715 | 🟡 | NULL_SAFETY深度变异Sequence<Long>#223 | `val x:Sequence<Long>=...;try{x!!;risky()}finally{x.method()}` | 局部val快照 |
| KT-0716 | 🔴 | NULL_SAFETY深度变异Char#224 | `val x:Char=...;var x:Any?=null;thread{x!!;x=null};thread{x.m` | AtomicReference+?.let |
| KT-0717 | 🔴 | NULL_SAFETY深度变异Double?#225 | `val x:Double?=...;val x=y!!?:z as? T` | 只用一种null处理方式 |
| KT-0718 | 🔴 | NULL_SAFETY深度变异Array<Boolean>#226 | `val x:Array<Boolean>=...;flow{val x=risky()!!;emit(x)}` | ?.let或catch |
| KT-0719 | 🟡 | NULL_SAFETY深度变异Float#227 | `init{val x=late};lateinit var late:Float` | lateinit放init前面 |
| KT-0720 | 🔴 | NULL_SAFETY深度变异Long?#228 | `val x:Long?=...;x!!.length` | 用?.或?:默认值 |
| KT-0721 | 🔴 | NULL_SAFETY深度变异MutableList<Double>#229 | `val x:MutableList<Double>=...;!!.method()` | ?.let |
| KT-0722 | 🔴 | NULL_SAFETY深度变异Boolean#230 | `val x:Boolean=...;a!!.b!!.c!!.d` | ?.链+单点let |
| KT-0723 | 🔴 | NULL_SAFETY深度变异Int?#231 | `val x:Int?=...;lateinit var x;x.method()` | ::x.isInitialized |
| KT-0724 | 🔴 | NULL_SAFETY深度变异Map<String,Int>#232 | `val x:Map<String,Int>=...;javaObj.field;x.length` | 显式类型+安全调用 |
| KT-0725 | 🟡 | NULL_SAFETY深度变异Double#233 | `val x:Double=...;a?.b?.c!!.d` | 统一用?.或统一用!! |
| KT-0726 | 🟡 | NULL_SAFETY深度变异String?#234 | `val x:String?=...;init{x.method()};lateinit var x` | lateinit放init之前 |
| KT-0727 | 🟡 | NULL_SAFETY深度变异Set<Int>#235 | `val x:Set<Int>=...;a?.let{b?.let{c?.let{}}}}` | 提取函数或flatMap |
| KT-0728 | 🟡 | NULL_SAFETY深度变异Long#236 | `val x:Long=...;val x=y as? T;x.method()` | as?.let或?:return |
| KT-0729 | 🟡 | NULL_SAFETY深度变异Any#237 | `val x:Any=...;a?.let{if(x)return}` | return@let |
| KT-0730 | ⚪ | NULL_SAFETY深度变异List<String>#238 | `val x:List<String>=...;val x:T=...;x?.method()` | x.method() |
| KT-0731 | ⚪ | NULL_SAFETY深度变异Int#239 | `val x:Int=...;val x=y?:null` | ?:默认值或抛异常 |
| KT-0732 | ⚪ | NULL_SAFETY深度变异Short#240 | `val x:Short=...;val x=y?:y` | 直接用y |
| KT-0733 | 🟡 | NULL_SAFETY深度变异Any?#241 | `val l:Any?<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0734 | ⚪ | NULL_SAFETY深度变异String#242 | `val x:String=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0735 | 🟡 | NULL_SAFETY深度变异Byte#243 | `val l:Byte<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0736 | ⚪ | NULL_SAFETY深度变异Boolean?#244 | `val x:Boolean?=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0737 | 🟡 | NULL_SAFETY深度变异Sequence<Long>#245 | `val l:Sequence<Long><{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0738 | ⚪ | NULL_SAFETY深度变异Char#246 | `val x:Char=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0739 | 🟡 | NULL_SAFETY深度变异Double?#247 | `val l:Double?<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0740 | ⚪ | NULL_SAFETY深度变异Array<Boolean>#248 | `val x:Array<Boolean>=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0741 | 🟡 | NULL_SAFETY深度变异Float#249 | `val l:Float<{t}>;l.filterNotNull().size` | 直接?.let |
| KT-0742 | ⚪ | NULL_SAFETY深度变异Long?#250 | `val x:Long?=...;val x:{t}=...;val y={t}?:null` | 提供实际默认值 |
| KT-0743 | 🔴 | NULL_SAFETY深度变异MutableList<Double>#251 | `val x:MutableList<Double>=...;val x=map[key]!!;x.method()` | map[key]?.let或getOrDefault |
| KT-0744 | 🔴 | NULL_SAFETY深度变异Boolean#252 | `val x:Boolean=...;val x=risky()!!?:default` | 统一用?.或!!不混 |
| KT-0745 | 🟡 | NULL_SAFETY深度变异Int?#253 | `val x:Int?=...;val x=y.takeIf{it>0}!!` | takeIf?.let |
| KT-0746 | 🟡 | NULL_SAFETY深度变异Map<String,Int>#254 | `val x:Map<String,Int>=...;list.firstOrNull()!!.prop` | firstOrNull?.prop |
| KT-0747 | 🟡 | NULL_SAFETY深度变异Double#255 | `val x:Double=...;fun <T> f(t:T){t!!.hashCode()}` | <T:Any>约束 |
| KT-0748 | ⚪ | NULL_SAFETY深度变异String?#256 | `val x:String?=...;x?.let{it!!.prop}` | x?.prop直接 |
| KT-0749 | ⚪ | NULL_SAFETY深度变异Set<Int>#257 | `val x:Set<Int>=...;requireNotNull(x);x.prop` | 直接用x |
| KT-0750 | 🔴 | NULL_SAFETY深度变异Long#258 | `val x:Long=...;val v=map.get(key)!!;v.method()` | getOrDefault |
| KT-0751 | 🟡 | NULL_SAFETY深度变异Any#259 | `val x:Any=...;flow{val x=repo.get()!!;emit(x)}` | ?.let+emit |
| KT-0752 | 🟡 | NULL_SAFETY深度变异List<String>#260 | `val x:List<String>=...;val x=requireNotNull(y?:z)` | 统一方式 |
| KT-0753 | 🟡 | NULL_SAFETY深度变异Int#261 | `val x:Int=...;try{x!!;risky()}finally{x.method()}` | 局部val快照 |

## PERFORMANCE（150条）

| ID | 严重度 | Bug描述 | 触发模式 | 修复建议 |
|---|------|------|------|------|
| KT-0127 | 🟡 | 循环中字符串拼接 | `for(i in 1..1000){s+="$i"}` | buildString |
| KT-0128 | 🟡 | 不必要装箱 | `val x:Int?=42;x?.let{` | 避免可空 |
| KT-0129 | 🟡 | 高阶函数链过多 | `list.filter{.map{.flatMap{` | 合并为单次fold |
| KT-0130 | 🟡 | 未使用sequence | `list.map{.filter{.first()` | asSequence |
| KT-0131 | ⚪ | 不必要的对象创建 | `for(i in 1..n){Regex("\\d").findAll(s)}` | 提取到循环外 |
| KT-0132 | ⚪ | varargs传递数组 | `fun f(vararg s:String);f(arrayOf("a"))` | f(*arrayOf) |
| KT-0244 | 🔴 | N+1查询在集合操作 | `users.forEach{u->db.query(u.id)}` | batch查询 |
| KT-0245 | 🟡 | 频繁创建SimpleDateFormat | `for(i in 1..1000){SimpleDateFormat("yyyy").parse(s)}` | java.time或DateTimeFormatter |
| KT-0246 | 🟡 | 未使用@Stable/@Immutable | `data class D(val x:Int,val y:Int)` | 加@Immutable |
| KT-0247 | ⚪ | 不必要的LazyThreadSafetyMode | `val x by lazy(LazyThreadSafetyMode.SYNCHRONIZED){42}` | NONE |
| KT-0248 | ⚪ | byte数组频繁copy | `for(chunk in stream){buf.copyOfRange()}` | 复用buffer |
| KT-0270 | 🔴 | EventBus频道互相触发死循环 | `EventBus.emit('a',ev);subscribe('a'){emit('b',ev)};subscribe` | 事件去重+深度限制 |
| KT-0271 | 🟡 | lazy初始化循环依赖 | `val a by lazy{b};val b by lazy{a}` | break one with lateinit |
| KT-0272 | 🔴 | inline函数互相展开到JVM 64KB限制 | `inline fun a(){b()};inline fun b(){a()}` | 一个去掉inline |
| KT-0273 | 🟡 | 5000条BugDB规则扫描空文件 | `BugDB.scan("")` | 空字符串短路返回 |
| KT-0283 | ⚪ | 10MB的JSON被当源码读入 | `val src=File('10mb.json').readText();compile(src)` | 文件扩展名检查 |
| KT-0284 | 🟡 | APK当JSON解析不报错只返回空 | `JsonUtil.decode(apkBytes)` | 检查Content-Type或magic bytes |
| KT-0289 | 🟡 | 路标指针指向null却认为有值 | `LiveDeclarationGraph.getNode返回null但下游当非null用` | getNode后判null |
| KT-0303 | 🟡 | asSequence+first+重复调用=每次重新求值 | `val seq=list.asSequence().filter{;seq.first();seq.first()` | 先toList再取 |
| KT-0308 | 🟡 | inline+递归泛型=编译时间爆炸 | `inline fun <reified T> f(n:Int){if(n>0)f<T>(n-1)}` | 去掉inline或用while |
| KT-0316 | 🟡 | parallelStream在ForkJoinPool里反而串行 | `list.parallelStream().map{sleep(100);it}.collect()` | 自定义ForkJoinPool |
| KT-0327 | 🟡 | benchmark跑在debug模式结果当release用 | `./gradlew benchmark在debug变体` | 用release变体或benchmark构建类型 |
| KT-0445 | ⚪ | varargs传递数组（Int版） | `fun f(vararg s:Int);f(arrayOf(\"a\"))` | f(*arrayOf) |
| KT-0446 | ⚪ | varargs传递数组（Long版） | `fun f(vararg s:Long);f(arrayOf(\"a\"))` | f(*arrayOf) |
| KT-0447 | ⚪ | varargs传递数组（Double版） | `fun f(vararg s:Double);f(arrayOf(\"a\"))` | f(*arrayOf) |
| KT-0448 | ⚪ | varargs传递数组（Boolean版） | `fun f(vararg s:Boolean);f(arrayOf(\"a\"))` | f(*arrayOf) |
| KT-0476 | 🟡 | 频繁创建Gson实例 | `Gson().toJson(obj)` | 单例 |
| KT-0477 | 🟡 | OkHttpClient每次创建 | `OkHttpClient().newCall(...)` | 单例 |
| KT-2672 | 🟡 | PERFORMANCE深度变异String#0 | `val x:String=...;for(i in 1..1000){s+=\"\$i\"}` | buildString |
| KT-2673 | 🟡 | PERFORMANCE深度变异Byte#1 | `val x:Byte?=42;x?.let{` | 避免可空 |
| KT-2674 | 🟡 | PERFORMANCE深度变异Boolean?#2 | `val x:Boolean?=...;list.filter{.map{.flatMap{` | 合并为单次fold |
| KT-2675 | 🟡 | PERFORMANCE深度变异Sequence<Long>#3 | `val x:Sequence<Long>=...;list.map{.filter{.first()` | asSequence |
| KT-2676 | ⚪ | PERFORMANCE深度变异Char#4 | `val x:Char=...;for(i in 1..n){Regex(\"\\\\d\").findAll(s)}` | 提取到循环外 |
| KT-2677 | ⚪ | PERFORMANCE深度变异Double?#5 | `fun f(vararg s:Double?);f(arrayOf(\"a\"))` | f(*arrayOf) |
| KT-2678 | 🔴 | PERFORMANCE深度变异Array<Boolean>#6 | `val x:Array<Boolean>=...;users.forEach{u->db.query(u.id)}` | batch查询 |
| KT-2679 | 🟡 | PERFORMANCE深度变异Float#7 | `val x:Float=...;for(i in 1..1000){SimpleDateFormat(\"yyyy\")` | java.time或DateTimeFormatter |
| KT-2680 | 🟡 | PERFORMANCE深度变异Long?#8 | `data class D(val x:Long?,val y:Long?)` | 加@Immutable |
| KT-2681 | ⚪ | PERFORMANCE深度变异MutableList<Double>#9 | `val x:MutableList<Double>=...;val x by lazy(LazyThreadSafety` | NONE |
| KT-2682 | ⚪ | PERFORMANCE深度变异Boolean#10 | `val x:Boolean=...;for(chunk in stream){buf.copyOfRange()}` | 复用buffer |
| KT-2683 | 🔴 | PERFORMANCE深度变异Int?#11 | `val x:Int?=...;EventBus.emit('a',ev);subscribe('a'){emit('b'` | 事件去重+深度限制 |
| KT-2684 | 🟡 | PERFORMANCE深度变异Map<String,Int>#12 | `val x:Map<String,Int>=...;val a by lazy{b};val b by lazy{a}` | break one with lateinit |
| KT-2685 | 🔴 | PERFORMANCE深度变异Double#13 | `val x:Double=...;inline fun a(){b()};inline fun b(){a()}` | 一个去掉inline |
| KT-2686 | 🟡 | PERFORMANCE深度变异String?#14 | `val x:String?=...;BugDB.scan(\"\")` | 空字符串短路返回 |
| KT-2687 | ⚪ | PERFORMANCE深度变异Set<Int>#15 | `val x:Set<Int>=...;val src=File('10mb.json').readText();comp` | 文件扩展名检查 |
| KT-2688 | 🟡 | PERFORMANCE深度变异Long#16 | `val x:Long=...;JsonUtil.decode(apkBytes)` | 检查Content-Type或magic bytes |
| KT-2689 | 🟡 | PERFORMANCE深度变异Any#17 | `val x:Any=...;LiveDeclarationGraph.getNode返回null但下游当非null用` | getNode后判null |
| KT-2690 | 🟡 | PERFORMANCE深度变异List<String>#18 | `val x:List<String>=...;val seq=list.asSequence().filter{;seq` | 先toList再取 |
| KT-2691 | 🟡 | PERFORMANCE深度变异Int#19 | `val x:Int=...;inline fun <reified T> f(n:Int){if(n>0)f<T>(n-` | 去掉inline或用while |
| KT-2692 | 🟡 | PERFORMANCE深度变异Short#20 | `val x:Short=...;list.parallelStream().map{sleep(100);it}.col` | 自定义ForkJoinPool |
| KT-2693 | 🟡 | PERFORMANCE深度变异Any?#21 | `val x:Any?=...;./gradlew benchmark在debug变体` | 用release变体或benchmark构建类型 |
| KT-2694 | ⚪ | PERFORMANCE深度变异String#22 | `fun f(vararg s:String);f(arrayOf(\\\"a\\\"))` | f(*arrayOf) |
| KT-2695 | ⚪ | PERFORMANCE深度变异Byte#23 | `val x:Byte=...;fun f(vararg s:Long);f(arrayOf(\\\"a\\\"))` | f(*arrayOf) |
| KT-2696 | ⚪ | PERFORMANCE深度变异Boolean?#24 | `val x:Boolean?=...;fun f(vararg s:Double);f(arrayOf(\\\"a\\\` | f(*arrayOf) |
| KT-2697 | ⚪ | PERFORMANCE深度变异Sequence<Long>#25 | `val x:Sequence<Long>=...;fun f(vararg s:Boolean);f(arrayOf(\` | f(*arrayOf) |
| KT-2698 | 🟡 | PERFORMANCE深度变异Char#26 | `val x:Char=...;Gson().toJson(obj)` | 单例 |
| KT-2699 | 🟡 | PERFORMANCE深度变异Double?#27 | `val x:Double?=...;OkHttpClient().newCall(...)` | 单例 |
| KT-2700 | 🟡 | PERFORMANCE深度变异Array<Boolean>#28 | `val x:Array<Boolean>=...;for(i in 1..1000){s+=\"\$i\"}` | buildString |
| KT-2701 | 🟡 | PERFORMANCE深度变异Float#29 | `val x:Float?=42;x?.let{` | 避免可空 |
| KT-2702 | 🟡 | PERFORMANCE深度变异Long?#30 | `val x:Long?=...;list.filter{.map{.flatMap{` | 合并为单次fold |
| KT-2703 | 🟡 | PERFORMANCE深度变异MutableList<Double>#31 | `val x:MutableList<Double>=...;list.map{.filter{.first()` | asSequence |
| KT-2704 | ⚪ | PERFORMANCE深度变异Boolean#32 | `val x:Boolean=...;for(i in 1..n){Regex(\"\\\\d\").findAll(s)` | 提取到循环外 |
| KT-2705 | ⚪ | PERFORMANCE深度变异Int?#33 | `fun f(vararg s:Int??);f(arrayOf(\"a\"))` | f(*arrayOf) |
| KT-2706 | 🔴 | PERFORMANCE深度变异Map<String,Int>#34 | `val x:Map<String,Int>=...;users.forEach{u->db.query(u.id)}` | batch查询 |
| KT-2707 | 🟡 | PERFORMANCE深度变异Double#35 | `val x:Double=...;for(i in 1..1000){SimpleDateFormat(\"yyyy\"` | java.time或DateTimeFormatter |
| KT-2708 | 🟡 | PERFORMANCE深度变异String?#36 | `data class D(val x:String?,val y:String?)` | 加@Immutable |
| KT-2709 | ⚪ | PERFORMANCE深度变异Set<Int>#37 | `val x:Set<Int>=...;val x by lazy(LazyThreadSafetyMode.SYNCHR` | NONE |
| KT-2710 | ⚪ | PERFORMANCE深度变异Long#38 | `val x:Long=...;for(chunk in stream){buf.copyOfRange()}` | 复用buffer |
| KT-2711 | 🔴 | PERFORMANCE深度变异Any#39 | `val x:Any=...;EventBus.emit('a',ev);subscribe('a'){emit('b',` | 事件去重+深度限制 |
| KT-2712 | 🟡 | PERFORMANCE深度变异List<String>#40 | `val x:List<String>=...;val a by lazy{b};val b by lazy{a}` | break one with lateinit |
| KT-2713 | 🔴 | PERFORMANCE深度变异Int#41 | `val x:Int=...;inline fun a(){b()};inline fun b(){a()}` | 一个去掉inline |
| KT-2714 | 🟡 | PERFORMANCE深度变异Short#42 | `val x:Short=...;BugDB.scan(\"\")` | 空字符串短路返回 |
| KT-2715 | ⚪ | PERFORMANCE深度变异Any?#43 | `val x:Any?=...;val src=File('10mb.json').readText();compile(` | 文件扩展名检查 |
| KT-2716 | 🟡 | PERFORMANCE深度变异String#44 | `val x:String=...;JsonUtil.decode(apkBytes)` | 检查Content-Type或magic bytes |
| KT-2717 | 🟡 | PERFORMANCE深度变异Byte#45 | `val x:Byte=...;LiveDeclarationGraph.getNode返回null但下游当非null用` | getNode后判null |
| KT-2718 | 🟡 | PERFORMANCE深度变异Boolean?#46 | `val x:Boolean?=...;val seq=list.asSequence().filter{;seq.fir` | 先toList再取 |
| KT-2719 | 🟡 | PERFORMANCE深度变异Sequence<Long>#47 | `inline fun <reified T> f(n:Sequence<Long>){if(n>0)f<T>(n-1)}` | 去掉inline或用while |
| KT-2720 | 🟡 | PERFORMANCE深度变异Char#48 | `val x:Char=...;list.parallelStream().map{sleep(100);it}.coll` | 自定义ForkJoinPool |
| KT-2721 | 🟡 | PERFORMANCE深度变异Double?#49 | `val x:Double?=...;./gradlew benchmark在debug变体` | 用release变体或benchmark构建类型 |
| KT-2722 | ⚪ | PERFORMANCE深度变异Array<Boolean>#50 | `fun f(vararg s:Array<Boolean>);f(arrayOf(\\\"a\\\"))` | f(*arrayOf) |
| KT-2723 | ⚪ | PERFORMANCE深度变异Float#51 | `val x:Float=...;fun f(vararg s:Long);f(arrayOf(\\\"a\\\"))` | f(*arrayOf) |
| KT-2724 | ⚪ | PERFORMANCE深度变异Long?#52 | `val x:Long?=...;fun f(vararg s:Double);f(arrayOf(\\\"a\\\"))` | f(*arrayOf) |
| KT-2725 | ⚪ | PERFORMANCE深度变异MutableList<Double>#53 | `val x:MutableList<Double>=...;fun f(vararg s:Boolean);f(arra` | f(*arrayOf) |
| KT-2726 | 🟡 | PERFORMANCE深度变异Boolean#54 | `val x:Boolean=...;Gson().toJson(obj)` | 单例 |
| KT-2727 | 🟡 | PERFORMANCE深度变异Int?#55 | `val x:Int?=...;OkHttpClient().newCall(...)` | 单例 |
| KT-2728 | 🟡 | PERFORMANCE深度变异Map<String,Int>#56 | `val x:Map<String,Int>=...;for(i in 1..1000){s+=\"\$i\"}` | buildString |
| KT-2729 | 🟡 | PERFORMANCE深度变异Double#57 | `val x:Double?=42;x?.let{` | 避免可空 |
| KT-2730 | 🟡 | PERFORMANCE深度变异String?#58 | `val x:String?=...;list.filter{.map{.flatMap{` | 合并为单次fold |
| KT-2731 | 🟡 | PERFORMANCE深度变异Set<Int>#59 | `val x:Set<Int>=...;list.map{.filter{.first()` | asSequence |
| KT-2732 | ⚪ | PERFORMANCE深度变异Long#60 | `val x:Long=...;for(i in 1..n){Regex(\"\\\\d\").findAll(s)}` | 提取到循环外 |
| KT-2733 | ⚪ | PERFORMANCE深度变异Any#61 | `fun f(vararg s:Any);f(arrayOf(\"a\"))` | f(*arrayOf) |
| KT-2734 | 🔴 | PERFORMANCE深度变异List<String>#62 | `val x:List<String>=...;users.forEach{u->db.query(u.id)}` | batch查询 |
| KT-2735 | 🟡 | PERFORMANCE深度变异Int#63 | `val x:Int=...;for(i in 1..1000){SimpleDateFormat(\"yyyy\").p` | java.time或DateTimeFormatter |
| KT-2736 | 🟡 | PERFORMANCE深度变异Short#64 | `data class D(val x:Short,val y:Short)` | 加@Immutable |
| KT-2737 | ⚪ | PERFORMANCE深度变异Any?#65 | `val x:Any?=...;val x by lazy(LazyThreadSafetyMode.SYNCHRONIZ` | NONE |
| KT-2738 | ⚪ | PERFORMANCE深度变异String#66 | `val x:String=...;for(chunk in stream){buf.copyOfRange()}` | 复用buffer |
| KT-2739 | 🔴 | PERFORMANCE深度变异Byte#67 | `val x:Byte=...;EventBus.emit('a',ev);subscribe('a'){emit('b'` | 事件去重+深度限制 |
| KT-2740 | 🟡 | PERFORMANCE深度变异Boolean?#68 | `val x:Boolean?=...;val a by lazy{b};val b by lazy{a}` | break one with lateinit |
| KT-2741 | 🔴 | PERFORMANCE深度变异Sequence<Long>#69 | `val x:Sequence<Long>=...;inline fun a(){b()};inline fun b(){` | 一个去掉inline |
| KT-2742 | 🟡 | PERFORMANCE深度变异Char#70 | `val x:Char=...;BugDB.scan(\"\")` | 空字符串短路返回 |
| KT-2743 | ⚪ | PERFORMANCE深度变异Double?#71 | `val x:Double?=...;val src=File('10mb.json').readText();compi` | 文件扩展名检查 |
| KT-2744 | 🟡 | PERFORMANCE深度变异Array<Boolean>#72 | `val x:Array<Boolean>=...;JsonUtil.decode(apkBytes)` | 检查Content-Type或magic bytes |
| KT-2745 | 🟡 | PERFORMANCE深度变异Float#73 | `val x:Float=...;LiveDeclarationGraph.getNode返回null但下游当非null用` | getNode后判null |
| KT-2746 | 🟡 | PERFORMANCE深度变异Long?#74 | `val x:Long?=...;val seq=list.asSequence().filter{;seq.first(` | 先toList再取 |
| KT-2747 | 🟡 | PERFORMANCE深度变异MutableList<Double>#75 | `inline fun <reified T> f(n:MutableMutableList<Double><Double` | 去掉inline或用while |
| KT-2748 | 🟡 | PERFORMANCE深度变异Boolean#76 | `val x:Boolean=...;list.parallelStream().map{sleep(100);it}.c` | 自定义ForkJoinPool |
| KT-2749 | 🟡 | PERFORMANCE深度变异Int?#77 | `val x:Int?=...;./gradlew benchmark在debug变体` | 用release变体或benchmark构建类型 |
| KT-2750 | ⚪ | PERFORMANCE深度变异Map<String,Int>#78 | `fun f(vararg s:Map<String,Int>);f(arrayOf(\\\"a\\\"))` | f(*arrayOf) |
| KT-2751 | ⚪ | PERFORMANCE深度变异Double#79 | `val x:Double=...;fun f(vararg s:Long);f(arrayOf(\\\"a\\\"))` | f(*arrayOf) |
| KT-2752 | ⚪ | PERFORMANCE深度变异String?#80 | `val x:String?=...;fun f(vararg s:Double);f(arrayOf(\\\"a\\\"` | f(*arrayOf) |
| KT-2753 | ⚪ | PERFORMANCE深度变异Set<Int>#81 | `val x:Set<Int>=...;fun f(vararg s:Boolean);f(arrayOf(\\\"a\\` | f(*arrayOf) |
| KT-2754 | 🟡 | PERFORMANCE深度变异Long#82 | `val x:Long=...;Gson().toJson(obj)` | 单例 |
| KT-2755 | 🟡 | PERFORMANCE深度变异Any#83 | `val x:Any=...;OkHttpClient().newCall(...)` | 单例 |
| KT-2756 | 🟡 | PERFORMANCE深度变异List<String>#84 | `val x:List<String>=...;for(i in 1..1000){s+=\"\$i\"}` | buildString |
| KT-2757 | 🟡 | PERFORMANCE深度变异Int#85 | `val x:Int=...;val x:Int?=42;x?.let{` | 避免可空 |
| KT-2758 | 🟡 | PERFORMANCE深度变异Short#86 | `val x:Short=...;list.filter{.map{.flatMap{` | 合并为单次fold |
| KT-2759 | 🟡 | PERFORMANCE深度变异Any?#87 | `val x:Any?=...;list.map{.filter{.first()` | asSequence |
| KT-2760 | ⚪ | PERFORMANCE深度变异String#88 | `val x:String=...;for(i in 1..n){Regex(\"\\\\d\").findAll(s)}` | 提取到循环外 |
| KT-2761 | ⚪ | PERFORMANCE深度变异Byte#89 | `fun f(vararg s:Byte);f(arrayOf(\"a\"))` | f(*arrayOf) |
| KT-2762 | 🔴 | PERFORMANCE深度变异Boolean?#90 | `val x:Boolean?=...;users.forEach{u->db.query(u.id)}` | batch查询 |
| KT-2763 | 🟡 | PERFORMANCE深度变异Sequence<Long>#91 | `val x:Sequence<Long>=...;for(i in 1..1000){SimpleDateFormat(` | java.time或DateTimeFormatter |
| KT-2764 | 🟡 | PERFORMANCE深度变异Char#92 | `data class D(val x:Char,val y:Char)` | 加@Immutable |
| KT-2765 | ⚪ | PERFORMANCE深度变异Double?#93 | `val x:Double?=...;val x by lazy(LazyThreadSafetyMode.SYNCHRO` | NONE |
| KT-2766 | ⚪ | PERFORMANCE深度变异Array<Boolean>#94 | `val x:Array<Boolean>=...;for(chunk in stream){buf.copyOfRang` | 复用buffer |
| KT-2767 | 🔴 | PERFORMANCE深度变异Float#95 | `val x:Float=...;EventBus.emit('a',ev);subscribe('a'){emit('b` | 事件去重+深度限制 |
| KT-2768 | 🟡 | PERFORMANCE深度变异Long?#96 | `val x:Long?=...;val a by lazy{b};val b by lazy{a}` | break one with lateinit |
| KT-2769 | 🔴 | PERFORMANCE深度变异MutableList<Double>#97 | `val x:MutableList<Double>=...;inline fun a(){b()};inline fun` | 一个去掉inline |
| KT-2770 | 🟡 | PERFORMANCE深度变异Boolean#98 | `val x:Boolean=...;BugDB.scan(\"\")` | 空字符串短路返回 |
| KT-2771 | ⚪ | PERFORMANCE深度变异Int?#99 | `val x:Int?=...;val src=File('10mb.json').readText();compile(` | 文件扩展名检查 |
| KT-2772 | 🟡 | PERFORMANCE深度变异Map<String,Int>#100 | `val x:Map<String,Int>=...;JsonUtil.decode(apkBytes)` | 检查Content-Type或magic bytes |
| KT-2773 | 🟡 | PERFORMANCE深度变异Double#101 | `val x:Double=...;LiveDeclarationGraph.getNode返回null但下游当非null` | getNode后判null |
| KT-2774 | 🟡 | PERFORMANCE深度变异String?#102 | `val x:String?=...;val seq=list.asSequence().filter{;seq.firs` | 先toList再取 |
| KT-2775 | 🟡 | PERFORMANCE深度变异Set<Int>#103 | `inline fun <reified T> f(n:Set<Int>){if(n>0)f<T>(n-1)}` | 去掉inline或用while |
| KT-2776 | 🟡 | PERFORMANCE深度变异Long#104 | `val x:Long=...;list.parallelStream().map{sleep(100);it}.coll` | 自定义ForkJoinPool |
| KT-2777 | 🟡 | PERFORMANCE深度变异Any#105 | `val x:Any=...;./gradlew benchmark在debug变体` | 用release变体或benchmark构建类型 |
| KT-2778 | ⚪ | PERFORMANCE深度变异List<String>#106 | `fun f(vararg s:List<String><String>);f(arrayOf(\\\"a\\\"))` | f(*arrayOf) |
| KT-2779 | ⚪ | PERFORMANCE深度变异Int#107 | `val x:Int=...;fun f(vararg s:Long);f(arrayOf(\\\"a\\\"))` | f(*arrayOf) |
| KT-2780 | ⚪ | PERFORMANCE深度变异Short#108 | `val x:Short=...;fun f(vararg s:Double);f(arrayOf(\\\"a\\\"))` | f(*arrayOf) |
| KT-2781 | ⚪ | PERFORMANCE深度变异Any?#109 | `val x:Any?=...;fun f(vararg s:Boolean);f(arrayOf(\\\"a\\\"))` | f(*arrayOf) |
| KT-2782 | 🟡 | PERFORMANCE深度变异String#110 | `val x:String=...;Gson().toJson(obj)` | 单例 |
| KT-2783 | 🟡 | PERFORMANCE深度变异Byte#111 | `val x:Byte=...;OkHttpClient().newCall(...)` | 单例 |
| KT-2784 | 🟡 | PERFORMANCE深度变异Boolean?#112 | `val x:Boolean?=...;for(i in 1..1000){s+=\"\$i\"}` | buildString |
| KT-2785 | 🟡 | PERFORMANCE深度变异Sequence<Long>#113 | `val x:Sequence<Long>?=42;x?.let{` | 避免可空 |
| KT-2786 | 🟡 | PERFORMANCE深度变异Char#114 | `val x:Char=...;list.filter{.map{.flatMap{` | 合并为单次fold |
| KT-2787 | 🟡 | PERFORMANCE深度变异Double?#115 | `val x:Double?=...;list.map{.filter{.first()` | asSequence |
| KT-2788 | ⚪ | PERFORMANCE深度变异Array<Boolean>#116 | `val x:Array<Boolean>=...;for(i in 1..n){Regex(\"\\\\d\").fin` | 提取到循环外 |
| KT-2789 | ⚪ | PERFORMANCE深度变异Float#117 | `fun f(vararg s:Float);f(arrayOf(\"a\"))` | f(*arrayOf) |
| KT-2790 | 🔴 | PERFORMANCE深度变异Long?#118 | `val x:Long?=...;users.forEach{u->db.query(u.id)}` | batch查询 |
| KT-2791 | 🟡 | PERFORMANCE深度变异MutableList<Double>#119 | `val x:MutableList<Double>=...;for(i in 1..1000){SimpleDateFo` | java.time或DateTimeFormatter |
| KT-2792 | 🟡 | PERFORMANCE深度变异Boolean#120 | `data class D(val x:Boolean,val y:Boolean)` | 加@Immutable |
| KT-2793 | ⚪ | PERFORMANCE深度变异Int?#121 | `val x:Int?=...;val x by lazy(LazyThreadSafetyMode.SYNCHRONIZ` | NONE |

## REFLECTION（100条）

| ID | 严重度 | Bug描述 | 触发模式 | 修复建议 |
|---|------|------|------|------|
| KT-0059 | 🔴 | 反射访问私有成员 | `cls.getDeclaredField("secret");f.isAccessible=true` | 提供公开接口 |
| KT-0060 | 🟡 | KClass与Java Class混淆 | `MyClass::class.java与MyClass::class` | 明确Java/Kotlin |
| KT-0061 | 🟡 | callBy参数顺序错误 | `func.callBy(mapOf(param to value))` | 使用带名参数 |
| KT-0062 | ⚪ | 反射性能开销 | `cls.members.forEach{` | 缓存KCallable |
| KT-0180 | 🔴 | 反射修改final字段 | `val f=cls.getDeclaredField("x");f.isAccessible=true;f.set(ob` | 提供公开setter |
| KT-0181 | 🟡 | KFunction反射调用性能 | `func.call(1,2)` | 缓存KCallable |
| KT-0182 | 🟡 | 反射获取泛型参数 | `cls.typeParameters[0].upperBounds` | reified+inline |
| KT-0183 | 🟡 | ::class在companion上 | `MyClass::class与MyClass.Companion::class` | 明确区分 |
| KT-0184 | ⚪ | KClass.simpleName与javaClass.simpleName | `KClass.simpleName可能为null` | 用qualifiedName |
| KT-0185 | ⚪ | 不必要的反射实例化 | `cls.java.newInstance()` | cls.createInstance()或工厂 |
| KT-1348 | 🔴 | REFLECTION深度变异String#0 | `val x:String=...;cls.getDeclaredField(\"secret\");f.isAccess` | 提供公开接口 |
| KT-1349 | 🟡 | REFLECTION深度变异Byte#1 | `val x:Byte=...;MyClass::class.java与MyClass::class` | 明确Java/Kotlin |
| KT-1350 | 🟡 | REFLECTION深度变异Boolean?#2 | `val x:Boolean?=...;func.callBy(mapOf(param to value))` | 使用带名参数 |
| KT-1351 | ⚪ | REFLECTION深度变异Sequence<Long>#3 | `val x:Sequence<Long>=...;cls.members.forEach{` | 缓存KCallable |
| KT-1352 | 🔴 | REFLECTION深度变异Char#4 | `val x:Char=...;val f=cls.getDeclaredField(\"x\");f.isAccessi` | 提供公开setter |
| KT-1353 | 🟡 | REFLECTION深度变异Double?#5 | `val x:Double?=...;func.call(1,2)` | 缓存KCallable |
| KT-1354 | 🟡 | REFLECTION深度变异Array<Boolean>#6 | `val x:Array<Boolean>=...;cls.typeParameters[0].upperBounds` | reified+inline |
| KT-1355 | 🟡 | REFLECTION深度变异Float#7 | `val x:Float=...;MyClass::class与MyClass.Companion::class` | 明确区分 |
| KT-1356 | ⚪ | REFLECTION深度变异Long?#8 | `val x:Long?=...;KClass.simpleName可能为null` | 用qualifiedName |
| KT-1357 | ⚪ | REFLECTION深度变异MutableList<Double>#9 | `val x:MutableList<Double>=...;cls.java.newInstance()` | cls.createInstance()或工厂 |
| KT-1358 | 🔴 | REFLECTION深度变异Boolean#10 | `val x:Boolean=...;cls.getDeclaredField(\"secret\");f.isAcces` | 提供公开接口 |
| KT-1359 | 🟡 | REFLECTION深度变异Int?#11 | `val x:Int?=...;MyClass::class.java与MyClass::class` | 明确Java/Kotlin |
| KT-1360 | 🟡 | REFLECTION深度变异Map<String,Int>#12 | `val x:Map<String,Int>=...;func.callBy(mapOf(param to value))` | 使用带名参数 |
| KT-1361 | ⚪ | REFLECTION深度变异Double#13 | `val x:Double=...;cls.members.forEach{` | 缓存KCallable |
| KT-1362 | 🔴 | REFLECTION深度变异String?#14 | `val x:String?=...;val f=cls.getDeclaredField(\"x\");f.isAcce` | 提供公开setter |
| KT-1363 | 🟡 | REFLECTION深度变异Set<Int>#15 | `val x:Set<Int>=...;func.call(1,2)` | 缓存KCallable |
| KT-1364 | 🟡 | REFLECTION深度变异Long#16 | `val x:Long=...;cls.typeParameters[0].upperBounds` | reified+inline |
| KT-1365 | 🟡 | REFLECTION深度变异Any#17 | `val x:Any=...;MyClass::class与MyClass.Companion::class` | 明确区分 |
| KT-1366 | ⚪ | REFLECTION深度变异List<String>#18 | `val x:List<String>=...;KClass.simpleName可能为null` | 用qualifiedName |
| KT-1367 | ⚪ | REFLECTION深度变异Int#19 | `val x:Int=...;cls.java.newInstance()` | cls.createInstance()或工厂 |
| KT-1368 | 🔴 | REFLECTION深度变异Short#20 | `val x:Short=...;cls.getDeclaredField(\"secret\");f.isAccessi` | 提供公开接口 |
| KT-1369 | 🟡 | REFLECTION深度变异Any?#21 | `val x:Any?=...;MyClass::class.java与MyClass::class` | 明确Java/Kotlin |
| KT-1370 | 🟡 | REFLECTION深度变异String#22 | `val x:String=...;func.callBy(mapOf(param to value))` | 使用带名参数 |
| KT-1371 | ⚪ | REFLECTION深度变异Byte#23 | `val x:Byte=...;cls.members.forEach{` | 缓存KCallable |
| KT-1372 | 🔴 | REFLECTION深度变异Boolean?#24 | `val x:Boolean?=...;val f=cls.getDeclaredField(\"x\");f.isAcc` | 提供公开setter |
| KT-1373 | 🟡 | REFLECTION深度变异Sequence<Long>#25 | `val x:Sequence<Long>=...;func.call(1,2)` | 缓存KCallable |
| KT-1374 | 🟡 | REFLECTION深度变异Char#26 | `val x:Char=...;cls.typeParameters[0].upperBounds` | reified+inline |
| KT-1375 | 🟡 | REFLECTION深度变异Double?#27 | `val x:Double?=...;MyClass::class与MyClass.Companion::class` | 明确区分 |
| KT-1376 | ⚪ | REFLECTION深度变异Array<Boolean>#28 | `val x:Array<Boolean>=...;KClass.simpleName可能为null` | 用qualifiedName |
| KT-1377 | ⚪ | REFLECTION深度变异Float#29 | `val x:Float=...;cls.java.newInstance()` | cls.createInstance()或工厂 |
| KT-1378 | 🔴 | REFLECTION深度变异Long?#30 | `val x:Long?=...;cls.getDeclaredField(\"secret\");f.isAccessi` | 提供公开接口 |
| KT-1379 | 🟡 | REFLECTION深度变异MutableList<Double>#31 | `val x:MutableList<Double>=...;MyClass::class.java与MyClass::c` | 明确Java/Kotlin |
| KT-1380 | 🟡 | REFLECTION深度变异Boolean#32 | `val x:Boolean=...;func.callBy(mapOf(param to value))` | 使用带名参数 |
| KT-1381 | ⚪ | REFLECTION深度变异Int?#33 | `val x:Int?=...;cls.members.forEach{` | 缓存KCallable |
| KT-1382 | 🔴 | REFLECTION深度变异Map<String,Int>#34 | `val x:Map<String,Int>=...;val f=cls.getDeclaredField(\"x\");` | 提供公开setter |
| KT-1383 | 🟡 | REFLECTION深度变异Double#35 | `val x:Double=...;func.call(1,2)` | 缓存KCallable |
| KT-1384 | 🟡 | REFLECTION深度变异String?#36 | `val x:String?=...;cls.typeParameters[0].upperBounds` | reified+inline |
| KT-1385 | 🟡 | REFLECTION深度变异Set<Int>#37 | `val x:Set<Int>=...;MyClass::class与MyClass.Companion::class` | 明确区分 |
| KT-1386 | ⚪ | REFLECTION深度变异Long#38 | `val x:Long=...;KClass.simpleName可能为null` | 用qualifiedName |
| KT-1387 | ⚪ | REFLECTION深度变异Any#39 | `val x:Any=...;cls.java.newInstance()` | cls.createInstance()或工厂 |
| KT-1388 | 🔴 | REFLECTION深度变异List<String>#40 | `val x:List<String>=...;cls.getDeclaredField(\"secret\");f.is` | 提供公开接口 |
| KT-1389 | 🟡 | REFLECTION深度变异Int#41 | `val x:Int=...;MyClass::class.java与MyClass::class` | 明确Java/Kotlin |
| KT-1390 | 🟡 | REFLECTION深度变异Short#42 | `val x:Short=...;func.callBy(mapOf(param to value))` | 使用带名参数 |
| KT-1391 | ⚪ | REFLECTION深度变异Any?#43 | `val x:Any?=...;cls.members.forEach{` | 缓存KCallable |
| KT-1392 | 🔴 | REFLECTION深度变异String#44 | `val x:String=...;val f=cls.getDeclaredField(\"x\");f.isAcces` | 提供公开setter |
| KT-1393 | 🟡 | REFLECTION深度变异Byte#45 | `val x:Byte=...;func.call(1,2)` | 缓存KCallable |
| KT-1394 | 🟡 | REFLECTION深度变异Boolean?#46 | `val x:Boolean?=...;cls.typeParameters[0].upperBounds` | reified+inline |
| KT-1395 | 🟡 | REFLECTION深度变异Sequence<Long>#47 | `val x:Sequence<Long>=...;MyClass::class与MyClass.Companion::c` | 明确区分 |
| KT-1396 | ⚪ | REFLECTION深度变异Char#48 | `val x:Char=...;KClass.simpleName可能为null` | 用qualifiedName |
| KT-1397 | ⚪ | REFLECTION深度变异Double?#49 | `val x:Double?=...;cls.java.newInstance()` | cls.createInstance()或工厂 |
| KT-1398 | 🔴 | REFLECTION深度变异Array<Boolean>#50 | `val x:Array<Boolean>=...;cls.getDeclaredField(\"secret\");f.` | 提供公开接口 |
| KT-1399 | 🟡 | REFLECTION深度变异Float#51 | `val x:Float=...;MyClass::class.java与MyClass::class` | 明确Java/Kotlin |
| KT-1400 | 🟡 | REFLECTION深度变异Long?#52 | `val x:Long?=...;func.callBy(mapOf(param to value))` | 使用带名参数 |
| KT-1401 | ⚪ | REFLECTION深度变异MutableList<Double>#53 | `val x:MutableList<Double>=...;cls.members.forEach{` | 缓存KCallable |
| KT-1402 | 🔴 | REFLECTION深度变异Boolean#54 | `val x:Boolean=...;val f=cls.getDeclaredField(\"x\");f.isAcce` | 提供公开setter |
| KT-1403 | 🟡 | REFLECTION深度变异Int?#55 | `val x:Int?=...;func.call(1,2)` | 缓存KCallable |
| KT-1404 | 🟡 | REFLECTION深度变异Map<String,Int>#56 | `val x:Map<String,Int>=...;cls.typeParameters[0].upperBounds` | reified+inline |
| KT-1405 | 🟡 | REFLECTION深度变异Double#57 | `val x:Double=...;MyClass::class与MyClass.Companion::class` | 明确区分 |
| KT-1406 | ⚪ | REFLECTION深度变异String?#58 | `val x:String?=...;KClass.simpleName可能为null` | 用qualifiedName |
| KT-1407 | ⚪ | REFLECTION深度变异Set<Int>#59 | `val x:Set<Int>=...;cls.java.newInstance()` | cls.createInstance()或工厂 |
| KT-1408 | 🔴 | REFLECTION深度变异Long#60 | `val x:Long=...;cls.getDeclaredField(\"secret\");f.isAccessib` | 提供公开接口 |
| KT-1409 | 🟡 | REFLECTION深度变异Any#61 | `val x:Any=...;MyClass::class.java与MyClass::class` | 明确Java/Kotlin |
| KT-1410 | 🟡 | REFLECTION深度变异List<String>#62 | `val x:List<String>=...;func.callBy(mapOf(param to value))` | 使用带名参数 |
| KT-1411 | ⚪ | REFLECTION深度变异Int#63 | `val x:Int=...;cls.members.forEach{` | 缓存KCallable |
| KT-1412 | 🔴 | REFLECTION深度变异Short#64 | `val x:Short=...;val f=cls.getDeclaredField(\"x\");f.isAccess` | 提供公开setter |
| KT-1413 | 🟡 | REFLECTION深度变异Any?#65 | `val x:Any?=...;func.call(1,2)` | 缓存KCallable |
| KT-1414 | 🟡 | REFLECTION深度变异String#66 | `val x:String=...;cls.typeParameters[0].upperBounds` | reified+inline |
| KT-1415 | 🟡 | REFLECTION深度变异Byte#67 | `val x:Byte=...;MyClass::class与MyClass.Companion::class` | 明确区分 |
| KT-1416 | ⚪ | REFLECTION深度变异Boolean?#68 | `val x:Boolean?=...;KClass.simpleName可能为null` | 用qualifiedName |
| KT-1417 | ⚪ | REFLECTION深度变异Sequence<Long>#69 | `val x:Sequence<Long>=...;cls.java.newInstance()` | cls.createInstance()或工厂 |
| KT-1418 | 🔴 | REFLECTION深度变异Char#70 | `val x:Char=...;cls.getDeclaredField(\"secret\");f.isAccessib` | 提供公开接口 |
| KT-1419 | 🟡 | REFLECTION深度变异Double?#71 | `val x:Double?=...;MyClass::class.java与MyClass::class` | 明确Java/Kotlin |
| KT-1420 | 🟡 | REFLECTION深度变异Array<Boolean>#72 | `val x:Array<Boolean>=...;func.callBy(mapOf(param to value))` | 使用带名参数 |
| KT-1421 | ⚪ | REFLECTION深度变异Float#73 | `val x:Float=...;cls.members.forEach{` | 缓存KCallable |
| KT-1422 | 🔴 | REFLECTION深度变异Long?#74 | `val x:Long?=...;val f=cls.getDeclaredField(\"x\");f.isAccess` | 提供公开setter |
| KT-1423 | 🟡 | REFLECTION深度变异MutableList<Double>#75 | `val x:MutableList<Double>=...;func.call(1,2)` | 缓存KCallable |
| KT-1424 | 🟡 | REFLECTION深度变异Boolean#76 | `val x:Boolean=...;cls.typeParameters[0].upperBounds` | reified+inline |
| KT-1425 | 🟡 | REFLECTION深度变异Int?#77 | `val x:Int?=...;MyClass::class与MyClass.Companion::class` | 明确区分 |
| KT-1426 | ⚪ | REFLECTION深度变异Map<String,Int>#78 | `val x:Map<String,Int>=...;KClass.simpleName可能为null` | 用qualifiedName |
| KT-1427 | ⚪ | REFLECTION深度变异Double#79 | `val x:Double=...;cls.java.newInstance()` | cls.createInstance()或工厂 |
| KT-1428 | 🔴 | REFLECTION深度变异String?#80 | `val x:String?=...;cls.getDeclaredField(\"secret\");f.isAcces` | 提供公开接口 |
| KT-1429 | 🟡 | REFLECTION深度变异Set<Int>#81 | `val x:Set<Int>=...;MyClass::class.java与MyClass::class` | 明确Java/Kotlin |
| KT-1430 | 🟡 | REFLECTION深度变异Long#82 | `val x:Long=...;func.callBy(mapOf(param to value))` | 使用带名参数 |
| KT-1431 | ⚪ | REFLECTION深度变异Any#83 | `val x:Any=...;cls.members.forEach{` | 缓存KCallable |
| KT-1432 | 🔴 | REFLECTION深度变异List<String>#84 | `val x:List<String>=...;val f=cls.getDeclaredField(\"x\");f.i` | 提供公开setter |
| KT-1433 | 🟡 | REFLECTION深度变异Int#85 | `val x:Int=...;func.call(1,2)` | 缓存KCallable |
| KT-1434 | 🟡 | REFLECTION深度变异Short#86 | `val x:Short=...;cls.typeParameters[0].upperBounds` | reified+inline |
| KT-1435 | 🟡 | REFLECTION深度变异Any?#87 | `val x:Any?=...;MyClass::class与MyClass.Companion::class` | 明确区分 |
| KT-1436 | ⚪ | REFLECTION深度变异String#88 | `val x:String=...;KClass.simpleName可能为null` | 用qualifiedName |
| KT-1437 | ⚪ | REFLECTION深度变异Byte#89 | `val x:Byte=...;cls.java.newInstance()` | cls.createInstance()或工厂 |

## SEALED_ENUM（100条）

| ID | 严重度 | Bug描述 | 触发模式 | 修复建议 |
|---|------|------|------|------|
| KT-0098 | 🔴 | values()每次新建数组 | `values().find{` | enumEntries() |
| KT-0099 | 🟡 | sealed class跨文件 | `sealed class A;File1:class B:A();File2:class C:A()` | 移到同文件或sealed interface |
| KT-0100 | 🟡 | 枚举ordinal依赖 | `when(e.ordinal){0->...}` | 用枚举常量 |
| KT-0101 | ⚪ | 枚举包含可变状态 | `enum class E(var x:Int)` | val |
| KT-0216 | 🟡 | when穷举sealed少子类 | `sealed class A;class B:A();when(a){is B->1}` | 加else或sealed interface |
| KT-0217 | 🟡 | 枚举name属性依赖 | `when(e.name){"A"->1}` | 用自定义属性 |
| KT-0218 | ⚪ | 枚举构造函数开销 | `enum class E(val x:Int=0);E.A` | 用object代替 |
| KT-0300 | 🟡 | sealed+反射枚举子类=新增子类不拦截 | `sealed class A;通过反射实例化未知子类` | sealed+禁止反射实例化 |
| KT-0469 | ⚪ | 枚举包含可变状态（Long版） | `enum class E(var x:Long)` | val |
| KT-0470 | ⚪ | 枚举包含可变状态（Double版） | `enum class E(var x:Double)` | val |
| KT-2082 | 🔴 | SEALED_ENUM深度变异String#0 | `val x:String=...;values().find{` | enumEntries() |
| KT-2083 | 🟡 | SEALED_ENUM深度变异Byte#1 | `val x:Byte=...;sealed class A;File1:class B:A();File2:class ` | 移到同文件或sealed interface |
| KT-2084 | 🟡 | SEALED_ENUM深度变异Boolean?#2 | `val x:Boolean?=...;when(e.ordinal){0->...}` | 用枚举常量 |
| KT-2085 | ⚪ | SEALED_ENUM深度变异Sequence<Long>#3 | `enum class E(var x:Sequence<Long>)` | val |
| KT-2086 | 🟡 | SEALED_ENUM深度变异Char#4 | `val x:Char=...;sealed class A;class B:A();when(a){is B->1}` | 加else或sealed interface |
| KT-2087 | 🟡 | SEALED_ENUM深度变异Double?#5 | `val x:Double?=...;when(e.name){\"A\"->1}` | 用自定义属性 |
| KT-2088 | ⚪ | SEALED_ENUM深度变异Array<Boolean>#6 | `enum class E(val x:Array<Boolean>=0);E.A` | 用object代替 |
| KT-2089 | 🟡 | SEALED_ENUM深度变异Float#7 | `val x:Float=...;sealed class A;通过反射实例化未知子类` | sealed+禁止反射实例化 |
| KT-2090 | ⚪ | SEALED_ENUM深度变异Long?#8 | `val x:Long?=...;enum class E(var x:Long)` | val |
| KT-2091 | ⚪ | SEALED_ENUM深度变异MutableList<Double>#9 | `val x:MutableList<Double>=...;enum class E(var x:Double)` | val |
| KT-2092 | 🔴 | SEALED_ENUM深度变异Boolean#10 | `val x:Boolean=...;values().find{` | enumEntries() |
| KT-2093 | 🟡 | SEALED_ENUM深度变异Int?#11 | `val x:Int?=...;sealed class A;File1:class B:A();File2:class ` | 移到同文件或sealed interface |
| KT-2094 | 🟡 | SEALED_ENUM深度变异Map<String,Int>#12 | `val x:Map<String,Int>=...;when(e.ordinal){0->...}` | 用枚举常量 |
| KT-2095 | ⚪ | SEALED_ENUM深度变异Double#13 | `enum class E(var x:Double)` | val |
| KT-2096 | 🟡 | SEALED_ENUM深度变异String?#14 | `val x:String?=...;sealed class A;class B:A();when(a){is B->1` | 加else或sealed interface |
| KT-2097 | 🟡 | SEALED_ENUM深度变异Set<Int>#15 | `val x:Set<Int>=...;when(e.name){\"A\"->1}` | 用自定义属性 |
| KT-2098 | ⚪ | SEALED_ENUM深度变异Long#16 | `enum class E(val x:Long=0);E.A` | 用object代替 |
| KT-2099 | 🟡 | SEALED_ENUM深度变异Any#17 | `val x:Any=...;sealed class A;通过反射实例化未知子类` | sealed+禁止反射实例化 |
| KT-2100 | ⚪ | SEALED_ENUM深度变异List<String>#18 | `val x:List<String>=...;enum class E(var x:Long)` | val |
| KT-2101 | ⚪ | SEALED_ENUM深度变异Int#19 | `val x:Int=...;enum class E(var x:Double)` | val |
| KT-2102 | 🔴 | SEALED_ENUM深度变异Short#20 | `val x:Short=...;values().find{` | enumEntries() |
| KT-2103 | 🟡 | SEALED_ENUM深度变异Any?#21 | `val x:Any?=...;sealed class A;File1:class B:A();File2:class ` | 移到同文件或sealed interface |
| KT-2104 | 🟡 | SEALED_ENUM深度变异String#22 | `val x:String=...;when(e.ordinal){0->...}` | 用枚举常量 |
| KT-2105 | ⚪ | SEALED_ENUM深度变异Byte#23 | `enum class E(var x:Byte)` | val |
| KT-2106 | 🟡 | SEALED_ENUM深度变异Boolean?#24 | `val x:Boolean?=...;sealed class A;class B:A();when(a){is B->` | 加else或sealed interface |
| KT-2107 | 🟡 | SEALED_ENUM深度变异Sequence<Long>#25 | `val x:Sequence<Long>=...;when(e.name){\"A\"->1}` | 用自定义属性 |
| KT-2108 | ⚪ | SEALED_ENUM深度变异Char#26 | `enum class E(val x:Char=0);E.A` | 用object代替 |
| KT-2109 | 🟡 | SEALED_ENUM深度变异Double?#27 | `val x:Double?=...;sealed class A;通过反射实例化未知子类` | sealed+禁止反射实例化 |
| KT-2110 | ⚪ | SEALED_ENUM深度变异Array<Boolean>#28 | `val x:Array<Boolean>=...;enum class E(var x:Long)` | val |
| KT-2111 | ⚪ | SEALED_ENUM深度变异Float#29 | `val x:Float=...;enum class E(var x:Double)` | val |
| KT-2112 | 🔴 | SEALED_ENUM深度变异Long?#30 | `val x:Long?=...;values().find{` | enumEntries() |
| KT-2113 | 🟡 | SEALED_ENUM深度变异MutableList<Double>#31 | `val x:MutableList<Double>=...;sealed class A;File1:class B:A` | 移到同文件或sealed interface |
| KT-2114 | 🟡 | SEALED_ENUM深度变异Boolean#32 | `val x:Boolean=...;when(e.ordinal){0->...}` | 用枚举常量 |
| KT-2115 | ⚪ | SEALED_ENUM深度变异Int?#33 | `enum class E(var x:Int?)` | val |
| KT-2116 | 🟡 | SEALED_ENUM深度变异Map<String,Int>#34 | `val x:Map<String,Int>=...;sealed class A;class B:A();when(a)` | 加else或sealed interface |
| KT-2117 | 🟡 | SEALED_ENUM深度变异Double#35 | `val x:Double=...;when(e.name){\"A\"->1}` | 用自定义属性 |
| KT-2118 | ⚪ | SEALED_ENUM深度变异String?#36 | `enum class E(val x:String?=0);E.A` | 用object代替 |
| KT-2119 | 🟡 | SEALED_ENUM深度变异Set<Int>#37 | `val x:Set<Int>=...;sealed class A;通过反射实例化未知子类` | sealed+禁止反射实例化 |
| KT-2120 | ⚪ | SEALED_ENUM深度变异Long#38 | `val x:Long=...;enum class E(var x:Long)` | val |
| KT-2121 | ⚪ | SEALED_ENUM深度变异Any#39 | `val x:Any=...;enum class E(var x:Double)` | val |
| KT-2122 | 🔴 | SEALED_ENUM深度变异List<String>#40 | `val x:List<String>=...;values().find{` | enumEntries() |
| KT-2123 | 🟡 | SEALED_ENUM深度变异Int#41 | `val x:Int=...;sealed class A;File1:class B:A();File2:class C` | 移到同文件或sealed interface |
| KT-2124 | 🟡 | SEALED_ENUM深度变异Short#42 | `val x:Short=...;when(e.ordinal){0->...}` | 用枚举常量 |
| KT-2125 | ⚪ | SEALED_ENUM深度变异Any?#43 | `enum class E(var x:Any?)` | val |
| KT-2126 | 🟡 | SEALED_ENUM深度变异String#44 | `val x:String=...;sealed class A;class B:A();when(a){is B->1}` | 加else或sealed interface |
| KT-2127 | 🟡 | SEALED_ENUM深度变异Byte#45 | `val x:Byte=...;when(e.name){\"A\"->1}` | 用自定义属性 |
| KT-2128 | ⚪ | SEALED_ENUM深度变异Boolean?#46 | `enum class E(val x:Boolean?=0);E.A` | 用object代替 |
| KT-2129 | 🟡 | SEALED_ENUM深度变异Sequence<Long>#47 | `val x:Sequence<Long>=...;sealed class A;通过反射实例化未知子类` | sealed+禁止反射实例化 |
| KT-2130 | ⚪ | SEALED_ENUM深度变异Char#48 | `val x:Char=...;enum class E(var x:Long)` | val |
| KT-2131 | ⚪ | SEALED_ENUM深度变异Double?#49 | `val x:Double?=...;enum class E(var x:Double)` | val |
| KT-2132 | 🔴 | SEALED_ENUM深度变异Array<Boolean>#50 | `val x:Array<Boolean>=...;values().find{` | enumEntries() |
| KT-2133 | 🟡 | SEALED_ENUM深度变异Float#51 | `val x:Float=...;sealed class A;File1:class B:A();File2:class` | 移到同文件或sealed interface |
| KT-2134 | 🟡 | SEALED_ENUM深度变异Long?#52 | `val x:Long?=...;when(e.ordinal){0->...}` | 用枚举常量 |
| KT-2135 | ⚪ | SEALED_ENUM深度变异MutableList<Double>#53 | `enum class E(var x:MutableMutableList<Double><Double>)` | val |
| KT-2136 | 🟡 | SEALED_ENUM深度变异Boolean#54 | `val x:Boolean=...;sealed class A;class B:A();when(a){is B->1` | 加else或sealed interface |
| KT-2137 | 🟡 | SEALED_ENUM深度变异Int?#55 | `val x:Int?=...;when(e.name){\"A\"->1}` | 用自定义属性 |
| KT-2138 | ⚪ | SEALED_ENUM深度变异Map<String,Int>#56 | `enum class E(val x:Map<String,Int>=0);E.A` | 用object代替 |
| KT-2139 | 🟡 | SEALED_ENUM深度变异Double#57 | `val x:Double=...;sealed class A;通过反射实例化未知子类` | sealed+禁止反射实例化 |
| KT-2140 | ⚪ | SEALED_ENUM深度变异String?#58 | `val x:String?=...;enum class E(var x:Long)` | val |
| KT-2141 | ⚪ | SEALED_ENUM深度变异Set<Int>#59 | `val x:Set<Int>=...;enum class E(var x:Double)` | val |
| KT-2142 | 🔴 | SEALED_ENUM深度变异Long#60 | `val x:Long=...;values().find{` | enumEntries() |
| KT-2143 | 🟡 | SEALED_ENUM深度变异Any#61 | `val x:Any=...;sealed class A;File1:class B:A();File2:class C` | 移到同文件或sealed interface |
| KT-2144 | 🟡 | SEALED_ENUM深度变异List<String>#62 | `val x:List<String>=...;when(e.ordinal){0->...}` | 用枚举常量 |
| KT-2145 | ⚪ | SEALED_ENUM深度变异Int#63 | `val x:Int=...;enum class E(var x:Int)` | val |
| KT-2146 | 🟡 | SEALED_ENUM深度变异Short#64 | `val x:Short=...;sealed class A;class B:A();when(a){is B->1}` | 加else或sealed interface |
| KT-2147 | 🟡 | SEALED_ENUM深度变异Any?#65 | `val x:Any?=...;when(e.name){\"A\"->1}` | 用自定义属性 |
| KT-2148 | ⚪ | SEALED_ENUM深度变异String#66 | `enum class E(val x:String=0);E.A` | 用object代替 |
| KT-2149 | 🟡 | SEALED_ENUM深度变异Byte#67 | `val x:Byte=...;sealed class A;通过反射实例化未知子类` | sealed+禁止反射实例化 |
| KT-2150 | ⚪ | SEALED_ENUM深度变异Boolean?#68 | `val x:Boolean?=...;enum class E(var x:Long)` | val |
| KT-2151 | ⚪ | SEALED_ENUM深度变异Sequence<Long>#69 | `val x:Sequence<Long>=...;enum class E(var x:Double)` | val |
| KT-2152 | 🔴 | SEALED_ENUM深度变异Char#70 | `val x:Char=...;values().find{` | enumEntries() |
| KT-2153 | 🟡 | SEALED_ENUM深度变异Double?#71 | `val x:Double?=...;sealed class A;File1:class B:A();File2:cla` | 移到同文件或sealed interface |
| KT-2154 | 🟡 | SEALED_ENUM深度变异Array<Boolean>#72 | `val x:Array<Boolean>=...;when(e.ordinal){0->...}` | 用枚举常量 |
| KT-2155 | ⚪ | SEALED_ENUM深度变异Float#73 | `enum class E(var x:Float)` | val |
| KT-2156 | 🟡 | SEALED_ENUM深度变异Long?#74 | `val x:Long?=...;sealed class A;class B:A();when(a){is B->1}` | 加else或sealed interface |
| KT-2157 | 🟡 | SEALED_ENUM深度变异MutableList<Double>#75 | `val x:MutableList<Double>=...;when(e.name){\"A\"->1}` | 用自定义属性 |
| KT-2158 | ⚪ | SEALED_ENUM深度变异Boolean#76 | `enum class E(val x:Boolean=0);E.A` | 用object代替 |
| KT-2159 | 🟡 | SEALED_ENUM深度变异Int?#77 | `val x:Int?=...;sealed class A;通过反射实例化未知子类` | sealed+禁止反射实例化 |
| KT-2160 | ⚪ | SEALED_ENUM深度变异Map<String,Int>#78 | `val x:Map<String,Int>=...;enum class E(var x:Long)` | val |
| KT-2161 | ⚪ | SEALED_ENUM深度变异Double#79 | `val x:Double=...;enum class E(var x:Double)` | val |
| KT-2162 | 🔴 | SEALED_ENUM深度变异String?#80 | `val x:String?=...;values().find{` | enumEntries() |
| KT-2163 | 🟡 | SEALED_ENUM深度变异Set<Int>#81 | `val x:Set<Int>=...;sealed class A;File1:class B:A();File2:cl` | 移到同文件或sealed interface |
| KT-2164 | 🟡 | SEALED_ENUM深度变异Long#82 | `val x:Long=...;when(e.ordinal){0->...}` | 用枚举常量 |
| KT-2165 | ⚪ | SEALED_ENUM深度变异Any#83 | `enum class E(var x:Any)` | val |
| KT-2166 | 🟡 | SEALED_ENUM深度变异List<String>#84 | `val x:List<String>=...;sealed class A;class B:A();when(a){is` | 加else或sealed interface |
| KT-2167 | 🟡 | SEALED_ENUM深度变异Int#85 | `val x:Int=...;when(e.name){\"A\"->1}` | 用自定义属性 |
| KT-2168 | ⚪ | SEALED_ENUM深度变异Short#86 | `enum class E(val x:Short=0);E.A` | 用object代替 |
| KT-2169 | 🟡 | SEALED_ENUM深度变异Any?#87 | `val x:Any?=...;sealed class A;通过反射实例化未知子类` | sealed+禁止反射实例化 |
| KT-2170 | ⚪ | SEALED_ENUM深度变异String#88 | `val x:String=...;enum class E(var x:Long)` | val |
| KT-2171 | ⚪ | SEALED_ENUM深度变异Byte#89 | `val x:Byte=...;enum class E(var x:Double)` | val |

## SECURITY（100条）

| ID | 严重度 | Bug描述 | 触发模式 | 修复建议 |
|---|------|------|------|------|
| KT-0133 | 🔴 | 硬编码密钥 | *另修: 用BuildConfig字段, keystore系统, 运行时从服务器拉取* |`val API_KEY="sk-abc123"` | 环境变量或BuildConfig |
| KT-0134 | 🔴 | SQL注入拼接 | *另修: 用Room DAO, 用预编译语句PreparedStatement, 输入白名单校验* |`db.execSQL("SELECT * FROM u WHERE n='$name'")` | 参数化查询 |
| KT-0135 | 🟡 | 日志泄露敏感信息 | `Log.d("TAG","token=$token")` | 脱敏 |
| KT-0136 | 🟡 | 明文存储密码 | `prefs.edit().putString("pwd",password).apply()` | EncryptedSharedPreferences |
| KT-0137 | 🟡 | 未验证SSL证书 | `trustAllCerts()` | 证书固定 |
| KT-0138 | ⚪ | WebView JavaScript启用 | `webView.settings.javaScriptEnabled=true` | 禁用或白名单 |
| KT-0249 | 🔴 | Intent extras明文传敏感数据 | `intent.putExtra("token",token)` | 加密或避免 |
| KT-0250 | 🟡 | WebView.addJavascriptInterface | `webView.addJavascriptInterface(obj,"android")` | @JavascriptInterface仅暴露必要方法 |
| KT-0251 | 🟡 | FileProvider路径遍历 | `FileProvider.getUriForFile(ctx,path)` | 限制根目录 |
| KT-0252 | ⚪ | 日志使用e.printStackTrace | `e.printStackTrace()` | Log.e(TAG,"msg",e) |
| KT-0276 | 🔴 | debug日志里打印完整银行卡号 | `if(BuildConfig.DEBUG)Log.d('PAY',cardNumber)` | release不输出或脱敏 |
| KT-0277 | 🟡 | 环境变量里的密钥被Git提交 | `BuildConfig.API_KEY从local.properties读但.gitignore漏了` | ci环境变量+不提交 |
| KT-0347 | 🟡 | 明文存储密码（Int版） | `prefs.edit().putInt(\"pwd\",password).apply()` | EncryptedSharedPreferences |
| KT-0348 | 🟡 | 明文存储密码（Long版） | `prefs.edit().putLong(\"pwd\",password).apply()` | EncryptedSharedPreferences |
| KT-0349 | 🟡 | 明文存储密码（Double版） | `prefs.edit().putDouble(\"pwd\",password).apply()` | EncryptedSharedPreferences |
| KT-0350 | 🟡 | 明文存储密码（Boolean版） | `prefs.edit().putBoolean(\"pwd\",password).apply()` | EncryptedSharedPreferences |
| KT-0404 | 🟡 | WebView.addJavascriptInterface（Long版） | `webView.addJavascriptLongerface(obj,\"android\")` | @JavascriptLongerface仅暴露必要方法 |
| KT-0405 | 🟡 | WebView.addJavascriptInterface（Double版） | `webView.addJavascriptDoubleerface(obj,\"android\")` | @JavascriptDoubleerface仅暴露必要方法 |
| KT-0406 | 🟡 | WebView.addJavascriptInterface（Float版） | `webView.addJavascriptFloaterface(obj,\"android\")` | @JavascriptFloaterface仅暴露必要方法 |
| KT-0478 | 🔴 | WebView允许文件访问 | `webView.settings.allowFileAccess=true` | 禁用 |
| KT-0479 | 🟡 | debug模式泄露 | `if(BuildConfig.DEBUG){logFullDump()}` | 移除或用if-release检查 |
| KT-2794 | 🔴 | SECURITY深度变异String#0 | `val x:String=...;val API_KEY=\"sk-abc123\"` | 环境变量或BuildConfig |
| KT-2795 | 🔴 | SECURITY深度变异Byte#1 | `val x:Byte=...;db.execSQL(\"SELECT * FROM u WHERE n='\$name'` | 参数化查询 |
| KT-2796 | 🟡 | SECURITY深度变异Boolean?#2 | `val x:Boolean?=...;Log.d(\"TAG\",\"token=\$token\")` | 脱敏 |
| KT-2797 | 🟡 | SECURITY深度变异Sequence<Long>#3 | `prefs.edit().putSequence<Long>(\"pwd\",password).apply()` | EncryptedSharedPreferences |
| KT-2798 | 🟡 | SECURITY深度变异Char#4 | `val x:Char=...;trustAllCerts()` | 证书固定 |
| KT-2799 | ⚪ | SECURITY深度变异Double?#5 | `val x:Double?=...;webView.settings.javaScriptEnabled=true` | 禁用或白名单 |
| KT-2800 | 🔴 | SECURITY深度变异Array<Boolean>#6 | `val x:Array<Boolean>=...;intent.putExtra(\"token\",token)` | 加密或避免 |
| KT-2801 | 🟡 | SECURITY深度变异Float#7 | `webView.addJavascriptFloaterface(obj,\"android\")` | @JavascriptInterface仅暴露必要方法 |
| KT-2802 | 🟡 | SECURITY深度变异Long?#8 | `val x:Long?=...;FileProvider.getUriForFile(ctx,path)` | 限制根目录 |
| KT-2803 | ⚪ | SECURITY深度变异MutableList<Double>#9 | `val x:MutableList<Double>=...;e.printStackTrace()` | Log.e(TAG,\"msg\",e) |
| KT-2804 | 🔴 | SECURITY深度变异Boolean#10 | `val x:Boolean=...;if(BuildConfig.DEBUG)Log.d('PAY',cardNumbe` | release不输出或脱敏 |
| KT-2805 | 🟡 | SECURITY深度变异Int?#11 | `val x:Int?=...;BuildConfig.API_KEY从local.properties读但.gitign` | ci环境变量+不提交 |
| KT-2806 | 🟡 | SECURITY深度变异Map<String,Int>#12 | `prefs.edit().putMap<String,Int>(\\\"pwd\\\",password).apply(` | EncryptedSharedPreferences |
| KT-2807 | 🟡 | SECURITY深度变异Double#13 | `val x:Double=...;prefs.edit().putLong(\\\"pwd\\\",password).` | EncryptedSharedPreferences |
| KT-2808 | 🟡 | SECURITY深度变异String?#14 | `val x:String?=...;prefs.edit().putDouble(\\\"pwd\\\",passwor` | EncryptedSharedPreferences |
| KT-2809 | 🟡 | SECURITY深度变异Set<Int>#15 | `val x:Set<Int>=...;prefs.edit().putBoolean(\\\"pwd\\\",passw` | EncryptedSharedPreferences |
| KT-2810 | 🟡 | SECURITY深度变异Long#16 | `val x:Long=...;webView.addJavascriptLongerface(obj,\\\"andro` | @JavascriptLongerface仅暴露必要方法 |
| KT-2811 | 🟡 | SECURITY深度变异Any#17 | `val x:Any=...;webView.addJavascriptDoubleerface(obj,\\\"andr` | @JavascriptDoubleerface仅暴露必要方法 |
| KT-2812 | 🟡 | SECURITY深度变异List<String>#18 | `val x:List<String>=...;webView.addJavascriptFloaterface(obj,` | @JavascriptFloaterface仅暴露必要方法 |
| KT-2813 | 🔴 | SECURITY深度变异Int#19 | `val x:Int=...;webView.settings.allowFileAccess=true` | 禁用 |
| KT-2814 | 🟡 | SECURITY深度变异Short#20 | `val x:Short=...;if(BuildConfig.DEBUG){logFullDump()}` | 移除或用if-release检查 |
| KT-2815 | 🔴 | SECURITY深度变异Any?#21 | `val x:Any?=...;val API_KEY=\"sk-abc123\"` | 环境变量或BuildConfig |
| KT-2816 | 🔴 | SECURITY深度变异String#22 | `val x:String=...;db.execSQL(\"SELECT * FROM u WHERE n='\$nam` | 参数化查询 |
| KT-2817 | 🟡 | SECURITY深度变异Byte#23 | `val x:Byte=...;Log.d(\"TAG\",\"token=\$token\")` | 脱敏 |
| KT-2818 | 🟡 | SECURITY深度变异Boolean?#24 | `prefs.edit().putBoolean?(\"pwd\",password).apply()` | EncryptedSharedPreferences |
| KT-2819 | 🟡 | SECURITY深度变异Sequence<Long>#25 | `val x:Sequence<Long>=...;trustAllCerts()` | 证书固定 |
| KT-2820 | ⚪ | SECURITY深度变异Char#26 | `val x:Char=...;webView.settings.javaScriptEnabled=true` | 禁用或白名单 |
| KT-2821 | 🔴 | SECURITY深度变异Double?#27 | `val x:Double?=...;intent.putExtra(\"token\",token)` | 加密或避免 |
| KT-2822 | 🟡 | SECURITY深度变异Array<Boolean>#28 | `webView.addJavascriptArray<Boolean>erface(obj,\"android\")` | @JavascriptInterface仅暴露必要方法 |
| KT-2823 | 🟡 | SECURITY深度变异Float#29 | `val x:Float=...;FileProvider.getUriForFile(ctx,path)` | 限制根目录 |
| KT-2824 | ⚪ | SECURITY深度变异Long?#30 | `val x:Long?=...;e.printStackTrace()` | Log.e(TAG,\"msg\",e) |
| KT-2825 | 🔴 | SECURITY深度变异MutableList<Double>#31 | `val x:MutableList<Double>=...;if(BuildConfig.DEBUG)Log.d('PA` | release不输出或脱敏 |
| KT-2826 | 🟡 | SECURITY深度变异Boolean#32 | `val x:Boolean=...;BuildConfig.API_KEY从local.properties读但.git` | ci环境变量+不提交 |
| KT-2827 | 🟡 | SECURITY深度变异Int?#33 | `prefs.edit().putInt?(\\\"pwd\\\",password).apply()` | EncryptedSharedPreferences |
| KT-2828 | 🟡 | SECURITY深度变异Map<String,Int>#34 | `val x:Map<String,Int>=...;prefs.edit().putLong(\\\"pwd\\\",p` | EncryptedSharedPreferences |
| KT-2829 | 🟡 | SECURITY深度变异Double#35 | `val x:Double=...;prefs.edit().putDouble(\\\"pwd\\\",password` | EncryptedSharedPreferences |
| KT-2830 | 🟡 | SECURITY深度变异String?#36 | `val x:String?=...;prefs.edit().putBoolean(\\\"pwd\\\",passwo` | EncryptedSharedPreferences |
| KT-2831 | 🟡 | SECURITY深度变异Set<Int>#37 | `val x:Set<Int>=...;webView.addJavascriptLongerface(obj,\\\"a` | @JavascriptLongerface仅暴露必要方法 |
| KT-2832 | 🟡 | SECURITY深度变异Long#38 | `val x:Long=...;webView.addJavascriptDoubleerface(obj,\\\"and` | @JavascriptDoubleerface仅暴露必要方法 |
| KT-2833 | 🟡 | SECURITY深度变异Any#39 | `val x:Any=...;webView.addJavascriptFloaterface(obj,\\\"andro` | @JavascriptFloaterface仅暴露必要方法 |
| KT-2834 | 🔴 | SECURITY深度变异List<String>#40 | `val x:List<String>=...;webView.settings.allowFileAccess=true` | 禁用 |
| KT-2835 | 🟡 | SECURITY深度变异Int#41 | `val x:Int=...;if(BuildConfig.DEBUG){logFullDump()}` | 移除或用if-release检查 |
| KT-2836 | 🔴 | SECURITY深度变异Short#42 | `val x:Short=...;val API_KEY=\"sk-abc123\"` | 环境变量或BuildConfig |
| KT-2837 | 🔴 | SECURITY深度变异Any?#43 | `val x:Any?=...;db.execSQL(\"SELECT * FROM u WHERE n='\$name'` | 参数化查询 |
| KT-2838 | 🟡 | SECURITY深度变异String#44 | `val x:String=...;Log.d(\"TAG\",\"token=\$token\")` | 脱敏 |
| KT-2839 | 🟡 | SECURITY深度变异Byte#45 | `prefs.edit().putByte(\"pwd\",password).apply()` | EncryptedSharedPreferences |
| KT-2840 | 🟡 | SECURITY深度变异Boolean?#46 | `val x:Boolean?=...;trustAllCerts()` | 证书固定 |
| KT-2841 | ⚪ | SECURITY深度变异Sequence<Long>#47 | `val x:Sequence<Long>=...;webView.settings.javaScriptEnabled=` | 禁用或白名单 |
| KT-2842 | 🔴 | SECURITY深度变异Char#48 | `val x:Char=...;intent.putExtra(\"token\",token)` | 加密或避免 |
| KT-2843 | 🟡 | SECURITY深度变异Double?#49 | `webView.addJavascriptDouble?erface(obj,\"android\")` | @JavascriptInterface仅暴露必要方法 |
| KT-2844 | 🟡 | SECURITY深度变异Array<Boolean>#50 | `val x:Array<Boolean>=...;FileProvider.getUriForFile(ctx,path` | 限制根目录 |
| KT-2845 | ⚪ | SECURITY深度变异Float#51 | `val x:Float=...;e.printStackTrace()` | Log.e(TAG,\"msg\",e) |
| KT-2846 | 🔴 | SECURITY深度变异Long?#52 | `val x:Long?=...;if(BuildConfig.DEBUG)Log.d('PAY',cardNumber)` | release不输出或脱敏 |
| KT-2847 | 🟡 | SECURITY深度变异MutableList<Double>#53 | `val x:MutableList<Double>=...;BuildConfig.API_KEY从local.prop` | ci环境变量+不提交 |
| KT-2848 | 🟡 | SECURITY深度变异Boolean#54 | `prefs.edit().putBoolean(\\\"pwd\\\",password).apply()` | EncryptedSharedPreferences |
| KT-2849 | 🟡 | SECURITY深度变异Int?#55 | `val x:Int?=...;prefs.edit().putLong(\\\"pwd\\\",password).ap` | EncryptedSharedPreferences |
| KT-2850 | 🟡 | SECURITY深度变异Map<String,Int>#56 | `val x:Map<String,Int>=...;prefs.edit().putDouble(\\\"pwd\\\"` | EncryptedSharedPreferences |
| KT-2851 | 🟡 | SECURITY深度变异Double#57 | `val x:Double=...;prefs.edit().putBoolean(\\\"pwd\\\",passwor` | EncryptedSharedPreferences |
| KT-2852 | 🟡 | SECURITY深度变异String?#58 | `val x:String?=...;webView.addJavascriptLongerface(obj,\\\"an` | @JavascriptLongerface仅暴露必要方法 |
| KT-2853 | 🟡 | SECURITY深度变异Set<Int>#59 | `val x:Set<Int>=...;webView.addJavascriptDoubleerface(obj,\\\` | @JavascriptDoubleerface仅暴露必要方法 |
| KT-2854 | 🟡 | SECURITY深度变异Long#60 | `val x:Long=...;webView.addJavascriptFloaterface(obj,\\\"andr` | @JavascriptFloaterface仅暴露必要方法 |
| KT-2855 | 🔴 | SECURITY深度变异Any#61 | `val x:Any=...;webView.settings.allowFileAccess=true` | 禁用 |
| KT-2856 | 🟡 | SECURITY深度变异List<String>#62 | `val x:List<String>=...;if(BuildConfig.DEBUG){logFullDump()}` | 移除或用if-release检查 |
| KT-2857 | 🔴 | SECURITY深度变异Int#63 | `val x:Int=...;val API_KEY=\"sk-abc123\"` | 环境变量或BuildConfig |
| KT-2858 | 🔴 | SECURITY深度变异Short#64 | `val x:Short=...;db.execSQL(\"SELECT * FROM u WHERE n='\$name` | 参数化查询 |
| KT-2859 | 🟡 | SECURITY深度变异Any?#65 | `val x:Any?=...;Log.d(\"TAG\",\"token=\$token\")` | 脱敏 |
| KT-2860 | 🟡 | SECURITY深度变异String#66 | `val x:String=...;prefs.edit().putString(\"pwd\",password).ap` | EncryptedSharedPreferences |
| KT-2861 | 🟡 | SECURITY深度变异Byte#67 | `val x:Byte=...;trustAllCerts()` | 证书固定 |
| KT-2862 | ⚪ | SECURITY深度变异Boolean?#68 | `val x:Boolean?=...;webView.settings.javaScriptEnabled=true` | 禁用或白名单 |
| KT-2863 | 🔴 | SECURITY深度变异Sequence<Long>#69 | `val x:Sequence<Long>=...;intent.putExtra(\"token\",token)` | 加密或避免 |
| KT-2864 | 🟡 | SECURITY深度变异Char#70 | `webView.addJavascriptCharerface(obj,\"android\")` | @JavascriptInterface仅暴露必要方法 |
| KT-2865 | 🟡 | SECURITY深度变异Double?#71 | `val x:Double?=...;FileProvider.getUriForFile(ctx,path)` | 限制根目录 |
| KT-2866 | ⚪ | SECURITY深度变异Array<Boolean>#72 | `val x:Array<Boolean>=...;e.printStackTrace()` | Log.e(TAG,\"msg\",e) |
| KT-2867 | 🔴 | SECURITY深度变异Float#73 | `val x:Float=...;if(BuildConfig.DEBUG)Log.d('PAY',cardNumber)` | release不输出或脱敏 |
| KT-2868 | 🟡 | SECURITY深度变异Long?#74 | `val x:Long?=...;BuildConfig.API_KEY从local.properties读但.gitig` | ci环境变量+不提交 |
| KT-2869 | 🟡 | SECURITY深度变异MutableList<Double>#75 | `prefs.edit().putMutableMutableList<Double><Double>(\\\"pwd\\` | EncryptedSharedPreferences |
| KT-2870 | 🟡 | SECURITY深度变异Boolean#76 | `val x:Boolean=...;prefs.edit().putLong(\\\"pwd\\\",password)` | EncryptedSharedPreferences |
| KT-2871 | 🟡 | SECURITY深度变异Int?#77 | `val x:Int?=...;prefs.edit().putDouble(\\\"pwd\\\",password).` | EncryptedSharedPreferences |
| KT-2872 | 🟡 | SECURITY深度变异Map<String,Int>#78 | `val x:Map<String,Int>=...;prefs.edit().putBoolean(\\\"pwd\\\` | EncryptedSharedPreferences |

## SMART_CAST（150条）

| ID | 严重度 | Bug描述 | 触发模式 | 修复建议 |
|---|------|------|------|------|
| KT-0089 | 🔴 | var智能转换失效 | `if(x is String){x.length}` | val y=x |
| KT-0090 | 🔴 | when穷举缺失 | `when(sealed){is A->...}` | 加else分支 |
| KT-0091 | 🟡 | 智能转换在闭包内失效 | `var x:Any?;launch{if(x!=null){x.method()}}` | 局部val快照 |
| KT-0092 | 🟡 | as不安全转换 | `val x:Any;val y=x as String` | as?+?:错误处理 |
| KT-0093 | 🟡 | is检查后类型窄化丢失 | `if(x is List<*>){x[0]}` | reified |
| KT-0094 | ⚪ | 不必要的as | `val x:String="hi";val y=x as String` | 直接使用 |
| KT-0208 | 🔴 | 内部类属性智能转换失效 | `class Outer{var x:Any?;inner class Inner{fun f(){if(x is Str` | 局部val快照 |
| KT-0209 | 🟡 | !!后is检查多余 | `x!!;if(x is String){x.length}` | 直接用is检查 |
| KT-0210 | 🟡 | 类型窄化+?:丢失 | `val x=y as? String?:return;x.length` | 直接使用不用?. |
| KT-0211 | ⚪ | when完整但编译器仍要else | `when(e){A->1;B->2;C->3}` | 加else抛异常 |
| KT-0212 | 🟡 | 可空Boolean智能转换 | `if(b==true){b.not()}` | b?.let或?:反 |
| KT-0305 | 🟡 | is检查+var+lambda=智能转换三次失效 | `var x:Any?="hi";if(x is String){launch{x.length}}` | 局部val快照+显式cast |
| KT-0392 | 🟡 | !!后is检查多余（Int版） | `x!!;if(x is Int){x.length}` | 直接用is检查 |
| KT-0393 | 🟡 | !!后is检查多余（Long版） | `x!!;if(x is Long){x.length}` | 直接用is检查 |
| KT-0394 | 🟡 | !!后is检查多余（Double版） | `x!!;if(x is Double){x.length}` | 直接用is检查 |
| KT-0395 | 🟡 | !!后is检查多余（Boolean版） | `x!!;if(x is Boolean){x.length}` | 直接用is检查 |
| KT-0433 | 🔴 | 内部类属性智能转换失效（Int版） | `class Outer{var x:Any?;inner class Inner{fun f(){if(x is Int` | 局部val快照 |
| KT-0434 | 🔴 | 内部类属性智能转换失效（Long版） | `class Outer{var x:Any?;inner class Inner{fun f(){if(x is Lon` | 局部val快照 |
| KT-0435 | 🔴 | 内部类属性智能转换失效（Double版） | `class Outer{var x:Any?;inner class Inner{fun f(){if(x is Dou` | 局部val快照 |
| KT-0436 | 🔴 | 内部类属性智能转换失效（Boolean版） | `class Outer{var x:Any?;inner class Inner{fun f(){if(x is Boo` | 局部val快照 |
| KT-0437 | 🟡 | as不安全转换（Int版） | `val x:Any;val y=x as Int` | as?+?:错误处理 |
| KT-0438 | 🟡 | as不安全转换（Long版） | `val x:Any;val y=x as Long` | as?+?:错误处理 |
| KT-0439 | 🟡 | as不安全转换（Double版） | `val x:Any;val y=x as Double` | as?+?:错误处理 |
| KT-0440 | 🟡 | as不安全转换（Boolean版） | `val x:Any;val y=x as Boolean` | as?+?:错误处理 |
| KT-0441 | ⚪ | 不必要的as（Int版） | `val x:Int=\"hi\";val y=x as Int` | 直接使用 |
| KT-0442 | ⚪ | 不必要的as（Long版） | `val x:Long=\"hi\";val y=x as Long` | 直接使用 |
| KT-0443 | ⚪ | 不必要的as（Double版） | `val x:Double=\"hi\";val y=x as Double` | 直接使用 |
| KT-0444 | ⚪ | 不必要的as（Boolean版） | `val x:Boolean=\"hi\";val y=x as Boolean` | 直接使用 |
| KT-0450 | 🟡 | 类型窄化+?:丢失（Int版） | `val x=y as? Int?:return;x.length` | 直接使用不用?. |
| KT-0451 | 🟡 | 类型窄化+?:丢失（Long版） | `val x=y as? Long?:return;x.length` | 直接使用不用?. |
| KT-0452 | 🟡 | 类型窄化+?:丢失（Double版） | `val x=y as? Double?:return;x.length` | 直接使用不用?. |
| KT-0453 | 🟡 | 类型窄化+?:丢失（Boolean版） | `val x=y as? Boolean?:return;x.length` | 直接使用不用?. |
| KT-0460 | 🟡 | is检查+var+lambda=智能转换三次失效（Int版） | `var x:Any?=\"hi\";if(x is Int){launch{x.length}}` | 局部val快照+显式cast |
| KT-0461 | 🟡 | is检查+var+lambda=智能转换三次失效（Long版） | `var x:Any?=\"hi\";if(x is Long){launch{x.length}}` | 局部val快照+显式cast |
| KT-0462 | 🟡 | is检查+var+lambda=智能转换三次失效（Double版） | `var x:Any?=\"hi\";if(x is Double){launch{x.length}}` | 局部val快照+显式cast |
| KT-0463 | 🟡 | is检查+var+lambda=智能转换三次失效（Boolean版） | `var x:Any?=\"hi\";if(x is Boolean){launch{x.length}}` | 局部val快照+显式cast |
| KT-0487 | 🟡 | when分支智能转换不稳定 | `when(x){is Int->x+1 is Long->x+1L}` | 显式as+else |
| KT-1909 | 🔴 | SMART_CAST深度变异String#0 | `val x:String=...;if(x is String){x.length}` | val y=x |
| KT-1910 | 🔴 | SMART_CAST深度变异Byte#1 | `val x:Byte=...;when(sealed){is A->...}` | 加else分支 |
| KT-1911 | 🟡 | SMART_CAST深度变异Boolean?#2 | `val x:Boolean?=...;var x:Any?;launch{if(x!=null){x.method()}` | 局部val快照 |
| KT-1912 | 🟡 | SMART_CAST深度变异Sequence<Long>#3 | `val x:Any;val y=x as Sequence<Long>` | as?+?:错误处理 |
| KT-1913 | 🟡 | SMART_CAST深度变异Char#4 | `if(x is Char<*>){x[0]}` | reified |
| KT-1914 | ⚪ | SMART_CAST深度变异Double?#5 | `val x:Double?=\"hi\";val y=x as Double?` | 直接使用 |
| KT-1915 | 🔴 | SMART_CAST深度变异Array<Boolean>#6 | `class Outer{var x:Any?;inner class Inner{fun f(){if(x is Arr` | 局部val快照 |
| KT-1916 | 🟡 | SMART_CAST深度变异Float#7 | `x!!;if(x is Float){x.length}` | 直接用is检查 |
| KT-1917 | 🟡 | SMART_CAST深度变异Long?#8 | `val x=y as? Long??:return;x.length` | 直接使用不用?. |
| KT-1918 | ⚪ | SMART_CAST深度变异MutableList<Double>#9 | `val x:MutableList<Double>=...;when(e){A->1;B->2;C->3}` | 加else抛异常 |
| KT-1919 | 🟡 | SMART_CAST深度变异Boolean#10 | `val x:Boolean=...;if(b==true){b.not()}` | b?.let或?:反 |
| KT-1920 | 🟡 | SMART_CAST深度变异Int?#11 | `var x:Any?=\"hi\";if(x is Int??){launch{x.length}}` | 局部val快照+显式cast |
| KT-1921 | 🟡 | SMART_CAST深度变异Map<String,Int>#12 | `x!!;if(x is Map<String,Int>){x.length}` | 直接用is检查 |
| KT-1922 | 🟡 | SMART_CAST深度变异Double#13 | `val x:Double=...;x!!;if(x is Long){x.length}` | 直接用is检查 |
| KT-1923 | 🟡 | SMART_CAST深度变异String?#14 | `val x:String?=...;x!!;if(x is Double){x.length}` | 直接用is检查 |
| KT-1924 | 🟡 | SMART_CAST深度变异Set<Int>#15 | `val x:Set<Int>=...;x!!;if(x is Boolean){x.length}` | 直接用is检查 |
| KT-1925 | 🔴 | SMART_CAST深度变异Long#16 | `class Outer{var x:Any?;inner class Inner{fun f(){if(x is Lon` | 局部val快照 |
| KT-1926 | 🔴 | SMART_CAST深度变异Any#17 | `val x:Any=...;class Outer{var x:Any?;inner class Inner{fun f` | 局部val快照 |
| KT-1927 | 🔴 | SMART_CAST深度变异List<String>#18 | `val x:List<String>=...;class Outer{var x:Any?;inner class In` | 局部val快照 |
| KT-1928 | 🔴 | SMART_CAST深度变异Int#19 | `val x:Int=...;class Outer{var x:Any?;inner class Inner{fun f` | 局部val快照 |
| KT-1929 | 🟡 | SMART_CAST深度变异Short#20 | `val x:Any;val y=x as Short` | as?+?:错误处理 |
| KT-1930 | 🟡 | SMART_CAST深度变异Any?#21 | `val x:Any?=...;val x:Any;val y=x as Long` | as?+?:错误处理 |
| KT-1931 | 🟡 | SMART_CAST深度变异String#22 | `val x:String=...;val x:Any;val y=x as Double` | as?+?:错误处理 |
| KT-1932 | 🟡 | SMART_CAST深度变异Byte#23 | `val x:Byte=...;val x:Any;val y=x as Boolean` | as?+?:错误处理 |
| KT-1933 | ⚪ | SMART_CAST深度变异Boolean?#24 | `val x:Boolean?=\\\"hi\\\";val y=x as Boolean?` | 直接使用 |
| KT-1934 | ⚪ | SMART_CAST深度变异Sequence<Long>#25 | `val x:Sequence<Long>=...;val x:Long=\\\"hi\\\";val y=x as Lo` | 直接使用 |
| KT-1935 | ⚪ | SMART_CAST深度变异Char#26 | `val x:Char=...;val x:Double=\\\"hi\\\";val y=x as Double` | 直接使用 |
| KT-1936 | ⚪ | SMART_CAST深度变异Double?#27 | `val x:Double?=...;val x:Boolean=\\\"hi\\\";val y=x as Boolea` | 直接使用 |
| KT-1937 | 🟡 | SMART_CAST深度变异Array<Boolean>#28 | `val x=y as? Array<Boolean>?:return;x.length` | 直接使用不用?. |
| KT-1938 | 🟡 | SMART_CAST深度变异Float#29 | `val x:Float=...;val x=y as? Long?:return;x.length` | 直接使用不用?. |
| KT-1939 | 🟡 | SMART_CAST深度变异Long?#30 | `val x:Long?=...;val x=y as? Double?:return;x.length` | 直接使用不用?. |
| KT-1940 | 🟡 | SMART_CAST深度变异MutableList<Double>#31 | `val x:MutableList<Double>=...;val x=y as? Boolean?:return;x.` | 直接使用不用?. |
| KT-1941 | 🟡 | SMART_CAST深度变异Boolean#32 | `var x:Any?=\\\"hi\\\";if(x is Boolean){launch{x.length}}` | 局部val快照+显式cast |
| KT-1942 | 🟡 | SMART_CAST深度变异Int?#33 | `val x:Int?=...;var x:Any?=\\\"hi\\\";if(x is Long){launch{x.` | 局部val快照+显式cast |
| KT-1943 | 🟡 | SMART_CAST深度变异Map<String,Int>#34 | `val x:Map<String,Int>=...;var x:Any?=\\\"hi\\\";if(x is Doub` | 局部val快照+显式cast |
| KT-1944 | 🟡 | SMART_CAST深度变异Double#35 | `val x:Double=...;var x:Any?=\\\"hi\\\";if(x is Boolean){laun` | 局部val快照+显式cast |
| KT-1945 | 🟡 | SMART_CAST深度变异String?#36 | `when(x){is String?->x+1 is Long->x+1L}` | 显式as+else |
| KT-1946 | 🔴 | SMART_CAST深度变异Set<Int>#37 | `if(x is Set<Set<Int>>){x.length}` | val y=x |
| KT-1947 | 🔴 | SMART_CAST深度变异Long#38 | `val x:Long=...;when(sealed){is A->...}` | 加else分支 |
| KT-1948 | 🟡 | SMART_CAST深度变异Any#39 | `val x:Any=...;var x:Any?;launch{if(x!=null){x.method()}}` | 局部val快照 |
| KT-1949 | 🟡 | SMART_CAST深度变异List<String>#40 | `val x:Any;val y=x as List<String><String>` | as?+?:错误处理 |
| KT-1950 | 🟡 | SMART_CAST深度变异Int#41 | `if(x is Int<*>){x[0]}` | reified |
| KT-1951 | ⚪ | SMART_CAST深度变异Short#42 | `val x:Short=\"hi\";val y=x as Short` | 直接使用 |
| KT-1952 | 🔴 | SMART_CAST深度变异Any?#43 | `class Outer{var x:Any?;inner class Inner{fun f(){if(x is Any` | 局部val快照 |
| KT-1953 | 🟡 | SMART_CAST深度变异String#44 | `val x:String=...;x!!;if(x is String){x.length}` | 直接用is检查 |
| KT-1954 | 🟡 | SMART_CAST深度变异Byte#45 | `val x=y as? Byte?:return;x.length` | 直接使用不用?. |
| KT-1955 | ⚪ | SMART_CAST深度变异Boolean?#46 | `val x:Boolean?=...;when(e){A->1;B->2;C->3}` | 加else抛异常 |
| KT-1956 | 🟡 | SMART_CAST深度变异Sequence<Long>#47 | `val x:Sequence<Long>=...;if(b==true){b.not()}` | b?.let或?:反 |
| KT-1957 | 🟡 | SMART_CAST深度变异Char#48 | `var x:Any?=\"hi\";if(x is Char){launch{x.length}}` | 局部val快照+显式cast |
| KT-1958 | 🟡 | SMART_CAST深度变异Double?#49 | `x!!;if(x is Double?){x.length}` | 直接用is检查 |
| KT-1959 | 🟡 | SMART_CAST深度变异Array<Boolean>#50 | `val x:Array<Boolean>=...;x!!;if(x is Long){x.length}` | 直接用is检查 |
| KT-1960 | 🟡 | SMART_CAST深度变异Float#51 | `val x:Float=...;x!!;if(x is Double){x.length}` | 直接用is检查 |
| KT-1961 | 🟡 | SMART_CAST深度变异Long?#52 | `val x:Long?=...;x!!;if(x is Boolean){x.length}` | 直接用is检查 |
| KT-1962 | 🔴 | SMART_CAST深度变异MutableList<Double>#53 | `class Outer{var x:Any?;inner class Inner{fun f(){if(x is Mut` | 局部val快照 |
| KT-1963 | 🔴 | SMART_CAST深度变异Boolean#54 | `val x:Boolean=...;class Outer{var x:Any?;inner class Inner{f` | 局部val快照 |
| KT-1964 | 🔴 | SMART_CAST深度变异Int?#55 | `val x:Int?=...;class Outer{var x:Any?;inner class Inner{fun ` | 局部val快照 |
| KT-1965 | 🔴 | SMART_CAST深度变异Map<String,Int>#56 | `val x:Map<String,Int>=...;class Outer{var x:Any?;inner class` | 局部val快照 |
| KT-1966 | 🟡 | SMART_CAST深度变异Double#57 | `val x:Any;val y=x as Double` | as?+?:错误处理 |
| KT-1967 | 🟡 | SMART_CAST深度变异String?#58 | `val x:String?=...;val x:Any;val y=x as Long` | as?+?:错误处理 |
| KT-1968 | 🟡 | SMART_CAST深度变异Set<Int>#59 | `val x:Set<Int>=...;val x:Any;val y=x as Double` | as?+?:错误处理 |
| KT-1969 | 🟡 | SMART_CAST深度变异Long#60 | `val x:Long=...;val x:Any;val y=x as Boolean` | as?+?:错误处理 |
| KT-1970 | ⚪ | SMART_CAST深度变异Any#61 | `val x:Any=\\\"hi\\\";val y=x as Any` | 直接使用 |
| KT-1971 | ⚪ | SMART_CAST深度变异List<String>#62 | `val x:List<String>=...;val x:Long=\\\"hi\\\";val y=x as Long` | 直接使用 |
| KT-1972 | ⚪ | SMART_CAST深度变异Int#63 | `val x:Int=...;val x:Double=\\\"hi\\\";val y=x as Double` | 直接使用 |
| KT-1973 | ⚪ | SMART_CAST深度变异Short#64 | `val x:Short=...;val x:Boolean=\\\"hi\\\";val y=x as Boolean` | 直接使用 |
| KT-1974 | 🟡 | SMART_CAST深度变异Any?#65 | `val x=y as? Any??:return;x.length` | 直接使用不用?. |
| KT-1975 | 🟡 | SMART_CAST深度变异String#66 | `val x:String=...;val x=y as? Long?:return;x.length` | 直接使用不用?. |
| KT-1976 | 🟡 | SMART_CAST深度变异Byte#67 | `val x:Byte=...;val x=y as? Double?:return;x.length` | 直接使用不用?. |
| KT-1977 | 🟡 | SMART_CAST深度变异Boolean?#68 | `val x:Boolean?=...;val x=y as? Boolean?:return;x.length` | 直接使用不用?. |
| KT-1978 | 🟡 | SMART_CAST深度变异Sequence<Long>#69 | `var x:Any?=\\\"hi\\\";if(x is Sequence<Long>){launch{x.lengt` | 局部val快照+显式cast |
| KT-1979 | 🟡 | SMART_CAST深度变异Char#70 | `val x:Char=...;var x:Any?=\\\"hi\\\";if(x is Long){launch{x.` | 局部val快照+显式cast |
| KT-1980 | 🟡 | SMART_CAST深度变异Double?#71 | `val x:Double?=...;var x:Any?=\\\"hi\\\";if(x is Double){laun` | 局部val快照+显式cast |
| KT-1981 | 🟡 | SMART_CAST深度变异Array<Boolean>#72 | `val x:Array<Boolean>=...;var x:Any?=\\\"hi\\\";if(x is Boole` | 局部val快照+显式cast |
| KT-1982 | 🟡 | SMART_CAST深度变异Float#73 | `when(x){is Float->x+1 is Long->x+1L}` | 显式as+else |
| KT-1983 | 🔴 | SMART_CAST深度变异Long?#74 | `if(x is Long?){x.length}` | val y=x |
| KT-1984 | 🔴 | SMART_CAST深度变异MutableList<Double>#75 | `val x:MutableList<Double>=...;when(sealed){is A->...}` | 加else分支 |
| KT-1985 | 🟡 | SMART_CAST深度变异Boolean#76 | `val x:Boolean=...;var x:Any?;launch{if(x!=null){x.method()}}` | 局部val快照 |
| KT-1986 | 🟡 | SMART_CAST深度变异Int?#77 | `val x:Any;val y=x as Int??` | as?+?:错误处理 |
| KT-1987 | 🟡 | SMART_CAST深度变异Map<String,Int>#78 | `if(x is Map<String,Int><*>){x[0]}` | reified |
| KT-1988 | ⚪ | SMART_CAST深度变异Double#79 | `val x:Double=\"hi\";val y=x as Double` | 直接使用 |
| KT-1989 | 🔴 | SMART_CAST深度变异String?#80 | `class Outer{var x:Any?;inner class Inner{fun f(){if(x is Str` | 局部val快照 |
| KT-1990 | 🟡 | SMART_CAST深度变异Set<Int>#81 | `x!!;if(x is Set<Set<Int>>){x.length}` | 直接用is检查 |
| KT-1991 | 🟡 | SMART_CAST深度变异Long#82 | `val x=y as? Long?:return;x.length` | 直接使用不用?. |
| KT-1992 | ⚪ | SMART_CAST深度变异Any#83 | `val x:Any=...;when(e){A->1;B->2;C->3}` | 加else抛异常 |
| KT-1993 | 🟡 | SMART_CAST深度变异List<String>#84 | `val x:List<String>=...;if(b==true){b.not()}` | b?.let或?:反 |
| KT-1994 | 🟡 | SMART_CAST深度变异Int#85 | `var x:Any?=\"hi\";if(x is Int){launch{x.length}}` | 局部val快照+显式cast |
| KT-1995 | 🟡 | SMART_CAST深度变异Short#86 | `x!!;if(x is Short){x.length}` | 直接用is检查 |
| KT-1996 | 🟡 | SMART_CAST深度变异Any?#87 | `val x:Any?=...;x!!;if(x is Long){x.length}` | 直接用is检查 |
| KT-1997 | 🟡 | SMART_CAST深度变异String#88 | `val x:String=...;x!!;if(x is Double){x.length}` | 直接用is检查 |
| KT-1998 | 🟡 | SMART_CAST深度变异Byte#89 | `val x:Byte=...;x!!;if(x is Boolean){x.length}` | 直接用is检查 |
| KT-1999 | 🔴 | SMART_CAST深度变异Boolean?#90 | `class Outer{var x:Any?;inner class Inner{fun f(){if(x is Boo` | 局部val快照 |
| KT-2000 | 🔴 | SMART_CAST深度变异Sequence<Long>#91 | `val x:Sequence<Long>=...;class Outer{var x:Any?;inner class ` | 局部val快照 |
| KT-2001 | 🔴 | SMART_CAST深度变异Char#92 | `val x:Char=...;class Outer{var x:Any?;inner class Inner{fun ` | 局部val快照 |
| KT-2002 | 🔴 | SMART_CAST深度变异Double?#93 | `val x:Double?=...;class Outer{var x:Any?;inner class Inner{f` | 局部val快照 |
| KT-2003 | 🟡 | SMART_CAST深度变异Array<Boolean>#94 | `val x:Any;val y=x as Array<Boolean>` | as?+?:错误处理 |
| KT-2004 | 🟡 | SMART_CAST深度变异Float#95 | `val x:Float=...;val x:Any;val y=x as Long` | as?+?:错误处理 |
| KT-2005 | 🟡 | SMART_CAST深度变异Long?#96 | `val x:Long?=...;val x:Any;val y=x as Double` | as?+?:错误处理 |
| KT-2006 | 🟡 | SMART_CAST深度变异MutableList<Double>#97 | `val x:MutableList<Double>=...;val x:Any;val y=x as Boolean` | as?+?:错误处理 |
| KT-2007 | ⚪ | SMART_CAST深度变异Boolean#98 | `val x:Boolean=\\\"hi\\\";val y=x as Boolean` | 直接使用 |
| KT-2008 | ⚪ | SMART_CAST深度变异Int?#99 | `val x:Int?=...;val x:Long=\\\"hi\\\";val y=x as Long` | 直接使用 |
| KT-2009 | ⚪ | SMART_CAST深度变异Map<String,Int>#100 | `val x:Map<String,Int>=...;val x:Double=\\\"hi\\\";val y=x as` | 直接使用 |
| KT-2010 | ⚪ | SMART_CAST深度变异Double#101 | `val x:Double=...;val x:Boolean=\\\"hi\\\";val y=x as Boolean` | 直接使用 |
| KT-2011 | 🟡 | SMART_CAST深度变异String?#102 | `val x=y as? String??:return;x.length` | 直接使用不用?. |
| KT-2012 | 🟡 | SMART_CAST深度变异Set<Int>#103 | `val x:Set<Int>=...;val x=y as? Long?:return;x.length` | 直接使用不用?. |
| KT-2013 | 🟡 | SMART_CAST深度变异Long#104 | `val x:Long=...;val x=y as? Double?:return;x.length` | 直接使用不用?. |
| KT-2014 | 🟡 | SMART_CAST深度变异Any#105 | `val x:Any=...;val x=y as? Boolean?:return;x.length` | 直接使用不用?. |
| KT-2015 | 🟡 | SMART_CAST深度变异List<String>#106 | `var x:Any?=\\\"hi\\\";if(x is List<String><String>){launch{x` | 局部val快照+显式cast |
| KT-2016 | 🟡 | SMART_CAST深度变异Int#107 | `val x:Int=...;var x:Any?=\\\"hi\\\";if(x is Long){launch{x.l` | 局部val快照+显式cast |
| KT-2017 | 🟡 | SMART_CAST深度变异Short#108 | `val x:Short=...;var x:Any?=\\\"hi\\\";if(x is Double){launch` | 局部val快照+显式cast |
| KT-2018 | 🟡 | SMART_CAST深度变异Any?#109 | `val x:Any?=...;var x:Any?=\\\"hi\\\";if(x is Boolean){launch` | 局部val快照+显式cast |
| KT-2019 | 🟡 | SMART_CAST深度变异String#110 | `when(x){is String->x+1 is Long->x+1L}` | 显式as+else |
| KT-2020 | 🔴 | SMART_CAST深度变异Byte#111 | `if(x is Byte){x.length}` | val y=x |
| KT-2021 | 🔴 | SMART_CAST深度变异Boolean?#112 | `val x:Boolean?=...;when(sealed){is A->...}` | 加else分支 |

## VALUE_CLASS（80条）

| ID | 严重度 | Bug描述 | 触发模式 | 修复建议 |
|---|------|------|------|------|
| KT-0095 | 🟡 | @JvmInline缺失 | `inline class Name(val s:String)` | 加@JvmInline |
| KT-0096 | 🟡 | 内联类实现接口 | `value class N(val s:String):Iface` | 避免接口 |
| KT-0097 | ⚪ | 内联类多属性 | `@JvmInline value class P(val x:Int,val y:Int)` | 拆成两个 |
| KT-0213 | 🟡 | 内联类在泛型中装箱 | `val x:List<MyValueClass>=listOf(MyValueClass(1))` | 考虑直接使用List<Int> |
| KT-0214 | 🟡 | 内联类==比较失效 | `inline class N(val i:Int);N(1)==N(1)` | 可用但注意引用比较 |
| KT-0215 | ⚪ | 内联类非主构造属性 | `inline class N(val s:String){val len=s.length}` | 内联到val本身 |
| KT-0341 | ⚪ | 内联类非主构造属性（Int版） | `inline class N(val s:Int){val len=s.length}` | 内联到val本身 |
| KT-0342 | ⚪ | 内联类非主构造属性（Long版） | `inline class N(val s:Long){val len=s.length}` | 内联到val本身 |
| KT-0343 | ⚪ | 内联类非主构造属性（Double版） | `inline class N(val s:Double){val len=s.length}` | 内联到val本身 |
| KT-0344 | ⚪ | 内联类非主构造属性（Boolean版） | `inline class N(val s:Boolean){val len=s.length}` | 内联到val本身 |
| KT-0388 | 🟡 | 内联类实现接口（Int版） | `value class N(val s:Int):Iface` | 避免接口 |
| KT-0389 | 🟡 | 内联类实现接口（Long版） | `value class N(val s:Long):Iface` | 避免接口 |
| KT-0390 | 🟡 | 内联类实现接口（Double版） | `value class N(val s:Double):Iface` | 避免接口 |
| KT-0391 | 🟡 | 内联类实现接口（Boolean版） | `value class N(val s:Boolean):Iface` | 避免接口 |
| KT-0426 | ⚪ | 内联类多属性（Long版） | `@JvmInline value class P(val x:Long,val y:Long)` | 拆成两个 |
| KT-0427 | ⚪ | 内联类多属性（Double版） | `@JvmInline value class P(val x:Double,val y:Double)` | 拆成两个 |
| KT-0428 | ⚪ | 内联类多属性（Float版） | `@JvmInline value class P(val x:Float,val y:Float)` | 拆成两个 |
| KT-0457 | 🟡 | 内联类==比较失效（Long版） | `inline class N(val i:Long);N(1)==N(1)` | 可用但注意引用比较 |
| KT-0458 | 🟡 | 内联类==比较失效（Double版） | `inline class N(val i:Double);N(1)==N(1)` | 可用但注意引用比较 |
| KT-0459 | 🟡 | 内联类==比较失效（Float版） | `inline class N(val i:Float);N(1)==N(1)` | 可用但注意引用比较 |
| KT-2022 | 🟡 | VALUE_CLASS深度变异String#0 | `val x:String=...;inline class Name(val s:String)` | 加@JvmInline |
| KT-2023 | 🟡 | VALUE_CLASS深度变异Byte#1 | `value class N(val s:Byte):Iface` | 避免接口 |
| KT-2024 | ⚪ | VALUE_CLASS深度变异Boolean?#2 | `@JvmInline value class P(val x:Boolean?,val y:Boolean?)` | 拆成两个 |
| KT-2025 | 🟡 | VALUE_CLASS深度变异Sequence<Long>#3 | `val x:Sequence<Long><MyValueClass>=listOf(MyValueClass(1))` | 考虑直接使用List<Int> |
| KT-2026 | 🟡 | VALUE_CLASS深度变异Char#4 | `inline class N(val i:Char);N(1)==N(1)` | 可用但注意引用比较 |
| KT-2027 | ⚪ | VALUE_CLASS深度变异Double?#5 | `inline class N(val s:Double?){val len=s.length}` | 内联到val本身 |
| KT-2028 | ⚪ | VALUE_CLASS深度变异Array<Boolean>#6 | `inline class N(val s:Array<Boolean>){val len=s.length}` | 内联到val本身 |
| KT-2029 | ⚪ | VALUE_CLASS深度变异Float#7 | `val x:Float=...;inline class N(val s:Long){val len=s.length}` | 内联到val本身 |
| KT-2030 | ⚪ | VALUE_CLASS深度变异Long?#8 | `val x:Long?=...;inline class N(val s:Double){val len=s.lengt` | 内联到val本身 |
| KT-2031 | ⚪ | VALUE_CLASS深度变异MutableList<Double>#9 | `val x:MutableList<Double>=...;inline class N(val s:Boolean){` | 内联到val本身 |
| KT-2032 | 🟡 | VALUE_CLASS深度变异Boolean#10 | `value class N(val s:Boolean):Iface` | 避免接口 |
| KT-2033 | 🟡 | VALUE_CLASS深度变异Int?#11 | `val x:Int?=...;value class N(val s:Long):Iface` | 避免接口 |
| KT-2034 | 🟡 | VALUE_CLASS深度变异Map<String,Int>#12 | `val x:Map<String,Int>=...;value class N(val s:Double):Iface` | 避免接口 |
| KT-2035 | 🟡 | VALUE_CLASS深度变异Double#13 | `val x:Double=...;value class N(val s:Boolean):Iface` | 避免接口 |
| KT-2036 | ⚪ | VALUE_CLASS深度变异String?#14 | `val x:String?=...;@JvmInline value class P(val x:Long,val y:` | 拆成两个 |
| KT-2037 | ⚪ | VALUE_CLASS深度变异Set<Int>#15 | `val x:Set<Int>=...;@JvmInline value class P(val x:Double,val` | 拆成两个 |
| KT-2038 | ⚪ | VALUE_CLASS深度变异Long#16 | `val x:Long=...;@JvmInline value class P(val x:Float,val y:Fl` | 拆成两个 |
| KT-2039 | 🟡 | VALUE_CLASS深度变异Any#17 | `val x:Any=...;inline class N(val i:Long);N(1)==N(1)` | 可用但注意引用比较 |
| KT-2040 | 🟡 | VALUE_CLASS深度变异List<String>#18 | `val x:List<String>=...;inline class N(val i:Double);N(1)==N(` | 可用但注意引用比较 |
| KT-2041 | 🟡 | VALUE_CLASS深度变异Int#19 | `val x:Int=...;inline class N(val i:Float);N(1)==N(1)` | 可用但注意引用比较 |
| KT-2042 | 🟡 | VALUE_CLASS深度变异Short#20 | `inline class Name(val s:Short)` | 加@JvmInline |
| KT-2043 | 🟡 | VALUE_CLASS深度变异Any?#21 | `value class N(val s:Any?):Iface` | 避免接口 |
| KT-2044 | ⚪ | VALUE_CLASS深度变异String#22 | `@JvmInline value class P(val x:String,val y:String)` | 拆成两个 |
| KT-2045 | 🟡 | VALUE_CLASS深度变异Byte#23 | `val x:Byte<MyValueClass>=listOf(MyValueClass(1))` | 考虑直接使用List<Int> |
| KT-2046 | 🟡 | VALUE_CLASS深度变异Boolean?#24 | `inline class N(val i:Boolean?);N(1)==N(1)` | 可用但注意引用比较 |
| KT-2047 | ⚪ | VALUE_CLASS深度变异Sequence<Long>#25 | `inline class N(val s:Sequence<Long>){val len=s.length}` | 内联到val本身 |
| KT-2048 | ⚪ | VALUE_CLASS深度变异Char#26 | `inline class N(val s:Char){val len=s.length}` | 内联到val本身 |
| KT-2049 | ⚪ | VALUE_CLASS深度变异Double?#27 | `val x:Double?=...;inline class N(val s:Long){val len=s.lengt` | 内联到val本身 |
| KT-2050 | ⚪ | VALUE_CLASS深度变异Array<Boolean>#28 | `val x:Array<Boolean>=...;inline class N(val s:Double){val le` | 内联到val本身 |
| KT-2051 | ⚪ | VALUE_CLASS深度变异Float#29 | `val x:Float=...;inline class N(val s:Boolean){val len=s.leng` | 内联到val本身 |
| KT-2052 | 🟡 | VALUE_CLASS深度变异Long?#30 | `value class N(val s:Long?):Iface` | 避免接口 |
| KT-2053 | 🟡 | VALUE_CLASS深度变异MutableList<Double>#31 | `val x:MutableList<Double>=...;value class N(val s:Long):Ifac` | 避免接口 |
| KT-2054 | 🟡 | VALUE_CLASS深度变异Boolean#32 | `val x:Boolean=...;value class N(val s:Double):Iface` | 避免接口 |
| KT-2055 | 🟡 | VALUE_CLASS深度变异Int?#33 | `val x:Int?=...;value class N(val s:Boolean):Iface` | 避免接口 |
| KT-2056 | ⚪ | VALUE_CLASS深度变异Map<String,Int>#34 | `val x:Map<String,Int>=...;@JvmInline value class P(val x:Lon` | 拆成两个 |
| KT-2057 | ⚪ | VALUE_CLASS深度变异Double#35 | `val x:Double=...;@JvmInline value class P(val x:Double,val y` | 拆成两个 |
| KT-2058 | ⚪ | VALUE_CLASS深度变异String?#36 | `val x:String?=...;@JvmInline value class P(val x:Float,val y` | 拆成两个 |
| KT-2059 | 🟡 | VALUE_CLASS深度变异Set<Int>#37 | `val x:Set<Int>=...;inline class N(val i:Long);N(1)==N(1)` | 可用但注意引用比较 |
| KT-2060 | 🟡 | VALUE_CLASS深度变异Long#38 | `val x:Long=...;inline class N(val i:Double);N(1)==N(1)` | 可用但注意引用比较 |
| KT-2061 | 🟡 | VALUE_CLASS深度变异Any#39 | `val x:Any=...;inline class N(val i:Float);N(1)==N(1)` | 可用但注意引用比较 |
| KT-2062 | 🟡 | VALUE_CLASS深度变异List<String>#40 | `inline class Name(val s:List<String><String>)` | 加@JvmInline |
| KT-2063 | 🟡 | VALUE_CLASS深度变异Int#41 | `value class N(val s:Int):Iface` | 避免接口 |
| KT-2064 | ⚪ | VALUE_CLASS深度变异Short#42 | `@JvmInline value class P(val x:Short,val y:Short)` | 拆成两个 |
| KT-2065 | 🟡 | VALUE_CLASS深度变异Any?#43 | `val x:Any?<MyValueClass>=listOf(MyValueClass(1))` | 考虑直接使用List<Int> |
| KT-2066 | 🟡 | VALUE_CLASS深度变异String#44 | `inline class N(val i:String);N(1)==N(1)` | 可用但注意引用比较 |
| KT-2067 | ⚪ | VALUE_CLASS深度变异Byte#45 | `inline class N(val s:Byte){val len=s.length}` | 内联到val本身 |
| KT-2068 | ⚪ | VALUE_CLASS深度变异Boolean?#46 | `inline class N(val s:Boolean?){val len=s.length}` | 内联到val本身 |
| KT-2069 | ⚪ | VALUE_CLASS深度变异Sequence<Long>#47 | `val x:Sequence<Long>=...;inline class N(val s:Long){val len=` | 内联到val本身 |
| KT-2070 | ⚪ | VALUE_CLASS深度变异Char#48 | `val x:Char=...;inline class N(val s:Double){val len=s.length` | 内联到val本身 |
| KT-2071 | ⚪ | VALUE_CLASS深度变异Double?#49 | `val x:Double?=...;inline class N(val s:Boolean){val len=s.le` | 内联到val本身 |
| KT-2072 | 🟡 | VALUE_CLASS深度变异Array<Boolean>#50 | `value class N(val s:Array<Boolean>):Iface` | 避免接口 |
| KT-2073 | 🟡 | VALUE_CLASS深度变异Float#51 | `val x:Float=...;value class N(val s:Long):Iface` | 避免接口 |
| KT-2074 | 🟡 | VALUE_CLASS深度变异Long?#52 | `val x:Long?=...;value class N(val s:Double):Iface` | 避免接口 |
| KT-2075 | 🟡 | VALUE_CLASS深度变异MutableList<Double>#53 | `val x:MutableList<Double>=...;value class N(val s:Boolean):I` | 避免接口 |
| KT-2076 | ⚪ | VALUE_CLASS深度变异Boolean#54 | `val x:Boolean=...;@JvmInline value class P(val x:Long,val y:` | 拆成两个 |
| KT-2077 | ⚪ | VALUE_CLASS深度变异Int?#55 | `val x:Int?=...;@JvmInline value class P(val x:Double,val y:D` | 拆成两个 |
| KT-2078 | ⚪ | VALUE_CLASS深度变异Map<String,Int>#56 | `val x:Map<String,Int>=...;@JvmInline value class P(val x:Flo` | 拆成两个 |
| KT-2079 | 🟡 | VALUE_CLASS深度变异Double#57 | `val x:Double=...;inline class N(val i:Long);N(1)==N(1)` | 可用但注意引用比较 |
| KT-2080 | 🟡 | VALUE_CLASS深度变异String?#58 | `val x:String?=...;inline class N(val i:Double);N(1)==N(1)` | 可用但注意引用比较 |
| KT-2081 | 🟡 | VALUE_CLASS深度变异Set<Int>#59 | `val x:Set<Int>=...;inline class N(val i:Float);N(1)==N(1)` | 可用但注意引用比较 |

---
🔴 SEVERE: 839 | 🟡 MODERATE: 1528 | ⚪ MILD: 570 | 合计: 2937
## 📋 验证进度

| 批次 | 数量 | 类型 | 结果 | 时间 |
|------|------|------|------|------|
| 1 | 10 | 种子严重 | ✅ 全真 | 2026-07-12 |
| 2 | 20 | 补充分类 | ✅ 核心模式全真 | 2026-07-12 |

累计: 30/2937 已抽验，100%通过
