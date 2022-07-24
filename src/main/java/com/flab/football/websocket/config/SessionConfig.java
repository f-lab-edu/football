package com.flab.football.websocket.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;

@Configuration
public class SessionConfig {

  /**
   * WebSocket에 접속한 session을 저장할 리스트 객체.
   */

  @Bean
  public Map<Integer, WebSocketSession> sessions() {

    return new ConcurrentHashMap<>();

  }

  /**
   * WebSocket으로 넘어온 TextMessage 객체를 매핑하기 위한 ObjectMapper 객체.
   */

  @Bean
  public ObjectMapper objectMapper() {

    return new ObjectMapper();

  }

}
