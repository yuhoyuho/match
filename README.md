# REALMATCH+

REALMATCH+는 소셜 로그인 기반 회원 관리, 추천/매칭, 1:1 채팅, 랜덤통화, 결제/코인, 커뮤니티, 운영자 관리 기능을 포함하는 매칭 서비스입니다.

현재 저장소는 PDF 설계 문서를 바탕으로 바로 개발을 시작할 수 있도록 백엔드, 프론트엔드, 데이터베이스 초기 환경만 구성한 상태입니다. 실제 비즈니스 기능, API, 화면 구현은 아직 포함되어 있지 않습니다.

## 프로젝트 구성

```text
RealMatch
├── backend
│   ├── src/main/java/com/realmatch/backend
│   ├── src/main/resources
│   ├── build.gradle
│   └── compose.yaml
├── frontend
│   ├── src
│   ├── package.json
│   └── vite.config.js
└── README.md
```

## 기술 스택

### 백엔드

- Java 21
- Spring Boot 4.0.6
- Gradle Wrapper
- Spring Web MVC
- Spring Security
- Spring Data JPA
- Spring WebSocket
- Spring Data Redis
- Flyway
- PostgreSQL
- Docker Compose

### 프론트엔드

- React 19
- Vite
- ESLint
- Vite 개발 서버 `/api` 프록시 설정

### 데이터베이스 및 인프라

- PostgreSQL 16
- Redis 7
- Flyway 기반 스키마 마이그레이션

## 로컬 실행 방법

### 백엔드 실행

```bash
cd backend
docker compose up -d
./gradlew bootRun
```

백엔드 서버는 기본적으로 아래 주소에서 실행됩니다.

```text
http://localhost:8080
```

Actuator 헬스체크 주소는 다음과 같습니다.

```text
http://localhost:8080/actuator/health
```

### 프론트엔드 실행

```bash
cd frontend
npm install
npm run dev
```

프론트엔드 개발 서버는 기본적으로 아래 주소에서 실행됩니다.

```text
http://localhost:5173
```

프론트엔드에서 `/api`로 시작하는 요청은 Vite 프록시를 통해 백엔드 서버로 전달됩니다.

## 로컬 인프라 포트

기존 로컬 개발 환경과의 포트 충돌을 줄이기 위해 PostgreSQL과 Redis의 호스트 포트는 기본 포트와 다르게 설정되어 있습니다.

| 인프라 | 주소 |
| --- | --- |
| PostgreSQL | `127.0.0.1:55432` |
| Redis | `127.0.0.1:6380` |

Spring Boot 설정은 [application.yml](/Users/lyh/Desktop/RealMatch/backend/src/main/resources/application.yml)에 정의되어 있으며, 필요한 경우 환경변수로 값을 덮어쓸 수 있습니다.

## 데이터베이스 스키마

초기 데이터베이스 스키마는 Flyway 마이그레이션 파일로 관리합니다.

```text
backend/src/main/resources/db/migration/V1__init_realmatch_schema.sql
```

현재 마이그레이션에는 PDF 설계 문서에서 개발에 필요한 테이블을 기준으로 다음 영역의 스키마가 포함되어 있습니다.

- 인증, 소셜 로그인 제공자, 리프레시 토큰
- 회원, 프로필, 프로필 이미지, 선호 조건, 디바이스, 약관 동의
- 추천 노출 이력, 좋아요/패스, 매칭, 차단, 신고
- 채팅방, 채팅방 멤버, 메시지, 읽음 상태, 메시지 신고
- 랜덤통화 대기열, 통화 세션, 세션 이벤트, 사용자 통화 상태
- 코인 상품, 결제 주문, 영수증, 지갑, 코인 원장, 예약 차감, 환불 요청
- 커뮤니티 카테고리, 게시글, 게시글 이미지, 댓글, 좋아요, 신고, 숨김 처리
- 관리자 계정, 관리자 권한, 운영 케이스, 제재, 운영 액션 로그, 감사 로그

## 백엔드 패키지 구조

