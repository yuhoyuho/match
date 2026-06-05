package com.realmatch.backend.payment.application.port.out;

import com.realmatch.backend.payment.domain.model.CoinLedger;
import com.realmatch.backend.payment.domain.model.CoinProduct;
import com.realmatch.backend.payment.domain.model.CoinWallet;
import com.realmatch.backend.payment.domain.model.PaymentOrder;
import java.util.List;
import java.util.Optional;

/** 결제/코인 영속성 출력 포트입니다. TODO: 상품, 주문, 영수증, 지갑, 원장, 예약 차감, 환불 요청 저장/조회를 구현합니다. */
public interface PaymentPersistencePort {

  List<CoinProduct> findActiveProducts();

  Optional<PaymentOrder> findPaymentByPurchaseToken(String store, String purchaseToken);

  PaymentOrder savePaymentOrder(PaymentOrder order);

  Optional<CoinWallet> findWallet(Long userId);

  CoinWallet saveWallet(CoinWallet wallet);

  CoinLedger saveLedger(CoinLedger ledger);

  List<CoinLedger> findLedger(Long userId);
}
