package com.flab.football.config;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis 사용을 위한 객체 생성 클래스.
 *
 * Redis Cluster로 서버 확장을 함에 따라 port에 대한 정보가 배열에 여러 데이터가 담기는 형태로 변경되었습니다.
 */

@Configuration
public class RedisConfig {

//  @Value("${spring.redis.host}")
//  private String redisHost;

//  @Value("${spring.redis.port}")
//  private int redisPort;

  @Value("${spring.redis.cluster.nodes}")
  private List<String> clusterNodes;

  /**
   * Redis Client 는 Lettuce로 지정합니다.
   * Jedis 보다 Lettuce가 성능적인 부분에서 우수하며, 이로 인해 Jedis는 Spring boot에서 Deprecated 처리 되었습니다.
   */

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {

    // return new LettuceConnectionFactory(redisHost, redisPort);

    RedisClusterConfiguration redisClusterConfig = new RedisClusterConfiguration(clusterNodes);
    return new LettuceConnectionFactory(redisClusterConfig);

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
