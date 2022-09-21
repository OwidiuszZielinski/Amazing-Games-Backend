package com.example.amazinggamesbackend.core.shoppingcart;


import com.example.amazinggamesbackend.core.shoppingcart.dto.AddToCartDTO;
import com.example.amazinggamesbackend.core.shoppingcart.dto.CreateShoppingCartDTO;
import com.example.amazinggamesbackend.core.shoppingcart.dto.ShoppingCartDTO;
import com.example.amazinggamesbackend.core.shoppingcart.model.CartDetail;
import com.example.amazinggamesbackend.core.shoppingcart.model.ShoppingCartEntity;
import com.example.amazinggamesbackend.core.games.GamesService;
import com.example.amazinggamesbackend.core.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class ShoppingCartService {

    final int INCREMENT = 1;

    @Autowired
    UsersService usersService;
    @Autowired
    ShoppingCartRepository shoppingCartRepository;
    @Autowired
    GamesService gamesService;

    //Ta metoda zwraca ladnie koszyk uzytkownika i operuje na tym w metodzie addToCart, ale w rescie nie zwraca
    public ShoppingCartDTO getCartByUserId(int userId) {


        System.out.println(ShoppingCartDTO.from(shoppingCartRepository.findByUserId(userId).get()).getUser());
        System.out.println(ShoppingCartDTO.from(shoppingCartRepository.findByUserId(userId).get()).getId());
        return ShoppingCartDTO.from(shoppingCartRepository.findByUserId(userId).get());


    }
//    public void addGameToCart(AddToCartDTO addDTO) {
//        ShoppingCartEntity cart = shoppingCartRepository.findByUserId(addDTO.getUserId()).get();
//        addDTO.getCartDetail().setGame();
//
//
//    }


//    public void addGameToCart(int id ,AddToCartDTO itemID) {
//        ShoppingCartEntity getCartByUserID = getCartByUserId(id);
//        if (getCartByUserID.getCartDetails().stream().anyMatch(game -> game.getGame().getId() == itemID.getId())) {
//            getCartByUserID.setCartDetails(getCartByUserID.getCartDetails());
//            quantityCartGame(getCartByUserID ,itemID);
//            shoppingCartRepository.save(getCartByUserID);
//
//        } else {
//            getCartByUserID.getCartDetails().add(new CartDetail(gamesService.addGameToCart(itemID) ,getCartByUserID ,INCREMENT));
//            getCartByUserID.getCartDetails().get(getCartByUserID.getCartDetails().size() - 1).setQuantity(1);
//        }
//        shoppingCartRepository.save(getCartByUserID);
//
//    }




//    public ShoppingCartEntity deleteGamesFromCart(int id ,EditShoppingCartDTO editShoppingCartDTO) {
//        ShoppingCartEntity getCartByUserID = getCartByUserId(id);
//        getCartByUserID.getCartDetails().stream().dropWhile(game -> game.getGame().getId() == editShoppingCartDTO.getIds().stream().findAny().get());
//        return shoppingCartRepository.save(getCartByUserID);
//
//    }
    //tylko id wepchnac usera bez DTO
    public void createCartForUser(CreateShoppingCartDTO cartDTO) {

        ShoppingCartEntity CartForUser = new ShoppingCartEntity();
        CartForUser.addUser(usersService.userById(cartDTO.getUserID()));
        shoppingCartRepository.save(CartForUser);
    }

//    public CartDetail quantityCartGame(ShoppingCartEntity getCart ,AddToCartDTO itemID) {
//        CartDetail getGames = getCart.getCartDetails().stream().filter(game -> game.getGame().getId() == itemID.getId()).findFirst().get();
//        getGames.setQuantity(getCart.getCartDetails().stream().filter(game -> game.getGame().getId() == itemID.getId()).findFirst().get().getQuantity() + INCREMENT);
//        return getGames;
//
//    }


}
