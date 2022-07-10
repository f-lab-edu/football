package com.flab.football.controller;

import com.flab.football.controller.request.CreateMatchRequest;
import com.flab.football.controller.response.ResponseDto;
import com.flab.football.service.match.MatchService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 매치 생성 및 조회 관련 API가 선언된 컨트롤러.
 */

@Slf4j
@RestController
@RequestMapping("/match")
@RequiredArgsConstructor
public class MatchController {

  private final MatchService matchService;

  /**
   * 매니저에 의한 매치 생성 API.
   * 매치 생성은 매니저에 의해서 생성됩니다.
   */

  @PostMapping("/user/{userId}/stadium/{stadiumId}")
  @PreAuthorize("hasAnyRole('ROLE_MANAGER')")
  public ResponseDto createMatch(@PathVariable(value = "userId") int userId,
                                 @PathVariable(value = "stadiumId") int stadiumId,
                                 @RequestBody @Valid CreateMatchRequest request) {

    matchService.save(CreateMatchRequest.toCommand(request, userId, stadiumId));

    return new ResponseDto(true, null, "매치 생성 완료", null);

  }

}
