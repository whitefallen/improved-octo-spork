package com.example.app.song.controller;

import com.example.app.song.dto.SongRequest;
import com.example.app.song.dto.SongResponse;
import com.example.app.song.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @GetMapping
    public List<SongResponse> getAll() {
        return songService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongResponse> getById(@PathVariable Long id) {
        return songService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public SongResponse create(@Valid @RequestBody SongRequest request) {
        return songService.save(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SongResponse> update(@PathVariable Long id, @Valid @RequestBody SongRequest request) {
        try {
            return ResponseEntity.ok(songService.update(id, request));
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
