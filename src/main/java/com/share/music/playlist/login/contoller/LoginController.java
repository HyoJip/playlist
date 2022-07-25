package com.share.music.playlist.login.contoller;

import com.share.music.playlist.common.JwtTokenProvider;
import com.share.music.playlist.login.domain.Member;
import com.share.music.playlist.login.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NamedQuery;
import java.util.Collections;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/login")
public class LoginController {

    private final JwtTokenProvider jwtTokenProvider;
    private final LoginRepository loginRepository;

    /*test용 하드코딩*/
    final String USERID = "jeeyeon27";
    final String LOGINID = "jeeyeon27@gmail.com";
    final String USERNM = "박지연";
    final String NICKNM = "야니";
    final String PHONENO = "010-0000-0000";

    //test 22.07.25

    Member member = Member.builder()
            .userId(USERID)
            .loginId(LOGINID)
            .userNm(USERNM)
            .nickNm(NICKNM)
            .phoneno(PHONENO)
            .roles(Collections.singletonList("ROLE_USER"))
            .build();


    /**
     *로그인
     *
     * @param
     * @return member.getUsername(), member.getRoles()
     */
    @PostMapping("/mainLogin")
   // public ApiResult<MemberDTO> mainLogin(@RequestBody LoginDTO loginDTO){
    public String mainLogin(@RequestBody Map<String, String> user){

        log.info("user id = {}",user.get("id"));
        Member member = loginRepository.findById(user.get("id"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 ID 입니다."));


        //사용자의 아이디와 비번확인하는 로직필요

       /* return ApiResult.ok(

        );*/

        return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());


    }

    /**
     *회원가입
     *
     */
    @PostMapping("/join")
    public String join(){
        log.info("로그인 시도됨");
        loginRepository.save(member);

        return member.toString();
    }


}
