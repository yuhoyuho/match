# REALMATCH+ API 명세서

이 문서는 REALMATCH+ 백엔드/프론트엔드 개발을 바로 시작할 수 있도록 정리한 API 명세 초안입니다.

현재 프로젝트는 기능 구현 전 스캐폴딩 단계이므로, 아래 명세는 다음 파일을 기준으로 작성했습니다.

- API 경로: `backend/src/main/java/com/realmatch/backend/common/Routes.java`
- 입력 포트: `backend/src/main/java/com/realmatch/backend/*/application/port/in/*UseCase.java`
- Web Adapter: `backend/src/main/java/com/realmatch/backend/*/adapter/in/web/*Controller.java`
- DB 스키마: `backend/src/main/resources/db/migration/V1__init_realmatch_schema.sql`

## 1. 공통 규칙

### 1.1 Base URL

```text
로컬 개발: http://localhost:8080
API Prefix: /api/v1
```

예시:

```text
http://localhost:8080/api/v1/auth/social/login
```

### 1.2 Content-Type

요청 본문이 있는 API는 기본적으로 JSON을 사용합니다.

```http
Content-Type: application/json
Accept: application/json
```

### 1.3 인증 방식

최종 구현에서는 아래 방식을 기준으로 합니다.

```http
Authorization: Bearer {accessToken}
```

다만 현재 스캐폴딩 컨트롤러는 Spring Security 인증 객체 추출 전 단계이므로 임시로 아래 헤더를 사용합니다.

```http
X-USER-ID: {userId}
X-ADMIN-ID: {adminId}
```

구현 단계에서 `X-USER-ID`, `X-ADMIN-ID`는 제거하고, JWT 인증 결과에서 사용자 식별자를 추출하도록 변경합니다.

### 1.4 공통 응답 규칙

현재 컨트롤러는 별도 응답 래퍼 없이 DTO를 직접 반환하는 구조입니다.

성공 응답 예시:

```json
{
  "userId": 1,
  "nickname": "realmatch",
  "email": "user@example.com",
  "status": "ACTIVE",
  "profileCompleted": true
}
```

목록 응답 예시:

```json
[
  {
    "roomId": 1,
    "matchId": 10,
    "status": "ACTIVE",
    "unreadCount": 2
  }
]
```

본문이 없는 성공 응답은 `204 No Content`를 권장합니다.

### 1.5 공통 에러 응답

현재 `ErrorResponse` 기준의 공통 에러 응답 형식입니다.

```json
{
  "timestamp": "2026-06-05T12:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "요청 값이 올바르지 않습니다."
}
```

권장 상태 코드는 다음과 같습니다.

| 상태 코드 | 의미 |
| --- | --- |
| `400` | 요청 파라미터 또는 본문 값 오류 |
| `401` | 인증 실패 또는 토큰 만료 |
| `403` | 접근 권한 없음 |
| `404` | 리소스 없음 |
| `409` | 중복 요청, 상태 충돌, 멱등성 충돌 |
| `422` | 도메인 규칙 위반 |
| `500` | 서버 내부 오류 |

### 1.6 페이지네이션

현재 스캐폴딩에서는 `size` 중심의 단순 조회만 정의되어 있습니다.

향후 목록 API가 커지면 아래 기준으로 확장합니다.

```text
size: 한 번에 조회할 개수
cursor: 다음 페이지 기준 커서
sort: 정렬 기준
```

### 1.7 멱등성

결제, 코인 사용, 랜덤통화 취소/종료, 메시지 읽음 처리처럼 중복 요청 가능성이 높은 API는 멱등성을 고려합니다.

현재 명세에서 명시된 멱등성 키:

```text
coins/use: idempotencyKey
```

결제 검증, 환불 요청, 랜덤통화 종료도 구현 시 멱등성 키 도입 여부를 확정합니다.

## 2. 주요 Enum 값

DB 스키마의 `CHECK` 제약 기준입니다.

