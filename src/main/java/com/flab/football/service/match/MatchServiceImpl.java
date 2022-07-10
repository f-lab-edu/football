package com.flab.football.service.match;

import com.flab.football.domain.Match;
import com.flab.football.domain.Match.MatchInfo;
import com.flab.football.repository.match.MatchRepository;
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

  private final MatchRepository matchRepository;

  /**
   * 매치 저장 로직.
   */

  public void save(CreateMatchCommand command) {

    MatchInfo info = MatchInfo.builder()
        .max(command.getMax())
        .min(command.getMin())
        .rule(command.getRule())
        .shoes(command.getShoes())
        .level(command.getLevel())
        .gender(command.getGender())
        .build();

    Match match = Match.builder()
        .startTime(command.getStartTime())
        .finishTime(command.getFinishTime())
        .build();

    info.setMatch(match);
    match.setInfo(info);

    matchRepository.save(match);

  }



}
