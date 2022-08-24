package com.flab.football.exception;

/**
 * 회원 정보로 저장되지 않은 이메일을 입력받은 경우 호출되는 예외 클래스.
 */

public class NotValidEmailException extends RuntimeException {

  /**
   *.
   */

  public NotValidEmailException(String message) {

    super(message);

  }
}
