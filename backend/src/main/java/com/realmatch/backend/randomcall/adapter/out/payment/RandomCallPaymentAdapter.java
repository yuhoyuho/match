package com.realmatch.backend.randomcall.adapter.out.payment;

import com.realmatch.backend.randomcall.application.port.out.RandomCallPaymentPort;
import org.springframework.stereotype.Component;

/** 랜덤통화 결제 연동 Adapter입니다. TODO: PaymentUseCase 또는 PaymentPort와 연결해 통화 코인 예약/해제를 구현합니다. */
@Component
public class RandomCallPaymentAdapter implements RandomCallPaymentPort {

  @Override
  public void reserveCallCoin(Long userId, Long callId) {
    throw new UnsupportedOperationException("TODO: 통화 코인을 예약 차감합니다.");
  }

  @Override
  public void releaseCallCoin(Long userId, Long callId, String reason) {
    throw new UnsupportedOperationException("TODO: 통화 실패/취소 시 예약 차감을 해제합니다.");
  }
}
