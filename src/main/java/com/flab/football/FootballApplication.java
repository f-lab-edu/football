package com.flab.football;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 어플리케이션 실행 클래스.
 */

@EnableScheduling
@SpringBootApplication
public class FootballApplication {
  public static void main(String[] args) {
    SpringApplication.run(FootballApplication.class, args);
  }
}
