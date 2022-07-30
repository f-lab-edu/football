package com.flab.football.service.security;

import com.flab.football.domain.User;
import com.flab.football.repository.user.UserRepository;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    // User 타입 객체 리턴 시
    /*
    return userRepository.findByEmail(email)
        .map(user -> createUserDetails(user))
        .orElseThrow(() -> new UsernameNotFoundException(username + " 존재하지 않는 username 입니다."));
    */

    // UserAdapter 객체 리턴 시
    User user = userRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException(username + " 존재하지 않는 username 입니다."));

    // UserDetails 인터페이스 구현 클래스인 User 클래스의 상속 클래스인 UserAdapter 클래스 return
    return createUserAdapter(user);

  }

  private UserDetails createUserDetails(User user) {

    return new org.springframework.security.core.userdetails.User(
        user.getEmail(),
        user.getPassword(),
        List.of(new SimpleGrantedAuthority(user.getRole().toString()))
    );

  }

  private UserAdapter createUserAdapter(User user) {

    return new UserAdapter(user);

  }

  @Getter
  public static class UserAdapter extends org.springframework.security.core.userdetails.User {

    // 엔티티 클래스인 User 클래스
    private User user;

    public UserAdapter(User user) {

      super(
          user.getEmail(),
          user.getPassword(),
          List.of(new SimpleGrantedAuthority(user.getRole().toString()))
      );

      this.user = user;

    }

  }

}
