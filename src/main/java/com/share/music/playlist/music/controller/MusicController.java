package com.share.music.playlist.music.controller;

import com.share.music.playlist.common.dto.ApiResult;
import com.share.music.playlist.music.domain.Music;
import com.share.music.playlist.music.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/music")
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;

    @GetMapping("/songs")
    public ApiResult<Page<MusicDTO>> findAllMusic(Pageable pageable) {
        return ApiResult.ok(
                musicService.findAll(pageable).map(MusicDTO::of)
        );
    }
    @GetMapping("/{id:\\d+}")
    public ApiResult<MusicDTO> findMusic(Long musicId) {
        return ApiResult.ok(
                MusicDTO.of(musicService.findById(musicId))
        );
    }
}
