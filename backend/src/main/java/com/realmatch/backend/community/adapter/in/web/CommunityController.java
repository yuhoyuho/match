package com.realmatch.backend.community.adapter.in.web;

import com.realmatch.backend.common.Routes;
import com.realmatch.backend.community.application.port.in.CommunityUseCase;
import com.realmatch.backend.community.application.port.in.CommunityUseCase.CreateCommentCommand;
import com.realmatch.backend.community.application.port.in.CommunityUseCase.CreatePostCommand;
import com.realmatch.backend.community.application.port.in.CommunityUseCase.PostQuery;
import com.realmatch.backend.community.application.port.in.CommunityUseCase.PostResult;
import com.realmatch.backend.community.application.port.in.CommunityUseCase.ReportCommand;
import com.realmatch.backend.config.security.CustomUserDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** 커뮤니티 Web Adapter */
@RestController
@RequiredArgsConstructor
public class CommunityController {

  private final CommunityUseCase communityUseCase;

  @GetMapping(Routes.COMMUNITY_POSTS)
  public List<PostResult> posts(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @RequestParam(required = false) Long categoryId,
      @RequestParam(required = false) Long cursor,
      @RequestParam(defaultValue = "20") int size) {
    return communityUseCase.getPosts(new PostQuery(userDetails.getUserId(), categoryId, cursor, size));
  }

  @GetMapping(Routes.COMMUNITY_POSTS_BY_ID)
  public PostResult post(
      @AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long postId) {
    return communityUseCase.getPost(userDetails.getUserId(), postId);
  }

  @PostMapping(Routes.COMMUNITY_POSTS)
  public PostResult create(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @RequestBody CreatePostRequest request) {
    return communityUseCase.createPost(request.toCommand(userDetails.getUserId()));
  }

  @PostMapping(Routes.COMMUNITY_COMMENTS)
  public void comment(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @PathVariable Long postId,
      @RequestBody CreateCommentRequest request) {
    communityUseCase.createComment(request.toCommand(userDetails.getUserId(), postId));
  }

  @PostMapping(Routes.COMMUNITY_LIKE)
  public void like(
      @AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long postId) {
    communityUseCase.likePost(userDetails.getUserId(), postId);
  }

  @PostMapping(Routes.COMMUNITY_REPORTS)
  public void report(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @RequestBody ReportRequest request) {
    communityUseCase.report(request.toCommand(userDetails.getUserId()));
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
