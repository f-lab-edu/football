package com.flab.football.websocket.handler;

import static com.flab.football.util.SecurityUtil.AUTHORIZATION_HEADER;
import static com.flab.football.websocket.util.WebSocketUtils.PREFIX_KEY;

import com.flab.football.service.redis.RedisService;
import com.flab.football.service.security.SecurityService;
import java.net.InetAddress;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
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

  private final Map<Integer, WebSocketSession> sessions;

  private final String address;

  /**
   * Client가 접속 시 호출되는 메서드.
     * 앱을 실행시킨 경우
     * key = userId, value = [웹소켓 서버 정보] 으로 Redis 에 저장
   */

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    log.info(session.getPrincipal().getName() + " 님이 입장하셨습니다.");

    int userId = getCurrentUserId(session);

    // userId 와 웹소켓 서버 정보를 redis 에 저장
    redisService.setSession(PREFIX_KEY + userId, address);

    // 웹소켓 서버 내 메모리에 session 객체를 저장
    sessions.put(userId, session);

  }

  /**
   * Client가 접속 해제 시 호출되는 메서드.
   */

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

    log.info(session.getPrincipal().getName() + " 님이 퇴장하셨습니다.");

    int userId = getCurrentUserId(session);

    // userId 와 웹소켓 서버 정보를 redis 에서 삭제
    redisService.deleteSession(PREFIX_KEY + userId);

    // 웹소켓 서버 내 메모리에 session 객체를 삭제
    sessions.remove(userId, session);

  }

  private int getCurrentUserId(WebSocketSession session) {

    String bearerToken = session.getHandshakeHeaders().get(AUTHORIZATION_HEADER).toString();

    return securityService.getCurrentUserId(bearerToken);

  }

}
