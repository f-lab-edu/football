package com.flab.football.websocket.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebSocketEtcConfig {

  private final ApplicationContext applicationContext;

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

    ObjectMapper objectMapper = new ObjectMapper();

    objectMapper.registerModule(new JavaTimeModule());

    return objectMapper;

  }
}
