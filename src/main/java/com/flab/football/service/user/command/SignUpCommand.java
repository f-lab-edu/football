package com.flab.football.service.user.command;

import com.flab.football.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 서비스 레이어에서 사용될 회원가입 Dto 클래스.
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpCommand {

  private String email;
  private String password;
  private String name;
  private String phone;
  private User.Gender gender;

}
