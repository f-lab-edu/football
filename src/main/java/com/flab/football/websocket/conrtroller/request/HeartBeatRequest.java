package com.flab.football.websocket.conrtroller.request;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeartBeatRequest {

  private String address;
  private int connectionCount;
  private LocalDateTime heartBeatTime;

}
