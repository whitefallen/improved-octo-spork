package com.example.app.game.service;

import com.example.app.game.domain.Game;
import com.example.app.game.dto.GameRequest;
import com.example.app.game.dto.GameResponse;
import com.example.app.game.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

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
    private GameRequest gameRequest;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.setTitle("Test Game");
        game.setGenre("Action");
        game.setPlatform("PC");
        game.setDeveloper("Test Dev");
        game.setReleaseYear(2024);
        game.setDescription("A great game");

        gameRequest = new GameRequest();
        gameRequest.setTitle("Test Game");
        gameRequest.setGenre("Action");
        gameRequest.setPlatform("PC");
        gameRequest.setDeveloper("Test Dev");
        gameRequest.setReleaseYear(2024);
        gameRequest.setDescription("A great game");
    }

    @Test
    void findAll_returnsAllGames() {
        when(gameRepository.findAll()).thenReturn(Arrays.asList(game));
        List<GameResponse> result = gameService.findAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Test Game");
    }

    @Test
    void findById_found() {
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        Optional<GameResponse> result = gameService.findById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getPlatform()).isEqualTo("PC");
    }

    @Test
    void findById_notFound() {
        when(gameRepository.findById(99L)).thenReturn(Optional.empty());
        assertThat(gameService.findById(99L)).isEmpty();
    }

    @Test
    void save_persistsGame() {
        when(gameRepository.save(any(Game.class))).thenReturn(game);
        GameResponse saved = gameService.save(gameRequest);
        assertThat(saved).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("Test Game");
        verify(gameRepository).save(any(Game.class));
    }

    @Test
    void update_existingGame() {
        Game existing = new Game();
        existing.setTitle("Old Title");
        when(gameRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(gameRepository.save(any(Game.class))).thenReturn(existing);

        GameResponse updated = gameService.update(1L, gameRequest);
        assertThat(updated.getTitle()).isEqualTo("Test Game");
    }

    @Test
    void update_notFound_throwsException() {
        when(gameRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> gameService.update(99L, gameRequest))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void deleteById_callsRepository() {
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        doNothing().when(gameRepository).delete(game);
        gameService.deleteById(1L);
        verify(gameRepository).delete(game);
    }

    @Test
    void deleteById_notFound_throwsException() {
        when(gameRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> gameService.deleteById(99L))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}
