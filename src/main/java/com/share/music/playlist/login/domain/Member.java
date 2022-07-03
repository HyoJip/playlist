package com.share.music.playlist.login.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Member {

    @Id
    @GeneratedValue
    private String userId;

    private String loginId;
    private String userNm;
    private String nickNm;
    private String phoneno;
    private String userPw;
}
