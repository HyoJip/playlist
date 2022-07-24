package com.share.music.playlist.room.controller;

import com.share.music.playlist.room.domain.Room;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Schema(description = "방 생성 요청 DTO")
@Getter
public class RoomCreateDTO {

  @Schema(description = "방이름", example = "test room name")
  @NotBlank
  private final String title;

  @Schema(description = "정원", example = "10")
  @Min(0) @Max(500)
  private final int limit;

  @Schema(description = "방 설명")
  private final String comment;

  @Builder
  @Jacksonized
  private RoomCreateDTO(String title, int limit, String comment) {
    this.title = title;
    this.limit = limit;
    this.comment = comment;
  }

  public Room toRoom(String ownerId) {
    return Room.builder()
      .title(getTitle())
      .ownerId(ownerId)
      .limit(getLimit())
      .comment(getComment())
      .build();
  }
}
