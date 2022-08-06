package com.flab.football.resolver;

import com.flab.football.annotation.LogInUserId;
import com.flab.football.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
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
public class LogInUserIdArgumentResolver implements HandlerMethodArgumentResolver {

  /**
   * 리졸버가 동작하기 위한 조건을 정의하는 메소드.
   */

  @Override
  public boolean supportsParameter(MethodParameter methodParameter) {

    return methodParameter.hasParameterAnnotation(LogInUserId.class);

  }

  /**
   * User 엔티티 객체를 조회해 리턴하는 메소드.
   */

  @Override
  public Object resolveArgument(
      MethodParameter methodParameter,
      ModelAndViewContainer modelAndViewContainer,
      NativeWebRequest nativeWebRequest,
      WebDataBinderFactory webDataBinderFactory
  ) {

    return SecurityUtil.getCurrentUserId();

  }

}
