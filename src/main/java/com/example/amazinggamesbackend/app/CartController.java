package com.example.amazinggamesbackend.app;

import com.example.amazinggamesbackend.core.cart.CartRepository;
import com.example.amazinggamesbackend.core.cart.CartService;
import com.example.amazinggamesbackend.core.cart.dto.CartDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
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
    @PostMapping("/cart/{id}/games")
    public ResponseEntity addToCart(@PathVariable int id ,@RequestBody Integer itemId) {
        cartService.addGameToCart(id ,itemId);
        return ResponseEntity.ok().build();

    }

    @Operation(summary = "Delete from cart")
    @DeleteMapping("/cart/{id}")
    public ResponseEntity deleteGameById(@PathVariable int id ,@RequestBody Integer itemId) {
        cartService.deleteGameFromCart(id ,itemId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Clean cart")
    @PatchMapping("/cart/{id}")
    public ResponseEntity clearCart(@PathVariable int id) {
        cartService.cleanCart(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get user cart")
    @GetMapping("/cart/{id}/games")
    public CartDTO getCart(@PathVariable int id) {
        return cartService.getCartByUserId(id);
    }

}
