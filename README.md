## ✨ 당근 마켓 따라하기
🤷‍♂️당근 마켓 기본적인 기능을 따라해보자.

<br><br>

## ⚒ 기술스택
* ### Backend
  * Java
  * Spring boot
  * Spring Security
  * Thymeleaf

* ### DB & ORM
  * PostgreSQL
  * JPA

* ### Frontend
  * HTML, CSS, Javascript
  * jQuery

<br><br>

## 💾 ERD 설계

<img src="https://user-images.githubusercontent.com/50009692/202901096-4e61f658-4bd0-43f8-abcc-6801c3d58c69.png">

<br><br>


## ⚙ 환경설정

* #### application.yml
```application.yml
spring:
  application:
    name: main-server
  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://localhost:5432/****
    username: ****
    password: ****
  jpa:
    hibernate:
      ddl-auto: create
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true
  data:
    mongodb:
      host: localhost
      port: 27017
      database: ****
  thymeleaf:
    cache: false
  messages:
    basename: messages, errors
  security:
    oauth2:
      client:
        registration:
          kakao:
            client-id: ****
            client-name: Kakao
            scope:
              - profile_nickname
              - account_email
              - profile_image
            authorization-grant-type: authorization_code
            redirect-uri: ****
            client-authorization-method: POST
          naver:
            client-id: ****
            client-secret: ****
            scope:
              - name
              - email
            client-name: Naver
            authorization-grant-type: authorization_code
            redirect-uri: ****
          google:
            client-id: ****
            client-secret: ****
            scope:
              - profile
              - email
        provider:
          kakao:
            authorization-uri: https://kauth.kakao.com/oauth/authorize
            token-uri: https://kauth.kakao.com/oauth/token
            user-info-uri: https://kapi.kakao.com/v2/user/me
            user-name-attribute: id
          naver:
            authorization-uri: https://nid.naver.com/oauth2.0/authorize
            token-uri: https://nid.naver.com/oauth2.0/token
            user-info-uri: https://openapi.naver.com/v1/nid/me
            user-name-attribute: response



logging.level:
  org.hibernate.SQL: debug


```

## 🛠️ 빌드 & 실행

### 일반 실행
```bash
./gradlew bootRun
```

### 개발 서버 실행 (PowerShell)
```powershell
./dev-run.ps1
```

### 빌드 캐시 문제 해결
Bean 충돌, 클래스 중복 등의 캐시 문제가 발생하면:

```powershell
# PowerShell 스크립트 실행
./clean-build.ps1

# 또는 수동으로
./gradlew clean build --refresh-dependencies
```

그 후 IntelliJ IDEA에서:
1. **File → Invalidate Caches / Restart**
2. **Invalidate and Restart** 클릭

### 💡 개발 팁
- `bootRun`은 자동으로 `clean`을 실행하도록 설정되어 있습니다
- 패키지 구조 변경이나 파일 이동 후에는 캐시 정리를 권장합니다
- Bean 중복 오류 발생 시 위의 캐시 정리 단계를 수행하세요
