package com.flab.football.repository.user;

import com.flab.football.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository 인터페이스.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  User save(User user);

  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);

}
