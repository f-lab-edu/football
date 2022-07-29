package com.flab.football.service.redis;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

  private final RedisTemplate<String, Object> redisTemplate;


  @Override
  public void setSession(String userId, String session) {

    redisTemplate.opsForValue().set(userId, session);

  }

  @Override
  public void deleteSession(String userId) {

    redisTemplate.delete(userId);

  }

  @Override
  public String getSession(String userId) {

    return (String) redisTemplate.opsForValue().get(userId);

  }

  @Override
  public void setServerInfo(String address, int connectionCount, LocalDateTime lastHeartBeatTime) {

    HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();

    hashOperations.put(address, "connectionCount", connectionCount);

    hashOperations.put(address, "lastHeartBeatTime", lastHeartBeatTime);

  }

  @Override
  public Object getServerInfo(String address) {

    HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();

    return hashOperations.entries(address);

  }

  @Override
  public Integer getConnectionCount(String address) {

    return (Integer) redisTemplate.opsForHash().get(address, "connectionCount");

  }

  @Override
  public LocalDateTime getLastHeartBeatTime(String address) {

    return (LocalDateTime) redisTemplate.opsForHash().get(address, "lastHeartBeatTime");

  }
}
