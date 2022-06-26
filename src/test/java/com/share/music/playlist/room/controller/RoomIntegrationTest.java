package com.share.music.playlist.room.controller;

import com.share.music.playlist.TestPage;
import com.share.music.playlist.common.dto.ApiResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class RoomIntegrationTest {

  static final String ROOM_URL = "/api/rooms";

  @Autowired
  TestRestTemplate testRestTemplate;

  @Test
  @DisplayName("[방 조회] DB에 방목록이 없을 때, 총 건수는 0이다.")
  void findAllRooms_whenRoomIsEmptyInDB_totalElementsIsZero() {
    ResponseEntity<ApiResult<TestPage<RoomDto>>> responseEntity = getRooms(new ParameterizedTypeReference<>(){});

    HttpStatus httpStatus = responseEntity.getStatusCode();
    ApiResult<TestPage<RoomDto>> apiResult = responseEntity.getBody();

    assertThat(httpStatus).isEqualTo(HttpStatus.OK);
    assertThat(apiResult.isSuccess()).isTrue();
    assertThat(apiResult.getResponse().getTotalElements()).isZero();
  }

  private <T> ResponseEntity<T> getRooms(ParameterizedTypeReference<T> responseType) {
    return testRestTemplate.exchange(ROOM_URL, HttpMethod.GET, null, responseType);
  }
}