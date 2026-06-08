package com.realmatch.backend.user.application.service;

import com.realmatch.backend.user.application.port.in.UserUseCase;
import com.realmatch.backend.user.application.port.out.UserPersistencePort;
import com.realmatch.backend.user.domain.model.User;
import com.realmatch.backend.user.domain.model.UserPreference;
import com.realmatch.backend.user.domain.model.UserProfile;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Set;

/** 회원 유스케이스 구현체 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserApplicationService implements UserUseCase {

  private static final Set<String> GENDERS = Set.of("MALE", "FEMALE", "OTHER", "UNKNOWN");
  private static final Set<String> PREFERRED_GENDERS = Set.of("MALE", "FEMALE", "ANY");

  private final UserPersistencePort userPersistencePort;

  @Override
  public UserInfoResult getMe(Long userId) {

    User findUser = userPersistencePort.findUser(userId)
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 ID입니다."));

    return UserInfoResult.from(findUser);
  }

  @Override
  @Transactional
  public UserInfoResult updateBasicInfo(UpdateBasicInfoCommand command) {
    OffsetDateTime now = OffsetDateTime.now();
    User user = requireUser(command.userId());

    String nickname = trim(command.nickname());
    String phoneNumber = trim(command.phoneNumber());
    String gender = upper(trim(command.gender()));
    Integer birthYear = command.birthYear();

    validateGender(gender);
    validateBirthYear(birthYear);

    User updatedUser = user.updateBasicInfo(nickname, phoneNumber, gender, birthYear, now);
    UserProfile profile = userPersistencePort.findProfile(command.userId()).orElse(null);
    UserPreference preference = userPersistencePort.findPreference(command.userId()).orElse(null);

    boolean profileCompleted = isProfileCompleted(updatedUser, profile, preference);
    User savedUser =
        userPersistencePort.saveUser(updatedUser.updateProfileCompleted(profileCompleted, now));

    return UserInfoResult.from(savedUser);
  }

  @Override
  public ProfileResult getProfile(Long userId) {
    requireUser(userId);

    return userPersistencePort
        .findProfile(userId)
        .map(ProfileResult::from)
        .orElseGet(() -> ProfileResult.empty(userId));
  }

  @Override
  @Transactional
  public ProfileResult updateProfile(UpdateProfileCommand command) {
    OffsetDateTime now = OffsetDateTime.now();
    User user = requireUser(command.userId());

    Integer heightCm = command.heightCm();
    String jobTitle = trim(command.jobTitle());
    String educationLevel = trim(command.educationLevel());
    String mbti = upper(trim(command.mbti()));
    String introduction = trim(command.introduction());
    String regionCode = trim(command.regionCode());

    validateHeight(heightCm);

    UserProfile currentProfile =
        userPersistencePort
            .findProfile(command.userId())
            .orElseGet(() -> UserProfile.empty(command.userId(), now));
    UserProfile updatedProfile =
        currentProfile.update(
            heightCm, jobTitle, educationLevel, mbti, introduction, regionCode, now);
    UserProfile savedProfile = userPersistencePort.saveProfile(updatedProfile);
    UserPreference preference = userPersistencePort.findPreference(command.userId()).orElse(null);

    boolean profileCompleted = isProfileCompleted(user, savedProfile, preference);
    userPersistencePort.saveUser(user.updateProfileCompleted(profileCompleted, now));

    return ProfileResult.from(savedProfile);
  }

  @Override
  public PreferenceResult getPreference(Long userId) {
    requireUser(userId);

    return userPersistencePort
        .findPreference(userId)
        .map(PreferenceResult::from)
        .orElseGet(() -> PreferenceResult.empty(userId));
  }

  @Override
  @Transactional
  public PreferenceResult updatePreference(UpdatePreferenceCommand command) {
    OffsetDateTime now = OffsetDateTime.now();
    User user = requireUser(command.userId());

    String preferredGender = upper(trim(command.preferredGender()));
    Integer preferredAgeMin = command.preferredAgeMin();
    Integer preferredAgeMax = command.preferredAgeMax();
    String preferredRegionCode = trim(command.preferredRegionCode());
    Boolean allowRandomCall = command.allowRandomCall();

    validatePreferredGender(preferredGender);
    validatePreferredAge(preferredAgeMin, preferredAgeMax);

    UserPreference currentPreference =
        userPersistencePort
            .findPreference(command.userId())
            .orElseGet(() -> UserPreference.empty(command.userId(), now));
    UserPreference updatedPreference =
        currentPreference.update(
            preferredGender,
            preferredAgeMin,
            preferredAgeMax,
            preferredRegionCode,
            allowRandomCall,
            now);
    UserPreference savedPreference = userPersistencePort.savePreference(updatedPreference);
    UserProfile profile = userPersistencePort.findProfile(command.userId()).orElse(null);

    boolean profileCompleted = isProfileCompleted(user, profile, savedPreference);
    userPersistencePort.saveUser(user.updateProfileCompleted(profileCompleted, now));

    return PreferenceResult.from(savedPreference);
  }

  private User requireUser(Long userId) {
    if (userId == null) {
      throw new NoSuchElementException("존재하지 않는 ID입니다.");
    }

    return userPersistencePort
        .findUser(userId)
        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 ID입니다."));
  }

  private boolean isProfileCompleted(
      User user, UserProfile profile, UserPreference preference) {
    return hasText(user.getNickname())
        && hasText(user.getPhoneNumber())
        && hasText(user.getGender())
        && user.getBirthYear() != null
        && profile != null
        && profile.getHeightCm() != null
        && hasText(profile.getJobTitle())
        && hasText(profile.getEducationLevel())
        && hasText(profile.getMbti())
        && hasText(profile.getIntroduction())
        && hasText(profile.getRegionCode())
        && preference != null
        && hasText(preference.getPreferredGender())
        && preference.getPreferredAgeMin() != null
        && preference.getPreferredAgeMax() != null
        && hasText(preference.getPreferredRegionCode())
        && preference.getAllowRandomCall() != null;
  }

  private void validateGender(String gender) {
    if (gender != null && !GENDERS.contains(gender)) {
      throw new IllegalArgumentException("지원하지 않는 성별입니다.");
    }
  }

  private void validatePreferredGender(String preferredGender) {
    if (preferredGender != null && !PREFERRED_GENDERS.contains(preferredGender)) {
      throw new IllegalArgumentException("지원하지 않는 선호 성별입니다.");
    }
  }

  private void validateBirthYear(Integer birthYear) {
    if (birthYear != null && (birthYear < 1900 || birthYear > 2100)) {
      throw new IllegalArgumentException("출생연도가 올바르지 않습니다.");
    }
  }

  private void validateHeight(Integer heightCm) {
    if (heightCm != null && (heightCm < 100 || heightCm > 250)) {
      throw new IllegalArgumentException("키가 올바르지 않습니다.");
    }
  }

  private void validatePreferredAge(Integer preferredAgeMin, Integer preferredAgeMax) {
    if (preferredAgeMin != null && preferredAgeMin < 0) {
      throw new IllegalArgumentException("선호 최소 나이가 올바르지 않습니다.");
    }
    if (preferredAgeMax != null && preferredAgeMax < 0) {
      throw new IllegalArgumentException("선호 최대 나이가 올바르지 않습니다.");
    }
    if (preferredAgeMin != null
        && preferredAgeMax != null
        && preferredAgeMin > preferredAgeMax) {
      throw new IllegalArgumentException("선호 최소 나이가 최대 나이보다 클 수 없습니다.");
    }
  }

  private String trim(String value) {
    return value == null ? null : value.trim();
  }

  private String upper(String value) {
    return value == null ? null : value.toUpperCase();
  }

  private boolean hasText(String value) {
    return value != null && !value.isBlank();
  }
}
