package com.flab.football.service.chat;

import com.flab.football.domain.Channel;
import com.flab.football.domain.Message;
import com.flab.football.domain.Message.Type;
import com.flab.football.domain.Participant;
import com.flab.football.domain.User;
import com.flab.football.repository.chat.ChannelRepository;
import com.flab.football.repository.chat.MessageRepository;
import com.flab.football.repository.chat.ParticipantRepository;
import com.flab.football.service.security.SecurityService;
import com.flab.football.service.user.UserService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 채팅 관련 비즈니스 로직이 선언된 서비스 구현체.
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

  private final UserService userService;

  private final SecurityService securityService;

  private final ChannelRepository channelRepository;

  private final ParticipantRepository participantRepository;

  private final MessageRepository messageRepository;

  @Override
  @Transactional
  public void createChannel(String name) {

    Channel channel = Channel.builder()
        .name(name)
        .build();

    channelRepository.save(channel);

  }

  @Override
  @Transactional
  public void inviteParticipants(int channelId, List<Integer> participants) {

    List<Participant> participantList = Participant.listOf();

    Channel channel = findChannelById(channelId);

    List<User> users = userService.findAllById(participants);

    for (User user : users) {

      Participant participant = Participant.builder()
          .user(user)
          .build();

      participant.setChannel(channel);

      channel.addParticipant(participant);

      participantList.add(participant);

    }

    participantRepository.saveAll(participantList);

  }

  @Override
  @Transactional
  public void saveMessage(int channelId, int userId, String content) {

    Channel channel = findChannelById(channelId);

    User user = userService.findById(userId);

    Message message = Message.builder()
        .type(Type.MESSAGE)
        .content(content)
        .createAt(LocalDateTime.now())
        .build();

    messageRepository.save(message);

    message.setUser(user);

    message.setChannel(channel);

    channel.addMessage(message);

  }

  @Override
  @Transactional(readOnly = true)
  public Channel findChannelById(int channelId) {

    Optional<Channel> channel = channelRepository.findById(channelId);

    if (channel.isEmpty()) {

      throw new RuntimeException("채팅방 정보가 존재하지 않습니다.");

    }

    return channel.get();

  }

  @Override
  @Transactional(readOnly = true)
  public List<Participant> findParticipantsByChannelId(int channelId) {

    return participantRepository.findAllByChannelId(channelId);

  }

  @Override
  @Transactional
  public List<Integer> findMessageReceivers(int channelId) {

    List<Integer> userIdList = new ArrayList<>();

    // channelId로 참가중인 참가자들의 user 정보를 조회한다.
    List<Participant> participants = findParticipantsByChannelId(channelId);

    for (Participant participant : participants) {

      userIdList.add(participant.getUser().getId());

    }

    return userIdList;

  }

}
