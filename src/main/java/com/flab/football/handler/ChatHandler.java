package com.flab.football.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.football.domain.Message;
import com.flab.football.service.chat.ChatService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * 웹소켓 위에서 동작할 채팅 관련 메소드가 선언된 클래스.
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatHandler extends TextWebSocketHandler {

  //private final static Map<String, Set<WebSocketSession>> sessionList = new ConcurrentHashMap<>();

  private final List<WebSocketSession> sessions;

  private final ObjectMapper objectMapper;

  private final ChatService chatService;

  /**
   * 메세지를 매핑해 전송하는 메소드.
   * {
   *   "type" : ENTER or MESSAGE or QUIT
   *   "channelId" : 1,
   *   "content" : "hello,world!"
   * }
   */

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    MappingMessage mappingMsg = objectMapper.readValue(message.getPayload(), MappingMessage.class);

    chatService.saveMessage(mappingMsg.getType(), mappingMsg.getChannelId(),
        mappingMsg.getContent());

    // message 전송
  }

  /**
   * Client가 접속 시 호출되는 메서드.
   */

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    log.info(session.getPrincipal().getName() + " 님이 입장하셨습니다.");

    sessions.add(session);

  }

  /**
   * Client가 접속 해제 시 호출되는 메서드.
   */

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

    log.info(session.getPrincipal().getName() + " 님이 퇴장하셨습니다.");

    sessions.remove(session);

  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  private static class MappingMessage {

    private Message.Type type;
    private int channelId;
    private String content;

  }
}
