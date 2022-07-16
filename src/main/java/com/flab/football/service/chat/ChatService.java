package com.flab.football.service.chat;

import com.flab.football.domain.Channel;
import java.util.List;

public interface ChatService {

  Channel createChannel(String name);

  void inviteParticipants(int channelId, List<Integer> participants);

}
