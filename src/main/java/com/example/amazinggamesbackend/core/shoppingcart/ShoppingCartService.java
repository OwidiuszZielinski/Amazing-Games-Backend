package com.example.amazinggamesbackend.core.shoppingcart;


import com.example.amazinggamesbackend.core.games.model.GameEntity;
import com.example.amazinggamesbackend.core.shoppingcart.dto.AddToCartDTO;
import com.example.amazinggamesbackend.core.shoppingcart.dto.ShoppingCartDTO;
import com.example.amazinggamesbackend.core.shoppingcart.dto.EditShoppingCartDTO;
import com.example.amazinggamesbackend.core.shoppingcart.model.ShoppingCartEntity;
import com.example.amazinggamesbackend.core.games.GamesService;
import com.example.amazinggamesbackend.core.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        getCart.getGames().add(gamesService.addGameToCart(itemID).get());
        return shoppingCartRepository.save(getCart);

    }
    public ShoppingCartEntity deleteItemFromCart(int id,EditShoppingCartDTO editShoppingCartDTO){
            ShoppingCartEntity getCart = shoppingCartRepository.findByUserId(id).get();
        getCart.setGames(gamesService.gamesToCartByEditDTO(editShoppingCartDTO));
        getCart.setUser(getCart.getUser());
            return shoppingCartRepository.save(getCart);

    }
    public ShoppingCartEntity addToCart(ShoppingCartDTO basket){
        ShoppingCartEntity newCart = new ShoppingCartEntity();
        newCart.setGames(gamesService.gamesToCartByDTO(basket));
        newCart.addUser(usersService.userById(basket.getUserID()));
        return shoppingCartRepository.save(newCart);
    }



}
