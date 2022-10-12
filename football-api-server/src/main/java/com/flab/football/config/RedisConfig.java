package com.flab.football.config;

import com.flab.football.config.RedisPropertiesConfig.RedisProperties;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis 사용을 위한 객체 생성 클래스.
 *
 * 주석 처리된 코드는 로컬 환경에서 Redis Cluster를 적용하기 위한 코드입니다.
 */

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

  private final RedisProperties properties;

  /**
   * Redis Client 는 Lettuce로 지정합니다.
   * Jedis 보다 Lettuce가 성능적인 부분에서 우수하며, 이로 인해 Jedis는 Spring boot에서 Deprecated 처리 되었습니다.
   */

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {

//    LettuceClientConfiguration clientConfiguration = LettuceClientConfiguration.builder()
//        .readFrom(ReadFrom.REPLICA_PREFERRED)
//        .build();
//    RedisClusterConfiguration redisClusterConfig = new RedisClusterConfiguration(properties.getNodes());
//    return new LettuceConnectionFactory(redisClusterConfig, clientConfiguration);

    return new LettuceConnectionFactory(properties.getHost(), properties.getPort());

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
