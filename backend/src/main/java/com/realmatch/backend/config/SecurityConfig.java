package com.realmatch.backend.config;

import com.realmatch.backend.auth.adapter.in.oauth.Oauth2LoginFailureHandler;
import com.realmatch.backend.auth.adapter.in.oauth.Oauth2LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/** Spring Security 설정 JWT 인증, OAuth2 로그인 검증, 관리자 API 권한 정책을 관리합니다. */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  // TODO: SecurityFilterChain, JWT 필터, CORS, 허용 경로, 관리자 권한 정책을 구현합니다.
  private final Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;
  private final Oauth2LoginFailureHandler oauth2LoginFailureHandler;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(
                        "/actuator/health",
                        "/oauth2/**",
                        "/login/oauth2/code/**",
                        "/api/v1/auth/token/refresh")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .oauth2Login(
            oauth2 ->
                oauth2
                    // OAuth2 로그인 성공 후 우리 서비스 회원 조회/생성과 JWT 발급 흐름으로 연결합니다.
                    .successHandler(oauth2LoginSuccessHandler)
                    // OAuth2 로그인 실패 시 프론트 로그인 화면으로 redirect합니다.
                    .failureHandler(oauth2LoginFailureHandler));

    return http.build();
  }
}
