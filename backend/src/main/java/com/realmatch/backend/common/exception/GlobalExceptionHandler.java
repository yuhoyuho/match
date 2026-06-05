package com.realmatch.backend.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** 전역 예외 처리기 컨트롤러에서 발생하는 예외를 공통 ErrorResponse로 변환합니다. */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  // TODO: IllegalArgumentException, ValidationException, 인증/인가 예외, 도메인 예외 처리 메서드를 구현합니다.
}
