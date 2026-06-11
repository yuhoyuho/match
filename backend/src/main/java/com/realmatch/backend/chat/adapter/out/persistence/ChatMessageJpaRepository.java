package com.realmatch.backend.chat.adapter.out.persistence;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** ChatMessageJpaEntity 전용 Spring Data JPA 저장소입니다. TODO: PersistencePort가 요구하는 조회 메서드를 추가합니다. */
@Repository
public interface ChatMessageJpaRepository extends JpaRepository<ChatMessageJpaEntity, Long> {

  @Query(
      """
      select m
      from ChatMessageJpaEntity m
      where m.roomId = :roomId
        and (:cursor is null or m.messageId < :cursor)
      order by m.messageId desc
      """)
  List<ChatMessageJpaEntity> findMessages(
      @Param("roomId") Long roomId, @Param("cursor") Long cursor, Pageable pageable);

  boolean existsByMessageIdAndRoomId(Long messageId, Long roomId);
}
