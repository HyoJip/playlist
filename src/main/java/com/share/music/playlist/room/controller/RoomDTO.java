package com.share.music.playlist.room.controller;

import com.share.music.playlist.login.contoller.MemberDTO;
import com.share.music.playlist.login.domain.Member;
import com.share.music.playlist.room.domain.Room;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "방 조회 요청 DTO")
@Getter
@Setter
public class RoomDTO {

  @Schema(description = "방 ID", example = "1")
  private final Long id;

  @Schema(description = "방 이름", example = "room name")
  private String title;

  @Schema(description = "방장")
  private MemberDTO member;

  @Schema(description = "카테고리")
  private List<CategoryTagDTO> categories;

  @Schema(description = "방 정원", example = "50")
  private int viewerLimit;

  @Schema(description = "시청인원", example = "21")
  private int viewerCount;

  @Builder
  private RoomDTO(Long id, String title, MemberDTO member, List<CategoryTagDTO> categories, int viewerLimit, int viewerCount) {
    this.id = id;
    this.title = title;
    this.member = member;
    this.categories = categories;
    this.viewerLimit = viewerLimit;
    this.viewerCount = viewerCount;
  }


  public static RoomDTO of(Room room) {
    Member member = Member.builder().userId(room.getOwnerId()).build();
    MemberDTO memberDTO = new MemberDTO(member);

    return RoomDTO.builder()
      .id(room.getId())
      .title(room.getTitle())
      .member(memberDTO)
      .categories(room.getCategoryTags()
        .stream()
        .map(CategoryTagDTO::of)
        .collect(Collectors.toList()))
      .viewerLimit(room.getLimit())
      .viewerCount(room.getEntrances().size())
      .build();
  }

  public static RoomDTO of(Room room, Member member) {
    MemberDTO memberDTO = new MemberDTO(member);

    RoomDTO roomDTO = RoomDTO.of(room);
    roomDTO.setMember(memberDTO);
    return roomDTO;
  }
}


