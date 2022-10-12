package com.flab.football.exception;

/**
 * 이메일과 회원정보가 일치하지 않는 비밀번호를 입력받은 경우 호출되는 예외 클래스.
 */

public class NotValidPasswordException extends RuntimeException {

  public NotValidPasswordException(String message) {

    super(message);

  }
}
