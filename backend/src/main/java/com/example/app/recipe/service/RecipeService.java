package com.example.app.recipe.service;

import com.example.app.recipe.dto.RecipeRequest;
import com.example.app.recipe.dto.RecipeResponse;

import java.util.List;
import java.util.Optional;

public interface RecipeService {
    List<RecipeResponse> findAll();
    Optional<RecipeResponse> findById(Long id);
    RecipeResponse save(RecipeRequest request);
    RecipeResponse update(Long id, RecipeRequest request);
    void deleteById(Long id);
}
