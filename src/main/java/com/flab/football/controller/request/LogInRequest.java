package com.flab.football.controller.request;

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
}
