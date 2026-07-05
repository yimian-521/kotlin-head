import com.qitong.head.lexer.*
import java.io.File

fun main() {
    val src = File("/tmp/qitong-gateway/qitong-ai-gateway-3.4.3/app/src/main/java/com/qtwl/gateway/data/model/Provider.kt").readText()
    val tokens = Lexer(src).tokenize()
    tokens.take(20).forEachIndexed { i, t -> println("$i: ${t.type} \"${t.text}\" @ ${t.pos}") }
}