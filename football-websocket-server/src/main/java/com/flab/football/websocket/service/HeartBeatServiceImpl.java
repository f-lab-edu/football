package com.flab.football.websocket.service;

import com.flab.football.websocket.conrtroller.request.HeartBeatRequest;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class HeartBeatServiceImpl implements HeartBeatService {

  private final RestTemplate restTemplate;

  private final SessionService sessionService;

  private final String address;


  public void sendHeartBeat() {

    HeartBeatRequest request = HeartBeatRequest.builder()
        .address(address)
        .connectionCount(sessionService.getSessionCount())
        .heartBeatTime(LocalDateTime.now())
        .build();

    if (!address.contains("8080")) {

      restTemplate.postForEntity(
          "http://localhost:8080/chat/health/check",
          request,
          HeartBeatRequest.class
      );

    }
  }

}
