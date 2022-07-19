package com.flab.football.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

/**
 * ChatHandler 주입할 객체를 생성하는 config 클래스.
 */

@Slf4j
@Component
public class ChatConfig {

  /**
   * WebSocket에 접속한 session을 저장할 리스트 객체.
   */

  @Bean
  public Map<String, WebSocketSession> sessions() {

    return new ConcurrentHashMap<>();

  }

  /**
   * JSON 형태로 넘어오는 메시지를 매핑해줄 ObjectMapper 객체.
   */

  @Bean
  public ObjectMapper objectMapper() {

    return new ObjectMapper();

  }

}
