package com.flab.football.service.redis;

import com.flab.football.util.WebSocketUtils;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

  private final RedisTemplate<String, Object> redisTemplate;


  @Override
  public void setWebSocketSession(int userId, String session) {

    redisTemplate.opsForValue().set(WebSocketUtils.PREFIX_KEY + userId, session);

  }

  @Override
  public void deleteWebSocketSession(int userId) {

    redisTemplate.delete(WebSocketUtils.PREFIX_KEY + userId);

  }

  @Override
  public String getWebSocketSession(String userId) {

    return (String) redisTemplate.opsForValue().get(userId);

  }

  @Override
  public void setWebSocketServerInfo(String address, int connectionCount,
      LocalDateTime lastHeartBeatTime) {

    HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();

    String key = WebSocketUtils.PREFIX_SERVER + address;

    hashOperations.put(key, WebSocketUtils.ADDRESS, address);

    hashOperations.put(key, WebSocketUtils.CONNECTION_COUNT, connectionCount);

    hashOperations.put(key, WebSocketUtils.LAST_HEARTBEAT_TIME, lastHeartBeatTime);

    log.info("address = {}", getWebSocketAddress(key));

    log.info("connectionCount = {}", getWebSocketConnectionCount(key));

    log.info("LastHeartBeatTime = {}", getWebSocketLastHeartBeatTime(key));

  }

  @Override
  public void deleteWebSocketServerInfo(String key) {

    redisTemplate.delete(key);

  }

  @Override
  public String getWebSocketAddress(String key) {

    return (String) redisTemplate.opsForHash().get(key, WebSocketUtils.ADDRESS);

  }

  @Override
  public Integer getWebSocketConnectionCount(String key) {

    return (Integer) redisTemplate.opsForHash().get(key, WebSocketUtils.CONNECTION_COUNT);

  }

  @Override
  public LocalDateTime getWebSocketLastHeartBeatTime(String key) {

    return (LocalDateTime) redisTemplate.opsForHash().get(key, WebSocketUtils.LAST_HEARTBEAT_TIME);

  }

  @Override
  public Cursor<String> scanWebSocketServerKey() {

    return redisTemplate.opsForHash()
        .getOperations()
        .scan(ScanOptions.scanOptions()
            .match(WebSocketUtils.PREFIX_SERVER + "*")
            .count(3) // 커맨드 한번당 가져올 데이터 개수
            .build()
        );

  }

  @Override
  public void setPrimaryWebSocketServerKeys() {

    Cursor<String> keys = scanWebSocketServerKey();

    // 연결 가능한 서버가 다운되어 조회된 키가 없는 경우
    if(!keys.hasNext()) {
      // 저장되어 있던 ZSet 데이터를 삭제
      redisTemplate.delete(WebSocketUtils.Z_SET_KEY);

    }

    while(keys.hasNext()) {

      String key = keys.next();

      Integer connectionCount = (Integer) redisTemplate.opsForHash()
          .get(key, WebSocketUtils.CONNECTION_COUNT);

      if (connectionCount == null) {

        throw new RuntimeException("웹 서버에 연결된 커넥션 정보가 없습니다.");

      }

      // Sorted Set 자료구조로 연결하기 가장 좋은 서버 정보를 따로 저장해둔다.
      redisTemplate.opsForZSet().add(WebSocketUtils.Z_SET_KEY, key, connectionCount);

    }

    log.info("ZSet = {}", redisTemplate.opsForZSet()
        .range(
            WebSocketUtils.Z_SET_KEY,
            0,
            redisTemplate.opsForZSet().size(WebSocketUtils.Z_SET_KEY)
        )
    );

  }

  @Override
  public String getPrimaryWebSocketServerKey() {

    // Sorted Set 으로 저장하기 때문에 가장 첫번째 녀석을 가져오면 된다.
    ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();

    Set<Object> set = zSetOperations.range(WebSocketUtils.Z_SET_KEY, 0, 0);

    if (set == null || set.isEmpty()) {

      throw new RuntimeException("접속 가능한 서버 정보가 없습니다.");

    }

    Optional<Object> key  =  set.stream().findFirst();

    return (String) key.get();

  }

}
