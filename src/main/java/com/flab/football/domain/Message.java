package com.flab.football.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Message 엔티티 클래스.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "message")
public class Message {

  @Getter
  @AllArgsConstructor
  public enum Type {
    ENTER, MESSAGE, QUIT
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private Type type;

  @Column(name = "content")
  private String content;

  @Column(name = "create_at")
  private LocalDateTime createAt;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "channel_id", referencedColumnName = "id")
  private Channel channel;

}
