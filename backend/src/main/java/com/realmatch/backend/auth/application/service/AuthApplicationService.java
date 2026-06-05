package com.realmatch.backend.auth.application.service;

import com.realmatch.backend.auth.application.port.in.AuthUseCase;
import com.realmatch.backend.auth.application.port.out.AuthPersistencePort;
import com.realmatch.backend.auth.application.port.out.SocialTokenPort;
import com.realmatch.backend.auth.application.port.out.TokenIssuePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 인증 유스케이스 구현체입니다. TODO: 소셜 토큰 검증, 계정 조회/생성, 디바이스 갱신, Refresh Token 저장, JWT 발급을 조율합니다. */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthApplicationService implements AuthUseCase {

  private final AuthPersistencePort authPersistencePort;
  private final SocialTokenPort socialTokenPort;
  private final TokenIssuePort tokenIssuePort;

  @Override
  @Transactional
  public TokenResult socialLogin(SocialLoginCommand command) {
    throw new UnsupportedOperationException("TODO: 소셜 로그인 흐름을 구현합니다.");
  }

  @Override
  @Transactional
  public TokenResult reissueToken(ReissueTokenCommand command) {
    throw new UnsupportedOperationException("TODO: Refresh Token 검증 후 토큰 재발급을 구현합니다.");
  }

  @Override
  @Transactional
  public void logout(LogoutCommand command) {
    throw new UnsupportedOperationException("TODO: Refresh Token 폐기와 세션 정리를 구현합니다.");
  }

  @Override
  @Transactional
  public void linkProvider(LinkProviderCommand command) {
    throw new UnsupportedOperationException("TODO: 명시적 소셜 계정 연동을 구현합니다.");
  }
}
