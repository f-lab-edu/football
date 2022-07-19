package com.flab.football.service.user.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LogInCommand {

  private String email;
  private String password;
  private int userId;
  private String name;

}
