package com.example.app.recipe.service;

import com.example.app.recipe.domain.Recipe;
import java.util.List;
import java.util.Optional;

public interface RecipeService {
    List<Recipe> findAll();
    Optional<Recipe> findById(Long id);
    Recipe save(Recipe recipe);
    Recipe update(Long id, Recipe recipe);
    void deleteById(Long id);
}
