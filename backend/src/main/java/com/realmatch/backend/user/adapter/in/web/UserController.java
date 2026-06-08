package com.realmatch.backend.user.adapter.in.web;

import com.realmatch.backend.common.Routes;
import com.realmatch.backend.config.security.CustomUserDetails;
import com.realmatch.backend.user.application.port.in.UserUseCase;
import com.realmatch.backend.user.application.port.in.UserUseCase.PreferenceResult;
import com.realmatch.backend.user.application.port.in.UserUseCase.ProfileResult;
import com.realmatch.backend.user.application.port.in.UserUseCase.UpdateBasicInfoCommand;
import com.realmatch.backend.user.application.port.in.UserUseCase.UpdatePreferenceCommand;
import com.realmatch.backend.user.application.port.in.UserUseCase.UpdateProfileCommand;
import com.realmatch.backend.user.application.port.in.UserUseCase.UserInfoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/** 회원 Web Adapter입니다. TODO: 인증 사용자 추출, 요청 검증, 응답 DTO 분리를 구현합니다. */
@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserUseCase userUseCase;

  @GetMapping(Routes.USERS_ME)
  public UserInfoResult getMe(@AuthenticationPrincipal CustomUserDetails userDetails) {
    return userUseCase.getMe(userDetails.getUserId());
  }

  @PatchMapping(Routes.USERS_ME)
  public UserInfoResult updateMe(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @RequestBody BasicInfoRequest request) {
    return userUseCase.updateBasicInfo(request.toCommand(userDetails.getUserId()));
  }

  @GetMapping(Routes.USERS_ME_PROFILE)
  public ProfileResult getProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
    return userUseCase.getProfile(userDetails.getUserId());
  }

  @PatchMapping(Routes.USERS_ME_PROFILE)
  public ProfileResult updateProfile(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @RequestBody ProfileRequest request) {
    return userUseCase.updateProfile(request.toCommand(userDetails.getUserId()));
  }

  @GetMapping(Routes.USERS_ME_PREFERENCES)
  public PreferenceResult getPreference(@AuthenticationPrincipal CustomUserDetails userDetails) {
    return userUseCase.getPreference(userDetails.getUserId());
  }

  @PatchMapping(Routes.USERS_ME_PREFERENCES)
  public PreferenceResult updatePreference(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @RequestBody PreferenceRequest request) {
    return userUseCase.updatePreference(request.toCommand(userDetails.getUserId()));
  }

  public record BasicInfoRequest(
      String nickname, String phoneNumber, String gender, Integer birthYear) {
    UpdateBasicInfoCommand toCommand(Long userId) {
      return new UpdateBasicInfoCommand(userId, nickname, phoneNumber, gender, birthYear);
    }
  }

  public record ProfileRequest(
      Integer heightCm,
      String jobTitle,
      String educationLevel,
      String mbti,
      String introduction,
      String regionCode) {
    UpdateProfileCommand toCommand(Long userId) {
      return new UpdateProfileCommand(
          userId, heightCm, jobTitle, educationLevel, mbti, introduction, regionCode);
    }
  }

  public record PreferenceRequest(
      String preferredGender,
      Integer preferredAgeMin,
      Integer preferredAgeMax,
      String preferredRegionCode,
      Boolean allowRandomCall) {
    UpdatePreferenceCommand toCommand(Long userId) {
      return new UpdatePreferenceCommand(
          userId,
          preferredGender,
          preferredAgeMin,
          preferredAgeMax,
          preferredRegionCode,
          allowRandomCall);
    }
  }
}
