package com.example.app.game.service;

import com.example.app.game.domain.Game;
import com.example.app.game.repository.GameRepository;
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
class GameServiceImplTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameServiceImpl gameService;

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.setTitle("Test Game");
        game.setGenre("Action");
        game.setPlatform("PC");
        game.setDeveloper("Test Dev");
        game.setReleaseYear(2024);
        game.setDescription("A great game");
    }

    @Test
    void findAll_returnsAllGames() {
        when(gameRepository.findAll()).thenReturn(Arrays.asList(game));
        List<Game> result = gameService.findAll();
        assertThat(result).hasSize(1);
    }

    @Test
    void findById_found() {
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        Optional<Game> result = gameService.findById(1L);
        assertThat(result).isPresent();
    }

    @Test
    void findById_notFound() {
        when(gameRepository.findById(99L)).thenReturn(Optional.empty());
        assertThat(gameService.findById(99L)).isEmpty();
    }

    @Test
    void save_persistsGame() {
        when(gameRepository.save(any(Game.class))).thenReturn(game);
        Game saved = gameService.save(game);
        assertThat(saved).isNotNull();
        verify(gameRepository).save(game);
    }

    @Test
    void update_existingGame() {
        Game existing = new Game();
        existing.setTitle("Old Title");
        when(gameRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(gameRepository.save(any(Game.class))).thenReturn(existing);

        Game updated = gameService.update(1L, game);
        assertThat(updated.getTitle()).isEqualTo("Test Game");
    }

    @Test
    void update_notFound_throwsException() {
        when(gameRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> gameService.update(99L, game))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void deleteById_callsRepository() {
        doNothing().when(gameRepository).deleteById(1L);
        gameService.deleteById(1L);
        verify(gameRepository).deleteById(1L);
    }
}
