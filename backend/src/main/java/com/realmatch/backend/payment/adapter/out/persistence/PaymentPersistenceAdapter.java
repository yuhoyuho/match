package com.realmatch.backend.payment.adapter.out.persistence;

import com.realmatch.backend.payment.application.port.out.PaymentPersistencePort;
import com.realmatch.backend.payment.domain.model.CoinLedger;
import com.realmatch.backend.payment.domain.model.CoinProduct;
import com.realmatch.backend.payment.domain.model.CoinWallet;
import com.realmatch.backend.payment.domain.model.PaymentOrder;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** 결제/코인 Persistence Adapter입니다. TODO: 결제 승인과 원장/지갑 갱신의 트랜잭션 정합성을 구현합니다. */
@Component
@RequiredArgsConstructor
public class PaymentPersistenceAdapter implements PaymentPersistencePort {

  private final CoinProductJpaRepository coinProductJpaRepository;
  private final PaymentOrderJpaRepository paymentOrderJpaRepository;
  private final CoinWalletJpaRepository coinWalletJpaRepository;
  private final CoinLedgerJpaRepository coinLedgerJpaRepository;

  @Override
  public List<CoinProduct> findActiveProducts() {
    throw new UnsupportedOperationException("TODO: 활성 상품을 조회합니다.");
  }

  @Override
  public Optional<PaymentOrder> findPaymentByPurchaseToken(String store, String purchaseToken) {
    throw new UnsupportedOperationException("TODO: 구매 토큰으로 결제를 조회합니다.");
  }

  @Override
  public PaymentOrder savePaymentOrder(PaymentOrder order) {
    throw new UnsupportedOperationException("TODO: 결제 주문을 저장합니다.");
  }

  @Override
  public Optional<CoinWallet> findWallet(Long userId) {
    throw new UnsupportedOperationException("TODO: 지갑을 조회합니다.");
  }

  @Override
  public CoinWallet saveWallet(CoinWallet wallet) {
    throw new UnsupportedOperationException("TODO: 지갑을 저장합니다.");
  }

  @Override
  public CoinLedger saveLedger(CoinLedger ledger) {
    throw new UnsupportedOperationException("TODO: 원장을 저장합니다.");
  }

  @Override
  public List<CoinLedger> findLedger(Long userId) {
    throw new UnsupportedOperationException("TODO: 원장 목록을 조회합니다.");
  }
}
