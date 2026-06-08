package com.realmatch.backend.user.application.port.in;

import com.realmatch.backend.user.domain.model.User;
import com.realmatch.backend.user.domain.model.UserPreference;
import com.realmatch.backend.user.domain.model.UserProfile;

/** 회원/사용자정보 입력 포트입니다. TODO: 내 정보, 프로필, 선호 조건 조회/수정 유스케이스를 구현합니다. */
public interface UserUseCase {

  UserInfoResult getMe(Long userId);

  UserInfoResult updateBasicInfo(UpdateBasicInfoCommand command);

  ProfileResult getProfile(Long userId);

  ProfileResult updateProfile(UpdateProfileCommand command);

  PreferenceResult getPreference(Long userId);

  PreferenceResult updatePreference(UpdatePreferenceCommand command);

  record UpdateBasicInfoCommand(
      Long userId, String nickname, String phoneNumber, String gender, Integer birthYear) {}

  record UpdateProfileCommand(
      Long userId,
      Integer heightCm,
      String jobTitle,
      String educationLevel,
      String mbti,
      String introduction,
      String regionCode) {}

  record UpdatePreferenceCommand(
      Long userId,
      String preferredGender,
      Integer preferredAgeMin,
      Integer preferredAgeMax,
      String preferredRegionCode,
      Boolean allowRandomCall) {}

  record UserInfoResult(
      Long userId,
      String nickname,
      String email,
      String phoneNumber,
      String gender,
      Integer birthYear,
      String status,
      boolean profileCompleted) {
    public static UserInfoResult from(User user) {
      return new UserInfoResult(
          user.getUserId(),
          user.getNickname(),
          user.getEmail(),
          user.getPhoneNumber(),
          user.getGender(),
          user.getBirthYear(),
          user.getStatus(),
          user.isProfileCompleted());
    }
  }

  record ProfileResult(
      Long userId,
      Integer heightCm,
      String jobTitle,
      String educationLevel,
      String mbti,
      String introduction,
      String regionCode) {
    public static ProfileResult from(UserProfile profile) {
      return new ProfileResult(
          profile.getUserId(),
          profile.getHeightCm(),
          profile.getJobTitle(),
          profile.getEducationLevel(),
          profile.getMbti(),
          profile.getIntroduction(),
          profile.getRegionCode());
    }

    public static ProfileResult empty(Long userId) {
      return new ProfileResult(userId, null, null, null, null, null, null);
    }
  }

  record PreferenceResult(
      Long userId,
      String preferredGender,
      Integer preferredAgeMin,
      Integer preferredAgeMax,
      String preferredRegionCode,
      boolean allowRandomCall) {
    public static PreferenceResult from(UserPreference preference) {
      return new PreferenceResult(
          preference.getUserId(),
          preference.getPreferredGender(),
          preference.getPreferredAgeMin(),
          preference.getPreferredAgeMax(),
          preference.getPreferredRegionCode(),
          Boolean.TRUE.equals(preference.getAllowRandomCall()));
    }

    public static PreferenceResult empty(Long userId) {
      return new PreferenceResult(userId, null, null, null, null, true);
    }
  }
}
