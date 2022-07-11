package com.flab.football.service.match;

import com.flab.football.service.match.command.CreateMatchCommand;

/**
 * MatchService 인터페이스.
 */

public interface MatchService {

  void save(CreateMatchCommand command);

}
