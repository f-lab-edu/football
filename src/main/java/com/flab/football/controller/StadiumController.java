package com.flab.football.controller;

import com.flab.football.controller.request.SaveStadiumRequest;
import com.flab.football.controller.response.ResponseDto;
import com.flab.football.controller.response.data.FindStadiumData;
import com.flab.football.domain.Stadium;
import com.flab.football.service.stadium.StadiumService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/stadium")
@RequiredArgsConstructor
public class StadiumController {

  private final StadiumService stadiumService;

  @PostMapping
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  public ResponseDto saveStadium(@Valid @RequestBody SaveStadiumRequest request) {

    stadiumService.save(SaveStadiumRequest.toCommand(request));

    return new ResponseDto(true, null, "저장 성공", null);

  }

  @GetMapping("/{stadiumId}")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  public ResponseDto findStadium(@PathVariable(value = "stadiumId") int stadiumId) {

    Stadium stadium = stadiumService.findById(stadiumId);

    return new ResponseDto(

        true, FindStadiumData.from(stadium), "조회 성공", null

    );

  }

}
