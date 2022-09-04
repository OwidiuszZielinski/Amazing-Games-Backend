package com.example.amazinggamesbackend.core.shoppingcart;


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

    public ShoppingCartEntity getBasketByUserID(int id) {
        return shoppingCartRepository.findByUserId(id).get();

    }
    public ShoppingCartEntity deleteItem(int id,EditShoppingCartDTO editShoppingCartDTO){
            ShoppingCartEntity getbasket = shoppingCartRepository.findByUserId(id).get();
            getbasket.setGamesIDS(gamesService.gamesToBasket(editShoppingCartDTO));
            getbasket.setUser(getbasket.getUser());
            return shoppingCartRepository.save(getbasket);

    }
    public ShoppingCartEntity addToBasket(ShoppingCartDTO basket){
        ShoppingCartEntity newbasket = new ShoppingCartEntity();
        newbasket.setGamesIDS(gamesService.gamesToBasket(basket));
        newbasket.addUser(usersService.userById(basket.getUserID()));
        return shoppingCartRepository.save(newbasket);
    }



}
