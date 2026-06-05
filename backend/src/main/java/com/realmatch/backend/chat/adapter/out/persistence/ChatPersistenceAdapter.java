package com.realmatch.backend.chat.adapter.out.persistence;

import com.realmatch.backend.chat.application.port.out.ChatPersistencePort;
import com.realmatch.backend.chat.domain.model.ChatMessage;
import com.realmatch.backend.chat.domain.model.ChatRoom;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** 채팅 Persistence Adapter입니다. TODO: 채팅방/메시지/읽음 상태 저장과 unread count 계산을 구현합니다. */
@Component
@RequiredArgsConstructor
public class ChatPersistenceAdapter implements ChatPersistencePort {

  private final ChatRoomJpaRepository chatRoomJpaRepository;
  private final ChatMessageJpaRepository chatMessageJpaRepository;

  @Override
  public List<ChatRoom> findRooms(Long userId) {
    throw new UnsupportedOperationException("TODO: 내 채팅방 목록을 조회합니다.");
  }

  @Override
  public Optional<ChatRoom> findRoom(Long roomId) {
    throw new UnsupportedOperationException("TODO: 채팅방을 조회합니다.");
  }

  @Override
  public List<ChatMessage> findMessages(Long roomId, int size) {
    throw new UnsupportedOperationException("TODO: 메시지 목록을 조회합니다.");
  }

  @Override
  public ChatMessage saveMessage(ChatMessage message) {
    throw new UnsupportedOperationException("TODO: 메시지를 저장합니다.");
  }

  @Override
  public void saveReadStatus(Long userId, Long roomId, Long lastReadMessageId) {
    throw new UnsupportedOperationException("TODO: 읽음 상태를 저장합니다.");
  }
}
