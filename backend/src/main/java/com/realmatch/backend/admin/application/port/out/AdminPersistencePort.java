package com.realmatch.backend.admin.application.port.out;

import com.realmatch.backend.admin.domain.model.AdminActionLog;
import com.realmatch.backend.admin.domain.model.AdminUser;
import com.realmatch.backend.admin.domain.model.ModerationCase;
import java.util.List;
import java.util.Optional;

/** 운영/관리자 영속성 출력 포트입니다. TODO: 관리자, 운영 케이스, 제재, 액션 로그, 대시보드 지표 저장/조회를 구현합니다. */
public interface AdminPersistencePort {

  Optional<AdminUser> findAdmin(Long adminId);

  List<ModerationCase> findCases(String status);

  void saveModerationCase(ModerationCase moderationCase);

  void saveActionLog(AdminActionLog log);

  void saveSanction(Long adminId, Long userId, String sanctionType, String reason);

  List<String> findActionLogs(Long adminId);
}
