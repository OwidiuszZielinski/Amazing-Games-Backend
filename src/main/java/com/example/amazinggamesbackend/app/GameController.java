package com.example.amazinggamesbackend.app;

import com.example.amazinggamesbackend.core.games.GamesRepository;
import com.example.amazinggamesbackend.core.games.GamesService;
import com.example.amazinggamesbackend.core.games.dto.GamesDTO;
import com.example.amazinggamesbackend.core.games.model.GamesEntity;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import java.util.List;


@RequiredArgsConstructor
@RestController
public class GameController {
    @Autowired
    GamesRepository gamesRepository;
    @Autowired
    private GamesService gamesService;
    @Operation(summary = "add game")
    @PostMapping("/games")
    public ResponseEntity addGame(@RequestBody GamesDTO gamesDTO){
        if (gamesDTO.getTitle().isBlank() || gamesDTO.getType().isBlank() || gamesDTO.getDescription().isBlank()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } else {
            gamesService.addGame(gamesDTO);
            return ResponseEntity.ok().build();
        }
    }
    @Operation(summary = "get all games")
    @GetMapping("/games")
    public List<GamesEntity> getGames(){
        return gamesService.gamelist();
    }
    @Operation(summary = "delete game by id")
    @DeleteMapping("games/{Id}")
    public void deleteGameById(@PathVariable int Id){
        gamesService.deleteGameById(Id);
    }
    @Operation(summary = "edit game by id")
    @PatchMapping("games/{Id}")
    public GamesEntity editGameById( @RequestBody GamesEntity gamesEntity,@PathVariable int Id){
        return gamesService.editGameById(Id,gamesEntity);

    }

}
