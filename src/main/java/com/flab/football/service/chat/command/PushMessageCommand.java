package com.flab.football.service.chat.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushMessageCommand {

  private int channelId;
  private int sendUserId;
  private int receiveUserId;
  private String content;

}
