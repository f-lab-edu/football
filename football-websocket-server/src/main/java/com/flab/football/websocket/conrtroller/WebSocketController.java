package com.flab.football.websocket.conrtroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.football.websocket.conrtroller.request.SendMessageRequest;
import com.flab.football.websocket.conrtroller.response.ResponseDto;
import com.flab.football.websocket.service.HeartBeatService;
import com.flab.football.websocket.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
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

  private final HeartBeatService heartBeatService;

  private final SessionService sessionService;

  private final ObjectMapper objectMapper;

  /**
   * WebSocket 서버의 상태를 지속적으로 API 서버로 전송하는 heartBeat 메소드.
   */

  @Scheduled(fixedRate = 3000)
  public void heartBeat() {

    heartBeatService.sendHeartBeat();

  }

  /**
   * 접속한 대상 회원에게 메세지 전송 API.
   */

  @PostMapping("/send/message")
  public ResponseDto sendMessage(
      @RequestBody SendMessageRequest request) throws Exception {

    WebSocketSession session = sessionService.findSessionByUserId(request.getReceiveUserId());

    session.sendMessage(new TextMessage(objectMapper.writeValueAsString(request)));

    return new ResponseDto(true, null, "메세지 전송 완료", null);

  }

}
