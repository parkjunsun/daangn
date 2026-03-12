## daangn-clone

당근마켓의 핵심 플로우를 학습 목적으로 구현한 Spring Boot 웹 애플리케이션입니다.  
게시글 거래, 채팅, 관심/알림, 리뷰, 소셜 로그인 흐름을 포함합니다.

## 주요 기능

- 회원가입/로그인/로그아웃
- OAuth2 소셜 로그인 (Kakao, Naver, Google)
- 동네(지역) 설정 및 인증
- 게시글 등록/조회/검색/카테고리 필터
- 관심 등록, 댓글, 활동 알림
- 1:1 채팅 및 채팅 알림
- 거래 완료 처리 및 구매자 지정
- 판매자/구매자 리뷰 작성 및 조회
- 비밀번호 재설정 메일 발송

## 기술 스택

- Language: Java 11
- Framework: Spring Boot 2.7.2
- Build: Gradle
- View: Thymeleaf
- Security: Spring Security, OAuth2 Client
- RDB: PostgreSQL + Spring Data JPA
- Chat Storage/Stream: MongoDB Reactive + WebFlux(Flux/Mono, SSE)
- Mail: Spring Mail

## 프로젝트 구조

```text
src/main/java/js/daangnclone
|- config          # Security, Web(MVC), MongoDB 설정
|- domain          # 엔티티/리포지토리 (board, member, chat, review, alarm...)
|- service         # 비즈니스 로직
|- web             # Controller + DTO
|- security        # UserDetails/OAuth2 사용자 처리
|- handler         # 공통 핸들러/인터셉터
\- SetUpDataLoader # 초기 샘플 데이터 적재
```

## 실행 전 준비

- JDK 11
- PostgreSQL (기본 예시: `localhost:5432/daangnDB`)
- MongoDB (기본 DB: `chatdb`, `MongoDBConfig` 기준)
- OAuth2 앱 키(Kakao/Naver/Google)
- 메일 발송 계정(SMTP)

## 설정

현재 `src/main/resources/application.yml`에 DB/OAuth/메일 관련 값이 직접 들어가 있습니다.  
협업/배포용으로는 민감정보를 반드시 외부화하세요.

권장 방식:

- `application-local.yml` 분리 후 Git 추적 제외
- 환경변수 또는 시크릿 매니저 사용
- 이미 노출된 키/비밀번호는 즉시 폐기(rotate)

예시(`application.yml`):

```yaml
spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  security:
    oauth2:
      client:
        registration:
          kakao:
            client-id: ${KAKAO_CLIENT_ID}
```

## 로컬 실행

```powershell
./gradlew.bat clean build
./gradlew.bat bootRun
```

기본 접속 주소: `http://localhost:8080`

## 테스트

현재 테스트 코드는 `src/test/java/js/daangnclone/DaangnCloneApplicationTests.java`의 컨텍스트 로딩 테스트가 포함되어 있습니다.

```powershell
./gradlew.bat test
```

## 참고 사항

- `SetUpDataLoader`가 실행 시 샘플 회원/게시글을 생성합니다.
- 채팅 메시지는 MongoDB 컬렉션을 통해 Reactive 스트림으로 처리합니다.
- 뷰 템플릿은 `src/main/resources/templates` 하위 도메인별 폴더로 구성되어 있습니다.
