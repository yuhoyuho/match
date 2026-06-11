package com.realmatch.backend.chat.domain.model;

import java.time.OffsetDateTime;
import lombok.Getter;

/**
 * 매칭 기반 1:1 채팅방을 표현하는 도메인 모델입니다.
 *
 * <p>TODO(PDF 설계서 기준): - 하나의 match_id에 하나의 chat_room만 생성합니다. - 메시지는 DB 저장 후 실시간 전달합니다. - 차단, 신고, 매칭
 * 종료 상태일 때 메시지 전송을 제한합니다.
 */
@Getter
public class ChatRoom {

  private final Long roomId;
  private final Long matchId;
  private final String roomType;
  private final String status;
  private final OffsetDateTime createdAt;
  private final OffsetDateTime closedAt;
  private final int unreadCount;

  public ChatRoom(
      Long roomId,
      Long matchId,
      String roomType,
      String status,
      OffsetDateTime createdAt,
      OffsetDateTime closedAt,
      int unreadCount) {
    this.roomId = roomId;
    this.matchId = matchId;
    this.roomType = roomType;
    this.status = status;
    this.createdAt = createdAt;
    this.closedAt = closedAt;
    this.unreadCount = unreadCount;
  }

  public boolean isActive() {
    return "ACTIVE".equals(status);
  }
}
