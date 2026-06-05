package com.realmatch.backend.randomcall.adapter.out.persistence;

import com.realmatch.backend.randomcall.application.port.out.RandomCallPersistencePort;
import com.realmatch.backend.randomcall.domain.model.CallQueueTicket;
import com.realmatch.backend.randomcall.domain.model.CallSession;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** 랜덤통화 Persistence Adapter입니다. TODO: 대기열 보조 이력, 통화 세션, 세션 이벤트 저장/조회를 구현합니다. */
@Component
@RequiredArgsConstructor
public class RandomCallPersistenceAdapter implements RandomCallPersistencePort {

  private final CallQueueTicketJpaRepository callQueueTicketJpaRepository;
  private final CallSessionJpaRepository callSessionJpaRepository;

  @Override
  public CallQueueTicket saveQueueTicket(CallQueueTicket ticket) {
    throw new UnsupportedOperationException("TODO: 대기열 티켓을 저장합니다.");
  }

  @Override
  public Optional<CallSession> findSession(Long callId) {
    throw new UnsupportedOperationException("TODO: 통화 세션을 조회합니다.");
  }

  @Override
  public CallSession saveSession(CallSession session) {
    throw new UnsupportedOperationException("TODO: 통화 세션을 저장합니다.");
  }

  @Override
  public void saveSessionEvent(Long callId, String eventType, String reason) {
    throw new UnsupportedOperationException("TODO: 통화 이벤트를 저장합니다.");
  }
}
