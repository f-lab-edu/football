package com.flab.football.handler;

import static com.flab.football.util.SecurityUtil.AUTHORIZATION_HEADER;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.football.service.chat.ChatService;
import com.flab.football.service.redis.RedisService;
import com.flab.football.service.security.SecurityService;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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

  private final SecurityService securityService;

  private final ChatService chatService;

  private final RedisService redisService;

  private final Map<Integer, WebSocketSession> sessions;

  private final ObjectMapper objectMapper;


  /**
   * 메세지를 매핑해 전송하는 메소드.
   * req:
   * {
   *   "type" : ENTER or MESSAGE or QUIT,
   *   "channelId" : 1,
   *   "content" : "hello,world!"
   * }

   * message를 직접 받을 회원
     * 현재 앱을 실행시키고 있는 회원 == 소캣에 연결된 회원

   * message를 푸시알림으로 받을 회원
     * 현재 앱을 실행시키고 있지 않은 회원 == 소캣에 연결되지 않은 회원

   * 메시지 전송 동작 흐름
     * channelId로 Participant 테이블의 user 리스트를 조회해온다.
     * user 리스트와 sessionList에 저장된 userId를 비교한다.
     * sessionList에 존재하는 user라면 접속중인 user 이므로 message를 전송한다.(sendMessage)
     * sessionList에 존재하지 않는다면 접속중이진 않지만 channel에 포함된 user이므로 푸시알림의 대상이 된다.(FCM 호출)
   */

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    for(WebSocketSession tempSession : sessions.values()) {

      tempSession.sendMessage(message);

    }

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
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  private static class MappingMessage {

    private int channelId;
    private int userId;
    private String content;

  }
}
