package com.flab.football.websocket.conrtroller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  /**
   * 접속한 대상 회원에게 메세지 전송 API.
   * 이후 모듈 분리 대상
   */

  @GetMapping("/send/message/{userId}")
  public ResponseEntity<HttpStatus> sendMessage(@PathVariable(value = "userId") int userId) throws Exception {

    WebSocketSession session = sessions.get(userId);

    session.sendMessage(new TextMessage("hello"));

    return new ResponseEntity<>(HttpStatus.OK);

  }

}
