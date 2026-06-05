package com.realmatch.backend.matching.domain.model;

/**
 * 상호 좋아요로 성립한 매칭을 표현하는 도메인 모델입니다.
 *
 * <p>TODO(PDF 설계서 기준): - 선호 조건, 기노출, 좋아요/패스, 차단, 신고 관계를 추천 제외 조건으로 반영합니다. - LIKE/PASS 요청은 중복 요청에도
 * 일관된 결과가 나오도록 idempotent하게 처리합니다. - 상호 LIKE가 확인되면 매칭을 생성하고 채팅방 생성 흐름과 연결할 수 있게 합니다.
 */
public class Match {

  // TODO: 외부 기술 의존성 없이 상태와 도메인 규칙을 구현합니다.
}
