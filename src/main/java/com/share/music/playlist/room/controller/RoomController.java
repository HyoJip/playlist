package com.share.music.playlist.room.controller;

import com.share.music.playlist.common.dto.ApiResult;
import com.share.music.playlist.login.domain.Member;
import com.share.music.playlist.room.domain.Room;
import com.share.music.playlist.room.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

  private final RoomService roomService;

  @Operation(summary = "단건 방 조회", description = "방 ID를 가지고 해당하는 방 정보를 조회한다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "조회 성공"),
    @ApiResponse(responseCode = "400", description = "유효하지 않은 방 ID", content = @Content),
    @ApiResponse(responseCode = "404", description = "해당하는 방 정보 없음", content = @Content)
  })
  @GetMapping("/{id:\\d+}")
  public ApiResult<RoomDTO> findRoom(
    @Parameter(description = "조회할 방 ID", required = true)
    @PathVariable @Min(0) @Max(Long.MAX_VALUE) Long id) {
    return ApiResult.ok(
      RoomDTO.of(roomService.find(id))
    );
  }

  @Operation(summary = "다건 방 조회", description = "조회조건에 맞는 방 정보를 페이징해 조회한다.")
  @GetMapping
  public ApiResult<Page<RoomDTO>> findAllRooms(@ParameterObject Pageable pageable) {
    return ApiResult.ok(
      roomService.findAll(pageable).map(RoomDTO::of)
    );
  }

  @Operation(summary = "방 생성", description = "로그인한 회원의 방을 생성한다.")
  @PostMapping
  public ApiResult<Long> createRoom(@RequestBody @Validated RoomCreateDTO roomDTO) {
    // 추후 세션에서 회원 GET
    Member loginUser = Member.builder()
      .userId("1")
      .userNm("tester")
      .loginId("loginId")
      .nickNm("nickName")
      .build();

    Room room = roomDTO.toRoom(loginUser.getUserId());
    return ApiResult.ok(roomService.create(room));
  }
}
