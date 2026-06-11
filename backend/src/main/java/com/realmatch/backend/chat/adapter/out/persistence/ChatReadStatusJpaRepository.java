package com.realmatch.backend.chat.adapter.out.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatReadStatusJpaRepository
    extends JpaRepository<ChatReadStatusJpaEntity, ChatReadStatusId> {

  Optional<ChatReadStatusJpaEntity> findByRoomIdAndUserId(Long roomId, Long userId);
}
