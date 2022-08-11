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
    public GamesEntity getGameByTitle(String title){
        return gamesRepository.findAll().stream().filter(e->e.getTitle().equalsIgnoreCase(title)).findFirst().get();
    }
    public ArrayList<GamesEntity> availableGameList(){
       return new ArrayList<GamesEntity>(gamesRepository.findAll().stream().filter(gamesEntity -> gamesEntity.isAvailability()==true).toList());
    }
    public void deleteGameByTitle(String title){
        gamesRepository.delete(gamesRepository.findAll().stream().filter(e->e.getTitle().equalsIgnoreCase(title)).findFirst().get());
    }
    public GamesEntity editGameByTitle(String title,GamesEntity gamesEntity){
        GamesEntity getgame = gamesRepository.findAll().stream().filter(e->e.getTitle().equalsIgnoreCase(title)).findFirst().get();
        getgame.setTitle(gamesEntity.getTitle());
        getgame.setGroupe(gamesEntity.getGroupe());
        getgame.setDescription(gamesEntity.getDescription());
        getgame.setPrice(gamesEntity.getPrice());
        getgame.setRating(gamesEntity.getRating());
        getgame.setQuantity(gamesEntity.getQuantity());
        return  gamesRepository.save(getgame);
    }
}
