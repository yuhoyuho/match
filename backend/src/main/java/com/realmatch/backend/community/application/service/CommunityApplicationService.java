package com.realmatch.backend.community.application.service;

import com.realmatch.backend.community.application.port.in.CommunityUseCase;
import com.realmatch.backend.community.application.port.out.CommunityCachePort;
import com.realmatch.backend.community.application.port.out.CommunityPersistencePort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 커뮤니티 유스케이스 구현체입니다. TODO: 목록 제외 조건, 작성 제한, 좋아요 idempotency, 신고 접수를 구현합니다. */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityApplicationService implements CommunityUseCase {

  private final CommunityPersistencePort communityPersistencePort;
  private final CommunityCachePort communityCachePort;

  @Override
  public List<PostResult> getPosts(PostQuery query) {
    throw new UnsupportedOperationException("TODO: 게시글 목록을 조회합니다.");
  }

  @Override
  public PostResult getPost(Long userId, Long postId) {
    throw new UnsupportedOperationException("TODO: 게시글 상세를 조회합니다.");
  }

  @Override
  @Transactional
  public PostResult createPost(CreatePostCommand command) {
    throw new UnsupportedOperationException("TODO: 게시글을 작성합니다.");
  }

  @Override
  @Transactional
  public void createComment(CreateCommentCommand command) {
    throw new UnsupportedOperationException("TODO: 댓글을 작성합니다.");
  }

  @Override
  @Transactional
  public void likePost(Long userId, Long postId) {
    throw new UnsupportedOperationException("TODO: 좋아요를 처리합니다.");
  }

  @Override
  @Transactional
  public void report(ReportCommand command) {
    throw new UnsupportedOperationException("TODO: 신고를 접수합니다.");
  }
}
