package com.share.music.playlist.login.contoller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


/*토큰의 값을 헤더에서 뽑거나 헤더에서 삽입할때*/
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class TokenDto {
    private String grantType;
    private String accessToken;
    private Long tokenExpiresIn;
}
