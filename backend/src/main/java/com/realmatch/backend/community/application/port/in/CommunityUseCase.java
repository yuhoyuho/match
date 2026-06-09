package com.realmatch.backend.community.application.port.in;

import java.util.List;

/** 커뮤니티 입력 포트 */
public interface CommunityUseCase {

  List<PostResult> getPosts(PostQuery query);

  PostResult getPost(Long userId, Long postId);

  PostResult createPost(CreatePostCommand command);

  void createComment(CreateCommentCommand command);

  void likePost(Long userId, Long postId);

  void report(ReportCommand command);

  record PostQuery(Long userId, Long categoryId, Long cursor, int size) {}

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
