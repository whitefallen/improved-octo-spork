package com.example.app.song.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SongResponse {
    private Long id;
    private String title;
    private String artist;
    private String album;
    private String genre;
    private Integer durationSeconds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
