package com.example.amazinggamesbackend.core.cart;



import com.example.amazinggamesbackend.core.cart.dto.CreateCartDTO;
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
        CartEntity cart = cartRepository.findByUserId(id).get();
        List<CartDetail> cartDetails = cart.getCartDetails();
        if (cartDetails.stream().anyMatch(game -> game.getGame().getId() == gameId)) {
            incraseGameQty(cart ,gameId);
        } else {
            cartDetails.add(new CartDetail(gamesService.getGameById(gameId) ,cart ,1));
        }
        cartRepository.save(cart);
    }


    //    public ShoppingCartEntity deleteGamesFromCart(int id ,EditShoppingCartDTO editShoppingCartDTO) {
//        ShoppingCartEntity getCartByUserID = getCartByUserId(id);
//        getCartByUserID.getCartDetails().stream().dropWhile(game -> game.getGame().getId() == editShoppingCartDTO.getIds().stream().findAny().get());
//        return shoppingCartRepository.save(getCartByUserID);
//
//    }
    //tylko id wepchnac usera bez DTO
    public void createCartForUser(CreateCartDTO cartDTO) {

        CartEntity CartForUser = new CartEntity();
        CartForUser.addUser(usersService.userById(cartDTO.getUserID()));
        cartRepository.save(CartForUser);
    }

    public void incraseGameQty(CartEntity cart ,int gameId) {
        CartDetail cartDetail = cart.getCartDetails().stream().filter(e -> e.getGame().getId() == gameId).findFirst().get();
        cartDetail.incraseQty();


    }


}
