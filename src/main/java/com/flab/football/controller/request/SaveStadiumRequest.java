package com.flab.football.controller.request;

import com.flab.football.service.stadium.command.SaveStadiumCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SaveStadiumRequest {

  private String name;
  private int rentalFee;
  private boolean showerRoom;
  private boolean shoes;
  private boolean uniform;


  public static SaveStadiumCommand toCommand(SaveStadiumRequest request) {

   return new SaveStadiumCommand(
       request.getName(),
       request.getRentalFee(),
       request.isShowerRoom(),
       request.isShoes(),
       request.isUniform()
   );

  }
}
