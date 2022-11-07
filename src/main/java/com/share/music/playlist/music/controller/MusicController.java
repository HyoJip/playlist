package com.share.music.playlist.music.controller;

import com.share.music.playlist.common.dto.ApiResult;
import com.share.music.playlist.login.domain.Member;
import com.share.music.playlist.music.domain.Music;
import com.share.music.playlist.music.service.MusicService;
import com.share.music.playlist.room.controller.RoomDTO;
import com.share.music.playlist.room.domain.Room;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("api/music")
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;

    @GetMapping("/songs")
    public ApiResult<Page<MusicDTO>> findAllMusic(Pageable pageable) {
        return ApiResult.ok(
                musicService.findAll(pageable).map(MusicDTO::of)
        );
    }
    /*
    @GetMapping("/songs")
    public ApiResult<Page<MusicDTO>> findAllMusic(@RequestParam String musicId, Pageable pageable) {
        return ApiResult.ok(
                musicService.findAll(pageable).map(MusicDTO::of)
        );
    }*/

    @GetMapping("/{musicId}")
    public ApiResult<MusicDTO> findMusic(
            @Parameter(description = "조회할 음악 ID", required = true)
            @PathVariable @Min(0) @Max(Long.MAX_VALUE) String musicId) {
        return ApiResult.ok(
                MusicDTO.of(musicService.findById(musicId))
        );
    }
    @PostMapping("/song")
    public ApiResult<Page<MusicDTO>> findByMusicNmAndMusicArtist(@RequestBody MusicDTO music,
                                                                        Pageable pageable) {
        return ApiResult.ok(
                musicService.findByMusicNmAndMusicArtist(music, pageable).map(MusicDTO::of)
        );
    }

}
