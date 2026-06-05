package com.realmatch.backend.auth.application.port.out;

import com.realmatch.backend.auth.application.port.in.AuthUseCase.TokenResult;

/** 서비스 JWT 발급 포트입니다. TODO: Access Token과 Refresh Token 생성, 서명, TTL 정책을 구현합니다. */
public interface TokenIssuePort {

  TokenResult issue(Long userId, boolean profileCompleted);
}
