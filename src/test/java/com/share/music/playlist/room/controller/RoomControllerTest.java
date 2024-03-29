package com.share.music.playlist.room.controller;

import com.share.music.playlist.login.domain.Member;
import com.share.music.playlist.login.service.LoginService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

  @MockBean
  LoginService memberService;

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
    given(memberService.findByUserId(room.getOwnerId())).willReturn(new Member());

    mockMvc.perform(get("/api/rooms/1").contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(jsonPath("$.success").value(true))
      .andExpect(jsonPath("$.response.id").value(room.getId()));
  }

  @Test
  @DisplayName("[find] 유효한 요청은, 방+방장 정보를 반환한다.")
  void find_whenRequestIsValid_returnRoomDto() throws Exception {
    Room room = RoomTestUtil.createRooms(1).get(0);
    Member owner = Member.builder()
      .userId("Mr.hong")
      .userNm("홍길동")
      .nickNm("지존도적")
      .build();
    given(roomService.find(1L)).willReturn(room);
    given(memberService.findByUserId(room.getOwnerId())).willReturn(owner);

    mockMvc.perform(get("/api/rooms/1").contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(jsonPath("$.success").value(true))
      .andExpect(jsonPath("$.response.title").value(room.getTitle()))
      .andExpect(jsonPath("$.response.member.userId").value(owner.getUserId()))
      .andExpect(jsonPath("$.response.member.userNm").value(owner.getUserNm()))
      .andExpect(jsonPath("$.response.member.nickNm").value(owner.getNickNm()));
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

    String json = "";
//        """
//        {
//          "title": "",
//          "limit":  5,
//          "comment": "테스트 방입니다."
//        }
//      """;
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

    String json = "";
//        """
//        {
//          "title": "test_title",
//          "limit":  -1,
//          "comment": "테스트 방입니다."
//        }
//      """;
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

    String json = "";
//        """
//        {
//          "title": "test_title",
//          "limit":  501,
//          "comment": "테스트 방입니다."
//        }
//      """;
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

    given(roomService.create(any(Room.class))).willReturn(1L);

    String json = "";
//        """
//        {
//          "title": "test_title",
//          "limit":  250,
//          "comment": "테스트 방입니다."
//        }
//      """;
    mockMvc.perform(post("/api/rooms")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
      )
      .andDo(print())
      .andExpect(jsonPath("$.success").value(true))
      .andExpect(jsonPath("$.error").isEmpty())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.response").value(1L));
  }

  @Test
  @DisplayName("[updateRoom] 유효한 방 변경 요청일 경우, 변경된 방 정보를 반환한다.")
  void updateRoom_whenRequestIsValid_returnUpdatedRoom() throws Exception {
    Room updatedRoom = Room.builder()
      .id(1L)
      .ownerId("1")
      .title("updated_title")
      .limit(50)
      .comment("수정된 테스트 방입니다.")
      .build();
    given(roomService.update(any(Long.class), any(String.class), any(int.class), any(String.class)))
      .willReturn(updatedRoom);

    String json = "";
//        """
//        {
//          "title": "updated_title",
//          "limit":  50,
//          "comment": "수정된 테스트 방입니다."
//        }
//      """;
    mockMvc.perform(put("/api/rooms/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
      )
      .andDo(print())
      .andExpect(jsonPath("$.success").value(true))
      .andExpect(jsonPath("$.error").isEmpty())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.response.title").value("updated_title"));
  }

  @Test
  @DisplayName("[updateRoom] url에 id가 없는 경우, 400을 반환한다.")
  void updateRoom_whenIdIsNotProvided_throwIllegalArgumentException() throws Exception {

    String json = "";
//        """
//        {
//          "title": "updated_title",
//          "limit":  50,
//          "comment": "수정된 테스트 방입니다."
//        }
//      """;
    mockMvc.perform(put("/api/rooms/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
      )
      .andDo(print())
      .andExpect(jsonPath("$.error").isNotEmpty())
      .andExpect(status().is4xxClientError());
  }
}