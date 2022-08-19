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

    // message 객체에 주입할 channel 정보 조회
    Channel channel = findChannelById(channelId);

    // message 객체에 주입할 user 정보를 조회
    User user = userService.findById(sendUserId);

    Message message = Message.builder()
        .type(Type.MESSAGE)
        .content(content)
        .createAt(LocalDateTime.now())
        .build();

    // user 정보 주입
    message.setUser(user);

    // channel 정보 주입
    message.setChannel(channel);

    messageRepository.save(message);

    // 해당 채팅방에 메세지를 받아야하는 대상자를 조회 -> N+1 쿼리 발생 지점!!
    List<Integer> userIdList = findMessageReceivers(channelId);

    // 조회된 user들에 대해 메세지를 푸시
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
  @Transactional
  public List<Integer> findMessageReceivers(int channelId) {

    List<Integer> userIdList = new ArrayList<>();

    List<Participant> participants = findParticipantsByChannelId(channelId);

    for (Participant participant : participants) {

      // participant 객체에 저장된 User 객체를 사용하기 위해 N번의 쿼리문 발생

      // 잠깐! N+1 쿼리라기 보다는 연관관계를 가진 객체를 조회하는 지점에서 쿼리문이 발생하는 지연 로딩으로 인한 N번의 쿼리문 발생 지점이진 않은지...
      userIdList.add(participant.getUser().getId());

    }

    return userIdList;

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
