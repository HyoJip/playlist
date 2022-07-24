package com.share.music.playlist.music.repository;

import com.share.music.playlist.music.controller.MusicDTO;
import com.share.music.playlist.music.domain.Music;
import com.share.music.playlist.room.domain.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {
    public Page<Music> findAll(Pageable pageable);
    //public Page<Music> findAllByMusicId(Long id, Pageable pageable);
    //public Music findById(Long musicId);
}
