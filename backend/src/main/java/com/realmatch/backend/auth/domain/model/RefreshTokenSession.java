package com.realmatch.backend.auth.domain.model;

/**
 * 모바일 세션 유지와 로그아웃/재발급을 위한 Refresh Token 세션 모델입니다.
 *
 * <p>TODO(PDF 설계서 기준): - Apple, Google, Kakao, Naver provider_user_id를 기준으로 동일 계정을 식별합니다. - Refresh
 * Token은 원문 저장 없이 hash 저장, 만료, 폐기 정책을 적용합니다. - 다른 제공자 이메일이 같아도 자동 병합하지 않고 명시적 계정 연동만 허용합니다.
 */
public class RefreshTokenSession {

  // TODO: 외부 기술 의존성 없이 상태와 도메인 규칙을 구현합니다.
}
