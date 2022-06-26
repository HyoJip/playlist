package com.share.music.playlist.common.dto;

import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResult<T> {

  private final boolean success;
  private final T response;
  private final ApiError error;

  private ApiResult(boolean success, T response, ApiError error) {
    this.success = success;
    this.response = response;
    this.error = error;
  }

  public static <T> ApiResult<T> ok(T response) {
    return new ApiResult<>(true, response, null);
  }

  public static ApiResult<?> error(Throwable throwable, HttpStatus status) {
    return new ApiResult<>(false, null, new ApiError(throwable, status));
  }

  public static ApiResult<?> error(String errorMessage, HttpStatus status) {
    return new ApiResult<>(false, null, new ApiError(errorMessage, status));
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
  }
}
