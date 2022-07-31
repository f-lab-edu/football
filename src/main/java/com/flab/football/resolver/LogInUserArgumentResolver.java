package com.flab.football.resolver;

import com.flab.football.annotation.LogInUser;
import com.flab.football.exception.NotValidEmailException;
import com.flab.football.service.security.CustomUserDetailsService.UserAdapter;
import com.flab.football.service.security.SecurityService;
import com.flab.football.util.SecurityUtil;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 요청을 보낸 회원 객체를 조회하기 위한 리졸버 클래스.
 */

@Component
@RequiredArgsConstructor
public class LogInUserArgumentResolver implements HandlerMethodArgumentResolver {

  private final UserDetailsService userDetailsService;

  /**
   * 리졸버가 동작하기 위한 조건을 정의하는 메소드.
   */

  @Override
  public boolean supportsParameter(MethodParameter methodParameter) {

    return methodParameter.hasParameterAnnotation(LogInUser.class);

  }

  /**
   * User 엔티티 객체를 조회해 리턴하는 메소드.
   */

  @Override
  public Object resolveArgument(
      MethodParameter methodParameter,
      ModelAndViewContainer modelAndViewContainer,
      NativeWebRequest nativeWebRequest,
      WebDataBinderFactory webDataBinderFactory)
      throws Exception {

    Optional<String> email  = SecurityUtil.getCurrentEmail();

    if (email.isEmpty()) {

      throw new NotValidEmailException("유효한 이메일이 아닙니다.");

    }

    UserAdapter userAdapter = (UserAdapter) userDetailsService.loadUserByUsername(email.get());

    return userAdapter.getUser();

  }

}
