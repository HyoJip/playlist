package com.share.music.playlist.music.repository;

import com.share.music.playlist.room.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Room, Long> {
}
