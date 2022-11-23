package com.example.amazinggamesbackend.app;

import com.example.amazinggamesbackend.core.games.GameRepository;
import com.example.amazinggamesbackend.core.games.GameService;
import com.example.amazinggamesbackend.core.games.dto.DeleteArrayDTO;
import com.example.amazinggamesbackend.core.games.dto.GameEntityDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
public class GameController {

    private final GameRepository gameRepository;

    private final GameService gameService;

    @Autowired
    public GameController(GameRepository gameRepository ,GameService gameService) {
        this.gameRepository = gameRepository;
        this.gameService = gameService;
    }

    @Operation(summary = "Add game")
    @PostMapping("/games")
    public ResponseEntity addGame(@RequestBody GameEntityDTO gameDTO) {
        if (gameDTO.getTitle().isBlank() || gameDTO.getType().isBlank() || gameDTO.getDescription().isBlank()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } else {
            gameService.addGame(gameDTO);
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Get all games")
    @GetMapping("/games")
    public List<GameEntityDTO> getGames() {
        return gameService.getGames();
    }

    @Operation(summary = "Delete games")
    @DeleteMapping("/games")
    public ResponseEntity deleteGameById(@RequestBody DeleteArrayDTO dto) {
        if (gameRepository.findAllById(dto.getIds()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } else
            gameService.deleteGamesById(dto.getIds());
        return ResponseEntity.ok().build();
    }



    @Operation(summary = "Edit game by id")
    @PatchMapping("/games/{Id}")
    public ResponseEntity editGameById(@RequestBody GameEntityDTO gameDTO ,@PathVariable int Id) {
        gameService.updateGame(Id ,gameDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Set discount game")
    @GetMapping("/games/discount")
    public void setDiscount(){
        gameService.discountGame();
    }

}
