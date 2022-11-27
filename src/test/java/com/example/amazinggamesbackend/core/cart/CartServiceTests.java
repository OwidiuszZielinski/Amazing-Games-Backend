package com.example.amazinggamesbackend.core.cart;

import com.example.amazinggamesbackend.core.cart.dto.CartDTO;
import com.example.amazinggamesbackend.core.cart.model.Cart;
import com.example.amazinggamesbackend.core.cart.model.CartDetail;
import com.example.amazinggamesbackend.core.games.GameRepository;
import com.example.amazinggamesbackend.core.games.GameService;
import com.example.amazinggamesbackend.core.games.model.Game;
import com.example.amazinggamesbackend.core.users.UserRepository;
import com.example.amazinggamesbackend.core.users.UserService;
import com.example.amazinggamesbackend.core.users.dto.UserDTO;
import com.example.amazinggamesbackend.core.users.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
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
    public void should_verify_FindCartByUserId() {
        Cart cartExample = new Cart();
        User user = new User();
        user.setId(1);
        cartExample.setUser(user);
        when(cartRepository.findByUserId(Mockito.anyInt())).thenReturn(Optional.of(cartExample));
        //given
        cartService.getCartByUserId(user.getId());
        //then
        verify(cartRepository).findByUserId(1);



    }

    @Test
    void checkCartExists() {
    }

    @Test
    void addGameToCart() {
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