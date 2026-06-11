package com.realmatch.backend.chat.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.realmatch.backend.chat.domain.model.ChatMessage;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * chat_messages 테이블과 매핑되는 JPA 엔티티입니다.
 *
 * <p>TODO: Flyway 스키마 컬럼을 기준으로 필요한 필드를 추가하고 도메인 모델과의 매핑을 PersistenceAdapter에서 처리합니다.
 */
@Getter
@Entity
@Table(name = "chat_messages")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessageJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "message_id")
  private Long messageId;

  @Column(name = "room_id", nullable = false)
  private Long roomId;

  @Column(name = "sender_id", nullable = false)
  private Long senderId;

  @Column(name = "message_type", nullable = false)
  private String messageType;

  @Column(name = "content", nullable = false)
  private String content;

  @Column(name = "status", nullable = false)
  private String status;

  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

  @Column(name = "delivered_at")
  private OffsetDateTime deliveredAt;

  public static ChatMessageJpaEntity from(ChatMessage message) {
    ChatMessageJpaEntity entity = new ChatMessageJpaEntity();
    entity.messageId = message.getMessageId();
    entity.roomId = message.getRoomId();
    entity.senderId = message.getSenderId();
    entity.messageType = message.getMessageType();
    entity.content = message.getContent();
    entity.status = message.getStatus();
    entity.createdAt = message.getCreatedAt();
    entity.deliveredAt = message.getDeliveredAt();
    return entity;
  }
}
