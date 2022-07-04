package com.share.music.playlist.room.controller;

import com.share.music.playlist.room.domain.Room;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RoomDTO {

  private final Long id;

  private String name;

  @Builder
  private RoomDTO(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public static RoomDTO of(Room room) {
    return RoomDTO.builder()
      .id(room.getId())
      .name(room.getName())
      .build();
  }
}


