package com.share.music.playlist.room.controller;

import com.share.music.playlist.room.domain.Room;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RoomDto {

  private final Long id;

  private String name;

  @Builder
  private RoomDto(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public static RoomDto of(Room room) {
    return RoomDto.builder()
      .id(room.getId())
      .name(room.getName())
      .build();
  }
}


