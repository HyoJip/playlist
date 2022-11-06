package com.share.music.playlist.room.controller;

import com.share.music.playlist.common.dto.ApiResult;
import com.share.music.playlist.login.domain.Member;
import com.share.music.playlist.login.service.LoginService;
import com.share.music.playlist.room.domain.Room;
import com.share.music.playlist.room.service.RoomService;
import com.share.music.playlist.util.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

  private final RoomService roomService;
  private final LoginService memberService;

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
    Room room = roomService.find(id);
    Member member = memberService.findByUserId(room.getOwnerId());
    return ApiResult.ok(
      RoomDTO.of(room, member)
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

  @Operation(summary = "방 수정", description = "방 정보를 수정한다.")
  @PutMapping("/{id:\\d+}")
  public ApiResult<RoomDTO> updateRoom(
    @PathVariable @Min(0) @Max(Long.MAX_VALUE) Long id,
    @RequestBody @Validated RoomUpdateRequest updateRequest) {
    // 추후 SpringSecurity 적용 @PreAuthorize("@hoaxSecurityService.isAllowedToDelete(#id, principal)")
    // TODO 본인인지 확인, 만약 본인이 아니면 403 반환

    Room updatedRoom = roomService.update(id, updateRequest.getTitle(), updateRequest.getLimit(), updateRequest.getComment());
    return ApiResult.ok(RoomDTO.of(updatedRoom));
  }
}

/* 뭐가 필요할까...
1. 방 정보 조회
  - 방 이름
  - 정원 수
  - 방장 정보
  - 방 설명
  - 카테고리
  + 현재 시청자수(UI특화)
  + 햔재 재생중인 노래
2. 방 다건 조회
  - 방 이름
  - 방장 정보
  - 방 설명
  - 카테고리
3. 방으로 회원 입장
4. 방 변경
5. 방 삭제
6. 방에서 회원 퇴장
7. 방 노래 신청
 */