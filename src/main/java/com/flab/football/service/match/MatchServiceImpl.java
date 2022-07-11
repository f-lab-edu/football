package com.flab.football.service.match;

import com.flab.football.domain.Match;
import com.flab.football.domain.Match.Manager;
import com.flab.football.domain.Match.Member;
import com.flab.football.domain.Stadium;
import com.flab.football.domain.User;
import com.flab.football.exception.NotExistStadiumException;
import com.flab.football.repository.match.MatchRepository;
import com.flab.football.repository.stadium.StadiumRepository;
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

  private final StadiumRepository stadiumRepository;

  private final MatchRepository matchRepository;

  /**
   * 매치 저장 로직.
   */

  @Override
  public void createMatch(CreateMatchCommand command) {

    Optional<User> user = userRepository.findById(command.getUserId());

    if (user.isEmpty()) {

      throw new RuntimeException("찾는 회원이 없습니다.");

    }

    Optional<Stadium> stadium = stadiumRepository.findById(command.getStadiumId());

    if (stadium.isEmpty()) {

      throw new NotExistStadiumException("찾는 구장 정보가 없습니다.");

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

    manager.setMatch(match); // 매치 - 매니저 양방향 관계 설정

    match.setManager(manager); // 매치 - 매니저 양방향 관계 설정

    match.setStadium(stadium.get()); // 매치 - 구장 단방향 관계 설정

    matchRepository.save(match);

  }

  @Override
  public void applyToParticipant(int userId, int matchId) {

    Optional<User> user = userRepository.findById(userId);

    if (user.isEmpty()) {

      throw new RuntimeException("회원 정보가 일치하지 않습니다.");

    }

    Optional<Match> match = matchRepository.findById(matchId);

    if(match.isEmpty()) {

      throw new RuntimeException("매치 정보가 일치하지 않습니다.");

    }

    Match.Member member = Match.Member.builder()
        .user(user.get())
        .match(match.get())
        .build();

    matchRepository.save(member);

    match.get().getMembers().add(member); // 매치 - 멤버 일대다 관계 생성

    matchRepository.save(match.get()); // 변경 감지 후 insert

  }
}
