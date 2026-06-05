package com.realmatch.backend.admin.domain.model;

/**
 * 운영자 계정과 권한 상태를 표현하는 도메인 모델입니다.
 *
 * <p>TODO(PDF 설계서 기준): - 운영자 API는 RBAC와 최소 권한 원칙을 적용합니다. - 제재, 환불, 숨김, 복구 같은 민감 액션은 사유와 함께 감사 로그를
 * 남깁니다. - 신고 누적, 결제 이상, 반복 차단 사용자 등을 우선 검토 대상으로 관리합니다.
 */
public class AdminUser {

  // TODO: 외부 기술 의존성 없이 상태와 도메인 규칙을 구현합니다.
}
