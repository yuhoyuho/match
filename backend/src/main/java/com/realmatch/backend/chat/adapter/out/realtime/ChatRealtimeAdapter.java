package com.realmatch.backend.chat.adapter.out.realtime;

import com.realmatch.backend.chat.application.port.out.ChatRealtimePort;
import com.realmatch.backend.chat.domain.model.ChatMessage;
import org.springframework.stereotype.Component;

/** 채팅 실시간 전달 Adapter입니다. TODO: Redis Pub/Sub 또는 WebSocket 세션 라우팅을 구현합니다. */
@Component
public class ChatRealtimeAdapter implements ChatRealtimePort {

  @Override
  public void publish(ChatMessage message) {
    // TODO : WebSocket or Redis Pub/Sub 실시간 전달 구현
  }
}
