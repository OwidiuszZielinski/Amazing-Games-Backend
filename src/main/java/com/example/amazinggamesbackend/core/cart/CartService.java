package com.example.amazinggamesbackend.core.cart;


import com.example.amazinggamesbackend.core.cart.dto.CartDTO;
import com.example.amazinggamesbackend.core.cart.exceptions.CartNotFound;
import com.example.amazinggamesbackend.core.games.exceptions.GameNotFound;
import com.example.amazinggamesbackend.core.cart.model.CartDetail;
import com.example.amazinggamesbackend.core.cart.model.Cart;
import com.example.amazinggamesbackend.core.games.GameService;
import com.example.amazinggamesbackend.core.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CartService {
    private final UserService userService;
    private final CartRepository cartRepository;
    private final GameService gameService;


    @Autowired
    public CartService(UserService userService ,CartRepository cartRepository ,GameService gameService) {
        this.userService = userService;
        this.cartRepository = cartRepository;
        this.gameService = gameService;
    }

    public CartDTO getCartByUserId(int userId) {
        return CartDTO.from(cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFound("Cart for this user not found")));
    }

    public boolean checkCartExists(int userId) {
        return cartRepository.findByUserId(userId).isPresent();
    }


    public CartDTO addGameToCart(int id ,int gameId) {
        Cart cart = getUserCart(id);
        final List<CartDetail> cartDetails = cart.getCartDetails();
        boolean gameIsInCart = cartDetails.stream().anyMatch(game -> game.getGame().getId() == gameId);
        if (gameIsInCart) {
            increaseGameQty(cart ,gameId);
        } else {
            cartDetails.add(new CartDetail(gameService.getGameById(gameId) ,cart ,1));
        }
        cartRepository.save(cart);
        return CartDTO.from(cart);
    }

    public void deleteGameFromCart(int id ,int gameId) {
        Cart cart = getUserCart(id);
        boolean removeIf = cart.getCartDetails().removeIf(x -> x.getGame().getId() == gameId);
        if (removeIf) {
            cartRepository.save(cart);
        } else
            throw new GameNotFound("game not found in cart");

    }

    public void cleanCart(int id) {
        final Cart cart = getUserCart(id);
        cart.getCartDetails().clear();
        cartRepository.save(cart);
    }

    public void createCartForUser(int id) {
        final Cart userCart = new Cart();
        userCart.setUser(userService.userById(id));
        cartRepository.save(userCart);
    }


    public void increaseGameQty(Cart cart ,int gameId) {
        CartDetail cartDetail = cart.getCartDetails()
                .stream()
                .filter(e -> e.getGame().getId() == gameId)
                .findFirst().orElseThrow(() -> new GameNotFound("game not found in cart"));
        cartDetail.increaseQty();

    }

    //Service method return Entity
    public Cart getUserCart(int id) {
        return cartRepository.findByUserId(id).orElseThrow(() -> new CartNotFound("Cart for this user not found"));
    }


}
