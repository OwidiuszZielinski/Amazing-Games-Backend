package com.example.amazinggamesbackend.app;

import com.example.amazinggamesbackend.core.games.DiscountService;
import com.example.amazinggamesbackend.core.games.GameService;
import com.example.amazinggamesbackend.core.games.dto.DeleteArrayDTO;
import com.example.amazinggamesbackend.core.games.dto.GameDTO;
import com.example.amazinggamesbackend.core.games.model.BestsellerService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
public class GameController {

    private final GameService gameService;

    private final DiscountService discountService;
    private final BestsellerService bestsellerService;

    public GameController(GameService gameService ,DiscountService discountService ,BestsellerService bestsellerService) {
        this.gameService = gameService;
        this.discountService = discountService;
        this.bestsellerService = bestsellerService;
    }


    @Operation(summary = "Add game")
    @PostMapping("/games")
    public ResponseEntity<GameDTO> addGame(@RequestBody GameDTO gameDTO) {
        try {
            gameService.addGame(gameDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (IllegalArgumentException illegalArgument){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @Operation(summary = "Get all games")
    @GetMapping("/games")
    public ResponseEntity<List<GameDTO>> getGames() {
        List<GameDTO> games = gameService.getGames();
        return new ResponseEntity<>(games,HttpStatus.OK);
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
    @PatchMapping("/games/{Id}")
    public ResponseEntity<GameDTO> editGameById(@RequestBody GameDTO gameDTO ,@PathVariable int Id) {
        try {
            gameService.updateGame(Id ,gameDTO);
            return ResponseEntity.ok().build();
        }catch (IllegalArgumentException exception){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @Operation(summary = "Set discount game")
    @GetMapping("/games/discount")
    public ResponseEntity setDiscount(){
        discountService.discountGame();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Bestseller")
    @GetMapping("/games/bestseller")
    public ResponseEntity<GameDTO> getBestsellerService() {
        try {
            GameDTO bestseller = bestsellerService.bestseller();
            return new ResponseEntity<>(bestseller,HttpStatus.ACCEPTED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

    }

}
