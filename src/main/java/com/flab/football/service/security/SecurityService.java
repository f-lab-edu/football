package com.flab.football.service.security;

import com.flab.football.service.user.command.LogInCommand;

/**
 * SecurityService 인터페이스.
 */

public interface SecurityService {

  void logIn(LogInCommand command);

  void logOut();

  int getCurrentUserId();

  int getCurrentUserId(String bearerToken);

  String getCurrentUserName();

}
