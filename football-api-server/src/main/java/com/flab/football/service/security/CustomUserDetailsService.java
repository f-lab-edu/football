package com.flab.football.service.security;

import com.flab.football.domain.User;
import com.flab.football.repository.user.UserRepository;
import java.util.ArrayList;
import java.util.List;
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

    User user = userRepository.findById(Integer.parseInt(username))
        .orElseThrow(() -> new UsernameNotFoundException(username + " 존재하지 않는 username 입니다."));

    return createUserDetails(user);

  }

  private UserDetails createUserDetails(User user) {

    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));

    return new org.springframework.security.core.userdetails.User(
        String.valueOf(user.getId()),
        user.getPassword(),
        authorities

        // Cannot find symbol 발생 지점
        //List.of(new SimpleGrantedAuthority(user.getRole().toString()))
    );

  }

}
