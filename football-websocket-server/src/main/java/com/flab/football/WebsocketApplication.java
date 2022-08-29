package com.flab.football;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@EnableScheduling
@SpringBootApplication
public class WebsocketApplication {

  public static void main(String[] args) {
    SpringApplication.run(WebsocketApplication.class, args);
  }
}
