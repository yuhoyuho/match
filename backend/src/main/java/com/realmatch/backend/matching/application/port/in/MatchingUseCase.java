package com.realmatch.backend.matching.application.port.in;

import java.util.List;

/** 매칭/추천 입력 포트입니다. TODO: 추천 조회, LIKE/PASS, 매칭 목록/상세, 차단 유스케이스를 구현합니다. */
public interface MatchingUseCase {

  RecommendationResult getRecommendations(RecommendationQuery query);

  MatchResult react(ReactionCommand command);

  List<MatchResult> getMatches(Long userId);

  MatchResult getMatch(Long userId, Long matchId);

  void blockUser(BlockCommand command);

  record RecommendationQuery(Long userId, int size) {}

  record RecommendationResult(List<Long> candidateUserIds) {}

  record ReactionCommand(Long userId, Long targetUserId, String reactionType) {}

  record MatchResult(Long matchId, Long user1Id, Long user2Id, String status, boolean created) {}

  record BlockCommand(Long userId, Long targetUserId, String reason) {}
}
