package com.flab.football.controller.request;


import com.flab.football.domain.Match.LimitGender;
import com.flab.football.domain.Match.LimitLevel;
import com.flab.football.domain.Match.LimitShoes;
import com.flab.football.domain.Match.Rule;
import com.flab.football.service.match.command.CreateMatchCommand;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 매치 생성 request 매핑 클래스.
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMatchRequest {

  private LocalDateTime startTime;
  private LocalDateTime finishTime;
  private int min;
  private int max;
  private Rule rule;
  private LimitLevel level;
  private LimitShoes shoes;
  private LimitGender gender;

  /**
   * request 객체에서 command 객체로 변경하기 위한 팩토리 메소드
   */

  public static CreateMatchCommand toCommand(CreateMatchRequest request,
      int userId, int stadiumId) {

    return new CreateMatchCommand(userId, stadiumId, request.startTime, request.finishTime,
        request.min, request.max, request.rule, request.level, request.shoes, request.gender);

  }

}
