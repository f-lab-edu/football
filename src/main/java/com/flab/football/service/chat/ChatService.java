package com.flab.football.service.chat;

import com.flab.football.domain.Channel;
import com.flab.football.domain.Message;
import com.flab.football.domain.Participant;
import java.util.List;

public interface ChatService {

  void createChannel(String name);

  void inviteParticipants(int channelId, List<Integer> participants);

  void saveMessage(Message.Type type, int channelId, String content);

  Channel findChannelById(int channelId);

  List<Participant> findParticipantsByChannelId(int channelId);

  List<String> findMessageReceivers(int channelId);

}
