package com.realmatch.backend.config;

import com.realmatch.backend.auth.adapter.in.oauth.Oauth2LoginFailureHandler;
import com.realmatch.backend.auth.adapter.in.oauth.Oauth2LoginSuccessHandler;
import com.realmatch.backend.config.security.JwtAuthenticationEntryPoint;
import com.realmatch.backend.config.security.JwtAuthenticationFilter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(SecurityConfig.CorsProperties.class)
@RequiredArgsConstructor
public class SecurityConfig {

  // TODO: SecurityFilterChain, JWT 필터, CORS, 허용 경로, 관리자 권한 정책을 구현합니다.
  private final Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;
  private final Oauth2LoginFailureHandler oauth2LoginFailureHandler;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final CorsProperties corsProperties;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
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
        .exceptionHandling(
            exception ->
                exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
        .oauth2Login(
            oauth2 ->
                oauth2
                    .successHandler(oauth2LoginSuccessHandler)
                    .failureHandler(oauth2LoginFailureHandler))
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    List<String> allowedOrigins =
        corsProperties.allowedOrigins() == null || corsProperties.allowedOrigins().isEmpty()
            ? List.of("http://localhost:5173")
            : corsProperties.allowedOrigins();

    CorsConfiguration configuration = new CorsConfiguration();
    // 프론트엔드 origin만 허용
    // 기본값 : application.yml -> FRONTEND_ORIGIN
    configuration.setAllowedOrigins(allowedOrigins);
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(
        List.of("Authorization", "Content-Type", "Accept", "Origin", "X-Requested-With"));
    configuration.setExposedHeaders(List.of("Authorization"));
    configuration.setAllowCredentials(false);
    configuration.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @ConfigurationProperties(prefix = "realmatch.web.cors")
  public record CorsProperties(List<String> allowedOrigins) {}
}
