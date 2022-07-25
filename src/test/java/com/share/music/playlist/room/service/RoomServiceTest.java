package com.share.music.playlist.room.service;

import com.share.music.playlist.error.NotFoundException;
import com.share.music.playlist.login.service.LoginService;
import com.share.music.playlist.room.RoomTestUtil;
import com.share.music.playlist.room.domain.Room;
import com.share.music.playlist.room.repository.RoomRepository;
import com.share.music.playlist.util.MessageUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

  @Autowired
  RoomService roomService;

  @Mock
  LoginService loginService;

  @Mock
  RoomRepository roomRepository;

  @BeforeAll
  static void setup() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("/i18n/messages");
    messageSource.setDefaultEncoding("UTF-8");
    MessageUtils.setMessageSourceAccessor(new MessageSourceAccessor(messageSource));
  }

  @BeforeEach
  void di() {
    roomService = new RoomService(roomRepository, loginService);
  }

  @Test
  @DisplayName("[find] 해당 id의 방이 없을 경우, 예외를 던진다.")
  void find_whenRoomIsNotExists_throwNotFoundException() {
    given(roomRepository.findById(anyLong())).willReturn(Optional.empty());

    assertThatThrownBy(() -> roomService.find(1L), "DB에 해당 ID 방이 없으면 예외 던짐")
      .hasMessage("Could not found 'Room' with query values (1)")
      .isInstanceOf(NotFoundException.class);
  }

  @Test
  @DisplayName("[create] 유효한 방 엔티티일 경우, 생성 후 ID를 반환한다.")
  void find_whenRoomIsValid_returnCreatedRoomId() {
    Room room = RoomTestUtil.createRooms(1).get(0);
    given(roomRepository.save(any(Room.class))).willReturn(room);

    Long roomId = roomService.create(room);

    assertThat(roomId).isEqualTo(room.getId());
  }
}