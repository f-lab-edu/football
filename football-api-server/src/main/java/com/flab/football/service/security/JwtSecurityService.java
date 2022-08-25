package com.flab.football.service.security;

import static com.flab.football.util.SecurityUtil.AUTHORIZATION_HEADER;

import com.flab.football.jwt.TokenProvider;
import com.flab.football.service.user.command.LogInCommand;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtSecurityService implements SecurityService {

  private final TokenProvider tokenProvider;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  @Override
  public void logIn(LogInCommand command) {

    // 인증용 객체 생성
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(command.getUserId(), command.getPassword());

    // 위 인증용 객체에 담긴 정보로 user 정보를 db에서 탐색해 UserDetails 타입 객체를 생성한다.

    // 인증용 객체와 UserDetails 객체를 비교 완료가 된다면 Authentication 타입 객체를 리턴!!

    // 리턴된 Authentication 객체는 SecurityContextHolder에 저장된다.
    Authentication authentication = authenticationManagerBuilder.getObject()
        .authenticate(authenticationToken);

    String jwt = tokenProvider.createToken(authentication, command.getName());

    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
        .currentRequestAttributes();

    HttpServletResponse response = attributes.getResponse();

    response.setHeader(AUTHORIZATION_HEADER, "Bearer " + jwt);

  }

  @Override
  public void logOut() {

  }

  @Override
  public int getCurrentUserId(String bearerToken) {

    return tokenProvider.getCurrentUserId(bearerToken);

  }

}
