package com.flab.football.repository.chat;

import com.flab.football.domain.Participant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {

  Participant save(Participant participant);

  @Override
  <S extends Participant> List<S> saveAll(Iterable<S> entities);

  Optional<Participant> findById(Participant participant);

  List<Participant> findAllByChannelId(int channelId);

}
