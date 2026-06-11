package com.realmatch.backend.chat.application.port.in;

import java.util.List;

/** 채팅 입력 포트 */
public interface ChatUseCase {

  List<ChatRoomResult> getRooms(Long userId);

  List<ChatMessageResult> getMessages(Long userId, Long roomId, Long cursor, int size);

  ChatMessageResult sendMessage(SendMessageCommand command);

  void markRead(ReadCommand command);

  void closeRoom(Long userId, Long roomId);

  record SendMessageCommand(Long userId, Long roomId, String messageType, String content) {}

  record ReadCommand(Long userId, Long roomId, Long lastReadMessageId) {}

  record ChatRoomResult(Long roomId, Long matchId, String status, int unreadCount) {}

  record ChatMessageResult(
      Long messageId,
      Long roomId,
      Long senderId,
      String messageType,
      String content,
      String status) {}
}
