package com.realmatch.backend.auth.application.service;

import com.realmatch.backend.auth.application.port.in.AuthUseCase;
import com.realmatch.backend.auth.application.port.out.AuthPersistencePort;
import com.realmatch.backend.auth.application.port.out.TokenIssuePort;
import com.realmatch.backend.auth.domain.model.AuthAccount;
import com.realmatch.backend.auth.domain.model.RefreshTokenSession;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;
import java.util.HexFormat;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 인증 유스케이스 구현체 TODO: 소셜 토큰 검증, 계정 조회/생성, 디바이스 갱신, Refresh Token 저장, JWT 발급 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthApplicationService implements AuthUseCase {

  private final AuthPersistencePort authPersistencePort;
  private final TokenIssuePort tokenIssuePort;

  @Value("${realmatch.security.refresh-token-ttl-days}")
  private long refreshTokenTtlDays;

  @Override
  @Transactional
  public TokenResult oauth2Login(OAuth2LoginCommand command) {
    OffsetDateTime now = OffsetDateTime.now();

    Optional<AuthAccount> authAccount =
        authPersistencePort.findAuthAccount(command.providerType(), command.providerUserId());

    if (authAccount.isPresent()) {
      AuthAccount authenticatedAccount = authAccount.get().authenticated(now);

      AuthAccount savedAccount = authPersistencePort.saveAuthAccount(authenticatedAccount);

      TokenResult result =
          tokenIssuePort.issue(
              savedAccount.getUserId(), true // 나중에 user.profileCompleted로 교체
              );

      // TODO : refresh 토큰 저장 후 TokenResult return
      String hash = hashToken(result.refreshToken());
      RefreshTokenSession session =
          RefreshTokenSession.create(
              result.userId(), hash, null, now.plusDays(refreshTokenTtlDays), now);
      authPersistencePort.saveRefreshTokenSession(session);

      return result;
    }

    Long userId = authPersistencePort.createUser(command.email(), command.nickname());

    AuthAccount newAccount =
        AuthAccount.create(
            userId, command.providerType(), command.providerUserId(), command.email(), now);

    AuthAccount savedAccount = authPersistencePort.saveAuthAccount(newAccount);

    TokenResult result = tokenIssuePort.issue(savedAccount.getUserId(), false);

    // TODO : refresh 토큰 저장 후 TokenResult return
    String hash = hashToken(result.refreshToken());
    RefreshTokenSession session =
        RefreshTokenSession.create(
            result.userId(), hash, null, now.plusDays(refreshTokenTtlDays), now);
    authPersistencePort.saveRefreshTokenSession(session);

    return result;
  }

  @Override
  @Transactional
  public TokenResult reissueToken(ReissueTokenCommand command) {
    throw new UnsupportedOperationException("TODO: Refresh Token 검증 후 토큰 재발급을 구현합니다.");
  }

  @Override
  @Transactional
  public void logout(LogoutCommand command) {
    throw new UnsupportedOperationException("TODO: Refresh Token 폐기와 세션 정리를 구현합니다.");
  }

  @Override
  @Transactional
  public void linkProvider(LinkProviderCommand command) {
    throw new UnsupportedOperationException("TODO: 명시적 소셜 계정 연동을 구현합니다.");
  }

  private String hashToken(String token) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] encodedHash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
      return HexFormat.of().formatHex(encodedHash);
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException("SHA-256 알고리즘을 사용할 수 없습니다.", e);
    }
  }
}
