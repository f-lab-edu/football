package com.flab.football.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stadium")
public class Stadium {

  @Id @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "name")
  private String name;

  @OneToOne(mappedBy = "stadium", cascade = CascadeType.ALL)
  private StadiumInfo info;

  @Setter
  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Entity
  @Table(name = "stadium_info")
  public static class StadiumInfo {

    @Id @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "rental_fee")
    private int rentalFee;

    @Column(name = "rental_shower_room")
    private boolean showerRoom;

    @Column(name = "rental_shoes")
    private boolean shoes;

    @Column(name = "rental_uniform")
    private boolean uniform;

    @OneToOne
    @JoinColumn(name = "stadium_id", referencedColumnName = "id")
    private Stadium stadium;

  }

}


