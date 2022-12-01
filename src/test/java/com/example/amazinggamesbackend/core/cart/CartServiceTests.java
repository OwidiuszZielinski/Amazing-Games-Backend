package com.example.amazinggamesbackend.core.cart;

import com.example.amazinggamesbackend.core.cart.dto.CartDTO;
import com.example.amazinggamesbackend.core.cart.exceptions.CartNotFound;
import com.example.amazinggamesbackend.core.cart.model.Cart;
import com.example.amazinggamesbackend.core.cart.model.CartDetail;
import com.example.amazinggamesbackend.core.games.GameService;
import com.example.amazinggamesbackend.core.games.model.Game;
import com.example.amazinggamesbackend.core.users.UserRepository;
import com.example.amazinggamesbackend.core.users.UserService;
import com.example.amazinggamesbackend.core.users.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class CartServiceTests {


    private CartService cartService;
    private CartRepository cartRepository;
    private UserRepository userRepository;
    private UserService userService;
    private GameService gameService;


    @BeforeEach
    public void setUp() {
        cartRepository = mock(CartRepository.class);
        userRepository = mock(UserRepository.class);
        userService = mock(UserService.class);
        gameService = mock(GameService.class);
        cartService = new CartService(userService ,cartRepository ,gameService);
    }


    @Test
    void should_getCartByUserId_params_OK() {
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
        verify(cartRepository ,times(1)).findByUserId(1);


    }

    @Test
    void should_checkCartExists_params_OK() {
        //given
        Cart cartExample = new Cart();
        User user = new User();
        user.setId(1);
        cartExample.setUser(user);
        when(cartRepository.findByUserId(Mockito.anyInt())).thenReturn(Optional.of(cartExample));
        //when
        boolean checkCartExists = cartService.checkCartExists(user.getId());
        //then
        verify(cartRepository ,times(1)).findByUserId(user.getId());
        assertTrue(checkCartExists);

    }


    @Test
    void should_addGameToCart_params_OK() {
        //given
        Game givenGame = Game.builder().id(1).build();
        User givenUser = new User();
        givenUser.setId(1);
        Cart givenCart = Cart.builder()
                .user(givenUser)
                .cartDetails(List.of(new CartDetail(givenGame ,1)))
                .build();

        when(cartRepository.findByUserId(givenUser.getId())).thenReturn(Optional.of(givenCart));
        when(cartRepository.save(Mockito.any(Cart.class))).thenReturn(givenCart);
        //when
        cartService.addGameToCart(givenUser.getId() ,givenGame.getId());
        //then
        verify(cartRepository ,times(1)).save(givenCart);


    }

    @Test
    void should_deleteGameFromCart_params_OK() {
        //given
        List<CartDetail> cartElements = new ArrayList<>();
        Game givenGame = Game.builder().id(1).build();
        User givenUser = new User();
        givenUser.setId(1);
        cartElements.add(new CartDetail(givenGame ,1));
        Cart givenCart = Cart.builder()
                .user(givenUser)
                .cartDetails(cartElements)
                .build();

        when(cartRepository.findByUserId(givenUser.getId())).thenReturn(Optional.of(givenCart));
        //when
        cartService.deleteGameFromCart(givenUser.getId() ,givenGame.getId());
        //then
        verify(cartRepository ,times(1)).save(givenCart);
        assertTrue(cartElements.isEmpty());


    }

    @Test
    void should_clearCart_params_OK() {
        //given
        List<CartDetail> list = new ArrayList<>();
        Game givenGame = Game.builder().id(1).build();
        Game givenGameTwo = Game.builder().id(2).build();
        User givenUser = new User();
        givenUser.setId(1);
        list.add(new CartDetail(givenGame ,1));
        list.add(new CartDetail(givenGameTwo ,1));
        Cart givenCart = Cart.builder()
                .user(givenUser)
                .cartDetails(list)
                .build();

        when(cartRepository.findByUserId(givenUser.getId())).thenReturn(Optional.of(givenCart));
        //when
        cartService.cleanCart(givenUser.getId());
        //then
        verify(cartRepository ,times(1)).save(givenCart);
        boolean checkCart = givenCart.getCartDetails().isEmpty();
        assertTrue(checkCart);

    }

    @Test
    void should_createCartForUser_params_OK() {

        //when
        cartService.createCartForUser(Mockito.anyInt());
        //then
        verify(cartRepository ,times(1)).save(Mockito.any(Cart.class));



    }

    @Test
    void should_increase_game_quantity_1pcs_in_cart_params_OK() {
        //given
        final Cart givenCart = new Cart();
        final Game givenGame = Game.builder().id(1).build();
        givenCart.setCartDetails(List.of(new CartDetail(givenGame ,1)));

        //when
        cartService.increaseGameQty(givenCart,givenGame.getId());
        //then
        int quantity = givenCart.getCartDetails().get(0).getQuantity();
        Assertions.assertEquals(quantity ,2);

    }

    @Test
    void should_increase_integer_oneUp_param_OK(){
        //given
        final CartDetail cartDetail = new CartDetail();
        cartDetail.setQuantity(5);
        //when
        cartDetail.increaseQty();
        //then
        Assertions.assertEquals(6,cartDetail.getQuantity());


    }
    @Test
    void should_getUserCart_params_OK() {
        User givenUser = new User();
        givenUser.setId(1);
        Cart givenCart = Cart.builder().user(givenUser).build();
        //given
        when(cartRepository.findByUserId(Mockito.anyInt())).thenReturn(Optional.of(givenCart));
        //when
        final Cart userCart = cartService.getUserCart(1);
        //then
        verify(cartRepository,times(1)).findByUserId(1);
        final int expectedUserFromCart = userCart.getUser().getId();
        final int givenUserFromCart = givenUser.getId();
        Assertions.assertEquals(expectedUserFromCart ,givenUserFromCart);
    }
    @Test
    void should_getUserCart_throw_CartNotFound_params_OK() {
        User givenUser = new User();
        givenUser.setId(1);
        Cart givenCart = Cart.builder().user(givenUser).build();
        //given
        when(cartRepository.findByUserId(1)).thenReturn(Optional.of(givenCart));

        //then
        Assertions.assertThrows(CartNotFound.class,()-> cartService.getUserCart(2));
        verify(cartRepository,times(1)).findByUserId(2);
    }


}