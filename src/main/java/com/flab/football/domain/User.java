package com.flab.football.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * User Entity 클래스.
 */

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

  /**
   * 회원 성별을 구분하기 위한 내부 enum 클래스.
   */

  @Getter
  @AllArgsConstructor
  public enum Gender {

    MALE("남성"), FEMALE("여성");

    final String text;

  }

  /**
   * 접근 권한을 구분하기 위한 Enum 클래스.
   */

  @Getter
  public enum Role {

    ROLE_USER,
    ROLE_MANAGER,
    ROLE_ADMIN

  }

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private int id;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @Column(name = "name")
  private String name;

  @Column(name = "phone")
  private String phone;

  @Enumerated(EnumType.STRING)
  @Column(name = "gender")
  private Gender gender;

  @Enumerated(EnumType.STRING)
  @Column(name = "role")
  private Role role;

}
