package com.flab.football.service.security.jwt;

import static com.flab.football.util.SecurityUtil.AUTHORIZATION_HEADER;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Request Header 에서 가져온 토큰을 필터링하는 과정을 담당
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter implements Filter {

  private final TokenProvider tokenProvider;

  /**
   * 실제 필터링 로직은 doFilter 내부에 작성 jwt 토큰의 인증 정보를 SecurityContext에 저장하는 역할.
   */

  @Override
  public void doFilter(ServletRequest request,ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest httpServletRequest = (HttpServletRequest) request;

    String jwt = resolveToken(httpServletRequest);

    String requestURI = httpServletRequest.getRequestURI();

    if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {

      Authentication authentication = tokenProvider.getAuthentication(jwt); // 이 부분에 DTO 클래스를 추가하면 된다? 굳이 userDetails와 관련 없어도 된다!!

      SecurityContextHolder.getContext().setAuthentication(authentication); // 넘겨주는 것!

      log.debug("Security Context에 '{}' 인증 정보 저장, uri: {}", authentication.getName(), requestURI);

    } else {

      log.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);

    }

    chain.doFilter(request, response);

  }

  /**
   * request header에서 토큰 정보를 꺼내오는 메소드.
   */

  private String resolveToken(HttpServletRequest request) {

    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {

      return bearerToken.substring(7);

    }

    return null;

  }
}
