package com.flab.football.exception;

/**
 * 조회된 구장 정보가 없는 경우에 호출되는 예외 클래스.
 */

public class NotExistStadiumException extends RuntimeException {

  /**
   * .
   */

  public NotExistStadiumException(String message) {

    super(message);

  }

}
