package com.realmatch.backend.payment.adapter.in.web;

import com.realmatch.backend.common.Routes;
import com.realmatch.backend.payment.application.port.in.PaymentUseCase;
import com.realmatch.backend.payment.application.port.in.PaymentUseCase.CoinLedgerResult;
import com.realmatch.backend.payment.application.port.in.PaymentUseCase.CoinProductResult;
import com.realmatch.backend.payment.application.port.in.PaymentUseCase.RefundCommand;
import com.realmatch.backend.payment.application.port.in.PaymentUseCase.UseCoinCommand;
import com.realmatch.backend.payment.application.port.in.PaymentUseCase.VerifyPaymentCommand;
import com.realmatch.backend.payment.application.port.in.PaymentUseCase.WalletResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** 결제/코인 Web Adapter입니다. TODO: 결제 검증 요청, idempotency key, 환불 사유 입력값 검증을 구현합니다. */
@RestController
@RequiredArgsConstructor
public class PaymentController {

  private final PaymentUseCase paymentUseCase;

  @GetMapping(Routes.PAYMENT_PRODUCTS)
  public List<CoinProductResult> products() {
    return paymentUseCase.getProducts();
  }

  @PostMapping(Routes.PAYMENT_VERIFY)
  public WalletResult verify(
      @RequestHeader(name = "X-USER-ID", required = false) Long userId,
      @RequestBody VerifyPaymentRequest request) {
    return paymentUseCase.verifyPayment(request.toCommand(userId));
  }

  @GetMapping(Routes.COINS_WALLET)
  public WalletResult wallet(@RequestHeader(name = "X-USER-ID", required = false) Long userId) {
    return paymentUseCase.getWallet(userId);
  }

  @GetMapping(Routes.COINS_LEDGER)
  public List<CoinLedgerResult> ledger(
      @RequestHeader(name = "X-USER-ID", required = false) Long userId) {
    return paymentUseCase.getLedger(userId);
  }

  @PostMapping(Routes.COINS_USE)
  public WalletResult useCoin(
      @RequestHeader(name = "X-USER-ID", required = false) Long userId,
      @RequestBody UseCoinRequest request) {
    return paymentUseCase.useCoin(request.toCommand(userId));
  }

  @PostMapping(Routes.PAYMENT_REFUNDS)
  public void refund(
      @RequestHeader(name = "X-USER-ID", required = false) Long userId,
      @RequestParam Long paymentId,
      @RequestParam(required = false) String reason) {
    paymentUseCase.requestRefund(new RefundCommand(userId, paymentId, reason));
  }

  public record VerifyPaymentRequest(
      Long productId, String store, String purchaseToken, String receiptPayload) {
    VerifyPaymentCommand toCommand(Long userId) {
      return new VerifyPaymentCommand(userId, productId, store, purchaseToken, receiptPayload);
    }
  }

  public record UseCoinRequest(
      String featureType, int amount, String refType, Long refId, String idempotencyKey) {
    UseCoinCommand toCommand(Long userId) {
      return new UseCoinCommand(userId, featureType, amount, refType, refId, idempotencyKey);
    }
  }
}
