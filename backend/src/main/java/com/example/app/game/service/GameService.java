package com.example.app.game.service;

import com.example.app.game.dto.GameRequest;
import com.example.app.game.dto.GameResponse;

import java.util.List;
import java.util.Optional;

public interface GameService {
    List<GameResponse> findAll();
    Optional<GameResponse> findById(Long id);
    GameResponse save(GameRequest request);
    GameResponse update(Long id, GameRequest request);
    void deleteById(Long id);
}
