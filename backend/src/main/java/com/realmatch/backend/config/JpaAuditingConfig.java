package com.realmatch.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/** JPA Auditing 설정 createdAt, updatedAt 같은 감사 컬럼을 자동으로 관리합니다. */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {

  // TODO: 생성자/수정자 AuditorAware가 필요해지면 현재 로그인 사용자 기반 구현을 추가합니다.
}
