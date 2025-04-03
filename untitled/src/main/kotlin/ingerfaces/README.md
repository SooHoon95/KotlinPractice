인터페이스
---

## 인터페이스란 무엇인가?
### 정의 

- 객체지향적 관점에서 보면 인터페이스는 서로 다른 객체와 협력할 수 있는 접점이라고 할 수 있다.<br>
- 인터페이스는 "이 클래스는 어떤 기능들을 반드시 가져야 해" 라고 하는 약속 설계도이다. 즉,

✅ 구현은 없고 기능의 이름만 정의한다.<br>
✅ 어떤 클래스가 이 인터페이스를 **구현한다** 라고 하는 것은 특정 함수의 기능을 직접 구현(override) 하는 것이다.

### Kotlin 에서의 인터페이스 철학
- kotlin에서 인터페이스는 Swift의 protocol 과 비슷한 형태와 역할을 가진다.
- 객체지향적 설계 관점에서 `유연한 설계`, `다형성` 을 지원하기 위한 구조이다.

### 문법

- kotlin 에서 인터페이스는 `interface` 를 사용하여 정의한다.
```
interface Authenricator {
    fun login(id: String, pw: String): Boolean
    fun logout()
}
```

- 구현내용은 인터페이스를 상속받은 클래스에서 `override` 해서 사용한다.
```
class EmailAuthenticator: Authenticator {
    override fun login(id: String, pw: String): Boolean {
        println("이메일 로그인 실행 중: $id")
        return true
    }
    
    override fun logout() {
        println("로그아웃 완료")
    }
}
```
- 실제 사용은 다음과 같다
```
fun main() {
    val auth: Authenticator = EmailAuthenticator()
    auth.login("test@example.com", "1234")
    auth.logout()
}
```

### 예외

- 예외적으로 디폴트구현도 가능하다. 이를 구현한 클래스에서는 굳이 override 하지 않고 사용할 수 있다.
```
interface Logger {
    fun log(message: String) {
        println("로그: $message")
    }
}

class SimpleLogger : Logger

fun main() {
    val logger = SimpleLogger()
    logger.log("Hello!") // 출력: 로그: Hello!
}
```

### 인터페이스 사용이 필요한 이유

- 처음 개발을 하다보면 '그냥 일일이 구현해서 사용하면 되지, 왜 굳이 복잡하게 구조를 나눠서 사용하지?' 라고 생각할 수 있다.
필자도 그랬다. 그런데, 객체지향에 대해서 공부하고 실무에서 유지보수에 관련된 일을 하다가 보면 이렇게 인터페이스를 나누는 것에 대해서 필요성을 알 수 있다.

1️⃣ 유지보수가 쉬워진다. -> 코드를 수정 할 때 특정 `다형성`을 구현함에 따라 특정 부분만 수정할 수 있고 이로인해서 다른 클래스에 영향을 덜 줄 수 있다.

2️⃣ 테스트가 쉬워진다. -> Mock 데이터를 넣어서 테스트가 용이해진다. 만약 내가 실제로 구현하고자하는 비즈니스 로직에다가 테스트를 하려면 실제 로직은
전부 주석 처리하고나서 Mock 데이터를 넣어서 테스트를 할 수 있다. 그러나 인터페이스를 사용한다면 간단하게 테스트가 가능하다.
제일 아래쪽에 이를 설명한 예시 코드를 넣어두겠다.

3️⃣ 구현을 바꿀 수 있다. -> 특정 인터페이스를 사용한다면 이 함수들을 사용할거야! 라는 약속은 지키되, 내부 구현은 자유롭게 변경할 수 있다.

### 예제
```
interface AuthService {
    fun authenticate(username: String, password: String): Boolean
}

class GoogleAuthService : AuthService {
    override fun authenticate(username: String, password: String): Boolean {
        println("구글 인증 실행: $username")
        return true
    }
}

class NaverAuthService : AuthService {
    override fun authenticate(username: String, password: String): Boolean {
        println("네이버 인증 실행: $username")
        return true
    }
}

fun main() {
    val auth: AuthService = GoogleAuthService()
    auth.authenticate("choesuhun", "secret")
}
```

### Mock 데이터를 활용한 테스트가 쉬워짐을 이해하기 위한 코드

가정 : 서비스 안에서 AuthService 라는 인증기능을 테스트하려고 한다.<br>
만약 매번 진짜 인증서버 (Firebase, Google)등을 붙이려면<br>
- 테스트가 느려지고(서버통신)
- 서버 장애 시 테스트가 불가
- 로그인 실패/성공 케이스를 내가 컨트롤할 수 없음

위와 같은 이유들로 테스트가 번거로워진다. 이떄, Mock 데이터를 활용하면 의도한대로 테스트를 진행할 수 있다.
이때 interface 를 사용할 수 있다.

❌ 인터페이스 없이 실제 구현 함수에서 테스트 
```
class AuthService {
    fun login(id: String, password: String): Boolean {
        // 실제 서버 호출 (예: retrofit, firebase 등)
        println("실제 로그인 로직 실행 중...")
        return true
    }
}
```
-> 이 코드에서는 AuthService를 대체하기 어렵다. 왜냐하면 테스트를 위해서이지만 반드시 진짜 서버를 타야하기 때문이다.

✅ Interface를 사용하여 수정한 테스트 코드

- 인터페이스
```
interface AuthService {
    fun login(id: String, password: String): Boolean
}
```
- 실제 서비스 구현 부분
```
class RealAuthService : AuthService {
    override fun login(id: String, password: String): Boolean {
        println("🔥 진짜 로그인 서버 호출 중!")
        return true
    }
}
```
- 테스트용 Mock 클래스
```
class MockAuthService : AuthService {
    override fun login(id: String, password: String): Boolean {
        println("🧪 가짜 로그인 실행")
        return id == "test" && password == "pass"
    }
}
```
- 테스트 코드
```
fun testLogin() {
    val authService: AuthService = MockAuthService()

    val result = authService.login("test", "pass")

    println("로그인 결과: $result") // 👉 true
}
```
-> 마지막 테스트 코드에서는 `아이디`와 `비밀번호`가 맞는다면 login 함수가 true를 리턴해주는지 확인한다는 핵심 로직만을 테스트하기 위해 Mock 데이터를 넣었고 실제 로그인 서버에 접속하지 않고 훨씬 빠르고 간편하게 이를 테스트 할 수 있었다.
interface를 사용해서 테스트용 클래스를 구현할 수 있었기 때문이다. 이 예제는 간단하니까 직접 하는 게 더 빨라보여도, 로직이 복잡해진다면 이러한 방식이 훨씬 효율적임을 예상할 수 있다.




















