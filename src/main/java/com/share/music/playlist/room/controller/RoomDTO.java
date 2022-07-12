package com.share.music.playlist.room.controller;

import com.share.music.playlist.room.domain.Room;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RoomDTO {

  private final Long id;

  private String title;

  @Builder
  private RoomDTO(Long id, String title) {
    this.id = id;
    this.title = title;
  }

  public static RoomDTO of(Room room) {
    return RoomDTO.builder()
      .id(room.getId())
      .title(room.getTitle())
      .build();
  }
}


