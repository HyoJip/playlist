package com.share.music.playlist.common;

import com.share.music.playlist.login.contoller.MemberDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtManager {

    private final String securityKey = "hello world"; // TODO 추후 해당 정보는 따로 분리할 것
    private final Long expiredTime = 1000 * 60L * 60L * 3L; // 유효시간 3시간

    /**
     * Member 정보를 담은 JWT 토큰을 생성한다.
     *
     * @param member Member 정보를 담은 객체
     * @return String JWT 토큰
     */
    public String generateJwtToken(MemberDTO member) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(member.getUserNm()) // 보통 username
                .setHeader(createHeader())
                .setClaims(createClaims(member)) // 클레임, 토큰에 포함될 정보
                .setExpiration(new Date(now.getTime() + expiredTime)) // 만료일
                .signWith(SignatureAlgorithm.HS256, securityKey)
                .compact();
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256"); // 해시 256 사용하여 암호화
        header.put("regDate", System.currentTimeMillis());
        return header;
    }

    /**
     * 클레임(Claim)을 생성한다.
     *
     * @param member 토큰을 생성하기 위한 계정 정보를 담은 객체
     * @return Map<String, Object> 클레임(Claim)
     */
    private Map<String, Object> createClaims(MemberDTO member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", member.getUserNm()); // username
        claims.put("roles", member.getRoles()); // 인가정보
        return claims;
    }

}
