package com.example.app.game.service;

import com.example.app.game.domain.Game;
import java.util.List;
import java.util.Optional;

public interface GameService {
    List<Game> findAll();
    Optional<Game> findById(Long id);
    Game save(Game game);
    Game update(Long id, Game game);
    void deleteById(Long id);
}
