package com.realmatch.backend.user.domain.model;

import java.time.OffsetDateTime;
import lombok.Getter;

/**
 * 매칭 품질에 필요한 확장 프로필을 표현하는 도메인 모델입니다.
 */
@Getter
public class UserProfile {

  private final Long userId;
  private final Integer heightCm;
  private final String jobTitle;
  private final String educationLevel;
  private final String mbti;
  private final String introduction;
  private final String regionCode;
  private final OffsetDateTime createdAt;
  private final OffsetDateTime updatedAt;

  public UserProfile(
      Long userId,
      Integer heightCm,
      String jobTitle,
      String educationLevel,
      String mbti,
      String introduction,
      String regionCode,
      OffsetDateTime createdAt,
      OffsetDateTime updatedAt) {
    this.userId = userId;
    this.heightCm = heightCm;
    this.jobTitle = jobTitle;
    this.educationLevel = educationLevel;
    this.mbti = mbti;
    this.introduction = introduction;
    this.regionCode = regionCode;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static UserProfile empty(Long userId, OffsetDateTime now) {
    return new UserProfile(userId, null, null, null, null, null, null, now, now);
  }

  public UserProfile update(
      Integer heightCm,
      String jobTitle,
      String educationLevel,
      String mbti,
      String introduction,
      String regionCode,
      OffsetDateTime now) {
    return new UserProfile(
        userId,
        heightCm == null ? this.heightCm : heightCm,
        jobTitle == null ? this.jobTitle : jobTitle,
        educationLevel == null ? this.educationLevel : educationLevel,
        mbti == null ? this.mbti : mbti,
        introduction == null ? this.introduction : introduction,
        regionCode == null ? this.regionCode : regionCode,
        createdAt,
        now);
  }
}
