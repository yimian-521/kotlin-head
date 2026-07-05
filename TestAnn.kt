import com.qitong.head.lexer.*
import com.qitong.head.parser.*
import com.qitong.head.ast.*

fun main() {
    val src = "@Entity(tableName = \"test\")\n@Serializable\ndata class TestData(val id: Int)\n"
    val tokens = Lexer(src).tokenize()
    val parser = Parser(tokens)
    val file = parser.parseFile()
    println("decls: ${file.declarations.size}")
    for (d in file.declarations) {
        when(d) {
            is KtClass -> {
                println("KtClass: ${d.name}, annotations: ${d.annotations.size}")
                d.annotations.forEach { println("  @${it.name}(${it.args})") }
            }
            else -> println("other: ${d.javaClass.simpleName}")
        }
    }
}
