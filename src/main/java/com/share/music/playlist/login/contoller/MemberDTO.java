package com.share.music.playlist.login.contoller;

import com.share.music.playlist.login.domain.Member;

import java.util.Collections;

/* 회원 가입때 사용*/
public class MemberDTO {
    private String userId;
    private String loginId;
    private String userNm;
    private String nickNm;

    private String phoneNo;
    private String loginPw;

    private String roles;

    /*Entity -> Dto*/
    public MemberDTO(Member member) {
        this.userId = member.getUserId();
        this.loginId = member.getLoginId();
        this.userNm = member.getUserNm();
        this.nickNm = member.getNickNm();
        this.phoneNo = member.getPhoneno();
        this.loginPw = member.getLoginPw();
        //this.roles = member.getRoles();
    }

/*    public static MemberDTO of(Member member){
        return MemberDTO.builder()
                .userId(USERID)
                .loginId(LOGINID)
                .userNm(USERNM)
                .nickNm(NICKNM)
                .phoneno(PHONENO)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }*/

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public String getNickNm() {
        return nickNm;
    }

    public void setNickNm(String nickNm) {
        this.nickNm = nickNm;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneno) {
        this.phoneNo = phoneno;
    }

    public String getLoginPw() {
        return loginPw;
    }

    public void setUserPw(String LoginPw) {
        this.loginPw = loginPw;
    }


    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}


