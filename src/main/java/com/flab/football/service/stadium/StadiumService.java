package com.flab.football.service.stadium;

import com.flab.football.domain.Stadium;
import com.flab.football.service.stadium.command.SaveStadiumCommand;

public interface StadiumService {

  void save(SaveStadiumCommand command);

  Stadium findById(int stadiumId);

}
