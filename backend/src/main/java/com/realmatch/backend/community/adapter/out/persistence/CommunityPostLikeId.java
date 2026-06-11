package com.realmatch.backend.community.adapter.out.persistence;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CommunityPostLikeId implements Serializable {

  private Long postId;
  private Long userId;
}
