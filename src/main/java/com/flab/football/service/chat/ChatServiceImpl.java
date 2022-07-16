package com.flab.football.service.chat;

import com.flab.football.domain.Channel;
import com.flab.football.domain.Message;
import com.flab.football.domain.Participant;
import com.flab.football.domain.User;
import com.flab.football.repository.chat.ChannelRepository;
import com.flab.football.repository.chat.MessageRepository;
import com.flab.football.repository.chat.ParticipantRepository;
import com.flab.football.repository.user.UserRepository;
import com.flab.football.service.security.SecurityService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 채팅 관련 비즈니스 로직이 선언된 서비스 구현체.
 */

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

  private final UserRepository userRepository;

  //private final SecurityService securityService;

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
  public void saveParticipants(int channelId, List<Integer> participants) {

    Optional<Channel> channel = channelRepository.findById(channelId);

    if (channel.isEmpty()) {

      throw new RuntimeException("채팅방 정보가 존재하지 않습니다.");

    }

    for (int userId : participants) {

      Optional<User> user = userRepository.findById(userId);

      if (user.isEmpty()) {

        throw new RuntimeException("회원 정보가 존재하지 않습니다.");
      }

      Participant participant = Participant.builder()
          .user(user.get())
          .build();

      participantRepository.save(participant);

      participant.setChannel(channel.get());

      channel.get().getParticipants().add(participant);

      channelRepository.save(channel.get());

    }

  }

  @Override
  @Transactional
  public void saveMessage(Message.Type type, int channelId, String content) {

    Optional<Channel> channel = channelRepository.findById(channelId);

    if (channel.isEmpty()) {

      throw new RuntimeException("채팅방 정보가 존재하지 않습니다.");

    }

    //String name = securityService.getCurrentUserName();

    Message message = Message.builder()
        .channel(channel.get())
        .type(type)
        .content(content)
        //.sender(name)
        .createAt(LocalDateTime.now())
        .build();

    messageRepository.save(message);

    channel.get().getMessages().add(message);

    channelRepository.save(channel.get());

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
}
