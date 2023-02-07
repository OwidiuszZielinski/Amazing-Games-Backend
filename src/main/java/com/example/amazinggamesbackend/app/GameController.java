package com.example.amazinggamesbackend.app;

import com.example.amazinggamesbackend.core.games.DiscountService;
import com.example.amazinggamesbackend.core.games.GameService;
import com.example.amazinggamesbackend.core.games.dto.DeleteArrayDTO;
import com.example.amazinggamesbackend.core.games.dto.GameDTO;
import com.example.amazinggamesbackend.core.games.exceptions.GameNotFound;
import com.example.amazinggamesbackend.core.games.model.BestsellerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class GameController {

    private final GameService gameService;
    private final DiscountService discountService;
    private final BestsellerService bestsellerService;

//    public GameController(GameService gameService ,DiscountService discountService ,BestsellerService bestsellerService) {
//        this.gameService = gameService;
//        this.discountService = discountService;
//        this.bestsellerService = bestsellerService;
//    }


    //Jackson obczaj do automatycznego mapowania
    //Mapowanie automatyczne w springu
    //W powaznych projektach  w request body piszemy do czego to dotyczy np createGameCommand zamist gameDTO klasa etc
    @Operation(summary = "Add game")
    @PostMapping("/games")
    public ResponseEntity<GameDTO> addGame(@RequestBody GameDTO gameDTO) {
        try {
            GameDTO gameAdded = gameService.addGame(gameDTO);
            return new ResponseEntity<>(gameAdded, HttpStatus.CREATED);
        } catch (IllegalArgumentException illegalArgument) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @Operation(summary = "Get all games")
    @GetMapping("/games")
    public List<GameDTO> getGames() {
        return gameService.getGames();
    }

    @Operation(summary = "Delete games")
    @DeleteMapping("/games")
    public ResponseEntity<DeleteArrayDTO> deleteGameById(@RequestBody DeleteArrayDTO dto) {
        try {
            gameService.deleteGamesById(dto.getIds());
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @Operation(summary = "Edit game by id")
    @PatchMapping("/games/{gameId}")
    public ResponseEntity<GameDTO> editGameById(@RequestBody GameDTO gameDTO, @PathVariable int gameId) {
        try {
            gameService.updateGame(gameId, gameDTO);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException | GameNotFound exception) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    //Jesli operacja cos zrobi i jest skonczona to powinienem to zwrocic tutaj sa losowe to warto zwrocic
    @Operation(summary = "Set discount game")
    @GetMapping("/games/discount")
    public ResponseEntity setDiscount() {
        discountService.discountGame();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Bestseller")
    @GetMapping("/games/bestseller")
    public ResponseEntity<GameDTO> getBestsellerService() {
        try {
            GameDTO bestseller = bestsellerService.bestseller();
            return new ResponseEntity<>(bestseller, HttpStatus.ACCEPTED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

    }

}
