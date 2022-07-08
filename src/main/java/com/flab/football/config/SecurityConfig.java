package com.flab.football.config;

import com.flab.football.service.security.jwt.JwtAccessDeniedHandler;
import com.flab.football.service.security.jwt.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
  private final JwtSecurityConfig jwtSecurityConfig;

  @Bean
  public PasswordEncoder passwordEncoder() {

    return new BCryptPasswordEncoder();

  }

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
          .antMatchers("/user/**").permitAll()

          // 그 외 API는 인증 절차 수행
          .anyRequest().authenticated()

          // JwtSecurityConfig 클래스 적용
          .and()
          .apply(jwtSecurityConfig);
    }
}
