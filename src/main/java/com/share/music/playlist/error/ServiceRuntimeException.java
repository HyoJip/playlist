package com.share.music.playlist.error;

import lombok.Getter;

@Getter
public abstract class ServiceRuntimeException extends RuntimeException {

  private final String messageKey;

  private final String detailKey;

  private final Object[] params;

  public ServiceRuntimeException(String messageKey, String detailKey, Object... params) {
    this.messageKey = messageKey;
    this.detailKey = detailKey;
    this.params = params;
  }
}