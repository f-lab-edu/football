package com.flab.football.websocket.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
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

  /**
   * 서버 실행시 서버 주소를 저장해두기 위한 객체.
   */

  @Bean
  public String address() {

    String port = applicationContext
        .getBean(Environment.class)
        .getProperty("server.port", String.class, "8080");

    log.info("WebSocket Server Address = {}", InetAddress.getLoopbackAddress().getHostAddress() + ":" + port);

    return InetAddress.getLoopbackAddress().getHostAddress() + ":" + port;

  }

}
