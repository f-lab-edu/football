package com.flab.football.repository.match;

import com.flab.football.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Match Repository 인터페이스.
 */

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {

  Match save(Match match);

  Match.Manager save(Match.Manager manager);


  // member 추가 로직

}
