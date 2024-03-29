package com.flab.football.security.config;

import com.flab.football.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * JwtFilter를 SecurityConfig에 적용할 때 사용할 JwtSecurityConfig.
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class JwtSecurityConfig extends
    SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private final JwtFilter jwtFilter;

  /**
   * JwtFilter를 Security 로직에 필터를 등록.
   */

  @Override
  public void configure(HttpSecurity http) {
    // Security 로직에 필터를 등록
    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
  }

}
