package com.realmatch.backend.user.application.port.out;

import com.realmatch.backend.user.domain.model.User;
import com.realmatch.backend.user.domain.model.UserPreference;
import com.realmatch.backend.user.domain.model.UserProfile;
import java.util.Optional;

/**
 * 회원 영속성 출력 포트입니다. TODO: users, user_profiles, user_preferences, user_devices,
 * user_terms_agreements 조회/저장을 구현합니다.
 */
public interface UserPersistencePort {

  Optional<User> findUser(Long userId);

  User saveUser(User user);

  Optional<UserProfile> findProfile(Long userId);

  UserProfile saveProfile(UserProfile profile);

  Optional<UserPreference> findPreference(Long userId);

  UserPreference savePreference(UserPreference preference);
}
