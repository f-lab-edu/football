package com.flab.football.service.stadium;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.flab.football.domain.Stadium;
import com.flab.football.exception.NotExistStadiumException;
import com.flab.football.repository.stadium.StadiumRepository;
import com.flab.football.service.stadium.command.SaveStadiumCommand;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StadiumServiceImplTest {

  StadiumService stadiumService;

  StadiumRepository stadiumRepository;

  SaveStadiumCommand command;

  @BeforeEach
  void setUp() {

    stadiumRepository = mock(StadiumRepository.class);

    stadiumService = new StadiumServiceImpl(stadiumRepository);

    command = new SaveStadiumCommand(
        "name", 10000, true, true, true
    );

  }

  @Test
  @DisplayName("구장 정보 저장 로직 테스트 - 정상적인 경우")
  void save() {
    // given

    // when
    stadiumService.save(command);

    ArgumentCaptor<Stadium> captor = ArgumentCaptor.forClass(Stadium.class);

    // then
    verify(stadiumRepository, times(1)).save(captor.capture());

    Stadium stadium = captor.getValue();

    assertThat(stadium.getName()).isEqualTo(command.getName());
    assertThat(stadium.getInfo().getRentalFee()).isEqualTo(command.getRentalFee());
    assertThat(stadium.getInfo().isShoes()).isEqualTo(command.isShoes());
    assertThat(stadium.getInfo().isUniform()).isEqualTo(command.isUniform());
    assertThat(stadium.getInfo().isShowerRoom()).isEqualTo(command.isShowerRoom());

  }

  @Test
  @DisplayName("구장 정보 조회 로직 테스트 - 정상적인 경우")
  void findById() {

    //given
    int stadiumId = 1;

    Stadium stadium = Stadium.builder()
        .id(stadiumId)
        .build();

    Optional<Stadium> optionalStadium = Optional.of(stadium);

    //when
    when(stadiumRepository.findById(stadiumId)).thenReturn(optionalStadium);

    Stadium findStadium = stadiumService.findById(stadiumId);

    //then
    assertThat(findStadium.getId()).isEqualTo(stadium.getId());

  }

  @Test
  @DisplayName("구장 정보 조회 로직 테스트 - 예외처리")
  void findByIdException() {

    //given

    //when
    when(stadiumRepository.findById(anyInt())).thenReturn(Optional.empty());

    //then
    assertThatThrownBy(() -> stadiumService.findById(anyInt()))
        .isInstanceOf(NotExistStadiumException.class);

  }

}