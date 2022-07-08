package com.flab.football.util;

import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 로그인 관련 유틸 클래스.
 */

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtil {

  public static final String AUTHORIZATION_HEADER = "Authorization";

  /**
   * user Email 조회를 위한 메소드.
   */

  public static Optional<String> getCurrentEmail() {

    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null) {
      log.debug("Security Context에 인증 정보가 없습니다.");
      return Optional.empty();
    }

    String username = null;

    if (authentication.getPrincipal() instanceof UserDetails) {

      UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
      username = springSecurityUser.getUsername();

    } else if (authentication.getPrincipal() instanceof String) {

      username = (String) authentication.getPrincipal();

    }

    return Optional.ofNullable(username);

  }

}
