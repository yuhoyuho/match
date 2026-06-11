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
@Table(name = "chat_room_members")
@IdClass(ChatRoomMemberId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomMemberJpaEntity {

  @Id
  @Column(name = "room_id", nullable = false)
  private Long roomId;

  @Id
  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "joined_at", nullable = false)
  private OffsetDateTime joinedAt;

  @Column(name = "left_at")
  private OffsetDateTime leftAt;
}
