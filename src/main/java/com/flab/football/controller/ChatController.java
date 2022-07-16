package com.flab.football.controller;

import com.flab.football.controller.response.ResponseDto;
import com.flab.football.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 채팅 기능 관련 API 선언이 되어있는 컨트롤러.
 */

@RestController("/chat")
@RequiredArgsConstructor
public class ChatController {

  private final ChatService chatService;

  /**
   * 새로운 채팅방 생성 API.
   */

  @PostMapping
  public ResponseDto createChannel() {


    return new ResponseDto(true, null, "채팅방 생성 완료", null);

  }

  /**
   * 로그인한 유저가 포함된 채팅방 목록을 조회 API.
   */

  @GetMapping
  public ResponseDto findChannels() {


    return new ResponseDto(true, null, "채팅방 생성 완료", null);
  }

}
