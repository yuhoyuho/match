package com.realmatch.backend.chat.adapter.out.persistence;

import com.realmatch.backend.chat.application.port.out.ChatPersistencePort;
import com.realmatch.backend.chat.domain.model.ChatMessage;
import com.realmatch.backend.chat.domain.model.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/** 채팅 Persistence Adapter */
@Component
@RequiredArgsConstructor
public class ChatPersistenceAdapter implements ChatPersistencePort {

  private final ChatRoomJpaRepository chatRoomJpaRepository;
  private final ChatMessageJpaRepository chatMessageJpaRepository;
  private final ChatRoomMemberJpaRepository chatRoomMemberJpaRepository;
  private final ChatReadStatusJpaRepository chatReadStatusJpaRepository;

  @Override
  public List<ChatRoom> findRooms(Long userId) {
    return chatRoomJpaRepository.findRoomsByMemberId(userId)
            .stream()
            .map(entity -> toDomain(entity, 0))
            .toList();
  }

  @Override
  public Optional<ChatRoom> findRoom(Long roomId) {
    return chatRoomJpaRepository.findById(roomId)
            .map(entity -> toDomain(entity, 0));
  }

  @Override
  public List<ChatMessage> findMessages(Long roomId, Long cursor, int size) {
    return chatMessageJpaRepository.findMessages(roomId, cursor, PageRequest.of(0, size)).stream()
        .map(this::toDomain)
        .toList();
  }

  @Override
  public ChatMessage saveMessage(ChatMessage message) {
    ChatMessageJpaEntity savedMessage = chatMessageJpaRepository.save(ChatMessageJpaEntity.from(message));

    return toDomain(savedMessage);
  }

  @Override
  public void saveReadStatus(Long userId, Long roomId, Long lastReadMessageId) {
    throw new UnsupportedOperationException("TODO: 읽음 상태를 저장합니다.");
  }

  @Override
  public boolean existsRoomMember(Long roomId, Long userId) {
    return chatRoomMemberJpaRepository.existsByRoomIdAndUserIdAndLeftAtIsNull(roomId, userId);
  }

  private ChatMessage toDomain(ChatMessageJpaEntity entity) {
    return new ChatMessage(
        entity.getMessageId(),
        entity.getRoomId(),
        entity.getSenderId(),
        entity.getMessageType(),
        entity.getContent(),
        entity.getStatus(),
        entity.getCreatedAt(),
        entity.getDeliveredAt());
  }

  private ChatRoom toDomain(ChatRoomJpaEntity entity, int unreadCount) {
    return new ChatRoom(
            entity.getRoomId(),
            entity.getMatchId(),
            entity.getRoomType(),
            entity.getStatus(),
            entity.getCreatedAt(),
            entity.getClosedAt(),
            unreadCount
    );
  }
}
