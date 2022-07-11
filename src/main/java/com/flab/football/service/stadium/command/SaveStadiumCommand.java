package com.flab.football.service.stadium.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SaveStadiumCommand {

  private String name;
  private int rentalFee;
  private boolean showerRoom;
  private boolean shoes;
  private boolean uniform;

}
