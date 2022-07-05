package com.flab.football.controller.request;

import com.flab.football.service.user.command.SignUpCommand;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * 회원가입 요청 클래스.
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

  @NotBlank
  private String email;
  @NotBlank
  @Length(max = 15)
  private String password;
  @NotBlank
  private String name;
  @NotBlank
  private String phone;
  @NotBlank
  private String address;

  /**
   * SignUpCommand 객체 생성을 위한 팩토리 메소드.
   */

  public static SignUpCommand toCommand(SignUpRequest requestDto) {
    return new SignUpCommand(
      requestDto.getEmail(),
      requestDto.getPassword(),
      requestDto.getName(),
      requestDto.getPhone(),
      requestDto.getAddress()
    );
  }
}
