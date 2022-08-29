package com.flab.football.websocket.handler.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveConnectInfoRequest {

  private int userId;
  private String address;

}
