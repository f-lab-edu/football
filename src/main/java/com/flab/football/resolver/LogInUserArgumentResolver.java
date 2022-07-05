package com.flab.football.resolver;

import com.flab.football.annotation.LogInUserId;
import com.flab.football.service.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 로그인한 회원 정보를 조회하기 위한 리졸버 클래스.
 */

@Component
@RequiredArgsConstructor
public class LogInUserArgumentResolver implements HandlerMethodArgumentResolver {

  private final SecurityService securityService;

  /**
   * 회원 정보를 저장할 매개변수에 대한 어노테이션 타입 지정.
   */

  @Override
  public boolean supportsParameter(MethodParameter parameter) {

    return parameter.hasParameterAnnotation(LogInUserId.class);

  }

  @Override
  public Object resolveArgument(MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory) {

    return securityService.getCurrentUserId();

  }

}
