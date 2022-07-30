package com.flab.football.service.redis;

import com.flab.football.websocket.util.WebSocketUtils;
import java.time.LocalDateTime;
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

    String key = WebSocketUtils.PREFIX_SERVER + address;

    hashOperations.put(key, WebSocketUtils.ADDRESS, address);

    hashOperations.put(key, WebSocketUtils.CONNECTION_COUNT, connectionCount);

    hashOperations.put(key, WebSocketUtils.LAST_HEARTBEAT_TIME, lastHeartBeatTime);

  }

  @Override
  public Object getServerInfo(String address) {

    HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();

    return hashOperations.entries(address);

  }

  @Override
  public String getAddress(String address) {

    return (String) redisTemplate.opsForHash()
        .get(WebSocketUtils.PREFIX_SERVER + address, WebSocketUtils.ADDRESS);

  }

  @Override
  public Integer getConnectionCount(String address) {

    return (Integer) redisTemplate.opsForHash()
        .get(WebSocketUtils.PREFIX_SERVER + address, WebSocketUtils.CONNECTION_COUNT);

  }

  @Override
  public LocalDateTime getLastHeartBeatTime(String address) {

    return (LocalDateTime) redisTemplate.opsForHash()
        .get(WebSocketUtils.PREFIX_SERVER + address, WebSocketUtils.LAST_HEARTBEAT_TIME);

  }

}
