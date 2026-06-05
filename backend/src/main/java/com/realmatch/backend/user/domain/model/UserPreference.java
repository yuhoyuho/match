package com.realmatch.backend.user.domain.model;

/**
 * 매칭 선호 조건과 랜덤통화 허용 여부를 표현하는 도메인 모델입니다.
 *
 * <p>TODO(PDF 설계서 기준): - 가입 직후 필수 정보와 프로필 완성 단계를 분리합니다. - is_profile_completed 기준을 프로필 사진, 자기소개,
 * 지역, 직업 등 2단계 완료 여부로 판단합니다. - 탈퇴는 soft delete를 우선 적용해 결제/신고/운영 로그 정합성을 유지합니다.
 */
public class UserPreference {

  // TODO: 외부 기술 의존성 없이 상태와 도메인 규칙을 구현합니다.
}
