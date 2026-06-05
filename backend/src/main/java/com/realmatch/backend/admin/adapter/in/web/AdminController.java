package com.realmatch.backend.admin.adapter.in.web;

import com.realmatch.backend.admin.application.port.in.AdminUseCase;
import com.realmatch.backend.admin.application.port.in.AdminUseCase.AdminUserResult;
import com.realmatch.backend.admin.application.port.in.AdminUseCase.DashboardMetricResult;
import com.realmatch.backend.admin.application.port.in.AdminUseCase.ModerationCaseResult;
import com.realmatch.backend.admin.application.port.in.AdminUseCase.ResolveCaseCommand;
import com.realmatch.backend.admin.application.port.in.AdminUseCase.SanctionCommand;
import com.realmatch.backend.common.Routes;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** 운영/관리자 Web Adapter입니다. TODO: 관리자 인증, RBAC, 민감 액션 사유 입력 검증을 구현합니다. */
@RestController
@RequiredArgsConstructor
public class AdminController {

  private final AdminUseCase adminUseCase;

  @GetMapping(Routes.ADMIN_USERS)
  public List<AdminUserResult> users(
      @RequestHeader(name = "X-ADMIN-ID", required = false) Long adminId) {
    return adminUseCase.getAdminUsers(adminId);
  }

  @GetMapping(Routes.ADMIN_MODERATION_CASES)
  public List<ModerationCaseResult> cases(
      @RequestHeader(name = "X-ADMIN-ID", required = false) Long adminId,
      @RequestParam(required = false) String status) {
    return adminUseCase.getModerationCases(adminId, status);
  }

  @PostMapping(Routes.ADMIN_USER_SANCTIONS)
  public void sanction(
      @RequestHeader(name = "X-ADMIN-ID", required = false) Long adminId,
      @PathVariable Long userId,
      @RequestBody SanctionRequest request) {
    adminUseCase.sanctionUser(request.toCommand(adminId, userId));
  }

  @PostMapping(Routes.ADMIN_MODERATION_RESOLVE)
  public void resolve(
      @RequestHeader(name = "X-ADMIN-ID", required = false) Long adminId,
      @PathVariable Long caseId,
      @RequestBody ResolveCaseRequest request) {
    adminUseCase.resolveCase(request.toCommand(adminId, caseId));
  }

  @GetMapping(Routes.ADMIN_ACTION_LOGS)
  public List<String> logs(@RequestHeader(name = "X-ADMIN-ID", required = false) Long adminId) {
    return adminUseCase.getActionLogs(adminId);
  }

  @GetMapping(Routes.ADMIN_DASHBOARD_METRICS)
  public List<DashboardMetricResult> metrics(
      @RequestHeader(name = "X-ADMIN-ID", required = false) Long adminId) {
    return adminUseCase.getMetrics(adminId);
  }

  public record SanctionRequest(String sanctionType, String reason) {
    SanctionCommand toCommand(Long adminId, Long userId) {
      return new SanctionCommand(adminId, userId, sanctionType, reason);
    }
  }

  public record ResolveCaseRequest(String actionType, String reason) {
    ResolveCaseCommand toCommand(Long adminId, Long caseId) {
      return new ResolveCaseCommand(adminId, caseId, actionType, reason);
    }
  }
}
