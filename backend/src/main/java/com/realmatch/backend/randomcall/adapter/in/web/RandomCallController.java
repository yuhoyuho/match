package com.realmatch.backend.randomcall.adapter.in.web;

import com.realmatch.backend.common.Routes;
import com.realmatch.backend.randomcall.application.port.in.RandomCallUseCase;
import com.realmatch.backend.randomcall.application.port.in.RandomCallUseCase.CallSessionResult;
import com.realmatch.backend.randomcall.application.port.in.RandomCallUseCase.EndCallCommand;
import com.realmatch.backend.randomcall.application.port.in.RandomCallUseCase.EnterRandomCallCommand;
import com.realmatch.backend.randomcall.application.port.in.RandomCallUseCase.RandomCallStatusResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** 랜덤통화 Web Adapter입니다. TODO: 진입 조건 입력값, 취소/종료 idempotency, 세션 권한 검증을 구현합니다. */
@RestController
@RequiredArgsConstructor
public class RandomCallController {

  private final RandomCallUseCase randomCallUseCase;

  @PostMapping(Routes.RANDOM_CALL_ENTER)
  public RandomCallStatusResult enter(
      @RequestHeader(name = "X-USER-ID", required = false) Long userId,
      @RequestBody EnterRandomCallRequest request) {
    return randomCallUseCase.enter(request.toCommand(userId));
  }

  @PostMapping(Routes.RANDOM_CALL_CANCEL)
  public void cancel(@RequestHeader(name = "X-USER-ID", required = false) Long userId) {
    randomCallUseCase.cancel(userId);
  }

  @GetMapping(Routes.RANDOM_CALL_STATUS)
  public RandomCallStatusResult status(
      @RequestHeader(name = "X-USER-ID", required = false) Long userId) {
    return randomCallUseCase.getStatus(userId);
  }

  @GetMapping(Routes.CALLS_BY_ID)
  public CallSessionResult session(
      @RequestHeader(name = "X-USER-ID", required = false) Long userId, @PathVariable Long callId) {
    return randomCallUseCase.getSession(userId, callId);
  }

  @PostMapping(Routes.CALLS_END)
  public void end(
      @RequestHeader(name = "X-USER-ID", required = false) Long userId,
      @PathVariable Long callId,
      @RequestParam(required = false) String reason) {
    randomCallUseCase.end(new EndCallCommand(userId, callId, reason));
  }

  public record EnterRandomCallRequest(
      String queueType, String regionCode, String preferredGender) {
    EnterRandomCallCommand toCommand(Long userId) {
      return new EnterRandomCallCommand(userId, queueType, regionCode, preferredGender);
    }
  }
}
