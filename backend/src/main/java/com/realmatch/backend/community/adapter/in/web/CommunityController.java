package com.realmatch.backend.community.adapter.in.web;

import com.realmatch.backend.common.Routes;
import com.realmatch.backend.community.application.port.in.CommunityUseCase;
import com.realmatch.backend.community.application.port.in.CommunityUseCase.CreateCommentCommand;
import com.realmatch.backend.community.application.port.in.CommunityUseCase.CreatePostCommand;
import com.realmatch.backend.community.application.port.in.CommunityUseCase.PostQuery;
import com.realmatch.backend.community.application.port.in.CommunityUseCase.PostResult;
import com.realmatch.backend.community.application.port.in.CommunityUseCase.ReportCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** 커뮤니티 Web Adapter입니다. TODO: 게시글/댓글 입력값, 신고 사유, 작성자 제재 상태 검증을 구현합니다. */
@RestController
@RequiredArgsConstructor
public class CommunityController {

  private final CommunityUseCase communityUseCase;

  @GetMapping(Routes.COMMUNITY_POSTS)
  public List<PostResult> posts(
      @RequestHeader(name = "X-USER-ID", required = false) Long userId,
      @RequestParam(required = false) Long categoryId,
      @RequestParam(defaultValue = "LATEST") String sort,
      @RequestParam(defaultValue = "20") int size) {
    return communityUseCase.getPosts(new PostQuery(userId, categoryId, sort, size));
  }

  @GetMapping(Routes.COMMUNITY_POSTS_BY_ID)
  public PostResult post(
      @RequestHeader(name = "X-USER-ID", required = false) Long userId, @PathVariable Long postId) {
    return communityUseCase.getPost(userId, postId);
  }

  @PostMapping(Routes.COMMUNITY_POSTS)
  public PostResult create(
      @RequestHeader(name = "X-USER-ID", required = false) Long userId,
      @RequestBody CreatePostRequest request) {
    return communityUseCase.createPost(request.toCommand(userId));
  }

  @PostMapping(Routes.COMMUNITY_COMMENTS)
  public void comment(
      @RequestHeader(name = "X-USER-ID", required = false) Long userId,
      @PathVariable Long postId,
      @RequestBody CreateCommentRequest request) {
    communityUseCase.createComment(request.toCommand(userId, postId));
  }

  @PostMapping(Routes.COMMUNITY_LIKE)
  public void like(
      @RequestHeader(name = "X-USER-ID", required = false) Long userId, @PathVariable Long postId) {
    communityUseCase.likePost(userId, postId);
  }

  @PostMapping(Routes.COMMUNITY_REPORTS)
  public void report(
      @RequestHeader(name = "X-USER-ID", required = false) Long userId,
      @RequestBody ReportRequest request) {
    communityUseCase.report(request.toCommand(userId));
  }

  public record CreatePostRequest(Long categoryId, String title, String body) {
    CreatePostCommand toCommand(Long userId) {
      return new CreatePostCommand(userId, categoryId, title, body);
    }
  }

  public record CreateCommentRequest(Long parentCommentId, String body) {
    CreateCommentCommand toCommand(Long userId, Long postId) {
      return new CreateCommentCommand(userId, postId, parentCommentId, body);
    }
  }

  public record ReportRequest(String targetType, Long targetId, String reason) {
    ReportCommand toCommand(Long userId) {
      return new ReportCommand(userId, targetType, targetId, reason);
    }
  }
}
