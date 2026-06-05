package com.realmatch.backend.chat.domain.model;

/**
 * 채팅 메시지 저장과 전달 상태를 표현하는 도메인 모델입니다.
 *
 * <p>TODO(PDF 설계서 기준): - 하나의 match_id에 하나의 chat_room만 생성합니다. - 메시지는 DB 저장 후 실시간 전달합니다. - 차단, 신고, 매칭
 * 종료 상태일 때 메시지 전송을 제한합니다.
 */
public class ChatMessage {

  // TODO: 외부 기술 의존성 없이 상태와 도메인 규칙을 구현합니다.
}
