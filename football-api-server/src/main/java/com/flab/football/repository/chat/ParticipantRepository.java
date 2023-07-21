package com.flab.football.repository.chat;

import com.flab.football.domain.Participant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {

  Participant save(Participant participant);

  @Override
  <S extends Participant> List<S> saveAll(Iterable<S> entities);

  Optional<Participant> findById(Participant participant);

  @Query("SELECT p FROM Participant p join fetch p.user WHERE p.channel.id = :channelId")
  List<Participant> findAllByChannelIdFetchJoin(@Param(value = "channelId") int channelId);

  List<Participant> findAllByChannelId(@Param(value = "channelId") int channelId);

  @Query("SELECT p.userId FROM Participant p WHERE p.channel.id = :channelId")
  List<Integer> findAllUserIdByChannelId(@Param(value = "channelId") int channelId);

}
