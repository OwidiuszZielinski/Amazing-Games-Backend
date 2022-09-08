package com.example.amazinggamesbackend.app;

import com.example.amazinggamesbackend.core.games.GamesRepository;
import com.example.amazinggamesbackend.core.games.GamesService;
import com.example.amazinggamesbackend.core.games.dto.DeleteGamesDTO;
import com.example.amazinggamesbackend.core.games.dto.GameDTO;

import com.example.amazinggamesbackend.core.games.model.GameEntity;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RequiredArgsConstructor
@RestController
public class GamesController {
    @Autowired
    GamesRepository gamesRepository;
    @Autowired
    private GamesService gamesService;

    @Operation(summary = "add game")
    @PostMapping("/games")
    public ResponseEntity addGame(@RequestBody GameDTO gameDTO) {
        if (gameDTO.getTitle().isBlank() || gameDTO.getType().isBlank() || gameDTO.getDescription().isBlank()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } else {
            gamesService.addGame(gameDTO);
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "get all games")
    @GetMapping("/games")
    public List<GameEntity> getGames() {
        return gamesService.gamelist();
    }

    @Operation(summary = "delete games")
    @DeleteMapping("/games")
    public ResponseEntity deleteGameById(@RequestBody DeleteGamesDTO deleteGamesDTO) {
        if (gamesRepository.findAllById(deleteGamesDTO.getIds()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        if (deleteGamesDTO.getIds().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } else
            gamesService.deleteGamesById(deleteGamesDTO.getIds());
        return ResponseEntity.ok().build();

    }

    @Operation(summary = "edit game by id")
    @PatchMapping("games/{Id}")
    public GameEntity editGameById(@RequestBody GameDTO gameDTO ,@PathVariable int Id) {
        return gamesService.editGameById(Id ,gameDTO);

    }

}
