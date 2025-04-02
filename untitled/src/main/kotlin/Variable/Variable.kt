package org.example.Variable

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val name: String = "devLifter" // 상수
    var age: Int = 31

    println("이름 : $name, 나이 : $age")
    age = 32
//    name = "devLifterrrrr" // 상수 변경 불가
    println("일년 후 나이 : $age")

    // 타입 추론
    val languageName = "Kotlin"
    val upperCaseName = languageName.uppercase()
    println(upperCaseName)
    // Fails to compile
//    languageName.inc() // Error
}