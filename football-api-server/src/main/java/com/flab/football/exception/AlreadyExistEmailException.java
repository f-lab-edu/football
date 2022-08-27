package com.flab.football.exception;

/**
 * 이미 존재하는 이메일을 입력받은 경우 호출되는 예외 클래스.
 */

public class AlreadyExistEmailException extends RuntimeException {

  /**
   * .
   */

  public AlreadyExistEmailException(String message) {

    super(message);

  }
}
