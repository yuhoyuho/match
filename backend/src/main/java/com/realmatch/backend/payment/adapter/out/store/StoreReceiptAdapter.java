package com.realmatch.backend.payment.adapter.out.store;

import com.realmatch.backend.payment.application.port.out.StoreReceiptPort;
import org.springframework.stereotype.Component;

/** 스토어 영수증 검증 Adapter입니다. TODO: App Store와 Google Play 검증 API를 연동합니다. */
@Component
public class StoreReceiptAdapter implements StoreReceiptPort {

  @Override
  public boolean verify(String store, String purchaseToken, String receiptPayload) {
    throw new UnsupportedOperationException("TODO: 스토어 영수증을 검증합니다.");
  }
}
