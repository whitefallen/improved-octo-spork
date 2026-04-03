package com.example.app.game.service;

import com.example.app.game.domain.Game;
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
    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Game> findById(Long id) {
        return gameRepository.findById(id);
    }

    @Override
    public Game save(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public Game update(Long id, Game game) {
        Game existing = gameRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Game not found: " + id));
        existing.setTitle(game.getTitle());
        existing.setGenre(game.getGenre());
        existing.setPlatform(game.getPlatform());
        existing.setDeveloper(game.getDeveloper());
        existing.setReleaseYear(game.getReleaseYear());
        existing.setDescription(game.getDescription());
        return gameRepository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        gameRepository.deleteById(id);
    }
}
