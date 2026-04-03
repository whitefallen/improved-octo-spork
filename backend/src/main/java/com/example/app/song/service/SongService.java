package com.example.app.song.service;

import com.example.app.song.domain.Song;
import java.util.List;
import java.util.Optional;

public interface SongService {
    List<Song> findAll();
    Optional<Song> findById(Long id);
    Song save(Song song);
    Song update(Long id, Song song);
    void deleteById(Long id);
}
