package com.flab.football.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
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

  // 외래키 값을 그대로 받아오는 형태
  @Column(name = "channel_id", insertable = false, updatable = false)
  private int channelId;

  @Column(name = "user_id", insertable = false, updatable = false)
  private int userId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "channel_id", referencedColumnName = "id")
  private Channel channel;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  /**
   * 매니저가 초대한 참가자 객체들을 담을 리스트 객체 생성 메소드.
   */

  public static List<Participant> listOf() {

    return new ArrayList<>();

  }

}
