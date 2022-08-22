package com.share.music.playlist.room.controller;

import com.share.music.playlist.room.domain.CategoryTag;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryTagDTO {

  private String name;

  @Builder
  private CategoryTagDTO(String name) {
    this.name = name;
  }

  public static CategoryTagDTO of(CategoryTag categoryTag) {
    return CategoryTagDTO.builder()
      .name("")
      .build();
  }
}
