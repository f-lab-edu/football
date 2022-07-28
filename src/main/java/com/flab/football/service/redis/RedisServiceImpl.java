package com.flab.football.service.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

  private final RedisTemplate<String, Object> redisTemplate;


  @Override
  public void setSession(String userId, Object session) {

    redisTemplate.opsForValue().set(userId, session);

  }

  @Override
  public void deleteSession(String userId) {

    redisTemplate.delete(userId);

  }

  @Override
  public Object getSession(String userId) {

    return redisTemplate.opsForValue().get(userId);

  }
}
