package com.share.music.playlist.playlist.service;

import com.share.music.playlist.playlist.domain.PlayList;
import com.share.music.playlist.playlist.repository.PlayListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayListService {
    private PlayListRepository playListRepository;

    @Transactional
    public Page<PlayList> findByRoomId(Long id, Pageable pageable){
        return playListRepository.findAllByRoomId(id, pageable);
    }

}
