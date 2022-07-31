package com.share.music.playlist.playlist.repository;

import com.share.music.playlist.playlist.domain.PlayList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayListRepository extends JpaRepository<PlayList, Long> {
    Page<PlayList> findAllByRoomId(Long id, Pageable pageable);
}
