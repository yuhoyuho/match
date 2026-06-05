package com.realmatch.backend.auth.adapter.out.oauth;

import com.realmatch.backend.auth.application.port.out.SocialTokenPort;
import org.springframework.stereotype.Component;

/** 소셜 로그인 토큰 검증 Adapter입니다. TODO: Apple, Google, Kakao, Naver 검증 로직을 providerType 기준으로 분기합니다. */
@Component
public class SocialTokenAdapter implements SocialTokenPort {

  @Override
  public SocialAccount verify(String providerType, String providerToken, String authorizationCode) {
    throw new UnsupportedOperationException("TODO: 외부 provider token을 검증합니다.");
  }
}
