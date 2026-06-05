package com.realmatch.backend.admin.adapter.out.persistence;

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
 * admin_action_logs 테이블과 매핑되는 JPA 엔티티입니다.
 *
 * <p>TODO: Flyway 스키마 컬럼을 기준으로 필요한 필드를 추가하고 도메인 모델과의 매핑을 PersistenceAdapter에서 처리합니다.
 */
@Getter
@Entity
@Table(name = "admin_action_logs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminActionLogJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "action_log_id")
  private Long actionLogId;
}
