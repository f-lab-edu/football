package com.flab.football.service.redis;

public interface RedisService {

  void setSession(String userId, Object session);

  void deleteSession(String userId);

  Object getSession(String userId);

}
