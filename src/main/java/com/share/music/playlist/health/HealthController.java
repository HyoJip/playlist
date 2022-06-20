package com.share.music.playlist.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/healthcheck")
public class HealthController {

  @GetMapping
  public String healthCheck() {
    return "success";
  }
}
