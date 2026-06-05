package com.realmatch.backend.chat.application.port.out;

import com.realmatch.backend.chat.domain.model.ChatMessage;
import com.realmatch.backend.chat.domain.model.ChatRoom;
import java.util.List;
import java.util.Optional;

/** 채팅 영속성 출력 포트입니다. TODO: 채팅방, 메시지, 읽음 상태, 메시지 신고 저장/조회 기능을 구현합니다. */
public interface ChatPersistencePort {

  List<ChatRoom> findRooms(Long userId);

  Optional<ChatRoom> findRoom(Long roomId);

  List<ChatMessage> findMessages(Long roomId, int size);

  ChatMessage saveMessage(ChatMessage message);

  void saveReadStatus(Long userId, Long roomId, Long lastReadMessageId);
}
