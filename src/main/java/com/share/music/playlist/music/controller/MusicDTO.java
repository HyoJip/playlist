package com.share.music.playlist.music.controller;

import com.share.music.playlist.music.domain.Music;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MusicDTO {

    private final Long id;
    private String name;

    @Builder
    private MusicDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static MusicDTO of(Music music) {
        return MusicDTO.builder()
                .id(music.getId())
                .name(music.getName())
                .build();
    }
}
