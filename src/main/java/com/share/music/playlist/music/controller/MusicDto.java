package com.share.music.playlist.music.controller;

import com.share.music.playlist.music.domain.Music;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MusicDto {

    private final Long id;
    private String name;

    @Builder
    private MusicDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static MusicDto of(Music music) {
        return MusicDto.builder()
                .id(music.getId())
                .name(music.getName())
                .build();
    }
}
