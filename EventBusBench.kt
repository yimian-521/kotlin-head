import com.qitong.head.eventbus.*

fun main() {
    EventBus.subscribe("test", object : EventHandler {
        override fun onEvent(event: Event) {}
    })

    // 预热
    repeat(1000) { EventBus.emitTo("test", "warmup") }

    // 实测
    val n = 100_000
    val start = System.nanoTime()
    repeat(n) { EventBus.emitTo("test", "bench") }
    val elapsed = System.nanoTime() - start

    val avgNs = elapsed.toDouble() / n
    println("次数: $n")
    println("总耗时: ${elapsed / 1_000_000}ms")
    println("平均每次: ${"%.0f".format(avgNs)}ns (${"%.2f".format(avgNs / 1000)}µs)")
}