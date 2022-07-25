package com.share.music.playlist.playlist.controller;

import com.share.music.playlist.music.domain.Music;
import com.share.music.playlist.playlist.domain.PlayList;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlayListResponseDTO {
    private Long playlistId;
    private Music music;
    private int musicSn;

    @Builder
    private PlayListResponseDTO(Long playlistId, Music music, int musicSn){
        this.playlistId = playlistId;
        this.music = music;
        this.musicSn = musicSn;
    }

    public static PlayListResponseDTO of(PlayList list){
        return PlayListResponseDTO.builder()
                .playlistId(list.getRoom().getId())
                .music(list.getMusic())
                .musicSn(list.getMusicSn())
                .build();
    }
}
