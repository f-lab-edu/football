package com.flab.football.exception;

/**
 * 로그인되어 있지 않은 브라우저에서 로그아웃 요청이 발생한 경우 호출되는 예외 클래스.
 */

public class NotLogInBrowserException extends RuntimeException {

  public NotLogInBrowserException(String message) {

    super(message);

  }
}
