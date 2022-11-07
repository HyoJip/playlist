package com.share.music.playlist.music.service;

import com.share.music.playlist.error.NotFoundException;
import com.share.music.playlist.music.controller.MusicDTO;
import com.share.music.playlist.music.domain.Music;
import com.share.music.playlist.music.repository.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MusicService {

    private final MusicRepository musicRepository;

    public Page<Music> findAll(Pageable pageable) {
        return musicRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Music findById(String musicId) {
        return musicRepository.findById(musicId)
                .orElseThrow(() -> new NotFoundException(Music.class, musicId));
    }
    @Transactional(readOnly = true)
    public Page<Music> findByMusicNmAndMusicArtist(MusicDTO music, Pageable pageable){
        return musicRepository.findByMusicNmContainingOrMusicArtistContaining(music.getMusicNm(), music.getMusicArtist(), pageable);
    }
}
