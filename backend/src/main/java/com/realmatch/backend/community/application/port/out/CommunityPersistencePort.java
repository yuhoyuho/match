package com.realmatch.backend.community.application.port.out;

import com.realmatch.backend.community.domain.model.CommunityComment;
import com.realmatch.backend.community.domain.model.CommunityPost;
import java.util.List;
import java.util.Optional;

/** 커뮤니티 영속성 출력 포트입니다. TODO: 게시글, 댓글, 좋아요, 신고, 숨김, 활동 로그 저장/조회를 구현합니다. */
public interface CommunityPersistencePort {

  List<CommunityPost> findPosts(Long categoryId, String sort, int size);

  Optional<CommunityPost> findPost(Long postId);

  CommunityPost savePost(CommunityPost post);

  void saveComment(CommunityComment comment);

  void likePost(Long userId, Long postId);

  void saveReport(Long userId, String targetType, Long targetId, String reason);
}
