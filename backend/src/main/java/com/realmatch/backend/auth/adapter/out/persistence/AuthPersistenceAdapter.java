package com.realmatch.backend.auth.adapter.out.persistence;

import com.realmatch.backend.auth.application.port.out.AuthPersistencePort;
import com.realmatch.backend.auth.domain.model.AuthAccount;
import com.realmatch.backend.auth.domain.model.RefreshTokenSession;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** 인증 Persistence Adapter입니다. TODO: JPA Repository를 사용해 AuthPersistencePort를 구현합니다. */
@Component
@RequiredArgsConstructor
public class AuthPersistenceAdapter implements AuthPersistencePort {

  private final AuthProviderJpaRepository authProviderJpaRepository;
  private final RefreshTokenJpaRepository refreshTokenJpaRepository;

  @Override
  public Optional<AuthAccount> findAuthAccount(String providerType, String providerUserId) {
    throw new UnsupportedOperationException("TODO: provider_type + provider_user_id로 계정을 조회합니다.");
  }

  @Override
  public AuthAccount saveAuthAccount(AuthAccount authAccount) {
    throw new UnsupportedOperationException("TODO: 소셜 계정 연결 정보를 저장합니다.");
  }

  @Override
  public Optional<RefreshTokenSession> findRefreshTokenSession(String tokenHash) {
    throw new UnsupportedOperationException("TODO: Refresh Token hash로 세션을 조회합니다.");
  }

  @Override
  public RefreshTokenSession saveRefreshTokenSession(RefreshTokenSession session) {
    throw new UnsupportedOperationException("TODO: Refresh Token 세션을 저장합니다.");
  }
}
