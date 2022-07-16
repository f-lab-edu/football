package com.flab.football.repository.chat;

import com.flab.football.domain.Participant;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {

  Participant save(Participant participant);

  Optional<Participant> findById(Participant participant);

}
