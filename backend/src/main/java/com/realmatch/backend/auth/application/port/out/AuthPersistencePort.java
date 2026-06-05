package com.realmatch.backend.auth.application.port.out;

import com.realmatch.backend.auth.domain.model.AuthAccount;
import com.realmatch.backend.auth.domain.model.RefreshTokenSession;
import java.util.Optional;

/**
 * 인증 영속성 출력 포트입니다. TODO: users, user_auth_providers, refresh_tokens, user_devices 저장/조회 기능을 구현합니다.
 */
public interface AuthPersistencePort {

  Optional<AuthAccount> findAuthAccount(String providerType, String providerUserId);

  AuthAccount saveAuthAccount(AuthAccount authAccount);

  Optional<RefreshTokenSession> findRefreshTokenSession(String tokenHash);

  RefreshTokenSession saveRefreshTokenSession(RefreshTokenSession session);
}
