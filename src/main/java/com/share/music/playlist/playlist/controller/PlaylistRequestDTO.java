package com.share.music.playlist.playlist.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlaylistRequestDTO {
    private Long roomId;
    private Long musicId;
    private Long musicSn;

    @Builder
    public PlaylistRequestDTO(Long roomId, Long musicId, Long musicSn){
        this.roomId = roomId;
        this.musicId = musicId;
        this.musicSn = musicSn;
    }
}
