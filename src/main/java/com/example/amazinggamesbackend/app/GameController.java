package com.example.amazinggamesbackend.app;

import com.example.amazinggamesbackend.core.games.GamesService;
import com.example.amazinggamesbackend.core.games.dto.GamesDTO;
import com.example.amazinggamesbackend.core.games.model.GamesEntity;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
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
    @Operation(summary = "get game by title")
    @GetMapping("/game/{title}")
    public GamesEntity getGameById(@PathVariable String title){
        return gamesService.getGameByTitle(title);
    }
    @Operation(summary = "get available games")
    @GetMapping("/allgames/available")
    public List<GamesEntity> getAvailableGames(){
        return gamesService.availableGameList();
    }
    @Operation(summary = "delete game by title")
    @DeleteMapping("/deletegame/{title}")
    public void deleteGameByTitle(@PathVariable String title){
        gamesService.deleteGameByTitle(title);
    }
    @Operation(summary = "edit game by title")
    @PutMapping("/editgame/{title}")
    public String editGameByTitle(@PathVariable String title, @RequestBody GamesEntity gamesEntity){
        gamesService.editGameByTitle(title,gamesEntity);
        return "edit sccuessfull";
    }

}
