// ╔══════════════════════════════════════════════════════════╗
// ║  地狱 v4：100 个中等到严重 bug——178S §1 门槛测试        ║
// ║  kotlin-head 必须：零崩溃、零静默、每个 bug 一条诊断    ║
// ╚══════════════════════════════════════════════════════════╝

// [1] 严重：不配对括号——传统编译器直接栈溢出
fun hell_1_broken_parens() {
    val x = (((((((((((((((((((((((((  // 多余左括号
}

// [2] 严重：右括号多于左括号
fun hell_2_extra_right() {
    val x = "test"))))))))))))))))))))
}

// [3] 严重：花括号不配对
class Hell_3_Braces { { { { { { { { { { { { { { { { {

// [4] 中等：return 后面无表达式
fun hell_4_bare_return(): Int {
    return
}

// [5] 中等：val 声明无右值
val hell_5_no_value:

// [6] 严重：class 关键字后无类名
class

// [7] 中等：fun 关键字后无函数名
fun

// [8] 严重：点号后面无 token
val hell_8 = x.

// [9] 中等：逗号后换行无下一个参数
fun hell_9(a: Int,
b: String,
c: Float,) { }

// [10] 严重：尖括号嵌套无闭合
val hell_10 = List<List<List<List<List<Int

// [11] 中等：when 分支无表达式
fun hell_11(x: Int) = when(x) {
    1 ->
    2 ->
    else ->
}

// [12] 严重：for 循环无 in
for hell_12

// [13] 严重：if 无条件
if

// [14] 中等：else 无 if
else

// [15] 严重：对象表达式无 body
val hell_15 = object

// [16] 中等：匿名函数无参数列表
val hell_16 = fun

// [17] 严重：try 无 catch 或 finally
val hell_17 = try { "oops" }

// [18] 中等：扩展函数无接收者
fun hell_18.boom() { }

// [19] 严重：泛型函数无类型参数
fun <hell_19> nope() { }

// [20] 中等：尖括号不对称
val hell_20 = Map<String, Int<Float, Double>>>

// [21] 严重：嵌套类声明不完整
class Hell_21 {
    class
}

// [22] 中等：构造函数无参数列表
class Hell_22 constructor

// [23] 严重：interface 无 body
interface Hell_23

// [24] 中等：object 声明无名称
object

// [25] 严重：注释未闭合
/* hell_25_unclosed_comment
val x = 1

// [26] 中等：字符串未闭合
val hell_26 = "unclosed string

// [27] 严重：空标签
@

// [28] 中等：注解无参数
@Deprecated
annotation class Hell_28

// [29] 严重：重复分隔符
val hell_29 = 1 + + + + 2

// [30] 中等：多行点号链断开
val hell_30 = obj.

    .chain2()

// [31] 严重：inline 和 crossinline 同时用在非函数类型
inline fun hell_31(crossinline x: Int) { }

// [32] 中等：reified 不用在泛型参数
fun hell_32(reified T: Any) { }

// [33] 严重：open 用在非类
open fun hell_33() { }

// [34] 中等：data class 无属性
data class Hell_34()

// [35] 严重：sealed class 在非顶层
fun hell_35() {
    sealed class Nope
}

// [36] 中等：enum 条目无逗号
enum class Hell_36 { A B C }

// [37] 严重：问号用错地方
val hell_37: ??? = null

// [38] 中等：泛型星号投影无类型
val hell_38: List<*> = listOf(1)

// [39] 严重：嵌套函数名重复
fun hell_39() { fun hell_39() { fun hell_39() { } } }

// [40] 中等：属性 getter 无 body
val hell_40: Int get

// [41] 严重：setter 无参数
var hell_41: Int set

// [42] 中等：by lazy 在顶层无委托
val hell_42 by

// [43] 严重：operator 无函数
operator

// [44] 中等：infix 在非成员函数
infix fun hell_44() { }

// [45] 严重：tailrec 在非递归函数
tailrec fun hell_45(x: Int): Int = x + 1

// [46] 中等：vararg 后面跟参数
fun hell_46(vararg x: Int, y: String) { }

// [47] 严重：大段垃圾 token
val hell_47 = @@@ ### $$$ %%% ^^^ &&& ***

// [48] 中等：lambda 在非函数位置
val hell_48 = { }

// [49] 严重：混合中英文符号
val hell_49 = "test" + 1 + true + null + "end"

// [50] 中等：下划线当类型
val hell_50: _ = 1

// [51] 严重：百万层 if 嵌套（简化为多层）
fun hell_51(): Int = if (true) if (true) if (true) if (true) if (true) if (true) if (true) if (true) 1 else 0 else 0 else 0 else 0 else 0 else 0 else 0 else 0

// [52] 中等：null 加运算符
val hell_52 = null + null

// [53] 严重：数组越界式索引
val hell_53 = list[

// [54] 中等：空 when
val hell_54 = when { }

// [55] 严重：赋值在表达式位置
val hell_55 = (x = 5)

// [56] 中等：init 块在对象外
init { println("nope") }

// [57] 严重：this 无上下文
val hell_57 = this.that

// [58] 中等：super 无父类
val hell_58 = super.toString()

// [59] 严重：continue 不在循环
continue

// [60] 中等：break 不在循环
break

// [61] 严重：注解中放代码
@Suppress(val x = 1)

// [62] 中等：import 在声明中间

import java.io.File

// [63] 严重：import 路径不存在
import com.hell.no.way.non.existent.path.Void

// [64] 中等：类型不匹配赋值
val hell_64: Int = "not an int"

// [65] 严重：类型不匹配返回
fun hell_65(): String = 42

// [66] 中等：Int 调用 String 方法
val hell_66 = 42.length

// [67] 严重：不存在的操作符
val hell_67 = "test" %%% 42

// [68] 中等：比较类型不兼容
val hell_68 = "hello" > 3.14

// [69] 严重：Boolean 算术
val hell_69 = true + false * null / 0

// [70] 中等：不完整三元模拟
val hell_70 = if (true) "yes" else

// [71] 严重：嵌套注释破坏
/* /* /* hell_71 */ /* */

// [72] 中等：扩展属性无接收者
val String.hell_72: Int get() = length

// [73] 严重：模糊的 receiver
with(42) { hell_73() }

// [74] 中等：类型别名循环引用
typealias Hell_74_A = Hell_74_B

// [75] 严重：委托属性循环
val hell_75 by lazy { hell_75 }

// [76] 中等：数组初始化无大小
val hell_76 = Array() { it }

// [77] 严重：range 无界限
val hell_77 = 1..

// [78] 中等：跳脱字符无效
val hell_78 = "\xZZ\uXXXX"

// [79] 严重：跨行容错连环——第79个
fun hell_79(
    x: Int
    y: String  // 缺逗号
    z: Float,
) { }

// [80] 中等：不支持的语言特性——内联类模拟
inline class Hell_80(val x: Int)

// [81] 严重：协程上下文错
suspend fun hell_81() { launch { } }  // launch无CoroutineScope

// [82] 中等：挂起函数在非协程调用
fun hell_82() { hell_81() }

// [83] 严重：受检异常未处理——Java互操作模拟
@Throws(Exception::class) fun hell_83() { throw Exception("bang") }

// [84] 中等：静态方法在非伴生对象
fun Hell_84.Companion.staticLike() { }

// [85] 严重：多个 init 块——合法但考验
class Hell_85 { init { } init { } init { } init { } }

// [86] 中等：constructor 带 body
class Hell_86 { constructor() { println("nope") } }

// [87] 严重：数据类继承数据类（Kotlin禁止）
data class Hell_87_A(val x: Int)

// [88] 中等：委托实现不完整
class Hell_88 : List<Int> by

// [89] 严重：sam转换失败场景
fun hell_89(r: Runnable) { }
fun call_89() { hell_89 { println("sam?") } }  // 在非Java接口

// [90] 中等：顶层属性初始化异常
val hell_90: Int = throw RuntimeException("init fail")

// [91] 严重：泛型变异不兼容
val hell_91: MutableList<out Int> = mutableListOf(1, 2)

// [92] 中等：where clause 无泛型
fun <T> hell_92(x: T) where T : Any { }

// [93] 严重：捕获非异常类型
try { } catch (e: String) { }

// [94] 中等：finally 中有 return
fun hell_94(): Int = try { 1 } finally { return 0 }

// [95] 严重：标签和标识符混淆
hell_95@ for (i in 1..10) { break@hell_95 }

// [96] 中等：lambda 标签嵌套
val hell_96 = listOf(1).map label@ { it * 2 }

// [97] 严重：引用未声明的变量
val hell_97 = undefinedVariable + anotherMissing

// [98] 中等：智能转换失败
fun hell_98(x: Any?) { if (x is String) { x as Int } }

// [99] 严重：覆盖属性类型冲突
interface I99 { val x: Int }

// [100] 中等：第一百个——多平台声明不完整
expect fun hell_100(x: Int): String

// ╔══════════════════════════════════════════════════════════╗
// ║  地狱 v4 结束：100 个 bug 全部就位                        ║
// ║  跑它：kotlin -cp kotlin-head.jar com.qitong.head.Main  ║
// ╚══════════════════════════════════════════════════════════╝
