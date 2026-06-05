package com.realmatch.backend.matching.application.port.out;

import com.realmatch.backend.matching.domain.model.Match;
import com.realmatch.backend.matching.domain.model.UserReaction;
import java.util.List;
import java.util.Optional;

/** 매칭/추천 영속성 출력 포트입니다. TODO: 추천 후보 조회, 노출 이력, 반응, 매칭, 차단/신고 조회를 구현합니다. */
public interface MatchingPersistencePort {

  List<Long> findRecommendationCandidates(Long userId, int size);

  Optional<UserReaction> findReaction(Long userId, Long targetUserId);

  UserReaction saveReaction(UserReaction reaction);

  Optional<Match> findMatchBetween(Long userAId, Long userBId);

  Match saveMatch(Match match);

  void saveExposure(Long userId, Long targetUserId, String surfaceType);

  void saveBlock(Long userId, Long targetUserId, String reason);
}
