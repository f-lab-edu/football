package com.flab.football.service.chat;

import com.flab.football.domain.Channel;
import com.flab.football.domain.Message;
import java.util.List;

public interface ChatService {

  void createChannel(String name);

  void inviteParticipants(int channelId, List<Integer> participants);

  void saveMessage(Message.Type type, int channelId, String content);

  Channel findChannelById(int channelId);


}
