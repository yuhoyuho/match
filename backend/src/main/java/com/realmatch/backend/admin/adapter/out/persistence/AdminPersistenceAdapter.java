package com.realmatch.backend.admin.adapter.out.persistence;

import com.realmatch.backend.admin.application.port.out.AdminPersistencePort;
import com.realmatch.backend.admin.domain.model.AdminActionLog;
import com.realmatch.backend.admin.domain.model.AdminUser;
import com.realmatch.backend.admin.domain.model.ModerationCase;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** 운영/관리자 Persistence Adapter입니다. TODO: 운영 케이스, 제재, 액션 로그 저장/조회를 구현합니다. */
@Component
@RequiredArgsConstructor
public class AdminPersistenceAdapter implements AdminPersistencePort {

  private final AdminUserJpaRepository adminUserJpaRepository;
  private final ModerationCaseJpaRepository moderationCaseJpaRepository;
  private final AdminActionLogJpaRepository adminActionLogJpaRepository;

  @Override
  public Optional<AdminUser> findAdmin(Long adminId) {
    throw new UnsupportedOperationException("TODO: 관리자 계정을 조회합니다.");
  }

  @Override
  public List<ModerationCase> findCases(String status) {
    throw new UnsupportedOperationException("TODO: 검토 케이스를 조회합니다.");
  }

  @Override
  public void saveModerationCase(ModerationCase moderationCase) {
    throw new UnsupportedOperationException("TODO: 검토 케이스를 저장합니다.");
  }

  @Override
  public void saveActionLog(AdminActionLog log) {
    throw new UnsupportedOperationException("TODO: 액션 로그를 저장합니다.");
  }

  @Override
  public void saveSanction(Long adminId, Long userId, String sanctionType, String reason) {
    throw new UnsupportedOperationException("TODO: 회원 제재를 저장합니다.");
  }

  @Override
  public List<String> findActionLogs(Long adminId) {
    throw new UnsupportedOperationException("TODO: 액션 로그를 조회합니다.");
  }
}
