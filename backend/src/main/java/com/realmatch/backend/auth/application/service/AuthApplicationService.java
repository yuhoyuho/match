package com.realmatch.backend.auth.application.service;

import com.realmatch.backend.auth.application.port.in.AuthUseCase;
import com.realmatch.backend.auth.application.port.out.AuthPersistencePort;
import com.realmatch.backend.auth.application.port.out.TokenIssuePort;
import com.realmatch.backend.auth.domain.model.AuthAccount;
import com.realmatch.backend.auth.domain.model.RefreshTokenSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;
import java.util.HexFormat;
import java.util.NoSuchElementException;
import java.util.Optional;

/** 인증 유스케이스 구현체 */
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
    OffsetDateTime now = OffsetDateTime.now();

    String refreshToken = command.refreshToken();
    if(refreshToken == null || refreshToken.isBlank()) {
      throw new IllegalStateException("토큰이 없습니다.");
    }

    String hash = hashToken(refreshToken);
    RefreshTokenSession session = authPersistencePort.findRefreshTokenSession(hash)
            .orElseThrow(() -> new IllegalStateException("유효하지 않은 토큰입니다."));

    if(session.getRevokedAt() != null) {
      throw new IllegalStateException("사용할 수 없는 토큰입니다.");
    }

    if(!session.getExpiresAt().isAfter(now)) {
      throw new IllegalStateException("만료된 토큰입니다.");
    }

    TokenResult result = tokenIssuePort.issue(session.getUserId(), true);

    RefreshTokenSession revokedSession = session.revoke(now);
    authPersistencePort.saveRefreshTokenSession(revokedSession);

    String newHash = hashToken(result.refreshToken());
    RefreshTokenSession newSession = RefreshTokenSession.create(
            session.getUserId(),
            newHash,
            session.getDeviceId(),
            now.plusDays(refreshTokenTtlDays),
            now
    );
    authPersistencePort.saveRefreshTokenSession(newSession);

    return result;
  }

  @Override
  @Transactional
  public void logout(LogoutCommand command) {
    OffsetDateTime now = OffsetDateTime.now();

    if(command.userId() == null) {
      throw new NoSuchElementException("존재하지 않는 사용자입니다.");
    }

    String refreshToken = command.refreshToken();
    if(refreshToken == null || refreshToken.isBlank()) {
      throw new IllegalStateException("토큰이 없습니다.");
    }

    String hash = hashToken(refreshToken);
    RefreshTokenSession session = authPersistencePort.findRefreshTokenSession(hash)
            .orElseThrow(() -> new IllegalStateException("유효하지 않은 토큰입니다."));

    if(!session.getUserId().equals(command.userId())) {
      throw new IllegalStateException("권한이 없는 사용자입니다.");
    }

    if(session.getRevokedAt() != null) {
      return;
    }

    RefreshTokenSession revokedSession = session.revoke(now);
    authPersistencePort.saveRefreshTokenSession(revokedSession);

    // TODO: 강제 로그아웃 구현할 경우 redis에 accessToken 블랙리스트 저장. 현재는 ttl 30분이라 괜찮음.
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
