package com.flab.football.controller.response.data;

import com.flab.football.domain.Stadium;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindStadiumData {

  private String name;
  private int fee;
  private boolean showerRoom;
  private boolean shoes;
  private boolean uniform;


  public static FindStadiumData from(Stadium stadium) {

    return new FindStadiumData(
        stadium.getName(),
        stadium.getInfo().getRentalFee(),
        stadium.getInfo().isShowerRoom(),
        stadium.getInfo().isShoes(),
        stadium.getInfo().isUniform()
    );

  }

}