| 구분 | 값 |
| --- | --- |
| 사용자 상태 | `ACTIVE`, `SUSPENDED`, `DORMANT`, `WITHDRAWN`, `BANNED` |
| 성별 | `MALE`, `FEMALE`, `OTHER`, `UNKNOWN` |
| 선호 성별 | `MALE`, `FEMALE`, `ANY` |
| 소셜 제공자 | `APPLE`, `GOOGLE`, `KAKAO`, `NAVER` |
| 디바이스 OS | `IOS`, `ANDROID`, `WEB`, `UNKNOWN` |
| 리액션 | `LIKE`, `PASS` |
| 매칭 상태 | `ACTIVE`, `ENDED`, `BLOCKED` |
| 채팅방 상태 | `ACTIVE`, `CLOSED`, `BLOCKED` |
| 메시지 타입 | `TEXT`, `IMAGE`, `SYSTEM` |
| 메시지 상태 | `SENT`, `DELIVERED`, `READ` |
| 랜덤통화 대기 상태 | `WAITING`, `MATCHED`, `CANCELED`, `EXPIRED` |
| 통화 세션 상태 | `CONNECTING`, `ACTIVE`, `ENDED`, `FAILED`, `CANCELED` |
| 스토어 | `APPLE_APP_STORE`, `GOOGLE_PLAY` |
| 결제 상태 | `PENDING`, `APPROVED`, `FAILED`, `CANCELED`, `REFUNDED` |
| 코인 원장 사유 | `CHARGE`, `USE`, `REFUND`, `ADJUST`, `RESERVE`, `RELEASE` |
| 환불 상태 | `REQUESTED`, `IN_REVIEW`, `APPROVED`, `REJECTED`, `CANCELED` |
| 커뮤니티 콘텐츠 상태 | `ACTIVE`, `HIDDEN`, `DELETED` |
| 신고 상태 | `RECEIVED`, `IN_REVIEW`, `RESOLVED`, `REJECTED` |
| 관리자 상태 | `ACTIVE`, `SUSPENDED` |
| 제재 타입 | `WARN`, `SUSPEND`, `BAN` |
| 운영 케이스 상태 | `OPEN`, `IN_REVIEW`, `RESOLVED` |

## 3. 인증 API

### 3.1 소셜 로그인

```http
POST /api/v1/auth/social/login
```

소셜 제공자의 토큰 또는 인가 코드를 검증하고 REALMATCH+ 토큰을 발급합니다.

Request Body:

```json
{
  "providerType": "KAKAO",
  "providerToken": "social-access-token",
  "authorizationCode": "authorization-code",
  "deviceId": "ios-device-001",
  "deviceOs": "IOS",
  "appVersion": "1.0.0",
  "pushToken": "fcm-or-apns-token"
}
```

Response `200 OK`:

```json
{
  "accessToken": "jwt-access-token",
  "refreshToken": "jwt-refresh-token",
  "userId": 1,
  "profileCompleted": false
}
```

구현 메모:

- `providerToken`과 `authorizationCode` 중 실제 앱 플로우에 맞는 필드를 사용합니다.
- 소셜 계정이 없으면 신규 회원을 생성합니다.
- 디바이스 정보와 푸시 토큰을 저장 또는 갱신합니다.
- 프로필 필수 항목 완료 여부를 `profileCompleted`로 내려줍니다.

### 3.2 토큰 재발급

```http
POST /api/v1/auth/token/refresh
```

Refresh Token을 검증하고 Access Token과 Refresh Token을 재발급합니다.

Request Body:

```json
{
  "refreshToken": "jwt-refresh-token",
  "deviceId": "ios-device-001"
}
```

Response `200 OK`:

```json
{
  "accessToken": "new-jwt-access-token",
  "refreshToken": "new-jwt-refresh-token",
  "userId": 1,
  "profileCompleted": true
}
```

구현 메모:

- 저장된 refresh token hash와 만료 여부를 확인합니다.
- 재발급 시 기존 refresh token은 폐기하고 새 토큰을 저장하는 방식을 권장합니다.

### 3.3 로그아웃

```http
POST /api/v1/auth/logout
```

현재 기기의 Refresh Token을 폐기합니다.

Headers:

```http
Authorization: Bearer {accessToken}
```

Request Body:

```json
{
  "refreshToken": "jwt-refresh-token",
  "deviceId": "ios-device-001"
}
```

Response:

```text
204 No Content
```

### 3.4 소셜 계정 연동

```http
POST /api/v1/auth/providers/link
```

로그인한 사용자에게 추가 소셜 계정을 연결합니다.

Headers:

```http
Authorization: Bearer {accessToken}
```

Request Body:

```json
{
  "providerType": "APPLE",
  "providerToken": "social-access-token",
  "authorizationCode": "authorization-code"
}
```

Response:

```text
204 No Content
```

## 4. 회원 API

### 4.1 내 정보 조회

```http
GET /api/v1/users/me
```

Response `200 OK`:

```json
{
  "userId": 1,
  "nickname": "realmatch",
  "email": "user@example.com",
  "status": "ACTIVE",
  "profileCompleted": true
}
```

