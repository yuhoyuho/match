package com.realmatch.backend.community.adapter.out.persistence;

import com.realmatch.backend.community.application.port.out.CommunityPersistencePort;
import com.realmatch.backend.community.domain.model.CommunityComment;
import com.realmatch.backend.community.domain.model.CommunityPost;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

/** 커뮤니티 Persistence Adapter */
@Component
@RequiredArgsConstructor
public class CommunityPersistenceAdapter implements CommunityPersistencePort {

  private final CommunityPostJpaRepository communityPostJpaRepository;
  private final CommunityPostLikeJpaRepository communityPostLikeJpaRepository;
  private final CommunityCommentJpaRepository communityCommentJpaRepository;

  @Override
  public List<CommunityPost> findPosts(Long categoryId, Long cursor, int size) {
    return communityPostJpaRepository.findActivePosts(categoryId, cursor, PageRequest.of(0, size))
        .stream()
        .map(this::toDomain)
        .toList();
  }

  @Override
  public Optional<CommunityPost> findPost(Long postId) {
    return communityPostJpaRepository.findById(postId).map(this::toDomain);
  }

  @Override
  public CommunityPost savePost(CommunityPost post) {
    throw new UnsupportedOperationException("TODO: 게시글을 저장합니다.");
  }

  @Override
  public void saveComment(CommunityComment comment) {
    throw new UnsupportedOperationException("TODO: 댓글을 저장합니다.");
  }

  @Override
  public void likePost(Long userId, Long postId) {
    if (!communityPostJpaRepository.existsActivePost(postId)) {
      throw new NoSuchElementException("게시글이 존재하지 않습니다.");
    }

    int insertedRows = communityPostLikeJpaRepository.insertIgnore(postId, userId);
    if (insertedRows == 0) {
      return;
    }

    int updatedRows = communityPostJpaRepository.incrementLikeCount(postId);
    if (updatedRows == 0) {
      throw new NoSuchElementException("게시글이 존재하지 않습니다.");
    }
  }

  @Override
  public void saveReport(Long userId, String targetType, Long targetId, String reason) {
    throw new UnsupportedOperationException("TODO: 신고를 저장합니다.");
  }

  private CommunityPost toDomain(CommunityPostJpaEntity entity) {
    return new CommunityPost(
        entity.getPostId(),
        entity.getCategoryId(),
        entity.getAuthorId(),
        entity.getTitle(),
        entity.getBody(),
        entity.getStatus(),
        entity.getViewCount(),
        entity.getCommentCount(),
        entity.getLikeCount(),
        entity.getCreatedAt(),
        entity.getUpdatedAt(),
        entity.getDeletedAt());
  }
}
