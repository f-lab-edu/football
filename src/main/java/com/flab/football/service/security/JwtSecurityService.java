package com.flab.football.service.security;

import static com.flab.football.util.SecurityUtil.AUTHORIZATION_HEADER;

import com.flab.football.service.security.jwt.TokenProvider;
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

    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(command.getEmail(), command.getPassword());

    Authentication authentication = authenticationManagerBuilder.getObject()
        .authenticate(authenticationToken);

    String jwt = tokenProvider.createToken(authentication, command.getUserId(), command.getName());

    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
        .currentRequestAttributes();

    HttpServletResponse response = attributes.getResponse();

    response.setHeader(AUTHORIZATION_HEADER, "Bearer " + jwt);


  }

  @Override
  public void logOut() {

  }


  @Override
  public int getCurrentUserId() {

    return tokenProvider.getCurrentUserId();

  }

  @Override
  public String getCurrentUserName() {

    return tokenProvider.getCurrentUserName();

  }

}
