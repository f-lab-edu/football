package com.flab.football.repository.user;

import com.flab.football.domain.User;
import java.util.Optional;

/**
 * UserRepository 인터페이스.
 */

public interface UserRepository {

  void persist(User user);

  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);

}
