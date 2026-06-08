package com.realmatch.backend.user.domain.model;

import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * 서비스 내부 기준 회원 계정을 표현하는 도메인 모델
 */
@Getter
public class User {
  private final Long userId;
  private final UUID userUUID;
  private final String status;
  private final String nickname;
  private final String email;
  private final String phoneNumber;
  private final String gender;
  private final Integer birthYear;
  private final boolean isProfileCompleted;
  private final OffsetDateTime lastLoginAt;
  private final OffsetDateTime createdAt;
  private final OffsetDateTime updatedAt;
  private final OffsetDateTime deletedAt;

  public User(
      Long userId,
      UUID userUUID,
      String status,
      String nickname,
      String email,
      String phoneNumber,
      String gender,
      Integer birthYear,
      boolean isProfileCompleted,
      OffsetDateTime lastLoginAt,
      OffsetDateTime createdAt,
      OffsetDateTime updatedAt,
      OffsetDateTime deletedAt) {
    this.userId = userId;
    this.userUUID = userUUID;
    this.status = status;
    this.nickname = nickname;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.gender = gender;
    this.birthYear = birthYear;
    this.isProfileCompleted = isProfileCompleted;
    this.lastLoginAt = lastLoginAt;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.deletedAt = deletedAt;
  }

  public User updateBasicInfo(
      String nickname, String phoneNumber, String gender, Integer birthYear, OffsetDateTime now) {
    return new User(
        userId,
        userUUID,
        status,
        nickname == null ? this.nickname : nickname,
        email,
        phoneNumber == null ? this.phoneNumber : phoneNumber,
        gender == null ? this.gender : gender,
        birthYear == null ? this.birthYear : birthYear,
        isProfileCompleted,
        lastLoginAt,
        createdAt,
        now,
        deletedAt);
  }

  public User updateProfileCompleted(boolean profileCompleted, OffsetDateTime now) {
    return new User(
        userId,
        userUUID,
        status,
        nickname,
        email,
        phoneNumber,
        gender,
        birthYear,
        profileCompleted,
        lastLoginAt,
        createdAt,
        now,
        deletedAt);
  }
}
