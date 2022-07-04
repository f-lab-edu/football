package com.flab.football.repository.user;

import com.flab.football.domain.User;

/**
 * UserRepository 인터페이스.
 */

public interface UserRepository {

  void save(User user);

  User findByEmail(String email);

  boolean isExistEmail(String email);

}
