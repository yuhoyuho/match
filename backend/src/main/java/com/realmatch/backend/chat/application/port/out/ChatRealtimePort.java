package com.realmatch.backend.chat.application.port.out;

import com.realmatch.backend.chat.domain.model.ChatMessage;

/** 채팅 실시간 전달 출력 포트입니다. TODO: Redis Pub/Sub 또는 WebSocket 세션 라우팅을 구현합니다. */
public interface ChatRealtimePort {

  void publish(ChatMessage message);
}