### 4.2 기본 정보 수정

```http
PATCH /api/v1/users/me
```

Request Body:

```json
{
  "nickname": "realmatch",
  "phoneNumber": "01012345678",
  "gender": "FEMALE",
  "birthYear": 1998
}
```

Response `200 OK`:

```json
{
  "userId": 1,
  "nickname": "realmatch",
  "email": "user@example.com",
  "status": "ACTIVE",
  "profileCompleted": true
}
```

구현 메모:

- `birthYear`는 `1900`부터 `2100` 사이만 허용합니다.
- 닉네임 중복 정책과 금칙어 정책은 구현 시 확정합니다.

### 4.3 프로필 조회

```http
GET /api/v1/users/me/profile
```

Response `200 OK`:

```json
{
  "userId": 1,
  "heightCm": 165,
  "jobTitle": "Product Designer",
  "educationLevel": "UNIVERSITY",
  "mbti": "ENFP",
  "introduction": "안녕하세요.",
  "regionCode": "SEOUL"
}
```

### 4.4 프로필 수정

```http
PATCH /api/v1/users/me/profile
```

Request Body:

```json
{
  "heightCm": 165,
  "jobTitle": "Product Designer",
  "educationLevel": "UNIVERSITY",
  "mbti": "ENFP",
  "introduction": "안녕하세요.",
  "regionCode": "SEOUL"
}
```

Response `200 OK`:

```json
{
  "userId": 1,
  "heightCm": 165,
  "jobTitle": "Product Designer",
  "educationLevel": "UNIVERSITY",
  "mbti": "ENFP",
  "introduction": "안녕하세요.",
  "regionCode": "SEOUL"
}
```

구현 메모:

- `heightCm`는 `100`부터 `250` 사이만 허용합니다.
- 프로필 이미지 API는 현재 스캐폴딩에 포함되어 있지 않으므로 추후 별도 정의가 필요합니다.

### 4.5 선호 조건 조회

```http
GET /api/v1/users/me/preferences
```

Response `200 OK`:

```json
{
  "userId": 1,
  "preferredGender": "ANY",
  "preferredAgeMin": 25,
  "preferredAgeMax": 35,
  "preferredRegionCode": "SEOUL",
  "allowRandomCall": true
}
```

### 4.6 선호 조건 수정

```http
PATCH /api/v1/users/me/preferences
```

Request Body:

```json
{
  "preferredGender": "ANY",
  "preferredAgeMin": 25,
  "preferredAgeMax": 35,
  "preferredRegionCode": "SEOUL",
  "allowRandomCall": true
}
```

Response `200 OK`:

```json
{
  "userId": 1,
  "preferredGender": "ANY",
  "preferredAgeMin": 25,
  "preferredAgeMax": 35,
  "preferredRegionCode": "SEOUL",
  "allowRandomCall": true
}
```

## 5. 추천/매칭 API

### 5.1 추천 목록 조회

```http
GET /api/v1/recommendations?size=20
```

Response `200 OK`:

```json
{
  "candidateUserIds": [2, 3, 4]
}
```

구현 메모:

- 실제 프론트 구현을 위해서는 후보 사용자 ID만이 아니라 카드 렌더링에 필요한 프로필 요약 응답으로 확장하는 것을 권장합니다.
- 이미 노출된 사용자, 차단된 사용자, 탈퇴/정지 사용자는 제외합니다.
- 조회 시 `recommendation_exposure_history`에 노출 이력을 기록합니다.

### 5.2 좋아요

```http
POST /api/v1/recommendations/{targetUserId}/like
```

Response `200 OK`:

```json
{
  "matchId": 10,
  "user1Id": 1,
  "user2Id": 2,
  "status": "ACTIVE",
  "created": true
}
```

구현 메모:

- 상대방도 나를 좋아요한 상태라면 매칭을 생성합니다.
- 이미 반응한 대상이면 `409 Conflict` 또는 기존 결과 반환 정책을 확정해야 합니다.

### 5.3 패스

```http
POST /api/v1/recommendations/{targetUserId}/pass
```

Response `200 OK`:

```json
{
  "matchId": null,
  "user1Id": 1,
  "user2Id": 2,
  "status": "PASS",
  "created": false
}
```

### 5.4 매칭 목록 조회

```http
GET /api/v1/matches
```

Response `200 OK`:

```json
[
  {
    "matchId": 10,
    "user1Id": 1,
    "user2Id": 2,
    "status": "ACTIVE",
    "created": false
  }
]
```

