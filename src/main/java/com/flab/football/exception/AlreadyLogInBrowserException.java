package com.flab.football.exception;

/**
 * 이미 로그인된 브라우저에서 로그인 요청이 발생한 경우 호출되는 예외 클래스.
 */

public class AlreadyLogInBrowserException extends RuntimeException {

  /**
   * .
   */

  public AlreadyLogInBrowserException(String message) {

    super(message);

  }
}
