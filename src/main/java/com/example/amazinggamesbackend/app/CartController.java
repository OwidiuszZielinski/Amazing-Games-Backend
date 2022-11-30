package com.example.amazinggamesbackend.app;

import com.example.amazinggamesbackend.core.cart.CartRepository;
import com.example.amazinggamesbackend.core.cart.CartService;
import com.example.amazinggamesbackend.core.cart.dto.CartDTO;
import com.example.amazinggamesbackend.core.cart.exceptions.CartNotFound;
import com.example.amazinggamesbackend.core.games.exceptions.GameNotFound;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class CartController {
    //Wstrzykuje zaleznosci do pola za pomoca adnotacji, Dependency Injection
    //Tworzy sie Bean zarzadzany przez kontener springa? Tutaj bezposrednio na pole
    //Domyslnie powinno wtrzykiwac sie na konstruktor?
    //Wtrzykujemy zaleznosci na pola w klasach gdzie tego potrzebujemy
    //nie robimy metody statycznej ktora to zwraca bo jest ciezka do testowania.
    //nie odwolujemy sie do klasy ktora zwraca np cartRepository aby sie bezposrednio nie wiazac
    //losecoupling
    //stworzona i zarzadzana instancja przez springa to Bean za obsluge odpowiada
    //Spring IoC Container
//    @Autowired
//    public CartController(CartRepository cartRepository){
//        this.cartRepository = cartRepository;
//    }

    //CO POWINNY ZWRACAĆ REQUESTY? RESPONSEENTITY CZY ENCJE?

    private final CartRepository cartRepository;

    private final CartService cartService;

    @Autowired
    public CartController(CartRepository cartRepository ,CartService cartService) {
        this.cartRepository = cartRepository;
        this.cartService = cartService;
    }


    @Operation(summary = "Get user cart")
    @GetMapping("/cart/{userId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable int userId) {
        try {
            CartDTO cartByUserId = cartService.getCartByUserId(userId);
            return new ResponseEntity<>(cartByUserId,HttpStatus.OK);
        } catch (CartNotFound exists) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }


    @Operation(summary = "Create new shopping cart")
    @PostMapping("/cart")
    public ResponseEntity<CartDTO> newCart(@RequestBody int userID) {
        boolean cartExists = cartService.checkCartExists(userID);
        if (cartExists) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } else {
            cartService.createCartForUser(userID);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }

    @Operation(summary = "Add to cart")
    @PutMapping("/cart/{userId}")
    public ResponseEntity<CartDTO> addToCart(@PathVariable int userId ,@RequestBody Integer itemId) {
        try {
            CartDTO cartDTO = cartService.addGameToCart(userId ,itemId);
            return new ResponseEntity<>(cartDTO,HttpStatus.ACCEPTED);
        } catch (GameNotFound | CartNotFound notFound) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

    }

    @Operation(summary = "Delete from cart")
    @DeleteMapping("/cart/{userId}")
    public ResponseEntity<CartDTO> deleteGameById(@PathVariable int userId ,@RequestBody Integer itemId) {
       try{
            cartService.deleteGameFromCart(userId ,itemId);
            return ResponseEntity.ok().build();
        }catch (CartNotFound | GameNotFound exception){
           return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
    }
    }

    @Operation(summary = "Clean cart")
    @PutMapping("/cart/{userId}/clean")
    public ResponseEntity<CartDTO> clearCart(@PathVariable int userId) {
        boolean check = cartService.checkCartExists(userId);
        if (check) {
            cartService.cleanCart(userId);
            return ResponseEntity.ok().build();
        } else return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
    }


}
