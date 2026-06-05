package com.realmatch.backend.matching.adapter.in.web;

import com.realmatch.backend.common.Routes;
import com.realmatch.backend.matching.application.port.in.MatchingUseCase;
import com.realmatch.backend.matching.application.port.in.MatchingUseCase.BlockCommand;
import com.realmatch.backend.matching.application.port.in.MatchingUseCase.MatchResult;
import com.realmatch.backend.matching.application.port.in.MatchingUseCase.ReactionCommand;
import com.realmatch.backend.matching.application.port.in.MatchingUseCase.RecommendationQuery;
import com.realmatch.backend.matching.application.port.in.MatchingUseCase.RecommendationResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** 매칭/추천 Web Adapter입니다. TODO: 추천 조회, LIKE/PASS, 매칭 조회, 차단 API 요청 검증을 구현합니다. */
@RestController
@RequiredArgsConstructor
public class MatchingController {

  private final MatchingUseCase matchingUseCase;

  @GetMapping(Routes.RECOMMENDATIONS_BASE)
  public RecommendationResult getRecommendations(
      @RequestHeader(name = "X-USER-ID", required = false) Long userId,
      @RequestParam(defaultValue = "20") int size) {
    return matchingUseCase.getRecommendations(new RecommendationQuery(userId, size));
  }

  @PostMapping(Routes.RECOMMENDATIONS_LIKE)
  public MatchResult like(
      @RequestHeader(name = "X-USER-ID", required = false) Long userId,
      @PathVariable Long targetUserId) {
    return matchingUseCase.react(new ReactionCommand(userId, targetUserId, "LIKE"));
  }

  @PostMapping(Routes.RECOMMENDATIONS_PASS)
  public MatchResult pass(
      @RequestHeader(name = "X-USER-ID", required = false) Long userId,
      @PathVariable Long targetUserId) {
    return matchingUseCase.react(new ReactionCommand(userId, targetUserId, "PASS"));
  }

  @GetMapping(Routes.MATCHES_BASE)
  public List<MatchResult> getMatches(
      @RequestHeader(name = "X-USER-ID", required = false) Long userId) {
    return matchingUseCase.getMatches(userId);
  }

  @GetMapping(Routes.MATCHES_BY_ID)
  public MatchResult getMatch(
      @RequestHeader(name = "X-USER-ID", required = false) Long userId,
      @PathVariable Long matchId) {
    return matchingUseCase.getMatch(userId, matchId);
  }

  @PostMapping(Routes.USERS_BLOCK_BY_ID)
  public void block(
      @RequestHeader(name = "X-USER-ID", required = false) Long userId,
      @PathVariable Long targetUserId,
      @RequestParam(required = false) String reason) {
    matchingUseCase.blockUser(new BlockCommand(userId, targetUserId, reason));
  }
}
