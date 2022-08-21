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

/*
 * 지속적으로 개수가 증가할 데이터인데 일대다 연관관계를 가지게 되면 심각한 N+1 쿼리를 발생할 수 있는 문제가 존재하지 않을까?
 * 멘토님 답변
   : N+1 쿼리 문제보단 엔티티 객체 로딩 시 너무 많은 양의 메세지 데이터가 함께 로딩되면 OOM(Out Of Memory Error) 발생할 수 있는 가능성이 높다.

  @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL)
  private List<Message> messages;
*/

  /**
   * 참가자 객체를 리스트에 저장하는 메소드.
   * 직접 변수를 호출해 저장하지 않고 메소드로 구현해 내부 구현 로직을 캡슐화하기 위한 목적으로 생성된 메소드입니다.
   */

  public void addParticipant(Participant participant) {

    this.participants.add(participant);

  }

  /*
   * messages 삭제 여부 판단 이후 주석처리를 할 지 다시 선택.

   * 메세지 객체를 리스트에 저장하는 메소드.
   * 직접 변수를 호출해 저장하지 않고 메소드로 구현해 내부 구현 로직을 캡슐화하기 위한 목적으로 생성된 메소드입니다.

  public void addMessage(Message message) {

    this.messages.add(message);

  }
  */
}
