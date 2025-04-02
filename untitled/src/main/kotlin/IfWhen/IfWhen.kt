package IfWhen

fun main() {
    val count = 42

    if (count == 42) {
        println("I have the answer.")
    } else if (count > 35){
        println("The answer eludes me.")
    } else {
        print("The answer eludes me.")
    }

    // 변수 선언시 사용 가능
    val answerString: String = if (count == 42) {
        "I have the answer."
    } else if (count > 35) {
        "The answer is close."
    } else {
        "The answer eludes me."
    }

    println(answerString)

    // when
    val grade = when (count / 10) {
        10, 9 -> "A"
        8 -> "B"
        7 -> "C"
        else -> "F"
    }

    val answerStirng2 = when {
        count == 42 -> "I have the answer."
        count > 35 -> "The answer is close."
        else -> "The answer eludes me."
    }
}