### 5.5 매칭 상세 조회

```http
GET /api/v1/matches/{matchId}
```

Response `200 OK`:

```json
{
  "matchId": 10,
  "user1Id": 1,
  "user2Id": 2,
  "status": "ACTIVE",
  "created": false
}
```

### 5.6 사용자 차단

```http
POST /api/v1/users/{targetUserId}/block?reason=불쾌한 대화
```

Response:

```text
204 No Content
```

구현 메모:

- 차단 후 활성 매칭과 채팅방 상태를 `BLOCKED` 또는 `CLOSED`로 전환하는 정책을 확정합니다.

## 6. 채팅 API

### 6.1 채팅방 목록 조회

```http
GET /api/v1/chat/rooms
```

Response `200 OK`:

```json
[
  {
    "roomId": 1,
    "matchId": 10,
    "status": "ACTIVE",
    "unreadCount": 2
  }
]
```

### 6.2 메시지 목록 조회

```http
GET /api/v1/chat/rooms/{roomId}/messages
```

Response `200 OK`:

```json
[
  {
    "messageId": 100,
    "roomId": 1,
    "senderId": 2,
    "messageType": "TEXT",
    "content": "안녕하세요.",
    "status": "SENT"
  }
]
```

구현 메모:

- 현재는 커서 파라미터가 없으므로 구현 시 `cursor`, `size` 추가를 권장합니다.

### 6.3 메시지 전송

```http
POST /api/v1/chat/rooms/{roomId}/messages
```

Request Body:

```json
{
  "messageType": "TEXT",
  "content": "안녕하세요."
}
```

Response `200 OK`:

```json
{
  "messageId": 101,
  "roomId": 1,
  "senderId": 1,
  "messageType": "TEXT",
  "content": "안녕하세요.",
  "status": "SENT"
}
```

구현 메모:

- 사용자가 채팅방 멤버인지 확인합니다.
- WebSocket 또는 Redis Pub/Sub 실시간 발행은 `ChatRealtimePort`를 통해 처리합니다.

### 6.4 메시지 읽음 처리

```http
POST /api/v1/chat/rooms/{roomId}/read?lastReadMessageId=101
```

Response:

```text
204 No Content
```

### 6.5 채팅방 종료

```http
POST /api/v1/chat/rooms/{roomId}/close
```

Response:

```text
204 No Content
```

## 7. 랜덤통화 API

### 7.1 랜덤통화 대기열 진입

```http
POST /api/v1/calls/random/enter
```

Request Body:

```json
{
  "queueType": "BASIC",
  "regionCode": "SEOUL",
  "preferredGender": "ANY"
}
```

Response `200 OK`:

```json
{
  "userId": 1,
  "status": "WAITING",
  "callId": null,
  "queueType": "BASIC"
}
```

구현 메모:

- 코인 차감 여부, 최소 잔액, 대기열 중복 진입을 검증합니다.
- 매칭 성공 시 `callId`를 포함해 반환합니다.
- Redis 대기열 처리는 `RandomCallQueuePort`를 통해 처리합니다.

### 7.2 랜덤통화 대기 취소

```http
POST /api/v1/calls/random/cancel
```

Response:

```text
204 No Content
```

### 7.3 랜덤통화 상태 조회

```http
GET /api/v1/calls/random/status
```

Response `200 OK`:

```json
{
  "userId": 1,
  "status": "CALL_WAITING",
  "callId": null,
  "queueType": "BASIC"
}
```

### 7.4 통화 세션 상세 조회

```http
GET /api/v1/calls/{callId}
```

Response `200 OK`:

```json
{
  "callId": 100,
  "callerId": 1,
  "calleeId": 2,
  "status": "ACTIVE",
  "failureReason": null
}
```

### 7.5 통화 종료

```http
POST /api/v1/calls/{callId}/end?reason=USER_EXIT
```

Response:

```text
204 No Content
```

구현 메모:

- 통화 참여자만 종료할 수 있습니다.
- 종료 사유와 세션 이벤트를 기록합니다.
- 예약 차감된 코인 정산 또는 환불 정책을 반영합니다.

## 8. 결제/코인 API

### 8.1 코인 상품 목록 조회

```http
GET /api/v1/payments/products
```

Response `200 OK`:

```json
[
  {
    "productId": 1,
    "productCode": "COIN_100",
    "coinAmount": 100,
    "bonusCoinAmount": 10,
    "priceAmount": "1200.00",
    "currency": "KRW"
  }
]
```

