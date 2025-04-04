# Retrofit (레트로핏)

안드로이드 개발 시 서버통신에 사용되는 HTTP API 를 JAVA, Kotlin 인터페이스 형태로 변환해서 API 호출을
쉽게 할 수 있도록 지원하는 라이브러리

- 타입 세이브한 HTTP 클라이언트 라이브러리
- REST API 의 HTTP 요청을 자바 인터페이스로 변환하는 것이 주 목적

## 장점

코드 간결성 증가
- 복잡한 HTTP API 코드를 간결하게 작성 가능합니다.<br>
- 어노테이션 문법으로 매서드와 URL 정의 가능합니다. 

안정성과 확장성

- 내부적으로 OkHttp 라이브러리를 사용해서 통신하므로 안정적인 통신이 가능합니다.<br>
- 인터셉터를 사용하여 프로세스를 확장 , 수정 가능합니다.

다양한 플러그인과 컨버터 지원
- Json, XML 등 다양한 데이터로 변환 가능합니다.
- RxJava, Coroutines와 같은 비동기 프로그래밍과 연동 가능합니다.


## 적용 및 사용
- Gradle에 추가
```
// build.gradle (Module: app)
dependencies {
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
	implementation 'com.google.code.gson:gson:2.8.6' // Gson 은 직렬화, 역직렬화를 위한 라이브러리 (Swift의 Encodable, Decodable)
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0' // Gson 컨버터 추가
		
}
```
- Manifests에 Internet 퍼미션 등록
```
// AndroidManifest.xml
<user-permission android:name="android.permission.INTERNET"/>
```
- Model 만들어 주기
```
// UserModel.kt (data class)
data class UserModel (
		@SerializedName("first_name") // 실제 json 데이터의 키 값
		var firstName: String, // 변수에 넣어줌
		@SerializedName("last_name")
		var lastName: String
)
```
- data class 생성
```
// UserListModel.kt (data class)
data class UserModel (
		var data: List<UserModel>?
)
```
- API 인터페이스 정의
```
interface ApiService {
    @GET("api/users")
    fun getUser(@Query("page") page: String): Call<UserListModel> // 콜 객체
}
```
- Retrofit 인스턴스 생성

-> apiService를 사용해서 API 사용 가능
```
// MainActivity.kt
val retrofit = Retrofit.Builder()
    .baseUrl("https://reqres.in/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

// 인터페이스를 레트로핏에 알려줘서 객체 얻기
val apiService = retrofit.create(ApiService::class.java)
```

## 응답 처리

- 응답 처리는 동기/비동기적으로 처리  가능한데, 

먼저 동기 처리는 `현재 스레드`에서 실행되고 응답이 올 떄까지 다음 코드 실행 잠시 중단되고,

비동기 처리는 `콜백`을 사용하여 백그라운드에서 실행. 응답이 도착했을 때 해당 콜백이 호출됩니다.
```
// MainActivity.kt
val response: Response<User> = apiService.getUser(id).execute()

// MainActivity.kt
// 네트워킹 객체 함수 콜하기
// enqueue 하는 순간 네트워킹 시작
apiService.getUser(page).enqueue(object: Callback<UserListModel> {

    override fun onResponse(call: Call<UserListModel>, response: Response<User>) {
        // 정상적으로 서버 데이터가 넘어오는 순간에 콜되는 함수 처리
				val userList = response.body()
				val mutableList = mutableListOf<Map<String, String>>()
				userList?.data?.forEach {
						val map = mapOf("firstName" to it.firstName, "lastName" to it.lastName)
						mutableList.add(map)
				}
				val adapter = SimpleAdapter(
						mutableList,
						android.R.layout.simple_list_item,
						arrayOf("firstName", "lastName")
						intArrayOf(android.R.id.text1, android.R.id.text2)
				)
				listView.adapter = adapter
    }

    override fun onFailure(call: Call<UserListModel>, t: Throwable) {
        // 서버 연동에 실패 됐을 때 콜 되는 함수란, 오류 처리
				call.cancel()
    }
})
```
-> onResponse 함수에서 성공 처리 , onFailure 에서 실패처리를 해줍니다.

- response 를 사용한 데이터 접근
```
if (response.isSuccessful) {
    val user: User? = response.body()
} else {
    // 오류 메시지 처리
    val error: String = response.errorBody()?.string() ?: "Unknown error"
}
```

- 에러 처리
```
override fun onFailure(call: Call<User>, t: Throwable) {
    // 오류 메시지 표시
    Log.e("API_ERROR", t.message ?: "Unknown error")
}
```

- Intercepter의 사용
```
// Moshi 컨버터 사용
val logging = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

val client = OkHttpClient.Builder()
    .addInterceptor(logging)
    .addInterceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer YOUR_TOKEN")
            .build()
        chain.proceed(request)
    }
    .build()

val retrofit = Retrofit.Builder()
    .baseUrl("https://api.example.com/")
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
```
-> 인터셉터 사용을 통해서 요청과 응답 사이에 원하는 로직을 추가할 수 있습니다.

출처 : [ouowinnie.log](https://velog.io/@ouowinnie/%EA%B0%9C%EB%85%90-%EB%A0%88%ED%8A%B8%EB%A1%9C%ED%95%8F-Retrofit)