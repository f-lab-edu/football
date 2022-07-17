package com.flab.football.service.user;

import com.flab.football.domain.User;
import com.flab.football.service.user.command.SignUpCommand;
import java.util.List;

/**
 * UserService 인터페이스.
 */

public interface UserService {

  void signUp(SignUpCommand commandDto);

  List<User> findAllById(List<Integer> userIdList);

  User findByEmail(String email);

  User findByEmailAndPw(String email, String password);

  boolean checkValidEmail(String email);

  boolean checkValidEmailAndPw(String email, String password);

  void updateUserRole(int userId);

}
