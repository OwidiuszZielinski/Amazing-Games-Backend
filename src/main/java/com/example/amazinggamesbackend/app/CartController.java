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

import java.util.NoSuchElementException;


@RestController
public class CartController {

    //CO POWINNY ZWRACAĆ REQUESTY? RESPONSEENTITY CZY ENCJE?

    @ExceptionHandler({ CartNotFound.class, GameNotFound.class, IllegalArgumentException.class })
    public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @Operation(summary = "Get user cart")
    @GetMapping("/cart/{userId}")
    CartDTO getCart(@PathVariable int userId) {
        return cartService.getCartByUserId(userId);

    }

    @Operation(summary = "Create new shopping cart")
    @PostMapping("/cart")
    ResponseEntity<CartDTO> newCart(@RequestBody int userID) {
        CartDTO cartForUser = cartService.createCartForUser(userID);
        return new ResponseEntity<>(cartForUser, HttpStatus.CREATED);
    }


    @Operation(summary = "Add to cart")
    @PutMapping("/cart/{userId}")
    public ResponseEntity<CartDTO> addToCart(@PathVariable int userId, @RequestBody Integer itemId) {
        try {
            CartDTO cartDTO = cartService.addGameToCart(userId, itemId);
            return new ResponseEntity<>(cartDTO, HttpStatus.ACCEPTED);
        } catch (GameNotFound | CartNotFound notFound) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

    }

    @Operation(summary = "Delete from cart")
    @DeleteMapping("/cart/{userId}")
    public ResponseEntity<CartDTO> deleteGameById(@PathVariable int userId, @RequestBody Integer itemId) {
        try {
            cartService.deleteGameFromCart(userId, itemId);
            return ResponseEntity.ok().build();
        } catch (CartNotFound | GameNotFound exception) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @Operation(summary = "Clean cart")
    @PutMapping("/cart/{userId}/clean")
    public ResponseEntity<CartDTO> clearCart(@PathVariable int userId) {
        CartDTO cartDTO = cartService.cleanCart(userId);
        return new ResponseEntity<>(cartDTO,HttpStatus.ACCEPTED);
    }


}
