package com.realmatch.backend.auth.adapter.in.oauth;

import java.util.Map;
import org.springframework.stereotype.Component;

/** Google, Kakao, Naver OAuth2 사용자 응답을 공통 프로필로 변환 */
@Component
public class UserProfileExtractor {

  public UserProfile extract(String providerType, Map<String, Object> attributes) {
    return switch (providerType) {
      case "GOOGLE" -> fromGoogle(attributes);
      case "KAKAO" -> fromKakao(attributes);
      case "NAVER" -> fromNaver(attributes);
      default -> throw new IllegalArgumentException("지원하지 않는 OAuth2 provider입니다: " + providerType);
    };
  }

  private UserProfile fromGoogle(Map<String, Object> attributes) {
    return new UserProfile(
        "GOOGLE",
        stringValue(attributes.get("sub")),
        stringValue(attributes.get("email")),
        stringValue(attributes.get("name")),
        stringValue(attributes.get("picture")));
  }

  private UserProfile fromKakao(Map<String, Object> attributes) {
    Map<String, Object> account = mapValue(attributes.get("kakao_account"));
    Map<String, Object> profile = mapValue(account.get("profile"));

    return new UserProfile(
        "KAKAO",
        stringValue(attributes.get("id")),
        stringValue(account.get("email")),
        stringValue(profile.get("nickname")),
        stringValue(profile.get("profile_image_url")));
  }

  private UserProfile fromNaver(Map<String, Object> attributes) {
    Map<String, Object> response = mapValue(attributes.get("response"));

    return new UserProfile(
        "NAVER",
        stringValue(response.get("id")),
        stringValue(response.get("email")),
        stringValue(response.get("nickname")),
        stringValue(response.get("profile_image")));
  }

  @SuppressWarnings("unchecked")
  private Map<String, Object> mapValue(Object value) {
    if (value instanceof Map<?, ?> map) {
      return (Map<String, Object>) map;
    }
    return Map.of();
  }

  private String stringValue(Object value) {
    return value == null ? null : String.valueOf(value);
  }
}
