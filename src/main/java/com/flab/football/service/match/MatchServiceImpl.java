package com.flab.football.service.match;

import com.flab.football.domain.Match;
import com.flab.football.domain.Match.Manager;
import com.flab.football.domain.Stadium;
import com.flab.football.domain.User;
import com.flab.football.exception.NotExistStadiumException;
import com.flab.football.repository.match.MatchRepository;
import com.flab.football.repository.stadium.StadiumRepository;
import com.flab.football.repository.user.UserRepository;
import com.flab.football.service.match.command.CreateMatchCommand;
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

    User user = userRepository.findById(command.getUserId())
        .orElseThrow(() -> new RuntimeException("회원정보가 존재하지 않습니다."));

    Stadium stadium = stadiumRepository.findById(command.getStadiumId())
        .orElseThrow(() -> new NotExistStadiumException("구장 정보가 존재하지 않습니다."));

    Match match = Match.builder()
        .startTime(command.getStartTime())
        .finishTime(command.getFinishTime())
        .min(command.getMin())
        .max(command.getMax())
        .rule(command.getRule())
        .shoes(command.getShoes())
        .level(command.getLevel())
        .gender(command.getGender())
        .stadium(stadium)
        .build();

    Match.Manager manager = Manager.builder()
        .user(user)
        .build();

    manager.setMatch(match); // 매니저 - 매치 양방향 관계 설정

    match.setManager(manager); // 매치 - 매니저 양방향 관계 설정

    matchRepository.save(match);

  }

  @Override
  public void applyToParticipant(int userId, int matchId) {

    Match match = matchRepository.findById(matchId)
        .orElseThrow(() -> new RuntimeException("매치 정보가 존재하지 않습니다."));

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("회원정보가 존재하지 않습니다."));

    Match.Member member = Match.Member.builder()
        .match(match)
        .user(user)
        .build();

    matchRepository.save(member);

  }
}
