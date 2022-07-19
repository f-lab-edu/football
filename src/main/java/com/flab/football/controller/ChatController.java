package com.flab.football.controller;

import com.flab.football.controller.request.CreateChannelRequest;
import com.flab.football.controller.request.InviteParticipantsRequest;
import com.flab.football.controller.response.ResponseDto;
import com.flab.football.service.chat.ChatService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
   * 메시지 전송 메소드.
   */

  @GetMapping("/message/channel/{channelId}")
  public ResponseDto findMessageReceivers(@PathVariable(value = "channelId") int channelId) {

    List<String> uriList = chatService.findMessageReceivers(channelId);

    for (String uri : uriList) {

      // 웹소켓에 접속중이지 않은 경우는 FCM을 통해 푸시 알림을 보낸다.
      if (uri == null) {

        log.info("푸시 알림을 보냅니다.");

      } else {

        // 조회된 uri를 가지고 RestTemplate을 통해 해당 API 서버로 message 전송 API에 요청을 보낸다.
        uri = "ws:/" + uri + "/ws/chat";

        restTemplate.optionsForAllow(uri, "Hello");

      }

    }

    return null;

  }

  @PatchMapping("send/message/channel/{channelId}")
  public ResponseDto sendMessage(@PathVariable(value = "channelId") int channelId) {

    // 해당 URL로 요청이 오면 메세지 전송 로직을 호출한다.



    return null;

  }

}
