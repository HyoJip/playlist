package com.share.music.playlist.room.repository;

import com.share.music.playlist.room.domain.Room;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RoomRepositoryTest {

  @Autowired
  RoomRepository roomRepository;

  @Test
  @DisplayName("JPA 임시 테스트")
  void jpaTest() {
    Room room = Room.builder()
      .id(1L)
      .name("bbbic")
      .build();

    roomRepository.save(room);
    Room roomInDB = roomRepository.findById(1L).get();
    assertThat(room.getName()).isEqualTo(roomInDB.getName());
  }
}