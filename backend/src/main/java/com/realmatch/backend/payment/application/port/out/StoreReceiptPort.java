package com.realmatch.backend.payment.application.port.out;

/** 스토어 영수증 검증 출력 포트입니다. TODO: App Store와 Google Play 구매 토큰 검증을 구현합니다. */
public interface StoreReceiptPort {

  boolean verify(String store, String purchaseToken, String receiptPayload);
}
