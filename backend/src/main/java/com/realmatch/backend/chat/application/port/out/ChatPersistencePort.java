package com.realmatch.backend.chat.application.port.out;

import com.realmatch.backend.chat.domain.model.ChatMessage;
import com.realmatch.backend.chat.domain.model.ChatRoom;
import java.util.List;
import java.util.Optional;

/** 채팅 영속성 출력 포트 */
public interface ChatPersistencePort {

  List<ChatRoom> findRooms(Long userId);

  Optional<ChatRoom> findRoom(Long roomId);

  List<ChatMessage> findMessages(Long roomId, Long cursor, int size);

  ChatMessage saveMessage(ChatMessage message);

  void saveReadStatus(Long userId, Long roomId, Long lastReadMessageId);

  boolean existsRoomMember(Long roomId, Long userId);
}
