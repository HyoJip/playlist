package com.share.music.playlist.login.repository;

import com.share.music.playlist.login.contoller.MemberDTO;
import com.share.music.playlist.login.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<Member, String> {

}
