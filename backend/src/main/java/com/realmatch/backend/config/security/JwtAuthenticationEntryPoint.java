package com.realmatch.backend.config.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/** 인증되지 않은 요청 또는 유효하지 않은 JWT 요청에 대한 401 응답 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");

    // TODO: 공통 에러 코드 체계가 확정되면 ErrorResponse 구조에 맞춰 응답 필드를 확장합니다.
    response
        .getWriter()
        .write(
            """
            {"status":401,"error":"Unauthorized","message":"인증이 필요합니다."}
            """);
  }
}
