package com.example.amazinggamesbackend.core.games;

import com.example.amazinggamesbackend.core.games.dto.GamesDTO;
import com.example.amazinggamesbackend.core.games.model.GamesEntity;
import com.example.amazinggamesbackend.core.orders.dto.OrdersDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    public List<GamesEntity> gamelist() {
        return gamesRepository.findAll();

    }
    public void deleteGameById(int Id){
        gamesRepository.delete(gamesRepository.findById(Id).get());
    }
    public GamesEntity editGameById(int id,GamesDTO gamesDTO){
        GamesEntity getgame = gamesRepository.findById(id).get();
        getgame.setTitle(gamesDTO.getTitle());
        getgame.setType(gamesDTO.getType());
        getgame.setDescription(gamesDTO.getDescription());
        getgame.setPrice(gamesDTO.getPrice());
        getgame.setRating(gamesDTO.getRating());
        getgame.setAvailability(gamesDTO.isAvailability());
        return gamesRepository.save(getgame);
    }
    public double calculateOrderValue(OrdersDTO order){
        System.out.println(order.getGames());
        return gamesRepository.findAllById(order.getGames()).stream().mapToDouble(GamesEntity::getPrice).sum();
    }
}
