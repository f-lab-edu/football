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

    Stadium.StadiumInfo info = StadiumInfo.builder()
        .rentalFee(command.getRentalFee())
        .showerRoom(command.isShowerRoom())
        .shoes(command.isShoes())
        .uniform(command.isUniform())
        .build();

    Stadium stadium = Stadium.builder()
        .name(command.getName())
        .build();

    info.setStadium(stadium);

    stadium.setInfo(info);

    stadiumRepository.save(stadium);

  }

  public Stadium findById(int stadiumId) {

    Optional<Stadium> stadium = stadiumRepository.findById(stadiumId);

    if (stadium.isEmpty()) {

      throw new NotExistStadiumException("해당 구장 정보는 존재하지 않습니다.");

    }

    return stadium.get();

  }

}
