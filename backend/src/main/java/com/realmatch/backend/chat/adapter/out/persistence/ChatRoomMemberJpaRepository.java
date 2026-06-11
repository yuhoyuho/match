package com.realmatch.backend.chat.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomMemberJpaRepository
    extends JpaRepository<ChatRoomMemberJpaEntity, ChatRoomMemberId> {

  boolean existsByRoomIdAndUserIdAndLeftAtIsNull(Long roomId, Long userId);
}
