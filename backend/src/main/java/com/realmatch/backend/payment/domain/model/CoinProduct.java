package com.realmatch.backend.payment.domain.model;

/**
 * 판매 코인 상품을 표현하는 도메인 모델입니다.
 *
 * <p>TODO(PDF 설계서 기준): - 클라이언트 결제 결과를 그대로 신뢰하지 않고 서버에서 영수증을 검증합니다. - 동일 purchase_token은 한 번만 반영되도록
 * 멱등성을 보장합니다. - 잔액보다 coin_ledger 추적 가능성을 우선하고 환불/복구도 원장에 기록합니다.
 */
public class CoinProduct {

  // TODO: 외부 기술 의존성 없이 상태와 도메인 규칙을 구현합니다.
}
