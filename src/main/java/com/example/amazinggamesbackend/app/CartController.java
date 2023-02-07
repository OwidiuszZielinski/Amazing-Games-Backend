package com.example.amazinggamesbackend.app;

import com.example.amazinggamesbackend.core.cart.CartRepository;
import com.example.amazinggamesbackend.core.cart.CartService;
import com.example.amazinggamesbackend.core.cart.dto.CartDTO;
import com.example.amazinggamesbackend.core.cart.exceptions.CartNotFound;
import com.example.amazinggamesbackend.core.games.exceptions.GameNotFound;
import com.example.amazinggamesbackend.exceptions.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/carts/")
public class CartController {


    @ExceptionHandler({ CartNotFound.class, GameNotFound.class, IllegalArgumentException.class })
    public ResponseEntity<ErrorResponse> handleException(RuntimeException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @Operation(summary = "Get user cart")
    @GetMapping("/{userId}")
    CartDTO getCart(@PathVariable int userId) {
        return cartService.getCartByUserId(userId);

    }

    @Operation(summary = "Create new shopping cart")
    @PostMapping
    ResponseEntity<CartDTO> newCart(@RequestBody int userId) {
        CartDTO cartForUser = cartService.createCartForUser(userId);
        return new ResponseEntity<>(cartForUser, HttpStatus.CREATED);
    }


    @Operation(summary = "Add to cart")
    @PutMapping("/{userId}")
    ResponseEntity<CartDTO> addToCart(@PathVariable int userId, @RequestBody Integer itemId) {
        CartDTO cartDTO = cartService.addGameToCart(userId, itemId);
        return new ResponseEntity<>(cartDTO, HttpStatus.ACCEPTED);

    }

    @Operation(summary = "Delete from cart")
    @DeleteMapping("/{userId}")
    public ResponseEntity<CartDTO> deleteGameById(@PathVariable int userId, @RequestBody Integer itemId) {
        cartService.deleteGameFromCart(userId, itemId);
        return ResponseEntity.ok().build();

    }

    @Operation(summary = "Clean cart")
    @PutMapping("/{userId}/clean")
    public ResponseEntity<CartDTO> clearCart(@PathVariable int userId) {
        CartDTO cartDTO = cartService.cleanCart(userId);
        return new ResponseEntity<>(cartDTO, HttpStatus.ACCEPTED);
    }


}
