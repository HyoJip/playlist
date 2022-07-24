package com.share.music.playlist.room;

import com.share.music.playlist.room.domain.Room;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class RoomTestUtil {

  public static List<Room> createRooms(int ea) {
    return LongStream.rangeClosed(1, ea)
      .mapToObj(idx -> Room.builder()
        .id(idx)
        .ownerId("1")
        .title("test" + idx)
        .build())
      .collect(Collectors.toList());
  }
}
