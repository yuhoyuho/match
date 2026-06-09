package com.realmatch.backend.community.application.service;

import com.realmatch.backend.community.application.port.in.CommunityUseCase;
import com.realmatch.backend.community.application.port.out.CommunityCachePort;
import com.realmatch.backend.community.application.port.out.CommunityPersistencePort;
import com.realmatch.backend.community.domain.model.CommunityPost;
import java.util.List;
import java.util.NoSuchElementException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 커뮤니티 유스케이스 구현체 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityApplicationService implements CommunityUseCase {

  private static final int DEFAULT_PAGE_SIZE = 20;
  private static final int MAX_PAGE_SIZE = 20;

  private final CommunityPersistencePort communityPersistencePort;
  private final CommunityCachePort communityCachePort;

  @Override
  public List<PostResult> getPosts(PostQuery query) {
    int size = normalizeSize(query.size());

    return communityPersistencePort.findPosts(query.categoryId(), query.cursor(), size)
            .stream()
            .map(this::toPostResult)
            .toList();
  }

  @Override
  public PostResult getPost(Long userId, Long postId) {
    CommunityPost post = communityPersistencePort.findPost(postId)
            .orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다."));

    // TODO: 조회 수 증가 기능 필요
    return toPostResult(post);
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
    communityPersistencePort.likePost(userId, postId);
  }

  @Override
  @Transactional
  public void report(ReportCommand command) {
    throw new UnsupportedOperationException("TODO: 신고를 접수합니다.");
  }

  private int normalizeSize(int size) {
    if (size <= 0) {
      return DEFAULT_PAGE_SIZE;
    }
    return Math.min(size, MAX_PAGE_SIZE);
  }

  private PostResult toPostResult(CommunityPost post) {
    return new PostResult(
        post.getPostId(),
        post.getAuthorId(),
        post.getTitle(),
        post.getBody(),
        post.getStatus(),
        post.getLikeCount(),
        post.getCommentCount());
  }
}
