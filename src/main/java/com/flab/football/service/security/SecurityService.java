package com.flab.football.service.security;

/**
 * SecurityService 인터페이스.
 */

public interface SecurityService {

  void logIn(int userId);

  void logOut();

  int getCurrentUserId();

}
