package com.realmatch.backend.chat.application.service;

import com.realmatch.backend.chat.application.port.in.ChatUseCase;
import com.realmatch.backend.chat.application.port.out.ChatPersistencePort;
import com.realmatch.backend.chat.application.port.out.ChatRealtimePort;
import java.util.List;

import com.realmatch.backend.chat.domain.model.ChatMessage;
import com.realmatch.backend.chat.domain.model.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 채팅 유스케이스 구현체입니다. TODO: 메시지 저장, 실시간 전달, 읽음 처리, 채팅방 상태 검증을 구현합니다. */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatApplicationService implements ChatUseCase {

  private final ChatPersistencePort chatPersistencePort;
  private final ChatRealtimePort chatRealtimePort;

  @Override
  public List<ChatRoomResult> getRooms(Long userId) {
    return chatPersistencePort.findRooms(userId)
            .stream()
            .map(this::toChatRoomResult)
            .toList();
  }

  @Override
  public List<ChatMessageResult> getMessages(Long userId, Long roomId, Long cursor, int size) {
    return chatPersistencePort.findMessages(roomId, cursor, size)
            .stream()
            .map(this::toChatMessageResult)
            .toList();
  }

  @Override
  @Transactional
  public ChatMessageResult sendMessage(SendMessageCommand command) {
    throw new UnsupportedOperationException("TODO: 메시지 저장 후 실시간 전달을 구현합니다.");
  }

  @Override
  @Transactional
  public void markRead(ReadCommand command) {
    throw new UnsupportedOperationException("TODO: 읽음 기준점을 갱신합니다.");
  }

  @Override
  @Transactional
  public void closeRoom(Long userId, Long roomId) {
    throw new UnsupportedOperationException("TODO: 채팅방 종료를 구현합니다.");
  }

  private ChatMessageResult toChatMessageResult(ChatMessage chat) {
    return new ChatMessageResult(
            chat.getMessageId(),
            chat.getRoomId(),
            chat.getSenderId(),
            chat.getMessageType(),
            chat.getContent(),
            chat.getStatus()
    );
  }

  private ChatRoomResult toChatRoomResult(ChatRoom room) {
    return new ChatRoomResult(
            room.getRoomId(),
            room.getMatchId(),
            room.getStatus(),
            room.getUnreadCount()
    );
  }
}
