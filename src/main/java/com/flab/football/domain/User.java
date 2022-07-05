package com.flab.football.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * User Entity 클래스.
 */

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

  private int id;
  private String email;
  private String password;
  private String name;
  private String phone;
  private String address;

}
