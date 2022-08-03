package com.flab.football.service.chat;

import static com.flab.football.websocket.util.WebSocketUtils.PREFIX_KEY;

import com.flab.football.service.chat.command.PushMessageCommand;
import com.flab.football.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatPushServiceImpl implements ChatPushService {

  private final RedisService redisService;

  private final RestTemplate restTemplate;

  @Async
  @Override
  public void pushMessage(PushMessageCommand command) {

    String address = redisService.getWebSocketSession(
        PREFIX_KEY + command.getReceiveUserId()
    );

    if (address == null) {

      // 웹소켓에 접속중이지 않은 경우는 FCM을 통해 푸시 알림을 보낸다.
      log.info(command.getReceiveUserId() + "님에게 푸시 알림을 보냅니다.");

    } else {

      // 그 외 경우엔 해당 서버에 접속중인 회원이 Map 컬렉션에 저장되어 있기에 메세지를 전송한다.
      String uri = "http://" + address + "/ws/send/message";

      restTemplate.postForEntity(uri, command, ResponseEntity.class);

      log.info(command.getReceiveUserId() + "님에게 메세지 전송이 완료되었습니다.");

    }

  }

}
