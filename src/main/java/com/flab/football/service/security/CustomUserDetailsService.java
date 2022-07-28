package com.flab.football.service.security;

import com.flab.football.domain.User;
import com.flab.football.repository.user.UserRepository;
import com.flab.football.service.user.UserService;
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
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    User user = userRepository.findByEmail(email).get();
    log.info("" + user.getId());

    UserAdapter userAdapter = createUserAdapter(user);
    log.info("" + userAdapter.getUser().getId());

    if (userAdapter == null) {

      throw new UsernameNotFoundException("존재하지 않는 email입니다.");

    }

    return userAdapter;

//    return userRepository.findByEmail(username)
//        .map(user -> createUserAdapter(user))
//        .orElseThrow(() -> new UsernameNotFoundException(username + " 존재하지 않는 username 입니다."));

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
