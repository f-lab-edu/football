package com.flab.football.service.redis;

import java.time.LocalDateTime;
import java.util.Set;

public interface RedisService {

  void setSession(String userId, String session);

  void deleteSession(String userId);

  String getSession(String userId);

  void setServerInfo(String address, int connectionCount, LocalDateTime lastHeartBeatTime);

  Set<String> getServerInfoKeySet();

  String getAddress(String key);

  Integer getConnectionCount(String key);

  LocalDateTime getLastHeartBeatTime(String key);

}
