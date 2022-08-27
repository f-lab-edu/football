package com.flab.football.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate 관련 객체 생성 클래스.
 */

@Configuration
public class RestTemplateConfig {

  /**
   * RestTemplate 빈 객체 생성.
   */

  @Bean
  public RestTemplate restTemplate() {

    return new RestTemplate();

  }
}
