package com.flab.football.jwt;

import com.flab.football.exception.NotValidTokenException;
import com.flab.football.util.SecurityUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * TokenProvider 클래스.
 * 토큰 생성 및 유효성 검사, Authentication 객체 생성을 담당
 */

@Slf4j
@Component
public class TokenProvider implements InitializingBean {

  private final String secret;
  private final long tokenValidityInMilliseconds;

  private Key key;

  public TokenProvider(
      @Value("${jwt.secret}") String secret,
      @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {

    this.secret = secret;
    this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;

  }

  /**
   * 빈이 생성이 되고 의존성 주입이 되고 난 후에 주입받은 secret 값을 Base64 Decode 해서 key 변수에 할당.
   */

  @Override
  public void afterPropertiesSet() {

    byte[] keyBytes = Decoders.BASE64.decode(secret);
    this.key = Keys.hmacShaKeyFor(keyBytes);

  }

  /**
   * Authentication 객체의 권한정보를 이용해서 토큰을 생성하는 createToken 메소드 추가.
   */

  public String createToken(Authentication authentication, String userName) {

    String authorities = authentication.getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));

    long now = (new Date()).getTime();
    Date validity = new Date(now + this.tokenValidityInMilliseconds);

    Claims claims = Jwts.claims()
        .setSubject(authentication.getName())
        .setExpiration(validity);

    claims.put(SecurityUtil.AUTHORITIES_KEY, authorities);
    claims.put(SecurityUtil.NAME_KEY, userName);

    return Jwts.builder()
        .setClaims(claims)
        .signWith(key, SignatureAlgorithm.HS512)
        .compact();

  }

  /**
   * token에 담겨있는 정보를 이용해 Authentication 객체를 리턴하는 메소드 생성.
   */

  public Authentication getAuthentication(String token) {

    Claims claims = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();

    List<SimpleGrantedAuthority> authorities = Arrays
        .stream(claims.get(SecurityUtil.AUTHORITIES_KEY).toString().split(","))
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());

    User principal = new User(claims.getSubject(), "", authorities);

    return new UsernamePasswordAuthenticationToken(principal, "", authorities);

  }

  /**
   * 토큰의 유효성 검증을 수행하는 validateToken 메소드 추가.
   */

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
      log.info("잘못된 JWT 서명입니다.");
    } catch (ExpiredJwtException e) {
      log.info("만료된 JWT 토큰입니다.");
    } catch (UnsupportedJwtException e) {
      log.info("지원되지 않는 JWT 토큰입니다.");
    } catch (IllegalArgumentException e) {
      log.info("JWT 토큰이 잘못되었습니다.");
    }
    return false;
  }

  /**
   * 웹소켓 서버에서 로그인 회원의 id를 조회.
   */

  public int getCurrentUserId(String bearerToken) {

    Claims claims = getCurrentClaims(bearerToken);

    return Integer.parseInt(claims.getSubject());

  }

  /**
   * 웹소켓 핸드쉐이크 요청 헤더에 담겨있는 토큰을 가지고 Claims 객체를 생성.
   */

  private Claims getCurrentClaims(String bearerToken) {

    if (!StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {

      throw new NotValidTokenException("유효한 토큰 형식이 아닙니다.");

    }

    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(bearerToken.substring(7))
        .getBody();

  }

}
