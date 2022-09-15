package com.flab.football.websocket.service;

import com.flab.football.websocket.conrtroller.request.HeartBeatRequest;
import com.flab.football.websocket.conrtroller.response.ResponseDto;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class HeartBeatServiceImpl implements HeartBeatService {

  private final RestTemplate restTemplate;

  private final SessionService sessionService;

  @Value("${server.host.chatting.public}")
  private String publicChattingServerAddress;

  @Value("${server.host.api}")
  private String apiAddress;

  public void sendHeartBeat() {

    HeartBeatRequest request = HeartBeatRequest.builder()
        .address(publicChattingServerAddress)
        .connectionCount(sessionService.getSessionCount())
        .heartBeatTime(LocalDateTime.now())
        .build();

    restTemplate.postForObject(
        "http://" + apiAddress + "/chat/health/check",
        request,
        ResponseDto.class
    );

  }

}
