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

  @Value("${server.host.websocket}")
  private String address;

  @Value("${server.host.api}")
  private String apiHost;

  public void sendHeartBeat() {

    HeartBeatRequest request = HeartBeatRequest.builder()
        .address(address)
        .connectionCount(sessionService.getSessionCount())
        .heartBeatTime(LocalDateTime.now())
        .build();

    restTemplate.postForObject(
        apiHost + "/chat/health/check",
        request,
        ResponseDto.class
    );

  }

}
