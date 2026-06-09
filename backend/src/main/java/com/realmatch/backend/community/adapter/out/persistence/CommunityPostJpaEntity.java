package com.realmatch.backend.community.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * community_posts 테이블과 매핑되는 JPA 엔티티
 */
@Getter
@Entity
@Table(name = "community_posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunityPostJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "post_id")
  private Long postId;

  @Column(name = "category_id", nullable = false)
  private Long categoryId;

  @Column(name = "author_id", nullable = false)
  private Long authorId;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "body", nullable = false)
  private String body;

  @Column(name = "status", nullable = false)
  private String status;

  @Column(name = "view_count", nullable = false)
  private int viewCount;

  @Column(name = "comment_count", nullable = false)
  private int commentCount;

  @Column(name = "like_count", nullable = false)
  private int likeCount;

  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private OffsetDateTime updatedAt;

  @Column(name = "deleted_at")
  private OffsetDateTime deletedAt;
}
