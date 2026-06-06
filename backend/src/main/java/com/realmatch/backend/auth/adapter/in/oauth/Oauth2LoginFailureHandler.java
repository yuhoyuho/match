package com.realmatch.backend.auth.adapter.in.oauth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class Oauth2LoginFailureHandler implements AuthenticationFailureHandler {

  @Value("${realmatch.oauth2.failure-redirect-uri:http://localhost:5173/login}")
  private String failureRedirectUri;

  @Override
  public void onAuthenticationFailure(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
      throws IOException, ServletException {
    String redirectUrl =
        UriComponentsBuilder.fromUriString(failureRedirectUri)
            .queryParam("error", "oauth2_login_failed")
            .build()
            .toUriString();

    response.sendRedirect(redirectUrl);
  }
}
