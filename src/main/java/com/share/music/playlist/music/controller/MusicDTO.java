package com.share.music.playlist.music.controller;

import com.share.music.playlist.music.domain.Music;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MusicDTO {

    private String musicId;
    private String musicNm;
    private String musicArtist;
    private String albumNm;
    private String playTime;

    public static MusicDTO of(Music music) {
        return MusicDTO.builder()
                .musicId(music.getMusicId())
                .musicNm(music.getMusicNm())
                .musicArtist(music.getMusicArtist())
                .albumNm(music.getAlbumNm())
                .playTime(music.getPlayTime())
                .build();
    }
}
