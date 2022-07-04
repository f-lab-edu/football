package com.flab.football.service.security;

import static com.flab.football.util.SecurityUtil.TOKEN_ID;

import com.flab.football.exception.AlreadyLogInBrowserException;
import com.flab.football.exception.NotLogInBrowserException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * JWT 토큰 방식의 로그인 인증 로직을 구현한 서비스 클래스.
 */

@Slf4j
@Service
public class JwtSecurityService implements SecurityService {

  @Value("${jwt.secretKey}")
  private String secretKey;

  @Value("${jwt.ttlMillis}")
  private int ttlMillis;

  /**
   * 로그인 메소드.
   */

  @Override
  public void logIn(int userId) {

    HttpServletRequest request = getCurrentRequest();

    HttpServletResponse response = getCurrentResponse();

    if (request.getHeader(TOKEN_ID) != null) {

      throw new AlreadyLogInBrowserException("이미 로그인된 회원입니다");

    }

    String token = createToken(userId);

    response.setHeader(TOKEN_ID, token);

  }

  /**
   * 로그아웃 메서드.
   */

  @Override
  public void logOut() {

    HttpServletRequest request = getCurrentRequest();

    String token = request.getHeader(TOKEN_ID);

    if (token == null) {

      throw new NotLogInBrowserException("로그인한 회원이 아닙니다.");

    }

    // 토큰의 유효기간을 0으로 만들어 로그아웃 기능 구현
    expirationToken(token);

  }

  @Override
  public int getCurrentUserId() {

    HttpServletRequest request = getCurrentRequest();

    String token = request.getHeader(TOKEN_ID);

    Claims claims = Jwts.parserBuilder()
        .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
        .build()
        .parseClaimsJws(token)
        .getBody();

    return Integer.parseInt(claims.getSubject());

  }

  private String createToken(int userId) {

    if (ttlMillis <= 0) {

      throw new RuntimeException("토큰 유효시간을 다시 설정하세요.");

    }

    byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(secretKey);

    Key signingKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS256.getJcaName());

    return Jwts.builder()
        .setSubject(String.valueOf(userId))
        .signWith(signingKey, SignatureAlgorithm.HS256)
        .setExpiration(new Date(System.currentTimeMillis() + ttlMillis))
        .compact();
  }

  private void expirationToken(String token) {

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
