package com.flab.football.config;

import com.flab.football.config.RedisPropertiesConfig.RedisProperties;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Redis 구성을 위한 현경 변수 설정 클래스
 *
 * 해당 클래스는 Setter를 사용하지 않고 Application.yml 파일 내에 환경 변수 값을 immutable하게 호출해 오기 위해 추가했습니다.
 * 주요 어노테이션 설명
   * `@EnableConfigurationProperties(value = RedisProperties.class)` : RedisProperties 객체를 Bean 등록하기 위한 어노테이션
   * `@ConstructorBinding` : 생성자 주입 방식으로 환경변수를 가져오기 위한 어노테이션
 */

@Configuration
@EnableConfigurationProperties(value = RedisProperties.class)
public class RedisPropertiesConfig {

  @Getter
  @RequiredArgsConstructor
  @ConstructorBinding
  @ConfigurationProperties(prefix = "spring.redis")
  public static class RedisProperties {

    private final List<String> nodes;

    private final String host;

    private final int port;

  }

}
