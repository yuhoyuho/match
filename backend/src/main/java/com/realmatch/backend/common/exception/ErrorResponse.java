package com.realmatch.backend.common.exception;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** 공통 에러 응답 DTO 예외 발생 시 클라이언트에 내려줄 에러 응답 형식을 정의합니다. */
@Getter
@RequiredArgsConstructor
public class ErrorResponse {

  // TODO: 서비스 공통 에러 코드, traceId, fieldErrors 구조가 확정되면 필드를 확장합니다.
  private final LocalDateTime timestamp = LocalDateTime.now();
  private final int status;
  private final String error;
  private final String message;
}
