package com.flab.football.service.chat;

import com.flab.football.domain.Channel;
import com.flab.football.domain.Participant;
import com.flab.football.domain.User;
import com.flab.football.repository.chat.ChannelRepository;
import com.flab.football.repository.chat.ParticipantRepository;
import com.flab.football.repository.user.UserRepository;
import com.flab.football.service.chat.command.CreateChannelCommand;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.Part;
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

  private final ChannelRepository channelRepository;

  private final ParticipantRepository participantRepository;

  @Override
  @Transactional
  public Channel createChannel(String name) {

    Channel channel = Channel.builder()
        .name(name)
        .build();

    return channelRepository.save(channel);

  }

  @Override
  @Transactional
  public void inviteParticipants(int channelId, List<Integer> participants) {

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

}
