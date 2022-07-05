package com.flab.football.config;

import com.flab.football.resolver.LogInUserArgumentResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 로그인/로그아웃과 관련된 클래스 객체를 생성하고 의존성 주입을 위한 클래스.
 */

@Configuration
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {

  private final LogInUserArgumentResolver logInUserArgumentResolver;

  /**
   * 회원 패스워드를 DB에 저장할 때, 암호화를 담당.
   */

  @Bean
  public PasswordEncoder passwordEncoder() {

    return new BCryptPasswordEncoder();

  }

  /**
   * 로그인한 회원 정보를 조회하고 컨트롤러의 매개변수 받아오기 위한 리졸버.
   */

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

    argumentResolvers.add(logInUserArgumentResolver);

  }
}

