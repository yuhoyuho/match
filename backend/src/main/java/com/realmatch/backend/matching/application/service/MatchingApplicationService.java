package com.realmatch.backend.matching.application.service;

import com.realmatch.backend.matching.application.port.in.MatchingUseCase;
import com.realmatch.backend.matching.application.port.out.MatchingPersistencePort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 매칭/추천 유스케이스 구현체입니다. TODO: 후보 조회, 제외 조건, 노출 이력, 반응 저장, 상호 LIKE 매칭 생성을 구현합니다. */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchingApplicationService implements MatchingUseCase {

  private final MatchingPersistencePort matchingPersistencePort;

  @Override
  public RecommendationResult getRecommendations(RecommendationQuery query) {
    throw new UnsupportedOperationException("TODO: 추천 후보 조회와 노출 이력 저장을 구현합니다.");
  }

  @Override
  @Transactional
  public MatchResult react(ReactionCommand command) {
    throw new UnsupportedOperationException("TODO: LIKE/PASS 처리와 상호 매칭 생성을 구현합니다.");
  }

  @Override
  public List<MatchResult> getMatches(Long userId) {
    throw new UnsupportedOperationException("TODO: 내 매칭 목록을 조회합니다.");
  }

  @Override
  public MatchResult getMatch(Long userId, Long matchId) {
    throw new UnsupportedOperationException("TODO: 매칭 상세를 조회합니다.");
  }

  @Override
  @Transactional
  public void blockUser(BlockCommand command) {
    throw new UnsupportedOperationException("TODO: 차단 관계 저장과 관련 상태 변경을 구현합니다.");
  }
}
