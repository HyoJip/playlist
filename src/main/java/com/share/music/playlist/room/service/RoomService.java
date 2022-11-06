package com.share.music.playlist.room.service;

import com.share.music.playlist.error.NotFoundException;
import com.share.music.playlist.login.service.LoginService;
import com.share.music.playlist.room.domain.Room;
import com.share.music.playlist.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomService {

  private final RoomRepository roomRepository;
  private final LoginService loginService;

  @Transactional(readOnly = true)
  public Room find(Long id) {
    return roomRepository.findById(id)
      .orElseThrow(() -> new NotFoundException(Room.class, id));
  }

  @Transactional(readOnly = true)
  public Page<Room> findAll(Pageable pageable) {
    return roomRepository.findAll(pageable);
  }

  @Transactional
  public Long create(Room room) {
    Room roomInDB = roomRepository.save(room);
    return roomInDB.getId();
  }

  @Transactional
  public Room update(Long id, String title, Integer limit, String comment) {
    Room room = roomRepository.findById(id)
      .orElseThrow(() -> new NotFoundException(Room.class, id));

    if (title != null) {
      room.changeTitle(title);
    }
    if (limit != null) {
      room.changeLimit(limit);
    }
    if (comment != null) {
      room.changeComment(comment);
    }
    return room;
  }
}
