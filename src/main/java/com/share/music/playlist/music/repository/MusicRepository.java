package com.share.music.playlist.music.repository;

import com.share.music.playlist.music.domain.Music;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {
}
