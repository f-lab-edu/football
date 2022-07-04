package com.flab.football.service.user;

import com.flab.football.domain.User;
import com.flab.football.service.user.command.SignUpCommand;

/**
 * UserService 인터페이스.
 */

public interface UserService {

  void signUp(SignUpCommand commandDto);

  User findByEmailAndPw(String email, String password);

  User findByEmail(String email);

  boolean isExistEmail(String email);

}
