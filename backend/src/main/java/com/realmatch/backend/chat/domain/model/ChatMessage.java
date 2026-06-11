package com.realmatch.backend.chat.domain.model;

import java.time.OffsetDateTime;
import lombok.Getter;

/**
 * 채팅 메시지 저장과 전달 상태를 표현하는 도메인 모델입니다.
 *
 * <p>TODO(PDF 설계서 기준): - 하나의 match_id에 하나의 chat_room만 생성합니다. - 메시지는 DB 저장 후 실시간 전달합니다. - 차단, 신고, 매칭
 * 종료 상태일 때 메시지 전송을 제한합니다.
 */
@Getter
public class ChatMessage {

  private final Long messageId;
  private final Long roomId;
  private final Long senderId;
  private final String messageType;
  private final String content;
  private final String status;
  private final OffsetDateTime createdAt;
  private final OffsetDateTime deliveredAt;

  public ChatMessage(
      Long messageId,
      Long roomId,
      Long senderId,
      String messageType,
      String content,
      String status,
      OffsetDateTime createdAt,
      OffsetDateTime deliveredAt) {
    this.messageId = messageId;
    this.roomId = roomId;
    this.senderId = senderId;
    this.messageType = messageType;
    this.content = content;
    this.status = status;
    this.createdAt = createdAt;
    this.deliveredAt = deliveredAt;
  }

  public static ChatMessage create(
      Long roomId, Long senderId, String messageType, String content, OffsetDateTime now) {
    return new ChatMessage(null, roomId, senderId, messageType, content, "SENT", now, null);
  }
}
