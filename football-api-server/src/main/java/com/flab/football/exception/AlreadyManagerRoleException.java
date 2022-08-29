package com.flab.football.exception;

/**
 * 이미 매니저 권한이 있는데 매니저 권한 변경 요청이 발생한 경우에 호출되는 예외 클래스.
 */

public class AlreadyManagerRoleException extends RuntimeException {

  /**
   * .
   */

  public AlreadyManagerRoleException(String message) {

    super(message);

  }

}
