package com.flab.football.repository.user;

import com.flab.football.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * JPA 활용 UserRepository 구현 클래스.
 */

@Slf4j
@Repository
public class JpaUserRepository implements UserRepository {

  @Override
  public void save(User user) {

  }

  @Override
  public User findByEmail(String email) {

    return null;

  }

  @Override
  public boolean isExistEmail(String email) {

    return false;

  }

}
