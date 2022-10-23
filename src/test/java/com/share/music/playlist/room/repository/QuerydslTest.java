package com.share.music.playlist.room.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.share.music.playlist.configure.JpaConfigure;
import com.share.music.playlist.room.domain.QRoom;
import com.share.music.playlist.room.domain.Room;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static com.share.music.playlist.room.domain.QRoom.*;

@DataJpaTest
@Import(JpaConfigure.class)
@ActiveProfiles("test")
class QuerydslTest {

  @Autowired
  JPAQueryFactory query;

  @Test
  @DisplayName("querydsl test")
  void test() {
    List<Room> rooms = query.select(room)
      .from(room)
      .where(room.id.eq(1L))
      .fetch();

    Assertions.assertThat(rooms.isEmpty());
  }
}
