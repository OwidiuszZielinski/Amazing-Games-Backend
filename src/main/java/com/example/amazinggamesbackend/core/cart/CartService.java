package com.example.amazinggamesbackend.core.cart;



import com.example.amazinggamesbackend.core.cart.dto.CartDTO;
import com.example.amazinggamesbackend.core.cart.model.CartDetail;
import com.example.amazinggamesbackend.core.cart.model.CartEntity;
import com.example.amazinggamesbackend.core.games.GamesService;
import com.example.amazinggamesbackend.core.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CartService {


    @Autowired
    UsersService usersService;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    GamesService gamesService;

    public CartDTO getCartByUserId(int userId) {
        return CartDTO.from(cartRepository.findByUserId(userId).get());

    }

    public void addGameToCart(int id ,int gameId) {
        CartEntity cart = getUserCart(id);
        List<CartDetail> cartDetails = cart.getCartDetails();
        if (cartDetails.stream().anyMatch(game -> game.getGame().getId() == gameId)) {
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
    public CartEntity getUserCart(int id){
        return cartRepository.findByUserId(id).get();
    }


}
