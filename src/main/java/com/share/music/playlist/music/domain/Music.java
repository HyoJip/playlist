package com.share.music.playlist.music.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MUSIC")
public class Music {

    @Id
    @GeneratedValue
    @Column(name = "MUSIC_ID")
    private String musicId;

    @Column(name = "MUSIC_NM")
    private String musicNm;

    @Column(name = "MUSIC_ARTIST")
    private String musicArtist;

    @Column(name = "ALBUM_NM")
    private String albumNm;

    @Column(name = "PLAY_TIME")
    private String playTime;

    @Builder
    public Music(String id, String name, String musicArtist, String albumNm, String playTime) {
        this.musicId = id;
        this.musicNm = name;
        this.musicArtist = musicArtist;
        this.albumNm = albumNm;
        this.playTime = playTime;
    }
}
