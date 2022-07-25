package com.flab.football.controller;

import com.flab.football.controller.request.CreateChannelRequest;
import com.flab.football.controller.request.InviteParticipantsRequest;
import com.flab.football.controller.request.SendMessageOrPushRequest;
import com.flab.football.controller.response.ResponseDto;
import com.flab.football.service.chat.ChatService;
import com.flab.football.service.redis.RedisService;
import com.flab.football.service.security.SecurityService;
import com.flab.football.websocket.conrtroller.request.SendMessageRequest;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 채팅 기능 관련 API 선언이 되어있는 컨트롤러.
 */

@Slf4j
@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

  private final ChatService chatService;

  private final SecurityService securityService;

  private final RedisService redisService;

  private final RestTemplate restTemplate;

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
   * 메시지 수신자를 대상으로 메세지 또는 푸시알림 전송 API.
   */

  @PostMapping("/send/message")
  public ResponseDto sendMessageOrPush(@RequestBody SendMessageOrPushRequest request) {

    // 메세지를 보내는 사용자 id 조회
    int sendUserId = securityService.getCurrentUserId();

    // 메세지 내용을 담을 request 객체 생성
    SendMessageRequest sendMessage = SendMessageRequest.builder()
        .channelId(request.getChannelId())
        .sendUserId(sendUserId)
        .content(request.getContent())
        .build();

    // 해당 채팅방에 메세지를 받아야하는 대상자를 조회
    List<Integer> userIdList = chatService.findMessageReceivers(request.getChannelId());

    // 조회된 user들에 대한 웹소켓 서버 정보를 redis에서 조회한다.
    for (int userId : userIdList) {

      String localAddress = (String) redisService.getValue(userId);

      if (localAddress == null) {

        // 웹소켓에 접속중이지 않은 경우는 FCM을 통해 푸시 알림을 보낸다.
        log.info(userId + "님에게 푸시 알림을 보냅니다.");

      } else {

        // 그 외 경우엔 해당 서버에 접속중인 회원이 Map 컬렉션에 저장되어 있기에 메세지를 전송한다.
        String uri = "http:/" + localAddress + "/ws/send/message/" + userId;

        // football.websocket.controller.WebSocketController.sendMessage() 호출
        restTemplate.postForEntity(uri, sendMessage, ResponseEntity.class);

        log.info(userId + "님에게 메세지 전송이 완료되었습니다.");

      }

    }

    // 푸시 알림 또는 메세지 전송이 성공하면 메세지 엔티티 객체를 저장
    chatService.saveMessage(request.getChannelId(), sendUserId, request.getContent());

    return new ResponseDto<>(true, null, "분류 완료.", null);

  }

}
