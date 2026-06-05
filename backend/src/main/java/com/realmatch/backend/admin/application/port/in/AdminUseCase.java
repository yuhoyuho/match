package com.realmatch.backend.admin.application.port.in;

import java.util.List;

/** 운영/관리자 입력 포트입니다. TODO: 관리자 조회, 제재, 검토 케이스, 결제 검토, 감사 로그, 대시보드 지표 유스케이스를 구현합니다. */
public interface AdminUseCase {

  List<AdminUserResult> getAdminUsers(Long adminId);

  List<ModerationCaseResult> getModerationCases(Long adminId, String status);

  void sanctionUser(SanctionCommand command);

  void resolveCase(ResolveCaseCommand command);

  List<String> getActionLogs(Long adminId);

  List<DashboardMetricResult> getMetrics(Long adminId);

  record SanctionCommand(Long adminId, Long userId, String sanctionType, String reason) {}

  record ResolveCaseCommand(Long adminId, Long caseId, String actionType, String reason) {}

  record AdminUserResult(Long adminId, String loginId, String role, String status) {}

  record ModerationCaseResult(
      Long caseId, String caseType, Long targetUserId, String status, Long assignedAdminId) {}

  record DashboardMetricResult(String metricCode, String metricValue) {}
}
