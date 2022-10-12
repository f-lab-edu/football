package com.flab.football.controller.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 채팅방 생성 request 클래스.
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateChannelRequest {

  @NotBlank
  private String name;

}
