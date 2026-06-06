package com.realmatch.backend.auth.adapter.in.oauth;

import com.realmatch.backend.auth.application.port.in.AuthUseCase.OAuth2LoginCommand;

/**
 * OAuth2 Provider 응답에서 추출한 공통 사용자 프로필
 *
 * <p>TODO: provider별 필수 동의 항목 누락, 이메일 미제공, 프로필 이미지 부재 시 정책을 구현합니다.
 */
public record UserProfile(
    String providerType,
    String providerUserId,
    String email,
    String nickname,
    String profileImageUrl) {

  public OAuth2LoginCommand toCommand() {
    return new OAuth2LoginCommand(providerType, providerUserId, email, nickname, profileImageUrl);
  }
}
