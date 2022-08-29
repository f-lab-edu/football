package com.flab.football.service.chat;

import com.flab.football.service.chat.command.PushMessageCommand;

public interface ChatPushService {

  void pushMessage(PushMessageCommand command);

}
