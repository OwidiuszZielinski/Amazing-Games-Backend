package com.example.amazinggamesbackend.app;

import com.example.amazinggamesbackend.core.cart.CartRepository;
import com.example.amazinggamesbackend.core.cart.CartService;
import com.example.amazinggamesbackend.core.cart.dto.CreateCartDTO;
import com.example.amazinggamesbackend.core.cart.dto.CartDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CartController {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartService cartService;

    @Operation(summary = "Create new shopping cart")
    @PostMapping("/cart")
    public ResponseEntity newCart(@RequestBody CreateCartDTO userID) {
        if (cartRepository.findByUserId(userID.getUserID()).isPresent()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } else {
            cartService.createCartForUser(userID);
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Add to cart")
    @PostMapping("/cart/{id}/games")
    public ResponseEntity addToCart(@PathVariable int id ,@RequestBody Integer itemId) {
        cartService.addGameToCart(id ,itemId);
        return ResponseEntity.ok().build();

    }

//    @Operation(summary = "delete items")
//    @DeleteMapping("/cart/{id}")
//    public ResponseEntity deleteGameById(@PathVariable int id ,@RequestBody EditShoppingCartDTO editShoppingCartDTO) {
//        shoppingCartService.deleteItemsFromCart(id ,editShoppingCartDTO);
//        return ResponseEntity.ok().build();
//
//    }


    @Operation(summary = "Get items from user")
    @GetMapping("/cart/{id}/games")
    public CartDTO getCart(@PathVariable int id) {
        return cartService.getCartByUserId(id);
    }


}
