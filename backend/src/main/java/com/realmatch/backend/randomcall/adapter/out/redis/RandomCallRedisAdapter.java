package com.realmatch.backend.randomcall.adapter.out.redis;

import com.realmatch.backend.randomcall.application.port.out.RandomCallQueuePort;
import org.springframework.stereotype.Component;

/** 랜덤통화 Redis Adapter입니다. TODO: 대기열, 사용자 상태, 분산 락, TTL 기반 정리를 구현합니다. */
@Component
public class RandomCallRedisAdapter implements RandomCallQueuePort {

  @Override
  public void enqueue(Long userId, String queueType) {
    throw new UnsupportedOperationException("TODO: Redis 대기열에 등록합니다.");
  }

  @Override
  public void cancel(Long userId) {
    throw new UnsupportedOperationException("TODO: Redis 대기열에서 제거합니다.");
  }

  @Override
  public boolean acquireUserLocks(Long userAId, Long userBId) {
    throw new UnsupportedOperationException("TODO: 통화 중복 연결 방지 락을 획득합니다.");
  }
}
