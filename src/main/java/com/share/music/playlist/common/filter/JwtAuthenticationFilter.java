package com.share.music.playlist.common.filter;

import com.share.music.playlist.common.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * JWT 인증 필터
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 헤더에서 JWT 받기
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        // 유효한 토큰인지 확인
        if (token != null && jwtTokenProvider.validateToken(token)) {

            // 토큰이 유효하면 토큰으로부터 유저 정보를 받기
            Authentication authentication = jwtTokenProvider.getAuthentication(token);

            // SecurityContext에 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        else{
            if (StringUtils.isEmpty(token)) {
                request.setAttribute("unauthorization", "401 인증키 없음");
            }

            if (JwtTokenProvider.validateToken(token)) {
                request.setAttribute("unauthorization", "401-001 인증키 만료");
            }
        }
        chain.doFilter(request, response);
    }

}
