package com.realmatch.backend.payment.adapter.out.redis;

import com.realmatch.backend.payment.application.port.out.PaymentDedupePort;
import org.springframework.stereotype.Component;

/** 결제 중복 처리 방지 Redis Adapter입니다. TODO: payment:dedupe 키를 TTL과 함께 관리합니다. */
@Component
public class PaymentDedupeAdapter implements PaymentDedupePort {

  @Override
  public boolean acquire(String store, String purchaseToken) {
    throw new UnsupportedOperationException("TODO: 중복 결제 방지 키를 획득합니다.");
  }
}
