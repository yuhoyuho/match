package com.realmatch.backend.auth.adapter.out.persistence;

import com.realmatch.backend.auth.domain.model.RefreshTokenSession;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * refresh_tokens 테이블과 매핑되는 JPA 엔티티입니다.
 *
 * <p>TODO: Flyway 스키마 컬럼을 기준으로 필요한 필드를 추가하고 도메인 모델과의 매핑을 PersistenceAdapter에서 처리합니다.
 */
@Getter
@Entity
@Table(name = "refresh_tokens")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshTokenJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "refresh_token_id")
  private Long refreshTokenId;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "token_hash", nullable = false)
  private String tokenHash;

  @Column(name = "device_id")
  private String deviceId;

  @Column(name = "expires_at", nullable = false)
  private OffsetDateTime expiresAt;

  @Column(name = "revoked_at")
  private OffsetDateTime revokedAt;

  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

  public static RefreshTokenJpaEntity from(RefreshTokenSession session) {
    RefreshTokenJpaEntity entity = new RefreshTokenJpaEntity();

    entity.refreshTokenId = session.getRefreshTokenId();
    entity.userId = session.getUserId();
    entity.tokenHash = session.getTokenHash();
    entity.deviceId = session.getDeviceId();
    entity.expiresAt = session.getExpiresAt();
    entity.revokedAt = session.getRevokedAt();
    entity.createdAt = session.getCreatedAt();

    return entity;
  }
}
