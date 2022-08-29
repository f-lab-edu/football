package com.flab.football.service.redis;

import java.time.LocalDateTime;
import org.springframework.data.redis.core.Cursor;

public interface RedisService {

  void setWebSocketSession(int userId, String session);

  void deleteWebSocketSession(int userId);

  String getWebSocketSession(String userId);

  void setWebSocketServerInfo(String address, int connectionCount, LocalDateTime lastHeartBeatTime);

  void deleteWebSocketServerInfo(String key);

  Cursor<String> scanWebSocketServerKey();

  String getWebSocketAddress(String key);

  Integer getWebSocketConnectionCount(String key);

  LocalDateTime getWebSocketLastHeartBeatTime(String key);

  void setPrimaryWebSocketServerKeys();

  String getPrimaryWebSocketServerKey();

}
