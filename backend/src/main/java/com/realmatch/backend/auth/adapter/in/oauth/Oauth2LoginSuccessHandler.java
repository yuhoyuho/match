package com.realmatch.backend.auth.adapter.in.oauth;

import com.realmatch.backend.auth.application.port.in.AuthUseCase;
import com.realmatch.backend.auth.application.port.in.AuthUseCase.TokenResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class Oauth2LoginSuccessHandler implements AuthenticationSuccessHandler {

  private final AuthUseCase authUseCase;
  private final UserProfileExtractor userProfileExtractor;

  @Value("${realmatch.oauth2.success-redirect-uri:http://localhost:5173/oauth/callback}")
  private String successRedirectUri;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {
    OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

    String providerType =
        oauthToken.getAuthorizedClientRegistrationId().toUpperCase(); // KAKAO, GOOGLE, NAVER

    OAuth2User oAuth2User = oauthToken.getPrincipal();
    UserProfile profile = userProfileExtractor.extract(providerType, oAuth2User.getAttributes());

    TokenResult tokenResult = authUseCase.oauth2Login(profile.toCommand());

    // TODO: 운영 단계에서는 JWT를 query string에 직접 싣지 말고 loginCode 교환 방식으로 변경합니다.
    String redirectUrl =
        UriComponentsBuilder.fromUriString(successRedirectUri)
            .queryParam("accessToken", tokenResult.accessToken())
            .queryParam("refreshToken", tokenResult.refreshToken())
            .queryParam("userId", tokenResult.userId())
            .queryParam("profileCompleted", tokenResult.profileCompleted())
            .build()
            .toUriString();

    response.sendRedirect(redirectUrl);
  }
}
