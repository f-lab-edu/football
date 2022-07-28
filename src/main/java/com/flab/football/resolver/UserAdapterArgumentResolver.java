package com.flab.football.resolver;

import com.flab.football.exception.NotValidEmailException;
import com.flab.football.service.security.CustomUserDetailsService.UserAdapter;
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

@Component
@RequiredArgsConstructor
public class UserAdapterArgumentResolver implements HandlerMethodArgumentResolver {

  private final UserDetailsService userDetailsService;

  @Override
  public boolean supportsParameter(MethodParameter methodParameter) {

    Class<?> parameterType = methodParameter.getParameterType();

    return UserAdapter.class.equals(parameterType);

  }

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

    return userDetailsService.loadUserByUsername(email.get());
  }

}
