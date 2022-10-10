package com.share.music.playlist.configure;

import com.share.music.playlist.common.JwtAccessDeniedHandler;
import com.share.music.playlist.common.JwtAuthenticationEntryPoint;
import com.share.music.playlist.common.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Spring Security 권한 설정
 */
@EnableWebSecurity //기본적인 web보안을 활성화
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;


    public SecurityConfig(
            JwtTokenProvider tokenProvider,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    //비밀번호 encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable() //토근방식 사용을 위해 csrf설정 비활성화
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers("/api/login").permitAll()
                //.antMatchers("/api/login/join").permitAll()
                .anyRequest().authenticated()

                .and()
                .apply(new JwtSecurityConfig(tokenProvider));

       /* http.httpBasic().disable()
                //httpServletRequest를 사용하는 요청들에 대한 접근제한을 설정
                .authorizeRequests()

                //해당 URL로 요청시 각 url에 대한 접근에 대한 설정
                //.antMatchers("/test").permitAll() //인증없이 접근허용
                .antMatchers("/api/login").authenticated()
                //.antMatchers("api/login").permitAll()

                //권한확인, 각 권한에 따른 URL 경로를 나눌 수 있음
                *//*
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")

                *//*
                //모두 허용
                //.antMatchers("/**").permitAll()
                //.anyRequest().authenticated() //나머지 url에 대해서
                ;



        //로그인 설정
        http.formLogin()
            .loginPage("/api/login")
            .failureUrl("/api/login?result=fail") //로그인 실패시
            .defaultSuccessUrl("/",true) //로그인 성공시
            .usernameParameter("id") //로그인 요청시 id용 파라미터
        ;

        //로그아웃 설정
        http
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/api/logout"))
                .logoutSuccessUrl("/") // 로그아웃 성공시
                .invalidateHttpSession(true);
*/
    }
}
