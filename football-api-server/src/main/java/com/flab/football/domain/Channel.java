package com.flab.football.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Channel 엔티티 클래스.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "channel")
public class Channel {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "name")
  private String name;

  @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL)
  private List<Participant> participants;

  /**
   * 참가자 객체를 리스트에 저장하는 메소드.
   * 직접 변수를 호출해 저장하지 않고 메소드로 구현해 내부 구현 로직을 캡슐화하기 위한 목적으로 생성된 메소드입니다.
   */

  public void addParticipant(Participant participant) {

    this.participants.add(participant);

  }
}
