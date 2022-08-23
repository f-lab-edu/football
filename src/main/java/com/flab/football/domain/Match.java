package com.flab.football.domain;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Match 엔티티 클래스.
 */

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`match`")
public class Match {

  @Getter
  public enum Rule {
    FIVE_VS_FIVE_TWO_TEAM, FIVE_VS_FIVE_THREE_TEAM,
    SIX_VS_SIX_TWO_TEAM, SIX_VS_SIX_THREE_TEAM
  }

  /**
   * 착용 가능 신발 제한 enum 클래스.
   */

  @Getter
  public enum LimitShoes {
    ALL, ONLY_FUTSAL_SHOES, ONLY_FOOTBALL_SHOES
  }

  /**
   * 참가자 레벨 제한 enum 클래스.
   */

  @Getter
  public enum LimitLevel {
    ALL, UNDER_BEGINNER, UNDER_AMATEUR, UPPER_PRO
  }

  /**
   * 성별 제한 enum 클래스.
   */

  @Getter
  public enum LimitGender {
    ALL, ONLY_MALE, ONLY_FEMALE
  }

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "start_time")
  private LocalDateTime startTime;

  @Column(name = "finish_time")
  private LocalDateTime finishTime;

  @Column(name = "min_number")
  private int min;

  @Column(name = "max_number")
  private int max;

  @Column(name = "rule")
  private Rule rule;

  @Column(name = "limit_shoes")
  private LimitShoes shoes;

  @Column(name = "limit_level")
  private LimitLevel level;

  @Column(name = "limit_gender")
  private LimitGender gender;

  @Column(name = "stadium_id", insertable = false, updatable = false)
  private int stadiumId;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "stadium_id", referencedColumnName = "id")
  private Stadium stadium;

  @OneToOne(mappedBy = "match", cascade = CascadeType.ALL)
  private Manager manager;

  @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
  private List<Member> members;

  /**
   * Match_Manger 엔티티 클래스.
   */

  @Setter
  @Getter
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  @Entity
  @Table(name = "match_manager")
  public static class Manager {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "match_id", insertable = false, updatable = false)
    private int matchId;

    @Column(name = "user_id", insertable = false, updatable = false)
    private int userId;

    @OneToOne
    @JoinColumn(name = "match_id", referencedColumnName = "id")
    private Match match;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

  }

  /**
   * match_member 엔티티 클래스.
   */

  @Setter
  @Getter
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  @Entity
  @Table(name = "match_member")
  public static class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "match_id", insertable = false, updatable = false)
    private int matchId;

    @Column(name = "user_id", insertable = false, updatable = false)
    private int userId;

    @ManyToOne
    @JoinColumn(name = "match_id", referencedColumnName = "id")
    private Match match;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
  }

}
