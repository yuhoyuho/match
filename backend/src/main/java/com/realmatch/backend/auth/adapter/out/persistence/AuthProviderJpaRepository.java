package com.realmatch.backend.auth.adapter.out.persistence;

import com.realmatch.backend.auth.domain.model.AuthAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/** AuthProviderJpaEntity 전용 Spring Data JPA 저장소입니다. TODO: PersistencePort가 요구하는 조회 메서드를 추가합니다. */
@Repository
public interface AuthProviderJpaRepository extends JpaRepository<AuthProviderJpaEntity, Long> {

    // providerType과 providerId로 사용자 조회
    Optional<AuthProviderJpaEntity> findByProviderTypeAndProviderUserId(String providerType, String providerId);

}
