package com.realmatch.backend.community.domain.model;

import java.time.OffsetDateTime;
import lombok.Getter;

/**
 * 커뮤니티 게시글 도메인 모델
 */
@Getter
public class CommunityPost {

  private final Long postId;
  private final Long categoryId;
  private final Long authorId;
  private final String title;
  private final String body;
  private final String status;
  private final int viewCount;
  private final int commentCount;
  private final int likeCount;
  private final OffsetDateTime createdAt;
  private final OffsetDateTime updatedAt;
  private final OffsetDateTime deletedAt;

  public CommunityPost(
      Long postId,
      Long categoryId,
      Long authorId,
      String title,
      String body,
      String status,
      int viewCount,
      int commentCount,
      int likeCount,
      OffsetDateTime createdAt,
      OffsetDateTime updatedAt,
      OffsetDateTime deletedAt) {
    this.postId = postId;
    this.categoryId = categoryId;
    this.authorId = authorId;
    this.title = title;
    this.body = body;
    this.status = status;
    this.viewCount = viewCount;
    this.commentCount = commentCount;
    this.likeCount = likeCount;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.deletedAt = deletedAt;
  }
}
