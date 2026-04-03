package com.example.app.song.service;

import com.example.app.song.domain.Song;
import com.example.app.song.dto.SongRequest;
import com.example.app.song.dto.SongResponse;
import com.example.app.song.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SongResponse> findAll() {
        return songRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SongResponse> findById(Long id) {
        return songRepository.findById(id).map(this::toResponse);
    }

    @Override
    public SongResponse save(SongRequest request) {
        Song song = toEntity(request);
        return toResponse(songRepository.save(song));
    }

    @Override
    public SongResponse update(Long id, SongRequest request) {
        Song existing = songRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Song not found: " + id));
        existing.setTitle(request.getTitle());
        existing.setArtist(request.getArtist());
        existing.setAlbum(request.getAlbum());
        existing.setGenre(request.getGenre());
        existing.setDurationSeconds(request.getDurationSeconds());
        return toResponse(songRepository.save(existing));
    }

    @Override
    public void deleteById(Long id) {
        songRepository.deleteById(id);
    }

    private Song toEntity(SongRequest request) {
        Song song = new Song();
        song.setTitle(request.getTitle());
        song.setArtist(request.getArtist());
        song.setAlbum(request.getAlbum());
        song.setGenre(request.getGenre());
        song.setDurationSeconds(request.getDurationSeconds());
        return song;
    }

    private SongResponse toResponse(Song song) {
        SongResponse response = new SongResponse();
        response.setId(song.getId());
        response.setTitle(song.getTitle());
        response.setArtist(song.getArtist());
        response.setAlbum(song.getAlbum());
        response.setGenre(song.getGenre());
        response.setDurationSeconds(song.getDurationSeconds());
        response.setCreatedAt(song.getCreatedAt());
        response.setUpdatedAt(song.getUpdatedAt());
        return response;
    }
}
