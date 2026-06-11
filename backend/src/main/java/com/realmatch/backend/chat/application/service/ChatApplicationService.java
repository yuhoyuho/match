package com.realmatch.backend.chat.application.service;

import com.realmatch.backend.chat.application.port.in.ChatUseCase;
import com.realmatch.backend.chat.application.port.out.ChatPersistencePort;
import com.realmatch.backend.chat.application.port.out.ChatRealtimePort;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import com.realmatch.backend.chat.domain.model.ChatMessage;
import com.realmatch.backend.chat.domain.model.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 채팅 유스케이스 구현체 */
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
    ChatRoom room = chatPersistencePort.findRoom(command.roomId())
            .orElseThrow(() -> new NoSuchElementException("채팅방을 찾을 수 없습니다."));

    if(!chatPersistencePort.existsRoomMember(command.roomId(), command.userId())) {
      throw new IllegalStateException("채팅방 멤버가 아닙니다.");
    }

    if(!room.isActive()) {
      throw new IllegalStateException("종료된 채팅방입니다.");
    }

    ChatMessage message = ChatMessage.create(
            command.roomId(),
            command.userId(),
            command.messageType(),
            command.content(),
            OffsetDateTime.now()
    );

    ChatMessage savedMessage = chatPersistencePort.saveMessage(message);
    chatRealtimePort.publish(savedMessage);

    return toChatMessageResult(savedMessage);
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
