package com.example.app.recipe.service;

import com.example.app.recipe.domain.Recipe;
import com.example.app.recipe.dto.RecipeRequest;
import com.example.app.recipe.dto.RecipeResponse;
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
    public List<RecipeResponse> findAll() {
        return recipeRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RecipeResponse> findById(Long id) {
        return recipeRepository.findById(id).map(this::toResponse);
    }

    @Override
    public RecipeResponse save(RecipeRequest request) {
        Recipe recipe = toEntity(request);
        return toResponse(recipeRepository.save(recipe));
    }

    @Override
    public RecipeResponse update(Long id, RecipeRequest request) {
        Recipe existing = recipeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Recipe not found: " + id));
        existing.setTitle(request.getTitle());
        existing.setDescription(request.getDescription());
        existing.setInstructions(request.getInstructions());
        existing.setIngredients(request.getIngredients());
        existing.setFrontendUrl(request.getFrontendUrl());
        return toResponse(recipeRepository.save(existing));
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }

    private Recipe toEntity(RecipeRequest request) {
        Recipe recipe = new Recipe();
        recipe.setTitle(request.getTitle());
        recipe.setDescription(request.getDescription());
        recipe.setInstructions(request.getInstructions());
        recipe.setIngredients(request.getIngredients());
        recipe.setFrontendUrl(request.getFrontendUrl());
        return recipe;
    }

    private RecipeResponse toResponse(Recipe recipe) {
        RecipeResponse response = new RecipeResponse();
        response.setId(recipe.getId());
        response.setTitle(recipe.getTitle());
        response.setDescription(recipe.getDescription());
        response.setInstructions(recipe.getInstructions());
        response.setIngredients(recipe.getIngredients());
        response.setFrontendUrl(recipe.getFrontendUrl());
        response.setCreatedAt(recipe.getCreatedAt());
        response.setUpdatedAt(recipe.getUpdatedAt());
        return response;
    }
}
