package com.share.music.playlist.common;

import com.share.music.playlist.common.dto.ApiResult;
import com.share.music.playlist.error.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GeneralExceptionAdvice {

  private ResponseEntity<ApiResult<?>> newResponse(Throwable throwable, HttpStatus status) {
    return new ResponseEntity<>(ApiResult.error(throwable, status), null, status);

  }

  @ExceptionHandler({NoHandlerFoundException.class, NotFoundException.class})
  public ResponseEntity<ApiResult<?>> handleNotFoundException(Exception e) {
    return newResponse(e, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({
    IllegalStateException.class, IllegalArgumentException.class,
    TypeMismatchException.class, HttpMessageNotReadableException.class,
    MissingServletRequestParameterException.class, MultipartException.class,
    MethodArgumentNotValidException.class
  })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ApiResult<?>> handleBadRequestException(Exception e) {
    log.debug("Bad request exception occurred: {}", e.getMessage(), e);
    return newResponse(e, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMediaTypeException.class)
  public ResponseEntity<ApiResult<?>> handleHttpMediaTypeException(Exception e) {
    return newResponse(e, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ApiResult<?>> handleMethodNotAllowedException(Exception e) {
    return newResponse(e, HttpStatus.METHOD_NOT_ALLOWED);
  }

  @ExceptionHandler({Exception.class, RuntimeException.class})
  public ResponseEntity<ApiResult<?>> handleException(Exception e) {
    log.error("Unexpected exception occurred: {}", e.getMessage(), e);
    return newResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
