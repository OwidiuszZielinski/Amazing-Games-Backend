package com.example.amazinggamesbackend.app;

import com.example.amazinggamesbackend.core.cart.exceptions.CartNotFound;
import com.example.amazinggamesbackend.core.games.DiscountService;
import com.example.amazinggamesbackend.core.games.GameService;
import com.example.amazinggamesbackend.core.games.dto.DeleteArrayDTO;
import com.example.amazinggamesbackend.core.games.dto.GameDTO;
import com.example.amazinggamesbackend.core.games.exceptions.FreeGame;
import com.example.amazinggamesbackend.core.games.exceptions.GameNotFound;
import com.example.amazinggamesbackend.core.games.model.BestsellerService;
import com.example.amazinggamesbackend.core.games.model.GameDayDiscount;
import com.example.amazinggamesbackend.exceptions.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


@RequiredArgsConstructor
@RestController
@RequestMapping("/games/")
public class GameController {

    @ExceptionHandler({GameNotFound.class, IllegalArgumentException.class, NoSuchElementException.class, FreeGame.class })
    public ResponseEntity<ErrorResponse> handleException(RuntimeException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private final GameService gameService;
    private final DiscountService discountService;
    private final BestsellerService bestsellerService;

    @Operation(summary = "Add game")
    @PostMapping
    ResponseEntity<GameDTO> addGame(@RequestBody GameDTO game) {
        GameDTO gameAdded = gameService.addGame(game);
        return new ResponseEntity<>(gameAdded, HttpStatus.CREATED);

    }

    @Operation(summary = "Get all games")
    @GetMapping
    public List<GameDTO> getGames() {
        return gameService.getGames();
    }

    @Operation(summary = "Delete games")
    @DeleteMapping
    public ResponseEntity<DeleteArrayDTO> deleteGameById(@RequestBody DeleteArrayDTO dto) {
        gameService.deleteGamesById(dto.getIds());
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Operation(summary = "Edit game by id")
    @PatchMapping("/{gameId}")
    public ResponseEntity<GameDTO> editGameById(@RequestBody GameDTO gameDTO, @PathVariable int gameId) {
        GameDTO updateGame = gameService.updateGame(gameId, gameDTO);
        return new ResponseEntity<>(updateGame,HttpStatus.OK);
    }

    @Operation(summary = "Set discount game")
    @GetMapping("/discount")
    public ResponseEntity<GameDayDiscount> setDiscount() {
        GameDayDiscount dayDiscount = discountService.discountGame();
        return new ResponseEntity<>(dayDiscount,HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Bestseller")
    @GetMapping("/games/bestseller")
    public ResponseEntity<GameDTO> getBestsellerService() {
        GameDTO bestseller = bestsellerService.bestseller();
        return new ResponseEntity<>(bestseller, HttpStatus.ACCEPTED);

    }

}
