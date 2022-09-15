package com.flab.football.websocket.handler;

import com.flab.football.websocket.conrtroller.response.ResponseDto;
import com.flab.football.websocket.handler.request.SaveConnectInfoRequest;
import com.flab.football.websocket.service.SessionService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
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

  private final RestTemplate restTemplate;

  private final SessionService sessionService;

  @Value("${server.host.api}")
  private String apiAddress;

  @Value("${server.host.chatting.private}")
  private String privateChattingServerAddress;

  /**
   * Client가 접속 시 호출되는 메서드.
   */

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    int userId = Integer.parseInt(Objects.requireNonNull(session.getPrincipal()).getName());

    log.info(userId + " 님이 입장하셨습니다.");

    restTemplate.postForObject(
        "http://" + apiAddress + "/chat/save/connect/info",
        SaveConnectInfoRequest.builder()
            .userId(userId)
            .address(privateChattingServerAddress)
            .build(),
        SaveConnectInfoRequest.class
    );

    sessionService.saveSession(userId, session);

  }

  /**
   * Client가 접속 해제 시 호출되는 메서드.
   */

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

    int userId = Integer.parseInt(Objects.requireNonNull(session.getPrincipal()).getName());

    log.info(userId + " 님이 퇴장하셨습니다.");

    restTemplate.postForObject(
        "http://" + apiAddress + "/chat/delete/connect/info",
        userId,
        ResponseDto.class
    );

    sessionService.removeSession(userId, session);

  }

}
