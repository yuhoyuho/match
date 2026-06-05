package com.realmatch.backend.chat.adapter.in.web;

import com.realmatch.backend.chat.application.port.in.ChatUseCase;
import com.realmatch.backend.chat.application.port.in.ChatUseCase.ChatMessageResult;
import com.realmatch.backend.chat.application.port.in.ChatUseCase.ChatRoomResult;
import com.realmatch.backend.chat.application.port.in.ChatUseCase.ReadCommand;
import com.realmatch.backend.chat.application.port.in.ChatUseCase.SendMessageCommand;
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

/** 채팅 Web Adapter입니다. TODO: 채팅방 권한, 메시지 입력값, 읽음 처리 idempotency 검증을 구현합니다. */
@RestController
@RequiredArgsConstructor
public class ChatController {

  private final ChatUseCase chatUseCase;

  @GetMapping(Routes.CHAT_ROOMS_BASE)
  public List<ChatRoomResult> getRooms(
      @RequestHeader(name = "X-USER-ID", required = false) Long userId) {
    return chatUseCase.getRooms(userId);
  }

  @GetMapping(Routes.CHAT_MESSAGES)
  public List<ChatMessageResult> getMessages(
      @RequestHeader(name = "X-USER-ID", required = false) Long userId, @PathVariable Long roomId) {
    return chatUseCase.getMessages(userId, roomId);
  }

  @PostMapping(Routes.CHAT_MESSAGES)
  public ChatMessageResult send(
      @RequestHeader(name = "X-USER-ID", required = false) Long userId,
      @PathVariable Long roomId,
      @RequestBody SendMessageRequest request) {
    return chatUseCase.sendMessage(request.toCommand(userId, roomId));
  }

  @PostMapping(Routes.CHAT_READ)
  public void read(
      @RequestHeader(name = "X-USER-ID", required = false) Long userId,
      @PathVariable Long roomId,
      @RequestParam(required = false) Long lastReadMessageId) {
    chatUseCase.markRead(new ReadCommand(userId, roomId, lastReadMessageId));
  }

  @PostMapping(Routes.CHAT_CLOSE)
  public void close(
      @RequestHeader(name = "X-USER-ID", required = false) Long userId, @PathVariable Long roomId) {
    chatUseCase.closeRoom(userId, roomId);
  }

  public record SendMessageRequest(String messageType, String content) {
    SendMessageCommand toCommand(Long userId, Long roomId) {
      return new SendMessageCommand(userId, roomId, messageType, content);
    }
  }
}
