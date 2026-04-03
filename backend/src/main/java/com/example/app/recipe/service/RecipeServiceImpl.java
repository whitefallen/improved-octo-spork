package com.example.app.recipe.service;

import com.example.app.recipe.domain.Recipe;
import com.example.app.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }

    @Override
    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe update(Long id, Recipe recipe) {
        Recipe existing = recipeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Recipe not found: " + id));
        existing.setTitle(recipe.getTitle());
        existing.setDescription(recipe.getDescription());
        existing.setInstructions(recipe.getInstructions());
        existing.setIngredients(recipe.getIngredients());
        existing.setFrontendUrl(recipe.getFrontendUrl());
        return recipeRepository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }
}
