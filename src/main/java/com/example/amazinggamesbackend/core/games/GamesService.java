package com.example.amazinggamesbackend.core.games;

import com.example.amazinggamesbackend.core.games.dto.GameDTO;
import com.example.amazinggamesbackend.core.games.model.GameEntity;
import com.example.amazinggamesbackend.core.orders.dto.OrderDTO;
import com.example.amazinggamesbackend.core.shoppingcart.dto.AddToCartDTO;
import com.example.amazinggamesbackend.core.shoppingcart.model.CartDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GamesService {

    @Autowired
    GamesRepository gamesRepository;

    public GameEntity addGame(GameDTO game){
        GameEntity newgame = new GameEntity();
        newgame.fromDTO(game);
        return gamesRepository.save(newgame);
    }
    public List<GameEntity> gamelist() {
        return gamesRepository.findAll();

    }
    public void deleteGamesById(List<Integer> ids){
        gamesRepository.deleteAllByIdInBatch(ids);

    }
    public GameEntity editGameById(int id,GameDTO gameDTO){
        GameEntity getgame = gamesRepository.findById(id).get();
        getgame.setTitle(gameDTO.getTitle());
        getgame.setType(gameDTO.getType());
        getgame.setDescription(gameDTO.getDescription());
        getgame.setPrice(gameDTO.getPrice());
        getgame.setRating(gameDTO.getRating());
        getgame.setAvailability(gameDTO.isAvailability());
        return gamesRepository.save(getgame);
    }
    public double calculateOrderValue(OrderDTO order){
        return gamesRepository.findAllById(order.getGames()).stream().mapToDouble(GameEntity::getPrice).sum();
    }

    public List<GameEntity> gamesPerOrder(OrderDTO order){

        return gamesRepository.findAllById(order.getGames()).stream().collect(Collectors.toList());
    }



//    public List<CartGames> gamesToCartByEditDTO(List<Integer> list){
//        List<GameEntity> games = gamesRepository.findAllById(list.stream().collect(Collectors.toList()));
//        List<CartGames> scg = new ArrayList<>();
//
//        for(GameEntity x : games){
//          //  scg.add(new ShoppingCartGames(1,x));
//
//        }
//        return scg;
//    }
//    public List<GameEntity> gamesToCartByEditDTO(List<Integer> list){
//
//        return gamesRepository.findAllById(list.stream().collect(Collectors.toList()));
//    }

//    public GameEntity addGameToCart(AddToCartDTO itemID){
//        return gamesRepository.findById(itemID.getId()).get();
//    }
    public GameEntity addGameToCart(int id){
        return gamesRepository.findById(id).get();
    }



}
