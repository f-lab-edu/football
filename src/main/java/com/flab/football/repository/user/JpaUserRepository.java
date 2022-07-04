package com.flab.football.repository.user;

import com.flab.football.domain.User;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 * UserRepository 인터페이스.
 */

@Repository
public interface JpaUserRepository {

  void persist(User user);

  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);

}
