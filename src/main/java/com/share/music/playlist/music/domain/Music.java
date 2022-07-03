package com.share.music.playlist.music.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Music {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @Builder
    public Music(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
