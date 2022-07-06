package com.flab.football.service.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.flab.football.domain.User;
import com.flab.football.domain.User.Gender;
import com.flab.football.exception.AlreadyExistEmailException;
import com.flab.football.exception.NotValidEmailException;
import com.flab.football.exception.NotValidPasswordException;
import com.flab.football.repository.user.UserRepository;
import com.flab.football.service.user.command.SignUpCommand;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  UserService userService;

  UserRepository userRepository;

  PasswordEncoder passwordEncoder;

  @BeforeEach
  void setUp() {

    userRepository = mock(UserRepository.class);

    passwordEncoder = mock(BCryptPasswordEncoder.class);

    userService = new UserServiceImpl(userRepository, passwordEncoder);

  }

  // 데이터 생성
  String email = "email@email.com";
  String password = "password1234";
  String name = "name";
  String phone = "010-0000-0000";
  User.Gender gender = Gender.MALE;

  SignUpCommand command = new SignUpCommand(email, password, name, phone, gender);

  User user = User.builder()
      .email(email)
      .password(password)
      .name(name)
      .phone(phone)
      .gender(gender)
      .build();

  @Test
  @DisplayName("회원가입 테스트 - 정상적인 경우")
  void signUp() {

    // given
    final ArgumentCaptor<User> userCap = ArgumentCaptor.forClass(User.class);

    // when
    when(userService.isExistEmail(command.getEmail())).thenReturn(false);

    when(passwordEncoder.encode(command.getPassword())).thenReturn(command.getPassword());

    userService.signUp(command);

    // then
    verify(userRepository).save(userCap.capture());

    User user = userCap.getValue();

    assertThat(user.getEmail()).isEqualTo(command.getEmail());
    assertThat(user.getPassword()).isEqualTo(command.getPassword());
    assertThat(user.getName()).isEqualTo(command.getName());
    assertThat(user.getPhone()).isEqualTo(command.getPhone());
    assertThat(user.getGender()).isEqualTo(command.getGender());

  }

  @Test
  @DisplayName("회원가입 테스트 - 예외처리")
  void signUpException() {

    // given

    // when
    when(userService.isExistEmail(command.getEmail())).thenReturn(true);

    // then
    assertThatThrownBy(() -> userService.signUp(command))
        .isInstanceOf(AlreadyExistEmailException.class);

  }

  @Test
  @DisplayName("중복 이메일 체크 테스트 - 정상적인 경우")
  void isExistEmail() {

    // given

    // when
    when(userRepository.existsByEmail(email)).thenReturn(true);

    boolean result = userService.isExistEmail(email);

    // then
    verify(userRepository, times(1)).existsByEmail(email);

    assertThat(result).isTrue();

  }

  @Test
  @DisplayName("이메일로 회원 조회 테스트 - 정상적인 경우")
  void findByEmail() {

    // given
    Optional<User> optionalUser = Optional.of(user);

    // when
    when(userRepository.findByEmail(email)).thenReturn(optionalUser);

    User findUser = userService.findByEmail(email);

    // then
    verify(userRepository, times(1)).findByEmail(email);

    assertThat(findUser.getEmail()).isEqualTo(user.getEmail());

  }

  @Test
  @DisplayName("이메일로 회원 조회 테스트 - 예외처리")
  void findByEmailException() {

    // given

    // when
    when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

    // then
    assertThatThrownBy(() -> userService.findByEmailAndPw(email, password))
        .isInstanceOf(NotValidEmailException.class);

  }


  @Test
  @DisplayName("이메일, 패스워드로 회원 조회 테스트 - 정상적인 경우")
  void findByEmailAndPw() {

    // given
    Optional<User> optionalUser = Optional.of(user);

    // when
    when(userRepository.findByEmail(email)).thenReturn(optionalUser);

    when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);

    User findUser = userService.findByEmailAndPw(email, password);

    // then
    assertThat(findUser.getEmail()).isEqualTo(user.getEmail());
    assertThat(findUser.getPassword()).isEqualTo(user.getPassword());

  }

  @Test
  @DisplayName("이메일, 패스워드로 회원 조회 테스트 - 예외처리")
  void findByEmailAndPwException() {

    // given
    Optional<User> optionalUser = Optional.of(user);

    // when
    when(userRepository.findByEmail(email)).thenReturn(optionalUser);

    when(passwordEncoder.matches(password, user.getPassword())).thenReturn(false);

    // then
    assertThatThrownBy(() -> userService.findByEmailAndPw(email, password))
        .isInstanceOf(NotValidPasswordException.class);

  }
}