package com.realmatch.backend.auth.adapter.out.persistence;

import com.realmatch.backend.auth.application.port.out.AuthPersistencePort;
import com.realmatch.backend.auth.domain.model.AuthAccount;
import com.realmatch.backend.auth.domain.model.RefreshTokenSession;
import java.util.Optional;

import com.realmatch.backend.user.adapter.out.persistence.UserJpaEntity;
import com.realmatch.backend.user.adapter.out.persistence.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Component;

/** 인증 Persistence Adapter입니다. TODO: JPA Repository를 사용해 AuthPersistencePort를 구현합니다. */
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
    throw new UnsupportedOperationException("TODO: Refresh Token hash로 세션을 조회합니다.");
  }

  @Override
  public RefreshTokenSession saveRefreshTokenSession(RefreshTokenSession session) {
    throw new UnsupportedOperationException("TODO: Refresh Token 세션을 저장합니다.");
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
}
