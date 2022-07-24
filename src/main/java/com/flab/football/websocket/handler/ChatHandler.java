package com.flab.football.websocket.handler;

import static com.flab.football.util.SecurityUtil.AUTHORIZATION_HEADER;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.football.controller.response.ResponseDto;
import com.flab.football.service.redis.RedisService;
import com.flab.football.service.security.SecurityService;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
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

  private final SecurityService securityService;

  private final RedisService redisService;

  private final RestTemplate restTemplate;

  private final Map<Integer, WebSocketSession> sessions;

  private final ObjectMapper objectMapper;

  /**
   * 클라이언트로부터 받은 메세지에 대한 로직을 구현한 메소드.
   *
   * req 예시
   *  {
   *    "channelId" : 1,
   *    "content" : "Hello, world!"
   *  }
   */

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    String payload = message.getPayload();

    MappedMessage mappedMessage = objectMapper.readValue(payload, MappedMessage.class);

    String bearerToken = session.getHandshakeHeaders().get(AUTHORIZATION_HEADER).toString();

    // 메세지를 보내는 사용자의 userId 를 추가
    mappedMessage.setUserId(securityService.getCurrentUserId(bearerToken));

    String uri = "http://localhost:8080/chat/message/channel"; // API 서버가 여러개일 경우는?

    // football.controller.ChatController.sendMessageOrPush() 메소드 호출
    restTemplate.postForEntity(uri, mappedMessage, ResponseDto.class);

  }

  /**
   * Client가 접속 시 호출되는 메서드.
     * 앱을 실행시킨 경우
     * key = userId, value = [웹소켓 서버 정보] 으로 Redis 에 저장
   */

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    String bearerToken = session.getHandshakeHeaders().get(AUTHORIZATION_HEADER).toString();

    log.info("ID." + securityService.getCurrentUserId(bearerToken) + " 님이 입장하셨습니다.");

    int userId = securityService.getCurrentUserId(bearerToken);

    // userId 와 웹소켓 서버 정보를 redis 에 저장
    redisService.setValue(userId, session.getLocalAddress().toString());

    // 웹소켓 서버 내 메모리에 session 객체를 저장
    sessions.put(userId, session);

  }

  /**
   * Client가 접속 해제 시 호출되는 메서드.
   */

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

    String bearerToken = session.getHandshakeHeaders().get(AUTHORIZATION_HEADER).toString();

    log.info(session.getPrincipal().getName() + " 님이 퇴장하셨습니다.");

    int userId = securityService.getCurrentUserId(bearerToken);

    // userId 와 웹소켓 서버 정보를 redis 에서 삭제
    redisService.deleteValue(userId);

    // 웹소켓 서버 내 메모리에 session 객체를 삭제
    sessions.remove(userId, session);

  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class MappedMessage {

    private int channelId;
    private int userId;
    private String content;

    public void setUserId(int userId) {

      this.userId = userId;

    }

  }

}
