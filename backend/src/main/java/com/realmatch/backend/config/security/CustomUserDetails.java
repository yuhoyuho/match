package com.realmatch.backend.config.security;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 현재 MVP 단계이기 때문에 userId, role만 담음
 */
public class CustomUserDetails implements UserDetails {

  private final Long userId;
  private final List<GrantedAuthority> authorities;

  public CustomUserDetails(Long userId) {
    this.userId = userId;
    this.authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
  }

  public Long getUserId() {
    return userId;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    // 현재 소셜 로그인만 구현되어 있어서 null.
    // 자체 로그인 구현 시에 password 반환
    return null;
  }

  @Override
  public String getUsername() {
    return String.valueOf(userId);
  }
}
