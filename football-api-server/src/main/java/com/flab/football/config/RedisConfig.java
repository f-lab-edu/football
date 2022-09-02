package com.flab.football.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis 사용을 위한 객체 생성 클래스.
 */

@Getter
@Setter
@Configuration
public class RedisConfig {


  @Value("${spring.redis.host}")
  private String redisHost;

  @Value("${spring.redis.port}")
  private int redisPort;
  /**
   * Redis Client 는 Lettuce로 지정합니다.
   * Jedis 보다 Lettuce가 성능적인 부분에서 우수하며, 이로 인해 Jedis는 Spring boot에서 Deprecated 처리 되었습니다.
   */

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {

    return new LettuceConnectionFactory(redisHost, redisPort);

  }

  /**
   * RedisTemplate 객체 생성 및 세팅.
   */

  @Bean
  public RedisTemplate<String, Object> redisTemplate() {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory());
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new StringRedisSerializer());
    return redisTemplate;
  }
}
