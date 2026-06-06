package com.realmatch.backend.auth.adapter.out.persistence;

import com.realmatch.backend.auth.domain.model.AuthAccount;
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
 * user_auth_providers 테이블과 매핑되는 JPA 엔티티입니다.
 *
 * TODO: Flyway 스키마 컬럼을 기준으로 필요한 필드를 추가하고 도메인 모델과의 매핑을 PersistenceAdapter에서 처리합니다.
 */
@Getter
@Entity
@Table(name = "user_auth_providers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthProviderJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "provider_id", nullable = false)
  private Long providerId; // 소셜 계정 연결 pk

  @Column(name = "user_id", nullable = false)
  private Long userId; // user 테이블의 pk

  @Column(name = "provider_type", nullable = false)
  private String providerType;

  @Column(name = "provider_user_id", nullable = false)
  private String providerUserId;

  @Column(name = "provider_email")
  private String providerEmail;

  @Column(name = "connected_at")
  private OffsetDateTime connectedAt;

  @Column(name = "last_authenticated_at")
  private OffsetDateTime lastAuthenticatedAt;

  public AuthProviderJpaEntity(
          Long userId,
          String providerUserId,
          String providerType,
          String providerEmail
  ) {
    this.userId = userId;
    this.providerUserId = providerUserId;
    this.providerType = providerType;
    this.providerEmail = providerEmail;
    this.connectedAt = OffsetDateTime.now();
    this.lastAuthenticatedAt = OffsetDateTime.now();
  }

    public static AuthProviderJpaEntity from(AuthAccount authAccount) {
      AuthProviderJpaEntity entity = new AuthProviderJpaEntity();
      entity.providerId = authAccount.getProviderId();
      entity.userId = authAccount.getUserId();
      entity.providerType = authAccount.getProviderType();
      entity.providerUserId = authAccount.getProviderUserId();
      entity.providerEmail = authAccount.getProviderEmail();
      entity.connectedAt = authAccount.getConnectedAt();
      entity.lastAuthenticatedAt = authAccount.getLastAuthenticatedAt();

      return entity;
    }
}
