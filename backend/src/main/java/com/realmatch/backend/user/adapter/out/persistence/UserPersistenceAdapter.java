package com.realmatch.backend.user.adapter.out.persistence;

import com.realmatch.backend.user.application.port.out.UserPersistencePort;
import com.realmatch.backend.user.domain.model.User;
import com.realmatch.backend.user.domain.model.UserPreference;
import com.realmatch.backend.user.domain.model.UserProfile;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** 회원 Persistence Adapter\ */
@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {

  private final UserJpaRepository userJpaRepository;
  private final UserProfileJpaRepository userProfileJpaRepository;
  private final UserPreferenceJpaRepository userPreferenceJpaRepository;

  @Override
  public Optional<User> findUser(Long userId) {
    return userJpaRepository.findById(userId)
            .map(this::toDomain);
  }

  @Override
  public User saveUser(User user) {
    UserJpaEntity entity = UserJpaEntity.from(user);
    UserJpaEntity savedEntity = userJpaRepository.save(entity);
    return toDomain(savedEntity);
  }

  @Override
  public Optional<UserProfile> findProfile(Long userId) {
    return userProfileJpaRepository.findById(userId).map(this::toDomain);
  }

  @Override
  public UserProfile saveProfile(UserProfile profile) {
    UserProfileJpaEntity entity = UserProfileJpaEntity.from(profile);
    UserProfileJpaEntity savedEntity = userProfileJpaRepository.save(entity);
    return toDomain(savedEntity);
  }

  @Override
  public Optional<UserPreference> findPreference(Long userId) {
    return userPreferenceJpaRepository.findById(userId).map(this::toDomain);
  }

  @Override
  public UserPreference savePreference(UserPreference preference) {
    UserPreferenceJpaEntity entity = UserPreferenceJpaEntity.from(preference);
    UserPreferenceJpaEntity savedEntity = userPreferenceJpaRepository.save(entity);
    return toDomain(savedEntity);
  }

  private User toDomain(UserJpaEntity entity) {
    return new User(
            entity.getUserId(),
            entity.getUserUUID(),
            entity.getStatus(),
            entity.getNickname(),
            entity.getEmail(),
            entity.getPhoneNumber(),
            entity.getGender(),
            entity.getBirthYear(),
            entity.isProfileCompleted(),
            entity.getLastLoginAt(),
            entity.getCreatedAt(),
            entity.getUpdatedAt(),
            entity.getDeletedAt()
    );
  }

  private UserProfile toDomain(UserProfileJpaEntity entity) {
    return new UserProfile(
        entity.getUserId(),
        toInteger(entity.getHeightCm()),
        entity.getJobTitle(),
        entity.getEducationLevel(),
        entity.getMbti(),
        entity.getIntroduction(),
        entity.getRegionCode(),
        entity.getCreatedAt(),
        entity.getUpdatedAt());
  }

  private UserPreference toDomain(UserPreferenceJpaEntity entity) {
    return new UserPreference(
        entity.getUserId(),
        entity.getPreferredGender(),
        toInteger(entity.getPreferredAgeMin()),
        toInteger(entity.getPreferredAgeMax()),
        entity.getPreferredRegionCode(),
        entity.isAllowRandomCall(),
        entity.getCreatedAt(),
        entity.getUpdatedAt());
  }

  private Integer toInteger(Short value) {
    return value == null ? null : value.intValue();
  }
}
