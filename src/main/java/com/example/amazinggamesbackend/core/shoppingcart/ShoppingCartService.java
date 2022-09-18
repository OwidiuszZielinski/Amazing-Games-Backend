package com.example.amazinggamesbackend.core.shoppingcart;


import com.example.amazinggamesbackend.core.shoppingcart.dto.AddToCartDTO;
import com.example.amazinggamesbackend.core.shoppingcart.dto.CreateShoppingCartDTO;
import com.example.amazinggamesbackend.core.shoppingcart.dto.EditShoppingCartDTO;
import com.example.amazinggamesbackend.core.shoppingcart.model.CartDetail;
import com.example.amazinggamesbackend.core.shoppingcart.model.ShoppingCartEntity;
import com.example.amazinggamesbackend.core.games.GamesService;
import com.example.amazinggamesbackend.core.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public ShoppingCartEntity addToCart(int id ,AddToCartDTO itemID) {
        ShoppingCartEntity getCartByUserID = getCartByUserId(id);
        if (getCartByUserID.getCartDetails().stream().anyMatch(game -> game.getGame().getId() == itemID.getId())) {
            getCartByUserID.setCartDetails(getCartByUserID.getCartDetails());
            incrementCartItem(getCartByUserID ,itemID);
            return shoppingCartRepository.save(getCartByUserID);

        } else {
            getCartByUserID.getCartDetails().add(new CartDetail(gamesService.addGameToCart(itemID) ,getCartByUserID ,1));
            // getCartByUserID.getCartDetails().add(gamesService.addGameToCart(itemID));
            getCartByUserID.getCartDetails().get(getCartByUserID.getCartDetails().size() - 1).setQuantity(1);
        }
        return shoppingCartRepository.save(getCartByUserID);

    }

    public ShoppingCartEntity deleteItemsFromCart(int id ,EditShoppingCartDTO editShoppingCartDTO) {
        ShoppingCartEntity getCartByUserID = getCartByUserId(id);
        deleteCartItem(editShoppingCartDTO.getIds() ,id);
//        List<Integer> listGamesIds = new ArrayList<>();
//        listGamesIds.addAll(getCartByUserID.getCartGames().stream().mapToInt(game -> game.getGame().getId()).boxed().collect(Collectors.toList()));
//        listGamesIds.removeAll(editShoppingCartDTO.getIds());
//        getCartByUserID.setCartGames(gamesService.gamesToCartByEditDTO(listGamesIds));
        return shoppingCartRepository.save(getCartByUserID);

    }

    public RuntimeException newCart(CreateShoppingCartDTO cartDTO) {
        if(userExistis(cartDTO.getUserID())){
            return new RuntimeException("Cart for this user exists");
        }
        else {
            ShoppingCartEntity newCartForUser = new ShoppingCartEntity();
            newCartForUser.addUser(usersService.userById(cartDTO.getUserID()));
            shoppingCartRepository.save(newCartForUser);
        }
        return new RuntimeException("Cart created");
    }

    public CartDetail incrementCartItem(ShoppingCartEntity getCart ,AddToCartDTO itemID) {
        CartDetail getGames = getCart.getCartDetails().stream().filter(game -> game.getGame().getId() == itemID.getId()).findFirst().get();
        getGames.setQuantity(getCart.getCartDetails().stream().filter(game -> game.getGame().getId() == itemID.getId()).findFirst().get().getQuantity() + 1);
        return getGames;

    }

    public boolean userExistis(int id) {

        if (shoppingCartRepository.findByUserId(id).isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    public void deleteCartItem(List<Integer> removeList ,int id) {


    }
}
