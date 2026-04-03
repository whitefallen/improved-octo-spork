package com.example.app.game.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GameRequest {
    @NotBlank
    private String title;
    private String genre;
    private String platform;
    private String developer;
    private Integer releaseYear;
    private String description;
}
