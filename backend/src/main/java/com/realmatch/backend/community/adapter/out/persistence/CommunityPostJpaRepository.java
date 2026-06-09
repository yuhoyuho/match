package com.realmatch.backend.community.adapter.out.persistence;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityPostJpaRepository extends JpaRepository<CommunityPostJpaEntity, Long> {

  @Query(
      """
      select p
      from CommunityPostJpaEntity p
      where p.status = 'ACTIVE'
        and p.deletedAt is null
        and (:categoryId is null or p.categoryId = :categoryId)
        and (:cursor is null or p.postId < :cursor)
      order by p.postId desc
      """)
  List<CommunityPostJpaEntity> findActivePosts(
      @Param("categoryId") Long categoryId,
      @Param("cursor") Long cursor,
      Pageable pageable);

  @Query(
      """
      select case when count(p) > 0 then true else false end
      from CommunityPostJpaEntity p
      where p.postId = :postId
        and p.status = 'ACTIVE'
        and p.deletedAt is null
      """)
  boolean existsActivePost(@Param("postId") Long postId);

  @Modifying
  @Query(
      """
      update CommunityPostJpaEntity p
      set p.likeCount = p.likeCount + 1
      where p.postId = :postId
        and p.status = 'ACTIVE'
        and p.deletedAt is null
      """)
  int incrementLikeCount(@Param("postId") Long postId);
}
