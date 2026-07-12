// kotlin-head 最小示例 — 新手五分钟跑通
fun main() {
    val name = "kotlin-head"
    val version = "v1.0.4"
    println("Hello from \$name \$version! 编译成功 ✅")
}

data class User(val id: Int, val name: String)
val user = User(1, "免免")
println("用户: \${user.name}")
