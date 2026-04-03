package com.example.app.song.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SongRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String artist;
    private String album;
    private String genre;
    @NotNull
    @Positive
    private Integer durationSeconds;
}
