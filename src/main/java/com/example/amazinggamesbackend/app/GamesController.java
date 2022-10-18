package com.example.amazinggamesbackend.app;

import com.example.amazinggamesbackend.core.games.GamesRepository;
import com.example.amazinggamesbackend.core.games.GamesService;
import com.example.amazinggamesbackend.core.games.dto.DeleteArrayDTO;
import com.example.amazinggamesbackend.core.games.dto.GameEntityDTO;
import com.example.amazinggamesbackend.core.tax.Rates;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.models.auth.In;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@RestController
public class GamesController {
    @Autowired
    GamesRepository gamesRepository;
    @Autowired
    private GamesService gamesService;

    @Operation(summary = "Add game")
    @PostMapping("/games")
    public ResponseEntity addGame(@RequestBody GameEntityDTO gameDTO) {
        if (gameDTO.getTitle().isBlank() || gameDTO.getType().isBlank() || gameDTO.getDescription().isBlank()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } else {
            gamesService.addGame(gameDTO);
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Get all games")
    @GetMapping("/games")
    public List<GameEntityDTO> getGames() {
        return gamesService.getGames();
    }

    @Operation(summary = "Delete games")
    @DeleteMapping("/games")
    public ResponseEntity deleteGameById(@RequestBody DeleteArrayDTO dto) {
        if (gamesRepository.findAllById(dto.getIds()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } else
            gamesService.deleteGamesById(dto.getIds());
        return ResponseEntity.ok().build();
    }



    @Operation(summary = "Edit game by id")
    @PatchMapping("games/{Id}")
    public ResponseEntity editGameById(@RequestBody GameEntityDTO gameDTO ,@PathVariable int Id) {
        gamesService.updateGame(Id ,gameDTO);
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "Get discount game")
    @GetMapping("games/discount")
    public int gameDiscount() throws FileNotFoundException {
        return gamesService.getDiscountGameFromFile();
    }

}
