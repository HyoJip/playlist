package com.share.music.playlist.room.controller;

import com.share.music.playlist.common.dto.ApiResult;
import com.share.music.playlist.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

  private final RoomService roomService;

  @GetMapping
  public ApiResult<Page<RoomDto>> findAllRooms(Pageable pageable) {
    return ApiResult.ok(
      roomService.findAll(pageable).map(RoomDto::of)
    );
  }
}
