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
        .orElseThrow(() -> new RuntimeException("회원 정보를 조회할 수 없습니다."));

    Stadium stadium = stadiumRepository.findById(command.getStadiumId())
        .orElseThrow(() -> new NotExistStadiumException("구장 정보를 조회할 수 없습니다."));

    Manager manager = Manager.builder()
        .user(user)
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

    match.setStadium(stadium); // 매치 - 구장 단방향 관계 설정

    matchRepository.save(match);

  }

  @Override
  public void applyToParticipant(int userId, int matchId) {

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("회원 정보를 조회할 수 없습니다."));

    Match match = matchRepository.findById(matchId)
        .orElseThrow(() -> new RuntimeException("매치 정보를 조회할 수 없습니다."));


    Member member = Member.builder()
        .user(user)
        .match(match)
        .build();

    matchRepository.save(member);

    match.getMembers().add(member); // 매치 - 멤버 일대다 관계 생성

    matchRepository.save(match); // 변경 감지 후 insert

  }
}
