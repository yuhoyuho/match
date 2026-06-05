package com.realmatch.backend.randomcall.application.port.out;

import com.realmatch.backend.randomcall.domain.model.CallQueueTicket;
import com.realmatch.backend.randomcall.domain.model.CallSession;
import java.util.Optional;

/** 랜덤통화 영속성 출력 포트입니다. TODO: 대기열 티켓, 통화 세션, 세션 이벤트, 사용자 통화 상태 저장/조회를 구현합니다. */
public interface RandomCallPersistencePort {

  CallQueueTicket saveQueueTicket(CallQueueTicket ticket);

  Optional<CallSession> findSession(Long callId);

  CallSession saveSession(CallSession session);

  void saveSessionEvent(Long callId, String eventType, String reason);
}
