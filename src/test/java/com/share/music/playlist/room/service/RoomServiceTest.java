package com.share.music.playlist.room.service;

import com.share.music.playlist.error.NotFoundException;
import com.share.music.playlist.room.repository.RoomRepository;
import com.share.music.playlist.util.MessageUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RoomServiceTest.TestConfig.class)
class RoomServiceTest {

  @Autowired
  RoomService roomService;

  @MockBean
  RoomRepository roomRepository;

  @BeforeAll
  static void setup() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("/i18n/messages");
    messageSource.setDefaultEncoding("UTF-8");
    MessageUtils.setMessageSourceAccessor(new MessageSourceAccessor(messageSource));
  }

  @Test
  @DisplayName("[find] 해당 id의 방이 없을경우, 예외를 던진다.")
  void find_whenRoomIsNotExists_throwNotFoundException() {
    given(roomRepository.findById(anyLong())).willReturn(Optional.empty());

    Assertions.assertThatThrownBy(() -> roomService.find(1L), "DB에 해당 ID 방이 없으면 예외 던짐")
      .hasMessage("Could not found 'Room' with query values (1)")
      .isInstanceOf(NotFoundException.class);
  }

  static class TestConfig {

    @Bean
    public RoomService roomService(RoomRepository roomRepository) {
      return new RoomService(roomRepository);
    }
  }
}