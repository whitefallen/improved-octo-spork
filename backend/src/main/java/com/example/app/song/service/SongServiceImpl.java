package com.example.app.song.service;

import com.example.app.song.domain.Song;
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
    public List<Song> findAll() {
        return songRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Song> findById(Long id) {
        return songRepository.findById(id);
    }

    @Override
    public Song save(Song song) {
        return songRepository.save(song);
    }

    @Override
    public Song update(Long id, Song song) {
        Song existing = songRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Song not found: " + id));
        existing.setTitle(song.getTitle());
        existing.setArtist(song.getArtist());
        existing.setAlbum(song.getAlbum());
        existing.setGenre(song.getGenre());
        existing.setDurationSeconds(song.getDurationSeconds());
        return songRepository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        songRepository.deleteById(id);
    }
}
