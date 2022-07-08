package com.flab.football.service.security;

import com.flab.football.domain.User;
import com.flab.football.repository.user.UserRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    return userRepository.findByEmail(username)
        .map(user -> createUserDetails(user))
        .orElseThrow(() -> new UsernameNotFoundException(username + " 존재하지 않는 username 입니다."));

  }

  private UserDetails createUserDetails(User user) {

    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole().toString());

    return new org.springframework.security.core.userdetails.User(
        user.getEmail(),
        user.getPassword(),
        Collections.singleton(grantedAuthority)
    );

  }

}
