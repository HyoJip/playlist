package com.share.music.playlist.configure;

import com.share.music.playlist.common.JwtTokenProvider;
import com.share.music.playlist.common.filter.JwtAuthenticationFilter;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/** JwtTokenProvider, JwtAuthenticationFilter를 SecurityConfig에 적용
 * */
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private JwtTokenProvider tokenProvider;

    public JwtSecurityConfig(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(
                new JwtAuthenticationFilter(tokenProvider),
                UsernamePasswordAuthenticationFilter.class
        );
    }
}
