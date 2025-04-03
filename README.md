# KotlinPractice
Kotlin 기본 다지기
---
# Kotlin 기본 문법

Kotlin은 간결하고 직관적인 문법으로 잘 알려진 현대적인 언어. 이 문서는 Kotlin을 처음 배우는 개발자를 위한 핵심 문법과 개념을 예제로 정리한 교본입니다.

---

## 1. 변수 선언 (val, var)
**val**은 불변(immutable) 변수로 값을 한 번만 설정할 수 있습니다. 반면 **var**는 가변(mutable) 변수로 값을 자유롭게 변경할 수 있습니다.
```kotlin
val name: String = "Kotlin"
var age: Int = 25

age = 26
println("이름: $name, 나이: $age")
```
**출력:**
```
이름: Kotlin, 나이: 26
```

---

## 2. 기본 타입
Kotlin은 기본적으로 다음과 같은 자료형을 사용합니다: `Int`, `Double`, `String`, `Boolean`
```kotlin
val count: Int = 10
val pi: Double = 3.14
val message: String = "Hello"
val isActive: Boolean = true

println("$count, $pi, $message, $isActive")
```
**출력:**
```
10, 3.14, Hello, true
```

---

## 3. 조건문 (if, when)
조건에 따라 분기 처리할 수 있으며, `when`은 `switch`문과 유사한 역할을 합니다.
```kotlin
val score = 85

if (score >= 90) {
    println("A등급")
} else if (score >= 80) {
    println("B등급")
} else {
    println("C등급 이하")
}

val grade = when (score / 10) {
    10, 9 -> "A"
    8 -> "B"
    7 -> "C"
    else -> "F"
}
println("학점: $grade")
```
**출력:**
```
B등급
학점: B
```

---

## 4. 반복문 (for, while)
리스트나 조건을 기반으로 반복 수행하는 문법입니다.
```kotlin
val items = listOf("apple", "banana", "orange")

for (item in items) {
    println(item)
}

var index = 0
while (index < items.size) {
    println(items[index])
    index++
}
```
**출력:**
```
apple
banana
orange
apple
banana
orange
```

---

## 5. 함수 정의 및 호출
Kotlin의 함수는 `fun` 키워드를 사용해 정의하며, 표현식 형태의 단축 함수도 가능합니다.
```kotlin
fun greet(name: String): String {
    return "Hello, $name"
}

fun add(a: Int, b: Int): Int = a + b

fun main() {
    println(greet("Kotlin"))
    println("2 + 3 = ${add(2, 3)}")
}
```
**출력:**
```
Hello, Kotlin
2 + 3 = 5
```

---

## 6. 클래스와 객체 생성
Kotlin은 객체지향 언어로, 클래스를 통해 객체를 정의하고 생성할 수 있습니다.
```kotlin
class Person(val name: String, var age: Int) {
    fun introduce() {
        println("저는 $name이고, $age살입니다.")
    }
}

val person = Person("지민", 27)
person.introduce()
```
**출력:**
```
저는 지민이고, 27살입니다.
```

---

## 7. Null Safety (?, ?:, !!)
Kotlin은 NullPointerException을 방지하기 위해 Null 안전성을 지원합니다.
```kotlin
var name: String? = null
println(name?.length)
println(name ?: "Unknown")
```
**출력:**
```
null
Unknown
```

---

## 8. Data class, object 키워드
`data class`는 데이터 저장용 클래스로 자동으로 `toString`, `equals`, `copy` 등을 생성해줍니다.
`object`는 싱글턴 객체를 정의할 때 사용되며, 클래스 없이 인스턴스 하나만 존재합니다.
```kotlin
data class User(val id: Int, val name: String)
val user = User(1, "홍길동")
println(user)

object Singleton {
    val version = "1.0"
    fun printVersion() = println(version)
}
Singleton.printVersion()
```
**출력:**
```
User(id=1, name=홍길동)
1.0
```

---

## 9. 확장 함수
기존 클래스에 새로운 함수를 추가할 수 있는 기능입니다. 주로 유틸성 함수 작성 시 유용합니다.
```kotlin
fun String.addPrefix(prefix: String): String {
    return "$prefix$this"
}

println("World".addPrefix("Hello "))
```
**출력:**
```
Hello World
```

---

## 10. 고차 함수, 람다식
고차 함수는 함수를 인자로 받거나 함수를 반환하는 함수입니다. 람다식은 간단한 함수 표현 방식입니다.
```kotlin
fun calculate(a: Int, b: Int, operation: (Int, Int) -> Int): Int {
    return operation(a, b)
}

val result = calculate(3, 4) { x, y -> x + y }
println("결과: $result")
```
**출력:**
```
결과: 7
```

---

## 11. Smart Cast
형 검사 이후 자동으로 형 변환해주는 기능입니다. 별도의 캐스팅 없이 사용 가능합니다.
```kotlin
fun printLength(obj: Any) {
    if (obj is String) {
        println("문자열 길이: ${obj.length}")
    }
}

printLength("Kotlin")
```
**출력:**
```
문자열 길이: 6
```

---

## 12. Scope Functions: let, run, with, apply, also
객체 컨텍스트를 기준으로 블록을 실행하고, 흐름을 유연하게 구성할 수 있도록 도와주는 함수입니다.
```kotlin
val str: String? = "Kotlin"

str?.let {
    println("let: $it")
}

val result = run {
    val a = 1
    val b = 2
    a + b
}
println("run 결과: $result")

val person = Person("수빈", 30).apply {
    age = 31
}

with(person) {
    println("이름: $name, 나이: $age")
}

person.also {
    println("also로 객체 사용: ${it.name}")
}
```
**출력:**
```
let: Kotlin
run 결과: 3
이름: 수빈, 나이: 31
also로 객체 사용: 수빈
```

---

## 13. 컬렉션 함수: map, filter, reduce, fold
컬렉션을 함수형 스타일로 처리하는 방법을 제공합니다. `map`, `filter`, `reduce`, `fold` 등을 사용할 수 있습니다.
```kotlin
val numbers = listOf(1, 2, 3, 4, 5)

val doubled = numbers.map { it * 2 }
val even = numbers.filter { it % 2 == 0 }
val sum = numbers.reduce { acc, i -> acc + i }
val sumWithInit = numbers.fold(10) { acc, i -> acc + i }

println(doubled)
println(even)
println("합계: $sum, 초기값 포함 합: $sumWithInit")
```
**출력:**
```
[2, 4, 6, 8, 10]
[2, 4]
합계: 15, 초기값 포함 합: 25
```

---

## 14. 문자열 처리 & 날짜 처리 (기초)
문자열은 다양한 메서드를 통해 조작할 수 있으며, 날짜는 `java.time` 패키지를 사용합니다.
```kotlin
val text = "Hello Kotlin"
println(text.uppercase())
println(text.replace("Kotlin", "World"))

val now = java.time.LocalDateTime.now()
println("현재 시간: $now")
```
**출력 예시:**
```
HELLO KOTLIN
Hello World
현재 시간: 2025-04-02T10:30:00.123456
```

---

이 교본은 Kotlin의 기본기를 다지는 데 집중되어 있으며, 실습 프로젝트와 함께 사용하면 더욱 효과적입니다.


