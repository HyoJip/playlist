package com.share.music.playlist.playlist.domain;

import com.share.music.playlist.music.domain.Music;
import com.share.music.playlist.room.domain.Room;

import javax.persistence.*;

@Entity
public class PlayList {
    @Id
    private Long roomId;
    @Id
    private Long musicId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ROOM_ID")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MUSIC_ID")
    private Music music;

}
