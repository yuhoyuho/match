package com.realmatch.backend.community.adapter.out.cache;

import com.realmatch.backend.community.application.port.out.CommunityCachePort;
import org.springframework.stereotype.Component;

/** 커뮤니티 Redis 캐시 Adapter입니다. TODO: 인기글 목록과 반응 수 캐시를 구현합니다. */
@Component
public class CommunityCacheAdapter implements CommunityCachePort {

  @Override
  public void refreshHotPost(Long postId) {
    throw new UnsupportedOperationException("TODO: 인기글 캐시를 갱신합니다.");
  }
}
