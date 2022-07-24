package com.flab.football.service.redis;

public interface RedisService {

  void setValue(int key, Object value);

  void deleteValue(int key);

  Object getValue(int key);

}
