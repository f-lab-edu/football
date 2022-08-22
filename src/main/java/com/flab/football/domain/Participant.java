package com.flab.football.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * Participant 엔티티 클래스.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "participant")
public class Participant {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "channel_id")
  private int channelId;

  @Column(name = "user_id")
  private int userId;

  @ManyToOne
  @JoinColumn(name = "channel_id", referencedColumnName = "id",
              insertable = false, updatable = false)
  private Channel channel;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id",
              insertable = false, updatable = false)
  private User user;

  /**
   * 매니저가 초대한 참가자 객체들을 담을 리스트 객체 생성 메소드.
   */

  public static List<Participant> listOf() {

    return new ArrayList<>();

  }

}
