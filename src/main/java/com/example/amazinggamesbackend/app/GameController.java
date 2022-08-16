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
    @PostMapping("/games")
    public String addGame(@RequestBody GamesDTO gamesDTO){
        gamesService.addGame(gamesDTO);
        return "game successfully added";
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
