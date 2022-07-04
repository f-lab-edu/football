package com.flab.football.config;

import com.flab.football.interceptor.JwtLogInHandlerInterceptor;
import com.flab.football.resolver.LogInUserArgumentResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 로그인/로그아웃과 관련된 클래스 객체를 생성하고 의존성 주입을 위한 클래스.
 */

@Configuration
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {

  private final LogInUserArgumentResolver logInUserArgumentResolver;

  @Value("${jwt.secretKey}")
  private String secretKey;

  /**
   * 회원 패스워드를 DB에 저장할 때, 암호화를 담당.
   */

  @Bean
  public PasswordEncoder passwordEncoder() {

    return new BCryptPasswordEncoder();

  }

  /**
   * Jwt 인증 방식 사용시 로그인 검증을 담당하는 인터셉터 객체.
   * 객체 빈 등록 단계에서 secretKey 를 주입받는다.
   */

  @Bean
  public JwtLogInHandlerInterceptor jwtSignInHandlerInterceptor() {

    return new JwtLogInHandlerInterceptor(secretKey);

  }

  /**
   * 생성한 인터셉터를 추가.
   */

  @Override
  public void addInterceptors(InterceptorRegistry registry) {

    registry.addInterceptor(jwtSignInHandlerInterceptor());

  }

  /**
   * 로그인한 회원 정보를 조회하고 컨트롤러의 매개변수 받아오기 위한 리졸버.
   */

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

    argumentResolvers.add(logInUserArgumentResolver);

  }
}

