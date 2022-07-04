package com.share.music.playlist.music.service;

import com.share.music.playlist.music.domain.Music;
import com.share.music.playlist.music.repository.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MusicService {

    private final MusicRepository musicRepository;

    @Transactional
    public Page<Music> findAll(Pageable pageable) {
        return musicRepository.findAll(pageable);
    }
}