백엔드는 DDD 기반 헥사고날 아키텍처를 적용할 수 있도록 구성했습니다. 다만 처음 적용하는 단계에서 파일 수가 과도하게 늘어나지 않도록, 도메인별 UseCase와 Port를 적당히 묶은 실전형 구조로 정리했습니다.

핵심 의존 방향은 아래와 같습니다.

```text
adapter -> application -> domain
```

`domain`은 Spring MVC, JPA, Redis, 외부 OAuth, 결제 API를 직접 알지 않습니다. `application`은 유스케이스를 조율하고, 필요한 외부 기능은 `port/out` 인터페이스로만 바라봅니다. 실제 DB, Redis, Web, OAuth, 결제 검증 구현은 `adapter`에 둡니다.

```text
com.realmatch.backend
├── common
│   ├── Routes.java
│   └── exception
├── config
│   ├── JpaAuditingConfig.java
│   ├── RedisConfig.java
│   └── SecurityConfig.java
├── auth
├── user
├── matching
├── chat
├── randomcall
├── payment
├── community
└── admin
```

각 도메인은 기본적으로 아래 구조를 가집니다.

```text
{domain}
├── domain
│   └── model
├── application
│   ├── port
│   │   ├── in
│   │   └── out
│   └── service
└── adapter
    ├── in
    │   └── web
    └── out
        ├── persistence
        └── redis/oauth/token/store/cache/realtime/payment
```

예를 들어 인증 도메인은 아래처럼 구성됩니다.

```text
auth
├── domain/model
│   ├── AuthAccount.java
│   └── RefreshTokenSession.java
├── application/port/in
│   └── AuthUseCase.java
├── application/port/out
│   ├── AuthPersistencePort.java
│   ├── SocialTokenPort.java
│   └── TokenIssuePort.java
├── application/service
│   └── AuthApplicationService.java
├── adapter/in/web
│   └── AuthController.java
└── adapter/out
    ├── persistence
    │   ├── AuthPersistenceAdapter.java
    │   ├── AuthProviderJpaEntity.java
    │   └── AuthProviderJpaRepository.java
    ├── oauth
    │   └── SocialTokenVerificationAdapter.java
    └── token
        └── JwtTokenAdapter.java
```

## 패키지별 의미

### `domain/model`

비즈니스 핵심 모델을 둡니다. JPA Entity가 아니라 순수 도메인 모델입니다.

- 회원 상태, 매칭 상태, 결제 원장 사유 같은 도메인 상태값
- `RefreshToken` 만료/폐기 여부
- `Match` 상호 좋아요 성립 규칙
- `CoinWallet`, `CoinLedger` 잔액 및 원장 규칙
- `CallSession` 통화 상태 전이 규칙

이 패키지에는 `@Entity`, `JpaRepository`, `@RestController` 같은 외부 기술 의존성을 넣지 않는 방향으로 개발합니다.

### `application/port/in`

외부에서 애플리케이션 안쪽으로 들어오는 유스케이스 인터페이스를 둡니다.

- `AuthUseCase`
- `UserUseCase`
- `MatchingUseCase`
- `ChatUseCase`
- `RandomCallUseCase`
- `PaymentUseCase`
- `CommunityUseCase`
- `AdminUseCase`

Command, Query, Result 객체는 각 UseCase 내부의 nested record로 묶었습니다. 파일 수를 줄이면서도 Web 요청 DTO를 바로 서비스에 넘기지 않고 유스케이스에 맞는 Command로 변환하는 흐름은 유지합니다.

### `application/port/out`

유스케이스가 바깥 세계에 요청해야 하는 기능을 인터페이스로 정의합니다.

- DB 조회/저장 포트
- Redis 대기열/락 포트
- OAuth 토큰 검증 포트
- JWT 발급 포트
- 스토어 영수증 검증 포트
- WebSocket/Redis Pub/Sub 메시지 전달 포트
- 인기글 캐시 포트

