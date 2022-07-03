package com.share.music.playlist.room.service;

import com.share.music.playlist.error.NotFoundException;
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

  @Transactional
  public Room find(Long id) {
    return roomRepository.findById(id).orElseThrow(() -> new NotFoundException(Room.class, id));
  }

  @Transactional
  public Page<Room> findAll(Pageable pageable) {
    return roomRepository.findAll(pageable);
  }
}
