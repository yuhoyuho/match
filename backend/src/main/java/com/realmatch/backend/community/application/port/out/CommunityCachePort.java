package com.realmatch.backend.community.application.port.out;

/** 커뮤니티 캐시 출력 포트입니다. TODO: 인기글, 반응 수, 카테고리별 상단 목록 캐시를 구현합니다. */
public interface CommunityCachePort {

  void refreshHotPost(Long postId);
}
