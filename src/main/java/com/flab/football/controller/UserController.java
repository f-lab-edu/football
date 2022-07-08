package com.flab.football.controller;

import com.flab.football.annotation.CheckLogIn;
import com.flab.football.annotation.LogInUserId;
import com.flab.football.controller.request.LogInRequest;
import com.flab.football.controller.request.SignUpRequest;
import com.flab.football.controller.response.ResponseDto;
import com.flab.football.domain.User;
import com.flab.football.service.security.SecurityService;
import com.flab.football.service.user.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 회원 관리를 위한 요청 및 응답 객체에 대한 API 가 선언된 컨트롤러.
 */

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  private final SecurityService securityService;

  /**
   * 회원가입 API.
   */

  @PostMapping("/signup")
  public ResponseDto signUp(@Valid @RequestBody SignUpRequest requestDto) {

    userService.signUp(SignUpRequest.toCommand(requestDto));

    return new ResponseDto(true, null, "회원가입 성공", null);

  }

  /**
   * 이메일 중복 여부 판단 API.
   */

  @GetMapping("/{email}/exists")
  public ResponseDto checkEmail(@PathVariable String email) {

    boolean isExistEmail = userService.checkValidEmail(email);

    if (isExistEmail) {

      return new ResponseDto(true, null, "이미 존재하는 이메일", null);

    }

    return new ResponseDto(true, null, "사용 가능한 이메일", null);

  }

  /**
   * 로그인 API.
   */

  @PostMapping("/login")
  public ResponseDto logIn(@Valid @RequestBody LogInRequest request) {

    if (userService.checkValidEmailAndPw(request.getEmail(), request.getPassword())) {

      securityService.logIn(request.getEmail(), request.getPassword());

    }

    return new ResponseDto(true, null, "로그인 성공", null);

  }

  /**
   * 로그아웃 API.
   */

  @PostMapping("/logout")
  public ResponseDto logOut() {

    securityService.logOut();

    return new ResponseDto(true, null, "로그아웃 성공", null);

  }

  /**
   * 로그인 회원 정보 확인 API.
   */

  @GetMapping("/login/check")
  @CheckLogIn
  public ResponseDto logInCheck(@LogInUserId int userId) {

    log.info("SigIn UserId = {}", userId);

    return new ResponseDto(true, null, "로그인 체크", null);

  }

}

