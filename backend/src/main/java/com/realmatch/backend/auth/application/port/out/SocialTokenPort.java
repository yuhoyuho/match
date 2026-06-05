package com.realmatch.backend.auth.application.port.out;

/** 외부 소셜 로그인 토큰 검증 포트입니다. TODO: provider별 idToken 또는 authorizationCode를 서버에서 직접 검증합니다. */
public interface SocialTokenPort {

  SocialAccount verify(String providerType, String providerToken, String authorizationCode);

  record SocialAccount(String providerUserId, String email, String nickname) {}
}
