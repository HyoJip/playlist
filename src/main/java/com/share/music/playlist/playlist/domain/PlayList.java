package com.share.music.playlist.playlist.domain;

import com.share.music.playlist.music.domain.Music;
import com.share.music.playlist.room.domain.Room;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
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

    @Id
    private int musicSn;

    @Builder
    public PlayList(Room room, Music music){
        this.music = music;
        this.room = room;
        this.musicSn = musicSn;
    }

}
