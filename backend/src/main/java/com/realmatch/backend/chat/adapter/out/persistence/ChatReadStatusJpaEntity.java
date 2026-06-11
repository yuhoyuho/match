package com.realmatch.backend.chat.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "chat_read_status")
@IdClass(ChatReadStatusId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatReadStatusJpaEntity {

  @Id
  @Column(name = "room_id", nullable = false)
  private Long roomId;

  @Id
  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "last_read_message_id")
  private Long lastReadMessageId;

  @Column(name = "read_at", nullable = false)
  private OffsetDateTime readAt;

  @Column(name = "updated_at", nullable = false)
  private OffsetDateTime updatedAt;

  public static ChatReadStatusJpaEntity create(
      Long roomId, Long userId, Long lastReadMessageId, OffsetDateTime now) {
    ChatReadStatusJpaEntity entity = new ChatReadStatusJpaEntity();
    entity.roomId = roomId;
    entity.userId = userId;
    entity.lastReadMessageId = lastReadMessageId;
    entity.readAt = now;
    entity.updatedAt = now;
    return entity;
  }

  public void updateLastReadMessageId(Long lastReadMessageId, OffsetDateTime now) {
    this.lastReadMessageId = lastReadMessageId;
    this.readAt = now;
    this.updatedAt = now;
  }
}
