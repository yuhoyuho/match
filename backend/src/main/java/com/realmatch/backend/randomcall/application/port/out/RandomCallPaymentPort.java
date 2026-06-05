package com.realmatch.backend.randomcall.application.port.out;

/** 랜덤통화 코인 연동 출력 포트입니다. TODO: 통화 시작 전 예약 차감, 실패/취소 시 해제 또는 환불을 결제 도메인과 연결합니다. */
public interface RandomCallPaymentPort {

  void reserveCallCoin(Long userId, Long callId);

  void releaseCallCoin(Long userId, Long callId, String reason);
}
