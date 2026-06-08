package com.realmatch.backend.user.domain.model;

import java.time.OffsetDateTime;
import lombok.Getter;

/**
 * 매칭 선호 조건과 랜덤통화 허용 여부를 표현하는 도메인 모델입니다.
 */
@Getter
public class UserPreference {

  private final Long userId;
  private final String preferredGender;
  private final Integer preferredAgeMin;
  private final Integer preferredAgeMax;
  private final String preferredRegionCode;
  private final Boolean allowRandomCall;
  private final OffsetDateTime createdAt;
  private final OffsetDateTime updatedAt;

  public UserPreference(
      Long userId,
      String preferredGender,
      Integer preferredAgeMin,
      Integer preferredAgeMax,
      String preferredRegionCode,
      Boolean allowRandomCall,
      OffsetDateTime createdAt,
      OffsetDateTime updatedAt) {
    this.userId = userId;
    this.preferredGender = preferredGender;
    this.preferredAgeMin = preferredAgeMin;
    this.preferredAgeMax = preferredAgeMax;
    this.preferredRegionCode = preferredRegionCode;
    this.allowRandomCall = allowRandomCall;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static UserPreference empty(Long userId, OffsetDateTime now) {
    return new UserPreference(userId, null, null, null, null, true, now, now);
  }

  public UserPreference update(
      String preferredGender,
      Integer preferredAgeMin,
      Integer preferredAgeMax,
      String preferredRegionCode,
      Boolean allowRandomCall,
      OffsetDateTime now) {
    return new UserPreference(
        userId,
        preferredGender == null ? this.preferredGender : preferredGender,
        preferredAgeMin == null ? this.preferredAgeMin : preferredAgeMin,
        preferredAgeMax == null ? this.preferredAgeMax : preferredAgeMax,
        preferredRegionCode == null ? this.preferredRegionCode : preferredRegionCode,
        allowRandomCall == null ? this.allowRandomCall : allowRandomCall,
        createdAt,
        now);
  }
}
