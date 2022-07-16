package com.flab.football.service.chat;

import com.flab.football.domain.Channel;
import com.flab.football.domain.Participant;
import com.flab.football.domain.User;
import com.flab.football.repository.chat.ChannelRepository;
import com.flab.football.repository.user.UserRepository;
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

  private final ChannelRepository channelRepository;

  @Override
  @Transactional
  public Channel createChannel() {

    Participant participant = Participant.builder()
        .build();

    Channel channel = Channel.builder()
        .build();


    return channelRepository.save(channel);

  }

}
