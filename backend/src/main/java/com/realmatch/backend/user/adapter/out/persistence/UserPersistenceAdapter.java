package com.realmatch.backend.user.adapter.out.persistence;

import com.realmatch.backend.user.application.port.out.UserPersistencePort;
import com.realmatch.backend.user.domain.model.User;
import com.realmatch.backend.user.domain.model.UserPreference;
import com.realmatch.backend.user.domain.model.UserProfile;

import java.time.OffsetDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** 회원 Persistence Adapter입니다. TODO: UserPersistencePort와 JPA Repository 사이의 매핑을 구현합니다. */
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
    throw new UnsupportedOperationException("TODO: 회원을 저장합니다.");
  }

  @Override
  public Optional<UserProfile> findProfile(Long userId) {
    throw new UnsupportedOperationException("TODO: 프로필을 조회합니다.");
  }

  @Override
  public UserProfile saveProfile(UserProfile profile) {
    throw new UnsupportedOperationException("TODO: 프로필을 저장합니다.");
  }

  @Override
  public Optional<UserPreference> findPreference(Long userId) {
    throw new UnsupportedOperationException("TODO: 선호 조건을 조회합니다.");
  }

  @Override
  public UserPreference savePreference(UserPreference preference) {
    throw new UnsupportedOperationException("TODO: 선호 조건을 저장합니다.");
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
}
