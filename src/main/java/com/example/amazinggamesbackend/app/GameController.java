package com.example.amazinggamesbackend.app;

import com.example.amazinggamesbackend.core.games.GamesService;
import com.example.amazinggamesbackend.core.games.dto.GamesDTO;
import com.example.amazinggamesbackend.core.games.model.GamesEntity;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class GameController {

    @Autowired
    private GamesService gamesService;
    @Operation(summary = "add game")
    @PostMapping("/addgame")
    public String addGame(@RequestBody GamesDTO gamesDTO){
        gamesService.addGame(gamesDTO);
        return "game successfully added";
    }
    @Operation(summary = "get all games")
    @GetMapping("/allgames")
    public List<GamesEntity> getGames(){
        return gamesService.gamelist();
    }
    @Operation(summary = "get game by id")
    @GetMapping("/game/{Id}")
    public GamesEntity getGameById(@PathVariable int Id){
        return gamesService.getGameById(Id);
    }
    @Operation(summary = "get available games")
    @GetMapping("/allgames/available")
    public List<GamesEntity> getAvailableGames(){
        return gamesService.availablegamelist();
    }
}
