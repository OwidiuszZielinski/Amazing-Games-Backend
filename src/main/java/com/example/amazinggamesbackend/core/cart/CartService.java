package com.example.amazinggamesbackend.core.cart;


import com.example.amazinggamesbackend.core.cart.dto.CartDTO;
import com.example.amazinggamesbackend.core.cart.model.CartDetail;
import com.example.amazinggamesbackend.core.cart.model.CartEntity;
import com.example.amazinggamesbackend.core.games.GamesService;
import com.example.amazinggamesbackend.core.users.UsersRepository;
import com.example.amazinggamesbackend.core.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CartService {
    private final UsersService usersService;
    private final CartRepository cartRepository;
    private final GamesService gamesService;



    @Autowired
    public CartService(UsersService usersService ,CartRepository cartRepository ,GamesService gamesService) {
        this.usersService = usersService;
        this.cartRepository = cartRepository;
        this.gamesService = gamesService;
    }

    public CartDTO getCartByUserId(int userId) {
        return CartDTO.from(cartRepository.findByUserId(userId).get());

    }

    public void addGameToCart(int id ,int gameId) {
        //Fail first
        if(gamesService.getGameById(gameId) == null){
            throw new RuntimeException("No game in DB");
        }

        final CartEntity cart = getUserCart(id);
        final List<CartDetail> cartDetails = cart.getCartDetails();
        boolean gameIsInCart = cartDetails.stream().anyMatch(game -> game.getGame().getId() == gameId);
        if (gameIsInCart) {
            increaseGameQty(cart ,gameId);
        } else {
            cartDetails.add(new CartDetail(gamesService.getGameById(gameId) ,cart ,1));
        }
        cartRepository.save(cart);
    }

    public void deleteGameFromCart(int id, int gameId){
        CartEntity cart = getUserCart(id);
        cart.getCartDetails().removeIf(x -> x.getGame().getId() == gameId);
        cartRepository.save(cart);
    }

    public void cleanCart(int id){
        CartEntity cart = getUserCart(id);
        cart.getCartDetails().clear();
        cartRepository.save(cart);
    }

    public void createCartForUser(int id) {
        CartEntity userCart = new CartEntity();
        userCart.addUser(usersService.userById(id));
        cartRepository.save(userCart);
    }


    public void increaseGameQty(CartEntity cart ,int gameId) {
        CartDetail cartDetail = cart.getCartDetails().stream().filter(e -> e.getGame().getId() == gameId).findFirst().get();
        cartDetail.increaseQty();

    }
    //Service method return Entity
    public CartEntity getUserCart(int id){
        return cartRepository.findByUserId(id).get();
    }


}
