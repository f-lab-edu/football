package com.flab.football.repository.chat;

import com.flab.football.domain.Channel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Integer> {

  Channel save(Channel channel);

  Optional<Channel> findById(int channelId);

}
