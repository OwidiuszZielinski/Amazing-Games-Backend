package com.example.amazinggamesbackend.core.games;

import com.example.amazinggamesbackend.core.games.dto.GamesDTO;
import com.example.amazinggamesbackend.core.games.model.GamesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GamesService {


    @Autowired
    private GamesRepository gamesRepository;
    public GamesEntity addGame(GamesDTO game){
        GamesEntity newgame = new GamesEntity();
        newgame.fromDTO(game);
        return gamesRepository.save(newgame);
    }
    public ArrayList<GamesEntity> gamelist() {
        return (ArrayList<GamesEntity>) gamesRepository.findAll();

    }
}
