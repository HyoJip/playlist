package com.share.music.playlist.room.domain;

import com.share.music.playlist.login.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

import static com.google.common.base.Preconditions.checkArgument;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ROOMS")
public class Room {

  @Id
  @GeneratedValue
  @Column(name = "ROOM_ID")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ROOM_MASTER_ID")
  private Member owner;

  @Column(name = "NUMBER_LIMIT")
  private int limit;

  @Column(name = "ROOM_TITLE")
  private String title;

  @Column(name = "ROOM_COMMENT")
  private String comment;

  @Builder
  public Room(Long id, Member owner, int limit, String title, String comment) {
    checkArgument(owner != null, "owner must be provided.");
    checkArgument(StringUtils.isNotBlank(title), "title must be provided.");

    this.id = id;
    this.owner = owner;
    this.limit = limit;
    this.title = title;
    this.comment = comment;
  }
}
