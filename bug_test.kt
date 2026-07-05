@Serializable
data class BuggyData(
    val id: Int,
    val name: String
)

open tailrec fun countDown(n: Int): Int = if (n <= 0) 0 else countDown(n - 1)

fun riskyCall(input: String): String {
    return input
}