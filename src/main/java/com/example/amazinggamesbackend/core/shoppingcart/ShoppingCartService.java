package com.example.amazinggamesbackend.core.shoppingcart;


import com.example.amazinggamesbackend.core.shoppingcart.dto.AddToCartDTO;
import com.example.amazinggamesbackend.core.shoppingcart.dto.CreateShoppingCartDTO;
import com.example.amazinggamesbackend.core.shoppingcart.dto.EditShoppingCartDTO;
import com.example.amazinggamesbackend.core.shoppingcart.model.ShoppingCartEntity;
import com.example.amazinggamesbackend.core.games.GamesService;
import com.example.amazinggamesbackend.core.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public ShoppingCartEntity addToCart(int id,AddToCartDTO itemID){
         ShoppingCartEntity getCart = shoppingCartRepository.findByUserId(id).get();
         if(getCart.getCartGames().stream().anyMatch(game -> game.getGame().getId() == itemID.getId())){
                getCart.setCartGames(getCart.getCartGames());
                incrementCartItem(getCart,itemID);
                return shoppingCartRepository.save(getCart);

        }
        else {
            getCart.getCartGames().add(gamesService.addGameToCart(itemID));
            getCart.getCartGames().get(getCart.getCartGames().size() - 1).setQuantity(1);
        }
             return shoppingCartRepository.save(getCart);

    }
    public ShoppingCartEntity deleteItemsFromCart(int id,EditShoppingCartDTO editShoppingCartDTO){
            ShoppingCartEntity getCartByUserID = (shoppingCartRepository.findByUserId(id).get());
            List<Integer> listGamesIds = new ArrayList<>();
            listGamesIds.addAll(getCartByUserID.getCartGames().stream().mapToInt(game -> game.getId()).boxed().collect(Collectors.toList()));
            listGamesIds.removeAll(editShoppingCartDTO.getIds());
           // getCartByUserID.setCartGames(gamesService.gamesToCartByEditDTO(listGamesIds));
            getCartByUserID.setUser(getCartByUserID.getUser());
            return shoppingCartRepository.save(getCartByUserID);

    }
    public ShoppingCartEntity newCart(CreateShoppingCartDTO cartDTO){
        ShoppingCartEntity newCartForUser = new ShoppingCartEntity();
        newCartForUser.addUser(usersService.userById(cartDTO.getUserID()));
        return shoppingCartRepository.save(newCartForUser);
    }
    public ShoppingCartGames incrementCartItem(ShoppingCartEntity getCart,AddToCartDTO itemID){
        ShoppingCartGames getGames = getCart.getCartGames().stream().filter(game -> game.getGame().getId() == itemID.getId()).findFirst().get();
        getGames.setQuantity(getCart.getCartGames().stream().filter(game -> game.getGame().getId() == itemID.getId()).findFirst().get().getQuantity()+1);
        return getGames;

    }

}
