package com.flab.football.repository.stadium;

import com.flab.football.domain.Stadium;
import com.flab.football.domain.Stadium.StadiumInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * StadiumRepository 클래스.
 */

@Repository
public interface StadiumRepository extends JpaRepository<Stadium, Integer> {

  Stadium save(Stadium stadium);

  StadiumInfo save(StadiumInfo stadiumInfo);

  Optional<Stadium> findById(int id);

}
