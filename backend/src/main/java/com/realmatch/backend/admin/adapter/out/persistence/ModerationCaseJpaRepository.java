package com.realmatch.backend.admin.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** ModerationCaseJpaEntity 전용 Spring Data JPA 저장소입니다. TODO: PersistencePort가 요구하는 조회 메서드를 추가합니다. */
@Repository
public interface ModerationCaseJpaRepository extends JpaRepository<ModerationCaseJpaEntity, Long> {}