### 8.2 결제 검증

```http
POST /api/v1/payments/verify
```

Request Body:

```json
{
  "productId": 1,
  "store": "APPLE_APP_STORE",
  "purchaseToken": "store-purchase-token",
  "receiptPayload": "raw-receipt-payload"
}
```

Response `200 OK`:

```json
{
  "userId": 1,
  "balance": 110
}
```

구현 메모:

- Apple App Store 또는 Google Play 영수증 검증은 `StoreReceiptPort`를 통해 처리합니다.
- 동일 구매 토큰 중복 검증을 방지합니다.
- 결제 승인 후 `payment_orders`, `coin_wallets`, `coin_ledger`를 함께 갱신합니다.

### 8.3 코인 지갑 조회

```http
GET /api/v1/coins/wallet
```

Response `200 OK`:

```json
{
  "userId": 1,
  "balance": 110
}
```

### 8.4 코인 원장 조회

```http
GET /api/v1/coins/ledger
```

Response `200 OK`:

```json
[
  {
    "ledgerId": 1,
    "delta": 110,
    "balanceAfter": 110,
    "reason": "CHARGE",
    "refType": "PAYMENT",
    "refId": 1
  }
]
```

구현 메모:

- 구현 시 `cursor`, `size` 파라미터 추가를 권장합니다.

### 8.5 코인 사용

```http
POST /api/v1/coins/use
```

Request Body:

```json
{
  "featureType": "RANDOM_CALL",
  "amount": 10,
  "refType": "CALL",
  "refId": 100,
  "idempotencyKey": "coin-use-uuid"
}
```

Response `200 OK`:

```json
{
  "userId": 1,
  "balance": 100
}
```

구현 메모:

- 잔액 부족 시 `422 Unprocessable Entity`를 반환합니다.
- `idempotencyKey` 기준으로 중복 차감을 방지합니다.

### 8.6 환불 요청

```http
POST /api/v1/payments/refunds?paymentId=1&reason=구매 취소 요청
```

Response:

```text
204 No Content
```

구현 메모:

- 환불 가능 상태와 스토어 정책을 검증합니다.
- 실제 환불 승인 처리는 운영자 검토 또는 스토어 콜백 정책에 맞춰 확정합니다.

## 9. 커뮤니티 API

### 9.1 게시글 목록 조회

```http
GET /api/v1/community/posts?categoryId=1&sort=LATEST&size=20
```

Response `200 OK`:

```json
[
  {
    "postId": 1,
    "authorId": 1,
    "title": "첫 게시글",
    "body": "내용입니다.",
    "status": "ACTIVE",
    "likeCount": 0,
    "commentCount": 0
  }
]
```

구현 메모:

- `sort` 후보값은 `LATEST`, `POPULAR` 정도로 시작하고 구현 시 확정합니다.
- 숨김/삭제된 게시글은 기본 목록에서 제외합니다.

### 9.2 게시글 상세 조회

```http
GET /api/v1/community/posts/{postId}
```

Response `200 OK`:

```json
{
  "postId": 1,
  "authorId": 1,
  "title": "첫 게시글",
  "body": "내용입니다.",
  "status": "ACTIVE",
  "likeCount": 0,
  "commentCount": 0
}
```

구현 메모:

- 현재 `PostResult`에는 댓글 목록이 포함되어 있지 않습니다.
- 댓글 조회 API를 별도로 둘지, 상세 응답에 포함할지 구현 전에 확정해야 합니다.

### 9.3 게시글 작성

```http
POST /api/v1/community/posts
```

Request Body:

```json
{
  "categoryId": 1,
  "title": "첫 게시글",
  "body": "내용입니다."
}
```

Response `200 OK`:

```json
{
  "postId": 1,
  "authorId": 1,
  "title": "첫 게시글",
  "body": "내용입니다.",
  "status": "ACTIVE",
  "likeCount": 0,
  "commentCount": 0
}
```

### 9.4 댓글 작성

```http
POST /api/v1/community/posts/{postId}/comments
```

Request Body:

```json
{
  "parentCommentId": null,
  "body": "댓글입니다."
}
```

Response:

```text
204 No Content
```

구현 메모:

- 현재 반환값이 없으므로 프론트 즉시 반영을 위해 댓글 응답 DTO 추가를 고려합니다.
- 대댓글은 `parentCommentId`로 표현합니다.

### 9.5 게시글 좋아요

```http
POST /api/v1/community/posts/{postId}/like
```

Response:

```text
204 No Content
```

구현 메모:

