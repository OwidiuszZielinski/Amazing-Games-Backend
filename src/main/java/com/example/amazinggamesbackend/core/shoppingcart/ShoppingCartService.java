package com.example.amazinggamesbackend.core.shoppingcart;


import com.example.amazinggamesbackend.core.games.model.GameEntity;
import com.example.amazinggamesbackend.core.shoppingcart.dto.AddToCartDTO;
import com.example.amazinggamesbackend.core.shoppingcart.dto.CreateShoppingCartDTO;
import com.example.amazinggamesbackend.core.shoppingcart.dto.EditShoppingCartDTO;
import com.example.amazinggamesbackend.core.shoppingcart.model.ShoppingCartEntity;
import com.example.amazinggamesbackend.core.games.GamesService;
import com.example.amazinggamesbackend.core.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {

    @Autowired
    UsersService usersService;
    @Autowired
    ShoppingCartRepository shoppingCartRepository;
    @Autowired
    GamesService gamesService;

    public ShoppingCartEntity getCartByUserId(int id) {
        return shoppingCartRepository.findByUserId(id).get();

    }

    public ShoppingCartEntity addtoCart(int id,AddToCartDTO itemID){
         ShoppingCartEntity getCart = shoppingCartRepository.findByUserId(id).get();
        if(getCart.getGames().stream().anyMatch(game -> game.getId() == itemID.getId())){
            getCart.setQuantity(getCart.getQuantity()+1);
            getCart.setGames(getCart.getGames());
        }
        else
            getCart.setQuantity(getCart.getQuantity());
            getCart.getGames().add(gamesService.addGameToCart(itemID).get());
            return shoppingCartRepository.save(getCart);

    }
    public ShoppingCartEntity deleteItemsFromCart(int id,EditShoppingCartDTO editShoppingCartDTO){
            ShoppingCartEntity getCartByUserID = (shoppingCartRepository.findByUserId(id).get());
            List<Integer> listGamesIds = new ArrayList<>();
            listGamesIds.addAll(getCartByUserID.getGames().stream().mapToInt(game -> game.getId()).boxed().collect(Collectors.toList()));
//            for(GameEntity x: getCartByUserID.getGames()){
//                listGamesIds.add(x.getId());
//            }
            listGamesIds.removeAll(editShoppingCartDTO.getIds());
            getCartByUserID.setGames(gamesService.gamesToCartByEditDTO(listGamesIds));
            getCartByUserID.setUser(getCartByUserID.getUser());
            return shoppingCartRepository.save(getCartByUserID);

    }
    public ShoppingCartEntity newCart(CreateShoppingCartDTO cartDTO){
        ShoppingCartEntity newCartForUser = new ShoppingCartEntity();
        newCartForUser.addUser(usersService.userById(cartDTO.getUserID()));
        return shoppingCartRepository.save(newCartForUser);
    }


}
