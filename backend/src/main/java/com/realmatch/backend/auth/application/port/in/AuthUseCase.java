package com.realmatch.backend.auth.application.port.in;

/**
 * 인증 도메인 입력 포트
 *
 * 소셜 로그인, 토큰 재발급, 로그아웃, 계정 연동 유스케이스를 구현
 */
public interface AuthUseCase {

  TokenResult oauth2Login(OAuth2LoginCommand command);

  TokenResult reissueToken(ReissueTokenCommand command);

  void logout(LogoutCommand command);

  void linkProvider(LinkProviderCommand command);

  record OAuth2LoginCommand(
      String providerType,
      String providerUserId,
      String email,
      String nickname,
      String profileImageUrl) {}

  record ReissueTokenCommand(String refreshToken) {}

  record LogoutCommand(Long userId, String refreshToken) {}

  record LinkProviderCommand(Long userId, String providerType) {}

  record TokenResult(
      String accessToken, String refreshToken, Long userId, boolean profileCompleted) {}
}
