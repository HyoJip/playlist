package com.share.music.playlist.login;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginController {


    @PostMapping("/mainLogin")
    public MemberDTO mainLogin(@RequestBody LoginDTO loginDTO){

        MemberDTO member = new MemberDTO();
        String userId = loginDTO.getUserId();
        String userPw = loginDTO.getUserPw();

        //사용자의 아이디와 비번확인하는 로직필요

        return member;

    }


}
