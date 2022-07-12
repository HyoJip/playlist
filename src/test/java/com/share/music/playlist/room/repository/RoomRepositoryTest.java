package com.share.music.playlist.room.repository;

import com.share.music.playlist.login.domain.Member;
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
  @DisplayName("JPA 임시 테스트")
  void jpaTest() {
    Room room = Room.builder()
      .id(1L)
      .title("bbbic")
      .owner(new Member())
      .build();

    roomRepository.save(room);
    Room roomInDB = roomRepository.findById(1L).get();
    assertThat(room.getTitle()).isEqualTo(roomInDB.getTitle());
  }
}