package com.share.music.playlist.room.repository;

import com.share.music.playlist.room.domain.Room;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class RoomRepositoryTest {

  @Autowired
  RoomRepository roomRepository;

  @Test
  @DisplayName("방을 생성할때 자동으로 ID를 만든다.")
  void jpaTest() {
    Room room = Room.builder()
      .title("bbbic")
      .ownerId("1")
      .build();

    Room roomInDB = roomRepository.save(room);
    assertThat(roomInDB.getId()).isNotNull();
  }
}