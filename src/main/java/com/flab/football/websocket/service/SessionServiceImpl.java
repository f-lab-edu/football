package com.flab.football.websocket.service;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

  private final Map<Integer, WebSocketSession> sessions;

  @Override
  public void saveSession(int userId, WebSocketSession session) {

    sessions.put(userId, session);

  }

  @Override
  public void removeSession(int userId, WebSocketSession session) {

    sessions.remove(userId, session);

  }

  @Override
  public WebSocketSession findSessionByUserId(int userId) {

    return sessions.get(userId);

  }

}