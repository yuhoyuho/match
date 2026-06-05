package com.realmatch.backend.matching.adapter.out.persistence;

import com.realmatch.backend.matching.application.port.out.MatchingPersistencePort;
import com.realmatch.backend.matching.domain.model.Match;
import com.realmatch.backend.matching.domain.model.UserReaction;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** 매칭/추천 Persistence Adapter입니다. TODO: 추천 제외 조건 쿼리, 반응 upsert, 매칭 유니크 제약 처리를 구현합니다. */
@Component
@RequiredArgsConstructor
public class MatchingPersistenceAdapter implements MatchingPersistencePort {

  private final RecommendationExposureJpaRepository recommendationExposureJpaRepository;
  private final UserReactionJpaRepository userReactionJpaRepository;
  private final MatchJpaRepository matchJpaRepository;

  @Override
  public List<Long> findRecommendationCandidates(Long userId, int size) {
    throw new UnsupportedOperationException("TODO: 추천 후보를 조회합니다.");
  }

  @Override
  public Optional<UserReaction> findReaction(Long userId, Long targetUserId) {
    throw new UnsupportedOperationException("TODO: 기존 반응을 조회합니다.");
  }

  @Override
  public UserReaction saveReaction(UserReaction reaction) {
    throw new UnsupportedOperationException("TODO: 사용자 반응을 저장합니다.");
  }

  @Override
  public Optional<Match> findMatchBetween(Long userAId, Long userBId) {
    throw new UnsupportedOperationException("TODO: 두 사용자 간 매칭을 조회합니다.");
  }

  @Override
  public Match saveMatch(Match match) {
    throw new UnsupportedOperationException("TODO: 매칭을 저장합니다.");
  }

  @Override
  public void saveExposure(Long userId, Long targetUserId, String surfaceType) {
    throw new UnsupportedOperationException("TODO: 추천 노출 이력을 저장합니다.");
  }

  @Override
  public void saveBlock(Long userId, Long targetUserId, String reason) {
    throw new UnsupportedOperationException("TODO: 차단 관계를 저장합니다.");
  }
}
