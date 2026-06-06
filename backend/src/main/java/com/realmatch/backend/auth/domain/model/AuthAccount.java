package com.realmatch.backend.auth.domain.model;

import lombok.Getter;

import java.time.OffsetDateTime;

/**
 * 소셜 로그인 제공자와 내부 사용자 계정 연결을 표현하는 도메인 모델입니다.
 *
 * <p>TODO(PDF 설계서 기준): - Apple, Google, Kakao, Naver provider_user_id를 기준으로 동일 계정을 식별합니다. - Refresh
 * Token은 원문 저장 없이 hash 저장, 만료, 폐기 정책을 적용합니다. - 다른 제공자 이메일이 같아도 자동 병합하지 않고 명시적 계정 연동만 허용합니다.
 */
@Getter
public class AuthAccount {

  // TODO: 외부 기술 의존성 없이 상태와 도메인 규칙을 구현합니다.
    private final Long providerId;
    private final Long userId;
    private final String providerType;
    private final String providerUserId;
    private final String providerEmail;
    private final OffsetDateTime connectedAt;
    private final OffsetDateTime lastAuthenticatedAt;

    public AuthAccount(Long providerId, Long userId, String providerType, String providerUserId, String providerEmail, OffsetDateTime connectedAt, OffsetDateTime lastAuthenticatedAt) {
        this.providerId = providerId;
        this.userId = userId;
        this.providerType = providerType;
        this.providerUserId = providerUserId;
        this.providerEmail = providerEmail;
        this.connectedAt = connectedAt;
        this.lastAuthenticatedAt = lastAuthenticatedAt;
    }

    public AuthAccount authenticated(OffsetDateTime time) {
        return new AuthAccount(
                providerId,
                userId,
                providerType,
                providerUserId,
                providerEmail,
                connectedAt,
                time
        );
    }

    public static AuthAccount create(Long userId, String providerType, String providerUserId, String providerEmail, OffsetDateTime time) {
        return new AuthAccount(
                null,
                userId,
                providerType,
                providerUserId,
                providerEmail,
                time,
                time);
    }
}
