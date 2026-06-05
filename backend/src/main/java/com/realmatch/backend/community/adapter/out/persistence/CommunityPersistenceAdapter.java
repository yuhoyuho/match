package com.realmatch.backend.community.adapter.out.persistence;

import com.realmatch.backend.community.application.port.out.CommunityPersistencePort;
import com.realmatch.backend.community.domain.model.CommunityComment;
import com.realmatch.backend.community.domain.model.CommunityPost;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** 커뮤니티 Persistence Adapter입니다. TODO: 게시글/댓글/신고 저장과 목록 제외 조건 쿼리를 구현합니다. */
@Component
@RequiredArgsConstructor
public class CommunityPersistenceAdapter implements CommunityPersistencePort {

  private final CommunityPostJpaRepository communityPostJpaRepository;
  private final CommunityCommentJpaRepository communityCommentJpaRepository;

  @Override
  public List<CommunityPost> findPosts(Long categoryId, String sort, int size) {
    throw new UnsupportedOperationException("TODO: 게시글 목록을 조회합니다.");
  }

  @Override
  public Optional<CommunityPost> findPost(Long postId) {
    throw new UnsupportedOperationException("TODO: 게시글을 조회합니다.");
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
    throw new UnsupportedOperationException("TODO: 좋아요를 저장합니다.");
  }

  @Override
  public void saveReport(Long userId, String targetType, Long targetId, String reason) {
    throw new UnsupportedOperationException("TODO: 신고를 저장합니다.");
  }
}
