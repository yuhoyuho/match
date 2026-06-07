package com.realmatch.backend.auth.adapter.out.persistence;

import com.realmatch.backend.auth.application.port.out.AuthPersistencePort;
import com.realmatch.backend.auth.domain.model.AuthAccount;
import com.realmatch.backend.auth.domain.model.RefreshTokenSession;
import com.realmatch.backend.user.adapter.out.persistence.UserJpaEntity;
import com.realmatch.backend.user.adapter.out.persistence.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/** 인증 Persistence Adapter */
@Component
@RequiredArgsConstructor
public class AuthPersistenceAdapter implements AuthPersistencePort {

  private final AuthProviderJpaRepository authProviderJpaRepository;
  private final RefreshTokenJpaRepository refreshTokenJpaRepository;
  private final UserJpaRepository userJpaRepository;

  @Override
  public Optional<AuthAccount> findAuthAccount(String providerType, String providerUserId) {
    return authProviderJpaRepository.findByProviderTypeAndProviderUserId(providerType, providerUserId)
            .map(this::toDomain);
  }

  @Override
  public AuthAccount saveAuthAccount(AuthAccount authAccount) {
    // DB 접근용 JPA 엔티티 생성 후 소셜 Account 저장
    AuthProviderJpaEntity entity = AuthProviderJpaEntity.from(authAccount);
    AuthProviderJpaEntity savedEntity = authProviderJpaRepository.save(entity);

    return toDomain(savedEntity);
  }

  @Override
  public Optional<RefreshTokenSession> findRefreshTokenSession(String tokenHash) {
    return refreshTokenJpaRepository.findByTokenHash(tokenHash)
            .map(this::toDomain);
  }

  @Override
  public RefreshTokenSession saveRefreshTokenSession(RefreshTokenSession session) {
    RefreshTokenJpaEntity entity = RefreshTokenJpaEntity.from(session);
    RefreshTokenJpaEntity savedEntity = refreshTokenJpaRepository.save(entity);

    return toDomain(savedEntity);
  }

  @Override
  public Long createUser(String email, String nickname) {

    UserJpaEntity user = UserJpaEntity.createForOAuth(email, nickname);
    return userJpaRepository.save(user).getUserId();
  }


  private AuthAccount toDomain(AuthProviderJpaEntity entity) {
    return new AuthAccount(
            entity.getProviderId(),
            entity.getUserId(),
            entity.getProviderType(),
            entity.getProviderUserId(),
            entity.getProviderEmail(),
            entity.getConnectedAt(),
            entity.getLastAuthenticatedAt()
    );
  }

  private RefreshTokenSession toDomain(RefreshTokenJpaEntity entity) {
    return new RefreshTokenSession(
            entity.getRefreshTokenId(),
            entity.getUserId(),
            entity.getTokenHash(),
            entity.getDeviceId(),
            entity.getExpiresAt(),
            entity.getRevokedAt(),
            entity.getCreatedAt()
    );
  }
}
