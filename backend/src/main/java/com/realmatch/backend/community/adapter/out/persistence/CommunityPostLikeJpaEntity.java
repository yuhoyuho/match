package com.realmatch.backend.community.adapter.out.persistence;

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
@Table(name = "community_post_likes")
@IdClass(CommunityPostLikeId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunityPostLikeJpaEntity {

  @Id
  @Column(name = "post_id", nullable = false)
  private Long postId;

  @Id
  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;
}
