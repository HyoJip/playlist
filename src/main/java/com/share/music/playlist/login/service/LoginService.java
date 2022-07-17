package com.share.music.playlist.login.service;

import com.share.music.playlist.error.NotFoundException;
import com.share.music.playlist.login.contoller.MemberDTO;
import com.share.music.playlist.login.domain.Member;
import com.share.music.playlist.login.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final LoginRepository loginRepository;


    /*userId가 DB에 있는지 확인*/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return loginRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다." + username));
    }

    @Transactional
    public Member findByUserId(String id){
        return loginRepository.findById(id).orElseThrow(() -> new NotFoundException(Member.class, id));
    }

    //회원가입
    public Member insertUser(MemberDTO userInfo){

        Member member = new Member();

        member.setUserId(userInfo.getUserId());
        member.setUserNm(userInfo.getUserNm());
        member.setLoginId(userInfo.getLoginId());
        member.setLoginPw(userInfo.getLoginPw()); //암호화 필요
        member.setNickNm(userInfo.getNickNm());
        member.setPhoneno(userInfo.getPhoneNo());

        //DB저장
        return  loginRepository.save(member);

    }

}
