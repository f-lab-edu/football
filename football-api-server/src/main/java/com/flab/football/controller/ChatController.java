package com.flab.football.controller;

import com.flab.football.controller.request.SaveConnectInfoRequest;
import com.flab.football.controller.request.CreateChannelRequest;
import com.flab.football.controller.request.HealthCheckRequest;
import com.flab.football.controller.request.InviteParticipantsRequest;
import com.flab.football.controller.request.SendMessageRequest;
import com.flab.football.controller.response.ResponseDto;
import com.flab.football.controller.response.data.FindPrimaryWebSocketServerAddressData;
import com.flab.football.service.chat.ChatService;
import com.flab.football.service.redis.RedisService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


  /**
   * 웹 소켓 서버에 대한 healthCheck 값을 받기 위한 API.
   */

  @PostMapping("/health/check")
  public ResponseDto healthCheck(@RequestBody HealthCheckRequest request) {

    chatService.healthCheck(
        request.getAddress(),
        request.getConnectionCount(),
        request.getHeartBeatTime()
    );

    return new ResponseDto(true, null, "헬스 체크 완료", null);

  }

  /**
   * 새로운 사용자가 접근할 최적 환경의 웹소켓 서버 주소를 탐색하는 API.
   */

  @GetMapping("/connect")
  public ResponseDto findPrimaryWebSocketServerAddress() {

    String address = chatService.findPrimaryWebSocketServerAddress();

    return new ResponseDto(
        true,
        new FindPrimaryWebSocketServerAddressData(address),
        "웹 소켓 주소 전송 완료.",
        null
    );

  }


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
  public ResponseDto sendMessage(
      @RequestBody SendMessageRequest request,
      @AuthenticationPrincipal UserDetails user
  ) {

    // 아래 로직이 모두 ChatService.sendMessage() 로 가야한다.
    chatService.sendMessage(
        request.getChannelId(),
        Integer.parseInt(user.getUsername()),
        request.getContent()
    );

    return new ResponseDto<>(true, null, "메세지 전송 완료", null);

  }

  @PostMapping("/save/connect/info")
  public ResponseDto connectWebsocket(@RequestBody SaveConnectInfoRequest request) {

    redisService.setWebSocketSession(request.getUserId(), request.getAddress());

    return new ResponseDto(true, null, "웹소켓 연결 성공", null);

  }

  @PostMapping("/delete/connect/info")
  public ResponseDto disconnectWebsocket(@RequestBody int userId) {

    redisService.deleteWebSocketSession(userId);

    return new ResponseDto(true, null, "웹소켓 연결 끊기 성공", null);

  }
}
