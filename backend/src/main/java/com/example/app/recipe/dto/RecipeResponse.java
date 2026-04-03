package com.example.app.recipe.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecipeResponse {
    private Long id;
    private String title;
    private String description;
    private String instructions;
    private String ingredients;
    private String frontendUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
