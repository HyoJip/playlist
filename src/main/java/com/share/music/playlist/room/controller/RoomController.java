package com.share.music.playlist.room.controller;

import com.share.music.playlist.common.dto.ApiResult;
import com.share.music.playlist.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

  private final RoomService roomService;

  @GetMapping("/{id:\\d+}")
  public ApiResult<RoomDto> findRoom(@PathVariable Long id) {
    return ApiResult.ok(
      RoomDto.of(roomService.find(id))
    );
  }
  @GetMapping
  public ApiResult<Page<RoomDto>> findAllRooms(Pageable pageable) {
    return ApiResult.ok(
      roomService.findAll(pageable).map(RoomDto::of)
    );
  }
}
