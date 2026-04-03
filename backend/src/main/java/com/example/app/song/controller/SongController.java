package com.example.app.song.controller;

import com.example.app.song.domain.Song;
import com.example.app.song.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @GetMapping
    public List<Song> getAll() {
        return songService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> getById(@PathVariable Long id) {
        return songService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Song create(@RequestBody Song song) {
        return songService.save(song);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Song> update(@PathVariable Long id, @RequestBody Song song) {
        try {
            return ResponseEntity.ok(songService.update(id, song));
        } catch (java.util.NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        songService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
