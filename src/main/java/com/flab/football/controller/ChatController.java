package com.flab.football.controller;

import com.flab.football.controller.request.CreateChannelRequest;
import com.flab.football.controller.request.InviteParticipantsRequest;
import com.flab.football.controller.response.ResponseDto;
import com.flab.football.handler.ChatHandler;
import com.flab.football.service.chat.ChatService;
import com.flab.football.service.redis.RedisService;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * 채팅 기능 관련 API 선언이 되어있는 컨트롤러.
 */

@Slf4j
@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

  private final ChatService chatService;

  private final RedisService redisService;

  private final RestTemplate restTemplate;

  private final ChatHandler chatHandler;

  private final Map<Integer, WebSocketSession> sessions; // chatHandler에 주입된 sessions와 다른 값을 가진다.

  /**
   * 새로운 채팅방 생성 API.
   */

  @PostMapping
  @PreAuthorize("hasAnyRole('ROLE_MANAGER')")
  public ResponseDto createChannel(@Valid @RequestBody CreateChannelRequest request) {

    chatService.createChannel(request.getName());

    return new ResponseDto(true, null, "채팅방 생성 완료", null);

  }

  /**
   * 특정 채팅방에 참가자 초대 API.
   * 초대할 수 있는 참가자는 다수가 될 수 있습니다.
   */

  @PostMapping("/{channelId}")
  @PreAuthorize("hasAnyRole('ROLE_MANAGER')")
  public ResponseDto inviteParticipants(@PathVariable(value = "channelId") int channelId,
                                        @RequestBody InviteParticipantsRequest request) {

    chatService.inviteParticipants(channelId, request.getParticipants());

    return new ResponseDto(true, null, "참가자 초대 완료", null);

  }

  /**
   * 로그인한 유저가 포함된 채팅방 목록을 조회 API.
   */

  @GetMapping
  public ResponseDto findChannels() {

    return new ResponseDto(true, null, "채팅방 조회", null);

  }

  /**
   * 메시지 대상자 분류 API.
   */

  @GetMapping("/message/channel/{channelId}")
  public ResponseDto findMessageReceiver(@PathVariable(value = "channelId") int channelId) {

    // 해당 채팅방에 메세지를 받아야하는 대상자를 조회
    List<Integer> userIdList = chatService.findMessageReceivers(channelId);

    // 조회된 user들에 대한 웹소켓 서버 정보를 redis에서 조회한다.
    for (int userId : userIdList) {

      String localAddress = (String) redisService.getValue(userId);

      if (localAddress == null) {

        // 웹소켓에 접속중이지 않은 경우는 FCM을 통해 푸시 알림을 보낸다.
        log.info(userId + "님에게 푸시 알림을 보냅니다.");

      } else {

        // 그 외 경우엔 해당 서버에 접속중인 회원이 Map 컬렉션에 저장되어 있기에 메세지를 전송한다.

        // API 서버에 메소드를 호출하면 웹소켓 서버 하나당 API 서버 하나가 존재하는 구조가 되는 것이 아닐까...?
        // restTemplate을 활용해 웹소켓 서버에 메세지 전송을 할 수 있는 방법이 있는지 알아야 한다.
        // String uri = "ws:/" + localAddress + "/ws/chat"; <= 프로토콜 에러 발생

        String uri = "http:/" + localAddress + "/chat/send/message";

        restTemplate.getForObject(uri, ResponseDto.class);

        log.info(userId + "님에게 메세지 전송이 완료되었습니다.");

      }

    }

    return new ResponseDto<>(true, null, "분류 완료.", null);

  }

  /**
   * 접속한 대상 회원에게 메세지 전송 API.
   */

  @GetMapping("/send/message")
  public ResponseDto sendMessage() throws Exception {

      // chatHandler 에서 주입받은 sessions와 controller에서 주입받은 sesssions가 달르다
      // 이 레이어에서의 sessions 컬렉션은 아무런 값을 가지고 있자 않아서 sendMessage()를 호출할 수 없다.

      // 그래서 호출 시점에서 session은 null로 주입하고
      // handleTextMessage() 가 수행하는 시점에서 sessions를 조회하고 메세지를 전송해야 한다.
      chatHandler.handleTextMessage(null, new TextMessage("hello"));

    return new ResponseDto(true, null, "메세지 전송 완료", null);

  }

}
