package com.realmatch.backend.payment.application.port.in;

import java.util.List;

/** 결제/코인 입력 포트입니다. TODO: 상품 조회, 결제 검증, 지갑/원장 조회, 코인 사용, 환불 요청 유스케이스를 구현합니다. */
public interface PaymentUseCase {

  List<CoinProductResult> getProducts();

  WalletResult verifyPayment(VerifyPaymentCommand command);

  WalletResult getWallet(Long userId);

  List<CoinLedgerResult> getLedger(Long userId);

  WalletResult useCoin(UseCoinCommand command);

  void requestRefund(RefundCommand command);

  record VerifyPaymentCommand(
      Long userId, Long productId, String store, String purchaseToken, String receiptPayload) {}

  record UseCoinCommand(
      Long userId,
      String featureType,
      int amount,
      String refType,
      Long refId,
      String idempotencyKey) {}

  record RefundCommand(Long userId, Long paymentId, String reason) {}

  record CoinProductResult(
      Long productId,
      String productCode,
      int coinAmount,
      int bonusCoinAmount,
      String priceAmount,
      String currency) {}

  record WalletResult(Long userId, int balance) {}

  record CoinLedgerResult(
      Long ledgerId, int delta, int balanceAfter, String reason, String refType, Long refId) {}
}
