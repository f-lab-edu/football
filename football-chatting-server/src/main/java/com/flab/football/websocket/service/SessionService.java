package com.flab.football.websocket.service;

import org.springframework.web.socket.WebSocketSession;

public interface SessionService {

  void saveSession(int userId, WebSocketSession session);

  void removeSession(int userId, WebSocketSession session);

  WebSocketSession findSessionByUserId(int userId);

  int getSessionCount();

}
