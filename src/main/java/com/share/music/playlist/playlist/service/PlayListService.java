package com.share.music.playlist.playlist.service;

import com.share.music.playlist.playlist.controller.PlayListDTO;
import com.share.music.playlist.playlist.repository.PlayListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayListService {

    private PlayListRepository playListRepository;

    public PlayListDTO findByRoomId(PlayListDTO dto){


        return null;
    }

}
