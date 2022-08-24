package com.flab.football.controller.request;

import com.flab.football.service.user.command.LogInCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 로그인 요청 객체.
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LogInRequest {

  private String email;
  private String password;

  /**
   * Command 객체로 변경하기 위한 팩토리 메소드.
   */

  public static LogInCommand toCommand(String email, String password, int userId, String name) {

    return new LogInCommand(email, password, userId, name);

  }

}
