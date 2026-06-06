package com.realmatch.backend.auth.adapter.out.token;

import com.realmatch.backend.auth.application.port.in.AuthUseCase.TokenResult;
import com.realmatch.backend.auth.application.port.out.TokenIssuePort;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;

/** JWT 발급 Adapter입니다. TODO: application.yml의 TTL 설정을 사용해 Access/Refresh Token을 발급합니다. */
@Component
public class JwtTokenAdapter implements TokenIssuePort {

  @Value("${realmatch.security.jwt-secret}")
  private String jwtSecret;

  @Value("${realmatch.security.access-token-ttl-minutes}")
  private long accessTokenTtlMinutes;

  @Value("${realmatch.security.refresh-token-ttl-days}")
  private long refreshTokenTtlDays;

  @Override
  public TokenResult issue(Long userId, boolean profileCompleted) {
    // TODO : jwt 발급 구현
    Instant now = Instant.now();

    String accessToken = createToken(
            userId,
            "ACCESS",
            now,
            now.plus(Duration.ofMinutes(accessTokenTtlMinutes)),
            Map.of("profileCompleted", profileCompleted)
    );

    String refreshToken = createToken(
            userId,
            "REFRESH",
            now,
            now.plus(Duration.ofDays(refreshTokenTtlDays)),
            Map.of()
    );

    return new TokenResult(accessToken, refreshToken, userId, profileCompleted);
  }

  private String createToken(Long userId, String tokenType, Instant issuedAt, Instant expired, Map<String, Boolean> claims) {
    SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

    JwtBuilder builder = Jwts.builder()
            .subject(String.valueOf(userId))
            .issuedAt(Date.from(issuedAt))
            .expiration(Date.from(expired))
            .claim("type", tokenType)
            .signWith(key, Jwts.SIG.HS256);

    claims.forEach(builder::claim);

    return builder.compact();
  }

}
