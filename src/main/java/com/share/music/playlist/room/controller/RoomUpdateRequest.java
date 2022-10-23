package com.share.music.playlist.room.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RoomUpdateRequest {

  private Long id;

  private String title;

  private Integer limit;

  private String comment;
}
