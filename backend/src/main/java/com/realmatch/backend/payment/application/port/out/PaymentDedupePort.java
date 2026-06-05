package com.realmatch.backend.payment.application.port.out;

/** 결제 중복 처리 방지 출력 포트입니다. TODO: Redis payment:dedupe:{store}:{purchaseToken} 키를 구현합니다. */
public interface PaymentDedupePort {

  boolean acquire(String store, String purchaseToken);
}
