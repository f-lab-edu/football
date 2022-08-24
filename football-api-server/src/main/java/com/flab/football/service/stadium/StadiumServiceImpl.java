package com.flab.football.service.stadium;

import com.flab.football.domain.Stadium;
import com.flab.football.domain.Stadium.StadiumInfo;
import com.flab.football.exception.NotExistStadiumException;
import com.flab.football.repository.stadium.StadiumRepository;
import com.flab.football.service.stadium.command.SaveStadiumCommand;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StadiumServiceImpl implements StadiumService {

  private final StadiumRepository stadiumRepository;

  @Override
  public void save(SaveStadiumCommand command) {

    Stadium stadium = Stadium.builder()
        .name(command.getName())
        .build();

    stadiumRepository.save(stadium);

    StadiumInfo info = StadiumInfo.builder()
        .rentalFee(command.getRentalFee())
        .showerRoom(command.isShowerRoom())
        .shoes(command.isShoes())
        .uniform(command.isUniform())
        .build();

    info.setStadium(stadium);
    stadium.setInfo(info);

    stadiumRepository.save(info);

  }

  public Stadium findById(int stadiumId) {

    return stadiumRepository.findById(stadiumId)
        .orElseThrow(() -> new NotExistStadiumException("구장 정보를 조회할 수 없습니다."));

  }

}
