package com.example.amazinggamesbackend.app;

import com.example.amazinggamesbackend.core.shoppingcart.ShoppingCartRepository;
import com.example.amazinggamesbackend.core.shoppingcart.ShoppingCartService;
import com.example.amazinggamesbackend.core.shoppingcart.dto.AddToCartDTO;
import com.example.amazinggamesbackend.core.shoppingcart.dto.CreateShoppingCartDTO;
import com.example.amazinggamesbackend.core.shoppingcart.dto.ShoppingCartDTO;
import com.example.amazinggamesbackend.core.shoppingcart.model.ShoppingCartEntity;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ShoppingCartController {
    @Autowired
    ShoppingCartRepository shoppingCartRepository;
    @Autowired
    ShoppingCartService shoppingCartService;

    @Operation(summary = "Create new shopping cart")
    @PostMapping("/cart")
    public ResponseEntity newCart(@RequestBody CreateShoppingCartDTO userID) {
        if(shoppingCartRepository.findByUserId(userID.getUserID()).isPresent()){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        else {
            shoppingCartService.createCartForUser(userID);
            return ResponseEntity.ok().build();
        }
    }
//    @Operation(summary = "Add to cart")
//    @PostMapping("/cart/{id}")
//    public ResponseEntity addToCart(@PathVariable int id ,@RequestBody AddToCartDTO itemId) {
//
//        shoppingCartService.addGameToCart(id,itemId);
//        return ResponseEntity.ok().build();
//
//    }
//    @Operation(summary = "delete items")
//    @DeleteMapping("/cart/{id}")
//    public ResponseEntity deleteGameById(@PathVariable int id ,@RequestBody EditShoppingCartDTO editShoppingCartDTO) {
//                 shoppingCartService.deleteItemsFromCart(id,editShoppingCartDTO);
//        return ResponseEntity.ok().build();
//
//    }
    @Operation(summary = "Get items from user")
    @GetMapping("/cart/{id}")
    public ShoppingCartDTO getCart(@PathVariable int id){
        return shoppingCartService.getCartByUserId(id);
    }


}
