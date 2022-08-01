package com.flab.football.service.chat;

import com.flab.football.domain.Channel;
import com.flab.football.domain.Message;
import com.flab.football.domain.Message.Type;
import com.flab.football.domain.Participant;
import com.flab.football.domain.User;
import com.flab.football.repository.chat.ChannelRepository;
import com.flab.football.repository.chat.MessageRepository;
import com.flab.football.repository.chat.ParticipantRepository;
import com.flab.football.service.chat.command.PushMessageCommand;
import com.flab.football.service.redis.RedisService;
import com.flab.football.service.user.UserService;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

  private final UserService userService;

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
  public void sendMessage(int channelId, int sendUserId, String content) {

    Channel channel = findChannelById(channelId);

    User user = userService.findById(sendUserId);

    Message message = Message.builder()
        .type(Type.MESSAGE)
        .content(content)
        .createAt(LocalDateTime.now())
        .build();

    messageRepository.save(message);

    message.setUser(user);

    message.setChannel(channel);

    // 해당 채팅방에 메세지를 받아야하는 대상자를 조회
    // N+1 쿼리 제어 필요
    List<Integer> userIdList = findMessageReceivers(channelId);

    // 조회된 user들에 대해 메세지를 푸시한다.
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
  public List<Participant> findParticipantsByChannelId(int channelId) {

    return participantRepository.findAllByChannelId(channelId);

  }

  @Override
  @Transactional
  public List<Integer> findMessageReceivers(int channelId) {

    List<Integer> userIdList = new ArrayList<>();

    List<Participant> participants = findParticipantsByChannelId(channelId);

    for (Participant participant : participants) {

      userIdList.add(participant.getUser().getId());

    }

    return userIdList;

  }

  @Override
  @Transactional
  public void healthCheck(String address, int connectionCount, LocalDateTime lastHeartBeatTime) {

    redisService.setServerInfo(address, connectionCount, lastHeartBeatTime);

  }

  @Override
  @Transactional
  public String findPossibleConnectServerAddress() {

    Set<String> serverInfoKeySet = redisService.getServerInfoKeySet();

    if (serverInfoKeySet.size() == 0) {

      throw new RuntimeException("연결 가능한 웹소켓 서버가 없습니다.");

    }

    int minConnectionCount = Integer.MAX_VALUE;

    String address = "";

    for (String key : serverInfoKeySet) {

      int connectionCount = redisService.getConnectionCount(key);

      if (connectionCount < minConnectionCount) {

        minConnectionCount = connectionCount;

        address = redisService.getAddress(key);

      }

    }

    return "ws://" + address + "/ws/chat";

  }

  @Scheduled(fixedRate = 3000)
  public void refuseWebSocketServer() {

    Set<String> serverInfoKeySet = redisService.getServerInfoKeySet();

    if (serverInfoKeySet.size() == 0) {

      return;

    }

    for (String key : serverInfoKeySet) {

      long lastHeartBeatTime = redisService.getLastHeartBeatTime(key).toEpochSecond(ZoneOffset.UTC);

      long currentTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);

      if (currentTime - lastHeartBeatTime > 10) {

        log.info(key + "'s heartbeatTime = {}", currentTime - lastHeartBeatTime);

        // redis 에서 해당 서버 정보 삭제
        redisService.deleteServerInfo(key);

      }

    }

  }


}
