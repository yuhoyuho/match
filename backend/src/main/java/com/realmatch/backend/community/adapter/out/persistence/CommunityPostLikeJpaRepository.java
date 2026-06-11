package com.realmatch.backend.community.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityPostLikeJpaRepository
    extends JpaRepository<CommunityPostLikeJpaEntity, CommunityPostLikeId> {

  @Modifying
  @Query(
      value =
          """
          insert into community_post_likes (post_id, user_id)
          values (:postId, :userId)
          on conflict (post_id, user_id) do nothing
          """,
      nativeQuery = true)
  int insertIgnore(@Param("postId") Long postId, @Param("userId") Long userId);
}
