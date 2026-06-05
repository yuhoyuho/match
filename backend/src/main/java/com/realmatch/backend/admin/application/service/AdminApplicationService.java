package com.realmatch.backend.admin.application.service;

import com.realmatch.backend.admin.application.port.in.AdminUseCase;
import com.realmatch.backend.admin.application.port.out.AdminPersistencePort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 운영/관리자 유스케이스 구현체입니다. TODO: RBAC, 신고 검토, 회원 제재, 콘텐츠 숨김/복구, 감사 로그 저장을 구현합니다. */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminApplicationService implements AdminUseCase {

  private final AdminPersistencePort adminPersistencePort;

  @Override
  public List<AdminUserResult> getAdminUsers(Long adminId) {
    throw new UnsupportedOperationException("TODO: 관리자 목록을 조회합니다.");
  }

  @Override
  public List<ModerationCaseResult> getModerationCases(Long adminId, String status) {
    throw new UnsupportedOperationException("TODO: 검토 케이스 목록을 조회합니다.");
  }

  @Override
  @Transactional
  public void sanctionUser(SanctionCommand command) {
    throw new UnsupportedOperationException("TODO: 회원 제재와 감사 로그를 저장합니다.");
  }

  @Override
  @Transactional
  public void resolveCase(ResolveCaseCommand command) {
    throw new UnsupportedOperationException("TODO: 검토 케이스 처리와 액션 로그 저장을 구현합니다.");
  }

  @Override
  public List<String> getActionLogs(Long adminId) {
    throw new UnsupportedOperationException("TODO: 감사 로그를 조회합니다.");
  }

  @Override
  public List<DashboardMetricResult> getMetrics(Long adminId) {
    throw new UnsupportedOperationException("TODO: 대시보드 지표를 조회합니다.");
  }
}
