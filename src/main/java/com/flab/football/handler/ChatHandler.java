package com.flab.football.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.football.domain.Message;
import com.flab.football.service.chat.ChatService;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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

  private final Map<String, Map<String, WebSocketSession>> sessionList = new ConcurrentHashMap<>();

  // sessionList 데이터 저장 형태 예시
  // {"channelId1" : {"userId2" : session1, "userId2" : session2}, "channelId2" : {"userId3" : session3}, ...}

  private final List<WebSocketSession> sessions;

  private final ObjectMapper objectMapper;

  private final ChatService chatService;

  /**
   * 메세지를 매핑해 전송하는 메소드.
   * req:
   * {
   *   "type" : ENTER or MESSAGE or QUIT,
   *   "channelId" : 1,
   *   "content" : "hello,world!"
   * }
   */

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    MappingMessage mappingMsg = objectMapper.readValue(message.getPayload(), MappingMessage.class);

    chatService.saveMessage(mappingMsg.getType(), mappingMsg.getChannelId(),
        mappingMsg.getContent());

    /*
     * message 전송

     * message를 직접 받을 회원
       * 현재 앱을 실행시키고 있는 회원 == 소캣에 연결된 회원

     * message를 푸시알림으로 받을 회원
       * 현재 앱을 실행시키고 있지 않은 회원 == 소캣에 연결되지 않은 회원

     * 메시지 전송 동작 흐름
       * channelId로 Participant 테이블의 user 리스트를 조회해온다.
       * user 리스트와 sessionList에 저장된 userId를 비교한다.
       * sessionList에 존재하는 user라면 접속중인 user 이므로 message를 전송한다.
       * sessionList에 존재하지 않는다면 접속중이진 않지만 channel에 포함된 user이므로 푸시알림의 대상이 된다.
     */
  }

  /**
   * Client가 접속 시 호출되는 메서드.
   */

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    log.info(session.getPrincipal().getName() + " 님이 입장하셨습니다.");

    sessions.add(session);

    /*
     * 앱을 실행시킨 경우
     * HashMap에 channelId와 userId를 가지고 session 정보를 저장
     * 문제는 channelId를 어떻게 조회할 지 고민해봐야 한다.
     * -> participant 테이블에서 userId로 해당 유저가 포함된 channelId를 조회해온다.
     * -> sessionList 에서 channelId로 조회
     * -> channelId로 조회한 value(여기도 Map)에 userId : session을 추
     */

  }

  /**
   * Client가 접속 해제 시 호출되는 메서드.
   */

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

    log.info(session.getPrincipal().getName() + " 님이 퇴장하셨습니다.");

    sessions.remove(session);

    /*
     * 앱을 종료시킨 경우
     * HashMap에 channelId와 userId를 가지고 session 정보를 삭제
     * 마찬가지로 문제는 channelId를 어떻게 조회할 지 고민해봐야 한다.
     * -> participant 테이블에서 userId로 해당 유저가 포함된 channelId를 조회해온다.
     * -> sessionList 에서 channelId로 조회
     * -> userId로 session을 조회
     * -> 해당 값을 삭제
     */

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
