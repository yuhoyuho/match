package com.realmatch.backend.randomcall.application.port.out;

/** Redis 기반 랜덤통화 대기열/락 출력 포트입니다. TODO: call:queue, call:lock, user heartbeat 키를 구현합니다. */
public interface RandomCallQueuePort {

  void enqueue(Long userId, String queueType);

  void cancel(Long userId);

  boolean acquireUserLocks(Long userAId, Long userBId);
}
