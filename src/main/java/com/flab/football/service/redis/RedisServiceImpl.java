package com.flab.football.service.redis;

import com.flab.football.websocket.util.WebSocketUtils;
import java.time.LocalDateTime;
import java.util.Set;
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

    log.info("address = {}", getAddress(key));

    log.info("connectionCount = {}", getConnectionCount(key));

    log.info("LastHeartBeatTime = {}", getLastHeartBeatTime(key));

  }

  @Override
  public Set<String> getServerInfoKeySet() {

    return redisTemplate.opsForHash()
        .getOperations()
        .keys(WebSocketUtils.PREFIX_SERVER + "*");
  }

  @Override
  public void deleteServerInfo(String key) {

    redisTemplate.delete(key);

  }

  @Override
  public String getAddress(String key) {

    return (String) redisTemplate.opsForHash().get(key, WebSocketUtils.ADDRESS);

  }

  @Override
  public Integer getConnectionCount(String key) {

    return (Integer) redisTemplate.opsForHash().get(key, WebSocketUtils.CONNECTION_COUNT);

  }

  @Override
  public LocalDateTime getLastHeartBeatTime(String key) {

    return (LocalDateTime) redisTemplate.opsForHash().get(key, WebSocketUtils.LAST_HEARTBEAT_TIME);

  }

}
