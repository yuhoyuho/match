package com.realmatch.backend.randomcall.application.port.in;

/** 랜덤통화 입력 포트입니다. TODO: 진입, 취소, 상태 조회, 세션 상세, 종료 유스케이스를 구현합니다. */
public interface RandomCallUseCase {

  RandomCallStatusResult enter(EnterRandomCallCommand command);

  void cancel(Long userId);

  RandomCallStatusResult getStatus(Long userId);

  CallSessionResult getSession(Long userId, Long callId);

  void end(EndCallCommand command);

  record EnterRandomCallCommand(
      Long userId, String queueType, String regionCode, String preferredGender) {}

  record EndCallCommand(Long userId, Long callId, String reason) {}

  record RandomCallStatusResult(Long userId, String status, Long callId, String queueType) {}

  record CallSessionResult(
      Long callId, Long callerId, Long calleeId, String status, String failureReason) {}
}
