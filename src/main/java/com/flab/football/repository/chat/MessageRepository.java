package com.flab.football.repository.chat;

import com.flab.football.domain.Message;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

  Message save(Message message);

  Optional<Message> findById(Message message);

}
