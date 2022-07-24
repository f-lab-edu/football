package com.flab.football.service.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

  private final RedisTemplate<Integer, Object> redisTemplate;


  @Override
  public void setValue(int key, Object value) {

    redisTemplate.opsForValue().set(key, value);

  }

  @Override
  public void deleteValue(int key) {

    redisTemplate.delete(key);

  }

  @Override
  public Object getValue(int key) {

    return redisTemplate.opsForValue().get(key);

  }
}
