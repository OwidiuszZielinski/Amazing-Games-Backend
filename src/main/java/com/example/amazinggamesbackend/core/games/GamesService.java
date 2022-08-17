package com.example.amazinggamesbackend.core.games;

import com.example.amazinggamesbackend.core.games.dto.GamesDTO;
import com.example.amazinggamesbackend.core.games.model.GamesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
    public void deleteGameById(int Id){
        gamesRepository.delete(gamesRepository.findById(Id).get());
    }
    public GamesEntity editGameById(int id,GamesEntity gamesEntity){
        GamesEntity getgame = gamesRepository.findById(id).get();
        getgame.setTitle(gamesEntity.getTitle());
        getgame.setType(gamesEntity.getType());
        getgame.setDescription(gamesEntity.getDescription());
        getgame.setPrice(gamesEntity.getPrice());
        getgame.setRating(gamesEntity.getRating());
        return gamesRepository.save(getgame);
    }
}
