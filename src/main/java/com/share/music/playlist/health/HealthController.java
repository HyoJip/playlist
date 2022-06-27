package com.share.music.playlist.health;

import com.share.music.playlist.common.dto.ApiResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
public class HealthController {

  @GetMapping("/check")
  public ApiResult<String> healthCheck() {
    return ApiResult.ok("success");
  }
}
