package com.example.amazinggamesbackend.core.games;

import com.example.amazinggamesbackend.core.games.dto.GameDTO;
import com.example.amazinggamesbackend.core.games.model.GameEntity;
import com.example.amazinggamesbackend.core.orders.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GamesService {

    @Autowired
    GamesRepository gamesRepository;

    public GameEntity addGame(GameDTO game){
        GameEntity newGame = new GameEntity();
        newGame.fromDTO(game);
        return gamesRepository.save(newGame);
    }
    public List<GameEntity> getGames() {
        return gamesRepository.findAll();

    }
    public void deleteGamesById(List<Integer> ids){
        gamesRepository.deleteAllByIdInBatch(ids);

    }
    public GameEntity editGameById(int id,GameDTO gameDTO){
        GameEntity getGame = gamesRepository.findById(id).get();
        getGame.setTitle(gameDTO.getTitle());
        getGame.setType(gameDTO.getType());
        getGame.setDescription(gameDTO.getDescription());
        getGame.setPrice(gameDTO.getPrice());
        getGame.setRating(gameDTO.getRating());
        getGame.setAvailability(gameDTO.isAvailability());
        return gamesRepository.save(getGame);
    }
    public double calculateOrderValue(OrderDTO order){
        return gamesRepository.findAllById(order.getGames()).stream().mapToDouble(GameEntity::getPrice).sum();
    }

    public List<GameEntity> gamesInOrder(OrderDTO order){
        return gamesRepository.findAllById(order.getGames()).stream().collect(Collectors.toList());
    }

    public GameEntity getGameById(int id){
        return gamesRepository.findById(id).get();
    }



}
