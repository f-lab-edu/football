package com.flab.football.service.match;

import com.flab.football.domain.Match;
import com.flab.football.domain.Match.Manager;
import com.flab.football.domain.User;
import com.flab.football.domain.User.Role;
import com.flab.football.repository.match.MatchRepository;
import com.flab.football.repository.user.UserRepository;
import com.flab.football.service.match.command.CreateMatchCommand;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 매치 모델 관련 비즈니스 로직이 선언되어 있는 서비스 클래스.
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

  private final UserRepository userRepository;

  private final MatchRepository matchRepository;

  /**
   * 매치 저장 로직.
   */

  public void save(CreateMatchCommand command) {

    Optional<User> user = userRepository.findById(command.getUserId());

    if (user.isEmpty()) {

      throw new RuntimeException("찾는 회원이 없습니다.");

    } else if (!user.get().getRole().equals(Role.ROLE_MANAGER)) {

      throw new RuntimeException("매니저 권한이 없는 회원입니다.");

    }

    Match.Manager manager = Manager.builder()
        .user(user.get())
        .build();

    matchRepository.save(manager);

    Match match = Match.builder()
        .startTime(command.getStartTime())
        .finishTime(command.getFinishTime())
        .min(command.getMin())
        .max(command.getMax())
        .rule(command.getRule())
        .shoes(command.getShoes())
        .level(command.getLevel())
        .gender(command.getGender())
        .build();

    match.setManager(manager);
    manager.setMatch(match);

    matchRepository.save(match);

  }



}
