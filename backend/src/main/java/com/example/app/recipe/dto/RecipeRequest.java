package com.example.app.recipe.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RecipeRequest {
    @NotBlank
    private String title;
    private String description;
    private String instructions;
    private String ingredients;
    private String frontendUrl;
}
