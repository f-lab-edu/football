package com.flab.football.config;

import com.flab.football.service.security.jwt.JwtAccessDeniedHandler;
import com.flab.football.service.security.jwt.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring Security 관련 설정 파일.
 */

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
  private final JwtSecurityConfig jwtSecurityConfig;

  /**
   * 암호화 방식 선택.
   */

  @Bean
  public PasswordEncoder passwordEncoder() {

    return new BCryptPasswordEncoder();

  }

  /**
   * 어플리케이션으로 넘어오는 요청에 대한 인증, 인가 관련 설정에 대한 메소드.
   */

  @Override
  public void configure(WebSecurity web) throws Exception {
    web
        .ignoring()
        .antMatchers("/chat/health/check")
        .antMatchers("/ws/send/message")
        .antMatchers("/ws/connect");

  }

  /**
   * REST API 접근에 대한 인증 처리 관련 설정.
   */

  @Override
  protected void configure(HttpSecurity http) throws Exception {
      http
          .csrf().disable()

          .exceptionHandling()
          .authenticationEntryPoint(jwtAuthenticationEntryPoint)
          .accessDeniedHandler(jwtAccessDeniedHandler)

          .and()
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

          // 인증 절차를 생략할 API를 지정
          .and()
          .authorizeRequests()
          .antMatchers("/user/signup", "/user/login").permitAll()

          // 그 외 API는 인증 절차 수행
          .anyRequest().authenticated()

          // JwtSecurityConfig 클래스 적용
          .and()
          .apply(jwtSecurityConfig);
    }
}
