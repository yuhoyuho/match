package com.realmatch.backend.user.application.service;

import com.realmatch.backend.user.application.port.in.UserUseCase;
import com.realmatch.backend.user.application.port.out.UserPersistencePort;
import com.realmatch.backend.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

/** 회원 유스케이스 구현체입니다. TODO: 기본 정보, 프로필 완성 여부, 선호 조건 유효성 검증을 구현합니다. */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserApplicationService implements UserUseCase {

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
    throw new UnsupportedOperationException("TODO: 회원 기본 정보를 수정합니다.");
  }

  @Override
  public ProfileResult getProfile(Long userId) {
    throw new UnsupportedOperationException("TODO: 프로필을 조회합니다.");
  }

  @Override
  @Transactional
  public ProfileResult updateProfile(UpdateProfileCommand command) {
    throw new UnsupportedOperationException("TODO: 프로필 수정과 완성 여부 갱신을 구현합니다.");
  }

  @Override
  public PreferenceResult getPreference(Long userId) {
    throw new UnsupportedOperationException("TODO: 선호 조건을 조회합니다.");
  }

  @Override
  @Transactional
  public PreferenceResult updatePreference(UpdatePreferenceCommand command) {
    throw new UnsupportedOperationException("TODO: 선호 조건을 수정합니다.");
  }
}
