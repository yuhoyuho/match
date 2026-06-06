package com.realmatch.backend.auth.application.port.out;

import com.realmatch.backend.auth.application.port.in.AuthUseCase;
import com.realmatch.backend.auth.domain.model.AuthAccount;
import com.realmatch.backend.auth.domain.model.RefreshTokenSession;
import java.util.Optional;

/**
 * 인증 영속성 출력 포트
 */
public interface AuthPersistencePort {

  /**
   * providerType, userId로 사용자 조회
   */
  Optional<AuthAccount> findAuthAccount(String providerType, String providerUserId);

  /**
   * 사용자 저장
   */
  AuthAccount saveAuthAccount(AuthAccount authAccount);

  /**
   * refresh토큰 세션 조회
   */
  Optional<RefreshTokenSession> findRefreshTokenSession(String tokenHash);

  /**
   * refresh토큰 세션 저장
   */
  RefreshTokenSession saveRefreshTokenSession(RefreshTokenSession session);

  Long createUser(String email, String nickname);
}
