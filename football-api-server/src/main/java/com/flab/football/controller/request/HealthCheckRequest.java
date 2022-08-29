package com.flab.football.controller.request;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthCheckRequest {

  private String address;
  private int connectionCount;
  private LocalDateTime heartBeatTime;

}
