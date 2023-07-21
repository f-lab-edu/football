package com.flab.football.repository.chat;

import com.flab.football.domain.Channel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Integer> {
  @Query(value = "select c from Channel c join fetch c.participants where c.id = :channelId")
  Optional<Channel> findByIdFetchJoin(int channelId);

}
