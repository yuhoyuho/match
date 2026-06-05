package com.realmatch.backend.payment.application.service;

import com.realmatch.backend.payment.application.port.in.PaymentUseCase;
import com.realmatch.backend.payment.application.port.out.PaymentDedupePort;
import com.realmatch.backend.payment.application.port.out.PaymentPersistencePort;
import com.realmatch.backend.payment.application.port.out.StoreReceiptPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 결제/코인 유스케이스 구현체입니다. TODO: 영수증 검증, 중복 반영 방지, 결제 승인, 원장 적립/차감, 지갑 갱신을 구현합니다. */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentApplicationService implements PaymentUseCase {

  private final PaymentPersistencePort paymentPersistencePort;
  private final StoreReceiptPort storeReceiptPort;
  private final PaymentDedupePort paymentDedupePort;

  @Override
  public List<CoinProductResult> getProducts() {
    throw new UnsupportedOperationException("TODO: 활성 코인 상품을 조회합니다.");
  }

  @Override
  @Transactional
  public WalletResult verifyPayment(VerifyPaymentCommand command) {
    throw new UnsupportedOperationException("TODO: 영수증 검증과 코인 적립을 구현합니다.");
  }

  @Override
  public WalletResult getWallet(Long userId) {
    throw new UnsupportedOperationException("TODO: 지갑을 조회합니다.");
  }

  @Override
  public List<CoinLedgerResult> getLedger(Long userId) {
    throw new UnsupportedOperationException("TODO: 코인 원장을 조회합니다.");
  }

  @Override
  @Transactional
  public WalletResult useCoin(UseCoinCommand command) {
    throw new UnsupportedOperationException("TODO: 코인 사용/예약 차감을 구현합니다.");
  }

  @Override
  @Transactional
  public void requestRefund(RefundCommand command) {
    throw new UnsupportedOperationException("TODO: 환불 요청을 접수합니다.");
  }
}
