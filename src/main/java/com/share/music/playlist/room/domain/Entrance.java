package com.share.music.playlist.room.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ROOM_USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Entrance {

  @MapsId("roomId")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ROOM_ID")
  private Room room;

  @EmbeddedId
  @EqualsAndHashCode.Include
  private EntranceId id;

  @Embeddable
  @EqualsAndHashCode
  public class EntranceId implements Serializable {

    private Long roomId; // @MapsId

    @Column(name = "USER_ID")
    private String userId;
  }

}
