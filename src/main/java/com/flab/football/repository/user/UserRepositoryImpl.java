package com.flab.football.repository.user;

import com.flab.football.domain.User;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

  @Override
  public void persist(User user) {

  }

  @Override
  public Optional<User> findByEmail(String email) {

    return Optional.empty();

  }

  @Override
  public boolean existsByEmail(String email) {

    return false;

  }

}