`port/out`은 `JpaRepository` 구현체가 아닙니다. 애플리케이션이 필요로 하는 능력을 추상화한 인터페이스입니다. 실제 구현은 `adapter/out`에서 담당합니다.

### `application/service`

Input Port를 구현하는 유스케이스 서비스입니다.

- 트랜잭션 경계 관리
- 도메인 모델 호출
- Output Port 호출
- API 한 번에 필요한 흐름 조율

예를 들어 `PaymentApplicationService`는 영수증 검증, 중복 결제 방지, 결제 승인, 원장 적립, 지갑 갱신 흐름을 조율합니다. 하지만 실제 영수증 검증 API나 JPA 저장소에는 직접 의존하지 않습니다.

### `adapter/in/web`

HTTP API 진입점입니다.

- `@RestController`
- Request DTO
- Response DTO
- Request -> Command 변환
- Result -> Response 변환

컨트롤러는 얇게 유지합니다. 비즈니스 판단은 `application/service` 또는 `domain/model` 쪽으로 내려보냅니다.

### `adapter/out/persistence`

DB 접근 구현을 담당합니다.

- `JpaEntity`
- `JpaRepository`
- `PersistenceAdapter`
- 도메인 모델과 JPA Entity 간 Mapper

`PersistenceAdapter`는 `application/port/out` 인터페이스를 `implements`하고, 내부에서 `JpaRepository`를 사용합니다.

흐름은 아래와 같습니다.

```text
ApplicationService
  -> UserPersistencePort
  -> UserPersistenceAdapter
  -> UserJpaRepository
  -> PostgreSQL
```

### `adapter/out/*`

DB 외부의 기술 구현을 둡니다.

- `auth/adapter/out/oauth`: Apple, Google, Kakao, Naver 토큰 검증
- `auth/adapter/out/token`: JWT 발급
- `chat/adapter/out/realtime`: WebSocket, Redis Pub/Sub 메시지 전달
- `randomcall/adapter/out/redis`: 랜덤통화 대기열, heartbeat, 분산 락
- `randomcall/adapter/out/payment`: 통화 코인 예약 차감 연동
- `payment/adapter/out/store`: App Store, Google Play 영수증 검증
- `payment/adapter/out/redis`: 결제 중복 처리 방지 키
- `community/adapter/out/cache`: 인기글, 반응 수 캐시

## 도메인 구성

### `auth`

소셜 로그인과 토큰 발급을 담당합니다.

- 소셜 provider token 서버 검증
- `provider_user_id` 기준 기존 계정 조회
- 신규 `users`, `user_auth_providers` 생성
- Refresh Token hash 저장, 재발급, 폐기
- 명시적 계정 연동
- 마지막 로그인 수단 해제 제한 정책

### `user`

회원 본체와 사용자 정보를 담당합니다.

- 기본 회원 정보
- 프로필 완성 단계
- 프로필 이미지
- 매칭 선호 조건
- 랜덤통화 허용 여부
- 디바이스/푸시 토큰
- 약관 동의 이력
- soft delete 기반 탈퇴 처리

### `matching`

추천 카드, 좋아요/패스, 상호 매칭, 차단/신고 제외를 담당합니다.

- 선호 조건 기반 1차 후보 조회
- 이미 본 사용자, 좋아요/패스, 차단, 신고 제외
- 추천 노출 이력 저장
- LIKE/PASS idempotent 처리
- 상호 LIKE 매칭 생성
- 차단 관계 저장

### `chat`

매칭 이후의 1:1 채팅을 담당합니다.

- `match_id` 기준 채팅방 1개 생성
- 메시지 DB 저장 후 실시간 전달
- 읽음 기준점 갱신
- unread count 계산
- 차단/신고/종료 상태에 따른 전송 제한
- 메시지 신고 연동

### `randomcall`

랜덤 음성 통화 대기열과 세션을 담당합니다.

- 코인 잔액, 통화 허용 여부, 차단/신고 상태 확인
- Redis 대기열 등록
- 중복 연결 방지 분산 락
- WebRTC 연결 세션 생성
- `CONNECTING`, `ACTIVE`, `ENDED`, `FAILED`, `CANCELED` 상태 전이
- 실패/취소 이벤트 기록
- 코인 차감/환불 연동

