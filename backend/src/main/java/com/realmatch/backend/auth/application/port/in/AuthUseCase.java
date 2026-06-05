package com.realmatch.backend.auth.application.port.in;

/**
 * 인증 도메인 입력 포트입니다.
 *
 * <p>TODO(PDF 설계서 기준): 소셜 로그인, 토큰 재발급, 로그아웃, 계정 연동 유스케이스를 구현합니다.
 */
public interface AuthUseCase {

  TokenResult socialLogin(SocialLoginCommand command);

  TokenResult reissueToken(ReissueTokenCommand command);

  void logout(LogoutCommand command);

  void linkProvider(LinkProviderCommand command);

  record SocialLoginCommand(
      String providerType,
      String providerToken,
      String authorizationCode,
      String deviceId,
      String deviceOs,
      String appVersion,
      String pushToken) {}

  record ReissueTokenCommand(String refreshToken, String deviceId) {}

  record LogoutCommand(Long userId, String refreshToken, String deviceId) {}

  record LinkProviderCommand(
      Long userId, String providerType, String providerToken, String authorizationCode) {}

  record TokenResult(
      String accessToken, String refreshToken, Long userId, boolean profileCompleted) {}
}
