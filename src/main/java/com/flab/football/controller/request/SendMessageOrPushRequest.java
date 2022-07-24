package com.flab.football.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageOrPushRequest {

  private int channelId;
  private int userId;
  private String content;

}
