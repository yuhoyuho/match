package com.realmatch.backend.matching.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * user_reactions 테이블과 매핑되는 JPA 엔티티입니다.
 *
 * <p>TODO: Flyway 스키마 컬럼을 기준으로 필요한 필드를 추가하고 도메인 모델과의 매핑을 PersistenceAdapter에서 처리합니다.
 */
@Getter
@Entity
@Table(name = "user_reactions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserReactionJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "reaction_id")
  private Long reactionId;
}
