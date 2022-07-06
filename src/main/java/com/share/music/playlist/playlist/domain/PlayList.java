package com.share.music.playlist.playlist.domain;

import com.share.music.playlist.music.domain.Music;
import com.share.music.playlist.room.domain.Room;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(PlayListId.class)
public class PlayList implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="roomId")
    private Room room;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="musicId")
    private Music music;

}
