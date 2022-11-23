package com.example.amazinggamesbackend.app;

import com.example.amazinggamesbackend.core.cart.CartRepository;
import com.example.amazinggamesbackend.core.cart.CartService;
import com.example.amazinggamesbackend.core.cart.dto.CartDTO;
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

    @Operation(summary = "Create new shopping cart")
    @PostMapping("/cart")
    public ResponseEntity newCart(@RequestBody int userID) {
        if (cartRepository.findByUserId(userID).isPresent()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } else {
            cartService.createCartForUser(userID);
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Add to cart")
    @PostMapping("/cart/{userId}")
    public ResponseEntity addToCart(@PathVariable int userId ,@RequestBody Integer itemId) {
        try {
            cartService.addGameToCart(userId ,itemId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @Operation(summary = "Delete from cart")
    @DeleteMapping("/cart/{userId}")
    public ResponseEntity deleteGameById(@PathVariable int userId ,@RequestBody Integer itemId) {
        cartService.deleteGameFromCart(userId ,itemId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Clean cart")
    @PutMapping("/cart/{userId}")
    public ResponseEntity clearCart(@PathVariable int userId) {
        cartService.cleanCart(userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get user cart")
    @GetMapping("/cart/{userId}")
    public CartDTO getCart(@PathVariable int userId) {
        return cartService.getCartByUserId(userId);
    }

}
