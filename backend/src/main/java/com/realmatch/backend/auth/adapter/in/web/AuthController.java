package com.realmatch.backend.auth.adapter.in.web;

import com.realmatch.backend.auth.application.port.in.AuthUseCase;
import com.realmatch.backend.auth.application.port.in.AuthUseCase.LinkProviderCommand;
import com.realmatch.backend.auth.application.port.in.AuthUseCase.LogoutCommand;
import com.realmatch.backend.auth.application.port.in.AuthUseCase.ReissueTokenCommand;
import com.realmatch.backend.auth.application.port.in.AuthUseCase.TokenResult;
import com.realmatch.backend.common.Routes;
import com.realmatch.backend.config.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/** TODO: 요청 검증, 인증 사용자 추출, Command 변환, TokenResult 응답 변환을 구현합니다. */
@RestController
@RequiredArgsConstructor
public class AuthController {

  private final AuthUseCase authUseCase;

  @PostMapping(Routes.AUTH_REFRESH)
  public TokenResult reissue(@RequestBody ReissueTokenRequest request) {
    return authUseCase.reissueToken(request.toCommand());
  }

  @PostMapping(Routes.AUTH_LOGOUT)
  public void logout(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @RequestBody ReissueTokenRequest request) {
    authUseCase.logout(new LogoutCommand(userDetails.getUserId(), request.refreshToken()));
  }

  @PostMapping(Routes.AUTH_PROVIDER_LINK)
  public void linkProvider(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @RequestBody LinkProviderRequest request) {
    authUseCase.linkProvider(request.toCommand(userDetails.getUserId()));
  }

  // 로그아웃이랑 형식 같아서 일단 같이 사용
  public record ReissueTokenRequest(String refreshToken) {
    ReissueTokenCommand toCommand() {
      return new ReissueTokenCommand(refreshToken);
    }
  }

  public record LinkProviderRequest(String providerType) {
    LinkProviderCommand toCommand(Long userId) {
      return new LinkProviderCommand(userId, providerType);
    }
  }
}