- 중복 좋아요 요청은 멱등하게 처리하는 것을 권장합니다.

### 9.6 커뮤니티 신고

```http
POST /api/v1/community/reports
```

Request Body:

```json
{
  "targetType": "POST",
  "targetId": 1,
  "reason": "부적절한 내용"
}
```

Response:

```text
204 No Content
```

## 10. 운영/관리자 API

관리자 API는 반드시 관리자 인증과 RBAC를 적용합니다.

현재 스캐폴딩에서는 임시 헤더를 사용합니다.

```http
X-ADMIN-ID: {adminId}
```

최종 구현에서는 `Authorization: Bearer {adminAccessToken}` 기반으로 변경합니다.

### 10.1 관리자 목록 조회

```http
GET /api/v1/admin/users
```

Response `200 OK`:

```json
[
  {
    "adminId": 1,
    "loginId": "admin",
    "role": "SUPER_ADMIN",
    "status": "ACTIVE"
  }
]
```

### 10.2 운영 케이스 목록 조회

```http
GET /api/v1/admin/moderation/cases?status=OPEN
```

Response `200 OK`:

```json
[
  {
    "caseId": 1,
    "caseType": "USER_REPORT",
    "targetUserId": 2,
    "status": "OPEN",
    "assignedAdminId": null
  }
]
```

### 10.3 사용자 제재

```http
POST /api/v1/admin/users/{userId}/sanctions
```

Request Body:

```json
{
  "sanctionType": "WARN",
  "reason": "신고 누적"
}
```

Response:

```text
204 No Content
```

구현 메모:

- `sanctionType`은 `WARN`, `SUSPEND`, `BAN` 중 하나입니다.
- 제재 수행 시 관리자 액션 로그를 반드시 기록합니다.

### 10.4 운영 케이스 처리

```http
POST /api/v1/admin/moderation/cases/{caseId}/resolve
```

Request Body:

```json
{
  "actionType": "HIDE",
  "reason": "운영 정책 위반"
}
```

Response:

```text
204 No Content
```

### 10.5 관리자 액션 로그 조회

```http
GET /api/v1/admin/action-logs
```

Response `200 OK`:

```json
[
  "2026-06-05T12:30:00 admin=1 action=WARN targetUser=2"
]
```

구현 메모:

- 현재 `AdminUseCase`는 문자열 목록을 반환하지만, 실제 구현 시 구조화된 로그 DTO로 변경하는 것을 권장합니다.

### 10.6 대시보드 지표 조회

```http
GET /api/v1/admin/dashboard/metrics
```

Response `200 OK`:

```json
[
  {
    "metricCode": "DAILY_ACTIVE_USERS",
    "metricValue": "120"
  }
]
```

## 11. 프론트엔드 연동 우선순위

초기 개발에서는 아래 순서로 API를 구현하면 화면 개발과 백엔드 개발 흐름이 맞기 쉽습니다.

1. 인증: 소셜 로그인, 토큰 재발급, 로그아웃
2. 회원: 내 정보, 프로필, 선호 조건
3. 추천/매칭: 추천 조회, 좋아요/패스, 매칭 조회
4. 채팅: 채팅방 목록, 메시지 목록, 메시지 전송
5. 결제/코인: 상품 조회, 결제 검증, 지갑 조회, 코인 사용
6. 랜덤통화: 대기열 진입, 상태 조회, 종료
7. 커뮤니티: 게시글 목록/상세/작성, 댓글, 좋아요, 신고
8. 운영/관리자: 신고/제재/대시보드

## 12. 구현 전에 확정할 항목

아래 항목은 현재 스캐폴딩과 DB 스키마만으로는 완전히 확정하기 어렵기 때문에 실제 개발 전에 정해야 합니다.

- JWT claim 구조와 Access Token/Refresh Token 만료 시간
- 소셜 로그인 제공자별 검증 방식
- 프로필 필수 입력 항목과 `profileCompleted` 계산 기준
- 추천 응답에 포함할 사용자 카드 정보 범위
- 매칭 생성 후 채팅방 자동 생성 여부
- 채팅 메시지 페이지네이션 방식
- WebSocket 엔드포인트와 이벤트 메시지 형식
- 랜덤통화 코인 차감 정책과 실패/취소 시 환불 정책
- 결제 영수증 검증 실패/중복 검증 처리 정책
- 커뮤니티 댓글 조회 방식
- 관리자 권한 Role 종류와 접근 가능한 API 범위
- 공통 에러 코드 체계와 필드 검증 오류 응답 형식
