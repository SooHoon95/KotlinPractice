package NullSafety

fun main() {
//    val languageName: String = null // Fail to complile
    var languageName: String? = null // 성공

    // Optional Binding
    val languageName2: String? = null
    if (languageName2 != null) {
        println(languageName2.uppercase())
    }

}