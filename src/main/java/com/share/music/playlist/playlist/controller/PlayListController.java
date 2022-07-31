package com.share.music.playlist.playlist.controller;

import com.share.music.playlist.common.dto.ApiResult;
import com.share.music.playlist.playlist.service.PlayListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/playlist")
@RequiredArgsConstructor
public class PlayListController {

    private PlayListService playListService;

    @GetMapping("/{id:\\d}")
    public ApiResult<Page<PlayListResponseDTO>> findPlaylist(@PathVariable long id, Pageable pageable){
        return ApiResult.ok(playListService.findByRoomId(id, pageable).map(PlayListResponseDTO::of));
        //주석
        //테스트테스트테스테
        //테스형
    }

}
