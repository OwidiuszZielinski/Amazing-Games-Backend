package com.example.amazinggamesbackend.core.shoppingcart.model;

import com.example.amazinggamesbackend.core.games.model.GameEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    private GameEntity game;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    //@JsonIgnore
    private ShoppingCartEntity cart;

    private int quantity;

    public CartDetail(GameEntity game ,ShoppingCartEntity cart ,int quantity) {
        this.game = game;
        this.cart = cart;
        this.quantity = quantity;
    }
}