### `payment`

결제, 코인 지갑, 원장을 담당합니다.

- 코인 상품 조회
- App Store / Google Play 영수증 검증
- purchase token 중복 반영 방지
- 결제 승인과 코인 적립 분리
- `coin_ledger` 기준 증감 이력 추적
- `coin_wallet` 잔액 갱신
- 기능 사용 전 예약 차감
- 환불/복구 요청

### `community`

커뮤니티 게시글, 댓글, 좋아요, 신고를 담당합니다.

- 카테고리별 게시글 목록
- 최신순/인기순 정렬
- 게시글 작성
- 댓글/대댓글 작성
- 게시글 좋아요 idempotent 처리
- 게시글/댓글 신고
- 사용자별 숨김 처리
- 인기글 Redis 캐시
- 추천 고도화용 활동 로그 적재

### `admin`

운영자와 운영 검토 흐름을 담당합니다.

- 관리자 계정과 RBAC
- 신고/검토 케이스
- 회원 경고, 정지, 영구 제재
- 콘텐츠 숨김/복구
- 결제/환불 검토
- 운영자 액션 감사 로그
- 운영 대시보드 지표

## 현재 생성된 골격

현재 백엔드에는 실제 비즈니스 구현 없이 개발 시작에 필요한 클래스와 인터페이스 골격만 생성되어 있습니다. Java 파일 수는 약 125개 수준으로 줄였고, 각 도메인마다 아래 구성만 우선 유지합니다.

- 각 도메인의 `domain/model` 순수 모델
- 각 도메인의 `application/port/in/{Domain}UseCase` 인터페이스
- 각 도메인의 `application/port/out` 외부 의존 포트 인터페이스
- 각 도메인의 `application/service/*ApplicationService`
- 각 도메인의 `adapter/in/web/*Controller`
- 각 도메인의 핵심 `adapter/out/persistence/*JpaEntity`, `*JpaRepository`, `*PersistenceAdapter`
- Redis, OAuth, JWT, Store, Realtime, Cache 등 외부 기술 어댑터 골격

각 파일에는 PDF 설계서 기준으로 실제 구현해야 할 내용을 `TODO` 주석으로 남겨두었습니다.

## 개발 진행 기준

실제 기능을 추가할 때는 아래 기준을 따릅니다.

- Controller는 Request를 Command로 변환하고 UseCase를 호출합니다.
- Application Service는 UseCase를 구현하고 트랜잭션 흐름을 조율합니다.
- Domain Model은 핵심 상태와 비즈니스 규칙을 표현합니다.
- Output Port는 DB, Redis, 외부 API, 토큰 발급 같은 외부 의존을 추상화합니다.
- Adapter는 Output Port를 구현하고 실제 기술을 사용합니다.
- JPA Entity는 `adapter/out/persistence`에만 둡니다.
- 도메인 모델을 API 응답이나 JPA Entity로 직접 노출하지 않습니다.
- 결제/코인/환불은 원장과 멱등성을 먼저 구현합니다.
- 랜덤통화/채팅은 Redis 기반 상태와 DB 이력의 역할을 분리합니다.
- 운영자 제재/환불/숨김/복구는 반드시 감사 로그를 남깁니다.

## 현재 개발 상태

- 프로젝트 초기 개발 환경 구성 완료
- 백엔드 Spring Boot 프로젝트 구성 완료
- 프론트엔드 React + Vite 프로젝트 구성 완료
- PostgreSQL, Redis Docker Compose 구성 완료
- Flyway 초기 스키마 작성 완료
- DDD 기반 실전형 헥사고날 패키지 구조 적용 완료
- PDF 설계서 기반 TODO 주석 작성 완료
- 각 UseCase, Port, Service, Adapter, 핵심 JPA Repository 골격 작성 완료
- `package-info.java` 제거 완료
- 실제 도메인 기능 구현은 아직 시작하지 않음
