package com.share.music.playlist.room.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ROOM_MUSIC_CATEGORY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class CategoryTag {

  @MapsId("roomId")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ROOM_ID")
  private Room room;

  @EmbeddedId
  @EqualsAndHashCode.Include
  private CategoryTagId id;

  @Embeddable
  @EqualsAndHashCode
  class CategoryTagId implements Serializable {

    @Column(name = "CATEGORY_ID")
    private Long categoryId;

    private Long roomId;
  }
}
