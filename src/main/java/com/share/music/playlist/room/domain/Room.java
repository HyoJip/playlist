package com.share.music.playlist.room.domain;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "ROOMS")
public class Room {

  @Id
  @GeneratedValue
  @Column(name = "ROOM_ID")
  @EqualsAndHashCode.Include
  private Long id;

  @Column(name = "ROOM_MASTER_ID")
  private String ownerId;

  @OneToMany(mappedBy = "room")
  private List<Entrance> entrance = new ArrayList<>();

  // TODO 룸 카테고리

  @Column(name = "NUMBER_LIMIT")
  private int limit;

  @Column(name = "ROOM_TITLE")
  private String title;

  @Column(name = "ROOM_COMMENT")
  private String comment;

  @Builder
  public Room(Long id, String ownerId, int limit, String title, String comment) {
    checkArgument(StringUtils.isNotBlank(ownerId), "ownerId must be provided.");
    checkArgument(StringUtils.isNotBlank(title), "title must be provided.");

    this.id = id;
    this.ownerId = ownerId;
    this.limit = limit;
    this.title = title;
    this.comment = comment;
  }
}

