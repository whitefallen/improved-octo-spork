package com.example.app.song.service;

import com.example.app.song.dto.SongRequest;
import com.example.app.song.dto.SongResponse;

import java.util.List;
import java.util.Optional;

public interface SongService {
    List<SongResponse> findAll();
    Optional<SongResponse> findById(Long id);
    SongResponse save(SongRequest request);
    SongResponse update(Long id, SongRequest request);
    void deleteById(Long id);
}
