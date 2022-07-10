package com.flab.football.service.match.command;

import com.flab.football.domain.Match.MatchInfo.LimitGender;
import com.flab.football.domain.Match.MatchInfo.LimitLevel;
import com.flab.football.domain.Match.MatchInfo.LimitShoes;
import com.flab.football.domain.Match.MatchInfo.Rule;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 매치 생성 command 클래스.
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMatchCommand {

  private int userId;
  private int stadiumId;

  private LocalDateTime startTime;
  private LocalDateTime finishTime;

  private int max;
  private int min;
  private Rule rule;
  private LimitLevel level;
  private LimitShoes shoes;
  private LimitGender gender;

}
