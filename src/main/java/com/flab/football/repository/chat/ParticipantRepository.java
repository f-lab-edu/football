package com.flab.football.repository.chat;

import com.flab.football.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {

  Participant save(Participant participant);

}
