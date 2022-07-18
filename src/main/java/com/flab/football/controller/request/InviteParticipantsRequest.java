package com.flab.football.controller.request;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 채팅방에 초대될 참가자 리스트에 대한 JSON 객체를 직렬화하기 위한 Request 클래스.
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InviteParticipantsRequest {

  @NotNull @Min(value = 1)
  private List<Integer> participants;

}
