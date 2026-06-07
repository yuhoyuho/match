package com.realmatch.backend.auth.domain.model;

import lombok.Getter;

import java.time.OffsetDateTime;

/**
 * Token : 원문 저장 x (Hash값 저장)
 */
@Getter
public class RefreshTokenSession {

  // TODO: 외부 기술 의존성 없이 상태와 도메인 규칙을 구현합니다.

    private final Long refreshTokenId;
    private final Long userId;
    private final String tokenHash;
    private final String deviceId;
    private final OffsetDateTime expiresAt;
    private final OffsetDateTime revokedAt;
    private final OffsetDateTime createdAt;

    public RefreshTokenSession(Long refreshTokenId, Long userId, String tokenHash, String deviceId, OffsetDateTime expiresAt, OffsetDateTime revokedAt, OffsetDateTime createdAt) {
        this.refreshTokenId = refreshTokenId;
        this.userId = userId;
        this.tokenHash = tokenHash;
        this.deviceId = deviceId;
        this.expiresAt = expiresAt;
        this.revokedAt = revokedAt;
        this.createdAt = createdAt;
    }

    public static RefreshTokenSession create(Long userId, String tokenHash, String deviceId, OffsetDateTime expiresAt, OffsetDateTime createdAt) {
        return new RefreshTokenSession(null, userId, tokenHash, deviceId, expiresAt, null, createdAt);
    }
}
