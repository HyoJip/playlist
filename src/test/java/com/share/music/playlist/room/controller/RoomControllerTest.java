package com.share.music.playlist.room.controller;

import com.share.music.playlist.room.RoomTestUtil;
import com.share.music.playlist.room.domain.Room;
import com.share.music.playlist.room.service.RoomService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(
  controllers = RoomController.class,
  excludeAutoConfiguration = SecurityAutoConfiguration.class,
  excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurerAdapter.class))
class RoomControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  RoomService roomService;

  @Test
  @DisplayName("[findAll] ApiResult<Page<RoomDTO>>> 형태로 역직렬화한다.")
  void findAll_whenRoomIsOne_returnOne() throws Exception {
    given(roomService.findAll(any(Pageable.class))).willReturn(new PageImpl<>(RoomTestUtil.createRooms(1)));

    mockMvc.perform(get("/api/rooms").contentType(MediaType.APPLICATION_JSON))
//      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.success").value(true))
      .andExpect(jsonPath("$.response.totalElements").value(1))
      .andExpect(jsonPath("$.response.content[0].id").value(1L))
      .andExpect(jsonPath("$.response.content[0].title").value("test1"));
  }

  @Test
  @DisplayName("[find] id 패턴과 일치하면 url에서 id를 추출한다.")
  void find_whenUrlMatchesIdPattern_extractId() throws Exception {
    Room room = RoomTestUtil.createRooms(1).get(0);
    given(roomService.find(1L)).willReturn(room);

    mockMvc.perform(get("/api/rooms/1").contentType(MediaType.APPLICATION_JSON))
//      .andDo(print())
      .andExpect(jsonPath("$.success").value(true))
      .andExpect(jsonPath("$.response.id").value(room.getId()))
      .andExpect(jsonPath("$.response.title").value(room.getTitle()));
  }

  @Test
  @DisplayName("[find] id 패턴과 일치하지 않으면 404 에러를 반환한다.")
  void find_whenUrlDoesNotMatchesIdPattern_returnNotFoundStatus() throws Exception {
    mockMvc.perform(get("/api/rooms/a").contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("[createRoom] 방 이름이 빈 값일 경우, 400 에러를 반환한다.")
//  @WithMockUser
  void createRoom_whenRoomTitleIsBlank_throwIllegalArgumentException() throws Exception {
    given(roomService.create(any(Room.class)))
      .willReturn(1L);

    String json = """
        {
          "title": "",
          "limit":  5,
          "comment": "테스트 방입니다."
        }
      """;
    mockMvc.perform(post("/api/rooms")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
      )
//      .andDo(print())
      .andExpect(jsonPath("$.success").value(false))
      .andExpect(jsonPath("$.error").isNotEmpty())
      .andExpect(status().is4xxClientError());
  }

  @Test
  @DisplayName("[createRoom] 방 정원이 음수일 경우, 400 에러를 반환한다.")
  void createRoom_whenRoomLimitIsNegative_throwIllegalArgumentException() throws Exception {
    given(roomService.create(any(Room.class)))
      .willReturn(1L);

    String json = """
        {
          "title": "test_title",
          "limit":  -1,
          "comment": "테스트 방입니다."
        }
      """;
    mockMvc.perform(post("/api/rooms")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
      )
//      .andDo(print())
      .andExpect(jsonPath("$.success").value(false))
      .andExpect(jsonPath("$.error").isNotEmpty())
      .andExpect(status().is4xxClientError());
  }

  @Test
  @DisplayName("[createRoom] 방 정원이 500초과할 경우, 400 에러를 반환한다.")
  void createRoom_whenRoomLimitMoreThan500_throwIllegalArgumentException() throws Exception {
    given(roomService.create(any(Room.class)))
      .willReturn(1L);

    String json = """
        {
          "title": "test_title",
          "limit":  501,
          "comment": "테스트 방입니다."
        }
      """;
    mockMvc.perform(post("/api/rooms")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
      )
//      .andDo(print())
      .andExpect(jsonPath("$.success").value(false))
      .andExpect(jsonPath("$.error").isNotEmpty())
      .andExpect(status().is4xxClientError());
  }

  @Test
  @DisplayName("[createRoom] 유효한 방 생성 요청일 경우, 생성된 방 ID를 반환한다.")
  void createRoom_whenRequestIsValid_returnCreatedRoomId() throws Exception {
    long createdRoomId = 1L;
    given(roomService.create(any(Room.class)))
      .willReturn(createdRoomId);

    String json = """
        {
          "title": "test_title",
          "limit":  250,
          "comment": "테스트 방입니다."
        }
      """;
    mockMvc.perform(post("/api/rooms")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
      )
//      .andDo(print())
      .andExpect(jsonPath("$.success").value(true))
      .andExpect(jsonPath("$.error").isEmpty())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.response").value(createdRoomId));
  }
}