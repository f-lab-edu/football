package com.flab.football.service.security;

import static com.flab.football.util.SecurityUtil.AUTHORIZATION_HEADER;

import com.flab.football.service.security.jwt.TokenProvider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
public class JwtSecurityService implements SecurityService {

  private final TokenProvider tokenProvider;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  @Override
  public void logIn(String email, String password) {

    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(email, password);

    Authentication authentication = authenticationManagerBuilder.getObject()
        .authenticate(authenticationToken);

    String jwt = tokenProvider.createToken(authentication);

    HttpServletResponse response = getCurrentResponse();

    response.setHeader(AUTHORIZATION_HEADER, "Bearer " + jwt);

  }

  @Override
  public void logOut() {

  }

  @Override
  public int getCurrentUserId() {
    
    return 0;
    
  }

  private ServletRequestAttributes getRequestAttributes() {

    return (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

  }

  private HttpServletRequest getCurrentRequest() {

    return getRequestAttributes().getRequest();

  }

  private HttpServletResponse getCurrentResponse() {

    return getRequestAttributes().getResponse();

  }

}
