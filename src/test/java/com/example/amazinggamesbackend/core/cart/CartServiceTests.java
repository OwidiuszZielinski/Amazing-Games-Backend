package com.example.amazinggamesbackend.core.cart;

import com.example.amazinggamesbackend.core.cart.dto.CartDTO;
import com.example.amazinggamesbackend.core.cart.model.Cart;
import com.example.amazinggamesbackend.core.cart.model.CartDetail;
import com.example.amazinggamesbackend.core.games.GameService;
import com.example.amazinggamesbackend.core.games.dto.GameDTO;
import com.example.amazinggamesbackend.core.games.exceptions.GameNotFound;
import com.example.amazinggamesbackend.core.games.model.Game;
import com.example.amazinggamesbackend.core.users.UserService;
import com.example.amazinggamesbackend.core.users.model.User;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceTests {



    private CartService cartService ;

    private CartRepository cartRepository ;
    private UserService userService;
    private GameService gameService;



    @BeforeEach
    public void setUp() {
        cartRepository = mock(CartRepository.class);
        userService = mock(UserService.class);
        gameService = mock(GameService.class);
        cartService = new CartService(userService,cartRepository,gameService);
    }


    @Test
    void should_getCartByUserId() {
        Cart cartExample = new Cart();
        User user = new User();
        user.setId(1);
        cartExample.setUser(user);
        //given
        //To taka implementacja metody dowolny int w parametrze bo cos trzeba wpisac mozna wpisac cokolwiek
        //zwraca nam obiekt cartExample ktoremu zakodowalem id usera na 1
        when(cartRepository.findByUserId(Mockito.anyInt())).thenReturn(Optional.of(cartExample));
        //when
        //Wywolanie metody z serwisu z user id 1 powinno wywolac dla id 1 i zwrocic cartExample
        CartDTO cartByUserId = cartService.getCartByUserId(user.getId());
        //then
        //sprawdzamy czy metoda na repo wywolala sie raz przy wywolaniu cartService.getCartByUserId
        verify(cartRepository,times(1)).findByUserId(1);




    }

    @Test
    void should_checkCartExists() {
        //given
        Cart cartExample = new Cart();
        User user = new User();
        user.setId(1);
        cartExample.setUser(user);
        when(cartRepository.findByUserId(Mockito.anyInt())).thenReturn(Optional.of(cartExample));
        //when
        boolean checkCartExists = cartService.checkCartExists(user.getId());
        //then
        verify(cartRepository,times(1)).findByUserId(user.getId());
        assertTrue(checkCartExists);

    }


    @Test
    void should_addGameToCart_with_good_parameters() {
        //given
        Game givenGame = Game.builder().id(1).build();
        User givenUser = new User();
        givenUser.setId(1);
        Cart givenCart = Cart.builder()
                .user(givenUser)
                .cartDetails(List.of(new CartDetail(givenGame,1)))
                .build();

        when(cartRepository.findByUserId(givenUser.getId())).thenReturn(Optional.of(givenCart));
        when(cartRepository.save(Mockito.any(Cart.class))).thenReturn(givenCart);
        //when
        cartService.addGameToCart(givenUser.getId() ,givenGame.getId());
        //then
        verify(cartRepository,times(1)).save(givenCart);





    }

    @Test
    void deleteGameFromCart() {


    }

    @Test
    void cleanCart() {
    }

    @Test
    void createCartForUser() {
    }

    @Test
    void increaseGameQty() {
    }

    @Test
    void getUserCart() {
    }
}