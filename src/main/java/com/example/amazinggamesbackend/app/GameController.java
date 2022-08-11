package com.example.amazinggamesbackend.app;

import com.example.amazinggamesbackend.core.games.GamesRepository;
import com.example.amazinggamesbackend.core.games.GamesService;
import com.example.amazinggamesbackend.core.games.dto.GamesDTO;
import com.example.amazinggamesbackend.core.games.model.GamesEntity;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
