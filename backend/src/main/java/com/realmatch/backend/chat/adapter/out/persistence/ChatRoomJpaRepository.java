package com.realmatch.backend.chat.adapter.out.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** ChatRoomJpaEntity 전용 Spring Data JPA 저장소입니다. TODO: PersistencePort가 요구하는 조회 메서드를 추가합니다. */
@Repository
public interface ChatRoomJpaRepository extends JpaRepository<ChatRoomJpaEntity, Long> {

  @Query(
      """
      select r
      from ChatRoomJpaEntity r
      where r.roomId in (
        select m.roomId
        from ChatRoomMemberJpaEntity m
        where m.userId = :userId
          and m.leftAt is null
      )
      order by (
        select max(m.createdAt)
        from ChatMessageJpaEntity m
        where m.roomId = r.roomId
      ) desc nulls last, r.createdAt desc
      """)
  List<ChatRoomJpaEntity> findRoomsByMemberId(@Param("userId") Long userId);
}
