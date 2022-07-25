package com.share.music.playlist.room.controller;

import com.share.music.playlist.room.domain.Room;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "방 조회 요청 DTO")
@Getter
public class RoomDTO {

  @Schema(description = "방 ID", example = "1")
  private final Long id;

  @Schema(description = "방 이름", example = "room name")
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


