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







