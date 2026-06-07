package com.realmatch.backend.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

/** JWT 문자열을 검증 */
@Component
public class JwtTokenParser {

  private static final String ACCESS_TOKEN_TYPE = "ACCESS";

  @Value("${realmatch.security.jwt-secret}")
  private String jwtSecret;

  public CustomUserDetails parseAccessToken(String token) {
    try {
      Claims claims =
          Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();

      // claim 확인
      String tokenType = claims.get("type", String.class);
      if (!ACCESS_TOKEN_TYPE.equals(tokenType)) {
        throw new BadCredentialsException("Access Token이 아닙니다.");
      }

      Long userId = Long.valueOf(claims.getSubject());
      return new CustomUserDetails(userId);
    } catch (JwtException | IllegalArgumentException e) {
      throw new BadCredentialsException("유효하지 않은 JWT입니다.", e);
    }
  }

  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
  }
}
