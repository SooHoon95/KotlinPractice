package forRoof

fun main() {
    val items = listOf("🍎", "🍌", "🍊")

    // for 반복
    for (item in items) {
        println("과일: $item")
    }

    // while 반복
    var i = 0
    while (i < items.size) {
        println("[$i] 번째 과일: ${items[i]}")
        i++
    }
}