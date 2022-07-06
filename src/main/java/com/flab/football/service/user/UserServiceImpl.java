package com.flab.football.service.user;

import com.flab.football.domain.User;
import com.flab.football.exception.AlreadyExistEmailException;
import com.flab.football.exception.NotValidEmailException;
import com.flab.football.exception.NotValidPasswordException;
import com.flab.football.repository.user.UserRepository;
import com.flab.football.service.user.command.SignUpCommand;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 회원 관리 요청에 대한 비즈니스 처리를 담당하는 서비스.
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public void signUp(SignUpCommand commandDto) {

    if (isExistEmail(commandDto.getEmail())) {

      throw new AlreadyExistEmailException("이미 존재하는 이메일입니다");

    }

    String encodedPassword = passwordEncoder.encode(commandDto.getPassword());

    User user = User.builder()
      .email(commandDto.getEmail())
      .password(encodedPassword)
      .name(commandDto.getName())
      .phone(commandDto.getPhone())
      .gender(commandDto.getGender())
      .build();

    userRepository.save(user);

  }

  @Override
  @Transactional(readOnly = true)
  public boolean isExistEmail(String email) {

    return userRepository.existsByEmail(email);

  }

  @Override
  @Transactional(readOnly = true)
  public User findByEmail(String email) {

    Optional<User> user = userRepository.findByEmail(email);

    if (user.isEmpty()) {

      throw new NotValidEmailException("이메일을 잘못 입력했습니다.");

    }

    return user.get();

  }

  @Override
  @Transactional(readOnly = true)
  public User findByEmailAndPw(String email, String password) {

    User user = findByEmail(email);

    if (!passwordEncoder.matches(password, user.getPassword())) {

      throw new NotValidPasswordException("비밀번호를 잘못 입력했습니다.");

    }

    return user;

  }

}
