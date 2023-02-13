package com.example.amazinggamesbackend.core.cart.model;

import com.example.amazinggamesbackend.core.games.model.Game;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class CartDetail {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private int quantity;

    public CartDetail(Game game, int quantity) {
        this.game = game;
        this.quantity = quantity;
    }

    public CartDetail(Game game, Cart cart, int quantity) {
        this.game = game;
        this.cart = cart;
        this.quantity = quantity;
    }

    public void increaseQty() {
        this.quantity = quantity + 1;
    }


}
