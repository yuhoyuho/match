package com.realmatch.backend.user.domain.model;

import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * 서비스 내부 기준 회원 계정을 표현하는 도메인 모델입니다.
 *
 * <p>TODO(PDF 설계서 기준): - 가입 직후 필수 정보와 프로필 완성 단계를 분리합니다. - is_profile_completed 기준을 프로필 사진, 자기소개,
 * 지역, 직업 등 2단계 완료 여부로 판단합니다. - 탈퇴는 soft delete를 우선 적용해 결제/신고/운영 로그 정합성을 유지합니다.
 */
@Getter
public class User {
  // TODO: 외부 기술 의존성 없이 상태와 도메인 규칙을 구현합니다.
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

    public User(Long userId, UUID userUUID,
                String status, String nickname, String email, String phoneNumber,
                String gender, Integer birthYear, boolean isProfileCompleted,
                OffsetDateTime lastLoginAt, OffsetDateTime createdAt, OffsetDateTime updatedAt, OffsetDateTime deletedAt) {
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
}
