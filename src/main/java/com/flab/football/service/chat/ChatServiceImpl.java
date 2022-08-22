package com.flab.football.service.chat;

import com.flab.football.domain.Channel;
import com.flab.football.domain.Message;
import com.flab.football.domain.Message.Type;
import com.flab.football.domain.Participant;
import com.flab.football.repository.chat.ChannelRepository;
import com.flab.football.repository.chat.MessageRepository;
import com.flab.football.repository.chat.ParticipantRepository;
import com.flab.football.service.chat.command.PushMessageCommand;
import com.flab.football.service.redis.RedisService;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 채팅 관련 비즈니스 로직이 선언된 서비스 구현체.
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

  private final ChatPushService chatPushService;

  private final RedisService redisService;

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

    for (int userId : participants) {

      Participant participant = Participant.builder()
          .userId(userId)
          .channelId(channelId)
          .build();

      participantList.add(participant);

    }

    participantRepository.saveAll(participantList);

  }

  @Override
  @Transactional
  public void sendMessage(int channelId, int sendUserId, String content) {

    Message message = Message.builder()
        .type(Type.MESSAGE)
        .content(content)
        .createAt(LocalDateTime.now())
        .channelId(channelId)
        .userId(sendUserId)
        .build();

    messageRepository.save(message);

    List<Integer> userIdList = findMessageReceivers(channelId);

    for (int receiveUserId : userIdList) {

      if (receiveUserId != sendUserId) {

        PushMessageCommand command = PushMessageCommand.builder()
            .channelId(channelId)
            .sendUserId(sendUserId)
            .receiveUserId(receiveUserId)
            .content(content)
            .build();

        chatPushService.pushMessage(command);

      }

    }

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
  public List<Integer> findMessageReceivers(int channelId) {

    return participantRepository.findAllUserIdByChannelId(channelId);

  }

  @Override
  @Transactional(readOnly = true)
  public List<Participant> findParticipantsByChannelId(int channelId) {

    return participantRepository.findAllByChannelId(channelId);

  }

  @Override
  @Transactional
  public void healthCheck(String address, int connectionCount, LocalDateTime lastHeartBeatTime) {

    redisService.setWebSocketServerInfo(address, connectionCount, lastHeartBeatTime);

  }

  @Override
  @Transactional
  public String findPrimaryWebSocketServerAddress() {

    redisService.setPrimaryWebSocketServerKeys();

    String key = redisService.getPrimaryWebSocketServerKey();

    return "ws://" + redisService.getWebSocketAddress(key) + "/ws/chat";

  }

  @Scheduled(fixedRate = 3000)
  public void deleteWebSocketServer() {

    Cursor<String> keys = redisService.scanWebSocketServerKey();

    if (keys.hasNext()) {

      String key = keys.next();

      long lastHeartBeatTime = redisService.getWebSocketLastHeartBeatTime(key)
          .toEpochSecond(ZoneOffset.UTC);

      long currentTime = LocalDateTime.now()
          .toEpochSecond(ZoneOffset.UTC);

      if (currentTime - lastHeartBeatTime > 10) {

        log.info("server name {} died", key);

        redisService.deleteWebSocketServerInfo(key);

      }

    }

  }

}
