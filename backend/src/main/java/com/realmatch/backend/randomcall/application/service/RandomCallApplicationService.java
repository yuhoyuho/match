package com.realmatch.backend.randomcall.application.service;

import com.realmatch.backend.randomcall.application.port.in.RandomCallUseCase;
import com.realmatch.backend.randomcall.application.port.out.RandomCallPaymentPort;
import com.realmatch.backend.randomcall.application.port.out.RandomCallPersistencePort;
import com.realmatch.backend.randomcall.application.port.out.RandomCallQueuePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 랜덤통화 유스케이스 구현체입니다. TODO: 진입 조건 확인, Redis 대기열, 분산 락, 세션 생성/종료, 코인 후처리를 구현합니다. */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RandomCallApplicationService implements RandomCallUseCase {

  private final RandomCallPersistencePort randomCallPersistencePort;
  private final RandomCallQueuePort randomCallQueuePort;
  private final RandomCallPaymentPort randomCallPaymentPort;

  @Override
  @Transactional
  public RandomCallStatusResult enter(EnterRandomCallCommand command) {
    throw new UnsupportedOperationException("TODO: 랜덤통화 대기열 진입을 구현합니다.");
  }

  @Override
  @Transactional
  public void cancel(Long userId) {
    throw new UnsupportedOperationException("TODO: 대기열 취소를 구현합니다.");
  }

  @Override
  public RandomCallStatusResult getStatus(Long userId) {
    throw new UnsupportedOperationException("TODO: 현재 통화 상태를 조회합니다.");
  }

  @Override
  public CallSessionResult getSession(Long userId, Long callId) {
    throw new UnsupportedOperationException("TODO: 통화 세션을 조회합니다.");
  }

  @Override
  @Transactional
  public void end(EndCallCommand command) {
    throw new UnsupportedOperationException("TODO: 통화 종료와 코인 후처리를 구현합니다.");
  }
}
