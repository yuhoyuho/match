package com.realmatch.backend.auth.adapter.out.token;

import com.realmatch.backend.auth.application.port.in.AuthUseCase.TokenResult;
import com.realmatch.backend.auth.application.port.out.TokenIssuePort;
import org.springframework.stereotype.Component;

/** JWT 발급 Adapter입니다. TODO: application.yml의 TTL 설정을 사용해 Access/Refresh Token을 발급합니다. */
@Component
public class JwtTokenAdapter implements TokenIssuePort {

  @Override
  public TokenResult issue(Long userId, boolean profileCompleted) {
    throw new UnsupportedOperationException("TODO: JWT 발급을 구현합니다.");
  }
}
