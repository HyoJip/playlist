package com.share.music.playlist.room.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CHAT_CONTENT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat {

  @Id
  @Column(name = "CHAT_NO")
  private String no;

  @Column(name = "CHAT_CONTENT")
  private String content;

  @Column(name = "CHAT_TIME")
  private LocalDateTime time;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns({
    @JoinColumn(name = "ROOM_ID", referencedColumnName = "ROOM_ID"),
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
  })
  private Entrance room;

}