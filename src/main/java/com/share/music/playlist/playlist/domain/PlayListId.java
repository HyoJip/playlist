package com.share.music.playlist.playlist.domain;

import com.share.music.playlist.music.domain.Music;
import com.share.music.playlist.room.domain.Room;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
public class PlayListId implements Serializable {
    private Room room;
    private Music music;
    private int musicSn;
}
