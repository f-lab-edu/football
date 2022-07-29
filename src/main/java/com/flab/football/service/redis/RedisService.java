package com.flab.football.service.redis;

import java.time.LocalDateTime;
import java.util.Map;

public interface RedisService {

  void setSession(String userId, String session);

  void deleteSession(String userId);

  String getSession(String userId);

  void setServerInfo(String address, int connectionCount, LocalDateTime lastHeartBeatTime);

  Object getServerInfo(String address);

  Integer getConnectionCount(String address);

  LocalDateTime getLastHeartBeatTime(String address);

}
