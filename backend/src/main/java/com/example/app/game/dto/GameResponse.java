package com.example.app.game.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GameResponse {
    private Long id;
    private String title;
    private String genre;
    private String platform;
    private String developer;
    private Integer releaseYear;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
