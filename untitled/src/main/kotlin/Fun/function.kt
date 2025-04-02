package Fun

fun main() {
    val answerString = generateAnswerString(42)
    println(answerString)
}

// 함수 반환 타입
fun generateAnswerString(count: Int) : String {

    val answerString = if (count == 42) {
        "A"
    } else {
        "B"
    }
    return answerString
}

// 함수 단순화
fun simpleFunction(count: Int): String {
    return if (count > 42) {
        "I have the answer."
    } else {
        "The answer eludes me."
    }
}

fun simpleFunction2(count: Int) : String = if (count > 42) {
    "I have the answer"
} else {
    "The answer eludes me"
}

// 익명함수
val stringLengthFunc: (String) -> Int = { input ->
    input.length
}

// 익명함수 사용
class AnonymousFunctionTestClass() {
    val stringLength: Int = stringLengthFunc("Android")
}

// 고차함수
fun stringMapper(str: String, mapper: (String) -> Int): Int {
    return mapper(str)
}
// 사용
class FunctionTest() {
stringMapper("Android", { input -> input.length })
}