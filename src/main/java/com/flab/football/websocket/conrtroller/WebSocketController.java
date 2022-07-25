package com.flab.football.websocket.conrtroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.football.websocket.conrtroller.request.SendMessageRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * 웹소켓 서버 내 접속한 유저들의 메세지 전송에 대한 책임을 가진 Controller.
 */

@Slf4j
@RestController
@RequestMapping("/ws")
@RequiredArgsConstructor
public class WebSocketController {

  private final Map<Integer, WebSocketSession> sessions;

  private final ObjectMapper objectMapper;

  /**
   * 접속한 대상 회원에게 메세지 전송 API.
   * 이후 모듈 분리 대상
   */

  @PostMapping("/send/message")
  public ResponseEntity<HttpStatus> sendMessage(
      @RequestBody SendMessageRequest request) throws Exception
  {

    WebSocketSession session = sessions.get(request.getReceiveUserId());

    session.sendMessage(new TextMessage(objectMapper.writeValueAsString(request)));

    return new ResponseEntity<>(HttpStatus.OK);

  }

}
