package com.share.music.playlist.room.controller;

import com.share.music.playlist.room.domain.Room;
import com.share.music.playlist.room.service.RoomService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(RoomController.class)
class RoomControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  RoomService roomService;

  @Test
  @DisplayName("[방조회] ApiResult<Page<RoomDTO>>> 형태로 역직렬화한다.")
  void findAll_whenRoomIsOne_returnOne() throws Exception {
    given(roomService.findAll(any(Pageable.class))).willReturn(new PageImpl<>(createRooms(1)));

    mockMvc.perform(get("/api/rooms").contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.success").value(true))
      .andExpect(jsonPath("$.response.totalElements").value(1))
      .andExpect(jsonPath("$.response.content[0].id").value(1L))
      .andExpect(jsonPath("$.response.content[0].name").value("test1"));
  }

  private List<Room> createRooms(int ea) {
    return IntStream.rangeClosed(1, ea)
      .mapToObj(idx -> Room.builder()
        .id((long) idx)
        .name("test" + idx)
        .build())
      .collect(Collectors.toList());
  }
}