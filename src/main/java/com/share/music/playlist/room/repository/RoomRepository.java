package com.share.music.playlist.room.repository;

import com.share.music.playlist.room.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
