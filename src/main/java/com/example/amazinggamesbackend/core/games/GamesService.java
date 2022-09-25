package com.example.amazinggamesbackend.core.games;

import com.example.amazinggamesbackend.core.games.dto.GameEntityDTO;
import com.example.amazinggamesbackend.core.games.model.GameEntity;
import com.example.amazinggamesbackend.core.orders.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GamesService {

    @Autowired
    GamesRepository gamesRepository;

    public void addGame(GameEntityDTO game){
        GameEntity newGame = new GameEntity();
        newGame.fromDTO(game);
        gamesRepository.save(newGame);
    }
    public List<GameEntityDTO> getGames() {
        List<GameEntityDTO> tempGames = new ArrayList<>();
        for(GameEntity x : gamesRepository.findAll()){
            tempGames.add(GameEntityDTO.from(x));
        }
        return tempGames;

    }
    public void deleteGamesById(List<Integer> ids){
        gamesRepository.deleteAllByIdInBatch(ids);

    }
    public void updateGame(int id,GameEntityDTO gameDTO){
        GameEntity game = getGameById(id);
        game.fromDTO(gameDTO);
        gamesRepository.save(game);
    }
    public double calculateOrderValue(OrderDTO order){
        return gamesRepository.findAllById(order.getGames()).stream().mapToDouble(GameEntity::getPrice).sum();
    }
    //Service method return Entity
    public List<GameEntity> gamesInOrder(OrderDTO order){
        return new ArrayList<>(gamesRepository.findAllById(order.getGames()));
    }
    //Service method return Entity
    public GameEntity getGameById(int id){
        return gamesRepository.findById(id).get();
    }



}
