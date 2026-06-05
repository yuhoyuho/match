package com.realmatch.backend.community.application.port.in;

import java.util.List;

/** 커뮤니티 입력 포트입니다. TODO: 게시글 목록/상세, 작성, 댓글, 좋아요, 신고 유스케이스를 구현합니다. */
public interface CommunityUseCase {

  List<PostResult> getPosts(PostQuery query);

  PostResult getPost(Long userId, Long postId);

  PostResult createPost(CreatePostCommand command);

  void createComment(CreateCommentCommand command);

  void likePost(Long userId, Long postId);

  void report(ReportCommand command);

  record PostQuery(Long userId, Long categoryId, String sort, int size) {}

  record CreatePostCommand(Long userId, Long categoryId, String title, String body) {}

  record CreateCommentCommand(Long userId, Long postId, Long parentCommentId, String body) {}

  record ReportCommand(Long userId, String targetType, Long targetId, String reason) {}

  record PostResult(
      Long postId,
      Long authorId,
      String title,
      String body,
      String status,
      int likeCount,
      int commentCount) {}
}
