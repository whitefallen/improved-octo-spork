package com.example.app.recipe.service;

import com.example.app.recipe.domain.Recipe;
import com.example.app.recipe.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    private Recipe recipe;

    @BeforeEach
    void setUp() {
        recipe = new Recipe();
        recipe.setTitle("Test Recipe");
        recipe.setDescription("A description");
        recipe.setInstructions("Some instructions");
        recipe.setIngredients("Ingredient 1, Ingredient 2");
        recipe.setFrontendUrl("http://example.com");
    }

    @Test
    void findAll_returnsAllRecipes() {
        when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipe));
        List<Recipe> result = recipeService.findAll();
        assertThat(result).hasSize(1);
        verify(recipeRepository).findAll();
    }

    @Test
    void findById_found() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        Optional<Recipe> result = recipeService.findById(1L);
        assertThat(result).isPresent();
    }

    @Test
    void findById_notFound() {
        when(recipeRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Recipe> result = recipeService.findById(99L);
        assertThat(result).isEmpty();
    }

    @Test
    void save_persistsRecipe() {
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        Recipe saved = recipeService.save(recipe);
        assertThat(saved).isNotNull();
        verify(recipeRepository).save(recipe);
    }

    @Test
    void update_existingRecipe() {
        Recipe existing = new Recipe();
        existing.setTitle("Old Title");
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(existing);

        Recipe updated = recipeService.update(1L, recipe);
        assertThat(updated.getTitle()).isEqualTo("Test Recipe");
    }

    @Test
    void update_notFound_throwsException() {
        when(recipeRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> recipeService.update(99L, recipe))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void deleteById_callsRepository() {
        doNothing().when(recipeRepository).deleteById(1L);
        recipeService.deleteById(1L);
        verify(recipeRepository).deleteById(1L);
    }
}
