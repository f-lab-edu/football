package com.flab.football.exception.hadler;

import com.flab.football.controller.response.ResponseDto;
import com.flab.football.exception.AlreadyExistEmailException;
import com.flab.football.exception.AlreadyManagerRoleException;
import com.flab.football.exception.NotExistStadiumException;
import com.flab.football.exception.NotLogInBrowserException;
import com.flab.football.exception.NotValidEmailException;
import com.flab.football.exception.NotValidPasswordException;
import com.flab.football.exception.NotValidTokenException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 발생할 수 있는 예외 상황에 대해 ResponseDto 객체를 리턴해주기 위한 핸들러 클래스.
 */

@RestControllerAdvice(basePackages = "com.flab.football.controller")
public class ExceptionHandlerAdvice {

  /**
   * 요청 객체의 유효성 검사에 대한 예외 처리를 위한 예외 처리.
   */

  @ExceptionHandler
  public ResponseDto methodArgumentNotValidException(MethodArgumentNotValidException e) {

    return fail(makeCode(e), makeMessage(e));

  }

  /**
   * 입력받은 이메일이 이미 회원가입된 이메일인 경우.
   */

  @ExceptionHandler(AlreadyExistEmailException.class)
  public ResponseDto alreadyExistEmailException(AlreadyExistEmailException e) {

    return fail("AlreadyExistEmail", e.getMessage());

  }

  /**
   * 회원가입 정보에 저장되지 않은 이메일인 경우.
   */

  @ExceptionHandler(NotValidEmailException.class)
  public ResponseDto notValidEmailException(NotValidEmailException e) {

    return fail("NotValidEmail", e.getMessage());

  }

  /**
   * 유효하지 않은 비밀번호인 경우.
   */

  @ExceptionHandler(NotValidPasswordException.class)
  public ResponseDto notValidPasswordException(NotValidPasswordException e) {

    return fail("NotValidPassword", e.getMessage());

  }

  /**
   * 로그인되지 않은 브라우저인 경우.
   */

  @ExceptionHandler(NotLogInBrowserException.class)
  public ResponseDto notLogInBrowserException(NotLogInBrowserException e) {

    return fail("NotLogInBrowser", e.getMessage());

  }

  /**
   * 토큰이 유효하지 않은 경우.
   */

  @ExceptionHandler(NotValidTokenException.class)
  public ResponseDto notValidTokenException(NotValidTokenException e) {

    return fail("NotValidToken", e.getMessage());

  }

  /**
   * 이미 매니저 권한이 부여된 회원인 경우.
   */

  @ExceptionHandler(AlreadyManagerRoleException.class)
  public ResponseDto alreadyManagerRoleException(AlreadyManagerRoleException e) {

    return fail("AlreadyManagerRole", e.getMessage());

  }

  /**
   * 조회된 구장 정보가 없는 경우.
   */

  @ExceptionHandler(NotExistStadiumException.class)
  public ResponseDto notExistStadiumException(NotExistStadiumException e) {

    return fail("NotExistStadium", e.getMessage());

  }



  /**
   * Error 클래스 객체 생성을 위한 팩토리 메서드.
   */

  private ResponseDto fail(String code, String message) {

    return new ResponseDto(

        false, null, "에러 발생", ResponseDto.Error.from(code, message)

    );

  }

  private String makeCode(MethodArgumentNotValidException e) {

    return e.getBindingResult().getFieldError().getCode();

  }

  private String makeMessage(MethodArgumentNotValidException e) {

    return e.getBindingResult().getFieldError().getDefaultMessage();

  }

}
