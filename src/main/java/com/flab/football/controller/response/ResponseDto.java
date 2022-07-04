package com.flab.football.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 응답 클래스.
 */

@Getter
@AllArgsConstructor
public class ResponseDto<T> {

  private boolean success;
  private T data;
  private String message;
  private Error error;

  /**
   * 에러 발생시 생성될 클래스.
   */

  @Getter
  @AllArgsConstructor
  public static class Error {

    private String code;
    private String message;

    /**
     * Error 타입 객체 생성을 위한 팩토리 메서드.
     */

    public static Error from(String code, String message) {

      return new Error(code, message);

    }
  }
}
