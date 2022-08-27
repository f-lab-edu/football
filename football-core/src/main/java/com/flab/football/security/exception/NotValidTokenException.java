package com.flab.football.security.exception;

/**
 * 유효하지 않은 토큰이 헤더에 담겨 온 경우 호출되는 예외 클래스.
 */

public class NotValidTokenException extends RuntimeException {

  /**
   *.
   */

  public NotValidTokenException(String message) {

    super(message);

  }
}
