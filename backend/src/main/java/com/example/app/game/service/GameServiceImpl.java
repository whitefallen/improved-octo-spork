package com.example.app.game.service;

import com.example.app.game.domain.Game;
import com.example.app.game.dto.GameRequest;
import com.example.app.game.dto.GameResponse;
import com.example.app.game.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    @Override
    @Transactional(readOnly = true)
    public List<GameResponse> findAll() {
        return gameRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GameResponse> findById(Long id) {
        return gameRepository.findById(id).map(this::toResponse);
    }

    @Override
    public GameResponse save(GameRequest request) {
        Game game = toEntity(request);
        return toResponse(gameRepository.save(game));
    }

    @Override
    public GameResponse update(Long id, GameRequest request) {
        Game existing = gameRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Game not found: " + id));
        existing.setTitle(request.getTitle());
        existing.setGenre(request.getGenre());
        existing.setPlatform(request.getPlatform());
        existing.setDeveloper(request.getDeveloper());
        existing.setReleaseYear(request.getReleaseYear());
        existing.setDescription(request.getDescription());
        return toResponse(gameRepository.save(existing));
    }

    @Override
    public void deleteById(Long id) {
        gameRepository.deleteById(id);
    }

    private Game toEntity(GameRequest request) {
        Game game = new Game();
        game.setTitle(request.getTitle());
        game.setGenre(request.getGenre());
        game.setPlatform(request.getPlatform());
        game.setDeveloper(request.getDeveloper());
        game.setReleaseYear(request.getReleaseYear());
        game.setDescription(request.getDescription());
        return game;
    }

    private GameResponse toResponse(Game game) {
        GameResponse response = new GameResponse();
        response.setId(game.getId());
        response.setTitle(game.getTitle());
        response.setGenre(game.getGenre());
        response.setPlatform(game.getPlatform());
        response.setDeveloper(game.getDeveloper());
        response.setReleaseYear(game.getReleaseYear());
        response.setDescription(game.getDescription());
        response.setCreatedAt(game.getCreatedAt());
        response.setUpdatedAt(game.getUpdatedAt());
        return response;
    }
}
