package com.flab.football.websocket.conrtroller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRequest {

  private int channelId;
  private int sendUserId;
  private String content;

}
