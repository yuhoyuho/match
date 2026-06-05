package com.realmatch.backend.auth.adapter.in.web;

import com.realmatch.backend.auth.application.port.in.AuthUseCase;
import com.realmatch.backend.auth.application.port.in.AuthUseCase.LinkProviderCommand;
import com.realmatch.backend.auth.application.port.in.AuthUseCase.LogoutCommand;
import com.realmatch.backend.auth.application.port.in.AuthUseCase.ReissueTokenCommand;
import com.realmatch.backend.auth.application.port.in.AuthUseCase.SocialLoginCommand;
import com.realmatch.backend.auth.application.port.in.AuthUseCase.TokenResult;
import com.realmatch.backend.common.Routes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/** 인증 Web Adapter입니다. TODO: 요청 검증, 인증 사용자 추출, Command 변환, TokenResult 응답 변환을 구현합니다. */
@RestController
@RequiredArgsConstructor
public class AuthController {

  private final AuthUseCase authUseCase;

  @PostMapping(Routes.AUTH_SOCIAL_LOGIN)
  public TokenResult socialLogin(@RequestBody SocialLoginRequest request) {
    return authUseCase.socialLogin(request.toCommand());
  }

  @PostMapping(Routes.AUTH_REFRESH)
  public TokenResult reissue(@RequestBody ReissueTokenRequest request) {
    return authUseCase.reissueToken(request.toCommand());
  }

  @PostMapping(Routes.AUTH_LOGOUT)
  public void logout(
      @RequestHeader(name = "X-USER-ID", required = false) Long userId,
      @RequestBody ReissueTokenRequest request) {
    authUseCase.logout(new LogoutCommand(userId, request.refreshToken(), request.deviceId()));
  }

  @PostMapping(Routes.AUTH_PROVIDER_LINK)
  public void linkProvider(
      @RequestHeader(name = "X-USER-ID", required = false) Long userId,
      @RequestBody LinkProviderRequest request) {
    authUseCase.linkProvider(request.toCommand(userId));
  }

  public record SocialLoginRequest(
      String providerType,
      String providerToken,
      String authorizationCode,
      String deviceId,
      String deviceOs,
      String appVersion,
      String pushToken) {
    SocialLoginCommand toCommand() {
      return new SocialLoginCommand(
          providerType,
          providerToken,
          authorizationCode,
          deviceId,
          deviceOs,
          appVersion,
          pushToken);
    }
  }

  public record ReissueTokenRequest(String refreshToken, String deviceId) {
    ReissueTokenCommand toCommand() {
      return new ReissueTokenCommand(refreshToken, deviceId);
    }
  }

  public record LinkProviderRequest(
      String providerType, String providerToken, String authorizationCode) {
    LinkProviderCommand toCommand(Long userId) {
      return new LinkProviderCommand(userId, providerType, providerToken, authorizationCode);
    }
  }
}
