package com.share.music.playlist.music.service;

import com.share.music.playlist.music.controller.MusicDTO;
import com.share.music.playlist.music.domain.Music;
import com.share.music.playlist.music.repository.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MusicService {

    private final MusicRepository musicRepository;

    public Page<Music> findAll(Pageable pageable) {
        return musicRepository.findAll(pageable);
    }

    public Page<Music> findAllByMusicId(Long id, Pageable pageable) {
        return musicRepository.findAll(pageable);
    }
    public Music findById(Long musicId) {
        return musicRepository.findById(musicId).get();
    }
}
